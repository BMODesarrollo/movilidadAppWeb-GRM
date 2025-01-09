/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ContableCtaVehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.ContableCtaTipo;
import com.movilidad.model.ContableCtaVehiculo;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite gestionar toda los datos para el objeto ContableCtaVehiculoVehiculo
 * principal tabla afectada contable_cta_vehiculo
 *
 * @author cesar
 */
@Named(value = "contableCtaVehiculoJSF")
@ViewScoped
public class ContableCtaVehiculoJSF implements Serializable {

    @EJB
    private ContableCtaVehiculoFacadeLocal contableCtaVehiculoFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;

    private ContableCtaVehiculo contableCtaVehiculo;
    private List<ContableCtaVehiculo> listContableCtaVehiculo;
    private List<Vehiculo> listVehiculo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNumCtaVehiculoAux;
    private Integer idContableCuentaTipo;
    private Integer idVehiculoAux;

    /**
     * Creates a new instance of ContableCtaVehiculoVehiculoJSF
     */
    public ContableCtaVehiculoJSF() {
    }

    @PostConstruct
    public void init() {
        cNumCtaVehiculoAux = "";
        idContableCuentaTipo = null;
        idVehiculoAux = null;
    }

    /**
     * Permite persistir la data del objeto ContableCtaVehiculo en la base de
     * datos
     */
    public void guardar() {
        try {
            if (contableCtaVehiculo != null) {
                if (idContableCuentaTipo == null) {
                    MovilidadUtil.addErrorMessage("Tipo de Cuenta es requerido");
                    return;
                }
                if (contableCtaVehiculo.getIdVehiculo() == null) {
                    MovilidadUtil.addErrorMessage("Vehículo es requerido");
                    return;
                }
//                if (validarNombre()) {
//                    MovilidadUtil.addErrorMessage("Número de cuenta no se encuentra disponible");
//                    return;
//                }
                if (validarVehiculo(contableCtaVehiculo.getIdVehiculo())) {
                    MovilidadUtil.addErrorMessage("Vehículo se encuentra asociado con Número de Cuenta");
                    return;
                }
                contableCtaVehiculo.setIdContableCtaTipo(new ContableCtaTipo(idContableCuentaTipo));
                contableCtaVehiculo.setCreado(new Date());
                contableCtaVehiculo.setModificado(new Date());
                contableCtaVehiculo.setEstadoReg(0);
                contableCtaVehiculo.setUsername(user.getUsername());
                contableCtaVehiculoFacadeLocal.create(contableCtaVehiculo);
                MovilidadUtil.addSuccessMessage("Se a registrado correctamente");
                contableCtaVehiculo = new ContableCtaVehiculo();
                idContableCuentaTipo = null;
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar");
        }
    }

    /**
     * Permite realizar un update del objeto ContableCtaVehiculo en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (contableCtaVehiculo != null) {
                if (idContableCuentaTipo == null) {
                    MovilidadUtil.addErrorMessage("Tipo de Cuenta es requerido");
                    return;
                }
                if (contableCtaVehiculo.getIdVehiculo() == null) {
                    MovilidadUtil.addErrorMessage("Vehículo es requerido");
                    return;
                }
//                if (!cNumCtaVehiculoAux.equals(contableCtaVehiculo.getNroCta())) {
//                    if (validarNombre()) {
//                        MovilidadUtil.addErrorMessage("Número de cuenta no se encuentra disponible");
//                        return;
//                    }
//                }
                if (!idVehiculoAux.equals(contableCtaVehiculo.getIdVehiculo().getIdVehiculo())) {
                    if (validarVehiculo(contableCtaVehiculo.getIdVehiculo())) {
                        MovilidadUtil.addErrorMessage("Vehículo se encuentra asociado con Número de Cuenta");
                        return;
                    }
                }
                contableCtaVehiculo.setIdContableCtaTipo(new ContableCtaTipo(idContableCuentaTipo));
                contableCtaVehiculo.setModificado(new Date());
                contableCtaVehiculoFacadeLocal.edit(contableCtaVehiculo);
                MovilidadUtil.addSuccessMessage("Se a actualizado correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar");
        }
    }

    /**
     * Permite crear la instancia del objeto ContableCtaVehiculo
     */
    public void prepareGuardar() {
        contableCtaVehiculo = new ContableCtaVehiculo();
    }

    /**
     * Permite cargar el objeto ListVehiculo
     */
    public void cargarListVeh() {
        listVehiculo = vehiculoFacadeLocal.getVehiclosActivo();
    }

    public void reset() {
        contableCtaVehiculo = null;
        cNumCtaVehiculoAux = "";
        listVehiculo = null;
        idContableCuentaTipo = null;
        idVehiculoAux = null;
    }

    /**
     * Permite capturar el objeto ContableCtaVehiculo seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto ContableCtaVehiculo
     */
    public void onContableCtaVehiculo(ContableCtaVehiculo event) {
        contableCtaVehiculo = event;
        cNumCtaVehiculoAux = event.getNroCta();
        idContableCuentaTipo = event.getIdContableCtaTipo().getIdContableCtaTipo();
        idVehiculoAux = event.getIdVehiculo().getIdVehiculo();
    }

    /**
     * Permite capturar el objeto Vehiculo seleccionado por el usuario
     *
     * @param event Evento que captura el objeto Vehiculo
     */
    public void onVehiculo(Vehiculo event) {
        contableCtaVehiculo.setIdVehiculo(event);
        PrimeFaces.current().executeScript("PF('vehDlg').hide();");
    }

    /**
     * Permite validar si el valor asignado al atributo NroCta se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<ContableCtaVehiculo> findAllEstadoReg = contableCtaVehiculoFacadeLocal.findAllEstadoReg();
        for (ContableCtaVehiculo cta : findAllEstadoReg) {
            if (cta.getNroCta().equals(contableCtaVehiculo.getNroCta())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permite validar si un objeto Vehiculo se encuentra relacionado con una
     * NroCta existente
     *
     * @return false si el objeto Vehiculo no se encuentra
     */
    boolean validarVehiculo(Vehiculo v) {
        List<ContableCtaVehiculo> findAllEstadoReg = contableCtaVehiculoFacadeLocal.findAllEstadoReg();
        for (ContableCtaVehiculo cta : findAllEstadoReg) {
            if (cta.getIdVehiculo().getIdVehiculo().equals(v.getIdVehiculo())) {
                return true;
            }
        }
        return false;
    }

    public ContableCtaVehiculo getContableCtaVehiculo() {
        return contableCtaVehiculo;
    }

    public void setContableCtaVehiculo(ContableCtaVehiculo contableCtaVehiculo) {
        this.contableCtaVehiculo = contableCtaVehiculo;
    }

    public List<ContableCtaVehiculo> getListContableCtaVehiculo() {
        listContableCtaVehiculo = contableCtaVehiculoFacadeLocal.findAllEstadoReg();
        return listContableCtaVehiculo;
    }

    public void setListContableCtaVehiculo(List<ContableCtaVehiculo> listContableCtaVehiculo) {
        this.listContableCtaVehiculo = listContableCtaVehiculo;
    }

    public List<Vehiculo> getListVehiculo() {
        return listVehiculo;
    }

    public void setListVehiculo(List<Vehiculo> listVehiculo) {
        this.listVehiculo = listVehiculo;
    }

    public Integer getIdContableCuentaTipo() {
        return idContableCuentaTipo;
    }

    public void setIdContableCuentaTipo(Integer idContableCuentaTipo) {
        this.idContableCuentaTipo = idContableCuentaTipo;
    }

}
