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

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "novedad_infrastruc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadInfrastruc.findAll", query = "SELECT n FROM NovedadInfrastruc n"),
    @NamedQuery(name = "NovedadInfrastruc.findByIdNovedadInfrastruc", query = "SELECT n FROM NovedadInfrastruc n WHERE n.idNovedadInfrastruc = :idNovedadInfrastruc"),
    @NamedQuery(name = "NovedadInfrastruc.findByFechaHoraReg", query = "SELECT n FROM NovedadInfrastruc n WHERE n.fechaHoraReg = :fechaHoraReg"),
    @NamedQuery(name = "NovedadInfrastruc.findByFechaHoraNov", query = "SELECT n FROM NovedadInfrastruc n WHERE n.fechaHoraNov = :fechaHoraNov"),
    @NamedQuery(name = "NovedadInfrastruc.findByDescripcion", query = "SELECT n FROM NovedadInfrastruc n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "NovedadInfrastruc.findByPathFotos", query = "SELECT n FROM NovedadInfrastruc n WHERE n.pathFotos = :pathFotos"),
    @NamedQuery(name = "NovedadInfrastruc.findByEstado", query = "SELECT n FROM NovedadInfrastruc n WHERE n.estado = :estado"),
    @NamedQuery(name = "NovedadInfrastruc.findByUserReporta", query = "SELECT n FROM NovedadInfrastruc n WHERE n.userReporta = :userReporta"),
    @NamedQuery(name = "NovedadInfrastruc.findByObsSeguimiento", query = "SELECT n FROM NovedadInfrastruc n WHERE n.obsSeguimiento = :obsSeguimiento"),
    @NamedQuery(name = "NovedadInfrastruc.findByUsrSeguimiento", query = "SELECT n FROM NovedadInfrastruc n WHERE n.usrSeguimiento = :usrSeguimiento"),
    @NamedQuery(name = "NovedadInfrastruc.findByObsCierre", query = "SELECT n FROM NovedadInfrastruc n WHERE n.obsCierre = :obsCierre"),
    @NamedQuery(name = "NovedadInfrastruc.findByUsrCierre", query = "SELECT n FROM NovedadInfrastruc n WHERE n.usrCierre = :usrCierre"),
    @NamedQuery(name = "NovedadInfrastruc.findByFechaHoraCierre", query = "SELECT n FROM NovedadInfrastruc n WHERE n.fechaHoraCierre = :fechaHoraCierre"),
    @NamedQuery(name = "NovedadInfrastruc.findByUsername", query = "SELECT n FROM NovedadInfrastruc n WHERE n.username = :username"),
    @NamedQuery(name = "NovedadInfrastruc.findByCreado", query = "SELECT n FROM NovedadInfrastruc n WHERE n.creado = :creado"),
    @NamedQuery(name = "NovedadInfrastruc.findByModificado", query = "SELECT n FROM NovedadInfrastruc n WHERE n.modificado = :modificado"),
    @NamedQuery(name = "NovedadInfrastruc.findByEstadoReg", query = "SELECT n FROM NovedadInfrastruc n WHERE n.estadoReg = :estadoReg")})
