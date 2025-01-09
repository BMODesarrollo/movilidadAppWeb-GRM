/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Vehiculo;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author soluciones-it
 */
@Named(value = "prgTcByVehiculoJSF")
@ViewScoped
public class PrgTcByVehiculoJSF implements Serializable {

    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;

    @Inject
    private SelectDispSistemaBean selectDispSistemaBean;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private PrgTc prgTcSelect;
    private Vehiculo vehiculoSelect;
    private List<PrgTc> listPrgTc; // servicios del vehículo by fecha;

    //
    private String codigoVehiculo;
    private Date fecha;

    /**
     * Creates a new instance of PrgTcByVehiculoJSF
     */
    public PrgTcByVehiculoJSF() {
        fecha = new Date();
    }

    public void consultarServiciosVehiculo() {
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar unidad funcional");
            return;
        }
        if (codigoVehiculo == null) {
            MovilidadUtil.addErrorMessage("Código vehículo es requerido");
            reset();
            return;
        }
        vehiculoSelect = vehiculoFacadeLocal.findByCodigo(codigoVehiculo);
        if (vehiculoSelect == null) {
            MovilidadUtil.addErrorMessage("Vehículo no encontrado");
            reset();
            return;
        }
        codigoVehiculo = vehiculoSelect.getCodigo();
        listPrgTc = prgTcFacadeLocal.findServicesByVehiculo(vehiculoSelect.getIdVehiculo(), fecha,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (listPrgTc.isEmpty()) {
            MovilidadUtil.addErrorMessage("Vehículo no cuenta con servicios");
            reset();
        }
        selectDispSistemaBean.consultarSistema();
    }

    public void onRowSelect(SelectEvent event) {
        prgTcSelect = (PrgTc) event.getObject();
        MovilidadUtil.addSuccessMessage("Servicio seleccionado");
    }

    public void reset() {
        prgTcSelect = null;
        vehiculoSelect = null;
        listPrgTc = null;
        codigoVehiculo = null;
        fecha = null;
    }

    public PrgTc getPrgTcSelect() {
        return prgTcSelect;
    }

    public void setPrgTcSelect(PrgTc prgTcSelect) {
        this.prgTcSelect = prgTcSelect;
    }

    public List<PrgTc> getListPrgTc() {
        return listPrgTc;
    }

    public void setListPrgTc(List<PrgTc> listPrgTc) {
        this.listPrgTc = listPrgTc;
    }

    public String getCodigoVehiculo() {
        return codigoVehiculo;
    }

    public void setCodigoVehiculo(String codigoVehiculo) {
        this.codigoVehiculo = codigoVehiculo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
