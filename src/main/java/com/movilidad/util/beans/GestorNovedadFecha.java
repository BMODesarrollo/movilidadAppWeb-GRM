package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Carlos Ballestas
 */
public class GestorNovedadFecha implements Serializable {

    private Date fecha;
    private String clase;

    public GestorNovedadFecha() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

}
