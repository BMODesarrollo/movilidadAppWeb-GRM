package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 * @func Servicios Perdidos (Art) para informe de control
 */
@XmlRootElement
public class KmsPerdidosArt implements Serializable {

    private long count_operaciones;
    private long count_mtto;
    private long count_otros;
    private BigDecimal operaciones;
    private BigDecimal mtto;
    private BigDecimal otros;

    public KmsPerdidosArt(long count_operaciones, BigDecimal operaciones, long count_mtto, BigDecimal mtto, long count_otros, BigDecimal otros) {
        this.count_operaciones = count_operaciones;
        this.count_mtto = count_mtto;
        this.count_otros = count_otros;
        this.operaciones = operaciones;
        this.mtto = mtto;
        this.otros = otros;
    }

    public long getCount_operaciones() {
        return count_operaciones;
    }

    public void setCount_operaciones(long count_operaciones) {
        this.count_operaciones = count_operaciones;
    }

    public long getCount_mtto() {
        return count_mtto;
    }

    public void setCount_mtto(long count_mtto) {
        this.count_mtto = count_mtto;
    }

    public long getCount_otros() {
        return count_otros;
    }

    public void setCount_otros(long count_otros) {
        this.count_otros = count_otros;
    }

    public BigDecimal getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(BigDecimal operaciones) {
        this.operaciones = operaciones;
    }

    public BigDecimal getMtto() {
        return mtto;
    }

    public void setMtto(BigDecimal mtto) {
        this.mtto = mtto;
    }

    public BigDecimal getOtros() {
        return otros;
    }

    public void setOtros(BigDecimal otros) {
        this.otros = otros;
    }

}
