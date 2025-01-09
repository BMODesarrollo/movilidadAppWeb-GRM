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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "atv_location_sugerida_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtvLocationSugeridaDet.findAll", query = "SELECT a FROM AtvLocationSugeridaDet a")
    , @NamedQuery(name = "AtvLocationSugeridaDet.findByIdAtvLocationSugeridaDet", query = "SELECT a FROM AtvLocationSugeridaDet a WHERE a.idAtvLocationSugeridaDet = :idAtvLocationSugeridaDet")
    , @NamedQuery(name = "AtvLocationSugeridaDet.findByLatitud", query = "SELECT a FROM AtvLocationSugeridaDet a WHERE a.latitud = :latitud")
    , @NamedQuery(name = "AtvLocationSugeridaDet.findByLongitud", query = "SELECT a FROM AtvLocationSugeridaDet a WHERE a.longitud = :longitud")})
public class AtvLocationSugeridaDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atv_location_sugerida_det")
    private Integer idAtvLocationSugeridaDet;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitud")
    private Float latitud;
    @Column(name = "longitud")
    private Float longitud;
    @JoinColumn(name = "id_atv_location_sugerida", referencedColumnName = "id_atv_location_sugerida")
    @ManyToOne(fetch = FetchType.LAZY)
    private AtvLocationSugerida idAtvLocationSugerida;

    public AtvLocationSugeridaDet() {
    }

    public AtvLocationSugeridaDet(Integer idAtvLocationSugeridaDet) {
        this.idAtvLocationSugeridaDet = idAtvLocationSugeridaDet;
    }

    public Integer getIdAtvLocationSugeridaDet() {
        return idAtvLocationSugeridaDet;
    }

    public void setIdAtvLocationSugeridaDet(Integer idAtvLocationSugeridaDet) {
        this.idAtvLocationSugeridaDet = idAtvLocationSugeridaDet;
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

    public AtvLocationSugerida getIdAtvLocationSugerida() {
        return idAtvLocationSugerida;
    }

    public void setIdAtvLocationSugerida(AtvLocationSugerida idAtvLocationSugerida) {
        this.idAtvLocationSugerida = idAtvLocationSugerida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtvLocationSugeridaDet != null ? idAtvLocationSugeridaDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtvLocationSugeridaDet)) {
            return false;
        }
        AtvLocationSugeridaDet other = (AtvLocationSugeridaDet) object;
        if ((this.idAtvLocationSugeridaDet == null && other.idAtvLocationSugeridaDet != null) || (this.idAtvLocationSugeridaDet != null && !this.idAtvLocationSugeridaDet.equals(other.idAtvLocationSugeridaDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AtvLocationSugeridaDet[ idAtvLocationSugeridaDet=" + idAtvLocationSugeridaDet + " ]";
    }
    
}
