/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopAlertaPresentacionFacadeLocal;
import com.movilidad.model.GopAlertaPresentacion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.ObjetoSigleton;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite parametrizar la data relacionada con los objetos
 * GopAlertaPresentacion Principal tabla afectada gop_alerta_presentacion
 *
 * @author soluciones-it
 */
@Named(value = "gopAlertaPresentacionJSF")
@ViewScoped
public class GopAlertaPresentacionJSF implements Serializable {

    @EJB
    private GopAlertaPresentacionFacadeLocal gopAlertaPresentacionFacadeLocal;

    private GopAlertaPresentacion gopAlertaPresentacion;

    private List<GopAlertaPresentacion> listGopAlertaPresentacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of GopAlertaPresentacionJSF
     */
    public GopAlertaPresentacionJSF() {
    }

    /**
     * Permite persistir la data del objeto GopAlertaPresentacion en la base de
     * datos
     */
    public void guardar() {
        try {
            if (gopAlertaPresentacion != null) {
                if (!listGopAlertaPresentacion.isEmpty()) {
                    MovilidadUtil.addErrorMessage("Ya se encuentra un registro parametrizado");
                    return;
                }
                gopAlertaPresentacion.setCreado(new Date());
                gopAlertaPresentacion.setModificado(new Date());
                gopAlertaPresentacion.setEstadoReg(0);
                gopAlertaPresentacion.setUsername(user.getUsername());
                gopAlertaPresentacionFacadeLocal.create(gopAlertaPresentacion);
                setGopAlertaPresentacionSingleton(gopAlertaPresentacion);
                MovilidadUtil.addSuccessMessage("Se a registrado alerta tiempo presentación correctamente");
                gopAlertaPresentacion = new GopAlertaPresentacion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar alerta tiempo presentación");
        }
    }

    /**
     * Permite realizar un update del objeto GopAlertaPresentacion en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (gopAlertaPresentacion != null) {
                gopAlertaPresentacion.setModificado(new Date());
                gopAlertaPresentacion.setUsername(user.getUsername());
                gopAlertaPresentacionFacadeLocal.edit(gopAlertaPresentacion);
                setGopAlertaPresentacionSingleton(gopAlertaPresentacion);
                MovilidadUtil.addSuccessMessage("Se a actualizado alerta tiempo presentación correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('gopAlertaPresentacion-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar alerta tiempo presentación");
        }
    }

    /**
     * Settea la unica instancia para la parametricación util para el panel
     * principal.
     *
     * @param alertaPresentacion
     */
    public void setGopAlertaPresentacionSingleton(GopAlertaPresentacion alertaPresentacion) {
        ObjetoSigleton.setGopAlertaPresentacion(alertaPresentacion);
    }

    /**
     * Permite crear la instancia del objeto GopAlertaPresentacion
     */
    public void prepareGuardar() {
        gopAlertaPresentacion = new GopAlertaPresentacion();
    }

    public void reset() {
        gopAlertaPresentacion = null;
    }

    /**
     * Permite capturar el objeto GopAlertaPresentacion seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto GopAlertaPresentacion
     */
    public void onRowSelect(SelectEvent event) {
        gopAlertaPresentacion = (GopAlertaPresentacion) event.getObject();
    }

    public GopAlertaPresentacionFacadeLocal getGopAlertaPresentacionFacadeLocal() {
        return gopAlertaPresentacionFacadeLocal;
    }

    public void setGopAlertaPresentacionFacadeLocal(GopAlertaPresentacionFacadeLocal gopAlertaPresentacionFacadeLocal) {
        this.gopAlertaPresentacionFacadeLocal = gopAlertaPresentacionFacadeLocal;
    }

    public GopAlertaPresentacion getGopAlertaPresentacion() {
        return gopAlertaPresentacion;
    }

    public void setGopAlertaPresentacion(GopAlertaPresentacion gopAlertaPresentacion) {
        this.gopAlertaPresentacion = gopAlertaPresentacion;
    }

    public List<GopAlertaPresentacion> getListGopAlertaPresentacion() {
        listGopAlertaPresentacion = gopAlertaPresentacionFacadeLocal.findAllEstadoReg();
        return listGopAlertaPresentacion;
    }

    public void setListGopAlertaPresentacion(List<GopAlertaPresentacion> listGopAlertaPresentacion) {
        this.listGopAlertaPresentacion = listGopAlertaPresentacion;
    }

}
