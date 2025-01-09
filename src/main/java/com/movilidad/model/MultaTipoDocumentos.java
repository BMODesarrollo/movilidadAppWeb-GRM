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
 * @author HP
 */
@Entity
@Table(name = "multa_tipo_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MultaTipoDocumentos.findAll", query = "SELECT m FROM MultaTipoDocumentos m")
    , @NamedQuery(name = "MultaTipoDocumentos.findByIdMultaTipoDocumento", query = "SELECT m FROM MultaTipoDocumentos m WHERE m.idMultaTipoDocumento = :idMultaTipoDocumento")
    , @NamedQuery(name = "MultaTipoDocumentos.findByNombreTipoDocumento", query = "SELECT m FROM MultaTipoDocumentos m WHERE m.nombreTipoDocumento = :nombreTipoDocumento")
    , @NamedQuery(name = "MultaTipoDocumentos.findByDescripcionTipoDocumento", query = "SELECT m FROM MultaTipoDocumentos m WHERE m.descripcionTipoDocumento = :descripcionTipoDocumento")
    , @NamedQuery(name = "MultaTipoDocumentos.findByObligatorio", query = "SELECT m FROM MultaTipoDocumentos m WHERE m.obligatorio = :obligatorio")
    , @NamedQuery(name = "MultaTipoDocumentos.findByUsername", query = "SELECT m FROM MultaTipoDocumentos m WHERE m.username = :username")
    , @NamedQuery(name = "MultaTipoDocumentos.findByCreado", query = "SELECT m FROM MultaTipoDocumentos m WHERE m.creado = :creado")
    , @NamedQuery(name = "MultaTipoDocumentos.findByModificado", query = "SELECT m FROM MultaTipoDocumentos m WHERE m.modificado = :modificado")
    , @NamedQuery(name = "MultaTipoDocumentos.findByEstadoReg", query = "SELECT m FROM MultaTipoDocumentos m WHERE m.estadoReg = :estadoReg")})
public class MultaTipoDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_multa_tipo_documento")
    private Integer idMultaTipoDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_tipo_documento")
    private String nombreTipoDocumento;
    @Size(max = 100)
    @Column(name = "descripcion_tipo_documento")
    private String descripcionTipoDocumento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "obligatorio")
    private int obligatorio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_reg")
    private int estadoReg;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMultaTipoDocumento", fetch = FetchType.LAZY)
    private List<MultaDocumentos> multaDocumentosList;

    public MultaTipoDocumentos() {
    }

    public MultaTipoDocumentos(Integer idMultaTipoDocumento) {
        this.idMultaTipoDocumento = idMultaTipoDocumento;
    }

    public MultaTipoDocumentos(Integer idMultaTipoDocumento, String nombreTipoDocumento, int obligatorio, String username, int estadoReg) {
        this.idMultaTipoDocumento = idMultaTipoDocumento;
        this.nombreTipoDocumento = nombreTipoDocumento;
        this.obligatorio = obligatorio;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdMultaTipoDocumento() {
        return idMultaTipoDocumento;
    }

    public void setIdMultaTipoDocumento(Integer idMultaTipoDocumento) {
        this.idMultaTipoDocumento = idMultaTipoDocumento;
    }

    public String getNombreTipoDocumento() {
        return nombreTipoDocumento;
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento) {
        this.nombreTipoDocumento = nombreTipoDocumento;
    }

    public String getDescripcionTipoDocumento() {
        return descripcionTipoDocumento;
    }

    public void setDescripcionTipoDocumento(String descripcionTipoDocumento) {
        this.descripcionTipoDocumento = descripcionTipoDocumento;
    }

    public int getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(int obligatorio) {
        this.obligatorio = obligatorio;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    @XmlTransient
    public List<MultaDocumentos> getMultaDocumentosList() {
        return multaDocumentosList;
    }

    public void setMultaDocumentosList(List<MultaDocumentos> multaDocumentosList) {
        this.multaDocumentosList = multaDocumentosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMultaTipoDocumento != null ? idMultaTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MultaTipoDocumentos)) {
            return false;
        }
        MultaTipoDocumentos other = (MultaTipoDocumentos) object;
        if ((this.idMultaTipoDocumento == null && other.idMultaTipoDocumento != null) || (this.idMultaTipoDocumento != null && !this.idMultaTipoDocumento.equals(other.idMultaTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MultaTipoDocumentos[ idMultaTipoDocumento=" + idMultaTipoDocumento + " ]";
    }
    
}
