/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class DiasSinOperar implements Serializable {

    private String desde;
    private String hasta;
    private Date fecha;
    private String codigo_vehiculo;
    private String estado;
    private BigDecimal total_km;

    public DiasSinOperar(String desde, String hasta,Date fecha, String codigo_vehiculo, BigDecimal total_km, String estado) {
        this.desde = desde;
        this.hasta = hasta;
        this.fecha = fecha;
        this.codigo_vehiculo = codigo_vehiculo;
        this.total_km = total_km;
        this.estado = estado;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigo_vehiculo() {
        return codigo_vehiculo;
    }

    public void setCodigo_vehiculo(String codigo_vehiculo) {
        this.codigo_vehiculo = codigo_vehiculo;
    }

    public BigDecimal getTotal_km() {
        return total_km;
    }

    public void setTotal_km(BigDecimal total_km) {
        this.total_km = total_km;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
