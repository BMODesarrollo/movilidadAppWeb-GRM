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
@Table(name = "pm_dias_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmDiasEmpresa.findAll", query = "SELECT p FROM PmDiasEmpresa p")
    , @NamedQuery(name = "PmDiasEmpresa.findByIdPmDiasEmpresa", query = "SELECT p FROM PmDiasEmpresa p WHERE p.idPmDiasEmpresa = :idPmDiasEmpresa")
    , @NamedQuery(name = "PmDiasEmpresa.findByDesde", query = "SELECT p FROM PmDiasEmpresa p WHERE p.desde = :desde")
    , @NamedQuery(name = "PmDiasEmpresa.findByHasta", query = "SELECT p FROM PmDiasEmpresa p WHERE p.hasta = :hasta")
    , @NamedQuery(name = "PmDiasEmpresa.findByNroDias", query = "SELECT p FROM PmDiasEmpresa p WHERE p.nroDias = :nroDias")
    , @NamedQuery(name = "PmDiasEmpresa.findByUsername", query = "SELECT p FROM PmDiasEmpresa p WHERE p.username = :username")
    , @NamedQuery(name = "PmDiasEmpresa.findByCreado", query = "SELECT p FROM PmDiasEmpresa p WHERE p.creado = :creado")
    , @NamedQuery(name = "PmDiasEmpresa.findByModificado", query = "SELECT p FROM PmDiasEmpresa p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PmDiasEmpresa.findByEstadoReg", query = "SELECT p FROM PmDiasEmpresa p WHERE p.estadoReg = :estadoReg")})
public class PmDiasEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_dias_empresa")
    private Integer idPmDiasEmpresa;
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
    @Column(name = "nro_dias")
    private int nroDias;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;

    public PmDiasEmpresa() {
    }

    public PmDiasEmpresa(Integer idPmDiasEmpresa) {
        this.idPmDiasEmpresa = idPmDiasEmpresa;
    }

    public PmDiasEmpresa(Integer idPmDiasEmpresa, Date desde, Date hasta, int nroDias, String username, Date creado, Date modificado, int estadoReg) {
        this.idPmDiasEmpresa = idPmDiasEmpresa;
        this.desde = desde;
        this.hasta = hasta;
        this.nroDias = nroDias;
        this.username = username;
        this.creado = creado;
        this.modificado = modificado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPmDiasEmpresa() {
        return idPmDiasEmpresa;
    }

    public void setIdPmDiasEmpresa(Integer idPmDiasEmpresa) {
        this.idPmDiasEmpresa = idPmDiasEmpresa;
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

    public int getNroDias() {
        return nroDias;
    }

    public void setNroDias(int nroDias) {
        this.nroDias = nroDias;
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
        hash += (idPmDiasEmpresa != null ? idPmDiasEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmDiasEmpresa)) {
            return false;
        }
        PmDiasEmpresa other = (PmDiasEmpresa) object;
        if ((this.idPmDiasEmpresa == null && other.idPmDiasEmpresa != null) || (this.idPmDiasEmpresa != null && !this.idPmDiasEmpresa.equals(other.idPmDiasEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmDiasEmpresa[ idPmDiasEmpresa=" + idPmDiasEmpresa + " ]";
    }
    
}
