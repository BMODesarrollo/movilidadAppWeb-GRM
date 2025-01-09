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
@Table(name = "generica_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaToken.findAll", query = "SELECT g FROM GenericaToken g")
    , @NamedQuery(name = "GenericaToken.findByIdGenericaToken", query = "SELECT g FROM GenericaToken g WHERE g.idGenericaToken = :idGenericaToken")
    , @NamedQuery(name = "GenericaToken.findByTipo", query = "SELECT g FROM GenericaToken g WHERE g.tipo = :tipo")
    , @NamedQuery(name = "GenericaToken.findBySolicitado", query = "SELECT g FROM GenericaToken g WHERE g.solicitado = :solicitado")
    , @NamedQuery(name = "GenericaToken.findByToken", query = "SELECT g FROM GenericaToken g WHERE g.token = :token")
    , @NamedQuery(name = "GenericaToken.findByUsado", query = "SELECT g FROM GenericaToken g WHERE g.usado = :usado")
    , @NamedQuery(name = "GenericaToken.findByActivo", query = "SELECT g FROM GenericaToken g WHERE g.activo = :activo")
    , @NamedQuery(name = "GenericaToken.findByModificado", query = "SELECT g FROM GenericaToken g WHERE g.modificado = :modificado")
    , @NamedQuery(name = "GenericaToken.findByEstadoReg", query = "SELECT g FROM GenericaToken g WHERE g.estadoReg = :estadoReg")})
public class GenericaToken implements Serializable {
    @OneToMany(mappedBy = "idGenericaToken", fetch = FetchType.LAZY)
    private List<GenericaSolicitud> genericaSolicitudList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_token")
    private Integer idGenericaToken;
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
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGenericaToken", fetch = FetchType.LAZY)
    private List<GenericaSolicitudLicencia> genericaSolicitudLicenciaList;

    public GenericaToken() {
    }

    public GenericaToken(Integer idGenericaToken) {
        this.idGenericaToken = idGenericaToken;
    }

    public Integer getIdGenericaToken() {
        return idGenericaToken;
    }

    public void setIdGenericaToken(Integer idGenericaToken) {
        this.idGenericaToken = idGenericaToken;
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

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @XmlTransient
    public List<GenericaSolicitudLicencia> getGenericaSolicitudLicenciaList() {
        return genericaSolicitudLicenciaList;
    }

    public void setGenericaSolicitudLicenciaList(List<GenericaSolicitudLicencia> genericaSolicitudLicenciaList) {
        this.genericaSolicitudLicenciaList = genericaSolicitudLicenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaToken != null ? idGenericaToken.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaToken)) {
            return false;
        }
        GenericaToken other = (GenericaToken) object;
        if ((this.idGenericaToken == null && other.idGenericaToken != null) || (this.idGenericaToken != null && !this.idGenericaToken.equals(other.idGenericaToken))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaToken[ idGenericaToken=" + idGenericaToken + " ]";
    }

    @XmlTransient
    public List<GenericaSolicitud> getGenericaSolicitudList() {
        return genericaSolicitudList;
    }

    public void setGenericaSolicitudList(List<GenericaSolicitud> genericaSolicitudList) {
        this.genericaSolicitudList = genericaSolicitudList;
    }
    

}
