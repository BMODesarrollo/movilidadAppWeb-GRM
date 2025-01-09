package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.GenericaSolicitudCambioFacadeLocal;
import com.movilidad.ejb.GenericaSolicitudFacadeLocal;
import com.movilidad.ejb.GenericaSolicitudPermisoFacadeLocal;
import com.movilidad.ejb.GenericaTokenFacadeLocal;
import com.movilidad.ejb.ParamAreaCargoFacadeLocal;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaSolicitud;
import com.movilidad.model.GenericaSolicitudCambio;
import com.movilidad.model.GenericaSolicitudPermiso;
import com.movilidad.model.GenericaToken;
import com.movilidad.model.ParamAreaCargo;
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
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "permisoCambioGenBean")
@ViewScoped
public class PermisoCambioGenManagedBean implements Serializable {

    @EJB
    private ParamAreaCargoFacadeLocal paramAreaCargoEjb;

    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaEjb;

    @EJB
    private GenericaSolicitudFacadeLocal genericaSolicitudEjb;

    @EJB
    private GenericaSolicitudPermisoFacadeLocal genericaSolicitudPermisoEjb;

    @EJB
    private GenericaSolicitudCambioFacadeLocal genericaSolicitudCambioEjb;

    @EJB
    private GenericaTokenFacadeLocal genericaTokenEjb;

    @EJB
    private GenericaJornadaFacadeLocal genericaJornadaEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;

    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    private GenericaToken genericaToken;
    private GenericaSolicitud genericaSolicitud;
    private GenericaSolicitudCambio genericaSolicitudCambio;
    private GenericaJornada genericaJornadaSolicitante;
    private GenericaJornada genericaJornadaReemplazo;
    private GenericaSolicitudPermiso genericaSolicitudPermiso;
    private ParamAreaCargo paramAreaCargo;

    private Integer idEmpleado;
    private String tipo_solicitud;
    private String codigo_colaborador;
    private String codigo_colaborador_reemplazo;
    private String token;
    private String logo;

    private boolean flagLogueado = false;
    private int modulo;

