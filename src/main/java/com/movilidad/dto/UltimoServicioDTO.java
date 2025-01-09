/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class UltimoServicioDTO implements Serializable {

    //Fecha solicitada
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "nombre_uf")
    private String nombreUf;
    //Número de servbus
    @Column(name = "servbus")
    private String servbus;
    /**
     * Contiene el detalle del ultimo servicio programado por servbus.
     */
    @Column(name = "detalle_servicio")
    private String detalleServicio;
    //hora de entrada de la tabla
    @Column(name = "hora_entrada")
    private String horaEntrada;
    //Lugar de entrada (centro logístico)
    @Column(name = "lugar_entrada")
    private String lugarEntrada;
    //Definir si es Larga o partida
    private String tipoTabla;
    //Número de sercón
    private String sercon;
    //Código operador 
    private String codigoTm;
    //Nombres operador
    private String nombreOperador;
    //Apellidos operador
    private String apellidoOperador;
    //Vehículo asignado
    private String vehiculo;
    //Ruta asignada
    private String ruta;
    //Número de tabla
    private String tabla;
    //Hora real de ingreso de acuerdo a validación my móvil
    @Column(name = "hora_real_ingreso")
    private String horaRealIngreso;
    //Resta hora real menos hora programada, ingreso del vehículo
    private String desviacion;
    //id_prg_tc programado como ultima tabla.
    private Integer idPrgTc;

    public UltimoServicioDTO() {
    }

    public UltimoServicioDTO(Date fecha, String nombreUf, String servbus, String lugarEntrada, Integer idPrgTc, String horaEntrada, String detalleServicio, String horaRealIngreso) {
        this.fecha = fecha;
        this.nombreUf = nombreUf;
        this.servbus = servbus;
        this.lugarEntrada = lugarEntrada;
        this.horaEntrada = horaEntrada;
        this.detalleServicio = detalleServicio;
        this.horaRealIngreso = horaRealIngreso;
        this.idPrgTc = idPrgTc;
        if (this.detalleServicio != null) {
            String[] parts = this.detalleServicio.split("#");
            this.sercon = parts[0];
            this.codigoTm = parts[1];
            this.nombreOperador = parts[2];
            this.apellidoOperador = parts[3];
            this.ruta = parts[4];
            this.tabla = parts[5];
            this.vehiculo = parts[6];
        }
        this.desviacion = this.horaRealIngreso == null ? null : MovilidadUtil.toTimeSec(MovilidadUtil.toSecs(this.horaRealIngreso) - MovilidadUtil.toSecs(this.horaEntrada));
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreUf() {
        return nombreUf;
    }

    public void setNombreUf(String nombreUf) {
        this.nombreUf = nombreUf;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public String getDetalleServicio() {
        return detalleServicio;
    }

    public void setDetalleServicio(String detalleServicio) {
        this.detalleServicio = detalleServicio;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getLugarEntrada() {
        return lugarEntrada;
    }

    public void setLugarEntrada(String lugarEntrada) {
        this.lugarEntrada = lugarEntrada;
    }

    public String getTipoTabla() {
        return tipoTabla;
    }

    public void setTipoTabla(String tipoTabla) {
        this.tipoTabla = tipoTabla;
    }

    public String getSercon() {
        return sercon;
    }

    public void setSercon(String sercon) {
        this.sercon = sercon;
    }

    public String getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(String codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getNombreOperador() {
        return nombreOperador;
    }

    public void setNombreOperador(String nombreOperador) {
        this.nombreOperador = nombreOperador;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getHoraRealIngreso() {
        return horaRealIngreso;
    }

    public void setHoraRealIngreso(String horaRealIngreso) {
        this.horaRealIngreso = horaRealIngreso;
    }

    public String getDesviacion() {
        return desviacion;
    }

    public void setDesviacion(String desviacion) {
        this.desviacion = desviacion;
    }

    public String getApellidoOperador() {
        return apellidoOperador;
    }

    public void setApellidoOperador(String apellidoOperador) {
        this.apellidoOperador = apellidoOperador;
    }

    public Integer getIdPrgTc() {
        return idPrgTc;
    }

    public void setIdPrgTc(Integer idPrgTc) {
        this.idPrgTc = idPrgTc;
    }

}
