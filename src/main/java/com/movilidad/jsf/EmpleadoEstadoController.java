/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoEstadoFacadeLocal;
import com.movilidad.model.EmpleadoEstado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
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
 * @author Soluciones IT
 */
@Named(value = "emplEstCtrl")
@ViewScoped
public class EmpleadoEstadoController implements Serializable {

    private EmpleadoEstado emplEstd;

    private List<EmpleadoEstado> listEmplEstd;

    @EJB
    private EmpleadoEstadoFacadeLocal empleadoEstadoEJB;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean b_alerta = false;

    /**
     * Creates a new instance of EmpleadoEstadoController
     */
    public EmpleadoEstadoController() {
        listEmplEstd = new ArrayList();
    }

    @PostConstruct
    public void init() {
        b_alerta = false;
        emplEstd = new EmpleadoEstado();
        listEmpleadosEstado();
    }

    public void listEmpleadosEstado() {
        try {
            listEmplEstd = empleadoEstadoEJB.findAll();
        } catch (Exception e) {
        }
    }

    public void guargar() {
        if (emplEstd != null) {
            emplEstd.setUsername(user.getUsername());
            emplEstd.setCreado(new Date());
            emplEstd.setEstadoReg(0);

            setCamposInt();

            try {
                empleadoEstadoEJB.create(emplEstd);
                MovilidadUtil.addSuccessMessage("Exito. Registro exitoso Estado Empleado");
                closeDialog();
            } catch (Exception e) {
                MovilidadUtil.addErrorMessage(e.getMessage());
            }
        }

    }

    public void guardarEdit() {
        emplEstd.setUsername(user.getUsername());
        emplEstd.setModificado(new Date());
        setCamposInt();

        try {
            empleadoEstadoEJB.edit(emplEstd);
            MovilidadUtil.addSuccessMessage("Exito. Se modificó el registro exitosamente");

            closeDialog();
        } catch (Exception e) {
        }
    }

    public void editar(EmpleadoEstado ee) {
        this.emplEstd = ee;
        this.b_alerta = ee.getAlertar() > 0;
    }

    public void eliminar(EmpleadoEstado ee) {
        ee.setEstadoReg(1);
        try {
            empleadoEstadoEJB.edit(ee);
            MovilidadUtil.addSuccessMessage("Eliminado. Se eliminó el registro exitosamente");
            init();
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage(e.getMessage());
        }

    }

    public void openDialog() {
        emplEstd = new EmpleadoEstado();
    }

    public void closeDialog() {
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlg2').hide();");
        init();
    }

    public void setCamposInt() {
        emplEstd.setAlertar((b_alerta) ? 1 : 0);
    }

    public EmpleadoEstado getEmplEstd() {
        return emplEstd;
    }

    public void setEmplEstd(EmpleadoEstado emplEstd) {
        this.emplEstd = emplEstd;
    }

    public boolean isB_alerta() {
        return b_alerta;
    }

    public void setB_alerta(boolean b_alerta) {
        this.b_alerta = b_alerta;
    }

    public List<EmpleadoEstado> getListEmplEstd() {
        return listEmplEstd;
    }

    public void setListEmplEstd(List<EmpleadoEstado> listEmplEstd) {
        this.listEmplEstd = listEmplEstd;
    }

}
