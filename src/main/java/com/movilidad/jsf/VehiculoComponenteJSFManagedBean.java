package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoComponenteZonaFacadeLocal;
import com.movilidad.ejb.VehiculoComponenteFacadeLocal;
import com.movilidad.model.VehiculoComponenteZona;
import com.movilidad.model.VehiculoComponente;
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
@Named(value = "vehiculoComponenteBean")
@ViewScoped
public class VehiculoComponenteJSFManagedBean implements Serializable {

    @EJB
    private VehiculoComponenteFacadeLocal vehiculoComponenteEjb;
    @EJB
    private VehiculoComponenteZonaFacadeLocal grupoZonaEJB;
    private VehiculoComponente vechiculoComponente;
    private VehiculoComponenteZona vehiCompZona;
    private List<VehiculoComponente> lista;
    private List<VehiculoComponenteZona> lstCteZona;
    private VehiculoComponente selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        this.lista = this.vehiculoComponenteEjb.findAll();
        this.lstCteZona = this.grupoZonaEJB.findAll();
        this.vechiculoComponente = new VehiculoComponente();
        this.vehiCompZona = new VehiculoComponenteZona();
        this.selected = null;
    }

    public void guardar() {
        vechiculoComponente.setIdVehiculoComponenteZona(vehiCompZona);
        vechiculoComponente.setUsername(user.getUsername());
        vechiculoComponente.setCreado(new Date());
        this.vehiculoComponenteEjb.create(vechiculoComponente);
        VehiculoComponenteZona x = this.grupoZonaEJB.find(vehiCompZona.getIdVehiculoComponenteZona());
        vechiculoComponente.setIdVehiculoComponenteZona(x);
        this.lista.add(vechiculoComponente);
        nuevo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Componente registrado éxitosamente."));
    }

    public void actualizar() {
        vechiculoComponente.setUsername(user.getUsername());
        vechiculoComponente.setIdVehiculoComponenteZona(vehiCompZona);
        this.vehiculoComponenteEjb.edit(vechiculoComponente);
        VehiculoComponenteZona x = this.grupoZonaEJB.find(vehiCompZona.getIdVehiculoComponenteZona());
        vechiculoComponente.setIdVehiculoComponenteZona(x);
        nuevo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Componente actualizado éxitosamente."));
    }

    public void editar() {
        this.vechiculoComponente = this.selected;
        vehiCompZona.setIdVehiculoComponenteZona(selected.getIdVehiculoComponenteZona().getIdVehiculoComponenteZona());
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoComponenteEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "El estado del componente fue cambiado éxitosamente."));
    }

    public void nuevo() {
        this.vechiculoComponente = new VehiculoComponente();
        this.vehiCompZona = new VehiculoComponenteZona();
        this.selected = null;
    }

    public VehiculoComponenteZona getVehiCompZona() {
        return vehiCompZona;
    }

    public void setVehiCompZona(VehiculoComponenteZona vehiCompZona) {
        this.vehiCompZona = vehiCompZona;
    }

    public VehiculoComponenteFacadeLocal getVehiculoComponenteEjb() {
        return vehiculoComponenteEjb;
    }

    public void setVehiculoComponenteEjb(VehiculoComponenteFacadeLocal vehiculoComponenteEjb) {
        this.vehiculoComponenteEjb = vehiculoComponenteEjb;
    }

    public VehiculoComponente getVechiculoComponente() {
        return vechiculoComponente;
    }

    public void setVechiculoComponente(VehiculoComponente vechiculoComponente) {
        this.vechiculoComponente = vechiculoComponente;
    }

    public VehiculoComponente getSelected() {
        return selected;
    }

    public void setSelected(VehiculoComponente selected) {
        this.selected = selected;
    }

    public List<VehiculoComponente> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoComponente> lista) {
        this.lista = lista;
    }

    public void setLstCteZona(List<VehiculoComponenteZona> lstCteZona) {
        this.lstCteZona = lstCteZona;
    }

    public List<VehiculoComponenteZona> getLstCteZona() {
        return lstCteZona;
    }

}
