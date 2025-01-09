/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 *
 * Se requiere un reporte donde se muestre la hora de entrada y salida real del
 * personal activo de la organización y las diferencias con el horario de turno
 * programado
 */
@XmlRootElement
public class EntradaSalidaJornadaDTO implements Serializable{

    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "nombre_uf")
    private String nombreUf;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "codigo_tm")
    private String codigoTm;
    @Column(name = "nombre_operador")
    private String nombreOperador;
    @Column(name = "hora_ingreso_prg")
    private String horaIngresoPrg;
    @Column(name = "presentacion_mymovil")
    private Integer presentacionMymovil;
    @Column(name = "hora_ingreso_presentacion")
    private String horaIngresoPresentacion;
    @Column(name = "hora_salida_prg")
    private String horaSalidaPrg;
    @Column(name = "salida_mymovil")
    private Integer salidaMymovil;
    @Column(name = "hora_salida_registrada")
    private String horaSalidaRegistrada;
    @Column(name = "prg_modificada")
    private Integer prgModificada;
    @Column(name = "nomina_borrada")
    private Integer nominaBorrada;
    @Column(name = "total_horas_programdas")
    private String totalHorasProgramdas;
    @Column(name = "total_horas_reales")
    private String totalHorasReales;
    @Column(name = "ultima_ruta")
    private String ultimaRuta;
    private String tiempoExtraRegistrado;

    public EntradaSalidaJornadaDTO(Date fecha, String nombreUf, String identificacion,
            String codigoTm, String nombreOperador, String horaIngresoPrg,
            Integer presentacionMymovil, String horaIngresoPresentacion,
            String horaSalidaPrg, Integer salidaMymovil, String horaSalidaRegistrada,
            Integer prgModificada, Integer nominaBorrada, String totalHorasProgramdas,
            String totalHorasReales, String ultimaRuta) {
        this.fecha = fecha;
        this.nombreUf = nombreUf;
        this.identificacion = identificacion;
        this.codigoTm = codigoTm;
        this.nombreOperador = nombreOperador;
        this.horaIngresoPrg = horaIngresoPrg;
        this.presentacionMymovil = presentacionMymovil;
        this.horaIngresoPresentacion = horaIngresoPresentacion;
        this.horaSalidaPrg = horaSalidaPrg;
        this.salidaMymovil = salidaMymovil;
        this.horaSalidaRegistrada = horaSalidaRegistrada;
        this.prgModificada = prgModificada;
        this.nominaBorrada = nominaBorrada;
        this.totalHorasProgramdas = totalHorasProgramdas;
        this.totalHorasReales = totalHorasReales;
        this.ultimaRuta = ultimaRuta;

    }

    public EntradaSalidaJornadaDTO() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreUf() {
        return nombreUf;
    }

    public void setNombreUf(String nombreUf) {
        this.nombreUf = nombreUf;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(String codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getNombreOperador() {
        return nombreOperador;
    }

    public void setNombreOperador(String nombreOperador) {
        this.nombreOperador = nombreOperador;
    }

    public String getHoraIngresoPrg() {
        return horaIngresoPrg;
    }

    public void setHoraIngresoPrg(String horaIngresoPrg) {
        this.horaIngresoPrg = horaIngresoPrg;
    }

    public Integer getPresentacionMymovil() {
        return presentacionMymovil;
    }

    public void setPresentacionMymovil(Integer presentacionMymovil) {
        this.presentacionMymovil = presentacionMymovil;
    }

    public String getHoraIngresoPresentacion() {
        return horaIngresoPresentacion;
    }

    public void setHoraIngresoPresentacion(String horaIngresoPresentacion) {
        this.horaIngresoPresentacion = horaIngresoPresentacion;
    }

    public String getHoraSalidaPrg() {
        return horaSalidaPrg;
    }

    public void setHoraSalidaPrg(String horaSalidaPrg) {
        this.horaSalidaPrg = horaSalidaPrg;
    }

    public Integer getSalidaMymovil() {
        return salidaMymovil;
    }

    public void setSalidaMymovil(Integer salidaMymovil) {
        this.salidaMymovil = salidaMymovil;
    }

    public String getHoraSalidaRegistrada() {
        return horaSalidaRegistrada;
    }

    public void setHoraSalidaRegistrada(String horaSalidaRegistrada) {
        this.horaSalidaRegistrada = horaSalidaRegistrada;
    }

    public Integer getPrgModificada() {
        return prgModificada;
    }

    public void setPrgModificada(Integer prgModificada) {
        this.prgModificada = prgModificada;
    }

    public Integer getNominaBorrada() {
        return nominaBorrada;
    }

    public void setNominaBorrada(Integer nominaBorrada) {
        this.nominaBorrada = nominaBorrada;
    }

    public String getTotalHorasProgramdas() {
        return totalHorasProgramdas;
    }

    public void setTotalHorasProgramdas(String totalHorasProgramdas) {
        this.totalHorasProgramdas = totalHorasProgramdas;
    }

    public String getTotalHorasReales() {
        return totalHorasReales;
    }

    public void setTotalHorasReales(String totalHorasReales) {
        this.totalHorasReales = totalHorasReales;
    }

    public String getUltimaRuta() {
        return ultimaRuta;
    }

    public void setUltimaRuta(String ultimaRuta) {
        this.ultimaRuta = ultimaRuta;
    }

    public String getTiempoExtraRegistrado() {
        return tiempoExtraRegistrado;
    }

    public void setTiempoExtraRegistrado(String tiempoExtraRegistrado) {
        this.tiempoExtraRegistrado = tiempoExtraRegistrado;
    }

}
