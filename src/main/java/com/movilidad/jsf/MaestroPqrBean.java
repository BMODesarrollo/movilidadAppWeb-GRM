/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.ejb.PqrFranjaHorariaFacadeLocal;
import com.movilidad.ejb.PqrMaestroDocumentosFacadeLocal;
import com.movilidad.ejb.PqrMaestroFacadeLocal;
import com.movilidad.ejb.PqrMaestroRespaldoFacadeLocal;
import com.movilidad.ejb.PqrMedioReporteFacadeLocal;
import com.movilidad.ejb.PqrProcedeFacadeLocal;
import com.movilidad.ejb.PqrTipoFacadeLocal;
import com.movilidad.ejb.PrgRouteFacadeLocal;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.ParamFeriado;
import com.movilidad.model.PqrFranjaHoraria;
import com.movilidad.model.PqrMaestro;
import com.movilidad.model.PqrMedioReporte;
import com.movilidad.model.PqrTipo;
import com.movilidad.model.PqrMaestroDocumentos;
import com.movilidad.model.PqrMaestroRespaldo;
import com.movilidad.model.PqrProcede;
import com.movilidad.model.PrgPattern;
import com.movilidad.model.PrgRoute;
import com.movilidad.model.Users;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Inject;

/**
 *
 * @author solucionesit
 */
@Named(value = "maestroPqrBean")
@ViewScoped
public class MaestroPqrBean implements Serializable {

    @EJB
    private PqrMaestroFacadeLocal pqrMaestroEJB;
    @EJB
    private PqrMaestroRespaldoFacadeLocal pqrMaestroRespaldoEJB;
    @EJB
    private PqrMedioReporteFacadeLocal pqrMedioReporteEJB;
    @EJB
    private PqrTipoFacadeLocal pqrTipoEJB;
    @EJB
    private PqrFranjaHorariaFacadeLocal pqrFranjaHorariaEJB;
    @EJB
    private PqrMaestroDocumentosFacadeLocal documentosEJB;
    @EJB
    private ParamFeriadoFacadeLocal paraFeEJB;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;
    @EJB
    private PqrProcedeFacadeLocal pqrProcedeFacadeLocal;
    @EJB
    private PrgRouteFacadeLocal prgRouteFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private UsersFacadeLocal usersFacadeLocal;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<PqrMaestro> list;
    private List<PqrTipo> lstTipos;
    private List<PqrFranjaHoraria> listaFranjaHoraria;
    private List<PqrMedioReporte> lstMedios;
    private List<PqrProcede> listaprocedesino;
    private List<PrgRoute> listaRoutes;
    private List<Vehiculo> listaVehicles;
    private List<Empleado> listaEmpleados;
    private List<PqrMaestroRespaldo> listaRespaldo;

    private PqrMaestro pqrMaestro;

    private List<UploadedFile> archivos;

    // private Date desde = MovilidadUtil.fechaHoy();
    private Date desde = MovilidadUtil.fechaAnteriormeses(1);
    private Date hasta = MovilidadUtil.fechaHoy();

    private Integer i_PqrTipo;
    private Integer i_PqrMedioRep;
    private Integer i_PqrProcede;
    private Integer i_PqrFranjaHoraria;
    private Integer i_Route;
    private Integer i_Vehicle;
    private Integer i_Operador;

    private String clasificacionTipo;
    private String responsableTipo;
    private String tiempoRespuestaOtrasAreas;
    private String tiempoRespuestaGH;
    private PqrTipo tipo;

    private int idGopUF;

    private boolean b_oligatorio;

    private NotificacionProcesos notificacionProceso;

    UserExtended user;

    @PostConstruct
    public void init() {
        consultar();
        getUser();
    }

    /**
     * Método que se encarga de obtener los datos del usuario logueado
     */
    public void getUser() {
        try {
            user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            user = null;
        }
    }

