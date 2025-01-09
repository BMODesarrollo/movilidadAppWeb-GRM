package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 * @func Clase para recibir los datos de consulta (Informe interventoría)
 */
@XmlRootElement
public class InformeInterventoria implements Serializable {

    private String fecha_registro;
    private int codigo_tm;
    private String nombre_cargo;
    private BigDecimal tiempoTotal;
    private BigDecimal comercial;
    private BigDecimal vacio;
    private long quejas;
    private long multas;

    public InformeInterventoria(String fecha_registro, int codigo_tm, String nombre_cargo, BigDecimal tiempoTotal, BigDecimal comercial, BigDecimal vacio, long quejas, long multas) {
        this.fecha_registro = fecha_registro;
        this.codigo_tm = codigo_tm;
        this.nombre_cargo = nombre_cargo;
        this.tiempoTotal = tiempoTotal;
        this.comercial = comercial;
        this.vacio = vacio;
        this.quejas = quejas;
        this.multas = multas;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getCodigo_tm() {
        return codigo_tm;
    }

    public void setCodigo_tm(int codigo_tm) {
        this.codigo_tm = codigo_tm;
    }

    public String getNombre_cargo() {
        return nombre_cargo;
    }

    public void setNombre_cargo(String nombre_cargo) {
        this.nombre_cargo = nombre_cargo;
    }

    public BigDecimal getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(BigDecimal tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public BigDecimal getComercial() {
        return comercial;
    }

    public void setComercial(BigDecimal comercial) {
        this.comercial = comercial;
    }

    public BigDecimal getVacio() {
        return vacio;
    }

    public void setVacio(BigDecimal vacio) {
        this.vacio = vacio;
    }

    public long getQuejas() {
        return quejas;
    }

    public void setQuejas(long quejas) {
        this.quejas = quejas;
    }

    public long getMultas() {
        return multas;
    }

    public void setMultas(long multas) {
        this.multas = multas;
    }
}