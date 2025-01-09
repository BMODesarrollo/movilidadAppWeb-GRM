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
@Table(name = "generica_pm_vrbono_grupal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPmVrbonoGrupal.findAll", query = "SELECT g FROM GenericaPmVrbonoGrupal g")
    , @NamedQuery(name = "GenericaPmVrbonoGrupal.findByIdGenericaPmVrbonoGrupal", query = "SELECT g FROM GenericaPmVrbonoGrupal g WHERE g.idGenericaPmVrbonoGrupal = :idGenericaPmVrbonoGrupal")
    , @NamedQuery(name = "GenericaPmVrbonoGrupal.findByDesde", query = "SELECT g FROM GenericaPmVrbonoGrupal g WHERE g.desde = :desde")
    , @NamedQuery(name = "GenericaPmVrbonoGrupal.findByHasta", query = "SELECT g FROM GenericaPmVrbonoGrupal g WHERE g.hasta = :hasta")
    , @NamedQuery(name = "GenericaPmVrbonoGrupal.findByVrBonoSalarial", query = "SELECT g FROM GenericaPmVrbonoGrupal g WHERE g.vrBonoSalarial = :vrBonoSalarial")
    , @NamedQuery(name = "GenericaPmVrbonoGrupal.findByVrBonoAlimentos", query = "SELECT g FROM GenericaPmVrbonoGrupal g WHERE g.vrBonoAlimentos = :vrBonoAlimentos")
    , @NamedQuery(name = "GenericaPmVrbonoGrupal.findByUsername", query = "SELECT g FROM GenericaPmVrbonoGrupal g WHERE g.username = :username")
    , @NamedQuery(name = "GenericaPmVrbonoGrupal.findByCreado", query = "SELECT g FROM GenericaPmVrbonoGrupal g WHERE g.creado = :creado")
    , @NamedQuery(name = "GenericaPmVrbonoGrupal.findByModificado", query = "SELECT g FROM GenericaPmVrbonoGrupal g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "GenericaPmVrbonoGrupal.findByEstadoReg", query = "SELECT g FROM GenericaPmVrbonoGrupal g WHERE g.estadoReg = :estadoReg")})
public class GenericaPmVrbonoGrupal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_pm_vrbono_grupal")
    private Integer idGenericaPmVrbonoGrupal;
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
    @Column(name = "vr_bono_salarial")
    private int vrBonoSalarial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vr_bono_alimentos")
    private int vrBonoAlimentos;
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
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;

    public GenericaPmVrbonoGrupal() {
    }

    public GenericaPmVrbonoGrupal(Integer idGenericaPmVrbonoGrupal) {
        this.idGenericaPmVrbonoGrupal = idGenericaPmVrbonoGrupal;
    }

    public GenericaPmVrbonoGrupal(Integer idGenericaPmVrbonoGrupal, Date desde, Date hasta, int vrBonoSalarial, int vrBonoAlimentos, String username, Date creado, int estadoReg) {
        this.idGenericaPmVrbonoGrupal = idGenericaPmVrbonoGrupal;
        this.desde = desde;
        this.hasta = hasta;
        this.vrBonoSalarial = vrBonoSalarial;
        this.vrBonoAlimentos = vrBonoAlimentos;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaPmVrbonoGrupal() {
        return idGenericaPmVrbonoGrupal;
    }

    public void setIdGenericaPmVrbonoGrupal(Integer idGenericaPmVrbonoGrupal) {
        this.idGenericaPmVrbonoGrupal = idGenericaPmVrbonoGrupal;
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

    public int getVrBonoSalarial() {
        return vrBonoSalarial;
    }

    public void setVrBonoSalarial(int vrBonoSalarial) {
        this.vrBonoSalarial = vrBonoSalarial;
    }

    public int getVrBonoAlimentos() {
        return vrBonoAlimentos;
    }

    public void setVrBonoAlimentos(int vrBonoAlimentos) {
        this.vrBonoAlimentos = vrBonoAlimentos;
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
        hash += (idGenericaPmVrbonoGrupal != null ? idGenericaPmVrbonoGrupal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPmVrbonoGrupal)) {
            return false;
        }
        GenericaPmVrbonoGrupal other = (GenericaPmVrbonoGrupal) object;
        if ((this.idGenericaPmVrbonoGrupal == null && other.idGenericaPmVrbonoGrupal != null) || (this.idGenericaPmVrbonoGrupal != null && !this.idGenericaPmVrbonoGrupal.equals(other.idGenericaPmVrbonoGrupal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPmVrbonoGrupal[ idGenericaPmVrbonoGrupal=" + idGenericaPmVrbonoGrupal + " ]";
    }
    
}
