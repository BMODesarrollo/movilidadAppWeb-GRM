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
 * @author solucionesit
 */
@Entity
@Table(name = "config_freeway")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigFreeway.findAll", query = "SELECT c FROM ConfigFreeway c")
    , @NamedQuery(name = "ConfigFreeway.findByIdConfigFreeway", query = "SELECT c FROM ConfigFreeway c WHERE c.idConfigFreeway = :idConfigFreeway")
    , @NamedQuery(name = "ConfigFreeway.findByCodigoSolucion", query = "SELECT c FROM ConfigFreeway c WHERE c.codigoSolucion = :codigoSolucion")
    , @NamedQuery(name = "ConfigFreeway.findByUrlServicio", query = "SELECT c FROM ConfigFreeway c WHERE c.urlServicio = :urlServicio")
    , @NamedQuery(name = "ConfigFreeway.findByUserFreeway", query = "SELECT c FROM ConfigFreeway c WHERE c.userFreeway = :userFreeway")
    , @NamedQuery(name = "ConfigFreeway.findByPasswordFreeway", query = "SELECT c FROM ConfigFreeway c WHERE c.passwordFreeway = :passwordFreeway")
    , @NamedQuery(name = "ConfigFreeway.findByUsername", query = "SELECT c FROM ConfigFreeway c WHERE c.username = :username")
    , @NamedQuery(name = "ConfigFreeway.findByCreado", query = "SELECT c FROM ConfigFreeway c WHERE c.creado = :creado")
    , @NamedQuery(name = "ConfigFreeway.findByModificado", query = "SELECT c FROM ConfigFreeway c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "ConfigFreeway.findByEstadoReg", query = "SELECT c FROM ConfigFreeway c WHERE c.estadoReg = :estadoReg")})
public class ConfigFreeway implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_config_freeway")
    private Integer idConfigFreeway;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "codigo_solucion")
    private String codigoSolucion;
    @Size(max = 150)
    @Column(name = "url_servicio")
    private String urlServicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "user_freeway")
    private String userFreeway;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password_freeway")
    private String passwordFreeway;
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
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;

    public ConfigFreeway() {
    }

    public ConfigFreeway(Integer idConfigFreeway) {
        this.idConfigFreeway = idConfigFreeway;
    }

    public ConfigFreeway(Integer idConfigFreeway, String codigoSolucion, String userFreeway, String passwordFreeway, String username, Date creado, int estadoReg) {
        this.idConfigFreeway = idConfigFreeway;
        this.codigoSolucion = codigoSolucion;
        this.userFreeway = userFreeway;
        this.passwordFreeway = passwordFreeway;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdConfigFreeway() {
        return idConfigFreeway;
    }

    public void setIdConfigFreeway(Integer idConfigFreeway) {
        this.idConfigFreeway = idConfigFreeway;
    }

    public String getCodigoSolucion() {
        return codigoSolucion;
    }

    public void setCodigoSolucion(String codigoSolucion) {
        this.codigoSolucion = codigoSolucion;
    }

    public String getUrlServicio() {
        return urlServicio;
    }

    public void setUrlServicio(String urlServicio) {
        this.urlServicio = urlServicio;
    }

    public String getUserFreeway() {
        return userFreeway;
    }

    public void setUserFreeway(String userFreeway) {
        this.userFreeway = userFreeway;
    }

    public String getPasswordFreeway() {
        return passwordFreeway;
    }

    public void setPasswordFreeway(String passwordFreeway) {
        this.passwordFreeway = passwordFreeway;
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

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfigFreeway != null ? idConfigFreeway.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigFreeway)) {
            return false;
        }
        ConfigFreeway other = (ConfigFreeway) object;
        if ((this.idConfigFreeway == null && other.idConfigFreeway != null) || (this.idConfigFreeway != null && !this.idConfigFreeway.equals(other.idConfigFreeway))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ConfigFreeway[ idConfigFreeway=" + idConfigFreeway + " ]";
    }
    
}
