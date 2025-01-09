/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaZonaFacadeLocal;
import com.movilidad.model.AccViaZona;
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
@Named(value = "accViaZonaJSF")
@ViewScoped
public class AccViaZonaJSF implements Serializable {

    @EJB
    private AccViaZonaFacadeLocal accViaZonaFacadeLocal;

    private AccViaZona accViaZona;

    private List<AccViaZona> listAccViaZona;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaZonaJSF() {
    }

    public void guardar() {
        try {
            if (accViaZona != null) {
                accViaZona.setViaZona(accViaZona.getViaZona().toUpperCase());
                accViaZona.setCreado(new Date());
                accViaZona.setModificado(new Date());
                accViaZona.setEstadoReg(0);
                accViaZona.setUsername(user.getUsername());
                accViaZonaFacadeLocal.create(accViaZona);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Zona correctamente");
                accViaZona = new AccViaZona();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Zona");
        }
    }

    public void actualizar() {
        try {
            if (accViaZona != null) {
                accViaZona.setViaZona(accViaZona.getViaZona().toUpperCase());
                accViaZonaFacadeLocal.edit(accViaZona);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Zona correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-zona-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Zona");
        }
    }

    public void prepareGuardar() {
        accViaZona = new AccViaZona();
    }

    public void reset() {
        accViaZona = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaZona = (AccViaZona) event.getObject();
    }

    public AccViaZona getAccViaZona() {
        return accViaZona;
    }

    public void setAccViaZona(AccViaZona accViaZona) {
        this.accViaZona = accViaZona;
    }

    public List<AccViaZona> getListAccViaZona() {
        listAccViaZona = accViaZonaFacadeLocal.estadoReg();
        return listAccViaZona;
    }

}
