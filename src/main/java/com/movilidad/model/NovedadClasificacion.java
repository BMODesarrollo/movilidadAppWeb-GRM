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
 * @author HP
 */
@Entity
@Table(name = "novedad_clasificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadClasificacion.findAll", query = "SELECT n FROM NovedadClasificacion n")
    , @NamedQuery(name = "NovedadClasificacion.findByIdNovedadClasificacion", query = "SELECT n FROM NovedadClasificacion n WHERE n.idNovedadClasificacion = :idNovedadClasificacion")
    , @NamedQuery(name = "NovedadClasificacion.findByNombre", query = "SELECT n FROM NovedadClasificacion n WHERE n.nombre = :nombre")
    , @NamedQuery(name = "NovedadClasificacion.findByDescripcion", query = "SELECT n FROM NovedadClasificacion n WHERE n.descripcion = :descripcion")
    , @NamedQuery(name = "NovedadClasificacion.findByUsername", query = "SELECT n FROM NovedadClasificacion n WHERE n.username = :username")
    , @NamedQuery(name = "NovedadClasificacion.findByCreado", query = "SELECT n FROM NovedadClasificacion n WHERE n.creado = :creado")
    , @NamedQuery(name = "NovedadClasificacion.findByModificado", query = "SELECT n FROM NovedadClasificacion n WHERE n.modificado = :modificado")
    , @NamedQuery(name = "NovedadClasificacion.findByEstadoReg", query = "SELECT n FROM NovedadClasificacion n WHERE n.estadoReg = :estadoReg")})
public class NovedadClasificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_clasificacion")
    private Integer idNovedadClasificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedadClasificacion", fetch = FetchType.LAZY)
    private List<NovedadTipoDetalles> novedadTipoDetallesList;

    public NovedadClasificacion() {
    }

    public NovedadClasificacion(Integer idNovedadClasificacion) {
        this.idNovedadClasificacion = idNovedadClasificacion;
    }

    public NovedadClasificacion(Integer idNovedadClasificacion, String nombre, String descripcion, String username, Date creado, int estadoReg) {
        this.idNovedadClasificacion = idNovedadClasificacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadClasificacion() {
        return idNovedadClasificacion;
    }

    public void setIdNovedadClasificacion(Integer idNovedadClasificacion) {
        this.idNovedadClasificacion = idNovedadClasificacion;
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

    @XmlTransient
    public List<NovedadTipoDetalles> getNovedadTipoDetallesList() {
        return novedadTipoDetallesList;
    }

    public void setNovedadTipoDetallesList(List<NovedadTipoDetalles> novedadTipoDetallesList) {
        this.novedadTipoDetallesList = novedadTipoDetallesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadClasificacion != null ? idNovedadClasificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadClasificacion)) {
            return false;
        }
        NovedadClasificacion other = (NovedadClasificacion) object;
        if ((this.idNovedadClasificacion == null && other.idNovedadClasificacion != null) || (this.idNovedadClasificacion != null && !this.idNovedadClasificacion.equals(other.idNovedadClasificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadClasificacion[ idNovedadClasificacion=" + idNovedadClasificacion + " ]";
    }
    
}
