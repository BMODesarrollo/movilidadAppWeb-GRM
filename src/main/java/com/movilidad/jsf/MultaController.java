package com.movilidad.jsf;

import com.movilidad.ejb.MultaClasificacionFacadeLocal;
import com.movilidad.ejb.MultaFacadeLocal;
import com.movilidad.ejb.MultaReportadoPorFacadeLocal;
import com.movilidad.ejb.MultaTipoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.OperacionPatiosFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Multa;
import com.movilidad.model.MultaClasificacion;
import com.movilidad.model.MultaDocumentos;
import com.movilidad.model.MultaReportadoPor;
import com.movilidad.model.MultaSeguimiento;
import com.movilidad.model.MultaTipo;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.OperacionPatios;
import com.movilidad.model.PrgTc;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.JsfUtil.PersistAction;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("multaController")
@ViewScoped
//@SessionScoped
public class MultaController implements Serializable {

    @EJB
    private MultaFacadeLocal multaEJB;
    @EJB
    private MultaReportadoPorFacadeLocal multaReportadoPorFacade;
    @EJB
    private MultaTipoFacadeLocal multaTipoFacadeLocal;
    @EJB
    private MultaClasificacionFacadeLocal multaClasificacionFacadeLocal;
    @EJB
    private OperacionPatiosFacadeLocal operacionPatiosFacadeLocal;
    @EJB
    private PrgTcFacadeLocal prgTcEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private NovedadUtilJSFManagedBean selectedUtil;

    @Inject
    private PrgTcJSFManagedBean prgTcJSFMB;

    @Inject
    private ValidarFinOperacionBean validarFinOperacionBean;
    UserExtended user;

    private List<Multa> items;

    private List<MultaReportadoPor> listaMultaRP;
    private List<MultaTipo> listaMultaTipo;
    private List<Empleado> lstEmpleados;
    private List<MultaClasificacion> listaMultaC;
    private List<OperacionPatios> listOperacionPatio;
    private List<PrgTc> lstPrgTcs;

    //---------
    private Multa selected;
    private Empleado empleado;
    private PrgTc prgTc;
    private MultaSeguimiento multaSeguimiento;
    private MultaDocumentos multaDocumentos;
    //---------
    private String c_coVehiculo = "";
    private String c_coMultaRP = "";
    private Integer i_coEmpleado = 0;
    private int i_idMultaTipo;
    private int i_idMultaClasidicacion;
    private int i_idOperacionPatio;
    private boolean b_procede;
    private int i_idMulta;
    private Date fechaInicio;
    private Date fechaFin;

