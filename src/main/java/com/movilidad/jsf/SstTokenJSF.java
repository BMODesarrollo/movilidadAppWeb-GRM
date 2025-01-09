/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.SstEmpresaFacadeLocal;
import com.movilidad.ejb.SstTokenFacadeLocal;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.SstEmpresa;
import com.movilidad.model.SstToken;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.TokenGeneratorUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

/**
 * Permite gestionar toda los datos para el objeto SstToken principal tabla
 * afectada sst_token
 *
 * @author cesar
 */
@Named(value = "sstTokenJSF")
@ViewScoped
public class SstTokenJSF implements Serializable {

    @EJB
    private SstTokenFacadeLocal sstTokenEJB;
    @EJB
    private SstEmpresaFacadeLocal sstEmpresaEJB;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    private SstEmpresa sstEmpresa;
    private String cCorreo;
    private String cToken;
    private String cTokenStorage;
    private String cUsuario;
    private String loadGif = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO);

    private boolean bFlag;

    /**
     * Creates a new instance of SstTokenJSF
     */
    public SstTokenJSF() {
    }

    @PostConstruct
    public void init() {
        cTokenStorage = null;
        bFlag = false;
        sstEmpresa = null;
    }

    /**
     * Permite validar los parametros requeridos para realizar el envio del
     * token, se valida si el correo y el nombre de usuario son correctos
     */
    public void validarParametrosEnviarToken() {
        if (cCorreo == null) {
            MovilidadUtil.addErrorMessage("Correo es requerido");
            return;
        }
        if (cUsuario == null) {
            MovilidadUtil.addErrorMessage("Usuario es requerido");
            return;
        }
        sstEmpresa = sstEmpresaEJB.login(cCorreo, cUsuario);
        if (sstEmpresa != null) {
            enviarSolicitud();
            PrimeFaces.current().executeScript("document.getElementById('form:acceso_toggler').click();");
            return;
        }
        MovilidadUtil.addErrorMessage("Correo o Usuario incorrectos");
    }

    /**
     * Permite realizar la validacion en bd si el token suministrado es correcto
     * y no ha expirado
     *
     * @throws java.text.ParseException
     */
    public void validarToken() throws ParseException {
        if (cToken != null) {
            SstToken sstTok = sstTokenEJB.findByToken(cToken, new Date());
            if (sstTok != null) {
                if (MovilidadUtil.fechasIgualMenorMayor(sstTok.getSolicitado(), new Date(), false) == 1) {
                    sstTok.setUsado(new Date());
                    sstTokenEJB.edit(sstTok);
                    String cTokenAct = "RGEL-" + cToken;
                    PrimeFaces.current().executeScript("localStorage.setItem('rgKey', '" + cTokenAct + "');");
                    PrimeFaces.current().executeScript("location.href = 'panelPrincipal.jsf'");
                    return;
                }
                MovilidadUtil.addErrorMessage("Código de acceso expirado");
            } else {
                MovilidadUtil.addErrorMessage("Código de acceso incorrecto");
            }
        }
    }

    /**
     * Permite obtener el token que se encuentra en el localStorage y validarlo
     * en el sistema
     */
    public void obtenerToken() {
        try {
            if (cTokenStorage != null && !cTokenStorage.isEmpty()) {
                String a = cTokenStorage.split("-")[1];
                SstToken findByToken = sstTokenEJB.findByToken(a, new Date());
                if (findByToken != null) {
                    sstEmpresa = findByToken.getIdSstEmpresa();
                    bFlag = true;
                    return;
                }
            }
            reset();
        } catch (Exception e) {
            reset();
        }
    }

    public void salir() {
        SstEmpresa sstEmpr = getSstEmpresa();
        cTokenStorage = null;
        if (sstEmpr != null) {
            SstToken sstTok = sstTokenEJB.findTokenByIdSstEmpresa(sstEmpr.getIdSstEmpresa(), new Date());
            sstTok.setUsado(new Date());
            sstTok.setActivo(Util.ID_TOKEN_USADO);
            sstTokenEJB.edit(sstTok);
        }
    }

    void reset() {
        cTokenStorage = null;
        bFlag = false;
        sstEmpresa = null;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String name = Util.getUrlContext(request) + "/public/sst/login.jsf";
        PrimeFaces.current().executeScript("localStorage.removeItem('rgKey')");
        PrimeFaces.current().executeScript("location.href = '" + name + "'");
    }

    public SstEmpresa getSstEmpresa() {
        if (sstEmpresa == null) {
            reset();
        }
        return sstEmpresa;
    }

    /**
     * Permite realizar el envío del código de acceso, se valida si el código ya
     * fue enviado, si no, se realiza el envío nuevamente
     */
    void enviarSolicitud() {
        try {
            SstToken sstTok;
            Date d = new Date();
            sstTok = sstTokenEJB.findTokenByIdSstEmpresa(sstEmpresa.getIdSstEmpresa(), d);
            if (sstTok != null && sstTok.getActivo().equals(0)) {
                MovilidadUtil.addSuccessMessage("Usted ya realizó la petición, a su correo fue enviado nuevamente el código de acceso");
                enviarCorreo(sstTok);
            } else {
                sstTok = new SstToken();
                sstTok.setActivo(Util.ID_TOKEN_SOLICITADO);
                sstTok.setIdSstEmpresa(sstEmpresa);
                sstTok.setToken(TokenGeneratorUtil.nextToken());
                sstTok.setSolicitado(d);
                sstTok.setModificado(d);
                sstTok.setEstadoReg(0);
                sstTokenEJB.create(sstTok);
                MovilidadUtil.addSuccessMessage("Código de acceso generado, "
                        + "fue enviado al correo correspondiente. "
                        + "Valido hasta las 23:59 de " + Util.dateFormat(d));
                enviarCorreo(sstTok);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Permite obtener los atributos de configuracion de correo, para realizar
     * un envío vía este.
     *
     * @return Objeto Map
     */
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

    /**
     * Permite realizar la validacion en bd si el token suministrado es correcto
     * y no ha expirado
     *
     * @param sstTok Objeto SstToken
     */
    void enviarCorreo(SstToken sstTok) {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("codigo", sstTok.getToken());
        mailProperties.put("modulo", "SST");
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        String subject = "Código de acceso Sst";
        String destinatarios = sstTok.getIdSstEmpresa().getEmailResponsable();
        SendMails.sendEmail(mapa,
                mailProperties,
                subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    public String getcCorreo() {
        return cCorreo;
    }

    public void setcCorreo(String cCorreo) {
        this.cCorreo = cCorreo;
    }

    public String getcToken() {
        return cToken;
    }

    public void setcToken(String cToken) {
        this.cToken = cToken;
    }

    public String getcUsuario() {
        return cUsuario;
    }

    public void setcUsuario(String cUsuario) {
        this.cUsuario = cUsuario;
    }

    public String getCTokenStorage() {
        return cTokenStorage;
    }

    public void setCTokenStorage(String cTokenStorage) {
        this.cTokenStorage = cTokenStorage;
    }

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

    public String getLoadGif() {
        return loadGif;
    }

    public void setLoadGif(String loadGif) {
        this.loadGif = loadGif;
    }

}
