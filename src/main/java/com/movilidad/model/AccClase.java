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
@Table(name = "acc_clase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccClase.findAll", query = "SELECT a FROM AccClase a")
    , @NamedQuery(name = "AccClase.findByIdAccClase", query = "SELECT a FROM AccClase a WHERE a.idAccClase = :idAccClase")
    , @NamedQuery(name = "AccClase.findByClase", query = "SELECT a FROM AccClase a WHERE a.clase = :clase")
    , @NamedQuery(name = "AccClase.findByUsername", query = "SELECT a FROM AccClase a WHERE a.username = :username")
    , @NamedQuery(name = "AccClase.findByCreado", query = "SELECT a FROM AccClase a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccClase.findByModificado", query = "SELECT a FROM AccClase a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccClase.findByEstadoReg", query = "SELECT a FROM AccClase a WHERE a.estadoReg = :estadoReg")})
public class AccClase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_clase")
    private Integer idAccClase;
    @Size(max = 60)
    @Column(name = "clase")
    private String clase;
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
    @OneToMany(mappedBy = "idAccClase", fetch = FetchType.LAZY)
    private List<AccInformeOpe> accInformeOpeList;
    @OneToMany(mappedBy = "idClase", fetch = FetchType.LAZY)
    private List<Accidente> accidenteList;

    public AccClase() {
    }

    public AccClase(Integer idAccClase) {
        this.idAccClase = idAccClase;
    }

    public Integer getIdAccClase() {
        return idAccClase;
    }

    public void setIdAccClase(Integer idAccClase) {
        this.idAccClase = idAccClase;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
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
    public List<AccInformeOpe> getAccInformeOpeList() {
        return accInformeOpeList;
    }

    public void setAccInformeOpeList(List<AccInformeOpe> accInformeOpeList) {
        this.accInformeOpeList = accInformeOpeList;
    }

    @XmlTransient
    public List<Accidente> getAccidenteList() {
        return accidenteList;
    }

    public void setAccidenteList(List<Accidente> accidenteList) {
        this.accidenteList = accidenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccClase != null ? idAccClase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccClase)) {
            return false;
        }
        AccClase other = (AccClase) object;
        if ((this.idAccClase == null && other.idAccClase != null) || (this.idAccClase != null && !this.idAccClase.equals(other.idAccClase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccClase[ idAccClase=" + idAccClase + " ]";
    }
    
}
