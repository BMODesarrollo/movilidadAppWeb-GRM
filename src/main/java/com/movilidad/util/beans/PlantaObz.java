/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 * @func Reporte Planta Obz
 */
@XmlRootElement
public class PlantaObz implements Serializable {

    @Column(name = "id_empleado")
    private Integer idEmpleado;
    @Column(name = "codigo_empresa")
    private String codigoEmpresa;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "nombrecompleto")
    private String nombrecompleto;
    @Column(name = "fecha_contrato")
    private String fechaContrato;
    @Column(name = "descripcion_cargo")
    private String descripcionaCargo;
    @Column(name = "codigo_tm")
    private int codigoTm;
    @Column(name = "accidentes")
    private Long accidentes;
    @Column(name = "km_recorrido")
    private BigDecimal kmRecorrido;
    @Column(name = "capacitacion")
    private Integer capacitacion;
    @Column(name = "infracciones")
    private Integer infracciones;
    @Column(name = "pqr")
    private Integer pqr;
    @Column(name = "horas_programadas")
    private String horasProgramadas;
    @Column(name = "horas_reales")
    private String horasReales;

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(String fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public String getDescripcionaCargo() {
        return descripcionaCargo;
    }

    public void setDescripcionaCargo(String descripcionaCargo) {
        this.descripcionaCargo = descripcionaCargo;
    }

    public int getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(int codigoTm) {
        this.codigoTm = codigoTm;
    }

    public Long getAccidentes() {
        return accidentes;
    }

    public void setAccidentes(Long accidentes) {
        this.accidentes = accidentes;
    }

    public BigDecimal getKmRecorrido() {
        return kmRecorrido;
    }

    public void setKmRecorrido(BigDecimal kmRecorrido) {
        this.kmRecorrido = kmRecorrido;
    }

    public Integer getCapacitacion() {
        return capacitacion;
    }

    public void setCapacitacion(Integer capacitacion) {
        this.capacitacion = capacitacion;
    }

    public Integer getInfracciones() {
        return infracciones;
    }

    public void setInfracciones(Integer infracciones) {
        this.infracciones = infracciones;
    }

    public Integer getPqr() {
        return pqr;
    }

    public void setPqr(Integer pqr) {
        this.pqr = pqr;
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

    public PlantaObz(Integer idEmpleado, String codigoEmpresa, String identificacion, String nombres, String apellidos, String nombrecompleto, String fechaContrato, String descripcionaCargo, int codigoTm, Long accidentes, BigDecimal kmRecorrido, Integer capacitacion, Integer infracciones, Integer pqr, String horasProgramadas, String horasReales) {
        this.idEmpleado = idEmpleado;
        this.codigoEmpresa = codigoEmpresa;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombrecompleto = nombrecompleto;
        this.fechaContrato = fechaContrato;
        this.descripcionaCargo = descripcionaCargo;
        this.codigoTm = codigoTm;
        this.accidentes = accidentes;
        this.kmRecorrido = kmRecorrido;
        this.capacitacion = capacitacion;
        this.infracciones = infracciones;
        this.pqr = pqr;
        this.horasProgramadas = horasProgramadas;
        this.horasReales = horasReales;
    }

}
