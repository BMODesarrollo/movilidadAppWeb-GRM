package com.movilidad.jsf;

import com.movilidad.dto.PdAgendaMasivaDTO;
import com.movilidad.dto.PdPrincipalDTO;
import com.movilidad.dto.PdReporteNovedadesDTO;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.PdEstadoProcesoFacadeLocal;
import com.movilidad.ejb.PdMaestroAsistenteFacadeLocal;
import com.movilidad.ejb.PdMaestroDetalleFacadeLocal;
import com.movilidad.ejb.PdMaestroFacadeLocal;
import com.movilidad.ejb.PdResponsablesFacadeLocal;
import com.movilidad.ejb.PdTipoSancionFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.PdEstadoProceso;
import com.movilidad.model.PdMaestro;
import com.movilidad.model.PdMaestroDetalle;
import com.movilidad.model.PdTipoSancion;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.PdMaestroAsistente;
import com.movilidad.model.PdResponsables;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.ProcesoDisciplinarioUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "pdMaestroBean")
@ViewScoped
public class PdMaestroBean implements Serializable {

    @EJB
    private PdMaestroFacadeLocal pdMaestroEjb;
    @EJB
    private PdEstadoProcesoFacadeLocal pdEstadoProcesoEjb;
    @EJB
    private PdTipoSancionFacadeLocal pdTipoSancionEjb;
    @EJB
    private PdMaestroDetalleFacadeLocal detalleEjb;
    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    NotificacionProcesosFacadeLocal notificacionProcesosEjb;
    @EJB
    EmpleadoFacadeLocal empleadoEjb;
    @EJB
    PdMaestroAsistenteFacadeLocal maestroAsistenteEjb;
    @EJB
    private PdResponsablesFacadeLocal PdResponsablesFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<PdPrincipalDTO> lstProcesos;
    private List<PdPrincipalDTO> lstProcesosAgenda;
    private List<PdEstadoProceso> lstEstadoProcesos;
    private List<PdTipoSancion> lstTipoSancion;
    private List<Novedad> lstNovedades;
    private List<Novedad> lstNovedadesSeleccionadas;
    private List<PdMaestroDetalle> lstDetalles;
    private List<PdMaestroDetalle> lstDetallesEliminados;
    private List<Empleado> lstEmpleados;
    private List<Empleado> lstEmpleadosSeleccionados;
    private List<PdMaestroAsistente> lstMaestroAsistente;
    private List<PdAgendaMasivaDTO> lstAgendaMasiva;
    private List<PdPrincipalDTO> lstPdAgendaMasiva;
    private List<PdResponsables> lstResponsablesPd;
    private List<PdReporteNovedadesDTO> lstNovedadesPd;
    private PdMaestro pdMaestro;
    private PdMaestro selected;
    private PdPrincipalDTO selectedDto;
    private Integer idEstadoProceso;
    private Integer idTipoSancion;
    private Date fechaDesde;
    private Date fechaHasta;
    private NotificacionProcesos notificacionProceso;
    private Empleado empleado;
    private PdMaestroAsistente pdMaestroAsistente;
    private Date fechaCitacion;
    private Date fechaHoy;
    private Date fechaInicioSancion;
    private Date fechaFinSancion;
    private int pdAbierto;
    private boolean flagRol;
    private boolean renderFechasSancion;
    private boolean renderResponsable;
    private int idResponsablePd;
    private int idEstadoAsistencia;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditing;

    @PostConstruct
    public void init() {
        //lstProcesos = pdMaestroEjb.findAllByEstadoReg(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        flagRol = validarRol();
        fechaDesde = new Date();
        fechaHasta = new Date();
        lstProcesos = new ArrayList<>();
        ordenarFechaApetura();
        pdMaestroAsistente = new PdMaestroAsistente();
        lstMaestroAsistente = new ArrayList<>();
        lstPdAgendaMasiva = new ArrayList<>();
        lstEmpleados = new ArrayList<>();
        lstEmpleadosSeleccionados = new ArrayList<>();
        fechaCitacion = new Date();
        Calendar calendar = Calendar.getInstance(); // Obtener una instancia de Calendar
        calendar.set(Calendar.HOUR_OF_DAY, 0); // Establecer la hora a 00:00:00
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        fechaHoy = calendar.getTime();
        lstAgendaMasiva = new ArrayList<>();
        pdAbierto = 0;
        idResponsablePd=0;
        renderFechasSancion = false;
        renderResponsable = false;
    }

