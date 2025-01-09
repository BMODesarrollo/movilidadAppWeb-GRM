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
@Table(name = "seg_pilona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegPilona.findAll", query = "SELECT s FROM SegPilona s"),
    @NamedQuery(name = "SegPilona.findByIdSegPilona", query = "SELECT s FROM SegPilona s WHERE s.idSegPilona = :idSegPilona"),
    @NamedQuery(name = "SegPilona.findByCodigo", query = "SELECT s FROM SegPilona s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "SegPilona.findByNombre", query = "SELECT s FROM SegPilona s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "SegPilona.findByLatitud", query = "SELECT s FROM SegPilona s WHERE s.latitud = :latitud"),
    @NamedQuery(name = "SegPilona.findByLongitud", query = "SELECT s FROM SegPilona s WHERE s.longitud = :longitud"),
    @NamedQuery(name = "SegPilona.findByUsername", query = "SELECT s FROM SegPilona s WHERE s.username = :username"),
    @NamedQuery(name = "SegPilona.findByCreado", query = "SELECT s FROM SegPilona s WHERE s.creado = :creado"),
    @NamedQuery(name = "SegPilona.findByModificado", query = "SELECT s FROM SegPilona s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SegPilona.findByEstadoReg", query = "SELECT s FROM SegPilona s WHERE s.estadoReg = :estadoReg")})
public class SegPilona implements Serializable {

    @OneToMany(mappedBy = "idPilona", fetch = FetchType.LAZY)
    private List<ActividadInfraDiaria> actividadInfraDiariaList;

    @OneToMany(mappedBy = "idPilona", fetch = FetchType.LAZY)
    private List<NovedadMttoDiaria> novedadMttoDiariaList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSegPilona", fetch = FetchType.LAZY)
    private List<SegReportePilona> segReportePilonaList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_pilona")
    private Integer idSegPilona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "latitud")
    private String latitud;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "longitud")
    private String longitud;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
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

    @OneToMany(mappedBy = "idSegPilona", fetch = FetchType.LAZY)
    private List<NovedadInfrastruc> novedadInfrastrucList;

    @OneToMany(mappedBy = "idSegPilona", fetch = FetchType.LAZY)
    private List<NovedadMtto> novedadMttoList;

    public SegPilona() {
    }

    public SegPilona(Integer idSegPilona) {
        this.idSegPilona = idSegPilona;
    }

    public SegPilona(Integer idSegPilona, String codigo, String nombre, String latitud, String longitud, String username, Date creado, int estadoReg) {
        this.idSegPilona = idSegPilona;
        this.codigo = codigo;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdSegPilona() {
        return idSegPilona;
    }

    public void setIdSegPilona(Integer idSegPilona) {
        this.idSegPilona = idSegPilona;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public int getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(int estadoReg) {
        this.estadoReg = estadoReg;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public List<NovedadInfrastruc> getNovedadInfrastrucList() {
        return novedadInfrastrucList;
    }

    public void setNovedadInfrastrucList(List<NovedadInfrastruc> novedadInfrastrucList) {
        this.novedadInfrastrucList = novedadInfrastrucList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegPilona != null ? idSegPilona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegPilona)) {
            return false;
        }
        SegPilona other = (SegPilona) object;
        if ((this.idSegPilona == null && other.idSegPilona != null) || (this.idSegPilona != null && !this.idSegPilona.equals(other.idSegPilona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegPilona[ idSegPilona=" + idSegPilona + " ]";
    }

    @XmlTransient
    public List<SegReportePilona> getSegReportePilonaList() {
        return segReportePilonaList;
    }

    public void setSegReportePilonaList(List<SegReportePilona> segReportePilonaList) {
        this.segReportePilonaList = segReportePilonaList;
    }


    @XmlTransient
    public List<NovedadMttoDiaria> getNovedadMttoDiariaList() {
        return novedadMttoDiariaList;
    }

    public void setNovedadMttoDiariaList(List<NovedadMttoDiaria> novedadMttoDiariaList) {
        this.novedadMttoDiariaList = novedadMttoDiariaList;
    }

    @XmlTransient
    public List<ActividadInfraDiaria> getActividadInfraDiariaList() {
        return actividadInfraDiariaList;
    }

    public void setActividadInfraDiariaList(List<ActividadInfraDiaria> actividadInfraDiariaList) {
        this.actividadInfraDiariaList = actividadInfraDiariaList;
    }

    @XmlTransient
    public List<NovedadMtto> getNovedadMttoList() {
        return novedadMttoList;
    }

    public void setNovedadMttoList(List<NovedadMtto> novedadMttoList) {
        this.novedadMttoList = novedadMttoList;
    }

}
