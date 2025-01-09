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
@Table(name = "vehiculo_propietarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoPropietarios.findAll", query = "SELECT v FROM VehiculoPropietarios v")
    , @NamedQuery(name = "VehiculoPropietarios.findByIdVehiculoPropietario", query = "SELECT v FROM VehiculoPropietarios v WHERE v.idVehiculoPropietario = :idVehiculoPropietario")
    , @NamedQuery(name = "VehiculoPropietarios.findByIdentificacion", query = "SELECT v FROM VehiculoPropietarios v WHERE v.identificacion = :identificacion")
    , @NamedQuery(name = "VehiculoPropietarios.findByNombres", query = "SELECT v FROM VehiculoPropietarios v WHERE v.nombres = :nombres")
    , @NamedQuery(name = "VehiculoPropietarios.findByDireccion", query = "SELECT v FROM VehiculoPropietarios v WHERE v.direccion = :direccion")
    , @NamedQuery(name = "VehiculoPropietarios.findByTelefono", query = "SELECT v FROM VehiculoPropietarios v WHERE v.telefono = :telefono")
    , @NamedQuery(name = "VehiculoPropietarios.findByUsername", query = "SELECT v FROM VehiculoPropietarios v WHERE v.username = :username")
    , @NamedQuery(name = "VehiculoPropietarios.findByCreado", query = "SELECT v FROM VehiculoPropietarios v WHERE v.creado = :creado")
    , @NamedQuery(name = "VehiculoPropietarios.findByModificado", query = "SELECT v FROM VehiculoPropietarios v WHERE v.modificado = :modificado")
    , @NamedQuery(name = "VehiculoPropietarios.findByEstadoReg", query = "SELECT v FROM VehiculoPropietarios v WHERE v.estadoReg = :estadoReg")})
public class VehiculoPropietarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_propietario")
    private Integer idVehiculoPropietario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "identificacion")
    private String identificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 50)
    @Column(name = "telefono")
    private String telefono;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculoPropietario", fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculoList;

    public VehiculoPropietarios() {
    }

    public VehiculoPropietarios(Integer idVehiculoPropietario) {
        this.idVehiculoPropietario = idVehiculoPropietario;
    }

    public VehiculoPropietarios(Integer idVehiculoPropietario, String identificacion, String nombres, String username, Date creado, int estadoReg) {
        this.idVehiculoPropietario = idVehiculoPropietario;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdVehiculoPropietario() {
        return idVehiculoPropietario;
    }

    public void setIdVehiculoPropietario(Integer idVehiculoPropietario) {
        this.idVehiculoPropietario = idVehiculoPropietario;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
    public List<Vehiculo> getVehiculoList() {
        return vehiculoList;
    }

    public void setVehiculoList(List<Vehiculo> vehiculoList) {
        this.vehiculoList = vehiculoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVehiculoPropietario != null ? idVehiculoPropietario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoPropietarios)) {
            return false;
        }
        VehiculoPropietarios other = (VehiculoPropietarios) object;
        if ((this.idVehiculoPropietario == null && other.idVehiculoPropietario != null) || (this.idVehiculoPropietario != null && !this.idVehiculoPropietario.equals(other.idVehiculoPropietario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoPropietarios[ idVehiculoPropietario=" + idVehiculoPropietario + " ]";
    }
    
}
