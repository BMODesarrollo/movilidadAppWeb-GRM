/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaDemarcacionFacadeLocal;
import com.movilidad.model.AccViaDemarcacion;
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
@Named(value = "accViaDemarcacionJSF")
@ViewScoped
public class AccViaDemarcacionJSF implements Serializable {

    @EJB
    private AccViaDemarcacionFacadeLocal accViaDemarcacionFacadeLocal;

    private AccViaDemarcacion accViaDemarcacion;

    private List<AccViaDemarcacion> listAccViaDemarcacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaDemarcacionJSF() {
    }

    public void guardar() {
        try {
            if (accViaDemarcacion != null) {
                accViaDemarcacion.setViaDemarcacion(accViaDemarcacion.getViaDemarcacion().toUpperCase());
                accViaDemarcacion.setCreado(new Date());
                accViaDemarcacion.setModificado(new Date());
                accViaDemarcacion.setEstadoReg(0);
                accViaDemarcacion.setUsername(user.getUsername());
                accViaDemarcacionFacadeLocal.create(accViaDemarcacion);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Demarcación correctamente");
                accViaDemarcacion = new AccViaDemarcacion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Demarcación");
        }
    }

    public void actualizar() {
        try {
            if (accViaDemarcacion != null) {
                accViaDemarcacion.setViaDemarcacion(accViaDemarcacion.getViaDemarcacion().toUpperCase());
                accViaDemarcacionFacadeLocal.edit(accViaDemarcacion);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Demarcación correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-demarcacion-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Demarcación");
        }
    }

    public void prepareGuardar() {
        accViaDemarcacion = new AccViaDemarcacion();
    }

    public void reset() {
        accViaDemarcacion = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaDemarcacion = (AccViaDemarcacion) event.getObject();
    }

    public AccViaDemarcacion getAccViaDemarcacion() {
        return accViaDemarcacion;
    }

    public void setAccViaDemarcacion(AccViaDemarcacion accViaDemarcacion) {
        this.accViaDemarcacion = accViaDemarcacion;
    }

    public List<AccViaDemarcacion> getListAccViaDemarcacion() {
        listAccViaDemarcacion = accViaDemarcacionFacadeLocal.estadoReg();
        return listAccViaDemarcacion;
    }

}
