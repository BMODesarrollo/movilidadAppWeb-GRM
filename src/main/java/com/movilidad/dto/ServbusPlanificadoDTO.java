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
public class ServbusPlanificadoDTO implements Serializable {

    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "id_uf")
    private int idUf;
    @Column(name = "nombre_uf")
    private String nombreUf;
    @Column(name = "nombre_tipo_vehiculo")
    private String nombreTipoVehiculo;
    @Column(name = "id_vehiculo_tipo")
    private Integer idVehiculoTipo;
    @Column(name = "total_programado")
    private Long totalProgramado;
    @Column(name = "total_asignado")
    private Long totalasignado;
    @Column(name = "tipo_dia")
    private String tipoDia;
    @Column(name = "estacionalidad")
    private Integer estacionalidad;

    public ServbusPlanificadoDTO(Date fecha, int idUf, String nombreUf, String nombreTipoVehiculo, Integer idVehiculoTipo, Long totalProgramado, Long totalasignado, String tipoDia, Integer estacionalidad) {
        this.fecha = fecha;
        this.idUf = idUf;
        this.nombreUf = nombreUf;
        this.nombreTipoVehiculo = nombreTipoVehiculo;
        this.idVehiculoTipo = idVehiculoTipo;
        this.totalProgramado = totalProgramado;
        this.totalasignado = totalasignado;
        this.tipoDia = tipoDia;
        this.estacionalidad = estacionalidad;
    }

    

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public int getIdUf() {
        return idUf;
    }

    public void setIdUf(int idUf) {
        this.idUf = idUf;
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

    public Long getTotalProgramado() {
        return totalProgramado;
    }

    public void setTotalProgramado(Long totalProgramado) {
        this.totalProgramado = totalProgramado;
    }

    public Long getTotalasignado() {
        return totalasignado;
    }

    public void setTotalasignado(Long totalasignado) {
        this.totalasignado = totalasignado;
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
