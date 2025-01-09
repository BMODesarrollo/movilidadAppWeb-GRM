/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class TP28UltimoServicioDTO implements Serializable {

    //Día en curso
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "nombre_uf")
    private String nombreUf;
    //hora programada de salida del último servicio
    @Column(name = "time_origin")
    private String horaSalida;
    //Ruta, una fila por cada una de las rutas
    @Column(name = "tarea")
    private String ruta;
    //Número de tabla del último servicio
    @Column(name = "tabla")
    private String tabla;
    //Código del operador programado en el último servicio de cada ruta
    @Column(name = "codigo_tm")
    private String codigoTm;
    //Nombre y apellido del operador
    @Column(name = "nombres")
    private String nombreOperador;
    //Nombre y apellido del operador
    @Column(name = "apellidos")
    private String apellidoOperador;
    //Teléfono del operador
    @Column(name = "telefono_movil")
    private String telefono;
    //Punto de inicio de recorrido del servicio por ruta
    @Column(name = "from_stop")
    private String lugarSalida;
    //Número de móvil programado
    @Column(name = "codigo_vehiculo")
    private String vehiculo;

    public TP28UltimoServicioDTO() {
    }

    public TP28UltimoServicioDTO(Date fecha, String nombreUf, String horaSalida, String ruta, String tabla, String codigoTm, String nombreOperador, String apellidoOperador, String telefono, String lugarSalida, String vehiculo) {
        this.fecha = fecha;
        this.nombreUf = nombreUf;
        this.horaSalida = horaSalida;
        this.ruta = ruta;
        this.tabla = tabla;
        this.codigoTm = codigoTm;
        this.nombreOperador = nombreOperador;
        this.apellidoOperador = apellidoOperador;
        this.telefono = telefono;
        this.lugarSalida = lugarSalida;
        this.vehiculo = vehiculo;
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

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
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

    public String getApellidoOperador() {
        return apellidoOperador;
    }

    public void setApellidoOperador(String apellidoOperador) {
        this.apellidoOperador = apellidoOperador;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

}
