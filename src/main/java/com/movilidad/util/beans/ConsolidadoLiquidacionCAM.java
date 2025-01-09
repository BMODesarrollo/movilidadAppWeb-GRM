package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@XmlRootElement
public class ConsolidadoLiquidacionCAM implements Serializable {

    private String identificacion;
    private String nombre;
    private String cargo;
    private BigDecimal salario;
    private BigDecimal diurnas;
    private BigDecimal nocturnas;
    private BigDecimal extra_diurna;
    private BigDecimal festivo_diurno;
    private BigDecimal festivo_nocturno;
    private BigDecimal festivo_extra_diurno;
    private BigDecimal festivo_extra_nocturno;
    private BigDecimal extra_nocturna;
    private BigDecimal dominical_comp_diurnas;
    private BigDecimal dominical_comp_nocturnas;
    private BigDecimal dominical_comp_diurna_extra;
    private BigDecimal dominical_comp_nocturna_extra;
    private double hora;
    private double salario_nocturno;
    private double salario_extra_diurno;
    private double salario_extra_nocturno;
    private double salario_festivo_extra_diurno;
    private double salario_festivo_extra_nocturno;
    private double salario_dominical_con_comp_extra_diurno;
    private double salario_dominical_con_comp_extra_nocturno;
    private double salario_dominical_nocturno;
    private double salario_dominical_diurno;
    private double salario_festivo_nocturno;
    private double salario_festivo_diurno;
    private double total;

    public ConsolidadoLiquidacionCAM(String identificacion, String nombre, String cargo, BigDecimal salario, BigDecimal diurnas, BigDecimal nocturnas, BigDecimal extra_diurna, BigDecimal festivo_diurno, BigDecimal festivo_nocturno, BigDecimal festivo_extra_diurno, BigDecimal festivo_extra_nocturno, BigDecimal extra_nocturna, BigDecimal dominical_comp_diurnas, BigDecimal dominical_comp_nocturnas, BigDecimal dominical_comp_diurna_extra, BigDecimal dominical_comp_nocturna_extra) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
        this.diurnas = diurnas;
        this.nocturnas = nocturnas;
        this.extra_diurna = extra_diurna;
        this.festivo_diurno = festivo_diurno;
        this.festivo_nocturno = festivo_nocturno;
        this.festivo_extra_diurno = festivo_extra_diurno;
        this.festivo_extra_nocturno = festivo_extra_nocturno;
        this.extra_nocturna = extra_nocturna;
        this.dominical_comp_diurnas = dominical_comp_diurnas;
        this.dominical_comp_nocturnas = dominical_comp_nocturnas;
        this.dominical_comp_diurna_extra = dominical_comp_diurna_extra;
        this.dominical_comp_nocturna_extra = dominical_comp_nocturna_extra;
        this.hora = this.salario.doubleValue() / 240;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public BigDecimal getExtra_diurna() {
        return extra_diurna;
    }

    public void setExtra_diurna(BigDecimal extra_diurna) {
        this.extra_diurna = extra_diurna;
    }

    public BigDecimal getFestivo_diurno() {
        return festivo_diurno;
    }

    public void setFestivo_diurno(BigDecimal festivo_diurno) {
        this.festivo_diurno = festivo_diurno;
    }

    public BigDecimal getFestivo_nocturno() {
        return festivo_nocturno;
    }

    public void setFestivo_nocturno(BigDecimal festivo_nocturno) {
        this.festivo_nocturno = festivo_nocturno;
    }

    public BigDecimal getFestivo_extra_diurno() {
        return festivo_extra_diurno;
    }

    public void setFestivo_extra_diurno(BigDecimal festivo_extra_diurno) {
        this.festivo_extra_diurno = festivo_extra_diurno;
    }

    public BigDecimal getFestivo_extra_nocturno() {
        return festivo_extra_nocturno;
    }

    public void setFestivo_extra_nocturno(BigDecimal festivo_extra_nocturno) {
        this.festivo_extra_nocturno = festivo_extra_nocturno;
    }

    public BigDecimal getExtra_nocturna() {
        return extra_nocturna;
    }

    public void setExtra_nocturna(BigDecimal extra_nocturna) {
        this.extra_nocturna = extra_nocturna;
    }

