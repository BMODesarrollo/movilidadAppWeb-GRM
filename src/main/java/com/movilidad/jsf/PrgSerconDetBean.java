/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgSerconDetFacadeLocal;
import com.movilidad.model.PrgSerconDet;
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
@Named(value = "prgSerconDetBean")
@ViewScoped
public class PrgSerconDetBean implements Serializable {

    @EJB
    private PrgSerconDetFacadeLocal prgSerconDetEJB;
    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();
    private List<PrgSerconDet> listPrgSerconDet;

    public void consultar() {
        listPrgSerconDet = prgSerconDetEJB.findByRangoFecha(desde, hasta);
    }

    @PostConstruct
    public void init() {
        consultar();
    }

    /**
     * Creates a new instance of PrgSerconDetBean
     */
    public PrgSerconDetBean() {
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

    public List<PrgSerconDet> getListPrgSerconDet() {
        return listPrgSerconDet;
    }

    public void setListPrgSerconDet(List<PrgSerconDet> listPrgSerconDet) {
        this.listPrgSerconDet = listPrgSerconDet;
    }

}
