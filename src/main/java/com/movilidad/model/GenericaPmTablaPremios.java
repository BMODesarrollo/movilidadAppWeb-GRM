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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "generica_pm_tabla_premios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPmTablaPremios.findAll", query = "SELECT g FROM GenericaPmTablaPremios g")
    , @NamedQuery(name = "GenericaPmTablaPremios.findByIdGenericaPmTablaPremios", query = "SELECT g FROM GenericaPmTablaPremios g WHERE g.idGenericaPmTablaPremios = :idGenericaPmTablaPremios")
    , @NamedQuery(name = "GenericaPmTablaPremios.findByDesde", query = "SELECT g FROM GenericaPmTablaPremios g WHERE g.desde = :desde")
    , @NamedQuery(name = "GenericaPmTablaPremios.findByHasta", query = "SELECT g FROM GenericaPmTablaPremios g WHERE g.hasta = :hasta")
    , @NamedQuery(name = "GenericaPmTablaPremios.findByPuntoMin", query = "SELECT g FROM GenericaPmTablaPremios g WHERE g.puntoMin = :puntoMin")
    , @NamedQuery(name = "GenericaPmTablaPremios.findByPuntoMax", query = "SELECT g FROM GenericaPmTablaPremios g WHERE g.puntoMax = :puntoMax")
    , @NamedQuery(name = "GenericaPmTablaPremios.findByPorcentaje", query = "SELECT g FROM GenericaPmTablaPremios g WHERE g.porcentaje = :porcentaje")
    , @NamedQuery(name = "GenericaPmTablaPremios.findByUsername", query = "SELECT g FROM GenericaPmTablaPremios g WHERE g.username = :username")
    , @NamedQuery(name = "GenericaPmTablaPremios.findByCreado", query = "SELECT g FROM GenericaPmTablaPremios g WHERE g.creado = :creado")
    , @NamedQuery(name = "GenericaPmTablaPremios.findByModificado", query = "SELECT g FROM GenericaPmTablaPremios g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "GenericaPmTablaPremios.findByEstadoReg", query = "SELECT g FROM GenericaPmTablaPremios g WHERE g.estadoReg = :estadoReg")})
public class GenericaPmTablaPremios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_pm_tabla_premios")
    private Integer idGenericaPmTablaPremios;
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
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;

    public GenericaPmTablaPremios() {
    }

    public GenericaPmTablaPremios(Integer idGenericaPmTablaPremios) {
        this.idGenericaPmTablaPremios = idGenericaPmTablaPremios;
    }

    public Integer getIdGenericaPmTablaPremios() {
        return idGenericaPmTablaPremios;
    }

    public void setIdGenericaPmTablaPremios(Integer idGenericaPmTablaPremios) {
        this.idGenericaPmTablaPremios = idGenericaPmTablaPremios;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
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
        hash += (idGenericaPmTablaPremios != null ? idGenericaPmTablaPremios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPmTablaPremios)) {
            return false;
        }
        GenericaPmTablaPremios other = (GenericaPmTablaPremios) object;
        if ((this.idGenericaPmTablaPremios == null && other.idGenericaPmTablaPremios != null) || (this.idGenericaPmTablaPremios != null && !this.idGenericaPmTablaPremios.equals(other.idGenericaPmTablaPremios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPmTablaPremios[ idGenericaPmTablaPremios=" + idGenericaPmTablaPremios + " ]";
    }
    
}
