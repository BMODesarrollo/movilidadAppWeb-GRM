package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@XmlRootElement
public class ReporteInterventoria implements Serializable {

    private Date fecha;
    private String cedula;
    private String nombre_completo;
    private String nombre_cargo;
    private String hora_inicio;
    private String hora_fin;

    public ReporteInterventoria(Date fecha, String cedula, String nombre_completo, String nombre_cargo, String hora_inicio, String hora_fin) {
        this.fecha = fecha;
        this.cedula = cedula;
        this.nombre_completo = nombre_completo;
        this.nombre_cargo = nombre_cargo;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getNombre_cargo() {
        return nombre_cargo;
    }

    public void setNombre_cargo(String nombre_cargo) {
        this.nombre_cargo = nombre_cargo;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

}
