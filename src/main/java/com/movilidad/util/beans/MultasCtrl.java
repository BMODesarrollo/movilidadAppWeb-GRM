package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 * @func Información detallada de multas para informe de control
 */
@XmlRootElement
public class MultasCtrl implements Serializable {

    private int codigo_operador;
    private String codigo_vehiculo;
    private String nombre_operador;
    private long operaciones;
    private long mantenimiento;
    private long lavado;
    private long seguridad_vial;
    private BigDecimal kms_operaciones;
    private BigDecimal kms_mantenimiento;
    private BigDecimal kms_lavado;
    private BigDecimal kms_seguridad_vial;

    public MultasCtrl(int codigo_operador, String codigo_vehiculo, String nombre_operador, long operaciones, long mantenimiento, long lavado, long seguridad_vial, BigDecimal kms_operaciones, BigDecimal kms_mantenimiento, BigDecimal kms_lavado, BigDecimal kms_seguridad_vial) {
        this.codigo_operador = codigo_operador;
        this.codigo_vehiculo = codigo_vehiculo;
        this.nombre_operador = nombre_operador;
        this.operaciones = operaciones;
        this.mantenimiento = mantenimiento;
        this.lavado = lavado;
        this.seguridad_vial = seguridad_vial;
        this.kms_operaciones = kms_operaciones;
        this.kms_mantenimiento = kms_mantenimiento;
        this.kms_lavado = kms_lavado;
        this.kms_seguridad_vial = kms_seguridad_vial;
    }

    public int getCodigo_operador() {
        return codigo_operador;
    }

    public void setCodigo_operador(int codigo_operador) {
        this.codigo_operador = codigo_operador;
    }

    public String getCodigo_vehiculo() {
        return codigo_vehiculo;
    }

    public void setCodigo_vehiculo(String codigo_vehiculo) {
        this.codigo_vehiculo = codigo_vehiculo;
    }

    public String getNombre_operador() {
        return nombre_operador;
    }

    public void setNombre_operador(String nombre_operador) {
        this.nombre_operador = nombre_operador;
    }

    public long getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(long operaciones) {
        this.operaciones = operaciones;
    }

    public long getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(long mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public long getLavado() {
        return lavado;
    }

    public void setLavado(long lavado) {
        this.lavado = lavado;
    }

    public long getSeguridad_vial() {
        return seguridad_vial;
    }

    public void setSeguridad_vial(long seguridad_vial) {
        this.seguridad_vial = seguridad_vial;
    }

    public BigDecimal getKms_operaciones() {
        return kms_operaciones;
    }

    public void setKms_operaciones(BigDecimal kms_operaciones) {
        this.kms_operaciones = kms_operaciones;
    }

    public BigDecimal getKms_mantenimiento() {
        return kms_mantenimiento;
    }

    public void setKms_mantenimiento(BigDecimal kms_mantenimiento) {
        this.kms_mantenimiento = kms_mantenimiento;
    }

    public BigDecimal getKms_lavado() {
        return kms_lavado;
    }

    public void setKms_lavado(BigDecimal kms_lavado) {
        this.kms_lavado = kms_lavado;
    }

    public BigDecimal getKms_seguridad_vial() {
        return kms_seguridad_vial;
    }

    public void setKms_seguridad_vial(BigDecimal kms_seguridad_vial) {
        this.kms_seguridad_vial = kms_seguridad_vial;
    }
}
