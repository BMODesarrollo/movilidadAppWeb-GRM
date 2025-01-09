/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "seg_reporte_pilona_novedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegReportePilonaNovedad.findAll", query = "SELECT s FROM SegReportePilonaNovedad s"),
    @NamedQuery(name = "SegReportePilonaNovedad.findByIdSegReportePilonaNovedad", query = "SELECT s FROM SegReportePilonaNovedad s WHERE s.idSegReportePilonaNovedad = :idSegReportePilonaNovedad"),
    @NamedQuery(name = "SegReportePilonaNovedad.findByPathFoto", query = "SELECT s FROM SegReportePilonaNovedad s WHERE s.pathFoto = :pathFoto"),
    @NamedQuery(name = "SegReportePilonaNovedad.findByUsername", query = "SELECT s FROM SegReportePilonaNovedad s WHERE s.username = :username"),
    @NamedQuery(name = "SegReportePilonaNovedad.findByCreado", query = "SELECT s FROM SegReportePilonaNovedad s WHERE s.creado = :creado"),
    @NamedQuery(name = "SegReportePilonaNovedad.findByModificado", query = "SELECT s FROM SegReportePilonaNovedad s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SegReportePilonaNovedad.findByEstadoReg", query = "SELECT s FROM SegReportePilonaNovedad s WHERE s.estadoReg = :estadoReg")})
public class SegReportePilonaNovedad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_reporte_pilona_novedad")
    private Integer idSegReportePilonaNovedad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "path_foto")
    private String pathFoto;
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
    @JoinColumn(name = "id_novedad_infrastruc", referencedColumnName = "id_novedad_infrastruc")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private NovedadInfrastruc novedadInfrastruc;
    @JoinColumn(name = "id_seg_reporte_pilona", referencedColumnName = "id_seg_reporte_pilona")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegReportePilona segReportePilona;

    public SegReportePilonaNovedad() {
    }

    public SegReportePilonaNovedad(Integer idSegReportePilonaNovedad) {
        this.idSegReportePilonaNovedad = idSegReportePilonaNovedad;
    }

    public SegReportePilonaNovedad(Integer idSegReportePilonaNovedad, String pathFoto) {
        this.idSegReportePilonaNovedad = idSegReportePilonaNovedad;
        this.pathFoto = pathFoto;
    }

    public Integer getIdSegReportePilonaNovedad() {
        return idSegReportePilonaNovedad;
    }

    public void setIdSegReportePilonaNovedad(Integer idSegReportePilonaNovedad) {
        this.idSegReportePilonaNovedad = idSegReportePilonaNovedad;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
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

    public NovedadInfrastruc getNovedadInfrastruc() {
        return novedadInfrastruc;
    }

    public void setNovedadInfrastruc(NovedadInfrastruc novedadInfrastruc) {
        this.novedadInfrastruc = novedadInfrastruc;
    }

    public SegReportePilona getSegReportePilona() {
        return segReportePilona;
    }

    public void setSegReportePilona(SegReportePilona segReportePilona) {
        this.segReportePilona = segReportePilona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegReportePilonaNovedad != null ? idSegReportePilonaNovedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegReportePilonaNovedad)) {
            return false;
        }
        SegReportePilonaNovedad other = (SegReportePilonaNovedad) object;
        if ((this.idSegReportePilonaNovedad == null && other.idSegReportePilonaNovedad != null) || (this.idSegReportePilonaNovedad != null && !this.idSegReportePilonaNovedad.equals(other.idSegReportePilonaNovedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegReportePilonaNovedad[ idSegReportePilonaNovedad=" + idSegReportePilonaNovedad + " ]";
    }

}
