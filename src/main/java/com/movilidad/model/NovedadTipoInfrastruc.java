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
 * @author solucionesit
 */
@Entity
@Table(name = "novedad_tipo_infrastruc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadTipoInfrastruc.findAll", query = "SELECT n FROM NovedadTipoInfrastruc n"),
    @NamedQuery(name = "NovedadTipoInfrastruc.findByIdNovedadTipoInfrastruc", query = "SELECT n FROM NovedadTipoInfrastruc n WHERE n.idNovedadTipoInfrastruc = :idNovedadTipoInfrastruc"),
    @NamedQuery(name = "NovedadTipoInfrastruc.findByNombre", query = "SELECT n FROM NovedadTipoInfrastruc n WHERE n.nombre = :nombre"),
    @NamedQuery(name = "NovedadTipoInfrastruc.findByDescripcion", query = "SELECT n FROM NovedadTipoInfrastruc n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "NovedadTipoInfrastruc.findByUsername", query = "SELECT n FROM NovedadTipoInfrastruc n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadTipoInfrastruc.findByCreado", query = "SELECT n FROM NovedadTipoInfrastruc n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadTipoInfrastruc.findByModificado", query = "SELECT n FROM NovedadTipoInfrastruc n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadTipoInfrastruc.findByEstadoReg", query = "SELECT n FROM NovedadTipoInfrastruc n WHERE n.estadoReg = :estadoReg")})
public class NovedadTipoInfrastruc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_tipo_infrastruc")
    private Integer idNovedadTipoInfrastruc;
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
    @OneToMany(mappedBy = "novedadTipoInfrastruc", fetch = FetchType.LAZY)
    private List<NovedadInfrastruc> novedadInfrastrucList;
    @OneToMany(mappedBy = "novedadTipoInfrastruc", fetch = FetchType.LAZY)
    private List<NovedadTipoDetallesInfrastruc> novedadTipoDetallesInfrastrucList;

    public NovedadTipoInfrastruc() {
    }

    public NovedadTipoInfrastruc(Integer idNovedadTipoInfrastruc) {
        this.idNovedadTipoInfrastruc = idNovedadTipoInfrastruc;
    }

    public NovedadTipoInfrastruc(Integer idNovedadTipoInfrastruc, String nombre, String descripcion) {
        this.idNovedadTipoInfrastruc = idNovedadTipoInfrastruc;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdNovedadTipoInfrastruc() {
        return idNovedadTipoInfrastruc;
    }

    public void setIdNovedadTipoInfrastruc(Integer idNovedadTipoInfrastruc) {
        this.idNovedadTipoInfrastruc = idNovedadTipoInfrastruc;
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
    public List<NovedadInfrastruc> getNovedadInfrastrucList() {
        return novedadInfrastrucList;
    }

    public void setNovedadInfrastrucList(List<NovedadInfrastruc> novedadInfrastrucList) {
        this.novedadInfrastrucList = novedadInfrastrucList;
    }

    @XmlTransient
    public List<NovedadTipoDetallesInfrastruc> getNovedadTipoDetallesInfrastrucList() {
        return novedadTipoDetallesInfrastrucList;
    }

    public void setNovedadTipoDetallesInfrastrucList(List<NovedadTipoDetallesInfrastruc> novedadTipoDetallesInfrastrucList) {
        this.novedadTipoDetallesInfrastrucList = novedadTipoDetallesInfrastrucList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadTipoInfrastruc != null ? idNovedadTipoInfrastruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadTipoInfrastruc)) {
            return false;
        }
        NovedadTipoInfrastruc other = (NovedadTipoInfrastruc) object;
        if ((this.idNovedadTipoInfrastruc == null && other.idNovedadTipoInfrastruc != null) || (this.idNovedadTipoInfrastruc != null && !this.idNovedadTipoInfrastruc.equals(other.idNovedadTipoInfrastruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadTipoInfrastruc[ idNovedadTipoInfrastruc=" + idNovedadTipoInfrastruc + " ]";
    }
    
}
