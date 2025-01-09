package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoComponenteDanoFacadeLocal;
import com.movilidad.ejb.VehiculoComponenteGrupoFacadeLocal;
import com.movilidad.ejb.VehiculoDanoFacadeLocal;
import com.movilidad.model.VehiculoComponenteDano;
import com.movilidad.model.VehiculoComponenteGrupo;
import com.movilidad.model.VehiculoDano;
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
@Named(value = "vehiculoComponenteDanoBean")
@ViewScoped
public class VehiculoComponenteDanoJSFManagedBean implements Serializable {

    @EJB
    private VehiculoComponenteDanoFacadeLocal vehiculoComponenteDanoEjb;
    @EJB
    private VehiculoComponenteGrupoFacadeLocal vehiculoComponenteGrupoEjb;
    @EJB
    private VehiculoDanoFacadeLocal vehiculoDanoEjb;

    private VehiculoComponenteDano vehiculoComponenteDano;
    private VehiculoComponenteGrupo vehiculoComponenteGrupo;
    private VehiculoDano vehiculoDano;

    private List<VehiculoComponenteDano> lista;
    private List<VehiculoComponenteGrupo> lstVehiculoComponente;
    private List<VehiculoDano> lstvehiculoDano;
    private VehiculoComponenteDano selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        this.lista = this.vehiculoComponenteDanoEjb.findAll();
        this.lstVehiculoComponente = this.vehiculoComponenteGrupoEjb.findAll();
        this.lstvehiculoDano = this.vehiculoDanoEjb.findAll();
        this.vehiculoComponenteDano = new VehiculoComponenteDano();
        this.vehiculoComponenteGrupo = new VehiculoComponenteGrupo();
        this.vehiculoDano = new VehiculoDano();
        this.selected = null;
    }

    public void guardar() {
        VehiculoComponenteDano res = vehiculoComponenteDanoEjb.findByIdCompGrupoAndIdVehiculoDano(
                vehiculoComponenteGrupo.getIdVehiculoComponenteGrupo(), vehiculoDano.getIdVehiculoDano(), 0);
        if (res != null) {
            MovilidadUtil.addErrorMessage("Ya existe un componente de daño similar.");
            return;
        }
        vehiculoComponenteDano.setIdVehiculoComponenteGrupo(vehiculoComponenteGrupo);
        vehiculoComponenteDano.setIdVehiculoDano(vehiculoDano);
        vehiculoComponenteDano.setUsername(user.getUsername());
        vehiculoComponenteDano.setCreado(new Date());
        this.vehiculoComponenteDanoEjb.create(vehiculoComponenteDano);
        VehiculoComponenteGrupo x = this.vehiculoComponenteGrupoEjb.find(vehiculoComponenteGrupo.getIdVehiculoComponenteGrupo());
        VehiculoDano y = this.vehiculoDanoEjb.find(vehiculoDano.getIdVehiculoDano());
        vehiculoComponenteDano.setIdVehiculoComponenteGrupo(x);
        vehiculoComponenteDano.setIdVehiculoDano(y);
        this.lista.add(vehiculoComponenteDano);
        MovilidadUtil.addSuccessMessage("Componente de daño registrado éxitosamente.");
        nuevo();
    }

    public void actualizar() {
        VehiculoComponenteDano res = vehiculoComponenteDanoEjb.findByIdCompGrupoAndIdVehiculoDano(
                vehiculoComponenteGrupo.getIdVehiculoComponenteGrupo(),
                vehiculoDano.getIdVehiculoDano(),
                vehiculoComponenteDano.getIdVehiculoComponenteDano());
        if (res != null) {
            MovilidadUtil.addErrorMessage("Ya existe un componente de daño similar.");
            return;
        }
        vehiculoComponenteDano.setUsername(user.getUsername());
        vehiculoComponenteDano.setIdVehiculoComponenteGrupo(vehiculoComponenteGrupo);
        vehiculoComponenteDano.setIdVehiculoDano(vehiculoDano);
        this.vehiculoComponenteDanoEjb.edit(vehiculoComponenteDano);
        VehiculoComponenteGrupo x = this.vehiculoComponenteGrupoEjb.find(vehiculoComponenteGrupo.getIdVehiculoComponenteGrupo());
        VehiculoDano y = this.vehiculoDanoEjb.find(vehiculoDano.getIdVehiculoDano());
        vehiculoComponenteDano.setIdVehiculoComponenteGrupo(x);
        vehiculoComponenteDano.setIdVehiculoDano(y);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Componente de daño actualizado éxitosamente."));
    }

    public void editar() {
        this.vehiculoComponenteDano = this.selected;
        vehiculoComponenteGrupo.setIdVehiculoComponenteGrupo(selected.getIdVehiculoComponenteGrupo().getIdVehiculoComponenteGrupo());
        vehiculoDano.setIdVehiculoDano(selected.getIdVehiculoDano().getIdVehiculoDano());
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoComponenteDanoEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado componente de daño fue cambiado éxitosamente."));
    }

    public void nuevo() {
        this.vehiculoComponenteDano = new VehiculoComponenteDano();
        this.vehiculoComponenteGrupo = new VehiculoComponenteGrupo();
        this.vehiculoDano = new VehiculoDano();
        this.selected = null;
    }

    public VehiculoComponenteDano getVehiculoComponenteDano() {
        return vehiculoComponenteDano;
    }

    public void setVehiculoComponenteDano(VehiculoComponenteDano vehiculoComponenteDano) {
        this.vehiculoComponenteDano = vehiculoComponenteDano;
    }

    public VehiculoDano getVehiculoDano() {
        return vehiculoDano;
    }

    public void setVehiculoDano(VehiculoDano vehiculoDano) {
        this.vehiculoDano = vehiculoDano;
    }

    public VehiculoComponenteDano getSelected() {
        return selected;
    }

    public void setSelected(VehiculoComponenteDano selected) {
        this.selected = selected;
    }

    public List<VehiculoComponenteDano> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoComponenteDano> lista) {
        this.lista = lista;
    }

    public List<VehiculoDano> getLstvehiculoDano() {
        return lstvehiculoDano;
    }

    public void setLstvehiculoDano(List<VehiculoDano> lstvehiculoDano) {
        this.lstvehiculoDano = lstvehiculoDano;
    }

    public VehiculoComponenteDanoFacadeLocal getVehiculoComponenteDanoEjb() {
        return vehiculoComponenteDanoEjb;
    }

    public void setVehiculoComponenteDanoEjb(VehiculoComponenteDanoFacadeLocal vehiculoComponenteDanoEjb) {
        this.vehiculoComponenteDanoEjb = vehiculoComponenteDanoEjb;
    }

    public List<VehiculoComponenteGrupo> getLstVehiculoComponente() {
        return lstVehiculoComponente;
    }

    public void setLstVehiculoComponente(List<VehiculoComponenteGrupo> lstVehiculoComponente) {
        this.lstVehiculoComponente = lstVehiculoComponente;
    }

    public VehiculoComponenteGrupo getVehiculoComponenteGrupo() {
        return vehiculoComponenteGrupo;
    }

    public void setVehiculoComponenteGrupo(VehiculoComponenteGrupo vehiculoComponenteGrupo) {
        this.vehiculoComponenteGrupo = vehiculoComponenteGrupo;
    }

}
