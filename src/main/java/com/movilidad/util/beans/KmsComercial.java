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
public class KmsComercial implements Serializable {

    private int codigo_vehiculo;
    private BigDecimal comercial;

    public KmsComercial(int codigo_vehiculo, BigDecimal comercial) {
        this.codigo_vehiculo = codigo_vehiculo;
        this.comercial = comercial;
    }

    public int getCodigo_vehiculo() {
        return codigo_vehiculo;
    }

    public void setCodigo_vehiculo(int codigo_vehiculo) {
        this.codigo_vehiculo = codigo_vehiculo;
    }

    public BigDecimal getComercial() {
        return comercial;
    }

    public void setComercial(BigDecimal comercial) {
        this.comercial = comercial;
    }

}
