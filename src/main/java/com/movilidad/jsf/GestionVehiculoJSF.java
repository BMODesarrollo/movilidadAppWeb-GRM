/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GestionVehiculoFacadeLocal;
import com.movilidad.ejb.GestionVehiculoTipoEstadoFacadeLocal;
import com.movilidad.ejb.GestionVehiculoUbicacionFacadeLocal;
import com.movilidad.model.GestionVehiculo;
import com.movilidad.model.GestionVehiculoTipoEstado;
import com.movilidad.model.GestionVehiculoUbicacion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author jjunco
 */
@Named(value = "gestionVehiculoJSF")
@ViewScoped
public class GestionVehiculoJSF implements Serializable {

    @EJB
    private GestionVehiculoFacadeLocal gestionVehiculoFacadeLocal;

    @EJB
    private GestionVehiculoTipoEstadoFacadeLocal gestionVehiculoTipoEstadoFacadeLocal;

    @EJB
    private GestionVehiculoUbicacionFacadeLocal gestionVehiculoUbicacionFacadeLocal;

    private GestionVehiculo gestionVehiculo;

    private List<GestionVehiculo> listGestionVehiculo;

    private List<GestionVehiculoTipoEstado> listGestionVehiculoTipoEstado;

    private List<GestionVehiculoUbicacion> listGestionVehiculoUbicacion;

    private int idGestionVehiculoTipoEstado;

    private int idGestionVehiculoUbicacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of GestionVehiculoJSF
     */
    public GestionVehiculoJSF() {
    }

    /**
     * Permite persistir la data del objeto GestionVehiculo en la base de datos
     */
    public void guardar() {
        try {
            if (gestionVehiculo != null) {
                gestionVehiculo.setCreado(new Date());
                gestionVehiculo.setModificado(new Date());
                gestionVehiculo.setEstadoReg(0);
                gestionVehiculo.setUsername(user.getUsername());
                gestionVehiculoFacadeLocal.create(gestionVehiculo);
                MovilidadUtil.addSuccessMessage("Se a registrado Gestion Vehiculo correctamente");
                gestionVehiculo = new GestionVehiculo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Gestion Vehiculo");
        }
    }

    /**
     * Permite realizar un update del objeto GestionVehiculo en la base de datos
     */
    public void actualizar() {
        try {
            if (gestionVehiculo != null) {

                gestionVehiculo.setModificado(new Date());
                gestionVehiculo.setIdGestionVehiculoEstado(gestionVehiculoTipoEstadoFacadeLocal.find(idGestionVehiculoTipoEstado));
                gestionVehiculo.setIdGestionVehiculoUbicacion(gestionVehiculoUbicacionFacadeLocal.find(idGestionVehiculoUbicacion));
                gestionVehiculoFacadeLocal.edit(gestionVehiculo);
                MovilidadUtil.addSuccessMessage("Se a actualizado el Gestion Vehiculo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Gestión Vehiculo");
        }
    }

    public void prepareGuardar() {
        gestionVehiculo = new GestionVehiculo();
        idGestionVehiculoTipoEstado = gestionVehiculo.getIdGestionVehiculoEstado().getIdGestionVehiculoTipoEstado();
    }

    public void reset() {
        gestionVehiculo = null;
    }

    /**
     * Permite capturar el objeto GestionVehiculo seleccionado por el usuario
     *
     * @param event Evento que captura el objeto GestionVehiculo
     */
    public void onGetGestionVehiculo(GestionVehiculo event) {
        gestionVehiculo = event;
    }

    public GestionVehiculo getGestionVehiculo() {
        return gestionVehiculo;
    }

    public void setGestionVehiculo(GestionVehiculo gestionVehiculo) {
        this.gestionVehiculo = gestionVehiculo;
    }

    public List<GestionVehiculo> getListGestionVehiculo() {
        return listGestionVehiculo = gestionVehiculoFacadeLocal.findAllEstadoReg();
    }

    public void setListGestionVehiculo(List<GestionVehiculo> listGestionVehiculo) {
        this.listGestionVehiculo = listGestionVehiculo;
    }

    public List<GestionVehiculoTipoEstado> getListGestionVehiculoTipoEstado() {
        return listGestionVehiculoTipoEstado = gestionVehiculoTipoEstadoFacadeLocal.findAll();
    }

    public void setListGestionVehiculoTipoEstado(List<GestionVehiculoTipoEstado> listGestionVehiculoTipoEstado) {
        this.listGestionVehiculoTipoEstado = listGestionVehiculoTipoEstado;
    }

    public List<GestionVehiculoUbicacion> getListGestionVehiculoUbicacion() {
        return listGestionVehiculoUbicacion = gestionVehiculoUbicacionFacadeLocal.findAll();
    }

    public void setListGestionVehiculoUbicacion(List<GestionVehiculoUbicacion> listGestionVehiculoUbicacion) {
        this.listGestionVehiculoUbicacion = listGestionVehiculoUbicacion;
    }

    public int getIdGestionVehiculoTipoEstado() {
        return idGestionVehiculoTipoEstado;
    }

    public void setIdGestionVehiculoTipoEstado(int idGestionVehiculoTipoEstado) {
        this.idGestionVehiculoTipoEstado = idGestionVehiculoTipoEstado;
    }

    public int getIdGestionVehiculoUbicacion() {
        return idGestionVehiculoUbicacion;
    }

    public void setIdGestionVehiculoUbicacion(int idGestionVehiculoUbicacion) {
        this.idGestionVehiculoUbicacion = idGestionVehiculoUbicacion;
    }

}
