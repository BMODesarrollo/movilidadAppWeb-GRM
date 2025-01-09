/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "pm_tipo_concepto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmTipoConcepto.findAll", query = "SELECT p FROM PmTipoConcepto p")
    , @NamedQuery(name = "PmTipoConcepto.findByIdPmTipoConcepto", query = "SELECT p FROM PmTipoConcepto p WHERE p.idPmTipoConcepto = :idPmTipoConcepto")
    , @NamedQuery(name = "PmTipoConcepto.findByTipo", query = "SELECT p FROM PmTipoConcepto p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "PmTipoConcepto.findByUsername", query = "SELECT p FROM PmTipoConcepto p WHERE p.username = :username")
    , @NamedQuery(name = "PmTipoConcepto.findByCreado", query = "SELECT p FROM PmTipoConcepto p WHERE p.creado = :creado")
    , @NamedQuery(name = "PmTipoConcepto.findByModificado", query = "SELECT p FROM PmTipoConcepto p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PmTipoConcepto.findByEstadoReg", query = "SELECT p FROM PmTipoConcepto p WHERE p.estadoReg = :estadoReg")})
public class PmTipoConcepto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_tipo_concepto")
    private Integer idPmTipoConcepto;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
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
    @OneToMany(mappedBy = "idPmTipoConcepto", fetch = FetchType.LAZY)
    private List<PmIeConceptos> pmIeConceptosList;

    public PmTipoConcepto() {
    }

    public PmTipoConcepto(Integer idPmTipoConcepto) {
        this.idPmTipoConcepto = idPmTipoConcepto;
    }

    public Integer getIdPmTipoConcepto() {
        return idPmTipoConcepto;
    }

    public void setIdPmTipoConcepto(Integer idPmTipoConcepto) {
        this.idPmTipoConcepto = idPmTipoConcepto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    @XmlTransient
    public List<PmIeConceptos> getPmIeConceptosList() {
        return pmIeConceptosList;
    }

    public void setPmIeConceptosList(List<PmIeConceptos> pmIeConceptosList) {
        this.pmIeConceptosList = pmIeConceptosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPmTipoConcepto != null ? idPmTipoConcepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmTipoConcepto)) {
            return false;
        }
        PmTipoConcepto other = (PmTipoConcepto) object;
        if ((this.idPmTipoConcepto == null && other.idPmTipoConcepto != null) || (this.idPmTipoConcepto != null && !this.idPmTipoConcepto.equals(other.idPmTipoConcepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmTipoConcepto[ idPmTipoConcepto=" + idPmTipoConcepto + " ]";
    }
    
}
