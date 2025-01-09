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
import javax.persistence.Lob;
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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "sst_autorizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SstAutorizacion.findAll", query = "SELECT s FROM SstAutorizacion s"),
    @NamedQuery(name = "SstAutorizacion.findByIdSstAutorizacion", query = "SELECT s FROM SstAutorizacion s WHERE s.idSstAutorizacion = :idSstAutorizacion"),
    @NamedQuery(name = "SstAutorizacion.findByFechaHoraLlegada", query = "SELECT s FROM SstAutorizacion s WHERE s.fechaHoraLlegada = :fechaHoraLlegada"),
    @NamedQuery(name = "SstAutorizacion.findByFechaHoraSalida", query = "SELECT s FROM SstAutorizacion s WHERE s.fechaHoraSalida = :fechaHoraSalida"),
    @NamedQuery(name = "SstAutorizacion.findByVisitaActiva", query = "SELECT s FROM SstAutorizacion s WHERE s.visitaActiva = :visitaActiva"),
    @NamedQuery(name = "SstAutorizacion.findByVisitaAprobada", query = "SELECT s FROM SstAutorizacion s WHERE s.visitaAprobada = :visitaAprobada"),
    @NamedQuery(name = "SstAutorizacion.findByUserAprueba", query = "SELECT s FROM SstAutorizacion s WHERE s.userAprueba = :userAprueba"),
    @NamedQuery(name = "SstAutorizacion.findByFechaAprobacion", query = "SELECT s FROM SstAutorizacion s WHERE s.fechaAprobacion = :fechaAprobacion"),
    @NamedQuery(name = "SstAutorizacion.findByIngreso", query = "SELECT s FROM SstAutorizacion s WHERE s.ingreso = :ingreso"),
    @NamedQuery(name = "SstAutorizacion.findBySalio", query = "SELECT s FROM SstAutorizacion s WHERE s.salio = :salio"),
    @NamedQuery(name = "SstAutorizacion.findByUsername", query = "SELECT s FROM SstAutorizacion s WHERE s.username = :username"),
    @NamedQuery(name = "SstAutorizacion.findByCreado", query = "SELECT s FROM SstAutorizacion s WHERE s.creado = :creado"),
    @NamedQuery(name = "SstAutorizacion.findByModificado", query = "SELECT s FROM SstAutorizacion s WHERE s.modificado = :modificado"),
    @NamedQuery(name = "SstAutorizacion.findByEstadoReg", query = "SELECT s FROM SstAutorizacion s WHERE s.estadoReg = :estadoReg")})
