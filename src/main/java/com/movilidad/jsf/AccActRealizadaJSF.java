/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccActRealizadaFacadeLocal;
import com.movilidad.model.AccActRealizada;
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
 * Permite parametrizar la data relacionada con los objetos AccActRealizada
 * Principal tabla afectada acc_act_realizada
 *
 * @author HP
 */
@Named(value = "accActRealizadaJSF")
@ViewScoped
public class AccActRealizadaJSF implements Serializable {

    @EJB
    private AccActRealizadaFacadeLocal accActRealizadaFacadeLocal;

    private AccActRealizada accActRealizada;

    private List<AccActRealizada> listAccActRealizada;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccActRealizadaJSF() {
    }

    /**
     * Permite persistir la data del objeto AccActRealizada en la base de datos
     */
    public void guardar() {
        try {
            if (accActRealizada != null) {
                accActRealizada.setActRealizada(accActRealizada.getActRealizada().toUpperCase());
                accActRealizada.setCreado(new Date());
                accActRealizada.setModificado(new Date());
                accActRealizada.setEstadoReg(0);
                accActRealizada.setUsername(user.getUsername());
                accActRealizadaFacadeLocal.create(accActRealizada);
                MovilidadUtil.addSuccessMessage("Se a registrado el acc actividad realizada correctamente");
                accActRealizada = new AccActRealizada();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar acc actividad realizada");
        }
    }

    /**
     * Permite realizar un update del objeto AccActRealizada en la base de datos
     */
    public void actualizar() {
        try {
            if (accActRealizada != null) {
                accActRealizada.setActRealizada(accActRealizada.getActRealizada().toUpperCase());
                accActRealizadaFacadeLocal.edit(accActRealizada);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc actividad realizada correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('a-realizada-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc actividad realizada");
        }
    }

    /**
     * Permite crear la instancia del objeto AccActRealizada
     */
    public void prepareGuardar() {
        accActRealizada = new AccActRealizada();
    }

    public void reset() {
        accActRealizada = null;
    }

    /**
     * Permite capturar el objeto AccActRealizada seleccionado por el usuario
     *
     * @param event Evento que captura el objeto AccActRealizada
     */
    public void onRowSelect(SelectEvent event) {
        accActRealizada = (AccActRealizada) event.getObject();
    }

    public AccActRealizada getAccActRealizada() {
        return accActRealizada;
    }

    public void setAccActRealizada(AccActRealizada accActRealizada) {
        this.accActRealizada = accActRealizada;
    }

    public List<AccActRealizada> getListAccActRealizada() {
        listAccActRealizada = accActRealizadaFacadeLocal.estadoReg();
        return listAccActRealizada;
    }

}
