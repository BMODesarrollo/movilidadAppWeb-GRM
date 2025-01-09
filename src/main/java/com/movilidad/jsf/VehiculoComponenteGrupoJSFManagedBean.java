package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoComponenteGrupoFacadeLocal;
import com.movilidad.model.VehiculoComponenteGrupo;
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
@Named(value = "vehiculoComponenteGrupoBean")
@ViewScoped
public class VehiculoComponenteGrupoJSFManagedBean implements Serializable {

    @EJB
    private VehiculoComponenteGrupoFacadeLocal vehiculoComponenteEjb;
    private VehiculoComponenteGrupo vehiculoComponenteGrupo;
    private List<VehiculoComponenteGrupo> lista;
    private VehiculoComponenteGrupo selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        this.lista = this.vehiculoComponenteEjb.findAll();
        this.vehiculoComponenteGrupo = new VehiculoComponenteGrupo();
        this.selected = null;
    }

    public void guardar() {
        vehiculoComponenteGrupo.setUsername(user.getUsername());
        vehiculoComponenteGrupo.setCreado(new Date());
        this.vehiculoComponenteEjb.create(vehiculoComponenteGrupo);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Grupo de componente registrado éxitosamente."));
    }

    public void actualizar() {
        vehiculoComponenteGrupo.setUsername(user.getUsername());
        this.vehiculoComponenteEjb.edit(vehiculoComponenteGrupo);
        MovilidadUtil.hideModal("mtipo");
        MovilidadUtil.addSuccessMessage("Grupo de componente actualizado éxitosamente.");
    }

    public void editar() {
        this.vehiculoComponenteGrupo = this.selected;
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoComponenteEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "El estado del grupo de componente fue cambiado éxitosamente."));
    }

    public void nuevo() {
        this.vehiculoComponenteGrupo = new VehiculoComponenteGrupo();
        this.selected = null;
    }

    public VehiculoComponenteGrupoFacadeLocal getVehiculoComponenteEjb() {
        return vehiculoComponenteEjb;
    }

    public void setVehiculoComponenteEjb(VehiculoComponenteGrupoFacadeLocal vehiculoComponenteEjb) {
        this.vehiculoComponenteEjb = vehiculoComponenteEjb;
    }

    public VehiculoComponenteGrupo getVehiculoComponenteGrupo() {
        return vehiculoComponenteGrupo;
    }

    public void setVehiculoComponenteGrupo(VehiculoComponenteGrupo vehiculoComponenteGrupo) {
        this.vehiculoComponenteGrupo = vehiculoComponenteGrupo;
    }

    public VehiculoComponenteGrupo getSelected() {
        return selected;
    }

    public void setSelected(VehiculoComponenteGrupo selected) {
        this.selected = selected;
    }

    public List<VehiculoComponenteGrupo> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoComponenteGrupo> lista) {
        this.lista = lista;
    }

}
