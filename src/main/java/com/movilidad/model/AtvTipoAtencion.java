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
 * @author solucionesit
 */
@Entity
@Table(name = "atv_tipo_atencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtvTipoAtencion.findAll", query = "SELECT a FROM AtvTipoAtencion a")
    , @NamedQuery(name = "AtvTipoAtencion.findByIdAtvTipoAtencion", query = "SELECT a FROM AtvTipoAtencion a WHERE a.idAtvTipoAtencion = :idAtvTipoAtencion")
    , @NamedQuery(name = "AtvTipoAtencion.findByNombre", query = "SELECT a FROM AtvTipoAtencion a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "AtvTipoAtencion.findByDescripcion", query = "SELECT a FROM AtvTipoAtencion a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "AtvTipoAtencion.findByUsername", query = "SELECT a FROM AtvTipoAtencion a WHERE a.username = :username")
    , @NamedQuery(name = "AtvTipoAtencion.findByCreado", query = "SELECT a FROM AtvTipoAtencion a WHERE a.creado = :creado")
    , @NamedQuery(name = "AtvTipoAtencion.findByModificado", query = "SELECT a FROM AtvTipoAtencion a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AtvTipoAtencion.findByEstadoReg", query = "SELECT a FROM AtvTipoAtencion a WHERE a.estadoReg = :estadoReg")})
public class AtvTipoAtencion implements Serializable {

    @OneToMany(mappedBy = "idAtvTipoAtencion", fetch = FetchType.LAZY)
    private List<AtvTipoEstado> atvTipoEstadoList;
    @OneToMany(mappedBy = "idAtvTipoAtencion", fetch = FetchType.LAZY)
    private List<Novedad> novedadList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atv_tipo_atencion")
    private Integer idAtvTipoAtencion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 150)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAtvTipoAtencion", fetch = FetchType.LAZY)
    private List<AtvVehiculosAtencion> atvVehiculosAtencionList;

    public AtvTipoAtencion() {
    }

    public AtvTipoAtencion(Integer idAtvTipoAtencion) {
        this.idAtvTipoAtencion = idAtvTipoAtencion;
    }

    public AtvTipoAtencion(Integer idAtvTipoAtencion, String nombre) {
        this.idAtvTipoAtencion = idAtvTipoAtencion;
        this.nombre = nombre;
    }

    public Integer getIdAtvTipoAtencion() {
        return idAtvTipoAtencion;
    }

    public void setIdAtvTipoAtencion(Integer idAtvTipoAtencion) {
        this.idAtvTipoAtencion = idAtvTipoAtencion;
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
    public List<AtvVehiculosAtencion> getAtvVehiculosAtencionList() {
        return atvVehiculosAtencionList;
    }

    public void setAtvVehiculosAtencionList(List<AtvVehiculosAtencion> atvVehiculosAtencionList) {
        this.atvVehiculosAtencionList = atvVehiculosAtencionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtvTipoAtencion != null ? idAtvTipoAtencion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtvTipoAtencion)) {
            return false;
        }
        AtvTipoAtencion other = (AtvTipoAtencion) object;
        if ((this.idAtvTipoAtencion == null && other.idAtvTipoAtencion != null) || (this.idAtvTipoAtencion != null && !this.idAtvTipoAtencion.equals(other.idAtvTipoAtencion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AtvTipoAtencion[ idAtvTipoAtencion=" + idAtvTipoAtencion + " ]";
    }

    @XmlTransient
    public List<AtvTipoEstado> getAtvTipoEstadoList() {
        return atvTipoEstadoList;
    }

    public void setAtvTipoEstadoList(List<AtvTipoEstado> atvTipoEstadoList) {
        this.atvTipoEstadoList = atvTipoEstadoList;
    }

    @XmlTransient
    public List<Novedad> getNovedadList() {
        return novedadList;
    }

    public void setNovedadList(List<Novedad> novedadList) {
        this.novedadList = novedadList;
    }
    
}
