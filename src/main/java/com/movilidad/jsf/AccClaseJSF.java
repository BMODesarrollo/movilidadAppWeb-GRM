/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccClaseFacadeLocal;
import com.movilidad.model.AccClase;
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
 * Permite parametrizar la data relacionada con los objetos AccClase Principal
 * tabla afectada acc_clase
 *
 * @author HP
 */
@Named(value = "accClaseJSF")
@ViewScoped
public class AccClaseJSF implements Serializable {

    @EJB
    private AccClaseFacadeLocal accClaseFacadeLocal;

    private AccClase accClase;

    private List<AccClase> listAccClase;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccClaseJSF() {
    }

    /**
     * Permite persistir la data del objeto AccClase en la base de datos
     */
    public void guardar() {
        try {
            if (accClase != null) {
                accClase.setClase(accClase.getClase().toUpperCase());
                accClase.setCreado(new Date());
                accClase.setModificado(new Date());
                accClase.setEstadoReg(0);
                accClase.setUsername(user.getUsername());
                accClaseFacadeLocal.create(accClase);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Clase correctamente");
                accClase = new AccClase();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Clase");
        }
    }

    /**
     * Permite realizar un update del objeto AccClase en la base de datos
     */
    public void actualizar() {
        try {
            if (accClase != null) {
                accClase.setClase(accClase.getClase().toUpperCase());
                accClaseFacadeLocal.edit(accClase);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Clase correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('clase-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Clase");
        }
    }

    /**
     * Permite crear la instancia del objeto AccClase
     */
    public void prepareGuardar() {
        accClase = new AccClase();
    }

    public void reset() {
        accClase = null;
    }

    /**
     *
     * @param event Objeto AccClase seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accClase = (AccClase) event.getObject();
    }

    public AccClase getAccClase() {
        return accClase;
    }

    public void setAccClase(AccClase accClase) {
        this.accClase = accClase;
    }

    public List<AccClase> getListAccClase() {
        listAccClase = accClaseFacadeLocal.estadoReg();
        return listAccClase;
    }

}
