/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccCondOrganizacionFacadeLocal;
import com.movilidad.model.AccCondOrganizacion;
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
 * Permite parametrizar la data relacionada con los objetos AccCondOranizacion Principal
 * tabla afectada acc_cond_organizacion
 *
 * @author HP
 */
@Named(value = "accCondOrganizacionJSF")
@ViewScoped
public class AccCondOrganizacionJSF implements Serializable {

    @EJB
    private AccCondOrganizacionFacadeLocal accCondOrganizacionFacadeLocal;

    private AccCondOrganizacion accCondOrganizacion;

    private List<AccCondOrganizacion> listAccCondOrganizacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccCondOrganizacionJSF() {
    }

    /**
     * Permite persistir la data del objeto AccCondOranizacion en la base de datos
     */
    public void guardar() {
        try {
            if (accCondOrganizacion != null) {
                accCondOrganizacion.setCondOrganizacion(accCondOrganizacion.getCondOrganizacion().toUpperCase());
                accCondOrganizacion.setCreado(new Date());
                accCondOrganizacion.setModificado(new Date());
                accCondOrganizacion.setEstadoReg(0);
                accCondOrganizacion.setUsername(user.getUsername());
                accCondOrganizacionFacadeLocal.create(accCondOrganizacion);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Condición de la Organización correctamente");
                accCondOrganizacion = new AccCondOrganizacion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Condición de la Organización");
        }
    }

    /**
     * Permite realizar un update del objeto AccCondOranizacion en la base de datos
     */
    public void actualizar() {
        try {
            if (accCondOrganizacion != null) {
                accCondOrganizacion.setCondOrganizacion(accCondOrganizacion.getCondOrganizacion().toUpperCase());
                accCondOrganizacionFacadeLocal.edit(accCondOrganizacion);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Condición de la Organización correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('cond-organizacion-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Condición de la Organización");
        }
    }

    /**
     * Permite crear la instancia del objeto AccCondOranizacion
     */
    public void prepareGuardar() {
        accCondOrganizacion = new AccCondOrganizacion();
    }

    public void reset() {
        accCondOrganizacion = null;
    }

    /**
     *
     * @param event Objeto AccCondOranizacion seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accCondOrganizacion = (AccCondOrganizacion) event.getObject();
    }

    public AccCondOrganizacion getAccCondOrganizacion() {
        return accCondOrganizacion;
    }

    public void setAccCondOrganizacion(AccCondOrganizacion accCondOrganizacion) {
        this.accCondOrganizacion = accCondOrganizacion;
    }

    public List<AccCondOrganizacion> getListAccCondOrganizacion() {
        listAccCondOrganizacion = accCondOrganizacionFacadeLocal.estadoReg();
        return listAccCondOrganizacion;
    }

}
