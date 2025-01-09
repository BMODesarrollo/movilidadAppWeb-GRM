/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopAlertaTiempoDetenidoFacadeLocal;
import com.movilidad.model.GopAlertaTiempoDetenido;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
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
 * GopAlertaTiempoDetenido Principal tabla afectada gop_alerta_tiempo_detenido
 *
 * @author soluciones-it
 */
@Named(value = "gopAlertaTiempoDetenidoJSF")
@ViewScoped
public class GopAlertaTiempoDetenidoJSF implements Serializable {

    @EJB
    private GopAlertaTiempoDetenidoFacadeLocal gopAlertaTiempoDetenidoFacadeLocal;

    private GopAlertaTiempoDetenido gopAlertaTiempoDetenido;

    private List<GopAlertaTiempoDetenido> listGopAlertaTiempoDetenido;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of GopAlertaTiempoDetenidoJSF
     */
    public GopAlertaTiempoDetenidoJSF() {
    }

    /**
     * Permite persistir la data del objeto GopAlertaTiempoDetenido en la base
     * de datos
     */
    public void guardar() {
        try {
            if (gopAlertaTiempoDetenido != null) {
                if (!listGopAlertaTiempoDetenido.isEmpty()) {
                    MovilidadUtil.addErrorMessage("Ya se encuentra un registro parametrizado");
                    return;
                }
                gopAlertaTiempoDetenido.setCreado(new Date());
                gopAlertaTiempoDetenido.setModificado(new Date());
                gopAlertaTiempoDetenido.setEstadoReg(0);
                gopAlertaTiempoDetenido.setUsername(user.getUsername());
                gopAlertaTiempoDetenidoFacadeLocal.create(gopAlertaTiempoDetenido);
                MovilidadUtil.addSuccessMessage("Se a registrado alerta tiempo detenido correctamente");
                gopAlertaTiempoDetenido = new GopAlertaTiempoDetenido();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar alerta tiempo detenido");
        }
    }

    /**
     * Permite realizar un update del objeto GopAlertaTiempoDetenido en la base
     * de datos
     */
    public void actualizar() {
        try {
            if (gopAlertaTiempoDetenido != null) {
                gopAlertaTiempoDetenido.setModificado(new Date());
                gopAlertaTiempoDetenido.setUsername(user.getUsername());
                gopAlertaTiempoDetenidoFacadeLocal.edit(gopAlertaTiempoDetenido);
                MovilidadUtil.addSuccessMessage("Se a actualizado alerta tiempo detenido correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('gopAlertaTiempoDetenido-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar alerta tiempo detenido");
        }
    }

    /**
     * Permite crear la instancia del objeto GopAlertaTiempoDetenido
     */
    public void prepareGuardar() {
        gopAlertaTiempoDetenido = new GopAlertaTiempoDetenido();
    }

    public void reset() {
        gopAlertaTiempoDetenido = null;
    }

    /**
     * Permite capturar el objeto GopAlertaTiempoDetenido seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto GopAlertaTiempoDetenido
     */
    public void onRowSelect(SelectEvent event) {
        gopAlertaTiempoDetenido = (GopAlertaTiempoDetenido) event.getObject();
    }

    public GopAlertaTiempoDetenidoFacadeLocal getGopAlertaTiempoDetenidoFacadeLocal() {
        return gopAlertaTiempoDetenidoFacadeLocal;
    }

    public void setGopAlertaTiempoDetenidoFacadeLocal(GopAlertaTiempoDetenidoFacadeLocal gopAlertaTiempoDetenidoFacadeLocal) {
        this.gopAlertaTiempoDetenidoFacadeLocal = gopAlertaTiempoDetenidoFacadeLocal;
    }

    public GopAlertaTiempoDetenido getGopAlertaTiempoDetenido() {
        return gopAlertaTiempoDetenido;
    }

    public void setGopAlertaTiempoDetenido(GopAlertaTiempoDetenido gopAlertaTiempoDetenido) {
        this.gopAlertaTiempoDetenido = gopAlertaTiempoDetenido;
    }

    public List<GopAlertaTiempoDetenido> getListGopAlertaTiempoDetenido() {
        listGopAlertaTiempoDetenido = gopAlertaTiempoDetenidoFacadeLocal.findAllEstadoReg();
        return listGopAlertaTiempoDetenido;
    }

    public void setListGopAlertaTiempoDetenido(List<GopAlertaTiempoDetenido> listGopAlertaTiempoDetenido) {
        this.listGopAlertaTiempoDetenido = listGopAlertaTiempoDetenido;
    }

}
