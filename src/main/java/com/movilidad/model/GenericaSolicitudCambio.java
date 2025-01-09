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
@Table(name = "generica_solicitud_cambio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GenericaSolicitudCambio.findAll", query = "SELECT g FROM GenericaSolicitudCambio g"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByIdGenericaSolicitudCambio", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.idGenericaSolicitudCambio = :idGenericaSolicitudCambio"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByFecha", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.fecha = :fecha"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByLugarInicioSolicitante", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.lugarInicioSolicitante = :lugarInicioSolicitante"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByLugarFinSolicitante", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.lugarFinSolicitante = :lugarFinSolicitante"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByLugarInicioReemplazo", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.lugarInicioReemplazo = :lugarInicioReemplazo"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByLugarFinReemplazo", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.lugarFinReemplazo = :lugarFinReemplazo"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByTimeOriginSolicitante", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.timeOriginSolicitante = :timeOriginSolicitante"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByTimeDestinySolicitante", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.timeDestinySolicitante = :timeDestinySolicitante"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByTimeOriginReemplazo", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.timeOriginReemplazo = :timeOriginReemplazo"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByTimeDestinyReemplazo", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.timeDestinyReemplazo = :timeDestinyReemplazo"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByCreado", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.creado = :creado"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByModificado", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.modificado = :modificado"),
    @NamedQuery(name = "GenericaSolicitudCambio.findByEstadoReg", query = "SELECT g FROM GenericaSolicitudCambio g WHERE g.estadoReg = :estadoReg")})
public class GenericaSolicitudCambio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generica_solicitud_cambio")
    private Integer idGenericaSolicitudCambio;
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
    @JoinColumn(name = "id_generica_jornada_reemplazo", referencedColumnName = "id_generica_jornada")
    @ManyToOne
    private GenericaJornada idGenericaJornadaReemplazo;
    @JoinColumn(name = "id_generica_solicitud", referencedColumnName = "id_generica_solicitud")
    @ManyToOne
    private GenericaSolicitud idGenericaSolicitud;

    public GenericaSolicitudCambio() {
    }

    public GenericaSolicitudCambio(Integer idGenericaSolicitudCambio) {
        this.idGenericaSolicitudCambio = idGenericaSolicitudCambio;
    }

    public GenericaSolicitudCambio(Integer idGenericaSolicitudCambio, Date creado) {
        this.idGenericaSolicitudCambio = idGenericaSolicitudCambio;
        this.creado = creado;
    }

    public Integer getIdGenericaSolicitudCambio() {
        return idGenericaSolicitudCambio;
    }

    public void setIdGenericaSolicitudCambio(Integer idGenericaSolicitudCambio) {
        this.idGenericaSolicitudCambio = idGenericaSolicitudCambio;
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

    public GenericaJornada getIdGenericaJornadaReemplazo() {
        return idGenericaJornadaReemplazo;
    }

    public void setIdGenericaJornadaReemplazo(GenericaJornada idGenericaJornadaReemplazo) {
        this.idGenericaJornadaReemplazo = idGenericaJornadaReemplazo;
    }

    public GenericaSolicitud getIdGenericaSolicitud() {
        return idGenericaSolicitud;
    }

    public void setIdGenericaSolicitud(GenericaSolicitud idGenericaSolicitud) {
        this.idGenericaSolicitud = idGenericaSolicitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenericaSolicitudCambio != null ? idGenericaSolicitudCambio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenericaSolicitudCambio)) {
            return false;
        }
        GenericaSolicitudCambio other = (GenericaSolicitudCambio) object;
        if ((this.idGenericaSolicitudCambio == null && other.idGenericaSolicitudCambio != null) || (this.idGenericaSolicitudCambio != null && !this.idGenericaSolicitudCambio.equals(other.idGenericaSolicitudCambio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.GenericaSolicitudCambio[ idGenericaSolicitudCambio=" + idGenericaSolicitudCambio + " ]";
    }
    
}
