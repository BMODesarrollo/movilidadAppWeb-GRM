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
 * @author soluciones-it
 */
@Entity
@Table(name = "atv_location_sugerida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtvLocationSugerida.findAll", query = "SELECT a FROM AtvLocationSugerida a")
    , @NamedQuery(name = "AtvLocationSugerida.findByIdAtvLocationSugerida", query = "SELECT a FROM AtvLocationSugerida a WHERE a.idAtvLocationSugerida = :idAtvLocationSugerida")
    , @NamedQuery(name = "AtvLocationSugerida.findByLatitudStart", query = "SELECT a FROM AtvLocationSugerida a WHERE a.latitudStart = :latitudStart")
    , @NamedQuery(name = "AtvLocationSugerida.findByLongitudStart", query = "SELECT a FROM AtvLocationSugerida a WHERE a.longitudStart = :longitudStart")
    , @NamedQuery(name = "AtvLocationSugerida.findByLatitudEnd", query = "SELECT a FROM AtvLocationSugerida a WHERE a.latitudEnd = :latitudEnd")
    , @NamedQuery(name = "AtvLocationSugerida.findByLongitudEnd", query = "SELECT a FROM AtvLocationSugerida a WHERE a.longitudEnd = :longitudEnd")
    , @NamedQuery(name = "AtvLocationSugerida.findByAddressStart", query = "SELECT a FROM AtvLocationSugerida a WHERE a.addressStart = :addressStart")
    , @NamedQuery(name = "AtvLocationSugerida.findByAddressEnd", query = "SELECT a FROM AtvLocationSugerida a WHERE a.addressEnd = :addressEnd")
    , @NamedQuery(name = "AtvLocationSugerida.findByDistance", query = "SELECT a FROM AtvLocationSugerida a WHERE a.distance = :distance")
    , @NamedQuery(name = "AtvLocationSugerida.findByDuration", query = "SELECT a FROM AtvLocationSugerida a WHERE a.duration = :duration")
    , @NamedQuery(name = "AtvLocationSugerida.findByFecha", query = "SELECT a FROM AtvLocationSugerida a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "AtvLocationSugerida.findByUsername", query = "SELECT a FROM AtvLocationSugerida a WHERE a.username = :username")})
public class AtvLocationSugerida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atv_location_sugerida")
    private Integer idAtvLocationSugerida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitud_start")
    private Float latitudStart;
    @Column(name = "longitud_start")
    private Float longitudStart;
    @Column(name = "latitud_end")
    private Float latitudEnd;
    @Column(name = "longitud_end")
    private Float longitudEnd;
    @Size(max = 255)
    @Column(name = "address_start")
    private String addressStart;
    @Size(max = 255)
    @Column(name = "address_end")
    private String addressEnd;
    @Column(name = "distance")
    private Integer distance;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @OneToMany(mappedBy = "idAtvLocationSugerida", fetch = FetchType.LAZY)
    private List<AtvLocationSugeridaDet> atvLocationSugeridaDetList;
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private Novedad idNovedad;

    @Column(name = "tipo_ruta")
    private Integer tipoRuta;

    public AtvLocationSugerida() {
    }

    public AtvLocationSugerida(Integer idAtvLocationSugerida) {
        this.idAtvLocationSugerida = idAtvLocationSugerida;
    }

    public Integer getIdAtvLocationSugerida() {
        return idAtvLocationSugerida;
    }

    public void setIdAtvLocationSugerida(Integer idAtvLocationSugerida) {
        this.idAtvLocationSugerida = idAtvLocationSugerida;
    }

    public Float getLatitudStart() {
        return latitudStart;
    }

    public void setLatitudStart(Float latitudStart) {
        this.latitudStart = latitudStart;
    }

    public Float getLongitudStart() {
        return longitudStart;
    }

    public void setLongitudStart(Float longitudStart) {
        this.longitudStart = longitudStart;
    }

    public Float getLatitudEnd() {
        return latitudEnd;
    }

    public void setLatitudEnd(Float latitudEnd) {
        this.latitudEnd = latitudEnd;
    }

    public Float getLongitudEnd() {
        return longitudEnd;
    }

    public void setLongitudEnd(Float longitudEnd) {
        this.longitudEnd = longitudEnd;
    }

    public String getAddressStart() {
        return addressStart;
    }

    public void setAddressStart(String addressStart) {
        this.addressStart = addressStart;
    }

    public String getAddressEnd() {
        return addressEnd;
    }

    public void setAddressEnd(String addressEnd) {
        this.addressEnd = addressEnd;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    @XmlTransient
    public List<AtvLocationSugeridaDet> getAtvLocationSugeridaDetList() {
        return atvLocationSugeridaDetList;
    }

    public void setAtvLocationSugeridaDetList(List<AtvLocationSugeridaDet> atvLocationSugeridaDetList) {
        this.atvLocationSugeridaDetList = atvLocationSugeridaDetList;
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
        hash += (idAtvLocationSugerida != null ? idAtvLocationSugerida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtvLocationSugerida)) {
            return false;
        }
        AtvLocationSugerida other = (AtvLocationSugerida) object;
        if ((this.idAtvLocationSugerida == null && other.idAtvLocationSugerida != null) || (this.idAtvLocationSugerida != null && !this.idAtvLocationSugerida.equals(other.idAtvLocationSugerida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AtvLocationSugerida[ idAtvLocationSugerida=" + idAtvLocationSugerida + " ]";
    }

    public Integer getTipoRuta() {
        return tipoRuta;
    }

    public void setTipoRuta(Integer tipoRuta) {
        this.tipoRuta = tipoRuta;
    }

}
