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
@Table(name = "acc_cond_equipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccCondEquipo.findAll", query = "SELECT a FROM AccCondEquipo a")
    , @NamedQuery(name = "AccCondEquipo.findByIdAccCondEquipo", query = "SELECT a FROM AccCondEquipo a WHERE a.idAccCondEquipo = :idAccCondEquipo")
    , @NamedQuery(name = "AccCondEquipo.findByCondEquipo", query = "SELECT a FROM AccCondEquipo a WHERE a.condEquipo = :condEquipo")
    , @NamedQuery(name = "AccCondEquipo.findByUsername", query = "SELECT a FROM AccCondEquipo a WHERE a.username = :username")
    , @NamedQuery(name = "AccCondEquipo.findByCreado", query = "SELECT a FROM AccCondEquipo a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccCondEquipo.findByModificado", query = "SELECT a FROM AccCondEquipo a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccCondEquipo.findByEstadoReg", query = "SELECT a FROM AccCondEquipo a WHERE a.estadoReg = :estadoReg")})
public class AccCondEquipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_cond_equipo")
    private Integer idAccCondEquipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "cond_equipo")
    private String condEquipo;
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

    public AccCondEquipo() {
    }

    public AccCondEquipo(Integer idAccCondEquipo) {
        this.idAccCondEquipo = idAccCondEquipo;
    }

    public AccCondEquipo(Integer idAccCondEquipo, String condEquipo) {
        this.idAccCondEquipo = idAccCondEquipo;
        this.condEquipo = condEquipo;
    }

    public Integer getIdAccCondEquipo() {
        return idAccCondEquipo;
    }

    public void setIdAccCondEquipo(Integer idAccCondEquipo) {
        this.idAccCondEquipo = idAccCondEquipo;
    }

    public String getCondEquipo() {
        return condEquipo;
    }

    public void setCondEquipo(String condEquipo) {
        this.condEquipo = condEquipo;
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
        hash += (idAccCondEquipo != null ? idAccCondEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccCondEquipo)) {
            return false;
        }
        AccCondEquipo other = (AccCondEquipo) object;
        if ((this.idAccCondEquipo == null && other.idAccCondEquipo != null) || (this.idAccCondEquipo != null && !this.idAccCondEquipo.equals(other.idAccCondEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccCondEquipo[ idAccCondEquipo=" + idAccCondEquipo + " ]";
    }
    
}
