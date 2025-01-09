/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccProfesionFacadeLocal;
import com.movilidad.model.AccProfesion;
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
 * Permite parametrizar la data relacionada con los objetos AccProfesion Principal
 * tabla afectada acc_profesion
 *
 * @author HP
 */
@Named(value = "accProfesionJSF")
@ViewScoped
public class AccProfesionJSF implements Serializable {

    @EJB
    private AccProfesionFacadeLocal accProfesionFacadeLocal;

    private AccProfesion accProfesion;

    private List<AccProfesion> listAccProfesion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccProfesionJSF() {
    }

    /**
     * Permite persistir la data del objeto AccProfesion en la base de datos
     */
    public void guardar() {
        try {
            if (accProfesion != null) {
                accProfesion.setProfesion(accProfesion.getProfesion().toUpperCase());
                accProfesion.setCreado(new Date());
                accProfesion.setModificado(new Date());
                accProfesion.setEstadoReg(0);
                accProfesion.setUsername(user.getUsername());
                accProfesionFacadeLocal.create(accProfesion);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Profesión correctamente");
                accProfesion = new AccProfesion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Profesión");
        }
    }

    /**
     * Permite realizar un update del objeto AccProfesion en la base de datos
     */
    public void actualizar() {
        try {
            if (accProfesion != null) {
                accProfesion.setProfesion(accProfesion.getProfesion().toUpperCase());
                accProfesionFacadeLocal.edit(accProfesion);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Profesión correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('profesion-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Profesión");
        }
    }

    /**
     * Permite crear la instancia del objeto AccProfesion
     */
    public void prepareGuardar() {
        accProfesion = new AccProfesion();
    }

    public void reset() {
        accProfesion = null;
    }

    /**
     *
     * @param event Objeto AccProfesion seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accProfesion = (AccProfesion) event.getObject();
    }

    public AccProfesion getAccProfesion() {
        return accProfesion;
    }

    public void setAccProfesion(AccProfesion accProfesion) {
        this.accProfesion = accProfesion;
    }

    public List<AccProfesion> getListAccProfesion() {
        listAccProfesion = accProfesionFacadeLocal.estadoReg();
        return listAccProfesion;
    }

}
