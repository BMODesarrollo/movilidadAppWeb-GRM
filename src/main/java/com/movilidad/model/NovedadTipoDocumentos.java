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
@Table(name = "novedad_tipo_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadTipoDocumentos.findAll", query = "SELECT n FROM NovedadTipoDocumentos n")
    , @NamedQuery(name = "NovedadTipoDocumentos.findByIdNovedadTipoDocumento", query = "SELECT n FROM NovedadTipoDocumentos n WHERE n.idNovedadTipoDocumento = :idNovedadTipoDocumento")
    , @NamedQuery(name = "NovedadTipoDocumentos.findByNombreTipoDocumento", query = "SELECT n FROM NovedadTipoDocumentos n WHERE n.nombreTipoDocumento = :nombreTipoDocumento")
    , @NamedQuery(name = "NovedadTipoDocumentos.findByDescripcionTipoDocumento", query = "SELECT n FROM NovedadTipoDocumentos n WHERE n.descripcionTipoDocumento = :descripcionTipoDocumento")
    , @NamedQuery(name = "NovedadTipoDocumentos.findByObligatorio", query = "SELECT n FROM NovedadTipoDocumentos n WHERE n.obligatorio = :obligatorio")
    , @NamedQuery(name = "NovedadTipoDocumentos.findByUsername", query = "SELECT n FROM NovedadTipoDocumentos n WHERE n.username = :username")
    , @NamedQuery(name = "NovedadTipoDocumentos.findByCreado", query = "SELECT n FROM NovedadTipoDocumentos n WHERE n.creado = :creado")
    , @NamedQuery(name = "NovedadTipoDocumentos.findByModificado", query = "SELECT n FROM NovedadTipoDocumentos n WHERE n.modificado = :modificado")
    , @NamedQuery(name = "NovedadTipoDocumentos.findByEstadoReg", query = "SELECT n FROM NovedadTipoDocumentos n WHERE n.estadoReg = :estadoReg")})
public class NovedadTipoDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_tipo_documento")
    private Integer idNovedadTipoDocumento;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedadTipoDocumento", fetch = FetchType.LAZY)
    private List<NovedadDocumentos> novedadDocumentosList;

    public NovedadTipoDocumentos() {
    }

    public NovedadTipoDocumentos(Integer idNovedadTipoDocumento) {
        this.idNovedadTipoDocumento = idNovedadTipoDocumento;
    }

    public NovedadTipoDocumentos(Integer idNovedadTipoDocumento, String nombreTipoDocumento, int obligatorio, String username, int estadoReg) {
        this.idNovedadTipoDocumento = idNovedadTipoDocumento;
        this.nombreTipoDocumento = nombreTipoDocumento;
        this.obligatorio = obligatorio;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadTipoDocumento() {
        return idNovedadTipoDocumento;
    }

    public void setIdNovedadTipoDocumento(Integer idNovedadTipoDocumento) {
        this.idNovedadTipoDocumento = idNovedadTipoDocumento;
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
    public List<NovedadDocumentos> getNovedadDocumentosList() {
        return novedadDocumentosList;
    }

    public void setNovedadDocumentosList(List<NovedadDocumentos> novedadDocumentosList) {
        this.novedadDocumentosList = novedadDocumentosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadTipoDocumento != null ? idNovedadTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadTipoDocumentos)) {
            return false;
        }
        NovedadTipoDocumentos other = (NovedadTipoDocumentos) object;
        if ((this.idNovedadTipoDocumento == null && other.idNovedadTipoDocumento != null) || (this.idNovedadTipoDocumento != null && !this.idNovedadTipoDocumento.equals(other.idNovedadTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadTipoDocumentos[ idNovedadTipoDocumento=" + idNovedadTipoDocumento + " ]";
    }
    
}
