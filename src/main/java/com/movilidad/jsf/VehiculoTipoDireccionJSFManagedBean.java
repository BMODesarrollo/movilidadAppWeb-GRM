/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoTipoDireccionFacadeLocal;
import com.movilidad.model.VehiculoTipoDireccion;
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
@Named(value = "vehiculoTipoDireccionBean")
@ViewScoped
public class VehiculoTipoDireccionJSFManagedBean implements Serializable {
    
    @EJB
    private VehiculoTipoDireccionFacadeLocal vehiculoTipoDireccionEjb;
    private VehiculoTipoDireccion vehiculoTipoDireccion;
    private List<VehiculoTipoDireccion> lista;
    private VehiculoTipoDireccion selected;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    @PostConstruct
    public void init() {
        this.lista = this.vehiculoTipoDireccionEjb.findAll();
        this.vehiculoTipoDireccion = new VehiculoTipoDireccion();
        this.selected = null;
    }
    
    public void guardar() {
        vehiculoTipoDireccion.setUsername(user.getUsername());
        vehiculoTipoDireccion.setCreado(new Date());
        this.vehiculoTipoDireccionEjb.create(vehiculoTipoDireccion);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Tipo de dirección agregado."));
    }
    
    public void actualizar() {
        vehiculoTipoDireccion.setUsername(user.getUsername());
        this.vehiculoTipoDireccionEjb.edit(vehiculoTipoDireccion);
        MovilidadUtil.hideModal("mtipo");
        MovilidadUtil.addSuccessMessage("Tipo de dirección actualizado.");
    }
    
    public void editar() {
        this.vehiculoTipoDireccion = this.selected;
    }
    
    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoTipoDireccionEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado Tipo de dirección cambiado éxitosamente."));
    }
    
    public void nuevo() {
        this.vehiculoTipoDireccion = new VehiculoTipoDireccion();
        this.selected = null;
    }
    
    public VehiculoTipoDireccionFacadeLocal getVehiculoTipoDireccionEjb() {
        return vehiculoTipoDireccionEjb;
    }
    
    public void setVehiculoTipoDireccionEjb(VehiculoTipoDireccionFacadeLocal vehiculoTipoDireccionEjb) {
        this.vehiculoTipoDireccionEjb = vehiculoTipoDireccionEjb;
    }
    
    public VehiculoTipoDireccion getVehiculoTipoDireccion() {
        return vehiculoTipoDireccion;
    }
    
    public void setVehiculoTipoDireccion(VehiculoTipoDireccion vehiculoTipoDireccion) {
        this.vehiculoTipoDireccion = vehiculoTipoDireccion;
    }
    
    public VehiculoTipoDireccion getSelected() {
        return selected;
    }
    
    public void setSelected(VehiculoTipoDireccion selected) {
        this.selected = selected;
    }
    
    public List<VehiculoTipoDireccion> getLista() {
        return lista;
    }
    
    public void setLista(List<VehiculoTipoDireccion> lista) {
        this.lista = lista;
    }
    
}
