package com.movilidad.jsf;

import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.ejb.CableRevisionDiaFacadeLocal;
import com.movilidad.ejb.CableRevisionDiaHorarioFacadeLocal;
import com.movilidad.ejb.CableRevisionDiaRtaFacadeLocal;
import com.movilidad.ejb.CableRevisionEstacionFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.model.CableEstacion;
import com.movilidad.model.CableRevisionDia;
import com.movilidad.model.CableRevisionDiaHorario;
import com.movilidad.model.CableRevisionDiaRta;
import com.movilidad.model.CableRevisionEstacion;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.RevisionDiaUtil;
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
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "cableRevisionDiaRtaBean")
@ViewScoped
public class CableRevisionDiaRtaBean implements Serializable {

    @EJB
    private CableRevisionDiaRtaFacadeLocal cableRevisionDiaRtaEjb;
    @EJB
    private CableRevisionDiaFacadeLocal cableRevisionDiaEjb;
    @EJB
    private CableRevisionDiaHorarioFacadeLocal cableRevisionDiaHorarioEjb;
    @EJB
    private CableRevisionEstacionFacadeLocal cableRevisionEstacionEjb;
    @EJB
    private CableEstacionFacadeLocal cableEstacionEjb;
    @EJB
    private UsersFacadeLocal usersEjb;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    private CableRevisionDiaRta revisionDiaRta;
    private CableRevisionDia selected;
    private CableRevisionDia cableRevisionDia;
    private Empleado empleado;

    private boolean isEditing;
    private boolean flagRespuestasEdicion;

    private Integer i_CableRevisionDiaHorario;
    private Integer i_CableEstacion;
    private Date fecha;
    private Date fechaDesde;
    private Date fechaHasta;
    private Date fechaDesdeDetallado;
    private Date fechaHastaDetallado;

