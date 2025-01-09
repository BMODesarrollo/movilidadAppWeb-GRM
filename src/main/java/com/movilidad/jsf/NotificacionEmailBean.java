/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author solucionesit
 */
@Named(value = "notifiEmailBean")
@ViewScoped
public class NotificacionEmailBean implements Serializable {

    /**
     * Creates a new instance of NotificacionEmailBean
     */
    public NotificacionEmailBean() {
    }
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEJB;

    /*
     * Parámetros para el envío de correos (Novedades PM)
     */
    public Map getMailParams(int idNotificaionConfig, String keyTemplate) {
        NotificacionCorreoConf conf = NCCEJB.find(idNotificaionConfig);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(keyTemplate);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    public String obtenerDestinatarios(String codigoNotificacionProceso, int idGopUnidadFunc) {
        String correos = "";
        NotificacionProcesos proceso = notificacionProcesosEJB.findByCodigo(codigoNotificacionProceso);
        if (proceso != null) {
            correos = proceso.getEmails();
            String correoDet = MovilidadUtil.obtenerCorreosByUf(proceso.getNotificacionProcesoDetList(), idGopUnidadFunc);
            if (correoDet != null) {
                correos = correos + "," + correoDet;
            }
        }
        return correos;
    }
}
