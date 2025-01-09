/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.Vehiculo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class VehiculosKmPatio implements Serializable, Comparable<VehiculosKmPatio> {

    private Date fecha;
    private Vehiculo idVehiculo;
    private BigDecimal kmPorRecorrer;
    private PrgStopPoint patio;

    public VehiculosKmPatio() {
    }

    public Date getFecha() {
        return fecha;
    }

    public VehiculosKmPatio(Date fecha, Vehiculo idVehiculo, BigDecimal kmPorRecorrer, PrgStopPoint patio) {
        this.fecha = fecha;
        this.idVehiculo = idVehiculo;
        this.kmPorRecorrer = kmPorRecorrer;
        this.patio = patio;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public BigDecimal getKmPorRecorrer() {
        return kmPorRecorrer;
    }

    public void setKmPorRecorrer(BigDecimal kmPorRecorrer) {
        this.kmPorRecorrer = kmPorRecorrer;
    }

    public PrgStopPoint getPatio() {
        return patio;
    }

    public void setPatio(PrgStopPoint patio) {
        this.patio = patio;
    }

    @Override
    public int compareTo(VehiculosKmPatio e) {
        return e.getKmPorRecorrer().compareTo(kmPorRecorrer);
    }
}
