/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class KmPrgEjecDTO implements Serializable {

    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "nombre_uf")
    private String nombreUf;
    @Column(name = "nombre_tipo_vehiculo")
    private String nombreTipoVehiculo;
    @Column(name = "id_vehiculo_tipo")
    private Integer idVehiculoTipo;
    @Column(name = "km_programado")
    private Long kmProgramado;
    @Column(name = "km_ejecutado")
    private Long kmEjecutado;
    @Column(name = "tipo_dia")
    private String tipoDia;
    @Column(name = "estacionalidad")
    private Integer estacionalidad;

    public KmPrgEjecDTO(Date fecha, String nombreUf, String nombreTipoVehiculo, Integer idVehiculoTipo, Long kmProgramado, Long kmEjecutado, String tipoDia, Integer estacionalidad) {
        this.fecha = fecha;
        this.nombreUf = nombreUf;
        this.nombreTipoVehiculo = nombreTipoVehiculo;
        this.idVehiculoTipo = idVehiculoTipo;
        this.kmProgramado = kmProgramado;
        this.kmEjecutado = kmEjecutado;
        this.tipoDia = tipoDia;
        this.estacionalidad = estacionalidad;
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

    public String getNombreTipoVehiculo() {
        return nombreTipoVehiculo;
    }

    public void setNombreTipoVehiculo(String nombreTipoVehiculo) {
        this.nombreTipoVehiculo = nombreTipoVehiculo;
    }

    public Integer getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(Integer idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    public Long getKmProgramado() {
        return kmProgramado;
    }

    public void setKmProgramado(Long kmProgramado) {
        this.kmProgramado = kmProgramado;
    }

    public Long getKmEjecutado() {
        return kmEjecutado;
    }

    public void setKmEjecutado(Long kmEjecutado) {
        this.kmEjecutado = kmEjecutado;
    }

    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }

    public Integer getEstacionalidad() {
        return estacionalidad;
    }

    public void setEstacionalidad(Integer estacionalidad) {
        this.estacionalidad = estacionalidad;
    }

}