    private boolean modulo;
    private boolean viewCreateMultaPP;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        items = new ArrayList<>();
        if (!MovilidadUtil.validarUrl("panelPrincipal")) {
            fechaInicio = MovilidadUtil.fechaCompletaHoy();
            fechaFin = MovilidadUtil.fechaCompletaHoy();
            items = multaEJB.findByDateRange(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        } else {
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get("viewCreateMultaPP") != null) {
                viewCreateMultaPP = SingletonConfigEmpresa.getMapConfiMapEmpresa().get("viewCreateMultaPP").equals("1");
            }
        }
        validarRol();
    }

    /**
     * Valida si el operador es tecnico de control.
     *
     */
    public void validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            validarFinOperacionBean.setIsTecControl(auth.getAuthority().equals("ROLE_TC"));
        }
    }

    public void prepareListPrgTc() {
        this.prgTc = new PrgTc();
    }

    public void prepareListEmpleados() {
        this.empleado = new Empleado();
    }

    public void onEmpleadoChosen(SelectEvent event) {
        setEmpleado((Empleado) event.getObject());
    }

    public void onRowEmpleadoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
        }
    }

    public void onPrgTcChosen(SelectEvent event) {
        setPrgTc((PrgTc) event.getObject());
    }

    public void onRowPrgTcClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof PrgTc) {
            setPrgTc((PrgTc) event.getObject());
        }
    }

    public void prepareCreate(boolean mdlo, Date fecha) {
        if (fecha != null && validarFinOperacionBean.validarDiaBloqueado(fecha,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional())) {
            return;
        }
        setModulo(mdlo);
        reset();
        prgTc = new PrgTc();
        empleado = new Empleado();
        listaMultaTipo = multaTipoFacadeLocal.findEstaRegis();
        listOperacionPatio = operacionPatiosFacadeLocal.findAllActivos();
        MovilidadUtil.openModal("MultaCreateDialog");
    }

    public void prepareCreatePrgTc(boolean mdlo) throws ParseException {
        setModulo(mdlo);
        reset();
        if (prgTcJSFMB.getPrgTc() != null) {
            if (prgTcJSFMB.getPrgTc().getIdEmpleado() != null) {
                prgTc = prgTcJSFMB.getPrgTc();
                empleado = prgTc.getIdEmpleado();
                selected.setFechaHora(MovilidadUtil.converterToHour(prgTc.getFecha(), prgTc.getTimeOrigin()));
                listaMultaTipo = multaTipoFacadeLocal.findEstaRegis();
                listOperacionPatio = operacionPatiosFacadeLocal.findAllActivos();
                PrimeFaces.current().executeScript("PF('MultaCreateDialog').show()");
            } else {
                MovilidadUtil.addErrorMessage("Acción no valida para este servicio");
            }

        } else {
            MovilidadUtil.addErrorMessage("No ha seleccionado el servicio");
        }
    }

    public void create() {
        persist(PersistAction.CREATE, "Multa se creó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Multa no se creó correctamente");
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Multa se actualizó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Multa no se actualizó correctamente");
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Multa se eliminó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Multa no se eliminó correctamente");
        }
    }

    public void createReportadoPor() {
        MultaReportadoPor mrp = new MultaReportadoPor();
        mrp.setNombres(c_coMultaRP);
        mrp.setUsername(user.getUsername());
        mrp.setCreado(new Date());
        mrp.setEstadoReg(0);
        multaReportadoPorFacade.create(mrp);
        c_coMultaRP = "";
        PrimeFaces.current().executeScript("PF('MultaReportadoPorCreateDialog').hide();");
        MovilidadUtil.addSuccessMessage("Registro creado exitosamente");
        listarMRP();
    }

    public List<Multa> getItems() {
        return items;
    }

    public void cargarMultaReportadoPor() {
        c_coMultaRP = getSelected().getIdMultaReportadoPor().getNombres();
    }

    public int cargarIdMulta() {
        try {
            return selected.getIdMulta();
        } catch (Exception e) {
            return 0;
        }
    }

    public void cargarCM() {
        listaMultaC = multaClasificacionFacadeLocal.obtenerTM(getI_idMultaTipo());
    }

    public void resetList() {
        listaMultaC = null;
    }

    public void listarMRP() {
        listaMultaRP = new ArrayList<>();
        c_coMultaRP = "";
        listaMultaRP = multaReportadoPorFacade.findAll();
    }

    public void reset() {
        selected = new Multa();
        prgTc = null;
        empleado = null;
        c_coMultaRP = "";
        i_idMultaTipo = 0;
        i_idMultaClasidicacion = 0;
        i_idOperacionPatio = 0;
        b_procede = false;
        items = multaEJB.findByDateRange(Util.toDate(Util.dateFormat(fechaInicio)),
                Util.toDate(Util.dateFormat(fechaFin)),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    void cargar() {
        MultaTipo multaTipo = new MultaTipo();
        multaTipo.setIdMultaTipo(i_idMultaTipo);
        if (prgTc.getIdPrgTc() != null) {
            getSelected().setIdEmpleado(prgTc.getIdEmpleado());
            getSelected().setIdVehiculo(prgTc.getIdVehiculo());
            getSelected().setIdPrgTc(prgTc);
        } else {
            getSelected().setIdPrgTc(null);
            getSelected().setIdEmpleado(empleado);
        }
        getSelected().setIdMultaClasificacion((MultaClasificacion) multaClasificacionFacadeLocal.find(i_idMultaClasidicacion));
        getSelected().setIdMultaTipo(multaTipo);
        getSelected().setIdPatio((OperacionPatios) operacionPatiosFacadeLocal.find(i_idOperacionPatio));
    }

    public void cargarDatos() {
        lstPrgTcs = new ArrayList<>();
        lstPrgTcs = prgTcEjb.findByFecha(Util.toDate(Util.dateFormat(selected.getFechaHora())),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public String validarFecha() {
        Date hoy = new Date();
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(hoy);
    }

    public void cargarEditar() {
        setModulo(true);
        if (getSelected().getIdPrgTc() != null) {
            prgTc = getSelected().getIdPrgTc();
        } else {
            prgTc = new PrgTc();
        }
        empleado = getSelected().getIdEmpleado();
        i_idMultaTipo = selected.getIdMultaTipo().getIdMultaTipo();
        i_idMultaClasidicacion = selected.getIdMultaClasificacion().getIdMultaClasificacion();
        i_idOperacionPatio = selected.getIdPatio() != null ? selected.getIdPatio().getIdOperacionPatios() : 0;
        b_procede = selected.getProcede() == 1;
        cargarCM();
        listarMRP();
        cargarDatos();
        c_coMultaRP = selected.getIdMultaReportadoPor().getNombres();
        listaMultaTipo = multaTipoFacadeLocal.findEstaRegis();
        listOperacionPatio = operacionPatiosFacadeLocal.findAllActivos();
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_MULTAS_TEMPLATE);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    private void notificar() {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("fecha", Util.dateFormat(selected.getFechaHora()));
        mailProperties.put("clasificacion", selected.getIdMultaClasificacion().getDescripcion() + " (" + selected.getIdMultaClasificacion().getCodigo() + ")");
        mailProperties.put("operador", selected.getIdEmpleado() != null ? selected.getIdEmpleado().getCodigoTm() + " - " + selected.getIdEmpleado().getNombres() + " " + selected.getIdEmpleado().getApellidos() : "");
        mailProperties.put("vehiculo", selected.getIdVehiculo() != null ? selected.getIdVehiculo().getCodigo() : "");
        if (selected.getIdPrgTc() != null) {
            mailProperties.put("servicio", selected.getIdPrgTc().getIdRuta() != null ? selected.getIdPrgTc().getIdRuta().getName() : selected.getIdPrgTc().getIdTaskType().getTarea());
            mailProperties.put("tabla", selected.getIdPrgTc() != null ? selected.getIdPrgTc().getTabla() : "");
        } else {
            mailProperties.put("servicio", "No Aplica");
            mailProperties.put("tabla", "No Aplica");
        }
        mailProperties.put("descripcion", selected.getDescripcion());
        mailProperties.put("patio", selected.getIdPatio() != null ? selected.getIdPatio().getNombrePatio() : "");
        mailProperties.put("reportada_por", selected.getIdMultaReportadoPor().getNombres());
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(selected.getCreado()));
        String subject = "Se ha registrado una multa";
        String destinatarios;

        /*Busqueda Operador Máster*/
        String correoMaster = "";
        if (selected.getIdEmpleado().getPmGrupoDetalleList().size() == 1) {
            correoMaster = selected.getIdEmpleado().getPmGrupoDetalleList().get(0).getIdGrupo().getIdEmpleado().getEmailCorporativo();
        }
        NotificacionProcesos notificacionProcesos = notificacionProcesosEjb.find(Util.ID_MULTAS_NOTI_PROC);
        destinatarios = selected.getIdEmpleado() != null ? correoMaster + "," + selected.getIdEmpleado().getEmailCorporativo() : "";
        if (notificacionProcesos != null) {
            if (destinatarios != null) {
                destinatarios = destinatarios + "," + notificacionProcesos.getEmails();
            } else {
                destinatarios = notificacionProcesos.getEmails();
            }

            if (notificacionProcesos.getNotificacionProcesoDetList() != null) {
                String destinatariosByUf = "";

                destinatariosByUf = MovilidadUtil.obtenerCorreosByUf(notificacionProcesos.getNotificacionProcesoDetList(), selected.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                if (destinatariosByUf != null) {
                    destinatarios = destinatarios + "," + destinatariosByUf;
                }
            }

        }
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);

    }

    private void persist(PersistAction persistAction, String successMessage) {
        int idGopUnidadFuncional = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        if (selected != null) {
            switch (persistAction) {
                case CREATE:
                    getSelected().setProcede(b_procede ? 1 : 0);
                    getSelected().setUsername(user.getUsername());
                    getSelected().setModificado(new Date());
                    getSelected().setEstadoReg(0);
                    getSelected().setCreado(new Date());
                    cargar();

                    if (idGopUnidadFuncional > 0) {
                        getSelected().setIdGopUnidadFuncional(new GopUnidadFuncional(idGopUnidadFuncional));
                    } else {
                        getSelected().setIdGopUnidadFuncional(null);
                    }
                    if (getSelected().getIdEmpleado() != null && getSelected().getIdEmpleado().getIdEmpleado() == null) {
                        getSelected().setIdEmpleado(null);
                    }
                    if (getSelected().getIdMultaReportadoPor() != null) {
                        multaEJB.create(selected);
                        selectedUtil.guardarMulta(selected);
                        notificar();
                        idGopUnidadFuncional = unidadFuncionalSessionBean.getIdGopUnidadFuncional();
                        items = multaEJB.findByDateRange(fechaInicio, fechaFin, idGopUnidadFuncional);
                        reset();
                        JsfUtil.addSuccessMessage(successMessage);
                        if (modulo) {
                            PrimeFaces.current().ajax().update(":MultaListForm:datalist");
                            PrimeFaces.current().ajax().update(":MultaListForm:uFSelect");
                        }
                    } else {
                        MovilidadUtil.addErrorMessage("Multa Reportado Por es requerido");
                    }
                    break;
                case DELETE:
                    getSelected().setModificado(new Date());
                    getSelected().setEstadoReg(1);
                    multaEJB.edit(selected);
                    JsfUtil.addSuccessMessage(successMessage);
                    reset();
                    break;
                case UPDATE:
                    getSelected().setModificado(new Date());
                    getSelected().setProcede(b_procede ? 1 : 0);
                    getSelected().setUsername(user.getUsername());
                    cargar();
                    if (idGopUnidadFuncional > 0) {
                        getSelected().setIdGopUnidadFuncional(new GopUnidadFuncional(idGopUnidadFuncional));
                    } else {
                        getSelected().setIdGopUnidadFuncional(null);
                    }
                    if (getSelected().getIdEmpleado() != null && getSelected().getIdEmpleado().getIdEmpleado() == null) {
                        getSelected().setIdEmpleado(null);
                    }
                    if (getSelected().getIdMultaReportadoPor() != null) {
                        multaEJB.edit(selected);
                        selectedUtil.actualizarMulta(selected);
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('MultaEditDialog').hide();");
                        idGopUnidadFuncional = unidadFuncionalSessionBean.getIdGopUnidadFuncional();
                        items = multaEJB.findByDateRange(fechaInicio, fechaFin, idGopUnidadFuncional);
                        reset();
                        JsfUtil.addSuccessMessage(successMessage);
                        if (modulo) {
                            PrimeFaces.current().ajax().update(":MultaListForm:datalist");
                            PrimeFaces.current().ajax().update(":MultaListForm:uFSelect");
                        }

                    } else {
                        MovilidadUtil.addErrorMessage("Multa Reportado Por es requerido");
                    }
                    break;
                default:
                    break;
            }

        }
    }

    public void getByDateRange() {
        items = multaEJB.findByDateRange(fechaInicio, fechaFin,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void clearFilters() {
        this.items = this.multaEJB.findByDateRange(fechaInicio, fechaFin,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        PrimeFaces.current().executeScript("PF('dtMultas').clearFilters()");
    }

    // Get y Set
    public Multa getSelected() {
        return selected;
    }

    public void setSelected(Multa selected) {
        this.selected = selected;
    }

    public String getC_coVehiculo() {
        return c_coVehiculo;
    }

    public void setC_coVehiculo(String c_coVehiculo) {
        this.c_coVehiculo = c_coVehiculo;
    }

    public Integer getI_coEmpleado() {
        return i_coEmpleado;
    }

    public void setI_coEmpleado(Integer i_coEmpleado) {
        this.i_coEmpleado = i_coEmpleado;
    }

    public String getC_coMultaRP() {
        return c_coMultaRP;
    }

    public void setC_coMultaRP(String c_coMultaRP) {
        this.c_coMultaRP = c_coMultaRP;
    }

    public int getI_idMultaTipo() {
        return i_idMultaTipo;
    }

    public void setI_idMultaTipo(int i_idMultaTipo) {
        this.i_idMultaTipo = i_idMultaTipo;
    }

    public int getI_idMultaClasidicacion() {
        return i_idMultaClasidicacion;
    }

    public void setI_idMultaClasidicacion(int i_idMultaClasidicacion) {
        this.i_idMultaClasidicacion = i_idMultaClasidicacion;
    }

    public boolean isB_procede() {
        return b_procede;
    }

    public void setB_procede(boolean b_procede) {
        this.b_procede = b_procede;
    }

    public int getI_idOperacionPatio() {
        return i_idOperacionPatio;
    }

    public int getI_idMulta() {
        return i_idMulta;
    }

    public void setI_idMulta(int i_idMulta) {
        this.i_idMulta = i_idMulta;
    }

    public MultaSeguimiento getMultaSeguimiento() {
        return multaSeguimiento;
    }

    public void setMultaSeguimiento(MultaSeguimiento multaSeguimiento) {
        this.multaSeguimiento = multaSeguimiento;
    }

    public void setI_idOperacionPatio(int i_idOperacionPatio) {
        this.i_idOperacionPatio = i_idOperacionPatio;
    }

    public MultaDocumentos getMultaDocumentos() {
        return multaDocumentos;
    }

    public void setMultaDocumentos(MultaDocumentos multaDocumentos) {
        this.multaDocumentos = multaDocumentos;
    }

    public List<MultaReportadoPor> getListaMultaRP() {
        return listaMultaRP;
    }

    public void setListaMultaRP(List<MultaReportadoPor> listaMultaRP) {
        this.listaMultaRP = listaMultaRP;
    }

    public List<MultaTipo> getListaMultaTipo() {
        return listaMultaTipo;
    }

    public void setListaMultaTipo(List<MultaTipo> listaMultaTipo) {
        this.listaMultaTipo = listaMultaTipo;
    }

    public List<MultaClasificacion> getListaMultaC() {
        return listaMultaC;
    }

    public void setListaMultaC(List<MultaClasificacion> listaMultaC) {
        this.listaMultaC = listaMultaC;
    }

    public List<OperacionPatios> getListOperacionPatio() {
        return listOperacionPatio;
    }

    public void setListOperacionPatio(List<OperacionPatios> listOperacionPatio) {
        this.listOperacionPatio = listOperacionPatio;
    }

    public boolean isModulo() {
        return modulo;
    }

    public void setModulo(boolean modulo) {
        this.modulo = modulo;
    }

    public List<PrgTc> getLstPrgTcs() {
        return lstPrgTcs;
    }

    public void setLstPrgTcs(List<PrgTc> lstPrgTcs) {
        this.lstPrgTcs = lstPrgTcs;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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

    public boolean isViewCreateMultaPP() {
        return viewCreateMultaPP;
    }

    public void setViewCreateMultaPP(boolean viewCreateMultaPP) {
        this.viewCreateMultaPP = viewCreateMultaPP;
    }

}