public class SstAutorizacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sst_autorizacion")
    private Integer idSstAutorizacion;
    @Column(name = "fecha_hora_llegada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraLlegada;
    @Column(name = "fecha_hora_salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraSalida;
    @Lob
    @Size(max = 65535)
    @Column(name = "motivo_visita")
    private String motivoVisita;
    @Basic(optional = false)
    @Column(name = "visita_activa")
    private int visitaActiva;
    @Column(name = "visita_aprobada")
    private Integer visitaAprobada;
    @Basic(optional = false)
    @Size(min = 1, max = 15)
    @Column(name = "user_aprueba")
    private String userAprueba;
    @Column(name = "fecha_aprobacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAprobacion;
    @Basic(optional = false)
    @Column(name = "ingreso")
    private int ingreso;
    @Basic(optional = false)
    @Column(name = "salio")
    private int salio;
    @Basic(optional = false)
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
    @JoinColumn(name = "id_responsable", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idResponsable;
    @JoinColumn(name = "id_sst_area_empresa", referencedColumnName = "id_sst_area_empresa")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstAreaEmpresa idSstAreaEmpresa;
    @JoinColumn(name = "id_sst_empresa", referencedColumnName = "id_sst_empresa")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstEmpresa idSstEmpresa;
    @JoinColumn(name = "id_sst_empresa_visitante", referencedColumnName = "id_sst_empresa_visitante")
    @ManyToOne(fetch = FetchType.LAZY)
    private SstEmpresaVisitante idSstEmpresaVisitante;
    @JoinColumn(name = "id_sst_es_mat_equi", referencedColumnName = "id_sst_es_mat_equi")
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private SstEsMatEqui idSstEsMatEqui;
    @JoinColumn(name = "id_sst_tipo_labor", referencedColumnName = "id_sst_tipo_labor")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SstTipoLabor idSstTipoLabor;

    public SstAutorizacion() {
    }

    public SstAutorizacion(Integer idSstAutorizacion) {
        this.idSstAutorizacion = idSstAutorizacion;
    }

    public SstAutorizacion(Integer idSstAutorizacion, int visitaActiva, String userAprueba, int ingreso, int salio, String username) {
        this.idSstAutorizacion = idSstAutorizacion;
        this.visitaActiva = visitaActiva;
        this.userAprueba = userAprueba;
        this.ingreso = ingreso;
        this.salio = salio;
        this.username = username;
    }

    public Integer getIdSstAutorizacion() {
        return idSstAutorizacion;
    }

    public void setIdSstAutorizacion(Integer idSstAutorizacion) {
        this.idSstAutorizacion = idSstAutorizacion;
    }

    public Date getFechaHoraLlegada() {
        return fechaHoraLlegada;
    }

    public void setFechaHoraLlegada(Date fechaHoraLlegada) {
        this.fechaHoraLlegada = fechaHoraLlegada;
    }

    public Date getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(Date fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public String getMotivoVisita() {
        return motivoVisita;
    }

    public void setMotivoVisita(String motivoVisita) {
        this.motivoVisita = motivoVisita;
    }

    public int getVisitaActiva() {
        return visitaActiva;
    }

    public void setVisitaActiva(int visitaActiva) {
        this.visitaActiva = visitaActiva;
    }

    public Integer getVisitaAprobada() {
        return visitaAprobada;
    }

    public void setVisitaAprobada(Integer visitaAprobada) {
        this.visitaAprobada = visitaAprobada;
    }

    public String getUserAprueba() {
        return userAprueba;
    }

    public void setUserAprueba(String userAprueba) {
        this.userAprueba = userAprueba;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public int getIngreso() {
        return ingreso;
    }

    public void setIngreso(int ingreso) {
        this.ingreso = ingreso;
    }

    public int getSalio() {
        return salio;
    }

    public void setSalio(int salio) {
        this.salio = salio;
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

    public Empleado getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Empleado idResponsable) {
        this.idResponsable = idResponsable;
    }

    public SstAreaEmpresa getIdSstAreaEmpresa() {
        return idSstAreaEmpresa;
    }

    public void setIdSstAreaEmpresa(SstAreaEmpresa idSstAreaEmpresa) {
        this.idSstAreaEmpresa = idSstAreaEmpresa;
    }

    public SstEmpresa getIdSstEmpresa() {
        return idSstEmpresa;
    }

    public void setIdSstEmpresa(SstEmpresa idSstEmpresa) {
        this.idSstEmpresa = idSstEmpresa;
    }

    public SstEmpresaVisitante getIdSstEmpresaVisitante() {
        return idSstEmpresaVisitante;
    }

    public void setIdSstEmpresaVisitante(SstEmpresaVisitante idSstEmpresaVisitante) {
        this.idSstEmpresaVisitante = idSstEmpresaVisitante;
    }

    public SstEsMatEqui getIdSstEsMatEqui() {
        return idSstEsMatEqui;
    }

    public void setIdSstEsMatEqui(SstEsMatEqui idSstEsMatEqui) {
        this.idSstEsMatEqui = idSstEsMatEqui;
    }

    public SstTipoLabor getIdSstTipoLabor() {
        return idSstTipoLabor;
    }

    public void setIdSstTipoLabor(SstTipoLabor idSstTipoLabor) {
        this.idSstTipoLabor = idSstTipoLabor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSstAutorizacion != null ? idSstAutorizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SstAutorizacion)) {
            return false;
        }
        SstAutorizacion other = (SstAutorizacion) object;
        if ((this.idSstAutorizacion == null && other.idSstAutorizacion != null) || (this.idSstAutorizacion != null && !this.idSstAutorizacion.equals(other.idSstAutorizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SstAutorizacion[ idSstAutorizacion=" + idSstAutorizacion + " ]";
    }
    
}
