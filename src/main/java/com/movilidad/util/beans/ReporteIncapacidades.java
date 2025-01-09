package com.movilidad.util.beans;

import com.movilidad.model.Novedad;

/**
 *
 * @author Carlos Ballestas
 */
public class ReporteIncapacidades {

    private Novedad novedad;
    private Integer dias;

    public ReporteIncapacidades() {
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

}
