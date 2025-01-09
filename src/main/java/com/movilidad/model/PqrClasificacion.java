/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "pqr_clasificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PqrClasificacion.findAll", query = "SELECT p FROM PqrClasificacion p"),
    @NamedQuery(name = "PqrClasificacion.findByIdPqrClasificacion", query = "SELECT p FROM PqrClasificacion p WHERE p.idPqrClasificacion = :idPqrClasificacion"),
    @NamedQuery(name = "PqrClasificacion.findByNombre", query = "SELECT p FROM PqrClasificacion p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PqrClasificacion.findByUsername", query = "SELECT p FROM PqrClasificacion p WHERE p.username = :username"),
    @NamedQuery(name = "PqrClasificacion.findByCreado", query = "SELECT p FROM PqrClasificacion p WHERE p.creado = :creado"),
    @NamedQuery(name = "PqrClasificacion.findByModificado", query = "SELECT p FROM PqrClasificacion p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PqrClasificacion.findByEstadoReg", query = "SELECT p FROM PqrClasificacion p WHERE p.estadoReg = :estadoReg")})
public class PqrClasificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pqr_clasificacion")
    private Integer idPqrClasificacion;
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
    @OneToMany(mappedBy = "idPqrClasificacion", fetch = FetchType.LAZY)
    private List<PqrTipo> pqrTipoList;

    public PqrClasificacion() {
    }

    public PqrClasificacion(Integer idPqrClasificacion) {
        this.idPqrClasificacion = idPqrClasificacion;
    }

    public PqrClasificacion(Integer idPqrClasificacion, String nombre, String descripcion) {
        this.idPqrClasificacion = idPqrClasificacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdPqrClasificacion() {
        return idPqrClasificacion;
    }

    public void setIdPqrClasificacion(Integer idPqrClasificacion) {
        this.idPqrClasificacion = idPqrClasificacion;
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

    @XmlTransient
    public List<PqrTipo> getPqrTipoList() {
        return pqrTipoList;
    }

    public void setPqrTipoList(List<PqrTipo> pqrTipoList) {
        this.pqrTipoList = pqrTipoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPqrClasificacion != null ? idPqrClasificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PqrClasificacion)) {
            return false;
        }
        PqrClasificacion other = (PqrClasificacion) object;
        if ((this.idPqrClasificacion == null && other.idPqrClasificacion != null) || (this.idPqrClasificacion != null && !this.idPqrClasificacion.equals(other.idPqrClasificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PqrClasificacion[ idPqrClasificacion=" + idPqrClasificacion + " ]";
    }

}
