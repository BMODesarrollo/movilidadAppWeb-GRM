/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.aja.jornada.controller.GenericaJornadaFlexible;
import com.aja.jornada.dto.ErrorPrgSercon;
import com.aja.jornada.model.EmpleadoLiqUtil;
import com.aja.jornada.model.GenericaJornadaLiqUtil;
import com.aja.jornada.model.GenericaJornadaTipoLiqUtil;
import com.aja.jornada.model.ParamAreaLiqUtil;
import com.dbconnection.Common;
import com.movilidad.ejb.ConfigFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaExtraFacadeLocal;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.GenericaJornadaMotivoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaParamFacadeLocal;
import com.movilidad.ejb.GenericaJornadaTipoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaTokenFacadeLocal;
import com.movilidad.ejb.MarcacionDocumentosFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.ParamAreaCargoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.model.ConfigControlJornada;
import com.movilidad.model.Empleado;
import com.movilidad.model.Errores;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaJornadaDet;
import com.movilidad.model.GenericaJornadaExtra;
import com.movilidad.model.GenericaJornadaMotivo;
import com.movilidad.model.GenericaJornadaParam;
import com.movilidad.model.GenericaJornadaTipo;
import com.movilidad.model.GenericaJornadaToken;
import com.movilidad.model.MarcacionDocumentos;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.ParamAreaCargo;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.ParamReporteHoras;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.GenericaJornadaObj;
import com.movilidad.util.beans.MarcacionDocumentosArchivo;
import com.movilidad.utils.CagarJornadaGenericaException;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.UtilJornada;
import com.movilidad.utils.MovilidadUtil;
import static com.movilidad.utils.MovilidadUtil.fechaDiferente;
import static com.movilidad.utils.MovilidadUtil.getProperty;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigControlJornada;
import com.movilidad.utils.TokenGeneratorUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import liquidadorjornada.Jornada;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.primefaces.event.ToggleSelectEvent;

/**
 *
 * @author solucionesit
 */
