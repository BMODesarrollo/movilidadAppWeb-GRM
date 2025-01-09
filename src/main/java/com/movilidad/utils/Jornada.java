package com.movilidad.utils;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Carlos Ballestas
 */
public class Jornada implements Serializable {

    private Date fecha;
    private String cedula;
    private String nombre;
    private String tipo_jornada;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo_jornada() {
        return tipo_jornada;
    }

    public void setTipo_jornada(String tipo_jornada) {
        this.tipo_jornada = tipo_jornada;
    }

}
