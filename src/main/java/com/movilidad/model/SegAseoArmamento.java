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
@Table(name = "seg_aseo_armamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegAseoArmamento.findAll", query = "SELECT s FROM SegAseoArmamento s"),
    @NamedQuery(name = "SegAseoArmamento.findByIdSegAseoArmamento", query = "SELECT s FROM SegAseoArmamento s WHERE s.idSegAseoArmamento = :idSegAseoArmamento"),
    @NamedQuery(name = "SegAseoArmamento.findByFechaInicio", query = "SELECT s FROM SegAseoArmamento s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SegAseoArmamento.findByFechaFin", query = "SELECT s FROM SegAseoArmamento s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SegAseoArmamento.findByPathFotos", query = "SELECT s FROM SegAseoArmamento s WHERE s.pathFotos = :pathFotos"),
    @NamedQuery(name = "SegAseoArmamento.findByPathPlanilla", query = "SELECT s FROM SegAseoArmamento s WHERE s.pathMinuta = :pathMinuta"),
    @NamedQuery(name = "SegAseoArmamento.findByUsername", query = "SELECT s FROM SegAseoArmamento s WHERE s.username = :username"),
    @NamedQuery(name = "SegAseoArmamento.findByCreado", query = "SELECT s FROM SegAseoArmamento s WHERE s.creado = :creado"),
    @NamedQuery(name = "SegAseoArmamento.findByModificado", query = "SELECT s FROM SegAseoArmamento s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SegAseoArmamento.findByEstadoReg", query = "SELECT s FROM SegAseoArmamento s WHERE s.estadoReg = :estadoReg")})
public class SegAseoArmamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_aseo_armamento")
    private Integer idSegAseoArmamento;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Size(max = 150)
    @Column(name = "path_fotos")
    private String pathFotos;
    @Size(max = 150)
    @Column(name = "path_minuta")
    private String pathMinuta;
    @Size(max = 200)
    @Column(name = "observacion")
    private String observacion;
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
    @JoinColumn(name = "id_sst_empresa_visitante", referencedColumnName = "id_sst_empresa_visitante")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstEmpresaVisitante sstEmpresaVisitante;
    @JoinColumn(name = "id_cable_ubicacion", referencedColumnName = "id_cable_ubicacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CableUbicacion cableUbicacion;
    @JoinColumn(name = "id_seg_registro_armamento", referencedColumnName = "id_seg_registro_armamento")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SegRegistroArmamento segRegistroArmamento;

    public SegAseoArmamento() {
    }

    public SegAseoArmamento(Integer idSegAseoArmamento) {
        this.idSegAseoArmamento = idSegAseoArmamento;
    }

    public SegAseoArmamento(Integer idSegAseoArmamento, String username, Date creado, int estadoReg) {
        this.idSegAseoArmamento = idSegAseoArmamento;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdSegAseoArmamento() {
        return idSegAseoArmamento;
    }

    public void setIdSegAseoArmamento(Integer idSegAseoArmamento) {
        this.idSegAseoArmamento = idSegAseoArmamento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getPathFotos() {
        return pathFotos;
    }

    public void setPathFotos(String pathFotos) {
        this.pathFotos = pathFotos;
    }

    public String getPathPlanilla() {
        return pathMinuta;
    }

    public void setPathPlanilla(String pathMinuta) {
        this.pathMinuta = pathMinuta;
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

    public SstEmpresaVisitante getSstEmpresaVisitante() {
        return sstEmpresaVisitante;
    }

    public void setSstEmpresaVisitante(SstEmpresaVisitante sstEmpresaVisitante) {
        this.sstEmpresaVisitante = sstEmpresaVisitante;
    }

    public CableUbicacion getCableUbicacion() {
        return cableUbicacion;
    }

    public void setCableUbicacion(CableUbicacion cableUbicacion) {
        this.cableUbicacion = cableUbicacion;
    }

    public SegRegistroArmamento getSegRegistroArmamento() {
        return segRegistroArmamento;
    }

    public void setSegRegistroArmamento(SegRegistroArmamento segRegistroArmamento) {
        this.segRegistroArmamento = segRegistroArmamento;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegAseoArmamento != null ? idSegAseoArmamento.hashCode() : 0);
        return hash;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegAseoArmamento)) {
            return false;
        }
        SegAseoArmamento other = (SegAseoArmamento) object;
        if ((this.idSegAseoArmamento == null && other.idSegAseoArmamento != null) || (this.idSegAseoArmamento != null && !this.idSegAseoArmamento.equals(other.idSegAseoArmamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegAseoArmamento[ idSegAseoArmamento=" + idSegAseoArmamento + " ]";
    }

}
