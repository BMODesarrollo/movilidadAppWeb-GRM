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
import javax.persistence.CascadeType;
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
@Table(name = "acc_tipo_docs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccTipoDocs.findAll", query = "SELECT a FROM AccTipoDocs a")
    , @NamedQuery(name = "AccTipoDocs.findByIdAccTipoDocs", query = "SELECT a FROM AccTipoDocs a WHERE a.idAccTipoDocs = :idAccTipoDocs")
    , @NamedQuery(name = "AccTipoDocs.findByTipoDocs", query = "SELECT a FROM AccTipoDocs a WHERE a.tipoDocs = :tipoDocs")
    , @NamedQuery(name = "AccTipoDocs.findByUsername", query = "SELECT a FROM AccTipoDocs a WHERE a.username = :username")
    , @NamedQuery(name = "AccTipoDocs.findByCreado", query = "SELECT a FROM AccTipoDocs a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccTipoDocs.findByModificado", query = "SELECT a FROM AccTipoDocs a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccTipoDocs.findByEstadoReg", query = "SELECT a FROM AccTipoDocs a WHERE a.estadoReg = :estadoReg")})
public class AccTipoDocs implements Serializable {

    @OneToMany(mappedBy = "idAccTpDocs", fetch = FetchType.LAZY)
    private List<CableAccDocumento> cableAccDocumentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccTipoDocumento")
    private List<AccChecklist> accChecklistList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_tipo_docs")
    private Integer idAccTipoDocs;
    @Size(max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 60)
    @Column(name = "tipo_docs")
    private String tipoDocs;
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
    @OneToMany(mappedBy = "idAccTipoDocs", fetch = FetchType.LAZY)
    private List<AccidenteDocumento> accidenteDocumentoList;

    public AccTipoDocs() {
    }

    public AccTipoDocs(Integer idAccTipoDocs) {
        this.idAccTipoDocs = idAccTipoDocs;
    }

    public Integer getIdAccTipoDocs() {
        return idAccTipoDocs;
    }

    public void setIdAccTipoDocs(Integer idAccTipoDocs) {
        this.idAccTipoDocs = idAccTipoDocs;
    }

    public String getTipoDocs() {
        return tipoDocs;
    }

    public void setTipoDocs(String tipoDocs) {
        this.tipoDocs = tipoDocs;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public List<AccidenteDocumento> getAccidenteDocumentoList() {
        return accidenteDocumentoList;
    }

    public void setAccidenteDocumentoList(List<AccidenteDocumento> accidenteDocumentoList) {
        this.accidenteDocumentoList = accidenteDocumentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccTipoDocs != null ? idAccTipoDocs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccTipoDocs)) {
            return false;
        }
        AccTipoDocs other = (AccTipoDocs) object;
        if ((this.idAccTipoDocs == null && other.idAccTipoDocs != null) || (this.idAccTipoDocs != null && !this.idAccTipoDocs.equals(other.idAccTipoDocs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccTipoDocs[ idAccTipoDocs=" + idAccTipoDocs + " ]";
    }

    @XmlTransient
    public List<AccChecklist> getAccChecklistList() {
        return accChecklistList;
    }

    public void setAccChecklistList(List<AccChecklist> accChecklistList) {
        this.accChecklistList = accChecklistList;
    }

    @XmlTransient
    public List<CableAccDocumento> getCableAccDocumentoList() {
        return cableAccDocumentoList;
    }

    public void setCableAccDocumentoList(List<CableAccDocumento> cableAccDocumentoList) {
        this.cableAccDocumentoList = cableAccDocumentoList;
    }
    
}
