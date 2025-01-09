/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class PrgTcDTO implements Serializable {

    private Integer id_prg_tc;
    private String servbus;
    private int codigo_tm;
    private String codigo_vehiculo;
    private Integer id_vehiculo;
    private String time_origin;
    private String time_destiny;

    public PrgTcDTO(Integer id_prg_tc, String servbus, int codigo_tm, String codigo_vehiculo, Integer id_vehiculo, String time_origin, String time_destiny) {
        this.id_prg_tc = id_prg_tc;
        this.servbus = servbus;
        this.codigo_tm = codigo_tm;
        this.codigo_vehiculo = codigo_vehiculo;
        this.id_vehiculo = id_vehiculo;
        this.time_origin = time_origin;
        this.time_destiny = time_destiny;
    }

    public Integer getId_prg_tc() {
        return id_prg_tc;
    }

    public void setId_prg_tc(Integer id_prg_tc) {
        this.id_prg_tc = id_prg_tc;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public int getCodigo_tm() {
        return codigo_tm;
    }

    public void setCodigo_tm(int codigo_tm) {
        this.codigo_tm = codigo_tm;
    }

    public String getCodigo_vehiculo() {
        return codigo_vehiculo;
    }

    public void setCodigo_vehiculo(String codigo_vehiculo) {
        this.codigo_vehiculo = codigo_vehiculo;
    }

    public Integer getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(Integer id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getTime_origin() {
        return time_origin;
    }

    public void setTime_origin(String time_origin) {
        this.time_origin = time_origin;
    }

    public String getTime_destiny() {
        return time_destiny;
    }

    public void setTime_destiny(String time_destiny) {
        this.time_destiny = time_destiny;
    }

    

}
