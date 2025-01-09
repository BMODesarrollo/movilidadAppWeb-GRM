/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;

/**
 *
 * @author HP
 */
public class LiquidacionSercon implements Serializable {

    private String fecha;
    private String compania;
    private String codigo;
    private String conductor;
    private String documento;
    private String tiempoProduccion;
    private String diurnas;
    private String nocturnas;
    private String extraDiurna;
    private String extraNocturna;
    private String festivoDiurno;
    private String festivoNocturno;
    private String festivoExtraDiurno;
    private String festivoExtraNocturno;
    private String compensatorio;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTiempoProduccion() {
        return tiempoProduccion;
    }

    public void setTiempoProduccion(String tiempoProduccion) {
        this.tiempoProduccion = tiempoProduccion;
    }

    public String getDiurnas() {
        return diurnas;
    }

    public void setDiurnas(String diurnas) {
        this.diurnas = diurnas;
    }

    public String getNocturnas() {
        return nocturnas;
    }

    public void setNocturnas(String nocturnas) {
        this.nocturnas = nocturnas;
    }

    public String getExtraDiurna() {
        return extraDiurna;
    }

    public void setExtraDiurna(String extraDiurna) {
        this.extraDiurna = extraDiurna;
    }

    public String getExtraNocturna() {
        return extraNocturna;
    }

    public void setExtraNocturna(String extraNocturna) {
        this.extraNocturna = extraNocturna;
    }

    public String getFestivoDiurno() {
        return festivoDiurno;
    }

    public void setFestivoDiurno(String festivoDiurno) {
        this.festivoDiurno = festivoDiurno;
    }

    public String getFestivoNocturno() {
        return festivoNocturno;
    }

    public void setFestivoNocturno(String festivoNocturno) {
        this.festivoNocturno = festivoNocturno;
    }

    public String getFestivoExtraDiurno() {
        return festivoExtraDiurno;
    }

    public void setFestivoExtraDiurno(String festivoExtraDiurno) {
        this.festivoExtraDiurno = festivoExtraDiurno;
    }

    public String getFestivoExtraNocturno() {
        return festivoExtraNocturno;
    }

    public void setFestivoExtraNocturno(String festivoExtraNocturno) {
        this.festivoExtraNocturno = festivoExtraNocturno;
    }

    public String getCompensatorio() {
        return compensatorio;
    }

    public void setCompensatorio(String compensatorio) {
        this.compensatorio = compensatorio;
    }

    @Override
    public String toString() {
        return "LiquidacionSercon{" + "fecha=" + fecha + ", compania=" + compania + ", codigo=" + codigo + ", conductor=" + conductor + ", documento=" + documento + ", tiempoProduccion=" + tiempoProduccion + ", diurnas=" + diurnas + ", nocturnas=" + nocturnas + ", extraDiurna=" + extraDiurna + ", extraNocturna=" + extraNocturna + ", festivoDiurno=" + festivoDiurno + ", festivoNocturno=" + festivoNocturno + ", festivoExtraDiurno=" + festivoExtraDiurno + ", festivoExtraNocturno=" + festivoExtraNocturno + ", compensatorio=" + compensatorio + '}';
    }

}