    private List<CableRevisionDiaRta> lstPreguntas;
    private List<CableRevisionDiaRta> lstReporteDetallado;
    private List<CableRevisionDia> lstCableRevisionDias;
    private List<CableRevisionDiaHorario> lstCableRevisionDiaHorarios;
    private List<CableEstacion> lstCableEstaciones;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        fechaDesde = MovilidadUtil.fechaHoy();
        fechaHasta = MovilidadUtil.fechaHoy();
        fechaDesdeDetallado = MovilidadUtil.fechaHoy();
        fechaHastaDetallado = MovilidadUtil.fechaHoy();
        consultar();
    }

    public void consultar() {
        lstCableRevisionDias = cableRevisionDiaEjb.findAllByDateRange(fechaDesde, fechaHasta);
    }

    public void consultarReporteDetallado() {
        lstReporteDetallado = cableRevisionDiaRtaEjb.findByDateRange(fechaDesdeDetallado, fechaHastaDetallado);
    }

    public void nuevo() {
        lstPreguntas = new ArrayList<>();
        lstCableEstaciones = cableEstacionEjb.findByEstadoReg();
        lstCableRevisionDiaHorarios = cableRevisionDiaHorarioEjb.findAllByEstadoReg();
        i_CableRevisionDiaHorario = null;
        i_CableEstacion = null;
        cableRevisionDia = new CableRevisionDia();
        empleado = new Empleado();
        selected = null;
        fecha = MovilidadUtil.fechaHoy();
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        lstCableRevisionDiaHorarios = cableRevisionDiaHorarioEjb.findAllByEstadoReg();
        lstCableEstaciones = cableEstacionEjb.findByEstadoReg();
        revisionDiaRta = cableRevisionDiaRtaEjb.findLastRecordByRevisionDia(selected.getIdCableRevisionDia());

        i_CableRevisionDiaHorario = revisionDiaRta.getIdCableRevisionDiaHorario().getIdCableRevisionDiaHorario();

        i_CableEstacion = selected.getIdCableEstacion().getIdCableEstacion();
        fecha = selected.getFecha();

        lstPreguntas = cableRevisionDiaRtaEjb.findByHorarioAndDia(i_CableRevisionDiaHorario, selected.getIdCableRevisionDia());
        cableRevisionDia = selected;

    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    private void guardarTransactional() {
        empleado = usersEjb.findUserForUsername(user.getUsername()).getIdEmpleado();
        String validacion = validarDatos();
        List<CableRevisionDiaRta> lstNovedades = new ArrayList<>();

        if (validacion == null) {

            if (isEditing) {
                cableRevisionDia.setUsername(user.getUsername());
                cableRevisionDia.setFecha(fecha);
                cableRevisionDia.setModificado(MovilidadUtil.fechaCompletaHoy());
                cableRevisionDiaEjb.edit(cableRevisionDia);

                for (CableRevisionDiaRta respuesta : lstPreguntas) {
                    respuesta.setIdCableRevisionDia(cableRevisionDia);
                    respuesta.setIdEmpleado(empleado);
                    respuesta.setIdCableRevisionDiaHorario(cableRevisionDiaHorarioEjb.find(i_CableRevisionDiaHorario));

                    if (flagRespuestasEdicion) {
                        respuesta.setUsername(user.getUsername());
                        respuesta.setEstadoReg(0);
                        respuesta.setCreado(MovilidadUtil.fechaCompletaHoy());

                        if (respuesta.getRespuesta() != null) {
                            if (respuesta.getIdCableRevisionEstacion().getIdCableRevisionActividad().getNotifica() == 1) {
                                if (respuesta.getRespuesta() > respuesta.getIdCableRevisionEstacion().getIdCableRevisionActividad().getLimiteSuperior()
                                        || respuesta.getRespuesta() < respuesta.getIdCableRevisionEstacion().getIdCableRevisionActividad().getLimiteInferior()) {
                                    lstNovedades.add(respuesta);
                                }
                            }
                        }
                        cableRevisionDiaRtaEjb.create(respuesta);
                    } else {
                        respuesta.setUsername(user.getUsername());
                        respuesta.setModificado(MovilidadUtil.fechaCompletaHoy());
                        cableRevisionDiaRtaEjb.edit(respuesta);
                    }
                }

                if (!lstNovedades.isEmpty()) {
                    notificar(lstNovedades);
                }

                MovilidadUtil.hideModal("wlvCableRevisionDiaRta");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {

                cableRevisionDia = cableRevisionDiaEjb.findByFecha(i_CableEstacion, fecha);

                if (cableRevisionDia == null) {
                    cableRevisionDia = new CableRevisionDia();
                    cableRevisionDia.setFecha(fecha);
                    cableRevisionDia.setIdCableEstacion(cableEstacionEjb.find(i_CableEstacion));
                    cableRevisionDia.setUsername(user.getUsername());
                    cableRevisionDia.setEstadoReg(0);
                    cableRevisionDia.setCreado(MovilidadUtil.fechaCompletaHoy());
                    cableRevisionDiaEjb.create(cableRevisionDia);
                }

                for (CableRevisionDiaRta respuesta : lstPreguntas) {
                    respuesta.setIdCableRevisionDia(cableRevisionDia);
                    respuesta.setIdEmpleado(empleado);
                    respuesta.setIdCableRevisionDiaHorario(cableRevisionDiaHorarioEjb.find(i_CableRevisionDiaHorario));
                    respuesta.setUsername(user.getUsername());
                    respuesta.setEstadoReg(0);
                    respuesta.setCreado(MovilidadUtil.fechaCompletaHoy());
                    cableRevisionDiaRtaEjb.create(respuesta);

                    if (respuesta.getRespuesta() != null) {
                        if (respuesta.getIdCableRevisionEstacion().getIdCableRevisionActividad().getNotifica() == 1) {
                            if (respuesta.getRespuesta() > respuesta.getIdCableRevisionEstacion().getIdCableRevisionActividad().getLimiteSuperior()
                                    || respuesta.getRespuesta() < respuesta.getIdCableRevisionEstacion().getIdCableRevisionActividad().getLimiteInferior()) {
                                lstNovedades.add(respuesta);
                            }
                        }
                    }
                }

                if (!lstNovedades.isEmpty()) {
                    notificar(lstNovedades);
                }

                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
            consultar();
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    /**
     * Carga las preguntas relacionadas a una estación en la vista donde se
     * diligencian las respuestas filtrándolas por franja horaria
     */
    public void cargarPreguntasPorFranjaHoraria() {

        if (isEditing) {
            lstPreguntas = cableRevisionDiaRtaEjb.findByHorarioAndDia(i_CableRevisionDiaHorario, selected.getIdCableRevisionDia());

            if (lstPreguntas == null || lstPreguntas.isEmpty()) {
                cargarPreguntasEnVista();
                flagRespuestasEdicion = true;
            }
        }
    }

    /**
     * Carga las preguntas relacionadas a una estación en la vista donde se
     * diligencian las respuestas.
     */
    public void cargarPreguntasEnVista() {
        List<CableRevisionEstacion> lstPreguntasAux = cableRevisionEstacionEjb.findByEstacion(i_CableEstacion);

        if (lstPreguntasAux != null && !lstPreguntasAux.isEmpty()) {
            lstPreguntas = crearListaPreguntas(lstPreguntasAux);
        } else {
            lstPreguntas = null;
            MovilidadUtil.addErrorMessage("NO se encontraron preguntas relacionadas a la estación seleccionada");
        }
    }

    /*
     * Parámetros para el envío de correos
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        return mapa;
    }

    /**
     * Realiza el envío de correo de la novedad registrada a las partes
     * interesadas
     */
    private void notificar(List<CableRevisionDiaRta> respuestas) {
        CableRevisionDiaRta respuesta = respuestas.get(0);
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(respuesta.getIdCableRevisionDia().getFecha()));
        mailProperties.put("estacion", respuesta.getIdCableRevisionDia().getIdCableEstacion().getNombre());
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(respuesta.getCreado()));

        String subject = "Se han encontrado registros con valores fuera de los limites establecidos";
        String destinatarios = respuesta.getIdCableRevisionEstacion() != null ? respuesta.getIdCableRevisionEstacion().getIdCableRevisionActividad().getEmails() : "";

        RevisionDiaUtil.sendEmail(mapa, mailProperties, subject,
                "", destinatarios, "Notificaciones RIGEL",
                respuestas);
    }

    /**
     * Retorna el listado de preguntas que se van a diligenciar en el formato
     * que se va a guardar/modificar en la base de datos.
     *
     * @param lista
     * @return Lista de preguntas que se van a diligenciar
     */
    private List<CableRevisionDiaRta> crearListaPreguntas(List<CableRevisionEstacion> lista) {
        List<CableRevisionDiaRta> listaFinal = new ArrayList<>();

        for (CableRevisionEstacion cableRevisionEstacion : lista) {
            CableRevisionDiaRta item = new CableRevisionDiaRta();
            item.setIdCableRevisionEstacion(cableRevisionEstacion);
            listaFinal.add(item);
        }

        return listaFinal;
    }

    private String validarDatos() {

        if (empleado == null) {
            return "NO se encuentra empleado asociado al usuario actual";
        }
        if (i_CableRevisionDiaHorario == null) {
            return "La franja horaria es OBLIGATORIA";
        }

        if (isEditing) {
            if (cableRevisionDiaRtaEjb.verificarRegistro(fecha, selected.getIdCableRevisionDia(), i_CableEstacion, i_CableRevisionDiaHorario) != null) {
                return "YA existe un registro con los parámetros ingresados";
            }
        } else {
            if (lstCableRevisionDias != null) {
                if (cableRevisionDiaRtaEjb.verificarRegistro(fecha, 0, i_CableEstacion, i_CableRevisionDiaHorario) != null) {
                    return "YA existe un registro con los parámetros ingresados";
                }
            }
        }
        return null;
    }

    public CableRevisionDia getSelected() {
        return selected;
    }

    public void setSelected(CableRevisionDia selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public Integer getI_CableRevisionDiaHorario() {
        return i_CableRevisionDiaHorario;
    }

    public void setI_CableRevisionDiaHorario(Integer i_CableRevisionDiaHorario) {
        this.i_CableRevisionDiaHorario = i_CableRevisionDiaHorario;
    }

    public List<CableRevisionDia> getLstCableRevisionDias() {
        return lstCableRevisionDias;
    }

    public void setLstCableRevisionDias(List<CableRevisionDia> lstCableRevisionDias) {
        this.lstCableRevisionDias = lstCableRevisionDias;
    }

    public List<CableRevisionDiaHorario> getLstCableRevisionDiaHorarios() {
        return lstCableRevisionDiaHorarios;
    }

    public void setLstCableRevisionDiaHorarios(List<CableRevisionDiaHorario> lstCableRevisionDiaHorarios) {
        this.lstCableRevisionDiaHorarios = lstCableRevisionDiaHorarios;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public CableRevisionDia getCableRevisionDia() {
        return cableRevisionDia;
    }

    public void setCableRevisionDia(CableRevisionDia cableRevisionDia) {
        this.cableRevisionDia = cableRevisionDia;
    }

    public Integer getI_CableEstacion() {
        return i_CableEstacion;
    }

    public void setI_CableEstacion(Integer i_CableEstacion) {
        this.i_CableEstacion = i_CableEstacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Date getFechaDesdeDetallado() {
        return fechaDesdeDetallado;
    }

    public void setFechaDesdeDetallado(Date fechaDesdeDetallado) {
        this.fechaDesdeDetallado = fechaDesdeDetallado;
    }

    public Date getFechaHastaDetallado() {
        return fechaHastaDetallado;
    }

    public void setFechaHastaDetallado(Date fechaHastaDetallado) {
        this.fechaHastaDetallado = fechaHastaDetallado;
    }

    public List<CableEstacion> getLstCableEstaciones() {
        return lstCableEstaciones;
    }

    public void setLstCableEstaciones(List<CableEstacion> lstCableEstaciones) {
        this.lstCableEstaciones = lstCableEstaciones;
    }

    public List<CableRevisionDiaRta> getLstPreguntas() {
        return lstPreguntas;
    }

    public void setLstPreguntas(List<CableRevisionDiaRta> lstPreguntas) {
        this.lstPreguntas = lstPreguntas;
    }

    public List<CableRevisionDiaRta> getLstReporteDetallado() {
        return lstReporteDetallado;
    }

    public void setLstReporteDetallado(List<CableRevisionDiaRta> lstReporteDetallado) {
        this.lstReporteDetallado = lstReporteDetallado;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public boolean isFlagRespuestasEdicion() {
        return flagRespuestasEdicion;
    }

    public void setFlagRespuestasEdicion(boolean flagRespuestasEdicion) {
        this.flagRespuestasEdicion = flagRespuestasEdicion;
    }

}
