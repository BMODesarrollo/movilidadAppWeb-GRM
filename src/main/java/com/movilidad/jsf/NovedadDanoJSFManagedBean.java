package com.movilidad.jsf;

import com.movilidad.ejb.AccReincidenciaFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.DanoFlotaComponenteFacadeLocal;
import com.movilidad.ejb.DanoFlotaNovedadComponenteFacadeLocal;
import com.movilidad.ejb.DanoFlotaParamSeveridadFacadeLocal;
import com.movilidad.ejb.DanoFlotaSolucionValorFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.NovedadDanoFacadeLocal;
import com.movilidad.ejb.PrgRouteFacadeLocal;
import com.movilidad.ejb.VehiculoComponenteDanoFacadeLocal;
import com.movilidad.ejb.VehiculoComponenteFacadeLocal;
import com.movilidad.ejb.VehiculoDanoSeveridadFacadeLocal;
import com.movilidad.model.AccReincidencia;
import com.movilidad.model.Accidente;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.DanoFlotaComponente;
import com.movilidad.model.DanoFlotaNovedadComponente;
import com.movilidad.model.DanoFlotaParamSeveridad;
import com.movilidad.model.DanoFlotaSolucionValor;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.NovedadDano;
import com.movilidad.model.PrgRoute;
import com.movilidad.model.VehiculoComponente;
import com.movilidad.model.VehiculoComponenteDano;
import com.movilidad.model.VehiculoDanoSeveridad;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "novedadDanoBean")
@ViewScoped
public class NovedadDanoJSFManagedBean implements Serializable {

    @EJB
    private NovedadDanoFacadeLocal novedadDanoEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private VehiculoComponenteDanoFacadeLocal vehiculoComponenteDanoEjb;
    @EJB
    private VehiculoComponenteFacadeLocal vehiculoComponenteEjb;
    @EJB
    private VehiculoDanoSeveridadFacadeLocal vehiculoDanoSeveridadEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @EJB
    private PrgRouteFacadeLocal prgRouteEjb;
    @EJB
    private DanoFlotaComponenteFacadeLocal danoFlotaComponenteFacadeLocal;
    @Inject
    private NovedadUtilJSFManagedBean novedadUtil;
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private NovedadJSFManagedBean novedaBean;
    @EJB
    private DanoFlotaSolucionValorFacadeLocal danoFlotaSolucionValorFacadeLocal;
    @EJB
    private DanoFlotaNovedadComponenteFacadeLocal danoFlotaNovedadComponenteFacadeLocal;
    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;
    @EJB
    private AccReincidenciaFacadeLocal accReincidenciaFacadeLocal;
    @EJB
    private DanoFlotaParamSeveridadFacadeLocal danoFlotaParamSeveridadFacadeLocal;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private NovedadDano novedadDano;
    private Vehiculo vehiculo;
    private Empleado empleado;
    private VehiculoComponenteDano vehiculoComponenteDano;
    private VehiculoComponente vehiculoComponente;
    private VehiculoDanoSeveridad vehiculoDanoSeveridad;

    private List<NovedadDano> lista;
    private List<NovedadDano> listaV1;
    private List<Vehiculo> lstVehiculos;
    private List<Empleado> lstEmpleados;
    private List<VehiculoComponenteDano> lstVehiculoComponenteDano;
    private List<VehiculoDanoSeveridad> lstVehiculoDanoSeveridad;
    private List<DanoFlotaComponente> danoFlotaComponente;
    private List<DanoFlotaNovedadComponente> listNovedadComponentesAfectados;
    private List<PrgRoute> lstRutas;
    private List<DanoFlotaSolucionValor> lstDanoFlotaSolucionValor;
    private Map<Long, List<DanoFlotaSolucionValor>> solucionesPorComponente;
    private NovedadDano selected;
    private UploadedFile file;
    private List<UploadedFile> archivos;
    private List<String> fotosNovedades;
    private List<DanoFlotaParamSeveridad> lstServeridadCalc;

    private boolean modulo;
    private boolean viewCreateDAnoPP;
    private boolean flagNovedad;
    private Date fechaInicio;
    private Date fechaFin;
    private List<String> selectedPieces;
    private Integer idRutaSelected;
    private Integer solucionSeleccionada;
    private Integer idDanoSolucionSelected;
    private Boolean flagSelectedPiezas;
    private boolean flagVandalismo;
    private boolean flagAccidente;
    List<DanoFlotaComponente> tmpList;
    private Integer editVandalismo;

    private final PrimeFaces current = PrimeFaces.current();

    @Inject
    private ValidarFinOperacionBean validarFinOperacionBean;

    @PostConstruct
    public void init() {
//        lista = new ArrayList<>();
        if (!MovilidadUtil.validarUrl("panelPrincipal")) {
            fechaInicio = MovilidadUtil.fechaCompletaHoy();
            fechaFin = MovilidadUtil.fechaCompletaHoy();
            this.lista = novedadDanoEjb.findByDateRange(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            this.listNovedadComponentesAfectados = danoFlotaNovedadComponenteFacadeLocal.findAll();
            CruzarComponentesListadoP();
        } else {
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get("viewCreateDanoPP") != null) {
                viewCreateDAnoPP = SingletonConfigEmpresa.getMapConfiMapEmpresa().get("viewCreateDanoPP").equals("1");
            }
        }
        this.vehiculo = null;
        this.empleado = null;
        this.vehiculoComponenteDano = null;
        this.vehiculoComponente = null;
        this.vehiculoDanoSeveridad = null;
        this.selected = null;
        this.danoFlotaComponente = null;
//        this.fotosNovedades = new ArrayList<>();

        setTecnicoControl();
        lstRutas = prgRouteEjb.getRutas();
        lstServeridadCalc = danoFlotaParamSeveridadFacadeLocal.getAllActivo();
        flagSelectedPiezas = false;
        flagVandalismo = false;
        flagAccidente = false;
        tmpList = new ArrayList<>();
        editVandalismo = 0;
    }

