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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "multa_puntos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MultaPuntos.findAll", query = "SELECT m FROM MultaPuntos m")
    , @NamedQuery(name = "MultaPuntos.findByIdMultaPuntosPm", query = "SELECT m FROM MultaPuntos m WHERE m.idMultaPuntosPm = :idMultaPuntosPm")
    , @NamedQuery(name = "MultaPuntos.findByDesde", query = "SELECT m FROM MultaPuntos m WHERE m.desde = :desde")
    , @NamedQuery(name = "MultaPuntos.findByHasta", query = "SELECT m FROM MultaPuntos m WHERE m.hasta = :hasta")
    , @NamedQuery(name = "MultaPuntos.findByVrPuntos", query = "SELECT m FROM MultaPuntos m WHERE m.vrPuntos = :vrPuntos")
    , @NamedQuery(name = "MultaPuntos.findByUsername", query = "SELECT m FROM MultaPuntos m WHERE m.username = :username")
    , @NamedQuery(name = "MultaPuntos.findByCreado", query = "SELECT m FROM MultaPuntos m WHERE m.creado = :creado")
    , @NamedQuery(name = "MultaPuntos.findByModificado", query = "SELECT m FROM MultaPuntos m WHERE m.modificado = :modificado")
    , @NamedQuery(name = "MultaPuntos.findByEstadoReg", query = "SELECT m FROM MultaPuntos m WHERE m.estadoReg = :estadoReg")})
public class MultaPuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_multa_puntos_pm")
    private Integer idMultaPuntosPm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vr_puntos")
    private int vrPuntos;
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

    public MultaPuntos() {
    }

    public MultaPuntos(Integer idMultaPuntosPm) {
        this.idMultaPuntosPm = idMultaPuntosPm;
    }

    public MultaPuntos(Integer idMultaPuntosPm, Date desde, Date hasta, int vrPuntos, String username, Date creado, int estadoReg) {
        this.idMultaPuntosPm = idMultaPuntosPm;
        this.desde = desde;
        this.hasta = hasta;
        this.vrPuntos = vrPuntos;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdMultaPuntosPm() {
        return idMultaPuntosPm;
    }

    public void setIdMultaPuntosPm(Integer idMultaPuntosPm) {
        this.idMultaPuntosPm = idMultaPuntosPm;
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

    public int getVrPuntos() {
        return vrPuntos;
    }

    public void setVrPuntos(int vrPuntos) {
        this.vrPuntos = vrPuntos;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMultaPuntosPm != null ? idMultaPuntosPm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MultaPuntos)) {
            return false;
        }
        MultaPuntos other = (MultaPuntos) object;
        if ((this.idMultaPuntosPm == null && other.idMultaPuntosPm != null) || (this.idMultaPuntosPm != null && !this.idMultaPuntosPm.equals(other.idMultaPuntosPm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MultaPuntos[ idMultaPuntosPm=" + idMultaPuntosPm + " ]";
    }
    
}
