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
@Table(name = "vehiculo_dano_severidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehiculoDanoSeveridad.findAll", query = "SELECT v FROM VehiculoDanoSeveridad v")
    , @NamedQuery(name = "VehiculoDanoSeveridad.findByIdVehiculoDanoSeveridad", query = "SELECT v FROM VehiculoDanoSeveridad v WHERE v.idVehiculoDanoSeveridad = :idVehiculoDanoSeveridad")
    , @NamedQuery(name = "VehiculoDanoSeveridad.findByNombre", query = "SELECT v FROM VehiculoDanoSeveridad v WHERE v.nombre = :nombre")
    , @NamedQuery(name = "VehiculoDanoSeveridad.findByDescripcion", query = "SELECT v FROM VehiculoDanoSeveridad v WHERE v.descripcion = :descripcion")
    , @NamedQuery(name = "VehiculoDanoSeveridad.findByPuntosPm", query = "SELECT v FROM VehiculoDanoSeveridad v WHERE v.puntosPm = :puntosPm")
    , @NamedQuery(name = "VehiculoDanoSeveridad.findByUsername", query = "SELECT v FROM VehiculoDanoSeveridad v WHERE v.username = :username")
    , @NamedQuery(name = "VehiculoDanoSeveridad.findByCreado", query = "SELECT v FROM VehiculoDanoSeveridad v WHERE v.creado = :creado")
    , @NamedQuery(name = "VehiculoDanoSeveridad.findByModificado", query = "SELECT v FROM VehiculoDanoSeveridad v WHERE v.modificado = :modificado")
    , @NamedQuery(name = "VehiculoDanoSeveridad.findByEstadoReg", query = "SELECT v FROM VehiculoDanoSeveridad v WHERE v.estadoReg = :estadoReg")})
public class VehiculoDanoSeveridad implements Serializable {

    @OneToMany(mappedBy = "idVehiculoDanoSeveridad", fetch = FetchType.LAZY)
    private List<VehiculoDanoCosto> vehiculoDanoCostoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo_dano_severidad")
    private Integer idVehiculoDanoSeveridad;
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
    @Column(name = "puntos_pm")
    private int puntosPm;
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
    @OneToMany(mappedBy = "idVehiculoDanoSeveridad", fetch = FetchType.LAZY)
    private List<NovedadDano> novedadDanoList;

    public VehiculoDanoSeveridad() {
    }

    public VehiculoDanoSeveridad(Integer idVehiculoDanoSeveridad) {
        this.idVehiculoDanoSeveridad = idVehiculoDanoSeveridad;
    }

    public VehiculoDanoSeveridad(Integer idVehiculoDanoSeveridad, String nombre, String descripcion, int puntosPm, String username, Date creado, int estadoReg) {
        this.idVehiculoDanoSeveridad = idVehiculoDanoSeveridad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntosPm = puntosPm;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdVehiculoDanoSeveridad() {
        return idVehiculoDanoSeveridad;
    }

    public void setIdVehiculoDanoSeveridad(Integer idVehiculoDanoSeveridad) {
        this.idVehiculoDanoSeveridad = idVehiculoDanoSeveridad;
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

    public int getPuntosPm() {
        return puntosPm;
    }

    public void setPuntosPm(int puntosPm) {
        this.puntosPm = puntosPm;
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
    public List<NovedadDano> getNovedadDanoList() {
        return novedadDanoList;
    }

    public void setNovedadDanoList(List<NovedadDano> novedadDanoList) {
        this.novedadDanoList = novedadDanoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVehiculoDanoSeveridad != null ? idVehiculoDanoSeveridad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VehiculoDanoSeveridad)) {
            return false;
        }
        VehiculoDanoSeveridad other = (VehiculoDanoSeveridad) object;
        if ((this.idVehiculoDanoSeveridad == null && other.idVehiculoDanoSeveridad != null) || (this.idVehiculoDanoSeveridad != null && !this.idVehiculoDanoSeveridad.equals(other.idVehiculoDanoSeveridad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.VehiculoDanoSeveridad[ idVehiculoDanoSeveridad=" + idVehiculoDanoSeveridad + " ]";
    }

    @XmlTransient
    public List<VehiculoDanoCosto> getVehiculoDanoCostoList() {
        return vehiculoDanoCostoList;
    }

    public void setVehiculoDanoCostoList(List<VehiculoDanoCosto> vehiculoDanoCostoList) {
        this.vehiculoDanoCostoList = vehiculoDanoCostoList;
    }
    
}
