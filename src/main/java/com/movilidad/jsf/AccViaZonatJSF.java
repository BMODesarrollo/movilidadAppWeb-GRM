/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaZonatFacadeLocal;
import com.movilidad.model.AccViaZonat;
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
@Named(value = "accViaZonatJSF")
@ViewScoped
public class AccViaZonatJSF implements Serializable {

    @EJB
    private AccViaZonatFacadeLocal accViaZonatFacadeLocal;

    private AccViaZonat accViaZonat;

    private List<AccViaZonat> listAccViaZonat;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaZonatJSF() {
    }

    public void guardar() {
        try {
            if (accViaZonat != null) {
                accViaZonat.setViaZonat(accViaZonat.getViaZonat().toUpperCase());
                accViaZonat.setCreado(new Date());
                accViaZonat.setModificado(new Date());
                accViaZonat.setEstadoReg(0);
                accViaZonat.setUsername(user.getUsername());
                accViaZonatFacadeLocal.create(accViaZonat);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Zonat correctamente");
                accViaZonat = new AccViaZonat();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Zonat");
        }
    }

    public void actualizar() {
        try {
            if (accViaZonat != null) {
                accViaZonat.setViaZonat(accViaZonat.getViaZonat().toUpperCase());
                accViaZonatFacadeLocal.edit(accViaZonat);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Zonat correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-zonat-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Zonat");
        }
    }

    public void prepareGuardar() {
        accViaZonat = new AccViaZonat();
    }

    public void reset() {
        accViaZonat = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaZonat = (AccViaZonat) event.getObject();
    }

    public AccViaZonat getAccViaZonat() {
        return accViaZonat;
    }

    public void setAccViaZonat(AccViaZonat accViaZonat) {
        this.accViaZonat = accViaZonat;
    }

    public List<AccViaZonat> getListAccViaZonat() {
        listAccViaZonat = accViaZonatFacadeLocal.estadoReg();
        return listAccViaZonat;
    }

}
