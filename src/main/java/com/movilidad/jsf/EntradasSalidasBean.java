/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.PrgTc;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author solucionesit
 */
@Named(value = "entradasSalidasBean")
@ViewScoped
public class EntradasSalidasBean implements Serializable {

    /**
     * Creates a new instance of EntradasSalidasBean
     */
    public EntradasSalidasBean() {
    }
    @EJB
    private PrgTcFacadeLocal prgTcEJB;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<PrgTc> prgTcSalidas = null;
    private List<PrgTc> prgTcEntradas = null;
    private Date fecha;

    @PostConstruct
    public void init() {
        fecha = MovilidadUtil.fechaHoy();
        consultar();
    }

    /**
     * Cargar las listas de entradas y salidas de los vehículos para los
     * reportes de entradas y salidas de flota, la fecha es seleccionada desde
     * el modulo de entradas y salida de flota.
     */
    public void consultar() {
        if (fecha == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una fecha");
            return;
        }

        prgTcEntradas = prgTcEJB.listarSerbus(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        prgTcSalidas = prgTcEJB.listarSalidas(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public List<PrgTc> getPrgTcSalidas() {
        return prgTcSalidas;
    }

    public void setPrgTcSalidas(List<PrgTc> prgTcSalidas) {
        this.prgTcSalidas = prgTcSalidas;
    }

    public List<PrgTc> getPrgTcEntradas() {
        return prgTcEntradas;
    }

    public void setPrgTcEntradas(List<PrgTc> prgTcEntradas) {
        this.prgTcEntradas = prgTcEntradas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
