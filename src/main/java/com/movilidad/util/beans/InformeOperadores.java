package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 * @func Informe operadores en cargo
 */
@XmlRootElement
public class InformeOperadores implements Serializable {

    private Date fecha;
    private long c_fecha;
    private int codigo_tm;
    private String nombres;
    private String apellidos;
    private String nombre_cargo;
    private int certificado;
    private String nombre_tipo_vehiculo;
    private long total_viajes;
    private BigDecimal km_realizados;

    public InformeOperadores(Date fecha, long c_fecha, int codigo_tm, String nombres, String apellidos, String nombre_cargo, int certificado, String nombre_tipo_vehiculo, long total_viajes, BigDecimal km_realizados) {
        this.fecha = fecha;
        this.c_fecha = c_fecha;
        this.codigo_tm = codigo_tm;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombre_cargo = nombre_cargo;
        this.certificado = certificado;
        this.nombre_tipo_vehiculo = nombre_tipo_vehiculo;
        this.total_viajes = total_viajes;
        this.km_realizados = km_realizados;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getC_fecha() {
        return c_fecha;
    }

    public void setC_fecha(long c_fecha) {
        this.c_fecha = c_fecha;
    }

    public int getCodigo_tm() {
        return codigo_tm;
    }

    public void setCodigo_tm(int codigo_tm) {
        this.codigo_tm = codigo_tm;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre_cargo() {
        return nombre_cargo;
    }

    public void setNombre_cargo(String nombre_cargo) {
        this.nombre_cargo = nombre_cargo;
    }

    public int getCertificado() {
        return certificado;
    }

    public void setCertificado(int certificado) {
        this.certificado = certificado;
    }

    public String getNombre_tipo_vehiculo() {
        return nombre_tipo_vehiculo;
    }

    public void setNombre_tipo_vehiculo(String nombre_tipo_vehiculo) {
        this.nombre_tipo_vehiculo = nombre_tipo_vehiculo;
    }

    public long getTotal_viajes() {
        return total_viajes;
    }

    public void setTotal_viajes(long total_viajes) {
        this.total_viajes = total_viajes;
    }

    public BigDecimal getKm_realizados() {
        return km_realizados;
    }

    public void setKm_realizados(BigDecimal km_realizados) {
        this.km_realizados = km_realizados;
    }

}
