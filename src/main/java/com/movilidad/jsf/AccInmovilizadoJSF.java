/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccInmovilizadoFacadeLocal;
import com.movilidad.model.AccInmovilizado;
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
 * Permite parametrizar la data relacionada con los objetos AccInmovilizado
 * Principal tabla afectada acc_inmovilizado
 *
 * @author HP
 */
@Named(value = "accInmovilizadoJSF")
@ViewScoped
public class AccInmovilizadoJSF implements Serializable {

    @EJB
    private AccInmovilizadoFacadeLocal accInmovilizadoFacadeLocal;

    private AccInmovilizado accInmovilizado;

    private List<AccInmovilizado> listAccInmovilizado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccInmovilizadoJSF() {
    }

    /**
     * Permite persistir la data del objeto AccInmovilizado en la base de datos
     */
    public void guardar() {
        try {
            if (accInmovilizado != null) {
                accInmovilizado.setInmovilizado(accInmovilizado.getInmovilizado().toUpperCase());
                accInmovilizado.setCreado(new Date());
                accInmovilizado.setModificado(new Date());
                accInmovilizado.setEstadoReg(0);
                accInmovilizado.setUsername(user.getUsername());
                accInmovilizadoFacadeLocal.create(accInmovilizado);
                MovilidadUtil.addSuccessMessage("Se a registrado el acc inmovilizado realizada correctamente");
                accInmovilizado = new AccInmovilizado();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar acc inmovilizado realizada");
        }
    }

    /**
     * Permite realizar un update del objeto AccInmovilizado en la base de datos
     */
    public void actualizar() {
        try {
            if (accInmovilizado != null) {
                accInmovilizado.setInmovilizado(accInmovilizado.getInmovilizado().toUpperCase());
                accInmovilizadoFacadeLocal.edit(accInmovilizado);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc inmovilizado realizada correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('inmovilizado-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc inmovilizado realizada");
        }
    }

    /**
     * Permite crear la instancia del objeto AccInmovilizado
     */
    public void prepareGuardar() {
        accInmovilizado = new AccInmovilizado();
    }

    public void reset() {
        accInmovilizado = null;
    }

    /**
     *
     * @param event Objeto AccInmovilizado seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accInmovilizado = (AccInmovilizado) event.getObject();
    }

    public AccInmovilizado getAccInmovilizado() {
        return accInmovilizado;
    }

    public void setAccInmovilizado(AccInmovilizado accInmovilizado) {
        this.accInmovilizado = accInmovilizado;
    }

    public List<AccInmovilizado> getListAccInmovilizado() {
        listAccInmovilizado = accInmovilizadoFacadeLocal.estadoReg();
        return listAccInmovilizado;
    }

}
