package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@XmlRootElement
public class ReporteServbusesPatioFin implements Serializable {

    private Date fecha;
    private String codigo;
    private String servbus;
    private String todepot;
    private String hora_entrada;

    public ReporteServbusesPatioFin(Date fecha, String codigo, String servbus, String todepot, String hora_entrada) {
        this.fecha = fecha;
        this.codigo = codigo;
        this.servbus = servbus;
        this.todepot = todepot;
        this.hora_entrada = hora_entrada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public String getTodepot() {
        return todepot;
    }

    public void setTodepot(String todepot) {
        this.todepot = todepot;
    }

    public String getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(String hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

}
