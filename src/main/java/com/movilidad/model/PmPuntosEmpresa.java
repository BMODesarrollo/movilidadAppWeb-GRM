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
@Table(name = "pm_puntos_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmPuntosEmpresa.findAll", query = "SELECT p FROM PmPuntosEmpresa p")
    , @NamedQuery(name = "PmPuntosEmpresa.findByIdPmPuntosEmpresa", query = "SELECT p FROM PmPuntosEmpresa p WHERE p.idPmPuntosEmpresa = :idPmPuntosEmpresa")
    , @NamedQuery(name = "PmPuntosEmpresa.findByDesde", query = "SELECT p FROM PmPuntosEmpresa p WHERE p.desde = :desde")
    , @NamedQuery(name = "PmPuntosEmpresa.findByHasta", query = "SELECT p FROM PmPuntosEmpresa p WHERE p.hasta = :hasta")
    , @NamedQuery(name = "PmPuntosEmpresa.findByVrPuntos", query = "SELECT p FROM PmPuntosEmpresa p WHERE p.vrPuntos = :vrPuntos")
    , @NamedQuery(name = "PmPuntosEmpresa.findByUsername", query = "SELECT p FROM PmPuntosEmpresa p WHERE p.username = :username")
    , @NamedQuery(name = "PmPuntosEmpresa.findByCreado", query = "SELECT p FROM PmPuntosEmpresa p WHERE p.creado = :creado")
    , @NamedQuery(name = "PmPuntosEmpresa.findByModificado", query = "SELECT p FROM PmPuntosEmpresa p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PmPuntosEmpresa.findByEstadoReg", query = "SELECT p FROM PmPuntosEmpresa p WHERE p.estadoReg = :estadoReg")})
public class PmPuntosEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_puntos_empresa")
    private Integer idPmPuntosEmpresa;
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

    public PmPuntosEmpresa() {
    }

    public PmPuntosEmpresa(Integer idPmPuntosEmpresa) {
        this.idPmPuntosEmpresa = idPmPuntosEmpresa;
    }

    public PmPuntosEmpresa(Integer idPmPuntosEmpresa, Date desde, Date hasta, int vrPuntos, String username, Date creado, int estadoReg) {
        this.idPmPuntosEmpresa = idPmPuntosEmpresa;
        this.desde = desde;
        this.hasta = hasta;
        this.vrPuntos = vrPuntos;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPmPuntosEmpresa() {
        return idPmPuntosEmpresa;
    }

    public void setIdPmPuntosEmpresa(Integer idPmPuntosEmpresa) {
        this.idPmPuntosEmpresa = idPmPuntosEmpresa;
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
        hash += (idPmPuntosEmpresa != null ? idPmPuntosEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmPuntosEmpresa)) {
            return false;
        }
        PmPuntosEmpresa other = (PmPuntosEmpresa) object;
        if ((this.idPmPuntosEmpresa == null && other.idPmPuntosEmpresa != null) || (this.idPmPuntosEmpresa != null && !this.idPmPuntosEmpresa.equals(other.idPmPuntosEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmPuntosEmpresa[ idPmPuntosEmpresa=" + idPmPuntosEmpresa + " ]";
    }
    
}
