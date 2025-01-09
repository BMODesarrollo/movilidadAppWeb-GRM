/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccRangoEdadFacadeLocal;
import com.movilidad.model.AccRangoEdad;
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
 * Permite parametrizar la data relacionada con los objetos AccRangoEdad
 * Principal tabla afectada acc_rango_edad
 *
 * @author HP
 */
@Named(value = "accRangoEdadJSF")
@ViewScoped
public class AccRangoEdadJSF implements Serializable {

    @EJB
    private AccRangoEdadFacadeLocal accRangoEdadFacadeLocal;

    private AccRangoEdad accRangoEdad;

    private List<AccRangoEdad> listAccRangoEdad;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccRangoEdadJSF() {
    }

    /**
     * Permite persistir la data del objeto AccRangoEdad en la base de datos
     */
    public void guardar() {
        try {
            if (accRangoEdad != null) {
                accRangoEdad.setRangoEdad(accRangoEdad.getRangoEdad().toUpperCase());
                accRangoEdad.setCreado(new Date());
                accRangoEdad.setModificado(new Date());
                accRangoEdad.setEstadoReg(0);
                accRangoEdad.setUsername(user.getUsername());
                accRangoEdadFacadeLocal.create(accRangoEdad);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Rango Edad correctamente");
                accRangoEdad = new AccRangoEdad();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Rango Edad");
        }
    }

    /**
     * Permite realizar un update del objeto AccRangoEdad en la base de datos
     */
    public void actualizar() {
        try {
            if (accRangoEdad != null) {
                accRangoEdad.setRangoEdad(accRangoEdad.getRangoEdad().toUpperCase());
                accRangoEdadFacadeLocal.edit(accRangoEdad);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Rango Edad correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('rango-edad-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Rango Edad");
        }
    }

    /**
     * Permite crear la instancia del objeto AccRangoEdad
     */
    public void prepareGuardar() {
        accRangoEdad = new AccRangoEdad();
    }

    public void reset() {
        accRangoEdad = null;
    }

    /**
     *
     * @param event Objeto AccRangoEdad seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accRangoEdad = (AccRangoEdad) event.getObject();
    }

    public AccRangoEdad getAccRangoEdad() {
        return accRangoEdad;
    }

    public void setAccRangoEdad(AccRangoEdad accRangoEdad) {
        this.accRangoEdad = accRangoEdad;
    }

    public List<AccRangoEdad> getListAccRangoEdad() {
        listAccRangoEdad = accRangoEdadFacadeLocal.estadoReg();
        return listAccRangoEdad;
    }

}
