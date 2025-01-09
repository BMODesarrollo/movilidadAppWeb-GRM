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
 * @author solucionesit
 */
@Entity
@Table(name = "seg_reporte_pilona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegReportePilona.findAll", query = "SELECT s FROM SegReportePilona s"),
    @NamedQuery(name = "SegReportePilona.findByIdSegReportePilona", query = "SELECT s FROM SegReportePilona s WHERE s.idSegReportePilona = :idSegReportePilona"),
    @NamedQuery(name = "SegReportePilona.findByPathFotos", query = "SELECT s FROM SegReportePilona s WHERE s.pathFotos = :pathFotos"),
    @NamedQuery(name = "SegReportePilona.findByPathFotoMinuta", query = "SELECT s FROM SegReportePilona s WHERE s.pathFotoMinuta = :pathFotoMinuta"),
    @NamedQuery(name = "SegReportePilona.findByObservaci\u00f3n", query = "SELECT s FROM SegReportePilona s WHERE s.observaci\u00f3n = :observaci\u00f3n"),
    @NamedQuery(name = "SegReportePilona.findByFechaHora", query = "SELECT s FROM SegReportePilona s WHERE s.fechaHora = :fechaHora"),
    @NamedQuery(name = "SegReportePilona.findByUsername", query = "SELECT s FROM SegReportePilona s WHERE s.username = :username"),
    @NamedQuery(name = "SegReportePilona.findByCreado", query = "SELECT s FROM SegReportePilona s WHERE s.creado = :creado"),
    @NamedQuery(name = "SegReportePilona.findByModificado", query = "SELECT s FROM SegReportePilona s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SegReportePilona.findByEstadoReg", query = "SELECT s FROM SegReportePilona s WHERE s.estadoReg = :estadoReg")})
public class SegReportePilona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_reporte_pilona")
    private Integer idSegReportePilona;
    @Column(name = "num_recorrido")
    private int numRecorrido;
    @Size(max = 150)
    @Column(name = "path_fotos")
    private String pathFotos;
    @Size(max = 150)
    @Column(name = "path_foto_minuta")
    private String pathFotoMinuta;
    @Size(max = 200)
    @Column(name = "observaci\u00f3n")
    private String observación;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
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
    @JoinColumn(name = "id_seg_pilona", referencedColumnName = "id_seg_pilona")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SegPilona idSegPilona;
    @OneToMany(mappedBy = "segReportePilona", fetch = FetchType.LAZY)
    private List<SegReportePilonaNovedad> segReportePilonaNovedadList;

    public SegReportePilona() {
    }

    public SegReportePilona(Integer idSegReportePilona) {
        this.idSegReportePilona = idSegReportePilona;
    }

    public SegReportePilona(Integer idSegReportePilona, Date fechaHora, String username, Date creado, int estadoReg) {
        this.idSegReportePilona = idSegReportePilona;
        this.fechaHora = fechaHora;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdSegReportePilona() {
        return idSegReportePilona;
    }

    public void setIdSegReportePilona(Integer idSegReportePilona) {
        this.idSegReportePilona = idSegReportePilona;
    }

    public String getPathFotos() {
        return pathFotos;
    }

    public void setPathFotos(String pathFotos) {
        this.pathFotos = pathFotos;
    }

    public String getPathFotoMinuta() {
        return pathFotoMinuta;
    }

    public void setPathFotoMinuta(String pathFotoMinuta) {
        this.pathFotoMinuta = pathFotoMinuta;
    }

    public String getObservación() {
        return observación;
    }

    public void setObservación(String observación) {
        this.observación = observación;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
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

    public SegPilona getIdSegPilona() {
        return idSegPilona;
    }

    public void setIdSegPilona(SegPilona idSegPilona) {
        this.idSegPilona = idSegPilona;
    }

    public int getNumRecorrido() {
        return numRecorrido;
    }

    public void setNumRecorrido(int numRecorrido) {
        this.numRecorrido = numRecorrido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegReportePilona != null ? idSegReportePilona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegReportePilona)) {
            return false;
        }
        SegReportePilona other = (SegReportePilona) object;
        if ((this.idSegReportePilona == null && other.idSegReportePilona != null) || (this.idSegReportePilona != null && !this.idSegReportePilona.equals(other.idSegReportePilona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegReportePilona[ idSegReportePilona=" + idSegReportePilona + " ]";
    }

    @XmlTransient
    public List<SegReportePilonaNovedad> getSegReportePilonaNovedadList() {
        return segReportePilonaNovedadList;
    }

    public void setSegReportePilonaNovedadList(List<SegReportePilonaNovedad> segReportePilonaNovedadList) {
        this.segReportePilonaNovedadList = segReportePilonaNovedadList;
    }

}
