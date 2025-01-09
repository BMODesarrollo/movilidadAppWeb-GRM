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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "lavado_tipo_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LavadoTipoServicio.findAll", query = "SELECT l FROM LavadoTipoServicio l"),
    @NamedQuery(name = "LavadoTipoServicio.findByIdLavadoTipoServicio", query = "SELECT l FROM LavadoTipoServicio l WHERE l.idLavadoTipoServicio = :idLavadoTipoServicio"),
    @NamedQuery(name = "LavadoTipoServicio.findByNombre", query = "SELECT l FROM LavadoTipoServicio l WHERE l.nombre = :nombre"),
    @NamedQuery(name = "LavadoTipoServicio.findByEspecial", query = "SELECT l FROM LavadoTipoServicio l WHERE l.especial = :especial"),
    @NamedQuery(name = "LavadoTipoServicio.findByUsername", query = "SELECT l FROM LavadoTipoServicio l WHERE l.username = :username"),
    @NamedQuery(name = "LavadoTipoServicio.findByCreado", query = "SELECT l FROM LavadoTipoServicio l WHERE l.creado = :creado"),
    @NamedQuery(name = "LavadoTipoServicio.findByModificado", query = "SELECT l FROM LavadoTipoServicio l WHERE l.modificado = :modificado"),
    @NamedQuery(name = "LavadoTipoServicio.findByEstadoReg", query = "SELECT l FROM LavadoTipoServicio l WHERE l.estadoReg = :estadoReg")})
public class LavadoTipoServicio implements Serializable {

    @OneToMany(mappedBy = "idLavadoTipoServicio", fetch = FetchType.LAZY)
    private List<LavadoCosto> lavadoCostoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lavado_tipo_servicio")
    private Integer idLavadoTipoServicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "especial")
    private int especial;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
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
    @JoinColumn(name = "id_vehiculo_tipo", referencedColumnName = "id_vehiculo_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipo idVehiculoTipo;

    public LavadoTipoServicio() {
    }

    public LavadoTipoServicio(Integer idLavadoTipoServicio) {
        this.idLavadoTipoServicio = idLavadoTipoServicio;
    }

    public LavadoTipoServicio(Integer idLavadoTipoServicio, String nombre, int especial, String descripcion, String username, int estadoReg) {
        this.idLavadoTipoServicio = idLavadoTipoServicio;
        this.nombre = nombre;
        this.especial = especial;
        this.descripcion = descripcion;
        this.username = username;
        this.estadoReg = estadoReg;
    }

    public Integer getIdLavadoTipoServicio() {
        return idLavadoTipoServicio;
    }

    public void setIdLavadoTipoServicio(Integer idLavadoTipoServicio) {
        this.idLavadoTipoServicio = idLavadoTipoServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEspecial() {
        return especial;
    }

    public void setEspecial(int especial) {
        this.especial = especial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public VehiculoTipo getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(VehiculoTipo idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
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
        hash += (idLavadoTipoServicio != null ? idLavadoTipoServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LavadoTipoServicio)) {
            return false;
        }
        LavadoTipoServicio other = (LavadoTipoServicio) object;
        if ((this.idLavadoTipoServicio == null && other.idLavadoTipoServicio != null) || (this.idLavadoTipoServicio != null && !this.idLavadoTipoServicio.equals(other.idLavadoTipoServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.LavadoTipoServicio[ idLavadoTipoServicio=" + idLavadoTipoServicio + " ]";
    }

    @XmlTransient
    public List<LavadoCosto> getLavadoCostoList() {
        return lavadoCostoList;
    }

    public void setLavadoCostoList(List<LavadoCosto> lavadoCostoList) {
        this.lavadoCostoList = lavadoCostoList;
    }

}
