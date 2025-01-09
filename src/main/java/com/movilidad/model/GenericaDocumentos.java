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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "generica_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaDocumentos.findAll", query = "SELECT g FROM GenericaDocumentos g"),
    @NamedQuery(name = "GenericaDocumentos.findByIdGenericaDocumento", query = "SELECT g FROM GenericaDocumentos g WHERE g.idGenericaDocumento = :idGenericaDocumento"),
    @NamedQuery(name = "GenericaDocumentos.findByPathDocumento", query = "SELECT g FROM GenericaDocumentos g WHERE g.pathDocumento = :pathDocumento"),
    @NamedQuery(name = "GenericaDocumentos.findByUsuario", query = "SELECT g FROM GenericaDocumentos g WHERE g.usuario = :usuario"),
    @NamedQuery(name = "GenericaDocumentos.findByCreado", query = "SELECT g FROM GenericaDocumentos g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaDocumentos.findByModificado", query = "SELECT g FROM GenericaDocumentos g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaDocumentos.findByEstadoReg", query = "SELECT g FROM GenericaDocumentos g WHERE g.estadoReg = :estadoReg")})
public class GenericaDocumentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_documento")
    private Integer idGenericaDocumento;
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
    @JoinColumn(name = "id_generica_tipo_documento", referencedColumnName = "id_generica_tipo_documento")
    @ManyToOne(optional = false)
    private GenericaTipoDocumentos idGenericaTipoDocumento;
    @JoinColumn(name = "id_generica", referencedColumnName = "id_generica")
    @ManyToOne(optional = false)
    private Generica idGenerica;

    public GenericaDocumentos() {
    }

    public GenericaDocumentos(Integer idGenericaDocumento) {
        this.idGenericaDocumento = idGenericaDocumento;
    }

    public GenericaDocumentos(Integer idGenericaDocumento, String usuario, Date creado, int estadoReg) {
        this.idGenericaDocumento = idGenericaDocumento;
        this.usuario = usuario;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGenericaDocumento() {
        return idGenericaDocumento;
    }

    public void setIdGenericaDocumento(Integer idGenericaDocumento) {
        this.idGenericaDocumento = idGenericaDocumento;
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

    public GenericaTipoDocumentos getIdGenericaTipoDocumento() {
        return idGenericaTipoDocumento;
    }

    public void setIdGenericaTipoDocumento(GenericaTipoDocumentos idGenericaTipoDocumento) {
        this.idGenericaTipoDocumento = idGenericaTipoDocumento;
    }

    public Generica getIdGenerica() {
        return idGenerica;
    }

    public void setIdGenerica(Generica idGenerica) {
        this.idGenerica = idGenerica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaDocumento != null ? idGenericaDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaDocumentos)) {
            return false;
        }
        GenericaDocumentos other = (GenericaDocumentos) object;
        if ((this.idGenericaDocumento == null && other.idGenericaDocumento != null) || (this.idGenericaDocumento != null && !this.idGenericaDocumento.equals(other.idGenericaDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaDocumentos[ idGenericaDocumento=" + idGenericaDocumento + " ]";
    }
    
}
