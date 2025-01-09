/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class PrgTcNovedadMtto  extends Object implements Serializable {

    private PrgTc prgTc;
    private int salida;
    private int tipoVehiculo;

    public PrgTcNovedadMtto() {
    }

    public PrgTcNovedadMtto(PrgTc prgTc, int salida, int tipoVehiculo) {
        this.prgTc = prgTc;
        this.salida = salida;
        this.tipoVehiculo = tipoVehiculo;
    }
    
    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public int getSalida() {
        return salida;
    }

    public void setSalida(int salida) {
        this.salida = salida;
    }

    public int getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(int tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

}
