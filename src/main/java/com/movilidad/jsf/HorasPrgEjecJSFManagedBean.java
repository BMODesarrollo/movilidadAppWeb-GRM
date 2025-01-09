/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.HoraPrgEjecDTO;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 * Horas de producción acumuladas por operador
 *
 * @author solucionesit
 */
@Named(value = "HorasPrgEjecBean")
@ViewScoped
public class HorasPrgEjecJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of BusesPlanificadosJSFManagedBean
     */
    public HorasPrgEjecJSFManagedBean() {
    }
    @EJB
    private PrgSerconFacadeLocal prgSerconFacadeLocal;
    
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    
    private Date desde = MovilidadUtil.fechaCompletaHoy();
    private Date hasta = MovilidadUtil.fechaCompletaHoy();
    private List<HoraPrgEjecDTO> list;

    public void generarReporte() {
    }

    public void consultar() {
        list = prgSerconFacadeLocal.getHoraPrgEjec(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
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

    public List<HoraPrgEjecDTO> getList() {
        return list;
    }

    public void setList(List<HoraPrgEjecDTO> list) {
        this.list = list;
    }

}
