/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julián Arévalo
 *
 * Bitacora Accidentalidad In Itinere
 *
 */
@XmlRootElement

public class BitacoraAccidentalidadInItinere implements Serializable {

    @Column(name = "fecha")
    private String fecha;
    @Column(name = "hora")
    private String hora;
    @Column(name = "placa")
    private String placa;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "codigo_operador")
    private String codigoOperador;
    @Column(name = "nombre_operador")
    private String nombreOperador;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "tipo_evento")
    private String tipoEvento;
    @Column(name = "observaciones")
    private String observaciones;

    public BitacoraAccidentalidadInItinere(String fecha, String hora, String placa, String codigo, String codigoOperador, String nombreOperador, String identificacion, String tipoEvento, String observaciones) {
        this.fecha = fecha;
        this.hora = hora;
        this.placa = placa;
        this.codigo = codigo;
        this.codigoOperador = codigoOperador;
        this.nombreOperador = nombreOperador;
        this.identificacion = identificacion;
        this.tipoEvento = tipoEvento;
        this.observaciones = observaciones;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoOperador() {
        return codigoOperador;
    }

    public void setCodigoOperador(String codigoOperador) {
        this.codigoOperador = codigoOperador;
    }

    public String getNombreOperador() {
        return nombreOperador;
    }

    public void setNombreOperador(String nombreOperador) {
        this.nombreOperador = nombreOperador;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
 
}
