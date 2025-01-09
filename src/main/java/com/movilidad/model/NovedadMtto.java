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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "novedad_mtto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadMtto.findAll", query = "SELECT n FROM NovedadMtto n")
    , @NamedQuery(name = "NovedadMtto.findByIdNovedadMtto", query = "SELECT n FROM NovedadMtto n WHERE n.idNovedadMtto = :idNovedadMtto")
    , @NamedQuery(name = "NovedadMtto.findByFechaHoraReg", query = "SELECT n FROM NovedadMtto n WHERE n.fechaHoraReg = :fechaHoraReg")
    , @NamedQuery(name = "NovedadMtto.findByFechaHoraNov", query = "SELECT n FROM NovedadMtto n WHERE n.fechaHoraNov = :fechaHoraNov")
    , @NamedQuery(name = "NovedadMtto.findByDescripcion", query = "SELECT n FROM NovedadMtto n WHERE n.descripcion = :descripcion")
    , @NamedQuery(name = "NovedadMtto.findByPathFotos", query = "SELECT n FROM NovedadMtto n WHERE n.pathFotos = :pathFotos")
    , @NamedQuery(name = "NovedadMtto.findByEstado", query = "SELECT n FROM NovedadMtto n WHERE n.estado = :estado")
    , @NamedQuery(name = "NovedadMtto.findByUserReporta", query = "SELECT n FROM NovedadMtto n WHERE n.userReporta = :userReporta")
    , @NamedQuery(name = "NovedadMtto.findByObsSeguimiento", query = "SELECT n FROM NovedadMtto n WHERE n.obsSeguimiento = :obsSeguimiento")
    , @NamedQuery(name = "NovedadMtto.findByUsrSeguimiento", query = "SELECT n FROM NovedadMtto n WHERE n.usrSeguimiento = :usrSeguimiento")
    , @NamedQuery(name = "NovedadMtto.findByObsCierre", query = "SELECT n FROM NovedadMtto n WHERE n.obsCierre = :obsCierre")
    , @NamedQuery(name = "NovedadMtto.findByUsrCierre", query = "SELECT n FROM NovedadMtto n WHERE n.usrCierre = :usrCierre")
    , @NamedQuery(name = "NovedadMtto.findByFechaHoraCierre", query = "SELECT n FROM NovedadMtto n WHERE n.fechaHoraCierre = :fechaHoraCierre")
    , @NamedQuery(name = "NovedadMtto.findByUsername", query = "SELECT n FROM NovedadMtto n WHERE n.username = :username")
    , @NamedQuery(name = "NovedadMtto.findByCreado", query = "SELECT n FROM NovedadMtto n WHERE n.creado = :creado")
    , @NamedQuery(name = "NovedadMtto.findByModificado", query = "SELECT n FROM NovedadMtto n WHERE n.modificado = :modificado")
    , @NamedQuery(name = "NovedadMtto.findByEstadoReg", query = "SELECT n FROM NovedadMtto n WHERE n.estadoReg = :estadoReg")})
public class NovedadMtto implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNovedadMtto", fetch = FetchType.LAZY)
    private List<NovedadMttoDocs> novedadMttoDocsList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad_mtto")
    private Integer idNovedadMtto;
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
    @JoinColumn(name = "id_cable_cabina", referencedColumnName = "id_cable_cabina")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableCabina idCableCabina;
    @JoinColumn(name = "id_cable_estacion", referencedColumnName = "id_cable_estacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CableEstacion idCableEstacion;
    @JoinColumn(name = "id_novedad_mtto_diaria", referencedColumnName = "id_novedad_mtto_diaria")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadMttoDiaria idNovedadMttoDiaria;
    @JoinColumn(name = "id_novedad_mtto_tipo", referencedColumnName = "id_novedad_mtto_tipo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NovedadMttoTipo idNovedadMttoTipo;
    @JoinColumn(name = "id_novedad_mtto_tipo_det", referencedColumnName = "id_novedad_mtto_tipo_det")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NovedadMttoTipoDet idNovedadMttoTipoDet;
    @JoinColumn(name = "id_seg_pilona", referencedColumnName = "id_seg_pilona")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegPilona idSegPilona;

    public NovedadMtto() {
    }

    public NovedadMtto(Integer idNovedadMtto) {
        this.idNovedadMtto = idNovedadMtto;
    }

    public NovedadMtto(Integer idNovedadMtto, String username, Date creado, int estadoReg) {
        this.idNovedadMtto = idNovedadMtto;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdNovedadMtto() {
        return idNovedadMtto;
    }

    public void setIdNovedadMtto(Integer idNovedadMtto) {
        this.idNovedadMtto = idNovedadMtto;
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

    public CableCabina getIdCableCabina() {
        return idCableCabina;
    }

    public void setIdCableCabina(CableCabina idCableCabina) {
        this.idCableCabina = idCableCabina;
    }

    public CableEstacion getIdCableEstacion() {
        return idCableEstacion;
    }

    public void setIdCableEstacion(CableEstacion idCableEstacion) {
        this.idCableEstacion = idCableEstacion;
    }

    public NovedadMttoDiaria getIdNovedadMttoDiaria() {
        return idNovedadMttoDiaria;
    }

    public void setIdNovedadMttoDiaria(NovedadMttoDiaria idNovedadMttoDiaria) {
        this.idNovedadMttoDiaria = idNovedadMttoDiaria;
    }

    public NovedadMttoTipo getIdNovedadMttoTipo() {
        return idNovedadMttoTipo;
    }

    public void setIdNovedadMttoTipo(NovedadMttoTipo idNovedadMttoTipo) {
        this.idNovedadMttoTipo = idNovedadMttoTipo;
    }

    public NovedadMttoTipoDet getIdNovedadMttoTipoDet() {
        return idNovedadMttoTipoDet;
    }

    public void setIdNovedadMttoTipoDet(NovedadMttoTipoDet idNovedadMttoTipoDet) {
        this.idNovedadMttoTipoDet = idNovedadMttoTipoDet;
    }

    public SegPilona getIdSegPilona() {
        return idSegPilona;
    }

    public void setIdSegPilona(SegPilona idSegPilona) {
        this.idSegPilona = idSegPilona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedadMtto != null ? idNovedadMtto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadMtto)) {
            return false;
        }
        NovedadMtto other = (NovedadMtto) object;
        if ((this.idNovedadMtto == null && other.idNovedadMtto != null) || (this.idNovedadMtto != null && !this.idNovedadMtto.equals(other.idNovedadMtto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.NovedadMtto[ idNovedadMtto=" + idNovedadMtto + " ]";
    }

    @XmlTransient
    public List<NovedadMttoDocs> getNovedadMttoDocsList() {
        return novedadMttoDocsList;
    }

    public void setNovedadMttoDocsList(List<NovedadMttoDocs> novedadMttoDocsList) {
        this.novedadMttoDocsList = novedadMttoDocsList;
    }
    
}
