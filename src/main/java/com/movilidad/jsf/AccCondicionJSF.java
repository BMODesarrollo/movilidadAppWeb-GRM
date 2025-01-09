/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccCondicionFacadeLocal;
import com.movilidad.model.AccCondicion;
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
 * Permite parametrizar la data relacionada con los objetos AccCondicion
 * Principal tabla afectada acc_condicion
 *
 * @author HP
 */
@Named(value = "accCondicionJSF")
@ViewScoped
public class AccCondicionJSF implements Serializable {

    @EJB
    private AccCondicionFacadeLocal accCondicionFacadeLocal;

    private AccCondicion accCondicion;

    private List<AccCondicion> listAccCondicion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccCondicionJSF() {
    }

    /**
     * Permite persistir la data del objeto AccCondicion en la base de datos
     */
    public void guardar() {
        try {
            if (accCondicion != null) {
                accCondicion.setCondicion(accCondicion.getCondicion().toUpperCase());
                accCondicion.setCreado(new Date());
                accCondicion.setModificado(new Date());
                accCondicion.setEstadoReg(0);
                accCondicion.setUsername(user.getUsername());
                accCondicionFacadeLocal.create(accCondicion);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Condición correctamente");
                accCondicion = new AccCondicion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Condición");
        }
    }

    /**
     * Permite realizar un update del objeto AccCondicion en la base de datos
     */
    public void actualizar() {
        try {
            if (accCondicion != null) {
                accCondicion.setCondicion(accCondicion.getCondicion().toUpperCase());
                accCondicionFacadeLocal.edit(accCondicion);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Condición correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('condicion-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Condición");
        }
    }

    /**
     * Permite crear la instancia del objeto AccCondicion
     */
    public void prepareGuardar() {
        accCondicion = new AccCondicion();
    }

    public void reset() {
        accCondicion = null;
    }

    /**
     *
     * @param event Objeto AccCondicion seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accCondicion = (AccCondicion) event.getObject();
    }

    public AccCondicion getAccCondicion() {
        return accCondicion;
    }

    public void setAccCondicion(AccCondicion accCondicion) {
        this.accCondicion = accCondicion;
    }

    public List<AccCondicion> getListAccCondicion() {
        listAccCondicion = accCondicionFacadeLocal.estadoReg();
        return listAccCondicion;
    }

}
