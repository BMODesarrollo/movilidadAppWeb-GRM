/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoVehiculoFacadeLocal;
import com.movilidad.model.AccTipoVehiculo;
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
@Named(value = "accTipoVehiculoJSF")
@ViewScoped
public class AccTipoVehiculoJSF implements Serializable {

    @EJB
    private AccTipoVehiculoFacadeLocal accTipoVehiculoFacadeLocal;

    private AccTipoVehiculo accTipoVehiculo;

    private List<AccTipoVehiculo> listAccTipoVehiculo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccTipoVehiculoJSF() {
    }

    public void guardar() {
        try {
            if (accTipoVehiculo != null) {
                accTipoVehiculo.setTipoVehiculo(accTipoVehiculo.getTipoVehiculo().toUpperCase());
                accTipoVehiculo.setCreado(new Date());
                accTipoVehiculo.setModificado(new Date());
                accTipoVehiculo.setEstadoReg(0);
                accTipoVehiculo.setUsername(user.getUsername());
                accTipoVehiculoFacadeLocal.create(accTipoVehiculo);
                MovilidadUtil.addSuccessMessage("Se a registrado el acc tipo vehículo realizada correctamente");
                accTipoVehiculo = new AccTipoVehiculo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar acc tipo vehículo realizada");
        }
    }

    public void actualizar() {
        try {
            if (accTipoVehiculo != null) {
                accTipoVehiculo.setTipoVehiculo(accTipoVehiculo.getTipoVehiculo().toUpperCase());
                accTipoVehiculoFacadeLocal.edit(accTipoVehiculo);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc tipo vehículo realizada correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('tp-vehiculo-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc tipo vehículo realizada");
        }
    }

    public void prepareGuardar() {
        accTipoVehiculo = new AccTipoVehiculo();
    }

    public void reset() {
        accTipoVehiculo = null;
    }

    public void onRowSelect(SelectEvent event) {
        accTipoVehiculo = (AccTipoVehiculo) event.getObject();
    }

    public AccTipoVehiculo getAccTipoVehiculo() {
        return accTipoVehiculo;
    }

    public void setAccTipoVehiculo(AccTipoVehiculo accTipoVehiculo) {
        this.accTipoVehiculo = accTipoVehiculo;
    }

    public List<AccTipoVehiculo> getListAccTipoVehiculo() {
        listAccTipoVehiculo = accTipoVehiculoFacadeLocal.estadoReg();
        return listAccTipoVehiculo;
    }

}
