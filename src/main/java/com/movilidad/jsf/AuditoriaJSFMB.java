/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaCostoFacadeLocal;
import com.movilidad.ejb.AuditoriaEncabezadoFacadeLocal;
import com.movilidad.ejb.AuditoriaFacadeLocal;
import com.movilidad.ejb.AuditoriaLugarFacadeLocal;
import com.movilidad.ejb.AuditoriaPreguntaFacadeLocal;
import com.movilidad.ejb.AuditoriaRealizadoPorFacadeLocal;
import com.movilidad.ejb.AuditoriaRespuestaFacadeLocal;
import com.movilidad.ejb.AuditoriaTipoFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaFacadeLocal;
import com.movilidad.ejb.GenericaTipoDetallesFacadeLocal;
import com.movilidad.ejb.GenericaTipoFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.Auditoria;
import com.movilidad.model.AuditoriaAreaComun;
import com.movilidad.model.AuditoriaCosto;
import com.movilidad.model.AuditoriaEncabezado;
import com.movilidad.model.AuditoriaEstacion;
import com.movilidad.model.AuditoriaLugar;
import com.movilidad.model.AuditoriaPregunta;
import com.movilidad.model.AuditoriaPreguntaRelacion;
import com.movilidad.model.AuditoriaTipo;
import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaTipoDetalles;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
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
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "auditoriaJSFMB")
@ViewScoped
public class AuditoriaJSFMB implements Serializable {

    /**
     * Creates a new instance of AuditoriaJSFMB
     */
    public AuditoriaJSFMB() {
    }
    @EJB
    private AuditoriaFacadeLocal audiEJB;
    @EJB
    private AuditoriaPreguntaFacadeLocal audiAuditoriaPreguntaEJB;
    @EJB
    private AuditoriaEncabezadoFacadeLocal audiAuditoriaEncabezadoEJB;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;
    @EJB
    private AuditoriaRespuestaFacadeLocal audiRespuestaEJB;
    @EJB
    private AuditoriaTipoFacadeLocal audiTipoEJB;
    @EJB
    private AuditoriaLugarFacadeLocal audiLugarEJB;
    @EJB
    private AuditoriaEncabezadoFacadeLocal audiEncabezadoEJB;
    @EJB
    private AuditoriaRealizadoPorFacadeLocal audiRealizadoPorEJB;
    @Inject
    private AuditoriaRespuestaJSFMB audiRespuestaJSFMB;
    @EJB
    private EmpleadoFacadeLocal emplEJB;
    @EJB
    private GenericaTipoFacadeLocal genericaTipoEJB;
    @EJB
    private NovedadTipoFacadeLocal novedadTipoEJB;
    @EJB
    private GenericaTipoDetallesFacadeLocal genericaTipoDetEJB;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetEJB;
    @EJB
    private AuditoriaCostoFacadeLocal costoEJB;

    @Inject
    private AuditoriaCostoBean auditoriaCostoBean;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private SelectGopUnidadFuncionalBean selectGopUnidadFuncionalBean;

    private List<Auditoria> list;
    private List<AuditoriaPregunta> selectPreguntas;
    private List<AuditoriaPregunta> listPreguntas;
    private List<AuditoriaEncabezado> listAudiEncabezado;
    private List<AuditoriaPreguntaRelacion> listAudiPreguntaRelacion;
    private List<AuditoriaTipo> listTipoAuditoria;
    private List<AuditoriaLugar> listLugarAuditoria;
    private List<Vehiculo> listVehiculo;
    private List<AuditoriaEstacion> listaAuditoriaEstacion;
    private List<AuditoriaAreaComun> listAuditoriaAreaComun;
    private List<?> listTipoNov;
    private List<?> listTipoDetNov;
    private Map<Integer, AuditoriaLugar> mapaAuditoriaLugar;
    private Auditoria auditoria;
    private AuditoriaEncabezado auditoriaEncabezado;
    private int i_idAudiEncabezado;
    private int i_idArea;
    private int i_tipo_auditoria;
    private int i_lugar_auditoria;
    private int i_bus;
    private int i_estacion;
    private int i_areaComun;
    private int i_idNovedadTipo;
    private int i_idNovedadTipoFDe;
    private boolean b_edit = false;
    private boolean b_clonar = false;
    private boolean b_clonanado = false;
    private boolean b_visualizar = false;
    private boolean b_realizar = false;
    private boolean b_activa;
    private boolean b_user_generico;
    private boolean flagListaUF;
    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();

