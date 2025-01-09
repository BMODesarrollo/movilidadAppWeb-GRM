/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccParteAfectadaFacadeLocal;
import com.movilidad.model.AccParteAfectada;
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
 * Permite parametrizar la data relacionada con los objetos AccParteAfectada
 * Principal tabla afectada acc_parte_afectada
 *
 * @author HP
 */
@Named(value = "accParteAfectadaJSF")
@ViewScoped
public class AccParteAfectadaJSF implements Serializable {

    @EJB
    private AccParteAfectadaFacadeLocal accParteAfectadaFacadeLocal;

    private AccParteAfectada accParteAfectada;

    private List<AccParteAfectada> listAccParteAfectada;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccParteAfectadaJSF() {
    }

    /**
     * Permite persistir la data del objeto AccParteAfectada en la base de datos
     */
    public void guardar() {
        try {
            if (accParteAfectada != null) {
                accParteAfectada.setParteAfectada(accParteAfectada.getParteAfectada().toUpperCase());
                accParteAfectada.setCreado(new Date());
                accParteAfectada.setModificado(new Date());
                accParteAfectada.setEstadoReg(0);
                accParteAfectada.setUsername(user.getUsername());
                accParteAfectadaFacadeLocal.create(accParteAfectada);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Parte Afectada correctamente");
                accParteAfectada = new AccParteAfectada();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Parte Afectada");
        }
    }

    /**
     * Permite realizar un update del objeto AccParteAfectada en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (accParteAfectada != null) {
                accParteAfectada.setParteAfectada(accParteAfectada.getParteAfectada().toUpperCase());
                accParteAfectadaFacadeLocal.edit(accParteAfectada);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Parte Afectada correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('p-afec-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Parte Afectada");
        }
    }

    /**
     * Permite crear la instancia del objeto AccParteAfectada
     */
    public void prepareGuardar() {
        accParteAfectada = new AccParteAfectada();
    }

    public void reset() {
        accParteAfectada = null;
    }

    /**
     *
     * @param event Objeto AccParteAfectada seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accParteAfectada = (AccParteAfectada) event.getObject();
    }

    public AccParteAfectada getAccParteAfectada() {
        return accParteAfectada;
    }

    public void setAccParteAfectada(AccParteAfectada accParteAfectada) {
        this.accParteAfectada = accParteAfectada;
    }

    public List<AccParteAfectada> getListAccParteAfectada() {
        listAccParteAfectada = accParteAfectadaFacadeLocal.estadoReg();
        return listAccParteAfectada;
    }

}
