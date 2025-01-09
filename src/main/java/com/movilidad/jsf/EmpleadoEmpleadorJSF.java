/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoEmpleadorFacadeLocal;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.model.EmpleadoEmpleador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "empleadoEmpleadorJSF")
@ViewScoped
public class EmpleadoEmpleadorJSF implements Serializable {

    @EJB
    private EmpleadoEmpleadorFacadeLocal empleadoEmpleadorFacadeLocal;

    private EmpleadoEmpleador empleadoEmpleador;

    private List<EmpleadoEmpleador> listEmpleadoEmpleador;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public EmpleadoEmpleadorJSF() {
    }

    @PostConstruct
    public void init() {
        empleadoEmpleador = null;
        listEmpleadoEmpleador = new ArrayList<>();
    }

    public void prepareGuardar() {
        empleadoEmpleador = new EmpleadoEmpleador();
    }

    public void guardar() {
        try {
            if (empleadoEmpleador != null) {
                empleadoEmpleador.setDescripcion(empleadoEmpleador.getDescripcion().toUpperCase());
                empleadoEmpleador.setCreado(new Date());
                empleadoEmpleador.setModificado(new Date());
                empleadoEmpleador.setEstadoReg(0);
                empleadoEmpleador.setUsername(user.getUsername());
                empleadoEmpleadorFacadeLocal.create(empleadoEmpleador);
                MovilidadUtil.addSuccessMessage("Empleador registrado correctamente");
                empleadoEmpleador = new EmpleadoEmpleador();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void actualizar() {
        try {
            if (empleadoEmpleador != null) {
                empleadoEmpleador.setDescripcion(empleadoEmpleador.getDescripcion().toUpperCase());
                empleadoEmpleador.setModificado(new Date());
                empleadoEmpleadorFacadeLocal.edit(empleadoEmpleador);
                MovilidadUtil.addSuccessMessage("Empleador actualizado correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('empleadorDlg').hide();");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void onPreprareActualizar(EmpleadoEmpleador event) {
        empleadoEmpleador = event;
    }

    public void reset() {
        empleadoEmpleador = null;
    }

    public EmpleadoEmpleador getEmpleadoEmpleador() {
        return empleadoEmpleador;
    }

    public void setEmpleadoEmpleador(EmpleadoEmpleador empleadoEmpleador) {
        this.empleadoEmpleador = empleadoEmpleador;
    }

    public List<EmpleadoEmpleador> getListEmpleadoEmpleador() {
        listEmpleadoEmpleador = empleadoEmpleadorFacadeLocal.findAllEstadoReg();
        return listEmpleadoEmpleador;
    }

    public void setListEmpleadoEmpleador(List<EmpleadoEmpleador> listEmpleadoEmpleador) {
        this.listEmpleadoEmpleador = listEmpleadoEmpleador;
    }

}
