/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaClimaFacadeLocal;
import com.movilidad.model.AccViaClima;
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
@Named(value = "accViaClimaJSF")
@ViewScoped
public class AccViaClimaJSF implements Serializable {

    @EJB
    private AccViaClimaFacadeLocal accViaClimaFacadeLocal;

    private AccViaClima accViaClima;

    private List<AccViaClima> listAccViaClima;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaClimaJSF() {
    }

    public void guardar() {
        try {
            if (accViaClima != null) {
                accViaClima.setViaClima(accViaClima.getViaClima().toUpperCase());
                accViaClima.setCreado(new Date());
                accViaClima.setModificado(new Date());
                accViaClima.setEstadoReg(0);
                accViaClima.setUsername(user.getUsername());
                accViaClimaFacadeLocal.create(accViaClima);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Clima correctamente");
                accViaClima = new AccViaClima();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Clima");
        }
    }

    public void actualizar() {
        try {
            if (accViaClima != null) {
                accViaClima.setViaClima(accViaClima.getViaClima().toUpperCase());
                accViaClimaFacadeLocal.edit(accViaClima);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Clima correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-clima-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Clima");
        }
    }

    public void prepareGuardar() {
        accViaClima = new AccViaClima();
    }

    public void reset() {
        accViaClima = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaClima = (AccViaClima) event.getObject();
    }

    public AccViaClima getAccViaClima() {
        return accViaClima;
    }

    public void setAccViaClima(AccViaClima accViaClima) {
        this.accViaClima = accViaClima;
    }

    public List<AccViaClima> getListAccViaClima() {
        listAccViaClima = accViaClimaFacadeLocal.estadoReg();
        return listAccViaClima;
    }

}
