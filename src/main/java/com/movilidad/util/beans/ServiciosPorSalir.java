/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;

/**
 *
 * @author Jesús Jiménez
 */
public class ServiciosPorSalir implements Serializable {

    private String codigoVehiculo;
    private String placaVehiculo;
    private String servbus;
    private String tarea;
    private String timeOrigin;

    public ServiciosPorSalir(String codigoVehiculo, String servbus, String tarea, String timeOrigin, String placaVehiculo) {
        this.codigoVehiculo = codigoVehiculo;
        this.servbus = servbus;
        this.tarea = tarea;
        this.timeOrigin = timeOrigin;
        this.placaVehiculo = placaVehiculo;
    }

    public String getCodigoVehiculo() {
        return codigoVehiculo;
    }

    public void setCodigoVehiculo(String codigoVehiculo) {
        this.codigoVehiculo = codigoVehiculo;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

}
