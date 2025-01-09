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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "novedad_incapacidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadIncapacidad.findAll", query = "SELECT n FROM NovedadIncapacidad n"),
    @NamedQuery(name = "NovedadIncapacidad.findByIdNovedadIncapacidad", query = "SELECT n FROM NovedadIncapacidad n WHERE n.idNovedadIncapacidad = :idNovedadIncapacidad"),
    @NamedQuery(name = "NovedadIncapacidad.findByFecha", query = "SELECT n FROM NovedadIncapacidad n WHERE n.fecha = :fecha"),
    @NamedQuery(name = "NovedadIncapacidad.findByUsername", query = "SELECT n FROM NovedadIncapacidad n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadIncapacidad.findByCreado", query = "SELECT n FROM NovedadIncapacidad n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadIncapacidad.findByModificado", query = "SELECT n FROM NovedadIncapacidad n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadIncapacidad.findByEstadoReg", query = "SELECT n FROM NovedadIncapacidad n WHERE n.estadoReg = :estadoReg")})
public class NovedadIncapacidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_incapacidad")
    private Integer idNovedadIncapacidad;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "numero_incapacidad")
    private Integer numeroIncapacidad;
    @Size(max = 15)
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
    @JoinColumn(name = "id_incapacidad_dx", referencedColumnName = "id_incapacidad_dx")
    @ManyToOne
    private IncapacidadDx idIncapacidadDx;
    @JoinColumn(name = "id_novedad", referencedColumnName = "id_novedad")
    @ManyToOne
    private Novedad idNovedad;
    @JoinColumn(name = "id_incapacidad_ordena", referencedColumnName = "id_incapacidad_ordena")
    @ManyToOne
    private IncapacidadOrdena idIncapacidadOrdena;
    @JoinColumn(name = "id_incapacidad_tipo", referencedColumnName = "id_incapacidad_tipo")
    @ManyToOne
    private IncapacidadTipo idIncapacidadTipo;

    public NovedadIncapacidad() {
    }

    public NovedadIncapacidad(Integer idNovedadIncapacidad) {
        this.idNovedadIncapacidad = idNovedadIncapacidad;
    }

    public Integer getIdNovedadIncapacidad() {
        return idNovedadIncapacidad;
    }

    public void setIdNovedadIncapacidad(Integer idNovedadIncapacidad) {
        this.idNovedadIncapacidad = idNovedadIncapacidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getNumeroIncapacidad() {
        return numeroIncapacidad;
    }

    public void setNumeroIncapacidad(Integer numeroIncapacidad) {
        this.numeroIncapacidad = numeroIncapacidad;
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

    public IncapacidadDx getIdIncapacidadDx() {
        return idIncapacidadDx;
    }

    public void setIdIncapacidadDx(IncapacidadDx idIncapacidadDx) {
        this.idIncapacidadDx = idIncapacidadDx;
    }

    public Novedad getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Novedad idNovedad) {
        this.idNovedad = idNovedad;
    }

    public IncapacidadOrdena getIdIncapacidadOrdena() {
        return idIncapacidadOrdena;
    }

    public void setIdIncapacidadOrdena(IncapacidadOrdena idIncapacidadOrdena) {
        this.idIncapacidadOrdena = idIncapacidadOrdena;
    }

    public IncapacidadTipo getIdIncapacidadTipo() {
        return idIncapacidadTipo;
    }

    public void setIdIncapacidadTipo(IncapacidadTipo idIncapacidadTipo) {
        this.idIncapacidadTipo = idIncapacidadTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadIncapacidad != null ? idNovedadIncapacidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadIncapacidad)) {
            return false;
        }
        NovedadIncapacidad other = (NovedadIncapacidad) object;
        if ((this.idNovedadIncapacidad == null && other.idNovedadIncapacidad != null) || (this.idNovedadIncapacidad != null && !this.idNovedadIncapacidad.equals(other.idNovedadIncapacidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadIncapacidad[ idNovedadIncapacidad=" + idNovedadIncapacidad + " ]";
    }

}
