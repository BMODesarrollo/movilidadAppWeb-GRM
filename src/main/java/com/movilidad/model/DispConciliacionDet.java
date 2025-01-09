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
@Table(name = "disp_conciliacion_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DispConciliacionDet.findAll", query = "SELECT d FROM DispConciliacionDet d"),
    @NamedQuery(name = "DispConciliacionDet.findByIdDispConciliacionDet", query = "SELECT d FROM DispConciliacionDet d WHERE d.idDispConciliacionDet = :idDispConciliacionDet"),
    @NamedQuery(name = "DispConciliacionDet.findByFecha", query = "SELECT d FROM DispConciliacionDet d WHERE d.fecha = :fecha"),
    @NamedQuery(name = "DispConciliacionDet.findByHora", query = "SELECT d FROM DispConciliacionDet d WHERE d.hora = :hora"),
    @NamedQuery(name = "DispConciliacionDet.findByUsuarioReporta", query = "SELECT d FROM DispConciliacionDet d WHERE d.usuarioReporta = :usuarioReporta"),
    @NamedQuery(name = "DispConciliacionDet.findByUbicacion", query = "SELECT d FROM DispConciliacionDet d WHERE d.ubicacion = :ubicacion"),
    @NamedQuery(name = "DispConciliacionDet.findByUsername", query = "SELECT d FROM DispConciliacionDet d WHERE d.username = :username"),
    @NamedQuery(name = "DispConciliacionDet.findByCreado", query = "SELECT d FROM DispConciliacionDet d WHERE d.creado = :creado"),
    @NamedQuery(name = "DispConciliacionDet.findByModificado", query = "SELECT d FROM DispConciliacionDet d WHERE d.modificado = :modificado"),
    @NamedQuery(name = "DispConciliacionDet.findByEstadoReg", query = "SELECT d FROM DispConciliacionDet d WHERE d.estadoReg = :estadoReg")})
