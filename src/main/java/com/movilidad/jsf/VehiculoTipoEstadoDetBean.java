package com.movilidad.jsf;

import com.movilidad.ejb.DispEstadoPendActualFacadeLocal;
import com.movilidad.ejb.VehiculoTipoEstadoDetFacadeLocal;
import com.movilidad.ejb.VehiculoTipoEstadoFacadeLocal;
import com.movilidad.model.DispEpaVted;
import com.movilidad.model.DispEstadoPendActual;
import com.movilidad.model.VehiculoTipoEstado;
import com.movilidad.model.VehiculoTipoEstadoDet;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "vehiculoTipoEstadoDetBean")
@ViewScoped
public class VehiculoTipoEstadoDetBean implements Serializable {

    @EJB
    private VehiculoTipoEstadoDetFacadeLocal vehiculoTipoEstadoDetEjb;
    @EJB
    private VehiculoTipoEstadoFacadeLocal vehiculoTipoEstadoEjb;
    @EJB
    private DispEstadoPendActualFacadeLocal dispEstadoPendActualEJB;

    private VehiculoTipoEstadoDet vehiculoTipoEstadoDet;
    private VehiculoTipoEstadoDet selected;
    private VehiculoTipoEstado vehiculoTipoEstado;
    private String nombre;

    private boolean isEditing;
//    private boolean b_por_defecto_diferir;

    private List<VehiculoTipoEstadoDet> lstVehiculoTipoEstadoDets;
    private List<VehiculoTipoEstado> lstVehiculoTipoEstado;
    private List<DispEstadoPendActual> listaEstadoPendActual;
    private List<DispEstadoPendActual> selectListaEstadoPendActual;
    private List<DispEpaVted> listDispEpaVted;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    void consultar() {
        lstVehiculoTipoEstadoDets = vehiculoTipoEstadoDetEjb.findAllByEstadoReg();
    }

    /**
     * Prepara la lista de tipos de eventos antes de registrar/modificar un
     * registro.
     */
    public void prepareListTipoEstado() {
        lstVehiculoTipoEstado = null;

        if (lstVehiculoTipoEstado == null) {
            lstVehiculoTipoEstado = vehiculoTipoEstadoEjb.findAll();
            PrimeFaces.current().ajax().update(":frmVehiculoTipoEstado:dtVehiculoTipoEstado");
            PrimeFaces.current().executeScript("PF('wlVdtVehiculoTipoEstado').clearFilters();");
        }
    }

    void cargarEstadoPendActual() {
        listaEstadoPendActual = dispEstadoPendActualEJB.findAllByEstadoReg();
        selectListaEstadoPendActual = new ArrayList<>();
    }

