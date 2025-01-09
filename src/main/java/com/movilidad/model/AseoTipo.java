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
import javax.persistence.CascadeType;
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
@Table(name = "aseo_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AseoTipo.findAll", query = "SELECT a FROM AseoTipo a"),
    @NamedQuery(name = "AseoTipo.findByIdAseoTipo", query = "SELECT a FROM AseoTipo a WHERE a.idAseoTipo = :idAseoTipo"),
    @NamedQuery(name = "AseoTipo.findByNombre", query = "SELECT a FROM AseoTipo a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AseoTipo.findByDescripcion", query = "SELECT a FROM AseoTipo a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "AseoTipo.findByUsername", query = "SELECT a FROM AseoTipo a WHERE a.username = :username"),
    @NamedQuery(name = "AseoTipo.findByCreado", query = "SELECT a FROM AseoTipo a WHERE a.creado = :creado"),
    @NamedQuery(name = "AseoTipo.findByModificado", query = "SELECT a FROM AseoTipo a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AseoTipo.findByEstadoReg", query = "SELECT a FROM AseoTipo a WHERE a.estadoReg = :estadoReg")})
public class AseoTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aseo_tipo")
    private Integer idAseoTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aseoTipo", fetch = FetchType.LAZY)
    private List<Aseo> aseoList;
    @Size(min = 1, max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
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

    public AseoTipo() {
    }

    public AseoTipo(Integer idAseoTipo) {
        this.idAseoTipo = idAseoTipo;
    }

    public AseoTipo(Integer idAseoTipo, String nombre) {
        this.idAseoTipo = idAseoTipo;
        this.nombre = nombre;
    }

    public AseoTipo(Integer idAseoTipo, String nombre, String username, Date creado, int estadoReg) {
        this.idAseoTipo = idAseoTipo;
        this.nombre = nombre;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAseoTipo() {
        return idAseoTipo;
    }

    public void setIdAseoTipo(Integer idAseoTipo) {
        this.idAseoTipo = idAseoTipo;
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

    @XmlTransient
    public List<Aseo> getAseoList() {
        return aseoList;
    }

    public void setAseoList(List<Aseo> aseoList) {
        this.aseoList = aseoList;
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
        hash += (idAseoTipo != null ? idAseoTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AseoTipo)) {
            return false;
        }
        AseoTipo other = (AseoTipo) object;
        if ((this.idAseoTipo == null && other.idAseoTipo != null) || (this.idAseoTipo != null && !this.idAseoTipo.equals(other.idAseoTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AseoTipo[ idAseoTipo=" + idAseoTipo + " ]";
    }

}
