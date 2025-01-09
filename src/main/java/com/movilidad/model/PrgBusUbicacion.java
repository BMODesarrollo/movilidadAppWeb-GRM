/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "prg_bus_ubicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgBusUbicacion.findAll", query = "SELECT p FROM PrgBusUbicacion p")
    , @NamedQuery(name = "PrgBusUbicacion.findByIdPrgBusUbicacion", query = "SELECT p FROM PrgBusUbicacion p WHERE p.idPrgBusUbicacion = :idPrgBusUbicacion")
    , @NamedQuery(name = "PrgBusUbicacion.findByObservacion", query = "SELECT p FROM PrgBusUbicacion p WHERE p.observacion = :observacion")})
public class PrgBusUbicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_bus_ubicacion")
    private Integer idPrgBusUbicacion;
    @Size(max = 15)
    @Column(name = "observacion")
    private String observacion;
    @JoinColumn(name = "id_patio", referencedColumnName = "id_prg_stoppoint")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrgStopPoint idPatio;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;

    public PrgBusUbicacion() {
    }

    public PrgBusUbicacion(Integer idPrgBusUbicacion) {
        this.idPrgBusUbicacion = idPrgBusUbicacion;
    }

    public Integer getIdPrgBusUbicacion() {
        return idPrgBusUbicacion;
    }

    public void setIdPrgBusUbicacion(Integer idPrgBusUbicacion) {
        this.idPrgBusUbicacion = idPrgBusUbicacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public PrgStopPoint getIdPatio() {
        return idPatio;
    }

    public void setIdPatio(PrgStopPoint idPatio) {
        this.idPatio = idPatio;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgBusUbicacion != null ? idPrgBusUbicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgBusUbicacion)) {
            return false;
        }
        PrgBusUbicacion other = (PrgBusUbicacion) object;
        if ((this.idPrgBusUbicacion == null && other.idPrgBusUbicacion != null) || (this.idPrgBusUbicacion != null && !this.idPrgBusUbicacion.equals(other.idPrgBusUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgBusUbicacion[ idPrgBusUbicacion=" + idPrgBusUbicacion + " ]";
    }
    
}
