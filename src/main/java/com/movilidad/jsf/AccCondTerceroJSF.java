/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccCondTerceroFacadeLocal;
import com.movilidad.model.AccCondTercero;
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
 * Permite parametrizar la data relacionada con los objetos AccCondTercero Principal
 * tabla afectada acc_cond_tercero
 *
 * @author HP
 */
@Named(value = "accCondTerceroJSF")
@ViewScoped
public class AccCondTerceroJSF implements Serializable {

    @EJB
    private AccCondTerceroFacadeLocal accCondTerceroFacadeLocal;

    private AccCondTercero accCondTercero;

    private List<AccCondTercero> listAccCondTercero;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccCondTerceroJSF() {
    }

    /**
     * Permite persistir la data del objeto AccCondTercero en la base de datos
     */
    public void guardar() {
        try {
            if (accCondTercero != null) {
                accCondTercero.setCondTercero(accCondTercero.getCondTercero().toUpperCase());
                accCondTercero.setCreado(new Date());
                accCondTercero.setModificado(new Date());
                accCondTercero.setEstadoReg(0);
                accCondTercero.setUsername(user.getUsername());
                accCondTerceroFacadeLocal.create(accCondTercero);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Condición de Terceros correctamente");
                accCondTercero = new AccCondTercero();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Condición de Terceros");
        }
    }

    /**
     * Permite realizar un update del objeto AccCondTercero en la base de datos
     */
    public void actualizar() {
        try {
            if (accCondTercero != null) {
                accCondTercero.setCondTercero(accCondTercero.getCondTercero().toUpperCase());
                accCondTerceroFacadeLocal.edit(accCondTercero);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Condición de Terceros correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('cond-tercero-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Condición de Terceros");
        }
    }

    /**
     * Permite crear la instancia del objeto AccCondTercero
     */
    public void prepareGuardar() {
        accCondTercero = new AccCondTercero();
    }

    public void reset() {
        accCondTercero = null;
    }

    /**
     *
     * @param event Objeto AccCondTercero seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accCondTercero = (AccCondTercero) event.getObject();
    }

    public AccCondTercero getAccCondTercero() {
        return accCondTercero;
    }

    public void setAccCondTercero(AccCondTercero accCondTercero) {
        this.accCondTercero = accCondTercero;
    }

    public List<AccCondTercero> getListAccCondTercero() {
        listAccCondTercero = accCondTerceroFacadeLocal.estadoReg();
        return listAccCondTercero;
    }

}
