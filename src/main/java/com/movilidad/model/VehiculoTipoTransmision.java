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
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "vehiculo_tipo_transmision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoTipoTransmision.findAll", query = "SELECT v FROM VehiculoTipoTransmision v")
    , @NamedQuery(name = "VehiculoTipoTransmision.findByIdVehiculoTipoTransmision", query = "SELECT v FROM VehiculoTipoTransmision v WHERE v.idVehiculoTipoTransmision = :idVehiculoTipoTransmision")
    , @NamedQuery(name = "VehiculoTipoTransmision.findByNombreTipoTransmision", query = "SELECT v FROM VehiculoTipoTransmision v WHERE v.nombreTipoTransmision = :nombreTipoTransmision")
    , @NamedQuery(name = "VehiculoTipoTransmision.findByDescripcionTipoTransmision", query = "SELECT v FROM VehiculoTipoTransmision v WHERE v.descripcionTipoTransmision = :descripcionTipoTransmision")
    , @NamedQuery(name = "VehiculoTipoTransmision.findByUsername", query = "SELECT v FROM VehiculoTipoTransmision v WHERE v.username = :username")
    , @NamedQuery(name = "VehiculoTipoTransmision.findByCreado", query = "SELECT v FROM VehiculoTipoTransmision v WHERE v.creado = :creado")
    , @NamedQuery(name = "VehiculoTipoTransmision.findByModificado", query = "SELECT v FROM VehiculoTipoTransmision v WHERE v.modificado = :modificado")
    , @NamedQuery(name = "VehiculoTipoTransmision.findByEstadoReg", query = "SELECT v FROM VehiculoTipoTransmision v WHERE v.estadoReg = :estadoReg")})
public class VehiculoTipoTransmision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_tipo_transmision")
    private Integer idVehiculoTipoTransmision;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_tipo_transmision")
    private String nombreTipoTransmision;
    @Size(max = 100)
    @Column(name = "descripcion_tipo_transmision")
    private String descripcionTipoTransmision;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculoTransmision", fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculoList;

    public VehiculoTipoTransmision() {
    }

    public VehiculoTipoTransmision(Integer idVehiculoTipoTransmision) {
        this.idVehiculoTipoTransmision = idVehiculoTipoTransmision;
    }

    public VehiculoTipoTransmision(Integer idVehiculoTipoTransmision, String nombreTipoTransmision, String username, int estadoReg) {
        this.idVehiculoTipoTransmision = idVehiculoTipoTransmision;
        this.nombreTipoTransmision = nombreTipoTransmision;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdVehiculoTipoTransmision() {
        return idVehiculoTipoTransmision;
    }

    public void setIdVehiculoTipoTransmision(Integer idVehiculoTipoTransmision) {
        this.idVehiculoTipoTransmision = idVehiculoTipoTransmision;
    }

    public String getNombreTipoTransmision() {
        return nombreTipoTransmision;
    }

    public void setNombreTipoTransmision(String nombreTipoTransmision) {
        this.nombreTipoTransmision = nombreTipoTransmision;
    }

    public String getDescripcionTipoTransmision() {
        return descripcionTipoTransmision;
    }

    public void setDescripcionTipoTransmision(String descripcionTipoTransmision) {
        this.descripcionTipoTransmision = descripcionTipoTransmision;
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

    @XmlTransient
    public List<Vehiculo> getVehiculoList() {
        return vehiculoList;
    }

    public void setVehiculoList(List<Vehiculo> vehiculoList) {
        this.vehiculoList = vehiculoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVehiculoTipoTransmision != null ? idVehiculoTipoTransmision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoTipoTransmision)) {
            return false;
        }
        VehiculoTipoTransmision other = (VehiculoTipoTransmision) object;
        if ((this.idVehiculoTipoTransmision == null && other.idVehiculoTipoTransmision != null) || (this.idVehiculoTipoTransmision != null && !this.idVehiculoTipoTransmision.equals(other.idVehiculoTipoTransmision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoTipoTransmision[ idVehiculoTipoTransmision=" + idVehiculoTipoTransmision + " ]";
    }
    
}