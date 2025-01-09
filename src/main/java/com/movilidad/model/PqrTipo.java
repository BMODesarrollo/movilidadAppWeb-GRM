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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "pqr_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PqrTipo.findAll", query = "SELECT p FROM PqrTipo p"),
    @NamedQuery(name = "PqrTipo.findByIdPqrTipo", query = "SELECT p FROM PqrTipo p WHERE p.idPqrTipo = :idPqrTipo"),
    @NamedQuery(name = "PqrTipo.findByNombre", query = "SELECT p FROM PqrTipo p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PqrTipo.findByTiempoRespuesta", query = "SELECT p FROM PqrTipo p WHERE p.tiempoRespuesta = :tiempoRespuesta"),
    @NamedQuery(name = "PqrTipo.findByUsername", query = "SELECT p FROM PqrTipo p WHERE p.username = :username"),
    @NamedQuery(name = "PqrTipo.findByCreado", query = "SELECT p FROM PqrTipo p WHERE p.creado = :creado"),
    @NamedQuery(name = "PqrTipo.findByModificado", query = "SELECT p FROM PqrTipo p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PqrTipo.findByEstadoReg", query = "SELECT p FROM PqrTipo p WHERE p.estadoReg = :estadoReg")})
public class PqrTipo implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPqrTipo", fetch = FetchType.LAZY)
    private List<PqrMaestro> pqrMaestroList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pqr_tipo")
    private Integer idPqrTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
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
    @Column(name = "tiempo_respuesta")
    private int tiempoRespuesta;
    @Column(name = "time_asne_real")
    private int obj_time_real;
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
    @JoinColumn(name = "id_pqr_clasificacion", referencedColumnName = "id_pqr_clasificacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private PqrClasificacion idPqrClasificacion;
    @JoinColumn(name = "id_pqr_responsable", referencedColumnName = "id_pqr_responsable")
    @ManyToOne(fetch = FetchType.LAZY)
    private PqrResponsable idPqrResponsable;

    public PqrTipo() {
    }

    public PqrTipo(Integer idPqrTipo) {
        this.idPqrTipo = idPqrTipo;
    }

    public PqrTipo(Integer idPqrTipo, String nombre, String descripcion, int tiempoRespuesta) {
        this.idPqrTipo = idPqrTipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tiempoRespuesta = tiempoRespuesta;
    }

    public Integer getIdPqrTipo() {
        return idPqrTipo;
    }

    public void setIdPqrTipo(Integer idPqrTipo) {
        this.idPqrTipo = idPqrTipo;
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

    public int getTiempoRespuesta() {
        return tiempoRespuesta;
    }

    public void setTiempoRespuesta(int tiempoRespuesta) {
        this.tiempoRespuesta = tiempoRespuesta;
    }

    public int getObj_time_real() {
        return obj_time_real;
    }

    public void setObj_time_real(int obj_time_real) {
        this.obj_time_real = obj_time_real;
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

    public PqrClasificacion getIdPqrClasificacion() {
        return idPqrClasificacion;
    }

    public void setIdPqrClasificacion(PqrClasificacion idPqrClasificacion) {
        this.idPqrClasificacion = idPqrClasificacion;
    }

    public PqrResponsable getIdPqrResponsable() {
        return idPqrResponsable;
    }

    public void setIdPqrResponsable(PqrResponsable idPqrResponsable) {
        this.idPqrResponsable = idPqrResponsable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPqrTipo != null ? idPqrTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PqrTipo)) {
            return false;
        }
        PqrTipo other = (PqrTipo) object;
        if ((this.idPqrTipo == null && other.idPqrTipo != null) || (this.idPqrTipo != null && !this.idPqrTipo.equals(other.idPqrTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PqrTipo[ idPqrTipo=" + idPqrTipo + " ]";
    }

    @XmlTransient
    public List<PqrMaestro> getPqrMaestroList() {
        return pqrMaestroList;
    }

    public void setPqrMaestroList(List<PqrMaestro> pqrMaestroList) {
        this.pqrMaestroList = pqrMaestroList;
    }

}