    /**
     * Método que se encarga de realizar la consulta de pqr por rango de fechas
     */
    public void consultar() {
        cargarUF();
        list = pqrMaestroEJB.findByRangoFechas(desde, hasta, idGopUF);
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public long calcularDiferenciaDias(Date fechaEstimada) {
        // Establecer la hora de "ahora" a las 00:00
        Calendar calAhora = Calendar.getInstance();
        calAhora.setTime(new Date());
        calAhora.set(Calendar.HOUR_OF_DAY, 0);
        calAhora.set(Calendar.MINUTE, 0);
        calAhora.set(Calendar.SECOND, 0);
        calAhora.set(Calendar.MILLISECOND, 0);
        Date ahora = calAhora.getTime();

        // Establecer la hora de "fechaEstimada" a las 23:59
        Calendar calEstimada = Calendar.getInstance();
        calEstimada.setTime(fechaEstimada);
        calEstimada.set(Calendar.HOUR_OF_DAY, 23);
        calEstimada.set(Calendar.MINUTE, 59);
        calEstimada.set(Calendar.SECOND, 59);
        calEstimada.set(Calendar.MILLISECOND, 999);
        Date fechaEstimadaAjustada = calEstimada.getTime();

        // Calcular la diferencia en milisegundos
        long diferencia = fechaEstimadaAjustada.getTime() - ahora.getTime();

        // Convertir la diferencia a días
        return TimeUnit.DAYS.convert(diferencia, TimeUnit.MILLISECONDS);
    }

    /**
     *
     * @return true si el usuario logueado es quien creo el registro, de lo
     *         contrario retorna false.
     */
    public boolean esAlterable() {
        if (pqrMaestro == null) {
            return true;
        }
        if (pqrMaestro.getEstado() == 2) {
            return true;
        }
        if (!validUser()) {
            return true;
        }
        return false;
    }

    /**
     * Método que se encarga de cargar los datos de un registro antes de
     * modificarlo
     */
    public void editar() {
        if (idGopUF == 0) {
            MovilidadUtil.addAdvertenciaMessage("Seleccione unidad Funcional");
            return;
        }
        if (esAlterable()) {
            MovilidadUtil.addErrorMessage("No es posible modificar éste registro");
            return;
        }
        if (pqrMaestro.getEstado() == 2) {
            MovilidadUtil.addErrorMessage("No es posible modificar éste registro - Cerrado");
            return;
        }
        limpiar();
        lstTipos = pqrTipoEJB.findAllByEstadoReg();
        lstMedios = pqrMedioReporteEJB.findAllByEstadoReg();
        listaFranjaHoraria = pqrFranjaHorariaEJB.findAllByEstadoReg();
        listaRoutes = prgRouteFacadeLocal.getRutas();
        listaVehicles = vehiculoFacadeLocal.getVehiclosActivo();
        i_PqrTipo = pqrMaestro.getIdPqrTipo().getIdPqrTipo();
        clasificacionTipo = pqrMaestro.getIdPqrTipo().getIdPqrClasificacion().getNombre();
        responsableTipo = pqrMaestro.getIdPqrTipo().getIdPqrResponsable().getResponsable();
        tiempoRespuestaOtrasAreas = String.valueOf(pqrMaestro.getIdPqrTipo().getTiempoRespuesta());
        tiempoRespuestaGH = String.valueOf(pqrMaestro.getIdPqrTipo().getObj_time_real());
        i_PqrMedioRep = pqrMaestro.getIdPqrMedioReporte().getIdPqrMedioReporte();
        if (pqrMaestro.getIdPqrFranjaHoraria() != null) {
            i_PqrFranjaHoraria = pqrMaestro.getIdPqrFranjaHoraria().getIdPqrFHoraria();
        }
        if (pqrMaestro.getIdPrgRoute() != null) {
            i_Route = pqrMaestro.getIdPrgRoute().getIdPrgRoute();
        }
        if (pqrMaestro.getIdVehiculo() != null) {
            i_Vehicle = pqrMaestro.getIdVehiculo().getIdVehiculo();
        }

        if (pqrMaestro.getIdPqrTipo() != null) {
            tipo = pqrMaestro.getIdPqrTipo();
        }

        archivos = new ArrayList<>();
        MovilidadUtil.openModal("nov_novedad_infra_wv");
    }

    /**
     * Método que se encarga de cargar la información de un tipo de PQR
     * seleccionado
     */
    public void consultarTipoPqr() {
        if (i_PqrTipo == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar un tipo");
            clasificacionTipo = null;
            responsableTipo = null;
            tiempoRespuestaOtrasAreas = null;
            tiempoRespuestaGH = null;
            return;
        }

        tipo = pqrTipoEJB.find(i_PqrTipo);

        if (tipo == null) {
            MovilidadUtil.addErrorMessage("Tipo NO encontrado");
            clasificacionTipo = null;
            responsableTipo = null;
            tiempoRespuestaOtrasAreas = null;
            tiempoRespuestaGH = null;
            return;
        }

        clasificacionTipo = tipo.getIdPqrClasificacion().getNombre();
        responsableTipo = tipo.getIdPqrResponsable().getResponsable();
        tiempoRespuestaOtrasAreas = String.valueOf(tipo.getTiempoRespuesta());
        tiempoRespuestaGH = String.valueOf(tipo.getObj_time_real());

    }

    public void changeEstatus(int opc) {
        if (!validUser()) {
            MovilidadUtil.addAdvertenciaMessage("El Usuaio no es valido");
            return;
        }
        pqrMaestro.setUsername(user.getUsername());
        pqrMaestro.setModificado(MovilidadUtil.fechaCompletaHoy());
        pqrMaestro.setEstado(opc);
        pqrMaestroEJB.edit(pqrMaestro);
        MovilidadUtil.addSuccessMessage("Accion finalizada con exito.");
        consultar();
    }

    /**
     * Método que se encarga de guardar los datos del cierre de una PQR
     */
    public void guardarCierre() {
        pqrMaestro.setUsername(user.getUsername());
        pqrMaestro.setUsrCierre(user.getUsername());
        pqrMaestro.setEstado(1);
        if (i_PqrProcede != null) {
            pqrMaestro.setId_procede(i_PqrProcede);
        }
        pqrMaestro.setModificado(MovilidadUtil.fechaCompletaHoy());

        if (i_Operador == null) {
            pqrMaestro.setIdEmpleado(null);
        } else {
            pqrMaestro.setIdEmpleado(empleadoFacadeLocal.find(i_Operador));
        }

        if (!archivos.isEmpty()) {
            String path = "/";
            PqrMaestroDocumentos documento;
            for (UploadedFile f : archivos) {
                documento = new PqrMaestroDocumentos();
                documento.setIdPqrMaestro(pqrMaestro);
                documento.setTipoDocumento(1);
                documento.setCreado(MovilidadUtil.fechaCompletaHoy());
                documento.setEstadoReg(0);
                documento.setUsuario(user.getUsername());
                documentosEJB.create(documento);

                path = Util.saveFile(f, pqrMaestro.getIdPqrMaestro(), "pqrGuardar");
                documento.setPathDocumento(path);
                documentosEJB.edit(documento);
            }
            // pqrMaestro.setPathFotos(path);
            pqrMaestroEJB.edit(pqrMaestro);
        }

        pqrMaestroEJB.edit(pqrMaestro);
        archivos = new ArrayList<>();
        MovilidadUtil.addSuccessMessage("Accion finalizada con exito.");
        MovilidadUtil.hideModal("cierre_wv");
        Users email = usersFacadeLocal.findEmail(pqrMaestro.getRegistradaPor());
        envioCorreoNotificacion(pqrMaestro, email.getEmail());
        consultar();
    }

    /**
     * Método que se encarga de guardar los archivos que se cargan en el módulo
     *
     * @param event Evento que se dispara al cargar un archivo
     */
    public void handleFileUpload(FileUploadEvent event) {
        archivos.add(event.getFile());
        MovilidadUtil.addSuccessMessage("Archivo(s) agregados éxitosamente.");
        MovilidadUtil.updateComponent(":msgs");
    }

    /**
     * Método que se encarga de verificar si una novedad está cerrada y de abrir
     * modal de cierre de una PQR ( en caso de que NO esté cerrada )
     */
    public void onCerrar() {

        if (pqrMaestro.getEstado() == 2) {
            MovilidadUtil.addAdvertenciaMessage("La PQR YA se encuentra cerrada");
            return;
        }
        limpiar();
        listaprocedesino = pqrProcedeFacadeLocal.findAll();
        listaEmpleados = empleadoFacadeLocal.findAllEmpleadosActivos(0);

        if (pqrMaestro.getIdEmpleado() != null) {
            i_Operador = pqrMaestro.getIdEmpleado().getIdEmpleado();
        } else {
            i_Operador = null;
        }

        if (pqrMaestro.getId_procede() != 0) {
            i_PqrProcede = pqrMaestro.getId_procede();
        } else {
            i_PqrProcede = null;
        }

        MovilidadUtil.openModal("cierre_wv");

        archivos = new ArrayList<>();
    }

    public boolean validUser() {
        return user.getAuthorities().stream().anyMatch(rol -> {
            return rol.getAuthority().contains("ROLE_GH");
        });
    }

    // Implementación de la función limpiarCaracteresEspeciales
    public String limpiarCaracteresEspeciales(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("[^a-zA-Z0-9\\s.,-]", "");
    }

    /**
     * Método que se encarga de guardar/modificar una PQR en la base de datos y
     * guardar los archivos que seleccionen en el proceso de guardado/modificado
     *
     * @throws IOException
     */
    public void guardar() throws IOException {
        pqrMaestro.setUsername(user.getUsername());
        pqrMaestro.setIdPqrTipo(tipo);
        pqrMaestro.setIdPqrMedioReporte(pqrMedioReporteEJB.find(i_PqrMedioRep));
        if (i_Vehicle != null) {
            pqrMaestro.setIdVehiculo(vehiculoFacadeLocal.find(i_Vehicle));
        }
        if (i_Route != null) {
            pqrMaestro.setIdPrgRoute(prgRouteFacadeLocal.find(i_Route));
        }
        if (i_PqrFranjaHoraria != null) {
            pqrMaestro.setIdPqrFranjaHoraria(pqrFranjaHorariaEJB.find(i_PqrFranjaHoraria));
        }

        // Limpiar caracteres especiales para los campos relevantes
        pqrMaestro.setReporte(limpiarCaracteresEspeciales(pqrMaestro.getReporte()));
        pqrMaestro.setAnalisis(limpiarCaracteresEspeciales(pqrMaestro.getAnalisis()));
        pqrMaestro.setObsCierre(limpiarCaracteresEspeciales(pqrMaestro.getObsCierre()));
        pqrMaestro.setC1(limpiarCaracteresEspeciales(pqrMaestro.getC1()));
        pqrMaestro.setC2(limpiarCaracteresEspeciales(pqrMaestro.getC2()));
        pqrMaestro.setC3(limpiarCaracteresEspeciales(pqrMaestro.getC3()));
        pqrMaestro.setC4(limpiarCaracteresEspeciales(pqrMaestro.getC4()));
        pqrMaestro.setAa1(limpiarCaracteresEspeciales(pqrMaestro.getAa1()));
        pqrMaestro.setAa2(limpiarCaracteresEspeciales(pqrMaestro.getAa2()));
        pqrMaestro.setAa3(limpiarCaracteresEspeciales(pqrMaestro.getAa3()));
        pqrMaestro.setAa4(limpiarCaracteresEspeciales(pqrMaestro.getAa4()));
        pqrMaestro.setIdGopUnidadFuncional(new GopUnidadFuncional(idGopUF));
        if (pqrMaestro.getIdPqrMaestro() == null) {
            pqrMaestro.setCreado(MovilidadUtil.fechaCompletaHoy());
            pqrMaestro.setModificado(MovilidadUtil.fechaCompletaHoy());
            pqrMaestro.setEstado(1);
            pqrMaestro.setFecha_estimada1(
                    calcularFecha(pqrMaestro.getFecha_radicado(), Integer.parseInt(tiempoRespuestaGH)));
            pqrMaestro.setFecha_estimada2(
                    calcularFecha(pqrMaestro.getFecha_radicado(), Integer.parseInt(tiempoRespuestaOtrasAreas)));
            pqrMaestro.setRegistradaPor(user.getUsername());
            envioCorreoNotificacion(pqrMaestro, null);
            pqrMaestroEJB.create(pqrMaestro);
        } else {
            pqrMaestro.setModificado(MovilidadUtil.fechaCompletaHoy());
            pqrMaestroEJB.edit(pqrMaestro);
        }

        if (!archivos.isEmpty()) {
            String path = "/";
            PqrMaestroDocumentos documento;
            for (UploadedFile f : archivos) {
                documento = new PqrMaestroDocumentos();
                documento.setIdPqrMaestro(pqrMaestro);
                documento.setTipoDocumento(0);
                documento.setCreado(MovilidadUtil.fechaCompletaHoy());
                documento.setEstadoReg(0);
                documento.setUsuario(user.getUsername());
                documentosEJB.create(documento);

                path = Util.saveFile(f, pqrMaestro.getIdPqrMaestro(), "pqrGuardar");
                documento.setPathDocumento(path);
                documentosEJB.edit(documento);
            }
            // pqrMaestro.setPathFotos(path);
            pqrMaestroEJB.edit(pqrMaestro);
        }

        nuevo();

        archivos.clear();
        MovilidadUtil.addSuccessMessage("Se registro la informacion exitosamente.");
        MovilidadUtil.hideModal("nov_novedad_infra_wv");
        pqrMaestro = null;
        consultar();
    }

    // Date fechaDesde = MovilidadUtil.fechaHoy();
    // Date fechaHasta = MovilidadUtil.fechaHoy();
    public Date calcularFecha(Date fechaInicio, int cantidadDias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);

        int diasAgregados = 0;
        while (diasAgregados < cantidadDias) {
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Suma un día
            int diaDeLaSemana = calendar.get(Calendar.DAY_OF_WEEK);

            // Comprueba si es sábado o domingo
            if (diaDeLaSemana == Calendar.SATURDAY || diaDeLaSemana == Calendar.SUNDAY) {
                continue; // No incrementa el contador de días hábiles si es fin de semana
            }

            diasAgregados++; // Incrementa el contador solo si es un día hábil
        }

        // Incluye todos los días festivos y fin de semana dentro del rango calculado
        List<ParamFeriado> listaParamFeriado = paraFeEJB.findAllByFechaMes(
                fechaInicio, calendar.getTime());
        for (ParamFeriado feriado : listaParamFeriado) {
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Añade un día adicional por cada feriado
            while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                calendar.add(Calendar.DAY_OF_MONTH, 1); // Salta el fin de semana si coincide con un feriado
            }
        }

