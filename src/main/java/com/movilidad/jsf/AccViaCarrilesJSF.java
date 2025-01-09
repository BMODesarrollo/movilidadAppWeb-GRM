/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaCarrilesFacadeLocal;
import com.movilidad.model.AccViaCarriles;
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
@Named(value = "accViaCarrilesJSF")
@ViewScoped
public class AccViaCarrilesJSF implements Serializable {

    @EJB
    private AccViaCarrilesFacadeLocal accViaCarrilesFacadeLocal;

    private AccViaCarriles accViaCarriles;

    private List<AccViaCarriles> listAccViaCarriles;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaCarrilesJSF() {
    }

    public void guardar() {
        try {
            if (accViaCarriles != null) {
                accViaCarriles.setViaCarriles(accViaCarriles.getViaCarriles().toUpperCase());
                accViaCarriles.setCreado(new Date());
                accViaCarriles.setModificado(new Date());
                accViaCarriles.setEstadoReg(0);
                accViaCarriles.setUsername(user.getUsername());
                accViaCarrilesFacadeLocal.create(accViaCarriles);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Carriles correctamente");
                accViaCarriles = new AccViaCarriles();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Carriles");
        }
    }

    public void actualizar() {
        try {
            if (accViaCarriles != null) {
                accViaCarriles.setViaCarriles(accViaCarriles.getViaCarriles().toUpperCase());
                accViaCarrilesFacadeLocal.edit(accViaCarriles);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Carriles correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-carriles-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Carriles");
        }
    }

    public void prepareGuardar() {
        accViaCarriles = new AccViaCarriles();
    }

    public void reset() {
        accViaCarriles = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaCarriles = (AccViaCarriles) event.getObject();
    }

    public AccViaCarriles getAccViaCarriles() {
        return accViaCarriles;
    }

    public void setAccViaCarriles(AccViaCarriles accViaCarriles) {
        this.accViaCarriles = accViaCarriles;
    }

    public List<AccViaCarriles> getListAccViaCarriles() {
        listAccViaCarriles = accViaCarrilesFacadeLocal.estadoReg();
        return listAccViaCarriles;
    }

}
