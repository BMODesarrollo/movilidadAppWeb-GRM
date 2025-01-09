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
@Table(name = "acc_novedad_infraestruc_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccNovedadInfraestrucDocumentos.findAll", query = "SELECT a FROM AccNovedadInfraestrucDocumentos a"),
    @NamedQuery(name = "AccNovedadInfraestrucDocumentos.findByIdAccNovedadInfraestrucDocumento", query = "SELECT a FROM AccNovedadInfraestrucDocumentos a WHERE a.idAccNovedadInfraestrucDocumento = :idAccNovedadInfraestrucDocumento"),
    @NamedQuery(name = "AccNovedadInfraestrucDocumentos.findByPathDocumento", query = "SELECT a FROM AccNovedadInfraestrucDocumentos a WHERE a.pathDocumento = :pathDocumento"),
    @NamedQuery(name = "AccNovedadInfraestrucDocumentos.findByUsuario", query = "SELECT a FROM AccNovedadInfraestrucDocumentos a WHERE a.usuario = :usuario"),
    @NamedQuery(name = "AccNovedadInfraestrucDocumentos.findByCreado", query = "SELECT a FROM AccNovedadInfraestrucDocumentos a WHERE a.creado = :creado"),
    @NamedQuery(name = "AccNovedadInfraestrucDocumentos.findByModificado", query = "SELECT a FROM AccNovedadInfraestrucDocumentos a WHERE a.modificado = :modificado"),
    @NamedQuery(name = "AccNovedadInfraestrucDocumentos.findByEstadoReg", query = "SELECT a FROM AccNovedadInfraestrucDocumentos a WHERE a.estadoReg = :estadoReg")})
public class AccNovedadInfraestrucDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_novedad_infraestruc_documento")
    private Integer idAccNovedadInfraestrucDocumento;
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
    @JoinColumn(name = "id_acc_novedad_infraestruc", referencedColumnName = "id_acc_novedad_infraestruc")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AccNovedadInfraestruc idAccNovedadInfraestruc;
    @JoinColumn(name = "id_acc_novedad_infraestruc_tipo_documentos", referencedColumnName = "id_acc_novedad_infraestruc_tipo_documentos")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccNovedadInfraestrucTipoDocumentos idAccNovedadInfraestrucTipoDocumentos;

    public AccNovedadInfraestrucDocumentos() {
    }

    public AccNovedadInfraestrucDocumentos(Integer idAccNovedadInfraestrucDocumento) {
        this.idAccNovedadInfraestrucDocumento = idAccNovedadInfraestrucDocumento;
    }

    public AccNovedadInfraestrucDocumentos(Integer idAccNovedadInfraestrucDocumento, String usuario, Date creado, int estadoReg) {
        this.idAccNovedadInfraestrucDocumento = idAccNovedadInfraestrucDocumento;
        this.usuario = usuario;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdAccNovedadInfraestrucDocumento() {
        return idAccNovedadInfraestrucDocumento;
    }

    public void setIdAccNovedadInfraestrucDocumento(Integer idAccNovedadInfraestrucDocumento) {
        this.idAccNovedadInfraestrucDocumento = idAccNovedadInfraestrucDocumento;
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

    public AccNovedadInfraestruc getIdAccNovedadInfraestruc() {
        return idAccNovedadInfraestruc;
    }

    public void setIdAccNovedadInfraestruc(AccNovedadInfraestruc idAccNovedadInfraestruc) {
        this.idAccNovedadInfraestruc = idAccNovedadInfraestruc;
    }

    public AccNovedadInfraestrucTipoDocumentos getIdAccNovedadInfraestrucTipoDocumentos() {
        return idAccNovedadInfraestrucTipoDocumentos;
    }

    public void setIdAccNovedadInfraestrucTipoDocumentos(AccNovedadInfraestrucTipoDocumentos idAccNovedadInfraestrucTipoDocumentos) {
        this.idAccNovedadInfraestrucTipoDocumentos = idAccNovedadInfraestrucTipoDocumentos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccNovedadInfraestrucDocumento != null ? idAccNovedadInfraestrucDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccNovedadInfraestrucDocumentos)) {
            return false;
        }
        AccNovedadInfraestrucDocumentos other = (AccNovedadInfraestrucDocumentos) object;
        if ((this.idAccNovedadInfraestrucDocumento == null && other.idAccNovedadInfraestrucDocumento != null) || (this.idAccNovedadInfraestrucDocumento != null && !this.idAccNovedadInfraestrucDocumento.equals(other.idAccNovedadInfraestrucDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccNovedadInfraestrucDocumentos[ idAccNovedadInfraestrucDocumento=" + idAccNovedadInfraestrucDocumento + " ]";
    }
    
}
