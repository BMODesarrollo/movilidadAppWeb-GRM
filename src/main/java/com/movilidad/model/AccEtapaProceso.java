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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_etapa_proceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccEtapaProceso.findAll", query = "SELECT a FROM AccEtapaProceso a")
    , @NamedQuery(name = "AccEtapaProceso.findByIdAccEtapaProceso", query = "SELECT a FROM AccEtapaProceso a WHERE a.idAccEtapaProceso = :idAccEtapaProceso")
    , @NamedQuery(name = "AccEtapaProceso.findByCausaRaiz", query = "SELECT a FROM AccEtapaProceso a WHERE a.causaRaiz = :causaRaiz")
    , @NamedQuery(name = "AccEtapaProceso.findByUsername", query = "SELECT a FROM AccEtapaProceso a WHERE a.username = :username")
    , @NamedQuery(name = "AccEtapaProceso.findByCreado", query = "SELECT a FROM AccEtapaProceso a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccEtapaProceso.findByModificado", query = "SELECT a FROM AccEtapaProceso a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccEtapaProceso.findByEstadoReg", query = "SELECT a FROM AccEtapaProceso a WHERE a.estadoReg = :estadoReg")})
public class AccEtapaProceso implements Serializable {

    @OneToMany(mappedBy = "idAccEtapaProceso", fetch = FetchType.LAZY)
    private List<AccidenteDiligenciasj> accidenteDiligenciasjList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_etapa_proceso")
    private Integer idAccEtapaProceso;
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
    @JoinColumn(name = "id_acc_tipo_proceso", referencedColumnName = "id_acc_tipo_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoProceso idAccTipoProceso;

    public AccEtapaProceso() {
    }

    public AccEtapaProceso(Integer idAccEtapaProceso) {
        this.idAccEtapaProceso = idAccEtapaProceso;
    }

    public Integer getIdAccEtapaProceso() {
        return idAccEtapaProceso;
    }

    public void setIdAccEtapaProceso(Integer idAccEtapaProceso) {
        this.idAccEtapaProceso = idAccEtapaProceso;
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

    public AccTipoProceso getIdAccTipoProceso() {
        return idAccTipoProceso;
    }

    public void setIdAccTipoProceso(AccTipoProceso idAccTipoProceso) {
        this.idAccTipoProceso = idAccTipoProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccEtapaProceso != null ? idAccEtapaProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccEtapaProceso)) {
            return false;
        }
        AccEtapaProceso other = (AccEtapaProceso) object;
        if ((this.idAccEtapaProceso == null && other.idAccEtapaProceso != null) || (this.idAccEtapaProceso != null && !this.idAccEtapaProceso.equals(other.idAccEtapaProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccEtapaProceso[ idAccEtapaProceso=" + idAccEtapaProceso + " ]";
    }

    @XmlTransient
    public List<AccidenteDiligenciasj> getAccidenteDiligenciasjList() {
        return accidenteDiligenciasjList;
    }

    public void setAccidenteDiligenciasjList(List<AccidenteDiligenciasj> accidenteDiligenciasjList) {
        this.accidenteDiligenciasjList = accidenteDiligenciasjList;
    }
    
}
