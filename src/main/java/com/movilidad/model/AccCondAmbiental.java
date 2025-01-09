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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "acc_cond_ambiental")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccCondAmbiental.findAll", query = "SELECT a FROM AccCondAmbiental a")
    , @NamedQuery(name = "AccCondAmbiental.findByIdAccCondAmbiental", query = "SELECT a FROM AccCondAmbiental a WHERE a.idAccCondAmbiental = :idAccCondAmbiental")
    , @NamedQuery(name = "AccCondAmbiental.findByCondAmbiental", query = "SELECT a FROM AccCondAmbiental a WHERE a.condAmbiental = :condAmbiental")
    , @NamedQuery(name = "AccCondAmbiental.findByUsername", query = "SELECT a FROM AccCondAmbiental a WHERE a.username = :username")
    , @NamedQuery(name = "AccCondAmbiental.findByCreado", query = "SELECT a FROM AccCondAmbiental a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccCondAmbiental.findByModificado", query = "SELECT a FROM AccCondAmbiental a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccCondAmbiental.findByEstadoReg", query = "SELECT a FROM AccCondAmbiental a WHERE a.estadoReg = :estadoReg")})
public class AccCondAmbiental implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_cond_ambiental")
    private Integer idAccCondAmbiental;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "cond_ambiental")
    private String condAmbiental;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.DATE)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;

    public AccCondAmbiental() {
    }

    public AccCondAmbiental(Integer idAccCondAmbiental) {
        this.idAccCondAmbiental = idAccCondAmbiental;
    }

    public AccCondAmbiental(Integer idAccCondAmbiental, String condAmbiental) {
        this.idAccCondAmbiental = idAccCondAmbiental;
        this.condAmbiental = condAmbiental;
    }

    public Integer getIdAccCondAmbiental() {
        return idAccCondAmbiental;
    }

    public void setIdAccCondAmbiental(Integer idAccCondAmbiental) {
        this.idAccCondAmbiental = idAccCondAmbiental;
    }

    public String getCondAmbiental() {
        return condAmbiental;
    }

    public void setCondAmbiental(String condAmbiental) {
        this.condAmbiental = condAmbiental;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccCondAmbiental != null ? idAccCondAmbiental.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccCondAmbiental)) {
            return false;
        }
        AccCondAmbiental other = (AccCondAmbiental) object;
        if ((this.idAccCondAmbiental == null && other.idAccCondAmbiental != null) || (this.idAccCondAmbiental != null && !this.idAccCondAmbiental.equals(other.idAccCondAmbiental))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccCondAmbiental[ idAccCondAmbiental=" + idAccCondAmbiental + " ]";
    }
    
}
