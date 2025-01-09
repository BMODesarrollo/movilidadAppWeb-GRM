package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoPropietarioFacadeLocal;
import com.movilidad.model.VehiculoPropietarios;
import com.movilidad.security.UserExtended;
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
@Named(value = "vehiculoPropietarioBean")
@ViewScoped
public class VehiculoPropietarioJSFManagedBean implements Serializable {

    @EJB
    private VehiculoPropietarioFacadeLocal vehiculoPropietarioEjb;
    private VehiculoPropietarios vehiculoPropietario;
    private List<VehiculoPropietarios> lista;
    private VehiculoPropietarios selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        this.lista = this.vehiculoPropietarioEjb.findAll();
        this.vehiculoPropietario = new VehiculoPropietarios();
        this.selected = null;
    }

    public void guardar() {
        vehiculoPropietario.setUsername(user.getUsername());
        vehiculoPropietario.setCreado(new Date());
        this.vehiculoPropietarioEjb.create(vehiculoPropietario);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos del propietario registrados éxitosamente."));
    }

    public void actualizar() {
        vehiculoPropietario.setUsername(user.getUsername());
        this.vehiculoPropietarioEjb.edit(vehiculoPropietario);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Datos del propietario actualizados éxitosamente."));
    }

    public void editar() {
        this.vehiculoPropietario = this.selected;
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoPropietarioEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "El estado de registro fue cambiado éxitosamente."));
    }

    public void nuevo() {
        this.vehiculoPropietario = new VehiculoPropietarios();
        this.selected = null;
    }

    public VehiculoPropietarioFacadeLocal getVehiculoPropietarioEjb() {
        return vehiculoPropietarioEjb;
    }

    public void setVehiculoPropietarioEjb(VehiculoPropietarioFacadeLocal vehiculoPropietarioEjb) {
        this.vehiculoPropietarioEjb = vehiculoPropietarioEjb;
    }

    public VehiculoPropietarios getVehiculoPropietario() {
        return vehiculoPropietario;
    }

    public void setVehiculoPropietario(VehiculoPropietarios vehiculoPropietario) {
        this.vehiculoPropietario = vehiculoPropietario;
    }

    public List<VehiculoPropietarios> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoPropietarios> lista) {
        this.lista = lista;
    }

    public VehiculoPropietarios getSelected() {
        return selected;
    }

    public void setSelected(VehiculoPropietarios selected) {
        this.selected = selected;
    }

}
