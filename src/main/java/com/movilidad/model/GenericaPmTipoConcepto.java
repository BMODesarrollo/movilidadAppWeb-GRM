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
@Table(name = "generica_pm_tipo_concepto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaPmTipoConcepto.findAll", query = "SELECT g FROM GenericaPmTipoConcepto g")
    , @NamedQuery(name = "GenericaPmTipoConcepto.findByIdGenericaPmTipoConcepto", query = "SELECT g FROM GenericaPmTipoConcepto g WHERE g.idGenericaPmTipoConcepto = :idGenericaPmTipoConcepto")
    , @NamedQuery(name = "GenericaPmTipoConcepto.findByTipo", query = "SELECT g FROM GenericaPmTipoConcepto g WHERE g.tipo = :tipo")
    , @NamedQuery(name = "GenericaPmTipoConcepto.findByUsername", query = "SELECT g FROM GenericaPmTipoConcepto g WHERE g.username = :username")
    , @NamedQuery(name = "GenericaPmTipoConcepto.findByCreado", query = "SELECT g FROM GenericaPmTipoConcepto g WHERE g.creado = :creado")
    , @NamedQuery(name = "GenericaPmTipoConcepto.findByModificado", query = "SELECT g FROM GenericaPmTipoConcepto g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "GenericaPmTipoConcepto.findByEstadoReg", query = "SELECT g FROM GenericaPmTipoConcepto g WHERE g.estadoReg = :estadoReg")})
public class GenericaPmTipoConcepto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_pm_tipo_concepto")
    private Integer idGenericaPmTipoConcepto;
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
    @OneToMany(mappedBy = "idGenericaPmTipoConcepto", fetch = FetchType.LAZY)
    private List<GenericaPmIeConceptos> genericaPmIeConceptosList;

    public GenericaPmTipoConcepto() {
    }

    public GenericaPmTipoConcepto(Integer idGenericaPmTipoConcepto) {
        this.idGenericaPmTipoConcepto = idGenericaPmTipoConcepto;
    }

    public Integer getIdGenericaPmTipoConcepto() {
        return idGenericaPmTipoConcepto;
    }

    public void setIdGenericaPmTipoConcepto(Integer idGenericaPmTipoConcepto) {
        this.idGenericaPmTipoConcepto = idGenericaPmTipoConcepto;
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
    public List<GenericaPmIeConceptos> getGenericaPmIeConceptosList() {
        return genericaPmIeConceptosList;
    }

    public void setGenericaPmIeConceptosList(List<GenericaPmIeConceptos> genericaPmIeConceptosList) {
        this.genericaPmIeConceptosList = genericaPmIeConceptosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaPmTipoConcepto != null ? idGenericaPmTipoConcepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaPmTipoConcepto)) {
            return false;
        }
        GenericaPmTipoConcepto other = (GenericaPmTipoConcepto) object;
        if ((this.idGenericaPmTipoConcepto == null && other.idGenericaPmTipoConcepto != null) || (this.idGenericaPmTipoConcepto != null && !this.idGenericaPmTipoConcepto.equals(other.idGenericaPmTipoConcepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaPmTipoConcepto[ idGenericaPmTipoConcepto=" + idGenericaPmTipoConcepto + " ]";
    }
    
}
