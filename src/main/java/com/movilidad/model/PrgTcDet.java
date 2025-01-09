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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "prg_tc_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgTcDet.findAll", query = "SELECT p FROM PrgTcDet p")
    , @NamedQuery(name = "PrgTcDet.findByIdPrgTcDet", query = "SELECT p FROM PrgTcDet p WHERE p.idPrgTcDet = :idPrgTcDet")
    , @NamedQuery(name = "PrgTcDet.findByEjecutado", query = "SELECT p FROM PrgTcDet p WHERE p.ejecutado = :ejecutado")
    , @NamedQuery(name = "PrgTcDet.findByUsername", query = "SELECT p FROM PrgTcDet p WHERE p.username = :username")
    , @NamedQuery(name = "PrgTcDet.findByCreado", query = "SELECT p FROM PrgTcDet p WHERE p.creado = :creado")
    , @NamedQuery(name = "PrgTcDet.findByModificado", query = "SELECT p FROM PrgTcDet p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PrgTcDet.findByEstadoReg", query = "SELECT p FROM PrgTcDet p WHERE p.estadoReg = :estadoReg")})
public class PrgTcDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_tc_det")
    private Integer idPrgTcDet;
    @Column(name = "ejecutado")
    private Integer ejecutado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @JoinColumn(name = "id_prg_pattern", referencedColumnName = "id_prg_pattern")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgPattern idPrgPattern;
    @JoinColumn(name = "id_prg_tc", referencedColumnName = "id_prg_tc")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgTc idPrgTc;

    public PrgTcDet() {
    }

    public PrgTcDet(Integer idPrgTcDet) {
        this.idPrgTcDet = idPrgTcDet;
    }

    public PrgTcDet(Integer idPrgTcDet, String username, Date creado, int estadoReg) {
        this.idPrgTcDet = idPrgTcDet;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgTcDet() {
        return idPrgTcDet;
    }

    public void setIdPrgTcDet(Integer idPrgTcDet) {
        this.idPrgTcDet = idPrgTcDet;
    }

    public Integer getEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(Integer ejecutado) {
        this.ejecutado = ejecutado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    public PrgPattern getIdPrgPattern() {
        return idPrgPattern;
    }

    public void setIdPrgPattern(PrgPattern idPrgPattern) {
        this.idPrgPattern = idPrgPattern;
    }

    public PrgTc getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(PrgTc idPrgTc) {
        this.idPrgTc = idPrgTc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgTcDet != null ? idPrgTcDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgTcDet)) {
            return false;
        }
        PrgTcDet other = (PrgTcDet) object;
        if ((this.idPrgTcDet == null && other.idPrgTcDet != null) || (this.idPrgTcDet != null && !this.idPrgTcDet.equals(other.idPrgTcDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgTcDet[ idPrgTcDet=" + idPrgTcDet + " ]";
    }
    
}
