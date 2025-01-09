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
 * @author HP
 */
@Entity
@Table(name = "pm_vrbono_tipovehi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmVrbonoTipovehi.findAll", query = "SELECT p FROM PmVrbonoTipovehi p")
    , @NamedQuery(name = "PmVrbonoTipovehi.findByIdPmVrbonoTipovehi", query = "SELECT p FROM PmVrbonoTipovehi p WHERE p.idPmVrbonoTipovehi = :idPmVrbonoTipovehi")
    , @NamedQuery(name = "PmVrbonoTipovehi.findByDesde", query = "SELECT p FROM PmVrbonoTipovehi p WHERE p.desde = :desde")
    , @NamedQuery(name = "PmVrbonoTipovehi.findByHasta", query = "SELECT p FROM PmVrbonoTipovehi p WHERE p.hasta = :hasta")
    , @NamedQuery(name = "PmVrbonoTipovehi.findByVrBono", query = "SELECT p FROM PmVrbonoTipovehi p WHERE p.vrBono = :vrBono")
    , @NamedQuery(name = "PmVrbonoTipovehi.findByUsername", query = "SELECT p FROM PmVrbonoTipovehi p WHERE p.username = :username")
    , @NamedQuery(name = "PmVrbonoTipovehi.findByCreado", query = "SELECT p FROM PmVrbonoTipovehi p WHERE p.creado = :creado")
    , @NamedQuery(name = "PmVrbonoTipovehi.findByModificado", query = "SELECT p FROM PmVrbonoTipovehi p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PmVrbonoTipovehi.findByEstadoReg", query = "SELECT p FROM PmVrbonoTipovehi p WHERE p.estadoReg = :estadoReg")})
public class PmVrbonoTipovehi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_vrbono_tipovehi")
    private Integer idPmVrbonoTipovehi;
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
    @Column(name = "vr_bono")
    private int vrBono;
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
    @JoinColumn(name = "id_vehiculo_tipo", referencedColumnName = "id_vehiculo_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VehiculoTipo idVehiculoTipo;

    public PmVrbonoTipovehi() {
    }

    public PmVrbonoTipovehi(Integer idPmVrbonoTipovehi) {
        this.idPmVrbonoTipovehi = idPmVrbonoTipovehi;
    }

    public PmVrbonoTipovehi(Integer idPmVrbonoTipovehi, Date desde, Date hasta, int vrBono, String username, Date creado, int estadoReg) {
        this.idPmVrbonoTipovehi = idPmVrbonoTipovehi;
        this.desde = desde;
        this.hasta = hasta;
        this.vrBono = vrBono;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPmVrbonoTipovehi() {
        return idPmVrbonoTipovehi;
    }

    public void setIdPmVrbonoTipovehi(Integer idPmVrbonoTipovehi) {
        this.idPmVrbonoTipovehi = idPmVrbonoTipovehi;
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

    public int getVrBono() {
        return vrBono;
    }

    public void setVrBono(int vrBono) {
        this.vrBono = vrBono;
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

    public VehiculoTipo getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(VehiculoTipo idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPmVrbonoTipovehi != null ? idPmVrbonoTipovehi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmVrbonoTipovehi)) {
            return false;
        }
        PmVrbonoTipovehi other = (PmVrbonoTipovehi) object;
        if ((this.idPmVrbonoTipovehi == null && other.idPmVrbonoTipovehi != null) || (this.idPmVrbonoTipovehi != null && !this.idPmVrbonoTipovehi.equals(other.idPmVrbonoTipovehi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmVrbonoTipovehi[ idPmVrbonoTipovehi=" + idPmVrbonoTipovehi + " ]";
    }
    
}
