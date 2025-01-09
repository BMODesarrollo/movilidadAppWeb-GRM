package com.movilidad.jsf;

import com.movilidad.ejb.DispEstadoPendActualFacadeLocal;
import com.movilidad.ejb.VehiculoTipoEstadoFacadeLocal;
import com.movilidad.model.VehiculoTipoEstadoDet;
import com.movilidad.model.DispEstadoPendActual;
import com.movilidad.model.VehiculoTipoEstado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Jesús Jimenez
 */
@Named(value = "estadoPendActualBean")
@ViewScoped
public class DispEstadoPendActualBean implements Serializable {

    @EJB
    private DispEstadoPendActualFacadeLocal estadoPendActualEjb;
    @EJB
    private VehiculoTipoEstadoFacadeLocal vehiculoTipoEstadoEJB;

    private DispEstadoPendActual estadoPendActual;
    private DispEstadoPendActual selected;
    private VehiculoTipoEstadoDet vehiculoTipoEstadoDet;
    private String nombre;

    private boolean isEditing;
    private boolean b_primer_estado;
//    private boolean b_por_defecto_diferir;

    private List<DispEstadoPendActual> lstDispEstadoPendActuals;
    private List<VehiculoTipoEstadoDet> lstVehiculoTipoEstadoDet;
    private List<VehiculoTipoEstado> lstVehiculoTipoEstado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstDispEstadoPendActuals = estadoPendActualEjb.findAll();
    }

    /**
     * Prepara la lista de tipos de eventos antes de registrar/modificar un
     * registro.
     */
    public void prepareTipoEstados() {
        lstVehiculoTipoEstado = vehiculoTipoEstadoEJB.findByEstadoReg();
    }

    /**
     * Evento que se dispara al seleccionar el tipo de evento en el modal que
     * muestra listado de tipos
     *
     * @param event
     */
    public void onRowVehiculoTipoEstadoDetClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof VehiculoTipoEstadoDet) {
            vehiculoTipoEstadoDet = (VehiculoTipoEstadoDet) event.getObject();
        }
    }

    public void nuevo() {
        nombre = "";
        b_primer_estado = false;
//        b_por_defecto_diferir = false;
        estadoPendActual = new DispEstadoPendActual();
        prepareTipoEstados();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        nombre = selected.getNombre();
        estadoPendActual = selected;
        prepareTipoEstados();
        b_primer_estado = estadoPendActual.getPrimerEstado() == 1;
//        b_por_defecto_diferir = estadoPendActual.getPorDefectoDiferir() == 1;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        if (validarDatos()) {
            return;
        }

        estadoPendActual.setNombre(nombre);
        estadoPendActual.setPrimerEstado(b_primer_estado ? 1 : 0);
//        estadoPendActual.setPorDefectoDiferir(b_por_defecto_diferir ? 1 : 0);

        if (isEditing) {

            estadoPendActual.setModificado(new Date());
            estadoPendActual.setUsername(user.getUsername());
            estadoPendActualEjb.edit(estadoPendActual);

            MovilidadUtil.hideModal("wv_disp_estado_pend");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {

            estadoPendActual.setEstadoReg(0);
            estadoPendActual.setCreado(new Date());
            estadoPendActual.setUsername(user.getUsername());
            estadoPendActualEjb.create(estadoPendActual);
            lstDispEstadoPendActuals.add(estadoPendActual);

            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
    }

    private boolean validarDatos() {

        int idDispEstadoPendActual = estadoPendActual.getIdDispEstadoPendActual() == null ? 0 : estadoPendActual.getIdDispEstadoPendActual();
        if (estadoPendActualEjb.findByNombreByIdVehiculoTipoEstadoDet(nombre.trim(), idDispEstadoPendActual) != null) {
            MovilidadUtil.addErrorMessage("YA existe un registro con el nombre a ingresar.");
            return true;
        }
//        if (b_por_defecto_diferir && estadoPendActualEjb.findEstadoDiferir(idDispEstadoPendActual) != null) {
//            MovilidadUtil.addErrorMessage("Ya existe un tipo de estado parametrizado con por defecto al diferir en Si.");
//            return true;
//        }

        return false;
    }

    public DispEstadoPendActual getDispEstadoPendActual() {
        return estadoPendActual;
    }

    public void setDispEstadoPendActual(DispEstadoPendActual cableEventoTipoDet) {
        this.estadoPendActual = cableEventoTipoDet;
    }

    public DispEstadoPendActual getSelected() {
        return selected;
    }

    public void setSelected(DispEstadoPendActual selected) {
        this.selected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public VehiculoTipoEstadoDet getVehiculoTipoEstadoDet() {
        return vehiculoTipoEstadoDet;
    }

    public void setVehiculoTipoEstadoDet(VehiculoTipoEstadoDet cableEventoTipo) {
        this.vehiculoTipoEstadoDet = cableEventoTipo;
    }

    public List<VehiculoTipoEstadoDet> getLstVehiculoTipoEstadoDet() {
        return lstVehiculoTipoEstadoDet;
    }

    public void setLstVehiculoTipoEstadoDet(List<VehiculoTipoEstadoDet> lstVehiculoTipoEstadoDet) {
        this.lstVehiculoTipoEstadoDet = lstVehiculoTipoEstadoDet;
    }

    public List<DispEstadoPendActual> getLstDispEstadoPendActuals() {
        return lstDispEstadoPendActuals;
    }

    public void setLstDispEstadoPendActuals(List<DispEstadoPendActual> lstDispEstadoPendActuals) {
        this.lstDispEstadoPendActuals = lstDispEstadoPendActuals;
    }

    public List<VehiculoTipoEstado> getLstVehiculoTipoEstado() {
        return lstVehiculoTipoEstado;
    }

    public void setLstVehiculoTipoEstado(List<VehiculoTipoEstado> lstVehiculoTipoEstado) {
        this.lstVehiculoTipoEstado = lstVehiculoTipoEstado;
    }

    public boolean isB_primer_estado() {
        return b_primer_estado;
    }

    public void setB_primer_estado(boolean b_primer_estado) {
        this.b_primer_estado = b_primer_estado;
    }

//    public boolean isB_por_defecto_diferir() {
//        return b_por_defecto_diferir;
//    }
//
//    public void setB_por_defecto_diferir(boolean b_por_defecto_diferir) {
//        this.b_por_defecto_diferir = b_por_defecto_diferir;
//    }
}
