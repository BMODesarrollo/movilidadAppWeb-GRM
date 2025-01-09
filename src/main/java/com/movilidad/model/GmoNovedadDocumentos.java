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
@Table(name = "gmo_novedad_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GmoNovedadDocumentos.findAll", query = "SELECT g FROM GmoNovedadDocumentos g"),
    @NamedQuery(name = "GmoNovedadDocumentos.findByIdGmoNovedadInfrastrucDocumento", query = "SELECT g FROM GmoNovedadDocumentos g WHERE g.idGmoNovedadInfrastrucDocumento = :idGmoNovedadInfrastrucDocumento"),
    @NamedQuery(name = "GmoNovedadDocumentos.findByPathDocumento", query = "SELECT g FROM GmoNovedadDocumentos g WHERE g.pathDocumento = :pathDocumento"),
    @NamedQuery(name = "GmoNovedadDocumentos.findByUsuario", query = "SELECT g FROM GmoNovedadDocumentos g WHERE g.usuario = :usuario"),
    @NamedQuery(name = "GmoNovedadDocumentos.findByCreado", query = "SELECT g FROM GmoNovedadDocumentos g WHERE g.creado = :creado"),
    @NamedQuery(name = "GmoNovedadDocumentos.findByModificado", query = "SELECT g FROM GmoNovedadDocumentos g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GmoNovedadDocumentos.findByEstadoReg", query = "SELECT g FROM GmoNovedadDocumentos g WHERE g.estadoReg = :estadoReg")})
public class GmoNovedadDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gmo_novedad_infrastruc_documento")
    private Integer idGmoNovedadInfrastrucDocumento;
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
    @JoinColumn(name = "id_gmo_novedad_infrastruc", referencedColumnName = "id_gmo_novedad_infrastruc")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GmoNovedadInfrastruc idGmoNovedadInfrastruc;

    public GmoNovedadDocumentos() {
    }

    public GmoNovedadDocumentos(Integer idGmoNovedadInfrastrucDocumento) {
        this.idGmoNovedadInfrastrucDocumento = idGmoNovedadInfrastrucDocumento;
    }

    public GmoNovedadDocumentos(Integer idGmoNovedadInfrastrucDocumento, String usuario, Date creado, int estadoReg) {
        this.idGmoNovedadInfrastrucDocumento = idGmoNovedadInfrastrucDocumento;
        this.usuario = usuario;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdGmoNovedadInfrastrucDocumento() {
        return idGmoNovedadInfrastrucDocumento;
    }

    public void setIdGmoNovedadInfrastrucDocumento(Integer idGmoNovedadInfrastrucDocumento) {
        this.idGmoNovedadInfrastrucDocumento = idGmoNovedadInfrastrucDocumento;
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

    public GmoNovedadInfrastruc getIdGmoNovedadInfrastruc() {
        return idGmoNovedadInfrastruc;
    }

    public void setIdGmoNovedadInfrastruc(GmoNovedadInfrastruc idGmoNovedadInfrastruc) {
        this.idGmoNovedadInfrastruc = idGmoNovedadInfrastruc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGmoNovedadInfrastrucDocumento != null ? idGmoNovedadInfrastrucDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GmoNovedadDocumentos)) {
            return false;
        }
        GmoNovedadDocumentos other = (GmoNovedadDocumentos) object;
        if ((this.idGmoNovedadInfrastrucDocumento == null && other.idGmoNovedadInfrastrucDocumento != null) || (this.idGmoNovedadInfrastrucDocumento != null && !this.idGmoNovedadInfrastrucDocumento.equals(other.idGmoNovedadInfrastrucDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GmoNovedadDocumentos[ idGmoNovedadInfrastrucDocumento=" + idGmoNovedadInfrastrucDocumento + " ]";
    }
    
}
