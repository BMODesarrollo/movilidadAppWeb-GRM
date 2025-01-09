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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "pm_vrbono_grupal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmVrbonoGrupal.findAll", query = "SELECT p FROM PmVrbonoGrupal p")
    , @NamedQuery(name = "PmVrbonoGrupal.findByIdPmVrbonoGrupal", query = "SELECT p FROM PmVrbonoGrupal p WHERE p.idPmVrbonoGrupal = :idPmVrbonoGrupal")
    , @NamedQuery(name = "PmVrbonoGrupal.findByDesde", query = "SELECT p FROM PmVrbonoGrupal p WHERE p.desde = :desde")
    , @NamedQuery(name = "PmVrbonoGrupal.findByHasta", query = "SELECT p FROM PmVrbonoGrupal p WHERE p.hasta = :hasta")
    , @NamedQuery(name = "PmVrbonoGrupal.findByVrBonoSalarial", query = "SELECT p FROM PmVrbonoGrupal p WHERE p.vrBonoSalarial = :vrBonoSalarial")
    , @NamedQuery(name = "PmVrbonoGrupal.findByVrBonoAlimentos", query = "SELECT p FROM PmVrbonoGrupal p WHERE p.vrBonoAlimentos = :vrBonoAlimentos")
    , @NamedQuery(name = "PmVrbonoGrupal.findByUsername", query = "SELECT p FROM PmVrbonoGrupal p WHERE p.username = :username")
    , @NamedQuery(name = "PmVrbonoGrupal.findByCreado", query = "SELECT p FROM PmVrbonoGrupal p WHERE p.creado = :creado")
    , @NamedQuery(name = "PmVrbonoGrupal.findByModificado", query = "SELECT p FROM PmVrbonoGrupal p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PmVrbonoGrupal.findByEstadoReg", query = "SELECT p FROM PmVrbonoGrupal p WHERE p.estadoReg = :estadoReg")})
public class PmVrbonoGrupal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_vrbono_grupal")
    private Integer idPmVrbonoGrupal;
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

    public PmVrbonoGrupal() {
    }

    public PmVrbonoGrupal(Integer idPmVrbonoGrupal) {
        this.idPmVrbonoGrupal = idPmVrbonoGrupal;
    }

    public PmVrbonoGrupal(Integer idPmVrbonoGrupal, Date desde, Date hasta, int vrBonoSalarial, int vrBonoAlimentos, String username, Date creado, int estadoReg) {
        this.idPmVrbonoGrupal = idPmVrbonoGrupal;
        this.desde = desde;
        this.hasta = hasta;
        this.vrBonoSalarial = vrBonoSalarial;
        this.vrBonoAlimentos = vrBonoAlimentos;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPmVrbonoGrupal() {
        return idPmVrbonoGrupal;
    }

    public void setIdPmVrbonoGrupal(Integer idPmVrbonoGrupal) {
        this.idPmVrbonoGrupal = idPmVrbonoGrupal;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPmVrbonoGrupal != null ? idPmVrbonoGrupal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmVrbonoGrupal)) {
            return false;
        }
        PmVrbonoGrupal other = (PmVrbonoGrupal) object;
        if ((this.idPmVrbonoGrupal == null && other.idPmVrbonoGrupal != null) || (this.idPmVrbonoGrupal != null && !this.idPmVrbonoGrupal.equals(other.idPmVrbonoGrupal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmVrbonoGrupal[ idPmVrbonoGrupal=" + idPmVrbonoGrupal + " ]";
    }
    
}
