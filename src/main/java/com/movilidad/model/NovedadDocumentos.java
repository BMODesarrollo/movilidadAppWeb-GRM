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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "novedad_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadDocumentos.findAll", query = "SELECT n FROM NovedadDocumentos n")
    , @NamedQuery(name = "NovedadDocumentos.findByIdNovedadDocumento", query = "SELECT n FROM NovedadDocumentos n WHERE n.idNovedadDocumento = :idNovedadDocumento")
    , @NamedQuery(name = "NovedadDocumentos.findByPathDocumento", query = "SELECT n FROM NovedadDocumentos n WHERE n.pathDocumento = :pathDocumento")
    , @NamedQuery(name = "NovedadDocumentos.findByUsuario", query = "SELECT n FROM NovedadDocumentos n WHERE n.usuario = :usuario")
    , @NamedQuery(name = "NovedadDocumentos.findByCreado", query = "SELECT n FROM NovedadDocumentos n WHERE n.creado = :creado")
    , @NamedQuery(name = "NovedadDocumentos.findByModificado", query = "SELECT n FROM NovedadDocumentos n WHERE n.modificado = :modificado")
    , @NamedQuery(name = "NovedadDocumentos.findByEstadoReg", query = "SELECT n FROM NovedadDocumentos n WHERE n.estadoReg = :estadoReg")})
public class NovedadDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_documento")
    private Integer idNovedadDocumento;
    @Size(max = 100)
    @Column(name = "path_documento")
    private String pathDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @NotNull
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
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Novedad idNovedad;
    @JoinColumn(name = "id_novedad_tipo_documento", referencedColumnName = "id_novedad_tipo_documento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NovedadTipoDocumentos idNovedadTipoDocumento;

    public NovedadDocumentos() {
    }

    public NovedadDocumentos(Integer idNovedadDocumento) {
        this.idNovedadDocumento = idNovedadDocumento;
    }

    public NovedadDocumentos(Integer idNovedadDocumento, String usuario, Date creado, int estadoReg) {
        this.idNovedadDocumento = idNovedadDocumento;
        this.usuario = usuario;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadDocumento() {
        return idNovedadDocumento;
    }

    public void setIdNovedadDocumento(Integer idNovedadDocumento) {
        this.idNovedadDocumento = idNovedadDocumento;
    }

    public String getPathDocumento() {
        return pathDocumento;
    }

    public void setPathDocumento(String pathDocumento) {
        this.pathDocumento = pathDocumento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    public NovedadTipoDocumentos getIdNovedadTipoDocumento() {
        return idNovedadTipoDocumento;
    }

    public void setIdNovedadTipoDocumento(NovedadTipoDocumentos idNovedadTipoDocumento) {
        this.idNovedadTipoDocumento = idNovedadTipoDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadDocumento != null ? idNovedadDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadDocumentos)) {
            return false;
        }
        NovedadDocumentos other = (NovedadDocumentos) object;
        if ((this.idNovedadDocumento == null && other.idNovedadDocumento != null) || (this.idNovedadDocumento != null && !this.idNovedadDocumento.equals(other.idNovedadDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadDocumentos[ idNovedadDocumento=" + idNovedadDocumento + " ]";
    }
    
}