    public void nuevo() {
        isEditing = false;
        pdMaestro = new PdMaestro();
        lstDetalles = new ArrayList<>();
        lstEmpleados = new ArrayList<>();
        lstEmpleadosSeleccionados = new ArrayList<>();
        lstNovedadesSeleccionadas = new ArrayList<>();
        lstEstadoProcesos = pdEstadoProcesoEjb.findAllByEstadoReg();
        lstTipoSancion = pdTipoSancionEjb.findAllByEstadoReg();
        lstResponsablesPd = PdResponsablesFacadeLocal.getAllActivo();
        idEstadoProceso = null;
        idTipoSancion = null;
        selected = null;
        selectedDto = null;
        notificacionProceso = null;
        empleado = null;
    }

    public void editar() {
        isEditing = true;
        pdMaestro = pdMaestroEjb.find(selectedDto.getIdPdMaestro());
        lstResponsablesPd = PdResponsablesFacadeLocal.getAllActivo();
        idEstadoProceso = pdMaestro.getIdPdEstadoProceso().getIdPdEstadoProceso();
        idTipoSancion = pdMaestro.getIdPdTipoSancion() != null ? pdMaestro.getIdPdTipoSancion().getIdPdTipoSancion() : null;
        idResponsablePd = pdMaestro.getResponsable() != null ? pdMaestro.getResponsable().getIdResponsable() : 0;
        renderResponsable = true;
        renderFechasSancion = idTipoSancion == 2 ? true : false;
        fechaInicioSancion = pdMaestro.getFechaInicioSancion();
        fechaFinSancion = pdMaestro.getFechaFinSancion();
        lstDetalles = pdMaestro.getPdMaestroDetalleList();
        lstEmpleados = new ArrayList<>();
        lstNovedadesSeleccionadas = new ArrayList<>();
        lstEstadoProcesos = pdEstadoProcesoEjb.findAllByEstadoReg();
        lstTipoSancion = pdTipoSancionEjb.findAllByEstadoReg();
        lstDetalles = detalleEjb.findByIdProceso(selectedDto.getIdPdMaestro());
        lstDetallesEliminados = new ArrayList<>();
    }

    public void findMaestro() {
        selected = pdMaestroEjb.find(selectedDto.getIdPdMaestro());
    }

    public void guardar() {
        guardarTransactional();
    }

    public void cerrar() {
        pdAbierto = 0;
        renderFechasSancion = false;
        renderResponsable = false;
        idResponsablePd = 0;
        idTipoSancion = 0;
        MovilidadUtil.hideModal("wlvPdMaestro");
        MovilidadUtil.addErrorMessage("Operación cancelada");
    }

