/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jesús Jiménez
 */
@Entity
@XmlRootElement
public class SumDistanciaEntradaPatioDTO implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "distancia_vehiculo")
    private BigDecimal distanciaVehiculo;

    @Column(name = "codigo_vehiculo")
    private String codigoVehiculo;

    @Column(name = "placa_vehiculo")
    private String placaVehiculo;

    @Column(name = "tipo_vehiculo")
    private String tipoVehiculo;

    @Column(name = "entrada_patio")
    private String entradaPatio;

    @Column(name = "lavado_si_no")
    private String lavadoSiNo;

    @Column(name = "ultima_fecha_lavado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechaLavado;

    @Column(name = "estado_vehiculo")
    private String estadoVehiculo;

    @Column(name = "nombre_tipo_novedad")
    private String nombreTipoNovedad;

    @Column(name = "titulo_tipo_novedad")
    private String tituloTipoNovedad;

    @Column(name = "fecha_novedad")
    @Temporal(TemporalType.DATE)
    private Date fechaNovedad;

    @Transient
    private CurrentStatusVehicleGEO currentStatusVehicleGEO;
    @Transient
    private String criterio;
    @Transient
    private float autonomiaCarga;

    public SumDistanciaEntradaPatioDTO() {
    }

    public SumDistanciaEntradaPatioDTO(Integer id, BigDecimal distanciaVehiculo, String codigoVehiculo, String placaVehiculo, String tipoVehiculo, String entradaPatio) {
        this.id = id;
        this.distanciaVehiculo = distanciaVehiculo;
        this.codigoVehiculo = codigoVehiculo;
        this.placaVehiculo = placaVehiculo;
        this.tipoVehiculo = tipoVehiculo;
        this.entradaPatio = entradaPatio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDistanciaVehiculo() {
        return distanciaVehiculo;
    }

    public void setDistanciaVehiculo(BigDecimal distanciaVehiculo) {
        this.distanciaVehiculo = distanciaVehiculo;
    }

    public String getEntradaPatio() {
        return entradaPatio;
    }

    public void setEntradaPatio(String entradaPatio) {
        this.entradaPatio = entradaPatio;
    }

    public String getCodigoVehiculo() {
        return codigoVehiculo;
    }

    public void setCodigoVehiculo(String codigoVehiculo) {
        this.codigoVehiculo = codigoVehiculo;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public CurrentStatusVehicleGEO getCurrentStatusVehicleGEO() {
        return currentStatusVehicleGEO;
    }

    public void setCurrentStatusVehicleGEO(CurrentStatusVehicleGEO currentStatusVehicleGEO) {
        this.currentStatusVehicleGEO = currentStatusVehicleGEO;
    }

    public String getLavadoSiNo() {
        return lavadoSiNo;
    }

    public void setLavadoSiNo(String lavadoSiNo) {
        this.lavadoSiNo = lavadoSiNo;
    }

    public Date getUltimaFechaLavado() {
        return ultimaFechaLavado;
    }

    public void setUltimaFechaLavado(Date ultimaFechaLavado) {
        this.ultimaFechaLavado = ultimaFechaLavado;
    }

    public String getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(String estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }

    public String getNombreTipoNovedad() {
        return nombreTipoNovedad;
    }

    public void setNombreTipoNovedad(String nombreTipoNovedad) {
        this.nombreTipoNovedad = nombreTipoNovedad;
    }

    public String getTituloTipoNovedad() {
        return tituloTipoNovedad;
    }

    public void setTituloTipoNovedad(String tituloTipoNovedad) {
        this.tituloTipoNovedad = tituloTipoNovedad;
    }

    public Date getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(Date fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public String obtenerHoraEntrada() {
        return Util.isStringNullOrEmpty(this.entradaPatio) ? "" : this.entradaPatio.split("-")[0];
    }

    public String obtenerPatioEntrada() {
        return Util.isStringNullOrEmpty(this.entradaPatio) ? "" : this.entradaPatio.split("-")[1];
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public float getAutonomiaCarga() {
        return autonomiaCarga;
    }

    public void setAutonomiaCarga(float autonomiaCarga) {
        this.autonomiaCarga = autonomiaCarga;
    }

    public void calcularCriterio() {
        if (this.currentStatusVehicleGEO == null) {
            return;
        }
        if (this.currentStatusVehicleGEO.getNivelRestanteEnergia() == null) {
            return;
        }
        if (this.currentStatusVehicleGEO.getNivelRestanteEnergia() <= 25) {
            this.criterio = "Critico";
        }
        if (this.currentStatusVehicleGEO.getNivelRestanteEnergia() > 25
                && this.currentStatusVehicleGEO.getNivelRestanteEnergia() < 50) {
            this.criterio = "Validar";
        }
        if (this.currentStatusVehicleGEO.getNivelRestanteEnergia() >= 50
                && this.currentStatusVehicleGEO.getNivelRestanteEnergia() < 75) {
            this.criterio = "Revisar";
        }
        if (this.currentStatusVehicleGEO.getNivelRestanteEnergia() >= 75) {
            this.criterio = "OK";
        }
    }

    public void calcularKmSegunCarga(Integer kmFor100) {
        if (this.currentStatusVehicleGEO == null) {
            return;
        }
        if (this.currentStatusVehicleGEO.getNivelRestanteEnergia() == null) {
            return;
        }
        this.autonomiaCarga = kmFor100 * this.currentStatusVehicleGEO.getNivelRestanteEnergia();
    }

}
