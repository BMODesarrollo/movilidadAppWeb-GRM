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
 * @author HP
 */
@Entity
@Table(name = "tabla_control_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TablaControlDet.findAll", query = "SELECT t FROM TablaControlDet t")
    , @NamedQuery(name = "TablaControlDet.findByIdTablaControlDet", query = "SELECT t FROM TablaControlDet t WHERE t.idTablaControlDet = :idTablaControlDet")
    , @NamedQuery(name = "TablaControlDet.findByFecha", query = "SELECT t FROM TablaControlDet t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "TablaControlDet.findByTipoDia", query = "SELECT t FROM TablaControlDet t WHERE t.tipoDia = :tipoDia")
    , @NamedQuery(name = "TablaControlDet.findByAsignacion", query = "SELECT t FROM TablaControlDet t WHERE t.asignacion = :asignacion")
    , @NamedQuery(name = "TablaControlDet.findByCodigoTm", query = "SELECT t FROM TablaControlDet t WHERE t.codigoTm = :codigoTm")
    , @NamedQuery(name = "TablaControlDet.findByAmplitud", query = "SELECT t FROM TablaControlDet t WHERE t.amplitud = :amplitud")
    , @NamedQuery(name = "TablaControlDet.findByTiempoProduccion", query = "SELECT t FROM TablaControlDet t WHERE t.tiempoProduccion = :tiempoProduccion")
    , @NamedQuery(name = "TablaControlDet.findByDistanciaProduccion", query = "SELECT t FROM TablaControlDet t WHERE t.distanciaProduccion = :distanciaProduccion")
    , @NamedQuery(name = "TablaControlDet.findByParteTrabajo", query = "SELECT t FROM TablaControlDet t WHERE t.parteTrabajo = :parteTrabajo")
    , @NamedQuery(name = "TablaControlDet.findByTipoTarea", query = "SELECT t FROM TablaControlDet t WHERE t.tipoTarea = :tipoTarea")
    , @NamedQuery(name = "TablaControlDet.findByFromStop", query = "SELECT t FROM TablaControlDet t WHERE t.fromStop = :fromStop")
    , @NamedQuery(name = "TablaControlDet.findByToStop", query = "SELECT t FROM TablaControlDet t WHERE t.toStop = :toStop")
    , @NamedQuery(name = "TablaControlDet.findByHoraInicio", query = "SELECT t FROM TablaControlDet t WHERE t.horaInicio = :horaInicio")
    , @NamedQuery(name = "TablaControlDet.findByHoraFin", query = "SELECT t FROM TablaControlDet t WHERE t.horaFin = :horaFin")
    , @NamedQuery(name = "TablaControlDet.findByDuracion", query = "SELECT t FROM TablaControlDet t WHERE t.duracion = :duracion")
    , @NamedQuery(name = "TablaControlDet.findByDistancia", query = "SELECT t FROM TablaControlDet t WHERE t.distancia = :distancia")
    , @NamedQuery(name = "TablaControlDet.findByServbus", query = "SELECT t FROM TablaControlDet t WHERE t.servbus = :servbus")
    , @NamedQuery(name = "TablaControlDet.findByServicioBase", query = "SELECT t FROM TablaControlDet t WHERE t.servicioBase = :servicioBase")
    , @NamedQuery(name = "TablaControlDet.findByIdVehiculoTipo", query = "SELECT t FROM TablaControlDet t WHERE t.idVehiculoTipo = :idVehiculoTipo")
    , @NamedQuery(name = "TablaControlDet.findByIdLinea", query = "SELECT t FROM TablaControlDet t WHERE t.idLinea = :idLinea")
    , @NamedQuery(name = "TablaControlDet.findByIdRuta", query = "SELECT t FROM TablaControlDet t WHERE t.idRuta = :idRuta")
    , @NamedQuery(name = "TablaControlDet.findByTabla", query = "SELECT t FROM TablaControlDet t WHERE t.tabla = :tabla")
    , @NamedQuery(name = "TablaControlDet.findByViajes", query = "SELECT t FROM TablaControlDet t WHERE t.viajes = :viajes")
    , @NamedQuery(name = "TablaControlDet.findByTrayecto", query = "SELECT t FROM TablaControlDet t WHERE t.trayecto = :trayecto")
    , @NamedQuery(name = "TablaControlDet.findByUsername", query = "SELECT t FROM TablaControlDet t WHERE t.username = :username")
    , @NamedQuery(name = "TablaControlDet.findByCreado", query = "SELECT t FROM TablaControlDet t WHERE t.creado = :creado")
    , @NamedQuery(name = "TablaControlDet.findByModificado", query = "SELECT t FROM TablaControlDet t WHERE t.modificado = :modificado")
    , @NamedQuery(name = "TablaControlDet.findByEstadoReg", query = "SELECT t FROM TablaControlDet t WHERE t.estadoReg = :estadoReg")})
