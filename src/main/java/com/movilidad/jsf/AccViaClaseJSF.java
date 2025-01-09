/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaClaseFacadeLocal;
import com.movilidad.model.AccViaClase;
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
@Named(value = "accViaClaseJSF")
@ViewScoped
public class AccViaClaseJSF implements Serializable {

    @EJB
    private AccViaClaseFacadeLocal accViaClaseFacadeLocal;

    private AccViaClase accViaClase;

    private List<AccViaClase> listAccViaClase;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaClaseJSF() {
    }

    public void guardar() {
        try {
            if (accViaClase != null) {
                accViaClase.setViaClase(accViaClase.getViaClase().toUpperCase());
                accViaClase.setCreado(new Date());
                accViaClase.setModificado(new Date());
                accViaClase.setEstadoReg(0);
                accViaClase.setUsername(user.getUsername());
                accViaClaseFacadeLocal.create(accViaClase);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Clase correctamente");
                accViaClase = new AccViaClase();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Clase");
        }
    }

    public void actualizar() {
        try {
            if (accViaClase != null) {
                accViaClase.setViaClase(accViaClase.getViaClase().toUpperCase());
                accViaClaseFacadeLocal.edit(accViaClase);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Clase correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-clase-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Clase");
        }
    }

    public void prepareGuardar() {
        accViaClase = new AccViaClase();
    }

    public void reset() {
        accViaClase = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaClase = (AccViaClase) event.getObject();
    }

    public AccViaClase getAccViaClase() {
        return accViaClase;
    }

    public void setAccViaClase(AccViaClase accViaClase) {
        this.accViaClase = accViaClase;
    }

    public List<AccViaClase> getListAccViaClase() {
        listAccViaClase = accViaClaseFacadeLocal.estadoReg();
        return listAccViaClase;
    }

}
