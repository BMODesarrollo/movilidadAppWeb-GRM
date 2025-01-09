package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jesús Jiménez
 */
@XmlRootElement
public class ConsolidadoLiquidacionGMO implements Serializable {

    @Column(name = "desde")
    private Date desde;
    @Column(name = "hasta")
    private Date hasta;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "quincena")
    private String quincena;
    @Column(name = "diurnas")
    private BigDecimal diurnas;
    @Column(name = "nocturnas")
    private BigDecimal nocturnas;
    @Column(name = "extra_diurna")
    private BigDecimal extraDiurna;
    @Column(name = "festivo_diurno")
    private BigDecimal festivoDiurno;
    @Column(name = "festivo_nocturno")
    private BigDecimal festivoNocturno;
    @Column(name = "festivo_extra_diurno")
    private BigDecimal festivoExtraDiurno;
    @Column(name = "festivo_extra_nocturno")
    private BigDecimal festivoExtraNocturno;
    @Column(name = "extra_nocturna")
    private BigDecimal extraNocturna;
    @Column(name = "dominical_comp_diurnas")
    private BigDecimal dominicalCompDiurnas;
    @Column(name = "dominical_comp_nocturnas")
    private BigDecimal dominicalCompNocturnas;
    @Column(name = "dominical_comp_diurna_extra")
    private BigDecimal dominicalCompDiurnaExtra;
    @Column(name = "dominical_comp_nocturna_extra")
    private BigDecimal dominicalCompNocturnaExtra;
    @Column(name = "salario")
    private BigDecimal salario;
    private double costoHora;
    private double costoNocturno;
    private double costoExtraDiurno;
    private double costoExtraNocturno;
    private double costoFestivoExtraDiurno;
    private double costoFestivoExtraNocturno;
    private double costoDominicalConCompExtraDiurno;
    private double costoDominicalConcompExtraNocturno;
    private double costoDominicalNocturno;
    private double costoDominicalDiurno;
    private double costoFestivoNocturno;
    private double costoFestivoDiurno;
    private double totalHoras;

    public ConsolidadoLiquidacionGMO(Date desde, Date hasta, String quincena, BigDecimal nocturnas, BigDecimal extraDiurna, BigDecimal extraNocturna, BigDecimal festivoDiurno, BigDecimal festivoNocturno, BigDecimal festivoExtraDiurno, BigDecimal festivoExtraNocturno, BigDecimal dominicalCompDiurnas, BigDecimal dominicalCompNocturnas, BigDecimal dominicalCompDiurnaExtra, BigDecimal dominicalCompNocturnaExtra) {
        this.desde = desde;
        this.hasta = hasta;
        this.quincena = quincena;
        this.nocturnas = nocturnas;
        this.extraDiurna = extraDiurna;
        this.extraNocturna = extraNocturna;
        this.festivoDiurno = festivoDiurno;
        this.festivoNocturno = festivoNocturno;
        this.festivoExtraDiurno = festivoExtraDiurno;
        this.festivoExtraNocturno = festivoExtraNocturno;
        this.dominicalCompDiurnas = dominicalCompDiurnas;
        this.dominicalCompNocturnas = dominicalCompNocturnas;
        this.dominicalCompDiurnaExtra = dominicalCompDiurnaExtra;
        this.dominicalCompNocturnaExtra = dominicalCompNocturnaExtra;
    }

    public ConsolidadoLiquidacionGMO(Date fecha, String quincena, BigDecimal salario, BigDecimal nocturnas, BigDecimal extraDiurna, BigDecimal extraNocturna, BigDecimal festivoDiurno, BigDecimal festivoNocturno, BigDecimal festivoExtraDiurno, BigDecimal festivoExtraNocturno, BigDecimal dominicalCompDiurnas, BigDecimal dominicalCompNocturnas, BigDecimal dominicalCompDiurnaExtra, BigDecimal dominicalCompNocturnaExtra) {
        this.fecha = fecha;
        this.quincena = quincena;
        this.salario = salario;
        this.nocturnas = nocturnas;
        this.extraDiurna = extraDiurna;
        this.extraNocturna = extraNocturna;
        this.festivoDiurno = festivoDiurno;
        this.festivoNocturno = festivoNocturno;
        this.festivoExtraDiurno = festivoExtraDiurno;
        this.festivoExtraNocturno = festivoExtraNocturno;
        this.dominicalCompDiurnas = dominicalCompDiurnas;
        this.dominicalCompNocturnas = dominicalCompNocturnas;
        this.dominicalCompDiurnaExtra = dominicalCompDiurnaExtra;
        this.dominicalCompNocturnaExtra = dominicalCompNocturnaExtra;
        this.costoHora = this.salario.doubleValue() / 240;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getQuincena() {
        return quincena;
    }

    public void setQuincena(String quincena) {
        this.quincena = quincena;
    }

    public BigDecimal getDiurnas() {
        return diurnas;
    }

    public void setDiurnas(BigDecimal diurnas) {
        this.diurnas = diurnas;
    }

    public BigDecimal getNocturnas() {
        return nocturnas;
    }

    public void setNocturnas(BigDecimal nocturnas) {
        this.nocturnas = nocturnas;
    }

    public BigDecimal getExtraDiurna() {
        return extraDiurna;
    }

    public void setExtraDiurna(BigDecimal extraDiurna) {
        this.extraDiurna = extraDiurna;
    }

    public BigDecimal getFestivoDiurno() {
        return festivoDiurno;
    }

    public void setFestivoDiurno(BigDecimal festivoDiurno) {
        this.festivoDiurno = festivoDiurno;
    }

    public BigDecimal getFestivoNocturno() {
        return festivoNocturno;
    }

    public void setFestivoNocturno(BigDecimal festivoNocturno) {
        this.festivoNocturno = festivoNocturno;
    }

    public BigDecimal getFestivoExtraDiurno() {
        return festivoExtraDiurno;
    }

    public void setFestivoExtraDiurno(BigDecimal festivoExtraDiurno) {
        this.festivoExtraDiurno = festivoExtraDiurno;
    }

    public BigDecimal getFestivoExtraNocturno() {
        return festivoExtraNocturno;
    }

    public void setFestivoExtraNocturno(BigDecimal festivoExtraNocturno) {
        this.festivoExtraNocturno = festivoExtraNocturno;
    }

    public BigDecimal getExtraNocturna() {
        return extraNocturna;
    }

    public void setExtraNocturna(BigDecimal extraNocturna) {
        this.extraNocturna = extraNocturna;
    }

    public BigDecimal getDominicalCompDiurnas() {
        return dominicalCompDiurnas;
    }

    public void setDominicalCompDiurnas(BigDecimal dominicalCompDiurnas) {
        this.dominicalCompDiurnas = dominicalCompDiurnas;
    }

    public BigDecimal getDominicalCompNocturnas() {
        return dominicalCompNocturnas;
    }

    public void setDominicalCompNocturnas(BigDecimal dominicalCompNocturnas) {
        this.dominicalCompNocturnas = dominicalCompNocturnas;
    }

    public BigDecimal getDominicalCompDiurnaExtra() {
        return dominicalCompDiurnaExtra;
    }

    public void setDominicalCompDiurnaExtra(BigDecimal dominicalCompDiurnaExtra) {
        this.dominicalCompDiurnaExtra = dominicalCompDiurnaExtra;
    }

    public BigDecimal getDominicalCompNocturnaExtra() {
        return dominicalCompNocturnaExtra;
    }

    public void setDominicalCompNocturnaExtra(BigDecimal dominicalCompNocturnaExtra) {
        this.dominicalCompNocturnaExtra = dominicalCompNocturnaExtra;
    }

    public double getCostoHora() {
        return costoHora;
    }

    public void setCostoHora(double costoHora) {
        this.costoHora = costoHora;
    }

    public double getCostoNocturno() {
        return costoNocturno;
    }

    public void setCostoNocturno(double costoNocturno) {
        this.costoNocturno = costoNocturno;
    }

    public double getCostoExtraDiurno() {
        return costoExtraDiurno;
    }

    public void setCostoExtraDiurno(double costoExtraDiurno) {
        this.costoExtraDiurno = costoExtraDiurno;
    }

    public double getCostoExtraNocturno() {
        return costoExtraNocturno;
    }

    public void setCostoExtraNocturno(double costoExtraNocturno) {
        this.costoExtraNocturno = costoExtraNocturno;
    }

    public double getCostoFestivoExtraDiurno() {
        return costoFestivoExtraDiurno;
    }

    public void setCostoFestivoExtraDiurno(double costoFestivoExtraDiurno) {
        this.costoFestivoExtraDiurno = costoFestivoExtraDiurno;
    }

    public double getCostoFestivoExtraNocturno() {
        return costoFestivoExtraNocturno;
    }

    public void setCostoFestivoExtraNocturno(double costoFestivoExtraNocturno) {
        this.costoFestivoExtraNocturno = costoFestivoExtraNocturno;
    }

    public double getCostoDominicalConCompExtraDiurno() {
        return costoDominicalConCompExtraDiurno;
    }

    public void setCostoDominicalConCompExtraDiurno(double costoDominicalConCompExtraDiurno) {
        this.costoDominicalConCompExtraDiurno = costoDominicalConCompExtraDiurno;
    }

    public double getCostoDominicalConcompExtraNocturno() {
        return costoDominicalConcompExtraNocturno;
    }

    public void setCostoDominicalConcompExtraNocturno(double costoDominicalConcompExtraNocturno) {
        this.costoDominicalConcompExtraNocturno = costoDominicalConcompExtraNocturno;
    }

    public double getCostoDominicalNocturno() {
        return costoDominicalNocturno;
    }

    public void setCostoDominicalNocturno(double costoDominicalNocturno) {
        this.costoDominicalNocturno = costoDominicalNocturno;
    }

    public double getCostoDominicalDiurno() {
        return costoDominicalDiurno;
    }

    public void setCostoDominicalDiurno(double costoDominicalDiurno) {
        this.costoDominicalDiurno = costoDominicalDiurno;
    }

    public double getCostoFestivoNocturno() {
        return costoFestivoNocturno;
    }

    public void setCostoFestivoNocturno(double costoFestivoNocturno) {
        this.costoFestivoNocturno = costoFestivoNocturno;
    }

    public double getCostoFestivoDiurno() {
        return costoFestivoDiurno;
    }

    public void setCostoFestivoDiurno(double costoFestivoDiurno) {
        this.costoFestivoDiurno = costoFestivoDiurno;
    }

    public double getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(double totalHoras) {
        this.totalHoras = totalHoras;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.quincena);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConsolidadoLiquidacionGMO other = (ConsolidadoLiquidacionGMO) obj;
        if (!Objects.equals(this.quincena, other.quincena)) {
            return false;
        }
        return true;
    }

}
