/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoLesionFacadeLocal;
import com.movilidad.model.AccTipoLesion;
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
 *
 * @author HP
 */
@Named(value = "accTipoLesionJSF")
@ViewScoped
public class AccTipoLesionJSF implements Serializable {

    @EJB
    private AccTipoLesionFacadeLocal accTipoLesionFacadeLocal;

    private AccTipoLesion accTipoLesion;

    private List<AccTipoLesion> listAccTipoLesion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccTipoLesionJSF() {
    }

    public void guardar() {
        try {
            if (accTipoLesion != null) {
                accTipoLesion.setTipoLesion(accTipoLesion.getTipoLesion().toUpperCase());
                accTipoLesion.setCreado(new Date());
                accTipoLesion.setModificado(new Date());
                accTipoLesion.setEstadoReg(0);
                accTipoLesion.setUsername(user.getUsername());
                accTipoLesionFacadeLocal.create(accTipoLesion);
                MovilidadUtil.addSuccessMessage("Se a registrado el tipo de lesión correctamente");
                accTipoLesion = new AccTipoLesion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar tipo de lesión");
        }
    }

    public void actualizar() {
        try {
            if (accTipoLesion != null) {
                accTipoLesion.setTipoLesion(accTipoLesion.getTipoLesion().toUpperCase());
                accTipoLesionFacadeLocal.edit(accTipoLesion);
                MovilidadUtil.addSuccessMessage("Se a actualizado el tipo de lesión correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('tp-lesion-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar tipo de lesión");
        }
    }

    public void prepareGuardar() {
        accTipoLesion = new AccTipoLesion();
    }

    public void reset() {
        accTipoLesion = null;
    }

    public void onRowSelect(SelectEvent event) {
        accTipoLesion = (AccTipoLesion) event.getObject();
    }

    public AccTipoLesion getAccTipoLesion() {
        return accTipoLesion;
    }

    public void setAccTipoLesion(AccTipoLesion accTipoLesion) {
        this.accTipoLesion = accTipoLesion;
    }

    public List<AccTipoLesion> getListAccTipoLesion() {
        listAccTipoLesion = accTipoLesionFacadeLocal.estadoReg();
        return listAccTipoLesion;
    }

}
