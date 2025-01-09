/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableOperacionCabinaFacadeLocal;
import com.movilidad.model.CableOperacionCabina;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author solucionesit
 */
@Named(value = "reporteOperacionCab")
@ViewScoped
public class ReporteOperacionCabina implements Serializable {

    /**
     * Creates a new instance of ReporteOperacionCabina
     */
    public ReporteOperacionCabina() {
    }

    @EJB
    private CableOperacionCabinaFacadeLocal cableOperacionCabinaEJB;

    private List<CableOperacionCabina> list;

    private Date desde;
    private Date hasta;

    public void consultar() {
        list = cableOperacionCabinaEJB.findByRangoFecha(desde, hasta);
    }

    @PostConstruct
    private void init() {
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
        consultar();
    }

    public List<CableOperacionCabina> getList() {
        return list;
    }

    public void setList(List<CableOperacionCabina> list) {
        this.list = list;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

}
