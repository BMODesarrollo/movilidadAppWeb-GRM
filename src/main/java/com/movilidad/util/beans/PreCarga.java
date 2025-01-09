/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.model.MttoTarea;
import com.movilidad.model.Vehiculo;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author César Mercado
 */
public class PreCarga implements Serializable {

    private Date fecha;
    private String servbus;
    private Vehiculo vehiculo;
    private String timeOrigin;
    private String timeDestiny;
    private MttoTarea mttoTarea;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getTimeDestiny() {
        return timeDestiny;
    }

    public void setTimeDestiny(String timeDestiny) {
        this.timeDestiny = timeDestiny;
    }

    public MttoTarea getMttoTarea() {
        return mttoTarea;
    }

    public void setMttoTarea(MttoTarea mttoTarea) {
        this.mttoTarea = mttoTarea;
    }

}
