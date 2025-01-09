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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "pqr_maestro_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PqrMaestroDocumentos.findAll", query = "SELECT p FROM PqrMaestroDocumentos p"),
    @NamedQuery(name = "PqrMaestroDocumentos.findByIdPqrMaestroDocumento", query = "SELECT p FROM PqrMaestroDocumentos p WHERE p.idPqrMaestroDocumento = :idPqrMaestroDocumento"),
    @NamedQuery(name = "PqrMaestroDocumentos.findByTipoDocumento", query = "SELECT p FROM PqrMaestroDocumentos p WHERE p.tipoDocumento = :tipoDocumento"),
    @NamedQuery(name = "PqrMaestroDocumentos.findByPathDocumento", query = "SELECT p FROM PqrMaestroDocumentos p WHERE p.pathDocumento = :pathDocumento"),
    @NamedQuery(name = "PqrMaestroDocumentos.findByUsuario", query = "SELECT p FROM PqrMaestroDocumentos p WHERE p.usuario = :usuario"),
    @NamedQuery(name = "PqrMaestroDocumentos.findByCreado", query = "SELECT p FROM PqrMaestroDocumentos p WHERE p.creado = :creado"),
    @NamedQuery(name = "PqrMaestroDocumentos.findByModificado", query = "SELECT p FROM PqrMaestroDocumentos p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PqrMaestroDocumentos.findByEstadoReg", query = "SELECT p FROM PqrMaestroDocumentos p WHERE p.estadoReg = :estadoReg")})
public class PqrMaestroDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pqr_maestro_documento")
    private Integer idPqrMaestroDocumento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_documento")
    private int tipoDocumento;
    @Size(max = 150)
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
    @JoinColumn(name = "id_pqr_maestro", referencedColumnName = "id_pqr_maestro")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PqrMaestro idPqrMaestro;

    public PqrMaestroDocumentos() {
    }

    public PqrMaestroDocumentos(Integer idPqrMaestroDocumento) {
        this.idPqrMaestroDocumento = idPqrMaestroDocumento;
    }

    public PqrMaestroDocumentos(Integer idPqrMaestroDocumento, int tipoDocumento, String usuario, Date creado, int estadoReg) {
        this.idPqrMaestroDocumento = idPqrMaestroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.usuario = usuario;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPqrMaestroDocumento() {
        return idPqrMaestroDocumento;
    }

    public void setIdPqrMaestroDocumento(Integer idPqrMaestroDocumento) {
        this.idPqrMaestroDocumento = idPqrMaestroDocumento;
    }

    public int getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(int tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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

    public PqrMaestro getIdPqrMaestro() {
        return idPqrMaestro;
    }

    public void setIdPqrMaestro(PqrMaestro idPqrMaestro) {
        this.idPqrMaestro = idPqrMaestro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPqrMaestroDocumento != null ? idPqrMaestroDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PqrMaestroDocumentos)) {
            return false;
        }
        PqrMaestroDocumentos other = (PqrMaestroDocumentos) object;
        if ((this.idPqrMaestroDocumento == null && other.idPqrMaestroDocumento != null) || (this.idPqrMaestroDocumento != null && !this.idPqrMaestroDocumento.equals(other.idPqrMaestroDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PqrMaestroDocumentos[ idPqrMaestroDocumento=" + idPqrMaestroDocumento + " ]";
    }
    
}
