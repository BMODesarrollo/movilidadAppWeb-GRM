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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "operacion_distancia_max")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OperacionDistanciaMax.findAll", query = "SELECT o FROM OperacionDistanciaMax o")
    , @NamedQuery(name = "OperacionDistanciaMax.findByIdOperacionDistanciaMax", query = "SELECT o FROM OperacionDistanciaMax o WHERE o.idOperacionDistanciaMax = :idOperacionDistanciaMax")
    , @NamedQuery(name = "OperacionDistanciaMax.findByDistancia", query = "SELECT o FROM OperacionDistanciaMax o WHERE o.distancia = :distancia")
    , @NamedQuery(name = "OperacionDistanciaMax.findByUsername", query = "SELECT o FROM OperacionDistanciaMax o WHERE o.username = :username")
    , @NamedQuery(name = "OperacionDistanciaMax.findByCreado", query = "SELECT o FROM OperacionDistanciaMax o WHERE o.creado = :creado")
    , @NamedQuery(name = "OperacionDistanciaMax.findByModificado", query = "SELECT o FROM OperacionDistanciaMax o WHERE o.modificado = :modificado")
    , @NamedQuery(name = "OperacionDistanciaMax.findByEstadoReg", query = "SELECT o FROM OperacionDistanciaMax o WHERE o.estadoReg = :estadoReg")})
public class OperacionDistanciaMax implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_operacion_distancia_max")
    private Integer idOperacionDistanciaMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "distancia")
    private int distancia;
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

    public OperacionDistanciaMax() {
    }

    public OperacionDistanciaMax(Integer idOperacionDistanciaMax) {
        this.idOperacionDistanciaMax = idOperacionDistanciaMax;
    }

    public OperacionDistanciaMax(Integer idOperacionDistanciaMax, int distancia, String username, Date creado, int estadoReg) {
        this.idOperacionDistanciaMax = idOperacionDistanciaMax;
        this.distancia = distancia;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdOperacionDistanciaMax() {
        return idOperacionDistanciaMax;
    }

    public void setIdOperacionDistanciaMax(Integer idOperacionDistanciaMax) {
        this.idOperacionDistanciaMax = idOperacionDistanciaMax;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperacionDistanciaMax != null ? idOperacionDistanciaMax.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OperacionDistanciaMax)) {
            return false;
        }
        OperacionDistanciaMax other = (OperacionDistanciaMax) object;
        if ((this.idOperacionDistanciaMax == null && other.idOperacionDistanciaMax != null) || (this.idOperacionDistanciaMax != null && !this.idOperacionDistanciaMax.equals(other.idOperacionDistanciaMax))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.OperacionDistanciaMax[ idOperacionDistanciaMax=" + idOperacionDistanciaMax + " ]";
    }
    
}
