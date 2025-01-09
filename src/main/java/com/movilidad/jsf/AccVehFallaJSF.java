/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccVehFallaFacadeLocal;
import com.movilidad.model.AccVehFalla;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accVehFallaJSF")
@ViewScoped
public class AccVehFallaJSF implements Serializable {

    @EJB
    private AccVehFallaFacadeLocal accVehFallaFacadeLocal;

    private AccVehFalla accVehFalla;

    private List<AccVehFalla> listAccVehFalla;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccVehFallaJSF() {
    }

    public void guardar() {
        try {
            if (accVehFalla != null) {
                accVehFalla.setFalla(accVehFalla.getFalla().toUpperCase());
                accVehFalla.setCreado(new Date());
                accVehFalla.setModificado(new Date());
                accVehFalla.setEstadoReg(0);
                accVehFalla.setUsername(user.getUsername());
                accVehFallaFacadeLocal.create(accVehFalla);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Fallas de Vehículo correctamente");
                accVehFalla = new AccVehFalla();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Fallas de Vehículo");
        }
    }

    public void actualizar() {
        try {
            if (accVehFalla != null) {
                accVehFalla.setFalla(accVehFalla.getFalla().toUpperCase());
                accVehFallaFacadeLocal.edit(accVehFalla);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Fallas de Vehículo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('veh-falla-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Fallas de Vehículo");
        }
    }

    public void prepareGuardar() {
        accVehFalla = new AccVehFalla();
    }

    public void reset() {
        accVehFalla = null;
    }

    public void onRowSelect(SelectEvent event) {
        accVehFalla = (AccVehFalla) event.getObject();
    }

    public AccVehFalla getAccVehFalla() {
        return accVehFalla;
    }

    public void setAccVehFalla(AccVehFalla accVehFalla) {
        this.accVehFalla = accVehFalla;
    }

    public List<AccVehFalla> getListAccVehFalla() {
        listAccVehFalla = accVehFallaFacadeLocal.estadoReg();
        return listAccVehFalla;
    }

}
