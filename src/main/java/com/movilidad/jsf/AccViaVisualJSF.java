/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaVisualFacadeLocal;
import com.movilidad.model.AccViaVisual;
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
@Named(value = "accViaVisualJSF")
@ViewScoped
public class AccViaVisualJSF implements Serializable {

    @EJB
    private AccViaVisualFacadeLocal accViaVisualFacadeLocal;

    private AccViaVisual accViaVisual;

    private List<AccViaVisual> listAccViaVisual;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaVisualJSF() {
    }

    public void guardar() {
        try {
            if (accViaVisual != null) {
                accViaVisual.setViaVisual(accViaVisual.getViaVisual().toUpperCase());
                accViaVisual.setCreado(new Date());
                accViaVisual.setModificado(new Date());
                accViaVisual.setEstadoReg(0);
                accViaVisual.setUsername(user.getUsername());
                accViaVisualFacadeLocal.create(accViaVisual);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Visual correctamente");
                accViaVisual = new AccViaVisual();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Visual");
        }
    }

    public void actualizar() {
        try {
            if (accViaVisual != null) {
                accViaVisual.setViaVisual(accViaVisual.getViaVisual().toUpperCase());
                accViaVisualFacadeLocal.edit(accViaVisual);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Visual correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-visual-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Visual");
        }
    }

    public void prepareGuardar() {
        accViaVisual = new AccViaVisual();
    }

    public void reset() {
        accViaVisual = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaVisual = (AccViaVisual) event.getObject();
    }

    public AccViaVisual getAccViaVisual() {
        return accViaVisual;
    }

    public void setAccViaVisual(AccViaVisual accViaVisual) {
        this.accViaVisual = accViaVisual;
    }

    public List<AccViaVisual> getListAccViaVisual() {
        listAccViaVisual = accViaVisualFacadeLocal.estadoReg();
        return listAccViaVisual;
    }

}
