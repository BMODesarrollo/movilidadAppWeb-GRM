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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cesar
 */
@Entity
@Table(name = "generica_solicitud_motivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaSolicitudMotivo.findAll", query = "SELECT g FROM GenericaSolicitudMotivo g"),
    @NamedQuery(name = "GenericaSolicitudMotivo.findByIdGenericaSolicitudMotivo", query = "SELECT g FROM GenericaSolicitudMotivo g WHERE g.idGenericaSolicitudMotivo = :idGenericaSolicitudMotivo"),
    @NamedQuery(name = "GenericaSolicitudMotivo.findByMotivo", query = "SELECT g FROM GenericaSolicitudMotivo g WHERE g.motivo = :motivo"),
    @NamedQuery(name = "GenericaSolicitudMotivo.findByDescripcion", query = "SELECT g FROM GenericaSolicitudMotivo g WHERE g.descripcion = :descripcion"),
    @NamedQuery(name = "GenericaSolicitudMotivo.findByUsername", query = "SELECT g FROM GenericaSolicitudMotivo g WHERE g.username = :username"),
    @NamedQuery(name = "GenericaSolicitudMotivo.findByCreado", query = "SELECT g FROM GenericaSolicitudMotivo g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaSolicitudMotivo.findByModificado", query = "SELECT g FROM GenericaSolicitudMotivo g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaSolicitudMotivo.findByEstadoReg", query = "SELECT g FROM GenericaSolicitudMotivo g WHERE g.estadoReg = :estadoReg")})
public class GenericaSolicitudMotivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_solicitud_motivo")
    private Integer idGenericaSolicitudMotivo;
    @Size(max = 60)
    @Column(name = "motivo")
    private String motivo;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
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
    @OneToMany(mappedBy = "idGenericaSolicitudMotivo", fetch = FetchType.LAZY)
    private List<GenericaSolicitud> genericaSolicitudList;
    @OneToMany(mappedBy = "idGenericaSolicitudMotivo", fetch = FetchType.LAZY)
    private List<GenericaSolicitudLicencia> genericaSolicitudLicenciaList;

    public GenericaSolicitudMotivo() {
    }

    public GenericaSolicitudMotivo(Integer idGenericaSolicitudMotivo) {
        this.idGenericaSolicitudMotivo = idGenericaSolicitudMotivo;
    }

    public Integer getIdGenericaSolicitudMotivo() {
        return idGenericaSolicitudMotivo;
    }

    public void setIdGenericaSolicitudMotivo(Integer idGenericaSolicitudMotivo) {
        this.idGenericaSolicitudMotivo = idGenericaSolicitudMotivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @XmlTransient
    public List<GenericaSolicitud> getGenericaSolicitudList() {
        return genericaSolicitudList;
    }

    public void setGenericaSolicitudList(List<GenericaSolicitud> genericaSolicitudList) {
        this.genericaSolicitudList = genericaSolicitudList;
    }

    public List<GenericaSolicitudLicencia> getGenericaSolicitudLicenciaList() {
        return genericaSolicitudLicenciaList;
    }

    public void setGenericaSolicitudLicenciaList(List<GenericaSolicitudLicencia> genericaSolicitudLicenciaList) {
        this.genericaSolicitudLicenciaList = genericaSolicitudLicenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaSolicitudMotivo != null ? idGenericaSolicitudMotivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaSolicitudMotivo)) {
            return false;
        }
        GenericaSolicitudMotivo other = (GenericaSolicitudMotivo) object;
        if ((this.idGenericaSolicitudMotivo == null && other.idGenericaSolicitudMotivo != null) || (this.idGenericaSolicitudMotivo != null && !this.idGenericaSolicitudMotivo.equals(other.idGenericaSolicitudMotivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaSolicitudMotivo[ idGenericaSolicitudMotivo=" + idGenericaSolicitudMotivo + " ]";
    }

}
