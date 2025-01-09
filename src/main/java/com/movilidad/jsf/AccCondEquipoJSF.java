/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccCondEquipoFacadeLocal;
import com.movilidad.model.AccCondEquipo;
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
 * * * Permite parametrizar la data relacionada con los objetos AccCondEquip
 * Principal tabla afectada acc_cond_equip
 *
 * @author HP
 */
@Named(value = "accCondEquipoJSF")
@ViewScoped
public class AccCondEquipoJSF implements Serializable {

    @EJB
    private AccCondEquipoFacadeLocal accCondEquipoFacadeLocal;

    private AccCondEquipo accCondEquipo;

    private List<AccCondEquipo> listAccCondEquipo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccCondEquipoJSF() {
    }

    /**
     * Permite persistir la data del objeto AccCondEquip en la base de datos
     */
    public void guardar() {
        try {
            if (accCondEquipo != null) {
                accCondEquipo.setCondEquipo(accCondEquipo.getCondEquipo().toUpperCase());
                accCondEquipo.setCreado(new Date());
                accCondEquipo.setModificado(new Date());
                accCondEquipo.setEstadoReg(0);
                accCondEquipo.setUsername(user.getUsername());
                accCondEquipoFacadeLocal.create(accCondEquipo);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Condición del Equipo correctamente");
                accCondEquipo = new AccCondEquipo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Condición del Equipo");
        }
    }

    /**
     * Permite realizar un update del objeto AccCondEquip en la base de datos
     */
    public void actualizar() {
        try {
            if (accCondEquipo != null) {
                accCondEquipo.setCondEquipo(accCondEquipo.getCondEquipo().toUpperCase());
                accCondEquipoFacadeLocal.edit(accCondEquipo);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Condición del Equipo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('cond-equipo-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Condición del Equipo");
        }
    }

    /**
     * Permite crear la instancia del objeto AccCondEquip
     */
    public void prepareGuardar() {
        accCondEquipo = new AccCondEquipo();
    }

    public void reset() {
        accCondEquipo = null;
    }

    /**
     *
     * @param event Objeto AccCondEquip seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accCondEquipo = (AccCondEquipo) event.getObject();
    }

    public AccCondEquipo getAccCondEquipo() {
        return accCondEquipo;
    }

    public void setAccCondEquipo(AccCondEquipo accCondEquipo) {
        this.accCondEquipo = accCondEquipo;
    }

    public List<AccCondEquipo> getListAccCondEquipo() {
        listAccCondEquipo = accCondEquipoFacadeLocal.estadoReg();
        return listAccCondEquipo;
    }

}
