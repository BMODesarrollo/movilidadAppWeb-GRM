package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@XmlRootElement
public class ResEstadoActFlota implements Serializable {

    private Integer id_gop_unidad_funcional;
    private String nombre;
    private long vehi_reg_art;
    private long vehi_reg_bi;
    private BigDecimal total_vehi_reg;
    private long vehi_nov_art;
    private long vehi_nov_bi;
    private BigDecimal total_vehi_nov;
    private double disp_art;
    private double disp_bi;
    private double total_disp;

    public ResEstadoActFlota(Integer id_gop_unidad_funcional, String nombre, long vehi_reg_art, long vehi_reg_bi, BigDecimal total_vehi_reg, long vehi_nov_art, long vehi_nov_bi, BigDecimal total_vehi_nov, double disp_art, double disp_bi) {
        this.id_gop_unidad_funcional = id_gop_unidad_funcional;
        this.nombre = nombre;
        this.vehi_reg_art = vehi_reg_art;
        this.vehi_reg_bi = vehi_reg_bi;
        this.total_vehi_reg = total_vehi_reg;
        this.vehi_nov_art = vehi_nov_art;
        this.vehi_nov_bi = vehi_nov_bi;
        this.total_vehi_nov = total_vehi_nov;
        this.disp_art = disp_art;
        this.disp_bi = disp_bi;
        this.total_disp = (total_vehi_reg.subtract(total_vehi_nov)).doubleValue() / total_vehi_reg.doubleValue() * 100;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getVehi_reg_art() {
        return vehi_reg_art;
    }

    public void setVehi_reg_art(long vehi_reg_art) {
        this.vehi_reg_art = vehi_reg_art;
    }

    public long getVehi_reg_bi() {
        return vehi_reg_bi;
    }

    public void setVehi_reg_bi(long vehi_reg_bi) {
        this.vehi_reg_bi = vehi_reg_bi;
    }

    public BigDecimal getTotal_vehi_reg() {
        return total_vehi_reg;
    }

    public void setTotal_vehi_reg(BigDecimal total_vehi_reg) {
        this.total_vehi_reg = total_vehi_reg;
    }

    public long getVehi_nov_art() {
        return vehi_nov_art;
    }

    public void setVehi_nov_art(long vehi_nov_art) {
        this.vehi_nov_art = vehi_nov_art;
    }

    public long getVehi_nov_bi() {
        return vehi_nov_bi;
    }

    public void setVehi_nov_bi(long vehi_nov_bi) {
        this.vehi_nov_bi = vehi_nov_bi;
    }

    public BigDecimal getTotal_vehi_nov() {
        return total_vehi_nov;
    }

    public void setTotal_vehi_nov(BigDecimal total_vehi_nov) {
        this.total_vehi_nov = total_vehi_nov;
    }

    public double getDisp_art() {
        return disp_art;
    }

    public void setDisp_art(double disp_art) {
        this.disp_art = disp_art;
    }

    public double getDisp_bi() {
        return disp_bi;
    }

    public void setDisp_bi(double disp_bi) {
        this.disp_bi = disp_bi;
    }

    public double getTotal_disp() {
        return total_disp;
    }

    public void setTotal_disp(double total_disp) {
        this.total_disp = total_disp;
    }

}
