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
@Table(name = "param_feriado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParamFeriado.findAll", query = "SELECT p FROM ParamFeriado p")
    , @NamedQuery(name = "ParamFeriado.findByIdParamFeriado", query = "SELECT p FROM ParamFeriado p WHERE p.idParamFeriado = :idParamFeriado")
    , @NamedQuery(name = "ParamFeriado.findByNombre", query = "SELECT p FROM ParamFeriado p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "ParamFeriado.findByFecha", query = "SELECT p FROM ParamFeriado p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "ParamFeriado.findByUsername", query = "SELECT p FROM ParamFeriado p WHERE p.username = :username")
    , @NamedQuery(name = "ParamFeriado.findByCreado", query = "SELECT p FROM ParamFeriado p WHERE p.creado = :creado")
    , @NamedQuery(name = "ParamFeriado.findByModificado", query = "SELECT p FROM ParamFeriado p WHERE p.modificado = :modificado")
    , @NamedQuery(name = "ParamFeriado.findByEstadoReg", query = "SELECT p FROM ParamFeriado p WHERE p.estadoReg = :estadoReg")})
public class ParamFeriado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_param_feriado")
    private Integer idParamFeriado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
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

    public ParamFeriado() {
    }

    public ParamFeriado(Integer idParamFeriado) {
        this.idParamFeriado = idParamFeriado;
    }

    public ParamFeriado(Integer idParamFeriado, String nombre, Date fecha, String username, Date creado, int estadoReg) {
        this.idParamFeriado = idParamFeriado;
        this.nombre = nombre;
        this.fecha = fecha;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdParamFeriado() {
        return idParamFeriado;
    }

    public void setIdParamFeriado(Integer idParamFeriado) {
        this.idParamFeriado = idParamFeriado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        hash += (idParamFeriado != null ? idParamFeriado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamFeriado)) {
            return false;
        }
        ParamFeriado other = (ParamFeriado) object;
        if ((this.idParamFeriado == null && other.idParamFeriado != null) || (this.idParamFeriado != null && !this.idParamFeriado.equals(other.idParamFeriado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ParamFeriado[ idParamFeriado=" + idParamFeriado + " ]";
    }
    
}
