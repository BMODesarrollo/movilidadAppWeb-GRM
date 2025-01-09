package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgSolicitudCambioFacadeLocal;
import com.movilidad.ejb.PrgSolicitudFacadeLocal;
import com.movilidad.ejb.PrgSolicitudMotivoFacadeLocal;
import com.movilidad.ejb.PrgSolicitudPermisoFacadeLocal;
import com.movilidad.ejb.PrgTokenFacadeLocal;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgSolicitud;
import com.movilidad.model.PrgSolicitudCambio;
import com.movilidad.model.PrgSolicitudMotivo;
import com.movilidad.model.PrgSolicitudPermiso;
import com.movilidad.model.PrgToken;
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
@Named(value = "permisoCambioBean")
@ViewScoped
public class PermisoCambioManagedBean implements Serializable {

    @EJB
    private PrgSolicitudFacadeLocal prgSolicitudEjb;

    @EJB
    private PrgSolicitudMotivoFacadeLocal prgSolicitudMotivoEjb;

    @EJB
    private PrgSolicitudPermisoFacadeLocal prgSolicitudPermisoEjb;

    @EJB
    private PrgSolicitudCambioFacadeLocal prgSolicitudCambioEjb;

    @EJB
    private PrgTokenFacadeLocal prgTokenEjb;

    @EJB
    private PrgSerconFacadeLocal prgSerconEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;

    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;

    private PrgToken prgToken;
    private PrgSolicitud prgSolicitud;
    private PrgSolicitudCambio prgSolicitudCambio;
    private PrgSercon prgSerconSolicitante;
    private PrgSercon prgSerconReemplazo;
    private PrgSolicitudPermiso prgSolicitudPermiso;

    private Integer idEmpleado;
    private String tipo_solicitud;
    private String codigo_operador;
    private String codigo_operador_reemplazo;
    private String token;
    private Integer i_idSolicitudMotivo;

    private List<PrgSolicitudMotivo> lstSolicitudMotivos;

