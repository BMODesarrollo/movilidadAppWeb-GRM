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
@Table(name = "vehiculo_dano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoDano.findAll", query = "SELECT v FROM VehiculoDano v"),
    @NamedQuery(name = "VehiculoDano.findByIdVehiculoDano", query = "SELECT v FROM VehiculoDano v WHERE v.idVehiculoDano = :idVehiculoDano"),
    @NamedQuery(name = "VehiculoDano.findByNombre", query = "SELECT v FROM VehiculoDano v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VehiculoDano.findByDescripcion", query = "SELECT v FROM VehiculoDano v WHERE v.descripcion = :descripcion"),
    @NamedQuery(name = "VehiculoDano.findByTaxonomiaMxm", query = "SELECT v FROM VehiculoDano v WHERE v.taxonomiaMxm = :taxonomiaMxm"),
    @NamedQuery(name = "VehiculoDano.findByUsername", query = "SELECT v FROM VehiculoDano v WHERE v.username = :username"),
    @NamedQuery(name = "VehiculoDano.findByCreado", query = "SELECT v FROM VehiculoDano v WHERE v.creado = :creado"),
    @NamedQuery(name = "VehiculoDano.findByModificado", query = "SELECT v FROM VehiculoDano v WHERE v.modificado = :modificado"),
    @NamedQuery(name = "VehiculoDano.findByEstadoReg", query = "SELECT v FROM VehiculoDano v WHERE v.estadoReg = :estadoReg")})
public class VehiculoDano implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_dano")
    private Integer idVehiculoDano;
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
    @Size(max = 10)
    @Column(name = "taxonomia_mxm")
    private String taxonomiaMxm;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehiculoDano", fetch = FetchType.LAZY)
    private List<VehiculoComponenteDano> vehiculoComponenteDanoList;

    public VehiculoDano() {
    }

    public VehiculoDano(Integer idVehiculoDano) {
        this.idVehiculoDano = idVehiculoDano;
    }

    public VehiculoDano(Integer idVehiculoDano, String nombre, String descripcion, String username, Date creado, int estadoReg) {
        this.idVehiculoDano = idVehiculoDano;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdVehiculoDano() {
        return idVehiculoDano;
    }

    public void setIdVehiculoDano(Integer idVehiculoDano) {
        this.idVehiculoDano = idVehiculoDano;
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

    public String getTaxonomiaMxm() {
        return taxonomiaMxm;
    }

    public void setTaxonomiaMxm(String taxonomiaMxm) {
        this.taxonomiaMxm = taxonomiaMxm;
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
    public List<VehiculoComponenteDano> getVehiculoComponenteDanoList() {
        return vehiculoComponenteDanoList;
    }

    public void setVehiculoComponenteDanoList(List<VehiculoComponenteDano> vehiculoComponenteDanoList) {
        this.vehiculoComponenteDanoList = vehiculoComponenteDanoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVehiculoDano != null ? idVehiculoDano.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoDano)) {
            return false;
        }
        VehiculoDano other = (VehiculoDano) object;
        if ((this.idVehiculoDano == null && other.idVehiculoDano != null) || (this.idVehiculoDano != null && !this.idVehiculoDano.equals(other.idVehiculoDano))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoDano[ idVehiculoDano=" + idVehiculoDano + " ]";
    }

}
