/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaIluminacionFacadeLocal;
import com.movilidad.model.AccViaIluminacion;
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
@Named(value = "accViaIluminacionJSF")
@ViewScoped
public class AccViaIluminacionJSF implements Serializable {

    @EJB
    private AccViaIluminacionFacadeLocal accViaIluminacionFacadeLocal;

    private AccViaIluminacion accViaIluminacion;

    private List<AccViaIluminacion> listAccViaIluminacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaIluminacionJSF() {
    }

    public void guardar() {
        try {
            if (accViaIluminacion != null) {
                accViaIluminacion.setViaIluminacion(accViaIluminacion.getViaIluminacion().toUpperCase());
                accViaIluminacion.setCreado(new Date());
                accViaIluminacion.setModificado(new Date());
                accViaIluminacion.setEstadoReg(0);
                accViaIluminacion.setUsername(user.getUsername());
                accViaIluminacionFacadeLocal.create(accViaIluminacion);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Iluminación correctamente");
                accViaIluminacion = new AccViaIluminacion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Iluminación");
        }
    }

    public void actualizar() {
        try {
            if (accViaIluminacion != null) {
                accViaIluminacion.setViaIluminacion(accViaIluminacion.getViaIluminacion().toUpperCase());
                accViaIluminacionFacadeLocal.edit(accViaIluminacion);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Iluminación correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-iluminacion-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Iluminación");
        }
    }

    public void prepareGuardar() {
        accViaIluminacion = new AccViaIluminacion();
    }

    public void reset() {
        accViaIluminacion = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaIluminacion = (AccViaIluminacion) event.getObject();
    }

    public AccViaIluminacion getAccViaIluminacion() {
        return accViaIluminacion;
    }

    public void setAccViaIluminacion(AccViaIluminacion accViaIluminacion) {
        this.accViaIluminacion = accViaIluminacion;
    }

    public List<AccViaIluminacion> getListAccViaIluminacion() {
        listAccViaIluminacion = accViaIluminacionFacadeLocal.estadoReg();
        return listAccViaIluminacion;
    }

}
