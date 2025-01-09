/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Julián Arévalo
 */
public class PdReporteNovedadesDTO implements Serializable {

    @Column(name = "id_pd_maestro")
    private Integer idPdMaestro;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "identificacion")
    private BigInteger identificacion;
    @Column(name = "codigo_tm")
    private String codigoTm; 
    @Column(name = "nombre")
    private String nombre;           
    @Column(name = "procede")
    private String procede;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "fecha_pd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPd;

    public PdReporteNovedadesDTO(Integer idPdMaestro, Date fecha, String tipo, String detalle, BigInteger identificacion, String codigoTm, String nombre, String procede, String observacion, Date fechaPd) {
        this.idPdMaestro = idPdMaestro;
        this.fecha = fecha;
        this.tipo = tipo;
        this.detalle = detalle;
        this.identificacion = identificacion;
        this.codigoTm = codigoTm;
        this.nombre = nombre;
        this.procede = procede;
        this.observacion = observacion;
        this.fechaPd = fechaPd;
    }
        

    public Integer getIdPdMaestro() {
        return idPdMaestro;
    }

    public void setIdPdMaestro(Integer idPdMaestro) {
        this.idPdMaestro = idPdMaestro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    } 

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigInteger getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(BigInteger identificacion) {
        this.identificacion = identificacion;
    }

    public String getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(String codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProcede() {
        return procede;
    }

    public void setProcede(String procede) {
        this.procede = procede;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaPd() {
        return fechaPd;
    }

    public void setFechaPd(Date fechaPd) {
        this.fechaPd = fechaPd;
    }
    
}
