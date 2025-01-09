/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_eps")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccEps.findAll", query = "SELECT a FROM AccEps a")
    , @NamedQuery(name = "AccEps.findByIdAccEps", query = "SELECT a FROM AccEps a WHERE a.idAccEps = :idAccEps")
    , @NamedQuery(name = "AccEps.findByEps", query = "SELECT a FROM AccEps a WHERE a.eps = :eps")
    , @NamedQuery(name = "AccEps.findByUsername", query = "SELECT a FROM AccEps a WHERE a.username = :username")
    , @NamedQuery(name = "AccEps.findByCreado", query = "SELECT a FROM AccEps a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccEps.findByModificado", query = "SELECT a FROM AccEps a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccEps.findByEstadoReg", query = "SELECT a FROM AccEps a WHERE a.estadoReg = :estadoReg")})
public class AccEps implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_eps")
    private Integer idAccEps;
    @Size(max = 60)
    @Column(name = "eps")
    private String eps;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @OneToMany(mappedBy = "idAccEps", fetch = FetchType.LAZY)
    private List<AccidenteVictima> accidenteVictimaList;

    public AccEps() {
    }

    public AccEps(Integer idAccEps) {
        this.idAccEps = idAccEps;
    }

    public Integer getIdAccEps() {
        return idAccEps;
    }

    public void setIdAccEps(Integer idAccEps) {
        this.idAccEps = idAccEps;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    @XmlTransient
    public List<AccidenteVictima> getAccidenteVictimaList() {
        return accidenteVictimaList;
    }

    public void setAccidenteVictimaList(List<AccidenteVictima> accidenteVictimaList) {
        this.accidenteVictimaList = accidenteVictimaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccEps != null ? idAccEps.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccEps)) {
            return false;
        }
        AccEps other = (AccEps) object;
        if ((this.idAccEps == null && other.idAccEps != null) || (this.idAccEps != null && !this.idAccEps.equals(other.idAccEps))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccEps[ idAccEps=" + idAccEps + " ]";
    }
    
}
