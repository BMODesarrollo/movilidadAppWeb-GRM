/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoTipoTransmisionFacadeLocal;
import com.movilidad.model.VehiculoTipoTransmision;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "vehiculoTipoTransmisionBean")
@ViewScoped
public class VehiculoTipoTransmisionJSFManagedBean implements Serializable {

    @EJB
    private VehiculoTipoTransmisionFacadeLocal vehiculoTipoTransmisionEjb;
    private VehiculoTipoTransmision vehiculoTipoTransmision;
    private List<VehiculoTipoTransmision> lista;
    private VehiculoTipoTransmision selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        this.lista = this.vehiculoTipoTransmisionEjb.findAll();
        this.vehiculoTipoTransmision = new VehiculoTipoTransmision();
        this.selected = null;
    }

    public void guardar() {
        vehiculoTipoTransmision.setUsername(user.getUsername());
        vehiculoTipoTransmision.setCreado(new Date());
        this.vehiculoTipoTransmisionEjb.create(vehiculoTipoTransmision);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Tipo de transmisión agregado."));
    }

    public void actualizar() {
        vehiculoTipoTransmision.setUsername(user.getUsername());
        this.vehiculoTipoTransmisionEjb.edit(vehiculoTipoTransmision);
        MovilidadUtil.addSuccessMessage("Tipo de transmisión actualizado.");
        MovilidadUtil.hideModal("mtipo");
    }

    public void editar() {
        this.vehiculoTipoTransmision = this.selected;
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoTipoTransmisionEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado Tipo de transmisión cambiado éxitosamente."));
    }

    public void nuevo() {
        this.vehiculoTipoTransmision = new VehiculoTipoTransmision();
        this.selected = null;
    }

    public VehiculoTipoTransmisionFacadeLocal getVehiculoTipoTransmisionEjb() {
        return vehiculoTipoTransmisionEjb;
    }

    public void setVehiculoTipoTransmisionEjb(VehiculoTipoTransmisionFacadeLocal vehiculoTipoTransmisionEjb) {
        this.vehiculoTipoTransmisionEjb = vehiculoTipoTransmisionEjb;
    }

    public VehiculoTipoTransmision getVehiculoTipoTransmision() {
        return vehiculoTipoTransmision;
    }

    public void setVehiculoTipoTransmision(VehiculoTipoTransmision vehiculoTipoTransmision) {
        this.vehiculoTipoTransmision = vehiculoTipoTransmision;
    }

    public VehiculoTipoTransmision getSelected() {
        return selected;
    }

    public void setSelected(VehiculoTipoTransmision selected) {
        this.selected = selected;
    }

    public List<VehiculoTipoTransmision> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoTipoTransmision> lista) {
        this.lista = lista;
    }

}
