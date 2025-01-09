/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;

/**
 *
 * @author soluciones-it
 */
public class VehiculoChangeDTO implements Serializable {

    private String newVehiculo;
    private String observacion;

    public VehiculoChangeDTO() {
    }

    public VehiculoChangeDTO(String newVehiculo, String observacion) {
        this.newVehiculo = newVehiculo;
        this.observacion = observacion;
    }

    public String getNewVehiculo() {
        return newVehiculo;
    }

    public void setNewVehiculo(String newVehiculo) {
        this.newVehiculo = newVehiculo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}
