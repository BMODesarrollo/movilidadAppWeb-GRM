/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaJornadaDetFacadeLocal;
import com.movilidad.model.GenericaJornadaDet;
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
@Named(value = "genJornadaBean")
@ViewScoped
public class GenericaJornadaBean implements Serializable {

    /**
     * Creates a new instance of GenericaJornadaBean
     */
    public GenericaJornadaBean() {
    }

    @EJB
    private GenericaJornadaDetFacadeLocal prgSerconDetEJB;
    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();
    private List<GenericaJornadaDet> listGenericaJornadaDet;

    public void consultar() {
        listGenericaJornadaDet = prgSerconDetEJB.findByRangoFecha(desde, hasta);
    }

    @PostConstruct
    public void init() {
        consultar();
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

    public List<GenericaJornadaDet> getListGenericaJornadaDet() {
        return listGenericaJornadaDet;
    }

    public void setListGenericaJornadaDet(List<GenericaJornadaDet> listGenericaJornadaDet) {
        this.listGenericaJornadaDet = listGenericaJornadaDet;
    }
}
