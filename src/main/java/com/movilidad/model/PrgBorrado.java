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
 * @author cesar
 */
@Entity
@Table(name = "prg_borrado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgBorrado.findAll", query = "SELECT p FROM PrgBorrado p")
    , @NamedQuery(name = "PrgBorrado.findByIdPrgBorrado", query = "SELECT p FROM PrgBorrado p WHERE p.idPrgBorrado = :idPrgBorrado")
    , @NamedQuery(name = "PrgBorrado.findByFechaHora", query = "SELECT p FROM PrgBorrado p WHERE p.fechaHora = :fechaHora")
    , @NamedQuery(name = "PrgBorrado.findByUsername", query = "SELECT p FROM PrgBorrado p WHERE p.username = :username")
    , @NamedQuery(name = "PrgBorrado.findByCreado", query = "SELECT p FROM PrgBorrado p WHERE p.creado = :creado")
    , @NamedQuery(name = "PrgBorrado.findByModificado", query = "SELECT p FROM PrgBorrado p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PrgBorrado.findByEstadoReg", query = "SELECT p FROM PrgBorrado p WHERE p.estadoReg = :estadoReg")})
public class PrgBorrado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_borrado")
    private Integer idPrgBorrado;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Lob
    @Size(max = 65535)
    @Column(name = "motivo")
    private String motivo;
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

    public PrgBorrado() {
    }

    public PrgBorrado(Integer idPrgBorrado) {
        this.idPrgBorrado = idPrgBorrado;
    }

    public Integer getIdPrgBorrado() {
        return idPrgBorrado;
    }

    public void setIdPrgBorrado(Integer idPrgBorrado) {
        this.idPrgBorrado = idPrgBorrado;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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
        hash += (idPrgBorrado != null ? idPrgBorrado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgBorrado)) {
            return false;
        }
        PrgBorrado other = (PrgBorrado) object;
        if ((this.idPrgBorrado == null && other.idPrgBorrado != null) || (this.idPrgBorrado != null && !this.idPrgBorrado.equals(other.idPrgBorrado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgBorrado[ idPrgBorrado=" + idPrgBorrado + " ]";
    }
    
}
