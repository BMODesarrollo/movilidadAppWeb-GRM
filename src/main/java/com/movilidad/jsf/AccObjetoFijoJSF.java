/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccObjetoFijoFacadeLocal;
import com.movilidad.model.AccObjetoFijo;
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
 * Permite parametrizar la data relacionada con los objetos AccObjetoFijo
 * Principal tabla afectada acc_objeto_fijo
 *
 * @author HP
 */
@Named(value = "accObjetoFijoJSF")
@ViewScoped
public class AccObjetoFijoJSF implements Serializable {

    @EJB
    private AccObjetoFijoFacadeLocal accObjetoFijoFacadeLocal;

    private AccObjetoFijo accObjetoFijo;

    private List<AccObjetoFijo> listAccObjetoFijo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccObjetoFijoJSF() {
    }

    /**
     * Permite persistir la data del objeto AccObjetoFijo en la base de datos
     */
    public void guardar() {
        try {
            if (accObjetoFijo != null) {
                accObjetoFijo.setObjetoFijo(accObjetoFijo.getObjetoFijo().toUpperCase());
                accObjetoFijo.setCreado(new Date());
                accObjetoFijo.setModificado(new Date());
                accObjetoFijo.setEstadoReg(0);
                accObjetoFijo.setUsername(user.getUsername());
                accObjetoFijoFacadeLocal.create(accObjetoFijo);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Objeto Fijo correctamente");
                accObjetoFijo = new AccObjetoFijo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Objeto Fijo");
        }
    }

    /**
     * Permite realizar un update del objeto AccObjetoFijo en la base de datos
     */
    public void actualizar() {
        try {
            if (accObjetoFijo != null) {
                accObjetoFijo.setObjetoFijo(accObjetoFijo.getObjetoFijo().toUpperCase());
                accObjetoFijoFacadeLocal.edit(accObjetoFijo);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Objeto Fijo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('obj-fijo-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Objeto Fijo");
        }
    }

    /**
     * Permite crear la instancia del objeto AccObjetoFijo
     */
    public void prepareGuardar() {
        accObjetoFijo = new AccObjetoFijo();
    }

    public void reset() {
        accObjetoFijo = null;
    }

    /**
     *
     * @param event Objeto AccObjetoFijo seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accObjetoFijo = (AccObjetoFijo) event.getObject();
    }

    public AccObjetoFijo getAccObjetoFijo() {
        return accObjetoFijo;
    }

    public void setAccObjetoFijo(AccObjetoFijo accObjetoFijo) {
        this.accObjetoFijo = accObjetoFijo;
    }

    public List<AccObjetoFijo> getListAccObjetoFijo() {
        listAccObjetoFijo = accObjetoFijoFacadeLocal.estadoReg();
        return listAccObjetoFijo;
    }

}
