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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cesar
 */
@Entity
@Table(name = "cable_acc_plan_accion")
@XmlRootElement
public class CableAccPlanAccion implements Serializable {

    @JoinColumn(name = "id_cable_acc_responsable", referencedColumnName = "id_cable_acc_responsable")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccResponsable idCableAccResponsable;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_acc_plan_accion")
    private Integer idCableAccPlanAccion;
    @Size(max = 60)
    @Column(name = "actividad")
    private String actividad;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 255)
    @Column(name = "observacion")
    private String observacion;
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
    @JoinColumn(name = "id_cable_accidentalidad", referencedColumnName = "id_cable_accidentalidad")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccidentalidad idCableAccidentalidad;

    public CableAccPlanAccion() {
    }

    public CableAccPlanAccion(Integer idCableAccPlanAccion) {
        this.idCableAccPlanAccion = idCableAccPlanAccion;
    }

    public Integer getIdCableAccPlanAccion() {
        return idCableAccPlanAccion;
    }

    public void setIdCableAccPlanAccion(Integer idCableAccPlanAccion) {
        this.idCableAccPlanAccion = idCableAccPlanAccion;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public CableAccidentalidad getIdCableAccidentalidad() {
        return idCableAccidentalidad;
    }

    public void setIdCableAccidentalidad(CableAccidentalidad idCableAccidentalidad) {
        this.idCableAccidentalidad = idCableAccidentalidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableAccPlanAccion != null ? idCableAccPlanAccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableAccPlanAccion)) {
            return false;
        }
        CableAccPlanAccion other = (CableAccPlanAccion) object;
        if ((this.idCableAccPlanAccion == null && other.idCableAccPlanAccion != null) || (this.idCableAccPlanAccion != null && !this.idCableAccPlanAccion.equals(other.idCableAccPlanAccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableAccPlanAccion[ idCableAccPlanAccion=" + idCableAccPlanAccion + " ]";
    }

    public CableAccResponsable getIdCableAccResponsable() {
        return idCableAccResponsable;
    }

    public void setIdCableAccResponsable(CableAccResponsable idCableAccResponsable) {
        this.idCableAccResponsable = idCableAccResponsable;
    }

}