    /**
     * Evento que se dispara al seleccionar el tipo de evento en el modal que
     * muestra listado de tipos
     *
     * @param event
     */
    public void onRowVehiculoTipoEstadoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof VehiculoTipoEstado) {
            setVehiculoTipoEstado((VehiculoTipoEstado) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVdtVehiculoTipoEstado').clearFilters();");
    }

    public List<DispEpaVted> getListEstadosPend(VehiculoTipoEstadoDet param) {
        return param.getDispEpaVtedList();
    }

    public void nuevo() {
        nombre = "";
        vehiculoTipoEstadoDet = new VehiculoTipoEstadoDet();
        vehiculoTipoEstado = new VehiculoTipoEstado();
        selected = null;
        isEditing = false;
//        b_por_defecto_diferir = false;
        cargarEstadoPendActual();
    }

    public void prepareaToEditar() {
        isEditing = true;
        vehiculoTipoEstado = selected.getIdVehiculoTipoEstado();
        nombre = selected.getNombre();
        vehiculoTipoEstadoDet = selected;
//        b_por_defecto_diferir = selected.getPorDefectoDiferir() > 0;
        listDispEpaVted = selected.getDispEpaVtedList();
        selectListaEstadoPendActual = new ArrayList<>();
        cargarEstadoPendActual();
        if (listDispEpaVted != null) {
            for (DispEpaVted obj : listDispEpaVted) {
                selectListaEstadoPendActual.add(obj.getIdDispEstadoPendActual());
            }
        } else {
            selected.setDispEpaVtedList(new ArrayList<DispEpaVted>());
        }
    }

    public void editar() {
        editTransaction();
        consultar();
        MovilidadUtil.hideModal("wlvVehiculoTipoEstadoDet");
    }

    public void guardar() {
        guardarTransactional();
        consultar();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {

            vehiculoTipoEstadoDet.setNombre(nombre);
            vehiculoTipoEstadoDet.setIdVehiculoTipoEstado(vehiculoTipoEstado);
//            vehiculoTipoEstadoDet.setPorDefectoDiferir(b_por_defecto_diferir ? 1 : 0);

            if (isEditing) {
                vehiculoTipoEstadoDet.setModificado(new Date());
                vehiculoTipoEstadoDet.setUsername(user.getUsername());
                vehiculoTipoEstadoDetEjb.edit(vehiculoTipoEstadoDet);

                MovilidadUtil.hideModal("wlvVehiculoTipoEstadoDet");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                prepareListEpaVted();
                vehiculoTipoEstadoDet.setEstadoReg(0);
                vehiculoTipoEstadoDet.setCreado(new Date());
                vehiculoTipoEstadoDet.setUsername(user.getUsername());
                vehiculoTipoEstadoDetEjb.create(vehiculoTipoEstadoDet);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    void prepareListEpaVted() {
        listDispEpaVted = new ArrayList<>();
        for (DispEstadoPendActual obj : selectListaEstadoPendActual) {
            DispEpaVted dispEpaVted = new DispEpaVted();
            dispEpaVted.setUsername(user.getUsername());
            dispEpaVted.setEstadoReg(0);
            dispEpaVted.setCreado(MovilidadUtil.fechaCompletaHoy());
            dispEpaVted.setIdVehiculoTipoEstadoDet(vehiculoTipoEstadoDet);
            dispEpaVted.setIdDispEstadoPendActual(obj);
            listDispEpaVted.add(dispEpaVted);
        }
        vehiculoTipoEstadoDet.setDispEpaVtedList(listDispEpaVted);
    }

    @Transactional
    public void editTransaction() {
        vehiculoTipoEstadoDet.setNombre(nombre);
        vehiculoTipoEstadoDet.setIdVehiculoTipoEstado(vehiculoTipoEstado);
//        vehiculoTipoEstadoDet.setPorDefectoDiferir(b_por_defecto_diferir ? 1 : 0);
        if (!selectListaEstadoPendActual.isEmpty()) {
            for (DispEstadoPendActual depa : selectListaEstadoPendActual) {
                boolean ok = false;
                for (DispEpaVted epaVted : listDispEpaVted) {
                    if (depa.getIdDispEstadoPendActual().equals(epaVted.getIdDispEstadoPendActual().getIdDispEstadoPendActual())) {
                        ok = true;
                    }
                }
                if (!ok) {
                    DispEpaVted dispEpaVted = new DispEpaVted();
                    dispEpaVted.setUsername(user.getUsername());
                    dispEpaVted.setEstadoReg(0);
                    dispEpaVted.setCreado(MovilidadUtil.fechaCompletaHoy());
                    dispEpaVted.setIdVehiculoTipoEstadoDet(vehiculoTipoEstadoDet);
                    dispEpaVted.setIdDispEstadoPendActual(depa);
                    listDispEpaVted.add(dispEpaVted);
                }
            }
            List<DispEpaVted> listDispEpaVtedAux = new ArrayList<>();
            listDispEpaVtedAux.addAll(listDispEpaVted);
            for (DispEpaVted aux_epaVted : listDispEpaVtedAux) {
                boolean aux_ok = true;
                for (DispEstadoPendActual actual : selectListaEstadoPendActual) {
                    if (aux_epaVted.getIdDispEstadoPendActual().getIdDispEstadoPendActual().equals(actual.getIdDispEstadoPendActual())) {
                        aux_ok = false;
                    }
                }
                if (aux_ok) {
                    listDispEpaVted.remove(aux_epaVted);
                }
            }
        } else {
            vehiculoTipoEstadoDet.setDispEpaVtedList(null);
        }
        vehiculoTipoEstadoDet.setModificado(MovilidadUtil.fechaCompletaHoy());
        vehiculoTipoEstadoDet.setUsername(user.getUsername());
        vehiculoTipoEstadoDetEjb.edit(vehiculoTipoEstadoDet);
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
    }

    private String validarDatos() {

        if (vehiculoTipoEstado.getIdVehiculoTipoEstado() == null) {
            return "Debe seleccionar un tipo de estado";
        }

        if (isEditing) {
            if (vehiculoTipoEstadoDetEjb.findByNombre(nombre.trim(), selected.getIdVehiculoTipoEstadoDet(), vehiculoTipoEstado.getIdVehiculoTipoEstado()) != null) {
                return "YA existe un registro con el nombre a ingresar para el tipo seleccionado";
            }
        } else {
            if (!lstVehiculoTipoEstadoDets.isEmpty()) {
                if (vehiculoTipoEstadoDetEjb.findByNombre(nombre.trim(), 0, vehiculoTipoEstado.getIdVehiculoTipoEstado()) != null) {
                    return "YA existe un registro con el nombre a ingresar para el tipo seleccionado";
                }
            }
        }
//        if (b_por_defecto_diferir && vehiculoTipoEstadoDetEjb.findEstadoDiferir(
//                isEditing ? selected.getIdVehiculoTipoEstadoDet() : 0) != null) {
//            return "Ya existe un tipo de estado parametrizado con por defecto al diferir en Si.";
//
//        }

        return null;
    }

    public VehiculoTipoEstadoDet getVehiculoTipoEstadoDet() {
        return vehiculoTipoEstadoDet;
    }

    public void setVehiculoTipoEstadoDet(VehiculoTipoEstadoDet cableEventoTipoDet) {
        this.vehiculoTipoEstadoDet = cableEventoTipoDet;
    }

    public VehiculoTipoEstadoDet getSelected() {
        return selected;
    }

    public void setSelected(VehiculoTipoEstadoDet selected) {
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

    public VehiculoTipoEstado getVehiculoTipoEstado() {
        return vehiculoTipoEstado;
    }

    public void setVehiculoTipoEstado(VehiculoTipoEstado cableEventoTipo) {
        this.vehiculoTipoEstado = cableEventoTipo;
    }

    public List<VehiculoTipoEstado> getLstVehiculoTipoEstado() {
        return lstVehiculoTipoEstado;
    }

    public void setLstVehiculoTipoEstado(List<VehiculoTipoEstado> lstVehiculoTipoEstado) {
        this.lstVehiculoTipoEstado = lstVehiculoTipoEstado;
    }

    public List<VehiculoTipoEstadoDet> getLstVehiculoTipoEstadoDets() {
        return lstVehiculoTipoEstadoDets;
    }

    public void setLstVehiculoTipoEstadoDets(List<VehiculoTipoEstadoDet> lstVehiculoTipoEstadoDets) {
        this.lstVehiculoTipoEstadoDets = lstVehiculoTipoEstadoDets;
    }

//    public boolean isB_por_defecto_diferir() {
//        return b_por_defecto_diferir;
//    }
//
//    public void setB_por_defecto_diferir(boolean b_por_defecto_diferir) {
//        this.b_por_defecto_diferir = b_por_defecto_diferir;
//    }

    public List<DispEstadoPendActual> getListaEstadoPendActual() {
        return listaEstadoPendActual;
    }

    public void setListaEstadoPendActual(List<DispEstadoPendActual> listaEstadoPendActual) {
        this.listaEstadoPendActual = listaEstadoPendActual;
    }

    public List<DispEstadoPendActual> getSelectListaEstadoPendActual() {
        return selectListaEstadoPendActual;
    }

    public void setSelectListaEstadoPendActual(List<DispEstadoPendActual> selectListaEstadoPendActual) {
        this.selectListaEstadoPendActual = selectListaEstadoPendActual;
    }

}
