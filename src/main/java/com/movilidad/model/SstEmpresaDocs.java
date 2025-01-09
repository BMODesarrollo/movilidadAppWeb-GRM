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
 * @author cesar
 */
@Entity
@Table(name = "sst_empresa_docs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstEmpresaDocs.findAll", query = "SELECT s FROM SstEmpresaDocs s"),
    @NamedQuery(name = "SstEmpresaDocs.findByIdSstEmpresaDocs", query = "SELECT s FROM SstEmpresaDocs s WHERE s.idSstEmpresaDocs = :idSstEmpresaDocs"),
    @NamedQuery(name = "SstEmpresaDocs.findByNumero", query = "SELECT s FROM SstEmpresaDocs s WHERE s.numero = :numero"),
    @NamedQuery(name = "SstEmpresaDocs.findByDesde", query = "SELECT s FROM SstEmpresaDocs s WHERE s.desde = :desde"),
    @NamedQuery(name = "SstEmpresaDocs.findByHasta", query = "SELECT s FROM SstEmpresaDocs s WHERE s.hasta = :hasta"),
    @NamedQuery(name = "SstEmpresaDocs.findByPath", query = "SELECT s FROM SstEmpresaDocs s WHERE s.path = :path"),
    @NamedQuery(name = "SstEmpresaDocs.findByActivo", query = "SELECT s FROM SstEmpresaDocs s WHERE s.activo = :activo"),
    @NamedQuery(name = "SstEmpresaDocs.findByUsername", query = "SELECT s FROM SstEmpresaDocs s WHERE s.username = :username"),
    @NamedQuery(name = "SstEmpresaDocs.findByCreado", query = "SELECT s FROM SstEmpresaDocs s WHERE s.creado = :creado"),
    @NamedQuery(name = "SstEmpresaDocs.findByModificado", query = "SELECT s FROM SstEmpresaDocs s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SstEmpresaDocs.findByEstadoReg", query = "SELECT s FROM SstEmpresaDocs s WHERE s.estadoReg = :estadoReg")})
public class SstEmpresaDocs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_empresa_docs")
    private Integer idSstEmpresaDocs;
    @Size(max = 15)
    @Column(name = "numero")
    private String numero;
    @Column(name = "desde")
    @Temporal(TemporalType.DATE)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;
    @Size(max = 255)
    @Column(name = "path")
    private String path;
    @Column(name = "activo")
    private int activo;
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
    @JoinColumn(name = "id_sst_empresa_tipo_doc", referencedColumnName = "id_sst_documento_empresa")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstDocumentoEmpresa idSstEmpresaTipoDoc;
    @JoinColumn(name = "id_sst_empresa", referencedColumnName = "id_sst_empresa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstEmpresa idSstEmpresa;

    public SstEmpresaDocs() {
    }

    public SstEmpresaDocs(Integer idSstEmpresaDocs) {
        this.idSstEmpresaDocs = idSstEmpresaDocs;
    }

    public Integer getIdSstEmpresaDocs() {
        return idSstEmpresaDocs;
    }

    public void setIdSstEmpresaDocs(Integer idSstEmpresaDocs) {
        this.idSstEmpresaDocs = idSstEmpresaDocs;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
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

    public SstDocumentoEmpresa getIdSstEmpresaTipoDoc() {
        return idSstEmpresaTipoDoc;
    }

    public void setIdSstEmpresaTipoDoc(SstDocumentoEmpresa idSstEmpresaTipoDoc) {
        this.idSstEmpresaTipoDoc = idSstEmpresaTipoDoc;
    }

    public SstEmpresa getIdSstEmpresa() {
        return idSstEmpresa;
    }

    public void setIdSstEmpresa(SstEmpresa idSstEmpresa) {
        this.idSstEmpresa = idSstEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstEmpresaDocs != null ? idSstEmpresaDocs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstEmpresaDocs)) {
            return false;
        }
        SstEmpresaDocs other = (SstEmpresaDocs) object;
        if ((this.idSstEmpresaDocs == null && other.idSstEmpresaDocs != null) || (this.idSstEmpresaDocs != null && !this.idSstEmpresaDocs.equals(other.idSstEmpresaDocs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstEmpresaDocs[ idSstEmpresaDocs=" + idSstEmpresaDocs + " ]";
    }

}
