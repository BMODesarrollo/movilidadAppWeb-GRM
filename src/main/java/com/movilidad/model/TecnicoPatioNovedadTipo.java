/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author julian.arevalo
 */
@Entity
@Table(name = "tp_novedad_tipo")
public class TecnicoPatioNovedadTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_novedad_tipo")
    private int idNovedadTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @Column(name = "username")
    private String username;

    public TecnicoPatioNovedadTipo(int idNovedadTipo, Date creado, String nombre, int estadoReg, String username) {
        this.idNovedadTipo = idNovedadTipo;
        this.creado = creado;
        this.nombre = nombre;
        this.estadoReg = estadoReg;
        this.username = username;
    }

    public TecnicoPatioNovedadTipo(int idNovedadTipo) {
        this.idNovedadTipo = idNovedadTipo;
    }

    public TecnicoPatioNovedadTipo() {
    }

    public int getIdNovedadTipo() {
        return idNovedadTipo;
    }

    public void setIdNovedadTipo(int idNovedadTipo) {
        this.idNovedadTipo = idNovedadTipo;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
