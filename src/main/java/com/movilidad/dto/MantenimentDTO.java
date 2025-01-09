/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

/**
 *
 * @author solucionesit
 */
public class MantenimentDTO {

    private String nombreSistema;
    private VehicleDTO vehiculo;
    private Integer diasInoperativos;

    public MantenimentDTO() {
    }

    public String getNombreSistema() {
        return nombreSistema;
    }

    public void setNombreSistema(String nombreSistema) {
        this.nombreSistema = nombreSistema;
    }

    public VehicleDTO getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(VehicleDTO vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Integer getDiasInoperativos() {
        return diasInoperativos;
    }

    public void setDiasInoperativos(Integer diasInoperativos) {
        this.diasInoperativos = diasInoperativos;
    }

}
