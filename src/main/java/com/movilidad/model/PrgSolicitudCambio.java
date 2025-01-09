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
@Table(name = "prg_solicitud_cambio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrgSolicitudCambio.findAll", query = "SELECT p FROM PrgSolicitudCambio p"),
    @NamedQuery(name = "PrgSolicitudCambio.findByIdPrgSolicitudCambio", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.idPrgSolicitudCambio = :idPrgSolicitudCambio"),
    @NamedQuery(name = "PrgSolicitudCambio.findByFecha", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "PrgSolicitudCambio.findByLugarInicioSolicitante", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.lugarInicioSolicitante = :lugarInicioSolicitante"),
    @NamedQuery(name = "PrgSolicitudCambio.findByLugarFinSolicitante", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.lugarFinSolicitante = :lugarFinSolicitante"),
    @NamedQuery(name = "PrgSolicitudCambio.findByLugarInicioReemplazo", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.lugarInicioReemplazo = :lugarInicioReemplazo"),
    @NamedQuery(name = "PrgSolicitudCambio.findByLugarFinReemplazo", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.lugarFinReemplazo = :lugarFinReemplazo"),
    @NamedQuery(name = "PrgSolicitudCambio.findByTimeOriginSolicitante", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.timeOriginSolicitante = :timeOriginSolicitante"),
    @NamedQuery(name = "PrgSolicitudCambio.findByTimeDestinySolicitante", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.timeDestinySolicitante = :timeDestinySolicitante"),
    @NamedQuery(name = "PrgSolicitudCambio.findByTimeOriginReemplazo", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.timeOriginReemplazo = :timeOriginReemplazo"),
    @NamedQuery(name = "PrgSolicitudCambio.findByTimeDestinyReemplazo", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.timeDestinyReemplazo = :timeDestinyReemplazo"),
    @NamedQuery(name = "PrgSolicitudCambio.findByCreado", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.creado = :creado"),
    @NamedQuery(name = "PrgSolicitudCambio.findByModificado", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.modificado = :modificado"),
    @NamedQuery(name = "PrgSolicitudCambio.findByEstadoReg", query = "SELECT p FROM PrgSolicitudCambio p WHERE p.estadoReg = :estadoReg")})
public class PrgSolicitudCambio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prg_solicitud_cambio")
    private Integer idPrgSolicitudCambio;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 40)
    @Column(name = "lugar_inicio_solicitante")
    private String lugarInicioSolicitante;
    @Size(max = 40)
    @Column(name = "lugar_fin_solicitante")
    private String lugarFinSolicitante;
    @Size(max = 40)
    @Column(name = "lugar_inicio_reemplazo")
    private String lugarInicioReemplazo;
    @Size(max = 40)
    @Column(name = "lugar_fin_reemplazo")
    private String lugarFinReemplazo;
    @Size(max = 8)
    @Column(name = "time_origin_solicitante")
    private String timeOriginSolicitante;
    @Size(max = 8)
    @Column(name = "time_destiny_solicitante")
    private String timeDestinySolicitante;
    @Size(max = 8)
    @Column(name = "time_origin_reemplazo")
    private String timeOriginReemplazo;
    @Size(max = 8)
    @Column(name = "time_destiny_reemplazo")
    private String timeDestinyReemplazo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @JoinColumn(name = "id_prg_sercon_reemplazo", referencedColumnName = "id_prg_sercon")
    @ManyToOne
    private PrgSercon idPrgSerconReemplazo;
    @JoinColumn(name = "id_prg_solicitud", referencedColumnName = "id_prg_solicitud")
    @ManyToOne
    private PrgSolicitud idPrgSolicitud;

    public PrgSolicitudCambio() {
    }

    public PrgSolicitudCambio(Integer idPrgSolicitudCambio) {
        this.idPrgSolicitudCambio = idPrgSolicitudCambio;
    }

    public PrgSolicitudCambio(Integer idPrgSolicitudCambio, Date creado) {
        this.idPrgSolicitudCambio = idPrgSolicitudCambio;
        this.creado = creado;
    }

    public Integer getIdPrgSolicitudCambio() {
        return idPrgSolicitudCambio;
    }

    public void setIdPrgSolicitudCambio(Integer idPrgSolicitudCambio) {
        this.idPrgSolicitudCambio = idPrgSolicitudCambio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getLugarInicioSolicitante() {
        return lugarInicioSolicitante;
    }

    public void setLugarInicioSolicitante(String lugarInicioSolicitante) {
        this.lugarInicioSolicitante = lugarInicioSolicitante;
    }

    public String getLugarFinSolicitante() {
        return lugarFinSolicitante;
    }

    public void setLugarFinSolicitante(String lugarFinSolicitante) {
        this.lugarFinSolicitante = lugarFinSolicitante;
    }

    public String getLugarInicioReemplazo() {
        return lugarInicioReemplazo;
    }

    public void setLugarInicioReemplazo(String lugarInicioReemplazo) {
        this.lugarInicioReemplazo = lugarInicioReemplazo;
    }

    public String getLugarFinReemplazo() {
        return lugarFinReemplazo;
    }

    public void setLugarFinReemplazo(String lugarFinReemplazo) {
        this.lugarFinReemplazo = lugarFinReemplazo;
    }

    public String getTimeOriginSolicitante() {
        return timeOriginSolicitante;
    }

    public void setTimeOriginSolicitante(String timeOriginSolicitante) {
        this.timeOriginSolicitante = timeOriginSolicitante;
    }

    public String getTimeDestinySolicitante() {
        return timeDestinySolicitante;
    }

    public void setTimeDestinySolicitante(String timeDestinySolicitante) {
        this.timeDestinySolicitante = timeDestinySolicitante;
    }

    public String getTimeOriginReemplazo() {
        return timeOriginReemplazo;
    }

    public void setTimeOriginReemplazo(String timeOriginReemplazo) {
        this.timeOriginReemplazo = timeOriginReemplazo;
    }

    public String getTimeDestinyReemplazo() {
        return timeDestinyReemplazo;
    }

    public void setTimeDestinyReemplazo(String timeDestinyReemplazo) {
        this.timeDestinyReemplazo = timeDestinyReemplazo;
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

    public PrgSercon getIdPrgSerconReemplazo() {
        return idPrgSerconReemplazo;
    }

    public void setIdPrgSerconReemplazo(PrgSercon idPrgSerconReemplazo) {
        this.idPrgSerconReemplazo = idPrgSerconReemplazo;
    }

    public PrgSolicitud getIdPrgSolicitud() {
        return idPrgSolicitud;
    }

    public void setIdPrgSolicitud(PrgSolicitud idPrgSolicitud) {
        this.idPrgSolicitud = idPrgSolicitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrgSolicitudCambio != null ? idPrgSolicitudCambio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrgSolicitudCambio)) {
            return false;
        }
        PrgSolicitudCambio other = (PrgSolicitudCambio) object;
        if ((this.idPrgSolicitudCambio == null && other.idPrgSolicitudCambio != null) || (this.idPrgSolicitudCambio != null && !this.idPrgSolicitudCambio.equals(other.idPrgSolicitudCambio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.PrgSolicitudCambio[ idPrgSolicitudCambio=" + idPrgSolicitudCambio + " ]";
    }
    
}
