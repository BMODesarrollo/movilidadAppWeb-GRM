/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccCondAmbientalFacadeLocal;
import com.movilidad.model.AccCondAmbiental;
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
 * Permite parametrizar la data relacionada con los objetos AccCondAmbiental
 * Principal tabla afectada acc_cond_ambiental
 *
 * @author HP
 */
@Named(value = "accCondAmbientalJSF")
@ViewScoped
public class AccCondAmbientalJSF implements Serializable {

    @EJB
    private AccCondAmbientalFacadeLocal accCondAmbientalFacadeLocal;

    private AccCondAmbiental accCondAmbiental;

    private List<AccCondAmbiental> listAccCondAmbiental;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccCondAmbientalJSF() {
    }

    /**
     * Permite persistir la data del objeto AccCondAmbiental en la base de datos
     */
    public void guardar() {
        try {
            if (accCondAmbiental != null) {
                accCondAmbiental.setCondAmbiental(accCondAmbiental.getCondAmbiental().toUpperCase());
                accCondAmbiental.setCreado(new Date());
                accCondAmbiental.setModificado(new Date());
                accCondAmbiental.setEstadoReg(0);
                accCondAmbiental.setUsername(user.getUsername());
                accCondAmbientalFacadeLocal.create(accCondAmbiental);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Condición Ambiental correctamente");
                accCondAmbiental = new AccCondAmbiental();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Condición Ambiental");
        }
    }

    /**
     * Permite realizar un update del objeto AccCondAmbiental en la base de datos
     */
    public void actualizar() {
        try {
            if (accCondAmbiental != null) {
                accCondAmbiental.setCondAmbiental(accCondAmbiental.getCondAmbiental().toUpperCase());
                accCondAmbientalFacadeLocal.edit(accCondAmbiental);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Condición Ambiental correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('cond-ambiental-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Ambiental");
        }
    }

    /**
     * Permite crear la instancia del objeto AccCondAmbiental
     */
    public void prepareGuardar() {
        accCondAmbiental = new AccCondAmbiental();
    }

    public void reset() {
        accCondAmbiental = null;
    }

    /**
     *
     * @param event Objeto AccCondAmbiental seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accCondAmbiental = (AccCondAmbiental) event.getObject();
    }

    public AccCondAmbiental getAccCondAmbiental() {
        return accCondAmbiental;
    }

    public void setAccCondAmbiental(AccCondAmbiental accCondAmbiental) {
        this.accCondAmbiental = accCondAmbiental;
    }

    public List<AccCondAmbiental> getListAccCondAmbiental() {
        listAccCondAmbiental = accCondAmbientalFacadeLocal.estadoReg();
        return listAccCondAmbiental;
    }

}
