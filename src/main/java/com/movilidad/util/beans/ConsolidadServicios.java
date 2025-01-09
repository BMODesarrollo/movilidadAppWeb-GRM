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
public class ConsolidadServicios implements Serializable{
    
    private Integer id;
    private BigDecimal programado;
    private BigDecimal eliminado;
    private BigDecimal adicional;

    public ConsolidadServicios() {
    }

    public ConsolidadServicios(Integer id, BigDecimal programado, BigDecimal eliminado, BigDecimal adicional) {
        this.id = id;
        this.programado = programado;
        this.eliminado = eliminado;
        this.adicional = adicional;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getProgramado() {
        return programado;
    }

    public void setProgramado(BigDecimal programado) {
        this.programado = programado;
    }

    public BigDecimal getEliminado() {
        return eliminado;
    }

    public void setEliminado(BigDecimal eliminado) {
        this.eliminado = eliminado;
    }

    public BigDecimal getAdicional() {
        return adicional;
    }

    public void setAdicional(BigDecimal adicional) {
        this.adicional = adicional;
    }


    
}
