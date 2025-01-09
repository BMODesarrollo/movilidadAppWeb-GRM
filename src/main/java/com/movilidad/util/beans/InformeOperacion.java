/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class InformeOperacion implements Serializable {

    private Date dia_operacion;
    private String bus;
    private String servbus;
    private String servicio;
    private String operador;
    private String codigo_operador;

    public InformeOperacion() {
    }

    public InformeOperacion(Date dia_operacion, String bus, String servbus, String servicio, String operador, String codigo_operador) {
        this.dia_operacion = dia_operacion;
        this.bus = bus;
        this.servbus = servbus;
        this.servicio = servicio;
        this.operador = operador;
        this.codigo_operador = codigo_operador;
    }

    public Date getDia_operacion() {
        return dia_operacion;
    }

    public void setDia_operacion(Date dia_operacion) {
        this.dia_operacion = dia_operacion;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getCodigo_operador() {
        return codigo_operador;
    }

    public void setCodigo_operador(String codigo_operador) {
        this.codigo_operador = codigo_operador;
    }

    

}
