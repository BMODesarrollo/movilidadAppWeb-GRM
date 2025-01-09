/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccCondInfraFacadeLocal;
import com.movilidad.model.AccCondInfra;
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
 * Permite parametrizar la data relacionada con los objetos AccClase Principal
 * tabla afectada acc_cond_infra
 *
 * @author HP
 */
@Named(value = "accCondInfraJSF")
@ViewScoped
public class AccCondInfraJSF implements Serializable {

    @EJB
    private AccCondInfraFacadeLocal accCondInfraFacadeLocal;

    private AccCondInfra accCondInfra;

    private List<AccCondInfra> listAccCondInfra;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccCondInfraJSF() {
    }

    /**
     * Permite persistir la data del objeto AccClase en la base de datos
     */
    public void guardar() {
        try {
            if (accCondInfra != null) {
                accCondInfra.setCondInfra(accCondInfra.getCondInfra().toUpperCase());
                accCondInfra.setCreado(new Date());
                accCondInfra.setModificado(new Date());
                accCondInfra.setEstadoReg(0);
                accCondInfra.setUsername(user.getUsername());
                accCondInfraFacadeLocal.create(accCondInfra);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Condición de la Infracción correctamente");
                accCondInfra = new AccCondInfra();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Condición de la Infracción");
        }
    }

    /**
     * Permite realizar un update del objeto AccClase en la base de datos
     */
    public void actualizar() {
        try {
            if (accCondInfra != null) {
                accCondInfra.setCondInfra(accCondInfra.getCondInfra().toUpperCase());
                accCondInfraFacadeLocal.edit(accCondInfra);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Condición de la Infracción correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('cond-infra-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Condición de la Infracción");
        }
    }

    /**
     * Permite crear la instancia del objeto AccClase
     */
    public void prepareGuardar() {
        accCondInfra = new AccCondInfra();
    }

    public void reset() {
        accCondInfra = null;
    }

    /**
     *
     * @param event Objeto AccClase seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accCondInfra = (AccCondInfra) event.getObject();
    }

    public AccCondInfra getAccCondInfra() {
        return accCondInfra;
    }

    public void setAccCondInfra(AccCondInfra accCondInfra) {
        this.accCondInfra = accCondInfra;
    }

    public List<AccCondInfra> getListAccCondInfra() {
        listAccCondInfra = accCondInfraFacadeLocal.estadoReg();
        return listAccCondInfra;
    }

}
