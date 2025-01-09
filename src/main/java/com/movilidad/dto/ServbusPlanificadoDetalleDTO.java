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
 * @author Julián Arévalo
 */
@XmlRootElement
public class ServbusPlanificadoDetalleDTO implements Serializable {

    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "servbus")
    private String servbus;
    @Column(name = "tipologia")
    private String tipologia;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "codigo_tm")
    private String codigoTm;
    @Column(name = "time_origin")
    private String timeOrigin;
    @Column(name = "time_destiny")
    private String timeDestiny;
    @Column(name = "codigo_bus")
    private String codigoBus;
    @Column(name = "uf")
    private String unidadFuncional;
    @Column(name = "tipo_tarea")
    private String tipoTarea;
    @Column(name = "km")
    private Long km;
    @Column(name = "estado_operacion")
    private String estadoOperacion;
    @Column(name = "tipo_novedad")
    private String tipoNovedad;
    @Column(name = "tipo_novedad_detalle")
    private String tipoNovedadDetalle;
    @Column(name = "motivo")
    private String motivo;

    public ServbusPlanificadoDetalleDTO(Date fecha, String servbus, String tipologia, String nombre, String codigoTm, String timeOrigin, String timeDestiny, String codigoBus, String unidadFuncional, String tipoTarea, Long km, String estadoOperacion, String tipoNovedad, String tipoNovedadDetalle, String motivo) {
        this.fecha = fecha;
        this.servbus = servbus;
        this.tipologia = tipologia;
        this.nombre = nombre;
        this.codigoTm = codigoTm;
        this.timeOrigin = timeOrigin;
        this.timeDestiny = timeDestiny;
        this.codigoBus = codigoBus;
        this.unidadFuncional = unidadFuncional;
        this.tipoTarea = tipoTarea;
        this.km = km;
        this.estadoOperacion = estadoOperacion;
        this.tipoNovedad = tipoNovedad;
        this.tipoNovedadDetalle = tipoNovedadDetalle;
        this.motivo = motivo;
    }
    
        public ServbusPlanificadoDetalleDTO(Date fecha, String servbus, String tipologia, String nombre, String codigoTm, String timeOrigin, String timeDestiny, String codigoBus, String unidadFuncional, String tipoTarea, Long km, String estadoOperacion) {
        this.fecha = fecha;
        this.servbus = servbus;
        this.tipologia = tipologia;
        this.nombre = nombre;
        this.codigoTm = codigoTm;
        this.timeOrigin = timeOrigin;
        this.timeDestiny = timeDestiny;
        this.codigoBus = codigoBus;
        this.unidadFuncional = unidadFuncional;
        this.tipoTarea = tipoTarea;
        this.km = km;
        this.estadoOperacion = estadoOperacion;
    }


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(String codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(String timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public String getTimeDestiny() {
        return timeDestiny;
    }

    public void setTimeDestiny(String timeDestiny) {
        this.timeDestiny = timeDestiny;
    }

    public String getCodigoBus() {
        return codigoBus;
    }

    public void setCodigoBus(String codigoBus) {
        this.codigoBus = codigoBus;
    }

    public String getUnidadFuncional() {
        return unidadFuncional;
    }

    public void setUnidadFuncional(String unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
    }

    public String getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(String tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public Long getKm() {
        return km;
    }

    public void setKm(Long km) {
        this.km = km;
    }

    public String getEstadoOperacion() {
        return estadoOperacion;
    }

    public void setEstadoOperacion(String estadoOperacion) {
        this.estadoOperacion = estadoOperacion;
    }

    public String getTipoNovedad() {
        return tipoNovedad;
    }

    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    public String getTipoNovedadDetalle() {
        return tipoNovedadDetalle;
    }

    public void setTipoNovedadDetalle(String tipoNovedadDetalle) {
        this.tipoNovedadDetalle = tipoNovedadDetalle;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
