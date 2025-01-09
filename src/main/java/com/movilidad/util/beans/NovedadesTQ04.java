/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class NovedadesTQ04 implements Serializable {

    private Date fecha_registro;
    private Date fecha;
    private String servicio_afectado;
    private String servbus;
    private String ultima_parada;
    private String tipo_novedad;
    private String tipo_detalle_novedad;
    private String operador;
    private String codigo_operador;
    private String vehiculo;
    private String vehiculo_remplazo;
    private String descripcion;
    private String usuario;
    private int estado_op;

    public NovedadesTQ04(Date fecha_registro, Date fecha, String servicio_afectado, String servbus, String ultima_parada, String tipo_novedad, String tipo_detalle_novedad, String operador, String codigo_operador, String vehiculo, String vehiculo_remplazo, String descripcion, String usuario, int estado_op) {
        this.fecha_registro = fecha_registro;
        this.fecha = fecha;
        this.servicio_afectado = servicio_afectado;
        this.servbus = servbus;
        this.ultima_parada = ultima_parada;
        this.tipo_novedad = tipo_novedad;
        this.tipo_detalle_novedad = tipo_detalle_novedad;
        this.operador = operador;
        this.codigo_operador = codigo_operador;
        this.vehiculo = vehiculo;
        this.vehiculo_remplazo = vehiculo_remplazo;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.estado_op = estado_op;
    }
    
    

    public NovedadesTQ04() {
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getServicio_afectado() {
        return servicio_afectado;
    }

    public void setServicio_afectado(String servicio_afectado) {
        this.servicio_afectado = servicio_afectado;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public String getUltima_parada() {
        return ultima_parada;
    }

    public void setUltima_parada(String ultima_parada) {
        this.ultima_parada = ultima_parada;
    }

    public String getTipo_novedad() {
        return tipo_novedad;
    }

    public void setTipo_novedad(String tipo_novedad) {
        this.tipo_novedad = tipo_novedad;
    }

    public String getTipo_detalle_novedad() {
        return tipo_detalle_novedad;
    }

    public void setTipo_detalle_novedad(String tipo_detalle_novedad) {
        this.tipo_detalle_novedad = tipo_detalle_novedad;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getCodigo_operador() {
        return codigo_operador;
    }

    public void setCodigo_operador(String codigo_operador) {
        this.codigo_operador = codigo_operador;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getVehiculo_remplazo() {
        return vehiculo_remplazo;
    }

    public void setVehiculo_remplazo(String vehiculo_remplazo) {
        this.vehiculo_remplazo = vehiculo_remplazo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getEstado_op() {
        return estado_op;
    }

    public void setEstado_op(int estado_op) {
        this.estado_op = estado_op;
    }

   

}
