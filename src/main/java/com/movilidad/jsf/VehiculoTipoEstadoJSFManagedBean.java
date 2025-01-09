package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoTipoEstadoFacadeLocal;
import com.movilidad.model.VehiculoTipoEstado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
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
@Named(value = "vehiculoTipoEstadoBean")
@ViewScoped
public class VehiculoTipoEstadoJSFManagedBean implements Serializable {

    @EJB
    private VehiculoTipoEstadoFacadeLocal vehiculoTipoEstadoEjb;
    private VehiculoTipoEstado vehiculoTipoEstado;
    private List<VehiculoTipoEstado> lista;
    private VehiculoTipoEstado selected;
    private boolean restriccion_operacion;
    private boolean b_cierra_novedad;

    UserExtended user;

    @PostConstruct
    public void init() {
        consultar();
//        this.vehiculoTipoEstado = new VehiculoTipoEstado();
//        this.selected = null;
//        this.restriccion_operacion = false;
//        this.b_cierra_novedad = false;
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    void consultar() {
        this.lista = this.vehiculoTipoEstadoEjb.findByEstadoReg();
    }

    boolean validar() {
        if (b_cierra_novedad && vehiculoTipoEstadoEjb.findEstadoCierraNovedad(
                vehiculoTipoEstado.getIdVehiculoTipoEstado() == null
                ? 0 : vehiculoTipoEstado.getIdVehiculoTipoEstado()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe un tipo de estado parametrizado con cierra novedad en Si.");
            return true;
        }
        return false;
    }

    public void guardar() {
        if (validar()) {
            return;
        }
        vehiculoTipoEstado.setUsername(user.getUsername());
        vehiculoTipoEstado.setRestriccionOperacion(this.restriccion_operacion ? 1 : 0);
        vehiculoTipoEstado.setCierraNovedad(this.b_cierra_novedad ? 1 : 0);
        vehiculoTipoEstado.setCreado(MovilidadUtil.fechaCompletaHoy());
        this.vehiculoTipoEstadoEjb.create(vehiculoTipoEstado);
        nuevo();
        consultar();
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
    }

    public void actualizar() {
        if (validar()) {
            return;
        }
        vehiculoTipoEstado.setUsername(user.getUsername());
        vehiculoTipoEstado.setRestriccionOperacion(this.restriccion_operacion ? 1 : 0);
        vehiculoTipoEstado.setCierraNovedad(this.b_cierra_novedad ? 1 : 0);
        vehiculoTipoEstado.setModificado(MovilidadUtil.fechaCompletaHoy());
        this.vehiculoTipoEstadoEjb.edit(vehiculoTipoEstado);
        consultar();
        MovilidadUtil.hideModal("mtipo");
        MovilidadUtil.addSuccessMessage(ConstantsUtil.UPDATE_DONDE);
    }

    public void editar() {
        this.vehiculoTipoEstado = this.selected;
        this.restriccion_operacion = this.vehiculoTipoEstado.getRestriccionOperacion() > 0;
        this.b_cierra_novedad = this.vehiculoTipoEstado.getCierraNovedad() > 0;
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoTipoEstadoEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado Tipo de estado cambiado éxitosamente."));
    }

    public void nuevo() {
        this.vehiculoTipoEstado = new VehiculoTipoEstado();
        this.selected = null;
        this.restriccion_operacion = false;
        this.b_cierra_novedad = false;
    }

    public VehiculoTipoEstadoFacadeLocal getVehiculoTipoEstadoEjb() {
        return vehiculoTipoEstadoEjb;
    }

    public void setVehiculoTipoEstadoEjb(VehiculoTipoEstadoFacadeLocal vehiculoTipoEstadoEjb) {
        this.vehiculoTipoEstadoEjb = vehiculoTipoEstadoEjb;
    }

    public VehiculoTipoEstado getVehiculoTipoEstado() {
        return vehiculoTipoEstado;
    }

    public void setVehiculoTipoEstado(VehiculoTipoEstado vehiculoTipoEstado) {
        this.vehiculoTipoEstado = vehiculoTipoEstado;
    }

    public VehiculoTipoEstado getSelected() {
        return selected;
    }

    public void setSelected(VehiculoTipoEstado selected) {
        this.selected = selected;
    }

    public List<VehiculoTipoEstado> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoTipoEstado> lista) {
        this.lista = lista;
    }

    public boolean isRestriccion_operacion() {
        return restriccion_operacion;
    }

    public void setRestriccion_operacion(boolean restriccion_operacion) {
        this.restriccion_operacion = restriccion_operacion;
    }

    public boolean isCierra_novedad() {
        return b_cierra_novedad;
    }

    public void setCierra_novedad(boolean b_cierra_novedad) {
        this.b_cierra_novedad = b_cierra_novedad;
    }

    public boolean isB_cierra_novedad() {
        return b_cierra_novedad;
    }

    public void setB_cierra_novedad(boolean b_cierra_novedad) {
        this.b_cierra_novedad = b_cierra_novedad;
    }

}
