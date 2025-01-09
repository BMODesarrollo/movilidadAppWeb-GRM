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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "empleado_municipio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoMunicipio.findAll", query = "SELECT e FROM EmpleadoMunicipio e")
    , @NamedQuery(name = "EmpleadoMunicipio.findByIdEmpleadoCiudad", query = "SELECT e FROM EmpleadoMunicipio e WHERE e.idEmpleadoCiudad = :idEmpleadoCiudad")
    , @NamedQuery(name = "EmpleadoMunicipio.findByNombre", query = "SELECT e FROM EmpleadoMunicipio e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "EmpleadoMunicipio.findByUsername", query = "SELECT e FROM EmpleadoMunicipio e WHERE e.username = :username")
    , @NamedQuery(name = "EmpleadoMunicipio.findByCreado", query = "SELECT e FROM EmpleadoMunicipio e WHERE e.creado = :creado")
    , @NamedQuery(name = "EmpleadoMunicipio.findByModificado", query = "SELECT e FROM EmpleadoMunicipio e WHERE e.modificado = :modificado")
    , @NamedQuery(name = "EmpleadoMunicipio.findByEstadoReg", query = "SELECT e FROM EmpleadoMunicipio e WHERE e.estadoReg = :estadoReg")})
public class EmpleadoMunicipio implements Serializable {

 
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado_ciudad")
    private Integer idEmpleadoCiudad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleadoMunicipio", fetch = FetchType.LAZY)
    private List<Empleado> empleadoList;
    @JoinColumn(name = "id_empleado_departamento", referencedColumnName = "id_empleado_departamento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoDepartamento idEmpleadoDepartamento;

    public EmpleadoMunicipio() {
    }

    public EmpleadoMunicipio(Integer idEmpleadoCiudad) {
        this.idEmpleadoCiudad = idEmpleadoCiudad;
    }

    public EmpleadoMunicipio(Integer idEmpleadoCiudad, String nombre, String username, Date creado, int estadoReg) {
        this.idEmpleadoCiudad = idEmpleadoCiudad;
        this.nombre = nombre;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdEmpleadoCiudad() {
        return idEmpleadoCiudad;
    }

    public void setIdEmpleadoCiudad(Integer idEmpleadoCiudad) {
        this.idEmpleadoCiudad = idEmpleadoCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public EmpleadoDepartamento getIdEmpleadoDepartamento() {
        return idEmpleadoDepartamento;
    }

    public void setIdEmpleadoDepartamento(EmpleadoDepartamento idEmpleadoDepartamento) {
        this.idEmpleadoDepartamento = idEmpleadoDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleadoCiudad != null ? idEmpleadoCiudad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoMunicipio)) {
            return false;
        }
        EmpleadoMunicipio other = (EmpleadoMunicipio) object;
        if ((this.idEmpleadoCiudad == null && other.idEmpleadoCiudad != null) || (this.idEmpleadoCiudad != null && !this.idEmpleadoCiudad.equals(other.idEmpleadoCiudad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.EmpleadoMunicipio[ idEmpleadoCiudad=" + idEmpleadoCiudad + " ]";
    }
   
}
