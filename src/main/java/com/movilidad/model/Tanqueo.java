/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "tanqueo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tanqueo.findAll", query = "SELECT t FROM Tanqueo t")
    , @NamedQuery(name = "Tanqueo.findByIdTanqueo", query = "SELECT t FROM Tanqueo t WHERE t.idTanqueo = :idTanqueo")
    , @NamedQuery(name = "Tanqueo.findByFecha", query = "SELECT t FROM Tanqueo t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "Tanqueo.findByKmInicial", query = "SELECT t FROM Tanqueo t WHERE t.kmInicial = :kmInicial")
    , @NamedQuery(name = "Tanqueo.findByKmFinal", query = "SELECT t FROM Tanqueo t WHERE t.kmFinal = :kmFinal")
    , @NamedQuery(name = "Tanqueo.findByVolumen", query = "SELECT t FROM Tanqueo t WHERE t.volumen = :volumen")
    , @NamedQuery(name = "Tanqueo.findByAlerta", query = "SELECT t FROM Tanqueo t WHERE t.alerta = :alerta")
    , @NamedQuery(name = "Tanqueo.findByUsername", query = "SELECT t FROM Tanqueo t WHERE t.username = :username")
    , @NamedQuery(name = "Tanqueo.findByCreado", query = "SELECT t FROM Tanqueo t WHERE t.creado = :creado")
    , @NamedQuery(name = "Tanqueo.findByModificado", query = "SELECT t FROM Tanqueo t WHERE t.modificado = :modificado")
    , @NamedQuery(name = "Tanqueo.findByEstadoReg", query = "SELECT t FROM Tanqueo t WHERE t.estadoReg = :estadoReg")})
public class Tanqueo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tanqueo")
    private Integer idTanqueo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "km_inicial")
    private int kmInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "km_final")
    private int kmFinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "volumen")
    private BigDecimal volumen;
    @Basic(optional = false)
    @NotNull
    @Column(name = "alerta")
    private int alerta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
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
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;

    public Tanqueo() {
    }

    public Tanqueo(Integer idTanqueo) {
        this.idTanqueo = idTanqueo;
    }

    public Tanqueo(Integer idTanqueo, Date fecha, int kmInicial, int kmFinal, BigDecimal volumen, int alerta, String username, Date creado, int estadoReg) {
        this.idTanqueo = idTanqueo;
        this.fecha = fecha;
        this.kmInicial = kmInicial;
        this.kmFinal = kmFinal;
        this.volumen = volumen;
        this.alerta = alerta;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdTanqueo() {
        return idTanqueo;
    }

    public void setIdTanqueo(Integer idTanqueo) {
        this.idTanqueo = idTanqueo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getKmInicial() {
        return kmInicial;
    }

    public void setKmInicial(int kmInicial) {
        this.kmInicial = kmInicial;
    }

    public int getKmFinal() {
        return kmFinal;
    }

    public void setKmFinal(int kmFinal) {
        this.kmFinal = kmFinal;
    }

    public BigDecimal getVolumen() {
        return volumen;
    }

    public void setVolumen(BigDecimal volumen) {
        this.volumen = volumen;
    }

    public int getAlerta() {
        return alerta;
    }

    public void setAlerta(int alerta) {
        this.alerta = alerta;
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

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTanqueo != null ? idTanqueo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tanqueo)) {
            return false;
        }
        Tanqueo other = (Tanqueo) object;
        if ((this.idTanqueo == null && other.idTanqueo != null) || (this.idTanqueo != null && !this.idTanqueo.equals(other.idTanqueo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.Tanqueo[ idTanqueo=" + idTanqueo + " ]";
    }
    
}
