/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccLugarFacadeLocal;
import com.movilidad.model.AccLugar;
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
 * Permite parametrizar la data relacionada con los objetos AccLugar Principal
 * tabla afectada acc_lugar
 *
 * @author HP
 */
@Named(value = "accLugarJSF")
@ViewScoped
public class AccLugarJSF implements Serializable {

    @EJB
    private AccLugarFacadeLocal accLugarFacadeLocal;

    private AccLugar accLugar;

    private List<AccLugar> listAccLugar;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccLugarJSF() {
    }

    /**
     * Permite persistir la data del objeto AccLugar en la base de datos
     */
    public void guardar() {
        try {
            if (accLugar != null) {
                accLugar.setLugar(accLugar.getLugar().toUpperCase());
                accLugar.setCreado(new Date());
                accLugar.setModificado(new Date());
                accLugar.setEstadoReg(0);
                accLugar.setUsername(user.getUsername());
                accLugarFacadeLocal.create(accLugar);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Lugar correctamente");
                accLugar = new AccLugar();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Lugar");
        }
    }

    /**
     * Permite realizar un update del objeto AccLugar en la base de datos
     */
    public void actualizar() {
        try {
            if (accLugar != null) {
                accLugar.setLugar(accLugar.getLugar().toUpperCase());
                accLugarFacadeLocal.edit(accLugar);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Lugar correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('lugar-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Lugar");
        }
    }

    /**
     * Permite crear la instancia del objeto AccLugar
     */
    public void prepareGuardar() {
        accLugar = new AccLugar();
    }

    public void reset() {
        accLugar = null;
    }

    /**
     *
     * @param event Objeto AccLugar seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accLugar = (AccLugar) event.getObject();
    }

    public AccLugar getAccLugar() {
        return accLugar;
    }

    public void setAccLugar(AccLugar accLugar) {
        this.accLugar = accLugar;
    }

    public List<AccLugar> getListAccLugar() {
        listAccLugar = accLugarFacadeLocal.estadoReg();
        return listAccLugar;
    }

}
