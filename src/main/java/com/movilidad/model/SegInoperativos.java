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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author solucionesit
 */
@Entity
@Table(name = "seg_inoperativos")
@XmlRootElement
public class SegInoperativos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg_inoperativos")
    private Integer idSegInoperativos;
    @Column(name = "fecha_evento")
    @Temporal(TemporalType.DATE)
    private Date fechaEvento;
    @Column(name = "fecha_notificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaNotificacion;
    @Column(name = "fecha_sancion_indefinida")
    @Temporal(TemporalType.DATE)
    private Date fechaSancionIndefinida;
    @Column(name = "fecha_habilitacion")
    @Temporal(TemporalType.DATE)
    private Date fechaHabilitacion;
    @Column(name = "dias_inoperativos")
    private int diasInoperativos;
    @Column(name = "segunda_fecha_envio_soporte_ente")
    @Temporal(TemporalType.DATE)
    private Date segundafechaEnvioSoporteEnte;
    @Column(name = "fecha_cita_ente")
    @Temporal(TemporalType.DATE)
    private Date fechaCitaEnte;
    @Column(name = "fecha_respuesta_ente")
    @Temporal(TemporalType.DATE)
    private Date fechaRespuestaEnte;
    @Column(name = "primera_fecha_envio_soporte_ente")
    @Temporal(TemporalType.DATE)
    private Date primerafechaEnvioSoporteEnte;
    @Column(name = "mes")
    @Temporal(TemporalType.DATE)
    private Date mes;
    @Column(name = "fecha_sancion")
    @Temporal(TemporalType.DATE)
    private Date fechaSancion;
    @Size(max = 60)
    @Column(name = "quien_reporta")
    private String quienReporta;
    @Lob
    @Size(max = 65535)
    @Column(name = "quien_se_notifico")
    private String quienSeNotifico;
    @Lob
    @Size(max = 65535)
    @Column(name = "calificaion_seg_vial")
    private String calificaionSegVial;
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
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_seg_gestiona_habilitacion", referencedColumnName = "id_seg_gestiona_habilitacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegGestionaHabilitacion idSegGestionaHabilitacion;
    @JoinColumn(name = "id_seg_medio_reporta", referencedColumnName = "id_seg_medio_reporta")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegMedioReporta idSegMedioReporta;
    @JoinColumn(name = "id_seg_tipo_conducta", referencedColumnName = "id_seg_tipo_conducta")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegTipoConducta idSegTipoConducta;
    @JoinColumn(name = "id_seg_tipo_incumplimiento", referencedColumnName = "id_seg_tipo_incumplimiento")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegTipoIncumplimiento idSegTipoIncumplimiento;
    @JoinColumn(name = "id_seg_tipo_respuesta_ente", referencedColumnName = "id_seg_tipo_respuesta_ente")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegTipoRespuestaEnte idSegTipoRespuestaEnte;
    @JoinColumn(name = "id_seg_tipo_sancion", referencedColumnName = "id_seg_tipo_sancion")
    @ManyToOne(fetch = FetchType.LAZY)
    private SegTipoSancion idSegTipoSancion;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSegInoperativos",
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SegActividadInoperativos> segActividadInoperativosList;

    public SegInoperativos() {
    }

    public SegInoperativos(Integer idSegInoperativos) {
        this.idSegInoperativos = idSegInoperativos;
    }

    public Integer getIdSegInoperativos() {
        return idSegInoperativos;
    }

    public void setIdSegInoperativos(Integer idSegInoperativos) {
        this.idSegInoperativos = idSegInoperativos;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public Date getFechaSancionIndefinida() {
        return fechaSancionIndefinida;
    }

    public void setFechaSancionIndefinida(Date fechaSancionIndefinida) {
        this.fechaSancionIndefinida = fechaSancionIndefinida;
    }

    public Date getFechaHabilitacion() {
        return fechaHabilitacion;
    }

    public void setFechaHabilitacion(Date fechaHabilitacion) {
        this.fechaHabilitacion = fechaHabilitacion;
    }

    public int getDiasInoperativos() {
        return diasInoperativos;
    }

    public void setDiasInoperativos(int diasInoperativos) {
        this.diasInoperativos = diasInoperativos;
    }

    public Date getFechaCitaEnte() {
        return fechaCitaEnte;
    }

    public void setFechaCitaEnte(Date fechaCitaEnte) {
        this.fechaCitaEnte = fechaCitaEnte;
    }

    public Date getFechaRespuestaEnte() {
        return fechaRespuestaEnte;
    }

    public void setFechaRespuestaEnte(Date fechaRespuestaEnte) {
        this.fechaRespuestaEnte = fechaRespuestaEnte;
    }

    public Date getSegundafechaEnvioSoporteEnte() {
        return segundafechaEnvioSoporteEnte;
    }

    public void setSegundafechaEnvioSoporteEnte(Date segundafechaEnvioSoporteEnte) {
        this.segundafechaEnvioSoporteEnte = segundafechaEnvioSoporteEnte;
    }

    public Date getPrimerafechaEnvioSoporteEnte() {
        return primerafechaEnvioSoporteEnte;
    }

    public void setPrimerafechaEnvioSoporteEnte(Date primerafechaEnvioSoporteEnte) {
        this.primerafechaEnvioSoporteEnte = primerafechaEnvioSoporteEnte;
    }

    public Date getFechaSancion() {
        return fechaSancion;
    }

    public void setFechaSancion(Date fechaSancion) {
        this.fechaSancion = fechaSancion;
    }

    public String getQuienReporta() {
        return quienReporta;
    }

    public void setQuienReporta(String quienReporta) {
        this.quienReporta = quienReporta;
    }

    public String getQuienSeNotifico() {
        return quienSeNotifico;
    }

    public void setQuienSeNotifico(String quienSeNotifico) {
        this.quienSeNotifico = quienSeNotifico;
    }

    public String getCalificaionSegVial() {
        return calificaionSegVial;
    }

    public void setCalificaionSegVial(String calificaionSegVial) {
        this.calificaionSegVial = calificaionSegVial;
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

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public SegGestionaHabilitacion getIdSegGestionaHabilitacion() {
        return idSegGestionaHabilitacion;
    }

    public void setIdSegGestionaHabilitacion(SegGestionaHabilitacion idSegGestionaHabilitacion) {
        this.idSegGestionaHabilitacion = idSegGestionaHabilitacion;
    }

    public SegMedioReporta getIdSegMedioReporta() {
        return idSegMedioReporta;
    }

    public void setIdSegMedioReporta(SegMedioReporta idSegMedioReporta) {
        this.idSegMedioReporta = idSegMedioReporta;
    }

    public SegTipoConducta getIdSegTipoConducta() {
        return idSegTipoConducta;
    }

    public void setIdSegTipoConducta(SegTipoConducta idSegTipoConducta) {
        this.idSegTipoConducta = idSegTipoConducta;
    }

    public SegTipoIncumplimiento getIdSegTipoIncumplimiento() {
        return idSegTipoIncumplimiento;
    }

    public void setIdSegTipoIncumplimiento(SegTipoIncumplimiento idSegTipoIncumplimiento) {
        this.idSegTipoIncumplimiento = idSegTipoIncumplimiento;
    }

    public SegTipoRespuestaEnte getIdSegTipoRespuestaEnte() {
        return idSegTipoRespuestaEnte;
    }

    public void setIdSegTipoRespuestaEnte(SegTipoRespuestaEnte idSegTipoRespuestaEnte) {
        this.idSegTipoRespuestaEnte = idSegTipoRespuestaEnte;
    }

    public SegTipoSancion getIdSegTipoSancion() {
        return idSegTipoSancion;
    }

    public void setIdSegTipoSancion(SegTipoSancion idSegTipoSancion) {
        this.idSegTipoSancion = idSegTipoSancion;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @XmlTransient
    public List<SegActividadInoperativos> getSegActividadInoperativosList() {
        return segActividadInoperativosList;
    }

    public void setSegActividadInoperativosList(List<SegActividadInoperativos> segActividadInoperativosList) {
        this.segActividadInoperativosList = segActividadInoperativosList;
    }

    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegInoperativos != null ? idSegInoperativos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegInoperativos)) {
            return false;
        }
        SegInoperativos other = (SegInoperativos) object;
        if ((this.idSegInoperativos == null && other.idSegInoperativos != null) || (this.idSegInoperativos != null && !this.idSegInoperativos.equals(other.idSegInoperativos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.SegInoperativos[ idSegInoperativos=" + idSegInoperativos + " ]";
    }

}
