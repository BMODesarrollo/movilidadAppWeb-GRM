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
@Table(name = "multa_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MultaDocumentos.findAll", query = "SELECT m FROM MultaDocumentos m")
    , @NamedQuery(name = "MultaDocumentos.findByIdMultaDocumento", query = "SELECT m FROM MultaDocumentos m WHERE m.idMultaDocumento = :idMultaDocumento")
    , @NamedQuery(name = "MultaDocumentos.findByPathDocumento", query = "SELECT m FROM MultaDocumentos m WHERE m.pathDocumento = :pathDocumento")
    , @NamedQuery(name = "MultaDocumentos.findByUsuario", query = "SELECT m FROM MultaDocumentos m WHERE m.usuario = :usuario")
    , @NamedQuery(name = "MultaDocumentos.findByCreado", query = "SELECT m FROM MultaDocumentos m WHERE m.creado = :creado")
    , @NamedQuery(name = "MultaDocumentos.findByModificado", query = "SELECT m FROM MultaDocumentos m WHERE m.modificado = :modificado")
    , @NamedQuery(name = "MultaDocumentos.findByEstadoReg", query = "SELECT m FROM MultaDocumentos m WHERE m.estadoReg = :estadoReg")})
public class MultaDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_multa_documento")
    private Integer idMultaDocumento;
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
    @JoinColumn(name = "id_multa", referencedColumnName = "id_multa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Multa idMulta;
    @JoinColumn(name = "id_multa_tipo_documento", referencedColumnName = "id_multa_tipo_documento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MultaTipoDocumentos idMultaTipoDocumento;

    public MultaDocumentos() {
    }

    public MultaDocumentos(Integer idMultaDocumento) {
        this.idMultaDocumento = idMultaDocumento;
    }

    public MultaDocumentos(Integer idMultaDocumento, String usuario, Date creado, int estadoReg) {
        this.idMultaDocumento = idMultaDocumento;
        this.usuario = usuario;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdMultaDocumento() {
        return idMultaDocumento;
    }

    public void setIdMultaDocumento(Integer idMultaDocumento) {
        this.idMultaDocumento = idMultaDocumento;
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

    public Multa getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(Multa idMulta) {
        this.idMulta = idMulta;
    }

    public MultaTipoDocumentos getIdMultaTipoDocumento() {
        return idMultaTipoDocumento;
    }

    public void setIdMultaTipoDocumento(MultaTipoDocumentos idMultaTipoDocumento) {
        this.idMultaTipoDocumento = idMultaTipoDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMultaDocumento != null ? idMultaDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MultaDocumentos)) {
            return false;
        }
        MultaDocumentos other = (MultaDocumentos) object;
        if ((this.idMultaDocumento == null && other.idMultaDocumento != null) || (this.idMultaDocumento != null && !this.idMultaDocumento.equals(other.idMultaDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.MultaDocumentos[ idMultaDocumento=" + idMultaDocumento + " ]";
    }
    
}
