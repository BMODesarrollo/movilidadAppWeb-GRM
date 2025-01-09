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
@Table(name = "acc_via_troncal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccViaTroncal.findAll", query = "SELECT a FROM AccViaTroncal a")
    , @NamedQuery(name = "AccViaTroncal.findByIdAccViaTroncal", query = "SELECT a FROM AccViaTroncal a WHERE a.idAccViaTroncal = :idAccViaTroncal")
    , @NamedQuery(name = "AccViaTroncal.findByViaTroncal", query = "SELECT a FROM AccViaTroncal a WHERE a.viaTroncal = :viaTroncal")
    , @NamedQuery(name = "AccViaTroncal.findByUsername", query = "SELECT a FROM AccViaTroncal a WHERE a.username = :username")
    , @NamedQuery(name = "AccViaTroncal.findByCreado", query = "SELECT a FROM AccViaTroncal a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccViaTroncal.findByModificado", query = "SELECT a FROM AccViaTroncal a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccViaTroncal.findByEstadoReg", query = "SELECT a FROM AccViaTroncal a WHERE a.estadoReg = :estadoReg")})
public class AccViaTroncal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_via_troncal")
    private Integer idAccViaTroncal;
    @Size(max = 60)
    @Column(name = "via_troncal")
    private String viaTroncal;
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
    @OneToMany(mappedBy = "idAccViaTroncal", fetch = FetchType.LAZY)
    private List<AccInformeOpe> accInformeOpeList;

    public AccViaTroncal() {
    }

    public AccViaTroncal(Integer idAccViaTroncal) {
        this.idAccViaTroncal = idAccViaTroncal;
    }

    public Integer getIdAccViaTroncal() {
        return idAccViaTroncal;
    }

    public void setIdAccViaTroncal(Integer idAccViaTroncal) {
        this.idAccViaTroncal = idAccViaTroncal;
    }

    public String getViaTroncal() {
        return viaTroncal;
    }

    public void setViaTroncal(String viaTroncal) {
        this.viaTroncal = viaTroncal;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccViaTroncal != null ? idAccViaTroncal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccViaTroncal)) {
            return false;
        }
        AccViaTroncal other = (AccViaTroncal) object;
        if ((this.idAccViaTroncal == null && other.idAccViaTroncal != null) || (this.idAccViaTroncal != null && !this.idAccViaTroncal.equals(other.idAccViaTroncal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccViaTroncal[ idAccViaTroncal=" + idAccViaTroncal + " ]";
    }
    
}
