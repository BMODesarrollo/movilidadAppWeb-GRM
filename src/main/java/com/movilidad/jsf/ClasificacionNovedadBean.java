/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.DispCausaEntradaFacadeLocal;
import com.movilidad.ejb.DispClasificacionNovedadFacadeLocal;
import com.movilidad.ejb.DispEstadoPendActualFacadeLocal;
import com.movilidad.ejb.DispSistemaFacadeLocal;
import com.movilidad.ejb.GopAlertaTiempoFueraServicioFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTelegramDetFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.DispCausaEntrada;
import com.movilidad.model.DispClasificacionNovedad;
import com.movilidad.model.DispEstadoPendActual;
import com.movilidad.model.DispSistema;
import com.movilidad.model.Empleado;
import com.movilidad.model.GopAlertaTiempoFueraServicio;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTelegramDet;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.CurrentLocation;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.ObjetoSigleton;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import com.movlidad.httpUtil.GeoService;
import com.movlidad.httpUtil.SenderNotificacionTelegram;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "clasificNovBean")
@ViewScoped
public class ClasificacionNovedadBean implements Serializable {

    /**
     * Creates a new instance of ClasificacionNovedadBean
     */
    public ClasificacionNovedadBean() {
    }

    @EJB
    private NovedadFacadeLocal novedadEJB;
    @EJB
    private DispSistemaFacadeLocal sistemaEJB;
    @EJB
    private DispCausaEntradaFacadeLocal dispCausaEntradaEJB;
    @EJB
    private DispEstadoPendActualFacadeLocal dispEstadoPendActualEJB;
    @EJB
    private DispClasificacionNovedadFacadeLocal clasificacionNovedadEJB;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private NotificacionTelegramDetFacadeLocal notificacionTelegramDetEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesoEjb;

    @EJB
    private DispSistemaFacadeLocal dispSistemaEjb;
    @EJB
    private GopAlertaTiempoFueraServicioFacadeLocal fueraServicioEJB;
    @Inject
    private novedadTipoAndDetalleBean tipoAndDetBean;
    @Inject
    private EmpleadoListJSFManagedBean empleadoListBean;
    @Inject
    private VehiculoEstadoHistoricoSaveBean vehiculoEstadoHistoricoSaveBean;
    @Inject
    private NovedadDuplicadaBean novedadDuplicadaBean;
    @Inject
    private MaximoBean maximoBean;
    @Inject
    private AtvNovedadBean atvNovedadBean;
    @Inject
    private NotificacionEmailBean notiEmailBean;

    private List<Novedad> listNovs;
    private List<Novedad> listVehInop;
    private List<String> listaTipoDetalle;
    private List<String> listaCausaTipoDetalle;
    private List<String> listaSistema;
    private List<String> listaEstadoPendActual;
    private List<String> listaTipoNovedad;
    private List<DispSistema> listDistSistema;
    private List<DispCausaEntrada> listDistCausaEntrada;
    private List<DispEstadoPendActual> listDistEstadoPend;
    private Date desde;
    private Date hasta;
    private Date fechaHoraHabilitacion;
    private UserExtended user;
    private Integer id_dis_sistema;
    private Integer id_dis_causa_entrada;
    private Integer id_dis_estado_pendiente;
    private String observacion;
    private DispClasificacionNovedad clasificacionNovedad;
    private Novedad selected;
    private Novedad novedad;
    private Empleado empleado;
    private Vehiculo vehiculo;
    private String c_vehiculo = "";
    private String cod_vehiculo = "";
    private boolean req_causa_entrada;
    private boolean inmovilizado;
    private boolean toMaximo;
    private Date fechaNov;
    private boolean b_input_causa_estrada;
    private boolean b_input_operador;
    private int param_gop_alerta_tiempo_fuera_servicio;
    private String updateClasificar;

