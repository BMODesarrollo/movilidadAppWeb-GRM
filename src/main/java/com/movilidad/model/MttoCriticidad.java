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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "mtto_criticidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MttoCriticidad.findAll", query = "SELECT m FROM MttoCriticidad m"),
    @NamedQuery(name = "MttoCriticidad.findByIdMttoCriticidad", query = "SELECT m FROM MttoCriticidad m WHERE m.idMttoCriticidad = :idMttoCriticidad"),
    @NamedQuery(name = "MttoCriticidad.findByCriticidad", query = "SELECT m FROM MttoCriticidad m WHERE m.criticidad = :criticidad"),
    @NamedQuery(name = "MttoCriticidad.findByUsername", query = "SELECT m FROM MttoCriticidad m WHERE m.username = :username"),
    @NamedQuery(name = "MttoCriticidad.findByCreado", query = "SELECT m FROM MttoCriticidad m WHERE m.creado = :creado"),
    @NamedQuery(name = "MttoCriticidad.findByModificado", query = "SELECT m FROM MttoCriticidad m WHERE m.modificado = :modificado"),
    @NamedQuery(name = "MttoCriticidad.findByEstadoReg", query = "SELECT m FROM MttoCriticidad m WHERE m.estadoReg = :estadoReg")})
public class MttoCriticidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mtto_criticidad")
    private Integer idMttoCriticidad;
    @Size(max = 45)
    @Column(name = "criticidad")
    private String criticidad;
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
    @OneToMany(mappedBy = "idMttoCriticidad", fetch = FetchType.LAZY)
    private List<MttoComponenteFalla> mttoComponenteFallaList;

    public MttoCriticidad() {
    }

    public MttoCriticidad(Integer idMttoCriticidad) {
        this.idMttoCriticidad = idMttoCriticidad;
    }

    public Integer getIdMttoCriticidad() {
        return idMttoCriticidad;
    }

    public void setIdMttoCriticidad(Integer idMttoCriticidad) {
        this.idMttoCriticidad = idMttoCriticidad;
    }

    public String getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(String criticidad) {
        this.criticidad = criticidad;
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
    public List<MttoComponenteFalla> getMttoComponenteFallaList() {
        return mttoComponenteFallaList;
    }

    public void setMttoComponenteFallaList(List<MttoComponenteFalla> mttoComponenteFallaList) {
        this.mttoComponenteFallaList = mttoComponenteFallaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMttoCriticidad != null ? idMttoCriticidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MttoCriticidad)) {
            return false;
        }
        MttoCriticidad other = (MttoCriticidad) object;
        if ((this.idMttoCriticidad == null && other.idMttoCriticidad != null) || (this.idMttoCriticidad != null && !this.idMttoCriticidad.equals(other.idMttoCriticidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MttoCriticidad[ idMttoCriticidad=" + idMttoCriticidad + " ]";
    }
    
}
