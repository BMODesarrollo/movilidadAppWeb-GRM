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
@Table(name = "acc_parte_afectada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccParteAfectada.findAll", query = "SELECT a FROM AccParteAfectada a")
    , @NamedQuery(name = "AccParteAfectada.findByIdAccParteAfectada", query = "SELECT a FROM AccParteAfectada a WHERE a.idAccParteAfectada = :idAccParteAfectada")
    , @NamedQuery(name = "AccParteAfectada.findByParteAfectada", query = "SELECT a FROM AccParteAfectada a WHERE a.parteAfectada = :parteAfectada")
    , @NamedQuery(name = "AccParteAfectada.findByUsername", query = "SELECT a FROM AccParteAfectada a WHERE a.username = :username")
    , @NamedQuery(name = "AccParteAfectada.findByCreado", query = "SELECT a FROM AccParteAfectada a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccParteAfectada.findByModificado", query = "SELECT a FROM AccParteAfectada a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccParteAfectada.findByEstadoReg", query = "SELECT a FROM AccParteAfectada a WHERE a.estadoReg = :estadoReg")})
public class AccParteAfectada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_parte_afectada")
    private Integer idAccParteAfectada;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "parte_afectada")
    private String parteAfectada;
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

    public AccParteAfectada() {
    }

    public AccParteAfectada(Integer idAccParteAfectada) {
        this.idAccParteAfectada = idAccParteAfectada;
    }

    public AccParteAfectada(Integer idAccParteAfectada, String parteAfectada) {
        this.idAccParteAfectada = idAccParteAfectada;
        this.parteAfectada = parteAfectada;
    }

    public Integer getIdAccParteAfectada() {
        return idAccParteAfectada;
    }

    public void setIdAccParteAfectada(Integer idAccParteAfectada) {
        this.idAccParteAfectada = idAccParteAfectada;
    }

    public String getParteAfectada() {
        return parteAfectada;
    }

    public void setParteAfectada(String parteAfectada) {
        this.parteAfectada = parteAfectada;
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
        hash += (idAccParteAfectada != null ? idAccParteAfectada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccParteAfectada)) {
            return false;
        }
        AccParteAfectada other = (AccParteAfectada) object;
        if ((this.idAccParteAfectada == null && other.idAccParteAfectada != null) || (this.idAccParteAfectada != null && !this.idAccParteAfectada.equals(other.idAccParteAfectada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccParteAfectada[ idAccParteAfectada=" + idAccParteAfectada + " ]";
    }
    
}
