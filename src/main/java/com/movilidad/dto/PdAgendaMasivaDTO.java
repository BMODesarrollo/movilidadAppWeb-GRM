/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Julián Arévalo
 */
public class PdAgendaMasivaDTO implements Serializable {

    @Column(name = "id_empleado")
    private Integer idEmpleado;
    @Column(name = "fecha_citacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCitacion;
    @Column(name = "id_uf")
    private int idUf;
    @Column(name = "codigo_tm")
    private String codigoTm;
    @Column(name = "nombre_completo")
    private String nombreCompleto;

    public PdAgendaMasivaDTO(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    public PdAgendaMasivaDTO(Integer idEmpleado, Date fechaCitacion, int idUf, String codigoTm, String nombreCompleto) {
        this.idEmpleado = idEmpleado;
        this.fechaCitacion = fechaCitacion;
        this.idUf = idUf;
        this.codigoTm = codigoTm;
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaCitacion() {
        return fechaCitacion;
    }

    public void setFechaCitacion(Date fechaCitacion) {
        this.fechaCitacion = fechaCitacion;
    }

    public int getIdUf() {
        return idUf;
    }

    public void setIdUf(int idUf) {
        this.idUf = idUf;
    }

    public String getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(String codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
        
}