    public void envioCorreoNotificacion(List<Empleado> lstEmpleado, int type, boolean procesoMasivo, Date fechaCitacion) { //1 Creación proceso, 2 confirmación agenda
        try {
            String operadoresEmails = "";
            Integer counterEmails = 0;
            String destinatarioFinal = "";
            NotificacionCorreoConf conf = NCCEJB.find(1);
            if (conf == null) {
                return;
            }
            NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(type == 1 ? ConstantsUtil.TEMPLATE_NOTIFICACION_PROCESO_DISCIPLINARIO : ConstantsUtil.TEMPLATE_NOTIFICACION_PD_AGENDA);
            if (template == null) {
                return;
            }
            notificacionProceso = notificacionProcesosEjb.findByCodigo("PD");

            Map mapa = SendMails.getMailParams(conf, template);
            Map mailProperties = new HashMap();
            String fechaCitacionFormato = "";
            if (type != 1) {
                SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                if (procesoMasivo) {
                    fechaCitacionFormato = formateador.format(fechaCitacion);
                } else {
                    fechaCitacionFormato = formateador.format(fechaCitacion);
                }

                mailProperties.put("fecha", fechaCitacionFormato);
            }
            if (!lstEmpleado.isEmpty()) {
                mailProperties.put("singularplural", "el operador");
                mailProperties.put("operador", lstEmpleado.get(0).getNombresApellidosCodigo());
                if (validarCorreo(lstEmpleado.get(0).getEmailCorporativo())) {
                    operadoresEmails = lstEmpleado.get(0).getEmailCorporativo() + ",";
                } else {
                    counterEmails = 1;
                }

            }
            if (!operadoresEmails.isEmpty()) {
                operadoresEmails = operadoresEmails.substring(0, operadoresEmails.length() - 1);
                if (counterEmails > 0) {
                    mailProperties.put("alertaadicional", "Alerta no fue posible notificar a todos los operadores involucrados en el proceso disciplinario");
                } else {
                    mailProperties.put("alertaadicional", "");
                }
                destinatarioFinal = operadoresEmails.length() > 2 ? notificacionProceso.getEmails() + "," + operadoresEmails : notificacionProceso.getEmails();

                mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
                SendMails.sendEmail(mapa,
                        mailProperties,
                        notificacionProceso.getNombreProceso(),
                        "",
                        destinatarioFinal,
                        "Notificaciones", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {

            PdEstadoProceso estado = pdEstadoProcesoEjb.find(idEstadoProceso);

            if (estado.getCierraProceso() == 1) {
                pdMaestro.setUsuarioCierre(user.getUsername());
            }
            pdMaestro.setIdPdEstadoProceso(estado);
            pdMaestro.setIdPdTipoSancion(idTipoSancion != null ? new PdTipoSancion(idTipoSancion) : null);
            pdMaestro.setFechaInicioSancion(renderFechasSancion ? fechaInicioSancion : null);
            pdMaestro.setFechaFinSancion(renderFechasSancion ? fechaFinSancion : null);
            pdMaestro.setResponsable(PdResponsablesFacadeLocal.find(idResponsablePd));
            pdMaestro.setPdMaestroDetalleList(new ArrayList<>());

            for (PdMaestroDetalle det : lstDetalles) {
                if (det.getIdPdMaestroDetalle() == null) {
                    pdMaestro.getPdMaestroDetalleList().add(det);
                    empleado = det.getIdNovedad().getIdEmpleado();
                    lstEmpleados.add(empleado);
                    empleado = null;
                    pdMaestro.setIdEmpleado(det.getIdNovedad().getIdEmpleado());
                } else {
                    pdMaestro.setIdEmpleado(det.getIdNovedad().getIdEmpleado());
                }

            }
            // Envío de correo creación proceso disciplinario
            //envioCorreoNotificacion(lstEmpleados, 1);

            if (isEditing) {

                if (pdMaestro.getIdPdTipoSancion().getIdPdTipoSancion() == 2) {

                    if (pdMaestro.getFechaFinSancion() == null || pdMaestro.getFechaFinSancion() == null) {
                        MovilidadUtil.addErrorMessage("Si selecciona sanción debe ingresar las fechas de inicio y fin");
                        return;
                    } else {
                        if (fechaFinSancion.before(fechaInicioSancion)) {
                            MovilidadUtil.addErrorMessage("La fecha de inicio de la sanción no puede ser superior a la fecha fin");
                            return;
                        }
                    }
                }

                if (lstDetallesEliminados != null) {
                    for (PdMaestroDetalle det : lstDetallesEliminados) {
                        detalleEjb.remove(det);
                    }
                }
                pdMaestro.setModificado(MovilidadUtil.fechaCompletaHoy());
                pdMaestroEjb.edit(pdMaestro);

                MovilidadUtil.hideModal("wlvPdMaestro");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                pdMaestro.setUsuarioApertura(user.getUsername());
                pdMaestro.setCreado(MovilidadUtil.fechaCompletaHoy());
                pdMaestro.setEstadoReg(0);
                pdMaestroEjb.create(pdMaestro);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

            init();
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    /**
     * Método que se encarga de limpiar las listas de la vista modal en la que
     * se muestran las novedades.
     */
    public void prepareListadoNovedades() {
        lstNovedades = null;
        fechaDesde = null;
        fechaHasta = null;
        lstNovedadesSeleccionadas = null;
    }

    /**
     * Método que se encarga de realizar la búsqueda de novedades por rango de
     * fechas.
     */
    public void cargarListadoNovedad() {
        lstNovedades = novedadEjb.findByDateRange(fechaDesde, fechaHasta, unidadFuncionalSessionBean.getIdGopUnidadFuncional());
    }


    /*
        Método que se encarga de crear la lista de detalles que se van a 
        almacenar en la base de datos
     */
    public void crearListaDetalles() {

        if (lstNovedadesSeleccionadas == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar al menos una novedad");
            return;
        }

        lstPdAgendaMasiva = pdMaestroEjb.conteoAbiertosPorEmpleado(lstNovedadesSeleccionadas.get(0).getIdEmpleado().getIdEmpleado());
        pdAbierto = lstPdAgendaMasiva.size();

        /**
         * Se verifica si alguno de los detalles existe una novedad ya
         * seleccionada.
         */
        String validacion = ProcesoDisciplinarioUtil.verificarExisteNovedad(lstDetalles, lstNovedadesSeleccionadas);

        if (validacion != null) {
            lstNovedadesSeleccionadas = null;
            MovilidadUtil.addErrorMessage(validacion);
            return;
        }
        if (!lstDetalles.isEmpty()) {
            List<PdMaestroDetalle> nuevosDetalles = ProcesoDisciplinarioUtil.generarListaDetalle(lstNovedadesSeleccionadas, user.getUsername(), pdMaestro);
            lstDetalles.addAll(nuevosDetalles);
        } else {
            lstDetalles = ProcesoDisciplinarioUtil.generarListaDetalle(lstNovedadesSeleccionadas, user.getUsername(), pdMaestro);
        }
        lstNovedades = null;
        lstNovedadesSeleccionadas = null;
        MovilidadUtil.hideModal("NovedadesListDialog");
        //PrimeFaces.current().ajax().update(":pdMaestro:frmPdMaestro");
    }

    /**
     * Método que realiza la eliminación de detalles en la base de datos y en la
     * vista.
     *
     * @param detalle
     */
    public void eliminarDetalle(PdMaestroDetalle detalle) {
        if (detalle.getIdPdMaestroDetalle() == null) {
            lstDetalles.remove(detalle);
        } else {
            lstDetallesEliminados.add(detalle);
            lstDetalles.remove(detalle);
        }
        MovilidadUtil.addSuccessMessage("Detalle eliminado con éxito");
    }

    private String validarDatos() {

        if (lstDetalles == null || lstDetalles.isEmpty()) {
            return "DEBE haber al menos un detalle en la lista";
        }

        if (idEstadoProceso == null) {
            return "DEBE seleccionar un estado de proceso";
        }

        if (pdMaestro.getFechaCierre() != null) {
            if (Util.validarFechaCambioEstado(pdMaestro.getFechaApertura(), pdMaestro.getFechaCierre())) {
                return "La fecha de apertura NO debe ser mayor a la de cierre";
            }
        }

        if (isEditing) {
        } else {

        }

        return null;
    }

    public static boolean validarCorreo(String correo) {
        // Patrón de expresión regular para validar el correo electrónico
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Compila el patrón en un objeto Pattern
        Pattern pattern = Pattern.compile(regex);

        // Crea un objeto Matcher para hacer la comparación
        Matcher matcher = pattern.matcher(correo);

        // Retorna true si el correo coincide con el patrón, de lo contrario, false
        return matcher.matches();
    }

    public void cargarListadoAsistentes(Integer idMaestro) {
        lstMaestroAsistente = maestroAsistenteEjb.findByIdMaestro(idMaestro);
        if (!lstMaestroAsistente.isEmpty()) {
            idEstadoAsistencia = lstMaestroAsistente.get(0).getAsiste();
        }
        fechaCitacion = selectedDto.getFechaCitacion();
    }

    public void cargarListadoEmpleados() {
        lstEmpleados = empleadoEjb.findEmpleadosRelacionadosPd(selectedDto.getIdPdMaestro());
        lstEmpleadosSeleccionados = lstEmpleados;
    }

    public void ActualizarAsistencia(PdMaestroAsistente asistente) {
        if (asistente.getIdPdMaestro().getIdPdEstadoProceso().getIdPdEstadoProceso() == 2) {
            MovilidadUtil.addErrorMessage("No puede editar un proceso disciplinario CERRADO su cambio no será guardado");
            return;
        }
        if (asistente != null) {
            try {
                asistente.setAsiste(idEstadoAsistencia);
                maestroAsistenteEjb.edit(asistente);
                MovilidadUtil.addSuccessMessage("Se actualizó la asistencia " + asistente.getIdEmpleado().getNombres() + " " + asistente.getIdEmpleado().getApellidos());
                idEstadoAsistencia = 0;
                consultarPrincipal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void crearListaEmpleados() {
        if (lstEmpleadosSeleccionados == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar al menos un participante");
            return;
        } else {
            try {
                if (selectedDto == null) {
                    MovilidadUtil.addErrorMessage("Ocurrió un problema intente nuevamente");
                    return;
                } else {
                    pdMaestro = pdMaestroEjb.find(selectedDto.getIdPdMaestro());
                    pdMaestro.setFechaCitacion(fechaCitacion);
                    pdMaestroEjb.edit(pdMaestro);
                }
                Boolean exist = false;
                for (Empleado emp : lstEmpleadosSeleccionados) {
                    for (PdMaestroAsistente asistente : lstMaestroAsistente) {
                        if (Objects.equals(asistente.getIdEmpleado().getIdEmpleado(), emp.getIdEmpleado())) {
                            exist = true;
                        }
                    }
                    if (!exist) {
                        pdMaestroAsistente.setIdEmpleado(emp);
                        pdMaestroAsistente.setUsername(user.getUsername());
                        pdMaestroAsistente.setCreado(MovilidadUtil.fechaCompletaHoy());
                        pdMaestroAsistente.setEstadoReg(0);
                        pdMaestroAsistente.setIdPdMaestro(pdMaestroEjb.find(selectedDto.getIdPdMaestro()));
                        pdMaestroAsistente.setAsiste(0);
                        maestroAsistenteEjb.create(pdMaestroAsistente);
                        pdMaestroAsistente = new PdMaestroAsistente();
                    }

                }
                envioCorreoNotificacion(lstEmpleadosSeleccionados, 2, false, fechaCitacion);
                cargarListadoAsistentes(selectedDto.getIdPdMaestro());
                MovilidadUtil.hideModal("EmpleadosAgendaDialog");
                MovilidadUtil.addSuccessMessage("Agenda actualizada correctamente");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        lstEmpleadosSeleccionados = null;
    }

    public void eliminarAsistente(PdMaestroAsistente asistente) {
        if (asistente.getIdPdMaestroAsistente() != null) {
            maestroAsistenteEjb.remove(asistente);
            pdMaestro = pdMaestroEjb.find(selectedDto.getIdPdMaestro());
            pdMaestro.setFechaCitacion(null);
            pdMaestroEjb.edit(pdMaestro);
        }
        cargarListadoAsistentes(asistente.getIdPdMaestro().getIdPdMaestro());
        MovilidadUtil.addSuccessMessage("Asistente eliminado con éxito");
    }

    public void cargarSelectorUf() {
        lstProcesos = pdMaestroEjb.findAllByEstadoReg(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        ordenarFechaApetura();
    }

    public void ordenarFechaApetura() {
        Collections.sort(lstProcesos, new Comparator<PdPrincipalDTO>() {
            @Override
            public int compare(PdPrincipalDTO p1, PdPrincipalDTO p2) {
                // Invertir la llamada a compareTo() para orden descendente
                return p2.getFechaApertura().compareTo(p1.getFechaApertura());
            }
        });
    }

    public void consultaAgendaMasiva() {
        lstAgendaMasiva = pdMaestroEjb.findAgendaPrgTc(0, fechaDesde, fechaHasta);
        lstProcesosAgenda = pdMaestroEjb.findAllByEstadoReg(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void consultarPrincipal() {
        lstProcesos = pdMaestroEjb.findAllByDate(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), fechaDesde, fechaHasta);
        ordenarFechaApetura();
    }

    public void confirmacionAgendaMasiva() {
        int conteo = 0;
        int conteoInvalidos = 0;
        for (PdPrincipalDTO objPrincipal : lstProcesosAgenda) {
            for (PdAgendaMasivaDTO objPdAgendaMasiva : lstAgendaMasiva) {
                if (Objects.equals(objPrincipal.getIdEmpleado(), objPdAgendaMasiva.getIdEmpleado()) && objPrincipal.getFechaCitacion() == null) {
                    if(objPrincipal.getFechaAperturaDate().before(objPdAgendaMasiva.getFechaCitacion())){
                       objPrincipal.setFechaCitacion(objPdAgendaMasiva.getFechaCitacion());
                       lstPdAgendaMasiva.add(objPrincipal);                    
                    }else{
                        conteoInvalidos++;
                    }
                }
            }
        }
        System.out.println("Invalidos "+conteoInvalidos);
        if (lstPdAgendaMasiva.isEmpty()) {
            PrimeFaces.current().ajax().update(":ListaAgendaMasivaDialog");
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.hideModal("ListaAgendaMasivaDlg");
            if(conteoInvalidos>0){
                MovilidadUtil.addErrorMessage("No se encontraron coincidencias para confirmar. Se encontraron "+ conteoInvalidos + " tareas que "
                        + "no se pueden aplicar.");
            }else{
                MovilidadUtil.addErrorMessage("No se encontraron coincidencias para confirmar.");
            }
        }
        if (!lstPdAgendaMasiva.isEmpty()) {
            for (PdPrincipalDTO objPdAgenda : lstPdAgendaMasiva) {
                pdMaestro = pdMaestroEjb.find(objPdAgenda.getIdPdMaestro());
                pdMaestro.setFechaCitacion(objPdAgenda.getFechaCitacion());
                pdMaestroEjb.edit(pdMaestro);
                pdMaestro = new PdMaestro();
                conteo = conteo + 1;
                empleado = empleadoEjb.find(objPdAgenda.getIdEmpleado());
                pdMaestroAsistente.setIdEmpleado(empleado);
                pdMaestroAsistente.setUsername(user.getUsername());
                pdMaestroAsistente.setCreado(MovilidadUtil.fechaCompletaHoy());
                pdMaestroAsistente.setEstadoReg(0);
                pdMaestroAsistente.setIdPdMaestro(pdMaestroEjb.find(objPdAgenda.getIdPdMaestro()));
                pdMaestroAsistente.setAsiste(0);
                maestroAsistenteEjb.create(pdMaestroAsistente);
                pdMaestroAsistente = new PdMaestroAsistente();
                lstEmpleados.add(empleado);
                envioCorreoNotificacion(lstEmpleados, 2, true, objPdAgenda.getFechaCitacion());
                empleado = new Empleado();
                lstEmpleados = new ArrayList<>();
            }

            PrimeFaces.current().ajax().update(":ListaAgendaMasivaDialog");
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.hideModal("ListaAgendaMasivaDlg");
            if(conteoInvalidos>0){
                MovilidadUtil.addAdvertenciaMessage("Se asignó fecha citación a " + conteo + " procesos disciplinarios abiertos. Se encontraron "+ conteoInvalidos + " tareas que "
                        + "no se pueden aplicar.");
            }else{
                MovilidadUtil.addSuccessMessage("Se asignó fecha citación a " + conteo + " procesos disciplinarios abiertos.");
            }
        }
        init();
    } 

    public boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_LIQGEN")
                    || auth.getAuthority().equals("ROLE_PROFPRG")
                    || auth.getAuthority().equals("ROLE_LIQ") || auth.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    public void renderFechaSancion(int tipo) {
        if (selectedDto != null && tipo == 2) {
            renderFechasSancion = true;
        } else {
            renderFechasSancion = false;
        }
    }

    public void consultarNovedadesPd() {
        lstNovedadesPd = pdMaestroEjb.findNovedadPd(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), fechaDesde, fechaHasta);
    }

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public List<Empleado> getLstEmpleadosSeleccionados() {
        return lstEmpleadosSeleccionados;
    }

    public void setLstEmpleadosSeleccionados(List<Empleado> lstEmpleadosSeleccionados) {
        this.lstEmpleadosSeleccionados = lstEmpleadosSeleccionados;
    }

    public List<PdPrincipalDTO> getLstProcesos() {
        return lstProcesos;
    }

    public void setLstProcesos(List<PdPrincipalDTO> lstProcesos) {
        this.lstProcesos = lstProcesos;
    }

    public List<PdEstadoProceso> getLstEstadoProcesos() {
        return lstEstadoProcesos;
    }

    public void setLstEstadoProcesos(List<PdEstadoProceso> lstEstadoProcesos) {
        this.lstEstadoProcesos = lstEstadoProcesos;
    }

    public List<PdTipoSancion> getLstTipoSancion() {
        return lstTipoSancion;
    }

    public void setLstTipoSancion(List<PdTipoSancion> lstTipoSancion) {
        this.lstTipoSancion = lstTipoSancion;
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

    public List<PdMaestroDetalle> getLstDetalles() {
        return lstDetalles;
    }

    public void setLstDetalles(List<PdMaestroDetalle> lstDetalles) {
        this.lstDetalles = lstDetalles;
    }

    public List<PdMaestroDetalle> getLstDetallesEliminados() {
        return lstDetallesEliminados;
    }

    public void setLstDetallesEliminados(List<PdMaestroDetalle> lstDetallesEliminados) {
        this.lstDetallesEliminados = lstDetallesEliminados;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public PdMaestro getPdMaestro() {
        return pdMaestro;
    }

    public void setPdMaestro(PdMaestro pdMaestro) {
        this.pdMaestro = pdMaestro;
    }

    public PdMaestro getSelected() {
        return selected;
    }

    public void setSelected(PdMaestro selected) {
        this.selected = selected;
    }

    public Integer getIdEstadoProceso() {
        return idEstadoProceso;
    }

    public void setIdEstadoProceso(Integer idEstadoProceso) {
        this.idEstadoProceso = idEstadoProceso;
    }

    public Integer getIdTipoSancion() {
        return idTipoSancion;
    }

    public void setIdTipoSancion(Integer idTipoSancion) {
        this.idTipoSancion = idTipoSancion;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<PdMaestroAsistente> getLstMaestroAsistente() {
        return lstMaestroAsistente;
    }

    public void setLstMaestroAsistente(List<PdMaestroAsistente> lstMaestroAsistente) {
        this.lstMaestroAsistente = lstMaestroAsistente;
    }

    public Date getFechaCitacion() {
        return fechaCitacion;
    }

    public void setFechaCitacion(Date fechaCitacion) {
        this.fechaCitacion = fechaCitacion;
    }

    public GopUnidadFuncionalSessionBean getUnidadFuncionalSessionBean() {
        return unidadFuncionalSessionBean;
    }

    public void setUnidadFuncionalSessionBean(GopUnidadFuncionalSessionBean unidadFuncionalSessionBean) {
        this.unidadFuncionalSessionBean = unidadFuncionalSessionBean;
    }

    public PdPrincipalDTO getSelectedDto() {
        return selectedDto;
    }

    public void setSelectedDto(PdPrincipalDTO selectedDto) {
        this.selectedDto = selectedDto;
    }

    public List<PdAgendaMasivaDTO> getLstAgendaMasiva() {
        return lstAgendaMasiva;
    }

    public void setLstAgendaMasiva(List<PdAgendaMasivaDTO> lstAgendaMasiva) {
        this.lstAgendaMasiva = lstAgendaMasiva;
    }

    public Date getFechaHoy() {
        return fechaHoy;
    }

    public void setFechaHoy(Date fechaHoy) {
        this.fechaHoy = fechaHoy;
    }

    public int getPdAbierto() {
        return pdAbierto;
    }

    public void setPdAbierto(int pdAbierto) {
        this.pdAbierto = pdAbierto;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public boolean isFlagRol() {
        return flagRol;
    }

    public void setFlagRol(boolean flagRol) {
        this.flagRol = flagRol;
    }

    public boolean isRenderFechasSancion() {
        return renderFechasSancion;
    }

    public void setRenderFechasSancion(boolean renderFechasSancion) {
        this.renderFechasSancion = renderFechasSancion;
    }

    public Date getFechaInicioSancion() {
        return fechaInicioSancion;
    }

    public void setFechaInicioSancion(Date fechaInicioSancion) {
        this.fechaInicioSancion = fechaInicioSancion;
    }

    public Date getFechaFinSancion() {
        return fechaFinSancion;
    }

    public void setFechaFinSancion(Date fechaFinSancion) {
        this.fechaFinSancion = fechaFinSancion;
    }

    public List<PdResponsables> getLstResponsablesPd() {
        return lstResponsablesPd;
    }

    public int getIdResponsablePd() {
        return idResponsablePd;
    }

    public void setIdResponsablePd(int idResponsablePd) {
        this.idResponsablePd = idResponsablePd;
    }

    public int getIdEstadoAsistencia() {
        return idEstadoAsistencia;
    }

    public void setIdEstadoAsistencia(int idEstadoAsistencia) {
        this.idEstadoAsistencia = idEstadoAsistencia;
    }

    public List<PdReporteNovedadesDTO> getLstNovedadesPd() {
        return lstNovedadesPd;
    }

    public boolean isRenderResponsable() {
        return renderResponsable;
    }

    public void setRenderResponsable(boolean renderResponsable) {
        this.renderResponsable = renderResponsable;
    }

}