    private boolean flagLogueado = false;
    private int modulo;

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
        }
    }

    public void login() {
        lstSolicitudMotivos = prgSolicitudMotivoEjb.findAll();
        prgToken = prgTokenEjb.login(token, idEmpleado);

        if (prgToken != null) {
            flagLogueado = true;
            prgSolicitud = new PrgSolicitud();
        }
    }

    public void reset() {
        PrimeFaces.current().executeScript("location.href='solicitud.jsf'");
    }

    private boolean validarDatos() {

        if (i_idSolicitudMotivo == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un motivo");
            return true;
        }

        if (prgSerconSolicitante == null) {
            MovilidadUtil.addErrorMessage("El sercon del operador solicitante es requerido");
            return true;
        }

        if (tipo_solicitud.equals("permiso")) {
            int dif = MovilidadUtil.diferencia(prgSolicitudPermiso.getTimeOrigin(), prgSolicitudPermiso.getTimeDestiny());
            if (dif < 0) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin");
                return true;
            }
        }

        if (tipo_solicitud.equals("cambio")) {
            if (prgSerconReemplazo == null) {
                MovilidadUtil.addErrorMessage("El sercon del operador reemplazante es requerido");
                return true;
            }

            int dif_S = MovilidadUtil.diferencia(prgSolicitudCambio.getTimeOriginSolicitante(), prgSolicitudCambio.getTimeDestinySolicitante());
            if (dif_S < 0) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin (OPERADOR SOLICITANTE)");
                return true;
            }

            int dif_R = MovilidadUtil.diferencia(prgSolicitudCambio.getTimeOriginReemplazo(), prgSolicitudCambio.getTimeDestinyReemplazo());
            if (dif_R < 0) {
                MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin (OPERADOR REEMPLAZANTE)");
                return true;
            }

            if (validarRangoHoras(prgSerconSolicitante, prgSerconReemplazo, prgSolicitudCambio.getFecha())) {
                MovilidadUtil.addErrorMessage("El turno NO permite respetar las 12 horas de descanso reglamentarias");
                return true;
            }
        }

        return false;
    }

    public Date obtenerFecha() {
        return MovilidadUtil.sumarDias(prgToken.getSolicitado(), -3);
    }

    @Transactional
    public void guardar() {

        if (validarDatos()) {
            return;
        }
        prgToken.setTipo(1);
        prgSolicitud.setIdPrgToken(prgToken);
        prgSolicitud.setIdPrgSercon(prgSerconSolicitante);
        prgSolicitud.setIdGopUnidadFuncional(prgToken.getIdEmpleado().getIdGopUnidadFuncional());
        prgSolicitud.setIdPrgSolicitudMotivo(new PrgSolicitudMotivo(i_idSolicitudMotivo));
        prgSolicitud.setCreado(new Date());
        prgSolicitud.setFechaSolicitud(prgToken.getSolicitado());
        prgSolicitud.setEstadoReg(0);
        prgSolicitud.setReponeTiempo(0);
        prgSolicitud.setRequiereSoporte(0);
        prgSolicitudEjb.create(prgSolicitud);

        if (tipo_solicitud.equals("permiso")) {

            prgSolicitudPermiso.setCreado(new Date());
            prgSolicitudPermiso.setEstadoReg(0);
            prgSolicitudPermiso.setIdPrgSolicitud(prgSolicitud);
            prgSolicitudPermisoEjb.create(prgSolicitudPermiso);

            prgToken.setActivo(1);
            prgToken.setUsado(new Date());
            prgToken.setModificado(new Date());
            prgTokenEjb.edit(prgToken);

            MovilidadUtil.addSuccessMessage("Solicitud de permiso registrada con éxito");
        } else if (tipo_solicitud.equals("cambio")) {
            prgSolicitudCambio.setCreado(new Date());
            prgSolicitudCambio.setEstadoReg(0);
            prgSolicitudCambio.setIdPrgSerconReemplazo(prgSerconReemplazo);
            prgSolicitudCambio.setIdPrgSolicitud(prgSolicitud);
            prgSolicitudCambioEjb.create(prgSolicitudCambio);

            prgToken.setActivo(1);
            prgToken.setUsado(new Date());
            prgToken.setModificado(new Date());
            prgTokenEjb.edit(prgToken);

            MovilidadUtil.addSuccessMessage("Solicitud de cambio registrada con éxito");
        }
        notificar();
        reset();

    }

    public void mostrarTipoSolicitud() {

        switch (tipo_solicitud) {
            case "N/A":
                modulo = 0;
                prgSerconSolicitante = null;
                break;
            case "permiso":
                modulo = 1;
                prgSolicitudPermiso = new PrgSolicitudPermiso();
                prgSerconSolicitante = null;
                prgSolicitudCambio = null;
                break;
            case "cambio":
                modulo = 2;
                codigo_operador_reemplazo = "";
                prgSolicitudCambio = new PrgSolicitudCambio();
                prgSerconReemplazo = null;
                prgSerconSolicitante = null;
                prgSolicitudPermiso = null;
                break;
        }
    }

    public void cargarSerconReemplazo() {

        if (empleadoEjb.
                getEmpleadoCodigoTmAndUnidadFuncional(Integer.parseInt(codigo_operador_reemplazo),
                        prgToken.getIdEmpleado().getIdGopUnidadFuncional()
                                .getIdGopUnidadFuncional()) == null) {
            prgSolicitudCambio.setLugarInicioReemplazo(null);
            prgSolicitudCambio.setLugarFinReemplazo(null);
            prgSolicitudCambio.setTimeOriginReemplazo(null);
            prgSolicitudCambio.setTimeDestinyReemplazo(null);
            MovilidadUtil.addErrorMessage("Operador REEMPLAZO NO pertenece a la unidad funcional del SOLICITANTE");
            return;
        }

        prgSerconReemplazo = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(codigo_operador_reemplazo, prgSolicitudCambio.getFecha(), prgToken.getIdEmpleado().getIdGopUnidadFuncional().getIdGopUnidadFuncional());

        if (prgSerconReemplazo == null) {
            prgSolicitudCambio.setLugarInicioReemplazo(null);
            prgSolicitudCambio.setLugarFinReemplazo(null);
            prgSolicitudCambio.setTimeOriginReemplazo(null);
            prgSolicitudCambio.setTimeDestinyReemplazo(null);
            MovilidadUtil.addErrorMessage("NO se encontró sercon para el operador REEMPLAZANTE en fecha seleccionada");
            return;
        }

        prgSolicitudCambio.setLugarInicioReemplazo(prgSerconReemplazo.getIdFromStop() != null ? prgSerconReemplazo.getIdFromStop().getName() : "N/A");
        prgSolicitudCambio.setLugarFinReemplazo(prgSerconReemplazo.getIdFromStop() != null ? prgSerconReemplazo.getIdToStop().getName() : "N/A");
        prgSolicitudCambio.setTimeOriginReemplazo(prgSerconReemplazo.getTimeOrigin());
        prgSolicitudCambio.setTimeDestinyReemplazo(prgSerconReemplazo.getTimeDestiny());

    }

    public void obtenerSercon() {
        switch (tipo_solicitud) {
            case "permiso":
                prgSerconSolicitante = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(prgToken.getIdEmpleado().getCodigoTm().toString(), prgSolicitudPermiso.getFecha(), prgToken.getIdEmpleado().getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                if (prgSerconSolicitante == null) {

                    prgSolicitudPermiso.setTimeOrigin(null);
                    prgSolicitudPermiso.setTimeDestiny(null);

                    prgSolicitudPermiso.setLugarInicio(null);
                    prgSolicitudPermiso.setLugarFin(null);

                    MovilidadUtil.addErrorMessage("NO se encontró sercon para la fecha seleccionada");
                    return;
                }

                prgSolicitudPermiso.setLugarInicio(prgSerconSolicitante.getIdFromStop() != null ? prgSerconSolicitante.getIdFromStop().getName() : "N/A");
                prgSolicitudPermiso.setLugarFin(prgSerconSolicitante.getIdFromStop() != null ? prgSerconSolicitante.getIdToStop().getName() : "N/A");
                prgSolicitudPermiso.setTimeOrigin(prgSerconSolicitante.getTimeOrigin());
                prgSolicitudPermiso.setTimeDestiny(prgSerconSolicitante.getTimeDestiny());

                break;
            case "cambio":
                prgSerconSolicitante = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(prgToken.getIdEmpleado().getCodigoTm().toString(), prgSolicitudCambio.getFecha(), prgToken.getIdEmpleado().getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                if (prgSerconSolicitante == null) {
                    prgSolicitudCambio.setLugarInicioSolicitante(null);
                    prgSolicitudCambio.setLugarFinSolicitante(null);
                    prgSolicitudCambio.setTimeOriginSolicitante(null);
                    prgSolicitudCambio.setTimeDestinySolicitante(null);
                    MovilidadUtil.addErrorMessage("NO se encontró sercon para el operador SOLICITANTE en la fecha seleccionada");
                    return;
                }

                prgSolicitudCambio.setLugarInicioSolicitante(prgSerconSolicitante.getIdFromStop() != null ? prgSerconSolicitante.getIdFromStop().getName() : "N/A");
                prgSolicitudCambio.setLugarFinSolicitante(prgSerconSolicitante.getIdFromStop() != null ? prgSerconSolicitante.getIdToStop().getName() : "N/A");
                prgSolicitudCambio.setTimeOriginSolicitante(prgSerconSolicitante.getTimeOrigin());
                prgSolicitudCambio.setTimeDestinySolicitante(prgSerconSolicitante.getTimeDestiny());

                break;
        }
    }

    private boolean validarRangoHoras(PrgSercon solicitanteAct, PrgSercon reemplazoAct, Date fecha) {
        PrgSercon prgSerconSolicitanteAnt;
        PrgSercon prgSerconReemplazoAnt;
        PrgSercon prgSerconSolicitanteSig;
        PrgSercon prgSerconReemplazoSig;
        String hIniTurnoACambiar;
        int dif;
        Date fechaAnterior = MovilidadUtil.sumarDias(fecha, -1);
        Date fechaSiguiente = MovilidadUtil.sumarDias(fecha, 1);

        prgSerconSolicitanteAnt = prgSerconEjb.validarEmplSinJornadaByUnindadFuncional(prgToken.getIdEmpleado().getIdEmpleado(), fechaAnterior, prgToken.getIdEmpleado().getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        prgSerconReemplazoAnt = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(codigo_operador_reemplazo, fechaAnterior, prgToken.getIdEmpleado().getIdGopUnidadFuncional().getIdGopUnidadFuncional());

        if (prgSerconReemplazoAnt != null) {
            // Caso 1: Verificar si el operador de reemplazo cumple 12 horas de descanso
            String sTimeOrigin = solicitanteAct.getTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOrigin) == 0) {
                sTimeOrigin = "24:00:00";
            }

            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOrigin, "24:00:00");
            dif = MovilidadUtil.diferencia(prgSerconReemplazoAnt.getTimeDestiny(), hIniTurnoACambiar);

            if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                return true;
            }
        }

        if (prgSerconSolicitanteAnt != null) {
            // Caso 2: Verificar si el operador solicitante cumple 12 horas de descanso
            String sTimeOriginRemp = reemplazoAct.getTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOriginRemp) == 0) {
                sTimeOriginRemp = "24:00:00";
            }

            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRemp, "24:00:00");
            dif = MovilidadUtil.diferencia(prgSerconSolicitanteAnt.getTimeDestiny(), hIniTurnoACambiar);

            if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                return true;
            }
        }

        prgSerconSolicitanteSig = prgSerconEjb.validarEmplSinJornadaByUnindadFuncional(prgToken.getIdEmpleado().getIdEmpleado(), fechaSiguiente, prgToken.getIdEmpleado().getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        prgSerconReemplazoSig = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(codigo_operador_reemplazo, fechaSiguiente, prgToken.getIdEmpleado().getIdGopUnidadFuncional().getIdGopUnidadFuncional());

        if (prgSerconSolicitanteSig != null) {

            // Caso 1: Verificar si el operador de reemplazo cumple 12 horas de descanso
            String sTimeOriginSolSig = prgSerconSolicitanteSig.getTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOriginSolSig) == 0) {
                sTimeOriginSolSig = "24:00:00";
            }

            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginSolSig, "24:00:00");
            dif = MovilidadUtil.diferencia(reemplazoAct.getTimeDestiny(), hIniTurnoACambiar);

            if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                return true;
            }

        }

        if (prgSerconReemplazoSig != null) {
            // Caso 2: Verificar si el operador solicitante cumple 12 horas de descanso
            String sTimeOriginRempSig = prgSerconReemplazoSig.getTimeOrigin();

            if (MovilidadUtil.toSecs(sTimeOriginRempSig) == 0) {
                sTimeOriginRempSig = "24:00:00";
            }

            hIniTurnoACambiar = MovilidadUtil.sumarHoraSrting(sTimeOriginRempSig, "24:00:00");
            dif = MovilidadUtil.diferencia(solicitanteAct.getTimeDestiny(), hIniTurnoACambiar);

            if (dif <= MovilidadUtil.toSecs(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_HORAS_DESCANSO))) {
                return true;
            }
        }

        return false;
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

    private void notificar() {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("titulo", "NOTIFICACION SOLICITUDES DE CAMBIOS Y/O PERMISOS");
        mailProperties.put("tipo", tipo_solicitud.equals("permiso") ? "PERMISO" : "CAMBIO");
        mailProperties.put("fecha", Util.dateFormat(prgSolicitud.getFechaSolicitud()));
        mailProperties.put("codigo", prgToken.getIdEmpleado().getCodigoTm());
        mailProperties.put("operador", prgToken.getIdEmpleado().getNombres().concat(" ").concat(prgToken.getIdEmpleado().getApellidos()));
        mailProperties.put("estado", prgToken.getActivo().equals(1) ? "ESTUDIO" : prgToken.getActivo().equals(2) ? "APROBADO" : "");
        String subject = "Se ha registrado una solicitud de " + tipo_solicitud;
        String destinatarios = prgToken.getIdEmpleado().getEmailCorporativo();

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

    public String getCodigo_operador() {
        return codigo_operador;
    }

    public void setCodigo_operador(String codigo_operador) {
        this.codigo_operador = codigo_operador;
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

    public PrgToken getPrgToken() {
        return prgToken;
    }

    public void setPrgToken(PrgToken prgToken) {
        this.prgToken = prgToken;
    }

    public PrgSolicitud getPrgSolicitud() {
        return prgSolicitud;
    }

    public void setPrgSolicitud(PrgSolicitud prgSolicitud) {
        this.prgSolicitud = prgSolicitud;
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

    public PrgSercon getPrgSerconSolicitante() {
        return prgSerconSolicitante;
    }

    public void setPrgSerconSolicitante(PrgSercon prgSerconSolicitante) {
        this.prgSerconSolicitante = prgSerconSolicitante;
    }

    public PrgSercon getPrgSerconReemplazo() {
        return prgSerconReemplazo;
    }

    public void setPrgSerconReemplazo(PrgSercon prgSerconReemplazo) {
        this.prgSerconReemplazo = prgSerconReemplazo;
    }

    public String getCodigo_operador_reemplazo() {
        return codigo_operador_reemplazo;
    }

    public void setCodigo_operador_reemplazo(String codigo_operador_reemplazo) {
        this.codigo_operador_reemplazo = codigo_operador_reemplazo;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getI_idSolicitudMotivo() {
        return i_idSolicitudMotivo;
    }

    public void setI_idSolicitudMotivo(Integer i_idSolicitudMotivo) {
        this.i_idSolicitudMotivo = i_idSolicitudMotivo;
    }

    public List<PrgSolicitudMotivo> getLstSolicitudMotivos() {
        return lstSolicitudMotivos;
    }

    public void setLstSolicitudMotivos(List<PrgSolicitudMotivo> lstSolicitudMotivos) {
        this.lstSolicitudMotivos = lstSolicitudMotivos;
    }

}
