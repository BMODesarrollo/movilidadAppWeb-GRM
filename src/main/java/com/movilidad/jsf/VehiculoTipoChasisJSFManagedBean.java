package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoTipoChasisFacadeLocal;
import com.movilidad.model.VehiculoTipoChasis;
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
@Named(value = "vehiculoTipoChasisBean")
@ViewScoped
public class VehiculoTipoChasisJSFManagedBean implements Serializable {

    @EJB
    private VehiculoTipoChasisFacadeLocal vehiculoTipoChasisEjb;
    private VehiculoTipoChasis vehiculoTipoChasis;
    private List<VehiculoTipoChasis> lista;
    private VehiculoTipoChasis selected;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        this.lista = this.vehiculoTipoChasisEjb.findAll();
        this.vehiculoTipoChasis = new VehiculoTipoChasis();
        this.selected = null;
    }

    public void guardar() {
        vehiculoTipoChasis.setUsername(user.getUsername());
        vehiculoTipoChasis.setCreado(new Date());
        this.vehiculoTipoChasisEjb.create(vehiculoTipoChasis);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Tipo de chasis agregado."));
    }

    public void actualizar() {
        vehiculoTipoChasis.setUsername(user.getUsername());
        this.vehiculoTipoChasisEjb.edit(vehiculoTipoChasis);
        MovilidadUtil.hideModal("mtipo");
        MovilidadUtil.addSuccessMessage("Tipo de chasis actualizado.");
    }

    public void editar() {
        this.vehiculoTipoChasis = this.selected;
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoTipoChasisEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado Tipo de chasis cambiado éxitosamente."));
    }

    public void nuevo() {
        this.vehiculoTipoChasis = new VehiculoTipoChasis();
        this.selected = null;
    }

    public VehiculoTipoChasisFacadeLocal getVehiculoTipoChasisEjb() {
        return vehiculoTipoChasisEjb;
    }

    public void setVehiculoTipoChasisEjb(VehiculoTipoChasisFacadeLocal vehiculoTipoChasisEjb) {
        this.vehiculoTipoChasisEjb = vehiculoTipoChasisEjb;
    }

    public VehiculoTipoChasis getVehiculoTipoChasis() {
        return vehiculoTipoChasis;
    }

    public void setVehiculoTipoChasis(VehiculoTipoChasis vehiculoTipoChasis) {
        this.vehiculoTipoChasis = vehiculoTipoChasis;
    }

    public VehiculoTipoChasis getSelected() {
        return selected;
    }

    public void setSelected(VehiculoTipoChasis selected) {
        this.selected = selected;
    }

    public List<VehiculoTipoChasis> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoTipoChasis> lista) {
        this.lista = lista;
    }

}
