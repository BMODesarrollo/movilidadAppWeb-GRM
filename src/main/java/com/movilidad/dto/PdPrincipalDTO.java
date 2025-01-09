/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Julián Arévalo
 */
public class PdPrincipalDTO implements Serializable {

    @Column(name = "id_pd_maestro")
    private Integer idPdMaestro;
    @Column(name = "fecha_apertura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaApertura;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Column(name = "comentarios")
    private String comentarios;
    @Column(name = "usuario_apertura")
    private String usuarioApertura;
    @Column(name = "usuario_cierre")
    private String usuarioCierre;
    @Column(name = "id_pd_estado_proceso")
    private int idPdEstadoProceso;
    @Column(name = "id_pd_tipo_sancion")
    private int idPdTipoSancion;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private int estadoReg;
    @Column(name = "fecha_citacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCitacion;
    @Column(name = "id_empleado")
    private int idEmpleado;
    @Column(name = "fecha_inicio_sancion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioSancion;
    @Column(name = "fecha_fin_sancion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinSancion;
    @Column(name = "id_responsable")
    private Integer idResponsable;
    @Column(name = "fecha_apertura_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAperturaDate;
    @Column(name = "fecha_cierre_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierreDate;
    @Column(name = "fecha_citacion_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCitacionDate;
    @Column(name = "codigo_tm")
    private String codigoTm;
    @Column(name = "estado_proceso")
    private String estadoProceso;
    @Column(name = "tipo_sancion")
    private String tipoSancion;
    @Column(name = "usuario_responsable")
    private String usuarioResponsable;
    @Column(name = "identificacion")
    private BigInteger identificacion;
    @Column(name = "asiste")
    private Integer estadoAsistencia;
    @Column(name = "id_empleado_estado")
    private Integer estadoEmpleado;

    public PdPrincipalDTO(Integer idPdMaestro) {
        this.idPdMaestro = idPdMaestro;
    }

    public PdPrincipalDTO() {
    }

    public PdPrincipalDTO(Integer idPdMaestro, Date fechaApertura, Date fechaCierre, String comentarios, String usuarioApertura, String usuarioCierre, int idPdEstadoProceso, int idPdTipoSancion, Date creado, Date modificado, int estadoReg, Date fechaCitacion, int idEmpleado, Date fechaInicioSancion, Date fechaFinSancion, Integer idResponsable, Date fechaAperturaDate, Date fechaCierreDate, Date fechaCitacionDate, String codigoTm, String estadoProceso, String tipoSancion, String usuarioResponsable, BigInteger identificacion, Integer estadoAsistencia, Integer estadoEmpleado) {
        this.idPdMaestro = idPdMaestro;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.comentarios = comentarios;
        this.usuarioApertura = usuarioApertura;
        this.usuarioCierre = usuarioCierre;
        this.idPdEstadoProceso = idPdEstadoProceso;
        this.idPdTipoSancion = idPdTipoSancion;
        this.creado = creado;
        this.modificado = modificado;
        this.estadoReg = estadoReg;
        this.fechaCitacion = fechaCitacion;
        this.idEmpleado = idEmpleado;
        this.fechaInicioSancion = fechaInicioSancion;
        this.fechaFinSancion = fechaFinSancion;
        this.idResponsable = idResponsable;
        this.fechaAperturaDate = fechaAperturaDate;
        this.fechaCierreDate = fechaCierreDate;
        this.fechaCitacionDate = fechaCitacionDate;
        this.codigoTm = codigoTm;
        this.estadoProceso = estadoProceso;
        this.tipoSancion = tipoSancion;
        this.usuarioResponsable = usuarioResponsable;
        this.identificacion = identificacion;
        this.estadoAsistencia = estadoAsistencia;
        this.estadoEmpleado = estadoEmpleado;
    }  

    public Integer getIdPdMaestro() {
        return idPdMaestro;
    }

    public void setIdPdMaestro(Integer idPdMaestro) {
        this.idPdMaestro = idPdMaestro;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getUsuarioApertura() {
        return usuarioApertura;
    }

    public void setUsuarioApertura(String usuarioApertura) {
        this.usuarioApertura = usuarioApertura;
    }

    public String getUsuarioCierre() {
        return usuarioCierre;
    }

    public void setUsuarioCierre(String usuarioCierre) {
        this.usuarioCierre = usuarioCierre;
    }

    public int getIdPdEstadoProceso() {
        return idPdEstadoProceso;
    }

    public void setIdPdEstadoProceso(int idPdEstadoProceso) {
        this.idPdEstadoProceso = idPdEstadoProceso;
    }

    public int getIdPdTipoSancion() {
        return idPdTipoSancion;
    }

    public void setIdPdTipoSancion(int idPdTipoSancion) {
        this.idPdTipoSancion = idPdTipoSancion;
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

    public Date getFechaCitacion() {
        return fechaCitacion;
    }

    public void setFechaCitacion(Date fechaCitacion) {
        this.fechaCitacion = fechaCitacion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaInicioSancion() {
        return fechaInicioSancion;
    }

    public void setFechaInicioSancion(Date fechaInicioSancion) {
        this.fechaInicioSancion = fechaInicioSancion;
    }

    public Date getFechaFinSancion() {
        return fechaFinSancion;
    }

    public void setFechaFinSancion(Date fechaFinSancion) {
        this.fechaFinSancion = fechaFinSancion;
    }

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
    }

    public Date getFechaAperturaDate() {
        return fechaAperturaDate;
    }

    public void setFechaAperturaDate(Date fechaAperturaDate) {
        this.fechaAperturaDate = fechaAperturaDate;
    }

    public Date getFechaCierreDate() {
        return fechaCierreDate;
    }

    public void setFechaCierreDate(Date fechaCierreDate) {
        this.fechaCierreDate = fechaCierreDate;
    }

    public Date getFechaCitacionDate() {
        return fechaCitacionDate;
    }

    public void setFechaCitacionDate(Date fechaCitacionDate) {
        this.fechaCitacionDate = fechaCitacionDate;
    }

    public String getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(String codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public String getTipoSancion() {
        return tipoSancion;
    }

    public void setTipoSancion(String tipoSancion) {
        this.tipoSancion = tipoSancion;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public BigInteger getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(BigInteger identificacion) {
        this.identificacion = identificacion;
    }

    public Integer getEstadoAsistencia() {
        return estadoAsistencia;
    }

    public void setEstadoAsistencia(Integer estadoAsistencia) {
        this.estadoAsistencia = estadoAsistencia;
    }

    public Integer getEstadoEmpleado() {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(Integer estadoEmpleado) {
        this.estadoEmpleado = estadoEmpleado;
    }
}
