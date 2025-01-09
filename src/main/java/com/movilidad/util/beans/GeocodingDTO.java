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
public class GeocodingDTO {

    private Float latitud;
    private Float longitud;
    private String direccion;

    public GeocodingDTO() {
    }

    public GeocodingDTO(String direccion) {
        this.direccion = direccion;
    }

    public GeocodingDTO(Float latitud, Float longitud, String direccion) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
