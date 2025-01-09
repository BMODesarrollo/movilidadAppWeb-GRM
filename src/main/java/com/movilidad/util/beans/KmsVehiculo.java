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
public class KmsVehiculo implements Serializable {

    private Date fecha;
    private String codigo_vehiculo;
    private String estado;
    private BigDecimal total_km;
    private BigDecimal total_mts;
    private BigDecimal comercial;
    private BigDecimal hlp_prg;
    private BigDecimal adicionales;
    private BigDecimal vaccom;
    private BigDecimal comercial_Eliminados;
    private BigDecimal hlp_Eliminados;
    private BigDecimal vac;

    public KmsVehiculo(Date fecha, String codigo_vehiculo, BigDecimal total_km, BigDecimal total_mts, BigDecimal comercial, BigDecimal hlp_prg, BigDecimal adicionales, BigDecimal vaccom, BigDecimal comercial_Eliminados, BigDecimal hlp_Eliminados, BigDecimal vac) {
        this.fecha = fecha;
        this.codigo_vehiculo = codigo_vehiculo;
        this.total_km = total_km;
        this.total_mts = total_mts;
        this.comercial = comercial;
        this.hlp_prg = hlp_prg;
        this.adicionales = adicionales;
        this.vaccom = vaccom;
        this.comercial_Eliminados = comercial_Eliminados;
        this.hlp_Eliminados = hlp_Eliminados;
        this.vac = vac;
    }

    public KmsVehiculo(Date fecha, String codigo_vehiculo, BigDecimal total_km, String estado) {
        this.fecha = fecha;
        this.codigo_vehiculo = codigo_vehiculo;
        this.total_km = total_km;
        this.estado = estado;
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

    public BigDecimal getTotal_mts() {
        return total_mts;
    }

    public void setTotal_mts(BigDecimal total_mts) {
        this.total_mts = total_mts;
    }

    public BigDecimal getComercial() {
        return comercial;
    }

    public void setComercial(BigDecimal comercial) {
        this.comercial = comercial;
    }

    public BigDecimal getHlp_prg() {
        return hlp_prg;
    }

    public void setHlp_prg(BigDecimal hlp_prg) {
        this.hlp_prg = hlp_prg;
    }

    public BigDecimal getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(BigDecimal adicionales) {
        this.adicionales = adicionales;
    }

    public BigDecimal getVaccom() {
        return vaccom;
    }

    public void setVaccom(BigDecimal vaccom) {
        this.vaccom = vaccom;
    }

    public BigDecimal getComercial_Eliminados() {
        return comercial_Eliminados;
    }

    public void setComercial_Eliminados(BigDecimal comercial_Eliminados) {
        this.comercial_Eliminados = comercial_Eliminados;
    }

    public BigDecimal getHlp_Eliminados() {
        return hlp_Eliminados;
    }

    public void setHlp_Eliminados(BigDecimal hlp_Eliminados) {
        this.hlp_Eliminados = hlp_Eliminados;
    }

    public BigDecimal getVac() {
        return vac;
    }

    public void setVac(BigDecimal vac) {
        this.vac = vac;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
