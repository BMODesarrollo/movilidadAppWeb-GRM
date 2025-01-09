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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cesar
 */
@Entity
@Table(name = "config_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigEmpresa.findAll", query = "SELECT c FROM ConfigEmpresa c")
    , @NamedQuery(name = "ConfigEmpresa.findByIdConfigEmpresa", query = "SELECT c FROM ConfigEmpresa c WHERE c.idConfigEmpresa = :idConfigEmpresa")
    , @NamedQuery(name = "ConfigEmpresa.findByLlave", query = "SELECT c FROM ConfigEmpresa c WHERE c.llave = :llave")
    , @NamedQuery(name = "ConfigEmpresa.findByValor", query = "SELECT c FROM ConfigEmpresa c WHERE c.valor = :valor")
    , @NamedQuery(name = "ConfigEmpresa.findByUsername", query = "SELECT c FROM ConfigEmpresa c WHERE c.username = :username")
    , @NamedQuery(name = "ConfigEmpresa.findByCreado", query = "SELECT c FROM ConfigEmpresa c WHERE c.creado = :creado")
    , @NamedQuery(name = "ConfigEmpresa.findByModificado", query = "SELECT c FROM ConfigEmpresa c WHERE c.modificado = :modificado")
    , @NamedQuery(name = "ConfigEmpresa.findByEstadoReg", query = "SELECT c FROM ConfigEmpresa c WHERE c.estadoReg = :estadoReg")})
public class ConfigEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_config_empresa")
    private Integer idConfigEmpresa;
    @Size(max = 45)
    @Column(name = "llave")
    private String llave;
    @Size(max = 255)
    @Column(name = "valor")
    private String valor;
    @Size(max = 255)
    @Column(name = "observacion")
    private String observacion;
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

    public ConfigEmpresa() {
    }

    public ConfigEmpresa(Integer idConfigEmpresa) {
        this.idConfigEmpresa = idConfigEmpresa;
    }

    public Integer getIdConfigEmpresa() {
        return idConfigEmpresa;
    }

    public void setIdConfigEmpresa(Integer idConfigEmpresa) {
        this.idConfigEmpresa = idConfigEmpresa;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfigEmpresa != null ? idConfigEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigEmpresa)) {
            return false;
        }
        ConfigEmpresa other = (ConfigEmpresa) object;
        if ((this.idConfigEmpresa == null && other.idConfigEmpresa != null) || (this.idConfigEmpresa != null && !this.idConfigEmpresa.equals(other.idConfigEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ConfigEmpresa{" + "idConfigEmpresa=" + idConfigEmpresa + ", llave=" + llave + ", valor=" + valor + ", username=" + username + ", creado=" + creado + ", modificado=" + modificado + ", estadoReg=" + estadoReg + '}';
    }

}
