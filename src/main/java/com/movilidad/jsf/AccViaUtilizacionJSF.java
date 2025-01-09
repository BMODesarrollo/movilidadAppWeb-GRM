/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaUtilizacionFacadeLocal;
import com.movilidad.model.AccViaUtilizacion;
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
@Named(value = "accViaUtilizacionJSF")
@ViewScoped
public class AccViaUtilizacionJSF implements Serializable {

    @EJB
    private AccViaUtilizacionFacadeLocal accViaUtilizacionFacadeLocal;

    private AccViaUtilizacion accViaUtilizacion;

    private List<AccViaUtilizacion> listAccViaUtilizacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaUtilizacionJSF() {
    }

    public void guardar() {
        try {
            if (accViaUtilizacion != null) {
                accViaUtilizacion.setViaUtilizacion(accViaUtilizacion.getViaUtilizacion().toUpperCase());
                accViaUtilizacion.setCreado(new Date());
                accViaUtilizacion.setModificado(new Date());
                accViaUtilizacion.setEstadoReg(0);
                accViaUtilizacion.setUsername(user.getUsername());
                accViaUtilizacionFacadeLocal.create(accViaUtilizacion);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Utilización correctamente");
                accViaUtilizacion = new AccViaUtilizacion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Utilización");
        }
    }

    public void actualizar() {
        try {
            if (accViaUtilizacion != null) {
                accViaUtilizacion.setViaUtilizacion(accViaUtilizacion.getViaUtilizacion().toUpperCase());
                accViaUtilizacionFacadeLocal.edit(accViaUtilizacion);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Utilización correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-utilizacion-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Utilización");
        }
    }

    public void prepareGuardar() {
        accViaUtilizacion = new AccViaUtilizacion();
    }

    public void reset() {
        accViaUtilizacion = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaUtilizacion = (AccViaUtilizacion) event.getObject();
    }

    public AccViaUtilizacion getAccViaUtilizacion() {
        return accViaUtilizacion;
    }

    public void setAccViaUtilizacion(AccViaUtilizacion accViaUtilizacion) {
        this.accViaUtilizacion = accViaUtilizacion;
    }

    public List<AccViaUtilizacion> getListAccViaUtilizacion() {
        listAccViaUtilizacion = accViaUtilizacionFacadeLocal.estadoReg();
        return listAccViaUtilizacion;
    }

}
