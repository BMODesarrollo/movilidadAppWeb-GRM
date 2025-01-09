/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccParteViaFacadeLocal;
import com.movilidad.model.AccParteVia;
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
 * Permite parametrizar la data relacionada con los objetos AccParteVia
 * Principal tabla afectada acc_parte_via
 *
 * @author HP
 */
@Named(value = "accParteViaJSF")
@ViewScoped
public class AccParteViaJSF implements Serializable {

    @EJB
    private AccParteViaFacadeLocal accParteViaFacadeLocal;

    private AccParteVia accParteVia;

    private List<AccParteVia> listAccParteVia;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccParteViaJSF() {
    }

    /**
     * Permite persistir la data del objeto AccParteVia en la base de datos
     */
    public void guardar() {
        try {
            if (accParteVia != null) {
                accParteVia.setParteVia(accParteVia.getParteVia().toUpperCase());
                accParteVia.setCreado(new Date());
                accParteVia.setModificado(new Date());
                accParteVia.setEstadoReg(0);
                accParteVia.setUsername(user.getUsername());
                accParteViaFacadeLocal.create(accParteVia);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Parte Vía correctamente");
                accParteVia = new AccParteVia();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Parte Vía");
        }
    }

    /**
     * Permite realizar un update del objeto AccParteVia en la base de datos
     */
    public void actualizar() {
        try {
            if (accParteVia != null) {
                accParteVia.setParteVia(accParteVia.getParteVia().toUpperCase());
                accParteViaFacadeLocal.edit(accParteVia);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Parte Via correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('p-via-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Parte Vía");
        }
    }

    /**
     * Permite crear la instancia del objeto AccParteVia
     */
    public void prepareGuardar() {
        accParteVia = new AccParteVia();
    }

    public void reset() {
        accParteVia = null;
    }

    /**
     *
     * @param event Objeto AccParteVia seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accParteVia = (AccParteVia) event.getObject();
    }

    public AccParteVia getAccParteVia() {
        return accParteVia;
    }

    public void setAccParteVia(AccParteVia accParteVia) {
        this.accParteVia = accParteVia;
    }

    public List<AccParteVia> getListAccParteVia() {
        listAccParteVia = accParteViaFacadeLocal.estadoReg();
        return listAccParteVia;
    }

}
