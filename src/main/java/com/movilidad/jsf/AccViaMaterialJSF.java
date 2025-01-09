/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaMaterialFacadeLocal;
import com.movilidad.model.AccViaMaterial;
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
@Named(value = "accViaMaterialJSF")
@ViewScoped
public class AccViaMaterialJSF implements Serializable {

    @EJB
    private AccViaMaterialFacadeLocal accViaMaterialFacadeLocal;

    private AccViaMaterial accViaMaterial;

    private List<AccViaMaterial> listAccViaMaterial;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaMaterialJSF() {
    }

    public void guardar() {
        try {
            if (accViaMaterial != null) {
                accViaMaterial.setViaMaterial(accViaMaterial.getViaMaterial().toUpperCase());
                accViaMaterial.setCreado(new Date());
                accViaMaterial.setModificado(new Date());
                accViaMaterial.setEstadoReg(0);
                accViaMaterial.setUsername(user.getUsername());
                accViaMaterialFacadeLocal.create(accViaMaterial);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Material correctamente");
                accViaMaterial = new AccViaMaterial();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Material");
        }
    }

    public void actualizar() {
        try {
            if (accViaMaterial != null) {
                accViaMaterial.setViaMaterial(accViaMaterial.getViaMaterial().toUpperCase());
                accViaMaterialFacadeLocal.edit(accViaMaterial);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Material correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-material-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Material");
        }
    }

    public void prepareGuardar() {
        accViaMaterial = new AccViaMaterial();
    }

    public void reset() {
        accViaMaterial = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaMaterial = (AccViaMaterial) event.getObject();
    }

    public AccViaMaterial getAccViaMaterial() {
        return accViaMaterial;
    }

    public void setAccViaMaterial(AccViaMaterial accViaMaterial) {
        this.accViaMaterial = accViaMaterial;
    }

    public List<AccViaMaterial> getListAccViaMaterial() {
        listAccViaMaterial = accViaMaterialFacadeLocal.estadoReg();
        return listAccViaMaterial;
    }

}
