package com.movilidad.util.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 * @func Información detallada de accidentes para informe de control
 */
@XmlRootElement
public class AccidenteCtrl implements Serializable {

    private int codigo_operador;
    private String nombre_operador;
    private String codigo_vehiculo;
    private long incidente;
    private long percance;
    private long TM01;
    private long TM02;
    private long TM16;

    public AccidenteCtrl(int codigo_operador, String codigo_vehiculo, String nombre_operador, long incidente, long percance, long TM01, long TM02, long TM16) {
        this.codigo_operador = codigo_operador;
        this.codigo_vehiculo = codigo_vehiculo;
        this.nombre_operador = nombre_operador;
        this.incidente = incidente;
        this.percance = percance;
        this.TM01 = TM01;
        this.TM02 = TM02;
        this.TM16 = TM16;
    }

    public int getCodigo_operador() {
        return codigo_operador;
    }

    public void setCodigo_operador(int codigo_operador) {
        this.codigo_operador = codigo_operador;
    }

    public String getCodigo_vehiculo() {
        return codigo_vehiculo;
    }

    public void setCodigo_vehiculo(String codigo_vehiculo) {
        this.codigo_vehiculo = codigo_vehiculo;
    }

    public String getNombre_operador() {
        return nombre_operador;
    }

    public void setNombre_operador(String nombre_operador) {
        this.nombre_operador = nombre_operador;
    }

    public long getIncidente() {
        return incidente;
    }

    public void setIncidente(long incidente) {
        this.incidente = incidente;
    }

    public long getPercance() {
        return percance;
    }

    public void setPercance(long percance) {
        this.percance = percance;
    }

    public long getTM01() {
        return TM01;
    }

    public void setTM01(long TM01) {
        this.TM01 = TM01;
    }

    public long getTM02() {
        return TM02;
    }

    public void setTM02(long TM02) {
        this.TM02 = TM02;
    }

    public long getTM16() {
        return TM16;
    }

    public void setTM16(long TM16) {
        this.TM16 = TM16;
    }
}
