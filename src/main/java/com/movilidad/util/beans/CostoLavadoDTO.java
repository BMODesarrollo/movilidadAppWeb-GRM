/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soluciones-it
 */
@XmlRootElement
public class CostoLavadoDTO implements Serializable {

    @Id
    @Column(name = "lavado_cerrado")
    private Long lavadoCerrados;
    @Column(name = "fecha")
    private String fecha;
    @Column(name = "total_costo")
    private BigDecimal totalCosto;

    public Long getLavadoCerrados() {
        return lavadoCerrados;
    }

    public void setLavadoCerrados(Long lavadoCerrados) {
        this.lavadoCerrados = lavadoCerrados;
    }

    public BigDecimal getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(BigDecimal totalCosto) {
        this.totalCosto = totalCosto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public CostoLavadoDTO(Long lavadoCerrados, String fecha, BigDecimal totalCosto) {
        this.lavadoCerrados = lavadoCerrados;
        this.fecha = fecha;
        this.totalCosto = totalCosto;
    }

    public CostoLavadoDTO() {
    }

}
