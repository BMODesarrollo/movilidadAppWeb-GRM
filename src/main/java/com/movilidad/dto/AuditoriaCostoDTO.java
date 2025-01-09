/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class AuditoriaCostoDTO implements Serializable {

    private String tipo_costo;
    private String tipo_vehiculo;
    private Integer valor;
    private Date desde;
    private Date hasta;

    public AuditoriaCostoDTO(String tipo_costo, String tipo_vehiculo, Integer valor, Date desde, Date hasta) {
        this.tipo_costo = tipo_costo;
        this.tipo_vehiculo = tipo_vehiculo;
        this.valor = valor;
        this.desde = desde;
        this.hasta = hasta;
    }

    public String getTipo_costo() {
        return tipo_costo;
    }

    public void setTipo_costo(String tipo_costo) {
        this.tipo_costo = tipo_costo;
    }

    public String getTipo_vehiculo() {
        return tipo_vehiculo;
    }

    public void setTipo_vehiculo(String tipo_vehiculo) {
        this.tipo_vehiculo = tipo_vehiculo;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
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


    

}
