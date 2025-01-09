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
 * @author soluciones-it
 */
@Entity
@Table(name = "atv_location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtvLocation.findAll", query = "SELECT a FROM AtvLocation a")
    , @NamedQuery(name = "AtvLocation.findByIdAtvLocation", query = "SELECT a FROM AtvLocation a WHERE a.idAtvLocation = :idAtvLocation")
    , @NamedQuery(name = "AtvLocation.findByLatitud", query = "SELECT a FROM AtvLocation a WHERE a.latitud = :latitud")
    , @NamedQuery(name = "AtvLocation.findByLongitud", query = "SELECT a FROM AtvLocation a WHERE a.longitud = :longitud")
    , @NamedQuery(name = "AtvLocation.findByFecha", query = "SELECT a FROM AtvLocation a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "AtvLocation.findByUsername", query = "SELECT a FROM AtvLocation a WHERE a.username = :username")})
public class AtvLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atv_location")
    private Integer idAtvLocation;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitud")
    private Float latitud;
    @Column(name = "longitud")
    private Float longitud;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 45)
    @Column(name = "username")
    private String username;
    @JoinColumn(name = "id_atv_tipo_estado", referencedColumnName = "id_atv_tipo_estado")
    @ManyToOne(fetch = FetchType.LAZY)
    private AtvTipoEstado idAtvTipoEstado;
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private Novedad idNovedad;

    public AtvLocation() {
    }

    public AtvLocation(Integer idAtvLocation) {
        this.idAtvLocation = idAtvLocation;
    }

    public Integer getIdAtvLocation() {
        return idAtvLocation;
    }

    public void setIdAtvLocation(Integer idAtvLocation) {
        this.idAtvLocation = idAtvLocation;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AtvTipoEstado getIdAtvTipoEstado() {
        return idAtvTipoEstado;
    }

    public void setIdAtvTipoEstado(AtvTipoEstado idAtvTipoEstado) {
        this.idAtvTipoEstado = idAtvTipoEstado;
    }

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtvLocation != null ? idAtvLocation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtvLocation)) {
            return false;
        }
        AtvLocation other = (AtvLocation) object;
        if ((this.idAtvLocation == null && other.idAtvLocation != null) || (this.idAtvLocation != null && !this.idAtvLocation.equals(other.idAtvLocation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AtvLocation[ idAtvLocation=" + idAtvLocation + " ]";
    }
    
}
