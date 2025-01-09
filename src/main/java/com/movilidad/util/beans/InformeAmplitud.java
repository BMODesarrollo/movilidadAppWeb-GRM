package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 */
@XmlRootElement
public class InformeAmplitud implements Serializable {

    private Date fecha;
    private int codigo_tm;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private String amplitude;
    private long veces;

    public InformeAmplitud(Date fecha, int codigo_tm, String identificacion, String nombres, String apellidos, String amplitude, long veces) {
        this.fecha = fecha;
        this.codigo_tm = codigo_tm;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.amplitude = amplitude;
        this.veces = veces;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCodigo_tm() {
        return codigo_tm;
    }

    public void setCodigo_tm(int codigo_tm) {
        this.codigo_tm = codigo_tm;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(String amplitude) {
        this.amplitude = amplitude;
    }

    public long getVeces() {
        return veces;
    }

    public void setVeces(long veces) {
        this.veces = veces;
    }

}
