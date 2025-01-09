/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.model;

/**
 *
 * @author julian.arevalo
 */
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dano_flota_param_severidad")
public class DanoFlotaParamSeveridad implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_param_severidad")
    @Basic(optional = false)
    private Integer idParamSeveridad;
    
    @Column(name = "nivel")
    private Integer nivel;
    
    @Column(name = "valor")
    private Double valor;
    
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    private Date creado;

    @Column(name = "estado_reg")
    private Integer estadoReg;

    @Size(max = 15)
    @Column(name = "username")
    private String username;

    public DanoFlotaParamSeveridad() {
    }

    public Integer getIdParamSeveridad() {
        return idParamSeveridad;
    }

    public void setIdParamSeveridad(Integer idParamSeveridad) {
        this.idParamSeveridad = idParamSeveridad;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