    private Map<String, String> configEmpresaMap;
    private List<ConfigEmpresa> lista;

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        token = params.get("pin");
        String id = params.get("id");
        if (token != null && id != null) {
            try {
                idEmpleado = new Integer(id);
            } catch (NumberFormatException e) {
                return;
            }

            login();
            logo = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO);
        }
    }

    public void login() {
        genericaToken = genericaTokenEjb.login(token, idEmpleado);

        if (genericaToken != null) {
            flagLogueado = true;
            genericaSolicitud = new GenericaSolicitud();
        }
    }

    public void reset() {
        PrimeFaces.current().executeScript("location.href='solicitud.jsf'");
    }

    private boolean validarDatos() {
        if (genericaJornadaSolicitante == null) {
            MovilidadUtil.addErrorMessage("No hay jornada para el colaborador solicitante en la fecha seleccionada");
            return true;
        }

        if (tipo_solicitud.equals("permiso")) {

            int dif = MovilidadUtil.diferencia(genericaSolicitudPermiso.getTimeOrigin(), genericaSolicitudPermiso.getTimeDestiny());
            if (dif < 0) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin");
                return true;
            }
        }

        if (tipo_solicitud.equals("cambio")) {
            if (genericaJornadaReemplazo == null) {
                MovilidadUtil.addErrorMessage("No hay jornada para el colaborador reemplazante en la fecha seleccionada");
                return true;
            }

            int dif_S = MovilidadUtil.diferencia(genericaSolicitudCambio.getTimeOriginSolicitante(), genericaSolicitudCambio.getTimeDestinySolicitante());
            if (dif_S < 0) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin (COLABORADOR SOLICITANTE)");
                return true;
            }

            int dif_R = MovilidadUtil.diferencia(genericaSolicitudCambio.getTimeOriginReemplazo(), genericaSolicitudCambio.getTimeDestinyReemplazo());
            if (dif_R < 0) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin (COLABORADOR REEMPLAZANTE)");
                return true;
            }
        }

        return false;
    }

    @Transactional
    public void guardar() {

        if (validarDatos()) {
            return;
        }

        genericaToken.setTipo(1);
        genericaSolicitud.setIdGenericaToken(genericaToken);
        genericaSolicitud.setIdGenericaJornada(genericaJornadaSolicitante);
        genericaSolicitud.setCreado(new Date());
        genericaSolicitud.setFechaSolicitud(genericaToken.getSolicitado());
        genericaSolicitud.setEstadoReg(0);
        genericaSolicitud.setReponeTiempo(0);
        genericaSolicitud.setRequiereSoporte(0);

        if (genericaJornadaSolicitante.getIdEmpleado() != null) {
            paramAreaCargo = paramAreaCargoEjb.getCargoAreaByCargo(genericaJornadaSolicitante.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo());
        }

        if (paramAreaCargo != null) {
            genericaSolicitud.setIdParamArea(paramAreaCargo.getIdParamArea());
        }

        genericaSolicitudEjb.create(genericaSolicitud);

        if (tipo_solicitud.equals("permiso")) {

            genericaSolicitudPermiso.setCreado(new Date());
            genericaSolicitudPermiso.setEstadoReg(0);
            genericaSolicitudPermiso.setIdGenericaSolicitud(genericaSolicitud);
            genericaSolicitudPermisoEjb.create(genericaSolicitudPermiso);

            genericaToken.setActivo(1);
            genericaToken.setUsado(new Date());
            genericaToken.setModificado(new Date());
            genericaTokenEjb.edit(genericaToken);

            MovilidadUtil.addSuccessMessage("Solicitud de permiso registrada con éxito");
        } else if (tipo_solicitud.equals("cambio")) {
            genericaSolicitudCambio.setCreado(new Date());
            genericaSolicitudCambio.setEstadoReg(0);
            genericaSolicitudCambio.setIdGenericaJornadaReemplazo(genericaJornadaReemplazo);
            genericaSolicitudCambio.setIdGenericaSolicitud(genericaSolicitud);
            genericaSolicitudCambioEjb.create(genericaSolicitudCambio);

            genericaToken.setActivo(1);
            genericaToken.setUsado(new Date());
            genericaToken.setModificado(new Date());
            genericaTokenEjb.edit(genericaToken);

            MovilidadUtil.addSuccessMessage("Solicitud de cambio registrada con éxito");
        }
        notificar();
        reset();
    }

    public void mostrarTipoSolicitud() {

        if (tipo_solicitud == null) {
            modulo = 0;
        }

        switch (tipo_solicitud) {
            case "permiso":
                modulo = 1;
                genericaSolicitudPermiso = new GenericaSolicitudPermiso();
                genericaSolicitudCambio = null;
                genericaJornadaSolicitante = null;
                break;
            case "cambio":
                modulo = 2;
                genericaSolicitudCambio = new GenericaSolicitudCambio();
                genericaSolicitudPermiso = null;
                codigo_colaborador_reemplazo = "";
                genericaJornadaReemplazo = null;
                genericaJornadaSolicitante = null;
                break;
        }
    }

    public void cargarJornadaReemplazo() {
        paramAreaCargo = paramAreaCargoEjb.getCargoAreaByCargo(genericaToken.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo());

        if (paramAreaCargo != null) {
            genericaJornadaReemplazo = genericaJornadaEjb.getJornadaByIdentificacionAndArea(codigo_colaborador_reemplazo, genericaSolicitudCambio.getFecha(), paramAreaCargo.getIdParamArea().getIdParamArea());
        }

        if (genericaJornadaReemplazo == null) {
            genericaSolicitudCambio.setLugarInicioReemplazo(null);
            genericaSolicitudCambio.setLugarFinReemplazo(null);
            genericaSolicitudCambio.setTimeOriginReemplazo(null);
            genericaSolicitudCambio.setTimeDestinyReemplazo(null);
            MovilidadUtil.addErrorMessage("NO se encontró jornada para el colaborador REEMPLAZANTE en fecha seleccionada");
            return;
        }

        genericaSolicitudCambio.setTimeOriginReemplazo(genericaJornadaReemplazo.getTimeOrigin());
        genericaSolicitudCambio.setTimeDestinyReemplazo(genericaJornadaReemplazo.getTimeDestiny());

    }

    public void obtenerSercon() {
        switch (tipo_solicitud) {
            case "permiso":
                paramAreaCargo = paramAreaCargoEjb.getCargoAreaByCargo(genericaToken.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo());

                if (paramAreaCargo != null) {
                    genericaJornadaSolicitante = genericaJornadaEjb.getJornadaByIdentificacionAndArea(genericaToken.getIdEmpleado().getIdentificacion(), genericaSolicitudPermiso.getFecha(), paramAreaCargo.getIdParamArea().getIdParamArea());
                }

                if (genericaJornadaSolicitante == null) {
                    MovilidadUtil.addErrorMessage("No hay jornada para la fecha seleccionada");
                }

                genericaSolicitudPermiso.setTimeOrigin(genericaJornadaSolicitante.getTimeOrigin());
                genericaSolicitudPermiso.setTimeDestiny(genericaJornadaSolicitante.getTimeDestiny());
                break;
            case "cambio":
                if (genericaToken.getIdEmpleado().getIdentificacion().equals(codigo_colaborador_reemplazo)) {
                    MovilidadUtil.addErrorMessage("La identificación del colaborador de REEMPLAZO NO puede ser igual al SOLICITANTE");
                    return;
                }
                paramAreaCargo = paramAreaCargoEjb.getCargoAreaByCargo(genericaToken.getIdEmpleado().getIdEmpleadoCargo().getIdEmpleadoTipoCargo());

                if (paramAreaCargo != null) {
                    genericaJornadaSolicitante = genericaJornadaEjb.getJornadaByIdentificacionAndArea(genericaToken.getIdEmpleado().getIdentificacion(), genericaSolicitudCambio.getFecha(), paramAreaCargo.getIdParamArea().getIdParamArea());
//                    genericaJornadaReemplazo = genericaJornadaEjb.getJornadaByIdentificacionAndArea(codigo_colaborador_reemplazo, genericaSolicitudCambio.getFecha(), paramAreaCargo.getIdParamArea().getIdParamArea());
                }

                if (genericaJornadaSolicitante == null) {
                    MovilidadUtil.addErrorMessage("No hay jornada para el colaborador SOLICITANTE en la fecha seleccionada");
                }

//                if (genericaJornadaReemplazo == null) {
//                    MovilidadUtil.addErrorMessage("No hay jornada para el colaborador REEMPLAZANTE en fecha seleccionada");
//                }
                genericaSolicitudCambio.setTimeOriginSolicitante(genericaJornadaSolicitante.getTimeOrigin());
                genericaSolicitudCambio.setTimeDestinySolicitante(genericaJornadaSolicitante.getTimeDestiny());
                break;
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

        mailProperties.put("logo", logo);

        mailProperties.put("titulo", "NOTIFICACION SOLICITUDES DE CAMBIOS Y/O PERMISOS");
        mailProperties.put("tipo", tipo_solicitud.equals("permiso") ? "PERMISO" : "CAMBIO");
        mailProperties.put("fecha", Util.dateFormat(genericaSolicitud.getFechaSolicitud()));
        mailProperties.put("cedula", genericaToken.getIdEmpleado().getIdentificacion());
        mailProperties.put("operador", genericaToken.getIdEmpleado().getNombres().concat(" ").concat(genericaToken.getIdEmpleado().getApellidos()));
        mailProperties.put("estado", genericaToken.getActivo().equals(1) ? "ESTUDIO" : genericaToken.getActivo().equals(2) ? "APROBADO" : "");
        String subject = "Se ha registrado una solicitud de " + tipo_solicitud;
        String destinatarios = genericaToken.getIdEmpleado().getEmailCorporativo();

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);

    }

    public String getTipo_solicitud() {
        return tipo_solicitud;
    }

    public void setTipo_solicitud(String tipo_solicitud) {
        this.tipo_solicitud = tipo_solicitud;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public String getCodigo_colaborador() {
        return codigo_colaborador;
    }

    public void setCodigo_colaborador(String codigo_colaborador) {
        this.codigo_colaborador = codigo_colaborador;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isFlagLogueado() {
        return flagLogueado;
    }

    public void setFlagLogueado(boolean flagLogueado) {
        this.flagLogueado = flagLogueado;
    }

    public GenericaToken getGenericaToken() {
        return genericaToken;
    }

    public void setGenericaToken(GenericaToken genericaToken) {
        this.genericaToken = genericaToken;
    }

    public GenericaSolicitud getGenericaSolicitud() {
        return genericaSolicitud;
    }

    public void setGenericaSolicitud(GenericaSolicitud genericaSolicitud) {
        this.genericaSolicitud = genericaSolicitud;
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

    public GenericaJornada getGenericaJornadaSolicitante() {
        return genericaJornadaSolicitante;
    }

    public void setGenericaJornadaSolicitante(GenericaJornada genericaJornadaSolicitante) {
        this.genericaJornadaSolicitante = genericaJornadaSolicitante;
    }

    public GenericaJornada getGenericaJornadaReemplazo() {
        return genericaJornadaReemplazo;
    }

    public void setGenericaJornadaReemplazo(GenericaJornada genericaJornadaReemplazo) {
        this.genericaJornadaReemplazo = genericaJornadaReemplazo;
    }

    public String getCodigo_colaborador_reemplazo() {
        return codigo_colaborador_reemplazo;
    }

    public void setCodigo_colaborador_reemplazo(String codigo_colaborador_reemplazo) {
        this.codigo_colaborador_reemplazo = codigo_colaborador_reemplazo;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public ParamAreaCargo getParamAreaCargo() {
        return paramAreaCargo;
    }

    public void setParamAreaCargo(ParamAreaCargo paramAreaCargo) {
        this.paramAreaCargo = paramAreaCargo;
    }

    public List<ConfigEmpresa> getLista() {
        return lista;
    }

    public void setLista(List<ConfigEmpresa> lista) {
        this.lista = lista;
    }

    public Map<String, String> getConfigEmpresaMap() {
        return configEmpresaMap;
    }

    public void setConfigEmpresaMap(Map<String, String> configEmpresaMap) {
        this.configEmpresaMap = configEmpresaMap;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
