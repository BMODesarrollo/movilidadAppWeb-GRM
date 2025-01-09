package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@XmlRootElement
public class ReporteServbuses implements Serializable {

    private Date fecha;
    private String codigo;
    private String servbus;
    private String fromdepot;
    private String todepot;
    private String time_origin;
    private String time_destiny;

    public ReporteServbuses(Date fecha, String codigo, String servbus, String fromdepot, String todepot, String time_origin, String time_destiny) {
        this.fecha = fecha;
        this.codigo = codigo;
        this.servbus = servbus;
        this.fromdepot = fromdepot;
        this.todepot = todepot;
        this.time_origin = time_origin;
        this.time_destiny = time_destiny;
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

    public String getFromdepot() {
        return fromdepot;
    }

    public void setFromdepot(String fromdepot) {
        this.fromdepot = fromdepot;
    }

    public String getTodepot() {
        return todepot;
    }

    public void setTodepot(String todepot) {
        this.todepot = todepot;
    }

    public String getTime_origin() {
        return time_origin;
    }

    public void setTime_origin(String time_origin) {
        this.time_origin = time_origin;
    }

    public String getTime_destiny() {
        return time_destiny;
    }

    public void setTime_destiny(String time_destiny) {
        this.time_destiny = time_destiny;
    }

}
