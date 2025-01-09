/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "gmo_novedad_tipo_infrastruc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GmoNovedadTipoInfrastruc.findAll", query = "SELECT g FROM GmoNovedadTipoInfrastruc g"),
    @NamedQuery(name = "GmoNovedadTipoInfrastruc.findByIdGmoNovedadTipoInfrastruc", query = "SELECT g FROM GmoNovedadTipoInfrastruc g WHERE g.idGmoNovedadTipoInfrastruc = :idGmoNovedadTipoInfrastruc"),
    @NamedQuery(name = "GmoNovedadTipoInfrastruc.findByNombre", query = "SELECT g FROM GmoNovedadTipoInfrastruc g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "GmoNovedadTipoInfrastruc.findByDescripcion", query = "SELECT g FROM GmoNovedadTipoInfrastruc g WHERE g.descripcion = :descripcion"),
    @NamedQuery(name = "GmoNovedadTipoInfrastruc.findByUsername", query = "SELECT g FROM GmoNovedadTipoInfrastruc g WHERE g.username = :username"),
    @NamedQuery(name = "GmoNovedadTipoInfrastruc.findByCreado", query = "SELECT g FROM GmoNovedadTipoInfrastruc g WHERE g.creado = :creado"),
    @NamedQuery(name = "GmoNovedadTipoInfrastruc.findByModificado", query = "SELECT g FROM GmoNovedadTipoInfrastruc g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GmoNovedadTipoInfrastruc.findByEstadoReg", query = "SELECT g FROM GmoNovedadTipoInfrastruc g WHERE g.estadoReg = :estadoReg")})
public class GmoNovedadTipoInfrastruc implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGmoNovedadTipoInfrastruc", fetch = FetchType.LAZY)
    private List<GmoNovedadInfrastruc> gmoNovedadInfrastrucList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gmo_novedad_tipo_infrastruc")
    private Integer idGmoNovedadTipoInfrastruc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
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
    @OneToMany(mappedBy = "idGmoNovedadTipoInfrastruc", fetch = FetchType.LAZY)
    private List<GmoNovedadTipoDetallesInfrastruc> gmoNovedadTipoDetallesInfrastrucList;

    public GmoNovedadTipoInfrastruc() {
    }

    public GmoNovedadTipoInfrastruc(Integer idGmoNovedadTipoInfrastruc) {
        this.idGmoNovedadTipoInfrastruc = idGmoNovedadTipoInfrastruc;
    }

    public GmoNovedadTipoInfrastruc(Integer idGmoNovedadTipoInfrastruc, String nombre, String descripcion) {
        this.idGmoNovedadTipoInfrastruc = idGmoNovedadTipoInfrastruc;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdGmoNovedadTipoInfrastruc() {
        return idGmoNovedadTipoInfrastruc;
    }

    public void setIdGmoNovedadTipoInfrastruc(Integer idGmoNovedadTipoInfrastruc) {
        this.idGmoNovedadTipoInfrastruc = idGmoNovedadTipoInfrastruc;
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
    public List<GmoNovedadTipoDetallesInfrastruc> getGmoNovedadTipoDetallesInfrastrucList() {
        return gmoNovedadTipoDetallesInfrastrucList;
    }

    public void setGmoNovedadTipoDetallesInfrastrucList(List<GmoNovedadTipoDetallesInfrastruc> gmoNovedadTipoDetallesInfrastrucList) {
        this.gmoNovedadTipoDetallesInfrastrucList = gmoNovedadTipoDetallesInfrastrucList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGmoNovedadTipoInfrastruc != null ? idGmoNovedadTipoInfrastruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GmoNovedadTipoInfrastruc)) {
            return false;
        }
        GmoNovedadTipoInfrastruc other = (GmoNovedadTipoInfrastruc) object;
        if ((this.idGmoNovedadTipoInfrastruc == null && other.idGmoNovedadTipoInfrastruc != null) || (this.idGmoNovedadTipoInfrastruc != null && !this.idGmoNovedadTipoInfrastruc.equals(other.idGmoNovedadTipoInfrastruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GmoNovedadTipoInfrastruc[ idGmoNovedadTipoInfrastruc=" + idGmoNovedadTipoInfrastruc + " ]";
    }

    @XmlTransient
    public List<GmoNovedadInfrastruc> getGmoNovedadInfrastrucList() {
        return gmoNovedadInfrastrucList;
    }

    public void setGmoNovedadInfrastrucList(List<GmoNovedadInfrastruc> gmoNovedadInfrastrucList) {
        this.gmoNovedadInfrastrucList = gmoNovedadInfrastrucList;
    }

}