    public void LoadV1() {
        this.listaV1 = novedadDanoEjb.findByDateRangeV1(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void CruzarComponentesListadoP() {
        String componentes = "";
        for (NovedadDano n : lista) {
            for (DanoFlotaNovedadComponente comp : listNovedadComponentesAfectados) {
                if (Objects.equals(n.getIdNovedadDano(), comp.getNovedadDano().getIdNovedadDano())) {
                    componentes = componentes + "," + comp.getDanoFlotaSolucionValor().getDanoFlotaComponente().getDescripcion();

                }
            }
            if (!componentes.isEmpty()) {
                n.setComponentesAfectados(componentes.substring(1));
                componentes = "";
            }
        }
    }

    public void cargarMapConfigEmpresa() {
        List<ConfigEmpresa> listCe = configEmpresaFacadeLocal.findEstadoReg();
        SingletonConfigEmpresa.setMapConfiMapEmpresa(new HashMap());
        for (ConfigEmpresa ce : listCe) {
            SingletonConfigEmpresa.getMapConfiMapEmpresa().put(ce.getLlave(), ce.getValor());
        }
    }

    /**
     * Valida si el operador es tecnico de control.
     *
     */
    public void setTecnicoControl() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            validarFinOperacionBean.setIsTecControl(auth.getAuthority().equals("ROLE_TC"));
        }
    }

    public void prepareListVehiculos() {
        this.vehiculo = new Vehiculo();
        consultarVehiculos();
    }

    void consultarVehiculos() {
        lstVehiculos = vehiculoEjb.findAllVehiculosByidGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void prepareListEmpleados() {
        lstEmpleados = empleadoEjb.findAllEmpleadosActivosByUnidadFunc(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void prepareListVehiculoCteDano() {
        this.vehiculoComponenteDano = new VehiculoComponenteDano();
    }

    public void prepareListVehiculoCte() {
        this.vehiculoComponente = new VehiculoComponente();
    }

    public void prepareListVehiculoDanoSeveridad() {
        this.vehiculoDanoSeveridad = new VehiculoDanoSeveridad();
    }

    public void onVehiculoChosen(SelectEvent event) {
        setVehiculo((Vehiculo) event.getObject());
    }

    public void onEmpleadoChosen(SelectEvent event) {
        setEmpleado((Empleado) event.getObject());
    }

    public void onVehiculoCteDanoChosen(SelectEvent event) {
        setVehiculoComponenteDano((VehiculoComponenteDano) event.getObject());
    }

    public void onVehiculoCteChosen(SelectEvent event) {
        setVehiculoComponente((VehiculoComponente) event.getObject());
    }

    public void onVehiculoDanoSeveridadChosen(SelectEvent event) {
        setVehiculoDanoSeveridad((VehiculoDanoSeveridad) event.getObject());
    }

    public void onRowDblClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Vehiculo) {
            setVehiculo((Vehiculo) event.getObject());
            if (validarUF()) {
                setVehiculo(null);
            }
        }
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
            if (validarUF()) {
                setEmpleado(null);
            }
        }
        if (event.getObject() instanceof VehiculoComponenteDano) {
            setVehiculoComponenteDano((VehiculoComponenteDano) event.getObject());
        }
        if (event.getObject() instanceof VehiculoComponente) {
            setVehiculoComponente((VehiculoComponente) event.getObject());
        }
        if (event.getObject() instanceof VehiculoDanoSeveridad) {
            setVehiculoDanoSeveridad((VehiculoDanoSeveridad) event.getObject());
        }
    }

