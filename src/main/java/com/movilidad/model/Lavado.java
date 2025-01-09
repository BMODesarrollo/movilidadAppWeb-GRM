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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "lavado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lavado.findAll", query = "SELECT l FROM Lavado l"),
    @NamedQuery(name = "Lavado.findByIdLavado", query = "SELECT l FROM Lavado l WHERE l.idLavado = :idLavado"),
    @NamedQuery(name = "Lavado.findByFechaHora", query = "SELECT l FROM Lavado l WHERE l.fechaHora = :fechaHora"),
    @NamedQuery(name = "Lavado.findByUsername", query = "SELECT l FROM Lavado l WHERE l.username = :username"),
    @NamedQuery(name = "Lavado.findByCreado", query = "SELECT l FROM Lavado l WHERE l.creado = :creado"),
    @NamedQuery(name = "Lavado.findByModificado", query = "SELECT l FROM Lavado l WHERE l.modificado = :modificado"),
    @NamedQuery(name = "Lavado.findByEstadoReg", query = "SELECT l FROM Lavado l WHERE l.estadoReg = :estadoReg")})
public class Lavado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lavado")
    private Integer idLavado;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
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
    @JoinColumn(name = "id_lavado_responsable", referencedColumnName = "id_lavado_responsable")
    @ManyToOne(optional = false)
    private LavadoResponsable idLavadoResponsable;
    @JoinColumn(name = "id_lavado_tipo", referencedColumnName = "id_lavado_tipo")
    @ManyToOne(optional = false)
    private LavadoTipo idLavadoTipo;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(optional = false)
    private Vehiculo idVehiculo;

    public Lavado() {
    }

    public Lavado(Integer idLavado) {
        this.idLavado = idLavado;
    }

    public Integer getIdLavado() {
        return idLavado;
    }

    public void setIdLavado(Integer idLavado) {
        this.idLavado = idLavado;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
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

    public LavadoResponsable getIdLavadoResponsable() {
        return idLavadoResponsable;
    }

    public void setIdLavadoResponsable(LavadoResponsable idLavadoResponsable) {
        this.idLavadoResponsable = idLavadoResponsable;
    }

    public LavadoTipo getIdLavadoTipo() {
        return idLavadoTipo;
    }

    public void setIdLavadoTipo(LavadoTipo idLavadoTipo) {
        this.idLavadoTipo = idLavadoTipo;
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
        hash += (idLavado != null ? idLavado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lavado)) {
            return false;
        }
        Lavado other = (Lavado) object;
        if ((this.idLavado == null && other.idLavado != null) || (this.idLavado != null && !this.idLavado.equals(other.idLavado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.Lavado[ idLavado=" + idLavado + " ]";
    }
    
}
