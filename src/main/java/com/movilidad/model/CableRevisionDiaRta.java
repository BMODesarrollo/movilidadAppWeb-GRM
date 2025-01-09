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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "cable_revision_dia_rta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableRevisionDiaRta.findAll", query = "SELECT c FROM CableRevisionDiaRta c"),
    @NamedQuery(name = "CableRevisionDiaRta.findByIdCableRevisionDiaRta", query = "SELECT c FROM CableRevisionDiaRta c WHERE c.idCableRevisionDiaRta = :idCableRevisionDiaRta"),
    @NamedQuery(name = "CableRevisionDiaRta.findByRespuesta", query = "SELECT c FROM CableRevisionDiaRta c WHERE c.respuesta = :respuesta"),
    @NamedQuery(name = "CableRevisionDiaRta.findByUsername", query = "SELECT c FROM CableRevisionDiaRta c WHERE c.username = :username"),
    @NamedQuery(name = "CableRevisionDiaRta.findByCreado", query = "SELECT c FROM CableRevisionDiaRta c WHERE c.creado = :creado"),
    @NamedQuery(name = "CableRevisionDiaRta.findByModificado", query = "SELECT c FROM CableRevisionDiaRta c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "CableRevisionDiaRta.findByEstadoReg", query = "SELECT c FROM CableRevisionDiaRta c WHERE c.estadoReg = :estadoReg")})
public class CableRevisionDiaRta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_revision_dia_rta")
    private Integer idCableRevisionDiaRta;
    @Column(name = "respuesta")
    private Integer respuesta;
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
    @JoinColumn(name = "id_cable_revision_dia", referencedColumnName = "id_cable_revision_dia")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableRevisionDia idCableRevisionDia;
    @JoinColumn(name = "id_cable_revision_dia_horario", referencedColumnName = "id_cable_revision_dia_horario")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableRevisionDiaHorario idCableRevisionDiaHorario;
    @JoinColumn(name = "id_cable_revision_estacion", referencedColumnName = "id_cable_revision_estacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableRevisionEstacion idCableRevisionEstacion;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;

    public CableRevisionDiaRta() {
    }

    public CableRevisionDiaRta(Integer idCableRevisionDiaRta) {
        this.idCableRevisionDiaRta = idCableRevisionDiaRta;
    }

    public Integer getIdCableRevisionDiaRta() {
        return idCableRevisionDiaRta;
    }

    public void setIdCableRevisionDiaRta(Integer idCableRevisionDiaRta) {
        this.idCableRevisionDiaRta = idCableRevisionDiaRta;
    }

    public Integer getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Integer respuesta) {
        this.respuesta = respuesta;
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

    public CableRevisionDia getIdCableRevisionDia() {
        return idCableRevisionDia;
    }

    public void setIdCableRevisionDia(CableRevisionDia idCableRevisionDia) {
        this.idCableRevisionDia = idCableRevisionDia;
    }

    public CableRevisionDiaHorario getIdCableRevisionDiaHorario() {
        return idCableRevisionDiaHorario;
    }

    public void setIdCableRevisionDiaHorario(CableRevisionDiaHorario idCableRevisionDiaHorario) {
        this.idCableRevisionDiaHorario = idCableRevisionDiaHorario;
    }

    public CableRevisionEstacion getIdCableRevisionEstacion() {
        return idCableRevisionEstacion;
    }

    public void setIdCableRevisionEstacion(CableRevisionEstacion idCableRevisionEstacion) {
        this.idCableRevisionEstacion = idCableRevisionEstacion;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableRevisionDiaRta != null ? idCableRevisionDiaRta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableRevisionDiaRta)) {
            return false;
        }
        CableRevisionDiaRta other = (CableRevisionDiaRta) object;
        if ((this.idCableRevisionDiaRta == null && other.idCableRevisionDiaRta != null) || (this.idCableRevisionDiaRta != null && !this.idCableRevisionDiaRta.equals(other.idCableRevisionDiaRta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableRevisionDiaRta[ idCableRevisionDiaRta=" + idCableRevisionDiaRta + " ]";
    }
    
}
