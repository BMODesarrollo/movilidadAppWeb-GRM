package com.movilidad.jsf;

import com.movilidad.ejb.PmVrbonoTipovehiFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.PmVrbonoTipovehi;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.JsfUtil.PersistAction;
import com.movilidad.utils.MovilidadUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("pmVrbonoTipovehiController")
@ViewScoped
public class PmVrbonoTipovehiController implements Serializable {

    @EJB
    private PmVrbonoTipovehiFacadeLocal pmVBonoTVehiculoEJB;
    @EJB
    private VehiculoTipoFacadeLocal vehiculoTipoFacadeLocal;
    private List<PmVrbonoTipovehi> items = null;
    private List<VehiculoTipo> listVehiculoTipo;
    private PmVrbonoTipovehi selected;
    private int i_idVehiculoTipo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public PmVrbonoTipovehiController() {
    }

    public PmVrbonoTipovehi prepareCreate() {
        selected = new PmVrbonoTipovehi();
        listVehiculoTipo = vehiculoTipoFacadeLocal.findAllEstadoR();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Programa master Valor Bonos Tipo de Vehículo se creó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Programa master Valor Bonos Tipo de Vehículo no se creó correctamente");
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Programa master Valor Bonos Tipo de Vehículo se actualizó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Programa master Valor Bonos Tipo de Vehículo no se actualizó correctamente");
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Programa master Valor Bonos Tipo de Vehículo se eliminó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Programa master Valor Bonos Tipo de Vehículo no se eliminó correctamente");
        }
    }

    public List<PmVrbonoTipovehi> getItems() {
        if (items == null) {
            items = pmVBonoTVehiculoEJB.cargarEstadoRegistro();
        }
        return items;
    }

    public void reset() {
        selected = new PmVrbonoTipovehi();
        i_idVehiculoTipo = 0;
        items = pmVBonoTVehiculoEJB.cargarEstadoRegistro();
        listVehiculoTipo = vehiculoTipoFacadeLocal.findAllEstadoR();
    }

    public void cargarEdit() {
        listVehiculoTipo = vehiculoTipoFacadeLocal.findAllEstadoR();
        i_idVehiculoTipo = selected.getIdVehiculoTipo() != null ? selected.getIdVehiculoTipo().getIdVehiculoTipo() : 0;
    }

    public int validarFecha() throws ParseException {
        SimpleDateFormat prueba = new SimpleDateFormat("yyyy/MM/dd");
        Date d = prueba.parse(prueba.format(new Date()));
        return selected.getDesde().compareTo(d);
    }

    void cargarVehiculotipo() {
        VehiculoTipo v = new VehiculoTipo();
        v.setIdVehiculoTipo((Integer) i_idVehiculoTipo);
        getSelected().setIdVehiculoTipo(v);
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        getSelected().setUsername(user.getUsername());
                        getSelected().setEstadoReg(0);
                        getSelected().setCreado(new Date());
                        cargarVehiculotipo();
                        if (validarFecha() < 0) {
                            MovilidadUtil.addErrorMessage("Fecha Desde debe ser mayor a fecha de hoy");
                            return;
                        }

                        if (getSelected().getVrBono() < 0) {
                            MovilidadUtil.addErrorMessage("Valor Bono debe ser mayor a 0");
                            return;
                        }
                        pmVBonoTVehiculoEJB.create(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        reset();
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        getSelected().setModificado(new Date());
                        pmVBonoTVehiculoEJB.edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        reset();
                        break;
                    case UPDATE:
                        cargarVehiculotipo();
                        getSelected().setUsername(user.getUsername());
                        getSelected().setModificado(new Date());
                        if (getSelected().getVrBono() < 0) {
                            MovilidadUtil.addErrorMessage("Valor Bono debe ser mayor a 0");
                            return;
                        }
                        pmVBonoTVehiculoEJB.edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('PmVrbonoTipovehiEditDialog').hide();");
                        reset();
                        break;
                    default:
                        break;
                }
            } catch (EJBException ex) {
                JsfUtil.addErrorMessage("Error del sistema");
            } catch (Exception ex) {
                JsfUtil.addErrorMessage("Error del sistema");
            }
        }
    }

    public PmVrbonoTipovehi getSelected() {
        return selected;
    }

    public List<VehiculoTipo> getListVehiculoTipo() {
        return listVehiculoTipo;
    }

    public void setListVehiculo(List<VehiculoTipo> listVehiculoTipo) {
        this.listVehiculoTipo = listVehiculoTipo;
    }

    public void setSelected(PmVrbonoTipovehi selected) {
        this.selected = selected;
    }

    public int getI_idVehiculoTipo() {
        return i_idVehiculoTipo;
    }

    public void setI_idVehiculoTipo(int i_idVehiculoTipo) {
        this.i_idVehiculoTipo = i_idVehiculoTipo;
    }
}
