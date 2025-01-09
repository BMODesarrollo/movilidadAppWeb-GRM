/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccCondHumanaFacadeLocal;
import com.movilidad.model.AccCondHumana;
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
 * Permite parametrizar la data relacionada con los objetos AccCondHumana
 * Principal tabla afectada acc_cond_humana
 *
 * @author HP
 */
@Named(value = "accCondHumanaJSF")
@ViewScoped
public class AccCondHumanaJSF implements Serializable {

    @EJB
    private AccCondHumanaFacadeLocal accCondHumanaFacadeLocal;

    private AccCondHumana accCondHumana;

    private List<AccCondHumana> listAccCondHumana;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccCondHumanaJSF() {
    }

    /**
     * Permite persistir la data del objeto AccCondHumana en la base de datos
     */
    public void guardar() {
        try {
            if (accCondHumana != null) {
                accCondHumana.setCondHumana(accCondHumana.getCondHumana().toUpperCase());
                accCondHumana.setCreado(new Date());
                accCondHumana.setModificado(new Date());
                accCondHumana.setEstadoReg(0);
                accCondHumana.setUsername(user.getUsername());
                accCondHumanaFacadeLocal.create(accCondHumana);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Condición Humana correctamente");
                accCondHumana = new AccCondHumana();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Condición Humana");
        }
    }

    /**
     * Permite realizar un update del objeto AccCondHumana en la base de datos
     */
    public void actualizar() {
        try {
            if (accCondHumana != null) {
                accCondHumana.setCondHumana(accCondHumana.getCondHumana().toUpperCase());
                accCondHumanaFacadeLocal.edit(accCondHumana);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Condición Humana correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('cond-humana-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Condición Humana");
        }
    }

    /**
     * Permite crear la instancia del objeto AccCondHumana
     */
    public void prepareGuardar() {
        accCondHumana = new AccCondHumana();
    }

    public void reset() {
        accCondHumana = null;
    }

    /**
     *
     * @param event Objeto AccCondHumana seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accCondHumana = (AccCondHumana) event.getObject();
    }

    public AccCondHumana getAccCondHumana() {
        return accCondHumana;
    }

    public void setAccCondHumana(AccCondHumana accCondHumana) {
        this.accCondHumana = accCondHumana;
    }

    public List<AccCondHumana> getListAccCondHumana() {
        listAccCondHumana = accCondHumanaFacadeLocal.estadoReg();
        return listAccCondHumana;
    }

}