    private ParamAreaUsr paramAreaUsr;
    private UserExtended user;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        if (paramAreaUsr != null) {
            i_idArea = paramAreaUsr.getIdParamArea().getIdParamArea();
        }
        audiRespuestaJSFMB.setB_view(false);
        audiRespuestaJSFMB.setUser(user);
        getUserGenerico();
        consultar();
        auditoriaCostoBean.setUser(user);
        auditoriaCostoBean.setParamAreaUsr(paramAreaUsr);
        flagListaUF = unidadFuncionalSessionBean.getIdGopUnidadFuncional() == 0;

    }

    public void getUserGenerico() {
        b_user_generico = false;
        if (user != null) {
            for (GrantedAuthority role : user.getAuthorities()) {
                if (role.getAuthority().contains("GEN")) {
                    b_user_generico = true;
                }
            }
        }
    }

    public String tituloDetTipoNov(AuditoriaEncabezado audiEnca) {
        if (audiEnca.getIdGenericaTipoDetalles() != null) {
            return audiEnca.getIdGenericaTipoDetalles().getTituloTipoGenerica();
        }
        if (audiEnca.getIdNovedadTipoDetalles() != null) {
            return audiEnca.getIdNovedadTipoDetalles().getTituloTipoNovedad();
        }
        return "N/A";
    }

    void preEditEncabezado(AuditoriaEncabezado obj) {
        auditoriaEncabezado = obj;
        i_lugar_auditoria = auditoriaEncabezado.getIdAuditoriaLugar().getIdAuditoriaLugar();
        i_tipo_auditoria = auditoriaEncabezado.getIdAuditoriaTipo().getIdAuditoriaTipo();
        b_activa = auditoriaEncabezado.getActiva() != null && auditoriaEncabezado.getActiva() == 1;
        listTipoAuditoria = audiTipoEJB.findByArea(i_idArea);
        listLugarAuditoria = audiLugarEJB.findByArea(i_idArea);
        updateFechaDesde();
        updateFechaHasta();
        auditoriaCostoBean.cargarCostoTipo(false);
        auditoriaCostoBean.cargarVehiculoTipo(false);
        auditoriaCostoBean.cargarCostosByAuditoria(auditoria);
        cargarListaAndMapAuditoriaLugar();
        cargarComponente();
    }

    @Transactional
    private void editTransactionalEncabezado() {
        auditoriaEncabezado.setUsername(user.getUsername());
        auditoriaEncabezado.setModificado(MovilidadUtil.fechaCompletaHoy());
        auditoriaEncabezado.setIdAuditoriaTipo(new AuditoriaTipo(i_tipo_auditoria));
        auditoriaEncabezado.setIdAuditoriaLugar(new AuditoriaLugar(i_lugar_auditoria));
        audiEncabezadoEJB.edit(auditoriaEncabezado);

    }

    public void preEdit(Auditoria a) {
        auditoria = a;
        auditoriaCostoBean.setB_addOrUpdate(true);
        if (!audiRealizadoPorEJB.findByIdAuditoria(auditoria.getIdAuditoria()).isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("Esta auditoría no puede ser modificada, porque ya fue realizada");
            return;
        }
        preEditEncabezado(auditoria.getIdAuditoriaEncabezado());
//        listAudiEncabezado = audiAuditoriaEncabezadoEJB.findByArea(i_idArea);
        listPreguntas = audiAuditoriaPreguntaEJB.findByIdArea(i_idArea);
        if (auditoria.getAuditoriaPreguntaRelacionList() == null) {
            auditoria.setAuditoriaPreguntaRelacionList(new ArrayList<AuditoriaPreguntaRelacion>());
        }
        listAudiPreguntaRelacion = auditoria.getAuditoriaPreguntaRelacionList();
        selectPreguntas = new ArrayList<>();
//        i_idAudiEncabezado = auditoria.getIdAuditoriaEncabezado().getIdAuditoriaEncabezado();
        if (auditoria.getAuditoriaPreguntaRelacionList() != null) {
            for (AuditoriaPreguntaRelacion p : auditoria.getAuditoriaPreguntaRelacionList()) {
                selectPreguntas.add(p.getIdAuditoriaPregunta());
            }
        }
        unidadFuncionalSessionBean.setI_unidad_funcional(
                auditoria.getIdGopUnidadFuncional() == null
                ? 0 : auditoria.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        cargarApartadoNovedad(auditoria);
        MovilidadUtil.openModal("crear_audi_dialog_wv");
    }

    public void guardar() {
        if (validarCampos()) {
            return;
        }
        if (validarCamposEncabezado()) {
            return;
        }
        guardarTransctionalEncabezado();
        guardarTransaction();
        auditoria = new Auditoria();
        auditoriaEncabezado = new AuditoriaEncabezado();
        listAudiPreguntaRelacion = null;
        selectPreguntas.clear();
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        if (b_clonanado) {
            MovilidadUtil.hideModal("crear_audi_dialog_wv");
        }
        consultar();
    }

    public void edit() {
        if (validarCampos()) {
            return;
        }
        if (validarCamposEncabezado()) {
            return;
        }
        editTransactionalEncabezado();
        editTransaction();
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        MovilidadUtil.hideModal("crear_audi_dialog_wv");
        consultar();
    }

    public void onCerrar() {
        consultar();
    }

    public void updateFechaDesde() {
        auditoriaCostoBean.setDesde(auditoriaEncabezado.getFechaDesde());
    }

    public void updateFechaHasta() {
        auditoriaCostoBean.setHasta(auditoriaEncabezado.getFechaHasta());
    }

    @Transactional
    public void editTransaction() {
        if (!selectPreguntas.isEmpty()) {
            for (AuditoriaPregunta ap : selectPreguntas) {
                boolean ok = false;
                for (AuditoriaPreguntaRelacion apr : listAudiPreguntaRelacion) {
                    if (ap.getIdAuditoriaPregunta().equals(apr.getIdAuditoriaPregunta().getIdAuditoriaPregunta())) {
                        ok = true;
                    }
                }
                if (!ok) {
                    AuditoriaPreguntaRelacion apr = new AuditoriaPreguntaRelacion();
                    apr.setCreado(MovilidadUtil.fechaCompletaHoy());
                    apr.setEstadoReg(0);
                    apr.setUsername(user.getUsername());
                    apr.setIdAuditoria(auditoria);
                    apr.setIdAuditoriaPregunta(ap);
                    listAudiPreguntaRelacion.add(apr);
                }
            }
            List<AuditoriaPreguntaRelacion> listAudiPreguntaRelacionAux = new ArrayList<>();
            listAudiPreguntaRelacionAux.addAll(listAudiPreguntaRelacion);
            for (AuditoriaPreguntaRelacion aux_apr : listAudiPreguntaRelacionAux) {
                boolean aux_ok = true;
                for (AuditoriaPregunta pregunta : selectPreguntas) {
                    if (aux_apr.getIdAuditoriaPregunta().getIdAuditoriaPregunta().equals(pregunta.getIdAuditoriaPregunta())) {
                        aux_ok = false;
                    }
                }
                if (aux_ok) {
                    listAudiPreguntaRelacion.remove(aux_apr);
                }
            }
        } else {
            auditoria.setAuditoriaPreguntaRelacionList(null);
        }
        setAuditoriaToCostos(auditoria);
        auditoria.setModificado(MovilidadUtil.fechaCompletaHoy());
        auditoria.setUsername(user.getUsername());
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() > 0) {
            auditoria.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
        }
        audiEJB.edit(auditoria);
    }

    public void clonarAuditoria(Auditoria a) {
        auditoria = a;
        b_clonanado = true;
        auditoria.setAuditoriaRealizadoPorList(null);

//        listAudiEncabezado = audiAuditoriaEncabezadoEJB.findByArea(i_idArea);
        listPreguntas = audiAuditoriaPreguntaEJB.findByIdArea(i_idArea);
        if (auditoria.getAuditoriaPreguntaRelacionList() == null) {
            auditoria.setAuditoriaPreguntaRelacionList(new ArrayList<>());
        }
        listAudiPreguntaRelacion = auditoria.getAuditoriaPreguntaRelacionList();
        selectPreguntas = new ArrayList<>();
        i_idAudiEncabezado = auditoria.getIdAuditoriaEncabezado().getIdAuditoriaEncabezado();
        if (auditoria.getAuditoriaPreguntaRelacionList() != null) {
            auditoria.getAuditoriaPreguntaRelacionList().forEach((p) -> {
                selectPreguntas.add(p.getIdAuditoriaPregunta());
            });
        }
        auditoria.setIdAuditoria(null);
        auditoria.setUsername(user.getUsername());
        auditoria.setCodigo(buildCodigo());
        auditoriaEncabezado = auditoria.getIdAuditoriaEncabezado();
        auditoriaEncabezado.setFechaDesde(MovilidadUtil.fechaCompletaHoy());
        auditoriaEncabezado.setFechaHasta(MovilidadUtil.fechaCompletaHoy());
        auditoriaEncabezado.setIdAuditoriaEncabezado(null);
        cargarApartadoNovedad(auditoria);
        preEditEncabezado(auditoriaEncabezado);
        MovilidadUtil.openModal("crear_audi_dialog_wv");
    }

    private void cargarApartadoNovedad(Auditoria audiParam) {
        if (b_user_generico) {
            if (audiParam.getIdAuditoriaEncabezado().getIdGenericaTipoDetalles() != null) {
                i_idNovedadTipo = audiParam.getIdAuditoriaEncabezado().getIdGenericaTipoDetalles().getIdGenericaTipo().getIdGenericaTipo();
                i_idNovedadTipoFDe = audiParam.getIdAuditoriaEncabezado().getIdGenericaTipoDetalles().getIdGenericaTipoDetalle();
            }
            listTipoNov = genericaTipoEJB.findAllByArea(i_idArea);
        } else {
            if (audiParam.getIdAuditoriaEncabezado().getIdNovedadTipoDetalles() != null) {
                i_idNovedadTipo = audiParam.getIdAuditoriaEncabezado().getIdNovedadTipoDetalles().getIdNovedadTipo().getIdNovedadTipo();
                i_idNovedadTipoFDe = audiParam.getIdAuditoriaEncabezado().getIdNovedadTipoDetalles().getIdNovedadTipoDetalle();
            }
            listTipoNov = novedadTipoEJB.obtenerTipos();
        }
        cargarTipoDetalle();
    }

    public void viewAuditoria(Auditoria a) {
        auditoria = a;
        audiRespuestaJSFMB.setAudi(auditoria);
        audiRespuestaJSFMB.setB_view(true);
        MovilidadUtil.openModal("crear_audi_view_dialog_wv");
    }

    public void viewRealizar(Auditoria a) {
        try {
            auditoria = a;
            b_realizar = MovilidadUtil.betweenDateToday(auditoria.getIdAuditoriaEncabezado().getFechaDesde(),
                    auditoria.getIdAuditoriaEncabezado().getFechaHasta(),
                    MovilidadUtil.fechaHoy());
            if (!b_realizar) {
                MovilidadUtil.addAdvertenciaMessage("Fuera de rango para realizar");
                return;
            }
            Empleado empl = emplEJB.getEmpleadoByUsername(user.getUsername());
            if (empl == null) {
                MovilidadUtil.addAdvertenciaMessage("No se encontró responsable para el usuario en sesión");
                return;
            }
            if (auditoria.getIdAuditoriaEncabezado().getActiva() != null
                    && auditoria.getIdAuditoriaEncabezado().getActiva().equals(0)) {
                MovilidadUtil.addAdvertenciaMessage("La auditoría esta desactivada.");
                return;
            }

            audiRespuestaJSFMB.setEmpleado(empl);
            audiRespuestaJSFMB.setAudi(auditoria);
            audiRespuestaJSFMB.setB_view(false);
            audiRespuestaJSFMB.setB_control(true);
            audiRespuestaJSFMB.getPreguntas();
            audiRespuestaJSFMB.cargarMapRespuestas();
            audiRespuestaJSFMB.cargarComponente();
            MovilidadUtil.openModal("crear_audi_resolver_dialog_wv");
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addAdvertenciaMessage("Opción invalida");
        }
    }

    @Transactional
    private void guardarTransctionalEncabezado() {
        if (i_idNovedadTipoFDe != 0) {
            if (b_user_generico) {
                auditoriaEncabezado.setIdGenericaTipoDetalles(new GenericaTipoDetalles(i_idNovedadTipoFDe));
            } else {
                auditoriaEncabezado.setIdNovedadTipoDetalles(new NovedadTipoDetalles(i_idNovedadTipoFDe));
            }
        }
        auditoriaEncabezado.setIdParamArea(paramAreaUsr.getIdParamArea());
        auditoriaEncabezado.setUsername(user.getUsername());
        auditoriaEncabezado.setCreado(MovilidadUtil.fechaCompletaHoy());
        auditoriaEncabezado.setEstadoReg(0);
        auditoriaEncabezado.setCreado(MovilidadUtil.fechaCompletaHoy());
        auditoriaEncabezado.setModificado(null);
        auditoriaEncabezado.setIdAuditoriaTipo(new AuditoriaTipo(i_tipo_auditoria));
        auditoriaEncabezado.setIdAuditoriaLugar(new AuditoriaLugar(i_lugar_auditoria));
        audiEncabezadoEJB.create(auditoriaEncabezado);
    }

    @Transactional
    public void guardarTransaction() {
        auditoria.setUsername(user.getUsername());
        auditoria.setCreado(MovilidadUtil.fechaCompletaHoy());
        auditoria.setEstadoReg(0);
        auditoria.setIdParamArea(paramAreaUsr.getIdParamArea());
        auditoria.setIdAuditoriaEncabezado(auditoriaEncabezado);
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() > 0) {
            auditoria.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
        }
        setAuditoriaToCostos(auditoria);
        listAudiPreguntaRelacion = new ArrayList<>();
        if (!selectPreguntas.isEmpty()) {
            for (AuditoriaPregunta p : selectPreguntas) {
                AuditoriaPreguntaRelacion apr = new AuditoriaPreguntaRelacion();
                apr.setCreado(MovilidadUtil.fechaCompletaHoy());
                apr.setEstadoReg(0);
                apr.setUsername(user.getUsername());
                apr.setIdAuditoria(auditoria);
                apr.setIdAuditoriaPregunta(p);
                listAudiPreguntaRelacion.add(apr);
            }
            auditoria.setAuditoriaPreguntaRelacionList(listAudiPreguntaRelacion);
        }
        audiEJB.create(auditoria);

    }

    private void setAuditoriaToCostos(Auditoria param) {
        List<AuditoriaCosto> lstAutidoriaCosto = auditoriaCostoBean.getLstAutidoriaCosto();
        if (!lstAutidoriaCosto.isEmpty()) {
            for (AuditoriaCosto obj : lstAutidoriaCosto) {
                obj.setIdAuditoria(param);
                if (obj.getIdAuditoriaCosto() == null) {
                    obj.setCreado(MovilidadUtil.fechaCompletaHoy());
                }
                obj.setModificado(MovilidadUtil.fechaCompletaHoy());
            }
        }
        param.setAuditoriaCostoList(lstAutidoriaCosto);
    }

    /**
     * Método responsable de cargar desde la vista la variable auditoria, la
     * cual se se utiliza para gestión de auditoria.
     *
     * Se acciona al seleccionar un registro de la tabla de la vista list.
     *
     * @param event Contiene el objeto del registro seleccionado desde la vista
     * list.
     * @throws ParseException
     */
    public void onRowSelect(SelectEvent event) throws ParseException {
        setAuditoria((Auditoria) event.getObject());
        if (auditoria == null) {
            MovilidadUtil.addAdvertenciaMessage("Null al seleccionar");
            return;
        }
        boolean b_gestionada = audiRespuestaEJB.findByIdAuditoria(auditoria.getIdAuditoria()).isEmpty();

        b_clonanado = false;
        b_clonar = true;
        b_edit = b_gestionada;
        b_visualizar = true;
        b_realizar = MovilidadUtil.betweenDateToday(auditoria.getIdAuditoriaEncabezado().getFechaDesde(),
                auditoria.getIdAuditoriaEncabezado().getFechaHasta(),
                MovilidadUtil.fechaHoy()) && b_gestionada;
    }

    /**
     * Método resposnable de hacer null la variable auditoria luego de
     * desseleccionar la fila previamente seleccionada en la tabla de la vista
     * list.
     *
     * Para desseleccionar un registro de la table se debe presionar la tecla
     * Ctrl+Clic sobre le registro.
     */
    public void onRowUnselect() {
        auditoria = null;
        b_clonar = false;
        b_edit = false;
        b_visualizar = false;
        b_realizar = false;
    }

    /**
     * Cargar Auditorias por id de area
     */
    public void consultar() {
        list = audiEJB.findByAreaAndIdGopUnidadFuncional(i_idArea, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    /**
     * Prepara variables responsable de la gestion de nueva auditoría
     */
    public void preGuardar() {

        auditoriaEncabezado = new AuditoriaEncabezado();
        auditoriaEncabezado.setFechaDesde(MovilidadUtil.fechaCompletaHoy());
        auditoriaEncabezado.setFechaHasta(MovilidadUtil.fechaCompletaHoy());
        updateFechaDesde();
        updateFechaHasta();
        i_lugar_auditoria = 0;
        i_tipo_auditoria = 0;
        b_activa = false;
        auditoriaCostoBean.setB_addOrUpdate(true);
        listTipoAuditoria = audiTipoEJB.findByArea(i_idArea);
        cargarListaAndMapAuditoriaLugar();
        if (b_user_generico) {
            listTipoNov = genericaTipoEJB.findAllByArea(i_idArea);
        } else {
            listTipoNov = novedadTipoEJB.obtenerTipos();
        }
        listAudiEncabezado = audiAuditoriaEncabezadoEJB.findByArea(i_idArea);
        listPreguntas = audiAuditoriaPreguntaEJB.findByIdArea(i_idArea);
        auditoria = new Auditoria();

        auditoria.setCodigo(buildCodigo());
        selectPreguntas = new ArrayList<>();
        auditoriaCostoBean.cargarCostoTipo(false);
        auditoriaCostoBean.cargarVehiculoTipo(false);
        auditoriaCostoBean.cargarCostosByAuditoria(auditoria);
    }

    public void cargarListaAndMapAuditoriaLugar() {
        listLugarAuditoria = audiLugarEJB.findByArea(i_idArea);
        mapaAuditoriaLugar = new HashMap<>();
        for (AuditoriaLugar al : listLugarAuditoria) {
            mapaAuditoriaLugar.put(al.getIdAuditoriaLugar(), al);
        }
    }

    public String buildCodigo() {
        int count = audiEJB.count();
        return "AU" + Util.dateFormatByCode(desde) + "-" + count;
    }

    /**
     * Validar variables cruciales para la gestion de auditoría
     *
     * @return valor boolean, true si hay una incorformidad, false si todo esta
     * bien.
     */
    boolean validarCampos() {
        if (auditoria.getCodigo() == null || auditoria.getCodigo().isEmpty()) {
            MovilidadUtil.addErrorMessage("No se ha digitado un código.");
            return true;
        }
        if (audiEJB.findByAreaIdAuditoriaAndCodigo(auditoria.getCodigo(),
                auditoria.getIdAuditoria() == null ? 0 : auditoria.getIdAuditoria(),
                i_idArea) != null) {
            MovilidadUtil.addErrorMessage("Ya existe una auditoria con el código indicado.");
            auditoria.setCodigo(buildCodigo());
            MovilidadUtil.addAdvertenciaMessage("Se ha generado un nuevo código.");
            return true;
        }
        return false;
    }

    /**
     * Validar variables cruciales para la gestion de auditoría encabezado
     *
     * @return valor boolean, true si hay una incorformidad, false si todo esta
     * bien.
     */
    boolean validarCamposEncabezado() {
        if (auditoriaEncabezado.getTitulo() == null || auditoriaEncabezado.getTitulo().isEmpty()) {
            MovilidadUtil.addErrorMessage("No se ha digitado un titulo de encabezado.");
            return true;
        }
        if (auditoriaEncabezado.getDescripcion() == null || auditoriaEncabezado.getDescripcion().isEmpty()) {
            MovilidadUtil.addErrorMessage("No se ha digitado un descripción.");
            return true;
        }
        if (auditoriaEncabezado.getIdAuditoriaLugar() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un lugar de auditoría");
            return true;
        }
        if (i_tipo_auditoria == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de auditoría");
            return true;
        }
        if (auditoriaEncabezado.getFechaDesde() == null) {
            MovilidadUtil.addErrorMessage("Seleccione la fecha desde.");
            return true;
        }
        if (auditoriaEncabezado.getFechaHasta() == null) {
            MovilidadUtil.addErrorMessage("Seleccione la fecha hasta.");
            return true;
        }
        if (auditoriaEncabezado.getFechaDesde().after(auditoriaEncabezado.getFechaHasta())) {
            MovilidadUtil.addErrorMessage("La fecha desde no puede ser mayor a la fecha hasta.");
            return true;
        }

        return false;
    }

    /**
     * Consultar pregunta segun la opcion indicada.
     *
     * @param opc
     */
    public void consultarPregunta(int opc) {
        listPreguntas = audiAuditoriaPreguntaEJB.findPreguntasByOpcion(opc);
    }

    /**
     * Cargar el lugar de auditoria a la auditoria
     */
    public void cargarComponente() {
        if (i_lugar_auditoria != 0) {
            AuditoriaLugar audiLugar = mapaAuditoriaLugar.get(i_lugar_auditoria);
            auditoriaEncabezado.setIdAuditoriaLugar(audiLugar);
            auditoriaCostoBean.setIdVehiculoTipo(audiLugar.getIdVehiculoTipo() == null ? 0 : audiLugar.getIdVehiculoTipo().getIdVehiculoTipo());
            auditoriaCostoBean.setVehiculoTipo(audiLugar.getIdVehiculoTipo() == null ? null : audiLugar.getIdVehiculoTipo());
            auditoriaCostoBean.setB_vehiculoTipo((audiLugar.getIdVehiculoTipo() != null));
//            if (audiLugar.isBus()) {
//                listVehiculo = vehiculoEJB.getVehiclosActivo();
//            }
//            if (audiLugar.isEstacion()) {
//                listaAuditoriaEstacion = audiEstacionEJB.findByArea(i_idArea);
//            }
//            if (audiLugar.isAreaComun()) {
//                listAuditoriaAreaComun = audiAreaComunEJB.findByArea(i_idArea);
//            }
        } else {
            auditoriaEncabezado.setIdAuditoriaLugar(null);
            auditoriaCostoBean.setIdVehiculoTipo(0);
            auditoriaCostoBean.setB_vehiculoTipo(false);
        }
    }

    public void cargarTipoDetalle() {
        if (i_idNovedadTipo != 0) {
            if (b_user_generico) {
                listTipoDetNov = genericaTipoDetEJB.findByTipo(i_idNovedadTipo);
            } else {
                listTipoDetNov = novedadTipoDetEJB.findByTipo(i_idNovedadTipo);
            }
        }
    }

    public List<Auditoria> getList() {
        return list;
    }

    public void setList(List<Auditoria> list) {
        this.list = list;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public List<AuditoriaPregunta> getSelectPreguntas() {
        return selectPreguntas;
    }

    public void setSelectPreguntas(List<AuditoriaPregunta> selectPreguntas) {
        this.selectPreguntas = selectPreguntas;
    }

    public List<AuditoriaPregunta> getListPreguntas() {
        return listPreguntas;
    }

    public void setListPreguntas(List<AuditoriaPregunta> listPreguntas) {
        this.listPreguntas = listPreguntas;
    }

    public List<AuditoriaEncabezado> getListAudiEncabezado() {
        return listAudiEncabezado;
    }

    public void setListAudiEncabezado(List<AuditoriaEncabezado> listAudiEncabezado) {
        this.listAudiEncabezado = listAudiEncabezado;
    }

    public int getI_idAudiEncabezado() {
        return i_idAudiEncabezado;
    }

    public void setI_idAudiEncabezado(int i_idAudiEncabezado) {
        this.i_idAudiEncabezado = i_idAudiEncabezado;
    }

    public boolean isB_edit() {
        return b_edit;
    }

    public void setB_edit(boolean b_edit) {
        this.b_edit = b_edit;
    }

    public boolean isB_clonar() {
        return b_clonar;
    }

    public void setB_clonar(boolean b_clonar) {
        this.b_clonar = b_clonar;
    }

    public boolean isB_visualizar() {
        return b_visualizar;
    }

    public void setB_visualizar(boolean b_visualizar) {
        this.b_visualizar = b_visualizar;
    }

    public boolean isB_realizar() {
        return b_realizar;
    }

    public void setB_realizar(boolean b_realizar) {
        this.b_realizar = b_realizar;
    }

    public AuditoriaEncabezado getAuditoriaEncabezado() {
        return auditoriaEncabezado;
    }

    public void setAuditoriaEncabezado(AuditoriaEncabezado auditoriaEncabezado) {
        this.auditoriaEncabezado = auditoriaEncabezado;
    }

    public int getI_tipo_auditoria() {
        return i_tipo_auditoria;
    }

    public void setI_tipo_auditoria(int i_tipo_auditoria) {
        this.i_tipo_auditoria = i_tipo_auditoria;
    }

    public List<AuditoriaTipo> getListTipoAuditoria() {
        return listTipoAuditoria;
    }

    public void setListTipoAuditoria(List<AuditoriaTipo> listTipoAuditoria) {
        this.listTipoAuditoria = listTipoAuditoria;
    }

    public int getI_lugar_auditoria() {
        return i_lugar_auditoria;
    }

    public void setI_lugar_auditoria(int i_lugar_auditoria) {
        this.i_lugar_auditoria = i_lugar_auditoria;
    }

    public List<AuditoriaLugar> getListLugarAuditoria() {
        return listLugarAuditoria;
    }

    public void setListLugarAuditoria(List<AuditoriaLugar> listLugarAuditoria) {
        this.listLugarAuditoria = listLugarAuditoria;
    }

    public List<Vehiculo> getListVehiculo() {
        return listVehiculo;
    }

    public void setListVehiculo(List<Vehiculo> listVehiculo) {
        this.listVehiculo = listVehiculo;
    }

    public List<AuditoriaEstacion> getListaAuditoriaEstacion() {
        return listaAuditoriaEstacion;
    }

    public void setListaAuditoriaEstacion(List<AuditoriaEstacion> listaAuditoriaEstacion) {
        this.listaAuditoriaEstacion = listaAuditoriaEstacion;
    }

    public List<AuditoriaAreaComun> getListAuditoriaAreaComun() {
        return listAuditoriaAreaComun;
    }

    public void setListAuditoriaAreaComun(List<AuditoriaAreaComun> listAuditoriaAreaComun) {
        this.listAuditoriaAreaComun = listAuditoriaAreaComun;
    }

    public int getI_bus() {
        return i_bus;
    }

    public void setI_bus(int i_bus) {
        this.i_bus = i_bus;
    }

    public int getI_estacion() {
        return i_estacion;
    }

    public void setI_estacion(int i_estacion) {
        this.i_estacion = i_estacion;
    }

    public int getI_areaComun() {
        return i_areaComun;
    }

    public void setI_areaComun(int i_areaComun) {
        this.i_areaComun = i_areaComun;
    }

    public boolean isB_activa() {
        return b_activa;
    }

    public void setB_activa(boolean b_activa) {
        this.b_activa = b_activa;
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

    public List<?> getListTipoNov() {
        return listTipoNov;
    }

    public void setListTipoNov(List<?> listTipoNov) {
        this.listTipoNov = listTipoNov;
    }

    public List<?> getListTipoDetNov() {
        return listTipoDetNov;
    }

    public void setListTipoDetNov(List<?> listTipoDetNov) {
        this.listTipoDetNov = listTipoDetNov;
    }

    public int getI_idNovedadTipo() {
        return i_idNovedadTipo;
    }

    public void setI_idNovedadTipo(int i_idNovedadTipo) {
        this.i_idNovedadTipo = i_idNovedadTipo;
    }

    public int getI_idNovedadTipoFDe() {
        return i_idNovedadTipoFDe;
    }

    public void setI_idNovedadTipoFDe(int i_idNovedadTipoFDe) {
        this.i_idNovedadTipoFDe = i_idNovedadTipoFDe;
    }

    public boolean isB_user_generico() {
        return b_user_generico;
    }

    public void setB_user_generico(boolean b_user_generico) {
        this.b_user_generico = b_user_generico;
    }

}
