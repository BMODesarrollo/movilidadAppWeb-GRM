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
import javax.persistence.Lob;
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
 * @author HP
 */
@Entity
@Table(name = "accidente_documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccidenteDocumento.findAll", query = "SELECT a FROM AccidenteDocumento a")
    , @NamedQuery(name = "AccidenteDocumento.findByIdAccidenteDocumento", query = "SELECT a FROM AccidenteDocumento a WHERE a.idAccidenteDocumento = :idAccidenteDocumento")
    , @NamedQuery(name = "AccidenteDocumento.findByFecha", query = "SELECT a FROM AccidenteDocumento a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "AccidenteDocumento.findByUsername", query = "SELECT a FROM AccidenteDocumento a WHERE a.username = :username")
    , @NamedQuery(name = "AccidenteDocumento.findByCreado", query = "SELECT a FROM AccidenteDocumento a WHERE a.creado = :creado")
    , @NamedQuery(name = "AccidenteDocumento.findByModificado", query = "SELECT a FROM AccidenteDocumento a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "AccidenteDocumento.findByEstadoReg", query = "SELECT a FROM AccidenteDocumento a WHERE a.estadoReg = :estadoReg")})
public class AccidenteDocumento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente_documento")
    private Integer idAccidenteDocumento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Lob
    @Size(max = 65535)
    @Column(name = "path")
    private String path;
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
    @JoinColumn(name = "id_accidente", referencedColumnName = "id_accidente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Accidente idAccidente;
    @JoinColumn(name = "id_acc_tipo_docs", referencedColumnName = "id_acc_tipo_docs")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccTipoDocs idAccTipoDocs;

    public AccidenteDocumento() {
    }

    public AccidenteDocumento(Integer idAccidenteDocumento) {
        this.idAccidenteDocumento = idAccidenteDocumento;
    }

    public Integer getIdAccidenteDocumento() {
        return idAccidenteDocumento;
    }

    public void setIdAccidenteDocumento(Integer idAccidenteDocumento) {
        this.idAccidenteDocumento = idAccidenteDocumento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Accidente getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Accidente idAccidente) {
        this.idAccidente = idAccidente;
    }

    public AccTipoDocs getIdAccTipoDocs() {
        return idAccTipoDocs;
    }

    public void setIdAccTipoDocs(AccTipoDocs idAccTipoDocs) {
        this.idAccTipoDocs = idAccTipoDocs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccidenteDocumento != null ? idAccidenteDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidenteDocumento)) {
            return false;
        }
        AccidenteDocumento other = (AccidenteDocumento) object;
        if ((this.idAccidenteDocumento == null && other.idAccidenteDocumento != null) || (this.idAccidenteDocumento != null && !this.idAccidenteDocumento.equals(other.idAccidenteDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccidenteDocumento[ idAccidenteDocumento=" + idAccidenteDocumento + " ]";
    }
    
}