public class NovedadInfrastruc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_infrastruc")
    private Integer idNovedadInfrastruc;
    @Column(name = "fecha_hora_reg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraReg;
    @Column(name = "fecha_hora_nov")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraNov;
    @Size(max = 250)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 150)
    @Column(name = "path_fotos")
    private String pathFotos;
    @Column(name = "estado")
    private Integer estado;
    @Size(max = 15)
    @Column(name = "user_reporta")
    private String userReporta;
    @Size(max = 250)
    @Column(name = "obs_seguimiento")
    private String obsSeguimiento;
    @Size(max = 15)
    @Column(name = "usr_seguimiento")
    private String usrSeguimiento;
    @Size(max = 250)
    @Column(name = "obs_cierre")
    private String obsCierre;
    @Size(max = 15)
    @Column(name = "usr_cierre")
    private String usrCierre;
    @Column(name = "fecha_hora_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCierre;
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
    @JoinColumn(name = "id_novedad_tipo_det_infrastruc", referencedColumnName = "id_novedad_tipo_det_infrastruc")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NovedadTipoDetallesInfrastruc novedadTipoDetallesInfrastruc;
    @JoinColumn(name = "id_novedad_tipo_infrastruc", referencedColumnName = "id_novedad_tipo_infrastruc")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NovedadTipoInfrastruc novedadTipoInfrastruc;
    @JoinColumn(name = "id_seg_pilona", referencedColumnName = "id_seg_pilona")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SegPilona idSegPilona;
    @JoinColumn(name = "id_aseo_param_area", referencedColumnName = "id_aseo_param_area")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AseoParamArea idAseoParamArea;
    @OneToMany(mappedBy = "novedadInfrastruc", fetch = FetchType.LAZY)
    private List<SegReportePilonaNovedad> segReportePilonaNovedadList;
    @JoinColumn(name = "id_actividad_infra_diaria", referencedColumnName = "id_actividad_infra_diaria")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ActividadInfraDiaria idActividadInfraDiaria;

    public NovedadInfrastruc() {
    }

    public NovedadInfrastruc(Integer idNovedadInfrastruc) {
        this.idNovedadInfrastruc = idNovedadInfrastruc;
    }

    public NovedadInfrastruc(Integer idNovedadInfrastruc, String username, Date creado, int estadoReg) {
        this.idNovedadInfrastruc = idNovedadInfrastruc;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadInfrastruc() {
        return idNovedadInfrastruc;
    }

    public void setIdNovedadInfrastruc(Integer idNovedadInfrastruc) {
        this.idNovedadInfrastruc = idNovedadInfrastruc;
    }

    public Date getFechaHoraReg() {
        return fechaHoraReg;
    }

    public void setFechaHoraReg(Date fechaHoraReg) {
        this.fechaHoraReg = fechaHoraReg;
    }

    public Date getFechaHoraNov() {
        return fechaHoraNov;
    }

    public void setFechaHoraNov(Date fechaHoraNov) {
        this.fechaHoraNov = fechaHoraNov;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPathFotos() {
        return pathFotos;
    }

    public void setPathFotos(String pathFotos) {
        this.pathFotos = pathFotos;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getUserReporta() {
        return userReporta;
    }

    public void setUserReporta(String userReporta) {
        this.userReporta = userReporta;
    }

    public String getObsSeguimiento() {
        return obsSeguimiento;
    }

    public void setObsSeguimiento(String obsSeguimiento) {
        this.obsSeguimiento = obsSeguimiento;
    }

    public String getUsrSeguimiento() {
        return usrSeguimiento;
    }

    public void setUsrSeguimiento(String usrSeguimiento) {
        this.usrSeguimiento = usrSeguimiento;
    }

    public String getObsCierre() {
        return obsCierre;
    }

    public void setObsCierre(String obsCierre) {
        this.obsCierre = obsCierre;
    }

    public String getUsrCierre() {
        return usrCierre;
    }

    public void setUsrCierre(String usrCierre) {
        this.usrCierre = usrCierre;
    }

    public Date getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(Date fechaHoraCierre) {
        this.fechaHoraCierre = fechaHoraCierre;
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

    public NovedadTipoDetallesInfrastruc getNovedadTipoDetallesInfrastruc() {
        return novedadTipoDetallesInfrastruc;
    }

    public void setNovedadTipoDetallesInfrastruc(NovedadTipoDetallesInfrastruc novedadTipoDetallesInfrastruc) {
        this.novedadTipoDetallesInfrastruc = novedadTipoDetallesInfrastruc;
    }

    public NovedadTipoInfrastruc getNovedadTipoInfrastruc() {
        return novedadTipoInfrastruc;
    }

    public void setNovedadTipoInfrastruc(NovedadTipoInfrastruc novedadTipoInfrastruc) {
        this.novedadTipoInfrastruc = novedadTipoInfrastruc;
    }

    public List<SegReportePilonaNovedad> getSegReportePilonaNovedadList() {
        return segReportePilonaNovedadList;
    }

    public void setSegReportePilonaNovedadList(List<SegReportePilonaNovedad> segReportePilonaNovedadList) {
        this.segReportePilonaNovedadList = segReportePilonaNovedadList;
    }

    public SegPilona getIdSegPilona() {
        return idSegPilona;
    }

    public void setIdSegPilona(SegPilona idSegPilona) {
        this.idSegPilona = idSegPilona;
    }

    public AseoParamArea getIdAseoParamArea() {
        return idAseoParamArea;
    }

    public void setIdAseoParamArea(AseoParamArea idAseoParamArea) {
        this.idAseoParamArea = idAseoParamArea;
    }

    public ActividadInfraDiaria getIdActividadInfraDiaria() {
        return idActividadInfraDiaria;
    }

    public void setIdActividadInfraDiaria(ActividadInfraDiaria idActividadInfraDiaria) {
        this.idActividadInfraDiaria = idActividadInfraDiaria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadInfrastruc != null ? idNovedadInfrastruc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadInfrastruc)) {
            return false;
        }
        NovedadInfrastruc other = (NovedadInfrastruc) object;
        if ((this.idNovedadInfrastruc == null && other.idNovedadInfrastruc != null) || (this.idNovedadInfrastruc != null && !this.idNovedadInfrastruc.equals(other.idNovedadInfrastruc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadInfrastruc[ idNovedadInfrastruc=" + idNovedadInfrastruc + " ]";
    }

}
