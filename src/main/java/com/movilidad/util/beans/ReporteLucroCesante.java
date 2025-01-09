/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 *
 * Costos de trámites y tiempos (costo del lucro cesante)
 *
 */
@XmlRootElement
public class ReporteLucroCesante implements Serializable {

    @Column(name = "id_empleado")
    private Integer idEmpleado;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "fecha_acc")
    private String fechaAcc;
    @Column(name = "codigo_tm")
    private int codigoTm;
    private String nombre;
    @Column(name = "fecha_salida_inmovilizado")
    private Date fechaSalidaInmovilizado;
    @Column(name = "dias_patio")
    private Long diasPatio;
    @Column(name = "costos_directos")
    private BigDecimal costosDirectos;
    @Column(name = "costos_indirectos")
    private BigDecimal costosIndirectos;

    public ReporteLucroCesante(Integer idEmpleado, String codigo, String fechaAcc, int codigoTm, String nombre, Date fechaSalidaInmovilizado, Long diasPatio, BigDecimal costosDirectos, BigDecimal costosIndirectos) {
        this.idEmpleado = idEmpleado;
        this.codigo = codigo;
        this.fechaAcc = fechaAcc;
        this.codigoTm = codigoTm;
        this.nombre = nombre;
        this.fechaSalidaInmovilizado = fechaSalidaInmovilizado;
        this.diasPatio = diasPatio;
        this.costosDirectos = costosDirectos;
        this.costosIndirectos = costosIndirectos;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFechaAcc() {
        return fechaAcc;
    }

    public void setFechaAcc(String fechaAcc) {
        this.fechaAcc = fechaAcc;
    }

    public int getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(int codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaSalidaInmovilizado() {
        return fechaSalidaInmovilizado;
    }

    public void setFechaSalidaInmovilizado(Date fechaSalidaInmovilizado) {
        this.fechaSalidaInmovilizado = fechaSalidaInmovilizado;
    }

    public Long getDiasPatio() {
        return diasPatio;
    }

    public void setDiasPatio(Long diasPatio) {
        this.diasPatio = diasPatio;
    }

    public BigDecimal getCostosDirectos() {
        return costosDirectos;
    }

    public void setCostosDirectos(BigDecimal costosDirectos) {
        this.costosDirectos = costosDirectos;
    }

    public BigDecimal getCostosIndirectos() {
        return costosIndirectos;
    }

    public void setCostosIndirectos(BigDecimal costosIndirectos) {
        this.costosIndirectos = costosIndirectos;
    }

}
