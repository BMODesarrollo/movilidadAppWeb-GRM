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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "prg_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgToken.findAll", query = "SELECT p FROM PrgToken p")
    , @NamedQuery(name = "PrgToken.findByIdPrgToken", query = "SELECT p FROM PrgToken p WHERE p.idPrgToken = :idPrgToken")
    , @NamedQuery(name = "PrgToken.findByTipo", query = "SELECT p FROM PrgToken p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "PrgToken.findBySolicitado", query = "SELECT p FROM PrgToken p WHERE p.solicitado = :solicitado")
    , @NamedQuery(name = "PrgToken.findByToken", query = "SELECT p FROM PrgToken p WHERE p.token = :token")
    , @NamedQuery(name = "PrgToken.findByUsado", query = "SELECT p FROM PrgToken p WHERE p.usado = :usado")
    , @NamedQuery(name = "PrgToken.findByActivo", query = "SELECT p FROM PrgToken p WHERE p.activo = :activo")
    , @NamedQuery(name = "PrgToken.findByModificado", query = "SELECT p FROM PrgToken p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "PrgToken.findByEstadoReg", query = "SELECT p FROM PrgToken p WHERE p.estadoReg = :estadoReg")})
public class PrgToken implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrgToken", fetch = FetchType.LAZY)
    private List<PrgSolicitudLicencia> prgSolicitudLicenciaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_token")
    private Integer idPrgToken;
    @Column(name = "tipo")
    private Integer tipo;
    @Column(name = "solicitado")
    @Temporal(TemporalType.DATE)
    private Date solicitado;
    @Size(max = 6)
    @Column(name = "token")
    private String token;
    @Column(name = "usado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usado;
    @Column(name = "activo")
    private Integer activo;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @OneToMany(mappedBy = "idPrgToken")
    private List<PrgSolicitud> prgSolicitudList;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;

    public PrgToken() {
    }

    public PrgToken(Integer idPrgToken) {
        this.idPrgToken = idPrgToken;
    }

    public Integer getIdPrgToken() {
        return idPrgToken;
    }

    public void setIdPrgToken(Integer idPrgToken) {
        this.idPrgToken = idPrgToken;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Date getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(Date solicitado) {
        this.solicitado = solicitado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUsado() {
        return usado;
    }

    public void setUsado(Date usado) {
        this.usado = usado;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
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

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgToken != null ? idPrgToken.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgToken)) {
            return false;
        }
        PrgToken other = (PrgToken) object;
        if ((this.idPrgToken == null && other.idPrgToken != null) || (this.idPrgToken != null && !this.idPrgToken.equals(other.idPrgToken))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgToken[ idPrgToken=" + idPrgToken + " ]";
    }

    @XmlTransient
    public List<PrgSolicitudLicencia> getPrgSolicitudLicenciaList() {
        return prgSolicitudLicenciaList;
    }

    public void setPrgSolicitudLicenciaList(List<PrgSolicitudLicencia> prgSolicitudLicenciaList) {
        this.prgSolicitudLicenciaList = prgSolicitudLicenciaList;
    }

    }
