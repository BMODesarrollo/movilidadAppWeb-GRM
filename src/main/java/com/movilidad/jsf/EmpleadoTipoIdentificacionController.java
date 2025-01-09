/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoTipoIdentificacionFacadeLocal;
import com.movilidad.model.EmpleadoTipoIdentificacion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "emplTipoIdenCtrl")
@ViewScoped
public class EmpleadoTipoIdentificacionController implements Serializable {
    
    @EJB
    private EmpleadoTipoIdentificacionFacadeLocal empleadoTipoIdenEJB;
    
    private EmpleadoTipoIdentificacion emplTipoIden;
    
    private List<EmpleadoTipoIdentificacion> listEmplTipoIden = new ArrayList();
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of TipoCargoController
     */
    public EmpleadoTipoIdentificacionController() {
        
    }
    
    @PostConstruct
    public void init() {
        this.emplTipoIden = new EmpleadoTipoIdentificacion();
        listEmplTipoIden = empleadoTipoIdenEJB.findAll();
    }
    
    public void guargar() {
        if (empleadoTipoIdenEJB.findByNombre(emplTipoIden.getNombre(), 0) != null) {
            MovilidadUtil.addErrorMessage("Ya existe Tipo de Identificación con este Nombre");
            return;
        }
        
        if (emplTipoIden.getNombre() != null) {
            emplTipoIden.setUsername(user.getUsername());
            emplTipoIden.setCreado(new Date());
            emplTipoIden.setEstadoReg(0);
            
            try {
                empleadoTipoIdenEJB.create(emplTipoIden);
                MovilidadUtil.addSuccessMessage("Exito. Registro exitoso Tipo de identificación");
                cerrarModal();
            } catch (EJBException e) {
                MovilidadUtil.addErrorMessage(e.getMessage());
            }
        }
    }
    
    public void listarEmpleadoTipoIdentificacions() {
        try {
            listEmplTipoIden = empleadoTipoIdenEJB.findAll();
        } catch (Exception e) {
            System.err.println("Mensaje error: " + e.getMessage());
        }
    }
    
    public void editar(EmpleadoTipoIdentificacion empTipIdn) {
        emplTipoIden = empTipIdn;
    }
    
    public void openDialog() {
        emplTipoIden = new EmpleadoTipoIdentificacion();
    }
    
    public void cerrarModal() {
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlg2').hide();");
        init();
    }
    
    public void guardarEdit() {
        if (empleadoTipoIdenEJB.findByNombre(emplTipoIden.getNombre(), emplTipoIden.getIdEmpleadoTipoIdentificacion()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe Tipo de Identificación con este Nombre");
            return;
        }
        emplTipoIden.setUsername(user.getUsername());
        try {
            empleadoTipoIdenEJB.edit(emplTipoIden);
            MovilidadUtil.addSuccessMessage("Exito. Se modificó el registro exitosamente");
            cerrarModal();
        } catch (Exception e) {
        }
    }
    
    public void eliminar(EmpleadoTipoIdentificacion empTiCar) {
        empTiCar.setEstadoReg(1);
        try {
            empleadoTipoIdenEJB.edit(empTiCar);
            MovilidadUtil.addSuccessMessage("Eliminado. Se eliminó el registro exitosamente");
            init();
            
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage(e.getMessage());
        }
        
    }
    
    public EmpleadoTipoIdentificacion getEmplTipoIden() {
        return emplTipoIden;
    }
    
    public void setEmplTipoIden(EmpleadoTipoIdentificacion emplTipoIden) {
        this.emplTipoIden = emplTipoIden;
    }
    
    public List<EmpleadoTipoIdentificacion> getListEmplTipoIden() {
        return listEmplTipoIden;
    }
    
    public void setListEmplTipoIden(List<EmpleadoTipoIdentificacion> listEmplTipoIden) {
        this.listEmplTipoIden = listEmplTipoIden;
    }
    
}
