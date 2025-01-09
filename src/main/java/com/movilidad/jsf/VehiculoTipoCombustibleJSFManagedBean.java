package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoTipoCombustibleFacadeLocal;
import com.movilidad.model.VehiculoTipoCombustible;
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
@Named(value = "vehiculoTipoCombustibleBean")
@ViewScoped
public class VehiculoTipoCombustibleJSFManagedBean implements Serializable {
    
    @EJB
    private VehiculoTipoCombustibleFacadeLocal vehiculoTipoCombustibleEjb;
    private VehiculoTipoCombustible vehiculoTipoCombustible;
    private List<VehiculoTipoCombustible> lista;
    private VehiculoTipoCombustible selected;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    @PostConstruct
    public void init() {
        this.lista = this.vehiculoTipoCombustibleEjb.findAll();
        this.vehiculoTipoCombustible = new VehiculoTipoCombustible();
        this.selected = null;
    }
    
    public void guardar() {
        vehiculoTipoCombustible.setUsername(user.getUsername());
        vehiculoTipoCombustible.setCreado(new Date());
        this.vehiculoTipoCombustibleEjb.create(vehiculoTipoCombustible);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Tipo de combustible agregado."));
    }
    
    public void actualizar() {
        vehiculoTipoCombustible.setUsername(user.getUsername());
        this.vehiculoTipoCombustibleEjb.edit(vehiculoTipoCombustible);
        MovilidadUtil.hideModal("mtipo");
        MovilidadUtil.addSuccessMessage("Tipo de combustible actualizado.");
    }
    
    public void editar() {
        this.vehiculoTipoCombustible = this.selected;
    }
    
    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoTipoCombustibleEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado Tipo de combustible cambiado éxitosamente."));
    }
    
    public void nuevo() {
        this.vehiculoTipoCombustible = new VehiculoTipoCombustible();
        this.selected = null;
    }
    
    public VehiculoTipoCombustibleFacadeLocal getVehiculoTipoCombustibleEjb() {
        return vehiculoTipoCombustibleEjb;
    }
    
    public void setVehiculoTipoCombustibleEjb(VehiculoTipoCombustibleFacadeLocal vehiculoTipoCombustibleEjb) {
        this.vehiculoTipoCombustibleEjb = vehiculoTipoCombustibleEjb;
    }
    
    public VehiculoTipoCombustible getVehiculoTipoCombustible() {
        return vehiculoTipoCombustible;
    }
    
    public void setVehiculoTipoCombustible(VehiculoTipoCombustible vehiculoTipoCombustible) {
        this.vehiculoTipoCombustible = vehiculoTipoCombustible;
    }
    
    public VehiculoTipoCombustible getSelected() {
        return selected;
    }
    
    public void setSelected(VehiculoTipoCombustible selected) {
        this.selected = selected;
    }
    
    public List<VehiculoTipoCombustible> getLista() {
        return lista;
    }
    
    public void setLista(List<VehiculoTipoCombustible> lista) {
        this.lista = lista;
    }
}
