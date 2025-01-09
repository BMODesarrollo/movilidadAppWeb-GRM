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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "novedad_dano_liq_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadDanoLiqDet.findAll", query = "SELECT n FROM NovedadDanoLiqDet n"),
    @NamedQuery(name = "NovedadDanoLiqDet.findByIdNovedadDanoLiqDet", query = "SELECT n FROM NovedadDanoLiqDet n WHERE n.idNovedadDanoLiqDet = :idNovedadDanoLiqDet"),
    @NamedQuery(name = "NovedadDanoLiqDet.findByCostoParam", query = "SELECT n FROM NovedadDanoLiqDet n WHERE n.costoParam = :costoParam"),
    @NamedQuery(name = "NovedadDanoLiqDet.findByCostoAjustado", query = "SELECT n FROM NovedadDanoLiqDet n WHERE n.costoAjustado = :costoAjustado"),
    @NamedQuery(name = "NovedadDanoLiqDet.findByUsername", query = "SELECT n FROM NovedadDanoLiqDet n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadDanoLiqDet.findByCreado", query = "SELECT n FROM NovedadDanoLiqDet n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadDanoLiqDet.findByModificado", query = "SELECT n FROM NovedadDanoLiqDet n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadDanoLiqDet.findByEstadoReg", query = "SELECT n FROM NovedadDanoLiqDet n WHERE n.estadoReg = :estadoReg")})
public class NovedadDanoLiqDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_dano_liq_det")
    private Integer idNovedadDanoLiqDet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "costo_param")
    private int costoParam;
    @Basic(optional = false)
    @NotNull
    @Column(name = "costo_ajustado")
    private int costoAjustado;
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
    @JoinColumn(name = "id_novedad_dano", referencedColumnName = "id_novedad_dano")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NovedadDano idNovedadDano;
    @JoinColumn(name = "id_novedad_dano_liq", referencedColumnName = "id_novedad_dano_liq")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NovedadDanoLiq idNovedadDanoLiq;
    @JoinColumn(name = "id_vehiculo_dano_costo", referencedColumnName = "id_vehiculo_dano_costo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoDanoCosto idVehiculoDanoCosto;

    public NovedadDanoLiqDet() {
    }

    public NovedadDanoLiqDet(Integer idNovedadDanoLiqDet) {
        this.idNovedadDanoLiqDet = idNovedadDanoLiqDet;
    }

    public NovedadDanoLiqDet(Integer idNovedadDanoLiqDet, int costoParam, int costoAjustado) {
        this.idNovedadDanoLiqDet = idNovedadDanoLiqDet;
        this.costoParam = costoParam;
        this.costoAjustado = costoAjustado;
    }

    public Integer getIdNovedadDanoLiqDet() {
        return idNovedadDanoLiqDet;
    }

    public void setIdNovedadDanoLiqDet(Integer idNovedadDanoLiqDet) {
        this.idNovedadDanoLiqDet = idNovedadDanoLiqDet;
    }

    public int getCostoParam() {
        return costoParam;
    }

    public void setCostoParam(int costoParam) {
        this.costoParam = costoParam;
    }

    public int getCostoAjustado() {
        return costoAjustado;
    }

    public void setCostoAjustado(int costoAjustado) {
        this.costoAjustado = costoAjustado;
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

    public NovedadDano getIdNovedadDano() {
        return idNovedadDano;
    }

    public void setIdNovedadDano(NovedadDano idNovedadDano) {
        this.idNovedadDano = idNovedadDano;
    }

    public NovedadDanoLiq getIdNovedadDanoLiq() {
        return idNovedadDanoLiq;
    }

    public void setIdNovedadDanoLiq(NovedadDanoLiq idNovedadDanoLiq) {
        this.idNovedadDanoLiq = idNovedadDanoLiq;
    }

    public VehiculoDanoCosto getIdVehiculoDanoCosto() {
        return idVehiculoDanoCosto;
    }

    public void setIdVehiculoDanoCosto(VehiculoDanoCosto idVehiculoDanoCosto) {
        this.idVehiculoDanoCosto = idVehiculoDanoCosto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadDanoLiqDet != null ? idNovedadDanoLiqDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadDanoLiqDet)) {
            return false;
        }
        NovedadDanoLiqDet other = (NovedadDanoLiqDet) object;
        if ((this.idNovedadDanoLiqDet == null && other.idNovedadDanoLiqDet != null) || (this.idNovedadDanoLiqDet != null && !this.idNovedadDanoLiqDet.equals(other.idNovedadDanoLiqDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadDanoLiqDet[ idNovedadDanoLiqDet=" + idNovedadDanoLiqDet + " ]";
    }
    
}
