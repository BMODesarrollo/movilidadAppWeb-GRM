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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "pqr_responsable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PqrResponsable.findAll", query = "SELECT p FROM PqrResponsable p"),
    @NamedQuery(name = "PqrResponsable.findByIdPqrResponsable", query = "SELECT p FROM PqrResponsable p WHERE p.idPqrResponsable = :idPqrResponsable"),
    @NamedQuery(name = "PqrResponsable.findByResponsable", query = "SELECT p FROM PqrResponsable p WHERE p.responsable = :responsable"),
    @NamedQuery(name = "PqrResponsable.findByUsername", query = "SELECT p FROM PqrResponsable p WHERE p.username = :username"),
    @NamedQuery(name = "PqrResponsable.findByCreado", query = "SELECT p FROM PqrResponsable p WHERE p.creado = :creado"),
    @NamedQuery(name = "PqrResponsable.findByModificado", query = "SELECT p FROM PqrResponsable p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PqrResponsable.findByEstadoReg", query = "SELECT p FROM PqrResponsable p WHERE p.estadoReg = :estadoReg")})
public class PqrResponsable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pqr_responsable")
    private Integer idPqrResponsable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "responsable")
    private String responsable;
    @Column(name = "codigo_notificacion")
    private String codigoNotificacion;
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
    @OneToMany(mappedBy = "idPqrResponsable", fetch = FetchType.LAZY)
    private List<PqrTipo> pqrTipoList;

    public PqrResponsable() {
    }

    public PqrResponsable(Integer idPqrResponsable) {
        this.idPqrResponsable = idPqrResponsable;
    }

    public PqrResponsable(Integer idPqrResponsable, String responsable) {
        this.idPqrResponsable = idPqrResponsable;
        this.responsable = responsable;
    }

    public Integer getIdPqrResponsable() {
        return idPqrResponsable;
    }

    public void setIdPqrResponsable(Integer idPqrResponsable) {
        this.idPqrResponsable = idPqrResponsable;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getCodigoNotificacion() {
        return codigoNotificacion;
    }

    public void setCodigoNotificacion(String codigoNotificacion) {
        this.codigoNotificacion = codigoNotificacion;
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
    public List<PqrTipo> getPqrTipoList() {
        return pqrTipoList;
    }

    public void setPqrTipoList(List<PqrTipo> pqrTipoList) {
        this.pqrTipoList = pqrTipoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPqrResponsable != null ? idPqrResponsable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PqrResponsable)) {
            return false;
        }
        PqrResponsable other = (PqrResponsable) object;
        if ((this.idPqrResponsable == null && other.idPqrResponsable != null) || (this.idPqrResponsable != null && !this.idPqrResponsable.equals(other.idPqrResponsable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PqrResponsable[ idPqrResponsable=" + idPqrResponsable + " ]";
    }

}
