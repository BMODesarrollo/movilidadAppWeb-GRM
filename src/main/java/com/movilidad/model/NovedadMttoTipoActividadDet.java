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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
 * @author cesar
 */
@Entity
@Table(name = "novedad_mtto_tipo_actividad_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadMttoTipoActividadDet.findAll", query = "SELECT n FROM NovedadMttoTipoActividadDet n")
    , @NamedQuery(name = "NovedadMttoTipoActividadDet.findByIdNovedadMttoTipoActividadDet", query = "SELECT n FROM NovedadMttoTipoActividadDet n WHERE n.idNovedadMttoTipoActividadDet = :idNovedadMttoTipoActividadDet")
    , @NamedQuery(name = "NovedadMttoTipoActividadDet.findByNombre", query = "SELECT n FROM NovedadMttoTipoActividadDet n WHERE n.nombre = :nombre")
    , @NamedQuery(name = "NovedadMttoTipoActividadDet.findByDescripcion", query = "SELECT n FROM NovedadMttoTipoActividadDet n WHERE n.descripcion = :descripcion")
    , @NamedQuery(name = "NovedadMttoTipoActividadDet.findByCreado", query = "SELECT n FROM NovedadMttoTipoActividadDet n WHERE n.creado = :creado")
    , @NamedQuery(name = "NovedadMttoTipoActividadDet.findByModificado", query = "SELECT n FROM NovedadMttoTipoActividadDet n WHERE n.modificado = :modificado")
    , @NamedQuery(name = "NovedadMttoTipoActividadDet.findByEstadoReg", query = "SELECT n FROM NovedadMttoTipoActividadDet n WHERE n.estadoReg = :estadoReg")})
public class NovedadMttoTipoActividadDet implements Serializable {

    @OneToMany(mappedBy = "idNovedadMttoTipoActDet", fetch = FetchType.LAZY)
    private List<NovedadMttoDiaria> novedadMttoDiariaList;

    @Size(max = 15)
    @Column(name = "username")
    private String username;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_mtto_tipo_actividad_det")
    private Integer idNovedadMttoTipoActividadDet;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_novedad_mtto_tipo_actividad", referencedColumnName = "id_novedad_mtto_tipo_actividad")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadMttoTipoActividad idNovedadMttoTipoActividad;

    public NovedadMttoTipoActividadDet() {
    }

    public NovedadMttoTipoActividadDet(Integer idNovedadMttoTipoActividadDet) {
        this.idNovedadMttoTipoActividadDet = idNovedadMttoTipoActividadDet;
    }

    public NovedadMttoTipoActividadDet(Integer idNovedadMttoTipoActividadDet, Date modificado) {
        this.idNovedadMttoTipoActividadDet = idNovedadMttoTipoActividadDet;
        this.modificado = modificado;
    }

    public Integer getIdNovedadMttoTipoActividadDet() {
        return idNovedadMttoTipoActividadDet;
    }

    public void setIdNovedadMttoTipoActividadDet(Integer idNovedadMttoTipoActividadDet) {
        this.idNovedadMttoTipoActividadDet = idNovedadMttoTipoActividadDet;
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

    public NovedadMttoTipoActividad getIdNovedadMttoTipoActividad() {
        return idNovedadMttoTipoActividad;
    }

    public void setIdNovedadMttoTipoActividad(NovedadMttoTipoActividad idNovedadMttoTipoActividad) {
        this.idNovedadMttoTipoActividad = idNovedadMttoTipoActividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadMttoTipoActividadDet != null ? idNovedadMttoTipoActividadDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadMttoTipoActividadDet)) {
            return false;
        }
        NovedadMttoTipoActividadDet other = (NovedadMttoTipoActividadDet) object;
        if ((this.idNovedadMttoTipoActividadDet == null && other.idNovedadMttoTipoActividadDet != null) || (this.idNovedadMttoTipoActividadDet != null && !this.idNovedadMttoTipoActividadDet.equals(other.idNovedadMttoTipoActividadDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadMttoTipoActividadDet[ idNovedadMttoTipoActividadDet=" + idNovedadMttoTipoActividadDet + " ]";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlTransient
    public List<NovedadMttoDiaria> getNovedadMttoDiariaList() {
        return novedadMttoDiariaList;
    }

    public void setNovedadMttoDiariaList(List<NovedadMttoDiaria> novedadMttoDiariaList) {
        this.novedadMttoDiariaList = novedadMttoDiariaList;
    }

}