        return calendar.getTime();
    }

    /**
     * Método que se encarga de cargar los datos antes del registro de una PQR
     */
    public void nuevo() {
        if (!validUser()) {
            MovilidadUtil.addAdvertenciaMessage("El Usuaio no es valido");
            return;
        }
        if (idGopUF == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
            return;
        }
        limpiar();
        lstMedios = pqrMedioReporteEJB.findAllByEstadoReg();
        lstTipos = pqrTipoEJB.findAllByEstadoReg();
        listaFranjaHoraria = pqrFranjaHorariaEJB.findAllByEstadoReg();
        listaRoutes = prgRouteFacadeLocal.findByUnidadFuncional(idGopUF);
        listaVehicles = vehiculoFacadeLocal.findByidGopUnidadFuncAndTipo(idGopUF, 0);
        clasificacionTipo = "";
        responsableTipo = "";
        tiempoRespuestaOtrasAreas = "";
        tiempoRespuestaGH = "";

        pqrMaestro = new PqrMaestro();
        archivos = new ArrayList<>();
        MovilidadUtil.openModal("nov_novedad_infra_wv");
    }

    public void limpiar() {
        i_PqrTipo = null;
        i_PqrMedioRep = null;
        i_Operador = null;
        i_PqrFranjaHoraria = null;
        i_PqrProcede = null;
        i_Route = null;
        i_Vehicle = null;
    }

    public void envioCorreoNotificacion(PqrMaestro pqrMaestro, String email) { //1 Creación proceso, 2 confirmación agenda
        try {
            NotificacionCorreoConf conf = NCCEJB.find(1);
            if (conf == null) {
                return;
            }
            if ( email != null ){
                NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(ConstantsUtil.TEMPLATE_NOTIFICACION_PQR_INSUMO);
                if (template == null) {
                    return;
                }
                
                Map mapa = SendMails.getMailParams(conf, template);
                Map mailProperties = new HashMap();
                mailProperties.put("radicado", pqrMaestro.getRadicado());
                SendMails.sendEmail(mapa,
                        mailProperties,
                        "Se encuentra insumo al radicado " + pqrMaestro.getRadicado(),
                        "",
                        email,
                        "Notificaciones PQR", null);
            }else {
                NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(ConstantsUtil.TEMPLATE_NOTIFICACION_PQR);
                if (template == null) {
                    return;
                }
                notificacionProceso = notificacionProcesosEjb.findByCodigo(pqrMaestro.getIdPqrTipo().getIdPqrResponsable().getCodigoNotificacion());
                String destinatarios = "";
                if (notificacionProceso != null) {
                    destinatarios = notificacionProceso.getEmails();
                    if (notificacionProceso.getNotificacionProcesoDetList() != null) {
                        String destinatariosByUf = "";
                        destinatariosByUf = MovilidadUtil.obtenerCorreosByUf(notificacionProceso.getNotificacionProcesoDetList(), pqrMaestro.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
                        if (destinatariosByUf != null) {
                            destinatarios = destinatarios + "," + destinatariosByUf;
                        }
                    }
                    Map mapa = SendMails.getMailParams(conf, template);
                    Map mailProperties = new HashMap();
                    SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    mailProperties.put("radicado", pqrMaestro.getRadicado());
                    mailProperties.put("fechaRadicado", formateador.format(pqrMaestro.getFecha_radicado()));
                    mailProperties.put("fechaEvento", formateador.format(pqrMaestro.getFecha_evento()));
                    mailProperties.put("fechaEstimada", formateador.format(pqrMaestro.getFecha_estimada2()));
                    mailProperties.put("tipoPqr", pqrMaestro.getIdPqrTipo().getDescripcion());
                    mailProperties.put("clasificacion", clasificacionTipo);
                    mailProperties.put("responsable", responsableTipo);
                    mailProperties.put("tiempoRespuesta", tiempoRespuestaOtrasAreas);
                    mailProperties.put("medioReporte", pqrMaestro.getIdPqrMedioReporte().getDescripcion());
                    SendMails.sendEmail(mapa,
                            mailProperties,
                            notificacionProceso.getNombreProceso() + " - " + pqrMaestro.getRadicado(),
                            "",
                            destinatarios,
                            "Notificaciones PQR", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private StreamedContent file;

    public void generateExcel(PqrMaestro pqrMaestro) {
        listaRespaldo = pqrMaestroRespaldoEJB.findByIdPqr(pqrMaestro.getIdPqrMaestro());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato deseado

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            // Crear fila de encabezado
            Row headerRow = sheet.createRow(0);
            String[] headers = {"idRespaldo", "idPqrMaestroRespaldo", "fecha_evento", "fecha_radicado", "fecha_estimada1", "fecha_estimada2",
                "radicado", "reporte", "analisis", "rpCedula", "rpNombre", "rpDireccion", "rpTelefono", "rpEmail",
                "estado", "obsCierre", "usrCierre", "fechaCierre", "registradaPor", "c1", "c2", "c3", "c4", "ar1",
                "af1", "aa1", "ar2", "af2", "aa2", "ar3", "af3", "aa3", "ar4", "af4", "aa4", "username", "paradero",
                "id_procede", "creado", "modificado", "estadoReg"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Llenar datos
            int rowIndex = 1;
            for (PqrMaestroRespaldo item : listaRespaldo) {
                Row row = sheet.createRow(rowIndex++);

                Cell cell = row.createCell(0);
                cell.setCellValue(item.getIdRespaldo() != null ? item.getIdRespaldo() : 0);

                cell = row.createCell(1);
                cell.setCellValue(item.getIdPqrMaestroRespaldo() != null ? item.getIdPqrMaestroRespaldo() : 0);

                cell = row.createCell(2);
     

                cell = row.createCell(3);
                cell.setCellValue(item.getFecha_radicado() != null ? dateFormat.format(item.getFecha_radicado()) : "");

                cell = row.createCell(4);
                cell.setCellValue(item.getFecha_estimada1() != null ? dateFormat.format(item.getFecha_estimada1()) : "");

                cell = row.createCell(5);
                cell.setCellValue(item.getFecha_estimada2() != null ? dateFormat.format(item.getFecha_estimada2()) : "");

                cell = row.createCell(6);
                cell.setCellValue(item.getRadicado() != null ? item.getRadicado() : "");
 
                    
                    cell = row.createCell(7);
                    cell.setCellValue(item.getReporte() != null ? item.getReporte() : "");
                    
                    cell = row.createCell(8); 
                cell.setCellValue(item.getAnalisis() != null ? item.getAnalisis() : "");

                cell = row.createCell(9);
                cell.setCellValue(item.getRpCedula() != null ? item.getRpCedula() : "");

                cell = row.createCell(10);
                cell.setCellValue(item.getRpNombre() != null ? item.getRpNombre() : "");

                cell = row.createCell(11);
                cell.setCellValue(item.getRpDireccion() != null ? item.getRpDireccion() : "");

                cell = row.createCell(12);
                cell.setCellValue(item.getRpTelefono() != null ? item.getRpTelefono() : "");

                cell = row.createCell(13);
                cell.setCellValue(item.getRpEmail() != null ? item.getRpEmail() : "");

                cell = row.createCell(14);
                cell.setCellValue(item.getEstado() != null ? item.getEstado() : 0);

                cell = row.createCell(15);
                cell.setCellValue(item.getObsCierre() != null ? item.getObsCierre() : "");

                cell = row.createCell(16);
                cell.setCellValue(
                        item.getUsrCierre() != null ? item.getUsrCierre() : "");

                cell = row.createCell(17);
                cell.setCellValue(
                        item.getFechaCierre() != null ? dateFormat.format(item.getFechaCierre()) : "");

                cell = row.createCell(18);
                cell.setCellValue(item.getRegistradaPor() != null ? item.getRegistradaPor() : "");

                cell = row.createCell(19);
                cell.setCellValue(item.getC1() != null ? item.getC1() : "");

                cell = row.createCell(20);
                cell.setCellValue(item.getC2() != null ? item.getC2() : "");

                cell = row.createCell(21);
                cell.setCellValue(item.getC3() != null ? item.getC3() : "");

                cell = row.createCell(22);
                cell.setCellValue(item.getC4() != null ? item.getC4() : "");

                cell = row.createCell(23);
                cell.setCellValue(item.getAr1() != null ? item.getAr1() : "");

                cell = row.createCell(24);
                cell.setCellValue(item.getAf1() != null ? dateFormat.format(item.getAf1()) : "");

                cell = row.createCell(25);
                cell.setCellValue(item.getAa1() != null ? item.getAa1() : "");

                cell = row.createCell(26);
                cell.setCellValue(item.getAr2() != null ? item.getAr2() : "");

                cell = row.createCell(27);
                cell.setCellValue(item.getAf2() != null ? dateFormat.format(item.getAf2()) : "");

                cell = row.createCell(28);
                cell.setCellValue(item.getAa2() != null ? item.getAa2() : "");

                cell = row.createCell(29);
                cell.setCellValue(item.getAr3() != null ? item.getAr3() : "");

                cell = row.createCell(30);
                cell.setCellValue(item.getAf3() != null ? dateFormat.format(item.getAf3()) : "");

                cell = row.createCell(31);
                cell.setCellValue(item.getAa3() != null ? item.getAa3() : "");

                cell = row.createCell(32);
                cell.setCellValue(item.getAr4() != null ? item.getAr4() : "");

                cell = row.createCell(33);
                cell.setCellValue(item.getAf4() != null ? dateFormat.format(item.getAf4()) : "");

                cell = row.createCell(34);
                cell.setCellValue(item.getAa4() != null ? item.getAa4() : "");

                cell = row.createCell(35);
                cell.setCellValue(item.getUsername() != null ? item.getUsername() : "");

                cell = row.createCell(36);
                cell.setCellValue(item.getParadero() != null ? item.getParadero() : "");

                cell = row.createCell(37);
                cell.setCellValue(item.getId_procede());

                cell = row.createCell(38);
                cell.setCellValue(item.getCreado() != null ? dateFormat.format(item.getCreado()) : "");

                cell = row.createCell(39);
                cell.setCellValue(item.getModificado() != null ? dateFormat.format(item.getModificado()) : "");

                cell = row.createCell(40);
                cell.setCellValue(item.getEstadoReg());
            }

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                workbook.write(out);
                InputStream inputStream = new ByteArrayInputStream(out.toByteArray());
                file = new DefaultStreamedContent(inputStream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Historico.xlsx");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StreamedContent getFile() {
        return file;
    }

    public List<PqrMaestro> getList() {
        return list;
    }

    public void setList(List<PqrMaestro> list) {
        this.list = list;
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

    public PqrMaestro getPqrMaestro() {
        return pqrMaestro;
                        
    }

    public void setPqrMaestro(PqrMaestro pqrMaestro) {
        this.pqrMaestro = pqrMaestro;
    }

    public List<UploadedFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }

    public List<PqrTipo> getLstTipos() {
        return lstTipos;
    }

    public void setLstTipos(List<PqrTipo> lstTipos) {
        this.lstTipos = lstTipos;
    }

    public List<PqrMedioReporte> getLstMedios() {
        return lstMedios;
    }

    public void setLstMedios(List<PqrMedioReporte> lstMedios) {
        this.lstMedios = lstMedios;
    }

    public Integer getI_PqrMedioRep() {
        return i_PqrMedioRep;
    }

    public void setI_PqrMedioRep(Integer i_PqrMedioRep) {
        this.i_PqrMedioRep = i_PqrMedioRep;
    }

    public Integer getI_PqrProcede() {
        return i_PqrProcede;
    }

    public void setI_PqrProcede(Integer i_PqrProcede) {
        this.i_PqrProcede = i_PqrProcede;
    }

    public Integer getI_PqrTipo() {
        return i_PqrTipo;
    }

    public void setI_PqrTipo(Integer i_PqrTipo) {
        this.i_PqrTipo = i_PqrTipo;
    }

    public String getClasificacionTipo() {
        return clasificacionTipo;
    }

    public void setClasificacionTipo(String clasificacionTipo) {
        this.clasificacionTipo = clasificacionTipo;
    }

    public String getResponsableTipo() {
        return responsableTipo;
    }

    public void setResponsableTipo(String responsableTipo) {
        this.responsableTipo = responsableTipo;
    }

    public String getTiempoRespuesta() {
        return tiempoRespuestaOtrasAreas;
    }

    public void setTiempoRespuesta(String tiempoRespuestaOtrasAreas) {
        this.tiempoRespuestaOtrasAreas = tiempoRespuestaOtrasAreas;
    }

    public boolean isB_oligatorio() {
        return b_oligatorio;
    }

    public void setB_oligatorio(boolean b_oligatorio) {
        this.b_oligatorio = b_oligatorio;
    }

    public List<PqrProcede> getListaprocedesino() {
        return listaprocedesino;
    }

    public void setListaprocedesino(List<PqrProcede> listaprocedesino) {
        this.listaprocedesino = listaprocedesino;
    }

    public List<PqrFranjaHoraria> getListaFranjaHoraria() {
        return listaFranjaHoraria;
    }

    public void setListaFranjaHoraria(List<PqrFranjaHoraria> listaFranjaHoraria) {
        this.listaFranjaHoraria = listaFranjaHoraria;
    }

    public List<PrgRoute> getListaRoutes() {
        return listaRoutes;
    }

    public void setListaRoutes(List<PrgRoute> listaRoutes) {
        this.listaRoutes = listaRoutes;
    }

    public List<Vehiculo> getListaVehicles() {
        return listaVehicles;
    }

    public void setListaVehicles(List<Vehiculo> listaVehicles) {
        this.listaVehicles = listaVehicles;
    }

    public Integer getI_PqrFranjaHoraria() {
        return i_PqrFranjaHoraria;
    }

    public void setI_PqrFranjaHoraria(Integer i_PqrFranjaHoraria) {
        this.i_PqrFranjaHoraria = i_PqrFranjaHoraria;
    }

    public Integer getI_Route() {
        return i_Route;
    }

    public void setI_Route(Integer i_Route) {
        this.i_Route = i_Route;
    }

    public Integer getI_Vehicle() {
        return i_Vehicle;
    }

    public void setI_Vehicle(Integer i_Vehicle) {
        this.i_Vehicle = i_Vehicle;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public Integer getI_Operador() {
        return i_Operador;
    }

    public void setI_Operador(Integer i_Operador) {
        this.i_Operador = i_Operador;
    }

}
