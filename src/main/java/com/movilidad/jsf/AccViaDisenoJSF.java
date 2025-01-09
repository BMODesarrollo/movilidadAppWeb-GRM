/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaDisenoFacadeLocal;
import com.movilidad.model.AccViaDiseno;
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
@Named(value = "accViaDisenoJSF")
@ViewScoped
public class AccViaDisenoJSF implements Serializable {

    @EJB
    private AccViaDisenoFacadeLocal accViaDisenoFacadeLocal;

    private AccViaDiseno accViaDiseno;

    private List<AccViaDiseno> listAccViaDiseno;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaDisenoJSF() {
    }

    public void guardar() {
        try {
            if (accViaDiseno != null) {
                accViaDiseno.setViaDiseno(accViaDiseno.getViaDiseno().toUpperCase());
                accViaDiseno.setCreado(new Date());
                accViaDiseno.setModificado(new Date());
                accViaDiseno.setEstadoReg(0);
                accViaDiseno.setUsername(user.getUsername());
                accViaDisenoFacadeLocal.create(accViaDiseno);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Diseño correctamente");
                accViaDiseno = new AccViaDiseno();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Diseno");
        }
    }

    public void actualizar() {
        try {
            if (accViaDiseno != null) {
                accViaDiseno.setViaDiseno(accViaDiseno.getViaDiseno().toUpperCase());
                accViaDisenoFacadeLocal.edit(accViaDiseno);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Diseño correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-diseno-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Diseño");
        }
    }

    public void prepareGuardar() {
        accViaDiseno = new AccViaDiseno();
    }

    public void reset() {
        accViaDiseno = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaDiseno = (AccViaDiseno) event.getObject();
    }

    public AccViaDiseno getAccViaDiseno() {
        return accViaDiseno;
    }

    public void setAccViaDiseno(AccViaDiseno accViaDiseno) {
        this.accViaDiseno = accViaDiseno;
    }

    public List<AccViaDiseno> getListAccViaDiseno() {
        listAccViaDiseno = accViaDisenoFacadeLocal.estadoReg();
        return listAccViaDiseno;
    }

}
