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
@Table(name = "vehiculo_tipo_carroceria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoTipoCarroceria.findAll", query = "SELECT v FROM VehiculoTipoCarroceria v")
    , @NamedQuery(name = "VehiculoTipoCarroceria.findByIdVehiculoTipoCarroceria", query = "SELECT v FROM VehiculoTipoCarroceria v WHERE v.idVehiculoTipoCarroceria = :idVehiculoTipoCarroceria")
    , @NamedQuery(name = "VehiculoTipoCarroceria.findByNombreTipoCarroceria", query = "SELECT v FROM VehiculoTipoCarroceria v WHERE v.nombreTipoCarroceria = :nombreTipoCarroceria")
    , @NamedQuery(name = "VehiculoTipoCarroceria.findByDescripcionTipoCarroceria", query = "SELECT v FROM VehiculoTipoCarroceria v WHERE v.descripcionTipoCarroceria = :descripcionTipoCarroceria")
    , @NamedQuery(name = "VehiculoTipoCarroceria.findByPathLayout", query = "SELECT v FROM VehiculoTipoCarroceria v WHERE v.pathLayout = :pathLayout")
    , @NamedQuery(name = "VehiculoTipoCarroceria.findByUsername", query = "SELECT v FROM VehiculoTipoCarroceria v WHERE v.username = :username")
    , @NamedQuery(name = "VehiculoTipoCarroceria.findByCreado", query = "SELECT v FROM VehiculoTipoCarroceria v WHERE v.creado = :creado")
    , @NamedQuery(name = "VehiculoTipoCarroceria.findByModificado", query = "SELECT v FROM VehiculoTipoCarroceria v WHERE v.modificado = :modificado")
    , @NamedQuery(name = "VehiculoTipoCarroceria.findByEstadoReg", query = "SELECT v FROM VehiculoTipoCarroceria v WHERE v.estadoReg = :estadoReg")})
public class VehiculoTipoCarroceria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_tipo_carroceria")
    private Integer idVehiculoTipoCarroceria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_tipo_carroceria")
    private String nombreTipoCarroceria;
    @Size(max = 100)
    @Column(name = "descripcion_tipo_carroceria")
    private String descripcionTipoCarroceria;
    @Size(max = 100)
    @Column(name = "path_layout")
    private String pathLayout;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "username")
    private String username;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculoCarroceria", fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculoList;

    public VehiculoTipoCarroceria() {
    }

    public VehiculoTipoCarroceria(Integer idVehiculoTipoCarroceria) {
        this.idVehiculoTipoCarroceria = idVehiculoTipoCarroceria;
    }

    public VehiculoTipoCarroceria(Integer idVehiculoTipoCarroceria, String nombreTipoCarroceria, String username, int estadoReg) {
        this.idVehiculoTipoCarroceria = idVehiculoTipoCarroceria;
        this.nombreTipoCarroceria = nombreTipoCarroceria;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdVehiculoTipoCarroceria() {
        return idVehiculoTipoCarroceria;
    }

    public void setIdVehiculoTipoCarroceria(Integer idVehiculoTipoCarroceria) {
        this.idVehiculoTipoCarroceria = idVehiculoTipoCarroceria;
    }

    public String getNombreTipoCarroceria() {
        return nombreTipoCarroceria;
    }

    public void setNombreTipoCarroceria(String nombreTipoCarroceria) {
        this.nombreTipoCarroceria = nombreTipoCarroceria;
    }

    public String getDescripcionTipoCarroceria() {
        return descripcionTipoCarroceria;
    }

    public void setDescripcionTipoCarroceria(String descripcionTipoCarroceria) {
        this.descripcionTipoCarroceria = descripcionTipoCarroceria;
    }

    public String getPathLayout() {
        return pathLayout;
    }

    public void setPathLayout(String pathLayout) {
        this.pathLayout = pathLayout;
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
        hash += (idVehiculoTipoCarroceria != null ? idVehiculoTipoCarroceria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoTipoCarroceria)) {
            return false;
        }
        VehiculoTipoCarroceria other = (VehiculoTipoCarroceria) object;
        if ((this.idVehiculoTipoCarroceria == null && other.idVehiculoTipoCarroceria != null) || (this.idVehiculoTipoCarroceria != null && !this.idVehiculoTipoCarroceria.equals(other.idVehiculoTipoCarroceria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoTipoCarroceria[ idVehiculoTipoCarroceria=" + idVehiculoTipoCarroceria + " ]";
    }
    
}