public class DispConciliacionDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disp_conciliacion_det")
    private Integer idDispConciliacionDet;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 8)
    @Column(name = "hora")
    private String hora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario_reporta")
    private String usuarioReporta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ubicacion")
    private int ubicacion;
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
    @Column(name = "fecha_habilitacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHabilitacion;
    @JoinColumn(name = "id_disp_causa_entrada", referencedColumnName = "id_disp_causa_entrada")
    @ManyToOne(fetch = FetchType.LAZY)
    private DispCausaEntrada idDispCausaEntrada;
    @JoinColumn(name = "id_disp_conciliacion", referencedColumnName = "id_disp_conciliacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private DispConciliacion idDispConciliacion;
    @JoinColumn(name = "id_disp_estado_pend_actual", referencedColumnName = "id_disp_estado_pend_actual")
    @ManyToOne(fetch = FetchType.LAZY)
    private DispEstadoPendActual idDispEstadoPendActual;
    @JoinColumn(name = "id_disp_sistema", referencedColumnName = "id_disp_sistema")
    @ManyToOne(fetch = FetchType.LAZY)
    private DispSistema idDispSistema;
    @JoinColumn(name = "id_gop_unidad_funcional", referencedColumnName = "id_gop_unidad_funcional")
    @ManyToOne(fetch = FetchType.LAZY)
    private GopUnidadFuncional idGopUnidadFuncional;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehiculo idVehiculo;
    @JoinColumn(name = "id_vehiculo_tipo_estado", referencedColumnName = "id_vehiculo_tipo_estado")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipoEstado idVehiculoTipoEstado;
    @JoinColumn(name = "id_vehiculo_tipo_estado_det", referencedColumnName = "id_vehiculo_tipo_estado_det")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehiculoTipoEstadoDet idVehiculoTipoEstadoDet;

    public DispConciliacionDet() {
    }

    public DispConciliacionDet(Integer idDispConciliacionDet) {
        this.idDispConciliacionDet = idDispConciliacionDet;
    }

    public DispConciliacionDet(Integer idDispConciliacionDet, String usuarioReporta, String username, Date creado, int estadoReg) {
        this.idDispConciliacionDet = idDispConciliacionDet;
        this.usuarioReporta = usuarioReporta;
        this.username = username;
        this.creado = creado;
        this.estadoReg = estadoReg;
    }

    public DispConciliacionDet(Vehiculo idVehiculo, VehiculoTipoEstado idVehiculoTipoEstado,
            VehiculoTipoEstadoDet idVehiculoTipoEstadoDet,
            GopUnidadFuncional idGopUnidadFuncional,
            DispSistema idDispSistema,
            DispCausaEntrada idDispCausaEntrada,
            DispEstadoPendActual idDispEstadoPendActual,
            Date fecha,
            String hora,
            String usuarioReporta,
            Date fechaHabilitacion, int enVia) {
        this.fecha = fecha;
        this.hora = hora;
        this.usuarioReporta = usuarioReporta;
        this.idDispCausaEntrada = idDispCausaEntrada;
        this.idDispEstadoPendActual = idDispEstadoPendActual;
        this.idDispSistema = idDispSistema;
        this.idGopUnidadFuncional = idGopUnidadFuncional;
        this.idVehiculo = idVehiculo;
        this.idVehiculoTipoEstado = idVehiculoTipoEstado;
        this.idVehiculoTipoEstadoDet = idVehiculoTipoEstadoDet;
        this.fechaHabilitacion = fechaHabilitacion;
        this.ubicacion = enVia;
    }

    public Integer getIdDispConciliacionDet() {
        return idDispConciliacionDet;
    }

    public void setIdDispConciliacionDet(Integer idDispConciliacionDet) {
        this.idDispConciliacionDet = idDispConciliacionDet;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUsuarioReporta() {
        return usuarioReporta;
    }

    public void setUsuarioReporta(String usuarioReporta) {
        this.usuarioReporta = usuarioReporta;
    }

    public int getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(int ubicacion) {
        this.ubicacion = ubicacion;
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

    public DispCausaEntrada getIdDispCausaEntrada() {
        return idDispCausaEntrada;
    }

    public void setIdDispCausaEntrada(DispCausaEntrada idDispCausaEntrada) {
        this.idDispCausaEntrada = idDispCausaEntrada;
    }

    public DispConciliacion getIdDispConciliacion() {
        return idDispConciliacion;
    }

    public void setIdDispConciliacion(DispConciliacion idDispConciliacion) {
        this.idDispConciliacion = idDispConciliacion;
    }

    public DispEstadoPendActual getIdDispEstadoPendActual() {
        return idDispEstadoPendActual;
    }

    public void setIdDispEstadoPendActual(DispEstadoPendActual idDispEstadoPendActual) {
        this.idDispEstadoPendActual = idDispEstadoPendActual;
    }

    public DispSistema getIdDispSistema() {
        return idDispSistema;
    }

    public void setIdDispSistema(DispSistema idDispSistema) {
        this.idDispSistema = idDispSistema;
    }

    public GopUnidadFuncional getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(GopUnidadFuncional idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public VehiculoTipoEstado getIdVehiculoTipoEstado() {
        return idVehiculoTipoEstado;
    }

    public void setIdVehiculoTipoEstado(VehiculoTipoEstado idVehiculoTipoEstado) {
        this.idVehiculoTipoEstado = idVehiculoTipoEstado;
    }

    public VehiculoTipoEstadoDet getIdVehiculoTipoEstadoDet() {
        return idVehiculoTipoEstadoDet;
    }

    public void setIdVehiculoTipoEstadoDet(VehiculoTipoEstadoDet idVehiculoTipoEstadoDet) {
        this.idVehiculoTipoEstadoDet = idVehiculoTipoEstadoDet;
    }

    public Date getFechaHabilitacion() {
        return fechaHabilitacion;
    }

    public void setFechaHabilitacion(Date fechaHabilitacion) {
        this.fechaHabilitacion = fechaHabilitacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDispConciliacionDet != null ? idDispConciliacionDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DispConciliacionDet)) {
            return false;
        }
        DispConciliacionDet other = (DispConciliacionDet) object;
        if ((this.idDispConciliacionDet == null && other.idDispConciliacionDet != null) || (this.idDispConciliacionDet != null && !this.idDispConciliacionDet.equals(other.idDispConciliacionDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.DispConciliacionDet[ idDispConciliacionDet=" + idDispConciliacionDet + " ]";
    }

}
