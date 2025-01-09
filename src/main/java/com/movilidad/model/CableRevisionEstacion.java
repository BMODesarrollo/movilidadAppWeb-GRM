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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "cable_revision_estacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableRevisionEstacion.findAll", query = "SELECT c FROM CableRevisionEstacion c"),
    @NamedQuery(name = "CableRevisionEstacion.findByIdCableRevisionEstacion", query = "SELECT c FROM CableRevisionEstacion c WHERE c.idCableRevisionEstacion = :idCableRevisionEstacion"),
    @NamedQuery(name = "CableRevisionEstacion.findByUsername", query = "SELECT c FROM CableRevisionEstacion c WHERE c.username = :username"),
    @NamedQuery(name = "CableRevisionEstacion.findByCreado", query = "SELECT c FROM CableRevisionEstacion c WHERE c.creado = :creado"),
    @NamedQuery(name = "CableRevisionEstacion.findByModificado", query = "SELECT c FROM CableRevisionEstacion c WHERE c.modificado = :modificado"),
    @NamedQuery(name = "CableRevisionEstacion.findByEstadoReg", query = "SELECT c FROM CableRevisionEstacion c WHERE c.estadoReg = :estadoReg")})
public class CableRevisionEstacion implements Serializable {
    @OneToMany(mappedBy = "idCableRevisionEstacion", fetch = FetchType.LAZY)
    private List<CableRevisionDiaRta> cableRevisionDiaRtaList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_revision_estacion")
    private Integer idCableRevisionEstacion;
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
    @JoinColumn(name = "id_cable_estacion", referencedColumnName = "id_cable_estacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableEstacion idCableEstacion;
    @JoinColumn(name = "id_cable_revision_actividad", referencedColumnName = "id_cable_revision_actividad")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableRevisionActividad idCableRevisionActividad;
    @JoinColumn(name = "id_cable_revision_equipo", referencedColumnName = "id_cable_revision_equipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableRevisionEquipo idCableRevisionEquipo;

    public CableRevisionEstacion() {
    }

    public CableRevisionEstacion(Integer idCableRevisionEstacion) {
        this.idCableRevisionEstacion = idCableRevisionEstacion;
    }

    public Integer getIdCableRevisionEstacion() {
        return idCableRevisionEstacion;
    }

    public void setIdCableRevisionEstacion(Integer idCableRevisionEstacion) {
        this.idCableRevisionEstacion = idCableRevisionEstacion;
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

    public CableEstacion getIdCableEstacion() {
        return idCableEstacion;
    }

    public void setIdCableEstacion(CableEstacion idCableEstacion) {
        this.idCableEstacion = idCableEstacion;
    }

    public CableRevisionActividad getIdCableRevisionActividad() {
        return idCableRevisionActividad;
    }

    public void setIdCableRevisionActividad(CableRevisionActividad idCableRevisionActividad) {
        this.idCableRevisionActividad = idCableRevisionActividad;
    }

    public CableRevisionEquipo getIdCableRevisionEquipo() {
        return idCableRevisionEquipo;
    }

    public void setIdCableRevisionEquipo(CableRevisionEquipo idCableRevisionEquipo) {
        this.idCableRevisionEquipo = idCableRevisionEquipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableRevisionEstacion != null ? idCableRevisionEstacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableRevisionEstacion)) {
            return false;
        }
        CableRevisionEstacion other = (CableRevisionEstacion) object;
        if ((this.idCableRevisionEstacion == null && other.idCableRevisionEstacion != null) || (this.idCableRevisionEstacion != null && !this.idCableRevisionEstacion.equals(other.idCableRevisionEstacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableRevisionEstacion[ idCableRevisionEstacion=" + idCableRevisionEstacion + " ]";
    }

    @XmlTransient
    public List<CableRevisionDiaRta> getCableRevisionDiaRtaList() {
        return cableRevisionDiaRtaList;
    }

    public void setCableRevisionDiaRtaList(List<CableRevisionDiaRta> cableRevisionDiaRtaList) {
        this.cableRevisionDiaRtaList = cableRevisionDiaRtaList;
    }
}
