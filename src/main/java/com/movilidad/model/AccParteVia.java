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
@Table(name = "acc_parte_via")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccParteVia.findAll", query = "SELECT a FROM AccParteVia a")
    , @NamedQuery(name = "AccParteVia.findByIdAccParteVia", query = "SELECT a FROM AccParteVia a WHERE a.idAccParteVia = :idAccParteVia")
    , @NamedQuery(name = "AccParteVia.findByParteVia", query = "SELECT a FROM AccParteVia a WHERE a.parteVia = :parteVia")
    , @NamedQuery(name = "AccParteVia.findByUsername", query = "SELECT a FROM AccParteVia a WHERE a.username = :username")
    , @NamedQuery(name = "AccParteVia.findByCreado", query = "SELECT a FROM AccParteVia a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccParteVia.findByModificado", query = "SELECT a FROM AccParteVia a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccParteVia.findByEstadoReg", query = "SELECT a FROM AccParteVia a WHERE a.estadoReg = :estadoReg")})
public class AccParteVia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_parte_via")
    private Integer idAccParteVia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "parte_via")
    private String parteVia;
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

    public AccParteVia() {
    }

    public AccParteVia(Integer idAccParteVia) {
        this.idAccParteVia = idAccParteVia;
    }

    public AccParteVia(Integer idAccParteVia, String parteVia) {
        this.idAccParteVia = idAccParteVia;
        this.parteVia = parteVia;
    }

    public Integer getIdAccParteVia() {
        return idAccParteVia;
    }

    public void setIdAccParteVia(Integer idAccParteVia) {
        this.idAccParteVia = idAccParteVia;
    }

    public String getParteVia() {
        return parteVia;
    }

    public void setParteVia(String parteVia) {
        this.parteVia = parteVia;
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
        hash += (idAccParteVia != null ? idAccParteVia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccParteVia)) {
            return false;
        }
        AccParteVia other = (AccParteVia) object;
        if ((this.idAccParteVia == null && other.idAccParteVia != null) || (this.idAccParteVia != null && !this.idAccParteVia.equals(other.idAccParteVia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccParteVia[ idAccParteVia=" + idAccParteVia + " ]";
    }
    
}