    public BigDecimal getDominical_comp_diurnas() {
        return dominical_comp_diurnas;
    }

    public void setDominical_comp_diurnas(BigDecimal dominical_comp_diurnas) {
        this.dominical_comp_diurnas = dominical_comp_diurnas;
    }

    public BigDecimal getDominical_comp_nocturnas() {
        return dominical_comp_nocturnas;
    }

    public void setDominical_comp_nocturnas(BigDecimal dominical_comp_nocturnas) {
        this.dominical_comp_nocturnas = dominical_comp_nocturnas;
    }

    public BigDecimal getDominical_comp_diurna_extra() {
        return dominical_comp_diurna_extra;
    }

    public void setDominical_comp_diurna_extra(BigDecimal dominical_comp_diurna_extra) {
        this.dominical_comp_diurna_extra = dominical_comp_diurna_extra;
    }

    public BigDecimal getDominical_comp_nocturna_extra() {
        return dominical_comp_nocturna_extra;
    }

    public void setDominical_comp_nocturna_extra(BigDecimal dominical_comp_nocturna_extra) {
        this.dominical_comp_nocturna_extra = dominical_comp_nocturna_extra;
    }

    public double getHora() {
        return hora;
    }

    public void setHora(double hora) {
        this.hora = hora;
    }

    public double getSalario_nocturno() {
        return salario_nocturno;
    }

    public void setSalario_nocturno(double salario_nocturno) {
        this.salario_nocturno = salario_nocturno;
    }

    public double getSalario_dominical_nocturno() {
        return salario_dominical_nocturno;
    }

    public void setSalario_dominical_nocturno(double salario_dominical_nocturno) {
        this.salario_dominical_nocturno = salario_dominical_nocturno;
    }

    public double getSalario_dominical_diurno() {
        return salario_dominical_diurno;
    }

    public void setSalario_dominical_diurno(double salario_dominical_diurno) {
        this.salario_dominical_diurno = salario_dominical_diurno;
    }

    public double getSalario_festivo_nocturno() {
        return salario_festivo_nocturno;
    }

    public void setSalario_festivo_nocturno(double salario_festivo_nocturno) {
        this.salario_festivo_nocturno = salario_festivo_nocturno;
    }

    public double getSalario_festivo_diurno() {
        return salario_festivo_diurno;
    }

    public void setSalario_festivo_diurno(double salario_festivo_diurno) {
        this.salario_festivo_diurno = salario_festivo_diurno;
    }

    public double getSalario_extra_diurno() {
        return salario_extra_diurno;
    }

    public void setSalario_extra_diurno(double salario_extra_diurno) {
        this.salario_extra_diurno = salario_extra_diurno;
    }

    public double getSalario_extra_nocturno() {
        return salario_extra_nocturno;
    }

    public void setSalario_extra_nocturno(double salario_extra_nocturno) {
        this.salario_extra_nocturno = salario_extra_nocturno;
    }

    public double getSalario_festivo_extra_diurno() {
        return salario_festivo_extra_diurno;
    }

    public void setSalario_festivo_extra_diurno(double salario_festivo_extra_diurno) {
        this.salario_festivo_extra_diurno = salario_festivo_extra_diurno;
    }

    public double getSalario_festivo_extra_nocturno() {
        return salario_festivo_extra_nocturno;
    }

    public void setSalario_festivo_extra_nocturno(double salario_festivo_extra_nocturno) {
        this.salario_festivo_extra_nocturno = salario_festivo_extra_nocturno;
    }

    public double getSalario_dominical_con_comp_extra_diurno() {
        return salario_dominical_con_comp_extra_diurno;
    }

    public void setSalario_dominical_con_comp_extra_diurno(double salario_dominical_con_comp_extra_diurno) {
        this.salario_dominical_con_comp_extra_diurno = salario_dominical_con_comp_extra_diurno;
    }

    public double getSalario_dominical_con_comp_extra_nocturno() {
        return salario_dominical_con_comp_extra_nocturno;
    }

    public void setSalario_dominical_con_comp_extra_nocturno(double salario_dominical_con_comp_extra_nocturno) {
        this.salario_dominical_con_comp_extra_nocturno = salario_dominical_con_comp_extra_nocturno;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

}
