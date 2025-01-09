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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "generica_solicitud_permiso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaSolicitudPermiso.findAll", query = "SELECT g FROM GenericaSolicitudPermiso g"),
    @NamedQuery(name = "GenericaSolicitudPermiso.findByIdGenericaSolicitudPermiso", query = "SELECT g FROM GenericaSolicitudPermiso g WHERE g.idGenericaSolicitudPermiso = :idGenericaSolicitudPermiso"),
    @NamedQuery(name = "GenericaSolicitudPermiso.findByFecha", query = "SELECT g FROM GenericaSolicitudPermiso g WHERE g.fecha = :fecha"),
    @NamedQuery(name = "GenericaSolicitudPermiso.findByLugarInicio", query = "SELECT g FROM GenericaSolicitudPermiso g WHERE g.lugarInicio = :lugarInicio"),
    @NamedQuery(name = "GenericaSolicitudPermiso.findByLugarFin", query = "SELECT g FROM GenericaSolicitudPermiso g WHERE g.lugarFin = :lugarFin"),
    @NamedQuery(name = "GenericaSolicitudPermiso.findByTimeOrigin", query = "SELECT g FROM GenericaSolicitudPermiso g WHERE g.timeOrigin = :timeOrigin"),
    @NamedQuery(name = "GenericaSolicitudPermiso.findByTimeDestiny", query = "SELECT g FROM GenericaSolicitudPermiso g WHERE g.timeDestiny = :timeDestiny"),
    @NamedQuery(name = "GenericaSolicitudPermiso.findByCreado", query = "SELECT g FROM GenericaSolicitudPermiso g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaSolicitudPermiso.findByModificado", query = "SELECT g FROM GenericaSolicitudPermiso g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaSolicitudPermiso.findByEstadoReg", query = "SELECT g FROM GenericaSolicitudPermiso g WHERE g.estadoReg = :estadoReg")})
public class GenericaSolicitudPermiso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_solicitud_permiso")
    private Integer idGenericaSolicitudPermiso;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 40)
    @Column(name = "lugar_inicio")
    private String lugarInicio;
    @Size(max = 40)
    @Column(name = "lugar_fin")
    private String lugarFin;
    @Size(max = 8)
    @Column(name = "time_origin")
    private String timeOrigin;
    @Size(max = 8)
    @Column(name = "time_destiny")
    private String timeDestiny;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_generica_solicitud", referencedColumnName = "id_generica_solicitud")
    @ManyToOne
    private GenericaSolicitud idGenericaSolicitud;

    public GenericaSolicitudPermiso() {
    }

    public GenericaSolicitudPermiso(Integer idGenericaSolicitudPermiso) {
        this.idGenericaSolicitudPermiso = idGenericaSolicitudPermiso;
    }

    public GenericaSolicitudPermiso(Integer idGenericaSolicitudPermiso, Date creado) {
        this.idGenericaSolicitudPermiso = idGenericaSolicitudPermiso;
        this.creado = creado;
    }

    public Integer getIdGenericaSolicitudPermiso() {
        return idGenericaSolicitudPermiso;
    }

    public void setIdGenericaSolicitudPermiso(Integer idGenericaSolicitudPermiso) {
        this.idGenericaSolicitudPermiso = idGenericaSolicitudPermiso;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getLugarInicio() {
        return lugarInicio;
    }

    public void setLugarInicio(String lugarInicio) {
        this.lugarInicio = lugarInicio;
    }

    public String getLugarFin() {
        return lugarFin;
    }

    public void setLugarFin(String lugarFin) {
        this.lugarFin = lugarFin;
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getTimeDestiny() {
        return timeDestiny;
    }

    public void setTimeDestiny(String timeDestiny) {
        this.timeDestiny = timeDestiny;
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

    public GenericaSolicitud getIdGenericaSolicitud() {
        return idGenericaSolicitud;
    }

    public void setIdGenericaSolicitud(GenericaSolicitud idGenericaSolicitud) {
        this.idGenericaSolicitud = idGenericaSolicitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaSolicitudPermiso != null ? idGenericaSolicitudPermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaSolicitudPermiso)) {
            return false;
        }
        GenericaSolicitudPermiso other = (GenericaSolicitudPermiso) object;
        if ((this.idGenericaSolicitudPermiso == null && other.idGenericaSolicitudPermiso != null) || (this.idGenericaSolicitudPermiso != null && !this.idGenericaSolicitudPermiso.equals(other.idGenericaSolicitudPermiso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaSolicitudPermiso[ idGenericaSolicitudPermiso=" + idGenericaSolicitudPermiso + " ]";
    }
    
}
