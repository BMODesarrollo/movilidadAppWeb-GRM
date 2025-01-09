/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.DispActividadFacadeLocal;
import com.movilidad.ejb.DispClasificacionNovedadFacadeLocal;
import com.movilidad.ejb.DispEstadoPendActualFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.VehiculoEstadoHistoricoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoEstadoDetFacadeLocal;
import com.movilidad.model.DispActividad;
import com.movilidad.model.DispClasificacionNovedad;
import com.movilidad.model.DispEstadoPendActual;
import com.movilidad.model.DispFaltanteRepuesto;
import com.movilidad.model.Novedad;
import com.movilidad.model.VehiculoEstadoHistorico;
import com.movilidad.model.VehiculoTipoEstado;
import com.movilidad.model.VehiculoTipoEstadoDet;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "dispActivBean")
@ViewScoped
public class DispActividadBean implements Serializable {

    /**
     * Creates a new instance of DispActividadBean
     */
    public DispActividadBean() {
    }

    @EJB
    private NovedadFacadeLocal novedadEJB;

    @EJB
    private DispEstadoPendActualFacadeLocal dispEstadoPendActualEJB;

    @EJB
    private DispActividadFacadeLocal dispActividadEJB;
    @EJB
    private DispClasificacionNovedadFacadeLocal dispClasificacionNovedadEJB;
    @EJB
    private VehiculoFacadeLocal vehiculoEJB;
    @EJB
    private VehiculoTipoEstadoDetFacadeLocal tipoEstadoDetEJB;
    @EJB
    private VehiculoEstadoHistoricoFacadeLocal estadoHistoricoEJB;
    @Inject
    private VehiculoEstadoHistoricoSaveBean vehiculoEstadoHistoricoSaveBean;
    @Inject
    private FaltanteRepuestoBean faltanteRepuestoBean;
    @Inject
    private NotificacionEmailBean notiEmailBean;
    private Integer id_estado_pend_actual;
    private VehiculoTipoEstadoDet vehiculoTipoEstadoDet;
    private boolean diferir;
    private Date fechaHora;
    private Date fechaHoraHabilitacion;
    private String observacion;
    private DispActividad dispActividad;
    private DispClasificacionNovedad dispClasificacionNovedad;
    private Novedad novedad;
    private VehiculoEstadoHistorico vehiculoEstadoHistorico;
    private UserExtended user;
    private List<VehiculoTipoEstadoDet> listVehTipoDet;
    private List<DispEstadoPendActual> listDistEstadoPend;
    private boolean habilitaVehiculo;
    private boolean flagRepuestos;
    private String headerModal = "";

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void sendToMaximo(Novedad param) {

        novedad = novedadEJB.find(param.getIdNovedad());
        if (novedad == null) {
            MovilidadUtil.addErrorMessage("Error inesperado.");
            return;
        }
        if (novedad.getIdDispClasificacionNovedad() == null) {
            MovilidadUtil.addErrorMessage("Se debe generar una clasificación para la novedad.");
            return;
        }
        String DESCRIPTION = param.getIdDispClasificacionNovedad().getIdDispSistema().getNombre();
        String DESCRIPTION_LONGDESCRIPTION = param.getIdDispClasificacionNovedad().getObservacion();
        String LOCATION = param.getIdVehiculo().getCodigo();
        String REPORTEDPRIORITY = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_REPORTED_PRIORITY_MAXIMO);
        String BMO_REPORTEDBYNAME = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_REPORTED_BY_NAME_MAXIMO);
        System.out.println("DESCRIPTION->" + DESCRIPTION);
        System.out.println("DESCRIPTION_LONGDESCRIPTION->" + DESCRIPTION_LONGDESCRIPTION);
        System.out.println("LOCATION->" + LOCATION);
        System.out.println("REPORTEDPRIORITY->" + REPORTEDPRIORITY);
        System.out.println("BMO_REPORTEDBYNAME->" + BMO_REPORTEDBYNAME);
    }

    public void addActividad(Novedad param) {
        if (param == null) {
            return;
        }
        if (param.getIdVehiculo() == null) {
            MovilidadUtil.addErrorMessage("La novedad no tiene vehículo.");
            return;
        }
        novedad = novedadEJB.find(param.getIdNovedad());
        if (novedad == null) {
            MovilidadUtil.addErrorMessage("Error inesperado.");
            return;
        }
        if (novedad.getIdDispClasificacionNovedad() == null) {
            MovilidadUtil.addErrorMessage("Se debe generar una clasificación para la novedad.");
            return;
        }
        dispClasificacionNovedad = novedad.getIdDispClasificacionNovedad();
        vehiculoTipoEstadoDet = novedad.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet();
        headerModal = novedad.getIdVehiculo().getPlaca() + " - "
                + novedad.getIdVehiculo().getCodigo()
                + " - " + vehiculoTipoEstadoDet.getNombre();
        if (ConstantsUtil.NOV_ESTADO_CERRADO == novedad.getEstadoNovedad()) {
            MovilidadUtil.addErrorMessage("La novedad ya ha sido cerrada.");
            return;
        }
        fechaHora = MovilidadUtil.fechaCompletaHoy();
//        id_vehiculo_tipo_estado_det = null;

        if (vehiculoTipoEstadoDet != null) {
            consularEstados(vehiculoTipoEstadoDet.getIdVehiculoTipoEstadoDet());
        }
        flagRepuestos = false;
        faltanteRepuestoBean.setList(null);
        resetValoresActividad();
        consularTipoEstadosDet();
        MovilidadUtil.openModal("wv_actividad");
    }

    void resetValoresActividad() {
        id_estado_pend_actual = null;
        dispActividad = null;
        diferir = false;
        habilitaVehiculo = false;
        observacion = "";
    }

    void consularTipoEstadosDet() {
        listVehTipoDet = tipoEstadoDetEJB.findAllByEstadoReg();
    }

    public List<DispActividad> getListActividades(Novedad param) {
        return dispActividadEJB.findAllByIdNovedad(param.getIdNovedad());
    }

    public void editActividad(DispActividad param) {
        novedad = novedadEJB.find(param.getIdNovedad());
        if (novedad == null) {
            MovilidadUtil.addErrorMessage("Error inesperado.");
            return;
        }
        if (ConstantsUtil.NOV_ESTADO_CERRADO == novedad.getEstadoNovedad()) {
            MovilidadUtil.addErrorMessage("La novedad ya ha sido cerrada.");
            return;
        }
        id_estado_pend_actual = param.getIdDispEstadoPendActual().getIdDispEstadoPendActual();
//        id_vehiculo_tipo_estado_det = param.getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstadoDet();
        vehiculoTipoEstadoDet = param.getIdVehiculoTipoEstadoDet();
        observacion = param.getObservacion();
        fechaHora = param.getFechaHora();
        diferir = param.getDiferir() == 1;
        dispActividad = param;
        novedad = dispActividad.getIdNovedad();
        if (vehiculoTipoEstadoDet != null) {
            consularEstados(vehiculoTipoEstadoDet.getIdVehiculoTipoEstadoDet());
        }
        consularTipoEstadosDet();
        MovilidadUtil.openModal("wv_actividad");
    }

    public void guardar() {
        if (validar()) {
            return;
        }
        boolean toCreate = false;
        if (dispActividad == null) {
            toCreate = true;
            dispActividad = new DispActividad();
            dispActividad.setCreado(MovilidadUtil.fechaCompletaHoy());
            dispActividad.setEstadoReg(0);
            dispActividad.setIdNovedad(novedad);
        } else {
            dispActividad.setModificado(MovilidadUtil.fechaCompletaHoy());
        }
//        if (diferir) {
//            setEstadoDiferir();
//            cargarObjeto();
//        }
        dispActividad.setFechaHora(fechaHora);
        dispActividad.setDiferir(diferir ? 1 : 0);
        dispActividad.setObservacion(observacion);
        dispActividad.setUsername(user.getUsername());
        if (!diferir) {
            dispActividad.setIdDispEstadoPendActual(new DispEstadoPendActual(id_estado_pend_actual));
            setEstadoPendienteActualToClacificacion(dispClasificacionNovedad,
                    dispActividad.getIdDispEstadoPendActual());
        }
        dispActividad.setIdVehiculoTipoEstadoDet(vehiculoTipoEstadoDet);
        if (toCreate) {
            dispActividadEJB.create(dispActividad);
        } else {
            dispActividadEJB.edit(dispActividad);
        }
        if (habilitaVehiculo || diferir) {
            cambiarEstadoNovedad(dispActividad,
                    diferir ? ConstantsUtil.NOV_ESTADO_DIFERIR : ConstantsUtil.NOV_ESTADO_CERRADO,
                    MovilidadUtil.fechaCompletaHoy(), user.getUsername());
            cambiarEstadoVehiculo(dispActividad.getIdNovedad().getIdVehiculo().getIdVehiculo(),
                    ConstantsUtil.VHCL_TIPO_ESTADO_OPERATIVO);

        }
        vehiculoEstadoHistoricoSaveBean.guardarEstadoVehiculoHistorico(
                vehiculoTipoEstadoDet,
                novedad.getIdVehiculo(),
                dispActividad, 0,
                diferir ? null : id_estado_pend_actual, observacion,
                habilitaVehiculo || diferir ? new VehiculoTipoEstado(ConstantsUtil.VHCL_TIPO_ESTADO_OPERATIVO)
                        : vehiculoTipoEstadoDet.getIdVehiculoTipoEstado(), true);

        if (flagRepuestos) {
            faltanteRepuestoBean.guardar(user.getUsername(), dispActividad.getIdDispActividad());
            dispActividad.setDispFaltanteRepuestoList(faltanteRepuestoBean.getList());
            notificar(dispActividad);
        }
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        MovilidadUtil.hideModal("wv_actividad");
    }

