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
 * @author jjunco
 */
@Entity
@Table(name = "gestion_vehiculo_ubicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GestionVehiculoUbicacion.findAll", query = "SELECT l FROM GestionVehiculoUbicacion l"),
    @NamedQuery(name = "GestionVehiculoUbicacion.findByIdGestionVehiculoUbicacion", query = "SELECT l FROM GestionVehiculoUbicacion l WHERE l.idGestionVehiculoUbicacion = :idGestionVehiculoUbicacion"),
    @NamedQuery(name = "GestionVehiculoUbicacion.findByUsername", query = "SELECT l FROM GestionVehiculoUbicacion l WHERE l.username = :username"),
    @NamedQuery(name = "GestionVehiculoUbicacion.findByCreado", query = "SELECT l FROM GestionVehiculoUbicacion l WHERE l.creado = :creado"),
    @NamedQuery(name = "GestionVehiculoUbicacion.findByModificado", query = "SELECT l FROM GestionVehiculoUbicacion l WHERE l.modificado = :modificado"),
    @NamedQuery(name = "GestionVehiculoUbicacion.findByEstadoReg", query = "SELECT l FROM GestionVehiculoUbicacion l WHERE l.estadoReg = :estadoReg")})
public class GestionVehiculoUbicacion implements Serializable {

    @OneToMany(mappedBy = "idGestionVehiculoUbicacion", fetch = FetchType.LAZY)
    private List<GestionVehiculo> gestionVehiculo;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gestion_vehiculo_ubicacion")
    private Integer idGestionVehiculoUbicacion;
    @Size(max = 45)
    @Column(name = "posicion")
    private String posicion;
    @Column(name = "id_gop_unidad_funcional")
    private Integer idGopUnidadFuncional;
    @Size(max = 45)
    @Column(name = "canopy")
    private String canopy;
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

    public GestionVehiculoUbicacion() {
    }

    public Integer getIdGestionVehiculoUbicacion() {
        return idGestionVehiculoUbicacion;
    }

    public void setIdGestionVehiculoUbicacion(Integer idGestionVehiculoUbicacion) {
        this.idGestionVehiculoUbicacion = idGestionVehiculoUbicacion;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Integer getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(Integer idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public String getCanopy() {
        return canopy;
    }

    public void setCanopy(String canopy) {
        this.canopy = canopy;
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

    @XmlTransient
    public List<GestionVehiculo> getGestionVehiculo() {
        return gestionVehiculo;
    }

    public void setGestionVehiculo(List<GestionVehiculo> gestionVehiculo) {
        this.gestionVehiculo = gestionVehiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGestionVehiculoUbicacion != null ? idGestionVehiculoUbicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestionVehiculoUbicacion)) {
            return false;
        }
        GestionVehiculoUbicacion other = (GestionVehiculoUbicacion) object;
        if ((this.idGestionVehiculoUbicacion == null && other.idGestionVehiculoUbicacion != null) || (this.idGestionVehiculoUbicacion != null && !this.idGestionVehiculoUbicacion.equals(other.idGestionVehiculoUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GestionVehiculoUbicacion[ idGestionVehiculoUbicacion=" + idGestionVehiculoUbicacion + " ]";
    }

}
