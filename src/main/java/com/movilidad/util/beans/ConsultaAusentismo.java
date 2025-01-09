package com.movilidad.util.beans;

import com.movilidad.model.Novedad;
import java.io.Serializable;

/**
 *
 * @author Carlos Ballestas
 */
public class ConsultaAusentismo implements Serializable {

    private Novedad diaActual;
    private Novedad diaSiguiente;

    public ConsultaAusentismo(Novedad diaActual, Novedad diaSiguiente) {
        this.diaActual = diaActual;
        this.diaSiguiente = diaSiguiente;
    }

    public Novedad getDiaActual() {
        return diaActual;
    }

    public void setDiaActual(Novedad diaActual) {
        this.diaActual = diaActual;
    }

    public Novedad getDiaSiguiente() {
        return diaSiguiente;
    }

    public void setDiaSiguiente(Novedad diaSiguiente) {
        this.diaSiguiente = diaSiguiente;
    }

}
