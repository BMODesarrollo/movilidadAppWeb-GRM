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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "param_cargo_cc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParamCargoCc.findAll", query = "SELECT p FROM ParamCargoCc p"),
    @NamedQuery(name = "ParamCargoCc.findByIdParamCargoCc", query = "SELECT p FROM ParamCargoCc p WHERE p.idParamCargoCc = :idParamCargoCc"),
    @NamedQuery(name = "ParamCargoCc.findByCentroCosto", query = "SELECT p FROM ParamCargoCc p WHERE p.centroCosto = :centroCosto"),
    @NamedQuery(name = "ParamCargoCc.findByUsername", query = "SELECT p FROM ParamCargoCc p WHERE p.username = :username"),
    @NamedQuery(name = "ParamCargoCc.findByCreado", query = "SELECT p FROM ParamCargoCc p WHERE p.creado = :creado"),
    @NamedQuery(name = "ParamCargoCc.findByModificado", query = "SELECT p FROM ParamCargoCc p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "ParamCargoCc.findByEstadoReg", query = "SELECT p FROM ParamCargoCc p WHERE p.estadoReg = :estadoReg")})
public class ParamCargoCc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_param_cargo_cc")
    private Integer idParamCargoCc;
    @Size(max = 5)
    @Column(name = "centro_costo")
    private String centroCosto;
    @Lob
    @Size(max = 65535)
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
    @JoinColumn(name = "id_empleado_tipo_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoTipoCargo;

    public ParamCargoCc() {
    }

    public ParamCargoCc(Integer idParamCargoCc) {
        this.idParamCargoCc = idParamCargoCc;
    }

    public Integer getIdParamCargoCc() {
        return idParamCargoCc;
    }

    public void setIdParamCargoCc(Integer idParamCargoCc) {
        this.idParamCargoCc = idParamCargoCc;
    }

    public String getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
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

    public EmpleadoTipoCargo getIdEmpleadoTipoCargo() {
        return idEmpleadoTipoCargo;
    }

    public void setIdEmpleadoTipoCargo(EmpleadoTipoCargo idEmpleadoTipoCargo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParamCargoCc != null ? idParamCargoCc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamCargoCc)) {
            return false;
        }
        ParamCargoCc other = (ParamCargoCc) object;
        if ((this.idParamCargoCc == null && other.idParamCargoCc != null) || (this.idParamCargoCc != null && !this.idParamCargoCc.equals(other.idParamCargoCc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ParamCargoCc[ idParamCargoCc=" + idParamCargoCc + " ]";
    }
    
}
