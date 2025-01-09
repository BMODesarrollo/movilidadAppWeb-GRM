package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.GenericaSolicitudCambioFacadeLocal;
import com.movilidad.ejb.GenericaSolicitudFacadeLocal;
import com.movilidad.ejb.GenericaSolicitudPermisoFacadeLocal;
import com.movilidad.ejb.GenericaTokenFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.GenericaSolicitud;
import com.movilidad.model.GenericaSolicitudCambio;
import com.movilidad.model.GenericaSolicitudPermiso;
import com.movilidad.model.GenericaToken;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.ParamAreaUsr;
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
@Named(value = "genericaSolicitudesGenBean")
@ViewScoped
public class GenericaSolicitudesManagedBean implements Serializable {

    @EJB
    private GenericaSolicitudFacadeLocal solicitudEjb;

    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaEjb;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;

    @EJB
    private GenericaSolicitudCambioFacadeLocal genericaSolicitudCambioEjb;

    @EJB
    private GenericaSolicitudPermisoFacadeLocal genericaSolicitudPermisoEjb;

    @EJB
    private GenericaTokenFacadeLocal genericaTokenEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;

    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private GenericaSolicitud genericaSolicitud;
    private GenericaSolicitudCambio genericaSolicitudCambio;
    private GenericaSolicitudPermiso genericaSolicitudPermiso;
    private Date fechaInicio;
    private Date fechaFin;
    private String tipo_solicitud;
    private String logo;

    private Integer idArea;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean b_requiere_soporte;
    private boolean b_devuelve_tiempo;
    private boolean b_rol = validarRol();

    private List<GenericaSolicitud> lstSolicitudes;
    private ParamAreaUsr pau;
    private Map<String, String> configEmpresaMap;
    private List<ConfigEmpresa> lista;
    private List<GopUnidadFuncional> lstUnidadesFuncionales;

    @PostConstruct
    public void init() {
        fechaInicio = new Date();
        fechaFin = new Date();

        logo = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO);

        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        idArea = obtenerArea(pau);
        if (idArea != null) {
            lstSolicitudes = solicitudEjb.findByDateRange(
                    fechaInicio, fechaFin, idArea,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        }
    }

    private Integer obtenerArea(ParamAreaUsr paramAreaUsr) {
        if (paramAreaUsr != null) {
            if (paramAreaUsr.getIdParamArea() != null) {
                return pau.getIdParamArea().getIdParamArea();
            }
        }
        return null;
    }

    public void obtenerInfoAdicional() {

        if (genericaSolicitud.getGenericaSolicitudCambioList().size() > 0) {
            genericaSolicitudCambio = genericaSolicitudCambioEjb.findBySolicitud(genericaSolicitud.getIdGenericaSolicitud());
            genericaSolicitudPermiso = null;
            b_requiere_soporte = (genericaSolicitud.getRequiereSoporte() == 1);
            b_devuelve_tiempo = (genericaSolicitud.getReponeTiempo() == 1);
        } else if (genericaSolicitud.getGenericaSolicitudPermisoList().size() > 0) {
            genericaSolicitudCambio = null;
            b_requiere_soporte = (genericaSolicitud.getRequiereSoporte() == 1);
            b_devuelve_tiempo = (genericaSolicitud.getReponeTiempo() == 1);
            genericaSolicitudPermiso = genericaSolicitudPermisoEjb.findBySolicitud(genericaSolicitud.getIdGenericaSolicitud());
        }
    }

    public void actualizarDatos() {
        genericaSolicitud.setRequiereSoporte(b_requiere_soporte ? 1 : 0);
        genericaSolicitud.setReponeTiempo(b_devuelve_tiempo ? 1 : 0);
        solicitudEjb.edit(genericaSolicitud);
        MovilidadUtil.addSuccessMessage("Los datos de la solicitud han sido actualizados éxitosamente");
    }

