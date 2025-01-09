package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.RecapacitacionMaestroFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.PrgTc;
import com.movilidad.model.RecapacitacionMaestro;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import com.movilidad.utils.SendMails;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Jeisson Junco
 */
@Named(value = "recapacitacionBean")
@ViewScoped
public class RecapacitacionBean implements Serializable {

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    @Inject
    private ArchivosJSFManagedBean archivosBean;

    @EJB
    private RecapacitacionMaestroFacadeLocal recapacitacionEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;

    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;

    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;

    private List<RecapacitacionMaestro> lstRecapacitacion;
    private List<RecapacitacionMaestro> lstRecapacitacionNuevo;
    private List<RecapacitacionMaestro> lstRecapacitacionAgendaMasiva;
    private List<RecapacitacionMaestro> lstAsistente;
    private List<RecapacitacionMaestro> lstRecapacitacionSelected;
    private List<PrgTc> lstPrgTcRecapacitacion;
    private List<Novedad> lstNovedades;
    private List<Novedad> lstNovedadesSeleccionadas;
    private RecapacitacionMaestro nuevaRecap;
    private List<Empleado> lstCapacitadores;
    private RecapacitacionMaestro selectedRecap;

    private NotificacionProcesos notificacionProceso;

    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaAsistencia;

    private Date fechaInicioAgendaMasiva;
    private Date fechaFinAgendaMasiva;
    private Date fechaCitacionAgendaMasiva;
    private Date fechaCitacionIndividual;

    private Boolean asignarmasivo;

