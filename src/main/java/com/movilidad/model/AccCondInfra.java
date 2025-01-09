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
@Table(name = "acc_cond_infra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccCondInfra.findAll", query = "SELECT a FROM AccCondInfra a")
    , @NamedQuery(name = "AccCondInfra.findByIdAccCondInfra", query = "SELECT a FROM AccCondInfra a WHERE a.idAccCondInfra = :idAccCondInfra")
    , @NamedQuery(name = "AccCondInfra.findByCondInfra", query = "SELECT a FROM AccCondInfra a WHERE a.condInfra = :condInfra")
    , @NamedQuery(name = "AccCondInfra.findByUsername", query = "SELECT a FROM AccCondInfra a WHERE a.username = :username")
    , @NamedQuery(name = "AccCondInfra.findByCreado", query = "SELECT a FROM AccCondInfra a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccCondInfra.findByModificado", query = "SELECT a FROM AccCondInfra a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccCondInfra.findByEstadoReg", query = "SELECT a FROM AccCondInfra a WHERE a.estadoReg = :estadoReg")})
public class AccCondInfra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_cond_infra")
    private Integer idAccCondInfra;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "cond_infra")
    private String condInfra;
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

    public AccCondInfra() {
    }

    public AccCondInfra(Integer idAccCondInfra) {
        this.idAccCondInfra = idAccCondInfra;
    }

    public AccCondInfra(Integer idAccCondInfra, String condInfra) {
        this.idAccCondInfra = idAccCondInfra;
        this.condInfra = condInfra;
    }

    public Integer getIdAccCondInfra() {
        return idAccCondInfra;
    }

    public void setIdAccCondInfra(Integer idAccCondInfra) {
        this.idAccCondInfra = idAccCondInfra;
    }

    public String getCondInfra() {
        return condInfra;
    }

    public void setCondInfra(String condInfra) {
        this.condInfra = condInfra;
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
        hash += (idAccCondInfra != null ? idAccCondInfra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccCondInfra)) {
            return false;
        }
        AccCondInfra other = (AccCondInfra) object;
        if ((this.idAccCondInfra == null && other.idAccCondInfra != null) || (this.idAccCondInfra != null && !this.idAccCondInfra.equals(other.idAccCondInfra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccCondInfra[ idAccCondInfra=" + idAccCondInfra + " ]";
    }
    
}
