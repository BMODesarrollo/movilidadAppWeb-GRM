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

/**
 *
 * @author cesar
 */
@Entity
@Table(name = "novedad_mtto_diaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadMttoDiaria.findAll", query = "SELECT n FROM NovedadMttoDiaria n")
    , @NamedQuery(name = "NovedadMttoDiaria.findByIdNovedadMttoDiaria", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.idNovedadMttoDiaria = :idNovedadMttoDiaria")
    , @NamedQuery(name = "NovedadMttoDiaria.findByIdNovedadMttoTipoActividad", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.idNovedadMttoTipoActividad = :idNovedadMttoTipoActividad")
    , @NamedQuery(name = "NovedadMttoDiaria.findByIdNovedadMttoTipoActDet", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.idNovedadMttoTipoActDet = :idNovedadMttoTipoActDet")
    , @NamedQuery(name = "NovedadMttoDiaria.findByFechaHora", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.fechaHora = :fechaHora")
    , @NamedQuery(name = "NovedadMttoDiaria.findByHoraInicio", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.horaInicio = :horaInicio")
    , @NamedQuery(name = "NovedadMttoDiaria.findByHoraFin", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.horaFin = :horaFin")
    , @NamedQuery(name = "NovedadMttoDiaria.findByDescripcion", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.descripcion = :descripcion")
    , @NamedQuery(name = "NovedadMttoDiaria.findByNombresEmpleados", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.nombresEmpleados = :nombresEmpleados")
    , @NamedQuery(name = "NovedadMttoDiaria.findByPathFotos", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.pathFotos = :pathFotos")
    , @NamedQuery(name = "NovedadMttoDiaria.findByCreado", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.creado = :creado")
    , @NamedQuery(name = "NovedadMttoDiaria.findByModificado", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.modificado = :modificado")
    , @NamedQuery(name = "NovedadMttoDiaria.findByEstadoReg", query = "SELECT n FROM NovedadMttoDiaria n WHERE n.estadoReg = :estadoReg")})
public class NovedadMttoDiaria implements Serializable {

    @JoinColumn(name = "id_novedad_mtto_tipo_actividad", referencedColumnName = "id_novedad_mtto_tipo_actividad")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadMttoTipoActividad idNovedadMttoTipoActividad;
    @JoinColumn(name = "id_novedad_mtto_tipo_act_det", referencedColumnName = "id_novedad_mtto_tipo_actividad_det")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadMttoTipoActividadDet idNovedadMttoTipoActDet;

    @Size(max = 15)
    @Column(name = "username")
    private String username;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_mtto_diaria")
    private Integer idNovedadMttoDiaria;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIME)
    private Date horaFin;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "nombres_empleados")
    private String nombresEmpleados;
    @Size(max = 255)
    @Column(name = "path_fotos")
    private String pathFotos;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_cabina", referencedColumnName = "id_cable_cabina")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableCabina idCabina;
    @JoinColumn(name = "id_estacion", referencedColumnName = "id_cable_estacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableEstacion idEstacion;
    @JoinColumn(name = "id_pilona", referencedColumnName = "id_seg_pilona")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegPilona idPilona;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedadMttoDiaria", fetch = FetchType.LAZY)
    private List<NovedadMtto> novedadMttoList;

    public NovedadMttoDiaria() {
    }

    public NovedadMttoDiaria(Integer idNovedadMttoDiaria) {
        this.idNovedadMttoDiaria = idNovedadMttoDiaria;
    }

    public NovedadMttoDiaria(Integer idNovedadMttoDiaria, Date modificado) {
        this.idNovedadMttoDiaria = idNovedadMttoDiaria;
        this.modificado = modificado;
    }

    public Integer getIdNovedadMttoDiaria() {
        return idNovedadMttoDiaria;
    }

    public void setIdNovedadMttoDiaria(Integer idNovedadMttoDiaria) {
        this.idNovedadMttoDiaria = idNovedadMttoDiaria;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombresEmpleados() {
        return nombresEmpleados;
    }

    public void setNombresEmpleados(String nombresEmpleados) {
        this.nombresEmpleados = nombresEmpleados;
    }

    public String getPathFotos() {
        return pathFotos;
    }

    public void setPathFotos(String pathFotos) {
        this.pathFotos = pathFotos;
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

    public CableCabina getIdCabina() {
        return idCabina;
    }

    public void setIdCabina(CableCabina idCabina) {
        this.idCabina = idCabina;
    }

    public CableEstacion getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(CableEstacion idEstacion) {
        this.idEstacion = idEstacion;
    }

    public SegPilona getIdPilona() {
        return idPilona;
    }

    public void setIdPilona(SegPilona idPilona) {
        this.idPilona = idPilona;
    }

    public List<NovedadMtto> getNovedadMttoList() {
        return novedadMttoList;
    }

    public void setNovedadMttoList(List<NovedadMtto> novedadMttoList) {
        this.novedadMttoList = novedadMttoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadMttoDiaria != null ? idNovedadMttoDiaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadMttoDiaria)) {
            return false;
        }
        NovedadMttoDiaria other = (NovedadMttoDiaria) object;
        if ((this.idNovedadMttoDiaria == null && other.idNovedadMttoDiaria != null) || (this.idNovedadMttoDiaria != null && !this.idNovedadMttoDiaria.equals(other.idNovedadMttoDiaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadMttoDiaria[ idNovedadMttoDiaria=" + idNovedadMttoDiaria + " ]";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public NovedadMttoTipoActividad getIdNovedadMttoTipoActividad() {
        return idNovedadMttoTipoActividad;
    }

    public void setIdNovedadMttoTipoActividad(NovedadMttoTipoActividad idNovedadMttoTipoActividad) {
        this.idNovedadMttoTipoActividad = idNovedadMttoTipoActividad;
    }

    public NovedadMttoTipoActividadDet getIdNovedadMttoTipoActDet() {
        return idNovedadMttoTipoActDet;
    }

    public void setIdNovedadMttoTipoActDet(NovedadMttoTipoActividadDet idNovedadMttoTipoActDet) {
        this.idNovedadMttoTipoActDet = idNovedadMttoTipoActDet;
    }

}
