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
import javax.persistence.Lob;
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
 * @author HP
 */
@Entity
@Table(name = "acc_tipo_proceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccTipoProceso.findAll", query = "SELECT a FROM AccTipoProceso a")
    , @NamedQuery(name = "AccTipoProceso.findByIdAccTipoProceso", query = "SELECT a FROM AccTipoProceso a WHERE a.idAccTipoProceso = :idAccTipoProceso")
    , @NamedQuery(name = "AccTipoProceso.findByCausaRaiz", query = "SELECT a FROM AccTipoProceso a WHERE a.causaRaiz = :causaRaiz")
    , @NamedQuery(name = "AccTipoProceso.findByUsername", query = "SELECT a FROM AccTipoProceso a WHERE a.username = :username")
    , @NamedQuery(name = "AccTipoProceso.findByCreado", query = "SELECT a FROM AccTipoProceso a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccTipoProceso.findByModificado", query = "SELECT a FROM AccTipoProceso a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccTipoProceso.findByEstadoReg", query = "SELECT a FROM AccTipoProceso a WHERE a.estadoReg = :estadoReg")})
public class AccTipoProceso implements Serializable {

    @OneToMany(mappedBy = "idAccTipoProceso", fetch = FetchType.LAZY)
    private List<AccidenteDiligenciasj> accidenteDiligenciasjList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_tipo_proceso")
    private Integer idAccTipoProceso;
    @Size(max = 60)
    @Column(name = "causa_raiz")
    private String causaRaiz;
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
    @OneToMany(mappedBy = "idAccTipoProceso", fetch = FetchType.LAZY)
    private List<AccEtapaProceso> accEtapaProcesoList;

    public AccTipoProceso() {
    }

    public AccTipoProceso(Integer idAccTipoProceso) {
        this.idAccTipoProceso = idAccTipoProceso;
    }

    public Integer getIdAccTipoProceso() {
        return idAccTipoProceso;
    }

    public void setIdAccTipoProceso(Integer idAccTipoProceso) {
        this.idAccTipoProceso = idAccTipoProceso;
    }

    public String getCausaRaiz() {
        return causaRaiz;
    }

    public void setCausaRaiz(String causaRaiz) {
        this.causaRaiz = causaRaiz;
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

    @XmlTransient
    public List<AccEtapaProceso> getAccEtapaProcesoList() {
        return accEtapaProcesoList;
    }

    public void setAccEtapaProcesoList(List<AccEtapaProceso> accEtapaProcesoList) {
        this.accEtapaProcesoList = accEtapaProcesoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccTipoProceso != null ? idAccTipoProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccTipoProceso)) {
            return false;
        }
        AccTipoProceso other = (AccTipoProceso) object;
        if ((this.idAccTipoProceso == null && other.idAccTipoProceso != null) || (this.idAccTipoProceso != null && !this.idAccTipoProceso.equals(other.idAccTipoProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccTipoProceso[ idAccTipoProceso=" + idAccTipoProceso + " ]";
    }

    @XmlTransient
    public List<AccidenteDiligenciasj> getAccidenteDiligenciasjList() {
        return accidenteDiligenciasjList;
    }

    public void setAccidenteDiligenciasjList(List<AccidenteDiligenciasj> accidenteDiligenciasjList) {
        this.accidenteDiligenciasjList = accidenteDiligenciasjList;
    }
    
}
