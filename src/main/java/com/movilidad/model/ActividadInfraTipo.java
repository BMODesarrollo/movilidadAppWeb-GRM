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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "actividad_infra_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadInfraTipo.findAll", query = "SELECT a FROM ActividadInfraTipo a")
    , @NamedQuery(name = "ActividadInfraTipo.findByIdActividadInfraTipo", query = "SELECT a FROM ActividadInfraTipo a WHERE a.idActividadInfraTipo = :idActividadInfraTipo")
    , @NamedQuery(name = "ActividadInfraTipo.findByNombre", query = "SELECT a FROM ActividadInfraTipo a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "ActividadInfraTipo.findByDescripcion", query = "SELECT a FROM ActividadInfraTipo a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "ActividadInfraTipo.findByUsername", query = "SELECT a FROM ActividadInfraTipo a WHERE a.username = :username")
    , @NamedQuery(name = "ActividadInfraTipo.findByCreado", query = "SELECT a FROM ActividadInfraTipo a WHERE a.creado = :creado")
    , @NamedQuery(name = "ActividadInfraTipo.findByModificado", query = "SELECT a FROM ActividadInfraTipo a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "ActividadInfraTipo.findByEstadoReg", query = "SELECT a FROM ActividadInfraTipo a WHERE a.estadoReg = :estadoReg")})
public class ActividadInfraTipo implements Serializable {

    @OneToMany(mappedBy = "idActividadInfraTipo", fetch = FetchType.LAZY)
    private List<ActividadInfraDiaria> actividadInfraDiariaList;

    @OneToMany(mappedBy = "idActividadInfraTipo", fetch = FetchType.LAZY)
    private List<ActividadInfraTipoDet> actividadInfraTipoDetList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actividad_infra_tipo")
    private Integer idActividadInfraTipo;
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

    public ActividadInfraTipo() {
    }

    public ActividadInfraTipo(Integer idActividadInfraTipo) {
        this.idActividadInfraTipo = idActividadInfraTipo;
    }

    public ActividadInfraTipo(Integer idActividadInfraTipo, String nombre, String descripcion) {
        this.idActividadInfraTipo = idActividadInfraTipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdActividadInfraTipo() {
        return idActividadInfraTipo;
    }

    public void setIdActividadInfraTipo(Integer idActividadInfraTipo) {
        this.idActividadInfraTipo = idActividadInfraTipo;
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
        hash += (idActividadInfraTipo != null ? idActividadInfraTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadInfraTipo)) {
            return false;
        }
        ActividadInfraTipo other = (ActividadInfraTipo) object;
        if ((this.idActividadInfraTipo == null && other.idActividadInfraTipo != null) || (this.idActividadInfraTipo != null && !this.idActividadInfraTipo.equals(other.idActividadInfraTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ActividadInfraTipo[ idActividadInfraTipo=" + idActividadInfraTipo + " ]";
    }

    @XmlTransient
    public List<ActividadInfraTipoDet> getActividadInfraTipoDetList() {
        return actividadInfraTipoDetList;
    }

    public void setActividadInfraTipoDetList(List<ActividadInfraTipoDet> actividadInfraTipoDetList) {
        this.actividadInfraTipoDetList = actividadInfraTipoDetList;
    }

    @XmlTransient
    public List<ActividadInfraDiaria> getActividadInfraDiariaList() {
        return actividadInfraDiariaList;
    }

    public void setActividadInfraDiariaList(List<ActividadInfraDiaria> actividadInfraDiariaList) {
        this.actividadInfraDiariaList = actividadInfraDiariaList;
    }
    
}