public class TablaControlDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tabla_control_det")
    private Integer idTablaControlDet;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "tipo_dia")
    private Character tipoDia;
    @Size(max = 10)
    @Column(name = "asignacion")
    private String asignacion;
    @Column(name = "codigo_tm")
    private Integer codigoTm;
    @Size(max = 8)
    @Column(name = "amplitud")
    private String amplitud;
    @Size(max = 8)
    @Column(name = "tiempo_produccion")
    private String tiempoProduccion;
    @Column(name = "distancia_produccion")
    private Integer distanciaProduccion;
    @Column(name = "parte_trabajo")
    private Integer parteTrabajo;
    @Size(max = 25)
    @Column(name = "tipo_tarea")
    private String tipoTarea;
    @Size(max = 25)
    @Column(name = "from_stop")
    private String fromStop;
    @Size(max = 25)
    @Column(name = "to_stop")
    private String toStop;
    @Size(max = 8)
    @Column(name = "hora_inicio")
    private String horaInicio;
    @Size(max = 8)
    @Column(name = "hora_fin")
    private String horaFin;
    @Size(max = 8)
    @Column(name = "duracion")
    private String duracion;
    @Column(name = "distancia")
    private Integer distancia;
    @Size(max = 10)
    @Column(name = "servbus")
    private String servbus;
    @Size(max = 10)
    @Column(name = "servicio_base")
    private String servicioBase;
    @Column(name = "id_vehiculo_tipo")
    private Integer idVehiculoTipo;
    @Column(name = "id_linea")
    private Integer idLinea;
    @Column(name = "id_ruta")
    private Integer idRuta;
    @Column(name = "tabla")
    private Integer tabla;
    @Column(name = "viajes")
    private Integer viajes;
    @Size(max = 25)
    @Column(name = "trayecto")
    private String trayecto;
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
    @JoinColumn(name = "id_tabla_control", referencedColumnName = "id_tabla_control")
    @ManyToOne(fetch = FetchType.LAZY)
    private TablaControl idTablaControl;

    public TablaControlDet() {
    }

    public TablaControlDet(Integer idTablaControlDet) {
        this.idTablaControlDet = idTablaControlDet;
    }

    public TablaControlDet(Integer idTablaControlDet, String username, Date creado, int estadoReg) {
        this.idTablaControlDet = idTablaControlDet;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public Integer getIdTablaControlDet() {
        return idTablaControlDet;
    }

    public void setIdTablaControlDet(Integer idTablaControlDet) {
        this.idTablaControlDet = idTablaControlDet;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Character getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(Character tipoDia) {
        this.tipoDia = tipoDia;
    }

    public String getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }

    public Integer getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(Integer codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getAmplitud() {
        return amplitud;
    }

    public void setAmplitud(String amplitud) {
        this.amplitud = amplitud;
    }

    public String getTiempoProduccion() {
        return tiempoProduccion;
    }

    public void setTiempoProduccion(String tiempoProduccion) {
        this.tiempoProduccion = tiempoProduccion;
    }

    public Integer getDistanciaProduccion() {
        return distanciaProduccion;
    }

    public void setDistanciaProduccion(Integer distanciaProduccion) {
        this.distanciaProduccion = distanciaProduccion;
    }

    public Integer getParteTrabajo() {
        return parteTrabajo;
    }

    public void setParteTrabajo(Integer parteTrabajo) {
        this.parteTrabajo = parteTrabajo;
    }

    public String getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(String tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public String getFromStop() {
        return fromStop;
    }

    public void setFromStop(String fromStop) {
        this.fromStop = fromStop;
    }

    public String getToStop() {
        return toStop;
    }

    public void setToStop(String toStop) {
        this.toStop = toStop;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public String getServicioBase() {
        return servicioBase;
    }

    public void setServicioBase(String servicioBase) {
        this.servicioBase = servicioBase;
    }

    public Integer getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(Integer idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    public Integer getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Integer idLinea) {
        this.idLinea = idLinea;
    }

    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }

    public Integer getTabla() {
        return tabla;
    }

    public void setTabla(Integer tabla) {
        this.tabla = tabla;
    }

    public Integer getViajes() {
        return viajes;
    }

    public void setViajes(Integer viajes) {
        this.viajes = viajes;
    }

    public String getTrayecto() {
        return trayecto;
    }

    public void setTrayecto(String trayecto) {
        this.trayecto = trayecto;
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

    public TablaControl getIdTablaControl() {
        return idTablaControl;
    }

    public void setIdTablaControl(TablaControl idTablaControl) {
        this.idTablaControl = idTablaControl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTablaControlDet != null ? idTablaControlDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TablaControlDet)) {
            return false;
        }
        TablaControlDet other = (TablaControlDet) object;
        if ((this.idTablaControlDet == null && other.idTablaControlDet != null) || (this.idTablaControlDet != null && !this.idTablaControlDet.equals(other.idTablaControlDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.TablaControlDet[ idTablaControlDet=" + idTablaControlDet + " ]";
    }
    
}
