package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoDanoSeveridadFacadeLocal;
import com.movilidad.model.VehiculoDanoSeveridad;
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
@Named(value = "vehiculoDanoSeveridadBean")
@ViewScoped
public class VehiculoDanoSeveridadJSFManagedBean implements Serializable {

    @EJB
    private VehiculoDanoSeveridadFacadeLocal vehiculoDanoSeveridadEjb;
    private VehiculoDanoSeveridad vehiculoDanoSeveridad;
    private List<VehiculoDanoSeveridad> lista;
    private VehiculoDanoSeveridad selected;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        this.lista = this.vehiculoDanoSeveridadEjb.findAll();
        this.vehiculoDanoSeveridad = new VehiculoDanoSeveridad();
        this.selected = null;
    }

    public void guardar() {
        vehiculoDanoSeveridad.setUsername(user.getUsername());
        vehiculoDanoSeveridad.setCreado(new Date());
        this.vehiculoDanoSeveridadEjb.create(vehiculoDanoSeveridad);
        nuevo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Severidad de daño agregado."));
    }

    public void actualizar() {
        vehiculoDanoSeveridad.setUsername(user.getUsername());
        this.vehiculoDanoSeveridadEjb.edit(vehiculoDanoSeveridad);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Severidad de daño actualizado."));
    }

    public void editar() {
        this.vehiculoDanoSeveridad = this.selected;
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoDanoSeveridadEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado de severidad daño cambiado éxitosamente."));
    }

    public void nuevo() {
        this.vehiculoDanoSeveridad = new VehiculoDanoSeveridad();
        this.selected = null;
    }

    public VehiculoDanoSeveridadFacadeLocal getVehiculoDanoSeveridadEjb() {
        return vehiculoDanoSeveridadEjb;
    }

    public void setVehiculoDanoSeveridadEjb(VehiculoDanoSeveridadFacadeLocal vehiculoDanoSeveridadEjb) {
        this.vehiculoDanoSeveridadEjb = vehiculoDanoSeveridadEjb;
    }

    public VehiculoDanoSeveridad getVehiculoDanoSeveridad() {
        return vehiculoDanoSeveridad;
    }

    public void setVehiculoDanoSeveridad(VehiculoDanoSeveridad vehiculoDanoSeveridad) {
        this.vehiculoDanoSeveridad = vehiculoDanoSeveridad;
    }

    public VehiculoDanoSeveridad getSelected() {
        return selected;
    }

    public void setSelected(VehiculoDanoSeveridad selected) {
        this.selected = selected;
    }

    public List<VehiculoDanoSeveridad> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoDanoSeveridad> lista) {
        this.lista = lista;
    }
}
