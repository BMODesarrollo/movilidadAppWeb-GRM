/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaTroncalFacadeLocal;
import com.movilidad.model.AccViaTroncal;
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
@Named(value = "accViaTroncalJSF")
@ViewScoped
public class AccViaTroncalJSF implements Serializable {

    @EJB
    private AccViaTroncalFacadeLocal accViaTroncalFacadeLocal;

    private AccViaTroncal accViaTroncal;

    private List<AccViaTroncal> listAccViaTroncal;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaTroncalJSF() {
    }

    public void guardar() {
        try {
            if (accViaTroncal != null) {
                accViaTroncal.setViaTroncal(accViaTroncal.getViaTroncal().toUpperCase());
                accViaTroncal.setCreado(new Date());
                accViaTroncal.setModificado(new Date());
                accViaTroncal.setEstadoReg(0);
                accViaTroncal.setUsername(user.getUsername());
                accViaTroncalFacadeLocal.create(accViaTroncal);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Troncal correctamente");
                accViaTroncal = new AccViaTroncal();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Troncal");
        }
    }

    public void actualizar() {
        try {
            if (accViaTroncal != null) {
                accViaTroncal.setViaTroncal(accViaTroncal.getViaTroncal().toUpperCase());
                accViaTroncalFacadeLocal.edit(accViaTroncal);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Troncal correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-troncal-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Troncal");
        }
    }

    public void prepareGuardar() {
        accViaTroncal = new AccViaTroncal();
    }

    public void reset() {
        accViaTroncal = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaTroncal = (AccViaTroncal) event.getObject();
    }

    public AccViaTroncal getAccViaTroncal() {
        return accViaTroncal;
    }

    public void setAccViaTroncal(AccViaTroncal accViaTroncal) {
        this.accViaTroncal = accViaTroncal;
    }

    public List<AccViaTroncal> getListAccViaTroncal() {
        listAccViaTroncal = accViaTroncalFacadeLocal.estadoReg();
        return listAccViaTroncal;
    }

}
