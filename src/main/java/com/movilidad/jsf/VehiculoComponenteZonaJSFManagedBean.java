package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoComponenteGrupoFacadeLocal;
import com.movilidad.ejb.VehiculoComponenteZonaFacadeLocal;
import com.movilidad.model.VehiculoComponenteGrupo;
import com.movilidad.model.VehiculoComponenteZona;
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
@Named(value = "vehiculoComponenteZonaBean")
@ViewScoped
public class VehiculoComponenteZonaJSFManagedBean implements Serializable {

    @EJB
    private VehiculoComponenteZonaFacadeLocal vehiculoComponenteZonaEjb;
    @EJB
    private VehiculoComponenteGrupoFacadeLocal grupoCteEJB;
    private VehiculoComponenteZona vehiculoComponenteZona;
    private VehiculoComponenteGrupo vehiCompGrupo;
    private List<VehiculoComponenteZona> lista;
    private List<VehiculoComponenteGrupo> lstCteGrupo;
    private VehiculoComponenteZona selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        this.lista = this.vehiculoComponenteZonaEjb.findAll();
        this.lstCteGrupo = this.grupoCteEJB.findAll();
        this.vehiculoComponenteZona = new VehiculoComponenteZona();
        this.vehiCompGrupo = new VehiculoComponenteGrupo();
        this.selected = null;
    }

    public void guardar() {
        if (vehiCompGrupo == null) {
            MovilidadUtil.addErrorMessage("Seleccionar grupo componente.");
            return;
        }
        if (vehiCompGrupo.getIdVehiculoComponenteGrupo() == null) {
            MovilidadUtil.addErrorMessage("Seleccionar grupo componente.");
            return;
        }
        if (MovilidadUtil.getWithoutSpaces(vehiculoComponenteZona.getNombre()).isEmpty()) {
            MovilidadUtil.addErrorMessage("El nombre es requerido.");
            return;
        }
        if (MovilidadUtil.getWithoutSpaces(vehiculoComponenteZona.getDescripcion()).isEmpty()) {
            MovilidadUtil.addErrorMessage("la descripción es requerida.");
            return;
        }
        vehiculoComponenteZona.setIdVehiculoComponenteGrupo(vehiCompGrupo);
        vehiculoComponenteZona.setUsername(user.getUsername());
        vehiculoComponenteZona.setCreado(new Date());
        this.vehiculoComponenteZonaEjb.create(vehiculoComponenteZona);
        VehiculoComponenteGrupo x = this.grupoCteEJB.find(vehiCompGrupo.getIdVehiculoComponenteGrupo());
        vehiculoComponenteZona.setIdVehiculoComponenteGrupo(x);
        this.lista.add(vehiculoComponenteZona);
        nuevo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Zona de componente registrada éxitosamente."));
    }

    public void actualizar() {
        vehiculoComponenteZona.setUsername(user.getUsername());
        vehiculoComponenteZona.setIdVehiculoComponenteGrupo(vehiCompGrupo);
        this.vehiculoComponenteZonaEjb.edit(vehiculoComponenteZona);
        VehiculoComponenteGrupo x = this.grupoCteEJB.find(vehiCompGrupo.getIdVehiculoComponenteGrupo());
        vehiculoComponenteZona.setIdVehiculoComponenteGrupo(x);
        MovilidadUtil.addSuccessMessage("Zona de componente actualizada éxitosamente.");
        MovilidadUtil.hideModal("mtipo");
    }

    public void editar() {
        this.vehiculoComponenteZona = this.selected;
        vehiCompGrupo.setIdVehiculoComponenteGrupo(selected.getIdVehiculoComponenteGrupo().getIdVehiculoComponenteGrupo());
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoComponenteZonaEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "El estado zona de componente fue cambiado éxitosamente."));
    }

    public void nuevo() {
        this.vehiculoComponenteZona = new VehiculoComponenteZona();
        this.vehiCompGrupo = new VehiculoComponenteGrupo();
        this.selected = null;
    }

    public VehiculoComponenteGrupo getVehiCompGrupo() {
        return vehiCompGrupo;
    }

    public void setVehiCompGrupo(VehiculoComponenteGrupo vehiCompGrupo) {
        this.vehiCompGrupo = vehiCompGrupo;
    }

    public VehiculoComponenteZonaFacadeLocal getVehiculoComponenteZonaEjb() {
        return vehiculoComponenteZonaEjb;
    }

    public void setVehiculoComponenteZonaEjb(VehiculoComponenteZonaFacadeLocal vehiculoComponenteZonaEjb) {
        this.vehiculoComponenteZonaEjb = vehiculoComponenteZonaEjb;
    }

    public VehiculoComponenteZona getVehiculoComponenteZona() {
        return vehiculoComponenteZona;
    }

    public void setVehiculoComponenteZona(VehiculoComponenteZona vehiculoComponenteZona) {
        this.vehiculoComponenteZona = vehiculoComponenteZona;
    }

    public VehiculoComponenteZona getSelected() {
        return selected;
    }

    public void setSelected(VehiculoComponenteZona selected) {
        this.selected = selected;
    }

    public List<VehiculoComponenteZona> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoComponenteZona> lista) {
        this.lista = lista;
    }

    public void setLstCteGrupo(List<VehiculoComponenteGrupo> lstCteGrupo) {
        this.lstCteGrupo = lstCteGrupo;
    }

    public List<VehiculoComponenteGrupo> getLstCteGrupo() {
        return lstCteGrupo;
    }

}
