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
@Table(name = "acc_cond_humana")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccCondHumana.findAll", query = "SELECT a FROM AccCondHumana a")
    , @NamedQuery(name = "AccCondHumana.findByIdAccCondHumana", query = "SELECT a FROM AccCondHumana a WHERE a.idAccCondHumana = :idAccCondHumana")
    , @NamedQuery(name = "AccCondHumana.findByCondHumana", query = "SELECT a FROM AccCondHumana a WHERE a.condHumana = :condHumana")
    , @NamedQuery(name = "AccCondHumana.findByUsername", query = "SELECT a FROM AccCondHumana a WHERE a.username = :username")
    , @NamedQuery(name = "AccCondHumana.findByCreado", query = "SELECT a FROM AccCondHumana a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccCondHumana.findByModificado", query = "SELECT a FROM AccCondHumana a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccCondHumana.findByEstadoReg", query = "SELECT a FROM AccCondHumana a WHERE a.estadoReg = :estadoReg")})
public class AccCondHumana implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_cond_humana")
    private Integer idAccCondHumana;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "cond_humana")
    private String condHumana;
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

    public AccCondHumana() {
    }

    public AccCondHumana(Integer idAccCondHumana) {
        this.idAccCondHumana = idAccCondHumana;
    }

    public AccCondHumana(Integer idAccCondHumana, String condHumana) {
        this.idAccCondHumana = idAccCondHumana;
        this.condHumana = condHumana;
    }

    public Integer getIdAccCondHumana() {
        return idAccCondHumana;
    }

    public void setIdAccCondHumana(Integer idAccCondHumana) {
        this.idAccCondHumana = idAccCondHumana;
    }

    public String getCondHumana() {
        return condHumana;
    }

    public void setCondHumana(String condHumana) {
        this.condHumana = condHumana;
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
        hash += (idAccCondHumana != null ? idAccCondHumana.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccCondHumana)) {
            return false;
        }
        AccCondHumana other = (AccCondHumana) object;
        if ((this.idAccCondHumana == null && other.idAccCondHumana != null) || (this.idAccCondHumana != null && !this.idAccCondHumana.equals(other.idAccCondHumana))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccCondHumana[ idAccCondHumana=" + idAccCondHumana + " ]";
    }
    
}
