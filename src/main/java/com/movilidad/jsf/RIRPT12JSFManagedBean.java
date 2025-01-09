/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.EntradaSalidaJornadaDTO;
import com.movilidad.ejb.PrgSerconFacadeLocal;
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
@Named(value = "rIRPT12Bean")
@ViewScoped
public class RIRPT12JSFManagedBean implements Serializable {

    /**
     * Creates a new instance of EntradaSalidaJornadaJSFBean
     */
    public RIRPT12JSFManagedBean() {
    }
    private Date desde;
    private Date hasta;

    private List<EntradaSalidaJornadaDTO> list;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;

    @PostConstruct
    public void init() {
        desde = MovilidadUtil.fechaCompletaHoy();
        hasta = MovilidadUtil.fechaCompletaHoy();
//        consultar();
    }

    public void consultar() {
        list = prgSerconEJB.findEntradaSalidasJornadas(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
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

    public List<EntradaSalidaJornadaDTO> getList() {
        return list;
    }

    public void setList(List<EntradaSalidaJornadaDTO> list) {
        this.list = list;
    }

    public String getTiempoExtraRegistrado(EntradaSalidaJornadaDTO param) {
        int tiempoRestante = MovilidadUtil.toSecs(param.getHoraSalidaRegistrada()) - MovilidadUtil.toSecs(param.getHoraSalidaPrg());
        return tiempoRestante < 0 ? "" : MovilidadUtil.toTimeSec(tiempoRestante);
    }
}
