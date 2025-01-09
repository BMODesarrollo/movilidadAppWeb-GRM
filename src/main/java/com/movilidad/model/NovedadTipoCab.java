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
import javax.persistence.Lob;
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
@Table(name = "novedad_tipo_cab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadTipoCab.findAll", query = "SELECT n FROM NovedadTipoCab n"),
    @NamedQuery(name = "NovedadTipoCab.findByIdNovedadTipoCab", query = "SELECT n FROM NovedadTipoCab n WHERE n.idNovedadTipoCab = :idNovedadTipoCab"),
    @NamedQuery(name = "NovedadTipoCab.findByNombre", query = "SELECT n FROM NovedadTipoCab n WHERE n.nombre = :nombre"),
    @NamedQuery(name = "NovedadTipoCab.findByDescripcion", query = "SELECT n FROM NovedadTipoCab n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "NovedadTipoCab.findByUsername", query = "SELECT n FROM NovedadTipoCab n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadTipoCab.findByCreado", query = "SELECT n FROM NovedadTipoCab n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadTipoCab.findByModificado", query = "SELECT n FROM NovedadTipoCab n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadTipoCab.findByEstadoReg", query = "SELECT n FROM NovedadTipoCab n WHERE n.estadoReg = :estadoReg")})
public class NovedadTipoCab implements Serializable {
    @Column(name = "estado_reg")
    private int estadoReg;
    @OneToMany(mappedBy = "idNovedadTipoCab", fetch = FetchType.LAZY)
    private List<NovedadCab> novedadCabList;
    @OneToMany(mappedBy = "idNovedadTipoCab", fetch = FetchType.LAZY)
    private List<NovedadTipoDetallesCab> novedadTipoDetallesCabList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_tipo_cab")
    private Integer idNovedadTipoCab;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
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

    public NovedadTipoCab() {
    }

    public NovedadTipoCab(Integer idNovedadTipoCab) {
        this.idNovedadTipoCab = idNovedadTipoCab;
    }

    public NovedadTipoCab(Integer idNovedadTipoCab, String nombre, String descripcion) {
        this.idNovedadTipoCab = idNovedadTipoCab;
        this.nombre = nombre;
        this.descripcion = descripcion;
		}
    public NovedadTipoCab(Integer idNovedadTipoCab, String nombre, String descripcion, int estadoReg) {
        this.idNovedadTipoCab = idNovedadTipoCab;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadTipoCab() {
        return idNovedadTipoCab;
    }

    public void setIdNovedadTipoCab(Integer idNovedadTipoCab) {
        this.idNovedadTipoCab = idNovedadTipoCab;
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

    @XmlTransient
    public List<NovedadTipoDetallesCab> getNovedadTipoDetallesCabList() {
        return novedadTipoDetallesCabList;
    }

    public void setNovedadTipoDetallesCabList(List<NovedadTipoDetallesCab> novedadTipoDetallesCabList) {
        this.novedadTipoDetallesCabList = novedadTipoDetallesCabList;
    }

    @XmlTransient
    public List<NovedadCab> getNovedadCabList() {
        return novedadCabList;
    }

    public void setNovedadCabList(List<NovedadCab> novedadCabList) {
        this.novedadCabList = novedadCabList;
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
        hash += (idNovedadTipoCab != null ? idNovedadTipoCab.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadTipoCab)) {
            return false;
        }
        NovedadTipoCab other = (NovedadTipoCab) object;
        if ((this.idNovedadTipoCab == null && other.idNovedadTipoCab != null) || (this.idNovedadTipoCab != null && !this.idNovedadTipoCab.equals(other.idNovedadTipoCab))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadTipoCab[ idNovedadTipoCab=" + idNovedadTipoCab + " ]";
    }


}
