/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccEpsFacadeLocal;
import com.movilidad.model.AccEps;
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
 * Permite parametrizar la data relacionada con los objetos AccEps Principal
 * tabla afectada acc_eps
 *
 * @author HP
 */
@Named(value = "accEpsJSF")
@ViewScoped
public class AccEpsJSF implements Serializable {

    @EJB
    private AccEpsFacadeLocal accEpsFacadeLocal;

    private AccEps accEps;

    private List<AccEps> listAccEps;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccEpsJSF() {
    }

    /**
     * Permite persistir la data del objeto AccEps en la base de datos
     */
    public void guardar() {
        try {
            if (accEps != null) {
                accEps.setEps(accEps.getEps().toUpperCase());
                accEps.setCreado(new Date());
                accEps.setModificado(new Date());
                accEps.setEstadoReg(0);
                accEps.setUsername(user.getUsername());
                accEpsFacadeLocal.create(accEps);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Eps correctamente");
                accEps = new AccEps();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Eps");
        }
    }

    /**
     * Permite realizar un update del objeto AccEps en la base de datos
     */
    public void actualizar() {
        try {
            if (accEps != null) {
                accEps.setEps(accEps.getEps().toUpperCase());
                accEpsFacadeLocal.edit(accEps);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Eps correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('eps-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Eps");
        }
    }

    /**
     * Permite crear la instancia del objeto AccEps
     */
    public void prepareGuardar() {
        accEps = new AccEps();
    }

    public void reset() {
        accEps = null;
    }

    /**
     *
     * @param event Objeto AccEps seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accEps = (AccEps) event.getObject();
    }

    public AccEps getAccEps() {
        return accEps;
    }

    public void setAccEps(AccEps accEps) {
        this.accEps = accEps;
    }

    public List<AccEps> getListAccEps() {
        listAccEps = accEpsFacadeLocal.estadoReg();
        return listAccEps;
    }

}
