package com.movilidad.util.beans;

import com.movilidad.model.Hallazgo;

/**
 *
 * @author Carlos Ballestas
 * @func Objeto para carga de hallazgos a excel
 */
public class HallazgoExcel {

    private Hallazgo hallazgo;
    private int diasRestantes;

    public HallazgoExcel() {
    }

    public Hallazgo getHallazgo() {
        return hallazgo;
    }

    public void setHallazgo(Hallazgo hallazgo) {
        this.hallazgo = hallazgo;
    }

    public int getDiasRestantes() {
        return diasRestantes;
    }

    public void setDiasRestantes(int diasRestantes) {
        this.diasRestantes = diasRestantes;
    }

}
