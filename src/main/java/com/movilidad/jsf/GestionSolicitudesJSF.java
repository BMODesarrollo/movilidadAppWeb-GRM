/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.PrgTokenFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.PrgToken;
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
@Named(value = "gestionSolicitudesJSF")
@ViewScoped
public class GestionSolicitudesJSF implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private PrgTokenFacadeLocal prgTokenFacadeLocal;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    private PrgToken prgToken;
    private Empleado empleado;

    private boolean bFlag;
    private Integer iCodigoTm;
    private String cToken;

    public GestionSolicitudesJSF() {
    }

    @PostConstruct
    public void init() {
        bFlag = true;
        iCodigoTm = null;
        prgToken = new PrgToken();
        empleado = null;
        paramToken();
    }

    public void validarCodigoEnviarToken() {
        if (iCodigoTm == null) {
            MovilidadUtil.addErrorMessage("Código Tm es requirido");
            return;
        }
        empleado = empleadoFacadeLocal.getEmpleadoCodigoTM(iCodigoTm);
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
                prgToken = prgTokenFacadeLocal.find(idToken);
                if (prgToken != null && prgToken.getActivo().equals(0)) {
                    if (Util.dateFormat(prgToken.getSolicitado()).equals(Util.dateFormat(new Date()))) {
                        if (prgToken.getIdEmpleado() != null) {
                            empleado = prgToken.getIdEmpleado();
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
            prgToken = prgTokenFacadeLocal.login(cToken, empleado.getIdEmpleado());
            if (prgToken != null) {
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
            PrgToken prgTk = prgTokenFacadeLocal.getValidarProcesoPorDia(d, empleado.getIdEmpleado());
            if (prgTk != null && prgTk.getActivo().equals(0)) {
                MovilidadUtil.addSuccessMessage("Usted ya realizó la petición, a su correo fue enviado nuevamente el código de acceso");
                enviarCorreo(prgTk);
            } else {
                prgToken.setActivo(Util.ID_TOKEN_SOLICITADO);
                prgToken.setIdEmpleado(empleado);
                prgToken.setToken(TokenGeneratorUtil.nextToken());
                prgToken.setSolicitado(d);
                prgToken.setModificado(d);
                prgToken.setEstadoReg(0);
                prgTokenFacadeLocal.create(prgToken);
                MovilidadUtil.addSuccessMessage("Código de seguridad generado, "
                        + "fue enviado a su correo corporativo. "
                        + "Valido hasta las 23:59 de " + Util.dateFormat(d));
                enviarCorreo(prgToken);
            }
            prgToken = new PrgToken();
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

    void enviarCorreo(PrgToken prgTk) {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("codigo", prgTk.getToken());
        mailProperties.put("modulo", "Solicitud de Licencias y/o Permisos");
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        String subject = Util.ID_ESTADO_SOLICITUDES;
        String destinatarios = prgTk.getIdEmpleado().getEmailCorporativo();
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

    public Integer getiCodigoTm() {
        return iCodigoTm;
    }

    public void setiCodigoTm(Integer iCodigoTm) {
        this.iCodigoTm = iCodigoTm;
    }

    public PrgToken getPrgToken() {
        return prgToken;
    }

    public void setPrgToken(PrgToken prgToken) {
        this.prgToken = prgToken;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getcToken() {
        return cToken;
    }

    public void setcToken(String cToken) {
        this.cToken = cToken;
    }

}
