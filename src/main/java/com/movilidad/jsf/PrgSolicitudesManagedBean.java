package com.movilidad.jsf;

import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgSolicitudCambioFacadeLocal;
import com.movilidad.ejb.PrgSolicitudFacadeLocal;
import com.movilidad.ejb.PrgSolicitudNovedadParamFacadeLocal;
import com.movilidad.ejb.PrgSolicitudPermisoFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.PrgTokenFacadeLocal;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.PrgSolicitud;
import com.movilidad.model.PrgSolicitudCambio;
import com.movilidad.model.PrgSolicitudNovedadParam;
import com.movilidad.model.PrgSolicitudPermiso;
import com.movilidad.model.PrgToken;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "prgSolicitudesBean")
@ViewScoped
public class PrgSolicitudesManagedBean implements Serializable {

    @EJB
    private PrgSolicitudFacadeLocal solicitudEjb;

    @EJB
    private PrgSolicitudCambioFacadeLocal prgSolicitudCambioEjb;

    @EJB
    private PrgSolicitudPermisoFacadeLocal prgSolicitudPermisoEjb;

    @EJB
    private PrgTokenFacadeLocal prgTokenEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;

    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    @EJB
    private PrgSolicitudNovedadParamFacadeLocal novedadParamEjb;
    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private PrgSerconFacadeLocal prgSerconEjb;
    @EJB
    private PrgTcFacadeLocal prgTcEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private PrgSolicitud prgSolicitud;
    private PrgSolicitudCambio prgSolicitudCambio;
    private PrgSolicitudPermiso prgSolicitudPermiso;
    private Date fechaInicio;
    private Date fechaFin;
    private String tipo_solicitud;
    private String logo;
    private Integer consecutivo;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean b_requiere_soporte;
    private boolean b_devuelve_tiempo;
    private boolean b_aprobado;
    private boolean b_rol = validarRol();

    private List<PrgSolicitud> lstSolicitudes;

