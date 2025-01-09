/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.util.beans.BitacoraAccidentalidad;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "bitacoraAccBean")
@ViewScoped
public class BitacoraAccBean implements Serializable {

    @EJB
    private AccidenteFacadeLocal accidenteEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<BitacoraAccidentalidad> lista;

    private Date fecha;
    private Date fechaFin;

    @PostConstruct
    public void init() {
        fecha = MovilidadUtil.fechaHoy();
        fechaFin = MovilidadUtil.fechaHoy();
        consultar();
    }

    public void consultar() {
        if (Util.validarFechaCambioEstado(fecha, fechaFin)) {
            MovilidadUtil.addErrorMessage("La fecha inicio no puede ser mayor a la fecha final");
            PrimeFaces.current().ajax().update(":msgs");
            return;
        }

        lista = accidenteEjb.obtenerDatosBitacoraAcc(fecha, fechaFin,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
        );

        if (lista == null) {
            MovilidadUtil.addErrorMessage("No existen registros para ése rango de fechas");
            PrimeFaces.current().ajax().update(":msgs");
        }
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<BitacoraAccidentalidad> getLista() {
        return lista;
    }

    public void setLista(List<BitacoraAccidentalidad> lista) {
        this.lista = lista;
    }

}
