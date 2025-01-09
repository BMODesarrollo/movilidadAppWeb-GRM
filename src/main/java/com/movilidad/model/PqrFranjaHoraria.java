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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "pqr_franja_horaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PqrFranjaHoraria.findAll", query = "SELECT p FROM PqrFranjaHoraria p"),
    @NamedQuery(name = "PqrFranjaHoraria.findByIdPqrFranjaHoraria", query = "SELECT p FROM PqrFranjaHoraria p WHERE p.idPqrFHoraria = :idPqrFHoraria"),
    @NamedQuery(name = "PqrFranjaHoraria.findByNombre", query = "SELECT p FROM PqrFranjaHoraria p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PqrFranjaHoraria.findByUsername", query = "SELECT p FROM PqrFranjaHoraria p WHERE p.username = :username"),
    @NamedQuery(name = "PqrFranjaHoraria.findByCreado", query = "SELECT p FROM PqrFranjaHoraria p WHERE p.creado = :creado"),
    @NamedQuery(name = "PqrFranjaHoraria.findByModificado", query = "SELECT p FROM PqrFranjaHoraria p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PqrFranjaHoraria.findByEstadoReg", query = "SELECT p FROM PqrFranjaHoraria p WHERE p.estadoReg = :estadoReg")})
public class PqrFranjaHoraria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pqr_franja_horaria")
    private Integer idPqrFHoraria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
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

    public PqrFranjaHoraria() {
    }

    public PqrFranjaHoraria(Integer idPqrFHoraria) {
        this.idPqrFHoraria = idPqrFHoraria;
    }

    public PqrFranjaHoraria(Integer idPqrFHoraria, String nombre) {
        this.idPqrFHoraria = idPqrFHoraria;
        this.nombre = nombre;
    }

    public Integer getIdPqrFHoraria() {
        return idPqrFHoraria;
    }

    public void setIdPqrFHoraria(Integer idPqrFHoraria) {
        this.idPqrFHoraria = idPqrFHoraria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        hash += (idPqrFHoraria != null ? idPqrFHoraria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PqrFranjaHoraria)) {
            return false;
        }
        PqrFranjaHoraria other = (PqrFranjaHoraria) object;
        if ((this.idPqrFHoraria == null && other.idPqrFHoraria != null) || (this.idPqrFHoraria != null && !this.idPqrFHoraria.equals(other.idPqrFHoraria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PqrFranjaHoraria[ idPqrFHoraria=" + idPqrFHoraria + " ]";
    }

}
