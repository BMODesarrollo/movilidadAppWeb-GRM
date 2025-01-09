/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class KmsOperador implements Serializable {

    private Integer codigo_tm;
    private String nombres;
    private String apellidos;
    private BigDecimal total;
    private BigDecimal comercial;
    private BigDecimal hlp_prg;
    private BigDecimal adicionales;
    private BigDecimal vaccom;
    private BigDecimal comercial_Eliminados;
    private BigDecimal hlp_Eliminados;
    private BigDecimal vac;

    public KmsOperador(Integer codigo_tm, String nombres, String apellidos, BigDecimal total, BigDecimal comercial, BigDecimal hlp_prg, BigDecimal adicionales, BigDecimal vaccom, BigDecimal comercial_Eliminados, BigDecimal hlp_Eliminados, BigDecimal vac) {
        this.codigo_tm = codigo_tm;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.total = total;
        this.comercial = comercial;
        this.hlp_prg = hlp_prg;
        this.adicionales = adicionales;
        this.vaccom = vaccom;
        this.comercial_Eliminados = comercial_Eliminados;
        this.hlp_Eliminados = hlp_Eliminados;
        this.vac = vac;
    }

    public Integer getCodigo_tm() {
        return codigo_tm;
    }

    public void setCodigo_tm(Integer codigo_tm) {
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getComercial() {
        return comercial;
    }

    public void setComercial(BigDecimal comercial) {
        this.comercial = comercial;
    }

    public BigDecimal getHlp_prg() {
        return hlp_prg;
    }

    public void setHlp_prg(BigDecimal hlp_prg) {
        this.hlp_prg = hlp_prg;
    }

    public BigDecimal getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(BigDecimal adicionales) {
        this.adicionales = adicionales;
    }

    public BigDecimal getVaccom() {
        return vaccom;
    }

    public void setVaccom(BigDecimal vaccom) {
        this.vaccom = vaccom;
    }

    public BigDecimal getComercial_Eliminados() {
        return comercial_Eliminados;
    }

    public void setComercial_Eliminados(BigDecimal comercial_Eliminados) {
        this.comercial_Eliminados = comercial_Eliminados;
    }

    public BigDecimal getHlp_Eliminados() {
        return hlp_Eliminados;
    }

    public void setHlp_Eliminados(BigDecimal hlp_Eliminados) {
        this.hlp_Eliminados = hlp_Eliminados;
    }

    public BigDecimal getVac() {
        return vac;
    }

    public void setVac(BigDecimal vac) {
        this.vac = vac;
    }

    
    
}