    @PostConstruct
    public void init() {
        fechaInicio = MovilidadUtil.fechaHoy();
        fechaFin = MovilidadUtil.fechaHoy();
        getByDateRange();
        logo = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO);
    }

    public void obtenerInfoAdicional() {

        if (prgSolicitud.getPrgSolicitudCambioList().size() > 0) {
            prgSolicitudCambio = prgSolicitudCambioEjb.findBySolicitud(prgSolicitud.getIdPrgSolicitud());
            prgSolicitudPermiso = null;
            b_requiere_soporte = (prgSolicitud.getRequiereSoporte() == 1);
            b_devuelve_tiempo = (prgSolicitud.getReponeTiempo() == 1);
            consecutivo = prgSolicitud.getConsecutivo();
        } else if (prgSolicitud.getPrgSolicitudPermisoList().size() > 0) {
            prgSolicitudCambio = null;
            b_requiere_soporte = (prgSolicitud.getRequiereSoporte() == 1);
            b_devuelve_tiempo = (prgSolicitud.getReponeTiempo() == 1);
            prgSolicitudPermiso = prgSolicitudPermisoEjb.findBySolicitud(prgSolicitud.getIdPrgSolicitud());
            consecutivo = prgSolicitud.getConsecutivo();
        }

    }

    @Transactional
    public void actualizarDatos() {
        prgSolicitud.setRequiereSoporte(b_requiere_soporte ? 1 : 0);
        prgSolicitud.setReponeTiempo(b_devuelve_tiempo ? 1 : 0);
        solicitudEjb.edit(prgSolicitud);
        MovilidadUtil.addSuccessMessage("Los datos de la solicitud han sido actualizados éxitosamente");
    }

    public void getByDateRange() {
        lstSolicitudes = solicitudEjb.findByDateRangeAndUnidadFuncional(fechaInicio, fechaFin,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    @Transactional
    public void aprobarSolicitud() {
        PrgToken prgToken = prgSolicitud.getIdPrgToken();

        PrgSolicitudNovedadParam novedadParam = novedadParamEjb.find(1);
        if (novedadParam == null) {
            MovilidadUtil.addErrorMessage("NO se encontró proceso relacionado al envío de novedades");
            return;
        }

        if (prgToken != null) {
            prgToken.setActivo(2);
            prgTokenEjb.edit(prgToken);

            Novedad novedad = new Novedad();
            novedad.setIdEmpleado(prgToken.getIdEmpleado());

            if (prgSolicitud.getPrgSolicitudCambioList().size() > 0) {
                novedad.setIdNovedadTipo(novedadParam.getIdNovedadCambio().getIdNovedadTipo());
                novedad.setIdNovedadTipoDetalle(novedadParam.getIdNovedadCambio());
                novedad.setPuntosPm(novedadParam.getIdNovedadCambio().getPuntosPm());
                novedad.setFecha(new Date());
                novedad.setObservaciones("Se cambia turno con: " + prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdEmpleado().getCodigoTm() + " - " + prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdEmpleado().getNombres().concat(" ").concat(prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdEmpleado().getApellidos()));

                prgSolicitud.setConsecutivo(novedadParam.getConsecCambioTurno());

                novedadParamEjb.aumentarConsecutivoCambio(novedadParam.getIdPrgSolicitudNovedadParam());

                prgTcEjb.realizarCambioOperacion(prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdEmpleado().getIdEmpleado(), prgSolicitud.getIdPrgSercon().getIdEmpleado().getIdEmpleado(), prgSolicitud.getIdPrgSercon().getSercon(), prgSolicitud.getPrgSolicitudCambioList().get(0).getFecha());
                prgTcEjb.realizarCambioOperacion(prgSolicitud.getIdPrgSercon().getIdEmpleado().getIdEmpleado(), prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdEmpleado().getIdEmpleado(), prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getSercon(), prgSolicitud.getPrgSolicitudCambioList().get(0).getFecha());

                prgSerconEjb.cambiarNomina(prgToken.getIdEmpleado().getIdEmpleado(), prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdPrgSercon());
                prgSerconEjb.cambiarNomina(prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdEmpleado().getIdEmpleado(), prgSolicitud.getIdPrgSercon().getIdPrgSercon());
            } else {
                novedad.setFecha(new Date());
                novedad.setIdNovedadTipo(novedadParam.getIdNovedadPermiso().getIdNovedadTipo());
                novedad.setIdNovedadTipoDetalle(novedadParam.getIdNovedadPermiso());
                novedad.setObservaciones("Se cubre turno");
                novedad.setPuntosPm(novedadParam.getIdNovedadPermiso().getPuntosPm());

                prgSolicitud.setConsecutivo(novedadParam.getConsecPermisos());
                novedadParamEjb.aumentarConsecutivoPermiso(novedadParam.getIdPrgSolicitudNovedadParam());
            }

            solicitudEjb.edit(prgSolicitud);

            novedad.setCreado(new Date());
            novedad.setUsername(user.getUsername());
            novedad.setEstadoReg(0);

            if (novedad.getIdNovedadTipoDetalle().getAfectaPm() == 1) {
                novedad.setProcede(1);
                novedad.setPuntosPmConciliados(novedad.getIdNovedadTipoDetalle().getPuntosPm());
            } else {
                novedad.setProcede(0);
                novedad.setPuntosPmConciliados(0);
            }

            novedadEjb.create(novedad);
            notificarCorreoNovedad(novedad);
            MovilidadUtil.addSuccessMessage("Novedad generada éxitosamente");

            notificar(novedadParam.getIdNotificacionProceso().getEmails());
            MovilidadUtil.addSuccessMessage("La solicitud ha sido aprobada con éxito");

            getByDateRange();
        }
    }

    @Transactional
    public void rechazarSolicitud() {
        PrgToken prgToken = prgSolicitud.getIdPrgToken();

        if (prgToken != null) {
            prgSolicitud.setUserAprueba(user.getUsername());
            solicitudEjb.edit(prgSolicitud);
            prgToken.setActivo(3);
            prgTokenEjb.edit(prgToken);
            notificar(null);
            MovilidadUtil.addSuccessMessage("La solicitud ha sido rechazada con éxito");
        }
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_SOLICITUDES);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    private void notificar(String emailsCtrl) {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();

        if (prgSolicitud.getPrgSolicitudCambioList().size() > 0) {
            tipo_solicitud = "CAMBIO";
        } else if (prgSolicitud.getPrgSolicitudPermisoList().size() > 0) {
            tipo_solicitud = "PERMISO";
        }

        String estado = prgSolicitud.getIdPrgToken().getActivo().equals(2) ? "aprobada" : "rechazada";
        mailProperties.put("titulo", "NOTIFICACION SOLICITUDES DE CAMBIOS Y/O PERMISOS");
        mailProperties.put("logo", logo);
        mailProperties.put("tipo", tipo_solicitud);
        mailProperties.put("fecha", Util.dateFormat(prgSolicitud.getFechaSolicitud()));
        mailProperties.put("codigo", prgSolicitud.getIdPrgToken().getIdEmpleado().getCodigoTm());
        mailProperties.put("operador", prgSolicitud.getIdPrgToken().getIdEmpleado().getNombres().concat(" ").concat(prgSolicitud.getIdPrgToken().getIdEmpleado().getApellidos()));
        mailProperties.put("estado", prgSolicitud.getIdPrgToken().getActivo().equals(1) ? "ESTUDIO" : prgSolicitud.getIdPrgToken().getActivo().equals(2) ? "APROBADA" : "RECHAZADA");
        String subject = "Su solicitud de " + tipo_solicitud + " ha sido " + estado;
        String destinatarios;

        if (tipo_solicitud.equals("PERMISO")) {
            destinatarios = prgSolicitud.getIdPrgToken().getIdEmpleado().getEmailCorporativo();
        } else {
            destinatarios = prgSolicitud.getIdPrgToken().getIdEmpleado().getEmailCorporativo() + ","
                    + prgSolicitud.getPrgSolicitudCambioList().get(0).getIdPrgSerconReemplazo().getIdEmpleado().getEmailCorporativo();
        }

        if (prgSolicitud.getIdPrgToken().getActivo().equals(2)) {
            destinatarios = destinatarios + "," + emailsCtrl;
        }

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);

    }

    /*
     * Parámetros para el envío de correos (Novedades PM)
     */
    private Map getMailParamsNovedad() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_NOVEDAD_PM_TEMPLATE);
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
     * Realiza el envío de correo de las novedad registrada a las partes
     * interesadas
     */
    private void notificarCorreoNovedad(Novedad novedad) {
        Map mapa = getMailParamsNovedad();
        Map mailProperties = new HashMap();
        mailProperties.put("fecha", Util.dateFormat(novedad.getFecha()));
        mailProperties.put("logo", logo);
        mailProperties.put("tipo", novedad.getIdNovedadTipo().getNombreTipoNovedad());
        mailProperties.put("detalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
        mailProperties.put("fechas", novedad.getFecha() != null ? Util.dateFormat(novedad.getFecha()) : "");
        mailProperties.put("operador", novedad.getIdEmpleado() != null ? novedad.getIdEmpleado().getCodigoTm() + " - " + novedad.getIdEmpleado().getNombres() + " " + novedad.getIdEmpleado().getApellidos() : "");
        mailProperties.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("observaciones", novedad.getObservaciones());
        String subject = "Novedad " + novedad.getIdNovedadTipo().getNombreTipoNovedad();
        String destinatarios = "";

        //Busqueda Operador Máster
        if (novedad.getIdEmpleado() != null) {
            String correoMaster = "";
            if (novedad.getIdEmpleado().getPmGrupoDetalleList().size() == 1) {
                correoMaster = novedad.getIdEmpleado().getPmGrupoDetalleList().get(0).getIdGrupo().getIdEmpleado().getEmailCorporativo();
            }
            destinatarios = novedad.getIdEmpleado() != null ? correoMaster + "," + novedad.getIdEmpleado().getEmailCorporativo() : "";
        }
        if (novedad.getIdNovedadTipoDetalle().getNotificacion() == 1) {
            if (novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {
                if (destinatarios != null) {
                    destinatarios = destinatarios + "," + novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                } else {
                    destinatarios = novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                }
            }
            SendMails.sendEmail(mapa, mailProperties, subject,
                    "",
                    destinatarios,
                    "Notificaciones RIGEL", null);
        }
    }

    boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
//            System.out.println(auth.getAuthority());
            if (auth.getAuthority().equals("ROLE_PROFOP")) {
                return true;
            }

        }
        return false;
    }

    public boolean validarRolMaster() {
        for (GrantedAuthority auth : user.getAuthorities()) {
//            System.out.println(auth.getAuthority());
            if (auth.getAuthority().equals("ROLE_TC")) {
                return true;
            }

        }
        return false;
    }

    public PrgSolicitud getPrgSolicitud() {
        return prgSolicitud;
    }

    public void setPrgSolicitud(PrgSolicitud prgSolicitud) {
        this.prgSolicitud = prgSolicitud;
    }

    public List<PrgSolicitud> getLstSolicitudes() {
        return lstSolicitudes;
    }

    public void setLstSolicitudes(List<PrgSolicitud> lstSolicitudes) {
        this.lstSolicitudes = lstSolicitudes;
    }

    public PrgSolicitudCambio getPrgSolicitudCambio() {
        return prgSolicitudCambio;
    }

    public void setPrgSolicitudCambio(PrgSolicitudCambio prgSolicitudCambio) {
        this.prgSolicitudCambio = prgSolicitudCambio;
    }

    public PrgSolicitudPermiso getPrgSolicitudPermiso() {
        return prgSolicitudPermiso;
    }

    public void setPrgSolicitudPermiso(PrgSolicitudPermiso prgSolicitudPermiso) {
        this.prgSolicitudPermiso = prgSolicitudPermiso;
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

    public String getTipo_solicitud() {
        return tipo_solicitud;
    }

    public void setTipo_solicitud(String tipo_solicitud) {
        this.tipo_solicitud = tipo_solicitud;
    }

    public boolean isB_requiere_soporte() {
        return b_requiere_soporte;
    }

    public void setB_requiere_soporte(boolean b_requiere_soporte) {
        this.b_requiere_soporte = b_requiere_soporte;
    }

    public boolean isB_devuelve_tiempo() {
        return b_devuelve_tiempo;
    }

    public void setB_devuelve_tiempo(boolean b_devuelve_tiempo) {
        this.b_devuelve_tiempo = b_devuelve_tiempo;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public boolean isB_rol() {
        return b_rol;
    }

    public void setB_rol(boolean b_rol) {
        this.b_rol = b_rol;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isB_aprobado() {
        return b_aprobado;
    }

    public void setB_aprobado(boolean b_aprobado) {
        this.b_aprobado = b_aprobado;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

}
