/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.model.PrgSercon;
import com.movilidad.utils.ConstantsUtil;
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

/**
 *
 * @author solucionesit
 */
@Named(value = "serconConfirmBean")
@ViewScoped
public class SerconConfirmBean implements Serializable {

    /**
     * Creates a new instance of MyAppSerconConfirmBean
     */
    public SerconConfirmBean() {
    }
    @EJB
    private PrgSerconFacadeLocal prgSerconFacadeLocal;

    //
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<PrgSercon> list;
    private Date desde;
    private Date hasta;

    private int idGopUF;

    @PostConstruct
    public void init() {
        desde = hasta = MovilidadUtil.fechaHoy();
        consultar();
    }

    public void consultar() {
        cargarUF();
        list = prgSerconFacadeLocal.findAllRangoFechasWithConfirmation(desde, hasta, idGopUF);
    }

    public String horaFinalTurno(PrgSercon ps) {
        if (ps.getAutorizado() == null) {
            return ps.getTimeDestiny();
        }
        if (ps.getAutorizado().equals(ConstantsUtil.OFF_INT)) {
            return ps.getTimeDestiny();
        }
        if ((!Util.isStringNullOrEmpty(ps.getRealHfinTurno3()))
                && MovilidadUtil.toSecs(ps.getRealHfinTurno3()) > 0) {
            return ps.getRealHfinTurno3();
        }
        if ((!Util.isStringNullOrEmpty(ps.getRealHfinTurno2()))
                && MovilidadUtil.toSecs(ps.getRealHfinTurno2()) > 0) {
            return ps.getRealHfinTurno2();
        }
        return ps.getRealTimeDestiny();
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public List<PrgSercon> getList() {
        return list;
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
