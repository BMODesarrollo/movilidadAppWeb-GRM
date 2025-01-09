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
@Table(name = "pm_tabla_premios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmTablaPremios.findAll", query = "SELECT p FROM PmTablaPremios p")
    , @NamedQuery(name = "PmTablaPremios.findByIdPmTablaPremios", query = "SELECT p FROM PmTablaPremios p WHERE p.idPmTablaPremios = :idPmTablaPremios")
    , @NamedQuery(name = "PmTablaPremios.findByDesde", query = "SELECT p FROM PmTablaPremios p WHERE p.desde = :desde")
    , @NamedQuery(name = "PmTablaPremios.findByHasta", query = "SELECT p FROM PmTablaPremios p WHERE p.hasta = :hasta")
    , @NamedQuery(name = "PmTablaPremios.findByPuntoMin", query = "SELECT p FROM PmTablaPremios p WHERE p.puntoMin = :puntoMin")
    , @NamedQuery(name = "PmTablaPremios.findByPuntoMax", query = "SELECT p FROM PmTablaPremios p WHERE p.puntoMax = :puntoMax")
    , @NamedQuery(name = "PmTablaPremios.findByPorcentaje", query = "SELECT p FROM PmTablaPremios p WHERE p.porcentaje = :porcentaje")
    , @NamedQuery(name = "PmTablaPremios.findByUsername", query = "SELECT p FROM PmTablaPremios p WHERE p.username = :username")
    , @NamedQuery(name = "PmTablaPremios.findByCreado", query = "SELECT p FROM PmTablaPremios p WHERE p.creado = :creado")
    , @NamedQuery(name = "PmTablaPremios.findByModificado", query = "SELECT p FROM PmTablaPremios p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PmTablaPremios.findByEstadoReg", query = "SELECT p FROM PmTablaPremios p WHERE p.estadoReg = :estadoReg")})
public class PmTablaPremios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_tabla_premios")
    private Integer idPmTablaPremios;
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Column(name = "posicion")
    private Integer posicion;
    @Column(name = "punto_min")
    private Integer puntoMin;
    @Column(name = "punto_max")
    private Integer puntoMax;
    @Column(name = "porcentaje")
    private Integer porcentaje;
    @Column(name = "otorgado")
    private Integer otorgado;
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

    public PmTablaPremios() {
    }

    public PmTablaPremios(Integer idPmTablaPremios) {
        this.idPmTablaPremios = idPmTablaPremios;
    }

    public Integer getIdPmTablaPremios() {
        return idPmTablaPremios;
    }

    public void setIdPmTablaPremios(Integer idPmTablaPremios) {
        this.idPmTablaPremios = idPmTablaPremios;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Integer getPuntoMin() {
        return puntoMin;
    }

    public void setPuntoMin(Integer puntoMin) {
        this.puntoMin = puntoMin;
    }

    public Integer getPuntoMax() {
        return puntoMax;
    }

    public void setPuntoMax(Integer puntoMax) {
        this.puntoMax = puntoMax;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
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
    
    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Integer getOtorgado() {
        return otorgado;
    }

    public void setOtorgado(Integer otorgado) {
        this.otorgado = otorgado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPmTablaPremios != null ? idPmTablaPremios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmTablaPremios)) {
            return false;
        }
        PmTablaPremios other = (PmTablaPremios) object;
        if ((this.idPmTablaPremios == null && other.idPmTablaPremios != null) || (this.idPmTablaPremios != null && !this.idPmTablaPremios.equals(other.idPmTablaPremios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmTablaPremios[ idPmTablaPremios=" + idPmTablaPremios + " ]";
    }
    
}
