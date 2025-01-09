package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author solucionesit
 */
public class GenericaJornadaObj implements Serializable {

    private Date fecha;
    private String identificacion;
    private String timeOrigin;
    private String timeDestiny;
    private String hiniTurno1;
    private String hfinTurno1;
    private String hiniTurno2;
    private String hfinTurno2;
    private String hiniTurno3;
    private String hfinTurno3;
    private String timeProduction;
    private String compensatorio;
    private String tipoJornada;
    private String motivoJornada;
    private String nombre;
    private String tarea;
    private String observacion;

    public GenericaJornadaObj() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getTimeDestiny() {
        return timeDestiny;
    }

    public void setTimeDestiny(String timeDestiny) {
        this.timeDestiny = timeDestiny;
    }

    public String getTimeProduction() {
        return timeProduction;
    }

    public void setTimeProduction(String timeProduction) {
        this.timeProduction = timeProduction;
    }

    public String getCompensatorio() {
        return compensatorio;
    }

    public void setCompensatorio(String compensatorio) {
        this.compensatorio = compensatorio;
    }

    public String getTipoJornada() {
        return tipoJornada;
    }

    public void setTipoJornada(String tipoJornada) {
        this.tipoJornada = tipoJornada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getHiniTurno1() {
        return hiniTurno1;
    }

    public void setHiniTurno1(String hiniTurno1) {
        this.hiniTurno1 = hiniTurno1;
    }

    public String getHfinTurno1() {
        return hfinTurno1;
    }

    public void setHfinTurno1(String hfinTurno1) {
        this.hfinTurno1 = hfinTurno1;
    }

    public String getHiniTurno2() {
        return hiniTurno2;
    }

    public void setHiniTurno2(String hiniTurno2) {
        this.hiniTurno2 = hiniTurno2;
    }

    public String getHfinTurno2() {
        return hfinTurno2;
    }

    public void setHfinTurno2(String hfinTurno2) {
        this.hfinTurno2 = hfinTurno2;
    }

    public String getHiniTurno3() {
        return hiniTurno3;
    }

    public void setHiniTurno3(String hiniTurno3) {
        this.hiniTurno3 = hiniTurno3;
    }

    public String getHfinTurno3() {
        return hfinTurno3;
    }

    public void setHfinTurno3(String hfinTurno3) {
        this.hfinTurno3 = hfinTurno3;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getMotivoJornada() {
        return motivoJornada;
    }

    public void setMotivoJornada(String motivoJornada) {
        this.motivoJornada = motivoJornada;
    }

    @Override
    public String toString() {
        return "GenericaJornadaObj{" + "fecha=" + fecha + ", identificacion=" + identificacion + ", timeOrigin=" + timeOrigin + ", timeDestiny=" + timeDestiny + ", timeProduction=" + timeProduction + ", compensatorio=" + compensatorio + ", tipoJornada=" + tipoJornada + ", nombre=" + nombre + ", tarea=" + tarea + '}';
    }

}