    private Integer idRecapacitacion;
    StreamedContent archivo = new DefaultStreamedContent();
    private List<UploadedFile> archivos;
    private Integer idCapacitador;
    private Integer tipoDocumento;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        fechaInicio = calendar.getTime();
        tipoDocumento = 0;
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        fechaFin = calendar.getTime();
        lstCapacitadores = empleadoEjb.findEmpleadosCapacitadores();
        findMaestro();
    }

    public void findMaestro() {
        lstRecapacitacion = recapacitacionEjb.findRangeRecapacitacion(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (lstRecapacitacion == null || lstRecapacitacion.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("No se encontraron novedades para éste rango de fechas");
        } else {
            for (RecapacitacionMaestro recap : lstRecapacitacion) {
                if (recap.getFechaCitacion() != null) {
                    recap.setFechaCitacionDateF(MovilidadUtil.dateSinHora(recap.getFechaCitacion()));
                }
                if (recap.getFechaEjecucion() != null) {
                    recap.setFechaEjecucionDateF(MovilidadUtil.dateSinHora(recap.getFechaEjecucion()));
                }
            }
        }
    }

    public void findAsistente() {
        archivos = new ArrayList<>();
        lstRecapacitacionSelected = new ArrayList<>();
        lstAsistente = recapacitacionEjb.findRangeRecapacitacionCita(fechaInicio, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (lstRecapacitacion == null || lstRecapacitacion.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("No se encontraron novedades para éste rango de fechas");
        }
    }

    public void findNoProgramadas() throws ParseException {

        if (fechaInicioAgendaMasiva == null || fechaFinAgendaMasiva == null) {
            MovilidadUtil.addAdvertenciaMessage("Las fechas de inicio y fin no pueden estar vacías");
            return;
        }

        lstRecapacitacionAgendaMasiva = recapacitacionEjb.findRangeRecapacitacionNoProgramadas(
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
        );

        lstPrgTcRecapacitacion = recapacitacionEjb.findRangeTareasRecapacitacionProgramadas(
                fechaInicioAgendaMasiva,
                fechaFinAgendaMasiva,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
        );

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Define el formato deseado
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd"); // Define el formato deseado
        for (RecapacitacionMaestro r : lstRecapacitacionAgendaMasiva) {
            if (r.getIdEmpleado() != null && r.getIdEmpleado().getIdEmpleado() != null) {
                for (PrgTc p : lstPrgTcRecapacitacion) {
                    if (p.getIdEmpleado() != null && p.getIdEmpleado().getIdEmpleado() != null) {
                        if (r.getIdEmpleado().getIdEmpleado().equals(p.getIdEmpleado().getIdEmpleado())) {
                            // Obtener la fecha de la novedad y calcular el rango de 30 días
                            Date fechaNovedad = r.getIdNovedad().getFecha();
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(fechaNovedad);
                            cal.add(Calendar.DAY_OF_YEAR, 30); // Añadir 30 días
                            Date fechaLimite = cal.getTime();

                            // Formatear la fecha concatenada de la programación
                            String fechaConcatenada = dateFormat1.format(p.getFecha()) + " " + p.getTimeOrigin();
                            Date fechaCitacion = dateFormat.parse(fechaConcatenada);

                            // Validar si la fecha de citación está entre la fecha de la novedad y 30 días después
                            if (!fechaCitacion.before(fechaNovedad) && !fechaCitacion.after(fechaLimite)) {
                                r.setProgramado(true);
                                r.setFechaCitacion(fechaCitacion);
                            }
                        }
                    }
                }
            }
        }

        // Filtrar la lista para dejar solo los programados
        lstRecapacitacionAgendaMasiva.removeIf(r -> !r.getProgramado());

        if (lstRecapacitacionAgendaMasiva == null || lstRecapacitacionAgendaMasiva.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("No se encontraron novedades para este rango de fechas");
            asignarmasivo = false;
            return;
        } else {
            asignarmasivo = true;
        }
    }

    public void cargarListadoNovedad() {
        lstNovedades = novedadEjb.obtenerNovedadesSinRecapacitacion(fechaInicio, fechaFin, unidadFuncionalSessionBean.getIdGopUnidadFuncional());
    }

    public void preCrear() {
        lstNovedadesSeleccionadas = null;
        lstRecapacitacionNuevo = null;
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar unidad funcional");
            return;
        }
        MovilidadUtil.openModal("wlvRecapMaestro");
        nuevaRecap = new RecapacitacionMaestro();
    }

    public void preCargarAsistencia() {
        lstRecapacitacionSelected = new ArrayList<>();
        lstAsistente = new ArrayList<>();
        fechaAsistencia = new Date();
        MovilidadUtil.openModal("CargarAsistenciaDialog");
    }

    public void preCargarDocs(Integer i) {
        tipoDocumento = i;
        fechaAsistencia = null;
        idCapacitador = 0;
        archivos.clear();
    }

    public void preAgendaMasiva() {
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar unidad funcional");
            return;
        }
        asignarmasivo = false;
        lstRecapacitacionAgendaMasiva = null;
        MovilidadUtil.openModal("ListaAgendaMasivaDlg");
    }

    public void agendar() {

        if (lstRecapacitacionAgendaMasiva != null) {
            for (RecapacitacionMaestro r : lstRecapacitacionAgendaMasiva) {
                recapacitacionEjb.edit(r);
                envioCorreoNotificacion(r);
                MovilidadUtil.addSuccessMessage("Recapacitación de " + r.getIdEmpleado().getCodigoTm() + " agendada");
            }
        } else {
            MovilidadUtil.addErrorMessage("Sin fechas por asignar");
            return;
        }
        findMaestro();
        MovilidadUtil.hideModal("ListaAgendaMasivaDlg");
    }

    public void envioCorreoNotificacion(RecapacitacionMaestro r) {
        try {
            NotificacionCorreoConf conf = NCCEJB.find(1);

            NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(ConstantsUtil.TEMPLATE_NOTIFICACION_RECAP);
            if (template == null) {
                return;
            }

            notificacionProceso = notificacionProcesosEjb.findByCodigo("RECAP");
            String destinatarios = "";

            if (notificacionProceso != null) {
                destinatarios = notificacionProceso.getEmails();

                destinatarios = destinatarios + "," + r.getIdEmpleado().getEmailCorporativo();

                Map mapa = SendMails.getMailParams(conf, template);
                Map mailProperties = new HashMap();
                SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                mailProperties.put("operador", r.getIdEmpleado().getNombresApellidos());
                mailProperties.put("fechaCitacion", formateador.format(r.getFechaCitacion()));
                SendMails.sendEmail(mapa,
                        mailProperties,
                        notificacionProceso.getNombreProceso(),
                        "",
                        destinatarios,
                        "Notificaciones Recapacitación", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean validUserGh() {
        return user.getAuthorities().stream().anyMatch(rol -> {
            return rol.getAuthority().contains("ROLE_GH");
        });
    }

    public boolean validUserSegop() {
        return user.getAuthorities().stream().anyMatch(rol -> {
            return rol.getAuthority().contains("ROLE_LIQ") || rol.getAuthority().contains("ROLE_TC");
        });
    }

    public boolean validUserProfPrg() {
        return user.getAuthorities().stream().anyMatch(rol -> {
            return rol.getAuthority().contains("ROLE_PROFPRG");
        });
    }

    public void prepareListadoNovedades() {
        lstNovedades = null;
        lstRecapacitacionNuevo = null;
        lstNovedadesSeleccionadas = null;
    }

    public void crearListaDetalles() {
        lstRecapacitacionNuevo = new ArrayList<>();
        if (lstNovedadesSeleccionadas == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar al menos una novedad");
            return;
        }

        for (Novedad n : lstNovedadesSeleccionadas) {
            RecapacitacionMaestro r = new RecapacitacionMaestro();
            r.setIdNovedad(n);
            r.setIdEmpleado(n.getIdEmpleado());
            lstRecapacitacionNuevo.add(r);
        }

        lstNovedades = null;
        MovilidadUtil.hideModal("NovedadesListDialog");
    }

    public void crear() {
        if (lstRecapacitacionNuevo == null || lstRecapacitacionNuevo.isEmpty()) {
            MovilidadUtil.addErrorMessage("Debe seleccionar al menos una novedad");
            return;
        }

        try {
            int unidadFuncionalId = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
            if (unidadFuncionalId == 0) {
                MovilidadUtil.addErrorMessage("Debe seleccionar unidad funcional");
                return;
            }

            GopUnidadFuncional unidadFuncional = new GopUnidadFuncional(unidadFuncionalId);

            lstRecapacitacionNuevo.forEach(r -> {
                RecapacitacionMaestro novedad = recapacitacionEjb.findNovedad(r.getIdNovedad().getIdNovedad());

                if (novedad != null) {
                    MovilidadUtil.addErrorMessage("La novedad de " + novedad.getIdEmpleado().getCodigoTm() + " existe.");
                    return;
                }

                r.setObservacion(nuevaRecap.getObservacion());
                r.setFechaCitacion(nuevaRecap.getFechaCitacion());
                r.setIdGopUnidadFuncional(unidadFuncional);

                // Sumar 30 días a la fecha inoperable usando Calendar
                Calendar cal = Calendar.getInstance();
                cal.setTime(r.getIdNovedad().getFecha());
                cal.add(Calendar.DAY_OF_MONTH, 30);
                r.setFechaInoperable(cal.getTime());

                r.setAsistencia(false);
                r.setNotificado(false);
                r.setUsername(user.getUsername());
                r.setEstadoReg(0);
                r.setCreado(MovilidadUtil.fechaCompletaHoy());
                r.setModificado(MovilidadUtil.fechaCompletaHoy());
                r.setProgramado(nuevaRecap.getFechaCitacion() != null);

                recapacitacionEjb.create(r);
            });

            findMaestro();
            MovilidadUtil.hideModal("wlvRecapMaestro");
        } catch (Exception e) {
            // Usa un logger para capturar el error
            System.out.println(e);
        }
    }

    public void guardarDocumento(Integer type) { //TIPO 1: Asistencia, TIPO 2: Evaluación, TIPO 3: Fotos
        if (lstRecapacitacionSelected.isEmpty()) {
            MovilidadUtil.addErrorMessage("Seleccione al menos una recapacitación.");
            return;
        }
        if (fechaAsistencia == null && type == 1) {
            MovilidadUtil.addErrorMessage("Seleccione Fecha de asistencia.");
            return;
        }
        if (idCapacitador == 0 && type == 1) {
            MovilidadUtil.addErrorMessage("Seleccione un capacitador.");
            return;
        }
        if (!archivos.isEmpty()) {
            String pathArchivo = "";
            UploadedFile f = archivos.get(0);
            if (f.getContentType().contains("pdf")) {
                if (null != type) {
                    switch (type) {
                        case 1:
                            pathArchivo = Util.saveFile(f, 1, "recapacitacion");
                            break;
                        case 2:
                            pathArchivo = Util.saveFile(f, 2, "recapacitacion");
                            break;
                        case 3:
                            pathArchivo = Util.saveFile(f, 3, "recapacitacion");
                            break;
                        default:
                            break;
                    }
                }
            }
            for (RecapacitacionMaestro o : lstRecapacitacionSelected) {
                if (null != type) {
                    switch (type) {
                        case 1:
                            o.setPathAsistencia(pathArchivo);
                            o.setFechaEjecucion(fechaAsistencia);
                            o.setAsistencia(true);
                            o.setIdCapacitador(empleadoEjb.find(idCapacitador));
                            break;
                        case 2:
                            o.setPathEvaluacion(pathArchivo);
                            break;
                        case 3:
                            o.setPathPhotos(pathArchivo);
                            break;
                        default:
                            break;
                    }
                }
                recapacitacionEjb.edit(o);
            }
            archivos.clear();
        }
        findAsistente();
        MovilidadUtil.hideModal("novedadDocumentos");
    }

    public void obtenerArchivo(String path) {
        try {
            // Verificar si el archivo existe antes de procesarlo
            if (Files.exists(Paths.get(path))) {
                archivosBean.setExtension("pdf");
                archivosBean.setPath(path);
                archivosBean.setModalHeader("Documento de recapacitación");
            } else {
                Logger.getLogger(NovedadJSFManagedBean.class.getName()).log(Level.WARNING, "El archivo no existe: " + path);
            }
        } catch (Exception ex) {
            Logger.getLogger(NovedadJSFManagedBean.class.getName()).log(Level.SEVERE, "Error al obtener el archivo", ex);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();

        if (event.getFile().getFileName().length() > 50) {
            MovilidadUtil.updateComponent(":msgs");
            MovilidadUtil.addErrorMessage("El nombre de archivo DEBE ser MENOR 50 a caracteres");
            return;
        }
        archivos.add(event.getFile());
        current.executeScript("PF('AddFilesListDialog').hide()");
        MovilidadUtil.updateComponent(":msgs");
        evaluacionAsistenciaSaveFile();
        if (tipoDocumento != 1) {
            findAsistente();
            MovilidadUtil.updateComponent("@form");
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Archivo agregado éxitosamente."));
    }

    public void evaluacionAsistenciaSaveFile() {
        if (tipoDocumento != 1) {
            guardarDocumento(tipoDocumento);
        }
    }

    // Método para manejar la selección de una fila
    public void onRowSelect(SelectEvent event) {
        RecapacitacionMaestro selectedRecap = (RecapacitacionMaestro) event.getObject();
        System.out.println(lstRecapacitacionSelected);
        System.out.println("Fila seleccionada: " + selectedRecap.getIdRecapacitacionMaestro());
        // Lógica adicional para manejar la selección si es necesario
    }

    public void asignacionIndividual() {
        selectedRecap.setFechaCitacion(fechaCitacionIndividual);
        selectedRecap.setProgramado(true);
        recapacitacionEjb.edit(selectedRecap); //HABILITAR EDICIÓN
        envioCorreoNotificacion(selectedRecap);
        MovilidadUtil.updateComponent(":msgs");
        MovilidadUtil.addSuccessMessage("Citación completa");
        MovilidadUtil.hideModal("AsignarAgendaDlg");
        selectedRecap = new RecapacitacionMaestro();
    }

    public void quitarItemslstRecapacitacionNuevo(int idNovedad) {
        lstRecapacitacionNuevo.removeIf(item -> item.getIdNovedad().getIdNovedad() == idNovedad);
    }

    public List<RecapacitacionMaestro> getLstRecapacitacion() {
        return lstRecapacitacion;
    }

    public void setLstRecapacitacion(List<RecapacitacionMaestro> lstRecapacitacion) {
        this.lstRecapacitacion = lstRecapacitacion;
    }

    public RecapacitacionMaestroFacadeLocal getRecapacitacionEjb() {
        return recapacitacionEjb;
    }

    public void setRecapacitacionEjb(RecapacitacionMaestroFacadeLocal recapacitacionEjb) {
        this.recapacitacionEjb = recapacitacionEjb;
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

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public List<RecapacitacionMaestro> getLstRecapacitacionNuevo() {
        return lstRecapacitacionNuevo;
    }

    public void setLstRecapacitacionNuevo(List<RecapacitacionMaestro> lstRecapacitacionNuevo) {
        this.lstRecapacitacionNuevo = lstRecapacitacionNuevo;
    }

    public List<Novedad> getLstNovedades() {
        return lstNovedades;
    }

    public void setLstNovedades(List<Novedad> lstNovedades) {
        this.lstNovedades = lstNovedades;
    }

    public List<Novedad> getLstNovedadesSeleccionadas() {
        return lstNovedadesSeleccionadas;
    }

    public void setLstNovedadesSeleccionadas(List<Novedad> lstNovedadesSeleccionadas) {
        this.lstNovedadesSeleccionadas = lstNovedadesSeleccionadas;
    }

    public RecapacitacionMaestro getNuevaRecap() {
        return nuevaRecap;
    }

    public void setNuevaRecap(RecapacitacionMaestro nuevaRecap) {
        this.nuevaRecap = nuevaRecap;
    }

    public Date getFechaInicioAgendaMasiva() {
        return fechaInicioAgendaMasiva;
    }

    public void setFechaInicioAgendaMasiva(Date fechaInicioAgendaMasiva) {
        this.fechaInicioAgendaMasiva = fechaInicioAgendaMasiva;
    }

    public Date getFechaFinAgendaMasiva() {
        return fechaFinAgendaMasiva;
    }

    public void setFechaFinAgendaMasiva(Date fechaFinAgendaMasiva) {
        this.fechaFinAgendaMasiva = fechaFinAgendaMasiva;
    }

    public List<RecapacitacionMaestro> getLstRecapacitacionAgendaMasiva() {
        return lstRecapacitacionAgendaMasiva;
    }

    public void setLstRecapacitacionAgendaMasiva(List<RecapacitacionMaestro> lstRecapacitacionAgendaMasiva) {
        this.lstRecapacitacionAgendaMasiva = lstRecapacitacionAgendaMasiva;
    }

    public Date getFechaCitacionAgendaMasiva() {
        return fechaCitacionAgendaMasiva;
    }

    public void setFechaCitacionAgendaMasiva(Date fechaCitacionAgendaMasiva) {
        this.fechaCitacionAgendaMasiva = fechaCitacionAgendaMasiva;
    }

    public Boolean getAsignarmasivo() {
        return asignarmasivo;
    }

    public void setAsignarmasivo(Boolean asignarmasivo) {
        this.asignarmasivo = asignarmasivo;
    }

    public List<RecapacitacionMaestro> getLstAsistente() {
        return lstAsistente;
    }

    public void setLstAsistente(List<RecapacitacionMaestro> lstAsistente) {
        this.lstAsistente = lstAsistente;
    }

    public List<RecapacitacionMaestro> getLstRecapacitacionSelected() {
        return lstRecapacitacionSelected;
    }

    public void setLstRecapacitacionSelected(List<RecapacitacionMaestro> lstRecapacitacionSelected) {
        this.lstRecapacitacionSelected = lstRecapacitacionSelected;
    }

    public Integer getIdRecapacitacion() {
        return idRecapacitacion;
    }

    public void setIdRecapacitacion(Integer idRecapacitacion) {
        this.idRecapacitacion = idRecapacitacion;
    }

    public StreamedContent getArchivo() {
        return archivo;
    }

    public void setArchivo(StreamedContent archivo) {
        this.archivo = archivo;
    }

    public List<UploadedFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }

    public List<Empleado> getLstCapacitadores() {
        return lstCapacitadores;
    }

    public Integer getIdCapacitador() {
        return idCapacitador;
    }

    public void setIdCapacitador(Integer idCapacitador) {
        this.idCapacitador = idCapacitador;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Date getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(Date fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

    public RecapacitacionMaestro getSelectedRecap() {
        return selectedRecap;
    }

    public void setSelectedRecap(RecapacitacionMaestro selectedRecap) {
        this.selectedRecap = selectedRecap;
    }

    public Date getFechaCitacionIndividual() {
        return fechaCitacionIndividual;
    }

    public void setFechaCitacionIndividual(Date fechaCitacionIndividual) {
        this.fechaCitacionIndividual = fechaCitacionIndividual;
    }

}
