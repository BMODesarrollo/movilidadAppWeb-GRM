/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 * @func Cantidad de novedades por tipo detalle en un rango de fechas
 */
@XmlRootElement
public class GestionNovedad implements Serializable {

    private Date fecha;
    private Integer idNovedadTipoDetalle;
    private Integer idGopUnidadFuncional;
    private Integer idEmpleadoTipoCargo;
    private Integer sumaGestor;
    private long valor;

    public GestionNovedad(Date fecha, Integer idNovedadTipoDetalle, Integer idGopUnidadFuncional, Integer idEmpleadoTipoCargo, Integer sumaGestor, long valor) {
        this.fecha = fecha;
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
        this.idGopUnidadFuncional = idGopUnidadFuncional;
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
        this.sumaGestor = sumaGestor;
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdNovedadTipoDetalle() {
        return idNovedadTipoDetalle;
    }

    public void setIdNovedadTipoDetalle(Integer idNovedadTipoDetalle) {
        this.idNovedadTipoDetalle = idNovedadTipoDetalle;
    }

    public Integer getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(Integer idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public Integer getIdEmpleadoTipoCargo() {
        return idEmpleadoTipoCargo;
    }

    public void setIdEmpleadoTipoCargo(Integer idEmpleadoTipoCargo) {
        this.idEmpleadoTipoCargo = idEmpleadoTipoCargo;
    }

    public Integer getSumaGestor() {
        return sumaGestor;
    }

    public void setSumaGestor(Integer sumaGestor) {
        this.sumaGestor = sumaGestor;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }

}
