/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaTokenFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaToken;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.TokenGeneratorUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author cesar
 */
@Named(value = "genericaTokenJSF")
@ViewScoped
public class GenericaTokenJSF implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private GenericaTokenFacadeLocal genericaTokenFacadeLocal;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    private GenericaToken genericaToken;
    private Empleado empleado;

    private boolean bFlag;
    private String cIdentificacion;
    private String cToken;

    public GenericaTokenJSF() {
    }

    @PostConstruct
    public void init() {
        bFlag = true;
        cIdentificacion = null;
        genericaToken = new GenericaToken();
        empleado = null;
        paramToken();
    }

    public void validarCodigoEnviarToken() {
        if (cIdentificacion == null) {
            MovilidadUtil.addErrorMessage("Identificación es requirido");
            return;
        }
        empleado = empleadoFacadeLocal.findByIdentificacion(cIdentificacion);
        if (empleado == null) {
            MovilidadUtil.addErrorMessage("No se puede realizar el procedimiento");
            return;
        }
        if (empleado.getEmailCorporativo() != null && !empleado.getEmailCorporativo().isEmpty()) {
            enviarSolicitud();
            PrimeFaces.current().executeScript("PF('tokenDG').show()");
        } else {
            MovilidadUtil.addErrorMessage("El sistema no cuenta con su correo corporativo, imposible realizar procedimiento");
        }
    }

    void paramToken() {
        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        String id = params.get("id");
        if (id != null) {
            try {
                Integer idToken = new Integer(id);
                genericaToken = genericaTokenFacadeLocal.find(idToken);
                if (genericaToken != null && genericaToken.getActivo().equals(0)) {
                    if (Util.dateFormat(genericaToken.getSolicitado()).equals(Util.dateFormat(new Date()))) {
                        if (genericaToken.getIdEmpleado() != null) {
                            empleado = genericaToken.getIdEmpleado();
                        }
                        bFlag = false;
                    }
                }
            } catch (NumberFormatException e) {
            }
        }
    }

    public void validarToken() {
        if (cToken != null) {
            genericaToken = genericaTokenFacadeLocal.login(cToken, empleado.getIdEmpleado());
            if (genericaToken != null) {
                bFlag = false;
                PrimeFaces.current().executeScript("PF('tokenDG').hide()");
            } else {
                MovilidadUtil.addErrorMessage("Código de Seguridad incorrecto");
            }
        }
    }

    //op es el tipo de solicitud 1 para solicitud de permiso o cambio, 2 para solicitud de licencia no remunerada
    void enviarSolicitud() {
        try {
            Date d = new Date();
            GenericaToken genericaTk = genericaTokenFacadeLocal.getValidarProcesoPorDia(d, empleado.getIdEmpleado());
            if (genericaTk != null && genericaTk.getActivo().equals(0)) {
                MovilidadUtil.addSuccessMessage("Usted ya realizó la petición, a su correo fue enviado nuevamente el código de acceso");
                enviarCorreo(genericaTk);
            } else {
                genericaToken.setActivo(Util.ID_TOKEN_SOLICITADO);
                genericaToken.setIdEmpleado(empleado);
                genericaToken.setToken(TokenGeneratorUtil.nextToken());
                genericaToken.setSolicitado(d);
                genericaToken.setModificado(d);
                genericaToken.setEstadoReg(0);
                genericaTokenFacadeLocal.create(genericaToken);
                MovilidadUtil.addSuccessMessage("Código de seguridad generado, "
                        + "fue enviado a su correo corporativo. "
                        + "Valido hasta las 23:59 de " + Util.dateFormat(d));
                enviarCorreo(genericaToken);
            }
            genericaToken = new GenericaToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map getMailParams() {
        String templ = Util.ID_TOKEN_TEMPLATE;
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(templ);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    void enviarCorreo(GenericaToken genericaTk) {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("codigo", genericaTk.getToken());
        mailProperties.put("modulo", "Solicitud de Licencias y/o Permisos");
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        String subject = Util.ID_ESTADO_SOLICITUDES;
        String destinatarios = genericaTk.getIdEmpleado().getEmailCorporativo();
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    public GenericaToken getGenericaToken() {
        return genericaToken;
    }

    public void setGenericaToken(GenericaToken genericaToken) {
        this.genericaToken = genericaToken;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

    public String getcIdentificacion() {
        return cIdentificacion;
    }

    public void setcIdentificacion(String cIdentificacion) {
        this.cIdentificacion = cIdentificacion;
    }

    public String getcToken() {
        return cToken;
    }

    public void setcToken(String cToken) {
        this.cToken = cToken;
    }

}
