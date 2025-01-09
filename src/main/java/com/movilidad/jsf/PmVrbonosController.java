package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoTipoCargoFacadeLocal;
import com.movilidad.ejb.PmVrbonosFacadeLocal;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.PmVrbonos;
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
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("pmVrbonosController")
@ViewScoped
public class PmVrbonosController implements Serializable {
    
    @EJB
    private PmVrbonosFacadeLocal pVBonoEJB;
    @EJB
    private EmpleadoTipoCargoFacadeLocal empleadoTipoCargoFacadeLocal;
    private List<PmVrbonos> items = null;
    private PmVrbonos selected;
    private int i_idEmpelado;
    private EmpleadoTipoCargo empleadoTipoC;
    private List<EmpleadoTipoCargo> listEmpleadoTC;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    public PmVrbonosController() {
    }
    
    public PmVrbonos prepareCreate() {
        listEmpleadoTC = empleadoTipoCargoFacadeLocal.findAllActivos();
        selected = new PmVrbonos();
        return selected;
    }
    
    public void create() {
        persist(PersistAction.CREATE, "Programa master Valor Bonos se creó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Programa master Valor Bonos no se creó correctamente");
        }
    }
    
    public void update() {
        persist(PersistAction.UPDATE, "Programa master Valor Bonos se actualizó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Programa master Valor Bonos no se actualizó correctamente");
        }
    }
    
    public void destroy() {
        persist(PersistAction.DELETE, "Programa master Valor Bonos no se eliminó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Programa master Valor Bonos no se eliminó correctamente");
        }
    }
    
    public List<PmVrbonos> getItems() {
        items = pVBonoEJB.cargarEstadoRegistro();
        return items;
    }
    
    public void reset() {
        selected = new PmVrbonos();
        i_idEmpelado = 0;
        items = pVBonoEJB.cargarEstadoRegistro();
        listEmpleadoTC = empleadoTipoCargoFacadeLocal.findAllActivos();
    }
    
    public void cargarEdit() {
        i_idEmpelado = selected.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo();
        listEmpleadoTC = empleadoTipoCargoFacadeLocal.findAllActivos();
    }
    
    public void cargarEmpleadoTC() {
        EmpleadoTipoCargo empleadoTipoCargo = new EmpleadoTipoCargo();
        empleadoTipoCargo.setIdEmpleadoTipoCargo(i_idEmpelado);
        getSelected().setIdEmpleadoTipoCargo(empleadoTipoCargo);
    }
    
    public int validarFecha() throws ParseException {
        SimpleDateFormat prueba = new SimpleDateFormat("yyyy/MM/dd");
        Date d = prueba.parse(prueba.format(new Date()));
        return selected.getDesde().compareTo(d);
    }
    
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        getSelected().setUsername(user.getUsername());
                        getSelected().setEstadoReg(0);
                        getSelected().setCreado(new Date());
                        cargarEmpleadoTC();
//                        if (validarFecha() < 0) {
//                            MovilidadUtil.addErrorMessage("Fecha Desde debe ser mayor a fecha de hoy");
//                            return;
//                        }
                        if (getSelected().getVrBonoAlimentos() < 0 || getSelected().getVrBonoSalarial() < 0) {
                            MovilidadUtil.addErrorMessage("Valor del Bono debe ser mayor o igual a 0");
                            return;
                        }
                        pVBonoEJB.create(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        reset();
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        getSelected().setModificado(new Date());
                        pVBonoEJB.edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        reset();
                        break;
                    case UPDATE:
                        getSelected().setIdEmpleadoTipoCargo(empleadoTipoC);
                        getSelected().setUsername(user.getUsername());
                        getSelected().setModificado(new Date());
                        cargarEmpleadoTC();
                        if (getSelected().getVrBonoAlimentos() < 0 || getSelected().getVrBonoSalarial() < 0) {
                            MovilidadUtil.addErrorMessage("Valor del Bono debe ser mayor o igual a 0");
                            return;
                        }
                        pVBonoEJB.edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        MovilidadUtil.hideModal("PmVrbonosCreateDialog");
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
    
    public PmVrbonos getSelected() {
        return selected;
    }
    
    public void setSelected(PmVrbonos selected) {
        this.selected = selected;
    }
    
    public int getI_idEmpelado() {
        return i_idEmpelado;
    }
    
    public void setI_idEmpelado(int i_idEmpelado) {
        this.i_idEmpelado = i_idEmpelado;
    }
    
    public List<EmpleadoTipoCargo> getListEmpleadoTC() {
        return listEmpleadoTC;
    }
    
    public void setListEmpleadoTC(List<EmpleadoTipoCargo> listEmpleadoTC) {
        this.listEmpleadoTC = listEmpleadoTC;
    }
    
    public EmpleadoTipoCargo getEmpleadoTipoC() {
        return empleadoTipoC;
    }
    
    public void setEmpleadoTipoC(EmpleadoTipoCargo empleadoTipoC) {
        this.empleadoTipoC = empleadoTipoC;
    }
}
