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
 * @author HP
 */
@Entity
@Table(name = "empleado_contrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoContrato.findAll", query = "SELECT e FROM EmpleadoContrato e")
    , @NamedQuery(name = "EmpleadoContrato.findByIdEmpleadoContrato", query = "SELECT e FROM EmpleadoContrato e WHERE e.idEmpleadoContrato = :idEmpleadoContrato")
    , @NamedQuery(name = "EmpleadoContrato.findByNroContrato", query = "SELECT e FROM EmpleadoContrato e WHERE e.nroContrato = :nroContrato")
    , @NamedQuery(name = "EmpleadoContrato.findByIdEmpleadoTipoCargo", query = "SELECT e FROM EmpleadoContrato e WHERE e.idEmpleadoTipoCargo = :idEmpleadoTipoCargo")
    , @NamedQuery(name = "EmpleadoContrato.findByFechaIngreso", query = "SELECT e FROM EmpleadoContrato e WHERE e.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "EmpleadoContrato.findByFechaRetiro", query = "SELECT e FROM EmpleadoContrato e WHERE e.fechaRetiro = :fechaRetiro")
    , @NamedQuery(name = "EmpleadoContrato.findByActivo", query = "SELECT e FROM EmpleadoContrato e WHERE e.activo = :activo")
    , @NamedQuery(name = "EmpleadoContrato.findByUsername", query = "SELECT e FROM EmpleadoContrato e WHERE e.username = :username")
    , @NamedQuery(name = "EmpleadoContrato.findByCreado", query = "SELECT e FROM EmpleadoContrato e WHERE e.creado = :creado")
    , @NamedQuery(name = "EmpleadoContrato.findByModificado", query = "SELECT e FROM EmpleadoContrato e WHERE e.modificado = :modificado")
    , @NamedQuery(name = "EmpleadoContrato.findByEstadoReg", query = "SELECT e FROM EmpleadoContrato e WHERE e.estadoReg = :estadoReg")})
public class EmpleadoContrato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado_contrato")
    private Integer idEmpleadoContrato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "nro_contrato")
    private String nroContrato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empleado_tipo_cargo")
    private int idEmpleadoTipoCargo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "fecha_retiro")
    @Temporal(TemporalType.DATE)
    private Date fechaRetiro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private int activo;
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
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado idEmpleado;

    public EmpleadoContrato() {
    }

    public EmpleadoContrato(Integer idEmpleadoContrato) {
        this.idEmpleadoContrato = idEmpleadoContrato;
    }

    public EmpleadoContrato(Integer idEmpleadoContrato, String nroContrato, int idEmpleadoTipoCargo, Date fechaIngreso, int activo, String username, Date creado, int estadoReg) {
        this.idEmpleadoContrato = idEmpleadoContrato;
        this.nroContrato = nroContrato;
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
        this.fechaIngreso = fechaIngreso;
        this.activo = activo;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdEmpleadoContrato() {
        return idEmpleadoContrato;
    }

    public void setIdEmpleadoContrato(Integer idEmpleadoContrato) {
        this.idEmpleadoContrato = idEmpleadoContrato;
    }

    public String getNroContrato() {
        return nroContrato;
    }

    public void setNroContrato(String nroContrato) {
        this.nroContrato = nroContrato;
    }

    public int getIdEmpleadoTipoCargo() {
        return idEmpleadoTipoCargo;
    }

    public void setIdEmpleadoTipoCargo(int idEmpleadoTipoCargo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
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

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleadoContrato != null ? idEmpleadoContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoContrato)) {
            return false;
        }
        EmpleadoContrato other = (EmpleadoContrato) object;
        if ((this.idEmpleadoContrato == null && other.idEmpleadoContrato != null) || (this.idEmpleadoContrato != null && !this.idEmpleadoContrato.equals(other.idEmpleadoContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.EmpleadoContrato[ idEmpleadoContrato=" + idEmpleadoContrato + " ]";
    }
    
}
