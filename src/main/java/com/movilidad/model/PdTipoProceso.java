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
@Table(name = "pd_tipo_proceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PdTipoProceso.findAll", query = "SELECT p FROM PdTipoProceso p"),
    @NamedQuery(name = "PdTipoProceso.findByIdPdTipoProceso", query = "SELECT p FROM PdTipoProceso p WHERE p.idPdTipoProceso = :idPdTipoProceso"),
    @NamedQuery(name = "PdTipoProceso.findByNombre", query = "SELECT p FROM PdTipoProceso p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PdTipoProceso.findByUsername", query = "SELECT p FROM PdTipoProceso p WHERE p.username = :username"),
    @NamedQuery(name = "PdTipoProceso.findByCreado", query = "SELECT p FROM PdTipoProceso p WHERE p.creado = :creado"),
    @NamedQuery(name = "PdTipoProceso.findByModificado", query = "SELECT p FROM PdTipoProceso p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PdTipoProceso.findByEstadoReg", query = "SELECT p FROM PdTipoProceso p WHERE p.estadoReg = :estadoReg")})
public class PdTipoProceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pd_tipo_proceso")
    private Integer idPdTipoProceso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;

    public PdTipoProceso() {
    }

    public PdTipoProceso(Integer idPdTipoProceso) {
        this.idPdTipoProceso = idPdTipoProceso;
    }

    public PdTipoProceso(Integer idPdTipoProceso, String nombre, String descripcion, String username, int estadoReg) {
        this.idPdTipoProceso = idPdTipoProceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPdTipoProceso() {
        return idPdTipoProceso;
    }

    public void setIdPdTipoProceso(Integer idPdTipoProceso) {
        this.idPdTipoProceso = idPdTipoProceso;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPdTipoProceso != null ? idPdTipoProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PdTipoProceso)) {
            return false;
        }
        PdTipoProceso other = (PdTipoProceso) object;
        if ((this.idPdTipoProceso == null && other.idPdTipoProceso != null) || (this.idPdTipoProceso != null && !this.idPdTipoProceso.equals(other.idPdTipoProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PdTipoProceso[ idPdTipoProceso=" + idPdTipoProceso + " ]";
    }
    
}
