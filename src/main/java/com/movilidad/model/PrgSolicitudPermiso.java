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
@Table(name = "prg_solicitud_permiso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgSolicitudPermiso.findAll", query = "SELECT p FROM PrgSolicitudPermiso p"),
    @NamedQuery(name = "PrgSolicitudPermiso.findByIdPrgSolicitudPermiso", query = "SELECT p FROM PrgSolicitudPermiso p WHERE p.idPrgSolicitudPermiso = :idPrgSolicitudPermiso"),
    @NamedQuery(name = "PrgSolicitudPermiso.findByFecha", query = "SELECT p FROM PrgSolicitudPermiso p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "PrgSolicitudPermiso.findByLugarInicio", query = "SELECT p FROM PrgSolicitudPermiso p WHERE p.lugarInicio = :lugarInicio"),
    @NamedQuery(name = "PrgSolicitudPermiso.findByLugarFin", query = "SELECT p FROM PrgSolicitudPermiso p WHERE p.lugarFin = :lugarFin"),
    @NamedQuery(name = "PrgSolicitudPermiso.findByTimeOrigin", query = "SELECT p FROM PrgSolicitudPermiso p WHERE p.timeOrigin = :timeOrigin"),
    @NamedQuery(name = "PrgSolicitudPermiso.findByTimeDestiny", query = "SELECT p FROM PrgSolicitudPermiso p WHERE p.timeDestiny = :timeDestiny"),
    @NamedQuery(name = "PrgSolicitudPermiso.findByCreado", query = "SELECT p FROM PrgSolicitudPermiso p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgSolicitudPermiso.findByModificado", query = "SELECT p FROM PrgSolicitudPermiso p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgSolicitudPermiso.findByEstadoReg", query = "SELECT p FROM PrgSolicitudPermiso p WHERE p.estadoReg = :estadoReg")})
public class PrgSolicitudPermiso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_solicitud_permiso")
    private Integer idPrgSolicitudPermiso;
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
    @JoinColumn(name = "id_prg_solicitud", referencedColumnName = "id_prg_solicitud")
    @ManyToOne
    private PrgSolicitud idPrgSolicitud;

    public PrgSolicitudPermiso() {
    }

    public PrgSolicitudPermiso(Integer idPrgSolicitudPermiso) {
        this.idPrgSolicitudPermiso = idPrgSolicitudPermiso;
    }

    public PrgSolicitudPermiso(Integer idPrgSolicitudPermiso, Date creado) {
        this.idPrgSolicitudPermiso = idPrgSolicitudPermiso;
        this.creado = creado;
    }

    public Integer getIdPrgSolicitudPermiso() {
        return idPrgSolicitudPermiso;
    }

    public void setIdPrgSolicitudPermiso(Integer idPrgSolicitudPermiso) {
        this.idPrgSolicitudPermiso = idPrgSolicitudPermiso;
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

    public PrgSolicitud getIdPrgSolicitud() {
        return idPrgSolicitud;
    }

    public void setIdPrgSolicitud(PrgSolicitud idPrgSolicitud) {
        this.idPrgSolicitud = idPrgSolicitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgSolicitudPermiso != null ? idPrgSolicitudPermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgSolicitudPermiso)) {
            return false;
        }
        PrgSolicitudPermiso other = (PrgSolicitudPermiso) object;
        if ((this.idPrgSolicitudPermiso == null && other.idPrgSolicitudPermiso != null) || (this.idPrgSolicitudPermiso != null && !this.idPrgSolicitudPermiso.equals(other.idPrgSolicitudPermiso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgSolicitudPermiso[ idPrgSolicitudPermiso=" + idPrgSolicitudPermiso + " ]";
    }
    
}
