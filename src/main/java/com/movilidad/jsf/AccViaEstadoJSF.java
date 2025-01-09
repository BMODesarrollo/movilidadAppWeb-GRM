/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaEstadoFacadeLocal;
import com.movilidad.model.AccViaEstado;
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
@Named(value = "accViaEstadoJSF")
@ViewScoped
public class AccViaEstadoJSF implements Serializable {

    @EJB
    private AccViaEstadoFacadeLocal accViaEstadoFacadeLocal;

    private AccViaEstado accViaEstado;

    private List<AccViaEstado> listAccViaEstado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaEstadoJSF() {
    }

    public void guardar() {
        try {
            if (accViaEstado != null) {
                accViaEstado.setViaEstado(accViaEstado.getViaEstado().toUpperCase());
                accViaEstado.setCreado(new Date());
                accViaEstado.setModificado(new Date());
                accViaEstado.setEstadoReg(0);
                accViaEstado.setUsername(user.getUsername());
                accViaEstadoFacadeLocal.create(accViaEstado);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Estado correctamente");
                accViaEstado = new AccViaEstado();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Estado");
        }
    }

    public void actualizar() {
        try {
            if (accViaEstado != null) {
                accViaEstado.setViaEstado(accViaEstado.getViaEstado().toUpperCase());
                accViaEstadoFacadeLocal.edit(accViaEstado);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Estado correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-estado-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Estado");
        }
    }

    public void prepareGuardar() {
        accViaEstado = new AccViaEstado();
    }

    public void reset() {
        accViaEstado = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaEstado = (AccViaEstado) event.getObject();
    }

    public AccViaEstado getAccViaEstado() {
        return accViaEstado;
    }

    public void setAccViaEstado(AccViaEstado accViaEstado) {
        this.accViaEstado = accViaEstado;
    }

    public List<AccViaEstado> getListAccViaEstado() {
        listAccViaEstado = accViaEstadoFacadeLocal.estadoReg();
        return listAccViaEstado;
    }

}