@Named(value = "genCtrlJornadaMB")
@ViewScoped
public class GenericaControlJornadaMB implements
        Serializable {

    /**
     * Creates a new instance of GenericaControlJornadaMB
     */
    public GenericaControlJornadaMB() {
    }

    @EJB
    private GenericaJornadaMotivoFacadeLocal jornadaMotivoEJB;

    @EJB
    private GenericaJornadaTipoFacadeLocal jornadaTipoEJB;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;

    @EJB
    private ParamFeriadoFacadeLocal paraFeEJB;

    @EJB
    private GenericaJornadaFacadeLocal genJornadaEJB;

    @EJB
    private EmpleadoFacadeLocal emplEJB;

    @EJB
    private ParamAreaCargoFacadeLocal areaCargoEJB;

    @EJB
    private GenericaJornadaTipoFacadeLocal jornadaTEJB;

    @EJB
    private ConfigFacadeLocal configEJB;

    @EJB
    private GenericaJornadaParamFacadeLocal genJorParamEJB;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;

    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    @EJB
    private GenericaJornadaExtraFacadeLocal genJornadaExtraEjb;

    @EJB
    private GenericaJornadaTokenFacadeLocal genJornadaTokenEJB;

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEJB;

    @EJB
    private MarcacionDocumentosFacadeLocal marcDocEJB;

    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    @Inject
    private CalcularMasivoJSFManagedBean calcularMasivoBean;
    @Inject
    private UploadDocumentJSFManagedBean uploadDocumentMB;

    private List<GenericaJornada> listGenericaJornada = new ArrayList<>();
    private List<GenericaJornada> listGenericaJornadaSelected = new ArrayList<>();
    private List<GenericaJornada> listGenericaJornadaPersistir = new ArrayList<>();
    private List<GenericaJornadaMotivo> genericaJornadaMotivoList;
    private List<GenericaJornadaTipo> genericaJornadaTipoList;
    private List<GenericaJornadaDet> listGenericaJornadaDet;

    private GenericaJornada genericaJornada = null;
    private GenericaJornada genericaJornadaFilter = null;
    private GenericaJornada genericaJornadaNew = null;

    private GenericaJornadaTipo genericaJornadaTipo = null;

    private Empleado empl;
    private Date fechaDesdeBM = MovilidadUtil.fechaHoy();
    private Date fechaHastaBM = MovilidadUtil.fechaHoy();
    private boolean b_autoriza = false;
    private boolean b_genera = false;
    private boolean b_generaDelete = false;
    private boolean b_liquida = false;
    private boolean b_controlLiquida = false;
    private boolean b_controlAutoriza = false;
    private boolean b_controlSubirArchivo = false;
    private boolean b_permiso = false;
    private boolean b_jornada_flexible = false;
    private boolean flag_cargar_jornada = false;
    private boolean flag_cargar_novedades = false;
    private boolean b_orden_tbj = false;
    private boolean b_maxHorasExtrasSemanaMes = false; // Bandera para saber si se debe tener en cuenta el maximo de horas semanales y mensuales
    private boolean b_autorizaMarcaciones = false; // Bandera para saber si se debe tener en cuenta el maximo de horas semanales y mensuales
    private boolean habilitarTurnos = false;
    private boolean b_novFromFile = false;
    private Integer i_genericaJornadaMotivo = 0;
    private Integer i_genericaJornadaTipo = 0;
    private String rol_user = "";
    private String observacionesBM = "";
    private String ordenTrabajo = "";

    private String empleadoLabel;
    private String nombreEmpleado;
    private UploadedFile uploadedFile;
    private UploadedFile archivoNovedades;
    private int modulo_opc;
    private double horasExtrasInicio;
    private double horasExtrasFin;
    private String form_update;
    private String realTimeOrigin;
    private String realTimeDestiny;
    private String observaciones;
    private String MAX_HORAS_EXTRAS_DIARIAS;
    private String HORAS_JORNADA;

    private List<MarcacionDocumentosArchivo> listaDocsAux;
    private MarcacionDocumentosArchivo selected;
    private UploadedFile file;
    private List<GenericaJornada> listAutorizar = new ArrayList<>();

    List<Errores> listaError = new ArrayList<>();
    List<Empleado> listaEmpleados = new ArrayList<>();

    Map<String, GenericaJornadaExtra> mapaEmplJornadaExtra;

    HashMap<String, GenericaJornadaTipo> mapGenericaJornadaTipo;
    HashMap<String, GenericaJornada> mapGenericaJornada;
    HashMap<String, GenericaJornadaTipo> mapGenericaJornadaTipoAux;
    List<GenericaJornada> listaJornadaNotificar;
    List<GenericaJornada> listaNovedadesMarcaciones;
    List<GenericaJornada> listaMarcacionesOK;
    List<GenericaJornada> listaMarcacionesPorAutorizar;
    List<GenericaJornada> listaNovedadesMarcacionesHistorico;
    List<MarcacionDocumentos> listMarcaDoc;
    List<MarcacionDocumentosArchivo> listDocumentos;

    private ParamAreaUsr pau;
    private GenericaJornadaParam genJornadaParam;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private GenericaJornadaFlexible flexible;

    public GenericaJornadaFlexible getJornadaFlexible() {
        if (flexible == null) {
            flexible = new GenericaJornadaFlexible();
        }
        return flexible;
    }

    @PostConstruct
    public void init() {
        HORAS_JORNADA = UtilJornada.getTotalHrsLaborales();
        MAX_HORAS_EXTRAS_DIARIAS = UtilJornada.getMaxHrsExtrasDia();
        genericaJornadaFilter = initGenericaJornadaFilter();
        for (GrantedAuthority auth : user.getAuthorities()) {
            rol_user = auth.getAuthority();
        }
        //Objeto param area user 
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        if (pau != null) {
            genJornadaParam = genJorParamEJB.getByIdArea(pau.getIdParamArea().getIdParamArea());
            b_maxHorasExtrasSemanaMes = genJornadaParam != null
                    && genJornadaParam.getHorasExtrasSemanales() >= 0
                    && genJornadaParam.getHorasExtrasMensuales() >= 0;

            b_permiso = pau.getIdParamArea().getControlJornada().equals(1);
            b_jornada_flexible = jornadaFlexible();
        }
        consultar();
        validarRol();
        cargarNovedadesBIO();
    }

    private GenericaJornada initGenericaJornadaFilter() {
        GenericaJornada obj = new GenericaJornada();
        Empleado emp = new Empleado();
        emp.setNombres("");
        emp.setApellidos("");
        emp.setIdentificacion("");
        obj.setIdEmpleado(emp);
        obj.setTimeOrigin("");
        obj.setTimeDestiny("");
        obj.setSercon("");
        return obj;
    }

    private void cargarNovedadesBIO() {
        if (b_permiso) {//si tiene acceso al módulo cargue la información
            if (b_autorizaMarcaciones) {
                cargarNovedadesMarcaciones(null, null);
            } else {
                cargarNovedadesMarcaciones(MovilidadUtil.sumarDias(MovilidadUtil.fechaHoy(), -2), MovilidadUtil.fechaHoy());
            }
//            this.listMarcaDoc = this.marcDocEJB.findAllActivos();
        }
    }

    public void consultarNovedades() {
        if (pau == null) {
            listaNovedadesMarcaciones = new ArrayList<>();
        } else {
            listaNovedadesMarcaciones = getListBioNovedades(pau.getIdParamArea().getIdParamArea(), calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta());
        }
        PrimeFaces.current().executeScript("PF('dtnovedadeslist').clearFilters()");
    }

    public void consultarHistorico() {
        if (pau == null) {
            listaNovedadesMarcacionesHistorico = new ArrayList<>();
        } else {
            listaNovedadesMarcacionesHistorico = getListBioNovedadesHistorico(pau.getIdParamArea().getIdParamArea(), calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta());
        }
        PrimeFaces.current().executeScript("PF('dtnovedadeslist').clearFilters()");
    }

    private void cargarNovedadesMarcaciones(Date fDesde, Date fHasta) {
        int idArea = pau.getIdParamArea().getIdParamArea();
        listaNovedadesMarcaciones = getListBioNovedades(idArea, fDesde, fHasta);//las novedades que se desean gestionar, se establecen en la query del método 'findBiometricoNovedadesPorGestionar', actualmente se gestionan las novedades con estado 1-Retardo, 2-Abandono, 3-Ausencia, 5-Extra NO autorizada, importante no gestionar novedades que ya estén liquidadas
        listaNovedadesMarcacionesHistorico = getListBioNovedadesHistorico(pau.getIdParamArea().getIdParamArea(), calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta());//
        listaMarcacionesPorAutorizar = getListBioNovedadesPorAutorizar(idArea);
        listaMarcacionesOK = getListBioNovedadesOK(idArea);
    }

    /**
     * Permite traer los registros de marcacion en el biometrico cuyo estado sea
     * distinto a cero esto es, cuyos registros tengan alguna novedad.
     * Actualmente 0-Normal, 1-Retardo, 2-Abandono, 3-Ausencia
     */
    private List<GenericaJornada> getListBioNovedades(int idArea, Date fDesde, Date fHasta) {
        return genJornadaEJB.findBiometricoNovedades(idArea, fDesde, fHasta, 0, b_autorizaMarcaciones ? 1 : 0);
    }

    private List<GenericaJornada> getListBioNovedadesHistorico(int idArea, Date fDesde, Date fHasta) {
        return genJornadaEJB.findHistoricoMarcaciones(idArea, fDesde, fHasta, 0);
    }

    private List<GenericaJornada> getListBioNovedadesPorAutorizar(int idArea) {
        return genJornadaEJB.findBiometricoNovedadesPorGestionar(idArea, 0);
    }

    private List<GenericaJornada> getListBioNovedadesOK(int idArea) {
        return genJornadaEJB.findBiometricoOK(idArea, MovilidadUtil.sumarDias(MovilidadUtil.fechaHoy(), -29), MovilidadUtil.fechaHoy(), 0);
    }

    private boolean jornadaFlexible() {
        return pau.getIdParamArea().getJornadaFlexible() == null ? false
                : pau.getIdParamArea().getJornadaFlexible().equals(1);
    }

    /**
     * Consultar jorndas por rango de fechas y limpiar los filtros de la tabla
     */
    public void consultar() {
        if (pau == null) {
            listGenericaJornada = new ArrayList<>();
        } else {
            listGenericaJornada = genJornadaEJB.getByDate(calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta(), pau.getIdParamArea().getIdParamArea());
        }
        PrimeFaces.current().executeScript("PF('dtserconlist').clearFilters()");
    }

    /**
     * Seleccionar un registor de la tabla de jornadas
     *
     * @param event
     * @throws Exception
     */
    public void onRowDblClckSelect(final SelectEvent event) throws Exception {
        if (empl == null) {
            empl = new Empleado();
        }
        PrimeFaces current = PrimeFaces.current();
        setEmpl((Empleado) event.getObject());
        nombreEmpleado = empl.getIdentificacion() + " " + empl.getNombres() + " " + empl.getApellidos();
        MovilidadUtil.addSuccessMessage("Empleado Cargado.");
        if (modulo_opc == 0) {
            current.ajax().update("form_turno:codigo_operador");
            current.ajax().update("form_turno:msgs_turno");
        }
        if (modulo_opc == 1) {
            current.ajax().update("form_borrar_masivo:codigo_operador");
            current.ajax().update("form_borrar_masivo:msgs_turno");
        }
        if (modulo_opc == 2) {
            current.ajax().update("form_ajuste_jornada_fecha:codigo_operador");
            current.ajax().update("form_ajuste_jornada_fecha:msgs_turno");
        }

        current.executeScript("PF('empleado_list_wv').hide();");
    }

    /**
     * Preparar variales para la gestión de borrado de jorndas por rango de
     * fechas.
     */
    public void preBorrarMasivo() {
        modulo_opc = 1;
        nombreEmpleado = "";
        empl = null;
        if (genericaJornada != null) {
            empl = genericaJornada.getIdEmpleado();
            nombreEmpleado = empl.getIdentificacion() + " " + empl.getNombres() + " " + empl.getApellidos();
        }
        i_genericaJornadaMotivo = 0;
        i_genericaJornadaTipo = 0;
        fechaDesdeBM = MovilidadUtil.fechaHoy();
        fechaHastaBM = MovilidadUtil.fechaHoy();
        cargarMotivos();
        consultar();
        MovilidadUtil.openModal("borrar_wv_dialog");
    }

    /**
     * Preparar las variblas para la gestion de ajuste de jorndas por rango de
     * fechas.
     */
    public void preAjustarPorFechas() {
        empl = null;
        nombreEmpleado = "";
        if (genericaJornada != null) {
            empl = genericaJornada.getIdEmpleado();
            nombreEmpleado = empl.getIdentificacion() + " " + empl.getNombres() + " " + empl.getApellidos();
        }
        observacionesBM = "";
        i_genericaJornadaMotivo = 0;
        i_genericaJornadaTipo = 0;
        modulo_opc = 2;
        fechaDesdeBM = MovilidadUtil.fechaHoy();
        fechaHastaBM = MovilidadUtil.fechaHoy();
        cargarMotivos();
        PrimeFaces.current().executeScript("PF('ajuste_jornada_fecha_wv').show();");
    }

    /**
     * Permite autorizar las novedades que se han pre-seleccionado en el reporte
     * de control jornada.El proceso toma en lista todos los registros
     * seleccionados en el reporte y evalúa si el estado del registro es "por
     * Autorizar", de ser así procede a ejecutar el proceso de autorización.
     *
     * @throws java.text.ParseException
     */
    public void autorizarNovedades() throws ParseException {
        if (!listGenericaJornadaSelected.isEmpty()) {
            for (GenericaJornada obj : listGenericaJornadaSelected) {
                if (obj.getAutorizado() != null && obj.getAutorizado() == -1) {//solamente se autorizan los registros que estén por autorizar
                    autorizar(obj, 1);
                }
            }
        }
    }

    /**
     * Preparar variables para la gestión de ajuste de jorndas, desde el botón
     * naranja.
     */
    public void preAjustar() {
        if (genericaJornada == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar una jornada.");
            return;
        }
        genericaJornada = genJornadaEJB.find(genericaJornada.getIdGenericaJornada());
        if (genericaJornada.getNominaBorrada() == 1) {
            genericaJornada = null;
            return;
        }
        if (liquidado(genericaJornada)) {
            genericaJornada = null;
            return;
        }
//Se debería comentar la inicialización de valores dado que 00:00:00 afecta la validación 
//al momento de calcular las jornadas por partes        

        if (genericaJornada.getHiniTurno2() == null) {
            genericaJornada.setHiniTurno2("00:00:00");
        }
        if (genericaJornada.getHfinTurno2() == null) {
            genericaJornada.setHfinTurno2("00:00:00");
        }
        if (genericaJornada.getHiniTurno3() == null) {
            genericaJornada.setHiniTurno3("00:00:00");
        }
        if (genericaJornada.getHfinTurno3() == null) {
            genericaJornada.setHfinTurno3("00:00:00");
        }
        ordenTrabajo = genericaJornada.getOrdenTrabajo();
        if (genJornadaParam != null) {
            b_orden_tbj = genericaJornada.getOrdenTrabajo() != null;
        }
        horasExtrasInicio = horasExtrasByJornada(genericaJornada);

        if (genericaJornada.getIdGenericaJornadaMotivo() != null) {
            i_genericaJornadaMotivo = genericaJornada.getIdGenericaJornadaMotivo().getIdGenericaJornadaMotivo();
        }
        if (genericaJornada.getRealTimeOrigin() == null) {
            genericaJornada.setRealTimeOrigin(genericaJornada.getTimeOrigin());
        }
        cargarMotivos();
        PrimeFaces.current().executeScript("PF('genCtrlJornadaMBDialog').show();");
    }

    public void preGestionar() {
        if (genericaJornada == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar una jornada.");
            return;
        }
        cargarMotivos();
        cargarTipoJornada();
        genericaJornada = genJornadaEJB.find(genericaJornada.getIdGenericaJornada());
        if (genericaJornada.getRealTimeOrigin() == null) {
            genericaJornada.setRealTimeOrigin(genericaJornada.getTimeOrigin());
        }
//        PrimeFaces.current().executeScript("PF('gestionMarcacionMBDialog').show();");
    }

    public void preAutorizar() {
        if (genericaJornada == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar una jornada.");
            return;
        }
        cargarMotivos();
        cargarTipoJornada();
        genericaJornada = genJornadaEJB.find(genericaJornada.getIdGenericaJornada());
        if (genericaJornada.getRealTimeOrigin() == null) {
            genericaJornada.setRealTimeOrigin(genericaJornada.getTimeOrigin());
        }
        PrimeFaces.current().executeScript("PF('gestionMarcacionMBDialog').show();");
    }

    /**
     * Sirve par indicar si un registro de de generica jornada esta liquidado o
     * no.
     *
     * @param gj jornada a evaluar
     * @return true si esta liquidada, false si no está liquidado.
     */
    boolean liquidado(GenericaJornada gj) {
        return gj.getLiquidado() != null && gj.getLiquidado() == 1;
    }

    public void ajustarHoras() {
        if (Objects.nonNull(i_genericaJornadaTipo) && i_genericaJornadaTipo != 0) {
            genericaJornadaTipo = jornadaTipoEJB.find(i_genericaJornadaTipo);
            genericaJornada.setRealTimeOrigin(genericaJornadaTipo.getHiniT1());
            genericaJornada.setRealTimeDestiny(genericaJornadaTipo.getHfinT1());
        }
    }

    /**
     * Consulta los motivos de jornada.
     */
    public void cargarMotivos() {
        genericaJornadaMotivoList = jornadaMotivoEJB.findByArea(pau.getIdParamArea().getIdParamArea());
    }

    /**
     * Consulta los tipos de jornada.
     */
    public void cargarTipoJornada() {
        genericaJornadaTipoList = jornadaTipoEJB.findAllByArea(pau.getIdParamArea().getIdParamArea());
    }

    /**
     * prepara las variables para la creaación de nuevos turnos genericos.
     */
    public void preCrearTurno() {
        ordenTrabajo = null;
        modulo_opc = 0;
        i_genericaJornadaMotivo = 0;
        empleadoLabel = "";
        nombreEmpleado = "";
        empl = null;
        if (genericaJornada != null) {
            empl = genericaJornada.getIdEmpleado();
            nombreEmpleado = empl.getIdentificacion() + " " + empl.getNombres() + " " + empl.getApellidos();
        }
        b_orden_tbj = false;
        genericaJornadaNew = new GenericaJornada();
        genericaJornadaNew.setFecha(MovilidadUtil.fechaCompletaHoy());
        genericaJornadaNew.setHiniTurno2("00:00:00");
        genericaJornadaNew.setHfinTurno2("00:00:00");
        genericaJornadaNew.setHiniTurno3("00:00:00");
        genericaJornadaNew.setHfinTurno3("00:00:00");
        cargarMotivos();
        MovilidadUtil.openModal("crear_turno_wv_dialog");
    }

    /**
     * Valida si la jornda nueva requiere orden de trabajo.
     */
    public void validarOrdenTrabajo() {

        if (genericaJornadaNew == null) {
            return;
        }
        if (genJornadaParam == null) {
            b_orden_tbj = false;
            return;
        }
        if (genericaJornadaNew.getRealTimeOrigin() == null) {
            return;
        }
        if (horaInicioMax(genericaJornadaNew)) {
            genericaJornadaNew.setRealTimeOrigin("23:59:59");
            return;
        }
        if (genericaJornadaNew.getRealTimeDestiny() == null) {
            return;
        }
        GenericaJornadaTipo gjt = findGenericaJornadaTipo(true, genericaJornadaNew);
        String horasExtras = horaExtras(genericaJornadaNew.getRealTimeOrigin(),
                genericaJornadaNew.getRealTimeDestiny(),
                workTimeFlexibleOrdinario(genericaJornadaNew.getSercon(), genericaJornadaNew.getWorkTime()), gjt);
        if (MovilidadUtil.toSecs(horasExtras) > 0
                && genJornadaParam.getRequerirOrdenTrabajo() == 1) {
            b_orden_tbj = true;
        } else {
            b_orden_tbj = false;
            ordenTrabajo = null;

        }
    }

    /**
     * Valida si la hora inicio no se pase d elo permitido.
     *
     * @param gj
     * @return true si es mayor 23:59:59 y false si no.
     */
    boolean horaInicioMax(GenericaJornada gj) {
        if (MovilidadUtil.toSecs(gj.getRealTimeOrigin()) > MovilidadUtil.toSecs("23:59:59")) {
            MovilidadUtil.addErrorMessage("La hora inicio no puede ser superior a 23:59:59.");
            return true;
        }
        return false;
    }

    /**
     * Realiza la suma de todas las horas extras de una jornada.
     *
     * @param gj jornada a evaluar
     * @return suma totla de las horas extras.
     */
    public int horasExtrasByJornada(GenericaJornada gj) {
        int extrasDiurna = MovilidadUtil.toSecs(gj.getExtraDiurna());
        int extrasNocturna = MovilidadUtil.toSecs(gj.getExtraNocturna());
        int festivoExtrasDiurna = MovilidadUtil.toSecs(gj.getFestivoExtraDiurno());
        int festivoExtrasNocturna = MovilidadUtil.toSecs(gj.getFestivoExtraNocturno());
        int dominicalExtrasNocturna = MovilidadUtil.toSecs(gj.getDominicalCompNocturnaExtra());
        int dominicalExtrasdiurna = MovilidadUtil.toSecs(gj.getDominicalCompDiurnaExtra());
        return extrasDiurna + extrasNocturna + festivoExtrasDiurna
                + festivoExtrasNocturna + dominicalExtrasNocturna + dominicalExtrasdiurna;
    }

    /**
     * Valida si la jornada al ajustar requiere orden de trabajo.
     *
     */
    public void validarOrdenTrabajoAlAjustar() {

        if (genericaJornada == null) {
            return;
        }
        if (genJornadaParam == null) {
            b_orden_tbj = false;
            return;
        }
        if (genericaJornada.getRealTimeOrigin() == null) {
            return;
        }
        if (horaInicioMax(genericaJornada)) {
            genericaJornada.setRealTimeOrigin("23:59:59");
            return;
        }
        if (genericaJornada.getRealTimeDestiny() == null) {
            return;
        }
        GenericaJornadaTipo gjt = findGenericaJornadaTipo(true, genericaJornada);
        String workTimeJornada = HORAS_JORNADA;
        if (b_jornada_flexible) {
            workTimeJornada = MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(genericaJornada.getSercon(), genericaJornada.getWorkTime()));
        }
        String horasExtrasNuevas = horaExtras(genericaJornada.getRealTimeOrigin(), genericaJornada.getRealTimeDestiny(), workTimeJornada, gjt);
        int totalExtrasViejas = horasExtrasByJornada(genericaJornada);
        if (MovilidadUtil.toSecs(horasExtrasNuevas) > totalExtrasViejas && genJornadaParam.getRequerirOrdenTrabajo() == 1) {
            b_orden_tbj = true;
        } else {
            if (!(genericaJornada.getOrdenTrabajo() == null || (genericaJornada.getOrdenTrabajo() != null
                    && genericaJornada.getOrdenTrabajo().replaceAll("\\s", "").isEmpty()))) {
                b_orden_tbj = true;
            } else {
                b_orden_tbj = false;
                ordenTrabajo = null;
            }
        }
    }

    /**
     * Calcula el total de horas extras de una jornada
     *
     * @param hIni Hora inicio de la jornada
     * @param hFin Hora fin de la jornada
     * @param gjt GenericaJornadaTipo parametrizada segun la hora inicio y fin
     * de la jornada.
     * @return numeor de horas en fomato de horas.
     */
    public String horaExtras(String hIni, String hFin, String workTime, GenericaJornadaTipo gjt) {
        int extras = (MovilidadUtil.toSecs(hFin)
                - MovilidadUtil.toSecs(hIni)) - MovilidadUtil.toSecs(workTime);
        if (extras > 0) {
            if (gjt != null) {
                if (extras > MovilidadUtil.toSecs(gjt.getDescanso())) {
                    return MovilidadUtil.toTimeSec(extras
                            - MovilidadUtil.toSecs(gjt.getDescanso()));
                } else {
                    return "00:00:00";
                }
            } else {
                return MovilidadUtil.toTimeSec(extras);
            }
        } else {
            return "00:00:00";
        }
    }

    public void autorizarCalcular(boolean b_descanso) {

        genericaJornadaNew.setAutorizado(1); //Se autoriza la jornada
        genericaJornadaNew.setUserAutorizado(user.getUsername());
        genericaJornadaNew.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        //Consultar el tipo de jornada segun la hora inicio y hora fin de la jornada.
        GenericaJornadaTipo gjt = findGenericaJornadaTipo(true, genericaJornadaNew);
        calcularMasivoBean.cargarMapParamFeriado();
        genericaJornadaNew.setIdGenericaJornadaTipo(gjt);
        boolean b_jornadaExtra = false;
        if (isExtra(genericaJornadaNew.getTimeOrigin(), genericaJornadaNew.getTimeDestiny(), HORAS_JORNADA)) {
            //Se valida que la jornada tenga un tipo de jornada.
            if (genericaJornadaNew.getIdGenericaJornadaTipo() != null) {
                //Se valida que la jornada no se pase de dos horas extras 
                //restando las horas de descanso de tipo de jornada.
                if (!((MovilidadUtil.toSecs(genericaJornadaNew.getTimeDestiny())
                        - MovilidadUtil.toSecs(genericaJornadaNew.getTimeOrigin())
                        - MovilidadUtil.toSecs(HORAS_JORNADA)
                        - MovilidadUtil.toSecs(genericaJornadaNew.getIdGenericaJornadaTipo().getDescanso()))
                        <= MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) {
                    MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                            + genericaJornadaNew.getIdEmpleado().getIdentificacion()
                            + "-"
                            + genericaJornadaNew.getIdEmpleado().getNombres()
                            + " "
                            + genericaJornadaNew.getIdEmpleado().getApellidos());
                    return;

                } else {
                    b_jornadaExtra = true;
                }
            } else {
                //Se valida que la jornada no se pase de dos horas extras 
                if (!((MovilidadUtil.toSecs(genericaJornadaNew.getTimeDestiny())
                        - MovilidadUtil.toSecs(genericaJornadaNew.getTimeOrigin())
                        - MovilidadUtil.toSecs(HORAS_JORNADA)) <= MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) {
                    MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                            + genericaJornadaNew.getIdEmpleado().getIdentificacion()
                            + "-"
                            + genericaJornadaNew.getIdEmpleado().getNombres()
                            + " "
                            + genericaJornadaNew.getIdEmpleado().getApellidos());
                    return;
                } else {
                    b_jornadaExtra = true;
                }
            }
        }
        boolean flagTipoCalculo = genericaJornadaNew.getIdGenericaJornadaTipo() != null && genericaJornadaNew.getIdGenericaJornadaTipo().getTipoCalculo() == 1;
        if (flagTipoCalculo) {
            calcularMasivoBean.cargarCalcularDatoPorPartes(genericaJornadaNew);
        } else {
            genericaJornadaNew = calcularMasivoBean.cargarCalcularDato(genericaJornadaNew, 2);
        }
        if (calcularMasivoBean.validarHorasPositivas(genericaJornadaNew)) {
            MovilidadUtil.addErrorMessage("Error al calcular jornada");
            return;
        }
        if (!flagTipoCalculo && (b_descanso && b_jornadaExtra)) {
            calcularExtrasJornadaOrdinaria(genericaJornadaNew);
        }
        genericaJornadaNew.setUserAutorizado(user.getUsername());
        genericaJornadaNew.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        // (1) uno para autorizar, (0) cero para no autorizar
        genericaJornadaNew.setAutorizado(1);
        genJornadaEJB.create(genericaJornadaNew);

        calcularMasivoBean.recalcularJornada(genericaJornadaNew);
    }

    public void noautorizarNoCalcular() {
        genericaJornadaNew.setAutorizado(-1);
        genericaJornadaNew.setUserAutorizado("");
        genericaJornadaNew.setFechaAutoriza(null);
        genJornadaEJB.create(genericaJornadaNew);
        //Notificar la creacion de la jornada
        if (genJornadaParam != null && genJornadaParam.getNotifica() == 1) {
            if (genJornadaParam.getEmails() != null) {
                notificar(genericaJornadaNew);
            }
        }
    }

    /**
     * Valida si hay horas extras en una jornada.
     *
     * @param horaInicio
     * @param horaFin
     * @return True si al restar la hora fin contra la hora incio el resultado
     * es mayor que el tiempo de trabajo, false si no.
     */
    private boolean isExtra(String horaInicio, String horaFin, String workTime) {
        return (MovilidadUtil.toSecs(horaFin)
                - MovilidadUtil.toSecs(horaInicio))
                > MovilidadUtil.toSecs(workTime);
    }

    public boolean validarOrdenTrabajoAjusteMasivo(List<GenericaJornada> listaJornada,
            GenericaJornadaTipo gjt) {
        for (GenericaJornada jornada : listaJornada) {
            String horasExtras = horaExtras(realTimeOrigin,
                    realTimeDestiny,
                    workTimeFlexibleOrdinario(jornada.getSercon(), jornada.getWorkTime()),
                    gjt);
            int totalExtrasViejas = horasExtrasByJornada(jornada);

            if (MovilidadUtil.toSecs(horasExtras) > totalExtrasViejas) { //Se valida si hay cambio en las horas extras.
                if (ordenTrabajo == null || (ordenTrabajo != null
                        && ordenTrabajo.replaceAll("\\s", "").isEmpty())) { // se valida que se haya digitado el orden de trabajo
                    MovilidadUtil.addErrorMessage("Falta orden de trabajo.");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean validarHrsExtrasPorRangoFechaJornadaFlexible(List<GenericaJornada> listaJornada, GenericaJornadaTipo gjt) throws ParseException {
        for (GenericaJornada jornadaValid : listaJornada) {
            String productionTime = timeProductionNew(jornadaValid);
            if (MovilidadUtil.toSecs(productionTime) < MovilidadUtil.toSecs(UtilJornada.getMinHrsLaboralesDia())) {
                String msg = "La jornada no cumple con el minimo de horas laborales, " + UtilJornada.getMinHrsLaboralesDia() + ". Encontrado, " + productionTime;
                if (UtilJornada.getMinHrsLaboralesDiaObj().getRestringe().equals(ConstantsUtil.ON_INT)) {
                    MovilidadUtil.addErrorMessage(msg);
                    return true;
                } else {
                    MovilidadUtil.addAdvertenciaMessage(msg);
                }
            }
            if (MovilidadUtil.toSecs(productionTime) > MovilidadUtil.toSecs(UtilJornada.getMaxHrsLaboralesDia())) {
                String mssg = "Se excedió el máximo de horas laborales por día, " + UtilJornada.getMaxHrsLaboralesDia() + ". Encontrado "
                        + productionTime;
                if (UtilJornada.getMaxHrsLaboralesDiaObj().getRestringe().equals(ConstantsUtil.ON_INT)) {
                    MovilidadUtil.addErrorMessage(mssg);
                    return true;
                } else {
                    MovilidadUtil.addAdvertenciaMessage(mssg);
                }
            }
            jornadaValid.setIdGenericaJornadaTipo(gjt);
            jornadaValid.setIdGenericaJornadaMotivo(new GenericaJornadaMotivo(i_genericaJornadaMotivo));
            jornadaValid.setObservaciones(observacionesBM);
            jornadaValid.setRealTimeOrigin(realTimeOrigin);
            jornadaValid.setRealTimeDestiny(realTimeDestiny);
            jornadaValid.setPrgModificada(1);
            if (validarHorasExtrasFlexible(jornadaValid, true, false)) {
                return true;
            }
        }
        if (validarMaxHrsExtrasSmnlsFlexibleRangoFecha(listaJornada, gjt, fechaDesdeBM, fechaHastaBM, realTimeOrigin, realTimeDestiny)) {
            return true;
        }
        return false;
    }

    boolean validarMaxHrsExtrasSmnlsFlexibleRangoFecha(List<GenericaJornada> list,
            GenericaJornadaTipo gjt, Date desde, Date hasta,
            String realTimeOrigin, String realTimeDestiny) throws ParseException {

        List<GenericaJornadaLiqUtil> listaParam = new ArrayList<>();
        int idEmpleado = 0;
        int idParamArea = 0;
        for (GenericaJornada item : list) {
            idEmpleado = item.getIdEmpleado().getIdEmpleado();
            idParamArea = item.getIdParamArea().getIdParamArea();
            GenericaJornadaLiqUtil liqUtil = new GenericaJornadaLiqUtil();
            liqUtil.setAutorizado(1);
            if (gjt != null) {
                liqUtil.setIdGenericaJornadaTipo(new GenericaJornadaTipoLiqUtil());
                liqUtil.getIdGenericaJornadaTipo().setDescanso(gjt.getDescanso());
                liqUtil.getIdGenericaJornadaTipo().setTipoCalculo(gjt.getTipoCalculo());
            }
            liqUtil.setFecha(item.getFecha());
            liqUtil.setRealTimeOrigin(realTimeOrigin);
            liqUtil.setRealTimeDestiny(realTimeDestiny);
            liqUtil.setSercon(item.getSercon());
            liqUtil.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(item.getSercon(), item.getWorkTime())));
            liqUtil.setIdEmpleado(new EmpleadoLiqUtil(idEmpleado));
            liqUtil.setIdParamArea(new ParamAreaLiqUtil());
            listaParam.add(liqUtil);
        }
        ErrorPrgSercon validateMaxHrsSmnl = getJornadaFlexible().validateMaxHrsSmnlFromList(listaParam, desde, hasta, idEmpleado, idParamArea);
        if (validateMaxHrsSmnl.isStatus()) {
            String msg = "Se excedió el máximo de horas extras semanales" + UtilJornada.getMaxHrsExtrasSmnl() + ". Encontrado "
                    + MovilidadUtil.toTimeSec(validateMaxHrsSmnl.getHora());

            ConfigControlJornada get = UtilJornada.getMaxHrsExtrasSmnlObj();
            if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                MovilidadUtil.addErrorMessage(msg);
            } else {
                MovilidadUtil.addAdvertenciaMessage(msg);
            }
            return true;
        }
        return false;
    }
//    @Transactional

    public void guardarAjusteJornadafechas() throws ParseException {
        listaJornadaNotificar = new ArrayList<>();
        if (fechaDesdeBM == null || fechaHastaBM == null) {
            MovilidadUtil.addErrorMessage("Seleccionar el rango de fechas");
            return;
        }
        if (fechaHastaBM.before(fechaDesdeBM)) {
            MovilidadUtil.addErrorMessage("La Fecha Hasta debe ser posterior o igual a la Fecha Desde");
            return;
        }
        if (validarJornadasViejas(fechaDesdeBM)) {
            MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
            return;
        }
        if (validarHoras(realTimeOrigin, realTimeDestiny)) {
            MovilidadUtil.addErrorMessage("La hora inicio del turno 1 no puede ser mayor a la hora fin turno 1");
            return;
        }
        if (empl == null) {
            MovilidadUtil.addErrorMessage("No se ha cargado el empleado");
            return;
        }
        if (i_genericaJornadaMotivo == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado el motivo");
            return;
        }
        GenericaJornadaTipo gjt = jornadaTEJB.
                findByHIniAndHFin(realTimeOrigin, realTimeDestiny,
                        pau.getIdParamArea().getIdParamArea()); // se consulta un tipo de jornada.

        List<GenericaJornada> listaJornada = genJornadaEJB.
                getJornadasPorRangoFechasIdAreaIdEmpleado(fechaDesdeBM,
                        fechaHastaBM, pau.getIdParamArea().getIdParamArea(),
                        empl.getIdEmpleado()); //Se consulta la lista de jornada que se van a afectar.
        if (listaJornada.isEmpty()) {
            MovilidadUtil.addErrorMessage("No existen jornada para registradas para empleado en el rango de fechas.");
            return;
        }
        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        for (GenericaJornada gj : listaJornada) {
            gj.setRealTimeOrigin(realTimeOrigin);
            gj.setRealTimeDestiny(realTimeDestiny);
        }
        UtilJornada.cargarParametrosJar();
        if (b_jornada_flexible) {
            if (validarHrsExtrasPorRangoFechaJornadaFlexible(listaJornada, gjt)) {
                return;
            }
        } else { //Se valida que la jornada no tenga mas de 8 horas.
            if (isExtra(realTimeOrigin, realTimeDestiny, HORAS_JORNADA)) { //Se valida que la jornada no tenga mas de 8 horas.

                if (gjt != null) {
                    if (((MovilidadUtil.toSecs(realTimeDestiny)
                            - MovilidadUtil.toSecs(realTimeOrigin)
                            - MovilidadUtil.toSecs(HORAS_JORNADA)
                            - MovilidadUtil.toSecs(gjt.getDescanso()))
                            > MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) { // Se valida que la jornada o tenga mas de 2 horas extas.
                        MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias");
                        return;

                    }
                } else {
                    if (((MovilidadUtil.toSecs(realTimeDestiny)
                            - MovilidadUtil.toSecs(realTimeOrigin)
                            - MovilidadUtil.toSecs(HORAS_JORNADA))
                            > MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) { //Se valida que la jornada no tenga mas de 2 horas extras
                        MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias.");
                        return;
                    }
                }
            }
        }

        boolean flag_orden = false;
        if (!b_jornada_flexible) {
            if (genJornadaParam != null
                    && genJornadaParam.getRequerirOrdenTrabajo() == 1) {
                flag_orden = true;
                if (validarOrdenTrabajoAjusteMasivo(listaJornada, gjt)) {
                    return;
                }
            }
        } //Se valida si el orden de trabajo es sequerido.
        for (GenericaJornada gj : listaJornada) {
            gj.setIdGenericaJornadaTipo(gjt);
            gj.setIdGenericaJornadaMotivo(new GenericaJornadaMotivo(i_genericaJornadaMotivo));
            gj.setObservaciones(observacionesBM);
            gj.setPrgModificada(1);
            gj.setUserGenera(user.getUsername());
            gj.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
            if (flag_orden) {
                gj.setOrdenTrabajo(ordenTrabajo);
            }
        }

        if (b_jornada_flexible) {
            if (validarRolesAutoriza()) {
                guardarJornadasRangoFechaFlexible(listaJornada);
            } else {
                for (GenericaJornada gj : listaJornada) {
                    gj.setAutorizado(-1);
                    gj.setUserAutorizado("");
                    gj.setFechaAutoriza(null);
                    genJornadaEJB.edit(gj);
                }
            }
        } else {
            boolean ok = false;
            if (b_maxHorasExtrasSemanaMes) {
                ok = validarHoraExtraSemanaAndMensualAjustarJornadaMasivo(listaJornada);
            }
            if (!ok) {
                calcularMasivoBean.cargarMapParamFeriado();
                for (GenericaJornada jornada : listaJornada) {

                    GenericaJornadaExtra genJorExtra = null;
                    if (b_maxHorasExtrasSemanaMes) {
                        horasExtrasInicio = horasExtrasByJornada(jornada);
                        horasExtrasFin = MovilidadUtil.toSecs(
                                horaExtras(jornada.getRealTimeOrigin(),
                                        jornada.getRealTimeDestiny(),
                                        workTimeFlexibleOrdinario(jornada.getSercon(), jornada.getWorkTime()),
                                        gjt));
                        genJorExtra = calcularGenericaJornadaExtra(jornada);
                    }
                    if (rol_user.equals("ROLE_EMPLGEN") || rol_user.equals("ROLE_MTTO") || rol_user.equals("ROLE_TECMTTO")) {
//                    if (gj.getAutorizado() != null && gj.getAutorizado() == 1) {
//                        consultar();
//                        MovilidadUtil.addAdvertenciaMessage("No se puede realizar la acción, el registro ya fue autorizado por un profesional");
//                        return;
//                    }
                        jornada.setAutorizado(-1);
                        jornada.setUserAutorizado("");
                        jornada.setFechaAutoriza(null);
                        jornada.setUserGenera(user.getUsername());
                        jornada.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
                        jornada.setProductionTimeReal(timeProduction(jornada));
                        genJornadaEJB.edit(jornada);
                        listaJornadaNotificar.add(jornada);
                        genJorExtra = null;
                    }
                    if (validarRolesAutoriza()) {
                        int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(jornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);

                        if (genJornadaParam == null) {
                            autorizarCalcularAlAjustar(jornada, false, false);
                        } else {
                            if (genJornadaParam.getCtrlAutorizarExtensionJornada() == 1
                                    || (genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                                    && validacionDia != 0)) {
                                autorizarCalcularAlAjustar(jornada, false, false);
                            } else {
                                jornada.setUserGenera(user.getUsername());
                                jornada.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
                                jornada.setProductionTimeReal(timeProduction(jornada));
                                genJornadaEJB.edit(jornada);
                                listaJornadaNotificar.add(jornada);
                                genJorExtra = null;
                            }
                        }
                    } else if (rol_user.equals("ROLE_DIRGEN")) {
                        autorizarCalcularAlAjustar(jornada, false, false);
                    }
                    if (genJorExtra != null) { //Se valida si hay jornada extra para registrar o modificar.
                        if (genJorExtra.getIdGenericaJornadaExtra() == null) {
                            genJornadaExtraEjb.create(genJorExtra);
                        } else {
                            genJornadaExtraEjb.edit(genJorExtra);
                        }
                    }
                }
                MovilidadUtil.addSuccessMessage("Operación exitosa.");
                if (genJornadaParam != null && genJornadaParam.getNotifica() == 1) {
                    if (genJornadaParam.getEmails() != null) {
                        listaJornadaNotificar.forEach((gj) -> {
                            notificar(gj);
                        });
                    }
                }
            }
        }
        realTimeDestiny = null;
        realTimeOrigin = null;
        i_genericaJornadaMotivo = 0;
        i_genericaJornadaTipo = 0;
        observacionesBM = "";
        MovilidadUtil.hideModal("ajuste_jornada_fecha_wv");
        consultar();
    }

    private boolean validarRolesAutoriza() {
        return rol_user.equals("ROLE_PROFGEN") || rol_user.equals("ROLE_LIQGEN")
                || rol_user.equals("ROLE_PROFMTTO") || rol_user.equals("ROLE_LIQMTTO")
                || rol_user.equals("ROLE_LIQ") || rol_user.equals("ROLE_PROFOP");
    }

    private void guardarJornadasRangoFechaFlexible(List<GenericaJornada> listaJornada) {
        for (GenericaJornada jornada : listaJornada) {
            jornada.setAutorizado(1);
            GenericaJornadaLiqUtil param = cargarObjetoParaJar(jornada);
            if (jornada.getIdGenericaJornadaTipo() != null) {
                param.setIdGenericaJornadaTipo(new GenericaJornadaTipoLiqUtil());
                param.getIdGenericaJornadaTipo().setDescanso(jornada.getIdGenericaJornadaTipo().getDescanso());
                param.getIdGenericaJornadaTipo().setTipoCalculo(jornada.getIdGenericaJornadaTipo().getTipoCalculo());
            }
            if (MovilidadUtil.isSunday(jornada.getFecha())) {

                List<GenericaJornadaLiqUtil> preCagarHorasDominicales = getJornadaFlexible().preCagarHorasDominicales(jornada.getFecha(),
                        jornada.getIdParamArea().getIdParamArea(),
                        jornada.getIdEmpleado().getIdEmpleado(), param);
                GenericaJornadaLiqUtil get = preCagarHorasDominicales.get(0);
                setValueFromPrgSerconJar(get, jornada);
                genJornadaEJB.edit(jornada);

            } else {
                param.setFecha(jornada.getFecha());
                param.setIdEmpleado(new EmpleadoLiqUtil(jornada.getIdEmpleado().getIdEmpleado()));
                param.setIdParamArea(new ParamAreaLiqUtil(jornada.getIdParamArea().getIdParamArea()));
                param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(jornada.getSercon(), jornada.getWorkTime())));
                ErrorPrgSercon errorPrgSercon
                        = getJornadaFlexible().recalcularHrsExtrasSmnl(param);
                if (errorPrgSercon.isStatus()) {
                    String msg = "Se excedió el máximo de horas extras diarias " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                            + MovilidadUtil.toTimeSec(errorPrgSercon.getHora());
                    ConfigControlJornada get = UtilJornada.getMaxHrsExtrasDiaObj();
                    if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                        MovilidadUtil.addErrorMessage(msg);
                        return;
                    } else {
                        MovilidadUtil.addAdvertenciaMessage(msg);
                    }
                }
                List<GenericaJornadaLiqUtil> list = errorPrgSercon.getListaGen().stream()
                        .filter(x -> !fechaDiferente(jornada.getFecha(), x.getFecha()))
                        .collect(Collectors.toList());
                GenericaJornadaLiqUtil get = list.get(0);
                setValueFromPrgSerconJar(get, jornada);
                genJornadaEJB.edit(jornada);
                list = errorPrgSercon.getListaGen().stream()
                        .filter(x -> fechaDiferente(jornada.getFecha(), x.getFecha()))
                        .collect(Collectors.toList());
                genJornadaEJB.updatePrgSerconFromList(list, 0);

            }
        }
    }

    public void guardarTurno() throws ParseException {
        if (pau == null) {
            MovilidadUtil.addErrorMessage("No tiene acceso para crear turnos");
            return;
        }
        if (disableBoton()) {
            MovilidadUtil.addErrorMessage("La fecha no puede ser anterior a la del día de hoy");
            return;
        }
        if (validarHoras(genericaJornadaNew.getRealTimeOrigin(), genericaJornadaNew.getRealTimeDestiny())) {
            MovilidadUtil.addErrorMessage("La hora inicio del turno 1 no puede ser mayor a la hora fin turno 1");
            return;
        }
        if (validarHoras(genericaJornadaNew.getHfinTurno2(), genericaJornadaNew.getHfinTurno2())) {
            MovilidadUtil.addErrorMessage("La hora inicio del turno 2 no puede ser mayor a la hora fin turno 2");
            return;
        }
        if (validarHoras(genericaJornadaNew.getHfinTurno3(), genericaJornadaNew.getHfinTurno3())) {
            MovilidadUtil.addErrorMessage("La hora inicio del turno 3 no puede ser mayor a la hora fin turno 3");
            return;
        }
        if (empl == null) {
            MovilidadUtil.addErrorMessage("No se ha cargado el empleado");
            return;
        }

        if (i_genericaJornadaMotivo == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado el motivo");
            return;
        }
        if (genericaJornadaNew.getFecha() == null) {
            MovilidadUtil.addErrorMessage("Seleccionar una la fecha");
            return;
        }
        GenericaJornada ps = genJornadaEJB.validarEmplSinJornada(empl.getIdEmpleado(), genericaJornadaNew.getFecha());
        if (ps != null) {
            MovilidadUtil.addErrorMessage("Ya existe jornada para el empleado");
            return;
        }
        if (genericaJornadaNew.getObservaciones() != null) {
            if (genericaJornadaNew.getObservaciones().isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite la observación");
                return;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite la observación");
            return;
        }

        ParamAreaCargo pac = areaCargoEJB.getByCargoArea(
                empl.getIdEmpleadoCargo().getIdEmpleadoTipoCargo(),
                pau.getIdParamArea().getIdParamArea());
        if (pac == null) {
            MovilidadUtil.addErrorMessage("Colaborador no a cargo");
            return;
        }
        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        UtilJornada.cargarParametrosJar();

        //Consultar el tipo de jornada segun la hora inicio y hora fin de la jornada.
        GenericaJornadaTipo gjt = findGenericaJornadaTipo(true, genericaJornadaNew);
        genericaJornadaNew.setIdGenericaJornadaTipo(gjt);
        genericaJornadaNew.setIdEmpleado(empl);
        genericaJornadaNew.setIdParamArea(pau.getIdParamArea());

        genericaJornadaNew.setTimeOrigin(genericaJornadaNew.getRealTimeOrigin());
        genericaJornadaNew.setTimeDestiny(genericaJornadaNew.getRealTimeDestiny());
        genericaJornadaNew.setPrgModificada(1);
        genericaJornadaNew.setUsername(user.getUsername());
        genericaJornadaNew.setCreado(MovilidadUtil.fechaCompletaHoy());
        genericaJornadaNew.setUserGenera(user.getUsername());
        genericaJornadaNew.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
        genericaJornadaNew.setIdGenericaJornadaMotivo(new GenericaJornadaMotivo(i_genericaJornadaMotivo));
        genericaJornadaNew.setNominaBorrada(0);
        genericaJornadaNew.setSercon("DP_COLABORADOR");
        genericaJornadaNew.setLiquidado(0);
        String productionTime = timeProductionNew(genericaJornadaNew);
        genericaJornadaNew.setProductionTime(productionTime);
        genericaJornadaNew.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(genericaJornadaNew.getSercon(), genericaJornadaNew.getWorkTime())));
        System.out.println("WORKTIME " + genericaJornadaNew.getWorkTime());
        /**
         * Rigel GMO fase II
         */
        if (b_jornada_flexible) {
            if (MovilidadUtil.toSecs(productionTime) < MovilidadUtil.toSecs(UtilJornada.getMinHrsLaboralesDia())) {
                String msg = "No la jornada no cumple con el minimo de horas laborales, " + UtilJornada.getMinHrsLaboralesDia() + ". Encontrado, " + productionTime;
                if (UtilJornada.getMinHrsLaboralesDiaObj().getRestringe().equals(ConstantsUtil.ON_INT)) {
                    MovilidadUtil.addErrorMessage(msg);
                    return;
                } else {
                    MovilidadUtil.addAdvertenciaMessage(msg);
                }
            }
            if (MovilidadUtil.toSecs(productionTime) > MovilidadUtil.toSecs(UtilJornada.getMaxHrsLaboralesDia())) {
                String mssg = "Se excedió el máximo de horas laborales por día, " + UtilJornada.getMaxHrsLaboralesDia() + ". Encontrado "
                        + productionTime;
                if (UtilJornada.getMaxHrsLaboralesDiaObj().getRestringe().equals(ConstantsUtil.ON_INT)) {
                    MovilidadUtil.addErrorMessage(mssg);
                    return;
                } else {
                    MovilidadUtil.addAdvertenciaMessage(mssg);
                }
            }
            genJornadaEJB.create(genericaJornadaNew);
            genericaJornadaNew = genJornadaEJB.find(genericaJornadaNew.getIdGenericaJornada());
            if (validarHorasExtrasFlexible(genericaJornadaNew, false, false)) {
                genJornadaEJB.remove(genericaJornadaNew);
                return;
            }
            if (validarMaxHorasExtrasSmanalesFlexible(genericaJornadaNew, false, false)) {
                genJornadaEJB.remove(genericaJornadaNew);
                return;
            }

        } else {
            if (isExtra(genericaJornadaNew.getRealTimeOrigin(), genericaJornadaNew.getRealTimeDestiny(), HORAS_JORNADA)) {

                if (gjt != null) {
                    if (((MovilidadUtil.toSecs(genericaJornadaNew.getRealTimeDestiny())
                            - MovilidadUtil.toSecs(genericaJornadaNew.getRealTimeOrigin())
                            - MovilidadUtil.toSecs(HORAS_JORNADA)
                            - MovilidadUtil.toSecs(gjt.getDescanso()))
                            > MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) {
                        MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                                + empl.getIdentificacion()
                                + "-"
                                + empl.getNombres()
                                + " "
                                + empl.getApellidos());
                        return;

                    }
                } else {
                    if (((MovilidadUtil.toSecs(genericaJornadaNew.getRealTimeDestiny())
                            - MovilidadUtil.toSecs(genericaJornadaNew.getRealTimeOrigin())
                            - MovilidadUtil.toSecs(HORAS_JORNADA)) > MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) {
                        MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                                + empl.getIdentificacion()
                                + "-"
                                + empl.getNombres()
                                + " "
                                + empl.getApellidos());
                        return;
                    }
                }
            }
        }
        GenericaJornadaExtra genJorExtra = null;
        if (genJornadaParam != null && genJornadaParam.getRequerirOrdenTrabajo() == 1) {

            String horasExtras = horaExtras(genericaJornadaNew.getRealTimeOrigin(),
                    genericaJornadaNew.getRealTimeDestiny(),
                    workTimeFlexibleOrdinario(genericaJornadaNew.getSercon(), genericaJornadaNew.getWorkTime()),
                    gjt
            );
            if (MovilidadUtil.toSecs(horasExtras) > MovilidadUtil.toSecs("00:00:00")) {
                if (ordenTrabajo == null || (ordenTrabajo != null
                        && ordenTrabajo.replaceAll("\\s", "").isEmpty())) {
                    MovilidadUtil.addErrorMessage("Falta orden de trabajo.");
                    return;
                } else {
                    genericaJornadaNew.setOrdenTrabajo(ordenTrabajo);
                }
            } else {
                ordenTrabajo = null;
            }
        }

        if (!b_jornada_flexible) {
            if (b_maxHorasExtrasSemanaMes) {
                double horasExtras = MovilidadUtil.toSecs(horaExtras(genericaJornadaNew.getRealTimeOrigin(),
                        genericaJornadaNew.getRealTimeDestiny(),
                        workTimeFlexibleOrdinario(genericaJornadaNew.getSercon(), genericaJornadaNew.getWorkTime()),
                        gjt));
                if (horasExtras > 0) {
                    genJorExtra = calcularGenJorExtraAlCrearJornada(genericaJornadaNew, horasExtras);
                    if (genJorExtra == null) {
                        return;
                    }
                }
            }
        }

        if (genericaJornadaNew.getProductionTimeReal() == null) {
            genericaJornadaNew.setProductionTimeReal(genericaJornadaNew.getProductionTime());
        }
        boolean b_descanso = false;
        if (gjt != null) {
            if (MovilidadUtil.toSecs(gjt.getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                b_descanso = true;
                int productionTime_ = MovilidadUtil.toSecs(genericaJornadaNew.getProductionTime());
                if (productionTime_ > MovilidadUtil.toSecs(genericaJornadaNew.getIdGenericaJornadaTipo().getDescanso())) {
                    productionTime_ = productionTime_ - MovilidadUtil.toSecs(genericaJornadaNew.getIdGenericaJornadaTipo().getDescanso());
                    genericaJornadaNew.setProductionTime(MovilidadUtil.toTimeSec(productionTime_));
                    genericaJornadaNew.setProductionTimeReal(MovilidadUtil.toTimeSec(productionTime_));
                }
            }
        }
        int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornadaNew.getFecha(), 1), MovilidadUtil.fechaHoy(), false);

        if (b_jornada_flexible) {
            if (isCalcularAutorizar()) {
                GenericaJornadaLiqUtil param = cargarObjetoParaJar(genericaJornadaNew);
                if (genericaJornadaNew.getIdGenericaJornadaTipo() != null) {
                    param.setIdGenericaJornadaTipo(new GenericaJornadaTipoLiqUtil());
                    param.getIdGenericaJornadaTipo().setDescanso(genericaJornadaNew.getIdGenericaJornadaTipo().getDescanso());
                    param.getIdGenericaJornadaTipo().setTipoCalculo(genericaJornadaNew.getIdGenericaJornadaTipo().getTipoCalculo());
                }
                if (MovilidadUtil.isSunday(genericaJornadaNew.getFecha())) {

                    List<GenericaJornadaLiqUtil> preCagarHorasDominicales = getJornadaFlexible().preCagarHorasDominicales(genericaJornadaNew.getFecha(),
                            genericaJornadaNew.getIdParamArea().getIdParamArea(),
                            genericaJornadaNew.getIdEmpleado().getIdEmpleado(), param);
                    GenericaJornadaLiqUtil get = preCagarHorasDominicales.get(0);
                    setValueFromPrgSerconJar(get, genericaJornadaNew);
                    genJornadaEJB.edit(genericaJornadaNew);

                } else {
                    param.setFecha(genericaJornadaNew.getFecha());
                    param.setIdEmpleado(new EmpleadoLiqUtil(genericaJornadaNew.getIdEmpleado().getIdEmpleado()));
                    param.setIdParamArea(new ParamAreaLiqUtil(genericaJornadaNew.getIdParamArea().getIdParamArea()));
                    param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(genericaJornadaNew.getSercon(), genericaJornadaNew.getWorkTime())));
                    ErrorPrgSercon errorPrgSercon
                            = getJornadaFlexible().recalcularHrsExtrasSmnl(param);
                    if (errorPrgSercon.isStatus()) {
                        String msg = "Se excedió el máximo de horas extras diarias " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                                + MovilidadUtil.toTimeSec(errorPrgSercon.getHora());
                        ConfigControlJornada get = UtilJornada.getMaxHrsExtrasDiaObj();
                        if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                            MovilidadUtil.addErrorMessage(msg);
                            genJornadaEJB.remove(genericaJornadaNew);
                        } else {
                            MovilidadUtil.addAdvertenciaMessage(msg);
                        }
                    }
                    List<GenericaJornadaLiqUtil> list = errorPrgSercon.getListaGen().stream()
                            .filter(x -> !fechaDiferente(genericaJornadaNew.getFecha(), x.getFecha()))
                            .collect(Collectors.toList());
                    GenericaJornadaLiqUtil get = list.get(0);
                    setValueFromPrgSerconJar(get, genericaJornadaNew);
                    genJornadaEJB.edit(genericaJornadaNew);
                    list = errorPrgSercon.getListaGen().stream()
                            .filter(x -> fechaDiferente(genericaJornadaNew.getFecha(), x.getFecha()))
                            .collect(Collectors.toList());
                    genJornadaEJB.updatePrgSerconFromList(list, 0);

                }
            } else {
                genericaJornadaNew.setAutorizado(-1);
                genericaJornadaNew.setUserAutorizado("");
                genericaJornadaNew.setFechaAutoriza(null);
                genJornadaEJB.edit(genericaJornadaNew);
            }

        } else {
            if ((rol_user.equals("ROLE_PROFMTTO") || rol_user.equals("ROLE_LIQMTTO")
                    || rol_user.equals("ROLE_PROFGEN") || rol_user.equals("ROLE_LIQGEN"))) {
                if (genJornadaParam == null) {
                    autorizarCalcular(b_descanso);

                } else {
                    if (genJornadaParam.getCtrlAutorizarExtensionJornada() == 1
                            || (genJornadaParam.getCtrlAutorizarExtensionJornada() == 0 && validacionDia != 0)) {
                        autorizarCalcular(b_descanso);
                    } else {
                        noautorizarNoCalcular();
                    }
                }
            } else {
                if (rol_user.equals("ROLE_DIRGEN")) {
                    autorizarCalcular(b_descanso);
                } else {
                    noautorizarNoCalcular();
                    MovilidadUtil.addSuccessMessage("Se crea jornada exitosamente");
                    PrimeFaces.current().executeScript("PF('crear_turno_wv_dialog').hide()");
                    consultar();
                    return;
                }
            }

            listGenericaJornada.add(genericaJornadaNew);
            if (genJorExtra != null) {
                if (genJorExtra.getIdGenericaJornadaExtra() != null) {
                    genJornadaExtraEjb.edit(genJorExtra);
                } else {
                    genJornadaExtraEjb.create(genJorExtra);
                }
            }
        }
        MovilidadUtil.addSuccessMessage("Se crea jornada exitosamente");
        PrimeFaces.current().executeScript("PF('crear_turno_wv_dialog').hide()");
        consultar();
    }

    private boolean isCalcularAutorizar() {
        return ((rol_user.equals("ROLE_PROFMTTO") || rol_user.equals("ROLE_LIQMTTO")
                || rol_user.equals("ROLE_PROFGEN") || rol_user.equals("ROLE_LIQGEN"))
                || rol_user.equals("ROLE_DIRGEN"));
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(1);
        NotificacionTemplate template = notificacionTemplateEjb.find(ConstantsUtil.TEMPLATEGENERICAJORNADA);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    /**
     * Enviar notificación vía email indicando que una jornada a sido modificada
     * y necesita su autorización
     *
     * @param gj: jornada modificada.
     */
    public void notificar(GenericaJornada gj) {
        try {
            Map mapa = getMailParams();
            Map mailProperties = new HashMap();
            Empleado empl = gj.getIdEmpleado();
            GenericaJornadaMotivo motivo = jornadaMotivoEJB.find(gj.getIdGenericaJornadaMotivo().getIdGenericaJornadaMotivo());
            mailProperties.put("identificacion", empl.getIdentificacion());
            mailProperties.put("nombre", empl.getNombres() + " " + empl.getApellidos());
            mailProperties.put("fecha", Util.dateFormat(gj.getFecha()));
            mailProperties.put("hora_ini_prg", gj.getTimeOrigin());
            mailProperties.put("hora_fin_prg", gj.getTimeDestiny());
            mailProperties.put("hora_ini_real", gj.getRealTimeOrigin());
            mailProperties.put("hora_fin_real", gj.getRealTimeDestiny());
            mailProperties.put("user_name", user.getUsername());
            mailProperties.put("motivo", motivo.getDescripcion());
            mailProperties.put("observacion", gj.getObservaciones());
//            System.out.println("Notificar...");
            mailProperties.put("url", generarTokenUrl(gj));
            String asunto = "MODIFICACIÓN JORNADA";
            String destinatarios = genJornadaParam.getEmails();
            SendMails.sendEmail(mapa, mailProperties, asunto, "", destinatarios, "Notificaciones RIGEL", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generarTokenUrl(GenericaJornada gj) {

        GenericaJornadaToken gjt = new GenericaJornadaToken();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();
        String ctxPath = request.getContextPath();
        url = url.replaceFirst(uri, "");
        url = url + ctxPath;
        String token = TokenGeneratorUtil.nextToken();
        url = url + "/genericaJornadaToken/genericaJornadaToken.jsf?pin=" + token;
        gjt.setActivo(0);
        gjt.setCreado(MovilidadUtil.fechaCompletaHoy());
        gjt.setEstadoReg(0);
        gjt.setIdGenericaJornada(gj);
        gjt.setToken(token);
        gjt.setUsername(user.getUsername());
        genJornadaTokenEJB.create(gjt);
        return url;
    }

    public String timeProductionNew(GenericaJornada jornada) {
        //Convertir a enteros las horas todos los turnos
        int turnoI1 = MovilidadUtil.toSecs(jornada.getRealTimeOrigin());
        int turnoF1 = MovilidadUtil.toSecs(jornada.getRealTimeDestiny());
        int turnoI2 = MovilidadUtil.toSecs(jornada.getHiniTurno2());
        int turnoF2 = MovilidadUtil.toSecs(jornada.getHfinTurno2());
        int turnoI3 = MovilidadUtil.toSecs(jornada.getHiniTurno3());
        int turnoF3 = MovilidadUtil.toSecs(jornada.getHfinTurno3());

        //Calcular el tiempo de produccion de todos los turnos
        int produccionTurno1 = turnoF1 - turnoI1;
        int produccionTurno2 = turnoF2 - turnoI2;
        int produccionTurno3 = turnoF3 - turnoI3;
        //Calcular el tiempo total de produccion
        int produccionTotal = produccionTurno1 + produccionTurno2 + produccionTurno3;

        //Retornar el tiempo todal de produccion en String
        if (produccionTotal == 0) {
            return "00:00:00";
        }
        return MovilidadUtil.toTimeSec(produccionTotal);
    }

    public String timeProduction(GenericaJornada gj) {
        int turnoI1 = 0;
        int turnoF1 = 0;
        //Convertir a enteros las horas todos los turnos
        if (gj.getRealTimeOrigin() != null) {
            turnoI1 = MovilidadUtil.toSecs(gj.getRealTimeOrigin());
        } else {
            turnoI1 = MovilidadUtil.toSecs(gj.getTimeOrigin());
        }
        if (gj.getRealTimeDestiny() != null) {
            turnoF1 = MovilidadUtil.toSecs(gj.getRealTimeDestiny());
        } else {
            turnoF1 = MovilidadUtil.toSecs(gj.getTimeDestiny());
        }
        int turnoI2 = MovilidadUtil.toSecs(gj.getHiniTurno2());
        int turnoF2 = MovilidadUtil.toSecs(gj.getHfinTurno2());
        int turnoI3 = MovilidadUtil.toSecs(gj.getHiniTurno3());
        int turnoF3 = MovilidadUtil.toSecs(gj.getHfinTurno3());

        //Calcular el tiempo de produccion de todos los turnos
        int produccionTurno1 = turnoF1 - turnoI1;
        int produccionTurno2 = turnoF2 - turnoI2;
        int produccionTurno3 = turnoF3 - turnoI3;
        //Calcular el tiempo total de produccion
        int produccionReal = produccionTurno1 + produccionTurno2 + produccionTurno3;

        int productionTimeProg = MovilidadUtil.toSecs(gj.getProductionTime());
        int productionTimeRealTotal;

        if (produccionReal < productionTimeProg) {
            int diferencia = productionTimeProg - produccionReal;
            productionTimeRealTotal = productionTimeProg - diferencia;
        } else {
            int diferencia = produccionReal - productionTimeProg;
            productionTimeRealTotal = productionTimeProg + diferencia;
        }

        //Retornar el tiempo todal de produccion en String
        if (productionTimeRealTotal == 0) {
            return "00:00:00";
        }
        return MovilidadUtil.toTimeSec(productionTimeRealTotal);
    }

    boolean validarHoras(String hora1, String hora2) {
        return MovilidadUtil.toSecs(hora1) > MovilidadUtil.toSecs(hora2);
    }

    public void emplFindByCodigoT() throws Exception {
        if (pau == null) {
            MovilidadUtil.addErrorMessage("No tiene area a cargo");
            return;
        }
        if (empleadoLabel == null) {
            return;
        }
        if ("".equals(empleadoLabel) || (empleadoLabel.isEmpty())) {
            MovilidadUtil.addAdvertenciaMessage("Digite la identificación del empleado");
            empl = null;
            nombreEmpleado = "";
            return;
        }
        empl = emplEJB.findCampo("identificacion", empleadoLabel, 0);
        if (empl == null) {
            MovilidadUtil.addErrorMessage("No existe operador con la identificación digitada");
            empl = null;
            nombreEmpleado = "";
            return;
        }
        GenericaJornada ps = genJornadaEJB.validarEmplSinJornada(empl.getIdEmpleado(), genericaJornadaNew.getFecha());
        if (ps != null) {
            MovilidadUtil.addErrorMessage("Empleado ya existe con jornada");
            empleadoLabel = "";
            nombreEmpleado = "";
            empl = null;
            return;
        }
        ParamAreaCargo pac = areaCargoEJB.getByCargoArea(empl.getIdEmpleadoCargo().getIdEmpleadoTipoCargo(), pau.getIdParamArea().getIdParamArea());
        if (pac == null) {
            MovilidadUtil.addErrorMessage("Empleado no a cargo");
            empleadoLabel = "";
            nombreEmpleado = "";
            empl = null;
            return;
        }
        nombreEmpleado = empl.getIdentificacion() + " - " + empl.getNombres() + " " + empl.getApellidos();
        MovilidadUtil.addSuccessMessage("Empleado cargado");

    }

    public void emplFindByCodigoTBM() throws Exception {
        if (pau == null) {
            MovilidadUtil.addErrorMessage("No tiene area asignada");
            return;
        }
        if (empleadoLabel == null) {
            return;
        }
        if ("".equals(empleadoLabel) || empleadoLabel.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("Digite el identificación del empleado");
            empl = null;
            nombreEmpleado = "";
            return;
        }
        empl = emplEJB.findCampo("identificacion", empleadoLabel, 0);
        if (empl == null) {
            MovilidadUtil.addErrorMessage("No existe empleado con la identificaion digitada");
            empl = null;
            nombreEmpleado = "";
            return;
        }
        ParamAreaCargo pac = areaCargoEJB.getByCargoArea(empl.getIdEmpleadoCargo().getIdEmpleadoTipoCargo(), pau.getIdParamArea().getIdParamArea());
        if (pac == null) {
            MovilidadUtil.addErrorMessage("Empleado no a cargo: " + empl.getIdentificacion() + " " + empl.getNombres() + " " + empl.getApellidos());
            empl = null;
            nombreEmpleado = "";
            return;
        }
        nombreEmpleado = empl.getIdentificacion() + " - " + empl.getNombres() + " " + empl.getApellidos();
        MovilidadUtil.addSuccessMessage("Empleado cargado");
    }

    void programacionModificada() {
//        if (genericaJornada.getTimeOrigin().equals(genericaJornada.getRealTimeOrigin())
//                && genericaJornada.getTimeDestiny().equals(genericaJornada.getRealTimeDestiny())) {
//            genericaJornada.setPrgModificada(0);
//        } else {
        genericaJornada.setPrgModificada(1);
        genericaJornada.setAutorizado(-1);
        genericaJornada.setUserAutorizado("");
        genericaJornada.setFechaAutoriza(null);
//        }
    }

    public void prepDownloadLocal(String path) throws Exception {
        switch (path) {
            case "PLANTILLA_CARGA_TURNOS_GEN":
            case "PLANTILLA_CARGA_JORNADAS":
            case "PLANTILLA_CARGA_NOVEDADES":
                path = getProperty(path);
                viewDMB.setDownload(MovilidadUtil.prepDownload(path));
                break;
            default:
                MovilidadUtil.addAdvertenciaMessage("Plantilla no válida");           
        }
    }

    boolean validarHoras(GenericaJornada gj) {
        return MovilidadUtil.toSecs(gj.getRealTimeOrigin()) > MovilidadUtil.toSecs(gj.getRealTimeDestiny());
    }

    boolean validarMarcaciones(GenericaJornada gj) {
        return MovilidadUtil.toSecs(gj.getRealTimeOrigin()) > MovilidadUtil.toSecs(gj.getRealTimeDestiny());
    }

    public boolean validarMaxDosHoraExtras(GenericaJornada gj, GenericaJornadaTipo gjt) {
        if (isExtra(gj.getRealTimeOrigin(), gj.getRealTimeDestiny(), HORAS_JORNADA)) {
            if (gjt != null) {
                if (((MovilidadUtil.toSecs(gj.getRealTimeDestiny())
                        - MovilidadUtil.toSecs(gj.getRealTimeOrigin())
                        - MovilidadUtil.toSecs(HORAS_JORNADA)
                        - MovilidadUtil.toSecs(gjt.getDescanso()))
                        > MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) {
                    return true;

                }
            } else {
                if (((MovilidadUtil.toSecs(gj.getRealTimeDestiny())
                        - MovilidadUtil.toSecs(gj.getRealTimeOrigin())
                        - MovilidadUtil.toSecs(HORAS_JORNADA)) > MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void generarHorasReales(boolean loadFile) {
        try {
            if (genericaJornada == null) {
                return;
            }
            if (validarHoras(genericaJornada)) {
                consultar();
                MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio");
                return;
            }
            if (habilitarTurnos) {//es necesario hacer cambio de turno en el biometrico
                GenericaJornadaTipo GJTipo = jornadaTipoEJB.find(i_genericaJornadaTipo);
                Empleado tEmpl = genericaJornada.getIdEmpleado();
                postBioAccLevel(tEmpl.getIdentificacion(), obtenerIdTurno(GJTipo.getDescripcion()), "031E7B21BDC5CD042BC6EB9D75CBC7CCAF4963F6FFB7A8FD5C65EC69240FF6E7");
            }
            GenericaJornadaTipo gjt = findGenericaJornadaTipo(true, genericaJornada);
            /**
             * Cargar parámetros para cálculo de jornada al jar encargado de tal
             * tarea.
             */
            UtilJornada.cargarParametrosJar();
            if (b_jornada_flexible) {
                if (validarTrasnochoMadrugada(genericaJornada, loadFile)) {
                    return;
                }

                if (validarHorasExtrasFlexible(genericaJornada, true, loadFile)) {
                    return;
                }
                if (validarMaxHorasExtrasSmanalesFlexible(genericaJornada, true, loadFile)) {
                    return;
                }
            } else {
                if (validarMaxDosHoraExtras(genericaJornada, gjt)) {
                    MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                            + genericaJornada.getIdEmpleado().getIdentificacion()
                            + "-"
                            + genericaJornada.getIdEmpleado().getNombres()
                            + " "
                            + genericaJornada.getIdEmpleado().getApellidos());
                    return;
                }
            }

            if (genJornadaParam != null && genJornadaParam.getRequerirOrdenTrabajo() == 1) {
                String horasExtras = horaExtras(genericaJornada.getRealTimeOrigin(),
                        genericaJornada.getRealTimeDestiny(),
                        workTimeFlexibleOrdinario(genericaJornada.getSercon(), genericaJornada.getWorkTime()),
                        gjt);
                int totalExtrasViejas = horasExtrasByJornada(genericaJornada);
                if (MovilidadUtil.toSecs(horasExtras) > totalExtrasViejas) {
                    if (ordenTrabajo == null || (ordenTrabajo != null
                            && ordenTrabajo.replaceAll("\\s", "").isEmpty())) {
                        MovilidadUtil.addErrorMessage("Falta orden de trabajo.");
                        return;
                    } else {
                        genericaJornada.setOrdenTrabajo(ordenTrabajo);
                    }
                } else {
                    if (genericaJornada.getOrdenTrabajo() == null || (genericaJornada.getOrdenTrabajo() != null
                            && genericaJornada.getOrdenTrabajo().replaceAll("\\s", "").isEmpty())) {
                        ordenTrabajo = null;
                    } else {
                        genericaJornada.setOrdenTrabajo(ordenTrabajo);
                    }
                }
            }
            GenericaJornadaExtra genJorExtra = null;
            if (b_jornada_flexible) {

            } else {
                if (b_maxHorasExtrasSemanaMes) {
                    horasExtrasFin = MovilidadUtil.toSecs(
                            horaExtras(genericaJornada.getRealTimeOrigin(),
                                    genericaJornada.getRealTimeDestiny(),
                                    workTimeFlexibleOrdinario(genericaJornada.getSercon(), genericaJornada.getWorkTime()),
                                    gjt
                            ));
                    genJorExtra = calcularGenericaJornadaExtra(genericaJornada);
                    if (genJorExtra == null) {
                        return;
                    }
                }
            }
            if (!isCalcularAutorizar()) {
                System.out.println("XXXXXXXXXXXXXXXXXXX");
                programacionModificada();
                noAutorizarNocalcularAlAjustar();
                return;
            }
            calcularMasivoBean.cargarMapParamFeriado();
            /**
             * Cargar parámetros para cálculo de jornada al jar encargado de tal
             * tarea.
             */
            UtilJornada.cargarParametrosJar();
            if (b_jornada_flexible) {

                genericaJornada.setUserGenera(user.getUsername());
                genericaJornada.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
                if (!b_novFromFile) {
                    genericaJornada.setIdGenericaJornadaMotivo(new GenericaJornadaMotivo(i_genericaJornadaMotivo));
                }
                String productionTime = timeProduction(genericaJornada);
                genericaJornada.setProductionTimeReal(productionTime);
                genericaJornada.setUserAutorizado(user.getUsername());
                genericaJornada.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
                // (1) uno para autorizar, (0) cero para no autorizar
                genericaJornada.setAutorizado(1);

                GenericaJornadaLiqUtil param = cargarObjetoParaJar(genericaJornada);
                if (genericaJornada.getIdGenericaJornadaTipo() != null) {
                    param.setIdGenericaJornadaTipo(new GenericaJornadaTipoLiqUtil());
                    param.getIdGenericaJornadaTipo().setDescanso(genericaJornada.getIdGenericaJornadaTipo().getDescanso());
                    param.getIdGenericaJornadaTipo().setTipoCalculo(genericaJornada.getIdGenericaJornadaTipo().getTipoCalculo());
                }
                if (MovilidadUtil.isSunday(genericaJornada.getFecha())) {
                    List<GenericaJornadaLiqUtil> preCagarHorasDominicales = getJornadaFlexible().preCagarHorasDominicales(genericaJornada.getFecha(),
                            genericaJornada.getIdParamArea().getIdParamArea(),
                            genericaJornada.getIdEmpleado().getIdEmpleado(), param);
                    GenericaJornadaLiqUtil get = preCagarHorasDominicales.get(0);
                    setValueFromPrgSerconJar(get, genericaJornada);
                    genJornadaEJB.edit(genericaJornada);

                } else {
                    param.setFecha(genericaJornada.getFecha());
                    param.setIdEmpleado(new EmpleadoLiqUtil(genericaJornada.getIdEmpleado().getIdEmpleado()));
                    param.setIdParamArea(new ParamAreaLiqUtil(genericaJornada.getIdParamArea().getIdParamArea()));
                    param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(genericaJornada.getSercon(), genericaJornada.getWorkTime())));
                    ErrorPrgSercon errorPrgSercon
                            = getJornadaFlexible().recalcularHrsExtrasSmnl(param);
                    if (errorPrgSercon.isStatus()) {
                        String msg = "Se excedió el máximo de horas extras diarias " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                                + MovilidadUtil.toTimeSec(errorPrgSercon.getHora());
                        ConfigControlJornada get = UtilJornada.getMaxHrsExtrasDiaObj();
                        if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                            MovilidadUtil.addErrorMessage(msg);
                        } else {
                            MovilidadUtil.addAdvertenciaMessage(msg);
                        }
                        return;
                    }
                    List<GenericaJornadaLiqUtil> list = errorPrgSercon.getListaGen().stream()
                            .filter(x -> !fechaDiferente(genericaJornada.getFecha(), x.getFecha()))
                            .collect(Collectors.toList());
                    GenericaJornadaLiqUtil get = list.get(0);
                    setValueFromPrgSerconJar(get, genericaJornada);
                    genJornadaEJB.edit(genericaJornada);
                    list = errorPrgSercon.getListaGen().stream()
                            .filter(x -> fechaDiferente(genericaJornada.getFecha(), x.getFecha()))
                            .collect(Collectors.toList());
                    genJornadaEJB.updatePrgSerconFromList(list, 0);

                }
                consultar();
                MovilidadUtil.addSuccessMessage(loadFile ? "Ajuste de jornada exitoso para " + genericaJornada.getIdEmpleado().getNombresApellidos()+ ", Fecha : "+ Util.dateFormat(genericaJornada.getFecha()) : 
                        "Horas reales registradas correctamente");
                genericaJornada = null;
                i_genericaJornadaMotivo = 0;
                MovilidadUtil.hideModal("genCtrlJornadaMBDialog");
            } else {
                if (rol_user.equals("ROLE_PROFGEN") || rol_user.equals("ROLE_LIQGEN")
                        || rol_user.equals("ROLE_PROFMTTO") || rol_user.equals("ROLE_LIQMTTO")) {
                    programacionModificada();
                    int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);

                    if (genJornadaParam == null) {
                        genericaJornada.setIdGenericaJornadaTipo(gjt);
                        autorizarCalcularAlAjustar(genericaJornada, true, loadFile);
                    } else {
                        if (genJornadaParam.getCtrlAutorizarExtensionJornada() == 1
                                || (genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                                && validacionDia != 0)) {
                            genericaJornada.setIdGenericaJornadaTipo(gjt);
                            autorizarCalcularAlAjustar(genericaJornada, true, loadFile);
                        } else {
                            noAutorizarNocalcularAlAjustar();
                            genJorExtra = null;
                        }
                    }
                } else if (rol_user.equals("ROLE_DIRGEN")) {
                    genericaJornada.setIdGenericaJornadaTipo(gjt);
                    autorizarCalcularAlAjustar(genericaJornada, true, loadFile);
                }
                if (genJorExtra != null) {
                    if (genJorExtra.getIdGenericaJornadaExtra() == null) {
                        genJornadaExtraEjb.create(genJorExtra);
                    } else {
                        genJornadaExtraEjb.edit(genJorExtra);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en Generar Horas Reales");
        }
    }

    private int isRolAutoriza(UserExtended userSession) throws ParseException {
        int flag = 0;
        for (GrantedAuthority auth : userSession.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_LIQMTTO")) {
                flag = 1;
                break;
            }
        }
        return flag;
    }

    private boolean validator(String pin, String levelds, String access_token) {
        boolean flag = false;
        if (Objects.nonNull(pin) && !pin.isEmpty()) {
            if (Objects.nonNull(levelds) && !levelds.isEmpty()) {
                if (Objects.nonNull(access_token) && !access_token.isEmpty()) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    private String postBioAccLevel(String pin, String levelds, String access_token) throws IOException, Exception {
        String mensaje = "Valores nulos o vacios";
        if (validator(pin, levelds, access_token)) {
            try {
                String URL = "https://10.0.3.157:8098/api/accLevel/syncPerson?" + "pin=" + pin + "&levelIds=" + levelds + "&access_token=" + access_token;
                Unirest.config().verifySsl(false);
                kong.unirest.HttpResponse<String> response = Unirest.post(URL)
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Cookie", "SESSION=ZTg5NTM0ZGUtNTMzZC00N2Q5LTk5YjMtMGM0OWFjNjcyYmYy; SESSION=ZTg5NTM0ZGUtNTMzZC00N2Q5LTk5YjMtMGM0OWFjNjcyYmYy")
                        .asString();
                Unirest.shutDown();

                if (response.getStatus() == 200) {
                    mensaje = "NO es posible setear horario al ID " + pin;
                }
                if (response.getStatus() == 400) {
                    mensaje = "Se asigna horario al ID " + pin;
                } else {
                    mensaje = "Error 500";
                }
            } catch (UnirestException e) {
                mensaje = "Error en Unirest";
            }
        }
        return mensaje;
    }

    private String obtenerIdTurnoPorTipoJornada(String jornada) {
        String id = "";
        switch (jornada) {
            case "DESCANSO":
                id = "8a80831d868513080188fddc8bf327e2";
                break;
            case "T1":
                id = "8a80831d8a2e3cad018a6b47b5304bd2";
                break;
            case "T2":
                id = "8a80831d8a2e3cad018a6b48203f4bd6";
                break;
            case "T3":
                id = "8a80831d8a2e3cad018a6b4975644bda";
                break;
            case "T4":
                id = "8a80831d8a2e3cad018a6b4699b64bce";
                break;
            case "T5":
                id = "8a80831d8a2e3cad018a6b4b573d4bea";
                break;
            case "T7":
                id = "8a80831d8a2e3cad018a6b4be4f74bee";
                break;
            case "T1R":
                id = "8a80831d8a2e3cad018a6b49e5624bde";
                break;
            case "T2R":
                id = "8a80831d8a2e3cad018a6b4a83424be2";
                break;
            case "T3R":
                id = "8a80831d8a2e3cad018a6b4ae7fe4be6";
                break;
            default:
                id = "8a80831d81ef09170181ef0a790903c9";//super usuario
                break;
        }
        return id;
    }

    private String obtenerIdTurno(String jornada) {
        String id = "";
        switch (jornada) {
            case "DESCANSO":
                id = "8a80831d868513080188fddc8bf327e2";
                break;
            case "T1":
                id = "8a80831d8a2e3cad018a6b47b5304bd2";
                break;
            case "T2":
                id = "8a80831d8a2e3cad018a6b48203f4bd6";
                break;
            case "T3":
                id = "8a80831d8a2e3cad018a6b4975644bda";
                break;
            case "T4":
                id = "8a80831d8a2e3cad018a6b4699b64bce";
                break;
            case "T5":
                id = "8a80831d8a2e3cad018a6b4b573d4bea";
                break;
            case "T7":
                id = "8a80831d8a2e3cad018a6b4be4f74bee";
                break;
            case "T1R":
                id = "8a80831d8a2e3cad018a6b49e5624bde";
                break;
            case "T2R":
                id = "8a80831d8a2e3cad018a6b4a83424be2";
                break;
            case "T3R":
                id = "8a80831d8a2e3cad018a6b4ae7fe4be6";
                break;
            default:
                id = "8a80831d81ef09170181ef0a790903c9";//super usuario
                break;
        }
        return id;
    }

    public void gestionarMarcacion() {
        try {
            if (Objects.nonNull(genericaJornada)) {
                if (!validarMarcaciones(genericaJornada)) {

                    if (b_autorizaMarcaciones) {//si el usuario en sesión es quien puede autorizar las marcaciones se debe hacer todo el proceso de una vez (Gestionar y Autorizar)
                        genericaJornada.setFechaAuth(MovilidadUtil.fechaCompletaHoy());
                        genericaJornada.setMarcacionAutorizada(1);
                        genericaJornada.setUsernameAuth(user.getUsername());
                        genericaJornada.setUserAutorizado(user.getUsername());
                        genericaJornada.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
                        genericaJornada.setAutorizado(1);
                    } else {
                        genericaJornada.setAutorizado(-1);
                    }
                    genericaJornada.setUsernameGestion(user.getUsername());
                    genericaJornada.setFechaGestion(MovilidadUtil.fechaCompletaHoy());
                    genericaJornada.setUserGenera(user.getUsername());
                    genericaJornada.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
                    genericaJornada.setIdGenericaJornadaMotivo(new GenericaJornadaMotivo(i_genericaJornadaMotivo));
                    String productionTime = timeProduction(genericaJornada);
                    genericaJornada.setProductionTimeReal(productionTime);
                    genericaJornada.setModificado(MovilidadUtil.fechaCompletaHoy());
                    genericaJornada.setUsername(user.getUsername());
                    genericaJornada.setMarcacionGestionada(1);//Marcación Gestionada
                    //se habilita el turno si la gestión es para un estado de abandono
                    if (genericaJornada.getEstadoMarcacion() == 2) {
                        //invocar método para cambiar turno
                        Empleado tEmpl = genericaJornada.getIdEmpleado();
                        postBioAccLevel(tEmpl.getIdentificacion(), obtenerIdTurno(genericaJornada.getSercon()), "031E7B21BDC5CD042BC6EB9D75CBC7CCAF4963F6FFB7A8FD5C65EC69240FF6E7");
                    }
                    generarHorasReales(false);
//                    genJornadaEJB.edit(genericaJornada);
//                    PrimeFaces.current().executeScript("PF('gestionMarcacionMBDialog').clearFilters();");
//                    PrimeFaces.current().executeScript("PF('formgestionarmarcacion').hide();");
                    cargarNovedadesBIO();
                } else {
                    MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en Generar Horas Reales");
        }
    }

    public void noAutorizarNocalcularAlAjustar() {
        genericaJornada.setUserGenera(user.getUsername());
        genericaJornada.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
        if (!b_novFromFile) {
            genericaJornada.setIdGenericaJornadaMotivo(new GenericaJornadaMotivo(i_genericaJornadaMotivo));
        }

        String productionTime = timeProduction(genericaJornada);
        genericaJornada.setProductionTimeReal(productionTime);
        genJornadaEJB.edit(genericaJornada);
        if (!b_jornada_flexible) {
            if (genJornadaParam != null && genJornadaParam.getNotifica() == 1) {
                if (genJornadaParam.getEmails() != null) {
                    notificar(genericaJornada);
                }
            }
        }
        consultar();
        MovilidadUtil.addSuccessMessage("Horas reales registradas correctamente");
        genericaJornada = null;
        i_genericaJornadaMotivo = 0;
        PrimeFaces.current().executeScript("PF('genCtrlJornadaMBDialog').hide();");
        return;
    }

    /**
     * @param genJornada objeto que contiene la información de la jornada a evaluar
     * @param NotRangeFechas 
     * @param loadFile parametro que permite identificar si el proceso se llama 
     *      desde una carga masiva, en tal caso se debe dar un detalle del error.
     * @throws java.text.ParseException
     */
    public void autorizarCalcularAlAjustar(GenericaJornada genJornada, boolean NotRangeFechas, boolean loadFile) throws ParseException {

        if (validarHoras(genJornada)) {
            MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio");
            return;
        }
        genJornada.setUserGenera(user.getUsername());
        genJornada.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
        genJornada.setIdGenericaJornadaMotivo(new GenericaJornadaMotivo(i_genericaJornadaMotivo));
        String productionTime = timeProduction(genJornada);
        genJornada.setProductionTimeReal(productionTime);
        genJornada.setUserAutorizado(user.getUsername());
        genJornada.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        // (1) uno para autorizar, (0) cero para no autorizar
        genJornada.setAutorizado(1);
        boolean b_descanso = false;
        if (genJornada.getIdGenericaJornadaTipo() != null) {
            if (MovilidadUtil.toSecs(genJornada.getIdGenericaJornadaTipo().getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                b_descanso = true;
                int productionTime_ = MovilidadUtil.toSecs(genJornada.getProductionTimeReal());
                if (productionTime_ > MovilidadUtil.toSecs(genJornada.getIdGenericaJornadaTipo().getDescanso())) {
                    productionTime_ = productionTime_ - MovilidadUtil.toSecs(genJornada.getIdGenericaJornadaTipo().getDescanso());
                    genJornada.setProductionTimeReal(MovilidadUtil.toTimeSec(productionTime_));
                }
                b_descanso = true;
            }
        }

        boolean b_jornadaExtra = false;

        if (isExtra(genJornada.getRealTimeOrigin(), genJornada.getRealTimeDestiny(), HORAS_JORNADA)) {

            if (genJornada.getIdGenericaJornadaTipo() != null) {
                if (((MovilidadUtil.toSecs(genJornada.getRealTimeDestiny())
                        - MovilidadUtil.toSecs(genJornada.getRealTimeOrigin())
                        - MovilidadUtil.toSecs(HORAS_JORNADA)
                        - MovilidadUtil.toSecs(genJornada.getIdGenericaJornadaTipo().getDescanso()))
                        <= MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) {
                    b_jornadaExtra = true;
                }
            } else {
                if (((MovilidadUtil.toSecs(genJornada.getRealTimeDestiny())
                        - MovilidadUtil.toSecs(genJornada.getRealTimeOrigin())
                        - MovilidadUtil.toSecs(HORAS_JORNADA)) <= MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) {
                    b_jornadaExtra = true;
                }
            }
        }
        boolean flagTipoCalculo = genJornada.getIdGenericaJornadaTipo() != null && genJornada.getIdGenericaJornadaTipo().getTipoCalculo() == 1;
        if (flagTipoCalculo) {
            System.out.println("por partes");
            calcularMasivoBean.cargarCalcularDatoPorPartes(genJornada);
        } else {
            calcularMasivoBean.cargarCalcularDato(genJornada, 2);
        }
        if (calcularMasivoBean.validarHorasPositivas(genJornada)) {
            MovilidadUtil.addErrorMessage("Error al calcular jornada (validar horas positivas)");
            return;
        }
        if (!flagTipoCalculo && (b_descanso && b_jornadaExtra)) {
            calcularExtrasJornadaOrdinaria(genJornada);
        }

        if (!rol_user.equals(
                "ROLE_DIRGEN")) {
            if (aprobarHorasFeriadas(genJornada)) {
                int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
                if (genJornadaParam != null
                        && genJornadaParam.getCtrlAprobarExtrasFeriadas() == 0
                        && validacionDia == 0) {
                    genJornada.setAutorizado(-1);
                    if (genJornadaParam.getNotifica() == 1) {
                        if (genJornadaParam.getEmails() != null) {
                            notificar(genJornada);
                        }
                    }
                }
            }
        }

        genJornada.setDominicalCompDiurnas(
                null);
        genJornada.setDominicalCompNocturnas(
                null);
        genJornada.setDominicalCompDiurnaExtra(
                null);
        genJornada.setDominicalCompNocturnaExtra(
                null);
        genJornadaEJB.edit(genJornada);

        calcularMasivoBean.recalcularJornada(genJornada);
        if (NotRangeFechas) {
            consultar();
            MovilidadUtil.addSuccessMessage(loadFile ? "Ajuste de jornada exitoso para " + genericaJornada.getIdEmpleado().getNombresApellidos()+ ", Fecha : "+ Util.dateFormat(genericaJornada.getFecha()) :
                    "Horas reales registradas correctamente");
            genJornada = null;
            i_genericaJornadaMotivo = 0;
            PrimeFaces.current().executeScript("PF('genCtrlJornadaMBDialog').hide();");
        }
    }

    public void guardarBMAndConsultar() throws ParseException {
        guardarBM();
        consultar();
    }

    @Transactional
    public void guardarBM() throws ParseException {
        if (empl == null) {
            MovilidadUtil.addErrorMessage("No se ha cargado el operador");
            return;
        }
        if (fechaDesdeBM == null || fechaHastaBM == null) {
            MovilidadUtil.addErrorMessage("Cargar fechas");
            return;
        }
        if (!rol_user.equals("ROLE_DIRGEN")) {
            int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(fechaDesdeBM, 1), MovilidadUtil.fechaHoy(), false);

            if (genJornadaParam != null
                    && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                    && validacionDia == 0) {
                MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                return;
            }
        }
        if (i_genericaJornadaMotivo == 0) {
            MovilidadUtil.addErrorMessage("Seleccionar el motivo");
            return;
        }
        long valor = genJornadaEJB.validarPeriodoLiquidadoEmpleado(calcularMasivoBean.getFechaDesde(), calcularMasivoBean.getFechaHasta(), empl.getIdEmpleado());
        if (valor < 0) {
            MovilidadUtil.addErrorMessage("El empleado ya tiene jornadar liquidadas para este periodo de tiempo");
            return;
        }
        genJornadaEJB.borradoMasivo(empl.getIdEmpleado(), i_genericaJornadaMotivo, fechaDesdeBM, fechaHastaBM, observacionesBM, user.getUsername(), 1);
        List<GenericaJornada> list = genJornadaEJB.getJornadasByDateAndEmpleado(fechaDesdeBM, fechaHastaBM, empl.getIdEmpleado());
        for (GenericaJornada g : list) {
            calcularMasivoBean.recalcularJornada(g);
            if (b_maxHorasExtrasSemanaMes) {
                double horasExtras = horasExtrasByJornada(g);
                if (horasExtras > 0) {
                    calcularGenJorExtraBM(g, horasExtras);
                }
            }
        }
        MovilidadUtil.addSuccessMessage("Nomina borrada para el periodo de tiempo");
        PrimeFaces.current().executeScript("PF('borrar_wv_dialog').hide()");
    }

    public void calcularGenJorExtraBM(GenericaJornada gj, double horasExtras) {
        GenericaJornadaExtra genJornadaExtra = genJornadaExtraEjb.getByEmpleadoAndFecha(gj.getIdEmpleado().getIdEmpleado(), gj.getFecha());
        horasExtras = horasExtras / 3600;
        if (genJornadaExtra == null) {
            Calendar primerDiaMes = Calendar.getInstance();
            Calendar ultimoDiaMes = Calendar.getInstance();
            primerDiaMes.setTime(gj.getFecha());
            ultimoDiaMes.setTime(gj.getFecha());

            primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
            ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));

            genJornadaExtra = new GenericaJornadaExtra();
            genJornadaExtra.setIdEmpleado(gj.getIdEmpleado());
            genJornadaExtra.setEstadoReg(0);
            genJornadaExtra.setUsername(user.getUsername());
            genJornadaExtra.setDesde(primerDiaMes.getTime());
            genJornadaExtra.setHasta(ultimoDiaMes.getTime());
            genJornadaExtra.setCreado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setModificado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setIdParamArea(pau.getIdParamArea());
            genJornadaExtra.setSemana1(0);
            genJornadaExtra.setSemana2(0);
            genJornadaExtra.setSemana3(0);
            genJornadaExtra.setSemana4(0);
            genJornadaExtra.setSemana5(0);
            genJornadaExtra.setTotal(0);
            genJornadaExtraEjb.create(genJornadaExtra);
        } else {
            Calendar fecha = Calendar.getInstance();
            fecha.setTime(gj.getFecha());
            int numeroSemana = fecha.get(Calendar.WEEK_OF_MONTH);
            if (numeroSemana == 1) {
                genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 2) {
                genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 3) {
                genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 4) {
                genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            if (numeroSemana == 5) {
                genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() - horasExtras);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - horasExtras);
            }
            genJornadaExtraEjb.edit(genJornadaExtra);
        }
    }

    public boolean aprobarHorasFeriadas(GenericaJornada gj) {
        int totalHorasExtrasNuevas = MovilidadUtil.toSecs(gj.getFestivoExtraDiurno())
                + MovilidadUtil.toSecs(gj.getFestivoExtraNocturno());
        return totalHorasExtrasNuevas > 0;

    }

    private String validateHorasExtras(GenericaJornada jornadaAuth, GenericaJornadaTipo gjt, GenericaJornadaExtra genJorExtra) {
        String respuestaValidate = "";
        if (isExtra(jornadaAuth.getRealTimeOrigin(), jornadaAuth.getRealTimeDestiny(), HORAS_JORNADA)) {

            if (jornadaAuth.getIdGenericaJornadaTipo() != null) {
                if (!((MovilidadUtil.toSecs(jornadaAuth.getRealTimeDestiny())
                        - MovilidadUtil.toSecs(jornadaAuth.getRealTimeOrigin())
                        - MovilidadUtil.toSecs(HORAS_JORNADA)
                        - MovilidadUtil.toSecs(jornadaAuth.getIdGenericaJornadaTipo().getDescanso()))
                        <= MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) {
                    MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                            + jornadaAuth.getIdEmpleado().getIdentificacion()
                            + "-"
                            + jornadaAuth.getIdEmpleado().getNombres()
                            + " "
                            + jornadaAuth.getIdEmpleado().getApellidos());
                    return "return";

                } else {
                    respuestaValidate = "extra";

                }
            } else {
                if (!((MovilidadUtil.toSecs(jornadaAuth.getRealTimeDestiny())
                        - MovilidadUtil.toSecs(jornadaAuth.getRealTimeOrigin())
                        - MovilidadUtil.toSecs(HORAS_JORNADA)) <= MovilidadUtil.toSecs(MAX_HORAS_EXTRAS_DIARIAS))) {
                    MovilidadUtil.addErrorMessage("Solo se permiten hasta 2 horas extras diarias Empleado: "
                            + jornadaAuth.getIdEmpleado().getIdentificacion()
                            + "-"
                            + jornadaAuth.getIdEmpleado().getNombres()
                            + " "
                            + jornadaAuth.getIdEmpleado().getApellidos());
                    return "return";
                } else {
                    respuestaValidate = "extra";
                }
            }
        }

        if (b_maxHorasExtrasSemanaMes) {
            horasExtrasInicio = horasExtrasByJornada(jornadaAuth);

            horasExtrasFin = MovilidadUtil.toSecs(horaExtras(jornadaAuth.getRealTimeOrigin(),
                    jornadaAuth.getRealTimeDestiny(),
                    workTimeFlexibleOrdinario(jornadaAuth.getSercon(), jornadaAuth.getWorkTime()),
                    gjt
            ));

            genJorExtra = calcularGenericaJornadaExtra(jornadaAuth);
            if (genJorExtra == null) {
                return "return";
            }
        }
        return respuestaValidate;
    }

    private GenericaJornadaTipo findGenericaJornadaTipo(boolean realTime, GenericaJornada jornada) {
        String timeOrigin = jornada.getTimeOrigin();
        String timeDestiny = jornada.getTimeDestiny();
        if (realTime) {
            timeOrigin = jornada.getRealTimeOrigin();
            timeDestiny = jornada.getRealTimeDestiny();
        }
        return jornadaTEJB.findByHIniAndHFin(timeOrigin, timeDestiny, pau.getIdParamArea().getIdParamArea());

    }

    //Autorizar registro rol Profesional
    public void autorizar(GenericaJornada jornadaAuth, int op) throws ParseException {
        UtilJornada.cargarParametrosJar();
        if (!rol_user.equals("ROLE_DIRGEN")) {
            int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(jornadaAuth.getFecha(), 1), MovilidadUtil.fechaHoy(), false);

            if (genJornadaParam != null
                    && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0
                    && validacionDia == 0) {
                MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                return;
            }
        }
        try {
            if (jornadaAuth == null) {
                MovilidadUtil.addErrorMessage("Error al seleccionar la tarea");
                return;
            }
            calcularMasivoBean.cargarMapParamFeriado();
            GenericaJornadaTipo gjt = findGenericaJornadaTipo(op == 1, jornadaAuth);
            jornadaAuth.setIdGenericaJornadaTipo(gjt);
            if (jornadaAuth.getPrgModificada() != 1) {
                MovilidadUtil.addErrorMessage("No puede realizar esta acción debido a que no está modificada la programación");
                return;
            }
            boolean b_jornadaExtra = false;
            GenericaJornadaExtra genJorExtra = null;

            if (b_jornada_flexible) {
                if (validarTrasnochoMadrugada(jornadaAuth, false)) {
                    return;
                }
            }
            if (op == 1) { // (1) uno para autorizar, (0) cero para no autorizar
                if (!b_jornada_flexible) {
                    if (validateHorasExtras(jornadaAuth, gjt, genJorExtra).equals("return")) {
                        return;
                    }
                    if (validateHorasExtras(jornadaAuth, gjt, genJorExtra).equals("extra")) {
                        b_jornadaExtra = true;
                    }
                }
            }

            if (!rol_user.equals("ROLE_DIRGEN")) {
                if (aprobarHorasFeriadas(jornadaAuth)) {
                    int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(jornadaAuth.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
                    if (genJornadaParam != null
                            && genJornadaParam.getCtrlAprobarExtrasFeriadas() == 0
                            && validacionDia == 0) {
                        MovilidadUtil.addErrorMessage("No es posible modificar jornada, sobrepasa 24hrs despues de su ejecución.");
                        consultar();
                        return;
                    }
                }
            }
            jornadaAuth.setUserAutorizado(user.getUsername());
            jornadaAuth.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
            if (b_jornada_flexible) {
                jornadaAuth.setAutorizado(op);
                GenericaJornadaLiqUtil param = cargarObjetoParaJar(jornadaAuth);
                if (jornadaAuth.getIdGenericaJornadaTipo() != null) {
                    param.setIdGenericaJornadaTipo(new GenericaJornadaTipoLiqUtil());
                    param.getIdGenericaJornadaTipo().setDescanso(jornadaAuth.getIdGenericaJornadaTipo().getDescanso());
                    param.getIdGenericaJornadaTipo().setTipoCalculo(jornadaAuth.getIdGenericaJornadaTipo().getTipoCalculo());
                }
                if (MovilidadUtil.isSunday(jornadaAuth.getFecha())) {
                    List<GenericaJornadaLiqUtil> preCagarHorasDominicales = getJornadaFlexible().preCagarHorasDominicales(jornadaAuth.getFecha(),
                            jornadaAuth.getIdParamArea().getIdParamArea(),
                            jornadaAuth.getIdEmpleado().getIdEmpleado(), param);
                    GenericaJornadaLiqUtil get = preCagarHorasDominicales.get(0);
                    setValueFromPrgSerconJar(get, jornadaAuth);
                    genJornadaEJB.edit(jornadaAuth);

                } else {
                    //
                    param.setFecha(jornadaAuth.getFecha());
                    param.setIdEmpleado(new EmpleadoLiqUtil(jornadaAuth.getIdEmpleado().getIdEmpleado()));
                    param.setIdParamArea(new ParamAreaLiqUtil(jornadaAuth.getIdParamArea().getIdParamArea()));
                    param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(jornadaAuth.getSercon(), jornadaAuth.getWorkTime())));
                    ErrorPrgSercon errorPrgSercon
                            = getJornadaFlexible().recalcularHrsExtrasSmnl(param);
                    if (errorPrgSercon.isStatus()) {
                        String msg = "Se excedió el máximo de horas extras diarias " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                                + MovilidadUtil.toTimeSec(errorPrgSercon.getHora());
                        ConfigControlJornada get = UtilJornada.getMaxHrsExtrasDiaObj();
                        if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                            MovilidadUtil.addErrorMessage(msg);
                        } else {
                            MovilidadUtil.addAdvertenciaMessage(msg);
                        }
                        return;
                    }
                    List<GenericaJornadaLiqUtil> list = errorPrgSercon.getListaGen().stream()
                            .filter(x -> !fechaDiferente(jornadaAuth.getFecha(), x.getFecha()))
                            .collect(Collectors.toList());
                    GenericaJornadaLiqUtil get = list.get(0);
                    setValueFromPrgSerconJar(get, jornadaAuth);
                    genJornadaEJB.edit(jornadaAuth);
                    list = errorPrgSercon.getListaGen().stream()
                            .filter(x -> fechaDiferente(jornadaAuth.getFecha(), x.getFecha()))
                            .collect(Collectors.toList());
                    genJornadaEJB.updatePrgSerconFromList(list, 0);
                }
                if (op == 1) { // (1) uno para autorizar, (0) cero para no autorizar
                    notificarOperador(jornadaAuth, "Aprobación Ajuste Jornada");
                    MovilidadUtil.addSuccessMessage("Registro autorizado");
                } else {
                    notificarOperador(jornadaAuth, "Rechazo Ajuste Jornada");
                    MovilidadUtil.addSuccessMessage("Registro NO autorizado");
                }
            } else {
                jornadaAuth.setAutorizado(op);
                boolean b_descanso = false;
                if (jornadaAuth.getIdGenericaJornadaTipo() != null) {
                    if (MovilidadUtil.toSecs(jornadaAuth.getIdGenericaJornadaTipo().getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                        b_descanso = true;
                    }
                }
                boolean flagTipoCalculo = jornadaAuth.getIdGenericaJornadaTipo() != null && jornadaAuth.getIdGenericaJornadaTipo().getTipoCalculo() == 1;

                if (flagTipoCalculo) {
                    calcularMasivoBean.cargarCalcularDatoPorPartes(jornadaAuth);
                } else {
                    calcularMasivoBean.cargarCalcularDato(jornadaAuth, 2);
                }
                if (calcularMasivoBean.validarHorasPositivas(jornadaAuth)) {
                    MovilidadUtil.addErrorMessage("Error al calcular jornada");
                    return;
                }

                if (!flagTipoCalculo && (b_descanso && b_jornadaExtra)) {
                    calcularExtrasJornadaOrdinaria(jornadaAuth);
                }

                jornadaAuth.setDominicalCompDiurnas(null);
                jornadaAuth.setDominicalCompNocturnas(null);
                jornadaAuth.setDominicalCompDiurnaExtra(null);
                jornadaAuth.setDominicalCompNocturnaExtra(null);
                genJornadaEJB.edit(jornadaAuth);
                if (genJorExtra != null) {
                    if (genJorExtra.getIdGenericaJornadaExtra() != null) {
                        genJornadaExtraEjb.edit(genJorExtra);
                    } else {
                        genJornadaExtraEjb.create(genJorExtra);
                    }
                }
                calcularMasivoBean.recalcularJornada(jornadaAuth);
                if (op == 1) { // (1) uno para autorizar, (0) cero para no autorizar
                    notificarOperador(jornadaAuth, "Aprobación Ajuste Jornada");
                    MovilidadUtil.addSuccessMessage("Registro autorizado");
                } else {
                    notificarOperador(jornadaAuth, "Rechazo Ajuste Jornada");
                    MovilidadUtil.addSuccessMessage("Registro NO autorizado");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error en autorizar");
        }
    }

    private void notificarOperador(GenericaJornada gj, String asunto) {
        try {
            NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(ConstantsUtil.TEMPLATE_NOTIFICACION_AJUSTE_JORNADA);
            if (template == null) {
                return;
            }
            Map mapa = getMailParams();
            mapa.replace("template", template.getPath());
            Map mailProperties = new HashMap();
            Empleado empl = gj.getIdEmpleado();
            GenericaJornadaMotivo motivo = jornadaMotivoEJB.find(gj.getIdGenericaJornadaMotivo().getIdGenericaJornadaMotivo());
            mailProperties.put("identificacion", empl.getIdentificacion());
            mailProperties.put("nombre", empl.getNombres() + " " + empl.getApellidos());
            mailProperties.put("fecha", Util.dateFormat(gj.getFecha()));
            mailProperties.put("hora_ini_prg", gj.getTimeOrigin());
            mailProperties.put("hora_fin_prg", gj.getTimeDestiny());
            mailProperties.put("hora_ini_real", gj.getRealTimeOrigin());
            mailProperties.put("hora_fin_real", gj.getRealTimeDestiny());
            mailProperties.put("user_name", user.getUsername());
            mailProperties.put("motivo", motivo.getDescripcion());
            mailProperties.put("observacion", gj.getObservaciones());
            SendMails.sendEmail(mapa, mailProperties, asunto, "", empl.getEmailCorporativo(), "Notificaciones RIGEL", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void autorizarNovedadBio(GenericaJornada jornadaAuth, int op) throws ParseException {
        try {
            if (Objects.nonNull(jornadaAuth)) {
                if (!validarMarcaciones(jornadaAuth)) {
                    jornadaAuth.setMarcacionAutorizada(op);
                    jornadaAuth.setUsernameAuth(user.getUsername());//usuario quien realiza la autorización de la novedad
                    jornadaAuth.setFechaAuth(MovilidadUtil.fechaCompletaHoy());//fecha con hora
                    jornadaAuth.setAutorizado(op);//se autoriza o desautoriza la novedad
                    genJornadaEJB.edit(jornadaAuth);
                    listaMarcacionesPorAutorizar = getListBioNovedadesPorAutorizar(pau.getIdParamArea().getIdParamArea());
//                    PrimeFaces.current().executeScript("PF('gestionMarcacionMBDialog').clearFilters();");
//                    PrimeFaces.current().executeScript("PF('gestionMarcacionMBDialog').hide();");
                } else {
                    MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en Generar Horas Reales");
        }
    }

    private boolean validarTrasnochoMadrugada(GenericaJornada solicitanteAct, boolean loadFile) throws ParseException {
        GenericaJornada jornadaSolicitanteAnt;
        GenericaJornada jornadaSolicitanteSig;
        String hIniTurnoACambiar;
        int dif;
        Date fechaAnterior = MovilidadUtil.sumarDias(solicitanteAct.getFecha(), -1);
        Date fechaSiguiente = MovilidadUtil.sumarDias(solicitanteAct.getFecha(), 1);

        jornadaSolicitanteAnt = genJornadaEJB.validarEmplSinJornadaByParamAreaFechaEmpleado(solicitanteAct.getIdEmpleado().getIdEmpleado(), fechaAnterior, 0);
        boolean despuesDeEjecutado = UtilJornada.registroDespuesDeEjecutado(solicitanteAct.getFecha(),
                UtilJornada.ultimaHoraRealJornada(solicitanteAct));
        String msg = "El turno NO permite respetar las 12 horas de descanso reglamentarias."+
                (loadFile ?  "\n"+ solicitanteAct.getIdEmpleado().getNombresApellidos() : "");
        if (jornadaSolicitanteAnt != null) {
            /**
             * Caso 2: Verificar si el operador cumple 12 horas de descanso con
             * respecto al turno del día anterior.
             */
            String sTimeOriginRemp = solicitanteAct.getTimeOrigin();
            if (calcularMasivoBean.isRealTime(solicitanteAct.getAutorizado(),
                    solicitanteAct.getPrgModificada(), solicitanteAct.getRealTimeOrigin())) {
                sTimeOriginRemp = solicitanteAct.getRealTimeOrigin();

            }

            if (MovilidadUtil.toSecs(sTimeOriginRemp) == 0) {
                sTimeOriginRemp = "24:00:00";
            }

            String timeDestinyDiaAnterior = jornadaSolicitanteAnt.getTimeDestiny();
            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRemp, "24:00:00");
            if (calcularMasivoBean.isRealTime(jornadaSolicitanteAnt.getAutorizado(),
                    jornadaSolicitanteAnt.getPrgModificada(), jornadaSolicitanteAnt.getRealTimeOrigin())) {
                timeDestinyDiaAnterior = jornadaSolicitanteAnt.getRealTimeDestiny();
            }
            dif = MovilidadUtil.diferencia(timeDestinyDiaAnterior, hIniTurnoACambiar);
            ConfigControlJornada horasDescanso = SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.KEY_HORAS_DESCANSO);
            if (dif <= MovilidadUtil.toSecs(horasDescanso.getTiempo())) {
                if (despuesDeEjecutado && UtilJornada.tipoLiquidacion()) {
                    MovilidadUtil.addAdvertenciaMessage(msg);
                    return false;
                } else {
                    MovilidadUtil.addErrorMessage(msg);
                    return true;
                }
            }
        }

        jornadaSolicitanteSig = genJornadaEJB.validarEmplSinJornadaByParamAreaFechaEmpleado(solicitanteAct.getIdEmpleado().getIdEmpleado(), fechaSiguiente, 0);

        if (jornadaSolicitanteSig != null) {
            /**
             * Caso 2: Verificar si el operador solicitante cumple 12 horas de
             * descanso con respecto a la jornada del día siguiente.
             */
            String sTimeOriginRempSig = jornadaSolicitanteSig.getTimeOrigin();
            if (calcularMasivoBean.isRealTime(jornadaSolicitanteSig.getAutorizado(),
                    jornadaSolicitanteSig.getPrgModificada(), jornadaSolicitanteSig.getRealTimeOrigin())) {
                sTimeOriginRempSig = jornadaSolicitanteSig.getRealTimeOrigin();
            }

            if (MovilidadUtil.toSecs(sTimeOriginRempSig) == 0) {
                sTimeOriginRempSig = "24:00:00";
            }

            String sTimeDestinySolicitante = solicitanteAct.getTimeDestiny();
            if (calcularMasivoBean.isRealTime(solicitanteAct.getAutorizado(),
                    solicitanteAct.getPrgModificada(), solicitanteAct.getRealTimeOrigin())) {
                sTimeDestinySolicitante = solicitanteAct.getRealTimeDestiny();
            }
            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRempSig, "24:00:00");
            dif = MovilidadUtil.diferencia(sTimeDestinySolicitante, hIniTurnoACambiar);
            ConfigControlJornada get = SingletonConfigControlJornada.getMapConfigControlJornada().get(ConstantsUtil.KEY_HORAS_DESCANSO);
            if (dif <= MovilidadUtil.toSecs(get.getTiempo())) {
                if (despuesDeEjecutado && UtilJornada.tipoLiquidacion()) {
                    MovilidadUtil.addAdvertenciaMessage(msg);
                    return false;
                } else {
                    MovilidadUtil.addErrorMessage(msg);
                    return true;
                }
            }
        }

        return false;
    }

    public void calcularExtrasJornadaOrdinaria(GenericaJornada jornadaAuth) {
        int festivaExtraNocturna = MovilidadUtil.toSecs(jornadaAuth.getFestivoExtraNocturno());
        int festivaExtraDiurna = MovilidadUtil.toSecs(jornadaAuth.getFestivoExtraDiurno());
        int extraNocturna = MovilidadUtil.toSecs(jornadaAuth.getExtraNocturna());
        int extraDiurna = MovilidadUtil.toSecs(jornadaAuth.getExtraDiurna());
        int descanso_ = MovilidadUtil.toSecs(jornadaAuth.getIdGenericaJornadaTipo().getDescanso());

        if (festivaExtraNocturna
                > 0) {
            if (festivaExtraNocturna > descanso_) {
                festivaExtraNocturna = festivaExtraNocturna - descanso_;
                descanso_ = 0;
            } else if (descanso_ > festivaExtraNocturna) {
                descanso_ = descanso_ - festivaExtraNocturna;
                festivaExtraNocturna = 0;
            } else if (festivaExtraNocturna == descanso_) {
                festivaExtraNocturna = festivaExtraNocturna - descanso_;
                descanso_ = 0;
            }
        }
        if (descanso_
                > 0) {
            if (festivaExtraDiurna > 0) {
                if (festivaExtraDiurna > descanso_) {
                    festivaExtraDiurna = festivaExtraDiurna - descanso_;
                    descanso_ = 0;
                } else if (descanso_ > festivaExtraDiurna) {
                    descanso_ = descanso_ - festivaExtraDiurna;
                    festivaExtraDiurna = 0;
                } else if (festivaExtraDiurna == descanso_) {
                    festivaExtraDiurna = festivaExtraDiurna - descanso_;
                    descanso_ = 0;
                }
            }
        }
        if (descanso_
                > 0) {
            if (extraNocturna > 0) {
                if (extraNocturna > descanso_) {
                    extraNocturna = extraNocturna - descanso_;
                    descanso_ = 0;
                } else if (descanso_ > extraNocturna) {
                    descanso_ = descanso_ - extraNocturna;
                    extraNocturna = 0;
                } else if (extraNocturna == descanso_) {
                    extraNocturna = extraNocturna - descanso_;
                    descanso_ = 0;
                }
            }
        }
        if (descanso_
                > 0) {
            if (extraDiurna > 0) {
                if (extraDiurna > descanso_) {
                    extraDiurna = extraDiurna - descanso_;
                    descanso_ = 0;
                } else if (descanso_ > extraDiurna) {
                    descanso_ = descanso_ - extraDiurna;
                    extraDiurna = 0;
                } else if (extraDiurna == descanso_) {
                    extraDiurna = extraDiurna - descanso_;
                    descanso_ = 0;
                }
            }
        }

        jornadaAuth.setFestivoExtraNocturno(MovilidadUtil.toTimeSec(festivaExtraNocturna));
        jornadaAuth.setFestivoExtraDiurno(MovilidadUtil.toTimeSec(festivaExtraDiurna));
        jornadaAuth.setExtraNocturna(MovilidadUtil.toTimeSec(extraNocturna));
        jornadaAuth.setExtraDiurna(MovilidadUtil.toTimeSec(extraDiurna));
    }

    public void liquidar(GenericaJornada ps, int op) {
        try {
            if (ps != null) {
                if (op == 1) { // (1) uno para liquidar, (0) para no loquidar
                    ps.setLiquidado(1);
                    MovilidadUtil.addSuccessMessage("Registro liquidado correctamente");
                }
                if (op == 0) {
                    ps.setLiquidado(0);
                    MovilidadUtil.addSuccessMessage("Registro NO liquidado correctamente");
                }
                ps.setFechaLiquida(MovilidadUtil.fechaCompletaHoy());
                ps.setUserLiquida(user.getUsername());  //generado por es para usuario que liquida
                genJornadaEJB.edit(ps);
                return;
            }
            MovilidadUtil.addErrorMessage("Error al seleccionar la tarea");
        } catch (Exception e) {
            System.out.println("Error en liquidar");
        }
    }

    void validarRol() {
        if (rol_user.equals("ROLE_PROFGEN") || rol_user.equals("ROLE_PROFMTTO")
                || rol_user.equals("ROLE_PROFOP")) {
            b_autoriza = true;
            b_genera = true;
            b_generaDelete = true;
            b_controlAutoriza = true;
//                b_controlSubirArchivo = true;
            flag_cargar_jornada = false;
            flag_cargar_novedades = false;
        }
        if (rol_user.equals("ROLE_EMPLGEN") || rol_user.equals("ROLE_MTTO") || rol_user.equals("ROLE_TC")) {
            b_genera = true;
            b_generaDelete = false;
            flag_cargar_jornada = false;
            flag_cargar_novedades = false;
        }
        if (rol_user.equals("ROLE_LIQGEN") || rol_user.equals("ROLE_LIQMTTO")
                || rol_user.equals("ROLE_LIQ")) {
            b_liquida = true;
            b_autoriza = true;
            b_controlAutoriza = true;
            b_controlSubirArchivo = true;
            b_genera = true;
            b_generaDelete = true;
            b_controlLiquida = true;
            flag_cargar_jornada = true;
            flag_cargar_novedades = true;
        }
        if (rol_user.equals("ROLE_LIQMTTO")) {//es el usuario que puede autorizar las novedades de marcación en biométricos
            b_autorizaMarcaciones = true;
        }
    }

    public void onRowSelectMarcaciones(SelectEvent event) throws ParseException {
        genericaJornada = new GenericaJornada();
        genericaJornada = (GenericaJornada) event.getObject();
        //Si validacionDia es igual a 0, la jornada que quiere modificar ya paso las 24 horas de plazo para hacerlo
//        int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
//        if (genericaJornada.getNominaBorrada() == 1 || (genericaJornada.getLiquidado() != null && genericaJornada.getLiquidado() == 1)) {
//            genericaJornada = null;
//            return;
//        }
//        if (rol_user.equals("ROLE_EMPLGEN") || rol_user.equals("ROLE_MTTO")) {
//            if (genJornadaParam != null && genJornadaParam.getCtrlCambioJornada() == 0) {
//                if (validacionDia == 0) {
//                    genericaJornada = null;
//                    return;
//                }
//            }
//        }
//        if (rol_user.equals("ROLE_PROFGEN") || rol_user.equals("ROLE_PROFMTTO")) {
//            if (genJornadaParam != null && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0) {
//                if (validacionDia == 0) {
//                    genericaJornada = null;
//                }
//            }
//        }
    }

    public void onRowSelect(SelectEvent event) throws ParseException {
        genericaJornada = new GenericaJornada();
        genericaJornada = (GenericaJornada) event.getObject();
        //Si validacionDia es igual a 0, la jornada que quiere modificar ya paso las 24 horas de plazo para hacerlo
        int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornada.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
        if (genericaJornada.getNominaBorrada() == 1 || (genericaJornada.getLiquidado() != null && genericaJornada.getLiquidado() == 1)) {
            genericaJornada = null;
            return;
        }
//        Config c = configEJB.findByKey("edit_jor");
//        if (c != null && c.getValue() == 0) {
//            for (GrantedAuthority auth : user.getAuthorities()) {
//                if (auth.getAuthority().equals("ROLE_EMPLGEN") || auth.getAuthority().equals("ROLE_MTTO")) {
//                    if (validacionDia != 1 || genericaJornada.getNominaBorrada() == 1) {
//                        genericaJornada = null;
//                        return;
//                    }
//                }
//            }
//        }
        if (rol_user.equals("ROLE_EMPLGEN") || rol_user.equals("ROLE_MTTO")) {
            if (genJornadaParam != null && genJornadaParam.getCtrlCambioJornada() == 0) {
                if (validacionDia == 0) {
                    genericaJornada = null;
                    return;
                }
            }
        }
        if (rol_user.equals("ROLE_PROFGEN") || rol_user.equals("ROLE_PROFMTTO")) {
            if (genJornadaParam != null && genJornadaParam.getCtrlAutorizarExtensionJornada() == 0) {
                if (validacionDia == 0) {
                    genericaJornada = null;
                }
            }
        }
    }

    //Validar que las jornadas no tengan mas de 24 horas de ejecutadas.
    public boolean validarJornadasViejas(Date fecha) throws ParseException {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_EMPLGEN") || auth.getAuthority().equals("ROLE_MTTO")) {
                if (genJornadaParam != null && genJornadaParam.getCtrlCambioJornada() == 0) {
                    int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(fecha, 1), MovilidadUtil.fechaHoy(), false);
                    if (validacionDia == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean disableBoton() throws ParseException {
        int validacionDia = MovilidadUtil.fechasIgualMenorMayor(MovilidadUtil.sumarDias(genericaJornadaNew.getFecha(), 1), MovilidadUtil.fechaHoy(), false);
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_EMPLGEN") || auth.getAuthority().equals("ROLE_MTTO")) {
                if (genJornadaParam != null && genJornadaParam.getCtrlCambioJornada() == 0) {
                    if (validacionDia == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void onRowUnselect() {
        genericaJornada = null;
    }

    void enviarCorreo() {

    }

    public void handleFileUpload(FileUploadEvent event) throws Exception {
        try {
            this.uploadedFile = event.getFile();
            calcularMasivoBean.cargarMapParamFeriado();
            cargarReporteLiquidacion();
//            MovilidadUtil.addSuccessMessage("Archivo cargado correctamente", "Aviso");
            consultar();
        } catch (CagarJornadaGenericaException e) {
            MovilidadUtil.addErrorMessage(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void cargarNovedades(FileUploadEvent event) throws Exception {
        try {
            this.archivoNovedades = event.getFile();
            calcularMasivoBean.cargarMapParamFeriado();
            if (archivoNovedades != null) {
                b_novFromFile = true;
                List<GenericaJornadaObj> list_novedades = new ArrayList<>();
                String path = Util.saveFile(archivoNovedades, 0, "carga_novedades");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcelAndCargarNovedades(fileInputStream, list_novedades);
                b_novFromFile = false;
            }
            consultar();
        } catch (CagarJornadaGenericaException e) {
            MovilidadUtil.addErrorMessage(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    void recorrerExcelAndCargarNovedades(FileInputStream inputStream,
            List<GenericaJornadaObj> list_novedades)
            throws CagarJornadaGenericaException, IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);

        int numFilas = sheet.getLastRowNum();
        for (int a = 1; a <= numFilas; a++) {
            GenericaJornadaObj jornada = new GenericaJornadaObj();
            Row fila = sheet.getRow(a);
            int numCols = fila.getLastCellNum();
            Empleado empleado;
            for (int b = 0; b < numCols; b++) {
                Cell celda = fila.getCell(b);
                if (celda != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    switch (b) {
                        case 0:
                            Date parse;
                            try {
                                parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                            } catch (Exception e) {
                                parse = Util.toDate(celda.toString());
                            }
                            if (parse == null) {
                                throw new CagarJornadaGenericaException("Formato erroneo evaluando el valor " + celda.getDateCellValue() + ". \nColumna: Fecha, Fila: " + Integer.valueOf(a + 1));
                            }
                            jornada.setFecha(parse);
                            break;
                        case 1:
                            //validar que exista el id_empleado                           
                            BigDecimal valorDecimal = new BigDecimal(celda.toString());
                            //como Bigdecimal nunca es null se evalua directamente
                            empleado = emplEJB.findByIdentificacion(String.valueOf(valorDecimal.intValue()));
                            if (empleado != null) {
                                jornada.setIdentificacion(empleado.getIdentificacion());
                                jornada.setNombre(empleado.getNombresApellidos());
                            } else {
                                throw new CagarJornadaGenericaException("Empleado con identificación " + celda.toString() + " no existe en la BD. Columna: Jornada, Fila: " + Integer.valueOf(a + 1));
                            }
                            break;
                        case 2://H. Inicio Prg
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'H. Inicio Prg', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                jornada.setTimeDestiny(celda.toString());
                            }
                            break;
                        case 3://H. Fin Prg
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). \nColumna: 'H. Fin Prg', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                jornada.setTimeDestiny(celda.toString());
                            }
                            break;
                        case 4://H. Ini Turno
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). Columna: 'H. Ini Turno', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                jornada.setHiniTurno1(celda.toString());
                            }
                            break;
                        case 5://H. Fin Turno
                            if (Util.isStringNullOrEmpty(celda.toString())) {
                                throw new CagarJornadaGenericaException("Error formato (" + celda.toString() + "). Columna: 'H. Fin Turno', Fila: " + Integer.valueOf(a + 1));
                            } else {
                                jornada.setHfinTurno1(celda.toString());
                            }
                            break;
                        case 6://motivo
                            GenericaJornadaMotivo gjmotivo = jornadaMotivoEJB.findByName(celda.toString().toUpperCase(), pau.getIdParamArea().getIdParamArea());
                            if (Objects.nonNull(gjmotivo)) {
                                jornada.setMotivoJornada(celda.toString());
                            } else {
                                throw new CagarJornadaGenericaException("Motivo no existe (" + celda.toString() + "). Columna: 'motivo', Fila: " + Integer.valueOf(a + 1));
                            }
                            break;
                        case 7://Observación
                            jornada.setObservacion(celda.toString());
                            break;
                    }
                }
            }
            list_novedades.add(jornada);
        }//fin del bucle que lee las filas del archivo
        List<GenericaJornada> listGenericaJornada = castGenericaJornada(list_novedades);
        if (listGenericaJornada != null && !listGenericaJornada.isEmpty()) {
            cargarNovedadesExcel(listGenericaJornada);
        }
    }

    private List<GenericaJornada> castGenericaJornada(List<GenericaJornadaObj> list) throws CagarJornadaGenericaException {
        List<GenericaJornada> listGJ = new ArrayList<>();
        GenericaJornada objGJ;
        //no se valida la existencia del empleado dado que la lista recibida debe 
        //validarse con anterioridad
        Empleado empl;
        for (GenericaJornadaObj obj : list) {
            empl = emplEJB.findByIdentificacion(obj.getIdentificacion());
            objGJ = genJornadaEJB.validarEmplSinJornada(empl.getIdEmpleado(), obj.getFecha());
            if (objGJ != null) {
                objGJ.setRealTimeOrigin(obj.getHiniTurno1());
                objGJ.setRealTimeDestiny(obj.getHfinTurno1());
                objGJ.setObservaciones(obj.getObservacion());
                objGJ.setModificado(new Date());
                objGJ.setIdGenericaJornadaMotivo(jornadaMotivoEJB.findByName(obj.getMotivoJornada(), pau.getIdParamArea().getIdParamArea()));
                listGJ.add(objGJ);
            } else {
                throw new CagarJornadaGenericaException("No existe Jornada para el colaborador " + obj.getIdentificacion() + " en la fecha " + obj.getFecha());
            }
        }
        return listGJ;
    }

    /**
     * Permite autorizar un conjunto de novedades ingresadas en una colección de
     * tipo GenericaJornadaObj
     *
     * @param list contiene las novedades que se desean autorizar
     */
    private void cargarNovedadesExcel(List<GenericaJornada> list) {
        //objeto en el que se salva la información que contenga genericaJornada, 
        //esto se debe hacer dado que el método generarHorasReales emplea el objeto 
        //global genericaJornada 
        GenericaJornada objTemp = genericaJornada;
        for (GenericaJornada obj : list) {
            genericaJornada = obj;
            generarHorasReales(true);//se debe identificar el colaborador en caso de errores dado que se trata de carga masiva. 
        }
        //asignar el objeto que se tenia al momento de lanzar el proceso
        genericaJornada = objTemp;
    }

    public void cargarReporteLiquidacion() throws IOException, Exception, CagarJornadaGenericaException {
        if (uploadedFile != null) {
            List<GenericaJornadaObj> list_LiquidacionSercon = new ArrayList<>();
            String path = Util.saveFile(uploadedFile, 0, "asignacion");
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            recorrerExcelAndCargarLista(fileInputStream, list_LiquidacionSercon);
        }
    }

    void recorrerExcelAndCargarLista(FileInputStream inputStream,
            List<GenericaJornadaObj> list_LiquidacionSercon)
            throws CagarJornadaGenericaException, IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);

        int numFilas = sheet.getLastRowNum();
        for (int a = 1; a <= numFilas; a++) {
            GenericaJornadaObj jornada = new GenericaJornadaObj();
            Row fila = sheet.getRow(a);
            int numCols = fila.getLastCellNum();
            for (int b = 0; b < numCols; b++) {
                Cell celda = fila.getCell(b);
                if (celda != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

                    switch (b) {
                        case 0:
                            Date parse;
                            try {
                                parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                            } catch (Exception e) {
                                parse = Util.toDate(celda.toString());
                            }
                            if (parse == null) {
                                throw new CagarJornadaGenericaException("Formato erroneo. Columna: Fecha, Fila: " + Integer.valueOf(a + 1));
                            }
                            jornada.setFecha(parse);
                            break;
                        case 1:
                            //validar que exista la Jornada
                            if (Objects.nonNull(jornadaTipoEJB.findBySercon(celda.toString(), pau.getIdParamArea().getIdParamArea()))) {
                                jornada.setTarea(celda.toString());
                            } else {
                                throw new CagarJornadaGenericaException("Jornada no existe. Columna: Jornada, Fila: " + Integer.valueOf(a + 1));
                            }
                            break;
                        case 2:
//                                jornada.setIdentificacion(celda.getCellType() == XSSFCell.CELL_TYPE_NUMERIC ? (celda.getNumericCellValue() + "").replace(".", ":").split(":")[0] : celda.getStringCellValue());
                                try {
                            jornada.setIdentificacion(celda.getStringCellValue());
                        } catch (Exception e) {
                            long ival = (long) celda.getNumericCellValue();
                            jornada.setIdentificacion(String.valueOf(ival));
                        }
                        if (jornada.getIdentificacion() == null || (jornada.getIdentificacion() != null && Util.getStringSinEspacios(jornada.getIdentificacion()).isEmpty())) {
                            throw new CagarJornadaGenericaException("Formato erroneo. Columna: Identificación, Fila: " + Integer.valueOf(a + 1));
                        }
                        break;
                        case 3:
                            jornada.setNombre(celda.toString());
                            break;
                        case 4:
                            jornada.setTimeOrigin(celda.toString());
                            if (jornada.getTimeOrigin() == null || (jornada.getTimeOrigin() != null && Util.getStringSinEspacios(jornada.getTimeOrigin()).isEmpty())) {
                                throw new CagarJornadaGenericaException("Formato erroneo. Columna: TimeOrigin, Fila: " + Integer.valueOf(a + 1));
                            }
                            break;
                        case 5:
                            jornada.setTimeDestiny(celda.toString());
                            if (jornada.getTimeDestiny() == null || (jornada.getTimeDestiny() != null && Util.getStringSinEspacios(jornada.getTimeDestiny()).isEmpty())) {
                                throw new CagarJornadaGenericaException("Formato erroneo. Columna: TimeDestiny, Fila: " + Integer.valueOf(a + 1));
                            }
                            break;
                        case 6:
                            jornada.setTimeProduction(celda.toString());
                            break;
                        case 7:
                            jornada.setCompensatorio(celda.toString());
                            break;
                        case 8:
                            jornada.setTipoJornada(celda.toString());
                            break;
                    }
                }
            }
            list_LiquidacionSercon.add(jornada);
        }
        try {
            list_LiquidacionSercon.sort((d1, d2) -> d1.getFecha().compareTo(d2.getFecha()));
        } catch (Exception e) {
            throw new CagarJornadaGenericaException("Valor nulo: Verifique último registro del archivo");
        }

        Date fromDate = list_LiquidacionSercon.get(0).getFecha();
        int indexLast = list_LiquidacionSercon.size() - 1;
        Date toDate = list_LiquidacionSercon.get(indexLast).getFecha();
        llenarMapJornadasByRangeFechas(fromDate, toDate);

        prepareJornadas(list_LiquidacionSercon);

        if (b_jornada_flexible) {//flexible

            if (listaError.isEmpty()) {
                Map<String, List<GenericaJornada>> mapJornadasSemanas
                        = cargarMapSemanalDeJornadas(listGenericaJornadaPersistir);
                for (Map.Entry<String, List<GenericaJornada>> entry : mapJornadasSemanas.entrySet()) {
                    System.out.println("key SEMANA---------->" + entry.getKey());
                    System.out.println("size SEMANA---------->" + entry.getValue().size());
                    String[] listaFecha = entry.getKey().split("_");
                    Date desde = Util.toDate(listaFecha[0]);
                    Date hasta = Util.toDate(listaFecha[1]);
                    for (GenericaJornada item : entry.getValue()) {
                        genJornadaEJB.create(item);
                    }
                    UtilJornada.cargarParametrosJar();
                    List<GenericaJornadaLiqUtil> calculoOrdinarioJornadas = getJornadaFlexible().calculoOrdinarioJornadas(desde, hasta, 1, pau.getIdParamArea().getIdParamArea());
                    System.out.println("calculoOrdinarioJornadas---------->" + calculoOrdinarioJornadas.size());
                    genJornadaEJB.updatePrgSerconFromList(calculoOrdinarioJornadas, 1);//no afecta los dominicales compensados
                    List<GenericaJornadaLiqUtil> calculoJornadaFlexible = getJornadaFlexible().calculoJornadaFlexible(desde, hasta, 1, pau.getIdParamArea().getIdParamArea());
                    System.out.println("calculoJornadaFlexible------------>" + calculoJornadaFlexible.size());
                    genJornadaEJB.updatePrgSerconFromList(calculoJornadaFlexible, 1);
                    List<GenericaJornadaLiqUtil> distribuirDomicalSinCompensatorio = getJornadaFlexible().distribuirDomicalSinCompensatorio(desde, hasta, pau.getIdParamArea().getIdParamArea());
                    System.out.println("distribuirDomicalSinCompensatorio->" + distribuirDomicalSinCompensatorio.size());
                    genJornadaEJB.updatePrgSerconFromList(distribuirDomicalSinCompensatorio, 1);
                }
            } else {
                PrimeFaces.current().executeScript("PF('crear_jornada_t_wv').show()");
                PrimeFaces.current().ajax().update("formErrores:erroresList");
            }
        } else {
            if (listaError.isEmpty()) {
                persistirTodoAndRecalcular();
            } else {
                PrimeFaces.current().executeScript("PF('crear_jornada_t_wv').show()");
                PrimeFaces.current().ajax().update("formErrores:erroresList");
            }

        }
        if (listaError.isEmpty()) {
            MovilidadUtil.addSuccessMessage("Carga finalizada con exito.");
        }
        wb.close();
    }

    private Map<String, List<GenericaJornada>> cargarMapSemanalDeJornadas(List<GenericaJornada> jornadas) throws ParseException {
        jornadas.sort((d1, d2) -> d1.getFecha().compareTo(d2.getFecha()));
        Date fromDate = jornadas.get(0).getFecha();
        int indexLast = jornadas.size() - 1;
        Date toDate = jornadas.get(indexLast).getFecha();
        Calendar current = Calendar.getInstance();
        current.setTime(fromDate);
        int i = 1;
        Map<String, List<GenericaJornada>> map = new HashMap<>();
        while (!current.getTime().after(toDate)) {
            Date diaDomingo = MovilidadUtil.getDiaSemana(current.getTime(), Calendar.SUNDAY);
            String key = Util.dateFormat(current.getTime()).concat("_").concat(Util.dateFormat(diaDomingo));
            List<GenericaJornada> list = jornadas.stream()
                    .filter(x -> MovilidadUtil.betweenSinHora(x.getFecha(), current.getTime(), diaDomingo))
                    .collect(Collectors.toList());
            map.put(key, list);
            current.setTime(MovilidadUtil.sumarDias(diaDomingo, 1));
        }
        List<Map.Entry<String, List<GenericaJornada>>> list = new ArrayList<>(map.entrySet());

        // Invertir el orden de la lista
        Collections.reverse(list);
        Map<String, List<GenericaJornada>> reversedMap = new HashMap<>();
        for (Map.Entry<String, List<GenericaJornada>> entry : list) {
            reversedMap.put(entry.getKey(), entry.getValue());
        }
        return reversedMap;
    }

    public void findEmplActivos() {
        listaEmpleados.clear();
        if (pau == null) {
            MovilidadUtil.addErrorMessage("Aun no está asignado a en un área");
            return;
        }
        if (pau.getIdParamArea().getParamAreaCargoList() == null) {
            MovilidadUtil.addErrorMessage("Aun no están cargados los cargos en el área");
            return;
        }
        if (pau.getIdParamArea().getParamAreaCargoList().isEmpty()) {
            MovilidadUtil.addErrorMessage("Aun no están cargados los cargos en el área");
            return;
        }
        listaEmpleados = emplEJB.getEmpledosByIdArea(pau.getIdParamArea().getIdParamArea(), 0);
        if (listaEmpleados.isEmpty()) {
            MovilidadUtil.addErrorMessage("Aun no hay empleado activos en el area");
        } else {
            PrimeFaces.current().executeScript("PF('empleado_list_wv').show()");
            PrimeFaces.current().ajax().update("formEmpleados:tabla");
        }

    }

    public HashMap llenarMapEmpleado() {
        HashMap<String, Empleado> mapa = new HashMap<>();
        List<Empleado> listaEmpleados = emplEJB.getEmpledosByIdArea(pau.getIdParamArea().getIdParamArea(), 0);
        for (Empleado e : listaEmpleados) {
            if (e.getIdEmpleadoEstado().getIdEmpleadoEstado().equals(1)) {
                mapa.put(e.getIdentificacion(), e);
            }
        }
        return mapa;
    }

    public void llenarMapJornadaTipo() {
        mapGenericaJornadaTipo = new HashMap<>();
        mapGenericaJornadaTipoAux = new HashMap<>();
        for (GenericaJornadaTipo p : pau.getIdParamArea().getGenericaJornadaTipoList()) {
            if (p.getEstadoReg().equals(0)) {
                mapGenericaJornadaTipo.put(p.getDescripcion(), p);
                mapGenericaJornadaTipoAux.put(p.getHiniT1() + "_" + p.getHfinT1(), p);
            }
        }
    }

    public void llenarMapJornadasByRangeFechas(Date desde, Date hasta) {
        mapGenericaJornada = new HashMap<>();
        List<GenericaJornada> list = genJornadaEJB.getByDate(desde, hasta, pau.getIdParamArea().getIdParamArea());
        for (GenericaJornada jor : list) {
            String key = Util.dateFormat(jor.getFecha()) + "_" + jor.getIdEmpleado().getIdentificacion();
            mapGenericaJornada.put(key, jor);
        }
    }

    @Transactional
    void persistirTodo() throws Exception {
        if (listaError.isEmpty()) {
            Connection con = null;
            PreparedStatement ps = null;

            String sql = "INSERT INTO generica_jornada(fecha,"
                    + "id_empleado,"
                    + "username,"
                    + "time_origin,"
                    + "time_destiny,"
                    + "id_param_area,"
                    + "creado,"
                    + "modificado,"
                    + "cargada,"
                    + "prg_modificada,"
                    + "id_generica_jornada_tipo,"
                    + "autorizado,"
                    + "liquidado,"
                    + "tipo_calculo,"
                    + "diurnas,"
                    + "nocturnas,"
                    + "extra_diurnas,"
                    + "extra_nocturnas,"
                    + "festivo_diurno,"
                    + "festivo_nocturno,"
                    + "festivo_extra_diurna,"
                    + "festivo_extra_nocturno,"
                    + "production_time,"
                    + "compensatorio,"
                    + "estado_reg) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
            con = Common.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ps = con.prepareStatement(sql);
            for (GenericaJornada g : listGenericaJornadaPersistir) {
                ps.setString(1, Util.dateFormat(g.getFecha()));
                ps.setInt(2, g.getIdEmpleado().getIdEmpleado());
                ps.setString(3, g.getUsername());
                ps.setString(4, g.getTimeOrigin());
                ps.setString(5, g.getTimeDestiny());
                ps.setInt(6, g.getIdParamArea().getIdParamArea());
                ps.setString(7, Util.dateFormat(MovilidadUtil.fechaCompletaHoy()));
                ps.setString(8, Util.dateFormat(MovilidadUtil.fechaCompletaHoy()));
                ps.setInt(9, g.getCargada());
                ps.setInt(10, g.getPrgModificada());
                ps.setInt(11, g.getIdGenericaJornadaTipo() == null ? null : g.getIdGenericaJornadaTipo().getIdGenericaJornadaTipo());
                ps.setInt(12, g.getAutorizado());
                ps.setInt(13, g.getLiquidado());
                ps.setInt(14, g.getTipoCalculo());
                ps.setString(15, g.getDiurnas());
                ps.setString(16, g.getNocturnas());
                ps.setString(17, g.getExtraDiurna());
                ps.setString(18, g.getExtraNocturna());
                ps.setString(19, g.getFestivoDiurno());
                ps.setString(20, g.getFestivoNocturno());
                ps.setString(21, g.getFestivoExtraDiurno());
                ps.setString(22, g.getFestivoExtraNocturno());
                ps.setString(23, g.getProductionTime());
                ps.setString(24, g.getCompensatorio());
                ps.setInt(25, g.getEstadoReg());

                ps.addBatch();
            }
            ps.executeBatch();
            con.commit();
        } else {
            PrimeFaces.current().executeScript("PF('crear_jornada_t_wv').show()");
            PrimeFaces.current().ajax().update("formErrores:erroresList");
        }
    }

    @Transactional
    void persistirTodoII() throws Exception {
        for (GenericaJornada g : listGenericaJornadaPersistir) {
            g.setAutorizado(null);
            genJornadaEJB.create(g);
        }
    }

    public void persistirTodoAndRecalcular() throws Exception {
        persistirTodoII();
        for (GenericaJornada g : listGenericaJornadaPersistir) {
            if (MovilidadUtil.isSunday(g.getFecha())) {
                calcularMasivoBean.recalcularJornada(g);
            }
        }
        if (mapaEmplJornadaExtra != null) {
            for (Map.Entry<String, GenericaJornadaExtra> entry : mapaEmplJornadaExtra.entrySet()) {
                if (entry.getValue().getIdGenericaJornadaExtra() == null) {
                    genJornadaExtraEjb.create(entry.getValue());
                } else {
                    genJornadaExtraEjb.edit(entry.getValue());
                }
            }
        }
    }

    boolean isDescansoAndSetGenericaJornadaTipo(GenericaJornada obj, GenericaJornadaObj jor, GenericaJornadaTipo gjt) {
        if (jor != null && jor != null && jor.getTipoJornada() != null) {
            if (jor.getTipoJornada().replaceAll("\\s", "").isEmpty()) {
                gjt = mapGenericaJornadaTipo.get(jor.getTipoJornada().replaceAll("\\s", ""));
                if (gjt != null) {
                    if (obj != null) {
                        obj.setIdGenericaJornadaTipo(gjt);
                    }
                    if (MovilidadUtil.toSecs(gjt.getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                        return true;
                    }
                } else {
                    gjt = mapGenericaJornadaTipo.get(jor.getTimeOrigin().replaceAll("\\s", "") + "_" + jor.getTimeDestiny().replaceAll("\\s", ""));
                    if (gjt != null) {
                        if (obj != null) {
                            obj.setIdGenericaJornadaTipo(gjt);
                        }
                        if (MovilidadUtil.toSecs(gjt.getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                            return true;

                        }
                    }
                }
            } else {
//                    System.out.println("Hora Inicio-->" + jor.getTimeOrigin());
//                    System.out.println("Hora Fin-->" + jor.getTimeDestiny());
                gjt = mapGenericaJornadaTipo.get(jor.getTimeOrigin().replaceAll("\\s", "")
                        + "_" + jor.getTimeDestiny().replaceAll("\\s", ""));
                if (gjt != null) {
                    if (obj != null) {
                        obj.setIdGenericaJornadaTipo(gjt);
                    }
                    if (MovilidadUtil.toSecs(gjt.getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                        return true;

                    }
                }
            }
        } else if (jor != null) {
            gjt = mapGenericaJornadaTipo.get(jor.getTimeOrigin().replaceAll("\\s", "")
                    + "_" + jor.getTimeDestiny().replaceAll("\\s", ""));
            if (gjt != null) {
                if (obj != null) {
                    obj.setIdGenericaJornadaTipo(gjt);
                }
                if (MovilidadUtil.toSecs(gjt.getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                    return true;
                }
            }
        } else if (gjt != null) {
            if (MovilidadUtil.toSecs(gjt.getDescanso()) > MovilidadUtil.toSecs("00:00:00")) {
                return true;
            }
        }
        return false;
    }

    void prepareJornadas(List<GenericaJornadaObj> list) throws Exception {
        listaError.clear();
        listGenericaJornadaPersistir = new ArrayList<>();
        HashMap<String, Empleado> mapEmpleado;
        mapEmpleado = llenarMapEmpleado();
        llenarMapJornadaTipo();
        calcularMasivoBean.cargarMapParamFeriado();
        Map<String, Date> countMap = new HashMap<>();
        Map<String, GenericaJornada> emplJornadaExtra = new HashMap<>();

        for (GenericaJornadaObj jor : list) {
            boolean b_error = false;
            Errores error = new Errores();
            error.setFecha(Util.dateFormat(jor.getFecha()));
            error.setServbus(jor.getIdentificacion() + " - " + jor.getNombre());

            if (countMap.containsKey(jor.getIdentificacion())) {
                if (jor.getFecha().equals(countMap.get(jor.getIdentificacion()))) {
                    error.setError("Registro duplicado en el archivo. ");
                    b_error = true;
                }
            } else {
                countMap.put(jor.getIdentificacion(), jor.getFecha());
            }

            GenericaJornada obj = new GenericaJornada();
            if (validarHoras(jor.getTimeOrigin(), jor.getTimeDestiny())) {
                error.setError(error.getError() + "La hora inicio no debe ser mayor a la hora fin. ");
                b_error = true;
            }
            Empleado empl = mapEmpleado.get(jor.getIdentificacion());
            if (empl == null) {
                if (error.getError() != null) {
                    error.setError(error.getError() + "No existe empleado en el área. ");
                } else {
                    error.setError("No existe empleado en el área. ");
                }
                b_error = true;

            } else {
                String key = Util.dateFormat(jor.getFecha()) + "_" + empl.getIdentificacion();
                GenericaJornada ps = mapGenericaJornada.get(key);
                if (ps != null) {
                    if (error.getError() != null) {
                        error.setError(error.getError() + "Ya existe jornada. ");
                    } else {
                        error.setError("Ya existe jornada. ");
                    }
                    b_error = true;

                }
            }
            boolean b_descanso = isDescansoAndSetGenericaJornadaTipo(obj, jor, null);

            obj.setIdEmpleado(empl);
            obj.setSercon(jor.getTarea());
            obj.setFecha(jor.getFecha());
            obj.setTimeOrigin(jor.getTimeOrigin());
            obj.setTimeDestiny(jor.getTimeDestiny());
            obj.setCompensatorio(jor.getCompensatorio());
            obj.setProductionTime(jor.getTimeProduction());
            obj.setWorkTime(jor.getTimeProduction());
            obj.setUsername(user.getUsername());
            obj.setCreado(MovilidadUtil.fechaCompletaHoy());
            obj.setCargada(1);
            obj.setLiquidado(0);
            obj.setModificado(MovilidadUtil.fechaCompletaHoy());
            obj.setEstadoReg(0);
            obj.setPrgModificada(0);
            obj.setIdParamArea(pau.getIdParamArea());
            setTipoJornada(jor.getTimeOrigin(), jor.getTimeDestiny(), obj);
            String totalHorasLaborales = HORAS_JORNADA;
            String maxHrsExtrasDia = MAX_HORAS_EXTRAS_DIARIAS;
            if (b_jornada_flexible) {//flexible
                totalHorasLaborales = obj.getWorkTime();
                maxHrsExtrasDia = UtilJornada.getMaxHrsExtrasDia();
            }
            String isJornadaExtraOrError = isJornadaExtraOrError(obj, error);
            boolean b_jornadaExtra = isJornadaExtraOrError.equals("JORNADA_EXTRA");

            if (isJornadaExtraOrError.equals("ERROR") || b_error) {
                listaError.add(error);
            } else {
                listGenericaJornadaPersistir.add(obj);
            }

        }
        if (b_maxHorasExtrasSemanaMes) {
            validarHoraExtraSemanaAndMensual();
        }
    }

    private void setTipoJornada(String iniT, String finT, GenericaJornada param) {
        param.setIdGenericaJornadaTipo(mapGenericaJornadaTipoAux.get(iniT.concat("_").concat(finT)));
    }

    String isJornadaExtraOrError(GenericaJornada obj, Errores error) {
        String totalHorasLaborales = HORAS_JORNADA;
        String maxHrsExtrasDia = MAX_HORAS_EXTRAS_DIARIAS;
        if (b_jornada_flexible) {//flexible
            totalHorasLaborales = obj.getWorkTime();
            maxHrsExtrasDia = UtilJornada.getMaxHrsExtrasDia();
        }
        if (isExtra(obj.getTimeOrigin(), obj.getTimeDestiny(), totalHorasLaborales)) {
            if (obj.getIdGenericaJornadaTipo() != null) {
                if (!((MovilidadUtil.toSecs(obj.getTimeDestiny())
                        - MovilidadUtil.toSecs(obj.getTimeOrigin())
                        - MovilidadUtil.toSecs(totalHorasLaborales)
                        - MovilidadUtil.toSecs(obj.getIdGenericaJornadaTipo().getDescanso()))
                        <= MovilidadUtil.toSecs(maxHrsExtrasDia))) {
                    if (error != null) {
                        if (error.getError() != null) {
                            error.setError(error.getError() + "Maś de 2 horas extras diarias. ");
                        } else {
                            error.setError("Maś de 2 horas extras diarias. ");
                        }
                    }
                    return "ERROR";
                } else {
                    return "JORNADA_EXTRA";
                }
            } else {
                if (!((MovilidadUtil.toSecs(obj.getTimeDestiny())
                        - MovilidadUtil.toSecs(obj.getTimeOrigin())
                        - MovilidadUtil.toSecs(HORAS_JORNADA)) <= MovilidadUtil.toSecs(maxHrsExtrasDia))) {
                    if (error != null) {
                        if (error.getError() != null) {
                            error.setError(error.getError() + "Maś de 2 horas extras diarias. ");
                        } else {
                            error.setError("Maś de 2 horas extras diarias. ");
                        }
                    }
                    return "ERROR";
                } else {
                    return "JORNADA_EXTRA";
                }
            }
        }
        return "";
    }

    void calularJornadas(List<GenericaJornada> list) {
        for (GenericaJornada obj : list) {

            boolean b_jornadaExtra = isJornadaExtraOrError(obj, null).equals("JORNADA_EXTRA");

            boolean flagTipoCalculo = obj.getIdGenericaJornadaTipo() != null && obj.getIdGenericaJornadaTipo().getTipoCalculo() == 1;

            if (flagTipoCalculo) {
                calcularMasivoBean.cargarCalcularDatoPorPartes(obj);
            } else {
                calcularMasivoBean.cargarCalcularDato(obj, 0);
            }
            if (calcularMasivoBean.validarHorasPositivas(obj)) {
            }
            boolean b_descanso = isDescansoAndSetGenericaJornadaTipo(null, null, obj.getIdGenericaJornadaTipo());

            if (!flagTipoCalculo && (b_jornadaExtra && b_descanso)) {
                calcularExtrasJornadaOrdinaria(obj);
            }
        }
    }

    public GenericaJornadaExtra calcularGenJorExtraAlCrearJornada(GenericaJornada gj, double horasExtras) {
        double maxHorasExtrasSemana = genJornadaParam.getHorasExtrasSemanales();
        double maxHorasExtrasMes = genJornadaParam.getHorasExtrasMensuales();
        horasExtras = horasExtras / 3600;
        GenericaJornadaExtra genJornadaExtra = genJornadaExtraEjb.getByEmpleadoAndFecha(gj.getIdEmpleado().getIdEmpleado(), gj.getFecha());
        if (genJornadaExtra == null) {
            Calendar primerDiaMes = Calendar.getInstance();
            Calendar ultimoDiaMes = Calendar.getInstance();
            primerDiaMes.setTime(gj.getFecha());
            ultimoDiaMes.setTime(gj.getFecha());

            primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
            ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));

            genJornadaExtra = new GenericaJornadaExtra();
            genJornadaExtra.setIdEmpleado(gj.getIdEmpleado());
            genJornadaExtra.setEstadoReg(0);
            genJornadaExtra.setUsername(user.getUsername());
            genJornadaExtra.setDesde(primerDiaMes.getTime());
            genJornadaExtra.setHasta(ultimoDiaMes.getTime());
            genJornadaExtra.setCreado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setModificado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setIdParamArea(pau.getIdParamArea());
            genJornadaExtra.setSemana1(0);
            genJornadaExtra.setSemana2(0);
            genJornadaExtra.setSemana3(0);
            genJornadaExtra.setSemana4(0);
            genJornadaExtra.setSemana5(0);
            genJornadaExtra.setTotal(0);
        }
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(gj.getFecha());
        int numeroSemana = fecha.get(Calendar.WEEK_OF_MONTH);
        if (numeroSemana == 1) {
            genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana1() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana1() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
            return genJornadaExtra;
        }
        if (numeroSemana == 2) {
            genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana2() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana2() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
            return genJornadaExtra;
        }
        if (numeroSemana == 3) {
            genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana3() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana3() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
            return genJornadaExtra;
        }
        if (numeroSemana == 4) {
            genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana4() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana4() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
            return genJornadaExtra;
        }
        if (numeroSemana == 5) {
            genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() + horasExtras);
            genJornadaExtra.setTotal(genJornadaExtra.getTotal() + horasExtras);
            if (genJornadaExtra.getSemana5() > maxHorasExtrasSemana) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana5() * 3600)));
                return null;
            }
            if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                return null;
            }
            return genJornadaExtra;
        }
        return genJornadaExtra;
    }

    public GenericaJornadaExtra calcularGenericaJornadaExtra(GenericaJornada gj) {
        double maxHorasExtrasSemana = genJornadaParam.getHorasExtrasSemanales();
        double maxHorasExtrasMes = genJornadaParam.getHorasExtrasMensuales();
        GenericaJornadaExtra genJornadaExtra = genJornadaExtraEjb.getByEmpleadoAndFecha(gj.getIdEmpleado().getIdEmpleado(), gj.getFecha());
        if (genJornadaExtra == null) {
            Calendar primerDiaMes = Calendar.getInstance();
            Calendar ultimoDiaMes = Calendar.getInstance();
            primerDiaMes.setTime(gj.getFecha());
            ultimoDiaMes.setTime(gj.getFecha());

            primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
            ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));

            genJornadaExtra = new GenericaJornadaExtra();
            genJornadaExtra.setIdEmpleado(gj.getIdEmpleado());
            genJornadaExtra.setEstadoReg(0);
            genJornadaExtra.setUsername(user.getUsername());
            genJornadaExtra.setDesde(primerDiaMes.getTime());
            genJornadaExtra.setHasta(ultimoDiaMes.getTime());
            genJornadaExtra.setCreado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setModificado(MovilidadUtil.fechaCompletaHoy());
            genJornadaExtra.setIdParamArea(pau.getIdParamArea());
            genJornadaExtra.setSemana1(0);
            genJornadaExtra.setSemana2(0);
            genJornadaExtra.setSemana3(0);
            genJornadaExtra.setSemana4(0);
            genJornadaExtra.setSemana5(0);
            genJornadaExtra.setTotal(0);
        }
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(gj.getFecha());
        int numeroSemana = fecha.get(Calendar.WEEK_OF_MONTH);
        horasExtrasInicio = horasExtrasInicio / 3600;
        horasExtrasFin = horasExtrasFin / 3600;
        if (horasExtrasInicio > horasExtrasFin) {
            double dif = horasExtrasInicio - horasExtrasFin;
            if (numeroSemana == 1) {
                genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 2) {
                genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 3) {
                genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 4) {
                genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
            if (numeroSemana == 5) {
                genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() - dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() - dif);
                return genJornadaExtra;
            }
        } else if (horasExtrasInicio < horasExtrasFin) {
            double dif = horasExtrasFin - horasExtrasInicio;
            if (numeroSemana == 1) {
                genJornadaExtra.setSemana1(genJornadaExtra.getSemana1() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana1() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana1() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 2) {
                genJornadaExtra.setSemana2(genJornadaExtra.getSemana2() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana2() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana2() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 3) {
                genJornadaExtra.setSemana3(genJornadaExtra.getSemana3() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana3() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana3() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 4) {
                genJornadaExtra.setSemana4(genJornadaExtra.getSemana4() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana4() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana4() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
            if (numeroSemana == 5) {
                genJornadaExtra.setSemana5(genJornadaExtra.getSemana5() + dif);
                genJornadaExtra.setTotal(genJornadaExtra.getTotal() + dif);
                if (genJornadaExtra.getSemana5() > maxHorasExtrasSemana) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras semanales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getSemana5() * 3600)));
                    return null;
                }
                if (genJornadaExtra.getTotal() > maxHorasExtrasMes) {
                    MovilidadUtil.addErrorMessage("Empleado sobrepasa el numero de horas extras mensuales. Total: " + MovilidadUtil.toTimeSec((int) (genJornadaExtra.getTotal() * 3600)));
                    return null;
                }
                return genJornadaExtra;
            }
        }
        return genJornadaExtra;
    }

    public void validarHoraExtraSemanaAndMensual() {
        mapaEmplJornadaExtra = new HashMap<>();
        int maxHorasSemana = genJornadaParam.getHorasExtrasSemanales();
        int maxHorasMes = genJornadaParam.getHorasExtrasMensuales();
        for (GenericaJornada jornada : listGenericaJornadaPersistir) {
            double horasExtras = getHoraExtrasJornada(jornada);

            if (horasExtras > 0) {
                Calendar primerDiaMes = Calendar.getInstance();
                Calendar ultimoDiaMes = Calendar.getInstance();
                primerDiaMes.setTime(jornada.getFecha());
                ultimoDiaMes.setTime(jornada.getFecha());

                primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
                ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));
                String key = jornada.getIdEmpleado().getIdentificacion() + Util.dateFormat(primerDiaMes.getTime()) + "_" + Util.dateFormat(ultimoDiaMes.getTime());

                if (mapaEmplJornadaExtra.containsKey(key)) {
                    mapaEmplJornadaExtra.put(key, sumarHorasExtra(jornada, mapaEmplJornadaExtra.get(key), horasExtras));
                } else {
                    GenericaJornadaExtra obj = genJornadaExtraEjb.getByEmpleadoAndFecha(jornada.getIdEmpleado().getIdEmpleado(), jornada.getFecha());
                    if (obj != null) {
                        mapaEmplJornadaExtra.put(key, obj);
                        mapaEmplJornadaExtra.put(key, sumarHorasExtra(jornada, mapaEmplJornadaExtra.get(key), horasExtras));
                    } else {
                        GenericaJornadaExtra gje = new GenericaJornadaExtra();
                        gje.setIdEmpleado(jornada.getIdEmpleado());
                        gje.setEstadoReg(0);
                        gje.setUsername(user.getUsername());
                        gje.setDesde(primerDiaMes.getTime());
                        gje.setHasta(ultimoDiaMes.getTime());
                        gje.setCreado(MovilidadUtil.fechaCompletaHoy());
                        gje.setModificado(MovilidadUtil.fechaCompletaHoy());
                        gje.setIdParamArea(pau.getIdParamArea());
                        gje.setSemana1(0);
                        gje.setSemana2(0);
                        gje.setSemana3(0);
                        gje.setSemana4(0);
                        gje.setSemana5(0);
                        gje.setTotal(0);
                        mapaEmplJornadaExtra.put(key, gje);
                        mapaEmplJornadaExtra.put(key, sumarHorasExtra(jornada, mapaEmplJornadaExtra.get(key), horasExtras));
                    }
                }
                boolean b_error = false;
                Errores error = new Errores();
                error.setFecha(Util.dateFormat(jornada.getFecha()));
                error.setServbus(jornada.getIdEmpleado().getIdentificacion() + " - "
                        + jornada.getIdEmpleado().getNombres() + " " + jornada.getIdEmpleado().getApellidos());

                if (maxHorasSemana < mapaEmplJornadaExtra.get(key).getSemana1()) {
                    error.setError("Maximo de horas extras en la semana 1 Superado. Total: "
                            + mapaEmplJornadaExtra.get(key).getSemana1() + ". ");

                }
                if (maxHorasSemana < mapaEmplJornadaExtra.get(key).getSemana2()) {
                    if (error.getError() != null) {
                        error.setError(error.getError() + "Maximo de horas extras en la semana 2 Superado. Total: "
                                + mapaEmplJornadaExtra.get(key).getSemana2() + ". ");
                    } else {
                        error.setError("Maximo de horas extras en la semana 2 Superado. Total: "
                                + mapaEmplJornadaExtra.get(key).getSemana2() + ". ");
                    }
                    b_error = true;

                }
                if (maxHorasSemana < mapaEmplJornadaExtra.get(key).getSemana3()) {
                    if (error.getError() != null) {
                        error.setError(error.getError() + "Maximo de horas extras en la semana 3 Superado. Total: "
                                + mapaEmplJornadaExtra.get(key).getSemana3() + ". ");
                    } else {
                        error.setError("Maximo de horas extras en la semana 3 Superado. Total: "
                                + mapaEmplJornadaExtra.get(key).getSemana3() + ". ");
                    }
                    b_error = true;

                }
                if (maxHorasSemana < mapaEmplJornadaExtra.get(key).getSemana4()) {
                    if (error.getError() != null) {
                        error.setError(error.getError() + "Maximo de horas extras en la semana 4 Superado. Total: "
                                + mapaEmplJornadaExtra.get(key).getSemana4() + ". ");
                    } else {
                        error.setError("Maximo de horas extras en la semana 4 Superado. Total: "
                                + mapaEmplJornadaExtra.get(key).getSemana4() + ". ");
                    }
                    b_error = true;

                }
                if (maxHorasSemana < mapaEmplJornadaExtra.get(key).getSemana5()) {
                    if (error.getError() != null) {
                        error.setError(error.getError() + "Maximo de horas extras en la semana 5 Superado. Total: "
                                + mapaEmplJornadaExtra.get(key).getSemana5() + ". ");
                    } else {
                        error.setError("Maximo de horas extras en la semana 5 Superado. Total: "
                                + mapaEmplJornadaExtra.get(key).getSemana5() + ". ");
                    }
                    b_error = true;

                }
                if (maxHorasMes < mapaEmplJornadaExtra.get(key).getTotal()) {
                    if (error.getError() != null) {
                        error.setError(error.getError() + "Maximo de horas extras al mes superado. Total: "
                                + mapaEmplJornadaExtra.get(key).getTotal() + ". ");
                    } else {
                        error.setError("Maximo de horas extras al mes superado. Total: "
                                + mapaEmplJornadaExtra.get(key).getTotal() + ". ");
                    }
                    b_error = true;

                }
                if (b_error) {
                    listaError.add(error);
                }
            }
        }
    }

    /**
     * Valida que el listado de jornada no sobrepasen el limite de horas extras
     * semanales y mensuales.
     *
     * @param list
     * @return
     */
    public boolean validarHoraExtraSemanaAndMensualAjustarJornadaMasivo(List<GenericaJornada> list) {
        mapaEmplJornadaExtra = new HashMap<>();
        int maxHorasSemana = genJornadaParam.getHorasExtrasSemanales();
        int maxHorasMes = genJornadaParam.getHorasExtrasMensuales();
        for (GenericaJornada jornada : list) {
            double extrasIniciales = horasExtrasByJornada(jornada);
            double ExtrasFinales = MovilidadUtil.toSecs(
                    horaExtras(jornada.getRealTimeOrigin(),
                            jornada.getRealTimeDestiny(),
                            workTimeFlexibleOrdinario(jornada.getSercon(), jornada.getWorkTime()),
                            jornada.getIdGenericaJornadaTipo()));

            if (ExtrasFinales > extrasIniciales) {
                ExtrasFinales = ExtrasFinales - extrasIniciales;
            } else {
                ExtrasFinales = 0;
            }

            if (ExtrasFinales > 0) {
                Calendar primerDiaMes = Calendar.getInstance();
                Calendar ultimoDiaMes = Calendar.getInstance();
                primerDiaMes.setTime(jornada.getFecha());
                ultimoDiaMes.setTime(jornada.getFecha());

                primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
                ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));
                String key = jornada.getIdEmpleado().getIdentificacion() + Util.dateFormat(primerDiaMes.getTime()) + "_" + Util.dateFormat(ultimoDiaMes.getTime());

                if (mapaEmplJornadaExtra.containsKey(key)) {
                    mapaEmplJornadaExtra.put(key, sumarHorasExtra(jornada, mapaEmplJornadaExtra.get(key), ExtrasFinales));
                } else {
                    GenericaJornadaExtra obj = genJornadaExtraEjb.getByEmpleadoAndFecha(jornada.getIdEmpleado().getIdEmpleado(), jornada.getFecha());
                    if (obj != null) {
                        mapaEmplJornadaExtra.put(key, obj);
                        mapaEmplJornadaExtra.put(key, sumarHorasExtra(jornada, mapaEmplJornadaExtra.get(key), ExtrasFinales));
                    } else {
                        GenericaJornadaExtra gje = new GenericaJornadaExtra();
                        gje.setIdEmpleado(jornada.getIdEmpleado());
                        gje.setEstadoReg(0);
                        gje.setUsername(user.getUsername());
                        gje.setDesde(primerDiaMes.getTime());
                        gje.setHasta(ultimoDiaMes.getTime());
                        gje.setCreado(MovilidadUtil.fechaCompletaHoy());
                        gje.setModificado(MovilidadUtil.fechaCompletaHoy());
                        gje.setIdParamArea(pau.getIdParamArea());
                        gje.setSemana1(0);
                        gje.setSemana2(0);
                        gje.setSemana3(0);
                        gje.setSemana4(0);
                        gje.setSemana5(0);
                        gje.setTotal(0);
                        mapaEmplJornadaExtra.put(key, gje);
                        mapaEmplJornadaExtra.put(key, sumarHorasExtra(jornada, mapaEmplJornadaExtra.get(key), ExtrasFinales));
                    }
                }

                if (maxHorasSemana < mapaEmplJornadaExtra.get(key).getSemana1()) {
                    MovilidadUtil.addErrorMessage("Maximo de horas extras en la semana 1 Superado. Total: "
                            + mapaEmplJornadaExtra.get(key).getSemana1());
                    MovilidadUtil.updateComponent("msgs");
                    return true;
                }
                if (maxHorasSemana < mapaEmplJornadaExtra.get(key).getSemana2()) {
                    MovilidadUtil.addErrorMessage("Maximo de horas extras en la semana 2 Superado. Total: "
                            + mapaEmplJornadaExtra.get(key).getSemana2());
                    MovilidadUtil.updateComponent("msgs");
                    return true;

                }
                if (maxHorasSemana < mapaEmplJornadaExtra.get(key).getSemana3()) {
                    MovilidadUtil.addErrorMessage("Maximo de horas extras en la semana 3 Superado. Total: "
                            + mapaEmplJornadaExtra.get(key).getSemana3());
                    MovilidadUtil.updateComponent("msgs");
                    return true;

                }
                if (maxHorasSemana < mapaEmplJornadaExtra.get(key).getSemana4()) {
                    MovilidadUtil.addErrorMessage("Maximo de horas extras en la semana 4 Superado. Total: "
                            + mapaEmplJornadaExtra.get(key).getSemana4());
                    MovilidadUtil.updateComponent("msgs");
                    return true;

                }
                if (maxHorasSemana < mapaEmplJornadaExtra.get(key).getSemana5()) {
                    MovilidadUtil.addErrorMessage("Maximo de horas extras en la semana 5 Superado. Total: "
                            + mapaEmplJornadaExtra.get(key).getSemana5());
                    MovilidadUtil.updateComponent("msgs");
                    return true;

                }
                if (maxHorasMes < mapaEmplJornadaExtra.get(key).getTotal()) {
                    MovilidadUtil.addErrorMessage("Maximo de horas extras al mes superado. Total: "
                            + mapaEmplJornadaExtra.get(key).getTotal());
                    MovilidadUtil.updateComponent("msgs");
                    return true;

                }
            }
        }
        return false;
    }

    public GenericaJornadaExtra sumarHorasExtra(GenericaJornada gj, GenericaJornadaExtra gje, double horasExtras) {
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(gj.getFecha());
        horasExtras = horasExtras / 3600;
        int numeroSemana = fecha.get(Calendar.WEEK_OF_MONTH);
//        System.out.println("Empleado->> " + gj.getIdEmpleado().getIdentificacion());
//        System.out.println("Horas Extras->> " + horasExtras);
//        System.out.println("Semana->> " + numeroSemana);
        if (numeroSemana == 1) {
            gje.setSemana1(gje.getSemana1() + horasExtras);
            gje.setTotal(gje.getTotal() + horasExtras);
            return gje;
        }
        if (numeroSemana == 2) {
            gje.setSemana2(gje.getSemana2() + horasExtras);
            gje.setTotal(gje.getTotal() + horasExtras);
            return gje;
        }
        if (numeroSemana == 3) {
            gje.setSemana3(gje.getSemana3() + horasExtras);
            gje.setTotal(gje.getTotal() + horasExtras);
            return gje;
        }
        if (numeroSemana == 4) {
            gje.setSemana4(gje.getSemana4() + horasExtras);
            gje.setTotal(gje.getTotal() + horasExtras);
            return gje;
        }
        if (numeroSemana == 5) {
            gje.setSemana5(gje.getSemana5() + horasExtras);
            gje.setTotal(gje.getTotal() + horasExtras);
            return gje;
        }
        return gje;
    }

    public int getHoraExtrasJornada(GenericaJornada gj) {
        if (gj.getExtraDiurna() != null && gj.getExtraNocturna() != null
                && gj.getFestivoExtraDiurno() != null && gj.getFestivoExtraNocturno() != null) {
            return MovilidadUtil.toSecs(gj.getExtraDiurna()) + MovilidadUtil.toSecs(gj.getExtraNocturna())
                    + MovilidadUtil.toSecs(gj.getFestivoExtraDiurno()) + MovilidadUtil.toSecs(gj.getFestivoExtraNocturno());
        }
        return 0;
    }

    private List<GenericaJornadaDet> generarDetallesJornada(GenericaJornada genJornada) {
        listGenericaJornadaDet = new ArrayList<>();
        List<ParamReporteHoras> list = paramReporteHorasEJB.findAllActivos(0);

        //<editor-fold defaultstate="collapsed" desc="Aquí se recorre la lista de parametro de reporte de horas.">
        for (ParamReporteHoras prh : list) {
            //Corresponde a recargo diurno
            if (prh.getTipoHora().equals(Util.RPH_DIURNAS)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getDiurnas_time_origin(),
                                Jornada.getDiurnas_time_destiny()
                        )
                );
            }
            //Corresponde a recargo nocturno
            if (prh.getTipoHora().equals(Util.RPH_NOCTURNAS)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getNocturnas_time_origin(),
                                Jornada.getNocturnas_time_destiny()
                        )
                );
            }
            //Corresponde a recargo extra diurno
            if (prh.getTipoHora().equals(Util.RPH_EXTRA_DIURNA)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getExtra_diurna_time_origin(),
                                Jornada.getExtra_diurna_time_destiny()
                        )
                );
            }
            //Corresponde a recargo extra nocturno
            if (prh.getTipoHora().equals(Util.RPH_EXTRA_NOCTURNA)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getExtra_nocturna_time_origin(),
                                Jornada.getExtra_nocturna_time_destiny()
                        )
                );
            }
            //Corresponde a recargo festivo diurno
            if (prh.getTipoHora().equals(Util.RPH_FESTIVO_DIURNO)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getFestivo_diurno_time_origin(),
                                Jornada.getFestivo_diurno_time_destiny()
                        )
                );
            }
            //Corresponde a recargo festivo nocturno
            if (prh.getTipoHora().equals(Util.RPH_FESTIVO_NOCTURNO)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getFestivo_nocturno_time_origin(),
                                Jornada.getFestivo_nocturno_time_destiny()
                        )
                );
            }
            //Corresponde a recargo festivo extra diurno
            if (prh.getTipoHora().equals(Util.RPH_FESTIVO_EXTRA_DIURNO)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getFestivo_extra_diurno_time_origin(),
                                Jornada.getFestivo_extra_diurno_time_destiny()
                        )
                );
            }
            //Corresponde a recargo festivo extra nocturno
            if (prh.getTipoHora().equals(Util.RPH_FESTIVO_EXTRA_NOCTURNO)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getFestivo_extra_nocturno_time_origin(),
                                Jornada.getFestivo_extra_nocturno_time_destiny()
                        )
                );
            }
            //Corresponde a recargo festivo diurno con compensatorio dominical
            if (prh.getTipoHora().equals(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getFestivo_diurno_time_origin(),
                                Jornada.getFestivo_diurno_time_destiny()
                        )
                );
            }
            //Corresponde a recargo festivo nocturno con compensatorio dominical
            if (prh.getTipoHora().equals(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getFestivo_nocturno_time_origin(),
                                Jornada.getFestivo_nocturno_time_destiny()
                        )
                );
            }
            //Corresponde a recargo festivo extra diurno con compensatorio dominical
            if (prh.getTipoHora().equals(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getFestivo_extra_diurno_time_origin(),
                                Jornada.getFestivo_extra_diurno_time_destiny()
                        )
                );
            }
            //Corresponde a recargo festivo extra nocturno con compensatorio dominical
            if (prh.getTipoHora().equals(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO)) {
                listGenericaJornadaDet.add(
                        crearDetalleJornda(
                                prh,
                                genJornada,
                                Jornada.getFestivo_extra_nocturno_time_origin(),
                                Jornada.getFestivo_extra_nocturno_time_destiny()
                        )
                );
            }
        }
        //</editor-fold>

        return listGenericaJornadaDet;
    }

    private GenericaJornadaDet crearDetalleJornda(ParamReporteHoras paramReporte,
            GenericaJornada genericaJornada, String horaIni, String horaFin) {
        GenericaJornadaDet obj = new GenericaJornadaDet();
        obj.setUsername(user.getUsername());
        obj.setCreado(MovilidadUtil.fechaCompletaHoy());
        obj.setEstadoReg(0);
        obj.setIdParamReporte(paramReporte);
        obj.setIdGenericaJornada(genericaJornada);
        obj.setTimeorigin(horaIni);
        obj.setTimedestiny(horaFin);
        String cantidad = MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(horaFin)
                - MovilidadUtil.toSecs(horaIni));
        obj.setCantidad(cantidad);

        return obj;
    }

    boolean validarHorasExtrasFlexible(GenericaJornada jornada, boolean realTime, boolean loadFile) throws ParseException {
        GenericaJornadaLiqUtil param = new GenericaJornadaLiqUtil();
        if (jornada.getIdGenericaJornadaTipo() != null) {
            param.setIdGenericaJornadaTipo(new GenericaJornadaTipoLiqUtil());
            param.getIdGenericaJornadaTipo().setDescanso(jornada.getIdGenericaJornadaTipo().getDescanso());
            param.getIdGenericaJornadaTipo().setTipoCalculo(jornada.getIdGenericaJornadaTipo().getTipoCalculo());
        }
        String iniTurno1 = jornada.getTimeOrigin();
        String finTurno1 = jornada.getTimeDestiny();
        if (realTime) {
            iniTurno1 = jornada.getRealTimeOrigin();
            finTurno1 = jornada.getRealTimeDestiny();
        }
        param.setFecha(jornada.getFecha());
        param.setTimeOrigin(iniTurno1);
        param.setTimeDestiny(finTurno1);
        param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(jornada.getSercon(), jornada.getWorkTime())));
        ErrorPrgSercon validateMaxHrsDia = getJornadaFlexible().validateMaxHrsDia(param);
        if (validateMaxHrsDia.isStatus()) {
            String msg = "Se excedió el máximo de horas extras diarias " + UtilJornada.getMaxHrsExtrasDia() + ". Encontrado "
                    + MovilidadUtil.toTimeSec(validateMaxHrsDia.getHora()) + " " + Util.dateFormat(param.getFecha()) + 
                    (loadFile ?  "\n"+ jornada.getIdEmpleado().getNombresApellidos()+ ", Fecha : "+ Util.dateFormat(param.getFecha()) : "");
            ConfigControlJornada get = UtilJornada.getMaxHrsExtrasDiaObj();
            if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                MovilidadUtil.addErrorMessage(msg);
            } else {
                MovilidadUtil.addAdvertenciaMessage(msg);
            }
            return true;
        }
        return false;
    }

    boolean validarMaxHorasExtrasSmanalesFlexible(GenericaJornada jornada, boolean realTime, boolean loadFile) throws ParseException {
        String iniTurno1 = jornada.getTimeOrigin();
        String finTurno1 = jornada.getTimeDestiny();
        if (realTime) {
            iniTurno1 = jornada.getRealTimeOrigin();
            finTurno1 = jornada.getRealTimeDestiny();
        }
        GenericaJornadaLiqUtil param = new GenericaJornadaLiqUtil();
        if (jornada.getIdGenericaJornadaTipo() != null) {
            param.setIdGenericaJornadaTipo(new GenericaJornadaTipoLiqUtil());
            param.getIdGenericaJornadaTipo().setDescanso(jornada.getIdGenericaJornadaTipo().getDescanso());
            param.getIdGenericaJornadaTipo().setTipoCalculo(jornada.getIdGenericaJornadaTipo().getTipoCalculo());
        }
        param.setTimeOrigin(iniTurno1);
        param.setTimeDestiny(finTurno1);
        param.setFecha(jornada.getFecha());
        param.setIdEmpleado(new EmpleadoLiqUtil(jornada.getIdEmpleado().getIdEmpleado()));
        param.setIdParamArea(new ParamAreaLiqUtil(jornada.getIdParamArea().getIdParamArea()));
        param.setWorkTime(MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(jornada.getSercon(), jornada.getWorkTime())));
        ErrorPrgSercon validateMaxHrsSmnl = getJornadaFlexible().validateMaxHrsSmnl(param);
        if (validateMaxHrsSmnl.isStatus()) {
            String msg = "Se excedió el máximo de horas extras semanales" + UtilJornada.getMaxHrsExtrasSmnl() + ". Encontrado "
                    + MovilidadUtil.toTimeSec(validateMaxHrsSmnl.getHora())+
                    (loadFile ?  "\n"+ jornada.getIdEmpleado().getNombresApellidos()+ ", Fecha : "+ Util.dateFormat(param.getFecha()) : "");

            ConfigControlJornada get = UtilJornada.getMaxHrsExtrasSmnlObj();
            if (get != null && get.getRestringe().equals(ConstantsUtil.ON_INT)) {
                MovilidadUtil.addErrorMessage(msg);
            } else {
                MovilidadUtil.addAdvertenciaMessage(msg);
            }
            return true;
        }
        return false;
    }

    private GenericaJornadaLiqUtil cargarObjetoParaJar(GenericaJornada jornada) {
        String timeOrigin1 = jornada.getTimeOrigin();
        String timeDestiny1 = jornada.getTimeDestiny();
        if (jornada.getAutorizado() != null && jornada.getAutorizado() == 1) {
            timeOrigin1 = jornada.getRealTimeOrigin();
            timeDestiny1 = jornada.getRealTimeDestiny();
        }
        return new GenericaJornadaLiqUtil(timeOrigin1,
                timeDestiny1);
    }

    private void setValueFromPrgSerconJar(GenericaJornadaLiqUtil param1,
            GenericaJornada param2) {
        param2.setDiurnas(param1.getDiurnas());
        param2.setNocturnas(param1.getNocturnas());
        param2.setExtraDiurna(param1.getExtraDiurna());
        param2.setExtraNocturna(param1.getExtraNocturna());
        param2.setFestivoDiurno(param1.getFestivoDiurno());
        param2.setFestivoNocturno(param1.getFestivoNocturno());
        param2.setFestivoExtraDiurno(param1.getFestivoExtraDiurno());
        param2.setFestivoExtraNocturno(param1.getFestivoExtraNocturno());
        param2.setDominicalCompDiurnas(param1.getDominicalCompDiurnas());
        param2.setDominicalCompNocturnas(param1.getDominicalCompNocturnas());
    }

    private String workTimeFlexibleOrdinario(String sercon, String workTime) {
        String workTimeJornada = HORAS_JORNADA;
        if (b_jornada_flexible) {
            workTimeJornada = MovilidadUtil.toTimeSec(UtilJornada.getWorkTimeJornada(sercon, workTime));
        }
        return workTimeJornada;
    }

    public void copiarHoraBio() {
        if (Objects.nonNull(genericaJornada.getBioEntrada())) {
            genericaJornada.setRealTimeOrigin(genericaJornada.getBioEntrada());
        }
        if (Objects.nonNull(genericaJornada.getBioSalida())) {
            genericaJornada.setRealTimeDestiny(genericaJornada.getBioSalida());
        }
    }

    public void agregar() {
        if (validar(selected)) {
            return;
        }
        listaDocsAux.add(selected);
        MovilidadUtil.hideModal("DocuMarcacionCreDlg_wv");
    }

    public boolean validar(MarcacionDocumentosArchivo param) {
        if (param.getMarcacionDocumentos().getIdEmpleado() == null) {
            MovilidadUtil.addErrorMessage("Se debe Seleecionar un Empleado");
            return true;
        }
        if (param.getArchivo() == null) {
            MovilidadUtil.addErrorMessage("Se debe Cargar el documento");
            return true;
        }
        return false;
    }

    public void guardarEdit() throws IOException, ParseException {
        String newPath = "";
        boolean resultDelete = false;

        if (selected.getArchivo() != null) {

            newPath = Util.getProperty("empleadoDocumentos.dir")
                    + selected.getMarcacionDocumentos().getIdEmpleado().getIdEmpleado() + "/";

            if (Util.crearDirectorio(newPath)) {
                newPath = newPath + Util.generarNombre(selected.getArchivo().getFileName());
                Util.saveFile(selected.getArchivo(), newPath, false);
            }
        }
        if (!"".equals(newPath)) {
            resultDelete = MovilidadUtil.eliminarFichero(selected.getMarcacionDocumentos().getPathDocumento());
        }
        selected.getMarcacionDocumentos().setPathDocumento(newPath);
        selected.getMarcacionDocumentos().setUsuario(user.getUsername());
        selected.getMarcacionDocumentos().setModificado(MovilidadUtil.fechaCompletaHoy());
        try {
            this.marcDocEJB.edit(selected.getMarcacionDocumentos());
            MovilidadUtil.addSuccessMessage("Se modificó el registro exitosamente");
            MovilidadUtil.hideModal("DocuMarcacionCreDlg_wv");
        } catch (Exception e) {
            MovilidadUtil.addFatalMessage("Error Interno " + e.getMessage());
        }
    }

    public void prepareCreate() {
        uploadDocumentMB.setUpdateComponent("formRegistrar");
        uploadDocumentMB.setFile(null);
        uploadDocumentMB.setFlag(false);
        uploadDocumentMB.setModal("PF('UploadFileDialog').hide();");
        PrimeFaces current = PrimeFaces.current();
        current.ajax().update("formRegistrar");
        current.executeScript("PF('UploadFileDialog').show();");
    }

    public void handleMarcacionFileUpload(FileUploadEvent event) {
        file = event.getFile();
        String name = Util.generarNombre(file.getFileName());
        if (name.length() > 80) {
            MovilidadUtil.addErrorMessage("El nombre de archivo es muy extenso. Máximo 80 caracteres.");
            MovilidadUtil.updateComponent("formAddDocumentBio:upload_file");
            return;
        }
        selected.setArchivo(file);
        MovilidadUtil.addSuccessMessage("Archivo cargado: " + file.getFileName());
    }

    public void habilitarListaTurnos() {
        if (i_genericaJornadaMotivo == 69) {//cambio de turno
            habilitarTurnos = true;
            cargarTipoJornada();
        } else {
            habilitarTurnos = false;
        }
    }

    public void cambiarTurno() {

    }

    public void autorizarMasivo() throws ParseException {
        try {
            if (!listAutorizar.isEmpty()) {
                for (GenericaJornada obj : listAutorizar) {
                    if (!validarMarcaciones(obj)) {
                        obj.setMarcacionAutorizada(1);
                        obj.setUsernameAuth(user.getUsername());//usuario quien realiza la autorización de la novedad
                        obj.setUsernameGestion(user.getUsername());//usuario quien realiza la gestión de la novedad
                        obj.setFechaAuth(new Date());//fecha autorización con hora
                        obj.setFechaGestion(new Date());//fecha gestión con hora
                        obj.setAutorizado(1);//se autoriza la novedad
                        obj.setMarcacionAutorizada(1);
                        obj.setMarcacionGestionada(1);
                        obj.setModificado(new Date());//fecha de modificación del registro
                        genJornadaEJB.edit(obj);
                    } else {
                        MovilidadUtil.addErrorMessage("Hora Fin no puede ser menor a Hora Inicio");
                    }
                }
                MovilidadUtil.addSuccessMessage("Autorizados Correctamente");
            }
            listaMarcacionesPorAutorizar = getListBioNovedadesPorAutorizar(pau.getIdParamArea().getIdParamArea());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al realizar la autorización masiva");
        }
    }

    public void onToggleSelect(ToggleSelectEvent event) throws ParseException {
        if (!event.isSelected()) {
            listGenericaJornadaSelected.clear();
        }
    }
    
    public void onToggleSelect2(ToggleSelectEvent event) {
        List<GenericaJornada> registrosVisibles = (List<GenericaJornada>) FacesContext.getCurrentInstance()
                .getViewRoot()
                .findComponent("formgestionarmarcacion:novedadeslist")
                .getAttributes()
                .get("filteredValue");

        if (registrosVisibles == null) {
            registrosVisibles = listaNovedadesMarcaciones;
        }

        if (event.isSelected()) {
            for (GenericaJornada novedad : registrosVisibles) {
                if (!listAutorizar.contains(novedad)) {
                    listAutorizar.add(novedad);
                }
            }
        } else {
            // Eliminar los registros visibles de la lista de selección
            listAutorizar.removeAll(registrosVisibles);
        }
    }

    public List<GenericaJornada> getListGenericaJornada() {
        return listGenericaJornada;
    }

    public boolean isB_autoriza() {
        return b_autoriza;
    }

    public void setB_autoriza(boolean b_autoriza) {
        this.b_autoriza = b_autoriza;
    }

    public boolean isB_genera() {
        return b_genera;
    }

    public void setB_genera(boolean b_genera) {
        this.b_genera = b_genera;
    }

    public boolean isB_liquida() {
        return b_liquida;
    }

    public void setB_liquida(boolean b_liquida) {
        this.b_liquida = b_liquida;
    }

    public boolean isB_controlLiquida() {
        return b_controlLiquida;
    }

    public void setB_controlLiquida(boolean b_controlLiquida) {
        this.b_controlLiquida = b_controlLiquida;
    }

    public boolean isB_controlAutoriza() {
        return b_controlAutoriza;
    }

    public void setB_controlAutoriza(boolean b_controlAutoriza) {
        this.b_controlAutoriza = b_controlAutoriza;
    }

    public GenericaJornada getGenericaJornada() {
        return genericaJornada;
    }

    public void setGenericaJornada(GenericaJornada genericaJornada) {
        this.genericaJornada = genericaJornada;
    }

    public List<GenericaJornadaMotivo> getGenericaJornadaMotivoList() {
        return genericaJornadaMotivoList;
    }

    public Integer getI_genericaJornadaMotivo() {
        return i_genericaJornadaMotivo;
    }

    public void setI_genericaJornadaMotivo(Integer i_genericaJornadaMotivo) {
        this.i_genericaJornadaMotivo = i_genericaJornadaMotivo;
    }

    public boolean isB_controlSubirArchivo() {
        return b_controlSubirArchivo;
    }

    public void setB_controlSubirArchivo(boolean b_controlSubirArchivo) {
        this.b_controlSubirArchivo = b_controlSubirArchivo;
    }

    public GenericaJornada getGenericaJornadaNew() {
        return genericaJornadaNew;
    }

    public void setGenericaJornadaNew(GenericaJornada genericaJornadaNew) {
        this.genericaJornadaNew = genericaJornadaNew;
    }

    public String getEmpleadoLabel() {
        return empleadoLabel;
    }

    public void setEmpleadoLabel(String empleadoLabel) {
        this.empleadoLabel = empleadoLabel;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Date getFechaDesdeBM() {
        return fechaDesdeBM;
    }

    public void setFechaDesdeBM(Date fechaDesdeBM) {
        this.fechaDesdeBM = fechaDesdeBM;
    }

    public Date getFechaHastaBM() {
        return fechaHastaBM;
    }

    public void setFechaHastaBM(Date fechaHastaBM) {
        this.fechaHastaBM = fechaHastaBM;
    }

    public String getObservacionesBM() {
        return observacionesBM;
    }

    public void setObservacionesBM(String observacionesBM) {
        this.observacionesBM = observacionesBM;
    }

    public boolean isB_generaDelete() {
        return b_generaDelete;
    }

    public void setB_generaDelete(boolean b_generaDelete) {
        this.b_generaDelete = b_generaDelete;
    }

    public boolean isB_permiso() {
        return b_permiso;
    }

    public void setB_permiso(boolean b_permiso) {
        this.b_permiso = b_permiso;
    }

    public boolean isB_autorizaMarcaciones() {
        return b_autorizaMarcaciones;
    }

    public void setB_autorizaMarcaciones(boolean b_autorizaMarcaciones) {
        this.b_autorizaMarcaciones = b_autorizaMarcaciones;
    }

    public List<GenericaJornada> getListGenericaJornadaPersistir() {
        return listGenericaJornadaPersistir;
    }

    public void setListGenericaJornadaPersistir(List<GenericaJornada> listGenericaJornadaPersistir) {
        this.listGenericaJornadaPersistir = listGenericaJornadaPersistir;
    }

    public List<Errores> getListaError() {
        return listaError;
    }

    public void setListaError(List<Errores> listaError) {
        this.listaError = listaError;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public Empleado getEmpl() {
        return empl;
    }

    public void setEmpl(Empleado empl) {
        this.empl = empl;
    }

    public boolean isFlag_cargar_jornada() {
        return flag_cargar_jornada;
    }

    public void setFlag_cargar_jornada(boolean flag_cargar_jornada) {
        this.flag_cargar_jornada = flag_cargar_jornada;
    }

    public boolean isFlag_cargar_novedades() {
        return flag_cargar_novedades;
    }

    public void setFlag_cargar_novedades(boolean flag_cargar_novedades) {
        this.flag_cargar_novedades = flag_cargar_novedades;
    }

    public boolean isB_orden_tbj() {
        return b_orden_tbj;
    }

    public void setB_orden_tbj(boolean b_orden_tbj) {
        this.b_orden_tbj = b_orden_tbj;
    }

    public String getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(String ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public String getRealTimeOrigin() {
        return realTimeOrigin;
    }

    public void setRealTimeOrigin(String realTimeOrigin) {
        this.realTimeOrigin = realTimeOrigin;
    }

    public String getRealTimeDestiny() {
        return realTimeDestiny;
    }

    public void setRealTimeDestiny(String realTimeDestiny) {
        this.realTimeDestiny = realTimeDestiny;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<GenericaJornada> getListaNovedadesMarcaciones() {
        return listaNovedadesMarcaciones;
    }

    public void setListaNovedadesMarcaciones(List<GenericaJornada> listaNovedadesMarcaciones) {
        this.listaNovedadesMarcaciones = listaNovedadesMarcaciones;
    }

    public List<GenericaJornada> getListaNovedadesMarcacionesHistorico() {
        return listaNovedadesMarcacionesHistorico;
    }

    public void setListaNovedadesMarcacionesHistorico(List<GenericaJornada> listaNovedadesMarcacionesHistorico) {
        this.listaNovedadesMarcacionesHistorico = listaNovedadesMarcacionesHistorico;
    }

    public List<GenericaJornada> getListaMarcacionesPorAutorizar() {
        return listaMarcacionesPorAutorizar;
    }

    public void setListaMarcacionesPorAutorizar(List<GenericaJornada> listaMarcacionesPorAutorizar) {
        this.listaMarcacionesPorAutorizar = listaMarcacionesPorAutorizar;
    }

    public List<GenericaJornada> getListaMarcacionesOK() {
        return listaMarcacionesOK;
    }

    public void setListaMarcacionesOK(List<GenericaJornada> listaMarcacionesOK) {
        this.listaMarcacionesOK = listaMarcacionesOK;
    }

    public List<MarcacionDocumentosArchivo> getListaDocsAux() {
        return listaDocsAux;
    }

    public void setListaDocsAux(List<MarcacionDocumentosArchivo> listaDocsAux) {
        this.listaDocsAux = listaDocsAux;
    }

    public MarcacionDocumentosArchivo getSelected() {
        return selected;
    }

    public void setSelected(MarcacionDocumentosArchivo selected) {
        this.selected = selected;
    }

    public List<MarcacionDocumentos> getListMarcaDoc() {
        return listMarcaDoc;
    }

    public void setListMarcaDoc(List<MarcacionDocumentos> listMarcaDoc) {
        this.listMarcaDoc = listMarcaDoc;
    }

    public List<MarcacionDocumentosArchivo> getListDocumentos() {
        return listDocumentos;
    }

    public void setListDocumentos(List<MarcacionDocumentosArchivo> listDocumentos) {
        this.listDocumentos = listDocumentos;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public GenericaJornadaTipoFacadeLocal getJornadaTipoEJB() {
        return jornadaTipoEJB;
    }

    public void setJornadaTipoEJB(GenericaJornadaTipoFacadeLocal jornadaTipoEJB) {
        this.jornadaTipoEJB = jornadaTipoEJB;
    }

    public List<GenericaJornadaTipo> getGenericaJornadaTipoList() {
        return genericaJornadaTipoList;
    }

    public void setGenericaJornadaTipoList(List<GenericaJornadaTipo> genericaJornadaTipoList) {
        this.genericaJornadaTipoList = genericaJornadaTipoList;
    }

    public Integer getI_genericaJornadaTipo() {
        return i_genericaJornadaTipo;
    }

    public void setI_genericaJornadaTipo(Integer i_genericaJornadaTipo) {
        this.i_genericaJornadaTipo = i_genericaJornadaTipo;
    }

    public UploadDocumentJSFManagedBean getUploadDocumentMB() {
        return uploadDocumentMB;
    }

    public void setUploadDocumentMB(UploadDocumentJSFManagedBean uploadDocumentMB) {
        this.uploadDocumentMB = uploadDocumentMB;
    }

    public boolean isHabilitarTurnos() {
        return habilitarTurnos;
    }

    public void setHabilitarTurnos(boolean habilitarTurnos) {
        this.habilitarTurnos = habilitarTurnos;
    }

    public List<GenericaJornada> getListGenericaJornadaSelected() {
        return listGenericaJornadaSelected;
    }

    public void setListGenericaJornadaSelected(List<GenericaJornada> listGenericaJornadaSelected) {
        this.listGenericaJornadaSelected = listGenericaJornadaSelected;
    }

    public GenericaJornada getGenericaJornadaFilter() {
        return genericaJornadaFilter;
    }

    public void setGenericaJornadaFilter(GenericaJornada genericaJornadaFilter) {
        this.genericaJornadaFilter = genericaJornadaFilter;
    }

    public boolean isB_novFromFile() {
        return b_novFromFile;
    }

    public void setB_novFromFile(boolean b_novFromFile) {
        this.b_novFromFile = b_novFromFile;
    }    
        
    public List<GenericaJornada> getListAutorizar() {
        return listAutorizar;
    }

    public void setListAutorizar(List<GenericaJornada> listAutorizar) {
        this.listAutorizar = listAutorizar;
    }
}
