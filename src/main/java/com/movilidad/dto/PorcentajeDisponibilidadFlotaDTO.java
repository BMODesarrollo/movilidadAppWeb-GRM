/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class PorcentajeDisponibilidadFlotaDTO {

    private Date fecha;
    @Column(name = "id_uf")
    private Integer idUf;
    @Column(name = "nombre_uf")
    private String nombreUnidadFunc;
    @Column(name = "total_flota")
    private long totalFlota;
    @Column(name = "total_disponibles")
    private long totalDisponibles;
    @Column(name = "total_inoperativos")
    private long totalInoperativos;
    @Column(name = "porcentaje_dispo")
    private double porcentajeDispo;
    @Column(name = "nombre_tipo_vehiculo")
    private String nombreTipoVehiculo;

    public PorcentajeDisponibilidadFlotaDTO(Integer idUf, String nombreUnidadFunc, Date fecha,
            long totalFlota, long totalDisponibles, long totalInoperativos,
            double porcentajeDispo, String nombreTipoVehiculo) {
        this.idUf = idUf;
        this.nombreUnidadFunc = nombreUnidadFunc;
        this.fecha = fecha;
        this.totalFlota = totalFlota;
        this.totalDisponibles = totalDisponibles;
        this.totalInoperativos = totalInoperativos;
        this.porcentajeDispo = porcentajeDispo;
        this.nombreTipoVehiculo = nombreTipoVehiculo;
    }

    public Integer getIdUf() {
        return idUf;
    }

    public void setIdUf(Integer idUf) {
        this.idUf = idUf;
    }

    public String getNombreUf() {
        return nombreUnidadFunc;
    }

    public void setNombreUf(String nombreUnidadFunc) {
        this.nombreUnidadFunc = nombreUnidadFunc;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getTotalFlota() {
        return totalFlota;
    }

    public void setTotalFlota(long totalFlota) {
        this.totalFlota = totalFlota;
    }

    public long getTotalDisponibles() {
        return totalDisponibles;
    }

    public void setTotalDisponibles(long totalDisponibles) {
        this.totalDisponibles = totalDisponibles;
    }

    public long getTotalInoperativos() {
        return totalInoperativos;
    }

    public void setTotalInoperativos(long totalInoperativos) {
        this.totalInoperativos = totalInoperativos;
    }

    public double getPorcentajeDispo() {
        return porcentajeDispo;
    }

    public void setPorcentajeDispo(double porcentajeDispo) {
        this.porcentajeDispo = porcentajeDispo;
    }

    public String getNombreTipoVehiculo() {
        return nombreTipoVehiculo;
    }

    public void setNombreTipoVehiculo(String nombreTipoVehiculo) {
        this.nombreTipoVehiculo = nombreTipoVehiculo;
    }

}
