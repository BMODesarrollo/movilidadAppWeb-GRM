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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@Entity
@Table(name = "cable_acc_documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CableAccDocumento.findAll", query = "SELECT c FROM CableAccDocumento c")
    , @NamedQuery(name = "CableAccDocumento.findByIdCableAccDocumento", query = "SELECT c FROM CableAccDocumento c WHERE c.idCableAccDocumento = :idCableAccDocumento")
    , @NamedQuery(name = "CableAccDocumento.findByDescripcion", query = "SELECT c FROM CableAccDocumento c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "CableAccDocumento.findByPaths", query = "SELECT c FROM CableAccDocumento c WHERE c.paths = :paths")
    , @NamedQuery(name = "CableAccDocumento.findByUsername", query = "SELECT c FROM CableAccDocumento c WHERE c.username = :username")
    , @NamedQuery(name = "CableAccDocumento.findByCreado", query = "SELECT c FROM CableAccDocumento c WHERE c.creado = :creado")
    , @NamedQuery(name = "CableAccDocumento.findByModificado", query = "SELECT c FROM CableAccDocumento c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "CableAccDocumento.findByEstadoReg", query = "SELECT c FROM CableAccDocumento c WHERE c.estadoReg = :estadoReg")})
public class CableAccDocumento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cable_acc_documento")
    private Integer idCableAccDocumento;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "paths")
    private String paths;
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
    @JoinColumn(name = "id_acc_tp_docs", referencedColumnName = "id_acc_tipo_docs")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoDocs idAccTpDocs;
    @JoinColumn(name = "id_cable_accidentalidad", referencedColumnName = "id_cable_accidentalidad")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableAccidentalidad idCableAccidentalidad;

    public CableAccDocumento() {
    }

    public CableAccDocumento(Integer idCableAccDocumento) {
        this.idCableAccDocumento = idCableAccDocumento;
    }

    public Integer getIdCableAccDocumento() {
        return idCableAccDocumento;
    }

    public void setIdCableAccDocumento(Integer idCableAccDocumento) {
        this.idCableAccDocumento = idCableAccDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPaths() {
        return paths;
    }

    public void setPath(String paths) {
        this.paths = paths;
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

    public AccTipoDocs getIdAccTpDocs() {
        return idAccTpDocs;
    }

    public void setIdAccTpDocs(AccTipoDocs idAccTpDocs) {
        this.idAccTpDocs = idAccTpDocs;
    }

    public CableAccidentalidad getIdCableAccidentalidad() {
        return idCableAccidentalidad;
    }

    public void setIdCableAccidentalidad(CableAccidentalidad idCableAccidentalidad) {
        this.idCableAccidentalidad = idCableAccidentalidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCableAccDocumento != null ? idCableAccDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CableAccDocumento)) {
            return false;
        }
        CableAccDocumento other = (CableAccDocumento) object;
        if ((this.idCableAccDocumento == null && other.idCableAccDocumento != null) || (this.idCableAccDocumento != null && !this.idCableAccDocumento.equals(other.idCableAccDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.CableAccDocumento[ idCableAccDocumento=" + idCableAccDocumento + " ]";
    }
    
}
