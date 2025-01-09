/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.ServbusPlanificadoDTO;
import com.movilidad.dto.ServbusPlanificadoDetalleDTO;
import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.ParamFeriado;
import com.movilidad.model.PrgTc;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 * Buses planificados para la operación por tipo día y estacionalidad.
 *
 * @author solucionesit
 */
@Named(value = "busesPlanificadosBean")
@ViewScoped
public class BusesPlanificadosJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of BusesPlanificadosJSFManagedBean
     */
    public BusesPlanificadosJSFManagedBean() {
    }
    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @EJB
    private ParamFeriadoFacadeLocal paramFeriadoEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private Date desde = MovilidadUtil.fechaCompletaHoy();
    private Date hasta = MovilidadUtil.fechaCompletaHoy();
    private List<ServbusPlanificadoDTO> list;
    private List<ServbusPlanificadoDetalleDTO> listDetalle;
    private List<PrgTc> listPrgTc;

    public void generarReporte() {
    }

    public void consultar() {
        list = prgTcFacadeLocal.findBusesPlanificadosByRangeDate(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listPrgTc = prgTcFacadeLocal.findPrimerasTareasServbus(desde, hasta);
        if (!list.isEmpty()) {
            for (ServbusPlanificadoDTO objPlanificados : list) {
                int count = 0;
                for (PrgTc obj : listPrgTc) {
                    if (Objects.equals(obj.getFecha(), objPlanificados.getFecha())
                            && Objects.equals(obj.getIdVehiculoTipo().getIdVehiculoTipo(), objPlanificados.getIdVehiculoTipo())
                            && Objects.equals(obj.getIdGopUnidadFuncional().getIdGopUnidadFuncional(), objPlanificados.getIdUf())) {
                        if (obj.getIdVehiculo() != null && obj.getIdEmpleado() != null) {
                            count = count + 1;
                        }
                    }
                }
                objPlanificados.setTotalasignado((long) count);
            }
        }
    }

    public void consultarDetalle() {
        listDetalle = prgTcFacadeLocal.findBusesPlanificadosDetalleByRangeDate(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    /**
     * Método que se encarga de devolver el tipo de dia en base a una fecha
     *
     * @param fecha
     * @return String
     */
    public String getTipoDia(Date fecha) {

        ParamFeriado paramFeriado = paramFeriadoEjb.findByFecha(fecha);

        if (paramFeriado != null) {
            return "Festivo";
        }

        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        int diaSemana = c.get(Calendar.DAY_OF_WEEK);

        if (diaSemana >= 2 && diaSemana <= 6) {
            return "Hábil";
        } else if (diaSemana == 7) {
            return "Sábado";
        } else {
            return "Domingo";
        }

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

    public List<ServbusPlanificadoDTO> getList() {
        return list;
    }

    public void setList(List<ServbusPlanificadoDTO> list) {
        this.list = list;
    }

    public List<ServbusPlanificadoDetalleDTO> getListDetalle() {
        return listDetalle;
    }

    public void setListDetalle(List<ServbusPlanificadoDetalleDTO> listDetalle) {
        this.listDetalle = listDetalle;
    }
}
