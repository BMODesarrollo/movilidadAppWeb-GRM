/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.model.NovedadPrgTc;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author solucionesit
 */
public class NovedadPrgTcAux implements Serializable {

    private NovedadPrgTc obj;
    private BigDecimal kmPerdido;

    public NovedadPrgTcAux() {
    }

    public NovedadPrgTc getObj() {
        return obj;
    }

    public void setObj(NovedadPrgTc obj) {
        this.obj = obj;
    }

    public BigDecimal getKmPerdido() {
        return kmPerdido;
    }

    public void setKmPerdido(BigDecimal kmPerdido) {
        this.kmPerdido = kmPerdido;
    }
    
    

}
