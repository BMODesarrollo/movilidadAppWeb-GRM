package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.VehiculoTipo;
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
@Named(value = "vehiculoTipoBean")
@ViewScoped
public class VehiculoTipoJSFManagedBean implements Serializable {

    @EJB
    private VehiculoTipoFacadeLocal vehiculoTipoEjb;
    private VehiculoTipo vehiculoTipo;
    private List<VehiculoTipo> lista;
    private VehiculoTipo selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        this.lista = this.vehiculoTipoEjb.findAll();
        this.vehiculoTipo = new VehiculoTipo();
        this.selected = null;
    }

    public void guardar() {
        vehiculoTipo.setUsername(user.getUsername());
        vehiculoTipo.setCreado(new Date());
        this.vehiculoTipoEjb.create(vehiculoTipo);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Tipo de vehículo agregado."));
    }

    public void actualizar() {
        vehiculoTipo.setUsername(user.getUsername());
        this.vehiculoTipoEjb.edit(vehiculoTipo);
        MovilidadUtil.addSuccessMessage("Tipo de vehículo actualizado.");
        MovilidadUtil.hideModal("mtipo");
    }

    public void editar() {
        this.vehiculoTipo = this.selected;
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoTipoEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado Tipo de vehículo cambiado éxitosamente."));
    }

    public void nuevo() {
        this.vehiculoTipo = new VehiculoTipo();
        this.selected = null;
    }

    public VehiculoTipoFacadeLocal getVehiculoTipoEjb() {
        return vehiculoTipoEjb;
    }

    public void setVehiculoTipoEjb(VehiculoTipoFacadeLocal vehiculoTipoEjb) {
        this.vehiculoTipoEjb = vehiculoTipoEjb;
    }

    public VehiculoTipo getVehiculoTipo() {
        return vehiculoTipo;
    }

    public void setVehiculoTipo(VehiculoTipo VehiculoTipo) {
        this.vehiculoTipo = VehiculoTipo;
    }

    public VehiculoTipo getSelected() {
        return selected;
    }

    public void setSelected(VehiculoTipo selected) {
        this.selected = selected;
    }

    public List<VehiculoTipo> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoTipo> lista) {
        this.lista = lista;
    }
}
