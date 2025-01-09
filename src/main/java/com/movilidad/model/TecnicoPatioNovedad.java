/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.model;

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
@Table(name = "tp_novedad")
public class TecnicoPatioNovedad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_novedad")
    private int idNovedad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @Column(name = "username")
    private String username;
    @JoinColumn(name = "id_prg_tc", referencedColumnName = "id_prg_tc")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgTc prgTc;
    @JoinColumn(name = "id_novedad_tipo", referencedColumnName = "id_novedad_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private TecnicoPatioNovedadTipo tpNovedadTipo;

    public TecnicoPatioNovedad(int idNovedad, Date creado, int estadoReg, String username, PrgTc prgTc, TecnicoPatioNovedadTipo tpNovedadTipo) {
        this.idNovedad = idNovedad;
        this.creado = creado;
        this.estadoReg = estadoReg;
        this.username = username;
        this.prgTc = prgTc;
        this.tpNovedadTipo = tpNovedadTipo;
    }

    public TecnicoPatioNovedad() {
    }

    public int getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(int idNovedad) {
        this.idNovedad = idNovedad;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
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

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public TecnicoPatioNovedadTipo getTpNovedadTipo() {
        return tpNovedadTipo;
    }

    public void setTpNovedadTipo(TecnicoPatioNovedadTipo tpNovedadTipo) {
        this.tpNovedadTipo = tpNovedadTipo;
    }

    

    

}
