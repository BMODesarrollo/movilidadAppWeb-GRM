package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Omar.beltran
 */
@XmlRootElement
public class NovedadesMarcaciones implements Serializable {

    private String id_empleado;
    private Date fecha;
    private String time_origin;
    private String time_destiny;
    private String bio_entrada;
    private String bio_salida;
    private String estado_marcacion;

    public NovedadesMarcaciones(String id_empleado, Date fecha, String time_origin, String time_destiny, String bio_entrada, String bio_salida, String estado_marcacion) {
        this.id_empleado = id_empleado;
        this.fecha = fecha;
        this.time_origin = time_origin;
        this.time_destiny = time_destiny;
        this.bio_entrada = bio_entrada;
        this.bio_salida = bio_salida;
        this.estado_marcacion = estado_marcacion;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public String getBio_entrada() {
        return bio_entrada;
    }

    public void setBio_entrada(String bio_entrada) {
        this.bio_entrada = bio_entrada;
    }

    public String getBio_salida() {
        return bio_salida;
    }

    public void setBio_salida(String bio_salida) {
        this.bio_salida = bio_salida;
    }

    public String getEstado_marcacion() {
        return estado_marcacion;
    }

    public void setEstado_marcacion(String estado_marcacion) {
        this.estado_marcacion = estado_marcacion;
    }
    
}
