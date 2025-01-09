/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoTipoCargoFacadeLocal;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "emplTipoCargoCtrl")
@ViewScoped
public class EmpleadoTipoCargoController implements Serializable {

    @EJB
    private EmpleadoTipoCargoFacadeLocal empleadoTipoCargoEJB;

    private EmpleadoTipoCargo emplTipoCargo;

    private List<EmpleadoTipoCargo> listEmpleadoTipoCargos;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of TipoCargoController
     */
    public EmpleadoTipoCargoController() {
    }

    @PostConstruct
    public void init() {
        emplTipoCargo = new EmpleadoTipoCargo();
        listEmpleadoTipoCargos = new ArrayList();
        listarEmpleadoTipoCargos();
    }

    public void guargar() {
        
        if (empleadoTipoCargoEJB.findByNombre(emplTipoCargo.getNombreCargo(),0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe Tipo de Cargo con este Nombre");
                return;
            }
        if (emplTipoCargo.getNombreCargo() != null) {
            emplTipoCargo.setUsername(user.getUsername());
            emplTipoCargo.setCreado(new Date());
            emplTipoCargo.setEstadoReg(0);

            try {
                empleadoTipoCargoEJB.create(emplTipoCargo);
               MovilidadUtil.addSuccessMessage("Exito. Registro exitoso Tipo de Cargo");
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('dlg2').hide();");
                init();
            } catch (EJBException e) {
               MovilidadUtil.addErrorMessage(e.getMessage());
            }
        }
    }

    public void listarEmpleadoTipoCargos() {
        try {
            listEmpleadoTipoCargos = empleadoTipoCargoEJB.findAll();
        } catch (Exception e) {
            System.err.println("Mensaje error: " + e.getMessage());
        }
    }

    public void editar(EmpleadoTipoCargo empTiCar) {
        emplTipoCargo = empTiCar;
    }

    public void openDialog() {
        emplTipoCargo=new EmpleadoTipoCargo();
    }

    public void guardarEdit() {
         if (empleadoTipoCargoEJB.findByNombre(emplTipoCargo.getNombreCargo()
                , emplTipoCargo.getIdEmpleadoTipoCargo()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe Tipo de Cargo con este Nombre");
                return;
            }
        
        emplTipoCargo.setUsername(user.getUsername());
        emplTipoCargo.setModificado(new Date());
        try {
            empleadoTipoCargoEJB.edit(emplTipoCargo);
            MovilidadUtil.addSuccessMessage("Exito. Se modificó el registro exitosamente");
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dlg2').hide();");
            init();
        } catch (Exception e) {
        }
    }

    public void eliminar(EmpleadoTipoCargo empTiCar) {
        empTiCar.setEstadoReg(1);
        try {
            empleadoTipoCargoEJB.edit(empTiCar);
            MovilidadUtil.addSuccessMessage("Eliminado. Se eliminó el registro exitosamente");
            init();

        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }
    
    public EmpleadoTipoCargo getEmplTipoCargo() {
        return emplTipoCargo;
    }

    public void setEmplTipoCargo(EmpleadoTipoCargo emplTipoCargo) {
        this.emplTipoCargo = emplTipoCargo;
    }

    public List<EmpleadoTipoCargo> getListEmpleadoTipoCargos() {
        return listEmpleadoTipoCargos;
    }

    public void setListEmpleadoTipoCargos(List<EmpleadoTipoCargo> listEmpleadoTipoCargos) {
        this.listEmpleadoTipoCargos = listEmpleadoTipoCargos;
    }

}
