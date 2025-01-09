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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "accidente_abogado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteAbogado.findAll", query = "SELECT a FROM AccidenteAbogado a")
    , @NamedQuery(name = "AccidenteAbogado.findByIdAccidenteAbogado", query = "SELECT a FROM AccidenteAbogado a WHERE a.idAccidenteAbogado = :idAccidenteAbogado")
    , @NamedQuery(name = "AccidenteAbogado.findByCausaRaiz", query = "SELECT a FROM AccidenteAbogado a WHERE a.causaRaiz = :causaRaiz")
    , @NamedQuery(name = "AccidenteAbogado.findByUsername", query = "SELECT a FROM AccidenteAbogado a WHERE a.username = :username")
    , @NamedQuery(name = "AccidenteAbogado.findByCreado", query = "SELECT a FROM AccidenteAbogado a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteAbogado.findByModificado", query = "SELECT a FROM AccidenteAbogado a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteAbogado.findByEstadoReg", query = "SELECT a FROM AccidenteAbogado a WHERE a.estadoReg = :estadoReg")})
public class AccidenteAbogado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente_abogado")
    private Integer idAccidenteAbogado;
    @Size(max = 60)
    @Column(name = "causa_raiz")
    private String causaRaiz;
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

    public AccidenteAbogado() {
    }

    public AccidenteAbogado(Integer idAccidenteAbogado) {
        this.idAccidenteAbogado = idAccidenteAbogado;
    }

    public Integer getIdAccidenteAbogado() {
        return idAccidenteAbogado;
    }

    public void setIdAccidenteAbogado(Integer idAccidenteAbogado) {
        this.idAccidenteAbogado = idAccidenteAbogado;
    }

    public String getCausaRaiz() {
        return causaRaiz;
    }

    public void setCausaRaiz(String causaRaiz) {
        this.causaRaiz = causaRaiz;
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
        hash += (idAccidenteAbogado != null ? idAccidenteAbogado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteAbogado)) {
            return false;
        }
        AccidenteAbogado other = (AccidenteAbogado) object;
        if ((this.idAccidenteAbogado == null && other.idAccidenteAbogado != null) || (this.idAccidenteAbogado != null && !this.idAccidenteAbogado.equals(other.idAccidenteAbogado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteAbogado[ idAccidenteAbogado=" + idAccidenteAbogado + " ]";
    }
    
}
