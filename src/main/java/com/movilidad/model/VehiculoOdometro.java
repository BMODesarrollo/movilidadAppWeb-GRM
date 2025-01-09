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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "vehiculo_odometro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoOdometro.findAll", query = "SELECT v FROM VehiculoOdometro v"),
    @NamedQuery(name = "VehiculoOdometro.findByIdVehiculoOdometro", query = "SELECT v FROM VehiculoOdometro v WHERE v.idVehiculoOdometro = :idVehiculoOdometro"),
    @NamedQuery(name = "VehiculoOdometro.findByFecha", query = "SELECT v FROM VehiculoOdometro v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VehiculoOdometro.findByOdometro", query = "SELECT v FROM VehiculoOdometro v WHERE v.odometro = :odometro"),
    @NamedQuery(name = "VehiculoOdometro.findByUsername", query = "SELECT v FROM VehiculoOdometro v WHERE v.username = :username"),
    @NamedQuery(name = "VehiculoOdometro.findByCreado", query = "SELECT v FROM VehiculoOdometro v WHERE v.creado = :creado"),
    @NamedQuery(name = "VehiculoOdometro.findByModificado", query = "SELECT v FROM VehiculoOdometro v WHERE v.modificado = :modificado"),
    @NamedQuery(name = "VehiculoOdometro.findByEstadoReg", query = "SELECT v FROM VehiculoOdometro v WHERE v.estadoReg = :estadoReg")})
public class VehiculoOdometro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_odometro")
    private Integer idVehiculoOdometro;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "odometro")
    private int odometro;
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
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(optional = false)
    private Vehiculo idVehiculo;

    public VehiculoOdometro() {
    }

    public VehiculoOdometro(Integer idVehiculoOdometro) {
        this.idVehiculoOdometro = idVehiculoOdometro;
    }

    public VehiculoOdometro(Integer idVehiculoOdometro, int odometro) {
        this.idVehiculoOdometro = idVehiculoOdometro;
        this.odometro = odometro;
    }

    public Integer getIdVehiculoOdometro() {
        return idVehiculoOdometro;
    }

    public void setIdVehiculoOdometro(Integer idVehiculoOdometro) {
        this.idVehiculoOdometro = idVehiculoOdometro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getOdometro() {
        return odometro;
    }

    public void setOdometro(int odometro) {
        this.odometro = odometro;
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

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVehiculoOdometro != null ? idVehiculoOdometro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoOdometro)) {
            return false;
        }
        VehiculoOdometro other = (VehiculoOdometro) object;
        if ((this.idVehiculoOdometro == null && other.idVehiculoOdometro != null) || (this.idVehiculoOdometro != null && !this.idVehiculoOdometro.equals(other.idVehiculoOdometro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoOdometro[ idVehiculoOdometro=" + idVehiculoOdometro + " ]";
    }
    
}
