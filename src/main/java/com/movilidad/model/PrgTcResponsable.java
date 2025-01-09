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
@Table(name = "prg_tc_responsable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgTcResponsable.findAll", query = "SELECT p FROM PrgTcResponsable p"),
    @NamedQuery(name = "PrgTcResponsable.findByIdPrgTcResponsable", query = "SELECT p FROM PrgTcResponsable p WHERE p.idPrgTcResponsable = :idPrgTcResponsable"),
    @NamedQuery(name = "PrgTcResponsable.findByResponsable", query = "SELECT p FROM PrgTcResponsable p WHERE p.responsable = :responsable"),
    @NamedQuery(name = "PrgTcResponsable.findByUsername", query = "SELECT p FROM PrgTcResponsable p WHERE p.username = :username"),
    @NamedQuery(name = "PrgTcResponsable.findByCreado", query = "SELECT p FROM PrgTcResponsable p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgTcResponsable.findByModificado", query = "SELECT p FROM PrgTcResponsable p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgTcResponsable.findByEstadoReg", query = "SELECT p FROM PrgTcResponsable p WHERE p.estadoReg = :estadoReg")})
public class PrgTcResponsable implements Serializable {

    @OneToMany(mappedBy = "idPrgTcResponsable", fetch = FetchType.LAZY)
    private List<PrgClasificacionMotivo> prgClasificacionMotivoList;
    @OneToMany(mappedBy = "idPrgTcResponsable", fetch = FetchType.LAZY)
    private List<NovedadPrgTc> novedadPrgTcList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_tc_responsable")
    private Integer idPrgTcResponsable;
    @Size(max = 45)
    @Column(name = "responsable")
    private String responsable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
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
    @OneToMany(mappedBy = "idPrgTcResponsable", fetch = FetchType.LAZY)
    private List<PrgTc> prgTcList;
    @OneToMany(mappedBy = "idPrgTcResponsable", fetch = FetchType.LAZY)
    private List<NovedadTipoDetalles> novedadTipoDetallesList;

    public PrgTcResponsable() {
    }

    public PrgTcResponsable(Integer idPrgTcResponsable) {
        this.idPrgTcResponsable = idPrgTcResponsable;
    }

    public PrgTcResponsable(Integer idPrgTcResponsable, String username, Date creado, int estadoReg) {
        this.idPrgTcResponsable = idPrgTcResponsable;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgTcResponsable() {
        return idPrgTcResponsable;
    }

    public void setIdPrgTcResponsable(Integer idPrgTcResponsable) {
        this.idPrgTcResponsable = idPrgTcResponsable;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
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
    public List<PrgTc> getPrgTcList() {
        return prgTcList;
    }

    public void setPrgTcList(List<PrgTc> prgTcList) {
        this.prgTcList = prgTcList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgTcResponsable != null ? idPrgTcResponsable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgTcResponsable)) {
            return false;
        }
        PrgTcResponsable other = (PrgTcResponsable) object;
        if ((this.idPrgTcResponsable == null && other.idPrgTcResponsable != null) || (this.idPrgTcResponsable != null && !this.idPrgTcResponsable.equals(other.idPrgTcResponsable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgTcResponsable[ idPrgTcResponsable=" + idPrgTcResponsable + " ]";
    }

    @XmlTransient
    public List<NovedadPrgTc> getNovedadPrgTcList() {
        return novedadPrgTcList;
    }

    public void setNovedadPrgTcList(List<NovedadPrgTc> novedadPrgTcList) {
        this.novedadPrgTcList = novedadPrgTcList;
    }

    @XmlTransient
    public List<PrgClasificacionMotivo> getPrgClasificacionMotivoList() {
        return prgClasificacionMotivoList;
    }

    public void setPrgClasificacionMotivoList(List<PrgClasificacionMotivo> prgClasificacionMotivoList) {
        this.prgClasificacionMotivoList = prgClasificacionMotivoList;
    }

    @XmlTransient
    public List<NovedadTipoDetalles> getNovedadTipoDetallesList() {
        return novedadTipoDetallesList;
    }

    public void setNovedadTipoDetallesList(List<NovedadTipoDetalles> novedadTipoDetallesList) {
        this.novedadTipoDetallesList = novedadTipoDetallesList;
    }

}
