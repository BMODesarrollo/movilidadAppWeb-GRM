/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaSectorFacadeLocal;
import com.movilidad.model.AccViaSector;
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
@Named(value = "accViaSectorJSF")
@ViewScoped
public class AccViaSectorJSF implements Serializable {

    @EJB
    private AccViaSectorFacadeLocal accViaSectorFacadeLocal;

    private AccViaSector accViaSector;

    private List<AccViaSector> listAccViaSector;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaSectorJSF() {
    }

    public void guardar() {
        try {
            if (accViaSector != null) {
                accViaSector.setViaSector(accViaSector.getViaSector().toUpperCase());
                accViaSector.setCreado(new Date());
                accViaSector.setModificado(new Date());
                accViaSector.setEstadoReg(0);
                accViaSector.setUsername(user.getUsername());
                accViaSectorFacadeLocal.create(accViaSector);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Sector correctamente");
                accViaSector = new AccViaSector();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Sector");
        }
    }

    public void actualizar() {
        try {
            if (accViaSector != null) {
                accViaSector.setViaSector(accViaSector.getViaSector().toUpperCase());
                accViaSectorFacadeLocal.edit(accViaSector);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Sector correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-sector-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Sector");
        }
    }

    public void prepareGuardar() {
        accViaSector = new AccViaSector();
    }

    public void reset() {
        accViaSector = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaSector = (AccViaSector) event.getObject();
    }

    public AccViaSector getAccViaSector() {
        return accViaSector;
    }

    public void setAccViaSector(AccViaSector accViaSector) {
        this.accViaSector = accViaSector;
    }

    public List<AccViaSector> getListAccViaSector() {
        listAccViaSector = accViaSectorFacadeLocal.estadoReg();
        return listAccViaSector;
    }

}
