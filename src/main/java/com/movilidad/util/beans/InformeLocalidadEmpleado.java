/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@XmlRootElement
public class InformeLocalidadEmpleado implements Serializable {

    @Column(name = "localidad")
    private String localidad;
    @Column(name = "total")
    private Float total;

    public InformeLocalidadEmpleado() {
    }

    public InformeLocalidadEmpleado(String localidad, Float total) {
        this.localidad = localidad;
        this.total = total;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

}