    @PostConstruct
    public void init() {
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (MovilidadUtil.validarUrl("/clasificNovMtto/historicoNovMtto")) {
            consultarNovHistorico();
            tipoAndDetBean.setCompUpdateVistaCreateNov("msgs,form_modificar_nov_historico_dlg:novedad_tipo_detalle,"
                    + "form_modificar_nov_historico_dlg:id_estado_pend");
        } else {
            if (MovilidadUtil.validarUrl("/reportes/vehiculosInoperativos")) {
                consultarMttoPreventivo();
                tipoAndDetBean.setCompUpdateVistaCreateNov("msgs,form_modificar_nov_historico_dlg:novedad_tipo_detalle,"
                        + "form_modificar_nov_historico_dlg:id_estado_pend");
            } else {
                tipoAndDetBean.setCompUpdateVistaCreateNov("nov_mtto_form:novedad_tipo_detalle,"
                        + "nov_mtto_form:novedad_tipo,nov_mtto_form:novedad_tipo_detalleBtn,"
                        + "nov_mtto_form:id_estado_pend,nov_mtto_form:SOMAtv_lbl,"
                        + "nov_mtto_form:atv_lbl,nov_mtto_form:SOMAtv_lbl_ii");
                if (!MovilidadUtil.validarUrl("/parrilla/principal")) {
                    consultarNov();
                }
                try {
                    b_input_causa_estrada = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.NOV_MTTO_INPUT_CAUSA_ENTRADA).equals("1");
                    b_input_operador = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.NOV_MTTO_INPUT_OPERADOR).equals("1");
                } catch (Exception e) {
                    MovilidadUtil.addAdvertenciaMessage("Agregar en config empresa la siguiente llaves: NOV_MTTO_INPUT_CAUSA_ENTRADA, NOV_MTTO_INPUT_OPERADOR");
                    MovilidadUtil.updateComponent("msgs");
                }
            }
        }
    }

    void consultarSistema() {
        listDistSistema = sistemaEJB.findAllByEstadoReg();
    }

    public void consultarCausas() {
        req_causa_entrada = false;
        if (id_dis_sistema != null) {
            listDistCausaEntrada = dispCausaEntradaEJB.findAllByIdDispSistema(id_dis_sistema);
            if (listDistCausaEntrada != null && !listDistCausaEntrada.isEmpty()) {
                req_causa_entrada = true;
            }
        }
    }

    public void consultarNovHistorico() {
        listNovs = novedadEJB.findNovsAfectaDispByFecha(desde, hasta);
        listaFiltros();
    }

    public void consultarNov() {
        listNovs = novedadEJB.findNovsAfectaDisp(desde, hasta);
        listaFiltros();
    }
    
    public void consultarMttoPreventivo() {
        //listNovs = novedadEJB.findNovsMttoPreventivo(desde, hasta);
        //listaFiltros();
    }

    void consularEstados(int idVehiculoTipoEstadoDet) {
        listDistEstadoPend = dispEstadoPendActualEJB
                .findFirtStatusByidVehiculoTipoEstadoDetOrAll(
                        idVehiculoTipoEstadoDet, false);
    }

    public void nuevaNovMtto() {
        fechaNov = MovilidadUtil.fechaCompletaHoy();
        consultarSistema();
        resetNovMtto();
    }

    public void prepareEditarNovedad(Novedad param) {
        fechaNov = param.getFecha();
        novedad = param;
        clasificacionNovedad = novedad.getIdDispClasificacionNovedad();
        tipoAndDetBean.setNovedadTipo(novedad.getIdNovedadTipo());
        tipoAndDetBean.setNovedadTipoDet(novedad.getIdNovedadTipoDetalle());
        empleadoListBean.setEmpl(novedad.getIdEmpleado());
        observacion = novedad.getIdDispClasificacionNovedad().getObservacion();
        c_vehiculo = novedad.getIdVehiculo().getPlaca();
        id_dis_estado_pendiente = novedad.getIdDispClasificacionNovedad().getIdDispEstadoPendActual().getIdDispEstadoPendActual();
        consultarSistema();
    }

    void resetNovMtto() {
        novedad = null;
        clasificacionNovedad = null;
        id_dis_sistema = null;
        atvNovedadBean.setI_tipoAtención(0);
        atvNovedadBean.setI_operacionPatio(0);
        atvNovedadBean.setOperacionPatios(null);
        atvNovedadBean.setAtvTipoAtencion(null);
//        tipoAndDetBean.setNovedadTipo(null);
        tipoAndDetBean.setNovedadTipoDet(null);
        empleadoListBean.setEmpl(null);
        observacion = "";
        c_vehiculo = "";
        cod_vehiculo = "";
        id_dis_estado_pendiente = null;
        listDistEstadoPend = null;
    }

    public void setEstadoPendienteActual() {
        getPrimerEstado(tipoAndDetBean.getNovedadTipoDet());
    }

    /**
     * Realiza la búsqueda de un vehículo por placa
     */
    public void cargarVehiculo() {
        if (!(c_vehiculo.equals("") && c_vehiculo.isEmpty())) {
            Vehiculo vehiculo = vehiculoFacadeLocal.getVehiculoPlaca(c_vehiculo);
            if (vehiculo != null) {
                this.vehiculo = vehiculo;
                this.c_vehiculo = this.vehiculo.getPlaca();
                this.cod_vehiculo = this.vehiculo.getCodigo();
                if (novedadDuplicadaBean.validarDuplicidadNovConVehiculo(observacion, user.getUsername(), vehiculo.getIdVehiculo(), true)) {
                    novedadDuplicadaBean.setDialogToCerrar("nov_mtto_wv");
                    MovilidadUtil.addAdvertenciaMessage("Este vehículo tiene una novedad que afecte la disponibilidad abierta.");
                    return;
                }
                MovilidadUtil.updateComponent("nov_mtto_form:messages");
                MovilidadUtil.addSuccessMessage("Vehículo cargado");
                return;
            }
        }
        this.vehiculo = null;
        this.c_vehiculo = "";
        MovilidadUtil.addErrorMessage("Vehículo no valido");
        MovilidadUtil.updateComponent("nov_mtto_form:messages");
    }

    /**
     * Realiza la búsqueda de un vehículo por código
     */
    public void cargarVehiculoCodigo() {
        if (!(cod_vehiculo.equals("") && cod_vehiculo.isEmpty())) {
            Vehiculo vehiculo = vehiculoFacadeLocal.getVehiculoCodigo(cod_vehiculo);
            if (vehiculo != null) {
                this.vehiculo = vehiculo;
                this.cod_vehiculo = this.vehiculo.getCodigo();
                this.c_vehiculo = this.vehiculo.getPlaca();
                if (novedadDuplicadaBean.validarDuplicidadNovConVehiculo(observacion, user.getUsername(), vehiculo.getIdVehiculo(), true)) {
                    novedadDuplicadaBean.setDialogToCerrar("nov_mtto_wv");
                    MovilidadUtil.addAdvertenciaMessage("Este vehículo tiene una novedad que afecte la disponibilidad abierta.");
                    return;
                }
                MovilidadUtil.updateComponent("nov_mtto_form:messages");
                MovilidadUtil.addSuccessMessage("Vehículo cargado");
                return;
            }
        }
        this.vehiculo = null;
        this.cod_vehiculo = "";
        MovilidadUtil.addErrorMessage("Vehículo no valido");
        MovilidadUtil.updateComponent("nov_mtto_form:messages");
    }

    public void guardarNovMtto() throws MalformedURLException {
        if (validarNovMtto()) {
            return;
        }
        if (novedadDuplicadaBean.validarDuplicidadNovConVehiculo(
                observacion, user.getUsername(), vehiculo.getIdVehiculo(), true)) {
            return;
        }
        clasificacionNovedad = new DispClasificacionNovedad();
        novedad = new Novedad();
        clasificacionNovedad.setCreado(MovilidadUtil.fechaCompletaHoy());
        clasificacionNovedad.setEstadoReg(0);
        setValoresNovedad(novedad, clasificacionNovedad);
        setValoresClasificacion(clasificacionNovedad);
        clasificacionNovedadEJB.create(clasificacionNovedad);
        novedad.setEstadoNovedad(ConstantsUtil.NOV_ESTADO_ABIERTO);
        novedadEJB.create(novedad);
        consultarNov();

        /*
         * Si el detalle afecta disponibilidad, se modifica el estado del
         * vehículo por el estado parametrizado en el módulo Tipo Estado 
         * Detalle (Vehículos)
         */
        if (novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == 1) {
            Vehiculo vehiculo = novedad.getIdVehiculo();
            if (vehiculo != null) {
                vehiculo.setIdVehiculoTipoEstado(novedad.getIdNovedadTipoDetalle()
                        .getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstado());
                vehiculoFacadeLocal.edit(vehiculo);
                vehiculoEstadoHistoricoSaveBean.guardarEstadoVehiculoHistorico(
                        novedad.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet(), vehiculo, null,
                        novedad.getIdNovedadTipoDetalle().getIdNovedadTipoDetalle(), null, novedad.getObservaciones(),
                        novedad.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstado(), true);
            }
        }
        if (novedad.getIdNovedadTipoDetalle().getNotificacion() == 1) {
            notificar();
        }
        if (novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == 1) {
            notificarMtto();

            /**
             * Se envía notificación telegram a procesos que se encuentren
             * parametrizados en el módulo parametrización telegram
             */
            if (novedad.getIdNovedadTipoDetalle().getNotificacion() == ConstantsUtil.ON_INT
                    && novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {

                String codigoProcesoMtto = SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.NOTIFICACION_PROCESO_MTTO);
                if (codigoProcesoMtto.equals(novedad.getIdNovedadTipoDetalle()
                        .getIdNotificacionProcesos().getCodigoProceso())) {
                    return;
                }

                enviarNotificacionTelegramMtto(novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos());
            }

        }
        if (toMaximo) {
            maximoBean.sendToMaximo(novedad);
        }
        if (novedad.getIdNovedadTipoDetalle().getAtv() == 1
                && atvNovedadBean.isB_atv()
                && novedad.getIdVehiculo() != null) {
            try {
                atvNovedadBean.sendAtvNovedad(novedad);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        MovilidadUtil.hideModal("nov_mtto_wv");
        resetNovMtto();
    }

    void setValoresNovedad(Novedad paramNov, DispClasificacionNovedad paramClasf) {
        paramNov.setIdVehiculo(vehiculo);
        paramNov.setIdGopUnidadFuncional(vehiculo.getIdGopUnidadFuncional());
        paramNov.setFecha(fechaNov);
        paramNov.setIdNovedadTipo(tipoAndDetBean.getNovedadTipo());
        paramNov.setIdNovedadTipoDetalle(tipoAndDetBean.getNovedadTipoDet());
        if (paramNov.getIdNovedadTipoDetalle().getAtv() == 1
                && atvNovedadBean.isB_atv()
                && paramNov.getIdVehiculo() != null) {
            paramNov.setEstadoNovedad(ConstantsUtil.NOV_ESTADO_ABIERTO);
            paramNov.setIdAtvTipoAtencion(atvNovedadBean.getAtvTipoAtencion());

        }
        paramNov.setUsername(user.getUsername());
        if (b_input_operador) {
            paramNov.setIdEmpleado(empleadoListBean.getEmpl());
        }
        paramNov.setEstadoReg(0);
        paramNov.setObservaciones(observacion);
        paramNov.setCreado(MovilidadUtil.fechaCompletaHoy());
        paramNov.setIdDispClasificacionNovedad(paramClasf);
        if (paramNov.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == 1) {
            paramNov.setInmovilizado(inmovilizado ? 1 : 0);
        }
    }

    void setValoresClasificacion(DispClasificacionNovedad param) {
        param.setFechaHabilitacion(fechaHoraHabilitacion);
        param.setUsername(user.getUsername());
        param.setIdDispSistema(new DispSistema(id_dis_sistema));
        param.setIdDispCausaEntrada(req_causa_entrada && b_input_causa_estrada ? new DispCausaEntrada(id_dis_causa_entrada) : null);
        param.setIdDispEstadoPendActual(id_dis_estado_pendiente == null
                ? null : new DispEstadoPendActual(id_dis_estado_pendiente));
        param.setObservacion(observacion);
    }

    public void guardar() {
        if (validar()) {
            return;
        }
        boolean toCreate = false;
        if (clasificacionNovedad == null) {
            toCreate = true;
            clasificacionNovedad = new DispClasificacionNovedad();
            clasificacionNovedad.setCreado(MovilidadUtil.fechaCompletaHoy());
            clasificacionNovedad.setEstadoReg(0);
        } else {
            clasificacionNovedad.setModificado(MovilidadUtil.fechaCompletaHoy());
        }
        setValoresClasificacion(clasificacionNovedad);
        if (toCreate) {
            clasificacionNovedadEJB.create(clasificacionNovedad);
            novedadEJB.updateClasificacion(selected.getIdNovedad(), clasificacionNovedad.getIdDispClasificacionNovedad());
        } else {
            clasificacionNovedadEJB.edit(clasificacionNovedad);
        }
        MovilidadUtil.hideModal("wv_clasific_nov");
        MovilidadUtil.addSuccessMessage("Registro existoso.");
        consultarNov();
    }

    public boolean validarRolParaClacificarDesdeHistorico() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa()
                    .get(ConstantsUtil.KEY_CLASIFIC_FROM_HISTORICO)
                    .contains(auth.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    public boolean clasificarFromHistorico(Novedad param) {
        String get = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_ID_NOV_MTTO);
        return param.getEstadoNovedad() == ConstantsUtil.NOV_ESTADO_CERRADO && validarRolParaClacificarDesdeHistorico()
                && param.getIdNovedadTipo().getIdNovedadTipo().equals(Integer.parseInt(get));
    }

    /**
     *
     * @param param
     * @param modulo Módolo desde el cual se está invocando la clasificación
     */
    public void clasificar(Novedad param, int modulo) {
        if (param == null) {
            return;
        }
        if (modulo == 1) {
            updateClasificar = "form_clasific_nov_dlg,\n"
                    + "                form_clasific_nov:dt_clasific_nov,\n"
                    + "                msgs";
        } else if (modulo == 2) {
            updateClasificar = "msgs,form_historico_nov_mtto:dt_historico_nov_mtto";
        }
        selected = novedadEJB.find(param.getIdNovedad());
        req_causa_entrada = false;
        if (selected == null) {
            MovilidadUtil.addErrorMessage("Error inesperado.");
            return;
        }
        if (modulo == 1) {
            if (ConstantsUtil.NOV_ESTADO_CERRADO == selected.getEstadoNovedad()) {
                MovilidadUtil.addErrorMessage("La novedad ya ha sido cerrada.");
                return;
            }
        }
        consultarSistema();
        clasificacionNovedad = selected.getIdDispClasificacionNovedad();
        observacion = "";
        id_dis_sistema = null;
        id_dis_causa_entrada = null;
        fechaHoraHabilitacion = null;
        getPrimerEstado(selected.getIdNovedadTipoDetalle());
        if (clasificacionNovedad != null) {
            fechaHoraHabilitacion = clasificacionNovedad.getFechaHabilitacion();
            id_dis_sistema = clasificacionNovedad.getIdDispSistema().getIdDispSistema();
            id_dis_causa_entrada = clasificacionNovedad.getIdDispCausaEntrada() == null
                    ? null : clasificacionNovedad.getIdDispCausaEntrada().getIdDispCausaEntrada();
            id_dis_estado_pendiente = clasificacionNovedad.getIdDispEstadoPendActual() == null ? null
                    : clasificacionNovedad.getIdDispEstadoPendActual().getIdDispEstadoPendActual();
            observacion = clasificacionNovedad.getObservacion() == null ? "" : clasificacionNovedad.getObservacion();
            if (b_input_causa_estrada) {
                consultarCausas();
            }
        }
        MovilidadUtil.openModal("wv_clasific_nov");
    }

    public void prepareNovedadHistorico(Novedad param) {
        id_dis_estado_pendiente = null;
        selected = novedadEJB.find(param.getIdNovedad());
        id_dis_sistema = selected.getIdDispClasificacionNovedad().getIdDispSistema().getIdDispSistema();
        id_dis_estado_pendiente = selected.getIdDispClasificacionNovedad().getIdDispEstadoPendActual() == null ? null
                : selected.getIdDispClasificacionNovedad().getIdDispEstadoPendActual().getIdDispEstadoPendActual();
        tipoAndDetBean.setNovedadTipo(selected.getIdNovedadTipo());
        tipoAndDetBean.setNovedadTipoDet(selected.getIdNovedadTipoDetalle());
        tipoAndDetBean.setModulo(ConstantsUtil.MDL_HISTORICO_NOV);
        consultarSistema();
        int idVehiculoTipoEstadoDet = selected.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet() == null ? 0
                : selected.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstadoDet();

        consularEstados(idVehiculoTipoEstadoDet);
        if (id_dis_estado_pendiente == null) {
            getPrimerEstado(selected.getIdNovedadTipoDetalle());
        }
        MovilidadUtil.openModal("wv_modificar_nov_historico");
    }

    public void modificarNovHistorico() {
        Novedad n = novedadEJB.find(selected.getIdNovedad());
        n.setIdNovedadTipo(tipoAndDetBean.getNovedadTipo());
        n.setIdNovedadTipoDetalle(tipoAndDetBean.getNovedadTipoDet());
        n.setUsername(user.getUsername());
        n.setModificado(MovilidadUtil.fechaCompletaHoy());
        novedadEJB.edit(n);
        DispClasificacionNovedad clasificacionNovedad = n.getIdDispClasificacionNovedad();
        clasificacionNovedad.setUsername(user.getUsername());
        clasificacionNovedad.setIdDispSistema(new DispSistema(id_dis_sistema));
        clasificacionNovedad.setIdDispCausaEntrada(req_causa_entrada && b_input_causa_estrada ? new DispCausaEntrada(id_dis_causa_entrada) : null);
        clasificacionNovedad.setIdDispEstadoPendActual(id_dis_estado_pendiente == null
                ? null : new DispEstadoPendActual(id_dis_estado_pendiente));
        clasificacionNovedad.setModificado(MovilidadUtil.fechaCompletaHoy());
        clasificacionNovedadEJB.edit(clasificacionNovedad);

        MovilidadUtil.hideModal("wv_modificar_nov_historico");
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        consultarNovHistorico();
    }

    void getPrimerEstado(NovedadTipoDetalles param) {
        int idVehiculoTipoEstadoDet = param.getIdVehiculoTipoEstadoDet() == null ? 0
                : param.getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstadoDet();
        if (idVehiculoTipoEstadoDet > 0) {
            consularEstados(idVehiculoTipoEstadoDet);
            List<DispEstadoPendActual> lista = dispEstadoPendActualEJB.findFirtStatusByidVehiculoTipoEstadoDetOrAll(idVehiculoTipoEstadoDet, true);
            id_dis_estado_pendiente = lista.isEmpty() ? null
                    : lista.get(0).getIdDispEstadoPendActual();
        }
    }

    boolean validarNovMtto() {
        if (tipoAndDetBean.getNovedadTipo() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar un tipo de novedad.");
            return true;
        }
        if (tipoAndDetBean.getNovedadTipoDet() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar un tipo de novedad detalle.");
            return true;
        }
        if (tipoAndDetBean.getNovedadTipoDet().getAtv() == 1 //                && atvNovedadBean.isB_atv()
                ) {
            if (atvNovedadBean.getAtvTipoAtencion() == null) {
                MovilidadUtil.addErrorMessage("Se debe seleccionar un tipo de atención");
                return true;
            }
            if (atvNovedadBean.isRequeridoDestino() && atvNovedadBean.getOperacionPatios() == null) {
                MovilidadUtil.addErrorMessage("Se debe seleccionar un destino.");
                return true;
            }
        }
        if (vehiculo == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar un vehículo.");
            return true;
        }
        if (validar()) {
            return true;
        }
        return false;
    }

    boolean validar() {
        if (id_dis_sistema == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar un Sistema.");
            return true;
        }
        if (req_causa_entrada && b_input_causa_estrada) {
            if (id_dis_causa_entrada == null) {
                MovilidadUtil.addErrorMessage("Se debe seleccionar una causa de entrada.");
                return true;
            }
        }
        return false;
    }

    public int tiempoInoperativo(Novedad param) throws ParseException {
        if (param.getHora() != null) {
            if (param.getIdDispClasificacionNovedad() != null) {
                if (param.getIdDispClasificacionNovedad().getFechaHabilitacion() != null) {
                    Date converterToHour = MovilidadUtil.converterToHour(param.getFecha(), param.getHora());
                    return MovilidadUtil.getDiferenciaHora(converterToHour, param.getIdDispClasificacionNovedad().getFechaHabilitacion());
                }
            }
        }
        return 0;
    }

    /**
     * Carga los datos antes de crear una novedad
     *
     */
    public void nuevo() {
        resetNovedad();
        MovilidadUtil.openModal("novedadesPM");
    }

    /**
     * Carga los datos antes de crear una novedad
     *
     */
    public void resetNovedad() {
        novedad = new Novedad();
        tipoAndDetBean.setNovedadTipo(null);
        tipoAndDetBean.setNovedadTipoDet(null);
        empleado = null;
        c_vehiculo = "";
        inmovilizado = false;
    }

    /**
     * Realiza el envío de correo de las fallas registradas a las partes
     * interesadas ( Mantenimiento )
     */
    private void notificarMtto() {
        Map mapa = notiEmailBean.getMailParams(ConstantsUtil.ID_NOTIFICACION_CONF, ConstantsUtil.TEMPLATE_NOVEDADES_MTTO);

        String sistema = id_dis_sistema != null ? dispSistemaEjb.find(id_dis_sistema).getNombre() : "";
        String estado = id_dis_estado_pendiente != null ? dispEstadoPendActualEJB.find(id_dis_estado_pendiente).getNombre() : "";

        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("tipoNovedad", novedad.getIdNovedadTipo().getNombreTipoNovedad());
        mailProperties.put("tipoDetalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
        mailProperties.put("fechaHora", Util.dateFormat(novedad.getFecha()));
        mailProperties.put("empleado", empleado != null ? empleado.getCodigoTm() + " - " + empleado.getNombres() + " " + empleado.getApellidos() : "");
        mailProperties.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");
        mailProperties.put("sistema", sistema);
        mailProperties.put("estado", estado);
        mailProperties.put("reportadoPor", "");
        mailProperties.put("observacion", novedad.getObservaciones());
        String subject;
        String destinatarios;

        String codigoProcesoMtto = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.NOTIFICACION_PROCESO_MTTO);

        if (codigoProcesoMtto == null) {
            MovilidadUtil.addErrorMessage("NO se encontró parametrizada la lista de distribución correspondiente al área de MANTENIMIENTO");
            return;
        }

        NotificacionProcesos procesoMtto = notificacionProcesoEjb.findByCodigo(codigoProcesoMtto);

        if (procesoMtto != null) {
            destinatarios = procesoMtto.getEmails();
            subject = procesoMtto.getMensaje();

            if (procesoMtto.getNotificacionProcesoDetList() != null) {
                String destinatariosByUf = "";

                destinatariosByUf = MovilidadUtil.obtenerCorreosByUf(procesoMtto.getNotificacionProcesoDetList(), novedad.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                if (destinatariosByUf != null) {
                    destinatarios = destinatarios + "," + destinatariosByUf;
                }
            }

            enviarNotificacionTelegramMtto(procesoMtto);

            SendMails.sendEmail(mapa, mailProperties, subject,
                    "",
                    destinatarios,
                    "Notificaciones RIGEL", null);
        }
    }

    private void enviarNotificacionTelegramMtto(NotificacionProcesos proceso) {

        try {
            NotificacionTelegramDet detalle = notificacionTelegramDetEjb.findByIdNotifProcesoAndUf(proceso.getIdNotificacionProceso(), novedad.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

            if (detalle != null) {
                String sistema = id_dis_sistema != null ? dispSistemaEjb.find(id_dis_sistema).getNombre() : "";
                String urlPost = SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.URL_NOTIF_TELEGRAM_AFECTA_DISPO);

                if (urlPost != null) {
                    JSONObject objeto = SenderNotificacionTelegram.getObjeto();
                    objeto.put("chatId", detalle.getChatId());
                    objeto.put("token", detalle.getIdNotificacionTelegram().getToken());
                    objeto.put("nombreBot", detalle.getIdNotificacionTelegram().getNombreBot());
                    objeto.put("tipoNovedad", novedad.getIdNovedadTipo().getNombreTipoNovedad());
                    objeto.put("tipoDetalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
                    objeto.put("fechaHora", Util.dateFormat(novedad.getFecha()));
                    objeto.put("operador", empleado != null ? empleado.getNombres() + " " + empleado.getApellidos() : "");
//                    objeto.put("operador", empleado != null ? empleado.getCodigoTm() + " - " + empleado.getNombres() + " " + empleado.getApellidos() : "");
                    objeto.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");

                    if (novedad.getIdVehiculo() != null) {

                        String urlServicio = SingletonConfigEmpresa.getMapConfiMapEmpresa()
                                .get(ConstantsUtil.URL_SERVICE_LOCATION_VEHICLE);
                        if (urlServicio == null) {
                            MovilidadUtil.addErrorMessage("El sistema no cuenta con el endpoint que busca la ubicación de los vehículos");
                            return;
                        }

                        CurrentLocation ubicacionVeh = GeoService.getCurrentPosition(urlServicio + novedad.getIdVehiculo().getCodigo());

                        if (ubicacionVeh == null) {
                            objeto.put("latitud", "");
                            objeto.put("longitud", "");
                        } else {
                            objeto.put("latitud", ubicacionVeh.get_Latitude());
                            objeto.put("longitud", ubicacionVeh.get_Longitude());
                        }

                    } else {
                        objeto.put("latitud", "");
                        objeto.put("longitud", "");
                    }

                    objeto.put("sistema", sistema);
                    objeto.put("estado", novedad.getIdDispClasificacionNovedad().getIdDispEstadoPendActual() != null ? novedad.getIdDispClasificacionNovedad().getIdDispEstadoPendActual().getNombre() : "");
                    objeto.put("generadoPor", novedad.getUsername());
                    objeto.put("observacion", novedad.getObservaciones());
                    boolean sent = SenderNotificacionTelegram.send(objeto, urlPost);
                    System.out.println("sent-" + sent);

                }
            }

        } catch (Exception e) {
        }

    }

    /**
     * Realiza el envío de correo de las novedad registrada a las partes
     * interesadas
     */
    private void notificar() {
        Map mapa = notiEmailBean.getMailParams(ConstantsUtil.ID_NOTIFICACION_CONF, ConstantsUtil.TEMPLATE_NOVEDAD_PM_OP);
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(novedad.getFecha()));
        mailProperties.put("tipo", novedad.getIdNovedadTipo().getNombreTipoNovedad());
        mailProperties.put("detalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
        mailProperties.put("fechas", novedad.getDesde() != null && novedad.getHasta() != null ? Util.dateFormat(novedad.getDesde()) + " hasta " + Util.dateFormat(novedad.getHasta()) : "");
        mailProperties.put("operador", (empleado != null && b_input_operador) ? empleado.getCodigoTm() + " - " + empleado.getNombres() + " " + empleado.getApellidos() : "");
        mailProperties.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("observaciones", novedad.getObservaciones());
        String subject = "Novedad " + novedad.getIdNovedadTipo().getNombreTipoNovedad();
        String destinatarios = obtenerDestinatarios(novedad);
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    String obtenerDestinatarios(Novedad Param) {
        String destinatarios = "";
        //Busqueda Operador Máster
        if (Param.getIdEmpleado() != null && b_input_operador) {
            String correoMaster = "";
            if (Param.getIdEmpleado().getPmGrupoDetalleList().size() == 1) {
                correoMaster = Param.getIdEmpleado().getPmGrupoDetalleList().get(0).getIdGrupo().getIdEmpleado().getEmailCorporativo();
            }
            destinatarios = Param.getIdEmpleado() != null ? correoMaster + "," + Param.getIdEmpleado().getEmailCorporativo() : "";
        }
        if (Param.getIdNovedadTipoDetalle().getNotificacion() == 1) {
            if (Param.getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {
                if (destinatarios != null) {
                    destinatarios = destinatarios + "," + Param.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                } else {
                    destinatarios = Param.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                }

                if (Param.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getNotificacionProcesoDetList() != null) {
                    String destinatariosByUf = "";

                    destinatariosByUf = MovilidadUtil.obtenerCorreosByUf(Param.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getNotificacionProcesoDetList(), Param.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                    if (destinatariosByUf != null) {
                        destinatarios = destinatarios + "," + destinatariosByUf;
                    }
                }

            }
        }
        return destinatarios;
    }

    public void validarNovedadDuplicada() {
        if (tipoAndDetBean.getNovedadTipoDet() != null && vehiculo != null) {
            if (tipoAndDetBean.getNovedadTipoDet().getAfectaDisponibilidad() == 1) {
                if (novedadDuplicadaBean.validarDuplicidadNovConVehiculo(observacion, user.getUsername(),
                        vehiculo.getIdVehiculo(), true)) {
                    novedadDuplicadaBean.setDialogToCerrar("nov_mtto_wv");
                    return;
                }
            }
        }
    }

    public boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa()
                    .get(ConstantsUtil.ROLE_DISP_FLOTA)
                    .contains(auth.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    public int getDiasInoperativos(Date fechaNov) {
        return MovilidadUtil.getDiferenciaDia(fechaNov, MovilidadUtil.fechaCompletaHoy());
    }

    public void listaFiltros() {
        listaTipoDetalle = new ArrayList<>();
        listaCausaTipoDetalle = new ArrayList<>();
        listaSistema = new ArrayList<>();
        listaEstadoPendActual = new ArrayList<>();
        listaTipoNovedad = new ArrayList<>();
        if (listNovs != null) {
            for (Novedad d : listNovs) {
                listaTipoDetalle.add(d.getIdNovedadTipoDetalle() == null
                        ? "N/A" : d.getIdNovedadTipoDetalle().getTituloTipoNovedad());
                listaCausaTipoDetalle.add(d.getIdNovedadTipoDetalle() == null
                        ? "N/A" : d.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet() == null
                        ? "N/A" : d.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet().getNombre());
                listaSistema.add(d.getIdDispClasificacionNovedad() == null
                        ? "N/A" : d.getIdDispClasificacionNovedad().getIdDispSistema() == null
                        ? "N/A" : d.getIdDispClasificacionNovedad().getIdDispSistema().getNombre());
                listaEstadoPendActual.add(d.getIdDispClasificacionNovedad() == null
                        ? "N/A" : d.getIdDispClasificacionNovedad().getIdDispEstadoPendActual() == null
                        ? "N/A" : d.getIdDispClasificacionNovedad().getIdDispEstadoPendActual().getNombre());
                listaTipoNovedad.add(d.getIdNovedadTipo() == null ? "N/A" : d.getIdNovedadTipo().getNombreTipoNovedad());
            }
            listaTipoDetalle = listaTipoDetalle.stream().distinct().collect(Collectors.toList());
            listaCausaTipoDetalle = listaCausaTipoDetalle.stream().distinct().collect(Collectors.toList());
            listaSistema = listaSistema.stream().distinct().collect(Collectors.toList());
            listaEstadoPendActual = listaEstadoPendActual.stream().distinct().collect(Collectors.toList());
            listaTipoNovedad = listaTipoNovedad.stream().distinct().collect(Collectors.toList());
        }
    }

    public List<Novedad> getListNovs() {
        return listNovs;
    }

    public void setListNovs(List<Novedad> listNovs) {
        this.listNovs = listNovs;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Integer getId_dis_sistema() {
        return id_dis_sistema;
    }

    public void setId_dis_sistema(Integer id_dis_sistema) {
        this.id_dis_sistema = id_dis_sistema;
    }

    public Integer getId_dis_causa_entrada() {
        return id_dis_causa_entrada;
    }

    public void setId_dis_causa_entrada(Integer id_dis_causa_entrada) {
        this.id_dis_causa_entrada = id_dis_causa_entrada;
    }

    public Integer getId_dis_estado_pendiente() {
        return id_dis_estado_pendiente;
    }

    public void setId_dis_estado_pendiente(Integer id_dis_estado_pendiente) {
        this.id_dis_estado_pendiente = id_dis_estado_pendiente;
    }

    public List<DispSistema> getListDistSistema() {
        return listDistSistema;
    }

    public void setListDistSistema(List<DispSistema> listDistSistema) {
        this.listDistSistema = listDistSistema;
    }

    public List<DispCausaEntrada> getListDistCausaEntrada() {
        return listDistCausaEntrada;
    }

    public void setListDistCausaEntrada(List<DispCausaEntrada> listDistCausaEntrada) {
        this.listDistCausaEntrada = listDistCausaEntrada;
    }

    public List<DispEstadoPendActual> getListDistEstadoPend() {
        return listDistEstadoPend;
    }

    public void setListDistEstadoPend(List<DispEstadoPendActual> listDistEstadoPend) {
        this.listDistEstadoPend = listDistEstadoPend;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Novedad getSelected() {
        return selected;
    }

    public void setSelected(Novedad selected) {
        this.selected = selected;
    }

    public Date getFechaHoraHabilitacion() {
        return fechaHoraHabilitacion;
    }

    public void setFechaHoraHabilitacion(Date fechaHoraHabilitacion) {
        this.fechaHoraHabilitacion = fechaHoraHabilitacion;
    }

    public boolean isReq_causa_entrada() {
        return req_causa_entrada;
    }

    public void setReq_causa_entrada(boolean req_causa_entrada) {
        this.req_causa_entrada = req_causa_entrada;
    }

    public String getC_vehiculo() {
        return c_vehiculo;
    }

    public void setC_vehiculo(String c_vehiculo) {
        this.c_vehiculo = c_vehiculo;
    }

    public String getCod_vehiculo() {
        return cod_vehiculo;
    }

    public void setCod_vehiculo(String cod_vehiculo) {
        this.cod_vehiculo = cod_vehiculo;
    }

    public Date getFechaNov() {
        return fechaNov;
    }

    public void setFechaNov(Date fechaNov) {
        this.fechaNov = fechaNov;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public boolean isInmovilizado() {
        return inmovilizado;
    }

    public void setInmovilizado(boolean inmovilizado) {
        this.inmovilizado = inmovilizado;
    }

    public boolean isToMaximo() {
        return toMaximo;
    }

    public void setToMaximo(boolean toMaximo) {
        this.toMaximo = toMaximo;
    }

    public boolean isB_input_causa_estrada() {
        return b_input_causa_estrada;
    }

    public void setB_input_causa_estrada(boolean b_input_causa_estrada) {
        this.b_input_causa_estrada = b_input_causa_estrada;
    }

    public boolean isB_input_operador() {
        return b_input_operador;
    }

    public void setB_input_operador(boolean b_input_operador) {
        this.b_input_operador = b_input_operador;
    }

    public int getParam_gop_alerta_tiempo_fuera_servicio() {
        if (ObjetoSigleton.getGopAlertaTiempoFueraServicio() == null) {
            List<GopAlertaTiempoFueraServicio> findEstadoReg = fueraServicioEJB.findEstadoReg();
            if (findEstadoReg.isEmpty()) {
                return 0;
            }
            ObjetoSigleton.setGopAlertaTiempoFueraServicio(findEstadoReg.get(0));
        }
        param_gop_alerta_tiempo_fuera_servicio = ObjetoSigleton.getGopAlertaTiempoFueraServicio().getDias();
        return param_gop_alerta_tiempo_fuera_servicio;
    }

    public void setParam_gop_alerta_tiempo_fuera_servicio(int param_gop_alerta_tiempo_fuera_servicio) {
        this.param_gop_alerta_tiempo_fuera_servicio = param_gop_alerta_tiempo_fuera_servicio;
    }

    public List<String> getListaTipoDetalle() {
        return listaTipoDetalle;
    }

    public void setListaTipoDetalle(List<String> listaTipoDetalle) {
        this.listaTipoDetalle = listaTipoDetalle;
    }

    public List<String> getListaCausaTipoDetalle() {
        return listaCausaTipoDetalle;
    }

    public void setListaCausaTipoDetalle(List<String> listaCausaTipoDetalle) {
        this.listaCausaTipoDetalle = listaCausaTipoDetalle;
    }

    public List<String> getListaSistema() {
        return listaSistema;
    }

    public void setListaSistema(List<String> listaSistema) {
        this.listaSistema = listaSistema;
    }

    public List<String> getListaEstadoPendActual() {
        return listaEstadoPendActual;
    }

    public void setListaEstadoPendActual(List<String> listaEstadoPendActual) {
        this.listaEstadoPendActual = listaEstadoPendActual;
    }

    public List<String> getListaTipoNovedad() {
        return listaTipoNovedad;
    }

    public void setListaTipoNovedad(List<String> listaTipoNovedad) {
        this.listaTipoNovedad = listaTipoNovedad;
    }

    public String getUpdateClasificar() {
        return updateClasificar;
    }

    public void setUpdateClasificar(String updateClasificar) {
        this.updateClasificar = updateClasificar;
    }

}