//    public void cargarObjeto() {
//        for (VehiculoTipoEstadoDet det : listVehTipoDet) {
//            if (det.getIdVehiculoTipoEstadoDet().equals(id_vehiculo_tipo_estado_det)) {
//                vehiculoTipoEstadoDet = det;
//                break;
//            }
//        }
//    }
    void cambiarEstadoVehiculo(Integer idVehiculo, Integer idVehiculoTipoEstado) {
        vehiculoEJB.updateEstadoVehiculo(idVehiculo, idVehiculoTipoEstado);
    }

    public void prepareDiferir(Novedad param) {
        if (dispActividadEJB.findDiferidaByIdNovedad(param.getIdNovedad(),
                dispActividad == null ? 0 : dispActividad.getIdDispActividad()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe una actividad diferidad");
            return;
        }
        VehiculoTipoEstadoDet vehiculoTipoEstadoDetRes = tipoEstadoDetEJB.findEstadoDiferir(0);
        System.out.println("vehiculoTipoEstadoDetRes->" + vehiculoTipoEstadoDetRes);
        if (vehiculoTipoEstadoDetRes == null) {
            MovilidadUtil.addErrorMessage("Se debe parametrizar estado tipo vehículo al diferir");
            return;
        }
        novedad = param;
        vehiculoTipoEstadoDet = novedad.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet();
        headerModal = novedad.getIdVehiculo().getPlaca() + " - " + novedad.getIdVehiculo().getCodigo();
        MovilidadUtil.openModal("wv_diferir");
    }

    public void prepareHabilitacion(Novedad param) {
        if (dispActividadEJB.findDiferidaByIdNovedad(param.getIdNovedad(),
                dispActividad == null ? 0 : dispActividad.getIdDispActividad()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe una actividad diferidad");
            return;
        }
        novedad = param;
        headerModal = novedad.getIdVehiculo().getPlaca() + " - " + novedad.getIdVehiculo().getCodigo();
        MovilidadUtil.openModal("wv_habilitar");
    }

    public void guardarDiferir() {
//        vehiculoTipoEstadoDet = null;
//        setEstadoDiferir();
        dispActividad = new DispActividad();
        dispActividad.setCreado(MovilidadUtil.fechaCompletaHoy());
        dispActividad.setEstadoReg(0);
        dispActividad.setIdNovedad(novedad);

        dispActividad.setFechaHora(MovilidadUtil.fechaCompletaHoy());
//        dispActividad.setDiferir(diferir ? 1 : 0);
        dispActividad.setDiferir(1);
        dispActividad.setObservacion(observacion);
        dispActividad.setUsername(user.getUsername());
//        if (id_estado_pend_actual != null) {
//            dispActividad.setIdDispEstadoPendActual(new DispEstadoPendActual(id_estado_pend_actual));
//        }
        dispActividad.setIdVehiculoTipoEstadoDet(vehiculoTipoEstadoDet);
        dispActividadEJB.create(dispActividad);
        cambiarEstadoVehiculo(novedad.getIdVehiculo().getIdVehiculo(),
                ConstantsUtil.VHCL_TIPO_ESTADO_OPERATIVO);
        cambiarEstadoNovedad(dispActividad,
                ConstantsUtil.NOV_ESTADO_DIFERIR,
                MovilidadUtil.fechaCompletaHoy(), user.getUsername());
        vehiculoEstadoHistoricoSaveBean.guardarEstadoVehiculoHistorico(
                vehiculoTipoEstadoDet,
                novedad.getIdVehiculo(),
                dispActividad, 0,
                null, observacion, new VehiculoTipoEstado(ConstantsUtil.VHCL_TIPO_ESTADO_OPERATIVO), false);
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        MovilidadUtil.hideModal("wv_diferir");
        dispActividad = null;
        novedad = null;
        observacion = "";
    }

    public void guardarHabilitacion() {
        dispActividad = new DispActividad();
        dispActividad.setCreado(MovilidadUtil.fechaCompletaHoy());
        dispActividad.setEstadoReg(0);
        dispActividad.setIdNovedad(novedad);

        dispActividad.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        dispActividad.setDiferir(0);
        dispActividad.setObservacion(observacion);
        dispActividad.setUsername(user.getUsername());
        dispActividad.setIdVehiculoTipoEstadoDet(novedad.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet());
        dispActividadEJB.create(dispActividad);
        cambiarEstadoNovedad(dispActividad,
                ConstantsUtil.NOV_ESTADO_CERRADO,
                MovilidadUtil.fechaCompletaHoy(), user.getUsername());
//            }
        cambiarEstadoVehiculo(novedad.getIdVehiculo().getIdVehiculo(),
                ConstantsUtil.VHCL_TIPO_ESTADO_OPERATIVO);

        vehiculoEstadoHistoricoSaveBean.guardarEstadoVehiculoHistorico(
                novedad.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet(),
                novedad.getIdVehiculo(),
                dispActividad, 0,
                id_estado_pend_actual, observacion,
                new VehiculoTipoEstado(ConstantsUtil.VHCL_TIPO_ESTADO_OPERATIVO), false);
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        MovilidadUtil.hideModal("wv_habilitar");
        dispActividad = null;
        novedad = null;
        observacion = "";

    }

    void guardarEstadoVehiculoHistorico(DispActividad param) {
        if (vehiculoEstadoHistorico == null) {
            vehiculoEstadoHistorico = new VehiculoEstadoHistorico();
        } else {
            vehiculoEstadoHistorico.setIdVehiculoEstadoHistorico(null);
        }
        vehiculoEstadoHistorico.setIdVehiculo(param.getIdNovedad().getIdVehiculo());
        vehiculoEstadoHistorico.setIdDispActividad(param);
        vehiculoEstadoHistorico.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        vehiculoEstadoHistorico.setUsuarioReporta(user.getUsername());
        vehiculoEstadoHistorico.setCreado(MovilidadUtil.fechaCompletaHoy());
        vehiculoEstadoHistorico.setUsername(user.getUsername());
        vehiculoEstadoHistorico.setEstadoReg(0);
        estadoHistoricoEJB.create(vehiculoEstadoHistorico);

    }

    void cambiarEstadoNovedad(DispActividad param, int estadoNovedad, Date fechaCierre, String usuarioCierre) {
//        System.out.println("dispActividad->>" + dispActividad);
        novedadEJB.updateEstadoNovedad(param.getIdNovedad().getIdNovedad(),
                estadoNovedad,
                fechaCierre, usuarioCierre);
    }

    boolean validar() {
        if (!diferir && id_estado_pend_actual == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar un Estado Pendiente");
            return true;
        }
//        if (id_vehiculo_tipo_estado_det == null) {
//            MovilidadUtil.addErrorMessage("Se debe seleccionar un Tipo Vehículo Detalle.");
//            return true;
//        }
        if (dispActividadEJB.findDiferidaByIdNovedad(novedad.getIdNovedad(),
                dispActividad == null ? 0 : dispActividad.getIdDispActividad()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe una actividad diferidad");
            return true;
        }
        return false;

    }

    @Transactional
    void setEstadoPendienteActualToClacificacion(DispClasificacionNovedad clasificacionNovedad,
            DispEstadoPendActual dispEstadoPendActual) {
        dispClasificacionNovedadEJB.updateSetestadoPendActual(dispClasificacionNovedad.getIdDispClasificacionNovedad(),
                dispEstadoPendActual.getIdDispEstadoPendActual(), user.getUsername());
    }

//    public void setEstadoDiferirFromView() {
//        DispEstadoPendActual dispEstadoPendActual = dispEstadoPendActualEJB.findEstadoDiferir(0);
//        if (dispEstadoPendActual != null) {
//            id_estado_pend_actual = dispEstadoPendActual.getIdDispEstadoPendActual();
//        }
//    }
//
//    public void setEstadoDiferir() {
//        DispEstadoPendActual dispEstadoPendActual = dispEstadoPendActualEJB.findEstadoDiferir(0);
//        if (dispEstadoPendActual != null) {
//            id_estado_pend_actual = dispEstadoPendActual.getIdDispEstadoPendActual();
//        }
//        VehiculoTipoEstadoDet vehiculoTipoEstadoDetRes = tipoEstadoDetEJB.findEstadoDiferir(0);
//        if (vehiculoTipoEstadoDetRes != null) {
//            vehiculoTipoEstadoDet = vehiculoTipoEstadoDetRes;
//        }
//    }
    public Integer getId_estado_pend_actual() {
        return id_estado_pend_actual;
    }

    void consularEstados(int idVehiculoTipoEstadoDet) {
        listDistEstadoPend = dispEstadoPendActualEJB
                .findFirtStatusByidVehiculoTipoEstadoDetOrAll(
                        idVehiculoTipoEstadoDet, false);
    }

    public void setId_estado_pend_actual(Integer id_estado_pend_actual) {
        this.id_estado_pend_actual = id_estado_pend_actual;
    }

//    public Integer getId_vehiculo_tipo_estado_det() {
//        return id_vehiculo_tipo_estado_det;
//    }
//
//    public void setId_vehiculo_tipo_estado_det(Integer id_vehiculo_tipo_estado_det) {
//        this.id_vehiculo_tipo_estado_det = id_vehiculo_tipo_estado_det;
//    }
    public void changeValue(boolean flag) {
        if (flag) {
            if (diferir) {
                habilitaVehiculo = false;
            }
        } else {
            if (habilitaVehiculo) {
                diferir = false;
            }
        }
    }

    /**
     * Realiza el envío de correo de las novedad registrada a las partes
     * interesadas
     */
    private void notificar(DispActividad param) {
        Map mapa = notiEmailBean.getMailParams(ConstantsUtil.ID_NOTIFICACION_CONF, ConstantsUtil.TEMPLATE_REPUESTOS_MTTO);
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fechaHora", Util.dateTimeFormat(param.getFechaHora()));
        mailProperties.put("vehiculo", param.getIdNovedad().getIdVehiculo().getCodigo());
        mailProperties.put("reportadoPor", param.getUsername());
        mailProperties.put("observacion", param.getObservacion());
        String subject = "Solicitud Repuestos Faltantes Vehiculo: " + param.getIdNovedad().getIdVehiculo().getCodigo();
        String get = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.CODE_NOTI_PROCESO_REPUESTOS);
        if (get != null) {
            String destinatarios = notiEmailBean.obtenerDestinatarios(get, 0);
            List<String> adjuntos = generarAdjunto(param.getDispFaltanteRepuestoList());
            SendMails.sendEmail(mapa, mailProperties, subject,
                    "",
                    destinatarios,
                    "Notificaciones RIGEL", adjuntos);
        }
    }

    List<String> generarAdjunto(List<DispFaltanteRepuesto> list) {
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        Map parametros = new HashMap();
        plantilla = plantilla + "Repuestos Faltantes.xlsx";
        parametros.put("list", list);
        destino = destino + "Repuestos Faltantes.xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        List<String> adjuntos = new ArrayList<>();
        adjuntos.add(destino);
        return adjuntos;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean isDiferir() {
        return diferir;
    }

    public void setDiferir(boolean diferir) {
        this.diferir = diferir;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<VehiculoTipoEstadoDet> getListVehTipoDet() {
        return listVehTipoDet;
    }

    public void setListVehTipoDet(List<VehiculoTipoEstadoDet> listVehTipoDet) {
        this.listVehTipoDet = listVehTipoDet;
    }

    public List<DispEstadoPendActual> getListDistEstadoPend() {
        return listDistEstadoPend;
    }

    public void setListDistEstadoPend(List<DispEstadoPendActual> listDistEstadoPend) {
        this.listDistEstadoPend = listDistEstadoPend;
    }

    public Date getFechaHoraHabilitacion() {
        return fechaHoraHabilitacion;
    }

    public void setFechaHoraHabilitacion(Date fechaHoraHabilitacion) {
        this.fechaHoraHabilitacion = fechaHoraHabilitacion;
    }

    public boolean isHabilitaVehiculo() {
        return habilitaVehiculo;
    }

    public void setHabilitaVehiculo(boolean habilitaVehiculo) {
        this.habilitaVehiculo = habilitaVehiculo;
    }

    public VehiculoTipoEstadoDet getVehiculoTipoEstadoDet() {
        return vehiculoTipoEstadoDet;
    }

    public void setVehiculoTipoEstadoDet(VehiculoTipoEstadoDet vehiculoTipoEstadoDet) {
        this.vehiculoTipoEstadoDet = vehiculoTipoEstadoDet;
    }

    public String getHeaderModal() {
        return headerModal;
    }

    public void setHeaderModal(String headerModal) {
        this.headerModal = headerModal;
    }

    public boolean isFlagRepuestos() {
        return flagRepuestos;
    }

    public void setFlagRepuestos(boolean flagRepuestos) {
        this.flagRepuestos = flagRepuestos;
    }

}
