/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigFacadeLocal;
import com.movilidad.ejb.DispClasificacionNovedadFacadeLocal;
import com.movilidad.ejb.DispSistemaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GestionVehiculoFacadeLocal;
import com.movilidad.ejb.GopCierreTurnoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTelegramDetFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadPrgTcFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.ejb.ParamCierreAusentismoFacadeLocal;
import com.movilidad.ejb.PrgPatternFacadeLocal;
import com.movilidad.ejb.PrgRouteFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.ejb.PrgTareaFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.PrgTcResponsableFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoViaFacadeLocal;
import com.movilidad.model.ArbolViewPrgTc;
import com.movilidad.model.DispClasificacionNovedad;
import com.movilidad.model.DispEstadoPendActual;
import com.movilidad.model.DispSistema;
import com.movilidad.model.Empleado;
import com.movilidad.model.GestionVehiculo;
import com.movilidad.model.GopCierreTurno;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTelegramDet;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadPrgTc;
import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.ParamCierreAusentismo;
import com.movilidad.model.PrgClasificacionMotivo;
import com.movilidad.model.PrgPattern;
import com.movilidad.model.PrgRoute;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgTarea;
import com.movilidad.model.PrgTc;
import com.movilidad.model.PrgTcResponsable;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoVia;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.CurrentLocation;
import com.movilidad.util.beans.GeocodingDTO;
import com.movilidad.util.beans.SerconList;
import com.movilidad.util.beans.VehiculosDetenidosDTO;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.SortPrgTcByTimeOrigin;
import com.movilidad.utils.Util;
import com.movilidad.utils.UtilJornada;
import com.movlidad.httpUtil.GeoService;
import com.movlidad.httpUtil.SenderNotificacionTelegram;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.json.JSONObject;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Entidad principal afectada PrgTc. Otras entidades afectadas: Novedad,
 * NovedadPrgTc, Accidente, AccidenteConductor, AccidenteVehiculo,
 * PrgAsignacion.
 *
 * @author Soluciones IT
 */
@Named(value = "prgTcJSFMB")
@ViewScoped
public class PrgTcJSFManagedBean implements Serializable {

    private PrgTc prgTc;
    private PrgTc prgTcSinOperador;
    private PrgTc prgTcOpDispo;
    private PrgTc prgTcNewService;
    private PrgTc prgTcVacOrVaccom;
    private PrgTc prgTcConServbus = null;
    private List<PrgTc> listPrgTc;
    private List<GestionVehiculo> listGestionVehiculo;
    private List<PrgTc> listPrgTcFilter;
    private List<PrgTc> listNovedadPrgTc;
    private PrgTc prgTcUnoAUno;
    private List<PrgTc> listPrgTcGestionVehiculo;
    private List<ArbolViewPrgTc> listPrgTcGestionEliminar;
    private List<PrgTc> listPrgTcGestionOperador;
    private List<PrgTc> listDesasignarOpServNull;
    private List<ArbolViewPrgTc> listPrgTcGestionEliminarAux;
    private List<PrgTc> listPrgTcAux = new ArrayList<>();
    private List<PrgTarea> listPrgTarea;
    private List<PrgRoute> listPrgRoute;
    private List<PrgPattern> listPrgPattern;
    private List<PrgStopPoint> listPrgStopPoint;
    private List<PrgStopPoint> listPrgStopPointByLinea;
//    private List<String> listDate;
    List<PrgTcResponsable> lstResponsable;
    List<PrgClasificacionMotivo> lstClasificacion;
    private List<NovedadTipoDetalles> lstDetallesAccesoRapido;
    private PrgStopPoint prgStopPoint;
    private Date fecha;
    private int tabla = 1;
    //Variables para la asignación de vehículo
    private Vehiculo vehiculoExist;
    private boolean b_validacion = true;
    private boolean propia = false;
    private boolean flagPropia = true;
    private boolean tokenSave = false;
    private Vehiculo VehiculoDisponible;
//    private Accidente accidente;
//    private AccidenteVehiculo accidenteVehiculo;
//    private AccidenteConductor accidenteConductor;
    private Empleado emplPrgTcSinOperador;
    // Parametros configurables bundle
    private int interval;
//    private boolean flag_submit_I = false;
//    private boolean flag_submit_II = false;
//    private boolean flag_submit_III = false;
    private boolean b_afectaPrgTc;
    private boolean b_isNovAccidente = false;
    private boolean inmovilizado;

    private PrgStopPoint lugarTQ = null;
    private String i_tabla = "";
    private String i_servicio = "";
    private String servBus = "";
    private String vehiculoC = "";
    private String codigoTm = "";
    private String codigoTmVac = "";
    private String codigoV = "";
    private String codigoOperador = "";
    private String sercon = "";
    private String destino = "";
    private String destinoVacio = "";
    private String observacionesNewService;
    private String observacionesPrgTc = "";
    private String codTMPrgTc = "";
    private String color = "";
    private String colorSinBus = "";
    private String viewCodVehiculo = "";
    private String viewPlaca = "";
    private String direccionAcc = "";
    private Date fechaConsulta;
    private int i_detalle = 0;
    private int i_tipoNovedad = 0;
    private int i_tipoNovedaDet = 0;
    private int entradaSalida = 0;
    private NovedadTipo i_tipoNovedadObj;
    private NovedadTipoDetalles i_tipoNovedaDetObj;
    private int i_codResponsable = 0;
    private Integer i_idTarea;
    private PrgTarea i_idTareaNewService;
    private PrgPattern idPuntoIni;
    private PrgStopPoint i_idPuntoIniStopPoint;
    private PrgStopPoint i_idPuntoFinStopPoint;
    private PrgPattern idPuntoFin;
    private Integer i_idPuntoIniInt = 0;
    private Integer i_idPuntoFinInt = 0;
    private int i_idRuta = 0;
    private int tipoVacio = 2;
    private int i_idClasificacion = 0;
    private String horaInicio_string;
    private String horaFin_string;
    private String horaFinString = "";
    private String horaInicioString = "";
    private String serconString;
    private String servbusString = "";
    private String punto_inicioString;
    private String punto_finString;
    private String entrada;
    private String salida;
    private String urlMapGeo;

    private PrgStopPoint entradaStopPoint;
    private PrgStopPoint salidaStopPoint;
    private boolean flagOpkmCl = false;
    private boolean flagVhcl = false;
    private boolean flagOpkmClOp = false;
    private boolean flagVhclOp = false;
    private boolean unoAUnoOperador = false;
    private boolean unoAUnoVehiculo = true;
    private boolean eliminarFromCabecera = false;
    private boolean vacio = false;
    private boolean flagVehiculo = false;
    private boolean flagOperador = false;
    private boolean flagSinOperador = false;
    private boolean flagEliminar = false;
    private boolean flagCambioHora = false;
    private boolean flagRuta = true;
    private boolean flagFromService = false;
    private boolean flagPInicioPFin = false;
    private boolean flagRol = false;
    private boolean flagGuardarTodo = false;
    private boolean flagConciliado = false;
    private boolean flagTurnoCerrado = false;
    private boolean flagAsignarOperador = false;
    private boolean flagAsignarVehiculo = false;
    private boolean flagEliminarXOperador = false;
    private boolean flagEliminarXVehiculo = false;
    private boolean flagAddServicios = false;
    private boolean flagGestionServicios = false;
    private boolean flagBotonesGestionServicio = false;
    private boolean flagUserTC = false;

    private boolean flagViewOption = true;
    private boolean flagViewVerRecorrido = true;
    private int componente;

    private Novedad novedad;
    private PrgTc prgTcOperadorDisponible;
    private Vehiculo vehiculo;
    private Vehiculo vehiculoNewService;
    private PrgTc prgTcForGetOperador;
    private Integer idGopUnidadFuncional;

    private Vehiculo vehiculoParaNov;
    private Empleado empleadoParaNov;
    private Vehiculo vehiculoParaNovNew;
    private Empleado empleadoParaNovNew;
    private List<NovedadTipo> listNovedadT;
    private List<NovedadTipoDetalles> listNovedadTDet;

    private TreeNode rootOperador = null;
    private TreeNode rootSinOperador = null;
    private TreeNode rootVehiculo = null;
    private TreeNode rootEliminar = null;
    private TreeNode[] checkboxSelectedNodesOperador;
    private TreeNode[] checkboxSelectedNodesEliminar;
    private TreeNode[] checkboxSelectedNodesVehiculo;

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    // Agregando una lista nueva
    private List<PrgTc> listaSinBus;
    private List<PrgTc> listaSinOperador;
    private List<PrgTc> listaDisponibles;
    private List<Vehiculo> listaDisponiblesV;

    @EJB
    private PrgTcFacadeLocal prgTcEJB;

    @EJB
    private GestionVehiculoFacadeLocal gestionVehiculoFacadeLocal;

    @EJB
    private PrgTareaFacadeLocal prgTareaEJB;
    @EJB
    private NovedadTipoFacadeLocal novedadTEJB;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTDetEJB;
    @EJB
    private NovedadFacadeLocal novEJB;
    @EJB
    private VehiculoFacadeLocal vehEJB;
    @EJB
    private PrgRouteFacadeLocal prgRoute;
    @EJB
    private PrgTcResponsableFacadeLocal prgTcRespEJB;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private EmpleadoFacadeLocal emplEjb;
    @EJB
    private PrgStopPointFacadeLocal prgSPEJB;
//    @EJB
//    private AccidenteFacadeLocal accidenteFacadeLocal;
//    @EJB
//    private AccidenteConductorFacadeLocal accidenteConductorFacadeLocal;
//    @EJB
//    private AccidenteVehiculoFacadeLocal accidenteVehiculoFacadeLocal;
    @EJB
    private NovedadPrgTcFacadeLocal NovPrgTcEJB;
    @EJB
    private PrgPatternFacadeLocal prgPatternEJB;
    @EJB
    private ConfigFacadeLocal configEJB;
    @EJB
    private DispClasificacionNovedadFacadeLocal clasificacionNovedadEJB;
    @EJB
    private GopCierreTurnoFacadeLocal gopCierreTurnoEJB;

    @EJB
    private NotificacionTelegramDetFacadeLocal notificacionTelegramDetEjb;
    @EJB
    private DispSistemaFacadeLocal dispSistemaEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesoEjb;

    @EJB
    private ParamCierreAusentismoFacadeLocal paramCierreAusentismoEjb;

    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;

    @EJB
    private VehiculoViaFacadeLocal vehiculoViaFacadeLocal;
    
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetEjb;
    
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private SelectGopUnidadFuncionalBean selectGopUnidadFuncionalBean;

    @Inject
    private EmpleadoListJSFManagedBean empleadoListJSFManagedBean;

    @Inject
    private VehiculoEstadoHistoricoSaveBean vehiculoEstadoHistoricoSaveBean;
    @Inject
    private SelectDispSistemaBean selectDispSistemaBean;

    @Inject
    private EmpleadosJSFManagedBean emplJSFMB;
    @Inject
    private NovedadDuplicadaBean novedadDuplicadaBean;
    @Inject
    private ValidarDocumentacionBean validarDocumentacionBean;
//    @Inject
//    private AccidenteJSF accidenteBean;
    @Inject
    private AccPreManagedBean accpreMB;
    @Inject
    private ValidarFinOperacionBean validarFinOperacionBean;
    @Inject
    private AtvNovedadBean atvNovedadBean;
    @Inject
    private MapGeoBean mapGeoBean;
    @Inject
    private AjusteJornadaFromGestionServicio ajusteJornadaFromGestionServicio;
    @Inject
    private GestorVehiculosDisponiblesJSF gestorVehiculosDisponiblesJSF;
    @Inject
    private AsignarBusToServbusBean asignarBusToServbusBean;

