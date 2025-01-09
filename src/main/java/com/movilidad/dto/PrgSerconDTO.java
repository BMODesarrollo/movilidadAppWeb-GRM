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
public class PrgSerconDTO implements Serializable {

    private Integer id_prg_sercon;
    private Integer codigo_tm;
    private String time_origin;
    private String time_destiny;

    public Integer getId_prg_sercon() {
        return id_prg_sercon;
    }

    public PrgSerconDTO(Integer id_prg_sercon, Integer codigo_tm, String time_origin, String time_destiny) {
        this.id_prg_sercon = id_prg_sercon;
        this.codigo_tm = codigo_tm;
        this.time_origin = time_origin;
        this.time_destiny = time_destiny;
    }

    public void setId_prg_sercon(Integer id_prg_sercon) {
        this.id_prg_sercon = id_prg_sercon;
    }

    public Integer getCodigo_tm() {
        return codigo_tm;
    }

    public void setCodigo_tm(Integer codigo_tm) {
        this.codigo_tm = codigo_tm;
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
