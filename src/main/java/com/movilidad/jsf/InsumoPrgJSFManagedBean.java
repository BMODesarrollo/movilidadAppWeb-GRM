/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.InsumoProgramacionDTO;
import com.movilidad.ejb.PrgTcFacadeLocal;
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
 *
 * Reporte según plantilla definida como insumo para el área de programación que
 * contenga: ingresos, renuncias, incapacitados, aislamiento, licencias,
 * disponibles, sancionados, capacitación, operadores de patio, solicitudes de
 * los operadores, solicitudes de analista de operaciones.
 */
@Named(value = "insumoPrgBean")
@ViewScoped
public class InsumoPrgJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of UltimosServiciosJSFManagedBean
     */
    public InsumoPrgJSFManagedBean() {
    }

    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private Date desde;
    private Date hasta;
    private List<InsumoProgramacionDTO> list;

    @PostConstruct
    public void init() {
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
    }

    public void consultar() {
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar la Unidad Funcional.");
            return;
        }
        list = prgTcFacadeLocal.findInsumoProgramacion(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
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

    public List<InsumoProgramacionDTO> getList() {
        return list;
    }

    public void setList(List<InsumoProgramacionDTO> list) {
        this.list = list;
    }

}
