/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class HoraPrgEjecDTO implements Serializable {

    @Column(name = "fecha_inicio")
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    private Date fechaFin;
    @Column(name = "nombre_uf")
    private String nombreUf;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "codigo_tm")
    private Integer codigoTm;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "horas_programadas")
    private String horasProgramadas;
    @Column(name = "horas_reales")
    private String horasReales;
    @Column(name = "dias_sin_operar")
    private Long diasSinOperar;

    public HoraPrgEjecDTO(Date fechaInicio, Date fechaFin, String nombreUf, String identificacion,
            Integer codigoTm, String nombres, String apellidos, String horasProgramadas, String horasReales, Long diasSinOperar) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombreUf = nombreUf;
        this.identificacion = identificacion;
        this.codigoTm = codigoTm;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.horasProgramadas = horasProgramadas;
        this.horasReales = horasReales;
        this.diasSinOperar = diasSinOperar;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombreUf() {
        return nombreUf;
    }

    public void setNombreUf(String nombreUf) {
        this.nombreUf = nombreUf;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Integer getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(Integer codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getHorasProgramadas() {
        return horasProgramadas;
    }

    public void setHorasProgramadas(String horasProgramadas) {
        this.horasProgramadas = horasProgramadas;
    }

    public String getHorasReales() {
        return horasReales;
    }

    public void setHorasReales(String horasReales) {
        this.horasReales = horasReales;
    }

    public Long getDiasSinOperar() {
        return diasSinOperar;
    }

    public void setDiasSinOperar(Long diasSinOperar) {
        this.diasSinOperar = diasSinOperar;
    }

}
