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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "vehiculo_tipo_combustible")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoTipoCombustible.findAll", query = "SELECT v FROM VehiculoTipoCombustible v")
    , @NamedQuery(name = "VehiculoTipoCombustible.findByIdVehiculoTipoCombustible", query = "SELECT v FROM VehiculoTipoCombustible v WHERE v.idVehiculoTipoCombustible = :idVehiculoTipoCombustible")
    , @NamedQuery(name = "VehiculoTipoCombustible.findByNombreTipoCombustible", query = "SELECT v FROM VehiculoTipoCombustible v WHERE v.nombreTipoCombustible = :nombreTipoCombustible")
    , @NamedQuery(name = "VehiculoTipoCombustible.findByDescripcionTipoCombustible", query = "SELECT v FROM VehiculoTipoCombustible v WHERE v.descripcionTipoCombustible = :descripcionTipoCombustible")
    , @NamedQuery(name = "VehiculoTipoCombustible.findByUsername", query = "SELECT v FROM VehiculoTipoCombustible v WHERE v.username = :username")
    , @NamedQuery(name = "VehiculoTipoCombustible.findByCreado", query = "SELECT v FROM VehiculoTipoCombustible v WHERE v.creado = :creado")
    , @NamedQuery(name = "VehiculoTipoCombustible.findByModificado", query = "SELECT v FROM VehiculoTipoCombustible v WHERE v.modificado = :modificado")
    , @NamedQuery(name = "VehiculoTipoCombustible.findByEstadoReg", query = "SELECT v FROM VehiculoTipoCombustible v WHERE v.estadoReg = :estadoReg")})
public class VehiculoTipoCombustible implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_tipo_combustible")
    private Integer idVehiculoTipoCombustible;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_tipo_combustible")
    private String nombreTipoCombustible;
    @Size(max = 100)
    @Column(name = "descripcion_tipo_combustible")
    private String descripcionTipoCombustible;
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
    @OneToMany(mappedBy = "idVehiculoCombustible", fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculoList;

    public VehiculoTipoCombustible() {
    }

    public VehiculoTipoCombustible(Integer idVehiculoTipoCombustible) {
        this.idVehiculoTipoCombustible = idVehiculoTipoCombustible;
    }

    public VehiculoTipoCombustible(Integer idVehiculoTipoCombustible, String nombreTipoCombustible, String username, int estadoReg) {
        this.idVehiculoTipoCombustible = idVehiculoTipoCombustible;
        this.nombreTipoCombustible = nombreTipoCombustible;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdVehiculoTipoCombustible() {
        return idVehiculoTipoCombustible;
    }

    public void setIdVehiculoTipoCombustible(Integer idVehiculoTipoCombustible) {
        this.idVehiculoTipoCombustible = idVehiculoTipoCombustible;
    }

    public String getNombreTipoCombustible() {
        return nombreTipoCombustible;
    }

    public void setNombreTipoCombustible(String nombreTipoCombustible) {
        this.nombreTipoCombustible = nombreTipoCombustible;
    }

    public String getDescripcionTipoCombustible() {
        return descripcionTipoCombustible;
    }

    public void setDescripcionTipoCombustible(String descripcionTipoCombustible) {
        this.descripcionTipoCombustible = descripcionTipoCombustible;
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
        hash += (idVehiculoTipoCombustible != null ? idVehiculoTipoCombustible.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoTipoCombustible)) {
            return false;
        }
        VehiculoTipoCombustible other = (VehiculoTipoCombustible) object;
        if ((this.idVehiculoTipoCombustible == null && other.idVehiculoTipoCombustible != null) || (this.idVehiculoTipoCombustible != null && !this.idVehiculoTipoCombustible.equals(other.idVehiculoTipoCombustible))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoTipoCombustible[ idVehiculoTipoCombustible=" + idVehiculoTipoCombustible + " ]";
    }
    
}
