/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccDetClaseFacadeLocal;
import com.movilidad.model.AccDetClase;
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
 * Permite parametrizar la data relacionada con los objetos AccDetClase
 * Principal tabla afectada acc_det_clase
 *
 * @author HP
 */
@Named(value = "accDetClaseJSF")
@ViewScoped
public class AccDetClaseJSF implements Serializable {

    @EJB
    private AccDetClaseFacadeLocal accDetClaseFacadeLocal;

    private AccDetClase accDetClase;

    private List<AccDetClase> listAccDetClase;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccDetClaseJSF() {
    }

    /**
     * Permite persistir la data del objeto AccDetClase en la base de datos
     */
    public void guardar() {
        try {
            if (accDetClase != null) {
                accDetClase.setDetClase(accDetClase.getDetClase().toUpperCase());
                accDetClase.setCreado(new Date());
                accDetClase.setModificado(new Date());
                accDetClase.setEstadoReg(0);
                accDetClase.setUsername(user.getUsername());
                accDetClaseFacadeLocal.create(accDetClase);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Detalle Clase correctamente");
                accDetClase = new AccDetClase();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Detalle Clase");
        }
    }

    /**
     * Permite realizar un update del objeto AccDetClase en la base de datos
     */
    public void actualizar() {
        try {
            if (accDetClase != null) {
                accDetClase.setDetClase(accDetClase.getDetClase().toUpperCase());
                accDetClaseFacadeLocal.edit(accDetClase);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Detalle Clase correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('det-clase-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Detalle Clase");
        }
    }

    /**
     * Permite crear la instancia del objeto AccDetClase
     */
    public void prepareGuardar() {
        accDetClase = new AccDetClase();
    }

    public void reset() {
        accDetClase = null;
    }

    /**
     *
     * @param event Objeto AccDetClase seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accDetClase = (AccDetClase) event.getObject();
    }

    public AccDetClase getAccDetClase() {
        return accDetClase;
    }

    public void setAccDetClase(AccDetClase accDetClase) {
        this.accDetClase = accDetClase;
    }

    public List<AccDetClase> getListAccDetClase() {
        listAccDetClase = accDetClaseFacadeLocal.estadoReg();
        return listAccDetClase;
    }

}
