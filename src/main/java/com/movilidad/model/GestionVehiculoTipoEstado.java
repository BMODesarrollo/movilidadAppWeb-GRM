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
@Table(name = "gestion_vehiculo_tipo_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GestionVehiculoTipoEstado.findAll", query = "SELECT l FROM GestionVehiculoTipoEstado l"),
    @NamedQuery(name = "GestionVehiculoTipoEstado.findByIdGestionVehiculoTipoEstado", query = "SELECT l FROM GestionVehiculoTipoEstado l WHERE l.idGestionVehiculoTipoEstado = :idGestionVehiculoTipoEstado"),
    @NamedQuery(name = "GestionVehiculoTipoEstado.findByUsername", query = "SELECT l FROM GestionVehiculoTipoEstado l WHERE l.username = :username"),
    @NamedQuery(name = "GestionVehiculoTipoEstado.findByCreado", query = "SELECT l FROM GestionVehiculoTipoEstado l WHERE l.creado = :creado"),
    @NamedQuery(name = "GestionVehiculoTipoEstado.findByModificado", query = "SELECT l FROM GestionVehiculoTipoEstado l WHERE l.modificado = :modificado"),
    @NamedQuery(name = "GestionVehiculoTipoEstado.findByEstadoReg", query = "SELECT l FROM GestionVehiculoTipoEstado l WHERE l.estadoReg = :estadoReg")})
public class GestionVehiculoTipoEstado implements Serializable {

    @OneToMany(mappedBy = "idGestionVehiculoEstado", fetch = FetchType.LAZY)
    private List<GestionVehiculo> gestionVehiculo;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gestion_vehiculo_tipo_estado")
    private Integer idGestionVehiculoTipoEstado;
    @Size(max = 45)
    @Column(name = "nombre_tipo_estado")
    private String nombreTipoEstado;
    @Size(max = 45)
    @Column(name = "descripcion_tipo_estado")
    private String descripcionTipoEstado;
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

    public GestionVehiculoTipoEstado() {
    }

    public Integer getIdGestionVehiculoTipoEstado() {
        return idGestionVehiculoTipoEstado;
    }

    public void setIdGestionVehiculoTipoEstado(Integer idGestionVehiculoTipoEstado) {
        this.idGestionVehiculoTipoEstado = idGestionVehiculoTipoEstado;
    }

    public String getNombreTipoEstado() {
        return nombreTipoEstado;
    }

    public void setNombreTipoEstado(String nombreTipoEstado) {
        this.nombreTipoEstado = nombreTipoEstado;
    }

    public String getDescripcionTipoEstado() {
        return descripcionTipoEstado;
    }

    public void setDescripcionTipoEstado(String descripcionTipoEstado) {
        this.descripcionTipoEstado = descripcionTipoEstado;
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
        hash += (idGestionVehiculoTipoEstado != null ? idGestionVehiculoTipoEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestionVehiculoTipoEstado)) {
            return false;
        }
        GestionVehiculoTipoEstado other = (GestionVehiculoTipoEstado) object;
        if ((this.idGestionVehiculoTipoEstado == null && other.idGestionVehiculoTipoEstado != null) || (this.idGestionVehiculoTipoEstado != null && !this.idGestionVehiculoTipoEstado.equals(other.idGestionVehiculoTipoEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GestionVehiculoTipoEstado[ idGestionVehiculoTipoEstado=" + idGestionVehiculoTipoEstado + " ]";
    }

}
