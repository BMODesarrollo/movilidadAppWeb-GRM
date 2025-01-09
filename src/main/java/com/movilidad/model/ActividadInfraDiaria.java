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
 * @author soluciones-it
 */
@Entity
@Table(name = "actividad_infra_diaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadInfraDiaria.findAll", query = "SELECT a FROM ActividadInfraDiaria a")
    , @NamedQuery(name = "ActividadInfraDiaria.findByIdActividadInfraDiaria", query = "SELECT a FROM ActividadInfraDiaria a WHERE a.idActividadInfraDiaria = :idActividadInfraDiaria")
    , @NamedQuery(name = "ActividadInfraDiaria.findByFechaHora", query = "SELECT a FROM ActividadInfraDiaria a WHERE a.fechaHora = :fechaHora")
    , @NamedQuery(name = "ActividadInfraDiaria.findByHoraInicio", query = "SELECT a FROM ActividadInfraDiaria a WHERE a.horaInicio = :horaInicio")
    , @NamedQuery(name = "ActividadInfraDiaria.findByHoraFin", query = "SELECT a FROM ActividadInfraDiaria a WHERE a.horaFin = :horaFin")
    , @NamedQuery(name = "ActividadInfraDiaria.findByDescripcion", query = "SELECT a FROM ActividadInfraDiaria a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "ActividadInfraDiaria.findByNombresEmpleados", query = "SELECT a FROM ActividadInfraDiaria a WHERE a.nombresEmpleados = :nombresEmpleados")
    , @NamedQuery(name = "ActividadInfraDiaria.findByPathFotos", query = "SELECT a FROM ActividadInfraDiaria a WHERE a.pathFotos = :pathFotos")
    , @NamedQuery(name = "ActividadInfraDiaria.findByUsername", query = "SELECT a FROM ActividadInfraDiaria a WHERE a.username = :username")
    , @NamedQuery(name = "ActividadInfraDiaria.findByCreado", query = "SELECT a FROM ActividadInfraDiaria a WHERE a.creado = :creado")
    , @NamedQuery(name = "ActividadInfraDiaria.findByModificado", query = "SELECT a FROM ActividadInfraDiaria a WHERE a.modificado = :modificado")
    , @NamedQuery(name = "ActividadInfraDiaria.findByEstadoReg", query = "SELECT a FROM ActividadInfraDiaria a WHERE a.estadoReg = :estadoReg")})
public class ActividadInfraDiaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actividad_infra_diaria")
    private Integer idActividadInfraDiaria;
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
    @Size(max = 15)
    @Column(name = "username")
    private String username;
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
    @JoinColumn(name = "id_actividad_infra_tipo", referencedColumnName = "id_actividad_infra_tipo")
    @ManyToOne(fetch = FetchType.LAZY)
    private ActividadInfraTipo idActividadInfraTipo;
    @JoinColumn(name = "id_actividad_infra_tipo_det", referencedColumnName = "id_actividad_infra_tipo_det")
    @ManyToOne(fetch = FetchType.LAZY)
    private ActividadInfraTipoDet idActividadInfraTipoDet;
    @JoinColumn(name = "id_cable_estacion", referencedColumnName = "id_cable_estacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableEstacion idEstacion;
    @JoinColumn(name = "id_seg_pilona", referencedColumnName = "id_seg_pilona")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegPilona idPilona;

    @OneToMany(mappedBy = "idActividadInfraDiaria", fetch = FetchType.LAZY)
    private List<NovedadInfrastruc> novedadInfrastrucList;

    public ActividadInfraDiaria() {
    }

    public ActividadInfraDiaria(Integer idActividadInfraDiaria) {
        this.idActividadInfraDiaria = idActividadInfraDiaria;
    }

    public ActividadInfraDiaria(Integer idActividadInfraDiaria, Date modificado) {
        this.idActividadInfraDiaria = idActividadInfraDiaria;
        this.modificado = modificado;
    }

    public Integer getIdActividadInfraDiaria() {
        return idActividadInfraDiaria;
    }

    public void setIdActividadInfraDiaria(Integer idActividadInfraDiaria) {
        this.idActividadInfraDiaria = idActividadInfraDiaria;
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

    public ActividadInfraTipo getIdActividadInfraTipo() {
        return idActividadInfraTipo;
    }

    public void setIdActividadInfraTipo(ActividadInfraTipo idActividadInfraTipo) {
        this.idActividadInfraTipo = idActividadInfraTipo;
    }

    public ActividadInfraTipoDet getIdActividadInfraTipoDet() {
        return idActividadInfraTipoDet;
    }

    public void setIdActividadInfraTipoDet(ActividadInfraTipoDet idActividadInfraTipoDet) {
        this.idActividadInfraTipoDet = idActividadInfraTipoDet;
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

    @XmlTransient
    public List<NovedadInfrastruc> getnovedadInfrastrucList() {
        return novedadInfrastrucList;
    }

    public void setnovedadInfrastrucList(List<NovedadInfrastruc> novedadInfrastrucList) {
        this.novedadInfrastrucList = novedadInfrastrucList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividadInfraDiaria != null ? idActividadInfraDiaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadInfraDiaria)) {
            return false;
        }
        ActividadInfraDiaria other = (ActividadInfraDiaria) object;
        if ((this.idActividadInfraDiaria == null && other.idActividadInfraDiaria != null) || (this.idActividadInfraDiaria != null && !this.idActividadInfraDiaria.equals(other.idActividadInfraDiaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.ActividadInfraDiaria[ idActividadInfraDiaria=" + idActividadInfraDiaria + " ]";
    }

}