    /**
     * responsable de verificar si las novedades que tenga tipo detalle novedad
     * que afecten programa master se guarden como proceden si o no.
     *
     * @return retorna valor boolean que se utiliza antes de persisitir una
     * novedad
     */
    public boolean procedeNovedad() {
        try {
            return configEJB.findByKey("nov").getValue() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    //agregado para iniciar la carga de los servicios sin operador y sin bus
    @PostConstruct
    public void init() {
        fechaConsulta = MovilidadUtil.fechaHoy();

        listarVehiculoDispo();
        flagRol = validarRol();
        validarDiaConsiliado();
        validarCierreTurno();
        listarSinBus();
        listarSinOperador();
    }

    public void listarSinOperador() {
        listaSinOperador = prgTcEJB.serviceSinOpe(MovilidadUtil.fechaHoy(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void listarSinBus() {
//        System.out.println("-----------");
        listaSinBus = prgTcEJB.serviceSinBus(MovilidadUtil.fechaHoy(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void validarDiaConsiliado() {
        //Valida que la fecha seleccionada no este conciliada
        flagConciliado = validarFinOperacionBean.validarDiaBloqueado(fechaConsulta,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void validarCierreTurno() {
        //Valida si el cierre de turno inmediatamente anterior es del mismo usuario en sesión
        GopCierreTurno findLastGopCierreTurno = gopCierreTurnoEJB.findLastGopCierreTurno(fechaConsulta,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        flagTurnoCerrado = findLastGopCierreTurno == null ? false : findLastGopCierreTurno.getUserTecControl().equals(user.getUsername());
    }

    /**
     * Carga las lista de Vehículos disponibles visualisados en el panel
     * principal
     */
    public void listarVehiculoDispo() {
//        long startTime = System.nanoTime();
        listaDisponiblesV = vehEJB.getDisponibles(MovilidadUtil.fechaHoy(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        gestorVehiculosDisponiblesJSF.obtenerCargasBateria();
//        long endTime = System.nanoTime();
//        setear los vehiculos en vía
        List<VehiculoVia> listVehiculoVia = vehiculoViaFacadeLocal
                .findByIdUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        // idVehiculo, VehiculoVia
        Map<Integer, VehiculoVia> mapVehiculoVia = listVehiculoVia
                .stream()
                .parallel()
                .collect(Collectors
                        .toMap(VehiculoVia::getvehiculoId, Function.identity()));
        listaDisponiblesV.stream()
                .parallel()
                .forEach(v -> {
                    if (mapVehiculoVia.containsKey(v.getIdVehiculo())) {
                        VehiculoVia vehiculoVía = mapVehiculoVia.get(v.getIdVehiculo());
                        if (vehiculoVía.isOnRoad()) {
                            v.setVehiculoVia(vehiculoVía);
                        }
                    }
                });
//        System.out.println("tiempo->" + ((endTime - startTime) / 1e6) + " ms");
    }

    /**
     * Carga las lista de los servicio si Vehículo y sin operador que son
     * visualidasos en el panel principal
     */
    public void cargar() {
        listaSinBus = prgTcEJB.serviceSinBus(MovilidadUtil.fechaHoy(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listaSinOperador = prgTcEJB.serviceSinOpe(MovilidadUtil.fechaHoy(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    /**
     * Carga las lista de operadores disponibles visualisados en el panel
     * principal
     *
     * @param fecha
     */
    public void cargarDisponibles() {
        listaDisponibles = prgTcEJB.operadoresDisponibles(MovilidadUtil.fechaHoy(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
        );
    }

//    /*
//     * Método que se encarga de obtener el id de la unidad funcional en base
//     * al nombre del usuario logueado, devuelve un número si el usuario tiene 
//     * unidad funcional asociada, de lo contrario devuelve NULL
//     */
//    private Integer obtenerUnidadFuncional() {
//        Users usuario = usersEjb.findUserForUsername(user.getUsername());
//
//        if (usuario.getIdGopUnidadFuncional() != null) {
//            return usuario.getIdGopUnidadFuncional().getIdGopUnidadFuncional();
//        }
//
//        return null;
//
//    }
    /**
     * Creates a new instance of PrgTcJSFManagedBean
     */
    public PrgTcJSFManagedBean() {

    }

    /**
     * Este método sirve para cargar los servicios seleccionados en vista para
     * la gestion de Servicio-operador
     *
     * @param event, este objeto contiene la data de ArbolViewPrgTc y este
     * ultimo contiene un servicio o prgTc y viene de la vista gestionPrgTc.
     * @throws ParseException
     */
    public void onNodeSelectOperador(NodeSelectEvent event) throws ParseException {
        if (event.getTreeNode().getData() instanceof ArbolViewPrgTc) {
            ArbolViewPrgTc tree = (ArbolViewPrgTc) event.getTreeNode().getData();
            /**
             * El nivel indica que hijo del arbol se ha seleccionado.
             */
            if (tree.getNivel() == 1) {
                for (TreeNode tn : event.getTreeNode().getChildren()) {
                    ArbolViewPrgTc at = (ArbolViewPrgTc) tn.getData();
                    listPrgTcGestionOperador.remove(at.getObjPrgTc());
                    listDesasignarOpServNull.remove(at.getObjPrgTc());
                    listPrgTcGestionOperador.add(at.getObjPrgTc());
                }
            } else if (tree.getNivel() == 2) {
                listPrgTcGestionOperador.remove(tree.getObjPrgTc());
                listPrgTcGestionOperador.add(tree.getObjPrgTc());
                listDesasignarOpServNull.remove(tree.getObjPrgTc());
            }
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                ajusteJornadaFromGestionServicio.setListDesasignarOpServNull(listDesasignarOpServNull);
                ajusteJornadaFromGestionServicio.setListPrgTcGestionOperador(listPrgTcGestionOperador);

                if (prgTcOperadorDisponible != null) {
                    ajusteJornadaFromGestionServicio.recrearJornadaFound("formGestionPrgTc:tabViews:turnoEmplSelected");
                    ajusteJornadaFromGestionServicio.ajusteJornadasFoundII(listPrgTcGestionOperador, "formGestionPrgTc:tabViews:turnoEmplFound");
                    ajusteJornadaFromGestionServicio.validarMaxHorasExtrasSmanales();
                }
//                ajusteJornadaFromGestionServicio.ajusteJornadasSelected(listPrgTcGestionOperador,
//                        listDesasignarOpServNull, tree.getObjPrgTc().getIdEmpleado(),
//                        disponibleAlCambio, desasignarOpeServNull, fechaConsulta);
            }
            ajusteJornadaFromGestionServicio.recrearJornadaSelected();
        }
    }

    /**
     * Este método sirve para cargar los servicios sin operador seleccionados en
     * vista para la gestion de asignación de operador en servicios sin operador
     *
     * @param event este objeto contiene la data de ArbolViewPrgTc y este ultimo
     * contiene un servicio o prgTc y viene de la vista gestionPrgTc.
     * @throws ParseException
     */
    public void onNodeSelectSinOperador(NodeSelectEvent event) throws ParseException {
        if (event.getTreeNode().getData() instanceof ArbolViewPrgTc) {
            ArbolViewPrgTc tree = (ArbolViewPrgTc) event.getTreeNode().getData();
            /**
             * El nivel indica que hijo del arbol se ha seleccionado.
             */
            if (tree.getNivel() == 1) {
                for (TreeNode tn : event.getTreeNode().getChildren()) {
                    ArbolViewPrgTc at = (ArbolViewPrgTc) tn.getData();
                    listPrgTcGestionOperador.remove(at.getObjPrgTc());
                    listPrgTcGestionOperador.add(at.getObjPrgTc());
                }
            } else if (tree.getNivel() == 2) {
                listPrgTcGestionOperador.remove(tree.getObjPrgTc());
                listPrgTcGestionOperador.add(tree.getObjPrgTc());
            }
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                ajusteJornadaFromGestionServicio.setListPrgTcGestionOperador(listPrgTcGestionOperador);
                if (prgTcOperadorDisponible != null) {
                    ajusteJornadaFromGestionServicio.recrearJornadaFound("formGestionPrgTc:tabViews:turnoEmplFoundII");
                    ajusteJornadaFromGestionServicio.ajusteJornadasFoundII(listPrgTcGestionOperador, "formGestionPrgTc:tabViews:turnoEmplFoundII");
                    ajusteJornadaFromGestionServicio.validarMaxHorasExtrasSmanales();
                }
            }
        }
    }

    /**
     * Permite consultar un vehículo para realizar la asignación en servicio sin
     * Vehículo desde el panel pricipal. codigoV se suministra desde la ventana
     * de asignación, para la asignación de servbus a vehículo.
     */
    public void findVehiculo() {
        if (!MovilidadUtil.stringIsEmpty(codigoV)) {
            vehiculoExist = vehEJB.findVehiculoExist(codigoV,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (vehiculoExist == null) {
                MovilidadUtil.addErrorMessage(ConstantsUtil.NO_EXISTE_VEHICULO);
                return;
            }
            if (!vehiculoExist.getIdVehiculoTipoEstado().getIdVehiculoTipoEstado().equals(ConstantsUtil.ON_INT)) {
                vehiculoExist = null;
                MovilidadUtil.addErrorMessage(ConstantsUtil.INOPERATIVO_VEHICULO);
                return;
            }
            boolean validarDocuVhiculo = validarDocumentacionBean.validarDocuVhiculo(fechaConsulta, vehiculoExist.getIdVehiculo());
            if (validarDocuVhiculo) {
                MovilidadUtil.addErrorMessage("El vehículo tiene documentos vencidos");
                vehiculoExist = null;
                return;
            }
            PrgTc vAsignar = prgTcEJB.findVehiculoAsignar(vehiculoExist.getIdVehiculo(), fechaConsulta);
            if (vAsignar == null) {
                MovilidadUtil.addErrorMessage("Vehículo ya tiene programación asignada");
                vehiculoExist = null;
            } else {
                MovilidadUtil.addSuccessMessage("Vehículo listo para recibir asignación");
            }
        }
    }

    public void borrarCampos() {
        i_tabla = "";
        i_servicio = "";
        servBus = "";
        vehiculoC = "";
        codigoOperador = "";
        sercon = "";
    }

    /**
     * Método responsable de preparar las variables para la asignación de
     * operadores a servicios sin operador. Invoca al método responsable de
     * armar el arbol para la presentación de los datos en la ventana de gestión
     * de servicios del panel principal
     *
     * @throws ParseException
     */
    public void prgAsignarOperador() throws ParseException {
        /**
         * Validar que el servicio no este conciliado
         */
        if (flagConciliado) {
            MovilidadUtil.addErrorMessage("Día Bloqueado.");
            return;
        }
        validarCierreTurno();
        if (flagTurnoCerrado) {
            MovilidadUtil.addErrorMessage("Turno Cerrado.");
            return;
        }
        resest();
        if (prgTcSinOperador == null) {
            MovilidadUtil.addErrorMessage("Seleccione un servicio en la tabla");
            return;
        }
        flagSinOperador = true;
        flagOperador = false;
        flagVehiculo = false;
        flagEliminar = false;
//        flag_submit_II = true;
        flagCambioHora = false;
        flagGuardarTodo = false;
        unoAUnoOperador = false;
        emplPrgTcSinOperador = null;
        prgTcOperadorDisponible = null;
        listPrgTcGestionOperador = new ArrayList<>();
        listPrgTcGestionVehiculo = new ArrayList<>();
        listPrgTcGestionEliminar = new ArrayList<>();
        listPrgTcGestionEliminarAux = new ArrayList<>();
        novedad = new Novedad();
        rootSinOperador = new DefaultTreeNode("Operador", null);
        b_afectaPrgTc = b_isNovAccidente = false;
        flagGestionServicios = true;
        /**
         * Armar arbol de servicios sin operador
         */
        armarArbolPrgTcSinOperador();
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
            ajusteJornadaFromGestionServicio.setFecha(prgTcSinOperador.getFecha());
            ajusteJornadaFromGestionServicio.resetHorasFound();
        }
        cargarResponsable();
        PrimeFaces.current().executeScript("PF('gestionPrgTcDialog').show()");
    }

    /**
     * Método responsable de preparar las variables para la asignación de
     * vehículos a servicios sin vehículo.
     *
     * @throws ParseException
     */
    public void prgAsignarVehiculo() throws ParseException {
        /**
         * Validar que el servicio no este conciliado
         */
        if (flagConciliado) {
            MovilidadUtil.addErrorMessage("Día Bloqueado.");
            return;
        }
        validarCierreTurno();
        if (flagTurnoCerrado) {
            MovilidadUtil.addErrorMessage("Turno Cerrado.");
            return;
        }
        resest();
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Seleccione un servicio en la tabla");
            return;
        }
        if (prgTc.getServbus() == null) {
            MovilidadUtil.addErrorMessage("Seleccione un servicio en la tabla");
            return;
        }

        if (validaConciliarAndServicioSeleccionado()) {
            return;
        }
        MovilidadUtil.openModal("wvConfirmDialogAsignarVehiculo");
    }

    public void prgAsignarVehiculoConfirm(boolean flag) {
        if (flag) {
            PrimeFaces.current().executeScript("PF('wvConfirmDialogAsignarVehiculo').hide()");
            asignarBusToServbusBean.prepareAsignarDesasignado(fechaConsulta, prgTc);
            listarSinBus();
        } else {
            PrimeFaces.current().executeScript("PF('wvConfirmDialogAsignarVehiculo').hide()");
        }
    }

    /**
     * Método responsable de validar y luego de vatias validaciones, invocar al
     * verdadero método que hace la persistencia de datos al momento de asignar
     * operador a servicios sin operador.
     *
     * Métodos invocado:
     *
     * 1. guardarAsignacion. persiste la información del proceso de asignación
     * de operador.
     *
     * 2. guardarNovedadPrgTc. perciste la información de de la relación entre
     * los servicios involucrados y la novedad.
     *
     * 3. findServiceBy. consultar los servicio para actualizar la data del
     * panel principal.
     *
     * 4. cargar. consultar los servicios sin operador y servicios sin Vehículo.
     * para actualizar la data presentada en el panel principal.
     */
    public void guardarAsignacionGlobal() {
//        if (flag_submit_II) {
//            flag_submit_II = false;
        if (MovilidadUtil.stringIsEmpty(observacionesPrgTc)) {
            MovilidadUtil.addErrorMessage("La descripción es requerida");
//                flag_submit_II = true;
            return;
        }
        if (i_codResponsable == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado el responsable");
//                flag_submit_II = true;
            return;
        }
        if (i_tipoNovedad == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado el Tipo de Novedad");
//                flag_submit_II = true;
            return;
        }
        if (i_tipoNovedaDet == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado el Detalle de Tipo de Novedad");
//                flag_submit_II = true;
            return;
        }
        if (prgTcOperadorDisponible == null) {
            MovilidadUtil.addErrorMessage("No a consultado el Operador");
//                flag_submit_II = true;
            return;
        }
        if (listPrgTcGestionOperador.isEmpty()) {
            MovilidadUtil.addErrorMessage("No hay servicios seleccionados");
//                flag_submit_II = true;
            return;
        }
        if (novedad.getIdNovedadTipoDetalle().getFechas() == ConstantsUtil.ON_INT && (novedad.getDesde() == null || novedad.getHasta() == null)) {
            MovilidadUtil.addErrorMessage("Las fechas son requeridas");
//                flag_submit_II = true;
            return;
        }
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
            if (ajusteJornadaFromGestionServicio.isFlagAjusteFound()) {
                if (ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getPrgSerconMotivo() == null) {
                    MovilidadUtil.addErrorMessage("Se debe seleccionar un motivo para el ajuste de jornada.");
                    return;
                }
            }
        }
        /**
         * Cargar parámetros para cálculo de jornada al jar encargado de tal
         * tarea.
         */
        UtilJornada.cargarParametrosJar();
        /**
         * Validar tipo de liquidación
         */
        if (UtilJornada.tipoLiquidacion()) {//Flexible
            if (ajusteJornadaFromGestionServicio.validarHorasExtrasFlexible()) {
                return;
            }
            if (ajusteJornadaFromGestionServicio.validarMaxHorasExtrasSmanalesFlexible()) {
                return;
            }
        }
        guardarAsignacion();
        guardarNovedadPrgTc();
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
            List<PrgTc> listTareaDispoFound = prgTcEJB.tareasDispoByIdEmpeladoAndFechaAndUnidadFunc(novedad.getIdEmpleado().getIdEmpleado(),
                    fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            ajusteJornadaFromGestionServicio.setListTareasDispFound(listTareaDispoFound);
            ajusteJornadaFromGestionServicio.guardarAjusteJornadaFound(listPrgTcGestionOperador);
        }
        findServiceBy();
        cargar();
        cargarDisponibles();
//        } else {
//            MovilidadUtil.addErrorMessage("Boton Deshabilitado");
//        }
    }

    /**
     * Método responsable de persistir en la base de datos el proceso de
     * asignación de operadores a servicios sin operador
     *
     * Métodos invocados:
     *
     * 1. guardarNovedadeAsignarOperador. Persiste en BD la novedad resultante
     * del proceso de asignación de operador.
     *
     * 2. notiicar. notificar vía correo una novedad.
     */
//    @Transactional
    public void guardarAsignacion() {
        listNovedadPrgTc = new ArrayList<>();
        try {
            for (PrgTc p : listPrgTcGestionOperador) {
                p.setIdEmpleado(prgTcOperadorDisponible.getIdEmpleado());
                p.setUsername(user.getUsername());
                p.setModificado(MovilidadUtil.fechaCompletaHoy());
                p.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                p.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                p.setObservaciones(observacionesPrgTc);
                prgTcEJB.edit(p);
            }
            guardarNovedadeAsignarOperador();
            /**
             * Notificar vía correo
             */
            notificar();
            MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
            /**
             * Cerra el dialog de la gestión de servicios
             */
            PrimeFaces.current().executeScript("PF('gestionPrgTcDialog').hide()");
            prgTc = null;
            codTMPrgTc = "";
            observacionesPrgTc = "";

        } catch (Exception e) {
//            flag_submit_II = true;
        }
    }

    /**
     * Método responsable de persistir en base de datos la novedad resultante de
     * la asignación de operadores a servicios sin operador
     */
    @Transactional
    public void guardarNovedadeAsignarOperador() {
        PrgTc p = null;
        novedad = new Novedad();
        novedad.setIdNovedadTipo(i_tipoNovedadObj);
        novedad.setIdNovedadTipoDetalle(i_tipoNovedaDetObj);
        novedad.setObservaciones(observacionesPrgTc);
        novedad.setFecha(listPrgTcGestionOperador.get(0).getFecha());
        novedad.setIdEmpleado(emplEjb.find(listPrgTcGestionOperador.get(0).getIdEmpleado().getIdEmpleado()));
        novedad.setOldEmpleado(prgTcOperadorDisponible.getIdEmpleado());
        novedad.setLiquidada(0);
        for (PrgTc tc : listPrgTcGestionOperador) {
            if (tc.getIdVehiculo() != null) {
                p = tc;
                novedad.setIdVehiculo(tc.getIdVehiculo());
                break;
            }
        }
        novedad.setProcede(0);
        novedad.setPuntosPm(0);
        novedad.setPuntosPmConciliados(0);
        novedad.setUsername(user.getUsername());
        novedad.setEstadoReg(0);
        novedad.setControl(codTMPrgTc);
        novedad.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
        novedad.setCreado(MovilidadUtil.fechaCompletaHoy());
        /**
         *
         * Verificar si el tipo de detaller de novedad seleccionado afecta
         * programa master
         */
        if (i_tipoNovedaDetObj.getAfectaPm() == ConstantsUtil.ON_INT) {
            /**
             * Verificar que el tipo de detalle de novedad proceda
             */
            if (procedeNovedad()) {
                novedad.setPuntosPm(i_tipoNovedaDetObj.getPuntosPm());
                novedad.setPuntosPmConciliados(i_tipoNovedaDetObj.getPuntosPm());
                novedad.setProcede(1);
            }
        }
        novEJB.create(novedad);
//        /**
//         * Verificar si el tipo de novedad, es de tipo accidentalidad
//         *
//         */
//        if (novedad.getIdNovedadTipo().getIdNovedadTipo().equals(MovilidadUtil.ID_TIPO_NOVEDAD_ACC)) {
//            /**
//             * Guardar novedad de tipo accidente
//             */
//            novedad.setPrgTc(p);
//            accpreMB.guardarAccidente(novedad);
//        }
    }

    /**
     * Este método sirve para cargar en la lista listPrgTcGestionVehiculo, los
     * servicios seleccionados en vista para la gestion de servicio en el
     * apartado de Vehículo.
     *
     * @param event, éste objeto contiene datos te tipo ArbolViewPrgTc y éste
     * ultimo contiene el servicio un objeto prgTc y viene de la vista
     * gestionPrgTc.
     * @throws ParseException
     */
    public void onNodeSelectVehiculo(NodeSelectEvent event) throws ParseException {
        if (event.getTreeNode().getData() instanceof ArbolViewPrgTc) {
            ArbolViewPrgTc tree = (ArbolViewPrgTc) event.getTreeNode().getData();
            /**
             * El nivel indica que hijo del arbol se ha seleccionado.
             */
            switch (tree.getNivel()) {
                case 1:
                    for (TreeNode tn : event.getTreeNode().getChildren()) {
                        for (TreeNode tnn : tn.getChildren()) {
                            ArbolViewPrgTc at = (ArbolViewPrgTc) tnn.getData();
                            listPrgTcGestionVehiculo.remove(at.getObjPrgTc());
                            listPrgTcGestionVehiculo.add(at.getObjPrgTc());
                        }
                    }
                    break;
                case 2:
                    for (TreeNode tnn : event.getTreeNode().getChildren()) {
                        ArbolViewPrgTc at = (ArbolViewPrgTc) tnn.getData();
                        if (at.getObjPrgTc().getSercon().equals(tree.getObjPrgTc().getSercon())) {
                            listPrgTcGestionVehiculo.remove(at.getObjPrgTc());
                            listPrgTcGestionVehiculo.add(at.getObjPrgTc());
                        }
                    }
                    break;
                case 3:
                    listPrgTcGestionVehiculo.add(tree.getObjPrgTc());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Este método sirve para cargar en la lista listPrgTcGestionEliminar, los
     * servicios seleccionados en vista, para la gestion de servicio en el
     * apartado de Eliminar.
     *
     * @param event este objeto contiene la data de ArbolViewPrgTc y este ultimo
     * contiene un servicio-prgTc o detalle de ruta-PrgPattern y viene de la
     * vista gestionPrgTc.
     * @throws ParseException
     */
    public void onNodeSelectEliminar(NodeSelectEvent event) throws ParseException {
        if (event.getTreeNode().getData() instanceof ArbolViewPrgTc) {
            ArbolViewPrgTc tree = (ArbolViewPrgTc) event.getTreeNode().getData();
            /**
             * El nivel indica que hijo del arbol se ha seleccionado.
             */
            switch (tree.getNivel()) {
                case 1:
                    for (TreeNode tn : event.getTreeNode().getChildren()) {
                        for (TreeNode tnn : tn.getChildren()) {
                            ArbolViewPrgTc at = (ArbolViewPrgTc) tnn.getData();
                            listPrgTcGestionEliminar.remove(at);
                            listPrgTcGestionEliminar.add(at);
                            List<ArbolViewPrgTc> listaSize = new ArrayList<>(listPrgTcGestionEliminarAux);
                            for (ArbolViewPrgTc pAux : listaSize) {
                                if (pAux.getObjPrgTc().getIdPrgTc().equals(at.getObjPrgTc().getIdPrgTc())) {
                                    listPrgTcGestionEliminarAux.remove(pAux);
                                }
                            }
                        }
                    }
                    break;
                case 2:
                    for (TreeNode tnn : event.getTreeNode().getChildren()) {
                        ArbolViewPrgTc at = (ArbolViewPrgTc) tnn.getData();
                        if (at.getObjPrgTc().getSercon().equals(tree.getObjPrgTc().getSercon())) {
                            listPrgTcGestionEliminar.remove(at);
                            listPrgTcGestionEliminar.add(at);
                            List<ArbolViewPrgTc> listaSize = new ArrayList<>(listPrgTcGestionEliminarAux);
                            for (ArbolViewPrgTc pAux : listaSize) {
                                if (pAux.getObjPrgTc().getIdPrgTc().equals(at.getObjPrgTc().getIdPrgTc())) {
                                    listPrgTcGestionEliminarAux.remove(pAux);
                                }
                            }
                        }

                    }
                    break;
                case 3:
                    listPrgTcGestionEliminar.add(tree);
                    List<ArbolViewPrgTc> listaSize = new ArrayList<>(listPrgTcGestionEliminarAux);
                    for (ArbolViewPrgTc pAux : listaSize) {
                        if (pAux.getObjPrgTc().getIdPrgTc().equals(tree.getObjPrgTc().getIdPrgTc())) {
                            listPrgTcGestionEliminarAux.remove(pAux);
                        }
                    }
                    break;
                case 4:
                    listPrgTcGestionEliminarAux.add(tree);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Este método sirve para eliminar de la lista listPrgTcGestionEliminar o
     * listPrgTcGestionEliminarAux, los servicios desseleccionados en vista,
     * para la gestion de servicio en el apartado de Eliminar.
     *
     * @param event este objeto contiene la data de ArbolViewPrgTc y este ultimo
     * contiene un servicio-prgTc o detalle de ruta-PrgPattern, y viene de la
     * vista gestionPrgTc.
     */
    public void onNodeUnselectEliminar(NodeUnselectEvent event) {
        if (event.getTreeNode().getData() instanceof ArbolViewPrgTc) {
            ArbolViewPrgTc tree = (ArbolViewPrgTc) event.getTreeNode().getData();
            /**
             * El nivel indica que hijo del arbol se ha desseleccionado.
             */
            switch (tree.getNivel()) {
                case 1: {
                    List<ArbolViewPrgTc> listAux = new ArrayList<>();
                    listAux.addAll(listPrgTcGestionEliminar);
                    for (ArbolViewPrgTc p : listAux) {
                        listPrgTcGestionEliminar.remove(p);
                    }
                    break;
                }
                case 2: {
                    List<ArbolViewPrgTc> listAux = new ArrayList<>();
                    listAux.addAll(listPrgTcGestionEliminar);
                    for (ArbolViewPrgTc p : listAux) {
                        if (p.getObjPrgTc().getSercon().equals(tree.getObjPrgTc().getSercon())) {
                            listPrgTcGestionEliminar.remove(p);
                        }
                    }
                    break;
                }
                case 3:
                    listPrgTcGestionEliminar.remove(tree);
                    break;
                case 4: {
                    List<ArbolViewPrgTc> listAux = new ArrayList<>();
                    listAux.addAll(listPrgTcGestionEliminarAux);
                    for (ArbolViewPrgTc hijo : listAux) {
                        if (hijo.getObjPrgPattern().getIdPrgPattern().equals(tree.getObjPrgPattern().getIdPrgPattern())) {
                            listPrgTcGestionEliminarAux.remove(hijo);
                            return;
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }

    /**
     * Método responsable de cargar en la lista listNovedadTDet, los detalles
     * del tipo de novedad seleccionada.
     *
     * Es invocado en las vistas adicionarServicios y gestionPrgTc.
     */
    public void findById() {
        b_afectaPrgTc = b_isNovAccidente = ConstantsUtil.ID_TIPO_NOVEDAD_ACC.equals(getI_tipoNovedad());

        for (NovedadTipo nt : listNovedadT) {
            if (nt.getIdNovedadTipo() == getI_tipoNovedad()) {
                i_tipoNovedadObj = nt;
                novedad.setIdNovedadTipo(i_tipoNovedadObj);
                if (i_tipoNovedadObj.getIdNovedadTipo() == 1 && flagUserTC) {
                    listNovedadTDet = novedadTipoDetEjb.findByTipoNovedadForTC(i_tipoNovedadObj.getIdNovedadTipo());//traer solo ausencia, ausencia parcial, inoperable TM;
                } else {
                    listNovedadTDet = nt.getNovedadTipoDetallesList();
                }
                break;
            }
        }
    }

    /**
     * Este método sirve para eliminar de la lista listPrgTcGestionOperador, los
     * servicios desseleccionados en vista, para la gestion de servicio en el
     * apartado de Operador.
     *
     * @param event este objeto contiene la data de ArbolViewPrgTc y este ultimo
     * contiene un servicio-prgTc y viene de la vista gestionPrgTc.
     */
    public void onNodeUnselectOperador(NodeUnselectEvent event) {
        if (event.getTreeNode().getData() instanceof ArbolViewPrgTc) {
            ArbolViewPrgTc tree = (ArbolViewPrgTc) event.getTreeNode().getData();
            /**
             * El nivel indica que hijo del arbol se ha desseleccionado.
             */
            if (tree.getNivel() == 1) {
                List<PrgTc> listAux = new ArrayList<>();
                listAux.addAll(listPrgTcGestionOperador);
                for (PrgTc p : listAux) {
                    listPrgTcGestionOperador.remove(p);
                    listDesasignarOpServNull.add(p);
                }
            } else if (tree.getNivel() == 2) {
                List<PrgTc> listAux = new ArrayList<>();
                listAux.addAll(listPrgTcGestionOperador);
                for (PrgTc p : listAux) {
                    if (p.getIdPrgTc().equals(tree.getObjPrgTc().getIdPrgTc())) {
                        listPrgTcGestionOperador.remove(p);
                        listDesasignarOpServNull.add(p);
                        break;
                    }
                }
            }
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                ajusteJornadaFromGestionServicio.setListDesasignarOpServNull(listDesasignarOpServNull);
                ajusteJornadaFromGestionServicio.setListPrgTcGestionOperador(listPrgTcGestionOperador);
                if (prgTcOperadorDisponible != null) {
                    ajusteJornadaFromGestionServicio.recrearJornadaFound("formGestionPrgTc:tabViews:turnoEmplSelected");
                    ajusteJornadaFromGestionServicio.ajusteJornadasFoundII(listPrgTcGestionOperador, "formGestionPrgTc:tabViews:turnoEmplFound");
                    ajusteJornadaFromGestionServicio.validarMaxHorasExtrasSmanales();
                }
//                ajusteJornadaFromGestionServicio.ajusteJornadasSelected(listPrgTcGestionOperador,
//                        listDesasignarOpServNull, tree.getObjPrgTc().getIdEmpleado(),
//                        disponibleAlCambio, desasignarOpeServNull, fechaConsulta);
                ajusteJornadaFromGestionServicio.recrearJornadaSelected();
            }
        }
    }

    /**
     * Este método sirve para eliminar de la lista listPrgTcGestionOperador, los
     * servicios desseleccionados en vista, para la gestion de servicio en el
     * apartado de Asignar operador en servicios sin operador.
     *
     * @param event este objeto contiene la data de ArbolViewPrgTc y este ultimo
     * contiene un servicio-prgTc y viene de la vista gestionPrgTc.
     */
    public void onNodeUnselectSinOperador(NodeUnselectEvent event) {
        if (event.getTreeNode().getData() instanceof ArbolViewPrgTc) {
            ArbolViewPrgTc tree = (ArbolViewPrgTc) event.getTreeNode().getData();
            /**
             * El nivel indica que hijo del arbol se ha desseleccionado.
             */
            if (tree.getNivel() == 1) {
                List<PrgTc> listAux = new ArrayList<>();
                listAux.addAll(listPrgTcGestionOperador);
                for (PrgTc p : listAux) {
                    listPrgTcGestionOperador.remove(p);
                }
            } else if (tree.getNivel() == 2) {
                List<PrgTc> listAux = new ArrayList<>();
                listAux.addAll(listPrgTcGestionOperador);
                for (PrgTc p : listAux) {
                    if (p.getIdPrgTc().equals(tree.getObjPrgTc().getIdPrgTc())) {
                        listPrgTcGestionOperador.remove(p);
                        break;
                    }
                }
            }
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                ajusteJornadaFromGestionServicio.setListPrgTcGestionOperador(listPrgTcGestionOperador);
                if (prgTcOperadorDisponible != null) {
                    ajusteJornadaFromGestionServicio.recrearJornadaFound("formGestionPrgTc:tabViews:turnoEmplFoundII");
                    ajusteJornadaFromGestionServicio.ajusteJornadasFoundII(listPrgTcGestionOperador, "formGestionPrgTc:tabViews:turnoEmplFoundII");
                    ajusteJornadaFromGestionServicio.validarMaxHorasExtrasSmanales();
                }
            }
        }
    }

    /**
     * Este método sirve para eliminar de la lista listPrgTcGestionVehiculo, los
     * servicios desseleccionados en vista, para la gestion de servicio en el
     * apartado de Vehículo.
     *
     * @param event este objeto contiene la data de ArbolViewPrgTc y este ultimo
     * contiene un servicio-prgTc y viene de la vista gestionPrgTc.
     */
    public void onNodeUnselectVehiculo(NodeUnselectEvent event) {
        if (event.getTreeNode().getData() instanceof ArbolViewPrgTc) {
            ArbolViewPrgTc tree = (ArbolViewPrgTc) event.getTreeNode().getData();
            /**
             * El nivel indica que hijo del arbol se ha desseleccionado.
             */
            switch (tree.getNivel()) {
                case 1: {
                    List<PrgTc> listAux = new ArrayList<>();
                    listAux.addAll(listPrgTcGestionVehiculo);
                    for (PrgTc p : listAux) {
                        if (p.getServbus().equals(tree.getNombre())) {
                            listPrgTcGestionVehiculo.remove(p);
                        }
                    }
                    break;
                }
                case 2: {
                    List<PrgTc> listAux = new ArrayList<>();
                    listAux.addAll(listPrgTcGestionVehiculo);
                    for (PrgTc p : listAux) {
                        if (p.getSercon().equals(tree.getObjPrgTc().getSercon())) {
                            listPrgTcGestionVehiculo.remove(p);
                        }
                    }
                    break;
                }
                case 3:
                    listPrgTcGestionVehiculo.remove(tree.getObjPrgTc());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Método responable de setear los puntos inicio o fin de un servicio,
     * seleccionados desde la vista, para el apartado de agregar servicios.
     *
     * @param opc, este parametro es un identificar para saber desde que
     * componente primeface se esta realizando la operación de seleccionar un
     * punto inicio/fin o prgPattern.
     *
     * Se utiliza desde la vista adicionarServicios.
     */
    public void setObjeto(int opc) {
        PrgRoute find = prgRoute.find(i_idRuta);
        if (find.getIsCircular().equals(ConstantsUtil.OFF_INT)) {
            if (i_idPuntoIniInt.equals(i_idPuntoFinInt)) {
                i_idPuntoIniInt = 0;
                i_idPuntoFinInt = 0;
                idPuntoIni = null;
                idPuntoFin = null;
                return;
            }
        }
        for (PrgPattern p : listPrgPattern) {
            if (opc == 0) {
                if (p.getIdPrgStoppoint().getIdPrgStoppoint().equals(i_idPuntoIniInt)) {
                    setIdPuntoIni(p);
                    break;
                }
            } else {
                if (p.getIdPrgStoppoint().getIdPrgStoppoint().equals(i_idPuntoFinInt)) {
                    setIdPuntoFin(p);
                    break;
                }
            }
        }

        if (!i_idPuntoFinInt.equals(
                0) && !i_idPuntoIniInt.equals(0)) {
            if (getIdPuntoIni().getDistance() > getIdPuntoFin().getDistance()) {
                MovilidadUtil.addErrorMessage("Error de sentido del servicio");
                i_idPuntoFinInt = 0;
                i_idPuntoIniInt = 0;
                idPuntoIni = null;
                idPuntoFin = null;
            }
        }
    }

    /**
     * Método responsable de guardar en memoria un servicio de VAC/VACCOM
     * indicado desde la eliminación de servicios en la ventana de gestion de
     * servicios
     */
    public void preGuardarVacio() {
        if (prgTcVacOrVaccom == null) {
            prgTcVacOrVaccom = new PrgTc();
        }
        if (prgTcOperadorDisponible != null) {
            prgTcVacOrVaccom.setIdEmpleado(prgTcOperadorDisponible.getIdEmpleado());
        } else {
            prgTcVacOrVaccom.setIdEmpleado(prgTc.getIdEmpleado());
        }
        if (prgTcVacOrVaccom.getToStop() == null && !eliminarFromCabecera) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar el destino");
            return;
        }
        prgTcVacOrVaccom.setEstadoOperacion(6);
        if (tipoVacio == 2) {
            prgTcVacOrVaccom.setEstadoOperacion(7);
        }
        prgTcVacOrVaccom.setUsername(user.getUsername());
        prgTcVacOrVaccom.setCreado(MovilidadUtil.fechaCompletaHoy());
        prgTcVacOrVaccom.setTimeOrigin(prgTc.getTimeOrigin());
        prgTcVacOrVaccom.setTimeDestiny(prgTc.getTimeDestiny());
        prgTcVacOrVaccom.setSercon(prgTc.getSercon());
        prgTcVacOrVaccom.setTaskDuration(prgTc.getTaskDuration());
        prgTcVacOrVaccom.setIdTaskType(new PrgTarea(tipoVacio));
        prgTcVacOrVaccom.setFecha(prgTc.getFecha());
        prgTcVacOrVaccom.setFromStop(prgTc.getFromStop());
        prgTcVacOrVaccom.setAmplitude(prgTc.getAmplitude());
        prgTcVacOrVaccom.setIdPrgTcResumen(prgTc.getIdPrgTcResumen());
        prgTcVacOrVaccom.setProductionDistance(prgTc.getProductionDistance());

        PrimeFaces.current().executeScript("PF('AdicionarVacioDialog').hide()");
        MovilidadUtil.addSuccessMessage("Vacio agregado");
    }

    /**
     * Método responsable de cancelar la gestion de servicios VAC/VACCOM en el
     * aprartado de elminación de servicios en la ventana de gestion de
     * servicios. Hace nula el servicio y cierra el dialog en vista.
     */
    public void cancelar() {
        prgTcVacOrVaccom = null;
        MovilidadUtil.addAdvertenciaMessage("Se cancela vacío al eliminar");
        PrimeFaces.current().executeScript("PF('AdicionarVacioDialog').hide()");
    }

    /**
     * Método responsable de la pre-cración de un vacío y retornar la instancia.
     *
     * @param prgTcLocal este parametro sriver para completar de crear un nuevo
     * PrgTc, ya que, viene con datos importantes cargados
     * @return PrgTc, el objeto completo con toda la data necesaria.
     */
    public PrgTc crearVacio(PrgTc prgTcLocal) {
        PrgTc prgTcNewVacio = new PrgTc();
        prgTcNewVacio.setEstadoOperacion(7);
        prgTcNewVacio.setIdVehiculo(prgTcLocal.getIdVehiculo());
        prgTcNewVacio.setIdVehiculoTipo(prgTcLocal.getIdVehiculoTipo());
        prgTcNewVacio.setIdTaskType(new PrgTarea(2));
        prgTcNewVacio.setSercon(prgTcLocal.getSercon());
        prgTcNewVacio.setServbus(prgTcLocal.getServbus());
        prgTcNewVacio.setTabla(prgTcLocal.getTabla());
        prgTcNewVacio.setDistance(prgTcLocal.getDistance());
        prgTcNewVacio.setTaskDuration(prgTcLocal.getTaskDuration());
        prgTcNewVacio.setFecha(prgTcLocal.getFecha());
        prgTcNewVacio.setFromStop(prgTcLocal.getFromStop());
        prgTcNewVacio.setIdPrgTcResumen(prgTcLocal.getIdPrgTcResumen());
        prgTcNewVacio.setAmplitude(prgTcLocal.getAmplitude());
        prgTcNewVacio.setProductionDistance(prgTcLocal.getProductionDistance());
        prgTcNewVacio.setTimeOrigin(prgTcLocal.getTimeOrigin());
        prgTcNewVacio.setTimeDestiny(prgTcLocal.getTimeDestiny());
        prgTcNewVacio.setIdEmpleado(prgTcLocal.getIdEmpleado());
        prgTcNewVacio.setCreado(MovilidadUtil.fechaCompletaHoy());
        prgTcNewVacio.setIdPrgTcResponsable(prgTcLocal.getIdPrgTcResponsable());
        prgTcNewVacio.setIdPrgClasificacionMotivo(prgTcLocal.getIdPrgClasificacionMotivo());
        prgTcNewVacio.setUsername(user.getUsername());
        prgTcNewVacio.setIdGopUnidadFuncional(prgTcLocal.getIdGopUnidadFuncional());
        return prgTcNewVacio;
    }

    /**
     * Método responsable de cargar la lista listPrgStopPoint con los puntos o
     * paradas-patios, según el texto indicado desde la vista.
     *
     * @param comp este parametro es un identificar para saber desde que
     * componente primeface se esta realizando la operación.
     *
     * Es invocado en al vista gestionPrgTc
     */
    public void findPunto(int comp) {
        entradaSalida = comp;
        if (comp == 1) {
            if (MovilidadUtil.stringIsEmpty(entrada)) {
                MovilidadUtil.addErrorMessage("Digíte una valor para Entra a.");
                return;
            }
            destino = entrada;
        } else if (comp == 2) {
            if (MovilidadUtil.stringIsEmpty(salida)) {
                MovilidadUtil.addErrorMessage("Digíte una valor para Sale de.");
                return;
            }
            destino = salida;
        }
        if (listPrgStopPoint != null) {
            listPrgStopPoint.clear();
        }

        listPrgStopPoint = prgSPEJB.findStopPointByName(destino, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (listPrgStopPoint.isEmpty()) {
            MovilidadUtil.addErrorMessage("No hay Paradas con este nombre");
            return;
        }
        PrimeFaces.current().executeScript("PF('wvEntradaSalidaListDialog').show()");
        PrimeFaces.current().ajax().update(":frmPrincipalEntradaSalidaList:tablaEntradaSalida");

    }

    /**
     * Método responsable de cargar la lista listPrgStopPoint con los puntos o
     * paradas-patios, según el texto indicado desde la vista y deacuerdo desde
     * cual input se hizo. El parametro comp, indica desde que componente se
     * hizo la busqueda. Modulo adisionar servicios y adicios vacío en elimnar
     * servicios.
     *
     * @param comp este parametro es un identificador para saber desde que
     * componente primeface se esta realizando la operación.
     */
    public void findDestino(int comp) {
        componente = comp;
        switch (componente) {
            case 1:
                destino = punto_inicioString;
                break;
            case 2:
                destino = punto_finString;
                break;
            case 0:
                destino = destinoVacio;
                break;
            default:
                break;
        }
        if (listPrgStopPoint != null) {
            listPrgStopPoint.clear();
        }

        if (MovilidadUtil.stringIsEmpty(destino)) {
            MovilidadUtil.addErrorMessage("Digíte el nombre de la parada");
            return;
        }
        if (propia) {
            listPrgStopPoint = prgSPEJB.getparadasByNombre(destino,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        } else {
            listPrgStopPoint = prgSPEJB.findStopPointByName(destino,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        }

        if (listPrgStopPoint.isEmpty()) {
            MovilidadUtil.addErrorMessage("No hay Paradas con este nombre");
            return;
        }
        PrimeFaces.current().executeScript("PF('wvStopPointListDialog').show()");
        PrimeFaces.current().executeScript("PF('tablaStopPointWV').clearFilters();");
        PrimeFaces.current().ajax().update("frmPrincipalStopPointList:tablaStopPoint");

    }

    /**
     * Método responsable de extraer el texto de los nodos creados a armar los
     * arboles, el método retorna el titulo que es visualisado por vista.
     *
     * @param node, este parametro viene de la vista gestionPrgTc y viene
     * cargado con un obejto PrgPattern o ArbolViewPrgTc, para luego extraer un
     * nombre
     * @return retorna un valor String con el nombre del objeto parametro. que
     * es armado en el método *returnTituloServ*
     */
    public String returnTitulo(Object node) {
        String titulo;
        if (node instanceof PrgPattern) {
            PrgPattern prgPattern = (PrgPattern) node;
            titulo = prgPattern.getIdPrgStoppoint().getName();
        } else {
            ArbolViewPrgTc arbolViewprgTc = (ArbolViewPrgTc) node;
            titulo = arbolViewprgTc.getNombre();
        }

        return titulo;
    }

    /**
     * Método responsable de armar el el titulo que llevaran los servicios para
     * ser visualizado en vista, recibe un objto Prgtc, de cual extrae la
     * información de sus atributos y arma una cadena que es retornada luego.
     *
     * @param servicio, es de tipo PrgTc y es util para definir como se
     * presentará el servicio en la vista gestionPrgTc.
     * @return Retornará un valor string con el siguiente orden:
     *
     * Hora_Inicio - Hora_Fin - TRAY/VAC - Nombre_Parada_Inicio -
     * Nombre_Parada_Fin - Sercon - Nombre_Tarea
     *
     * Ejemplo:
     *
     * 05:03:15 - 06:18:00 - TRAY - Portal Sur T5 - Terminal A - B11 TERMINAL
     *
     */
    public String returnTituloServ(PrgTc servicio) {

        String var = "TRAY";
        if (servicio.getIdRuta() == null) {
            var = "VAC";
        }

        return servicio.getTimeOrigin() + " - " + servicio.getTimeDestiny() + ", " + var
                + ", " + servicio.getFromStop().getName() + " - " + servicio.getToStop().getName()
                + servicio.getSercon() + " - " + servicio.getIdTaskType().getTarea();
    }

    private boolean validaConciliarAndServicioSeleccionado() {
        if (flagConciliado) {
            MovilidadUtil.addErrorMessage("Día Bloqueado.");
            return true;
        }
        validarCierreTurno();
        if (flagTurnoCerrado) {
            MovilidadUtil.addErrorMessage("Turno Cerrado.");
            return true;
        }
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Seleccione un servicio en la tabla");
            return true;
        }
        return false;
    }

    /**
     * Método responsable de preparar las variables para la gestion de servicios
     * Eliminar, cambio/desasignación de Vehículo/operador.
     *
     * @param opc se utiliza para saber que comportamiento tendra Gestion
     * servicios. Si opc es igual a 1 se prepararán las variables para una
     * novedad de tipo accidente.
     * @throws java.text.ParseException
     */
    public void prepareGestionServicio(int opc) throws ParseException {
        resest();
        if (validaConciliarAndServicioSeleccionado()) {
            return;
        }
        if (prgTc.getIdEmpleado() == null) {
            MovilidadUtil.addErrorMessage("Opción no valida");
            return;
        }

        if (opc == 0) {
            flagBotonesGestionServicio = true;
            cargarDetallesAccesoRapido();
        }

        lugarTQ = null;
        vehiculoParaNov = null;
        empleadoParaNov = null;
        vehiculoParaNovNew = null;
        empleadoParaNovNew = null;
        b_validacion = true;
        flagOperador = true;
//        flag_submit_I = true;
        flagVehiculo = true;
        flagEliminar = true;
        flagCambioHora = false;
        flagSinOperador = false;
        eliminarFromCabecera = false;
        unoAUnoOperador = false;

        unoAUnoVehiculo = true;
        flagGuardarTodo = true;
        propia = false;
        listPrgTcGestionVehiculo = new ArrayList<>();
        listPrgTcGestionEliminar = new ArrayList<>();
        listPrgTcGestionOperador = new ArrayList<>();
        listPrgTcGestionEliminarAux = new ArrayList<>();
//        listNovedadTDet = new ArrayList<>();
        prgTcVacOrVaccom = null;
        prgTcUnoAUno = null;
        vehiculo = null;
        destino = "";
        destinoVacio = "";
        salidaStopPoint = null;
        entradaStopPoint = null;
        codigoTm = "";
        codigoTmVac = "";
        entrada = "";
        salida = "";
        prgTcOperadorDisponible = null;
        novedad = new Novedad();
        rootOperador = new DefaultTreeNode("Operador", null);
        rootVehiculo = new DefaultTreeNode("Vehiculo", null);
        rootEliminar = new DefaultTreeNode("Eliminar", null);
        flagGestionServicios = true;
        //Armar arbol que se visualizara en el apartado de Eliminar en gestion de servicios
        armarArbolAlEliminar();
        //Armar arbol que se visualizara en el apartado de Operador en gestion de servicios

        armarArbolOperador();
        //Armar arbol que se visualizara en el apartado de Vehículo en gestion de servicios

        armarArbolVehiculo();
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
            ajusteJornadaFromGestionServicio.resetHorasFound();
            ajusteJornadaFromGestionServicio.setDesasignarOpeServNull(false);
            ajusteJornadaFromGestionServicio.setDisponibleAlCambio(true);
            ajusteJornadaFromGestionServicio.setIdEmpleadoSelected(prgTc.getIdEmpleado().getIdEmpleado());
            ajusteJornadaFromGestionServicio.setFecha(prgTc.getFecha());
            ajusteJornadaFromGestionServicio.setListPrgTcGestionOperador(listPrgTcGestionOperador);
            ajusteJornadaFromGestionServicio.cargarListaServiciosOpSelected();
            ajusteJornadaFromGestionServicio.cargarListaServiciosOpFound();
            ajusteJornadaFromGestionServicio.cargarPrgSerconSelected();
            ajusteJornadaFromGestionServicio.recrearJornadaSelected();
            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().setI_prgSerconMotivo(null);
            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().setPrgSerconMotivo(null);
        }
        if (opc == 1) {
            getListNovedadT();
            i_tipoNovedad = ConstantsUtil.ID_TIPO_NOVEDAD_ACC;
            b_isNovAccidente = true;
            findById();
        } else {
            b_afectaPrgTc = b_isNovAccidente = false;
        }
        cargarResponsable();
        PrimeFaces.current()
                .executeScript("PF('gestionPrgTcDialog').show()");
        PrimeFaces.current()
                .ajax().update("formGestionPrgTc");
    }

    /**
     * Valida que roles pueden tener accesos a todas las opciones de la gestión
     * de servicio.
     *
     * @return valor boolean si el rol del usuario logueado es ROLE_TC ROLE_LIQ
     * ROLE_PROFOP o ROLE_ADMIN
     */
    public boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            validarFinOperacionBean.setIsTecControl(auth.getAuthority().equals("ROLE_TC"));
            if (auth.getAuthority().equals("ROLE_TC")) {
                flagUserTC = true;
                return true;
            }
            if (auth.getAuthority().equals("ROLE_LIQ")
                    || auth.getAuthority().equals("ROLE_ADMIN")
                    || auth.getAuthority().equals("ROLE_PROFOP")) {
                return true;
            }

        }
        return false;
    }

    /**
     * Método encargado visualizar por pantalla un formulario donde se efectuará
     * la gestion de revertir un servicio eliminado.
     *
     * Se valida si la fecha ya esta conciliada
     */
    public void revertir() {
        if (validaConciliarAndServicioSeleccionado()) {
            return;
        }
        MovilidadUtil.openModal("wvConfirmDialogRevivirServicio");
    }

    /**
     * Método encargar persistir en BD la gestion de revertir la eliminación de
     * un servicio.
     */
    public void revertirSi() {

        if (prgTc.getServbus() != null) {
            if (prgTc.getServbus().endsWith("AD")) {
                if (prgTc.getIdTaskType() != null) {
                    prgTc.setEstadoOperacion(3);
                    if (isVaccom(prgTc.getIdTaskType().getIdPrgTarea())) {
                        prgTc.setEstadoOperacion(6);
                    }
                    if (isVac(prgTc.getIdTaskType().getIdPrgTarea())) {
                        prgTc.setEstadoOperacion(7);
                    }
                }
            } else {
                prgTc.setEstadoOperacion(0);
            }
        } else {
            prgTc.setEstadoOperacion(0);
        }
        prgTcEJB.edit(prgTc);
        MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
        PrimeFaces.current().executeScript("PF('wvConfirmDialogRevivirServicio').hide()");
        if (prgTc.getIdEmpleado() == null) {
            listarSinOperador();
        }
        if (prgTc.getIdVehiculo() == null) {
            listarSinBus();
        }
        findServiceBy();

    }

    public void revertirNo() {
        PrimeFaces.current().executeScript("PF('wvConfirmDialogRevivirServicio').hide()");
    }

    /**
     * Método encargado visualizar por pantalla un formulario donde se efectuará
     * la gestion de eliminar una tarea sin operador.
     *
     * Se valida si la fecha ya esta conciliada
     */
    public void EliminarXOperador() {
        if (validaConciliarAndServicioSeleccionado()) {
            return;
        }
        MovilidadUtil.openModal("wvConfirmDialogEliminarXOperador");
    }

    /**
     * Método encargado visualizar por pantalla un formulario donde se efectuará
     * la gestion de eliminar una tarea sin Vehículo asignado.
     *
     * Se valida si la fecha ya esta conciliada
     */
    public void EliminarXVehiculo() {
        if (validaConciliarAndServicioSeleccionado()) {
            return;
        }
        MovilidadUtil.openModal("wvConfirmDialogEliminarXVehiculo");
    }

    /**
     * Método encargado aplicar la eliminación del servicio sin operador según
     * confirmación del usuario
     */
    public void EliminarXOperadorConfirm(boolean flag) {
        if (flag) {
            PrimeFaces.current().executeScript("PF('wvConfirmDialogEliminarXOperador').hide()");
            if (prgTc.getServbus() != null && prgTc.getServbus().endsWith("AD")) {
                if (prgTc.getIdTaskType() != null) {
                    prgTc.setEstadoOperacion(99);
                    if (isVaccom(prgTc.getIdTaskType().getIdPrgTarea())) {
                        prgTc.setEstadoOperacion(99);
                    }
                    if (isVac(prgTc.getIdTaskType().getIdPrgTarea())) {
                        prgTc.setEstadoOperacion(8);
                    }
                }
            } else {
                prgTc.setEstadoOperacion(5);
            }
            prgTcEJB.edit(prgTc);
            MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
            PrimeFaces.current().executeScript("PF('wvConfirmDialogRevivirServicio').hide()");
            listarSinOperador();
            findServiceBy();

        } else {
            PrimeFaces.current().executeScript("PF('wvConfirmDialogEliminarXOperador').hide()");
        }
    }

    /**
     * Método encargado aplicar la eliminación del servicio sin VEHÍCULO según
     * confirmación del usuario
     */
    public void EliminarXVehiculoConfirm(boolean flag) {
        if (flag) {
            PrimeFaces.current().executeScript("PF('wvConfirmDialogEliminarXVehiculo').hide()");
            if (prgTc.getServbus() != null && prgTc.getServbus().endsWith("AD")) {
                if (prgTc.getIdTaskType() != null) {
                    prgTc.setEstadoOperacion(99);
                    if (isVaccom(prgTc.getIdTaskType().getIdPrgTarea())) {
                        prgTc.setEstadoOperacion(99);
                    }
                    if (isVac(prgTc.getIdTaskType().getIdPrgTarea())) {
                        prgTc.setEstadoOperacion(8);
                    }
                }
            } else {
                prgTc.setEstadoOperacion(5);
            }
            prgTcEJB.edit(prgTc);
            MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
            PrimeFaces.current().executeScript("PF('wvConfirmDialogRevivirServicio').hide()");
            listarSinBus();
            findServiceBy();

        } else {
            PrimeFaces.current().executeScript("PF('wvConfirmDialogEliminarXVehiculo').hide()");
        }
    }

    /**
     * Método responsable de persistir en BD el registro resultante entre La
     * novedad y los servicios (prgTc) involucrados en la gestión
     */
    public void guardarNovedadPrgTc() {

        if (!listPrgTcGestionEliminar.isEmpty()) {
            for (ArbolViewPrgTc a : listPrgTcGestionEliminar) {
                listNovedadPrgTc.add(a.getObjPrgTc());
            }
        }
        if (!listPrgTcGestionEliminarAux.isEmpty()) {
            for (ArbolViewPrgTc td : listPrgTcGestionEliminarAux) {
                listNovedadPrgTc.add(td.getObjPrgTc());
            }
        }

        if (!listPrgTcGestionOperador.isEmpty()) {
            listNovedadPrgTc.addAll(listPrgTcGestionOperador);

        }

        if (!listPrgTcGestionVehiculo.isEmpty()) {
            listNovedadPrgTc.addAll(listPrgTcGestionVehiculo);
        }

        //eliminar duplicados de la lista
        Set<PrgTc> hashSet;
        hashSet = new HashSet<>(listNovedadPrgTc);
        listNovedadPrgTc.clear();
        //agregar objetos sin duplicados a la lista a persistir
        listNovedadPrgTc.addAll(hashSet);

        for (PrgTc tc : listNovedadPrgTc) {
            NovedadPrgTc np = new NovedadPrgTc();
            PrgTc tcFind = prgTcEJB.find(tc.getIdPrgTc());

            np.setIdNovedad(novedad);
            np.setIdPrgTc(tc);
            np.setIdEmpleado(tc.getIdEmpleado());
            if (tc.getOldEmpleado() != null) {
                np.setIdOldEmpleado(new Empleado(tc.getOldEmpleado()));
            }
            np.setIdVehiculo(tc.getIdVehiculo());
            if (tc.getOldVehiculo() != null) {
                np.setIdOldVehiculo(new Vehiculo(tc.getOldVehiculo()));
            }
            if (tcFind != null) {
                np.setToStop(tcFind.getToStop());
                np.setFromStop(tcFind.getFromStop());
                np.setIdPrgTcResponsable(tcFind.getIdPrgTcResponsable());
                np.setObservaciones(tcFind.getObservaciones());
                np.setTimeOrigin(tcFind.getTimeOrigin());
                np.setTimeDestiny(tcFind.getTimeDestiny());
                np.setEstadoOperacion(tcFind.getEstadoOperacion());
                np.setDistancia(prgTcEJB.findDistandeByFromStopAndToStop(tcFind.getFromStop().getIdPrgStoppoint(), tcFind.getToStop().getIdPrgStoppoint()));
            } else {
                np.setToStop(tc.getToStop());
                np.setFromStop(tc.getFromStop());
                np.setIdPrgTcResponsable(tc.getIdPrgTcResponsable());
                np.setObservaciones(novedad.getObservaciones());
                np.setTimeOrigin(tc.getTimeOrigin());
                np.setTimeDestiny(tc.getTimeDestiny());
                np.setEstadoOperacion(tc.getEstadoOperacion());
                np.setDistancia(prgTcEJB.findDistandeByFromStopAndToStop(tc.getFromStop().getIdPrgStoppoint(), tc.getToStop().getIdPrgStoppoint()));
            }
            np.setUsername(user.getUsername());
            np.setCreado(MovilidadUtil.fechaCompletaHoy());
            np.setEstadoReg(0);
            /**
             * Set unidad Funcional
             */
            np.setIdGopUnidadFuncional(novedad.getIdGopUnidadFuncional());
            NovPrgTcEJB.create(np);
        }
    }

    /**
     * Método encargado de invocar todos lo métodos que persisten la informacion
     * del proceso de gestion de servicios.
     *
     * Invoca los métodos:
     *
     * 1. guardarTodo. Responsable de persistir en base de datos los tres
     * procesos(Eliminación de servicios, cambio o desasignacón de vehículo a
     * servicios, o cambio o desasignación de operador a servicios ) despues de
     * una previa validación.
     *
     * 2. GuardarNovedad. Responsable de persistir la información de una novedad
     * resultante de la gestion de servicios.
     *
     * 3. guardarNovedadPrgTc. Responsable de persisitir las relaciones de una
     * novedad generada, con los servicio o prgTc involucrados.
     *
     * 4. cargar. consultar los servicios sin operador y servicios sin Vehículo.
     * para actualizar la data presentada en el panel principal.
     *
     * 5. cargar. consultar los vehículos disponibles. para actualizar la data
     * presentada en el panel principal.
     *
     * 6. findServiceBy. consultar los servicio para actualizar la data del
     * panel principal.
     *
     * @throws ParseException
     * @throws InterruptedException
     */
    public void guardarTodoGlobal() throws ParseException, InterruptedException {
        //bandera que sirve para evidar el doble submit desde la vista
//        if (flag_submit_I) {
//            flag_submit_I = false;
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
            if (ajusteJornadaFromGestionServicio.isFlagRestringirMaxHorasExtras()) {
                MovilidadUtil.addErrorMessage("No es posible guardar los cambio, operador sobrepasa el máximo de horas extras.");
                return;
            }
            if (ajusteJornadaFromGestionServicio.isFlagRestringirMaxHrExtrasSmnal()) {
                MovilidadUtil.addErrorMessage("No es posible guardar, se ha excedido el máximo de horas extras semanales.");
                return;
            }
        }
        if (eliminarFromCabecera) {
            if (listPrgTcGestionEliminarAux.isEmpty()) {
                MovilidadUtil.addErrorMessage("Acción no alpica como eliminación parcial desde cabecera");
                return;
            }
            if (prgTcVacOrVaccom == null) {
                preGuardarVacio();
            }
        }
        if (validarDatosNovedad()) {
            return;
        }
        if (eliminarFromCabecera) {
            /**
             * persiste en BD la gestion de eliminación parcial desde cabecera
             * del servicio.
             */
            guardarEliminadoParcialFromCabecera();
        } else {
            /**
             * persiste en BD la gestion de las 3 opciones (Vehículo, Operador,
             * Eliminar) en la gestión de servicios
             */
            guardarTodo();
        }

        /**
         * bandera que sirve para saber si el método inmediatamente anterior
         * persistió la data sin ningun inconveniente.
         */
        if (!b_afectaPrgTc) {
            tokenSave = true;
        }
        if (tokenSave) {
            //Guardar novedad resultante de la gestión de servicio
            GuardarNovedad();
            /**
             * Guardar la relación de novedad y cada uno de los prgTc(Servicios)
             * tocados en la gestión de servicio
             */
            guardarNovedadPrgTc();
            /**
             * Cargar Listas de servicio sin Vhículo y servicios sin operador.
             */
            cargar();
            /**
             * Cargar Lista de vehículos disponibles
             */
            listarVehiculoDispo();
            /**
             * Cargar Servicios para actualizar el panel principal
             */
            findServiceBy();
            /**
             * cargar operadores disponibles
             */
            cargarDisponibles();
            prgTc = null;
            prgTcUnoAUno = null;
            vehiculo = null;
            codTMPrgTc = "";
            observacionesPrgTc = "";
            prgTcVacOrVaccom = null;
            lugarTQ = null;
            MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
            PrimeFaces.current().executeScript("PF('gestionPrgTcDialog').hide()");
            tokenSave = false;
            flagBotonesGestionServicio = false;
        } else {
            MovilidadUtil.addErrorMessage("No se realizó algún cambio");
        }
//        } else {
//            MovilidadUtil.addErrorMessage("Boton deshabilitado");
//        }
    }

    /**
     * Validar que los datos básicos requeridos de la novedad, esten
     * diligenciados y ademas valida si el Empleado ya tiene novedad similar
     * para las fechas indicada( si aplica).
     *
     * @return true, si falta algun valor por digitar.
     */
    public boolean validarDatosNovedad() throws ParseException {
        if (MovilidadUtil.stringIsEmpty(observacionesPrgTc)) {
            if (!b_isNovAccidente || (b_isNovAccidente && b_afectaPrgTc)) {
                MovilidadUtil.addErrorMessage("La descripción es requerida");
                tokenSave = false;
//            flag_submit_I = true;
                return true;
            } else {
                observacionesPrgTc = "Novedad Accidente.";
            }
        }
        if (i_tipoNovedad == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado el de Tipo de Novedad");
            tokenSave = false;
//            flag_submit_I = true;
            return true;
        }
        if (i_tipoNovedaDet == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado el Detalle de Tipo de Novedad");
            tokenSave = false;
//            flag_submit_I = true;
            return true;
        }

        if (novedad.getIdNovedadTipo().getIdNovedadTipo().equals(
                Integer.parseInt(
                        SingletonConfigEmpresa.getMapConfiMapEmpresa()
                                .get(ConstantsUtil.KEY_ID_NOV_AUSENTISMO)))) {

            ParamCierreAusentismo cierreAusentismo = paramCierreAusentismoEjb.buscarPorRangoFechasYUnidadFuncional(
                    prgTc.getFecha(), prgTc.getFecha(),
                    prgTc.getIdEmpleado().getIdGopUnidadFuncional().getIdGopUnidadFuncional());

            if (cierreAusentismo != null) {

                if (cierreAusentismo.getBloqueado() == 1) {
                    MovilidadUtil.addErrorMessage("Se ha realizado el cierre de ausentismos para la fecha seleccionada.");
                    return true;
                }

            }

        }

        if (i_codResponsable == 0) {
            if (!b_isNovAccidente || (b_isNovAccidente && b_afectaPrgTc)) {
                MovilidadUtil.addErrorMessage("No ha seleccionado el responsable");
                tokenSave = false;
//            flag_submit_I = true;
                return true;
            }
        }
        if (novedad.getIdNovedadTipoDetalle().getAtv() == ConstantsUtil.ON_INT //                && atvNovedadBean.isB_atv()
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
        if (novedad.getIdNovedadTipoDetalle().getFechas() == ConstantsUtil.ON_INT) {
            if ((novedad.getDesde() == null || novedad.getHasta() == null)) {
                MovilidadUtil.addErrorMessage("Las fechas son requeridas");
                tokenSave = false;
//                flag_submit_I = true;
                return true;
            } else {

                if (MovilidadUtil.dateSinHora(novedad.getDesde()).before(MovilidadUtil.fechaHoy())) {
                    MovilidadUtil.addErrorMessage("La fecha desde debe ser igual o posterior a la fecha de hoy");
                    tokenSave = false;
//                    flag_submit_I = true;
                    return true;
                }
                if (SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {

                    if (novedad.getIdNovedadTipoDetalle().getAfectaProgramacion() == ConstantsUtil.ON_INT
                            && novedad.getIdNovedadTipoDetalle().getFechas() == ConstantsUtil.ON_INT) {
                        boolean ok = false;
                        /**
                         * Fecha desde de la novedad es mayo a hoy.
                         */
                        if (novedad.getDesde().after(MovilidadUtil.fechaHoy())) {
                            ok = true;
                            /**
                             * Fecha desde es menor a hoy o es hoy y fecha hasta
                             * es mayor a hoy.
                             */
                        } else if ((novedad.getDesde().before(MovilidadUtil.fechaHoy())
                                || MovilidadUtil.dateSinHora(novedad.getDesde())
                                        .equals(MovilidadUtil.dateSinHora(MovilidadUtil.fechaHoy())))
                                && novedad.getHasta().after(MovilidadUtil.fechaHoy())) {
                            ok = true;
                        }
                        if (ok) {
                            if (ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getI_prgSerconMotivo() == null) {
                                MovilidadUtil.addErrorMessage("Se debe seleccionar un motivo para el ajuste de jornada.");
                                return true;

                            }
                        }
                    }
                }
                if (!listPrgTcGestionOperador.isEmpty()) {
                    Novedad n = novEJB.validarNovedadConFechas(listPrgTcGestionOperador.get(0).getIdEmpleado().getIdEmpleado(), novedad.getDesde(), novedad.getHasta());
                    if (n != null) {
                        MovilidadUtil.addErrorMessage("El operador ya tiene un novedad registrada para este periodo. " + Util.dateFormat(n.getDesde()) + " a " + Util.dateFormat(n.getHasta()));
                        tokenSave = false;
//                        flag_submit_I = true;
                        return true;
                    }
                } else {
                    if (!listPrgTcGestionVehiculo.isEmpty()) {
                        Novedad n = novEJB.validarNovedadConFechas(listPrgTcGestionVehiculo.get(0).getIdEmpleado().getIdEmpleado(), novedad.getDesde(), novedad.getHasta());
                        if (n != null) {
                            MovilidadUtil.addErrorMessage("El operador ya tiene un novedad registrada para este periodo. " + Util.dateFormat(n.getDesde()) + " a " + Util.dateFormat(n.getHasta()));
                            tokenSave = false;
//                            flag_submit_I = true;
                            return true;
                        }
                    }
                    if (!listPrgTcGestionEliminarAux.isEmpty()) {
                        Novedad n = novEJB.validarNovedadConFechas(listPrgTcGestionEliminarAux.get(0).getObjPrgTc().getIdEmpleado().getIdEmpleado(), novedad.getDesde(), novedad.getHasta());
                        if (n != null) {
                            MovilidadUtil.addErrorMessage("El operador ya tiene un novedad registrada para este periodo. " + Util.dateFormat(n.getDesde()) + " a " + Util.dateFormat(n.getHasta()));
                            tokenSave = false;
//                            flag_submit_I = true;
                            return true;
                        }
                    }
                    if (!listPrgTcGestionEliminar.isEmpty()) {
                        Novedad n = novEJB.validarNovedadConFechas(listPrgTcGestionEliminar.get(0).getObjPrgTc().getIdEmpleado().getIdEmpleado(), novedad.getDesde(), novedad.getHasta());
                        if (n != null) {
                            MovilidadUtil.addErrorMessage("El operador ya tiene un novedad registrada para este periodo. " + Util.dateFormat(n.getDesde()) + " a " + Util.dateFormat(n.getHasta()));
                            tokenSave = false;
//                            flag_submit_I = true;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Método encargado persistir en BD la gestión de las 3 opciones (Vehículo,
     * Operador, Eliminar) en la gestión de servicios, valida que todos las
     * lista y variables esten listas para persistir.
     *
     * Para cumplir su cometido, valida si el proceso que se va a realizar es de
     * gestion de eliminacion, gestión de operador o gestion de vehículo y luego
     * continua tsu flujo.
     *
     * Invoca los métodos:
     *
     * 1. guardarEliminado. Responsable persistir en BD la gestion de
     * eliminación de servicios.
     *
     * 2. guardarCambioVechiculo. Responsable de persistir en BD la gestión de
     * vehículo.
     *
     * 3. guardarCambioOp. Responsable de persistir en BD la gestió de operador.
     *
     *
     * @throws ParseException
     */
    @Transactional
    public void guardarTodo() throws ParseException {

        boolean b_eliminar = false; //bandera para saber si se puede eliminar
        boolean b_vehiculo = false; //bandera para saber si se puede hacer cambion de vehículo
        boolean b_operador = false; //bandera para saber si se puede realizar la gestion de operador
        listNovedadPrgTc = new ArrayList<>();
        vehiculoParaNov = null;
        empleadoParaNov = null;
        vehiculoParaNovNew = null;
        empleadoParaNovNew = null;
        tokenSave = false;
        try {
//            if (validarDatosNovedad()) {
//                return;
//            }
            if (!b_isNovAccidente) {
                b_afectaPrgTc = true;
            }
            /**
             * Se verifica si hay eliminación parcial y completa de servicios
             */
            if (!listPrgTcGestionEliminarAux.isEmpty()
                    || !listPrgTcGestionEliminar.isEmpty() && b_afectaPrgTc) {

                if (listPrgTcGestionEliminarAux.size() > 1) {
                    MovilidadUtil.addErrorMessage("Acción no permitida al eliminar, Tiene dos servicios seleccionados parcialmente");
                    tokenSave = false;
//                    flag_submit_I = true;
                    return;
                }
                if (!listPrgTcGestionEliminarAux.isEmpty()) {
                    if (prgTcVacOrVaccom == null) {
                        MovilidadUtil.addErrorMessage("No se ha agregado el vacío");
                        tokenSave = false;
//                        flag_submit_I = true;
                        return;
                    }
                }
                if (empleadoParaNov == null) {
                    if (!listPrgTcGestionEliminarAux.isEmpty()) {
                        empleadoParaNov = new Empleado();
                        empleadoParaNov.setIdEmpleado(prgTc.getIdEmpleado().getIdEmpleado());
                    } else {
                        if (!listPrgTcGestionEliminar.isEmpty()) {
                            empleadoParaNov = new Empleado();
                            empleadoParaNov.setIdEmpleado(prgTc.getIdEmpleado().getIdEmpleado());
                        }
                    }
                }
                if (vehiculoParaNov == null) {
                    if (!listPrgTcGestionEliminarAux.isEmpty()) {
                        vehiculoParaNov = new Vehiculo();
                        vehiculoParaNov.setIdVehiculo(listPrgTcGestionEliminarAux.get(0).getObjPrgTc().getIdVehiculo().getIdVehiculo());
                    } else {
                        if (!listPrgTcGestionEliminar.isEmpty()) {
                            vehiculoParaNov = new Vehiculo();
                            vehiculoParaNov.setIdVehiculo(listPrgTcGestionEliminar.get(0).getObjPrgTc().getIdVehiculo().getIdVehiculo());
                        }
                    }
                }
                b_eliminar = true; //Se puede realizar la gestión de elimación
            } else {
                if (empleadoParaNov == null) {
                    if (!b_afectaPrgTc) {
                        empleadoParaNov = new Empleado();
                        empleadoParaNov.setIdEmpleado(prgTc.getIdEmpleado().getIdEmpleado());
                    }
                }
                if (vehiculoParaNov == null) {
                    if (!b_afectaPrgTc) {
                        vehiculoParaNov = new Vehiculo();
                        vehiculoParaNov.setIdVehiculo(prgTc.getIdVehiculo().getIdVehiculo());
                    }
                }
            }

            if (!listPrgTcGestionVehiculo.isEmpty() && b_afectaPrgTc) {
                if (listPrgTcGestionVehiculo.size() > 1 && unoAUnoVehiculo) {
                    MovilidadUtil.addErrorMessage("Se debe selececcionar un solo servicio, de donde se efectuará en cambio");
                    tokenSave = false;
//                    flag_submit_I = true;
                    return;
                }
                /**
                 * Permite la desasignación del vehículo a los servicios a
                 * través del null
                 */
                if (!codigoV.equalsIgnoreCase("null")) {
                    if (unoAUnoVehiculo) {
                        if (prgTcUnoAUno == null && vehiculo == null) {
                            MovilidadUtil.addErrorMessage("No se ha cargado un vehículo");
                            tokenSave = false;
//                        flag_submit_I = true;
                            return;
                        }
                        if (vehiculo != null) {
                            if (entradaStopPoint == null && prgTcVacOrVaccom != null) {
                                MovilidadUtil.addErrorMessage("No se han cargados los puntos de entrada");
                                tokenSave = false;
//                            flag_submit_I = true;
                                return;
                            }
                            if (entradaStopPoint == null || salidaStopPoint == null && prgTcVacOrVaccom == null) {
                                MovilidadUtil.addErrorMessage("No se han cargados los puntos de entrada y salida");
                                tokenSave = false;
//                            flag_submit_I = true;
                                return;
                            }
                        }
                    } else {
                        if (vehiculo == null) {
                            MovilidadUtil.addErrorMessage("No se ha cargado un vehículo");
                            tokenSave = false;
//                        flag_submit_I = true;
                            return;
                        }
                        if (entradaStopPoint == null && prgTcVacOrVaccom != null) {
                            MovilidadUtil.addErrorMessage("No se ha cargado punto de entrada");
                            tokenSave = false;
//                        flag_submit_I = true;
                            return;
                        }
                        if (entradaStopPoint == null || salidaStopPoint == null && prgTcVacOrVaccom == null) {
                            MovilidadUtil.addErrorMessage("No se han cargados los puntos de entrada y salida");
                            tokenSave = false;
//                        flag_submit_I = true;
                            return;
                        }
                    }
                } else {
                    tokenSave = true;
                }
                vehiculoParaNov = new Vehiculo();
                empleadoParaNov = new Empleado();
                if (prgTc.getIdVehiculo() != null) {
                    vehiculoParaNov.setIdVehiculo(prgTc.getIdVehiculo().getIdVehiculo());
                } else {
                    vehiculoParaNov.setIdVehiculo(listPrgTcGestionVehiculo.get(0).getIdVehiculo().getIdVehiculo());
                }
                if (prgTc.getIdEmpleado() != null) {
                    empleadoParaNov.setIdEmpleado(prgTc.getIdEmpleado().getIdEmpleado());
                } else {
                    empleadoParaNov.setIdEmpleado(listPrgTcGestionVehiculo.get(0).getIdEmpleado().getIdEmpleado());
                }

                b_vehiculo = true; //Se puede realizar la gestión de vehículo

            } else {
                if (empleadoParaNov == null) {
                    if (!b_afectaPrgTc) {
                        empleadoParaNov = new Empleado();
                        empleadoParaNov.setIdEmpleado(prgTc.getIdEmpleado().getIdEmpleado());
                    }
                }
                if (vehiculoParaNov == null) {
                    if (!b_afectaPrgTc) {
                        vehiculoParaNov = new Vehiculo();
                        vehiculoParaNov.setIdVehiculo(prgTc.getIdVehiculo().getIdVehiculo());
                    }
                }
            }

            if (!listPrgTcGestionOperador.isEmpty() && b_afectaPrgTc) {
                if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                    if (!ajusteJornadaFromGestionServicio.isDisponibleAlCambio()) {
                        if (ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getPrgSerconMotivo() == null) {
                            MovilidadUtil.addErrorMessage("Se debe seleccionar un motivo para el ajuste de jornada.");
                            tokenSave = false;
                            return;
                        }
                    }
                    if (ajusteJornadaFromGestionServicio.isFlagAjusteFound()) {
                        if (ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getPrgSerconMotivo() == null) {
                            MovilidadUtil.addErrorMessage("Se debe seleccionar un motivo para el ajuste de jornada.");
                            tokenSave = false;
                            return;
                        }
                    }
                }
                if (!codigoTm.equalsIgnoreCase("null")) {
                    if (prgTcOperadorDisponible == null) {
                        MovilidadUtil.addErrorMessage("No a consultado el Operador");
                        PrimeFaces.current().ajax().update("formGestionPrgTc:msgsGestionPrgTc");
                        tokenSave = false;
//                        flag_submit_I = true;
                        return;
                    }

                    String hIniTurnoACambiar;
                    int dif;
                    PrgTc primerServicio = Collections.min(listPrgTcGestionOperador, Comparator.comparing(tc -> MovilidadUtil.toSecs(tc.getTimeOrigin())));
                    Date dateBefore = MovilidadUtil.sumarDias(primerServicio.getFecha(), -1);
                    Date dateAfter = MovilidadUtil.sumarDias(primerServicio.getFecha(), 1);
                    PrgTc ultimoServicioAyerRemplazo = prgTcEJB
                            .findFirtOrEndServiceByIdEmpleado(
                                    prgTcOperadorDisponible.getIdEmpleado().getIdEmpleado(),
                                    dateBefore, "DESC");
                    if (ultimoServicioAyerRemplazo != null) {

                        String sTimeOriginRemp = primerServicio.getTimeOrigin();
                        String timeDestinyDiaAnterior = ultimoServicioAyerRemplazo.getTimeDestiny();
                        if (MovilidadUtil.toSecs(timeDestinyDiaAnterior) == 0) {
                            sTimeOriginRemp = "24:00:00";
                        }
                        hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRemp, "24:00:00");

                        dif = MovilidadUtil.diferencia(timeDestinyDiaAnterior, hIniTurnoACambiar);

                        if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                            MovilidadUtil.addErrorMessage("El siguiente cambio no está respetando " + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO) + " de descanso con respecto al dia anterior.");
                            return;
                        } else {
                            PrgTc ultimoServicioMananaRemplazo = prgTcEJB
                                    .findFirtOrEndServiceByIdEmpleado(
                                            prgTcOperadorDisponible.getIdEmpleado().getIdEmpleado(),
                                            dateAfter, "ASC");
                            if (ultimoServicioMananaRemplazo != null) {
                                String sTimeOriginRempSig = ultimoServicioMananaRemplazo.getTimeOrigin();

                                if (MovilidadUtil.toSecs(sTimeOriginRempSig) == 0) {
                                    sTimeOriginRempSig = "24:00:00";
                                }

                                hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRempSig, "24:00:00");
                                dif = MovilidadUtil.diferencia(primerServicio.getTimeDestiny(), hIniTurnoACambiar);
                                if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                                    MovilidadUtil.addErrorMessage("El siguiente cambio no está respetando " + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO) + " de descanso con respecto al dia siguiente.");
                                    return;
                                }
                            }
                        }
                    }
                }
                /**
                 * Cargar empleado para la novedad
                 */
                empleadoParaNov = new Empleado(prgTc.getIdEmpleado().getIdEmpleado());

                b_operador = true; //Se puede realizar la gestión de operador
            } else {
                if (empleadoParaNov == null) {
                    if (!b_afectaPrgTc) {
                        empleadoParaNov = new Empleado();
                        empleadoParaNov.setIdEmpleado(prgTc.getIdEmpleado().getIdEmpleado());
                    }
                }
            }

            novedad.setIdVehiculo(vehiculoParaNov);
            novedad.setObservaciones(observacionesPrgTc);
            if (vehiculoParaNov != null && novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
                if (novedadDuplicadaBean.validarDuplicidadNovConVehiculo(novedad.getObservaciones(),
                        user.getUsername(), vehiculoParaNov.getIdVehiculo(), true)) {
                    return;
                }
            }

            if (b_eliminar && b_afectaPrgTc) {
                /**
                 * guardar la gestion de eliminar y retornar una variable
                 * boolean para saber si todo esta bien. si es False, no salio
                 * bien el proceso.
                 */
                boolean guardarEliminadoFlag = guardarEliminado();
                if (!guardarEliminadoFlag) {
                    tokenSave = false;
//                    flag_submit_I = true;
                    PrimeFaces.current().ajax().update("formGestionPrgTc:msgsGestionPrgTc");
                    throw new NullPointerException();
                } else {
                    tokenSave = true;
                }
            }
            if (b_vehiculo && b_afectaPrgTc) {
                /**
                 * guardar la gestion de vehículo y retornar una variable
                 * boolean para saber si todo esta bien. si es False, no salio
                 * bien el proceso.
                 */
                boolean guardarCambioVechiculoFlag = guardarCambioVechiculo();
                if (!guardarCambioVechiculoFlag) {
                    tokenSave = false;
//                    flag_submit_I = true;
                    PrimeFaces.current().ajax().update("formGestionPrgTc:msgsGestionPrgTc");
                    throw new NullPointerException();
                } else {
                    tokenSave = true;
                }
            }
            if (b_operador && b_afectaPrgTc) {
                /**
                 * guardar la gestion de operador y retornar una variable
                 * boolean para saber si todo esta bien. si es False, no salio
                 * bien el proceso.
                 */
                boolean guardarCambioOpFlag = guardarCambioOp();
                if (!guardarCambioOpFlag) {
                    tokenSave = false;
//                    flag_submit_I = true;
                    PrimeFaces.current().ajax().update("formGestionPrgTc:msgsGestionPrgTc");
                    throw new NullPointerException();
                } else {
                    tokenSave = true;
                    if (vehiculoParaNov == null) {
                        for (PrgTc tc : listPrgTcGestionOperador) {
                            if (tc.getIdVehiculo() != null) {
                                vehiculoParaNov = new Vehiculo();
                                vehiculoParaNov.setIdVehiculo(tc.getIdVehiculo().getIdVehiculo());
                                break;
                            }
                        }
                    }
                }
            }

        } catch (NullPointerException | ParseException e) {
//            flag_submit_I = true;
            MovilidadUtil.addErrorMessage("No se realizó algún cambio");
            e.printStackTrace();
            throw new NullPointerException();
        }
    }

    @Transactional
    public void guardarEliminadoParcialFromCabecera() throws ParseException {
        listNovedadPrgTc = new ArrayList<>();
        boolean b_eliminar = false; //bandera para saber si se puede eliminar
        vehiculoParaNov = null;
        empleadoParaNov = null;
        tokenSave = false;
//        if (validarDatosNovedad()) {
//            return;
//        }
        if (!b_isNovAccidente) {
            b_afectaPrgTc = true;
        }
        /**
         * Se verifica si hay eliminación parcial y completa de servicios
         */
        if (!listPrgTcGestionEliminarAux.isEmpty()
                || !listPrgTcGestionEliminar.isEmpty() && b_afectaPrgTc) {

            if (listPrgTcGestionEliminarAux.size() > 1) {
                MovilidadUtil.addErrorMessage("Acción no permitida al eliminar, Tiene dos servicios seleccionados parcialmente");
                tokenSave = false;
//                flag_submit_I = true;
                return;
            }
            if (!listPrgTcGestionEliminarAux.isEmpty()) {
                if (prgTcVacOrVaccom == null) {
                    MovilidadUtil.addErrorMessage("No se ha agregado el vacío");
                    tokenSave = false;
//                    flag_submit_I = true;
                    return;
                }
            }
            if (empleadoParaNov == null) {
                if (!listPrgTcGestionEliminarAux.isEmpty()) {
                    empleadoParaNov = new Empleado();
                    empleadoParaNov.setIdEmpleado(prgTc.getIdEmpleado().getIdEmpleado());
                } else {
                    if (!listPrgTcGestionEliminar.isEmpty()) {
                        empleadoParaNov = new Empleado();
                        empleadoParaNov.setIdEmpleado(prgTc.getIdEmpleado().getIdEmpleado());
                    }
                }
            }
            if (vehiculoParaNov == null) {
                if (!listPrgTcGestionEliminarAux.isEmpty()) {
                    vehiculoParaNov = new Vehiculo();
                    vehiculoParaNov.setIdVehiculo(listPrgTcGestionEliminarAux.get(0).getObjPrgTc().getIdVehiculo().getIdVehiculo());
                } else {
                    if (!listPrgTcGestionEliminar.isEmpty()) {
                        vehiculoParaNov = new Vehiculo();
                        vehiculoParaNov.setIdVehiculo(listPrgTcGestionEliminar.get(0).getObjPrgTc().getIdVehiculo().getIdVehiculo());
                    }
                }
            }
            b_eliminar = true; //Se puede realizar la gestión de elimación.
        } else {
            if (empleadoParaNov == null) {
                if (!b_afectaPrgTc) {
                    empleadoParaNov = new Empleado();
                    empleadoParaNov.setIdEmpleado(prgTc.getIdEmpleado().getIdEmpleado());
                }
            }
            if (vehiculoParaNov == null) {
                if (!b_afectaPrgTc) {
                    vehiculoParaNov = new Vehiculo();
                    vehiculoParaNov.setIdVehiculo(prgTc.getIdVehiculo().getIdVehiculo());
                }
            }
        }

        if (b_eliminar && b_afectaPrgTc) {
            /**
             * guardar la gestion de eliminar y retornar una variable boolean
             * para saber si todo esta bien. si es False, no salio bien el
             * proceso.
             */
            boolean guardarEliminadoFlag = guardarEliminadoDesdeCabecera();
            if (!guardarEliminadoFlag) {
                tokenSave = false;
//                flag_submit_I = true;
                PrimeFaces.current().ajax().update("formGestionPrgTc:msgsGestionPrgTc");
                throw new NullPointerException();
            } else {
                tokenSave = true;
            }
        }

    }

    /**
     * Método encargado de preparar las variables para la gestion de agregar
     * nuevos servicios. Desde el boton del panel principal
     *
     * @throws ParseException
     */
    public void prepareAddServiceFromButton() {
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar Unidad Funcional");
            return;
        }
        flagConciliado = validarFinOperacionBean.validarDiaBloqueado(fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        flagAddServicios = true;
        vehiculoParaNov = null;
        empleadoParaNov = null;
        servbusString = "";
        if (flagConciliado) {
            MovilidadUtil.addErrorMessage("Día Bloqueado.");
            return;
        }
        validarCierreTurno();
        if (flagTurnoCerrado) {
            MovilidadUtil.addErrorMessage("Turno Cerrado.");
            return;
        }
        resest();
        flagFromService = true;
        if (fechaConsulta == null) {
            MovilidadUtil.addErrorMessage("Seleccionar la fecha");
            return;
        }
        novedad = new Novedad();
        prgTcNewService = new PrgTc();
//        listDate = MovilidadUtil.ListaFechas(MovilidadUtil.fechaHoy());
        cargarResponsable();
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
            ajusteJornadaFromGestionServicio.resetHorasFound();
            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().setI_prgSerconMotivo(null);
            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().setPrgSerconMotivo(null);
        }
        MovilidadUtil.openModal("AdicionServicioDialog");
    }

    /**
     * Método encargado de copiar el código de servbus de un servicio
     * selecconado y escribirlo en el campo servbus para una busqueda mas
     * sencialla.
     *
     * Utiliza el objeto prgTc que se carga al hacer click sobre un servicio en
     * la tabla de servicio del panel principal.
     */
    public void copiarServbus() {
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Seleccione un servicio en la tabla");
        } else {
            if (prgTc.getServbus() == null) {
                MovilidadUtil.addErrorMessage("Acción no válida para este servicio");
                return;
            }
            borrarCampos();
            servBus = prgTc.getServbus();
            PrimeFaces.current().executeScript("$(function(){PrimeFaces.focus('prgtcForm:servBusPrtTc');});");

        }
    }

    /**
     * Método encargado de copiar el código TM de un servicio selecconado y
     * escribirlo en el campo código TM para una busqueda mas sencilla.
     *
     * Utiliza el objeto prgTc que se carga al hacer click sobre un servicio en
     * la tabla de servicio del panel principal.
     */
    public void copiarCodTm() {
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Seleccione un servicio en la tabla");
        } else {
            borrarCampos();
            if (prgTc.getIdEmpleado() != null) {
                codigoOperador = prgTc.getIdEmpleado().getCodigoTm().toString();
                PrimeFaces.current().executeScript("$(function(){PrimeFaces.focus('prgtcForm:operadorPrtTc');});");
            }
        }
    }

    /**
     * Método encargado de copiar el sercon de un servicio selecconado y
     * escribirlo en el campo sercon para una busqueda mas sencilla.
     *
     * Utiliza el objeto prgTc que se carga al hacer click sobre un servicio en
     * la tabla de servicio del panel principal.
     */
    public void copiarSercon() {
        if (prgTcSinOperador == null) {
            MovilidadUtil.addErrorMessage("Seleccione un servicio en la tabla");
        } else {
            borrarCampos();
            sercon = prgTcSinOperador.getSercon();
            PrimeFaces.current().executeScript("$(function(){PrimeFaces.focus('prgtcForm:serconPrtTc');});");

        }
    }

    /**
     * Método encargado de copiar el sercon de un servicio selecconado y
     * escribirlo en el campo sercon para una busqueda mas sencilla.
     *
     * Utiliza el objeto prgTc que se carga al hacer click sobre un servicio en
     * la tabla de servicio del panel principal.
     */
    public void copiarSerconPLeft() {
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Seleccione un servicio en la tabla");
        } else {
            borrarCampos();
            sercon = prgTc.getSercon();
            PrimeFaces.current().executeScript("$(function(){PrimeFaces.focus('prgtcForm:serconPrtTc');});");

        }
    }

    /**
     * Método encargado de preparar las variables para la gestion de agregar
     * nuevos servicios. Desde el menu contextual (Click derecho sobre un
     * servicio). el
     *
     *
     * Utiliza el objeto prgTc que se carga al hacer click sobre un servicio en
     * la tabla de servicio del panel principal, así qué, que la ventana
     * adicionarServicios, va estar con datos ya precargados, como el operador,
     * vehículo, tabla, sercon y hora inicio.
     *
     * @throws ParseException
     */
    public void prepareAddService() throws ParseException {
        flagAddServicios = true;
        vehiculoParaNov = null;
        empleadoParaNov = null;
        if (validaConciliarAndServicioSeleccionado()) {
            return;
        }
        resest();
        if (prgTc.getIdVehiculo() == null || prgTc.getIdEmpleado() == null) {
            MovilidadUtil.addErrorMessage("Acción no válida para este servicio");
            return;
        }

        novedad = new Novedad();
        if (prgTc.getTabla() == null) {
            tabla = MovilidadUtil.tabla();
        } else {
            tabla = prgTc.getTabla();
        }
        flagFromService = false;
        codigoV = prgTc.getIdVehiculo().getCodigo();
        vehiculoNewService = prgTc.getIdVehiculo();
        prgTcForGetOperador = prgTc;
        horaInicioString = prgTc.getTimeDestiny();
        codigoTm = Integer.toString(prgTc.getIdEmpleado().getCodigoTm());
        serconString = prgTc.getSercon();
        servbusString = prgTc.getServbus();
        String tipo = "TA";
        /**
         * Verificar si el tipo de servicio es biarticulado o articulado para
         * así colocar el identificaro de servbus correcto
         */
        if (prgTc.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
            tipo = "TB";
        }
        /**
         * Generar servbus random si aplica
         */
        if (servbusString == null
                || Util.getStringSinEspacios(servbusString).isEmpty()
                || (servbusString != null && !servbusString.contains("AD"))) {

            servbusString = MovilidadUtil.servbus(tipo);
        }
        prgTcNewService = new PrgTc();

        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
            ajusteJornadaFromGestionServicio.setIdEmpleadoFound(prgTc.getIdEmpleado().getIdEmpleado());
            ajusteJornadaFromGestionServicio.setFecha(fechaConsulta);
            ajusteJornadaFromGestionServicio.cargarPrgSerconFound();
            ajusteJornadaFromGestionServicio.cargarTotalHorasExtrasSmanal();
            ajusteJornadaFromGestionServicio.cargarListaServiciosOpFound();
            ajusteJornadaFromGestionServicio.recrearJornadaFound("formAdicionServicio:turnoEmplFound");
            ajusteJornadaFromGestionServicio.validarMaxHorasExtrasSmanales();
            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().setI_prgSerconMotivo(null);
            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().setPrgSerconMotivo(null);
        }

//        listDate = MovilidadUtil.ListaFechas(MovilidadUtil.fechaHoy());
        cargarResponsable();
        PrimeFaces.current().executeScript("PF('AdicionServicioDialog').show()");

    }

    /**
     * Método encargado de prepara las variables para la gestion de cambio de
     * horariio de un servicio.
     *
     * Utiliza el objeto prgTc que se carga al hacer click sobre un servicio en
     * la tabla de servicio del panel principal.
     *
     * @throws ParseException
     */
    public void prepareCambioHorario() throws ParseException {
        vehiculoParaNov = null;
        empleadoParaNov = null;
        if (validaConciliarAndServicioSeleccionado()) {
            return;
        }
        resest();
        horaInicio_string = prgTc.getTimeOrigin();
        horaFin_string = prgTc.getTimeDestiny();
        flagOperador = false;
        flagVehiculo = false;
        flagEliminar = false;
//        flag_submit_III = true;
        flagSinOperador = false;
        flagGuardarTodo = false;
        flagCambioHora = true;
        flagGestionServicios = true;
        cargarResponsable();
        novedad = new Novedad();
        b_afectaPrgTc = b_isNovAccidente = false;
        PrimeFaces.current().executeScript("PF('gestionPrgTcDialog').show()");
    }

    /**
     * Método encargado de persistis en BD la gestión de cambio de horario de un
     * servicio
     *
     * 1. GuardarNovedad. Responsable de persistir la información de una novedad
     * resultante de la gestion de servicios.
     *
     * 2. findServiceBy. consultar los servicio para actualizar la data del
     * panel principal.
     *
     * @throws ParseException
     */
    public void guardarCambioHora() throws ParseException {
//        if (flag_submit_III) {
//            flag_submit_III = false;

        if (observacionesPrgTc.replaceAll("\\s", "").isEmpty()) {
            MovilidadUtil.addErrorMessage("La descripción es requerida");
//                flag_submit_III = true;
            return;
        }
        if (i_tipoNovedad == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado el de Tipo de Novedad");
//                flag_submit_III = true;
            return;
        }
        if (i_tipoNovedaDet == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado el Detalle de Tipo de Novedad");
//                flag_submit_III = true;
            return;
        }
        if (i_codResponsable == 0) {
            MovilidadUtil.addErrorMessage("No ha seleccionado el responsable");
//                flag_submit_III = true;
            return;
        }
        if (novedad.getIdNovedadTipoDetalle() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar novedad tipo detalle.");
            return;
        }
        if (novedad.getIdNovedadTipoDetalle().getFechas() == ConstantsUtil.ON_INT && (novedad.getDesde() == null || novedad.getHasta() == null)) {
            MovilidadUtil.addErrorMessage("Las fechas son requeridas");
//                flag_submit_III = true;
            return;
        }
        if (prgTc.getIdEmpleado() != null) {
            Novedad n = novEJB.validarNovedadConFechas(prgTc.getIdEmpleado().getIdEmpleado(), novedad.getDesde(), novedad.getHasta());
            if (n != null) {
                MovilidadUtil.addErrorMessage("El operador ya tiene un novedad registrada para este periodo. " + Util.dateFormat(n.getDesde()) + " a " + Util.dateFormat(n.getHasta()));
//                    flag_submit_III = true;
                return;
            }
        }
        PrgTc tcValidar = new PrgTc();
        tcValidar.setTimeOrigin(horaInicio_string);
        tcValidar.setTimeDestiny(horaFin_string);
        tcValidar.setFecha(prgTc.getFecha());

        if (validarDescansoLegal(prgTc.getIdEmpleado(), tcValidar)) {
            return;
        }

        prgTc.setTimeOrigin(horaInicio_string);
        prgTc.setTimeDestiny(horaFin_string);
        prgTc.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
        prgTc.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
        prgTc.setModificado(MovilidadUtil.fechaCompletaHoy());
        prgTc.setUsername(user.getUsername());
        prgTc.setControl(codTMPrgTc);
        prgTc.setObservaciones(observacionesPrgTc);
        prgTcEJB.edit(prgTc);
        int index = listPrgTc.indexOf(prgTc);
        listPrgTc.add(index, prgTc);
        /**
         * Guardar novedad resultante de la gesstión cambio de horario
         */
        GuardarNovedad();
        NovedadPrgTc np = new NovedadPrgTc();

        np.setIdNovedad(novedad);
        np.setIdPrgTc(prgTc);
        np.setIdEmpleado(prgTc.getIdEmpleado());
        if (prgTc.getOldEmpleado() != null) {
            np.setIdOldEmpleado(new Empleado(prgTc.getOldEmpleado()));
        }
        np.setIdVehiculo(prgTc.getIdVehiculo());
        if (prgTc.getOldVehiculo() != null) {
            np.setIdOldVehiculo(new Vehiculo(prgTc.getOldVehiculo()));
        }
        np.setToStop(prgTc.getToStop());
        np.setFromStop(prgTc.getFromStop());
        np.setIdPrgTcResponsable(prgTc.getIdPrgTcResponsable());
        np.setObservaciones(prgTc.getObservaciones());
        np.setTimeOrigin(prgTc.getTimeOrigin());
        np.setTimeDestiny(prgTc.getTimeDestiny());
        np.setEstadoOperacion(prgTc.getEstadoOperacion());
        np.setDistancia(prgTcEJB.findDistandeByFromStopAndToStop(prgTc.getFromStop().getIdPrgStoppoint(), prgTc.getToStop().getIdPrgStoppoint()));
        np.setUsername(user.getUsername());
        np.setCreado(MovilidadUtil.fechaCompletaHoy());
        np.setEstadoReg(0);
        /**
         * Presistir en BD la relación resultante entre la novedad y el servicio
         * involucrado en la gestión de cambio de horario
         */
        NovPrgTcEJB.create(np);

        MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
        PrimeFaces.current().executeScript("PF('gestionPrgTcDialog').hide()");
        findServiceBy();
        PrimeFaces.current().ajax().update("prgtcForm:tblPrgTc");
        prgTc = null;
        codTMPrgTc = "";
        observacionesPrgTc = "";
//        } else {
//            MovilidadUtil.addErrorMessage("Boton deshabilitado");
//        }

    }

    /**
     * Método encargado de validar que la hora inicio no sea posterior a la hora
     * fin. Se utiliza para la gestion adicionar servicios.
     *
     * Utiliza las variables string que son suministradas por el usuario desde
     * la vista.
     */
    public void validarHora() {
        if (!horaInicioString.equals("") && !horaFinString.equals("")) {
            int dif = MovilidadUtil.diferencia(horaInicioString, horaFinString);
            if (dif < 0) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin");
                horaInicioString = "";
                horaFinString = "";
            }
        }
    }

    /**
     * Método encargado de validar que la hora inicio no sea posterior a la hora
     * fin. Se utiliza para la gestion de cambio de horario.
     *
     * * Utiliza las variables string que son suministradas por el usuario
     * desde la vista.
     */
    public void validarHoraCambioHora() {
        if (!horaInicio_string.equals("") && !horaFin_string.equals("")) {
            int dif = MovilidadUtil.diferencia(horaInicio_string, horaFin_string);
            if (dif < 0) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin");
                horaInicio_string = "";
                horaFin_string = "";
            }
        }
    }

    public void resest() {
        i_tipoNovedad = 0;
        i_tipoNovedaDet = 0;
        i_codResponsable = 0;
        i_idClasificacion = 0;
        i_idTarea = 0;
        i_idRuta = 0;
        i_idPuntoIniInt = 0;
        i_idPuntoFinInt = 0;
        idPuntoIni = null;
        idPuntoFin = null;
        observacionesNewService = "";
        observacionesPrgTc = "";
        destino = "";
        destinoVacio = "";
        codigoV = "";
        codigoTm = "";
        punto_finString = "";
        punto_inicioString = "";
        serconString = "";
        tabla = 1;
        listPrgTcAux = new ArrayList<>();
        i_tipoNovedadObj = null;
        i_tipoNovedaDetObj = null;
    }

    boolean validarHorasServicioAdicional() {
        int dif = MovilidadUtil.diferencia(horaInicioString, horaFinString);
        if (dif < 0) {
            MovilidadUtil.addErrorMessage("La hora inicio no debe ser mayor a la hora fin.");
            horaFinString = "";
            return true;
        }
        for (PrgTc obj : listPrgTcAux) {
            if (MovilidadUtil.toSecs(horaInicioString) > MovilidadUtil.toSecs(obj.getTimeOrigin())) {
                if (MovilidadUtil.toSecs(horaInicioString) < MovilidadUtil.toSecs(obj.getTimeDestiny())) {
                    MovilidadUtil.addErrorMessage("La hora inicio("
                            + horaInicioString + ") del servicio que desea agregar, esta dentro del rango de horas un servicio("
                            + obj.getTimeOrigin() + "-" + obj.getTimeDestiny() + ")");
                    return true;
                }
            }
            if (MovilidadUtil.toSecs(horaFinString) > MovilidadUtil.toSecs(obj.getTimeOrigin())) {
                if (MovilidadUtil.toSecs(horaFinString) < MovilidadUtil.toSecs(obj.getTimeDestiny())) {
                    MovilidadUtil.addErrorMessage("La hora fin("
                            + horaFinString + ") del servicio que desea agregar, esta dentro del rango de horas un servicio("
                            + obj.getTimeOrigin() + "-" + obj.getTimeDestiny() + ")");
                    return true;
                }
            }
            if (MovilidadUtil.toSecs(horaInicioString) <= MovilidadUtil.toSecs(obj.getTimeOrigin())) {
                if (MovilidadUtil.toSecs(horaFinString) >= MovilidadUtil.toSecs(obj.getTimeDestiny())) {
                    MovilidadUtil.addErrorMessage("El servicio que desea agregar(" + horaFinString + "-" + horaFinString + "), agrupa al servicio("
                            + obj.getTimeOrigin() + "-" + obj.getTimeDestiny() + ")");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método encargado de agregar a la lista temporal listPrgTcAux los nuevos
     * servicios en el apartado de adicionar servicios. para luego sean
     * persistidos guardarNewService.
     *
     */
    public void onAddServicio() {
        prgTcNewService.setIdTaskType(i_idTareaNewService);
        if (prgTcForGetOperador == null) {
            MovilidadUtil.addErrorMessage("Se debe cargar un Operador.");
            return;
        }
        if (vehiculoNewService == null) {
            MovilidadUtil.addErrorMessage("Se debe cargar un Vehículo.");
            return;
        }
        if (vehiculoNewService.getIdVehiculoTipo().getIdVehiculoTipo().equals(ConstantsUtil.ID_V_TIPO_DOS)
                && prgTcForGetOperador.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo()
                        .equals(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_OP_TIPO_UNO))) {
            if (prgTcForGetOperador.getIdEmpleado().getCertificado() == null
                    || !prgTcForGetOperador.getIdEmpleado().getCertificado().equals(ConstantsUtil.ON_INT)) {
                MovilidadUtil.addErrorMessage("Operador no habilitado para " + vehiculoNewService.getIdVehiculoTipo().getNombreTipoVehiculo()
                        + " " + empleadoToString(prgTcForGetOperador.getIdEmpleado()));
                return;
            }
        }
        if (horaInicioString.equals("")) {
            MovilidadUtil.addErrorMessage("Se debe indicar un tiempo de inicio para la tarea.");
            return;
        }
        if (horaFinString.equals("")) {
            MovilidadUtil.addErrorMessage("Se debe indicar un tiempo de fin para la tarea.");
            return;
        }
        if (tabla < 1) {
            MovilidadUtil.addErrorMessage("Se debe indicar un numero de tabla para la tarea.");
            return;
        }
        if (prgTcNewService.getIdTaskType() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar una Linea para la tarea.");
            return;
        }
        if (validarHorasServicioAdicional()) {
            return;
        }
        if ((prgTcNewService.getIdTaskType().getIdPrgTarea() > 3)) {
            if (i_idRuta == 0) {
                MovilidadUtil.addErrorMessage("Se debe seleccionar Ruta para la tarea");
                return;
            }
            prgTcNewService.setIdRuta(new PrgRoute(i_idRuta));
            if (idPuntoIni == null) {
                MovilidadUtil.addErrorMessage("Se debe seleccionar punto inicio para la tarea.");
                return;
            }
            if (idPuntoFin == null) {
                MovilidadUtil.addErrorMessage("Se debe seleccionar punto fin para la tarea.");
                return;
            }
        } else {
            if (i_idPuntoIniStopPoint == null) {
                MovilidadUtil.addErrorMessage("Se debe seleccionar punto inicio");
                return;
            }
            if (i_idPuntoFinStopPoint == null) {
                MovilidadUtil.addErrorMessage("Se debe seleccionar punto fin");
                return;
            }
        }

//        PrgTc prgTcEjecucion = prgTcEJB.validarServicioEnEjecucionByHora(horaInicioString, prgTcForGetOperador.getIdEmpleado().getCodigoTm(), fechaConsulta);
//        if (prgTcEjecucion != null) {
//            MovilidadUtil.addErrorMessage("Operado no disponible para este servicio");
//            return;
//        } else {
//            prgTcEjecucion = prgTcEJB.validarServicioEnEjecucionByHora(horaFinString, prgTcForGetOperador.getIdEmpleado().getCodigoTm(), fechaConsulta);
//            if (prgTcEjecucion != null) {
//                MovilidadUtil.addErrorMessage("Operado no disponible para este servicio");
//                return;
//            }
//        }
        if (prgTcNewService.getIdTaskType().getComercial() != 0) {
            prgTcNewService.setTabla(MovilidadUtil.tabla());
            prgTcNewService.setViajes(1);
            prgTcNewService.setTrayecto("Normal");
            prgTcNewService.setEstadoOperacion(3);
        }
        /**
         * Si la tarea del servicio en 3(id que e BD corresponde a la tarea
         * Vacio comercial), el movimiento será vacío pago o comercial.
         */
        if (isVaccom(prgTcNewService.getIdTaskType().getIdPrgTarea())) {
            /**
             * Estado de operadión asignados a los movimientos en vacío pagos o
             * comerciales
             */
            prgTcNewService.setEstadoOperacion(6);
        }
        if (isNotTareas(prgTcNewService.getIdTaskType().getIdPrgTarea())) {
            prgTcNewService.setFromStop(idPuntoIni.getIdPrgStoppoint());
            prgTcNewService.setToStop(idPuntoFin.getIdPrgStoppoint());
            double difDistancia = idPuntoFin.getDistance() - idPuntoIni.getDistance();
            prgTcNewService.setDistance(new BigDecimal(difDistancia));
        } else {
            prgTcNewService.setFromStop(i_idPuntoIniStopPoint);
            prgTcNewService.setToStop(i_idPuntoFinStopPoint);
            BigDecimal distancia = prgTcEJB.findDistandeByFromStopAndToStop(i_idPuntoIniStopPoint.getIdPrgStoppoint(), i_idPuntoFinStopPoint.getIdPrgStoppoint());
            prgTcNewService.setDistance(distancia);
        }
        /**
         * Si la tarea del servicio no es comercial, el movimiento en vacío será
         * no pago.
         */
        if (prgTcNewService.getIdTaskType().getComercial() == 0) {
            /**
             * Estado de operadión asignados a los movimientos en vacío no
             * pagos.
             */
            prgTcNewService.setEstadoOperacion(7);
        }
        prgTcNewService.setServbus(servbusString);
        prgTcNewService.setControl(codTMPrgTc);
        prgTcNewService.setIdEmpleado(prgTcForGetOperador.getIdEmpleado());
        prgTcNewService.setSercon(serconString);
        prgTcNewService.setIdVehiculo(vehiculoNewService);
        prgTcNewService.setIdVehiculoTipo(vehiculoNewService.getIdVehiculoTipo());
        prgTcNewService.setTimeOrigin(horaInicioString);
        prgTcNewService.setTabla(tabla);
        prgTcNewService.setFecha(fechaConsulta);
        prgTcNewService.setTimeDestiny(horaFinString);
        prgTcNewService.setWorkTime(prgTcForGetOperador.getWorkTime() == null ? "00:00:00" : prgTcForGetOperador.getWorkTime());
        prgTcNewService.setWorkPiece(1);
        prgTcNewService.setUsername(user.getUsername());
        prgTcNewService.setCreado(MovilidadUtil.fechaCompletaHoy());
        prgTcNewService.setEstadoReg(0);
        prgTcNewService.setObservaciones(observacionesNewService);
        prgTcNewService.setTaskDuration(MovilidadUtil.diferenciaHoras(prgTcNewService.getTimeOrigin(), prgTcNewService.getTimeDestiny()));

        listPrgTcAux.add(prgTcNewService);
        listPrgTcAux.sort(SortPrgTcByTimeOrigin.getSortPrgTcByTimeOrigin());
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
            ajusteJornadaFromGestionServicio.recrearJornadaFound("formAdicionServicio:turnoEmplFound");
            ajusteJornadaFromGestionServicio.ajusteJornadasFoundII(listPrgTcAux, "formAdicionServicio:turnoEmplFound");
            ajusteJornadaFromGestionServicio.validarMaxHorasExtrasSmanales();
        }
        prgTcNewService = new PrgTc();
        horaInicioString = horaFinString;
        horaFinString = "";
        punto_inicioString = "";
        punto_finString = "";
        i_idPuntoIniInt = 0;
        i_idPuntoFinInt = 0;
        i_idPuntoIniStopPoint = null;
        i_idPuntoFinStopPoint = null;
    }

    /**
     * Método encargado de remover de la lista listPrgTcAux un servicio, desde
     * la gestion de adicionar servicios.
     *
     * @param index, indice del objeto a eliminar en la lista
     */
    public void onDeleteServicio(int index) {
        listPrgTcAux.remove(index);
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
            ajusteJornadaFromGestionServicio.recrearJornadaFound("formAdicionServicio:turnoEmplFound");
            ajusteJornadaFromGestionServicio.ajusteJornadasFoundII(listPrgTcAux, "formAdicionServicio:turnoEmplFound");
            ajusteJornadaFromGestionServicio.validarMaxHorasExtrasSmanales();
        }
    }

    /**
     * Método encargado de persistir en BD, cada uno de los servicio gestionados
     * en el apartado adicionar servicios.
     *
     * Desde este método tambien se persiste la novedad resultante de la
     * gestión. Se puede crear un accidente, en el caso de que sea una novedad
     * de tipo accidente.
     *
     * Se invoca el método notificar, éste responsable de notificar vía correo
     * la novedad generada.
     */
    public void guardarNewService() {
        try {
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                if (ajusteJornadaFromGestionServicio.isFlagRestringirMaxHorasExtras()) {
                    MovilidadUtil.addErrorMessage("No es posible guardar los cambio, operador sobrepasa el máximo de horas extras.");
                    return;
                }
                if (ajusteJornadaFromGestionServicio.isFlagRestringirMaxHrExtrasSmnal()) {
                    MovilidadUtil.addErrorMessage("No es posible guardar, se ha excedido el máximo de horas extras semanales.");
                    return;
                }
            }
            if (listPrgTcAux.isEmpty()) {
                MovilidadUtil.addErrorMessage("Se debe agregar al menos un servicio.");
                return;
            }
            if (i_tipoNovedaDet == 0) {
                MovilidadUtil.addErrorMessage("Se debe selecionar un Detalle Tipo de Novedad.");
                return;
            }
            if (i_codResponsable == 0) {
                MovilidadUtil.addErrorMessage("Se debe seleccionar un responsable.");
                return;
            }
            if (observacionesNewService == null) {
                MovilidadUtil.addErrorMessage("Se debe agregar una observación.");
                return;
            }
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                if (ajusteJornadaFromGestionServicio.isFlagAjusteFound()) {
                    if (ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getPrgSerconMotivo() == null) {
                        MovilidadUtil.addErrorMessage("Se debe seleccionar un motivo para el ajuste de jornada.");
                        return;
                    }
                }
            }
            for (PrgTc prgNS : listPrgTcAux) {
                prgNS.setIdGopUnidadFuncional(new GopUnidadFuncional(
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
                prgNS.setObservaciones(observacionesNewService);
                prgNS.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                prgNS.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                if (isVacPrg(prgNS.getIdTaskType().getIdPrgTarea())) {
                    prgNS.setIdRuta(null);
                }
                prgTcEJB.create(prgNS);
            }
            novedad = new Novedad();
            novedad.setIdNovedadTipo(i_tipoNovedadObj);
            novedad.setIdNovedadTipoDetalle(i_tipoNovedaDetObj);
            NovedadTipoDetalles nTD = novedadTDetEJB.find(i_tipoNovedaDet);

            novedad.setObservaciones(observacionesNewService);
            novedad.setFecha(listPrgTcAux.get(0).getFecha());
            novedad.setIdEmpleado(listPrgTcAux.get(0).getIdEmpleado());
            novedad.setLiquidada(0);
            novedad.setIdVehiculo(listPrgTcAux.get(0).getIdVehiculo());
            novedad.setProcede(0);
            novedad.setPuntosPm(0);
            novedad.setPuntosPmConciliados(0);
            novedad.setUsername(user.getUsername());
            novedad.setEstadoReg(0);
            novedad.setControl(codTMPrgTc);
            novedad.setIdGopUnidadFuncional(new GopUnidadFuncional(
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
            novedad.setCreado(MovilidadUtil.fechaCompletaHoy());
            if (nTD.getAfectaPm() == ConstantsUtil.ON_INT) {
                if (procedeNovedad()) {
                    novedad.setPuntosPm(nTD.getPuntosPm());
                    novedad.setPuntosPmConciliados(nTD.getPuntosPm());
                    novedad.setProcede(1);
                }
            }
            novEJB.create(novedad);
//            if (novedad.getIdNovedadTipo().getIdNovedadTipo().equals(MovilidadUtil.ID_TIPO_NOVEDAD_ACC)) {
////                guardarAccidente(novedad);
//                novedad.setPrgTc(listPrgTcAux.get(0));
//                accpreMB.guardarAccidente(novedad);
//            }
            for (PrgTc tc : listPrgTcAux) {
                NovedadPrgTc np = new NovedadPrgTc();
                np.setIdNovedad(novedad);
                np.setIdPrgTc(tc);
                np.setIdEmpleado(novedad.getIdEmpleado());
                np.setIdVehiculo(novedad.getIdVehiculo());
                np.setToStop(tc.getToStop());
                np.setFromStop(tc.getFromStop());
                np.setIdPrgTcResponsable(tc.getIdPrgTcResponsable());
                np.setObservaciones(tc.getObservaciones());
                np.setTimeOrigin(tc.getTimeOrigin());
                np.setTimeDestiny(tc.getTimeDestiny());
                np.setEstadoOperacion(tc.getEstadoOperacion());
                np.setDistancia(prgTcEJB.findDistandeByFromStopAndToStop(tc.getFromStop().getIdPrgStoppoint(), tc.getToStop().getIdPrgStoppoint()));
                np.setUsername(user.getUsername());
                np.setCreado(MovilidadUtil.fechaCompletaHoy());
                np.setEstadoReg(0);
                NovPrgTcEJB.create(np);
            }
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                List<PrgTc> listTareaDispoFound = prgTcEJB.tareasDispoByIdEmpeladoAndFechaAndUnidadFunc(novedad.getIdEmpleado().getIdEmpleado(),
                        fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                ajusteJornadaFromGestionServicio.setObservacion(observacionesNewService);
                ajusteJornadaFromGestionServicio.setListTareasDispFound(listTareaDispoFound);
                ajusteJornadaFromGestionServicio.guardarAjusteJornadaFound(listPrgTcAux);
            }
            /**
             * Notificar via correo
             */
            notificar();
            MovilidadUtil.addSuccessMessage("Se han guardado los cambios satisfactoriamente");
            MovilidadUtil.hideModal("AdicionServicioDialog");
            findServiceBy();
            MovilidadUtil.updateComponent("prgtcForm:tblPrgTc");
            codTMPrgTc = "";
            observacionesPrgTc = "";
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error interno, comuniquese con sistemas");
        }
    }

    /**
     * Método responsable de capturar la acción de seleccionar un servico desde
     * el panel principal y cargarlo en la variable prgTc. Ademas invoca al
     * método viewEmpleado, para viasualisar el empleado del servicio el la
     * parte superior derecha del panel principal.
     *
     * @param event
     * @throws Exception
     */
    public void onRowlClckSelect(final SelectEvent event) throws Exception {
        setPrgTc((PrgTc) event.getObject());
        unidadFuncionalSessionBean.setI_unidad_funcional(prgTc.getIdGopUnidadFuncional() == null
                ? null : prgTc.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        if (unidadFuncionalSessionBean.isFiltradoUF()) {
            selectGopUnidadFuncionalBean.setterNombre();
            MovilidadUtil.updateComponent("prgtcForm:uni_func");
            MovilidadUtil.updateComponent("relog_id");
        }
        flagConciliado = validarFinOperacionBean.validarDiaBloqueado(fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (prgTc.getIdEmpleado() != null) {
            empleadoListJSFManagedBean.setEmpl(prgTc.getIdEmpleado());
            emplJSFMB.viewEmpleado(prgTc.getIdEmpleado());
            setPrgTcSinOperador(null);
        } else {
            setPrgTcSinOperador(prgTc);
        }
        flagViewOption = !(prgTc.getEstadoOperacion() == 5 || prgTc.getEstadoOperacion() == 8);
        flagAsignarOperador = prgTcSinOperador != null && prgTcSinOperador.getIdEmpleado() == null;
        flagAsignarVehiculo = prgTc != null && prgTc.getIdVehiculo() == null && prgTc.getServbus() != null;
        flagEliminarXOperador = prgTc != null && prgTc.getIdEmpleado() == null && prgTc.getSercon() != null;
        flagEliminarXVehiculo = prgTc != null && prgTc.getServbus() != null && prgTc.getIdVehiculo() == null && prgTc.getSercon() != null;
        flagViewVerRecorrido = MovilidadUtil.toSecs(prgTc.getTimeOrigin()) < MovilidadUtil.toSecs(Util.dateToTimeHHMMSS(MovilidadUtil.fechaCompletaHoy()));
    }

    /**
     * Método encargado de cargar en la variable prgTcSinOperador al seleccionar
     * un servicio el tabla de servicios sin operador un servicio
     *
     * @param event
     * @throws Exception
     */
    public void servicioSinOperador(final SelectEvent event) throws Exception {
        setPrgTcSinOperador((PrgTc) event.getObject());
        borrarCampos();
        sercon = prgTcSinOperador.getSercon();
        findServiceBy();
    }

    /**
     * Método encargado de la busqueda de servicios sin vehículo desde el panel
     * central, del panel principal
     *
     * @param event
     * @throws Exception
     */
    public void servicioSinVehiculo(final SelectEvent event) throws Exception {
        PrgTc tc = (PrgTc) event.getObject();
        borrarCampos();
        servBus = tc.getServbus();
        findServiceBy();
    }

    /**
     * Método encargado consultar los servicios de un vehiculo seleccionado
     * desde el tab de vehiculos detenidos
     *
     * @param event
     * @throws Exception
     */
    public void findServVehiculoDetenido(final SelectEvent event) throws Exception {
        VehiculosDetenidosDTO detenidosDTO = ((VehiculosDetenidosDTO) event.getObject());
        borrarCampos();
        vehiculoC = detenidosDTO.get_Vehicle();
        findServiceBy();
    }

    /**
     * Método encargado de cargar en la variable prgTcOpDispo al seleccionar un
     * servicio el tabla de operadores disponibles. Ademas invoca al método
     * viewEmpleado, para viasualisar el empleado del servicio el la parte
     * superior derecha del panel principal.
     *
     * @param event
     * @throws Exception
     */
    public void onRowSelectOpDispo(final SelectEvent event) throws Exception {
        setPrgTcOpDispo((PrgTc) event.getObject());
//        emplJSFMB.viewEmpleado(prgTcOpDispo.getIdEmpleado());
        empleadoListJSFManagedBean.setEmpl(prgTcOpDispo.getIdEmpleado());

        codigoOperador = Integer.toString(prgTcOpDispo.getIdEmpleado().getCodigoTm());

        findServiceBy();
//        codigoOperador = "";

    }

    public void onRowClckSelectEntradaSalida(final SelectEvent event) throws Exception {
        PrimeFaces current = PrimeFaces.current();
        if (entradaSalida == 1) {
            setEntradaStopPoint((PrgStopPoint) event.getObject());
            entrada = entradaStopPoint.getName();
            current.ajax().update("formGestionPrgTc:tabViews:txtEntra");
        } else {
            setSalidaStopPoint((PrgStopPoint) event.getObject());
            salida = salidaStopPoint.getName();
            current.ajax().update("formGestionPrgTc:tabViews:txtSale");
        }
        current.ajax().update(":frmPrincipalEntradaSalidaList:tablaEntradaSalida");
        current.executeScript("PF('wvEntradaSalidaListDialog').hide()");
    }

    /**
     * Método encargado de cargar parada (PrgStopPoint) al seleccionar una
     * registro desde la tabla de puntos de parada consultadas.
     *
     * @param event
     * @throws Exception
     */
    public void onRowDblClckSelectStopPoint(final SelectEvent event) throws Exception {
        PrimeFaces current = PrimeFaces.current();
        setPrgStopPoint((PrgStopPoint) event.getObject());
        if (prgTcVacOrVaccom == null) {
            prgTcVacOrVaccom = new PrgTc();
        }
        /**
         * Según la variable componente se setea la parada en una parada porque
         * se usa el mismo método para trabajar dos modulos.
         */
        switch (componente) {
            case 0:
                prgTcVacOrVaccom.setToStop(prgStopPoint);
                destinoVacio = prgStopPoint.getName();
                current.ajax().update("formAdicionarVacio:txtDestino");
                current.ajax().update(":frmPrincipalStopPointList:tablaStopPoint");
                current.executeScript("PF('wvStopPointListDialog').hide()");
                break;
            case 1:
                flagPInicioPFin = true;
                setI_idPuntoIniStopPoint((PrgStopPoint) event.getObject());
                punto_inicioString = i_idPuntoIniStopPoint.getName();
                if (i_idPuntoFinStopPoint != null) {
                    if (i_idPuntoFinStopPoint.getIdPrgStoppoint().equals(i_idPuntoIniStopPoint.getIdPrgStoppoint())) {
                        i_idPuntoFinStopPoint = null;
                        i_idPuntoIniStopPoint = null;
                        punto_inicioString = "";
                        punto_finString = "";
                    }
                }
                current.ajax().update(":formAdicionServicio:addServ_pIniByLinea");
                current.ajax().update(":formAdicionServicio:addServ_pFinByLinea");
                current.ajax().update(":frmPrincipalStopPointList:tablaStopPoint");
                current.executeScript("PF('wvStopPointListDialog').hide()");
                break;
            default:
                setI_idPuntoFinStopPoint((PrgStopPoint) event.getObject());
                punto_finString = i_idPuntoFinStopPoint.getName();
                if (i_idPuntoIniStopPoint != null) {
                    if (i_idPuntoFinStopPoint.getIdPrgStoppoint().equals(i_idPuntoIniStopPoint.getIdPrgStoppoint())) {
                        i_idPuntoFinStopPoint = null;
                        i_idPuntoIniStopPoint = null;
                        punto_inicioString = "";
                        punto_finString = "";
                    }
                }
                current.ajax().update(":formAdicionServicio:addServ_pFinByLinea");
                current.ajax().update(":formAdicionServicio:addServ_pIniByLinea");
                current.ajax().update(":frmPrincipalStopPointList:tablaStopPoint");
                current.executeScript("PF('wvStopPointListDialog').hide()");
                break;
        }
    }

    public void setTipoNovedadDetAccesoRapido(NovedadTipoDetalles detalle) {
        i_tipoNovedad = detalle.getIdNovedadTipo().getIdNovedadTipo();
        i_tipoNovedaDet = detalle.getIdNovedadTipoDetalle();
        findById();
        setTipoNovedadDet();
    }

    public void setTipoNovedadDet() {
        if (i_tipoNovedaDet != 0) {
            i_tipoNovedaDetObj = novedadTDetEJB.find(i_tipoNovedaDet);
            // opciones para accidentalidad
            if (i_tipoNovedaDetObj != null && i_tipoNovedaDetObj.getReqHora() == ConstantsUtil.ON_INT) {
                novedad.setHora(Util.dateToTimeHHMMSS(new Date()));
            }
            // Logica para incluir la ubicacion del vehiculo cuando se crea el accidente

            //
            novedad.setIdNovedadTipoDetalle(i_tipoNovedaDetObj);

            salidaStopPoint = i_tipoNovedaDetObj.getFromStop() != null ? i_tipoNovedaDetObj.getFromStop() : null;
            entradaStopPoint = i_tipoNovedaDetObj.getToStop() != null ? i_tipoNovedaDetObj.getToStop() : null;
            entrada = i_tipoNovedaDetObj.getToStop() != null ? i_tipoNovedaDetObj.getToStop().getName() : "";
            salida = i_tipoNovedaDetObj.getFromStop() != null ? i_tipoNovedaDetObj.getFromStop().getName() : "";
            cargarResponsable();
            observacionesPrgTc = i_tipoNovedaDetObj.getDescripcionGestionServicio() != null ? i_tipoNovedaDetObj.getDescripcionGestionServicio() : "";
            i_idClasificacion = i_tipoNovedaDetObj.getIdPrgClasificacionMotivo() != null ? i_tipoNovedaDetObj.getIdPrgClasificacionMotivo().getIdPrgClasificacionMotivo() : 0;
            i_codResponsable = i_tipoNovedaDetObj.getIdPrgTcResponsable() != null ? i_tipoNovedaDetObj.getIdPrgTcResponsable().getIdPrgTcResponsable() : 0;

            if (i_codResponsable > 0) {
                cargarClasificacion();
            }

        }
        if (prgTc != null && novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
            selectDispSistemaBean.consultarSistema();
            if (prgTc.getIdVehiculo() != null) {
                if (novedadDuplicadaBean.validarDuplicidadNovConVehiculo(observacionesPrgTc,
                        user.getUsername(), prgTc.getIdVehiculo().getIdVehiculo(), true)) {
                }
            }
        }
    }

    /**
     * Método encargado de guardar la novedad resultante de la gestion de
     * servicios, del panel principal
     *
     * @throws ParseException
     */
    @Transactional
    public void GuardarNovedad() throws ParseException {

        if (novedad.getIdNovedadTipoDetalle().getFechas() == 0) {
            novedad.setDesde(null);
            novedad.setHasta(null);
        }
        novedad.setControl(codTMPrgTc);
        novedad.setEstadoNovedad(ConstantsUtil.NOV_ESTADO_ABIERTO);
        novedad.setOldVehiculo(vehiculoParaNovNew);
        novedad.setObservaciones(observacionesPrgTc);
        novedad.setFecha(prgTc.getFecha());
        novedad.setIdEmpleado(empleadoParaNov);
        novedad.setOldEmpleado(empleadoParaNovNew);
        if (novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
            novedad.setInmovilizado(inmovilizado ? 1 : 0);
        }

        /**
         * Verificar si la novedad afecta la programacion, y si es así se invoca
         * un método para desasignar al empleado de servicios futuros, si
         * aplica.
         */
        if (novedad.getIdNovedadTipoDetalle().getAfectaProgramacion() == ConstantsUtil.ON_INT && novedad.getIdNovedadTipoDetalle().getFechas() == ConstantsUtil.ON_INT) {
            /**
             * Fecha desde de la novedad es mayo a hoy.
             */
            if (novedad.getDesde().after(MovilidadUtil.fechaHoy())) {
                novEJB.desasignarOperador(novedad.getDesde(), novedad.getHasta(),
                        novedad.getIdEmpleado().getIdEmpleado());
                if (SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                    /**
                     * Ajuste de jornada al desasignar operador de servicios.
                     */
                    prgSerconEJB.ajustarJornadaCero(novedad.getIdEmpleado().getIdEmpleado(),
                            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getI_prgSerconMotivo(),
                            novedad.getDesde(), novedad.getHasta(), novedad.getObservaciones(), user.getUsername());
                }

                /**
                 * Fecha desde es menor a hoy o es hoy y fecha hasta es mayor a
                 * hoy.
                 */
            } else if ((novedad.getDesde().before(MovilidadUtil.fechaHoy())
                    || MovilidadUtil.dateSinHora(novedad.getDesde())
                            .equals(MovilidadUtil.dateSinHora(MovilidadUtil.fechaHoy())))
                    && novedad.getHasta().after(MovilidadUtil.fechaHoy())) {

                novEJB.desasignarOperador(MovilidadUtil.sumarDias(MovilidadUtil.fechaHoy(), 1),
                        novedad.getHasta(), novedad.getIdEmpleado().getIdEmpleado());

                if (SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                    /**
                     * Ajuste de jornada al desasignar operador de servicios.
                     */
                    prgSerconEJB.ajustarJornadaCero(novedad.getIdEmpleado().getIdEmpleado(),
                            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getI_prgSerconMotivo(),
                            MovilidadUtil.sumarDias(MovilidadUtil.fechaHoy(), 1),
                            novedad.getHasta(), novedad.getObservaciones(), user.getUsername());
                }
            }
        }
        if (novedad.getIdNovedadTipoDetalle().getAtv() == ConstantsUtil.ON_INT
                //                && atvNovedadBean.isB_atv()
                && novedad.getIdVehiculo() != null) {
            novedad.setEstadoNovedad(ConstantsUtil.NOV_ESTADO_ABIERTO);
            novedad.setIdAtvTipoAtencion(atvNovedadBean.getAtvTipoAtencion());
        }
        novedad.setLiquidada(0);
        if (vehiculoParaNov != null && vehiculoParaNov.getIdVehiculo() != null) {
            vehiculoParaNov = vehEJB.find(vehiculoParaNov.getIdVehiculo());
        }
        novedad.setIdVehiculo(vehiculoParaNov);
        novedad.setUsername(user.getUsername());
        novedad.setEstadoReg(0);
        novedad.setCreado(MovilidadUtil.fechaCompletaHoy());
        novedad.setProcede(0);
        novedad.setPuntosPm(0);
        novedad.setPuntosPmConciliados(0);
        novedad.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));

        /**
         * Verificar si la noveada afecta programa master y si es el caso
         * configurar la novedad para que aplique a nivel de puntos y de
         * afectación en el programa
         */
        if (novedad.getIdNovedadTipoDetalle().getAfectaPm() == ConstantsUtil.ON_INT) {
            if (procedeNovedad()) {
                novedad.setProcede(1);
                novedad.setPuntosPm(novedad.getIdNovedadTipoDetalle().getPuntosPm());
                novedad.setPuntosPmConciliados(novedad.getIdNovedadTipoDetalle().getPuntosPm());
            }
        }
        novEJB.create(novedad);
        direccionAcc = "";
        /**
         * Verificar si la novedad es de tipo accidente, y si es el caso,
         * invocar al método que persiste un accidente en base de datos
         */
        if (novedad.getIdNovedadTipo().getIdNovedadTipo().equals(ConstantsUtil.ID_TIPO_NOVEDAD_ACC)) {
//            guardarAccidente(novedad);
//            accidenteBean.guardarAccidente(novedad);
            novedad.setPrgTc(prgTc);
            GeocodingDTO cordenadas = accpreMB.guardarAccidente(novedad);
            direccionAcc = cordenadas != null ? cordenadas.getDireccion() : "";
        }
        /*
         * Si el detalle afecta disponibilidad, se modifica el estado del
         * vehículo por el estado parametrizado en el módulo Tipo Estado 
         * Detalle (Vehículos)
         */
        if (novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
            if (novedad.getIdVehiculo() != null) {
                novedad.getIdVehiculo().setIdVehiculoTipoEstado(novedad.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstado());
                vehEJB.updateEstadoVehiculo(novedad.getIdVehiculo().getIdVehiculo(), novedad.getIdVehiculo().getIdVehiculoTipoEstado().getIdVehiculoTipoEstado());
                vehiculoEstadoHistoricoSaveBean.guardarEstadoVehiculoHistorico(
                        novedad.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet(), novedad.getIdVehiculo(), null,
                        novedad.getIdNovedadTipoDetalle().getIdNovedadTipoDetalle(), null, novedad.getObservaciones(),
                        novedad.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstado(), true);
                /**
                 * Al Registrar una novedad que en el detalle afecte la
                 * disponibilidad, de acuerdo al sistema seleccionado se genera
                 * una clasificación para la novedad.
                 */
                DispClasificacionNovedad result = guardarClasificacion(novedad.getObservaciones(), novedad.getIdNovedadTipoDetalle());
                novedad.setIdDispClasificacionNovedad(result);
                this.novEJB.edit(novedad);
            }
        }
        if (novedad.getIdNovedadTipoDetalle().getAtv() == ConstantsUtil.ON_INT
                //                && atvNovedadBean.isB_atv()
                && novedad.getIdVehiculo() != null) {
            try {
                atvNovedadBean.sendAtvNovedad(novedad);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (novedad.getIdNovedadTipoDetalle().getNotificacion() == ConstantsUtil.ON_INT) {
            notificar();
        }
        if (novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
            notificarMtto();

            /**
             * Se envía notificación telegram a procesos que se encuentren
             * parametrizados en el módulo parametrización telegram
             */
            if (novedad.getIdNovedadTipoDetalle().getNotificacion() == ConstantsUtil.ON_INT
                    && novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {
                String codigoProcesoMtto = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.NOTIFICACION_PROCESO_MTTO);
                if (codigoProcesoMtto.equals(novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getCodigoProceso())) {
                    return;
                }
                if (novedad.getIdNovedadTipo().getIdNovedadTipo().equals(Util.ID_ACCIDENTE)) {
                    return;
                }
                enviarNotificacionTelegramMtto(novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos());
            }
        }
    }

    /**
     * Método encargado de crear el objeto de una clasificación de novedad y
     * persistir en base de datos.
     *
     * @param observacion observacion de la novedad que es heredado por la
     * clasificación de la novedad.
     * @param novedadTipoDetalle mediante este objeto se obtiene el primer
     * Estado Pendiente Actual para la Novedad generado según la parametrización
     * @return
     */
    DispClasificacionNovedad guardarClasificacion(String observacion, NovedadTipoDetalles novedadTipoDetalle
    ) {
        DispClasificacionNovedad clasificacionNovedad = new DispClasificacionNovedad();
        clasificacionNovedad.setCreado(MovilidadUtil.fechaCompletaHoy());
        clasificacionNovedad.setUsername(user.getUsername());
        clasificacionNovedad.setIdDispSistema(new DispSistema(selectDispSistemaBean.getId_dis_sistema()));
        int idVehiculoTipoEstadoDet = novedadTipoDetalle.getIdVehiculoTipoEstadoDet() == null ? 0
                : novedadTipoDetalle.getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstadoDet();
        if (idVehiculoTipoEstadoDet > 0) {
            DispEstadoPendActual primerEstadoPendienteActual
                    = vehiculoEstadoHistoricoSaveBean.getPrimerEstadoPendienteActual(idVehiculoTipoEstadoDet);
            clasificacionNovedad.setIdDispEstadoPendActual(primerEstadoPendienteActual);
        }
        clasificacionNovedad.setObservacion(observacion);
        clasificacionNovedadEJB.create(clasificacionNovedad);
        return clasificacionNovedad;
    }

    /**
     * Método encargado de persistir en BD el proceso de el apartado de eliminar
     * en la gestion de servicio, se valida si un servicio es eliminado de
     * manera parcial o completo.
     *
     * Valida si el proceso que se desea persistir en eliminado parcial de un
     * servicio, eliminado completo de servicios o los dos procesos
     *
     * @return
     */
    public boolean guardarEliminado() {
        try {
            /**
             * Si la lista listPrgTcGestionEliminarAux no esta vacia se procede
             * a realizar la operación de eliminación parcial
             */
            if (!listPrgTcGestionEliminarAux.isEmpty()) {

                /**
                 * Se consulta en BD el servicio que se va a eliminar parcial
                 * por medio de id del prgTc que contiene prgPattern.
                 * "parteEjecutada" sera la parte que se ejecutó del servicio
                 * eliminado parcialmente.
                 */
                PrgTc parteEjecutada = prgTcEJB.find(listPrgTcGestionEliminarAux.get(0).getObjPrgTc().getIdPrgTc());
                PrgPattern pdt = listPrgTcGestionEliminarAux.get(0).getObjPrgPattern();

                BigDecimal distRealProgramada = new BigDecimal(parteEjecutada.getDistance().doubleValue());
                BigDecimal distParadaFinalEliminado = new BigDecimal(pdt.getDistance());
                BigDecimal distParadaInicial = BigDecimal.ZERO;

                for (PrgPattern pptt : parteEjecutada.getIdRuta().getPrgPatternList()) {
                    if (parteEjecutada.getFromStop().getIdPrgStoppoint().equals(pptt.getIdPrgStoppoint().getIdPrgStoppoint())) {
                        distParadaInicial = new BigDecimal(pptt.getDistance());
                    }
                }
                /**
                 * Se calcula la distancia real recorrida, restandole a la
                 * distancia donde queda el servicio, la distancia de la parada
                 * incial del servicio esto se hace porque en ocaciones el
                 * servicio no comienda en la cabecera de la tarea.
                 */
                BigDecimal distRealRecorrida = distParadaFinalEliminado.subtract(distParadaInicial);
                /**
                 * se calcula la distancia real eliminada, restandole a la
                 * distancia real programada, la distancia real recorrida.
                 */
                BigDecimal distRealEliminada = distRealProgramada.subtract(distRealRecorrida);

                parteEjecutada.setToStop(pdt.getIdPrgStoppoint());
                parteEjecutada.setDistance(distRealRecorrida);
                /**
                 * La parte del servicio que se ejecutó tomara un estado de
                 * operación de 2 para servicio programados o 4 para servicios
                 * adicionales
                 */
                if (parteEjecutada.getServbus().endsWith("AD")) {
                    parteEjecutada.setEstadoOperacion(4);
                } else {
                    parteEjecutada.setEstadoOperacion(2);
                }
                parteEjecutada.setUsername(user.getUsername());
//                BigDecimal distanciaPrgTcDet = listPrgTcGestionEliminarAux.get(0).getObjPrgTc().getDistance().subtract(p.getDistance());
                boolean ok = false;
                int servsElimnados = 0;
                int servsEjecutados = 0;
                for (PrgPattern pd : parteEjecutada.getIdRuta().getPrgPatternList()) {
                    if (!ok) {
                        if (!pd.getIdPrgPattern().equals(pdt.getIdPrgPattern())) {
                            ok = false;
                            servsEjecutados++;
                        } else {
                            ok = true;
                            servsEjecutados++;
                        }
                    } else {
                        servsElimnados++;
                    }
                }
                parteEjecutada.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                parteEjecutada.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                parteEjecutada.setControl(codTMPrgTc);
                int horatotal = MovilidadUtil.diferencia(parteEjecutada.getTimeOrigin(), parteEjecutada.getTimeDestiny());
                int serviciosTotales = servsEjecutados + servsElimnados;
                int horaEjecutada = (int) servsEjecutados * horatotal / serviciosTotales;
                int horaNoEjecutada = (int) servsElimnados * horatotal / serviciosTotales;

                String horaFinServicioEjecutado = MovilidadUtil.sumarHoraSrting(parteEjecutada.getTimeOrigin(), MovilidadUtil.toTimeSec(horaEjecutada));

                /**
                 * "pPerdido" será la parte que ser perdió del servicio eliminó
                 * parcialmente
                 */
                PrgTc pPerdido = new PrgTc();
                /**
                 * Set undidad Funcional
                 */
                pPerdido.setIdGopUnidadFuncional(parteEjecutada.getIdGopUnidadFuncional());
                pPerdido.setFecha(parteEjecutada.getFecha());
                pPerdido.setIdTaskType(parteEjecutada.getIdTaskType());
                pPerdido.setTimeOrigin(horaFinServicioEjecutado);
                pPerdido.setTimeDestiny(parteEjecutada.getTimeDestiny());
                /**
                 * Parada incial en donde comienza el servicio eliminado
                 */
                pPerdido.setFromStop(pdt.getIdPrgStoppoint());
                /**
                 * Parada final en donde termina el servicio eliminado
                 */
                pPerdido.setToStop(listPrgTcGestionEliminarAux.get(0).getObjPrgTc().getToStop());
                pPerdido.setSercon(parteEjecutada.getSercon());
                pPerdido.setServbus(parteEjecutada.getServbus());
                /**
                 * Distancia de la parte del servicio que se pierde.
                 */
                pPerdido.setDistance(distRealEliminada);
                pPerdido.setCreado(MovilidadUtil.fechaCompletaHoy());
                pPerdido.setUsername(user.getUsername());
                pPerdido.setTabla(parteEjecutada.getTabla());
                pPerdido.setTaskDuration(parteEjecutada.getTaskDuration());
                pPerdido.setWorkPiece(parteEjecutada.getWorkPiece());
                pPerdido.setWorkTime(parteEjecutada.getWorkTime());
                pPerdido.setIdEmpleado(parteEjecutada.getIdEmpleado());
                pPerdido.setIdVehiculo(parteEjecutada.getIdVehiculo());
                pPerdido.setIdVehiculoTipo(parteEjecutada.getIdVehiculoTipo());
                pPerdido.setViajes(parteEjecutada.getViajes());
                pPerdido.setIdRuta(parteEjecutada.getIdRuta());
                pPerdido.setProductionDistance(parteEjecutada.getProductionDistance());
                pPerdido.setAmplitude(parteEjecutada.getAmplitude());
                pPerdido.setIdPrgTcResumen(parteEjecutada.getIdPrgTcResumen());

                /**
                 * Sí el servicio que se desea eliminar parcialmete es un
                 * servicio adicional, al ser eliminado toma un estado de
                 * operación 8, por lo contrario, sí es un servicio programdo,
                 * tomará un estado de operación 5
                 */
                if (pPerdido.getServbus().endsWith("AD")) {
                    pPerdido.setEstadoOperacion(8);
                } else {
                    pPerdido.setEstadoOperacion(5);
                }
                pPerdido.setEstadoReg(0);
                pPerdido.setObservaciones(observacionesPrgTc);
                pPerdido.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                pPerdido.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                pPerdido.setControl(codTMPrgTc);
                /**
                 * Se obtine la parada en donde quedo el servico para ser
                 * utilizada en respueda vía email deeste tipo de procesos
                 */
                lugarTQ = pPerdido.getFromStop();
                prgTcEJB.create(pPerdido);
                listNovedadPrgTc.add(pPerdido);
                /**
                 * Set TimeDestiny del servicio que se ejecutó parcialmente
                 */
                parteEjecutada.setTimeDestiny(horaFinServicioEjecutado);
                parteEjecutada.setObservaciones(observacionesPrgTc);
                prgTcEJB.edit(parteEjecutada);
                listNovedadPrgTc.add(parteEjecutada);

                /**
                 * "prgTcVacOrVaccom" será el servicio VAC/VACCOM que se generá
                 * obligatoriametne al realizar una eliminación de servicio
                 * parcial
                 */
                prgTcVacOrVaccom.setUsername(user.getUsername());
                prgTcVacOrVaccom.setIdVehiculo(pPerdido.getIdVehiculo());
                prgTcVacOrVaccom.setIdVehiculoTipo(pPerdido.getIdVehiculoTipo());
                /**
                 * Set undidad Funcional
                 */
                prgTcVacOrVaccom.setIdGopUnidadFuncional(parteEjecutada.getIdGopUnidadFuncional());
                /**
                 * Verificar si el tipo de servicio es biarticulado o articulado
                 * para así colocar el identificaro de servbus correcto
                 */
                String tipo = "TA";
                if (prgTcVacOrVaccom.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                    tipo = "TB";
                }
                prgTcVacOrVaccom.setServbus(MovilidadUtil.servbus(tipo));
                prgTcVacOrVaccom.setTabla(MovilidadUtil.tabla());
                /**
                 * Parada inicial en donde comienza el movimiento en vacío
                 */
                prgTcVacOrVaccom.setFromStop(pdt.getIdPrgStoppoint());
                prgTcVacOrVaccom.setObservaciones(observacionesPrgTc);
                /**
                 * Distancia total que VAC/VACCOM gastará
                 */
                BigDecimal distanciaVacio = prgTcEJB.findDistandeByFromStopAndToStop(prgTcVacOrVaccom.getFromStop().getIdPrgStoppoint(), prgTcVacOrVaccom.getToStop().getIdPrgStoppoint());
                prgTcVacOrVaccom.setDistance(distanciaVacio);
                if (prgTcVacOrVaccom.getFromStop().getIdPrgStoppoint().equals(prgTcVacOrVaccom.getToStop().getIdPrgStoppoint())) {
                    prgTcVacOrVaccom = new PrgTc();
                } else {
                    prgTcVacOrVaccom.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                    prgTcVacOrVaccom.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                    prgTcVacOrVaccom.setTimeOrigin(horaFinServicioEjecutado);
                    prgTcVacOrVaccom.setTimeDestiny(MovilidadUtil.sumarMinutosHora(horaFinServicioEjecutado, 10));
                    prgTcEJB.create(prgTcVacOrVaccom);
                    listNovedadPrgTc.add(prgTcVacOrVaccom);

                }

            }
            if (!listPrgTcGestionEliminar.isEmpty() && !listPrgTcGestionEliminarAux.isEmpty()) {
                for (ArbolViewPrgTc p : listPrgTcGestionEliminar) {
                    p.getObjPrgTc().setUsername(user.getUsername());
                    p.getObjPrgTc().setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                    p.getObjPrgTc().setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                    /**
                     * Sí el servicio que se desea eliminar parcialmete es un
                     * servicio adicional, al ser eliminado toma un estado de
                     * operación 8, por lo contrario, sí es un servicio
                     * programdo, tomará un estado de operación 5
                     */
                    if (p.getObjPrgTc().getServbus().endsWith("AD")) {
                        p.getObjPrgTc().setEstadoOperacion(8);
                    } else {
                        p.getObjPrgTc().setEstadoOperacion(5);
                    }
                    p.getObjPrgTc().setControl(codTMPrgTc);
                    p.getObjPrgTc().setObservaciones(observacionesPrgTc);
                    prgTcEJB.eliminarPrgTc(p.getObjPrgTc());
                    /**
                     * Se agrega el servicio o prgTc a la lista que luego
                     * persistirá la relación de la novedad con el servico
                     * involucrado
                     */
                    listNovedadPrgTc.add(p.getObjPrgTc());
                }
            }
            if (!listPrgTcGestionEliminar.isEmpty() && listPrgTcGestionEliminarAux.isEmpty()) {

                ArbolViewPrgTc prgTcFechaMenor = Collections.min(listPrgTcGestionEliminar,
                        Comparator.comparing(tc -> MovilidadUtil.toSecs(tc.getObjPrgTc().getTimeOrigin())));//Primer servicio seleccionado;

                ArbolViewPrgTc prgTcFechaMayor = Collections.max(listPrgTcGestionEliminar,
                        Comparator.comparing(tc -> MovilidadUtil.toSecs(tc.getObjPrgTc().getTimeOrigin())));//Último servicio seleccionado;
                for (ArbolViewPrgTc p : listPrgTcGestionEliminar) {
                    /**
                     * Sí el servicio que se desea eliminar parcialmete es un
                     * servicio adicional, al ser eliminado toma un estado de
                     * operación 8, por lo contrario, sí es un servicio
                     * programdo, tomará un estado de operación 5
                     */
                    if (p.getObjPrgTc().getServbus().endsWith("AD")) {
                        p.getObjPrgTc().setEstadoOperacion(8);
                    } else {
                        p.getObjPrgTc().setEstadoOperacion(5);
                    }
                    p.getObjPrgTc().setUsername(user.getUsername());
                    p.getObjPrgTc().setObservaciones(observacionesPrgTc);
                    p.getObjPrgTc().setControl(codTMPrgTc);
                    p.getObjPrgTc().setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
//                    System.out.println("clasificación->" + i_idClasificacion);
                    p.getObjPrgTc().setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                    if (lugarTQ == null && i_tipoNovedaDet == MovilidadUtil.ID_TQ04) {
                        lugarTQ = prgTcFechaMenor.getObjPrgTc().getFromStop();
                    }

                    prgTcEJB.eliminarPrgTc(p.getObjPrgTc());
                    /**
                     * Se agrega el servicio o prgTc a la lista que luego
                     * persistirá la relación de la novedad con el servico
                     * involucrado
                     */
                    listNovedadPrgTc.add(p.getObjPrgTc());
                }
                if (prgTcVacOrVaccom != null) {

                    prgTcVacOrVaccom.setIdVehiculo(listPrgTcGestionEliminar.get(0).getObjPrgTc().getIdVehiculo());
                    prgTcVacOrVaccom.setIdVehiculoTipo(listPrgTcGestionEliminar.get(0).getObjPrgTc().getIdVehiculoTipo());
                    /**
                     * Set undidad Funcional
                     */
                    prgTcVacOrVaccom.setIdGopUnidadFuncional(listPrgTcGestionEliminar.get(0).getObjPrgTc().getIdGopUnidadFuncional());
                    /**
                     * Verificar si el tipo de servicio es biarticulado o
                     * articulado para así colocar el identificaro de servbus
                     * correcto
                     */
                    String tipo = "TA";
                    if (prgTcVacOrVaccom.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                        tipo = "TB";
                    }
                    prgTcVacOrVaccom.setServbus(MovilidadUtil.servbus(tipo));
                    prgTcVacOrVaccom.setTabla(MovilidadUtil.tabla());
                    /**
                     * Parada inicial en donde comienza el movimiento en vacío
                     */
                    prgTcVacOrVaccom.setFromStop(prgTcFechaMenor.getObjPrgTc().getFromStop());
                    prgTcVacOrVaccom.setTimeOrigin(prgTcFechaMenor.getObjPrgTc().getTimeOrigin());
                    prgTcVacOrVaccom.setTimeDestiny(MovilidadUtil.sumarMinutosHora(prgTcFechaMenor.getObjPrgTc().getTimeOrigin(), 10));
                    prgTcVacOrVaccom.setObservaciones(observacionesPrgTc);
                    prgTcVacOrVaccom.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                    prgTcVacOrVaccom.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                    /**
                     * Distancia total que VAC/VACCOM gastará
                     */
                    BigDecimal distanciaVacio = prgTcEJB.findDistandeByFromStopAndToStop(prgTcVacOrVaccom.getFromStop().getIdPrgStoppoint(), prgTcVacOrVaccom.getToStop().getIdPrgStoppoint());
                    prgTcVacOrVaccom.setDistance(distanciaVacio);
                    prgTcEJB.create(prgTcVacOrVaccom);
                    /**
                     * Se agrega el servicio o prgTc a la lista que luego
                     * persistirá la relación de la novedad con el servico
                     * involucrado
                     */
                    listNovedadPrgTc.add(prgTcVacOrVaccom);

                }
            }

        } catch (ParseException e) {
//            flag_submit_I = true;
            return false;
        }
        return true;
    }

    /**
     * Método encargado de persistir en BD el proceso de el apartado de eliminar
     * en la gestion de servicio, se valida si un servicio es eliminado de
     * manera parcial o completo.
     *
     * Valida si el proceso que se desea persistir en eliminado parcial de un
     * servicio, eliminado completo de servicios o los dos procesos
     *
     * @return
     */
    public boolean guardarEliminadoDesdeCabecera() {
        try {
            /**
             * Si la lista listPrgTcGestionEliminarAux no esta vacia se procede
             * a realizar la operación de eliminación parcial
             */
            if (!listPrgTcGestionEliminarAux.isEmpty()) {

                /**
                 * Se consulta en BD el servicio que se va a eliminar parcial
                 * por medio de id del prgTc que contiene prgPattern.
                 * "parteEliminada" sera la parte que no se ejecutó del servicio
                 * eliminado parcialmente.
                 */
                ArbolViewPrgTc prgTcFechaMenor = null;
                if (!listPrgTcGestionEliminar.isEmpty()) {
                    prgTcFechaMenor = Collections.min(listPrgTcGestionEliminar,
                            Comparator.comparing(tc -> MovilidadUtil.toSecs(tc.getObjPrgTc().getTimeOrigin())));//Primer servicio seleccionado;
                }

                PrgTc parteEliminada = prgTcEJB.find(listPrgTcGestionEliminarAux.get(0).getObjPrgTc().getIdPrgTc());
                PrgPattern pdt = listPrgTcGestionEliminarAux.get(0).getObjPrgPattern();
                GopUnidadFuncional uf = parteEliminada.getIdGopUnidadFuncional();

                BigDecimal distRealProgramada = new BigDecimal(parteEliminada.getDistance().doubleValue());
                BigDecimal distParadaFinalEliminado = new BigDecimal(pdt.getDistance());
                BigDecimal distParadaInicial = BigDecimal.ZERO;

                for (PrgPattern pptt : parteEliminada.getIdRuta().getPrgPatternList()) {
                    if (parteEliminada.getFromStop().getIdPrgStoppoint().equals(pptt.getIdPrgStoppoint().getIdPrgStoppoint())) {
                        distParadaInicial = new BigDecimal(pptt.getDistance());
                    }
                }
                /**
                 * Se calcula la distancia real recorrida, restandole a la
                 * distancia donde queda el servicio, la distancia de la parada
                 * incial del servicio esto se hace porque en ocaciones el
                 * servicio no comienda en la cabecera de la tarea.
                 */
                BigDecimal distRealRecorrida = distParadaFinalEliminado.subtract(distParadaInicial);
                /**
                 * se calcula la distancia real eliminada, restandole a la
                 * distancia real programada, la distancia real recorrida.
                 */
                BigDecimal distRealEliminada = distRealProgramada.subtract(distRealRecorrida);

                parteEliminada.setToStop(pdt.getIdPrgStoppoint());
                parteEliminada.setDistance(distRealRecorrida);

                /**
                 * Sí el servicio que se desea eliminar parcialmete es un
                 * servicio adicional, al ser eliminado toma un estado de
                 * operación 8, por lo contrario, sí es un servicio programdo,
                 * tomará un estado de operación 5
                 */
                if (parteEliminada.getServbus().endsWith("AD")) {
                    parteEliminada.setEstadoOperacion(8);
                } else {
                    parteEliminada.setEstadoOperacion(5);
                }
                parteEliminada.setUsername(user.getUsername());
//                BigDecimal distanciaPrgTcDet = listPrgTcGestionEliminarAux.get(0).getObjPrgTc().getDistance().subtract(p.getDistance());
                boolean ok = false;
                int servsElimnados = 0;
                int servsEjecutados = 0;
                for (PrgPattern pd : parteEliminada.getIdRuta().getPrgPatternList()) {
                    if (!ok) {
                        if (!pd.getIdPrgPattern().equals(pdt.getIdPrgPattern())) {
                            ok = false;
                            servsEjecutados++;
                        } else {
                            ok = true;
                            servsEjecutados++;
                        }
                    } else {
                        servsElimnados++;
                    }
                }
                parteEliminada.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                parteEliminada.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                parteEliminada.setControl(codTMPrgTc);
                int horatotal = MovilidadUtil.diferencia(parteEliminada.getTimeOrigin(), parteEliminada.getTimeDestiny());
                int serviciosTotales = servsEjecutados + servsElimnados;
                int horaEjecutada = (int) servsEjecutados * horatotal / serviciosTotales;
                int horaNoEjecutada = (int) servsElimnados * horatotal / serviciosTotales;

                String horaFinServicioEjecutado = MovilidadUtil.sumarHoraSrting(parteEliminada.getTimeOrigin(), MovilidadUtil.toTimeSec(horaEjecutada));

                /**
                 * "parteEjecutada" será la parte que se ejecutó del servicio
                 * eliminado parcialmente
                 */
                PrgTc parteEjecutada = new PrgTc();
                /**
                 * Set undidad Funcional
                 */
                parteEjecutada.setIdGopUnidadFuncional(uf);
                parteEjecutada.setFecha(parteEliminada.getFecha());
                parteEjecutada.setIdTaskType(parteEliminada.getIdTaskType());
                parteEjecutada.setTimeOrigin(horaFinServicioEjecutado);
                parteEjecutada.setTimeDestiny(parteEliminada.getTimeDestiny());
                /**
                 * Parada incial en donde comienza el servicio ejecutado
                 */
                parteEjecutada.setFromStop(pdt.getIdPrgStoppoint());
                /**
                 * Parada final en donde termina el servicio ejecutado
                 */
                parteEjecutada.setToStop(listPrgTcGestionEliminarAux.get(0).getObjPrgTc().getToStop());
                parteEjecutada.setSercon(parteEliminada.getSercon());
                parteEjecutada.setServbus(parteEliminada.getServbus());
                /**
                 * Distancia de la parte del servicio que se recorre.
                 */
                parteEjecutada.setDistance(distRealEliminada);
                parteEjecutada.setCreado(MovilidadUtil.fechaCompletaHoy());
                parteEjecutada.setUsername(user.getUsername());
                parteEjecutada.setTabla(parteEliminada.getTabla());
                parteEjecutada.setTaskDuration(parteEliminada.getTaskDuration());
                parteEjecutada.setWorkPiece(parteEliminada.getWorkPiece());
                parteEjecutada.setWorkTime(parteEliminada.getWorkTime());
                parteEjecutada.setIdEmpleado(parteEliminada.getIdEmpleado());
                parteEjecutada.setIdVehiculo(parteEliminada.getIdVehiculo());
                parteEjecutada.setIdVehiculoTipo(parteEliminada.getIdVehiculoTipo());
                parteEjecutada.setViajes(parteEliminada.getViajes());
                parteEjecutada.setIdRuta(parteEliminada.getIdRuta());
                parteEjecutada.setProductionDistance(parteEliminada.getProductionDistance());
                parteEjecutada.setAmplitude(parteEliminada.getAmplitude());
                parteEjecutada.setIdPrgTcResumen(parteEliminada.getIdPrgTcResumen());

                /**
                 * La parte del servicio que se ejecutó tomara un estado de
                 * operación de 2 para servicio programados o 4 para servicios
                 * adicionales
                 */
                if (parteEjecutada.getServbus().endsWith("AD")) {
                    parteEjecutada.setEstadoOperacion(4);
                } else {
                    parteEjecutada.setEstadoOperacion(2);
                }
                parteEjecutada.setEstadoReg(0);
                parteEjecutada.setObservaciones(observacionesPrgTc);
                parteEjecutada.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                parteEjecutada.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                parteEjecutada.setControl(codTMPrgTc);
                /**
                 * Se obtine la parada en donde quedo el servico para ser
                 * utilizada en respueda vía email deeste tipo de procesos
                 */
                lugarTQ = parteEliminada.getFromStop();
                prgTcEJB.create(parteEjecutada);
                listNovedadPrgTc.add(parteEjecutada);
                /**
                 * Set TimeDestiny del servicio que se ejecutó parcialmente
                 */
                parteEliminada.setTimeDestiny(horaFinServicioEjecutado);
                parteEliminada.setObservaciones(observacionesPrgTc);
                prgTcEJB.edit(parteEliminada);
                listNovedadPrgTc.add(parteEliminada);

                /**
                 * "prgTcVacOrVaccom" será el servicio VAC/VACCOM que se generá
                 * obligatoriametne al realizar una eliminación de servicio
                 * parcial
                 */
                prgTcVacOrVaccom.setUsername(user.getUsername());
                prgTcVacOrVaccom.setIdVehiculo(parteEjecutada.getIdVehiculo());
                prgTcVacOrVaccom.setIdVehiculoTipo(parteEjecutada.getIdVehiculoTipo());
                /**
                 * Set undidad Funcional
                 */
                prgTcVacOrVaccom.setIdGopUnidadFuncional(uf);
                /**
                 * Verificar si el tipo de servicio es biarticulado o articulado
                 * para así colocar el identificaro de servbus correcto
                 */
                String tipo = "RG";
                if (prgTcVacOrVaccom.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                    tipo = "RG";
                }
                prgTcVacOrVaccom.setServbus(MovilidadUtil.servbus(tipo));
                prgTcVacOrVaccom.setTabla(parteEliminada.getTabla());
                /**
                 * Parada inicial en donde comienza el movimiento en vacío
                 */
                if (prgTcFechaMenor != null) {
                    if (MovilidadUtil.toSecs(prgTcFechaMenor.getObjPrgTc().getTimeOrigin())
                            < MovilidadUtil.toSecs(parteEliminada.getTimeOrigin())) {
                        prgTcVacOrVaccom.setFromStop(prgTcFechaMenor.getObjPrgTc().getFromStop());
                        prgTcVacOrVaccom.setTimeOrigin(prgTcFechaMenor.getObjPrgTc().getTimeOrigin());
                    }
                } else {
                    prgTcVacOrVaccom.setFromStop(parteEliminada.getFromStop());
                    prgTcVacOrVaccom.setTimeOrigin(MovilidadUtil.sumarMinutosHora(parteEliminada.getTimeOrigin(), 2));
                }

                if (prgTcVacOrVaccom.getToStop() == null) {
                    prgTcVacOrVaccom.setToStop(parteEjecutada.getFromStop());
                }
                prgTcVacOrVaccom.setObservaciones(observacionesPrgTc);
                /**
                 * Distancia total que VAC/VACCOM gastará
                 */
                BigDecimal distanciaVacio = prgTcEJB.findDistandeByFromStopAndToStop(prgTcVacOrVaccom.getFromStop().getIdPrgStoppoint(), prgTcVacOrVaccom.getToStop().getIdPrgStoppoint());
                prgTcVacOrVaccom.setDistance(distanciaVacio);
                if (prgTcVacOrVaccom.getFromStop().getIdPrgStoppoint().equals(prgTcVacOrVaccom.getToStop().getIdPrgStoppoint())) {
                    prgTcVacOrVaccom = new PrgTc();
                } else {
                    prgTcVacOrVaccom.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                    prgTcVacOrVaccom.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                    prgTcVacOrVaccom.setTimeDestiny(MovilidadUtil.sumarMinutosHora(parteEliminada.getTimeDestiny(), -2));
                    prgTcEJB.create(prgTcVacOrVaccom);
                    listNovedadPrgTc.add(prgTcVacOrVaccom);

                }

            }
            if (!listPrgTcGestionEliminar.isEmpty()) {
                for (ArbolViewPrgTc p : listPrgTcGestionEliminar) {
                    p.getObjPrgTc().setUsername(user.getUsername());
                    p.getObjPrgTc().setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                    p.getObjPrgTc().setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                    /**
                     * Sí el servicio que se desea eliminar parcialmete es un
                     * servicio adicional, al ser eliminado toma un estado de
                     * operación 8, por lo contrario, sí es un servicio
                     * programdo, tomará un estado de operación 5
                     */
                    if (p.getObjPrgTc().getServbus().endsWith("AD")) {
                        p.getObjPrgTc().setEstadoOperacion(8);
                    } else {
                        p.getObjPrgTc().setEstadoOperacion(5);
                    }
                    p.getObjPrgTc().setControl(codTMPrgTc);
                    p.getObjPrgTc().setObservaciones(observacionesPrgTc);
                    prgTcEJB.eliminarPrgTc(p.getObjPrgTc());
                    /**
                     * Se agrega el servicio o prgTc a la lista que luego
                     * persistirá la relación de la novedad con el servico
                     * involucrado
                     */
                    listNovedadPrgTc.add(p.getObjPrgTc());
                }
            }
        } catch (ParseException e) {
//            flag_submit_I = true;
            return false;
        }
        return true;
    }

    void desasignarOp(List<PrgTc> list) {
        for (PrgTc p : list) {
//            System.out.println("prgTc->" + p.toString());
            p.setObservaciones(observacionesPrgTc);
            p.setUsername(user.getUsername());
            if (p.getIdEmpleado() != null) {
                p.setOldEmpleado(p.getIdEmpleado().getIdEmpleado());
            } else {
                p.setOldEmpleado(null);
            }
            p.setControl(codTMPrgTc);
            p.setModificado(MovilidadUtil.fechaCompletaHoy());
            p.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
            p.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null
                    : new PrgClasificacionMotivo(i_idClasificacion));
            p.setIdEmpleado(null);
            prgTcEJB.desasignarOp(p);
            /**
             * Se agrega el servicio o prgTc a la lista que luego persistirá la
             * relación de la novedad con el servico involucrado
             */
            listNovedadPrgTc.add(p);
        }
    }

    /**
     * Método encargado de persistir en BD ell proceso de operador, en la
     * gestión de servicios, acá se procesa la información de cambio de operador
     * o desasignación.
     *
     * @return variable boolean como ventana para saber si se se realizó o no,
     * el proceso
     */
    public boolean guardarCambioOp() {
        try {
            /**
             * Sí el código es la palabra null, se efecturá el proceso de
             * desasignación de operador.
             */
            if (codigoTm.equalsIgnoreCase("null")) {
                if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                    ajusteJornadaFromGestionServicio.setIdEmpleadoFound(null);
                    ajusteJornadaFromGestionServicio.setPrgSerconFound(null);
                }
                desasignarOp(listPrgTcGestionOperador);
            } else {
                if (!listPrgTcGestionOperador.isEmpty()) {

                    Empleado op = prgTcOperadorDisponible.getIdEmpleado();
                    /**
                     * Empleado para seterar en la novedad, como empleado nuevo
                     * (el que remplaza al que estaba en el servicio)
                     */
                    empleadoParaNovNew = new Empleado();
                    empleadoParaNovNew.setIdEmpleado(op.getIdEmpleado());
                    for (PrgTc p : listPrgTcGestionOperador) {
                        if (p.getIdEmpleado() != null) {
                            p.setOldEmpleado(p.getIdEmpleado().getIdEmpleado());
                        } else {
                            p.setOldEmpleado(null);
                        }
                        p.setIdEmpleado(op);
                        p.setObservaciones(observacionesPrgTc);
                        p.setUsername(user.getUsername());
                        p.setControl(codTMPrgTc);
                        p.setModificado(MovilidadUtil.fechaCompletaHoy());
                        p.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                        p.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                        prgTcEJB.desasignarOp(p);
                        /**
                         * Se agrega el servicio o prgTc a la lista que luego
                         * persistirá la relación de la novedad con el servico
                         * involucrado
                         */
                        listNovedadPrgTc.add(p);
                    }
                }
                if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                    if (ajusteJornadaFromGestionServicio.isDesasignarOpeServNull()) {
                        desasignarOp(listDesasignarOpServNull);
                    }
                }
            }
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                ajusteJornadaFromGestionServicio.setObservacion(observacionesPrgTc);
                ajusteJornadaFromGestionServicio.guardarAjusteJornada();
            }
        } catch (Exception e) {
//            flag_submit_I = true;
            return false;
        }
        return true;
    }

    /**
     *
     * @return @throws ParseException
     */
    public boolean guardarCambioVechiculo() throws ParseException {
        try {
            /**
             * -----------------------------------------------------------------
             * Sí el cambio de vehículo es uno a uno, desde la vista se habrá
             * seleccionado un servicio con el vehículo en cuestión y consultado
             * otro vehículo inmediato. a partir de la hora inicial de los
             * servicios en donde esten asignados los vehículos, se realizará el
             * cambio hasta el ultimo servicio en donde esten asignados.
             * -----------------------------------------------------------------
             * Sí el cambio no es uno a uno, desde las vista se habrán
             * selecionado todos los servicios en donde este asignado el
             * vehículo en cuestión, y consultado el vehículo inmediato. A todos
             * los servicio seleccionados se les asignará el vehículo consultado
             * -----------------------------------------------------------------
             */
            if (unoAUnoVehiculo) {

                listPrgTcGestionVehiculo.get(0).setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                listPrgTcGestionVehiculo.get(0).setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                /**
                 * -------------------------------------------------------------
                 * prgTcUnoAUno tiene cargado el vehículo consultado para
                 * efectuar el cambio con el vehículo que esta cargado en el
                 * servicio selecconado desde la vista.
                 * -------------------------------------------------------------
                 * Sí prgTcUnoAUno es diferente de nulo, quiere decir que que el
                 * vehículo consultado no tiene servicio asignados.
                 * -------------------------------------------------------------
                 */
                if (prgTcUnoAUno != null) {
                    /**
                     * Cargar vehículo nuevo para setera en la novedad(El que
                     * remplaza al que estaba)
                     */
                    vehiculoParaNovNew = new Vehiculo();
                    vehiculoParaNovNew.setIdVehiculo(prgTcUnoAUno.getIdVehiculo().getIdVehiculo());
                    PrgTc prgTcArbolServAnterior = prgTcEJB.servicioAnterior(listPrgTcGestionVehiculo.get(0));
                    prgTcUnoAUno.setUsername(user.getUsername());
                    prgTcUnoAUno.setObservaciones(observacionesPrgTc);
                    listPrgTcGestionVehiculo.get(0).setControl(codTMPrgTc);
                    /**
                     * Se consultan previamente los servicios que se van a
                     * intervenir en el proceso para luego agregarlos a la lista
                     * que luego persistirá la relación de la novedad con el
                     * servico involucrado
                     */
                    List< PrgTc> listaPrgAfectados = prgTcEJB.changeOneToOneReturnPrgTcAfectados(listPrgTcGestionVehiculo.get(0), prgTcUnoAUno);
                    listNovedadPrgTc.addAll(listaPrgAfectados);
                    /**
                     * Efectuar cambio uno a uno,
                     */
                    prgTcEJB.saveChangeOneToOne(listPrgTcGestionVehiculo.get(0), prgTcUnoAUno);

                    /**
                     * Validación si los servicios son de patios distintos. si
                     * no se da el caso se deberan crear los movientos en vacío
                     * correspondientes.
                     */
                    if (!(listPrgTcGestionVehiculo.get(0).getFromStop().getIdPrgStoppoint().equals(prgTcUnoAUno.getFromStop().getIdPrgStoppoint()))) {
                        if (!(listPrgTcGestionVehiculo.get(0).getFromStop().getIsDepot().equals(ConstantsUtil.ON_INT) && prgTcUnoAUno.getFromStop().getIsDepot().equals(ConstantsUtil.ON_INT))) {

                            PrgTc vacio1 = crearVacio(listPrgTcGestionVehiculo.get(0));
                            PrgTc vacio2 = crearVacio(prgTcUnoAUno);
                            String horaInicioVacio1;
                            String horaFinVacio1;
                            String horaInicioVacio2;
                            String horaFinVacio2;
                            if (prgTcArbolServAnterior != null) {
                                horaInicioVacio1 = prgTcArbolServAnterior.getTimeDestiny();
                                horaFinVacio1 = MovilidadUtil.sumarMinutosHora(prgTcArbolServAnterior.getTimeDestiny(), 5);
                            } else {
                                horaInicioVacio1 = MovilidadUtil.sumarMinutosHora(prgTcUnoAUno.getTimeOrigin(), -5);
                                horaFinVacio1 = prgTcUnoAUno.getTimeOrigin();
                            }

                            horaFinVacio2 = listPrgTcGestionVehiculo.get(0).getTimeOrigin();
                            horaInicioVacio2 = MovilidadUtil.sumarMinutosHora(listPrgTcGestionVehiculo.get(0).getTimeOrigin(), -5);
                            /**
                             * Parada Final en donde terminará el movimiento en
                             * vacío
                             */
                            vacio1.setIdGopUnidadFuncional(prgTcUnoAUno.getIdGopUnidadFuncional());
                            vacio1.setToStop(prgTcUnoAUno.getFromStop());
                            vacio1.setObservaciones(observacionesPrgTc);
                            vacio1.setServbus(prgTcUnoAUno.getServbus());
                            vacio1.setSercon(prgTcUnoAUno.getSercon());
                            vacio1.setIdEmpleado(prgTcUnoAUno.getIdEmpleado());
                            vacio1.setTimeOrigin(horaInicioVacio1);
                            vacio1.setTimeDestiny(horaFinVacio1);
                            vacio1.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                            vacio1.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));

                            /**
                             * Parada Final en donde terminará el movimiento en
                             * vacío
                             */
                            vacio2.setIdGopUnidadFuncional(listPrgTcGestionVehiculo.get(0).getIdGopUnidadFuncional());
                            vacio2.setToStop(listPrgTcGestionVehiculo.get(0).getFromStop());
                            vacio2.setServbus(listPrgTcGestionVehiculo.get(0).getServbus());
                            vacio2.setSercon(listPrgTcGestionVehiculo.get(0).getSercon());
                            vacio2.setIdEmpleado(listPrgTcGestionVehiculo.get(0).getIdEmpleado());
                            vacio2.setTimeOrigin(horaInicioVacio2);
                            vacio2.setTimeDestiny(horaFinVacio2);
                            vacio2.setObservaciones(observacionesPrgTc);
                            vacio2.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                            vacio2.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                            /**
                             * Distancia total del movimiento en vacío
                             */
                            BigDecimal d1 = prgTcEJB.findDistandeByFromStopAndToStop(vacio1.getFromStop().getIdPrgStoppoint(), vacio1.getToStop().getIdPrgStoppoint());
                            vacio1.setDistance(d1);
                            BigDecimal d2 = prgTcEJB.findDistandeByFromStopAndToStop(vacio2.getFromStop().getIdPrgStoppoint(), vacio2.getToStop().getIdPrgStoppoint());
                            vacio2.setDistance(d2);
                            /**
                             * Verificar si el tipo de servicio es biarticulado
                             * o articulado para así colocar el identificaro de
                             * servbus correcto
                             */
                            String tipo = "TA";
                            if (vacio1.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                                tipo = "TB";
                            }
                            vacio1.setServbus(MovilidadUtil.servbus(tipo));
                            if (prgTcVacOrVaccom == null) {
                                prgTcEJB.create(vacio1);
                                listNovedadPrgTc.add(vacio1);
                            }
                            /**
                             * Verificar si el tipo de servicio es biarticulado
                             * o articulado para así colocar el identificaro de
                             * servbus correcto
                             */
                            tipo = "TA";
                            if (vacio2.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                                tipo = "TB";
                            }
                            vacio2.setServbus(MovilidadUtil.servbus(tipo));
                            prgTcEJB.create(vacio2);
                            /**
                             * Se agrega el servicio o prgTc a la lista que
                             * luego persistirá la relación de la novedad con el
                             * servico involucrado
                             */
                            listNovedadPrgTc.add(vacio2);
                        }
                    }
                } else if (vehiculo != null) {
                    /**
                     * Cargar vehículo nuevo para setera en la novedad(El que
                     * remplaza al que estaba)
                     */
                    vehiculoParaNovNew = new Vehiculo();
                    vehiculoParaNovNew.setIdVehiculo(vehiculo.getIdVehiculo());
                    listPrgTcGestionVehiculo.get(0).setControl(codTMPrgTc);
                    /**
                     * Se consultan previamente los servicios que se van a
                     * intervenir en el proceso para luego agregarlos a la lista
                     * que luego persistirá la relación de la novedad con el
                     * servico involucrado
                     */
                    List<PrgTc> listaAfectados
                            = prgTcEJB.prgTcUnoAUnoReturnPrgTcAfectados(
                                    listPrgTcGestionVehiculo.get(0).getServbus(),
                                    listPrgTcGestionVehiculo.get(0).getFecha(),
                                    listPrgTcGestionVehiculo.get(0).getTimeOrigin(),
                                    listPrgTcGestionVehiculo.get(0).getIdGopUnidadFuncional() == null
                                    ? 0 : listPrgTcGestionVehiculo.get(0).getIdGopUnidadFuncional().getIdGopUnidadFuncional());
                    listNovedadPrgTc.addAll(listaAfectados);
                    /**
                     * Se efectúa el cambio uno a uno.
                     */
                    prgTcEJB.updatePrgTcUnoAUno(vehiculo.getIdVehiculo(),
                            listPrgTcGestionVehiculo.get(0).getServbus(),
                            listPrgTcGestionVehiculo.get(0).getFecha(),
                            listPrgTcGestionVehiculo.get(0).getTimeOrigin(),
                            listPrgTcGestionVehiculo.get(0).getIdVehiculo().getIdVehiculo(),
                            listPrgTcGestionVehiculo.get(0).getControl(),
                            observacionesPrgTc, user.getUsername(),
                            listPrgTcGestionVehiculo.get(0).getIdPrgTcResponsable().getIdPrgTcResponsable(),
                            listPrgTcGestionVehiculo.get(0).getIdPrgClasificacionMotivo(),
                            listPrgTcGestionVehiculo.get(0).getIdGopUnidadFuncional() == null
                            ? 0 : listPrgTcGestionVehiculo.get(0).getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                    PrgTc prgTcNewVacio = new PrgTc();
                    /**
                     * Estado de operadión asignados a los movimientos en vacío
                     * no pagos.
                     */
                    prgTcNewVacio.setEstadoOperacion(7);
                    prgTcNewVacio.setIdVehiculo(vehiculo);
                    prgTcNewVacio.setIdVehiculoTipo(vehiculo.getIdVehiculoTipo());
                    prgTcNewVacio.setIdTaskType(new PrgTarea(2));
                    prgTcNewVacio.setSercon(listPrgTcGestionVehiculo.get(0).getSercon());
                    prgTcNewVacio.setServbus(listPrgTcGestionVehiculo.get(0).getServbus());
                    prgTcNewVacio.setTabla(listPrgTcGestionVehiculo.get(0).getTabla());
                    prgTcNewVacio.setIdPrgTcResponsable(listPrgTcGestionVehiculo.get(0).getIdPrgTcResponsable());
                    prgTcNewVacio.setIdPrgClasificacionMotivo(listPrgTcGestionVehiculo.get(0).getIdPrgClasificacionMotivo());
                    prgTcNewVacio.setIdGopUnidadFuncional(listPrgTcGestionVehiculo.get(0).getIdGopUnidadFuncional());

                    prgTcNewVacio.setFromStop(salidaStopPoint);
                    prgTcNewVacio.setToStop(listPrgTcGestionVehiculo.get(0).getFromStop());
                    /**
                     * Distancia total del movimiento en vacío
                     */
                    BigDecimal d1 = prgTcEJB.findDistandeByFromStopAndToStop(prgTcNewVacio.getFromStop().getIdPrgStoppoint(), prgTcNewVacio.getToStop().getIdPrgStoppoint());
                    prgTcNewVacio.setDistance(d1);
                    prgTcNewVacio.setTaskDuration(listPrgTcGestionVehiculo.get(0).getTaskDuration());
                    prgTcNewVacio.setFecha(listPrgTcGestionVehiculo.get(0).getFecha());

                    prgTcNewVacio.setAmplitude(listPrgTcGestionVehiculo.get(0).getAmplitude());
                    prgTcNewVacio.setProductionDistance(listPrgTcGestionVehiculo.get(0).getProductionDistance());

                    PrgTc prgTcArbolServAnterior = prgTcEJB.servicioAnterior(listPrgTcGestionVehiculo.get(0));
                    String horaInicioVacio1;
                    String horaFinVacio1;
                    if (prgTcArbolServAnterior != null) {
                        horaInicioVacio1 = prgTcArbolServAnterior.getTimeDestiny();
                        horaFinVacio1 = MovilidadUtil.sumarMinutosHora(prgTcArbolServAnterior.getTimeDestiny(), 5);
                    } else {
                        horaInicioVacio1 = MovilidadUtil.sumarMinutosHora(listPrgTcGestionVehiculo.get(0).getTimeOrigin(), -5);
                        horaFinVacio1 = listPrgTcGestionVehiculo.get(0).getTimeOrigin();
                    }

                    prgTcNewVacio.setTimeOrigin(MovilidadUtil.sumarMinutosHora(listPrgTcGestionVehiculo.get(0).getTimeOrigin(), -5));
                    prgTcNewVacio.setTimeDestiny(listPrgTcGestionVehiculo.get(0).getTimeOrigin());

                    prgTcNewVacio.setIdEmpleado(listPrgTcGestionVehiculo.get(0).getIdEmpleado());
                    prgTcNewVacio.setCreado(MovilidadUtil.fechaCompletaHoy());
                    prgTcNewVacio.setUsername(user.getUsername());

                    /**
                     * Verificar si el tipo de servicio es biarticulado o
                     * articulado para así colocar el identificaro de servbus
                     * correcto
                     */
                    String tipo = "TA";
                    if (prgTcNewVacio.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                        tipo = "TB";
                    }
                    prgTcNewVacio.setServbus(MovilidadUtil.servbus(tipo));
                    if (!(prgTcNewVacio.getFromStop().getIdPrgStoppoint().equals(prgTcNewVacio.getToStop().getIdPrgStoppoint()))) {
                        prgTcEJB.create(prgTcNewVacio);
                        listNovedadPrgTc.add(prgTcNewVacio);
                    }
                    PrgTc vacio1 = crearVacio(listPrgTcGestionVehiculo.get(0));
                    /**
                     * Parada Final en donde terminará el movimiento en vacío
                     */
                    vacio1.setToStop(entradaStopPoint);
                    /**
                     * Distancia total del movimiento en vacío
                     */
                    BigDecimal d2 = prgTcEJB.findDistandeByFromStopAndToStop(listPrgTcGestionVehiculo.get(0).getFromStop().getIdPrgStoppoint(),
                            prgTcNewVacio.getFromStop().getIdPrgStoppoint());
                    vacio1.setDistance(d2);
                    vacio1.setTimeOrigin(horaInicioVacio1);
                    vacio1.setTimeDestiny(horaFinVacio1);

                    /**
                     * Verificar si el tipo de servicio es biarticulado o
                     * articulado para así colocar el identificaro de servbus
                     * correcto
                     */
                    tipo = "TA";
                    if (vacio1.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                        tipo = "TB";
                    }
                    vacio1.setServbus(MovilidadUtil.servbus(tipo));
                    if (prgTcVacOrVaccom == null) {
                        if (!(vacio1.getFromStop().getIdPrgStoppoint().equals(vacio1.getToStop().getIdPrgStoppoint()))) {
                            prgTcEJB.create(vacio1);
                            /**
                             * Se agrega el servicio o prgTc a la lista que
                             * luego persistirá la relación de la novedad con el
                             * servico involucrado
                             */
                            listNovedadPrgTc.add(vacio1);
                        }
                    }
                } else if (codigoV.equalsIgnoreCase("null")) {
                    // codigoV = null
                    //Se desasignará de los servicios el vehículo
                    /**
                     * Se efectúa la desasignación uno a uno.
                     */
                    prgTcEJB.updatePrgTcUnoAUno(null,
                            listPrgTcGestionVehiculo.get(0).getServbus(),
                            listPrgTcGestionVehiculo.get(0).getFecha(),
                            listPrgTcGestionVehiculo.get(0).getTimeOrigin(),
                            listPrgTcGestionVehiculo.get(0).getIdVehiculo().getIdVehiculo(),
                            listPrgTcGestionVehiculo.get(0).getControl(),
                            observacionesPrgTc, user.getUsername(),
                            listPrgTcGestionVehiculo.get(0).getIdPrgTcResponsable().getIdPrgTcResponsable(),
                            listPrgTcGestionVehiculo.get(0).getIdPrgClasificacionMotivo(),
                            listPrgTcGestionVehiculo.get(0).getIdGopUnidadFuncional() == null
                            ? 0 : listPrgTcGestionVehiculo.get(0).getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                }
//                else {
//                    MovilidadUtil.addErrorMessage("No se ha cargado un vehículo");
//                    flag_submit_I = true;
//                    return false;
//                }

            } else {
                /**
                 * Cambio no uno a uno.
                 */
                Vehiculo ve = null;
                Vehiculo veOld = prgTc.getIdVehiculo();
                if (!codigoV.equalsIgnoreCase("null")) {
                    if (vehiculo != null) {
                        ve = vehiculo;
                    } else {
                        MovilidadUtil.addErrorMessage("No se ha cargado un vehículo");
//                    flag_submit_I = true;
                        return false;
                    }
                }
                /*
                 * prgTcFechaMenorUUN = Primer Servicio seleccionado en la lista
                 */
                PrgTc prgTcFechaMenorUUN = null;
                /**
                 * Cargar vehículo nuevo para setera en la novedad(El que
                 * remplaza al que estaba)
                 */
                if (vehiculo != null) {
                    vehiculoParaNovNew = new Vehiculo();
                    vehiculoParaNovNew.setIdVehiculo(vehiculo.getIdVehiculo());
                }

                for (PrgTc p : listPrgTcGestionVehiculo) {
                    int index = listPrgTcGestionVehiculo.indexOf(p);
                    listPrgTcGestionVehiculo.get(index).setIdVehiculo(ve);
                    p.setIdVehiculo(ve);
                    p.setObservaciones(observacionesPrgTc);
                    p.setControl(codTMPrgTc);
                    p.setUsername(user.getUsername());
                    p.setOldVehiculo(veOld.getIdVehiculo());
                    p.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                    p.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                    p.setModificado(MovilidadUtil.fechaCompletaHoy());
                    prgTcEJB.reasignarVe(p);
                }

                if (!codigoV.equalsIgnoreCase("null")) {
                    prgTcFechaMenorUUN = Collections.min(listPrgTcGestionVehiculo,
                            Comparator.comparing(tc -> MovilidadUtil.toSecs(tc.getTimeOrigin())));//Primer servicio seleccionado

                    PrgTc prgTcNewVacioUUN = new PrgTc();
                    prgTcNewVacioUUN.setEstadoOperacion(7);
                    prgTcNewVacioUUN.setOldVehiculo(prgTcFechaMenorUUN.getIdVehiculo().getIdVehiculo());
                    prgTcNewVacioUUN.setIdVehiculoTipo(vehiculo.getIdVehiculoTipo());
                    prgTcNewVacioUUN.setIdTaskType(new PrgTarea(2));
                    prgTcNewVacioUUN.setSercon(prgTcFechaMenorUUN.getSercon());
                    prgTcNewVacioUUN.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                    prgTcNewVacioUUN.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                    prgTcNewVacioUUN.setServbus(prgTcFechaMenorUUN.getServbus());
                    prgTcNewVacioUUN.setTabla(prgTcFechaMenorUUN.getTabla());
                    prgTcNewVacioUUN.setIdGopUnidadFuncional(prgTcFechaMenorUUN.getIdGopUnidadFuncional());
                    /**
                     * Parada inicial en donde comenzará el movimiento en vacío
                     */
                    prgTcNewVacioUUN.setFromStop(salidaStopPoint);
                    /**
                     * Parada Final en donde terminará el movimiento en vacío
                     */
                    prgTcNewVacioUUN.setToStop(prgTcFechaMenorUUN.getFromStop());
                    /**
                     * Distancia total del movimiento en vacío
                     */
                    BigDecimal d1 = prgTcEJB.findDistandeByFromStopAndToStop(prgTcNewVacioUUN.getFromStop().getIdPrgStoppoint(), prgTcNewVacioUUN.getToStop().getIdPrgStoppoint());
                    prgTcNewVacioUUN.setDistance(d1);
                    prgTcNewVacioUUN.setTaskDuration(prgTcFechaMenorUUN.getTaskDuration());
                    prgTcNewVacioUUN.setFecha(prgTcFechaMenorUUN.getFecha());

                    prgTcNewVacioUUN.setAmplitude(prgTcFechaMenorUUN.getAmplitude());
                    prgTcNewVacioUUN.setProductionDistance(prgTcFechaMenorUUN.getProductionDistance());

                    /**
                     * Se consulta el servicio inmediataente anterior al primero
                     * de los seleccionado, para saber que hora colocarle al
                     * movimiento en vacío.
                     */
                    PrgTc prgTcArbolServAnterior = prgTcEJB.servicioAnterior(prgTcFechaMenorUUN);
                    String horaInicioVacio1;
                    String horaFinVacio1;
                    if (prgTcArbolServAnterior != null) {
                        horaInicioVacio1 = prgTcArbolServAnterior.getTimeDestiny();
                        horaFinVacio1 = MovilidadUtil.sumarMinutosHora(prgTcArbolServAnterior.getTimeDestiny(), 5);
                    } else {
                        horaInicioVacio1 = MovilidadUtil.sumarMinutosHora(prgTcFechaMenorUUN.getTimeOrigin(), -5);
                        horaFinVacio1 = prgTcFechaMenorUUN.getTimeOrigin();
                    }

                    prgTcNewVacioUUN.setTimeOrigin(MovilidadUtil.sumarMinutosHora(prgTcFechaMenorUUN.getTimeOrigin(), -5));
                    prgTcNewVacioUUN.setTimeDestiny(prgTcFechaMenorUUN.getTimeOrigin());
                    if (listPrgTcGestionOperador.isEmpty()) {
                        prgTcNewVacioUUN.setIdEmpleado(prgTcFechaMenorUUN.getIdEmpleado());
                    } else {
                        prgTcNewVacioUUN.setIdEmpleado(prgTcOperadorDisponible.getIdEmpleado());
                    }

                    prgTcNewVacioUUN.setCreado(MovilidadUtil.fechaCompletaHoy());
                    prgTcNewVacioUUN.setUsername(user.getUsername());

                    /**
                     * Verificar si el tipo de servicio es biarticulado o
                     * articulado para así colocar el identificaro de servbus
                     * correcto
                     */
                    String tipo = "TA";
                    if (prgTcNewVacioUUN.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                        tipo = "TB";
                    }
                    prgTcNewVacioUUN.setServbus(MovilidadUtil.servbus(tipo));
                    if (!(prgTcNewVacioUUN.getFromStop().getIdPrgStoppoint().equals(prgTcNewVacioUUN.getToStop().getIdPrgStoppoint()))) {
                        prgTcEJB.create(prgTcNewVacioUUN);
                        /**
                         * Se agrega el servicio o prgTc a la lista que luego
                         * persistirá la relación de la novedad con el servico
                         * involucrado
                         */
                        listNovedadPrgTc.add(prgTcNewVacioUUN);

                    }
                    PrgTc vacio1UNN = crearVacio(prgTcFechaMenorUUN);
                    vacio1UNN.setIdVehiculo(new Vehiculo(prgTcFechaMenorUUN.getOldVehiculo()));
                    /**
                     * Parada Final en donde terminará el movimiento en vacío
                     */
                    vacio1UNN.setToStop(entradaStopPoint);
                    /**
                     * Distancia total del movimiento en vacío
                     */
                    BigDecimal d2 = prgTcEJB.findDistandeByFromStopAndToStop(prgTcFechaMenorUUN.getFromStop().getIdPrgStoppoint(),
                            prgTcNewVacioUUN.getFromStop().getIdPrgStoppoint());
                    vacio1UNN.setDistance(d2);
                    vacio1UNN.setTimeOrigin(horaInicioVacio1);
                    vacio1UNN.setTimeDestiny(horaFinVacio1);
                    vacio1UNN.setIdPrgTcResponsable(new PrgTcResponsable(i_codResponsable));
                    vacio1UNN.setIdPrgClasificacionMotivo(i_idClasificacion == 0 ? null : new PrgClasificacionMotivo(i_idClasificacion));
                    /**
                     * Verificar si el tipo de servicio es biarticulado o
                     * articulado para así colocar el identificaro de servbus
                     * correcto
                     */
                    tipo = "TA";
                    if (vacio1UNN.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
                        tipo = "TB";
                    }
                    vacio1UNN.setServbus(MovilidadUtil.servbus(tipo));
                    if (prgTcVacOrVaccom == null) {
                        if (!(vacio1UNN.getFromStop().getIdPrgStoppoint().equals(vacio1UNN.getToStop().getIdPrgStoppoint()))) {
                            prgTcEJB.create(vacio1UNN);
                            /**
                             * Se agrega el servicio o prgTc a la lista que
                             * luego persistirá la relación de la novedad con el
                             * servico involucrado
                             */
                            listNovedadPrgTc.add(vacio1UNN);
                        }
                    }
                }
            }

        } catch (ParseException e) {
//            flag_submit_I = true;
            return false;
        }
        return true;
    }

    /**
     * Ayuda visual en vista para alertar cuando un serviocio sin operador esta
     * proximo a ejecutarse.
     *
     * @param p
     * @return valor String con el nombre de clases css ya credas.
     * @throws ParseException
     */
    public String alertSinOp(PrgTc p) throws ParseException {
        if (p != null) {

            if (MovilidadUtil.alertServiciosSinOpVencidos(p.getTimeOrigin())) {
                return "rowRedStyle";
            }
            if (MovilidadUtil.alertServiciosSinOp(p.getTimeOrigin())) {
                if (color.isEmpty()) {
                    color = "rowBlueStyle";
                    return "rowBlueStyle cssWhite";
                } else if (color.equals("rowBlueStyle")) {
                    color = "";
                    return "";
                } else if (color.equals("")) {
                    color = "rowBlueStyle";
                    return "rowBlueStyle cssWhite";
                }
            }
        }
        return "";
    }

    /**
     * Ayuda visual en vista para alertar cuando un serviocio sin vehículo esta
     * proximo a ejecutarse.
     *
     * @param p
     * @return valor String con el nombre de clases css ya credas.
     * @throws ParseException
     */
    public String alertSinBus(PrgTc p) throws ParseException {
        if (p != null) {

            if (MovilidadUtil.alertServiciosSinOpVencidos(p.getTimeOrigin())) {
                return "rowRedStyle";
            }
            if (MovilidadUtil.alertServiciosSinOp(p.getTimeOrigin())) {
                if (colorSinBus.isEmpty()) {
                    colorSinBus = "rowBlueStyle";
                    return "rowBlueStyle";
                } else if (color.equals("rowBlueStyle")) {
                    colorSinBus = "";
                    return "";
                } else if (colorSinBus.equals("")) {
                    colorSinBus = "rowBlueStyle";
                    return "rowBlueStyle";
                }
            }
        }
        return "";
    }

    /**
     * Ayuda visual para identificar por colores los servicos y sus estados.
     *
     * @param p
     * @return valor String con el nombre de clases css ya credas.
     * @throws ParseException
     */
    public String alarStyleClass(PrgTc p) throws ParseException {
        if (p == null) {
            return null;
        }
//        if (MovilidadUtil.fechaBetween(fechaConsulta, p.getTimeOrigin(), p.getTimeDestiny())
//                && p.getEstadoOperacion() != 5 && p.getEstadoOperacion() != 8) {
//            return "rowGrenStyle";
//        }
        /**
         * Servicio ejecutado parcialmente.
         */
        if (p.getEstadoOperacion() == 2) {
            return "rowOrangeStyle";
        }
        /**
         * Servicio eliminado.
         */
        if (p.getEstadoOperacion() == 5 || p.getEstadoOperacion() == 8) {
            return "rowRedStyle";
        }
        /**
         * Servicio adicional VAC o vacío no pago.
         */
        if (p.getEstadoOperacion() == 7) {
            return "rowGrisOscuroStyle";
        }
        /**
         * Servicio adicional VACCOM o vacío pago.
         */
        if (p.getEstadoOperacion() == 6) {
            return "rowTurquesaStyle";
        }
        /**
         * Servicio adicional comercial.
         */
        if (p.getEstadoOperacion() == 3 || p.getEstadoOperacion() == 4) {
            return "rowBlueStyle";
        }
        return null;
    }

    /**
     * Método responsable de identificar por medio de un tiempo de origen,
     * tiempo final y estado de operación, si un servicio esta en ejecución
     *
     * @param p
     * @return valor boolean a la vista para luego ser evaluado por un
     * javascritp
     * @throws ParseException
     */
    public boolean enEjecucion(PrgTc p) throws ParseException {
        if (p == null) {
            return false;
        }
        return MovilidadUtil.fechaBetween(fechaConsulta, p.getTimeOrigin(), p.getTimeDestiny()) && p.getEstadoOperacion() != 5;
    }

    /**
     * Método responsable de armar arbol para los servicios en el apartado de
     * eliminar, en la ventana de gestion de servicios, los servicios son
     * agrupados por servbus, luego por sercon y por ultimo por cada servicio se
     * cargan las paradas de éste.
     *
     * @throws ParseException
     */
    public void armarArbolAlEliminar() throws ParseException {
        String servbus = prgTc.getServbus();
        String timeOrigin = prgTc.getTimeOrigin();
        prgTcConServbus = null;
        if (servbus == null) {
            //consultar primer servicio por medio del sercon, fecha y tiempo de origen.
            prgTcConServbus = prgTcEJB.findPrgTcForDeleteAndChangeVehiculo(prgTc.getSercon(),
                    fechaConsulta, prgTc.getTimeOrigin(), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (prgTcConServbus == null) {
                return;
            } else {
                servbus = prgTcConServbus.getServbus();
                timeOrigin = prgTcConServbus.getTimeOrigin();
            }
        }
        /**
         * Consultar todos los sercones que puede tener un servbus segun éste,
         * fecha y tiempo de origen
         */
        List<SerconList> listaSercon = prgTcEJB.findSerconByServbus(servbus, prgTc.getFecha(), timeOrigin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        /**
         * rootEliminar es papá. La instancia asignada a hijo1, terminado siento
         * un hijo de rootEliminar
         */
        TreeNode hijo1 = new DefaultTreeNode(new ArbolViewPrgTc(servbus, prgTc, 1), this.rootEliminar);
        hijo1.setExpanded(true);
        for (SerconList serconObj : listaSercon) {
            /**
             * Consultar todos los servicios(PrgTc) por sercon, fecha, tiempo de
             * origen y servbus
             */
            List<PrgTc> listaTc = prgTcEJB.findTcBySercon(serconObj.getSercon(),
                    prgTc.getFecha(), timeOrigin, servbus,
                    prgTc.getIdGopUnidadFuncional() == null
                    ? 0 : prgTc.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
            /**
             * hijo1 es papá ahora de la instancia de hijo2.
             */
            TreeNode hijo2 = new DefaultTreeNode(new ArbolViewPrgTc(serconObj.getSercon(), new PrgTc(serconObj.getSercon(), servbus), 2), hijo1);
            hijo2.setExpanded(true);
            for (PrgTc pp : listaTc) {
                /**
                 * hijo2 es papá ahora de la instancia de hijo3.
                 */
                TreeNode hijo3 = new DefaultTreeNode(new ArbolViewPrgTc(returnTituloServ(pp), pp, 3), hijo2);
                if (pp.getIdRuta() != null) {
                    List<PrgPattern> prgPatternList = prgPatternEJB.findAllOrderedByIdRoute(pp.getIdRuta().getIdPrgRoute());
                    int fromStopPoint = 0;
                    int toStopPoint = 0;
                    for (PrgPattern pd : prgPatternList) {
                        /**
                         * hijo3 es papá ahora de la instancia de hijo4.
                         */
                        TreeNode hijo4 = new DefaultTreeNode(new ArbolViewPrgTc(pd.getIdPrgStoppoint().getName(), pp, pd, 4), hijo3);
                        if (pp.getFromStop().getIdPrgStoppoint().equals(pd.getIdPrgStoppoint().getIdPrgStoppoint())) {
                            fromStopPoint = pd.getSecuenceNumber();
                        }
                        if (fromStopPoint == 0) {
                            hijo4.setSelectable(false);
                        } else {
                            if (toStopPoint > 0) {
                                hijo4.setSelectable(false);
                            }
                        }
                        if (pp.getToStop().getIdPrgStoppoint().equals(pd.getIdPrgStoppoint().getIdPrgStoppoint())) {
                            toStopPoint = pd.getSecuenceNumber();
                        }
                    }
                }
            }
        }
    }

    /**
     * Método responsable de armar arbol para los servicios en el apartado de
     * operador, en la ventana de gestion de servicios, los servicios son
     * agrupados por sercon.
     *
     * @throws ParseException
     */
    public void armarArbolOperador() throws ParseException {
        /**
         * rootOperador es papá. La instancia asignada a hijo1, terminado siento
         * un hijo de rootEliminar
         */
        TreeNode hijo1 = new DefaultTreeNode(new ArbolViewPrgTc(prgTc.getSercon(), prgTc, 1), this.rootOperador); //Padre
        hijo1.setExpanded(true);

        boolean dispo = prgTc.getIdTaskType() == null ? false : prgTc.getIdTaskType().getOpDisponible() == ConstantsUtil.ON_INT;
        List<PrgTc> listaTc = prgTcEJB.findAllTcBySercon(prgTc.getSercon(),
                prgTc.getFecha(), prgTc.getTimeOrigin(), prgTc.getIdEmpleado().getIdEmpleado(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), dispo);
        listDesasignarOpServNull = new ArrayList<>();
        for (PrgTc pp : listaTc) {
            /**
             * hijo1 es papá ahora de la instancia de hijo2.
             */
            TreeNode hijo2 = new DefaultTreeNode(new ArbolViewPrgTc(returnTituloServ(pp), pp, 2), hijo1);
//            hijo2.setSelected(true);
//            listPrgTcGestionOperador.add(pp);
            listDesasignarOpServNull.add(pp);
        }
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
            ajusteJornadaFromGestionServicio.setListDesasignarOpServNull(listDesasignarOpServNull);
            ajusteJornadaFromGestionServicio.setListPrgTcGestionOperador(listPrgTcGestionOperador);
        }
    }

    /**
     * Método responsable de armar arbol para los servicios en el apartado de
     * vehículo, en la ventana de gestion de servicios, los servicios son
     * agrupados por sercon.
     *
     * @throws ParseException
     */
    public void armarArbolVehiculo() throws ParseException {
        String servbus = prgTc.getServbus();
        String timeOrigin = prgTc.getTimeOrigin();
        PrgTc prgTcConServbus_local = null;
        if (servbus == null) {
            prgTcConServbus_local = prgTcEJB.findPrgTcForDeleteAndChangeVehiculo(prgTc.getSercon(), fechaConsulta, timeOrigin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (prgTcConServbus_local == null) {
                return;
            } else {
                servbus = prgTcConServbus_local.getServbus();
                timeOrigin = prgTcConServbus_local.getTimeOrigin();
                viewCodVehiculo = prgTcConServbus_local.getIdVehiculo().getCodigo();
                viewPlaca = prgTcConServbus_local.getIdVehiculo().getPlaca();
            }
        } else {
            if (prgTc.getIdVehiculo() != null) {
                viewCodVehiculo = prgTc.getIdVehiculo().getCodigo();
                viewPlaca = prgTc.getIdVehiculo().getPlaca();
            }
        }
        List<SerconList> listaSercon = prgTcEJB.findSerconByServbus(servbus, prgTc.getFecha(), timeOrigin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        TreeNode hijo1 = new DefaultTreeNode(new ArbolViewPrgTc(servbus, prgTc, 1), this.rootVehiculo); //Padre
        hijo1.setExpanded(true);
        for (SerconList serconObj : listaSercon) {
            List<PrgTc> listaTc = prgTcEJB.findTcBySercon(serconObj.getSercon(),
                    prgTc.getFecha(), timeOrigin, servbus,
                    prgTc.getIdGopUnidadFuncional() == null
                    ? 0 : prgTc.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
            TreeNode hijo2 = new DefaultTreeNode(new ArbolViewPrgTc(serconObj.getSercon(), new PrgTc(serconObj.getSercon(), servbus), 2), hijo1);
            hijo2.setExpanded(true);
            for (PrgTc pp : listaTc) {
                TreeNode hijo3 = new DefaultTreeNode(new ArbolViewPrgTc(returnTituloServ(pp), pp, 3), hijo2);
            }
        }
    }

    /**
     * Método responsable de preparar arbol de servicios sin operador que es
     * visualisado en vista en el apartado de asignación de servicios sin
     * operador
     *
     * @throws ParseException
     */
    public void armarArbolPrgTcSinOperador() throws ParseException {
        TreeNode hijo1 = new DefaultTreeNode(new ArbolViewPrgTc(prgTcSinOperador.getSercon(), prgTcSinOperador, 1), this.rootSinOperador); //Padre
        hijo1.setExpanded(true);
        List<PrgTc> listaTc = prgTcEJB.findAllTcBySerconSinOperador(prgTcSinOperador.getFecha(),
                prgTcSinOperador.getTimeOrigin(), prgTcSinOperador.getSercon(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        for (PrgTc pp : listaTc) {
            TreeNode hijo2 = new DefaultTreeNode(new ArbolViewPrgTc(returnTituloServ(pp), pp, 2), hijo1);
        }
    }

    /**
     * Método responsable de cargar un vehículo para la gestion de adicionar
     * servicios
     *
     * @throws ParseException
     */
    public void findVehiculoByCodNewService() throws ParseException {
        if (MovilidadUtil.stringIsEmpty(codigoV)) {
            MovilidadUtil.addErrorMessage("Dítige un código valido para vehículo.");
            return;
        }
        //isEmpty
        vehiculoNewService = vehEJB.findVehiculoExist(codigoV, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (vehiculoNewService == null) {
            MovilidadUtil.addErrorMessage(ConstantsUtil.NO_EXISTE_VEHICULO);
            return;
        }
        if (!vehiculoNewService.getIdVehiculoTipoEstado().getIdVehiculoTipoEstado().equals(ConstantsUtil.ON_INT)) {
            vehiculoNewService = null;
            MovilidadUtil.addErrorMessage(ConstantsUtil.INOPERATIVO_VEHICULO);
            return;
        }

        unidadFuncionalSessionBean.setI_unidad_funcional(vehiculoNewService.getIdGopUnidadFuncional() != null
                ? vehiculoNewService.getIdGopUnidadFuncional().getIdGopUnidadFuncional() : null);
        if (vehiculoNewService.getIdGopUnidadFuncional() == null) {
            MovilidadUtil.addFatalMessage("Se necesita unidad funcional para vehiculo.");
            vehiculoNewService = null;
            return;
        }
        if (prgTcForGetOperador != null) {
            if (!vehiculoNewService.getIdGopUnidadFuncional().getIdGopUnidadFuncional()
                    .equals(prgTcForGetOperador.getIdGopUnidadFuncional().getIdGopUnidadFuncional())) {
                MovilidadUtil.addErrorMessage("Vehículo y Operador no comparten la misma unidad funcional.");
                return;
            }
        }
        boolean validarDocuVhiculo = validarDocumentacionBean.validarDocuVhiculo(fechaConsulta, vehiculoNewService.getIdVehiculo());
        if (validarDocuVhiculo) {
            MovilidadUtil.addErrorMessage("El vehículo tiene documentos vencidos");
            vehiculoNewService = null;
            return;
        }
        codigoV = vehiculoNewService.getCodigo();
        String tipo = "TA";
        if (vehiculoNewService.getIdVehiculoTipo().getIdVehiculoTipo().equals(2)) {
            tipo = "TB";
        }
        servbusString = MovilidadUtil.servbus(tipo);
        MovilidadUtil.addSuccessMessage("Vehículo Cargado: " + vehiculoNewService.getCodigo());

    }

    /**
     * Método responsable de cargar un operador para la gestion de adicionar
     * servicios
     *
     * @throws ParseException
     */
    public void findOperadorByCodNewService() throws ParseException {
        if (MovilidadUtil.stringIsEmpty(codigoTm)) {
            MovilidadUtil.addErrorMessage("Dítige un código valido para operador.");
            return;
        }
        Empleado empl = emplEjb.findbyCodigoTmAndIdGopUnidadFuncActivo(MovilidadUtil.convertStringToInt(codigoTm), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (empl == null) {
            MovilidadUtil.addErrorMessage("Operador no disponible");
            return;
        }
        if (empl.getIdGopUnidadFuncional() == null) {
            MovilidadUtil.addFatalMessage("Se necesita unidad funcional para el operador.");
            empl = null;
            return;
        }
        boolean validarDocuOperador = validarDocumentacionBean.validarDocuOperador(fechaConsulta, empl.getIdEmpleado());
        if (validarDocuOperador) {
            MovilidadUtil.addErrorMessage("El Operador tiene documentos vencidos");
            empl = null;
            prgTcForGetOperador = null;
            return;
        }
        if (vehiculoNewService != null) {
            if (!vehiculoNewService.getIdGopUnidadFuncional().getIdGopUnidadFuncional()
                    .equals(empl.getIdGopUnidadFuncional().getIdGopUnidadFuncional())) {
                MovilidadUtil.addErrorMessage("Vehículo y Operador no comparten la misma unidad funcional.");
                return;
            }
        }
        prgTcForGetOperador = prgTcEJB.findSerconByCodigoTmAndIdUnidadFunc(
                MovilidadUtil.convertStringToInt(codigoTm),
                fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (prgTcForGetOperador != null) {
            serconString = prgTcForGetOperador.getSercon();
        } else {
            serconString = MovilidadUtil.Sercon();
            prgTcForGetOperador = new PrgTc();
            prgTcForGetOperador.setIdEmpleado(empl);
            prgTcForGetOperador.setIdGopUnidadFuncional(empl.getIdGopUnidadFuncional());
        }
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
            ajusteJornadaFromGestionServicio.setIdEmpleadoFound(empl.getIdEmpleado());
            ajusteJornadaFromGestionServicio.setFecha(fechaConsulta);
            ajusteJornadaFromGestionServicio.cargarPrgSerconFound();
            ajusteJornadaFromGestionServicio.cargarTotalHorasExtrasSmanal();
            ajusteJornadaFromGestionServicio.cargarListaServiciosOpFound();
            ajusteJornadaFromGestionServicio.recrearJornadaFound("formAdicionServicio:turnoEmplFound");
            ajusteJornadaFromGestionServicio.ajusteJornadasFoundII(listPrgTcAux, "formAdicionServicio:turnoEmplFound");
            ajusteJornadaFromGestionServicio.validarMaxHorasExtrasSmanales();
        }
        MovilidadUtil.addSuccessMessage("Empleado Cargado: "
                + empleadoToString(prgTcForGetOperador.getIdEmpleado()));
        codigoTm = Integer.toString(prgTcForGetOperador.getIdEmpleado().getCodigoTm());
    }

    /**
     * Permite mostrar mensaje en vista cunado se confirma la carga de vehículo
     * en cambio de vehículo
     */
    public void confirmarSi() {
        MovilidadUtil.addSuccessMessage("Vehículo Disponible");
    }

    /**
     * Método encargado de cancelar la selección del vehículo en la gestion de
     * servicio cambio de vehículo, inicializa las variables en nulo, para que
     * no se cargue el vehículo en cuestión
     */
    public void confirmarNo() {
        PrimeFaces.current().executeScript("PF('wvConfirmDialogArtToBiart').hide()");
        vehiculo = null;
        prgTcUnoAUno = null;
        codigoV = "";
    }

    /**
     * Método que confirmar si se desea un novedad que con caracteristicas que
     * ya se han registrados(Empleado, tipo detalle novedad y fecha)
     *
     * @throws ParseException
     * @throws InterruptedException
     */
    public void confirmarNovedadSi() throws ParseException, InterruptedException {
        PrimeFaces.current().executeScript("PF('confirmarNovedadWV').hide()");
        b_validacion = false;
        guardarTodoGlobal();
    }

    /**
     * Método reposnable de cancelar la persistencia de una novedad que con
     * caracteristicas que ya se han registrados(Empleado, tipo detalle novedad
     * y fecha)
     */
    public void confirmarNovedadNo() {
        PrimeFaces.current().executeScript("PF('confirmarNovedadWV').hide()");
        b_validacion = true;
    }

    /**
     * Método responsable de la consulta de un vehículo en el apartado gestión
     * de vehículo, en la vista gestionPrgTc.
     *
     * Valida si el cambio es uno a uno.
     *
     * Se consulta el vehículo por codigoV que es el código de vehículo
     * ingresado por el usuario, tambien por la fecha del servicio seleccionado.
     *
     * Si el cambio e uno a uno el criterio de busqueda aumenta añadiendo el
     * tiempo de origen del servicio seleccionado, para así, verificar si el
     * vehículo a consultar tiene servicio posteriores a este tiempo.
     *
     * @throws ParseException
     */
    public void findVehiculoByCod() throws ParseException {

        //con la anterior información buscar en la tabla de novedades, extraer el id del campo id_disp_clasificacion_novedad, y validar con el campo 
        if (MovilidadUtil.stringIsEmpty(codigoV)) {
            /**
             * en caso de que no se digite el código antes de consultar.
             */
            MovilidadUtil.addErrorMessage("Digíte el código del Vehículo");
            return;
        }
        Vehiculo vehiculoExiste = vehEJB.findVehiculoExist(codigoV, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        /**
         * Permite la desasignación del vehículo a los servicios a través del
         * null
         */
        if (!codigoV.equalsIgnoreCase("null")) {
            if (vehiculoExiste == null) {
                MovilidadUtil.addErrorMessage(ConstantsUtil.NO_EXISTE_VEHICULO);
                return;
            }
            //validación de carácter informativo, indica si el móvil es requerido por mantenimiento  
            if (vehiculoExiste != null) {
                String fecha1 = Util.dateFormat(prgTc.getFecha()) + " " + prgTc.getTimeOrigin();
                String mensaje = vehEJB.esVehiculoRequerido(vehiculoExiste.getIdVehiculo(), Util.dateTimeFormat(fecha1));
                if (!mensaje.isEmpty()) {
                    MovilidadUtil.addAdvertenciaMessage(mensaje);
                }
            }
            if (!vehiculoExiste.getIdVehiculoTipoEstado().getIdVehiculoTipoEstado().equals(ConstantsUtil.ON_INT)) {
                vehiculoExiste = null;
                MovilidadUtil.addErrorMessage(ConstantsUtil.INOPERATIVO_VEHICULO);
                return;
            }
            boolean validarDocuVhiculo = validarDocumentacionBean.validarDocuVhiculo(fechaConsulta, vehiculoExiste.getIdVehiculo());
            if (validarDocuVhiculo) {
                MovilidadUtil.addErrorMessage("El vehículo tiene documentos vencidos");
                vehiculoExiste = null;
                return;
            }
        }

        if (!codigoV.equalsIgnoreCase("null")) {
            /**
             * Se valida si la consulta se va a realizar sobre un cambio uno a
             * uno
             */
            if (unoAUnoVehiculo) {
                if (listPrgTcGestionVehiculo.isEmpty()) {
                    MovilidadUtil.addErrorMessage("Selecione el servicio desde donde hará el cambio");
                    return;
                }
                if (listPrgTcGestionVehiculo.size() > 1) {
                    MovilidadUtil.addErrorMessage("Solo debe seleccionar un servicio. El sistema a partir de él hace el cambio UNO a UNO");
                    return;
                }
                /**
                 * Se consulta un objeto prgTc o servicio, con los criterios de
                 * busqueda, código de vehículo, fecha, y tiempo de origen.
                 */
                prgTcUnoAUno = prgTcEJB.findByCodigoAndHoraUnoAUno(codigoV, fechaConsulta,
                        listPrgTcGestionVehiculo.get(0).getTimeOrigin(),
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                /**
                 * Se valida si el vehículo consultado, está en un servicio.
                 * para esto se vetifica que el metodo inmediatamente anterior
                 * tenga una respuesta no nula
                 */
                if (prgTcUnoAUno == null) {
                    /**
                     * Se verifica si el vehiculo consultado cumple con la
                     * tipología del servicio seleccionado desde la vista. En
                     * este caso se asume que el tipo de servicio es para la
                     * tipología de articulado
                     */
                    if ((listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getIdVehiculoTipo() == 1
                            && vehiculoExiste.getIdVehiculoTipo().getIdVehiculoTipo() == 2)) {
                        MovilidadUtil.addErrorMessage("Servicio programado para "
                                + listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getNombreTipoVehiculo()
                                + ", no se puede asignar "
                                + vehiculoExiste.getIdVehiculoTipo().getNombreTipoVehiculo());
                        prgTcUnoAUno = null;
                        vehiculo = null;
                        vehiculoExiste = null;
                        return;
                    }
                    /**
                     * Se verifica si el vehículo consultado cumple con la
                     * tipología del servicio seleccionado desde la vista. En
                     * este caso se asume que el tipo de servicio seleccionado
                     * desde la vista corresponde a la tipología de Biarticulado
                     * y el del vehículo consultado a Articulado.
                     *
                     * Si se cumple lo siguiente se abre un dialogo para
                     * confirmar la solicitud
                     *
                     * Si no se cumple lo siguiente, quiere decir que todo esta
                     * correcto y el vehículo consultado se puede asignar a el
                     * servicio seleccionado
                     */
                    if ((listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getIdVehiculoTipo() == 2
                            && vehiculoExiste.getIdVehiculoTipo().getIdVehiculoTipo() == 1)) {
                        prgTcUnoAUno = null;
                        vehiculo = vehiculoExiste;
                        codigoV = vehiculoExiste.getCodigo();
                        PrimeFaces.current().executeScript("PF('wvConfirmDialogArtToBiart').show()");
                    } else {
                        prgTcUnoAUno = null;
                        vehiculo = vehiculoExiste;
                        codigoV = vehiculoExiste.getCodigo();
                        MovilidadUtil.addSuccessMessage("Vehículo Disponible");
                    }
                } else {
                    /**
                     * El vehículo consultado no esta en un servicio según los
                     * criterios de busqueda
                     */
                    /**
                     * Se verifica si el vehiculo consultado cumple con la
                     * tipología del servicio seleccionado desde la vista. En
                     * este caso se asume que el tipo de servicio es para la
                     * tipología de articulado
                     */
                    if ((listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getIdVehiculoTipo() == 1
                            && prgTcUnoAUno.getIdVehiculo().getIdVehiculoTipo().getIdVehiculoTipo() == 2)) {
                        MovilidadUtil.addErrorMessage("Servicio programado para "
                                + listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getNombreTipoVehiculo()
                                + ", no se puede asignar "
                                + prgTcUnoAUno.getIdVehiculo().getIdVehiculoTipo().getNombreTipoVehiculo());
                        prgTcUnoAUno = null;
                        vehiculo = null;
                        return;
                    }
                    /**
                     * Se verifica si el vehículo consultado cumple con la
                     * tipología del servicio seleccionado desde la vista. En
                     * este caso se asume que el tipo de servicio seleccionado
                     * desde la vista corresponde a la tipología de Biarticulado
                     * y el del vehículo consultado a Articulado.
                     */
                    if ((listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getIdVehiculoTipo() == 2
                            && prgTcUnoAUno.getIdVehiculo().getIdVehiculoTipo().getIdVehiculoTipo() == 1)) {
                        MovilidadUtil.addErrorMessage("Vehículo consultado con servicios pendientes de tipologia "
                                + prgTcUnoAUno.getIdVehiculo().getIdVehiculoTipo().getNombreTipoVehiculo() + ", no se puede asignar "
                                + listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getNombreTipoVehiculo());
                        prgTcUnoAUno = null;
                        vehiculo = null;
                        return;
                    }
                    if ((listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getIdVehiculoTipo() == 2
                            && prgTcUnoAUno.getIdVehiculo().getIdVehiculoTipo().getIdVehiculoTipo() == 1)) {
                        vehiculo = null;
                        codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();

                        if (listPrgTcGestionVehiculo.get(0).getFromStop().getIdPrgStoppoint().equals(prgTcUnoAUno.getFromStop().getIdPrgStoppoint())) {
                            codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                            MovilidadUtil.addErrorMessage("Servicio programado para "
                                    + listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getNombreTipoVehiculo());
                            PrimeFaces.current().executeScript("PF('wvConfirmDialogArtToBiart').show()");
                        } else {
                            if (prgTcUnoAUno.getFromStop().getIsDepot() == 0 && listPrgTcGestionVehiculo.get(0).getFromStop().getIsDepot() == 0) {
                                //Los vehículos estan en una estación y tienen la misma hora
                                if (listPrgTcGestionVehiculo.get(0).getTimeOrigin().equals(prgTcUnoAUno.getTimeOrigin())) {
                                    //Ambos están en la misma parada en la misma hora
                                    codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                                    MovilidadUtil.addErrorMessage("Servicio programado para "
                                            + listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getNombreTipoVehiculo());
                                    PrimeFaces.current().executeScript("PF('wvConfirmDialogArtToBiart').show()");
                                } else {
                                    codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                                    MovilidadUtil.addErrorMessage("El vehículo no está en la parada "
                                            + listPrgTcGestionVehiculo.get(0).getFromStop().getName());
                                }
                            } else {
                                codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                                MovilidadUtil.addErrorMessage("Servicio programado para "
                                        + listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getNombreTipoVehiculo());
                                PrimeFaces.current().executeScript("PF('wvConfirmDialogArtToBiart').show()");
                            }
                        }
                    } else {
                        vehiculo = null;
                        /**
                         * Bus original listPrgTcGestionVehiculo.get(0)
                         *
                         * Bus que va a reemplazar, prgTcUnoAUno
                         *
                         * Se valida que los vehículos estén en el mismo lugar
                         * físico
                         */
                        if (listPrgTcGestionVehiculo.get(0).getFromStop().getIdPrgStoppoint().equals(prgTcUnoAUno.getFromStop().getIdPrgStoppoint())) {

                            /**
                             * Se valida que ambos estén en patio
                             */
                            if (listPrgTcGestionVehiculo.get(0).getFromStop().getIsDepot() == ConstantsUtil.ON_INT && prgTcUnoAUno.getFromStop().getIsDepot() == ConstantsUtil.ON_INT) {
                                /**
                                 * Están en el mismo patio
                                 */
                                codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                                MovilidadUtil.addSuccessMessage("Vehículo Disponible");
                            } else if (listPrgTcGestionVehiculo.get(0).getFromStop().getIsDepot() == 0 && prgTcUnoAUno.getFromStop().getIsDepot() == 0) {
                                if (listPrgTcGestionVehiculo.get(0).getTimeOrigin().equals(prgTcUnoAUno.getTimeOrigin())) {
                                    /**
                                     * Ambos están en la misma parada en la
                                     * misma hora
                                     */
                                    codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                                    MovilidadUtil.addSuccessMessage("Vehículo Disponible");
                                } else {
                                    codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                                    MovilidadUtil.addErrorMessage("El vehículo no está en la parada " + listPrgTcGestionVehiculo.get(0).getFromStop().getName());
                                }
                            }
                        } else {
                            if ((listPrgTcGestionVehiculo.get(0).getFromStop().getIsDepot() == 0 && prgTcUnoAUno.getFromStop().getIsDepot() == 1)
                                    || (listPrgTcGestionVehiculo.get(0).getFromStop().getIsDepot() == 1 && prgTcUnoAUno.getFromStop().getIsDepot() == 0)) {
                                /**
                                 * Vehículo a reemplazar está en estación y
                                 * vehículo que reemplaza está en patio
                                 */
                                codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                                MovilidadUtil.addSuccessMessage("Vehículo Disponible");
                                return;
                            }
                            if (listPrgTcGestionVehiculo.get(0).getFromStop().getIsDepot() == 1 && prgTcUnoAUno.getFromStop().getIsDepot() == 1) {
                                /**
                                 * Vehículo a reemplazar está en patio y
                                 * vehículo que reemplaza está en patio pero son
                                 * diferentes
                                 *
                                 */
                                codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                                MovilidadUtil.addSuccessMessage("Vehículo Disponible");
                                return;
                            }
                            if (listPrgTcGestionVehiculo.get(0).getFromStop().getIsDepot() == 0 && prgTcUnoAUno.getFromStop().getIsDepot() == 0) {
                                /**
                                 * Vehículo a reemplazar está en estación y
                                 * vehículo que reemplaza está en estación
                                 */
                                if (listPrgTcGestionVehiculo.get(0).getTimeOrigin().equals(prgTcUnoAUno.getTimeOrigin())) {
                                    /**
                                     * Ambos están en la misma parada en la
                                     * misma hora
                                     */
                                    codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                                    MovilidadUtil.addSuccessMessage("Vehículo Disponible, se encuentra en " + prgTcUnoAUno.getFromStop().getName());
                                } else {
                                    codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                                    MovilidadUtil.addErrorMessage("El vehículo no está en la parada " + listPrgTcGestionVehiculo.get(0).getFromStop().getName());
                                }
                            }
                        }
                    }
                }
            } else {
                /**
                 * Consulta sobre un cambio no uno a uno.
                 */
                if (listPrgTcGestionVehiculo.isEmpty()) {
                    MovilidadUtil.addErrorMessage("Selecciones los servicio para cambio");
                    return;
                }
                /**
                 * Se consulta vehículo con el código sumistrado por el usuario
                 * y la fecha del servicio.
                 */
                vehiculo = vehEJB.findVehiculoByCod(codigoV, fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                if (vehiculo != null) {
                    /**
                     * Se verifica si el vehiculo consultado cumple con la
                     * tipología del servicio seleccionado desde la vista. En
                     * este caso se asume que el tipo de servicio es para la
                     * tipología de articulado y tambien se asume que el tipo de
                     * vehículo es de biarticulado.
                     */
                    if ((listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getIdVehiculoTipo() == 1
                            && vehiculo.getIdVehiculoTipo().getIdVehiculoTipo() == 2)) {
                        MovilidadUtil.addErrorMessage("Servicio programado para "
                                + listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getNombreTipoVehiculo()
                                + ", no se puede asignar "
                                + vehiculo.getIdVehiculoTipo().getNombreTipoVehiculo());
                        vehiculo = null;
                        return;
                    }
                    prgTcUnoAUno = null;
                    codigoV = vehiculo.getCodigo();
                    MovilidadUtil.addSuccessMessage("Vehículo Disponible");

                } else {
                    PrgTc prgTcFechaMenor = Collections.min(listPrgTcGestionVehiculo,
                            Comparator.comparing(tc -> MovilidadUtil.toSecs(tc.getTimeOrigin())));//Primer servicio seleccionado
                    PrgTc prgTcFechaMayor = Collections.max(listPrgTcGestionVehiculo,
                            Comparator.comparing(tc -> MovilidadUtil.toSecs(tc.getTimeOrigin())));//Último servicio seleccionado

                    String timeDestiny = "";
                    if (prgTcFechaMayor == null) {
                        timeDestiny = prgTcFechaMenor.getTimeDestiny();
                    } else {
                        timeDestiny = prgTcFechaMayor.getTimeDestiny();
                    }
                    /**
                     * Se verifica si el vehículo consultado tiene servicio
                     * pendientes que se crucen en tiempos de ejecución con los
                     * servicio seleccionados en vista
                     */

                    if (validarVehiculoOcupado(listPrgTcGestionVehiculo, codigoV, fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional())) {
                        return;
                    }
//                PrgTc prgTcEjecucion = prgTcEJB.validarServicioEnEjecucionByHoraAndVehiculo(
//                        prgTcFechaMenor.getTimeOrigin(), codigoV, fechaConsulta,
//                        prgTcFechaMenor.getIdGopUnidadFuncional() == null ? 0 : prgTcFechaMenor.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
//                if (prgTcEjecucion != null) {
//                    MovilidadUtil.addErrorMessage("Vehículo con servicios pendientes");
//                    return;
//                } else {
//                    prgTcEjecucion = prgTcEJB.validarServicioEnEjecucionByHoraAndVehiculo(timeDestiny, codigoV, fechaConsulta,
//                            unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
//                    if (prgTcEjecucion != null) {
//                        MovilidadUtil.addErrorMessage("Vehículo con servicios pendientes");
//                        return;
//                    }
//                }

                    /**
                     * Se valida que el vehículo a consultar no tenga servicio
                     * pendientes para no cruzarse con los servicios
                     * seleccionados desde vista.
                     */
                    prgTcUnoAUno = prgTcEJB.findByCodigoAndHora(codigoV, fechaConsulta, prgTcFechaMenor.getTimeOrigin(), timeDestiny,
                            prgTcFechaMenor.getIdGopUnidadFuncional() == null ? 0 : prgTcFechaMenor.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                    if (prgTcUnoAUno == null) {
                        MovilidadUtil.addErrorMessage("Este vehículo tiene servicios pendientes");
                    } else {
                        /**
                         * Se verifica si el vehiculo consultado cumple con la
                         * tipología del servicio seleccionado desde la vista.
                         * En este caso se asume que el tipo de servicio del los
                         * servicios seleccionados desde la vista son de
                         * tipologia articulado y tambien se asume que el tipo
                         * de vehículo es de biarticulado.
                         */
                        if ((listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getIdVehiculoTipo() == 1
                                && prgTcUnoAUno.getIdVehiculo().getIdVehiculoTipo().getIdVehiculoTipo() == 2)) {
                            MovilidadUtil.addErrorMessage("Servicio programado para "
                                    + listPrgTcGestionVehiculo.get(0).getIdVehiculoTipo().getNombreTipoVehiculo()
                                    + ", no se puede asignar "
                                    + prgTcUnoAUno.getIdVehiculo().getIdVehiculoTipo().getNombreTipoVehiculo());
                            prgTcUnoAUno = null;
                            return;
                        }
                        vehiculo = prgTcUnoAUno.getIdVehiculo();
                        codigoV = prgTcUnoAUno.getIdVehiculo().getCodigo();
                        MovilidadUtil.addSuccessMessage("Vehículo Disponible");
                    }
                }
            }
        } else {
            MovilidadUtil.addSuccessMessage("Vehículo será desasignado");
        }
    }

    /**
     * Método ecardago de consulta un vehículo por medio de la varivale tipo
     * String codigoTm, que es ingresada por vista.
     *
     * Se invoca desde las vistas: adicionarServicios, adicionarVacio y
     * gestionPrgTc.
     *
     *
     *
     */
    public void findOperadorByCod() {
        prgTcOperadorDisponible = null;
        if (listPrgTcGestionOperador.isEmpty()) {
            MovilidadUtil.addErrorMessage("No hay servicios seleccionados");
            return;
        }
        if (MovilidadUtil.stringIsEmpty(codigoTm)) {
            /**
             * En caso de que no se haya digitado el código antes de consultar
             */
            MovilidadUtil.addErrorMessage("Digíte el código del Operador");
            return;
        }
        Empleado empl = emplEjb.findbyCodigoTmAndIdGopUnidadFuncActivo(MovilidadUtil.convertStringToInt(codigoTm), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (empl == null) {
            MovilidadUtil.addErrorMessage("Operador no disponible");
            return;
        }
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals("1")) {
            ajusteJornadaFromGestionServicio.setIdEmpleadoFound(empl.getIdEmpleado());
            if (ajusteJornadaFromGestionServicio.cargarPrgSerconFound()) {
                empl = null;
                MovilidadUtil.addErrorMessage("Operador no cuenta con jornada en Control Jornada.");
                return;
            }
        }
        boolean validarDocuOperador = validarDocumentacionBean.validarDocuOperador(fechaConsulta, empl.getIdEmpleado());
        if (validarDocuOperador) {
            MovilidadUtil.addErrorMessage("El Operador tiene documentos vencidos");
            empl = null;
            return;
        }

        /**
         * En el siguiente bucle for sirve para saber que servicios de los
         * seleccionados en vista es el primero en el ejecutarse y cual es le
         * ultimo.
         */
        PrgTc primerServicio = Collections.min(listPrgTcGestionOperador, Comparator.comparing(tc -> MovilidadUtil.toSecs(tc.getTimeOrigin())));
        PrgTc UltimoServicio = Collections.max(listPrgTcGestionOperador, Comparator.comparing(tc -> MovilidadUtil.toSecs(tc.getTimeOrigin())));
//        System.out.println("primerServicio->" + primerServicio.getTimeOrigin());
//        System.out.println("UltimoServicio->" + UltimoServicio.getTimeOrigin());
        if (validarDescansoLegal(empl, primerServicio)) {
            codigoTm = Integer.toString(empl.getCodigoTm());
            prgTcOperadorDisponible = null;
            return;
        }
        /**
         * Se verifica si el operador consultado esta en servicio o proximamente
         * ocupado.
         */
//        System.out.println("primerServicio.getTimeOrigin()->" + primerServicio.getTimeOrigin());
//        System.out.println("Integer.parseInt(codigoTm)->" + Integer.parseInt(codigoTm));
//        System.out.println("fechaConsulta->" + fechaConsulta);
        if (validarOperadorOcupado(listPrgTcGestionOperador, empl.getIdEmpleado(), fechaConsulta)) {
            prgTcOperadorDisponible = null;
            return;
        }
//        PrgTc prgTcEjecucion = prgTcEJB.serviciosPrgTcPendientesPorOperador(primerServicio.getTimeOrigin(), empl.getIdEmpleado(), fechaConsulta);
////        System.out.println("prgTcEjecucion1->" + prgTcEjecucion);
//        if (prgTcEjecucion != null) {
//            MovilidadUtil.addErrorMessage("Operado no disponible, ocupado");
//            return;
//        } else {
//            prgTcEjecucion = prgTcEJB.validarServicioEnEjecucionByHoraForOperador(UltimoServicio.getTimeDestiny(), empl.getIdEmpleado(), fechaConsulta);
////            System.out.println("prgTcEjecucion2->" + prgTcEjecucion);
//            if (prgTcEjecucion != null) {
//                MovilidadUtil.addErrorMessage("Operado no disponible, ocupado");
//                return;
//            }
//        }
        /**
         * El siguiente bloque de código ayuda a verificar si los servicios
         * seleccionados tiene buses asignados
         */
        /*
         *--------------------------------------------------------------
         */
        prgTcOperadorDisponible = prgTcEJB.findOperadorByCod(codigoTm, fechaConsulta);
        PrgTc tcValidar = null;
        for (PrgTc tc : listPrgTcGestionOperador) {
            if (tc.getIdTaskType() != null) {
                if (tc.getIdVehiculoTipo() != null) {
                    tcValidar = tc;

                }
            }
        }
        if (tcValidar == null) {
            tcValidar = listPrgTcGestionOperador.get(0);
        }
        if (tcValidar.getIdVehiculoTipo() == null) {
            prgTcConServbus = prgTcEJB.findPrgTcAsignacionOperador(prgTc.getSercon(),
                    prgTc.getFecha(), prgTc.getTimeOrigin(), prgTc.getIdGopUnidadFuncional() == null ? 0 : prgTc.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
            if (prgTcConServbus != null) {
                tcValidar = prgTcConServbus;
            } else {
                prgTcOperadorDisponible = null;
                MovilidadUtil.addErrorMessage("No se permite Hacer el cambio. Realizar asignación de vehículos");
                return;
            }
        }
        /**
         * -------------------------------------------------------------
         */

        if (prgTcOperadorDisponible != null) {
            /**
             * Verificar sí el operador en casos de que los servicios sean de
             * tipología Biarticulado, sea tambien de esta tipología
             */
            if ((tcValidar.getIdVehiculoTipo().getIdVehiculoTipo() == 2)
                    && prgTcOperadorDisponible.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo()
                            .equals(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_OP_TIPO_UNO))) {
                if (prgTcOperadorDisponible.getIdEmpleado().getCertificado() == null
                        || !prgTcOperadorDisponible.getIdEmpleado().getCertificado().equals(1)) {
                    MovilidadUtil.addErrorMessage("Operador no habilitado para "
                            + tcValidar.getIdVehiculoTipo().getNombreTipoVehiculo() + " "
                            + empleadoToString(prgTcOperadorDisponible.getIdEmpleado()));
                    codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
                    prgTcOperadorDisponible = null;
                    return;
                }
            }
            MovilidadUtil.addSuccessMessage("Empleado Cargado: "
                    + empleadoToString(prgTcOperadorDisponible.getIdEmpleado())
                    + " con tarea: " + prgTcOperadorDisponible.getSercon());
            codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
            serconString = prgTcOperadorDisponible.getSercon();
        } else {
            prgTcOperadorDisponible = prgTcEJB.findOperadorProgramado(codigoTm, fechaConsulta);
            if (prgTcOperadorDisponible != null) {
                /**
                 * Verificar sí el operador en casos de que los servicios sean
                 * de tipología Biarticulado, sea tambien de esta tipología
                 */
                if ((tcValidar.getIdVehiculoTipo().getIdVehiculoTipo() == 2)
                        && prgTcOperadorDisponible.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo()
                                .equals(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_OP_TIPO_UNO))) {
                    if (prgTcOperadorDisponible.getIdEmpleado().getCertificado() == null
                            || !prgTcOperadorDisponible.getIdEmpleado().getCertificado().equals(1)) {
                        MovilidadUtil.addErrorMessage("Operador no habilitado para "
                                + tcValidar.getIdVehiculoTipo().getNombreTipoVehiculo() + " "
                                + empleadoToString(prgTcOperadorDisponible.getIdEmpleado()));
                        codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
                        prgTcOperadorDisponible = null;
                        return;
                    }
                }
                MovilidadUtil.addSuccessMessage("Empleado Cargado: "
                        + empleadoToString(prgTcOperadorDisponible.getIdEmpleado())
                        + " con tarea: " + prgTcOperadorDisponible.getSercon());
                codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
                serconString = prgTcOperadorDisponible.getSercon();
            } else {
                prgTcOperadorDisponible = new PrgTc();
                prgTcOperadorDisponible.setIdEmpleado(empl);
                MovilidadUtil.addSuccessMessage("Empleado Cargado: "
                        + empleadoToString(prgTcOperadorDisponible.getIdEmpleado()));
                codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
            }
        }
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals("1")) {
            List<PrgTc> listTareaDispoFound = prgTcEJB.tareasDispoByIdEmpeladoAndFechaAndUnidadFunc(prgTcOperadorDisponible.getIdEmpleado().getIdEmpleado(),
                    fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            ajusteJornadaFromGestionServicio.setListTareasDispFound(listTareaDispoFound);
            ajusteJornadaFromGestionServicio.setIdEmpleadoFound(prgTcOperadorDisponible.getIdEmpleado().getIdEmpleado());
            ajusteJornadaFromGestionServicio.cargarListaServiciosOpFound();
            ajusteJornadaFromGestionServicio.recrearJornadaFound("formGestionPrgTc:tabViews:turnoEmplSelected");
            ajusteJornadaFromGestionServicio.ajusteJornadasFoundII(listPrgTcGestionOperador, "formGestionPrgTc:tabViews:turnoEmplFound");
            ajusteJornadaFromGestionServicio.recrearJornadaSelected();
            ajusteJornadaFromGestionServicio.cargarTotalHorasExtrasSmanal();
            ajusteJornadaFromGestionServicio.validarMaxHorasExtrasSmanales();
        }
    }

    public void refrescarTurno() {
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals("1")) {
            ajusteJornadaFromGestionServicio.recrearJornadaSelected();
            if (!ajusteJornadaFromGestionServicio.isDisponibleAlCambio()) {
                ajusteJornadaFromGestionServicio.setDisponibleContingencia(false);
            }
        }
    }

    /**
     * Metodo responsable de consultra y cargar operador para un vacio depuesde
     * de una eliminación de servicios.
     *
     * Se invoca en la vista adicionarVacio al invocar la gestion de eliminación
     * desde el panel principal.
     *
     * la variable codigoTmVac es suministrado desde la vista por el usuario.
     */
    public void findOperadorByCodCreateVaio() {
        if (MovilidadUtil.stringIsEmpty(codigoTmVac)) {
            MovilidadUtil.addErrorMessage("Digíte el código del Operador");
            return;
        }
        Empleado empl = emplEjb.findbyCodigoTmAndIdGopUnidadFuncActivo(Integer.parseInt(codigoTmVac), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (empl == null) {
            MovilidadUtil.addErrorMessage("Operador no disponible");
            return;
        }
        boolean validarDocuOperador = validarDocumentacionBean.validarDocuOperador(fechaConsulta, empl.getIdEmpleado());
        if (validarDocuOperador) {
            MovilidadUtil.addErrorMessage("El Operador tiene documentos vencidos");
            empl = null;
            return;
        }
        empl = null;
        prgTcOperadorDisponible = prgTcEJB.findOperadorByCod(codigoTmVac, fechaConsulta);
        if (prgTcOperadorDisponible == null) {
            MovilidadUtil.addErrorMessage("Operador no disponible");
        }
        if (prgTc.getIdVehiculoTipo().getIdVehiculoTipo() == 2
                && prgTcOperadorDisponible.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo()
                        .equals(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_OP_TIPO_UNO))) {
            if (prgTcOperadorDisponible.getIdEmpleado().getCertificado() == null
                    || !prgTcOperadorDisponible.getIdEmpleado().getCertificado().equals(1)) {
                MovilidadUtil.addErrorMessage("Operador no habilitado para "
                        + prgTc.getIdVehiculoTipo().getNombreTipoVehiculo() + " "
                        + empleadoToString(prgTcOperadorDisponible.getIdEmpleado()));
                codigoTmVac = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
                prgTcOperadorDisponible = null;
                return;
            }
        }
        MovilidadUtil.addSuccessMessage("Empleado Cargado: "
                + empleadoToString(prgTcOperadorDisponible.getIdEmpleado())
                + " con tarea: " + prgTcOperadorDisponible.getSercon());
        codigoTmVac = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
        serconString = prgTcOperadorDisponible.getSercon();
    }

    /**
     * Metodo responsable de consultar y cargar operador para el proceso de
     * asignacion de operador a servicios sin operador.
     *
     * Se invoca en la vista gestionPrgTc al utilizar la gestion de asignacion
     * de servicios sin operador.
     *
     * la variable codigoTm es suministrado desde la vista por el usuario.
     */
    public void findOperadorByCodForSinOperador() {
        prgTcOperadorDisponible = null;
        if (listPrgTcGestionOperador.isEmpty()) {
            MovilidadUtil.addErrorMessage("No hay servicios seleccionados");
            return;
        }
        if (MovilidadUtil.stringIsEmpty(codigoTm)) {
            MovilidadUtil.addErrorMessage("Digíte el código del Operador");
            emplPrgTcSinOperador = null;
            return;
        }
        Empleado empl = emplEjb.findbyCodigoTmAndIdGopUnidadFuncActivo(MovilidadUtil.convertStringToInt(codigoTm), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (empl == null) {
            MovilidadUtil.addErrorMessage("No existe operador diponible con el codigo digitado.");
            return;
        }
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals("1")) {
            ajusteJornadaFromGestionServicio.setIdEmpleadoFound(empl.getIdEmpleado());
            if (ajusteJornadaFromGestionServicio.cargarPrgSerconFound()) {
                empl = null;
                MovilidadUtil.addErrorMessage("Operador no cuenta con jornada en Control Jornada.");
                return;
            }
        }
        boolean validarDocuOperador = validarDocumentacionBean.validarDocuOperador(fechaConsulta, empl.getIdEmpleado());
        if (validarDocuOperador) {
            MovilidadUtil.addErrorMessage("El Operador tiene documentos vencidos");
            empl = null;
            return;
        }
        PrgTc primerServicio = Collections.min(listPrgTcGestionOperador, Comparator.comparing(tc -> MovilidadUtil.toSecs(tc.getTimeOrigin())));
        PrgTc UltimoServicio = Collections.max(listPrgTcGestionOperador, Comparator.comparing(tc -> MovilidadUtil.toSecs(tc.getTimeOrigin())));
        if (validarDescansoLegal(empl, primerServicio)) {
            codigoTm = Integer.toString(empl.getCodigoTm());
            prgTcOperadorDisponible = null;
            return;
        }
        /**
         * Se verifica si el operador consultado esta en servicio o proximamente
         * ocupado.
         */

        if (validarOperadorOcupado(listPrgTcGestionOperador, empl.getIdEmpleado(), fechaConsulta)) {
            prgTcOperadorDisponible = null;
            emplPrgTcSinOperador = null;
            return;
        }
//        PrgTc prgTcEjecucion = prgTcEJB.validarServicioEnEjecucionByHoraForOperador(primerServicio.getTimeOrigin(), empl.getIdEmpleado(), fechaConsulta);
//        if (prgTcEjecucion != null) {
//            MovilidadUtil.addErrorMessage("Operado no disponible");
//            return;
//        } else {
//            prgTcEjecucion = prgTcEJB.validarServicioEnEjecucionByHoraForOperador(UltimoServicio.getTimeDestiny(), empl.getIdEmpleado(), fechaConsulta);
//            if (prgTcEjecucion != null) {
//                MovilidadUtil.addErrorMessage("Operado no disponible");
//                return;
//            }
//        }
        prgTcOperadorDisponible = prgTcEJB.findOperadorByCod(codigoTm, fechaConsulta);
        prgTcSinOperador = primerServicio;
        if (prgTcSinOperador.getIdVehiculoTipo() == null) {
            for (PrgTc p : listPrgTcGestionOperador) {
                if (p.getIdVehiculoTipo() != null) {
                    prgTcSinOperador = p;
                }
            }
        }
        /**
         * Verificar sí el operador en casos de que los servicios sean de
         * tipología Biarticulado, sea tambien de esta tipología o este
         * certificado
         */
        if (prgTcOperadorDisponible != null) {

            emplPrgTcSinOperador = prgTcOperadorDisponible.getIdEmpleado();
            if (prgTcSinOperador.getIdVehiculoTipo() != null) {
                if (prgTcSinOperador.getIdVehiculoTipo().getIdVehiculoTipo().equals(ConstantsUtil.ID_V_TIPO_DOS)
                        && prgTcOperadorDisponible.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo()
                                .equals(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_OP_TIPO_UNO))) {
                    if (prgTcOperadorDisponible.getIdEmpleado().getCertificado() == null
                            || !prgTcOperadorDisponible.getIdEmpleado().getCertificado().equals(1)) {
                        MovilidadUtil.addErrorMessage("Operador no habilitado para "
                                + prgTcSinOperador.getIdVehiculoTipo().getNombreTipoVehiculo() + " "
                                + empleadoToString(prgTcOperadorDisponible.getIdEmpleado()));
                        codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
                        prgTcOperadorDisponible = null;
                        return;
                    }
                }
            } else {
                /**
                 * Verificar sí el operador en casos de que los servicios sean
                 * de tipología Biarticulado, sea tambien de esta tipología o
                 * este certificado.
                 */
                PrgTc prgTcConServbus = prgTcEJB.findServiceConServbus(prgTcSinOperador.getSercon(),
                        prgTcSinOperador.getFecha(), prgTcSinOperador.getIdGopUnidadFuncional() == null
                        ? 0 : prgTcSinOperador.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
                if (prgTcConServbus != null) {
                    if (prgTcConServbus.getIdVehiculoTipo().getIdVehiculoTipo().equals(ConstantsUtil.ID_V_TIPO_DOS)
                            && prgTcOperadorDisponible.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo()
                                    .equals(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_OP_TIPO_UNO))) {
                        if (prgTcOperadorDisponible.getIdEmpleado().getCertificado() == null
                                || !prgTcOperadorDisponible.getIdEmpleado().getCertificado().equals(1)) {
                            MovilidadUtil.addErrorMessage("Operador no habilitado para "
                                    + prgTcConServbus.getIdVehiculoTipo().getNombreTipoVehiculo() + " "
                                    + empleadoToString(prgTcOperadorDisponible.getIdEmpleado()));
                            codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
                            prgTcOperadorDisponible = null;
                            return;
                        }
                    }
                }
            }

            MovilidadUtil.addSuccessMessage("Empleado Cargado: "
                    + empleadoToString(prgTcOperadorDisponible.getIdEmpleado())
                    + " con tarea: " + prgTcOperadorDisponible.getSercon());
            codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
            serconString = prgTcOperadorDisponible.getSercon();
        } else {
            prgTcOperadorDisponible = prgTcEJB.findOperadorProgramado(codigoTm, fechaConsulta);
            /**
             * Verificar sí el operador en casos de que los servicios sean de
             * tipología Biarticulado, sea tambien de esta tipología o este
             * certificado.
             */
            if (prgTcOperadorDisponible != null) {
                emplPrgTcSinOperador = prgTcOperadorDisponible.getIdEmpleado();
                if (prgTcSinOperador.getIdVehiculoTipo() != null) {
                    if (prgTcSinOperador.getIdVehiculoTipo().getIdVehiculoTipo().equals(ConstantsUtil.ID_V_TIPO_DOS)
                            && prgTcOperadorDisponible.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo()
                                    .equals(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_OP_TIPO_UNO))) {
                        if (prgTcOperadorDisponible.getIdEmpleado().getCertificado() == null
                                || !prgTcOperadorDisponible.getIdEmpleado().getCertificado().equals(1)) {
                            MovilidadUtil.addErrorMessage("Operador no habilitado para "
                                    + prgTcSinOperador.getIdVehiculoTipo().getNombreTipoVehiculo() + " "
                                    + empleadoToString(prgTcOperadorDisponible.getIdEmpleado()));
                            codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
                            prgTcOperadorDisponible = null;
                            return;
                        }
                    }
                } else {
                    /**
                     * Verificar sí el operador en casos de que los servicios
                     * sean de tipología Biarticulado, sea tambien de esta
                     * tipología o este certificado.
                     */
                    PrgTc prgTcConServbus_local = prgTcEJB.findServiceConServbus(
                            prgTcSinOperador.getSercon(), prgTcSinOperador.getFecha(),
                            prgTcSinOperador.getIdGopUnidadFuncional() == null
                            ? 0 : prgTcSinOperador.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
                    if (prgTcConServbus_local != null) {
                        if (prgTcConServbus_local.getIdVehiculoTipo().getIdVehiculoTipo().equals(ConstantsUtil.ID_V_TIPO_DOS)
                                && prgTcOperadorDisponible.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo()
                                        .equals(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_OP_TIPO_UNO))) {
                            if (prgTcOperadorDisponible.getIdEmpleado().getCertificado() == null
                                    || !prgTcOperadorDisponible.getIdEmpleado().getCertificado().equals(1)) {
                                MovilidadUtil.addErrorMessage("Operador no habilitado para "
                                        + prgTcConServbus_local.getIdVehiculoTipo().getNombreTipoVehiculo() + " "
                                        + empleadoToString(prgTcOperadorDisponible.getIdEmpleado()));
                                codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
                                prgTcOperadorDisponible = null;
                                return;
                            }
                        }
                    }
                }
                MovilidadUtil.addSuccessMessage("Empleado Cargado: "
                        + empleadoToString(prgTcOperadorDisponible.getIdEmpleado())
                        + " con tarea: " + prgTcOperadorDisponible.getSercon());
                codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
                serconString = prgTcOperadorDisponible.getSercon();
            } else {

                /**
                 * Verificar sí el operador en casos de que los servicios sean
                 * de tipología Biarticulado, sea tambien de esta tipología o
                 * este certificado.
                 */
                emplPrgTcSinOperador = empl;
                prgTcOperadorDisponible = new PrgTc();
                prgTcOperadorDisponible.setIdEmpleado(empl);
                if (prgTcSinOperador.getIdVehiculoTipo() != null) {
                    if (prgTcSinOperador.getIdVehiculoTipo().getIdVehiculoTipo().equals(ConstantsUtil.ID_V_TIPO_DOS)
                            && prgTcOperadorDisponible.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo()
                                    .equals(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_OP_TIPO_UNO))) {
                        if (prgTcOperadorDisponible.getIdEmpleado().getCertificado() == null
                                || !prgTcOperadorDisponible.getIdEmpleado().getCertificado().equals(1)) {
                            MovilidadUtil.addErrorMessage("Operador no habilitado para "
                                    + prgTcSinOperador.getIdVehiculoTipo().getNombreTipoVehiculo() + " "
                                    + empleadoToString(prgTcOperadorDisponible.getIdEmpleado()));
                            codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
                            prgTcOperadorDisponible = null;
                            return;
                        }
                    }
                } else {
                    /**
                     * Verificar sí el operador en casos de que los servicios
                     * sean de tipología Biarticulado, sea tambien de esta
                     * tipología o este certificado.
                     */
                    PrgTc prgTcConServbus = prgTcEJB.findServiceConServbus(
                            prgTcSinOperador.getSercon(), prgTcSinOperador.getFecha(),
                            prgTcSinOperador.getIdGopUnidadFuncional() == null
                            ? 0 : prgTcSinOperador.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
                    if (prgTcConServbus != null) {
                        if (prgTcConServbus.getIdVehiculoTipo().getIdVehiculoTipo().equals(ConstantsUtil.ID_V_TIPO_DOS)
                                && prgTcOperadorDisponible.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo()
                                        .equals(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_OP_TIPO_UNO))) {
                            if (prgTcOperadorDisponible.getIdEmpleado().getCertificado() == null
                                    || !prgTcOperadorDisponible.getIdEmpleado().getCertificado().equals(1)) {
                                MovilidadUtil.addErrorMessage("Operador no habilitado para "
                                        + prgTcConServbus.getIdVehiculoTipo().getNombreTipoVehiculo() + " "
                                        + empleadoToString(prgTcOperadorDisponible.getIdEmpleado()));
                                codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());
                                prgTcOperadorDisponible = null;
                                return;
                            }
                        }
                    }
                }
                MovilidadUtil.addSuccessMessage("Empleado Cargado: "
                        + empleadoToString(prgTcOperadorDisponible.getIdEmpleado()));
                codigoTm = Integer.toString(prgTcOperadorDisponible.getIdEmpleado().getCodigoTm());

            }
        }
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals("1")) {
            List<PrgTc> listTareaDispoFound = prgTcEJB.tareasDispoByIdEmpeladoAndFechaAndUnidadFunc(prgTcOperadorDisponible.getIdEmpleado().getIdEmpleado(),
                    fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

            ajusteJornadaFromGestionServicio.setListTareasDispFound(listTareaDispoFound);
            ajusteJornadaFromGestionServicio.setIdEmpleadoFound(prgTcOperadorDisponible.getIdEmpleado().getIdEmpleado());
            ajusteJornadaFromGestionServicio.cargarPrgSerconFound();
            ajusteJornadaFromGestionServicio.cargarListaServiciosOpFound();
            ajusteJornadaFromGestionServicio.recrearJornadaFound("formGestionPrgTc:tabViews:turnoEmplFoundII");
            ajusteJornadaFromGestionServicio.ajusteJornadasFoundII(listPrgTcGestionOperador, "formGestionPrgTc:tabViews:turnoEmplFoundII");
            ajusteJornadaFromGestionServicio.cargarTotalHorasExtrasSmanal();
            ajusteJornadaFromGestionServicio.validarMaxHorasExtrasSmanales();
        }
    }

    /**
     * Método encargado de cargas los detalles de ruta o PrgPattern en la
     * gestión de adicionar servicios
     *
     * Utiliza la variable i_idRuta que es seleccionada desde un componente
     * SelectOneMenu.
     *
     * Se invoca en la vista adicionarServicios.
     */
    public void findByIdRoute() {
        listPrgPattern = prgPatternEJB.findAllOrderedByIdRoute(getI_idRuta());
        if (listPrgPattern.isEmpty()) {
            MovilidadUtil.addErrorMessage("Sin paradas");
        }
    }

    /**
     * Método encardado de identificar que tipo de servicio se desea adicionar
     * desde la gestión de adicionar servicios.
     *
     * utiliza la variable i_idTarea que es seleccionada desde un componente
     * SelectOneMenu.
     *
     * Se invoca desde la vista adicionarServicios.
     */
    public void onOffRuta() {
        for (PrgTarea p : listPrgTarea) {
            if (i_idTarea != null) {
                if (i_idTarea.equals(p.getIdPrgTarea())) {
                    if (isVaccom(p.getIdPrgTarea())) {
                        setI_idTareaNewService(p);
                        flagRuta = true;
                        flagPInicioPFin = true;
                        flagPropia = false;
                        propia = true;
                        return;
                    }
                    if (isVac(p.getIdPrgTarea())) {
                        setI_idTareaNewService(p);
                        flagRuta = true;
                        flagPInicioPFin = true;
                        flagPropia = true;
                        propia = false;
                        return;
                    }
                    if (p.getComercial() == 1 && !(isVaccom(p.getIdPrgTarea()))) {
                        setI_idTareaNewService(p);
                        flagRuta = false;
                        flagPInicioPFin = false;
                        flagPropia = true;
                        propia = false;
                        return;
                    }
                }
            }
        }
    }

    /**
     * Método responsable de consultar los servicios para ser cargados en la
     * tabla de servicios del panel principal
     *
     * utiliza las variables tabla, servbus, sercon, vehículo, servicio,
     * operador que son suministradas por el usuario desde el panel principal.
     *
     * Se invoca desde la vista prgTc que esta incluida en la vista
     * panelPrincipal, que hacer parte del panel principal.
     */
    public void findServiceBy() {
        prgTc = null;
//        listPrgTc = new ArrayList<>();
//        String campo;
        if (MovilidadUtil.stringIsEmpty(i_tabla)
                && MovilidadUtil.stringIsEmpty(i_servicio)
                && MovilidadUtil.stringIsEmpty(servBus)
                && MovilidadUtil.stringIsEmpty(sercon)
                && MovilidadUtil.stringIsEmpty(codigoOperador)
                && MovilidadUtil.stringIsEmpty(vehiculoC)) {
            listPrgTc = null;
            return;
        }

        listPrgTc = prgTcEJB.findServicioGenerico(i_tabla, i_servicio, servBus,
                sercon, codigoOperador, vehiculoC, fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        listGestionVehiculo = gestionVehiculoFacadeLocal.findAllEstadoReg();

        for (PrgTc prgTcItem : listPrgTc) {
            for (GestionVehiculo gestionVehiculoItem : listGestionVehiculo) {
                if (prgTcItem.getIdVehiculo() != null) {
                    if (prgTcItem.getIdVehiculo().equals(gestionVehiculoItem.getIdVehiculo())) {
                        System.out.println(gestionVehiculoItem.getIdVehiculo());
                        prgTcItem.setBateria(gestionVehiculoItem.getBateria());
                        //    prgTcItem.setBateria(gestionVehiculoItem.getBateria());
                        break;
                    }
                }

            }
        }
    }

    /**
     * Método encargado de cargar el un mapa con la data necesaria para enviar
     * un correo. La data es consultada en la base de datos.
     *
     * @return Mapa cargado con la data necesaria.
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(1);
        NotificacionTemplate template = notificacionTemplateEjb.find(ConstantsUtil.TEMPLATENOVOPERACION);
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
     * Método responsable de notificar vía correo, la acción realizada
     *
     * Utiliza un mapa con la configuración para enviar correos que es cargado
     * desde el método getMailParams().
     *
     * Utiliza la Un objeto de novedad previamente cargado el la gestión de
     * servicios.
     */
    private void notificar() {
        try {

            Map mapa = getMailParams();
            Map mailProperties = new HashMap();
            //Verificar su la novedad tiene empleado cargado
            if (novedad.getIdEmpleado() != null) {
                //consultar empleado cargado en la novedad
                Empleado emp = emplEjb.find(novedad.getIdEmpleado().getIdEmpleado());
                novedad.setIdEmpleado(emp);
            }
            //Verificar si la novedad tiene vehículo cargado
            if (novedad.getIdVehiculo() != null && novedad.getIdVehiculo().getCodigo() != null) {
                //Consultar vehículo cargado en al novedad
                Vehiculo veh = vehEJB.find(novedad.getIdVehiculo().getIdVehiculo());
                novedad.setIdVehiculo(veh);
            }
            mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
            mailProperties.put("tipo", novedad.getIdNovedadTipo().getNombreTipoNovedad());
            mailProperties.put("detalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
            mailProperties.put("operador", novedad.getIdEmpleado() != null ? empleadoToString(novedad.getIdEmpleado()) : "");
            mailProperties.put("telefono", novedad.getIdEmpleado() != null ? novedad.getIdEmpleado().getTelefonoMovil() : "");
            mailProperties.put("servicio", novedad.getPrgTc() != null
                    ? (novedad.getPrgTc().getIdTaskType() != null
                    ? novedad.getPrgTc().getIdTaskType().getTarea() + " Tabla: " + novedad.getPrgTc().getTabla() : "") : "");
            mailProperties.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");
            mailProperties.put("lugar", lugarTQ != null ? lugarTQ.getName() + " " + direccionAcc : direccionAcc);
            mailProperties.put("username", "");
            mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
            mailProperties.put("sistema",
                    novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT ? ""
                    : selectDispSistemaBean.getId_dis_sistema() != null
                    ? dispSistemaEjb.find(selectDispSistemaBean.getId_dis_sistema()).getNombre() : "");
            mailProperties.put("observaciones", novedad.getObservaciones());
            String subject = "Novedad " + novedad.getIdNovedadTipo().getNombreTipoNovedad();
            String destinatarios;

            //Busqueda Operador Máster/
            String correoMaster = "";
            try {
                //identificar cual es el lider de grupo para el empleado cargado
                if (novedad.getIdEmpleado().getPmGrupoDetalleList().size() == 1) {
                    correoMaster = novedad.getIdEmpleado().getPmGrupoDetalleList().get(0).getIdGrupo().getIdEmpleado().getEmailCorporativo();
                }
            } catch (Exception e) {

            }

            destinatarios = novedad.getIdEmpleado() != null ? correoMaster + "," + novedad.getIdEmpleado().getEmailCorporativo() : "";
            //Verificar si el tipo de detalle de novedad, notifica
            if (novedad.getIdNovedadTipoDetalle().getNotificacion() == 1) {
                if (novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {
                    if (destinatarios != null) {
                        destinatarios = destinatarios + "," + novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                    } else {
                        destinatarios = novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                    }

                    if (novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getNotificacionProcesoDetList() != null) {
                        String destinatariosByUf = "";

                        destinatariosByUf = MovilidadUtil.obtenerCorreosByUf(novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getNotificacionProcesoDetList(), novedad.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                        if (destinatariosByUf != null) {
                            destinatarios = destinatarios + "," + destinatariosByUf;
                        }
                    }

                }
                //Método responasble de enviar el mensaje vía email
                SendMails.sendEmail(mapa, mailProperties, subject, "", destinatarios, "Notificaciones RIGEL", null);
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al enviar correo");
            PrimeFaces.current().executeScript("PF('gestionPrgTcDialog').hide()");
            PrimeFaces.current().ajax().update("prgtcForm:tblPrgTc");
            findServiceBy();
            e.printStackTrace();
        }
    }

    private void cargarResponsable() {
        lstResponsable = prgTcRespEJB.getPrgResponsables();
    }

    private void cargarDetallesAccesoRapido() {
        lstDetallesAccesoRapido = novedadTDetEJB.obtenerDetallesAccesoRapido();

        if (lstDetallesAccesoRapido == null || lstDetallesAccesoRapido.isEmpty()) {
            flagBotonesGestionServicio = true;
        }

    }

    public void cargarClasificacion() {
        lstClasificacion = lstClasificacionSingleton();
        Optional<PrgTcResponsable> findFirst = lstResponsable.stream().filter((obj)
                -> (obj.getIdPrgTcResponsable().equals(i_codResponsable))).findFirst();
        if (findFirst.isPresent()) {
            for (PrgClasificacionMotivo s : findFirst.get().getPrgClasificacionMotivoList()) {
                if (s.getEstadoReg().equals(0)) {
                    lstClasificacion.add(s);
                }
            }
        }
    }

    private List<PrgClasificacionMotivo> lstClasificacionSingleton() {
        if (lstClasificacion != null) {
            lstClasificacion.clear();
            return lstClasificacion;
        } else {
            return new ArrayList<>();
        }
    }

    public void validarDiaBloqueado() {
        if (listPrgTc != null) {
            listPrgTc.clear();
        }
        flagConciliado = validarFinOperacionBean.validarDiaBloqueado(fechaConsulta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    private boolean validarDescansoLegal(Empleado empl, PrgTc primerServicio) {
        if (empl == null) {
            return false;
        }
        String hIniTurnoACambiar;
        int dif;
        Date dateBefore = MovilidadUtil.sumarDias(primerServicio.getFecha(), -1);
        Date dateAfter = MovilidadUtil.sumarDias(primerServicio.getFecha(), 1);
        PrgTc ultimoServicioAyerRemplazo = prgTcEJB
                .findFirtOrEndServiceByIdEmpleado(
                        empl.getIdEmpleado(),
                        dateBefore, "DESC");
        if (ultimoServicioAyerRemplazo != null) {

            String sTimeOriginRemp = primerServicio.getTimeOrigin();
            String timeDestinyDiaAnterior = ultimoServicioAyerRemplazo.getTimeDestiny();
            if (MovilidadUtil.toSecs(timeDestinyDiaAnterior) == 0) {
                sTimeOriginRemp = "24:00:00";
            }
            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRemp, "24:00:00");

            dif = MovilidadUtil.diferencia(timeDestinyDiaAnterior, hIniTurnoACambiar);

            if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                MovilidadUtil.addErrorMessage("El siguiente cambio no está respetando " + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO) + " de descanso con respecto al dia anterior.");
                return true;
            }
        }
        PrgTc ultimoServicioMananaRemplazo = prgTcEJB
                .findFirtOrEndServiceByIdEmpleado(
                        empl.getIdEmpleado(),
                        dateAfter, "ASC");
        if (ultimoServicioMananaRemplazo != null) {
            String sTimeOriginRempSig = ultimoServicioMananaRemplazo.getTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOriginRempSig) == 0) {
                sTimeOriginRempSig = "24:00:00";
            }

            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRempSig, "24:00:00");
            dif = MovilidadUtil.diferencia(primerServicio.getTimeDestiny(), hIniTurnoACambiar);
            if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                MovilidadUtil.addErrorMessage("El siguiente cambio no está respetando " + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO) + " de descanso con respecto al dia siguiente.");
                return true;
            }
        }
        return false;
    }

    String empleadoToString(Empleado param) {
        return param.getCodigoTm() + "-" + param.getNombres() + " " + param.getApellidos();
    }

    public void prepareMapGeo() {
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Debe selecionar un servicio de la tabla.");
            return;
        }
        if (prgTc.getIdVehiculo() == null) {
            MovilidadUtil.addErrorMessage("El servicio nmo tiene vehículo asignado.");
            return;
        }
        mapGeoBean.openMapGeo(prgTc.getIdVehiculo().getCodigo());
    }

    public void prepareMapGeoVehEspecifico() {
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Debe selecionar un servicio de la tabla.");
            return;
        }
        if (prgTc.getIdVehiculo() == null) {
            MovilidadUtil.addErrorMessage("El servicio no tiene vehículo asignado.");
            return;
        }
        mapGeoBean.openMapGeoVehEspecifico(prgTc);
    }

    /*
     * Parámetros para el envío de correos de fallas a Mantenimiento
     */
    private Map getMailParamsMTTO() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_NOVEDADES_MTTO);
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
     * Realiza el envío de correo de las fallas registradas a las partes
     * interesadas ( Mantenimiento )
     */
    private void notificarMtto() {
        Map mapa = getMailParamsMTTO();
        Map mailProperties = new HashMap();
        //Verificar su la novedad tiene empleado cargado
        if (novedad.getIdEmpleado() != null) {
            //consultar empleado cargado en la novedad
            Empleado emp = emplEjb.find(novedad.getIdEmpleado().getIdEmpleado());
            novedad.setIdEmpleado(emp);
        }
        //Verificar si la novedad tiene vehículo cargado
        if (novedad.getIdVehiculo() != null && novedad.getIdVehiculo().getCodigo() != null) {
            //Consultar vehículo cargado en al novedad
            Vehiculo veh = vehEJB.find(novedad.getIdVehiculo().getIdVehiculo());
            novedad.setIdVehiculo(veh);
        }
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("tipoNovedad", novedad.getIdNovedadTipo().getNombreTipoNovedad());
        mailProperties.put("tipoDetalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
        mailProperties.put("fechaHora", Util.dateFormat(novedad.getFecha()));
        mailProperties.put("empleado", novedad.getIdEmpleado() != null ? novedad.getIdEmpleado().getCodigoTm() + " - " + novedad.getIdEmpleado().getNombres() + " " + novedad.getIdEmpleado().getApellidos() : "");
        mailProperties.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");
        mailProperties.put("sistema", selectDispSistemaBean.getId_dis_sistema() != null ? dispSistemaEjb.find(selectDispSistemaBean.getId_dis_sistema()).getNombre() : "");
        mailProperties.put("estado", novedad.getIdDispClasificacionNovedad().getIdDispEstadoPendActual() != null ? novedad.getIdDispClasificacionNovedad().getIdDispEstadoPendActual().getNombre() : "");
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
                    objeto.put("operador", novedad.getIdEmpleado() != null ? novedad.getIdEmpleado().getNombres() + " " + novedad.getIdEmpleado().getApellidos() : "");
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

                    objeto.put("sistema", selectDispSistemaBean.getId_dis_sistema() != null ? dispSistemaEjb.find(selectDispSistemaBean.getId_dis_sistema()).getNombre() : "");
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

    private boolean validarOperadorOcupado(List<PrgTc> listServiciosSeleccionados, int idempleado, Date fecha) {
        List<PrgTc> serviciosPrgTcPendientesPorOperador = prgTcEJB.serviciosPrgTcPendientesPorOperador(idempleado, fecha);

        for (PrgTc select : listServiciosSeleccionados) {
            for (PrgTc pend : serviciosPrgTcPendientesPorOperador) {
                if (MovilidadUtil.horaBetweenSinIgual(select.getTimeOrigin(), pend.getTimeOrigin(), pend.getTimeDestiny())
                        || MovilidadUtil.horaBetweenSinIgual(select.getTimeDestiny(), pend.getTimeOrigin(), pend.getTimeDestiny())) {
                    MovilidadUtil.addErrorMessage("Operado no disponible, ocupado. " + select.getTimeOrigin() + " " + select.getTimeDestiny());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean validarVehiculoOcupado(List<PrgTc> listServiciosSeleccionados, String codVehiculo, Date fecha, int idUnidaFunc) {
        List<PrgTc> serviciosPendientesPorVehiculo = prgTcEJB.serviciosPendientesPorVehiculo(codVehiculo, fecha, idUnidaFunc);

        for (PrgTc select : listServiciosSeleccionados) {
            for (PrgTc pend : serviciosPendientesPorVehiculo) {
                if (MovilidadUtil.horaBetweenSinIgual(select.getTimeOrigin(), pend.getTimeOrigin(), pend.getTimeDestiny())
                        || MovilidadUtil.horaBetweenSinIgual(select.getTimeDestiny(), pend.getTimeOrigin(), pend.getTimeDestiny())) {
                    MovilidadUtil.addErrorMessage("Vehículo con servicios pendientes. " + select.getTimeOrigin() + " " + select.getTimeDestiny());
                    return true;
                }
            }
        }
        return false;
    }

    public void findByCodigoOperadorPP(final SelectEvent event) {
        PrgSercon param = ((PrgSercon) event.getObject());
        if (param == null) {
            return;
        }
        empleadoListJSFManagedBean.setEmpl(param.getIdEmpleado());
        borrarCampos();
        codigoOperador = param.getIdEmpleado().getCodigoTm().toString();
        findServiceBy();
    }

// En tu ManagedBean o clase de backend
    public String getBatteryLevelClass(int bateria) {
        if (bateria >= 0 && bateria <= 10) {
            return "battery-level alert";
        } else if (bateria >= 11 && bateria <= 30) {
            return "battery-level warn";
        } else {
            return "battery-level";
        }
    }

    public String getBatteryHeight(int bateria) {
        if (bateria >= 0 && bateria <= 10) {
            return "10%";
        } else if (bateria == 30) {
            return "18%";
        } else if (bateria >= 11 && bateria <= 30) {
            return "25%";
        } else {
            return Integer.toString(bateria) + "%";
        }
    }

    public void onCerrarModalAsignarServbus() {
        listarSinBus();
        listarVehiculoDispo();
    }

    private boolean isVacPrg(Integer idTarea) {
        return validVacPrg(idTarea + "");
    }

    private boolean validVacPrg(String id) {
        for (String c : cargarArrayVacPrg()) {
            if (c.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private String[] cargarArrayVacPrg() {
        return SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_VAC_PRG).split(",");
    }

    private boolean isVaccom(Integer idTarea) {
        return validVaccom(idTarea + "");
    }

    private boolean isVac(Integer idTarea) {
        return validVac(idTarea + "");
    }

    private boolean isNotTareas(Integer idTarea) {
        return validTareas(idTarea + "");
    }

    private String[] cargarArrayVaccoms() {
        return SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_VACCOMS).split(",");
    }

    private String[] cargarArrayVac() {
        return SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_VAC).split(",");
    }

    private String[] cargarArrayTareas() {
        return SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_TAREAS).split(",");
    }

    private boolean validVaccom(String id) {
        for (String c : cargarArrayVaccoms()) {
            if (c.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean validVac(String id) {
        for (String c : cargarArrayVac()) {
            if (c.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean validTareas(String id) {
        for (String c : cargarArrayTareas()) {
            if (c.equalsIgnoreCase(id)) {
                return false;
            }
        }
        return true;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public List<PrgTc> getListPrgTc() {
        return listPrgTc;
    }

    public void setListPrgTc(List<PrgTc> listPrgTc) {
        this.listPrgTc = listPrgTc;
    }

    public String getI_tabla() {
        return i_tabla;
    }

    public void setI_tabla(String i_tabla) {
        this.i_tabla = i_tabla;
    }

    public String getI_servicio() {
        return i_servicio;
    }

    public void setI_servicio(String i_servicio) {
        this.i_servicio = i_servicio;
    }

    public String getServBus() {
        return servBus;
    }

    public void setServBus(String servBus) {
        this.servBus = servBus;
    }

    public List<PrgTc> getListPrgTcAux() {
        return listPrgTcAux;
    }

    public void setListPrgTcAux(List<PrgTc> listPrgTcAux) {
        this.listPrgTcAux = listPrgTcAux;
    }

    public String getVehiculoC() {
        return vehiculoC;
    }

    public void setVehiculoC(String vehiculoC) {
        this.vehiculoC = vehiculoC;
    }

    public PrgTc getprgTcOperadorDisponible() {
        return prgTcOperadorDisponible;
    }

    public void setprgTcOperadorDisponible(PrgTc prgTcOperadorDisponible) {
        this.prgTcOperadorDisponible = prgTcOperadorDisponible;
    }

    public String getCodigoOperador() {
        return codigoOperador;
    }

    public void setCodigoOperador(String codigoOperador) {
        this.codigoOperador = codigoOperador;
    }

    public String getSercon() {
        return sercon;
    }

    public void setSercon(String sercon) {
        this.sercon = sercon;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public TreeNode getRootOperador() {
        return rootOperador;
    }

    public void setRootOperador(TreeNode rootOperador) {
        this.rootOperador = rootOperador;
    }

    public TreeNode getRootVehiculo() {
        return rootVehiculo;
    }

    public void setRootVehiculo(TreeNode rootVehiculo) {
        this.rootVehiculo = rootVehiculo;
    }

    public TreeNode getRootEliminar() {
        return rootEliminar;
    }

    public void setRootEliminar(TreeNode rootEliminar) {
        this.rootEliminar = rootEliminar;
    }

    public TreeNode[] getCheckboxSelectedNodesOperador() {
        return checkboxSelectedNodesOperador;
    }

    public void setCheckboxSelectedNodesOperador(TreeNode[] checkboxSelectedNodesOperador) {
        this.checkboxSelectedNodesOperador = checkboxSelectedNodesOperador;
    }

    public TreeNode[] getCheckboxSelectedNodesEliminar() {
        return checkboxSelectedNodesEliminar;
    }

    public void setCheckboxSelectedNodesEliminar(TreeNode[] checkboxSelectedNodesEliminar) {
        this.checkboxSelectedNodesEliminar = checkboxSelectedNodesEliminar;
    }

    public TreeNode[] getCheckboxSelectedNodesVehiculo() {
        return checkboxSelectedNodesVehiculo;
    }

    public void setCheckboxSelectedNodesVehiculo(TreeNode[] checkboxSelectedNodesVehiculo) {
        this.checkboxSelectedNodesVehiculo = checkboxSelectedNodesVehiculo;
    }

    public int getI_detalle() {
        return i_detalle;
    }

    public void setI_detalle(int i_detalle) {
        this.i_detalle = i_detalle;
    }

    public int getI_tipoNovedad() {
        return i_tipoNovedad;
    }

    public void setI_tipoNovedad(int i_tipoNovedad) {
        this.i_tipoNovedad = i_tipoNovedad;
    }

    public int getI_tipoNovedaDet() {
        return i_tipoNovedaDet;
    }

    public void setI_tipoNovedaDet(int i_tipoNovedaDet) {
        this.i_tipoNovedaDet = i_tipoNovedaDet;
    }

    public List<NovedadTipo> getListNovedadT() {
        if (listNovedadT == null) {
            listNovedadT = novedadTEJB.obtenerTipos();
        }
        return listNovedadT;
    }

    public void setListNovedadT(List<NovedadTipo> listNovedadT) {
        this.listNovedadT = listNovedadT;
    }

    public boolean isFlagOpkmCl() {
        return flagOpkmCl;
    }

    public void setFlagOpkmCl(boolean flagOpkmCl) {
        this.flagOpkmCl = flagOpkmCl;
    }

    public boolean isFlagVhcl() {
        return flagVhcl;
    }

    public void setFlagVhcl(boolean flagVhcl) {
        this.flagVhcl = flagVhcl;
    }

    public String getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(String codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getCodigoV() {
        return codigoV;
    }

    public void setCodigoV(String codigoV) {
        this.codigoV = codigoV;
    }

    public boolean isFlagOpkmClOp() {
        return flagOpkmClOp;
    }

    public void setFlagOpkmClOp(boolean flagOpkmClOp) {
        this.flagOpkmClOp = flagOpkmClOp;
    }

    public boolean isFlagVhclOp() {
        return flagVhclOp;
    }

    public void setFlagVhclOp(boolean flagVhclOp) {
        this.flagVhclOp = flagVhclOp;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public boolean isUnoAUnoOperador() {
        return unoAUnoOperador;
    }

    public void setUnoAUnoOperador(boolean unoAUnoOperador) {
        this.unoAUnoOperador = unoAUnoOperador;
    }

    public boolean isUnoAUnoVehiculo() {
        return unoAUnoVehiculo;
    }

    public void setUnoAUnoVehiculo(boolean unoAUnoVehiculo) {
        this.unoAUnoVehiculo = unoAUnoVehiculo;
    }

    public int getColorFecha() throws ParseException {
        return MovilidadUtil.fechasIgualMenorMayor(fechaConsulta, MovilidadUtil.fechaHoy(), false);
    }

    public List<PrgTarea> getListPrgTarea() {
        listPrgTarea = prgTareaEJB.findFromAddServices(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        return listPrgTarea;
    }

    public void setListPrgTarea(List<PrgTarea> listPrgTarea) {
        this.listPrgTarea = listPrgTarea;
    }

    public Integer getI_idTarea() {
        return i_idTarea;
    }

    public void setI_idTarea(Integer i_idTarea) {
        this.i_idTarea = i_idTarea;
    }

    public PrgPattern getIdPuntoIni() {
        return idPuntoIni;
    }

    public void setIdPuntoIni(PrgPattern idPuntoIni) {
        this.idPuntoIni = idPuntoIni;
    }

    public PrgPattern getIdPuntoFin() {
        return idPuntoFin;
    }

    public void setIdPuntoFin(PrgPattern idPuntoFin) {
        this.idPuntoFin = idPuntoFin;
    }

    public String getHoraInicio_string() {
        return horaInicio_string;
    }

    public void setHoraInicio_string(String horaInicio_string) {
        this.horaInicio_string = horaInicio_string;
    }

    public String getHoraFin_string() {
        return horaFin_string;
    }

    public void setHoraFin_string(String horaFin_string) {
        this.horaFin_string = horaFin_string;
    }

    public int getI_idRuta() {
        return i_idRuta;
    }

    public void setI_idRuta(int i_idRuta) {
        this.i_idRuta = i_idRuta;
    }

    public List<PrgRoute> getListPrgRoute() {
        listPrgRoute = prgRoute.findActivasByUnidadFuncional(
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        return listPrgRoute;
    }

    public void setListPrgRoute(List<PrgRoute> listPrgRoute) {
        this.listPrgRoute = listPrgRoute;
    }

    public List<PrgPattern> getListPrgPattern() {
        return listPrgPattern;
    }

    public void setListPrgPattern(List<PrgPattern> listPrgPattern) {
        this.listPrgPattern = listPrgPattern;
    }

//    public List<String> getListDate() throws ParseException {
//        return listDate;
//    }
//    
//    public void setListDate(List<String> listDate) {
//        this.listDate = listDate;
//    }
    public boolean isFlagVehiculo() {
        return flagVehiculo;
    }

    public void setFlagVehiculo(boolean flagVehiculo) {
        this.flagVehiculo = flagVehiculo;
    }

    public boolean isFlagOperador() {
        return flagOperador;
    }

    public void setFlagOperador(boolean flagOperador) {
        this.flagOperador = flagOperador;
    }

    public boolean isFlagEliminar() {
        return flagEliminar;
    }

    public void setFlagEliminar(boolean flagEliminar) {
        this.flagEliminar = flagEliminar;
    }

    public EmpleadosJSFManagedBean getEmplJSFMB() {
        return emplJSFMB;
    }

    public void setEmplJSFMB(EmpleadosJSFManagedBean emplJSFMB) {
        this.emplJSFMB = emplJSFMB;
    }

    public List<PrgTc> getListPrgTcGestionVehiculo() {
        return listPrgTcGestionVehiculo;
    }

    public void setListPrgTcGestionVehiculo(List<PrgTc> listPrgTcGestionVehiculo) {
        this.listPrgTcGestionVehiculo = listPrgTcGestionVehiculo;
    }

    public List<ArbolViewPrgTc> getListPrgTcGestionEliminar() {
        return listPrgTcGestionEliminar;
    }

    public void setListPrgTcGestionEliminar(List<ArbolViewPrgTc> listPrgTcGestionEliminar) {
        this.listPrgTcGestionEliminar = listPrgTcGestionEliminar;
    }

    public List<PrgTc> getListPrgTcGestionOperador() {
        return listPrgTcGestionOperador;
    }

    public void setListPrgTcGestionOperador(List<PrgTc> listPrgTcGestionOperador) {
        this.listPrgTcGestionOperador = listPrgTcGestionOperador;
    }

    public int getTipoVacio() {
        return tipoVacio;
    }

    public void setTipoVacio(int tipoVacio) {
        this.tipoVacio = tipoVacio;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public boolean isVacio() {
        return vacio;
    }

    public void setVacio(boolean vacio) {
        this.vacio = vacio;
    }

    public boolean isFlagCambioHora() {
        return flagCambioHora;
    }

    public void setFlagCambioHora(boolean flagCambioHora) {
        this.flagCambioHora = flagCambioHora;
    }

    public List<PrgStopPoint> getListPrgStopPoint() {
        return listPrgStopPoint;
    }

    public void setListPrgStopPoint(List<PrgStopPoint> listPrgStopPoint) {
        this.listPrgStopPoint = listPrgStopPoint;
    }

    public PrgStopPoint getPrgStopPoint() {
        return prgStopPoint;
    }

    public void setPrgStopPoint(PrgStopPoint prgStopPoint) {
        this.prgStopPoint = prgStopPoint;
    }

    public String getHoraFinString() {
        return horaFinString;
    }

    public void setHoraFinString(String horaFinString) {
        this.horaFinString = horaFinString;
    }

    public String getHoraInicioString() {
        return horaInicioString;
    }

    public void setHoraInicioString(String horaInicioString) {
        this.horaInicioString = horaInicioString;
    }

    public String getSerconString() {
        return serconString;
    }

    public void setSerconString(String serconString) {
        this.serconString = serconString;
    }

    public String getServbusString() {
        return servbusString;
    }

    public void setServbusString(String servbusString) {
        this.servbusString = servbusString;
    }

    public boolean isFlagRuta() {
        return flagRuta;
    }

    public void setFlagRuta(boolean flagRuta) {
        this.flagRuta = flagRuta;
    }

    public List<PrgStopPoint> getListPrgStopPointByLinea() {
        return listPrgStopPointByLinea;
    }

    public void setListPrgStopPointByLinea(List<PrgStopPoint> listPrgStopPointByLinea) {
        this.listPrgStopPointByLinea = listPrgStopPointByLinea;
    }

    public String getPunto_inicioString() {
        return punto_inicioString;
    }

    public void setPunto_inicioString(String punto_inicioString) {
        this.punto_inicioString = punto_inicioString;
    }

    public String getPunto_finString() {
        return punto_finString;
    }

    public void setPunto_finString(String punto_finString) {
        this.punto_finString = punto_finString;
    }

    public Vehiculo getVehiculoNewService() {
        return vehiculoNewService;
    }

    public void setVehiculoNewService(Vehiculo vehiculoNewService) {
        this.vehiculoNewService = vehiculoNewService;
    }

    public Integer getI_idPuntoIniInt() {
        return i_idPuntoIniInt;
    }

    public void setI_idPuntoIniInt(Integer i_idPuntoIniInt) {
        this.i_idPuntoIniInt = i_idPuntoIniInt;
    }

    public Integer getI_idPuntoFinInt() {
        return i_idPuntoFinInt;
    }

    public void setI_idPuntoFinInt(Integer i_idPuntoFinInt) {
        this.i_idPuntoFinInt = i_idPuntoFinInt;
    }

    public String getObservacionesNewService() {
        return observacionesNewService;
    }

    public void setObservacionesNewService(String observacionesNewService) {
        this.observacionesNewService = observacionesNewService;
    }

    public PrgTarea getI_idTareaNewService() {
        return i_idTareaNewService;
    }

    public void setI_idTareaNewService(PrgTarea i_idTareaNewService) {
        this.i_idTareaNewService = i_idTareaNewService;
    }

    public boolean isFlagFromService() {
        return flagFromService;
    }

    public void setFlagFromService(boolean flagFromService) {
        this.flagFromService = flagFromService;
    }

    public void setListNovedadTDet(List<NovedadTipoDetalles> listNovedadTDet) {
        this.listNovedadTDet = listNovedadTDet;
    }

    public List<NovedadTipoDetalles> getListNovedadTDet() {
        return listNovedadTDet;
    }

    public List<PrgTcResponsable> getLstResponsable() {
        return lstResponsable;
    }

    public int getI_codResponsable() {
        return i_codResponsable;
    }

    public void setI_codResponsable(int i_codResponsable) {
        this.i_codResponsable = i_codResponsable;
    }

    public PrgStopPoint getI_idPuntoIniStopPoint() {
        return i_idPuntoIniStopPoint;
    }

    public void setI_idPuntoIniStopPoint(PrgStopPoint i_idPuntoIniStopPoint) {
        this.i_idPuntoIniStopPoint = i_idPuntoIniStopPoint;
    }

    public PrgStopPoint getI_idPuntoFinStopPoint() {
        return i_idPuntoFinStopPoint;
    }

    public void setI_idPuntoFinStopPoint(PrgStopPoint i_idPuntoFinStopPoint) {
        this.i_idPuntoFinStopPoint = i_idPuntoFinStopPoint;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTabla() {
        return tabla;
    }

    public void setTabla(int tabla) {
        this.tabla = tabla;
    }

    public String getObservacionesPrgTc() {
        return observacionesPrgTc;
    }

    public void setObservacionesPrgTc(String observacionesPrgTc) {
        this.observacionesPrgTc = observacionesPrgTc;
    }

    public PrgTc getPrgTcSinOperador() {
        return prgTcSinOperador;
    }

    public void setPrgTcSinOperador(PrgTc prgTcSinOperador) {
        this.prgTcSinOperador = prgTcSinOperador;
    }

    public boolean isFlagSinOperador() {
        return flagSinOperador;
    }

    public void setFlagSinOperador(boolean flagSinOperador) {
        this.flagSinOperador = flagSinOperador;
    }

    public TreeNode getRootSinOperador() {
        return rootSinOperador;
    }

    public void setRootSinOperador(TreeNode rootSinOperador) {
        this.rootSinOperador = rootSinOperador;
    }

    public String getCodTMPrgTc() {
        return codTMPrgTc;
    }

    public void setCodTMPrgTc(String codTMPrgTc) {
        this.codTMPrgTc = codTMPrgTc;
    }

    public boolean isFlagRol() {
        return flagRol;
    }

    public void setFlagRol(boolean flagRol) {
        this.flagRol = flagRol;
    }

    public boolean isFlagViewOption() {
        return flagViewOption;
    }

    public void setFlagViewOption(boolean flagViewOption) {
        this.flagViewOption = flagViewOption;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public PrgTc getPrgTcOpDispo() {
        return prgTcOpDispo;
    }

    public void setPrgTcOpDispo(PrgTc prgTcOpDispo) {
        this.prgTcOpDispo = prgTcOpDispo;
    }

    public boolean isFlagGuardarTodo() {
        return flagGuardarTodo;
    }

    public void setFlagGuardarTodo(boolean flagGuardarTodo) {
        this.flagGuardarTodo = flagGuardarTodo;
    }

    public NovedadTipoDetalles getI_tipoNovedaDetObj() {
        return i_tipoNovedaDetObj;
    }

    public void setI_tipoNovedaDetObj(NovedadTipoDetalles i_tipoNovedaDetObj) {
        this.i_tipoNovedaDetObj = i_tipoNovedaDetObj;
    }

    public String getViewCodVehiculo() {
        return viewCodVehiculo;
    }

    public void setViewCodVehiculo(String viewCodVehiculo) {
        this.viewCodVehiculo = viewCodVehiculo;
    }

    public String getViewPlaca() {
        return viewPlaca;
    }

    public void setViewPlaca(String viewPlaca) {
        this.viewPlaca = viewPlaca;
    }

    public List<PrgTc> getListPrgTcFilter() {
        return listPrgTcFilter;
    }

    public void setListPrgTcFilter(List<PrgTc> listPrgTcFilter) {
        this.listPrgTcFilter = listPrgTcFilter;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public PrgStopPoint getEntradaStopPoint() {
        return entradaStopPoint;
    }

    public void setEntradaStopPoint(PrgStopPoint entradaStopPoint) {
        this.entradaStopPoint = entradaStopPoint;
    }

    public PrgStopPoint getSalidaStopPoint() {
        return salidaStopPoint;
    }

    public void setSalidaStopPoint(PrgStopPoint salidaStopPoint) {
        this.salidaStopPoint = salidaStopPoint;
    }

    public String getCodigoTmVac() {
        return codigoTmVac;
    }

    public void setCodigoTmVac(String codigoTmVac) {
        this.codigoTmVac = codigoTmVac;
    }

    public boolean isPropia() {
        return propia;
    }

    public void setPropia(boolean propia) {
        this.propia = propia;
    }

    public boolean isFlagPropia() {
        return flagPropia;
    }

    public void setFlagPropia(boolean flagPropia) {
        this.flagPropia = flagPropia;
    }

    public boolean isFlagConciliado() {
        return flagConciliado;
    }

    public void setFlagConciliado(boolean flagConciliado) {
        this.flagConciliado = flagConciliado;
    }

    public List<Vehiculo> getListaDisponiblesV() {
        return listaDisponiblesV;
    }

    public void setListaDisponiblesV(List<Vehiculo> listaDisponiblesV) {
        this.listaDisponiblesV = listaDisponiblesV;
    }

    public Vehiculo getVehiculoDisponible() {
        return VehiculoDisponible;
    }

    public void setVehiculoDisponible(Vehiculo VehiculoDisponible) {
        this.VehiculoDisponible = VehiculoDisponible;
    }

    public String getDestinoVacio() {
        return destinoVacio;
    }

    public void setDestinoVacio(String destinoVacio) {
        this.destinoVacio = destinoVacio;
    }

    public boolean isFlagAsignarOperador() {
        return flagAsignarOperador;
    }

    public void setFlagAsignarOperador(boolean flagAsignarOperador) {
        this.flagAsignarOperador = flagAsignarOperador;
    }

    public boolean isFlagAsignarVehiculo() {
        return flagAsignarVehiculo;
    }

    public boolean isFlagEliminarXOperador() {
        return flagEliminarXOperador;
    }

    public boolean isFlagEliminarXVehiculo() {
        return flagEliminarXVehiculo;
    }

    public Empleado getEmplPrgTcSinOperador() {
        return emplPrgTcSinOperador;
    }

    public void setEmplPrgTcSinOperador(Empleado emplPrgTcSinOperador) {
        this.emplPrgTcSinOperador = emplPrgTcSinOperador;
    }

    public boolean isEliminarFromCabecera() {
        return eliminarFromCabecera;
    }

    public void setEliminarFromCabecera(boolean eliminarFromCabecera) {
        this.eliminarFromCabecera = eliminarFromCabecera;
    }

    public int getI_idClasificacion() {
        return i_idClasificacion;
    }

    public void setI_idClasificacion(int i_idClasificacion) {
        this.i_idClasificacion = i_idClasificacion;
    }

    public List<PrgClasificacionMotivo> getLstClasificacion() {
        return lstClasificacion;
    }

    public void setLstClasificacion(List<PrgClasificacionMotivo> lstClasificacion) {
        this.lstClasificacion = lstClasificacion;
    }

    public boolean isB_afectaPrgTc() {
        return b_afectaPrgTc;
    }

    public void setB_afectaPrgTc(boolean b_afectaPrgTc) {
        this.b_afectaPrgTc = b_afectaPrgTc;
    }

    public boolean isB_isNovAccidente() {
        return b_isNovAccidente;
    }

    public void setB_isNovAccidente(boolean b_isNovAccidente) {
        this.b_isNovAccidente = b_isNovAccidente;
    }

    public List<PrgTc> getListaSinBus() {
        return listaSinBus;
    }

    public void setListaSinBus(List<PrgTc> listaSinBus) {
        this.listaSinBus = listaSinBus;
    }

    public List<PrgTc> getListaSinOperador() {
        return listaSinOperador;
    }

    public void setListaSinOperador(List<PrgTc> listaSinOperador) {
        this.listaSinOperador = listaSinOperador;
    }

    public List<PrgTc> getListaDisponibles() {
        return listaDisponibles;
    }

    public void setListaDisponibles(List<PrgTc> listaDisponibles) {
        this.listaDisponibles = listaDisponibles;
    }

    public Integer getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(Integer idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public boolean isInmovilizado() {
        return inmovilizado;
    }

    public void setInmovilizado(boolean inmovilizado) {
        this.inmovilizado = inmovilizado;
    }

    public String getUrlMapGeo() {
        return urlMapGeo;
    }

    public void setUrlMapGeo(String urlMapGeo) {
        this.urlMapGeo = urlMapGeo;
    }

    public boolean isFlagTurnoCerrado() {
        return flagTurnoCerrado;
    }

    public void setFlagTurnoCerrado(boolean flagTurnoCerrado) {
        this.flagTurnoCerrado = flagTurnoCerrado;
    }

    public PrgTc getPrgTcOperadorDisponible() {
        return prgTcOperadorDisponible;
    }

    public void setPrgTcOperadorDisponible(PrgTc prgTcOperadorDisponible) {
        this.prgTcOperadorDisponible = prgTcOperadorDisponible;
    }

    public PrgTc getPrgTcForGetOperador() {
        return prgTcForGetOperador;
    }

    public void setPrgTcForGetOperador(PrgTc prgTcForGetOperador) {
        this.prgTcForGetOperador = prgTcForGetOperador;
    }

    public boolean isFlagAddServicios() {
        return flagAddServicios;
    }

    public void setFlagAddServicios(boolean flagAddServicios) {
        this.flagAddServicios = flagAddServicios;
    }

    public boolean isFlagGestionServicios() {
        return flagGestionServicios;
    }

    public void setFlagGestionServicios(boolean flagGestionServicios) {
        this.flagGestionServicios = flagGestionServicios;
    }

    public boolean isFlagViewVerRecorrido() {
        return flagViewVerRecorrido;
    }

    public void setFlagViewVerRecorrido(boolean flagViewVerRecorrido) {
        this.flagViewVerRecorrido = flagViewVerRecorrido;
    }

    public boolean isFlagBotonesGestionServicio() {
        return flagBotonesGestionServicio;
    }

    public void setFlagBotonesGestionServicio(boolean flagBotonesGestionServicio) {
        this.flagBotonesGestionServicio = flagBotonesGestionServicio;
    }

    public List<NovedadTipoDetalles> getLstDetallesAccesoRapido() {
        return lstDetallesAccesoRapido;
    }

    public void setLstDetallesAccesoRapido(List<NovedadTipoDetalles> lstDetallesAccesoRapido) {
        this.lstDetallesAccesoRapido = lstDetallesAccesoRapido;
    }

}
