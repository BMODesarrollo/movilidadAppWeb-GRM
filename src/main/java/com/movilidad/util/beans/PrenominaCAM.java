package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@XmlRootElement
public class PrenominaCAM implements Serializable {

    private String identificacion;
    private String nombre;
    private Date fecha_inicio;
    private String cargo;
    private String area;
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

    public PrenominaCAM(String identificacion, String nombre, Date fecha_inicio, String cargo, String area, BigDecimal salario, BigDecimal diurnas, BigDecimal nocturnas, BigDecimal extra_diurna, BigDecimal festivo_diurno, BigDecimal festivo_nocturno, BigDecimal festivo_extra_diurno, BigDecimal festivo_extra_nocturno, BigDecimal extra_nocturna, BigDecimal dominical_comp_diurnas, BigDecimal dominical_comp_nocturnas, BigDecimal dominical_comp_diurna_extra, BigDecimal dominical_comp_nocturna_extra) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
        this.cargo = cargo;
        this.area = area;
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
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
