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
 * @author solucionesit
 */
@Entity
@Table(name = "generica_pm_dias_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPmDiasEmpresa.findAll", query = "SELECT g FROM GenericaPmDiasEmpresa g"),
    @NamedQuery(name = "GenericaPmDiasEmpresa.findByIdGenericaPmDiasEmpresa", query = "SELECT g FROM GenericaPmDiasEmpresa g WHERE g.idGenericaPmDiasEmpresa = :idGenericaPmDiasEmpresa"),
    @NamedQuery(name = "GenericaPmDiasEmpresa.findByDesde", query = "SELECT g FROM GenericaPmDiasEmpresa g WHERE g.desde = :desde"),
    @NamedQuery(name = "GenericaPmDiasEmpresa.findByHasta", query = "SELECT g FROM GenericaPmDiasEmpresa g WHERE g.hasta = :hasta"),
    @NamedQuery(name = "GenericaPmDiasEmpresa.findByNroDias", query = "SELECT g FROM GenericaPmDiasEmpresa g WHERE g.nroDias = :nroDias"),
    @NamedQuery(name = "GenericaPmDiasEmpresa.findByUsername", query = "SELECT g FROM GenericaPmDiasEmpresa g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaPmDiasEmpresa.findByCreado", query = "SELECT g FROM GenericaPmDiasEmpresa g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaPmDiasEmpresa.findByModificado", query = "SELECT g FROM GenericaPmDiasEmpresa g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaPmDiasEmpresa.findByEstadoReg", query = "SELECT g FROM GenericaPmDiasEmpresa g WHERE g.estadoReg = :estadoReg")})
public class GenericaPmDiasEmpresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_pm_dias_empresa")
    private Integer idGenericaPmDiasEmpresa;
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
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParamArea idParamArea;

    public GenericaPmDiasEmpresa() {
    }

    public GenericaPmDiasEmpresa(Integer idGenericaPmDiasEmpresa) {
        this.idGenericaPmDiasEmpresa = idGenericaPmDiasEmpresa;
    }

    public GenericaPmDiasEmpresa(Integer idGenericaPmDiasEmpresa, Date desde, Date hasta, int nroDias, String username, Date creado, Date modificado, int estadoReg) {
        this.idGenericaPmDiasEmpresa = idGenericaPmDiasEmpresa;
        this.desde = desde;
        this.hasta = hasta;
        this.nroDias = nroDias;
        this.username = username;
        this.creado = creado;
        this.modificado = modificado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaPmDiasEmpresa() {
        return idGenericaPmDiasEmpresa;
    }

    public void setIdGenericaPmDiasEmpresa(Integer idGenericaPmDiasEmpresa) {
        this.idGenericaPmDiasEmpresa = idGenericaPmDiasEmpresa;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaPmDiasEmpresa != null ? idGenericaPmDiasEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPmDiasEmpresa)) {
            return false;
        }
        GenericaPmDiasEmpresa other = (GenericaPmDiasEmpresa) object;
        if ((this.idGenericaPmDiasEmpresa == null && other.idGenericaPmDiasEmpresa != null) || (this.idGenericaPmDiasEmpresa != null && !this.idGenericaPmDiasEmpresa.equals(other.idGenericaPmDiasEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPmDiasEmpresa[ idGenericaPmDiasEmpresa=" + idGenericaPmDiasEmpresa + " ]";
    }
    
}
