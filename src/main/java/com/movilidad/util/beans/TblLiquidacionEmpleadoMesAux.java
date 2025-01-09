/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.model.Empleado;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author solucionesit
 */
public class TblLiquidacionEmpleadoMesAux implements Serializable {

    private String desde;
    private String hasta;
    private Empleado empleado;
    private BigInteger diasNovedad;
    private BigInteger puntosPmConciliados;
    private BigDecimal valorBono;
    private BigDecimal descuentoPuntos;
    private BigDecimal descuentoDias;
    private BigInteger bonoAmplitud;
    private BigDecimal vrPagar;

    public TblLiquidacionEmpleadoMesAux() {
    }
    

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public BigInteger getDiasNovedad() {
        return diasNovedad;
    }

    public void setDiasNovedad(BigInteger diasNovedad) {
        this.diasNovedad = diasNovedad;
    }

    public BigInteger getPuntosPmConciliados() {
        return puntosPmConciliados;
    }

    public void setPuntosPmConciliados(BigInteger puntosPmConciliados) {
        this.puntosPmConciliados = puntosPmConciliados;
    }

    public BigDecimal getValorBono() {
        return valorBono;
    }

    public void setValorBono(BigDecimal valorBono) {
        this.valorBono = valorBono;
    }

    public BigDecimal getDescuentoPuntos() {
        return descuentoPuntos;
    }

    public void setDescuentoPuntos(BigDecimal descuentoPuntos) {
        this.descuentoPuntos = descuentoPuntos;
    }

    public BigDecimal getDescuentoDias() {
        return descuentoDias;
    }

    public void setDescuentoDias(BigDecimal descuentoDias) {
        this.descuentoDias = descuentoDias;
    }

    public BigInteger getBonoAmplitud() {
        return bonoAmplitud;
    }

    public void setBonoAmplitud(BigInteger bonoAmplitud) {
        this.bonoAmplitud = bonoAmplitud;
    }

    public BigDecimal getVrPagar() {
        return vrPagar;
    }

    public void setVrPagar(BigDecimal vrPagar) {
        this.vrPagar = vrPagar;
    }
    
    
    
    
}
