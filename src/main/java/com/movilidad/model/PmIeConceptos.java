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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "pm_ie_conceptos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmIeConceptos.findAll", query = "SELECT p FROM PmIeConceptos p")
    , @NamedQuery(name = "PmIeConceptos.findByIdPmIeConceptos", query = "SELECT p FROM PmIeConceptos p WHERE p.idPmIeConceptos = :idPmIeConceptos")
    , @NamedQuery(name = "PmIeConceptos.findByConcepto", query = "SELECT p FROM PmIeConceptos p WHERE p.concepto = :concepto")
    , @NamedQuery(name = "PmIeConceptos.findByUsername", query = "SELECT p FROM PmIeConceptos p WHERE p.username = :username")
    , @NamedQuery(name = "PmIeConceptos.findByCreado", query = "SELECT p FROM PmIeConceptos p WHERE p.creado = :creado")
    , @NamedQuery(name = "PmIeConceptos.findByModificado", query = "SELECT p FROM PmIeConceptos p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PmIeConceptos.findByEstadoReg", query = "SELECT p FROM PmIeConceptos p WHERE p.estadoReg = :estadoReg")})
public class PmIeConceptos implements Serializable {

    @OneToMany(mappedBy = "idPmIeConceptos", fetch = FetchType.LAZY)
    private List<PmIngEgr> pmIngEgrList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pm_ie_conceptos")
    private Integer idPmIeConceptos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 160)
    @Column(name = "concepto")
    private String concepto;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
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
    @JoinColumn(name = "id_pm_tipo_concepto", referencedColumnName = "id_pm_tipo_concepto")
    @ManyToOne(fetch = FetchType.LAZY)
    private PmTipoConcepto idPmTipoConcepto;

    public PmIeConceptos() {
    }

    public PmIeConceptos(Integer idPmIeConceptos) {
        this.idPmIeConceptos = idPmIeConceptos;
    }

    public PmIeConceptos(Integer idPmIeConceptos, String concepto) {
        this.idPmIeConceptos = idPmIeConceptos;
        this.concepto = concepto;
    }

    public Integer getIdPmIeConceptos() {
        return idPmIeConceptos;
    }

    public void setIdPmIeConceptos(Integer idPmIeConceptos) {
        this.idPmIeConceptos = idPmIeConceptos;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public PmTipoConcepto getIdPmTipoConcepto() {
        return idPmTipoConcepto;
    }

    public void setIdPmTipoConcepto(PmTipoConcepto idPmTipoConcepto) {
        this.idPmTipoConcepto = idPmTipoConcepto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPmIeConceptos != null ? idPmIeConceptos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmIeConceptos)) {
            return false;
        }
        PmIeConceptos other = (PmIeConceptos) object;
        if ((this.idPmIeConceptos == null && other.idPmIeConceptos != null) || (this.idPmIeConceptos != null && !this.idPmIeConceptos.equals(other.idPmIeConceptos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PmIeConceptos[ idPmIeConceptos=" + idPmIeConceptos + " ]";
    }

    @XmlTransient
    public List<PmIngEgr> getPmIngEgrList() {
        return pmIngEgrList;
    }

    public void setPmIngEgrList(List<PmIngEgr> pmIngEgrList) {
        this.pmIngEgrList = pmIngEgrList;
    }
    
}
