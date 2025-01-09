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
@Table(name = "empleado_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoEstado.findAll", query = "SELECT e FROM EmpleadoEstado e")
    , @NamedQuery(name = "EmpleadoEstado.findByIdEmpleadoEstado", query = "SELECT e FROM EmpleadoEstado e WHERE e.idEmpleadoEstado = :idEmpleadoEstado")
    , @NamedQuery(name = "EmpleadoEstado.findByNombre", query = "SELECT e FROM EmpleadoEstado e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "EmpleadoEstado.findByDescripcion", query = "SELECT e FROM EmpleadoEstado e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "EmpleadoEstado.findByAlertar", query = "SELECT e FROM EmpleadoEstado e WHERE e.alertar = :alertar")
    , @NamedQuery(name = "EmpleadoEstado.findByUsername", query = "SELECT e FROM EmpleadoEstado e WHERE e.username = :username")
    , @NamedQuery(name = "EmpleadoEstado.findByCreado", query = "SELECT e FROM EmpleadoEstado e WHERE e.creado = :creado")
    , @NamedQuery(name = "EmpleadoEstado.findByModificado", query = "SELECT e FROM EmpleadoEstado e WHERE e.modificado = :modificado")
    , @NamedQuery(name = "EmpleadoEstado.findByEstadoReg", query = "SELECT e FROM EmpleadoEstado e WHERE e.estadoReg = :estadoReg")})
public class EmpleadoEstado implements Serializable {
    @OneToMany(mappedBy = "idEmpleadoEstado")
    private List<PrgOperadorInactivo> prgOperadorInactivoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado_estado")
    private Integer idEmpleadoEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "alertar")
    private int alertar;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoEstado", fetch = FetchType.LAZY)
    private List<Empleado> empleadoList;

    public EmpleadoEstado() {
    }

    public EmpleadoEstado(Integer idEmpleadoEstado) {
        this.idEmpleadoEstado = idEmpleadoEstado;
    }

    public EmpleadoEstado(Integer idEmpleadoEstado, String nombre, String descripcion, int alertar, String username, Date creado, int estadoReg) {
        this.idEmpleadoEstado = idEmpleadoEstado;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.alertar = alertar;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdEmpleadoEstado() {
        return idEmpleadoEstado;
    }

    public void setIdEmpleadoEstado(Integer idEmpleadoEstado) {
        this.idEmpleadoEstado = idEmpleadoEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAlertar() {
        return alertar;
    }

    public void setAlertar(int alertar) {
        this.alertar = alertar;
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
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleadoEstado != null ? idEmpleadoEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoEstado)) {
            return false;
        }
        EmpleadoEstado other = (EmpleadoEstado) object;
        if ((this.idEmpleadoEstado == null && other.idEmpleadoEstado != null) || (this.idEmpleadoEstado != null && !this.idEmpleadoEstado.equals(other.idEmpleadoEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.EmpleadoEstado[ idEmpleadoEstado=" + idEmpleadoEstado + " ]";
    }

    @XmlTransient
    public List<PrgOperadorInactivo> getPrgOperadorInactivoList() {
        return prgOperadorInactivoList;
    }

    public void setPrgOperadorInactivoList(List<PrgOperadorInactivo> prgOperadorInactivoList) {
        this.prgOperadorInactivoList = prgOperadorInactivoList;
    }
    
}
