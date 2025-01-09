/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.movilidad.utils.Util;
import java.util.Date;

/**
 *
 * @author soluciones-it
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentStatusVehicleGEO {

    private boolean cargando;
    private boolean patio;
    private Float consumoEnergia;
    private String fechaHoraLecturaDato;
    private String idVehiculo;
    private LocationVehicleGEO localizacionVehiculo;
    private Float nivelRestanteEnergia;
    private Float regeneracionEnergia;

    public CurrentStatusVehicleGEO() {
    }

    public CurrentStatusVehicleGEO(boolean cargando, boolean patio, Float consumoEnergia, String fechaHoraLecturaDato, String idVehiculo, LocationVehicleGEO localizacionVehiculo, Float nivelRestanteEnergia, Float regeneracionEnergia) {
        this.cargando = cargando;
        this.patio = patio;
        this.consumoEnergia = consumoEnergia;
        this.fechaHoraLecturaDato = fechaHoraLecturaDato;
        this.idVehiculo = idVehiculo;
        this.localizacionVehiculo = localizacionVehiculo;
        this.nivelRestanteEnergia = nivelRestanteEnergia;
        this.regeneracionEnergia = regeneracionEnergia;
    }

    public boolean isCargando() {
        return cargando;
    }

    public void setCargando(boolean cargando) {
        this.cargando = cargando;
    }

    public boolean isPatio() {
        return patio;
    }

    public void setPatio(boolean patio) {
        this.patio = patio;
    }

    public Float getConsumoEnergia() {
        return consumoEnergia;
    }

    public void setConsumoEnergia(Float consumoEnergia) {
        this.consumoEnergia = consumoEnergia;
    }

    public Date getFechaHoraLecturaDato() {
        return Util.toDateWS_DDMMYYHHMMSSZZZ(this.fechaHoraLecturaDato);
    }

    public void setFechaHoraLecturaDato(String fechaHoraLecturaDato) {
        this.fechaHoraLecturaDato = fechaHoraLecturaDato;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public LocationVehicleGEO getLocalizacionVehiculo() {
        return localizacionVehiculo;
    }

    public void setLocalizacionVehiculo(LocationVehicleGEO localizacionVehiculo) {
        this.localizacionVehiculo = localizacionVehiculo;
    }

    public Float getNivelRestanteEnergia() {
        return nivelRestanteEnergia;
    }

    public void setNivelRestanteEnergia(Float nivelRestanteEnergia) {
        this.nivelRestanteEnergia = nivelRestanteEnergia;
    }

    public Float getRegeneracionEnergia() {
        return regeneracionEnergia;
    }

    public void setRegeneracionEnergia(Float regeneracionEnergia) {
        this.regeneracionEnergia = regeneracionEnergia;
    }

}
