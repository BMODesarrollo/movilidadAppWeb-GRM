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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "param_area_cargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParamAreaCargo.findAll", query = "SELECT p FROM ParamAreaCargo p"),
    @NamedQuery(name = "ParamAreaCargo.findByIdParamAreaCargo", query = "SELECT p FROM ParamAreaCargo p WHERE p.idParamAreaCargo = :idParamAreaCargo"),
    @NamedQuery(name = "ParamAreaCargo.findByUsername", query = "SELECT p FROM ParamAreaCargo p WHERE p.username = :username"),
    @NamedQuery(name = "ParamAreaCargo.findByCreado", query = "SELECT p FROM ParamAreaCargo p WHERE p.creado = :creado"),
    @NamedQuery(name = "ParamAreaCargo.findByModificado", query = "SELECT p FROM ParamAreaCargo p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "ParamAreaCargo.findByEstadoReg", query = "SELECT p FROM ParamAreaCargo p WHERE p.estadoReg = :estadoReg")})
public class ParamAreaCargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_param_area_cargo")
    private Integer idParamAreaCargo;
    @Size(max = 26)
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
    @JoinColumn(name = "id_param_area", referencedColumnName = "id_param_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParamArea idParamArea;
    @JoinColumn(name = "id_empleado_tipo_cargo", referencedColumnName = "id_empleado_tipo_cargo")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpleadoTipoCargo idEmpleadoTipoCargo;

    public ParamAreaCargo() {
    }

    public ParamAreaCargo(Integer idParamAreaCargo) {
        this.idParamAreaCargo = idParamAreaCargo;
    }

    public Integer getIdParamAreaCargo() {
        return idParamAreaCargo;
    }

    public void setIdParamAreaCargo(Integer idParamAreaCargo) {
        this.idParamAreaCargo = idParamAreaCargo;
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

    public ParamArea getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(ParamArea idParamArea) {
        this.idParamArea = idParamArea;
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
        hash += (idParamAreaCargo != null ? idParamAreaCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamAreaCargo)) {
            return false;
        }
        ParamAreaCargo other = (ParamAreaCargo) object;
        if ((this.idParamAreaCargo == null && other.idParamAreaCargo != null) || (this.idParamAreaCargo != null && !this.idParamAreaCargo.equals(other.idParamAreaCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ParamAreaCargo[ idParamAreaCargo=" + idParamAreaCargo + " ]";
    }
    
}
