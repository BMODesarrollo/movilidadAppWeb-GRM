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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "generica_tipo_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaTipoDocumentos.findAll", query = "SELECT g FROM GenericaTipoDocumentos g"),
    @NamedQuery(name = "GenericaTipoDocumentos.findByIdGenericaTipoDocumento", query = "SELECT g FROM GenericaTipoDocumentos g WHERE g.idGenericaTipoDocumento = :idGenericaTipoDocumento"),
    @NamedQuery(name = "GenericaTipoDocumentos.findByNombreTipoDocumento", query = "SELECT g FROM GenericaTipoDocumentos g WHERE g.nombreTipoDocumento = :nombreTipoDocumento"),
    @NamedQuery(name = "GenericaTipoDocumentos.findByDescripcionTipoDocumento", query = "SELECT g FROM GenericaTipoDocumentos g WHERE g.descripcionTipoDocumento = :descripcionTipoDocumento"),
    @NamedQuery(name = "GenericaTipoDocumentos.findByObligatorio", query = "SELECT g FROM GenericaTipoDocumentos g WHERE g.obligatorio = :obligatorio"),
    @NamedQuery(name = "GenericaTipoDocumentos.findByUsername", query = "SELECT g FROM GenericaTipoDocumentos g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaTipoDocumentos.findByCreado", query = "SELECT g FROM GenericaTipoDocumentos g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaTipoDocumentos.findByModificado", query = "SELECT g FROM GenericaTipoDocumentos g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaTipoDocumentos.findByEstadoReg", query = "SELECT g FROM GenericaTipoDocumentos g WHERE g.estadoReg = :estadoReg")})
public class GenericaTipoDocumentos implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGenericaTipoDocumento")
    private List<GenericaDocumentos> genericaDocumentosList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_tipo_documento")
    private Integer idGenericaTipoDocumento;
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
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(optional = false)
    private ParamArea idParamArea;

    public GenericaTipoDocumentos() {
    }

    public GenericaTipoDocumentos(Integer idGenericaTipoDocumento) {
        this.idGenericaTipoDocumento = idGenericaTipoDocumento;
    }

    public GenericaTipoDocumentos(Integer idGenericaTipoDocumento, String nombreTipoDocumento, int obligatorio, String username, int estadoReg) {
        this.idGenericaTipoDocumento = idGenericaTipoDocumento;
        this.nombreTipoDocumento = nombreTipoDocumento;
        this.obligatorio = obligatorio;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaTipoDocumento() {
        return idGenericaTipoDocumento;
    }

    public void setIdGenericaTipoDocumento(Integer idGenericaTipoDocumento) {
        this.idGenericaTipoDocumento = idGenericaTipoDocumento;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaTipoDocumento != null ? idGenericaTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaTipoDocumentos)) {
            return false;
        }
        GenericaTipoDocumentos other = (GenericaTipoDocumentos) object;
        if ((this.idGenericaTipoDocumento == null && other.idGenericaTipoDocumento != null) || (this.idGenericaTipoDocumento != null && !this.idGenericaTipoDocumento.equals(other.idGenericaTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaTipoDocumentos[ idGenericaTipoDocumento=" + idGenericaTipoDocumento + " ]";
    }

    @XmlTransient
    public List<GenericaDocumentos> getGenericaDocumentosList() {
        return genericaDocumentosList;
    }

    public void setGenericaDocumentosList(List<GenericaDocumentos> genericaDocumentosList) {
        this.genericaDocumentosList = genericaDocumentosList;
    }
    
}
