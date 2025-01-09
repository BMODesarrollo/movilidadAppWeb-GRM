/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

/**
 *
 * @author soluciones-it
 */
public class LocationVehicleGEO {

    private Float latitud;
    private Float longitud;

    public LocationVehicleGEO() {
    }

    public LocationVehicleGEO(Float latitud, Float longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

}
