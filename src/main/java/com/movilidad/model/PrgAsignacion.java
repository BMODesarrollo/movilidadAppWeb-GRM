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
import javax.persistence.FetchType;
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
 * @author soluciones-it
 */
@Entity
@Table(name = "prg_asignacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgAsignacion.findAll", query = "SELECT p FROM PrgAsignacion p")
    , @NamedQuery(name = "PrgAsignacion.findByIdPrgAsignacion", query = "SELECT p FROM PrgAsignacion p WHERE p.idPrgAsignacion = :idPrgAsignacion")
    , @NamedQuery(name = "PrgAsignacion.findByFecha", query = "SELECT p FROM PrgAsignacion p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "PrgAsignacion.findByServbus", query = "SELECT p FROM PrgAsignacion p WHERE p.servbus = :servbus")
    , @NamedQuery(name = "PrgAsignacion.findByUsername", query = "SELECT p FROM PrgAsignacion p WHERE p.username = :username")
    , @NamedQuery(name = "PrgAsignacion.findByCreado", query = "SELECT p FROM PrgAsignacion p WHERE p.creado = :creado")
    , @NamedQuery(name = "PrgAsignacion.findByModificado", query = "SELECT p FROM PrgAsignacion p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PrgAsignacion.findByEstadoReg", query = "SELECT p FROM PrgAsignacion p WHERE p.estadoReg = :estadoReg")
    , @NamedQuery(name = "PrgAsignacion.findByTimeOrigin", query = "SELECT p FROM PrgAsignacion p WHERE p.timeOrigin = :timeOrigin")
    , @NamedQuery(name = "PrgAsignacion.findByTimeDestiny", query = "SELECT p FROM PrgAsignacion p WHERE p.timeDestiny = :timeDestiny")})
public class PrgAsignacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_asignacion")
    private Integer idPrgAsignacion;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 10)
    @Column(name = "servbus")
    private String servbus;
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
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;

    @Column(name = "time_origin")
    private String timeOrigin;
    @Size(max = 8)
    @Column(name = "time_destiny")
    private String timeDestiny;
    @JoinColumn(name = "id_mtto_tarea", referencedColumnName = "id_mtto_tarea")
    @ManyToOne(fetch = FetchType.LAZY)
    private MttoTarea idMttoTarea;

    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public PrgAsignacion() {
    }

    public PrgAsignacion(Integer idPrgAsignacion) {
        this.idPrgAsignacion = idPrgAsignacion;
    }

    public PrgAsignacion(Integer idPrgAsignacion, String username, Date creado, int estadoReg) {
        this.idPrgAsignacion = idPrgAsignacion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgAsignacion() {
        return idPrgAsignacion;
    }

    public void setIdPrgAsignacion(Integer idPrgAsignacion) {
        this.idPrgAsignacion = idPrgAsignacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
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

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
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

    public MttoTarea getIdMttoTarea() {
        return idMttoTarea;
    }

    public void setIdMttoTarea(MttoTarea idMttoTarea) {
        this.idMttoTarea = idMttoTarea;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgAsignacion != null ? idPrgAsignacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgAsignacion)) {
            return false;
        }
        PrgAsignacion other = (PrgAsignacion) object;
        if ((this.idPrgAsignacion == null && other.idPrgAsignacion != null) || (this.idPrgAsignacion != null && !this.idPrgAsignacion.equals(other.idPrgAsignacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgAsignacion[ idPrgAsignacion=" + idPrgAsignacion + " ]";
    }

}
