/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaCondicionFacadeLocal;
import com.movilidad.model.AccViaCondicion;
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
@Named(value = "accViaCondicionJSF")
@ViewScoped
public class AccViaCondicionJSF implements Serializable {

    @EJB
    private AccViaCondicionFacadeLocal accViaCondicionFacadeLocal;

    private AccViaCondicion accViaCondicion;

    private List<AccViaCondicion> listAccViaCondicion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaCondicionJSF() {
    }

    public void guardar() {
        try {
            if (accViaCondicion != null) {
                accViaCondicion.setViaCondicion(accViaCondicion.getViaCondicion().toUpperCase());
                accViaCondicion.setCreado(new Date());
                accViaCondicion.setModificado(new Date());
                accViaCondicion.setEstadoReg(0);
                accViaCondicion.setUsername(user.getUsername());
                accViaCondicionFacadeLocal.create(accViaCondicion);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Condición correctamente");
                accViaCondicion = new AccViaCondicion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Condición");
        }
    }

    public void actualizar() {
        try {
            if (accViaCondicion != null) {
                accViaCondicion.setViaCondicion(accViaCondicion.getViaCondicion().toUpperCase());
                accViaCondicionFacadeLocal.edit(accViaCondicion);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Condición correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-condicion-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Condición");
        }
    }

    public void prepareGuardar() {
        accViaCondicion = new AccViaCondicion();
    }

    public void reset() {
        accViaCondicion = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaCondicion = (AccViaCondicion) event.getObject();
    }

    public AccViaCondicion getAccViaCondicion() {
        return accViaCondicion;
    }

    public void setAccViaCondicion(AccViaCondicion accViaCondicion) {
        this.accViaCondicion = accViaCondicion;
    }

    public List<AccViaCondicion> getListAccViaCondicion() {
        listAccViaCondicion = accViaCondicionFacadeLocal.estadoReg();
        return listAccViaCondicion;
    }

}