    boolean validarUF() {
        if (vehiculo != null && empleado != null) {
            if (vehiculo.getIdGopUnidadFuncional() != null && empleado.getIdGopUnidadFuncional() != null) {
                if (!vehiculo.getIdGopUnidadFuncional().getIdGopUnidadFuncional()
                        .equals(empleado.getIdGopUnidadFuncional().getIdGopUnidadFuncional())) {
                    MovilidadUtil.addErrorMessage("Vehículo y Operador no comparten la misma unidad funcional.");
                    MovilidadUtil.updateComponent("msgs");
                    MovilidadUtil.updateComponent("frmNuevoTipo:messages");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean renderedEdit(String userName) {
        if (userName.equals(user.getUsername())) {
            return true;
        }
        for (GrantedAuthority p : user.getAuthorities()) {
            if ("ROLE_PROFOP".equals(p.getAuthority()) || "ROLE_SEG".equals(p.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    private boolean validarCampos() {

        if (novedadDano.getFecha() == null) {
            MovilidadUtil.updateComponent(":frmNuevoTipo:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha");
            return true;
        }

        if (novedadDano.getDescripcion() == null) {
            MovilidadUtil.addErrorMessage("Debe ingresar una descripción");
            return true;
        }

        if (Util.validarFecha(novedadDano.getFecha())) {
            MovilidadUtil.addAdvertenciaMessage("La Fecha de la novedad de daño debe ser igual o menor al dia de hoy");
            return true;
        }

        if (empleado == null) {
            MovilidadUtil.updateComponent(":frmNuevoTipo:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un operador");
            return true;
        }

        if (vehiculo == null) {
            MovilidadUtil.updateComponent(":frmNuevoTipo:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un vehículo");
            return true;
        }

        if (selected == null) {
            if (danoFlotaComponente == null || danoFlotaComponente.isEmpty()) {
                MovilidadUtil.updateComponent(":frmNuevoTipo:messages");
                MovilidadUtil.addErrorMessage("Debe seleccionar componentes para continuar");
                return true;
            }
        }
        return false;
    }

    public void guardar() {

        if (validarCampos()) {
            return;
        }
        String piezas = "";
        double sumaNotificacion=0;
        novedadDano.setIdVehiculo(vehiculo);
        if (!flagVandalismo) {
            novedadDano.setIdVehiculoDanoSeveridad(vehiculoDanoSeveridadEjb.find(ValidacionSeveridad(danoFlotaComponente)));
        } else {
            novedadDano.setIdVehiculoDanoSeveridad(vehiculoDanoSeveridadEjb.find(1)); //VANDALISMO
        }
        if (empleado.getIdEmpleado() != null) {
            novedadDano.setIdEmpleado(empleado);
            novedadDano.setIdGopUnidadFuncional(empleado.getIdGopUnidadFuncional());
        }
        novedadDano.setUsername(user.getUsername());
        novedadDano.setCreado(new Date());
        novedadDano.setVersion(2);
        if (idRutaSelected != null) {
            novedadDano.setIdPrgRoute(prgRouteEjb.find(idRutaSelected));
        }
        this.novedadDanoEjb.create(novedadDano);

        if (!archivos.isEmpty()) {
            String path_documento = " ";
            for (UploadedFile f : archivos) {
                path_documento = Util.saveFile(f, novedadDano.getIdNovedadDano(), "danos");
            }
            novedadDano.setPathFotos(path_documento);
            this.novedadDanoEjb.edit(novedadDano);
            archivos.clear();
        }

        for (DanoFlotaComponente obj : danoFlotaComponente) {
            DanoFlotaNovedadComponente objNovedadComponente = new DanoFlotaNovedadComponente();
            objNovedadComponente.setCreado(new Date());
            objNovedadComponente.setDanoFlotaSolucionValor(obj.getDanoFlotaSolucionValor());
            objNovedadComponente.setEstadoReg(0);
            objNovedadComponente.setNovedadDano(novedadDano);
            objNovedadComponente.setUsername(user.getUsername());
            piezas = piezas + "," + obj.getDescripcion();
            try {
                sumaNotificacion=sumaNotificacion + obj.getDanoFlotaSolucionValor().getCosto();
            } catch (Exception e) {
            }
            
            danoFlotaNovedadComponenteFacadeLocal.create(objNovedadComponente);
        }

        notificar(piezas,sumaNotificacion);
        if (novedadDano.getIdVehiculoDanoSeveridad().getIdVehiculoDanoSeveridad() != 1) {
            alertaReincidenciaNovedadDano(novedadDano, piezas,sumaNotificacion);
        }

        novedadUtil.guardarNovedadDano(novedadDano);
        //getByDateRange();
        piezas = "";
        flagSelectedPiezas = false;
        if (modulo) {
            nuevo(modulo, MovilidadUtil.fechaHoy());
            MovilidadUtil.hideModal("mtipo");
            MovilidadUtil.addSuccessMessage("Novedad de daño registrada éxitosamente.");
            init();
            MovilidadUtil.clearFilter("dtNovedades");
            MovilidadUtil.updateComponent("frmPrincipalNvdDano");
            MovilidadUtil.updateComponent("frmPrincipalNvdDano:tbl_danos");
        } else {
            nuevo(false, null);
            MovilidadUtil.hideModal("mtipo");
        }
    }

    public void actualizar() {

        if (validarCampos()) {
            return;
        }

        if (Util.validarFecha(novedadDano.getFecha())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Info", "La Fecha de la novedad de daño debe ser igual o menor al dia de hoy");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }

        if (!flagVandalismo) {
            novedadDano.setIdVehiculoDanoSeveridad(vehiculoDanoSeveridadEjb.find(editVandalismo));
        } else {
            novedadDano.setIdVehiculoDanoSeveridad(vehiculoDanoSeveridadEjb.find(1)); //VANDALISMO
        }

        novedadDano.setIdVehiculo(vehiculo);
        if (empleado.getIdEmpleado() != null) {
            novedadDano.setIdEmpleado(empleado);
        }

        novedadDano.setUsername(user.getUsername());
        novedadDano.setIdPrgRoute(prgRouteEjb.find(idRutaSelected));
        this.novedadDanoEjb.edit(novedadDano);

        if (!archivos.isEmpty()) {
            String path_documento = " ";
            for (UploadedFile f : archivos) {
                path_documento = Util.saveFile(f, novedadDano.getIdNovedadDano(), "danos");
            }
            novedadDano.setPathFotos(path_documento);
            this.novedadDanoEjb.edit(novedadDano);
            archivos.clear();
        }
        novedadUtil.actualizarNovedadDano(novedadDano);
        getByDateRange();
        MovilidadUtil.hideModal("mtipo");
        init();
        MovilidadUtil.clearFilter("dtNovedades");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Novedad de daño actualizada éxitosamente."));
        MovilidadUtil.updateComponent("frmPrincipalNvdDano:tbl_danos");
        MovilidadUtil.updateComponent("frmDanoB");
    }

    public void editar() {
        this.novedadDano = this.selected;
        this.vehiculo = novedadDano.getIdVehiculo();
        this.empleado = novedadDano.getIdEmpleado();
        this.vehiculoComponenteDano = novedadDano.getIdVehiculoComponenteDano();
        this.vehiculoComponente = novedadDano.getIdVehiculoComponente();
        this.vehiculoDanoSeveridad = novedadDano.getIdVehiculoDanoSeveridad();
        this.archivos = new ArrayList<>();
        this.idRutaSelected = novedadDano.getIdPrgRoute() == null ? 0: novedadDano.getIdPrgRoute().getIdPrgRoute();
        this.flagVandalismo = novedadDano.getIdVehiculoDanoSeveridad().getIdVehiculoDanoSeveridad() == 1 ? true : false;
        this.danoFlotaComponente = new ArrayList<>();
        this.editVandalismo = novedadDano.getIdVehiculoDanoSeveridad().getIdVehiculoDanoSeveridad();
        LoadComponentsEdit();
    }

    public StreamedContent getImagenDinamica() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selected != null) {
            String nombre_imagen;
            String path = selected.getPathFotos();
            if (path != null) {
                if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                    return new DefaultStreamedContent();
                } else {
                    nombre_imagen = context.getExternalContext().getRequestParameterMap().get("nombre_imagen");
                    try {

                        return Util.mostrarImagen(nombre_imagen, path);
                    } catch (Exception e) {
//                        System.out.println(e.getMessage());

                    }
                }
            }
        }
        return new DefaultStreamedContent();
    }

    public void obtenerFotosNovedad() throws IOException {
        if (fotosNovedades != null) {
            this.fotosNovedades.clear();
        } else {
            fotosNovedades = new ArrayList<>();
        }
        List<String> lstNombresImg = Util.getFileList(selected.getIdNovedadDano(), "danos");

        if (lstNombresImg != null) {
            for (String f : lstNombresImg) {
                f = selected.getPathFotos() + f;
                fotosNovedades.add(f);
            }
        }
        fotoJSFManagedBean.setListFotos(fotosNovedades);
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.novedadDanoEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "El estado de la novedad de daño fue cambiado éxitosamente."));
    }

    public void nuevo(boolean mdlo, Date fecha) {
        if (fecha != null && validarFinOperacionBean.validarDiaBloqueado(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional())) {
            return;
        }
        setModulo(mdlo);
        this.novedadDano = new NovedadDano();
        this.novedadDano.setFecha(MovilidadUtil.fechaHoy());
        this.vehiculo = null;
        this.empleado = null;
        this.vehiculoComponenteDano = null;
        this.vehiculoComponente = null;
        this.vehiculoDanoSeveridad = null;
        this.selected = null;
        this.archivos = new ArrayList<>();
        MovilidadUtil.openModal("mtipo");
    }

    public void handleFileUpload(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();

        archivos.add(event.getFile());

        current.executeScript("PF('AddFilesListDanoDialog').hide()");
        current.ajax().update(":frmDano:messages");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Fotos agregadas éxitosamente."));
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_DANOS_TEMPLATE);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    private void notificar(String piezas, double notificacionSuma) {
        List<String> lstNombresImg = Util.getFileList(novedadDano.getIdNovedadDano(), "danos");
        List<String> adjuntos = new ArrayList<>();
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("fecha", Util.dateFormat(novedadDano.getFecha()));
        mailProperties.put("operador", novedadDano.getIdEmpleado() != null ? novedadDano.getIdEmpleado().getCodigoTm() + " - " + novedadDano.getIdEmpleado().getNombres() + " " + novedadDano.getIdEmpleado().getApellidos() : "");
        mailProperties.put("vehiculo", novedadDano.getIdVehiculo() != null ? novedadDano.getIdVehiculo().getCodigo() : "");
        mailProperties.put("componente", piezas.substring(1));
        mailProperties.put("valor", notificacionSuma);
        mailProperties.put("severidad", novedadDano.getIdVehiculoDanoSeveridad().getNombre());
        mailProperties.put("generada", Util.dateTimeFormat(novedadDano.getCreado()));
        mailProperties.put("observaciones", novedadDano.getDescripcion());
        String subject = "Novedad de daño";
        String destinatarios;
        /*Busqueda Operador Máster*/
        String correoMaster = "";
        if (novedadDano.getIdEmpleado().getPmGrupoDetalleList().size() == 1) {
            correoMaster = novedadDano.getIdEmpleado().getPmGrupoDetalleList().get(0).getIdGrupo().getIdEmpleado().getEmailCorporativo();
//            System.out.println("máster: " + correoMaster);
        }
        NotificacionProcesos notificacionProcesos = notificacionProcesosEjb.findByCodigo(Util.ID_DANOS_NOTI_PROC);
        destinatarios = novedadDano.getIdEmpleado() != null ? correoMaster + "," + novedadDano.getIdEmpleado().getEmailCorporativo() : "";

        if (lstNombresImg != null) {
            for (String f : lstNombresImg) {
                adjuntos.add(novedadDano.getPathFotos() + f);
            }
        }
        if (notificacionProcesos != null) {
            if (destinatarios != null) {
                destinatarios = destinatarios + "," + notificacionProcesos.getEmails();
            } else {
                destinatarios = notificacionProcesos.getEmails();
            }

            if (notificacionProcesos.getNotificacionProcesoDetList() != null) {
                String destinatariosByUf = "";

                destinatariosByUf = MovilidadUtil.obtenerCorreosByUf(notificacionProcesos.getNotificacionProcesoDetList(), novedadDano.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                if (destinatariosByUf != null) {
                    destinatarios = destinatarios + "," + destinatariosByUf;
                }
            }

            SendMails.sendEmail(mapa, mailProperties, subject,
                    "",
                    destinatarios,
                    "Notificaciones RIGEL", adjuntos != null ? adjuntos : null);

        }
    }

    public void getByDateRange() {
        this.lista = novedadDanoEjb.findByDateRange(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        this.listNovedadComponentesAfectados = danoFlotaNovedadComponenteFacadeLocal.findAll();
        CruzarComponentesListadoP();
        MovilidadUtil.updateComponent("frmPrincipalNvdDano:tbl_danos");
    }

    public void clearFilters() {
        fechaInicio = new Date();
        fechaFin = new Date();
        this.lista = this.novedadDanoEjb.findByDateRange(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        PrimeFaces.current().executeScript("PF('dtNovedades').clearFilters()");
    }

    public void validarOperacionCerrada() {
        if (novedadDano.getFecha() != null && validarFinOperacionBean
                .validarDiaBloqueado(novedadDano.getFecha(), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional())) {
            novedadDano.setFecha(MovilidadUtil.fechaHoy());
        }
    }

    /**
     * Valida si el usuario logueado corresponde al área de Seguridad Vial
     *
     * @return true si el usuario tiene rol ROLE_SEG
     */
    public boolean validarRolSeg() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_SEG")) {
                return true;
            }
        }
        return false;
    }

    public void processSelectedPieces() {
        FacesContext context = FacesContext.getCurrentInstance();
        String selectedPiecesJSON = context.getExternalContext().getRequestParameterMap().get("selectedPiecesJSON");
        // Conversión de JSON a lista
        this.selectedPieces = Arrays.asList(selectedPiecesJSON.replace("[", "").replace("]", "").replace("\"", "").split(","));
        System.out.println("Piezas seleccionadas: " + selectedPieces);
        StringBuilder cadenaFormateada = new StringBuilder();
        for (String pieza : selectedPieces) {
            cadenaFormateada.append("'").append(pieza).append("'").append(", ");
        }
        // Eliminar la última coma y espacio
        if (cadenaFormateada.length() > 0) {
            cadenaFormateada.deleteCharAt(cadenaFormateada.length() - 2);
        }
        if (selected != null) {
            tmpList = danoFlotaComponenteFacadeLocal.findPieces(cadenaFormateada.toString(), vehiculo.getIdVehiculoTipo().getIdVehiculoTipo());
            for (DanoFlotaComponente objTemp : tmpList) {
                if (objTemp.getDanoFlotaSolucionValor() == null) {
                    DanoFlotaSolucionValor objValor = new DanoFlotaSolucionValor();
                    objValor.setIdSolucionValor(0);
                    objValor.setCosto(0);
                    objTemp.setDanoFlotaSolucionValor(objValor);
                    danoFlotaComponente.add(objTemp);
                }

            }
        } else {
            danoFlotaComponente = danoFlotaComponenteFacadeLocal.findPieces(cadenaFormateada.toString(), vehiculo.getIdVehiculoTipo().getIdVehiculoTipo());
            for (DanoFlotaComponente obj : danoFlotaComponente) {
                if (obj.getDanoFlotaSolucionValor() == null) {
                    DanoFlotaSolucionValor objValor = new DanoFlotaSolucionValor();
                    objValor.setIdSolucionValor(0);
                    objValor.setCosto(0);
                    obj.setDanoFlotaSolucionValor(objValor);
                }

            }

        }

    }

    public List<DanoFlotaSolucionValor> getSolucionesPorComponente(Long idComponente) {

        if (solucionesPorComponente == null) {
            solucionesPorComponente = new HashMap<>();
        }
        if (!solucionesPorComponente.containsKey(idComponente)) {
            // Lógica para cargar las soluciones específicas del componente desde la base de datos
            // Utiliza idComponente para filtrar los resultados
            List<DanoFlotaSolucionValor> soluciones = danoFlotaSolucionValorFacadeLocal.findSolucionesByComponente(idComponente);
            solucionesPorComponente.put(idComponente, soluciones);
        }
        return solucionesPorComponente.get(idComponente);
    }

    public void onSolucionChange(DanoFlotaComponente componente) {
        try {
            if (componente.getDanoFlotaSolucionValor().getIdSolucionValor() == 0) {
                DanoFlotaSolucionValor objValor = new DanoFlotaSolucionValor();
                objValor.setIdSolucionValor(0);
                objValor.setCosto(0);
                componente.setDanoFlotaSolucionValor(objValor);
            } else {
                componente.setDanoFlotaSolucionValor(danoFlotaSolucionValorFacadeLocal.find(componente.getDanoFlotaSolucionValor().getIdSolucionValor()));
            }
        } catch (Exception e) {
        }
    }

    public void preSavePieces() {
        if (danoFlotaComponente == null || danoFlotaComponente.isEmpty()) {
            MovilidadUtil.addErrorMessage("No seleccionó piezas");
            MovilidadUtil.updateComponent("msgs");
        } else {
            for (DanoFlotaComponente obj : danoFlotaComponente) {
                if (obj.getDanoFlotaSolucionValor().getIdSolucionValor() == 0) {
                    MovilidadUtil.addErrorMessage("No todas las piezas tienen una solución seleccionada.");
                    MovilidadUtil.updateComponent("msgs");
                    return;
                }
            }
            flagSelectedPiezas = true;
            cerrarModal();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Piezas preseleccionadas."));
        }
    }

    public Integer findValorComponentes(NovedadDano obj) {
        try {
            return danoFlotaNovedadComponenteFacadeLocal.findValueComponents(obj);
        } catch (Exception e) {
            return 0;
        }
    }

    void alertaReincidenciaNovedadDano(NovedadDano a, String piezas, double sumaNotificacion) {
        try {
            if (a.getIdEmpleado() == null) {
                return;
            }
            AccReincidencia accRein;
            List<AccReincidencia> findAllEstadoReg = accReincidenciaFacadeLocal.findAllEstadoReg();
            if (findAllEstadoReg.isEmpty()) {
                return;
            }
            accRein = findAllEstadoReg.get(0);
            String desde = Util.dateFormat(
                    MovilidadUtil.sumarDias(
                            a.getFecha(),
                            accRein.getDias() * -1));
            String hasta = Util.dateFormat(
                    MovilidadUtil.sumarDias(
                            a.getFecha(),
                            accRein.getDias()));
            List<NovedadDano> listDanoFlotaReincidencia = novedadDanoEjb
                    .findAllByIdEmpleadoAndDates(
                            a.getIdNovedadDano(),
                            a.getIdEmpleado().getIdEmpleado(),
                            desde,
                            hasta, unidadFuncionalSessionBean.getIdGopUnidadFuncional());

            if (listDanoFlotaReincidencia != null && listDanoFlotaReincidencia.isEmpty()) {
                return;
            }
            NotificacionCorreoConf conf = NCCEJB.find(1);
            if (conf == null) {
                return;
            }
            NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(ConstantsUtil.TEMPLATE_REINCIDENCIA_NOVEDAD_DANO);
            if (template == null) {
                return;
            }
            Map mapa = SendMails.getMailParams(conf, template);
            Map mailProperties = new HashMap();
            mailProperties.put("fecha", Util.dateFormat(a.getFecha()));
            mailProperties.put("tipo", a.getIdVehiculoDanoSeveridad().getDescripcion());
            mailProperties.put("operador", a.getIdEmpleado() != null ? a.getIdEmpleado().getNombresApellidos() : "");
            mailProperties.put("reincidencia", listDanoFlotaReincidencia.size() + 1);
            mailProperties.put("rango", accRein.getDias());
            mailProperties.put("piezas", piezas.substring(1));
            mailProperties.put("valor", sumaNotificacion);
            mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
            SendMails.sendEmail(mapa,
                    mailProperties,
                    "Reincidencia daño a flota " + a.getIdEmpleado().getCodigoTm(),
                    "",
                    accRein.getIdNotificacionProceso().getEmails(),
                    "Notificaciones", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validarExistenciaAccidente() {
        if (vehiculo != null && novedadDano.getFecha() != null) {
            List<Accidente> accidenteList = accidenteFacadeLocal.findAllByIdVehiculoAndDate(vehiculo.getIdVehiculo(), novedadDano.getFecha());
            if (accidenteList != null && !accidenteList.isEmpty()) {
                flagAccidente = true;
                MovilidadUtil.addAdvertenciaMessage("Ya existe un accidente para este día y este vehículo en el maestro de accidentalidad. ¡¡¡VERIFIQUE ANTES DE CONTINUAR!!!");
                MovilidadUtil.updateComponent("msgs");
            }
        } else if (vehiculo != null && novedadDano.getFecha() == null) {
            MovilidadUtil.addErrorMessage("Seleccione fecha.");
            MovilidadUtil.updateComponent("msgs");
            MovilidadUtil.hideModal("BusetonDanoVectorDialog");
            MovilidadUtil.hideModal("PadronDanoVectorDialog");
        } else if (vehiculo == null && novedadDano.getFecha() != null) {
            MovilidadUtil.addErrorMessage("Seleccione vehículo.");
            MovilidadUtil.updateComponent("msgs");
            MovilidadUtil.hideModal("BusetonDanoVectorDialog");
            MovilidadUtil.hideModal("PadronDanoVectorDialog");
        } else {
            MovilidadUtil.addErrorMessage("Seleccione vehículo y fecha.");
            MovilidadUtil.updateComponent("msgs");
            MovilidadUtil.hideModal("BusetonDanoVectorDialog");
            MovilidadUtil.hideModal("PadronDanoVectorDialog");
        }
    }

    public void eliminarPieza(DanoFlotaComponente obj) {
        if (obj != null && obj.getDanoFlotaComponenteEdit() != null) {
            danoFlotaNovedadComponenteFacadeLocal.remove(obj.getDanoFlotaComponenteEdit());
            danoFlotaComponente.remove(obj);
        } else {
            danoFlotaComponente.remove(obj);
        }
    }

    public void updateSavePieces() {
        if (danoFlotaComponente == null || danoFlotaComponente.isEmpty()) {
            MovilidadUtil.addErrorMessage("No seleccionó piezas");
            MovilidadUtil.updateComponent("msgs");
        } else {
            for (DanoFlotaComponente obj : danoFlotaComponente) {
                DanoFlotaNovedadComponente objNovedadComponente = new DanoFlotaNovedadComponente();
                if (obj.getDanoFlotaSolucionValor().getIdSolucionValor() == 0) {
                    MovilidadUtil.addErrorMessage("No todas las piezas tienen una solución seleccionada.");
                    MovilidadUtil.updateComponent("msgs");
                    return;
                }
                if (obj.getDanoFlotaComponenteEdit() != null) {
                    objNovedadComponente.setCreado(new Date());
                    objNovedadComponente.setIdNovedadDanoComponente(obj.getDanoFlotaComponenteEdit().getIdNovedadDanoComponente());
                    objNovedadComponente.setNovedadDano(selected);
                    objNovedadComponente.setDanoFlotaSolucionValor(obj.getDanoFlotaSolucionValor());
                    objNovedadComponente.setEstadoReg(0);
                    objNovedadComponente.setUsername(user.getUsername());
                    danoFlotaNovedadComponenteFacadeLocal.edit(objNovedadComponente);
                } else {
                    objNovedadComponente.setCreado(new Date());
                    objNovedadComponente.setDanoFlotaSolucionValor(obj.getDanoFlotaSolucionValor());
                    objNovedadComponente.setEstadoReg(0);
                    objNovedadComponente.setNovedadDano(selected);
                    objNovedadComponente.setUsername(user.getUsername());
                    danoFlotaNovedadComponenteFacadeLocal.create(objNovedadComponente);
                }
            }
            editVandalismo = ValidacionSeveridad(danoFlotaComponente);
            cerrarModal();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Piezas actualizadas."));
        }
    }

    public void cerrarModal() {
        if (vehiculo.getIdVehiculoTipo().getIdVehiculoTipo() == 1) {
            MovilidadUtil.hideModal("BusetonDanoVectorDialog");
        } else {
            MovilidadUtil.hideModal("PadronDanoVectorDialog");
        }
    }

    public void cerrar() {
        danoFlotaComponente = new ArrayList<>();
        init();
        if (!modulo) {
            MovilidadUtil.hideModal("mtipo");
            MovilidadUtil.addErrorMessage("Operación cancelada");
            return;
        }
        MovilidadUtil.addErrorMessage("Operación cancelada");
        MovilidadUtil.updateComponent("msgs");
        MovilidadUtil.updateComponent("frmPrincipalNvdDano");
    }

    public void cerrarEdit() {
        danoFlotaComponente = new ArrayList<>();
        tmpList = new ArrayList<>();
        LoadComponentsEdit();
        MovilidadUtil.addErrorMessage("Operación cancelada");
        MovilidadUtil.updateComponent("msgs");
    }

    public void LoadComponentsEdit() {
        List<DanoFlotaNovedadComponente> listNovedadComponenteEdit = danoFlotaNovedadComponenteFacadeLocal.findSolucionesByNovedad(novedadDano.getIdNovedadDano());
        for (DanoFlotaNovedadComponente obj : listNovedadComponenteEdit) {
            DanoFlotaComponente objBuild = obj.getDanoFlotaSolucionValor().getDanoFlotaComponente();
            objBuild.setDanoFlotaSolucionValor(obj.getDanoFlotaSolucionValor());
            objBuild.setDanoFlotaComponenteEdit(obj);
            danoFlotaComponente.add(objBuild);
        }
    }

    public int ValidacionSeveridad(List<DanoFlotaComponente> danoFlotaComponente) {
        double sumar = 0;
        for (DanoFlotaComponente obj : danoFlotaComponente) {
            DanoFlotaNovedadComponente objNovedadComponente = new DanoFlotaNovedadComponente();
            objNovedadComponente.setCreado(new Date());
            objNovedadComponente.setDanoFlotaSolucionValor(obj.getDanoFlotaSolucionValor());
            objNovedadComponente.setEstadoReg(0);
            objNovedadComponente.setNovedadDano(novedadDano);
            objNovedadComponente.setUsername(user.getUsername());
            sumar = sumar + obj.getDanoFlotaSolucionValor().getCosto();
        }
        DanoFlotaParamSeveridad sev1 = new DanoFlotaParamSeveridad();
        DanoFlotaParamSeveridad sev2 = new DanoFlotaParamSeveridad();
        DanoFlotaParamSeveridad sev3 = new DanoFlotaParamSeveridad();
        for (DanoFlotaParamSeveridad obj : lstServeridadCalc) {
            switch (obj.getNivel()) {
                case 1:
                    sev1 = obj;
                    break;
                case 2:
                    sev2 = obj;
                    break;
                case 3:
                    sev3 = obj;
                    break;
                default:
                    throw new AssertionError();
            }
        }

        if (sumar >= 0 && sumar <= sev1.getValor()) {
            return 2;
        } else if (sumar > sev1.getValor() && sumar <= sev2.getValor()) {
            return 3;
        } else if (sumar > sev2.getValor()) {
            return 4;
        } else {
            return 2;
        }
    }

    public NovedadDanoFacadeLocal getNovedadDanoEjb() {
        return novedadDanoEjb;
    }

    public void setNovedadDanoEjb(NovedadDanoFacadeLocal novedadDanoEjb) {
        this.novedadDanoEjb = novedadDanoEjb;
    }

    public VehiculoDanoSeveridadFacadeLocal getVehiculoDanoSeveridadEjb() {
        return vehiculoDanoSeveridadEjb;
    }

    public void setVehiculoDanoSeveridadEjb(VehiculoDanoSeveridadFacadeLocal vehiculoDanoSeveridadEjb) {
        this.vehiculoDanoSeveridadEjb = vehiculoDanoSeveridadEjb;
    }

    public NovedadDano getNovedadDano() {
        return novedadDano;
    }

    public void setNovedadDano(NovedadDano novedadDano) {
        this.novedadDano = novedadDano;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public VehiculoComponenteDano getVehiculoComponenteDano() {
        return vehiculoComponenteDano;
    }

    public void setVehiculoComponenteDano(VehiculoComponenteDano vehiculoComponenteDano) {
        this.vehiculoComponenteDano = vehiculoComponenteDano;
    }

    public VehiculoDanoSeveridad getVehiculoDanoSeveridad() {
        return vehiculoDanoSeveridad;
    }

    public void setVehiculoDanoSeveridad(VehiculoDanoSeveridad vehiculoDanoSeveridad) {
        this.vehiculoDanoSeveridad = vehiculoDanoSeveridad;
    }

    public List<NovedadDano> getLista() {
        return lista;
    }

    public void setLista(List<NovedadDano> lista) {
        this.lista = lista;
    }

    public List<VehiculoComponenteDano> getLstVehiculoComponenteDano() {
        if (vehiculoComponente != null) {
            lstVehiculoComponenteDano = vehiculoComponenteDanoEjb.findByCteGrupo(vehiculoComponente.getIdVehiculoComponenteZona().getIdVehiculoComponenteGrupo());
        }
        return lstVehiculoComponenteDano;
    }

    public void setLstVehiculoComponenteDano(List<VehiculoComponenteDano> lstVehiculoComponenteDano) {
        this.lstVehiculoComponenteDano = lstVehiculoComponenteDano;
    }

    public List<VehiculoDanoSeveridad> getLstVehiculoDanoSeveridad() {
        if (lstVehiculoDanoSeveridad == null) {
            lstVehiculoDanoSeveridad = vehiculoDanoSeveridadEjb.findAll();
        }
        return lstVehiculoDanoSeveridad;
    }

    public void setLstVehiculoDanoSeveridad(List<VehiculoDanoSeveridad> lstVehiculoDanoSeveridad) {
        this.lstVehiculoDanoSeveridad = lstVehiculoDanoSeveridad;
    }

    public NovedadDano getSelected() {
        return selected;
    }

    public void setSelected(NovedadDano selected) {
        this.selected = selected;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<UploadedFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }

    public List<Vehiculo> getLstVehiculos() {
        return lstVehiculos;
    }

    public void setLstVehiculos(List<Vehiculo> lstVehiculos) {
        this.lstVehiculos = lstVehiculos;
    }

    public List<Empleado> getLstEmpleados() {
        if (lstEmpleados == null) {
//            lstEmpleados = empleadoEjb.findAll();
        }
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public List<String> getFotosNovedades() {
        return fotosNovedades;
    }

    public void setFotosNovedades(List<String> fotosNovedades) {
        this.fotosNovedades = fotosNovedades;
    }

    public NovedadUtilJSFManagedBean getNovedadUtil() {
        return novedadUtil;
    }

    public void setNovedadUtil(NovedadUtilJSFManagedBean novedadUtil) {
        this.novedadUtil = novedadUtil;
    }

    public boolean isModulo() {
        return modulo;
    }

    public void setModulo(boolean modulo) {
        this.modulo = modulo;
    }

    public VehiculoComponente getVehiculoComponente() {
        return vehiculoComponente;
    }

    public void setVehiculoComponente(VehiculoComponente vehiculoComponente) {
        this.vehiculoComponente = vehiculoComponente;
    }

    public List<VehiculoComponente> getLstVehiculoComponente() {
        return vehiculoComponenteEjb.findAll();
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isViewCreateDAnoPP() {
        return viewCreateDAnoPP;
    }

    public void setViewCreateDAnoPP(boolean viewCreateDAnoPP) {
        this.viewCreateDAnoPP = viewCreateDAnoPP;
    }

    public boolean isFlagNovedad() {
        return flagNovedad;
    }

    public void setFlagNovedad(boolean flagNovedad) {
        this.flagNovedad = flagNovedad;
    }

    // Getter y Setter para selectedPieces
    public List<String> getSelectedPieces() {
        return selectedPieces;
    }

    public void setSelectedPieces(List<String> selectedPieces) {
        this.selectedPieces = selectedPieces;
    }

    public Integer getIdRutaSelected() {
        return idRutaSelected;
    }

    public void setIdRutaSelected(Integer idRutaSelected) {
        this.idRutaSelected = idRutaSelected;
    }

    public List<PrgRoute> getLstRutas() {
        return lstRutas;
    }

    public List<DanoFlotaComponente> getDanoFlotaComponente() {
        return danoFlotaComponente;
    }

    public void setDanoFlotaComponente(List<DanoFlotaComponente> danoFlotaComponente) {
        this.danoFlotaComponente = danoFlotaComponente;
    }

    public Integer getSolucionSeleccionada() {
        return solucionSeleccionada;
    }

    public void setSolucionSeleccionada(Integer solucionSeleccionada) {
        this.solucionSeleccionada = solucionSeleccionada;
    }

    public Map<Long, List<DanoFlotaSolucionValor>> getSolucionesPorComponente() {
        return solucionesPorComponente;
    }

    public void setSolucionesPorComponente(Map<Long, List<DanoFlotaSolucionValor>> solucionesPorComponente) {
        this.solucionesPorComponente = solucionesPorComponente;
    }

    public List<DanoFlotaSolucionValor> getLstDanoFlotaSolucionValor() {
        return lstDanoFlotaSolucionValor;
    }

    public void setLstDanoFlotaSolucionValor(List<DanoFlotaSolucionValor> lstDanoFlotaSolucionValor) {
        this.lstDanoFlotaSolucionValor = lstDanoFlotaSolucionValor;
    }

    public Integer getIdDanoSolucionSelected() {
        return idDanoSolucionSelected;
    }

    public void setIdDanoSolucionSelected(Integer idDanoSelected) {
        this.idDanoSolucionSelected = idDanoSelected;
    }

    public Boolean getFlagSelectedPiezas() {
        return flagSelectedPiezas;
    }

    public void setFlagSelectedPiezas(Boolean flagSelectedPiezas) {
        this.flagSelectedPiezas = flagSelectedPiezas;
    }

    public boolean isFlagVandalismo() {
        return flagVandalismo;
    }

    public void setFlagVandalismo(boolean flagVandalismo) {
        this.flagVandalismo = flagVandalismo;
    }

    public List<NovedadDano> getListaV1() {
        return listaV1;
    }

    public void setListaV1(List<NovedadDano> listaV1) {
        this.listaV1 = listaV1;
    }

}
