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
 * @author Carlos Ballestas
 */
@Entity
@Table(name = "prg_solicitud_novedad_param")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgSolicitudNovedadParam.findAll", query = "SELECT p FROM PrgSolicitudNovedadParam p"),
    @NamedQuery(name = "PrgSolicitudNovedadParam.findByIdPrgSolicitudNovedadParam", query = "SELECT p FROM PrgSolicitudNovedadParam p WHERE p.idPrgSolicitudNovedadParam = :idPrgSolicitudNovedadParam"),
    @NamedQuery(name = "PrgSolicitudNovedadParam.findByConsecPermisos", query = "SELECT p FROM PrgSolicitudNovedadParam p WHERE p.consecPermisos = :consecPermisos"),
    @NamedQuery(name = "PrgSolicitudNovedadParam.findByConsecCambioTurno", query = "SELECT p FROM PrgSolicitudNovedadParam p WHERE p.consecCambioTurno = :consecCambioTurno"),
    @NamedQuery(name = "PrgSolicitudNovedadParam.findByConsecLicencia", query = "SELECT p FROM PrgSolicitudNovedadParam p WHERE p.consecLicencia = :consecLicencia"),
    @NamedQuery(name = "PrgSolicitudNovedadParam.findByUsername", query = "SELECT p FROM PrgSolicitudNovedadParam p WHERE p.username = :username"),
    @NamedQuery(name = "PrgSolicitudNovedadParam.findByCreado", query = "SELECT p FROM PrgSolicitudNovedadParam p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgSolicitudNovedadParam.findByModificado", query = "SELECT p FROM PrgSolicitudNovedadParam p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgSolicitudNovedadParam.findByEstadoReg", query = "SELECT p FROM PrgSolicitudNovedadParam p WHERE p.estadoReg = :estadoReg")})
public class PrgSolicitudNovedadParam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_solicitud_novedad_param")
    private Integer idPrgSolicitudNovedadParam;
    @Column(name = "consec_permisos")
    private Integer consecPermisos;
    @Column(name = "consec_cambio_turno")
    private Integer consecCambioTurno;
    @Column(name = "consec_licencia")
    private Integer consecLicencia;
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
    @JoinColumn(name = "id_novedad_cambio", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadCambio;
    @JoinColumn(name = "id_novedad_licencia", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadLicencia;
    @JoinColumn(name = "id_novedad_no_firma", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadNoFirma;
    @JoinColumn(name = "id_notificacion_proceso", referencedColumnName = "id_notificacion_proceso")
    @ManyToOne(fetch = FetchType.LAZY)
    private NotificacionProcesos idNotificacionProceso;
    @JoinColumn(name = "id_novedad_permiso", referencedColumnName = "id_novedad_tipo_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private NovedadTipoDetalles idNovedadPermiso;

    public PrgSolicitudNovedadParam() {
    }

    public PrgSolicitudNovedadParam(Integer idPrgSolicitudNovedadParam) {
        this.idPrgSolicitudNovedadParam = idPrgSolicitudNovedadParam;
    }

    public PrgSolicitudNovedadParam(Integer idPrgSolicitudNovedadParam, String username, Date creado, int estadoReg) {
        this.idPrgSolicitudNovedadParam = idPrgSolicitudNovedadParam;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdPrgSolicitudNovedadParam() {
        return idPrgSolicitudNovedadParam;
    }

    public void setIdPrgSolicitudNovedadParam(Integer idPrgSolicitudNovedadParam) {
        this.idPrgSolicitudNovedadParam = idPrgSolicitudNovedadParam;
    }

    public Integer getConsecPermisos() {
        return consecPermisos;
    }

    public void setConsecPermisos(Integer consecPermisos) {
        this.consecPermisos = consecPermisos;
    }

    public Integer getConsecCambioTurno() {
        return consecCambioTurno;
    }

    public void setConsecCambioTurno(Integer consecCambioTurno) {
        this.consecCambioTurno = consecCambioTurno;
    }

    public Integer getConsecLicencia() {
        return consecLicencia;
    }

    public void setConsecLicencia(Integer consecLicencia) {
        this.consecLicencia = consecLicencia;
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

    public NovedadTipoDetalles getIdNovedadCambio() {
        return idNovedadCambio;
    }

    public void setIdNovedadCambio(NovedadTipoDetalles idNovedadCambio) {
        this.idNovedadCambio = idNovedadCambio;
    }

    public NovedadTipoDetalles getIdNovedadLicencia() {
        return idNovedadLicencia;
    }

    public void setIdNovedadLicencia(NovedadTipoDetalles idNovedadLicencia) {
        this.idNovedadLicencia = idNovedadLicencia;
    }

    public NovedadTipoDetalles getIdNovedadNoFirma() {
        return idNovedadNoFirma;
    }

    public void setIdNovedadNoFirma(NovedadTipoDetalles idNovedadNoFirma) {
        this.idNovedadNoFirma = idNovedadNoFirma;
    }

    public NotificacionProcesos getIdNotificacionProceso() {
        return idNotificacionProceso;
    }

    public void setIdNotificacionProceso(NotificacionProcesos idNotificacionProceso) {
        this.idNotificacionProceso = idNotificacionProceso;
    }

    public NovedadTipoDetalles getIdNovedadPermiso() {
        return idNovedadPermiso;
    }

    public void setIdNovedadPermiso(NovedadTipoDetalles idNovedadPermiso) {
        this.idNovedadPermiso = idNovedadPermiso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgSolicitudNovedadParam != null ? idPrgSolicitudNovedadParam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgSolicitudNovedadParam)) {
            return false;
        }
        PrgSolicitudNovedadParam other = (PrgSolicitudNovedadParam) object;
        if ((this.idPrgSolicitudNovedadParam == null && other.idPrgSolicitudNovedadParam != null) || (this.idPrgSolicitudNovedadParam != null && !this.idPrgSolicitudNovedadParam.equals(other.idPrgSolicitudNovedadParam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgSolicitudNovedadParam[ idPrgSolicitudNovedadParam=" + idPrgSolicitudNovedadParam + " ]";
    }
    
}
