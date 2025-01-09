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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "sst_documento_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstDocumentoEmpresa.findAll", query = "SELECT s FROM SstDocumentoEmpresa s"),
    @NamedQuery(name = "SstDocumentoEmpresa.findByIdSstDocumentoEmpresa", query = "SELECT s FROM SstDocumentoEmpresa s WHERE s.idSstDocumentoEmpresa = :idSstDocumentoEmpresa"),
    @NamedQuery(name = "SstDocumentoEmpresa.findByTipoDocumento", query = "SELECT s FROM SstDocumentoEmpresa s WHERE s.tipoDocumento = :tipoDocumento"),
    @NamedQuery(name = "SstDocumentoEmpresa.findByVigencia", query = "SELECT s FROM SstDocumentoEmpresa s WHERE s.vigencia = :vigencia"),
    @NamedQuery(name = "SstDocumentoEmpresa.findByRequerido", query = "SELECT s FROM SstDocumentoEmpresa s WHERE s.requerido = :requerido"),
    @NamedQuery(name = "SstDocumentoEmpresa.findByUsername", query = "SELECT s FROM SstDocumentoEmpresa s WHERE s.username = :username"),
    @NamedQuery(name = "SstDocumentoEmpresa.findByCreado", query = "SELECT s FROM SstDocumentoEmpresa s WHERE s.creado = :creado"),
    @NamedQuery(name = "SstDocumentoEmpresa.findByModificado", query = "SELECT s FROM SstDocumentoEmpresa s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SstDocumentoEmpresa.findByEstadoReg", query = "SELECT s FROM SstDocumentoEmpresa s WHERE s.estadoReg = :estadoReg")})
public class SstDocumentoEmpresa implements Serializable {

    @OneToMany(mappedBy = "idSstEmpresaTipoDoc", fetch = FetchType.LAZY)
    private List<SstEmpresaDocs> sstEmpresaDocsList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_documento_empresa")
    private Integer idSstDocumentoEmpresa;
    @Size(max = 60)
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "vigencia")
    private Integer vigencia;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "requerido")
    private Integer requerido;
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

    public SstDocumentoEmpresa() {
    }

    public SstDocumentoEmpresa(Integer idSstDocumentoEmpresa) {
        this.idSstDocumentoEmpresa = idSstDocumentoEmpresa;
    }

    public Integer getIdSstDocumentoEmpresa() {
        return idSstDocumentoEmpresa;
    }

    public void setIdSstDocumentoEmpresa(Integer idSstDocumentoEmpresa) {
        this.idSstDocumentoEmpresa = idSstDocumentoEmpresa;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    public Integer getRequerido() {
        return requerido;
    }

    public void setRequerido(Integer requerido) {
        this.requerido = requerido;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstDocumentoEmpresa != null ? idSstDocumentoEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstDocumentoEmpresa)) {
            return false;
        }
        SstDocumentoEmpresa other = (SstDocumentoEmpresa) object;
        if ((this.idSstDocumentoEmpresa == null && other.idSstDocumentoEmpresa != null) || (this.idSstDocumentoEmpresa != null && !this.idSstDocumentoEmpresa.equals(other.idSstDocumentoEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstDocumentoEmpresa[ idSstDocumentoEmpresa=" + idSstDocumentoEmpresa + " ]";
    }

    @XmlTransient
    public List<SstEmpresaDocs> getSstEmpresaDocsList() {
        return sstEmpresaDocsList;
    }

    public void setSstEmpresaDocsList(List<SstEmpresaDocs> sstEmpresaDocsList) {
        this.sstEmpresaDocsList = sstEmpresaDocsList;
    }
    
}
