/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@XmlRootElement
public class ServbusIdTipoVehiculo {

    @Column(name = "servbus")
    private String servbus;
    @Column(name = "id_vehiculo_tipo")
    private Integer idTipoVehiculo;

    public ServbusIdTipoVehiculo(String servbus, Integer idTipoVehiculo) {
        this.servbus = servbus;
        this.idTipoVehiculo = idTipoVehiculo;
    }

    public ServbusIdTipoVehiculo() {
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public Integer getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(Integer idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }

}
