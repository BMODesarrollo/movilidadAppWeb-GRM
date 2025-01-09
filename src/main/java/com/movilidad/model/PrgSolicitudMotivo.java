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
@Table(name = "prg_solicitud_motivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgSolicitudMotivo.findAll", query = "SELECT p FROM PrgSolicitudMotivo p"),
    @NamedQuery(name = "PrgSolicitudMotivo.findByIdPrgSolicitudMotivo", query = "SELECT p FROM PrgSolicitudMotivo p WHERE p.idPrgSolicitudMotivo = :idPrgSolicitudMotivo"),
    @NamedQuery(name = "PrgSolicitudMotivo.findByMotivo", query = "SELECT p FROM PrgSolicitudMotivo p WHERE p.motivo = :motivo"),
    @NamedQuery(name = "PrgSolicitudMotivo.findByDescripcion", query = "SELECT p FROM PrgSolicitudMotivo p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "PrgSolicitudMotivo.findByUsername", query = "SELECT p FROM PrgSolicitudMotivo p WHERE p.username = :username"),
    @NamedQuery(name = "PrgSolicitudMotivo.findByCreado", query = "SELECT p FROM PrgSolicitudMotivo p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgSolicitudMotivo.findByModificado", query = "SELECT p FROM PrgSolicitudMotivo p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgSolicitudMotivo.findByEstadoReg", query = "SELECT p FROM PrgSolicitudMotivo p WHERE p.estadoReg = :estadoReg")})
public class PrgSolicitudMotivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_solicitud_motivo")
    private Integer idPrgSolicitudMotivo;
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
    @OneToMany(mappedBy = "idPrgSolicitudMotivo", fetch = FetchType.LAZY)
    private List<PrgSolicitud> prgSolicitudList;
    @OneToMany(mappedBy = "idPrgSolicitudMotivo", fetch = FetchType.LAZY)
    private List<PrgSolicitudLicencia> prgSolicitudLicenciaList;

    public PrgSolicitudMotivo() {
    }

    public PrgSolicitudMotivo(Integer idPrgSolicitudMotivo) {
        this.idPrgSolicitudMotivo = idPrgSolicitudMotivo;
    }

    public Integer getIdPrgSolicitudMotivo() {
        return idPrgSolicitudMotivo;
    }

    public void setIdPrgSolicitudMotivo(Integer idPrgSolicitudMotivo) {
        this.idPrgSolicitudMotivo = idPrgSolicitudMotivo;
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
    public List<PrgSolicitud> getPrgSolicitudList() {
        return prgSolicitudList;
    }

    public void setPrgSolicitudList(List<PrgSolicitud> prgSolicitudList) {
        this.prgSolicitudList = prgSolicitudList;
    }

    public List<PrgSolicitudLicencia> getPrgSolicitudLicenciaList() {
        return prgSolicitudLicenciaList;
    }

    public void setPrgSolicitudLicenciaList(List<PrgSolicitudLicencia> prgSolicitudLicenciaList) {
        this.prgSolicitudLicenciaList = prgSolicitudLicenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgSolicitudMotivo != null ? idPrgSolicitudMotivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgSolicitudMotivo)) {
            return false;
        }
        PrgSolicitudMotivo other = (PrgSolicitudMotivo) object;
        if ((this.idPrgSolicitudMotivo == null && other.idPrgSolicitudMotivo != null) || (this.idPrgSolicitudMotivo != null && !this.idPrgSolicitudMotivo.equals(other.idPrgSolicitudMotivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgSolicitudMotivo[ idPrgSolicitudMotivo=" + idPrgSolicitudMotivo + " ]";
    }

}