    public void getByDateRange() {
        idArea = obtenerArea(pau);
        if (idArea != null) {
            lstSolicitudes = solicitudEjb.findByDateRange(fechaInicio, fechaFin, idArea,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        }

        if (lstSolicitudes == null || lstSolicitudes.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos para el rango de fechas seleccionado");
        }
    }

    @Transactional
    public void aprobarSolicitud() {
        GenericaToken prgToken = genericaSolicitud.getIdGenericaToken();

        if (prgToken != null) {
            genericaSolicitud.setUserAprueba(user.getUsername());
            solicitudEjb.edit(genericaSolicitud);
            prgToken.setActivo(2);
            genericaTokenEjb.edit(prgToken);
            notificar();
            MovilidadUtil.addSuccessMessage("La solicitud ha sido aprobada con éxito");
        }
    }

    @Transactional
    public void rechazarSolicitud() {
        GenericaToken prgToken = genericaSolicitud.getIdGenericaToken();

        if (prgToken != null) {
            genericaSolicitud.setUserAprueba(user.getUsername());
            solicitudEjb.edit(genericaSolicitud);
            prgToken.setActivo(3);
            genericaTokenEjb.edit(prgToken);
            notificar();
            MovilidadUtil.addSuccessMessage("La solicitud ha sido rechazada con éxito");
        }
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_SOLICITUDES_GEN);
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

        if (genericaSolicitud.getGenericaSolicitudCambioList().size() > 0) {
            tipo_solicitud = "CAMBIO";
        } else if (genericaSolicitud.getGenericaSolicitudPermisoList().size() > 0) {
            tipo_solicitud = "PERMISO";
        }

        String estado = genericaSolicitud.getIdGenericaToken().getActivo().equals(2) ? "aprobada" : "rechazada";
        mailProperties.put("titulo", "NOTIFICACION SOLICITUDES DE CAMBIOS Y/O PERMISOS");
        mailProperties.put("tipo", tipo_solicitud);

        mailProperties.put("logo", logo);

        mailProperties.put("fecha", Util.dateFormat(genericaSolicitud.getFechaSolicitud()));
        mailProperties.put("cedula", genericaSolicitud.getIdGenericaToken().getIdEmpleado().getIdentificacion());
        mailProperties.put("operador", genericaSolicitud.getIdGenericaToken().getIdEmpleado().getNombres().concat(" ").concat(genericaSolicitud.getIdGenericaToken().getIdEmpleado().getApellidos()));
        mailProperties.put("estado", genericaSolicitud.getIdGenericaToken().getActivo().equals(1) ? "ESTUDIO" : genericaSolicitud.getIdGenericaToken().getActivo().equals(2) ? "APROBADO" : "RECHAZADO");
        String subject = "Su solicitud de " + tipo_solicitud + " ha sido " + estado;
        String destinatarios = genericaSolicitud.getIdGenericaToken().getIdEmpleado().getEmailCorporativo();

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);

    }

    boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
//            System.out.println(auth.getAuthority());
            if (auth.getAuthority().equals("ROLE_PROFGEN")) {
                return true;
            }

        }
        return false;
    }

    public GenericaSolicitud getGenericaSolicitud() {
        return genericaSolicitud;
    }

    public void setGenericaSolicitud(GenericaSolicitud genericaSolicitud) {
        this.genericaSolicitud = genericaSolicitud;
    }

    public List<GenericaSolicitud> getLstSolicitudes() {
        return lstSolicitudes;
    }

    public void setLstSolicitudes(List<GenericaSolicitud> lstSolicitudes) {
        this.lstSolicitudes = lstSolicitudes;
    }

    public GenericaSolicitudCambio getGenericaSolicitudCambio() {
        return genericaSolicitudCambio;
    }

    public void setGenericaSolicitudCambio(GenericaSolicitudCambio genericaSolicitudCambio) {
        this.genericaSolicitudCambio = genericaSolicitudCambio;
    }

    public GenericaSolicitudPermiso getGenericaSolicitudPermiso() {
        return genericaSolicitudPermiso;
    }

    public void setGenericaSolicitudPermiso(GenericaSolicitudPermiso genericaSolicitudPermiso) {
        this.genericaSolicitudPermiso = genericaSolicitudPermiso;
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

    public ParamAreaUsr getPau() {
        return pau;
    }

    public void setPau(ParamAreaUsr pau) {
        this.pau = pau;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Map<String, String> getConfigEmpresaMap() {
        return configEmpresaMap;
    }

    public void setConfigEmpresaMap(Map<String, String> configEmpresaMap) {
        this.configEmpresaMap = configEmpresaMap;
    }

    public List<ConfigEmpresa> getLista() {
        return lista;
    }

    public void setLista(List<ConfigEmpresa> lista) {
        this.lista = lista;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<GopUnidadFuncional> getLstUnidadesFuncionales() {
        return lstUnidadesFuncionales;
    }

    public void setLstUnidadesFuncionales(List<GopUnidadFuncional> lstUnidadesFuncionales) {
        this.lstUnidadesFuncionales = lstUnidadesFuncionales;
    }

}
