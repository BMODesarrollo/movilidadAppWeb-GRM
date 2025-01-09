/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julián Arévalo
 */
@Entity
@Table(name = "reporte_semana_motivo_prg")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReporteSemanaMotivoPrg.findAll", query = "SELECT p FROM ReporteSemanaMotivoPrg p"),
    @NamedQuery(name = "ReporteSemanaMotivoPrg.findByUsername", query = "SELECT p FROM ReporteSemanaMotivoPrg p WHERE p.username = :username"),
    @NamedQuery(name = "ReporteSemanaMotivoPrg.findByCreado", query = "SELECT p FROM ReporteSemanaMotivoPrg p WHERE p.creado = :creado"),
    @NamedQuery(name = "ReporteSemanaMotivoPrg.findByModificado", query = "SELECT p FROM ReporteSemanaMotivoPrg p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "ReporteSemanaMotivoPrg.findByEstadoReg", query = "SELECT p FROM ReporteSemanaMotivoPrg p WHERE p.estadoReg = :estadoReg")})
public class ReporteSemanaMotivoPrg implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_motivo_prg") 
    private Integer idMotivoPrg;
    
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    private Date creado;
    
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    
    @Column(name = "estado_reg")
    private Integer estadoReg;
    
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    
    @JoinColumn(name = "id_motivo", referencedColumnName = "id_motivo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ReporteSemanaActualMotivo idMotivo;
    
    @JoinColumn(name = "id_prg_tc", referencedColumnName = "id_prg_tc")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PrgTc idPrgTc;

    public ReporteSemanaMotivoPrg() {
    }

    public ReporteSemanaMotivoPrg(Integer idMotivoPrg) {
        this.idMotivoPrg = idMotivoPrg;
    }

    public ReporteSemanaMotivoPrg(Integer idMotivoPrg, Date creado, Date modificado, Integer estadoReg, String username, ReporteSemanaActualMotivo idMotivo, PrgTc idPrgTc) {
        this.idMotivoPrg = idMotivoPrg;
        this.creado = creado;
        this.modificado = modificado;
        this.estadoReg = estadoReg;
        this.username = username;
        this.idMotivo = idMotivo;
        this.idPrgTc = idPrgTc;
    }

    public Integer getIdMotivoPrg() {
        return idMotivoPrg;
    }

    public void setIdMotivoPrg(Integer idMotivoPrg) {
        this.idMotivoPrg = idMotivoPrg;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
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

    public ReporteSemanaActualMotivo getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(ReporteSemanaActualMotivo idMotivo) {
        this.idMotivo = idMotivo;
    }

    public PrgTc getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(PrgTc idPrgTc) {
        this.idPrgTc = idPrgTc;
    }
    
}
