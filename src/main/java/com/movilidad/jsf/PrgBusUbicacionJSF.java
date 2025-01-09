/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgBusUbicacionFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.PrgBusUbicacion;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Vehiculo;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HP
 */
@Named(value = "prgBusUbicacion")
@ViewScoped
public class PrgBusUbicacionJSF implements Serializable {

    @EJB
    private PrgBusUbicacionFacadeLocal busUbicacionFacadeLocal;

    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;

    private PrgBusUbicacion busUbicacion;
    private PrgTc prgTc;
    private Vehiculo vehiculo;

    private int id_patio = 0;
    private String c_vehiculo = "";

    public PrgBusUbicacionJSF() {
    }

    public void onRowSelect(SelectEvent event) {
        busUbicacion = new PrgBusUbicacion();
        prgTc = new PrgTc();
        prgTc = (PrgTc) event.getObject();
    }

    public void guardarUbicacionBus() {
        if (prgTc != null) {
            if(prgTc.getIdVehiculo() == null){
                MovilidadUtil.addErrorMessage("No puede ingresar a patio un servicio sin vehículo");
                return;
            }
            busUbicacion.setIdPatio(prgTc.getToStop());
            busUbicacion.setIdVehiculo(prgTc.getIdVehiculo());
            busUbicacionFacadeLocal.create(busUbicacion);
            MovilidadUtil.addSuccessMessage("Registro Exitoso");
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('patioDialog').hide()");
            reset();
            return;
        }
        MovilidadUtil.addErrorMessage("No se seleccionó vehículo");
        reset();
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('patioDialog').hide()");
    }

    public void reset() {
        busUbicacion = new PrgBusUbicacion();
        prgTc = new PrgTc();
        PrimeFaces.current().ajax().update(":panel-entrada");
        PrimeFaces.current().ajax().update(":panel-salida");
    }

    public void obtenerPatio(int i_patio) {
        busUbicacion = new PrgBusUbicacion();
        System.out.println("id_patio : " + i_patio);
        id_patio = i_patio;
    }

    public void guardarBusNoProgramado() {
        if (id_patio != 0) {
            if (vehiculo == null) {
                MovilidadUtil.addErrorMessage("Vehiculo no valido");
                return;
            }
            busUbicacion.setIdPatio(new PrgStopPoint(id_patio));
            busUbicacion.setIdVehiculo(vehiculo);
            busUbicacionFacadeLocal.create(busUbicacion);
            MovilidadUtil.addSuccessMessage("Registro Exitoso");
            PrimeFaces.current().executeScript("PF('NoPrgPatioDlg').hide()");
            PrimeFaces.current().ajax().update("opcPatio:msg-patio");
            reset();
            return;
        }
        MovilidadUtil.addErrorMessage("Error al seleccionar Patio");
        PrimeFaces.current().ajax().update("opcPatio:msg-patio");
        reset();
        PrimeFaces.current().executeScript("PF('NoPrgPatioDlg').hide()");
    }

    public void validarVehiculo() {
        vehiculo = vehiculoFacadeLocal.getVehiculo(c_vehiculo,0);
        if (vehiculo == null) {
            MovilidadUtil.addErrorMessage("Vehículo no valido");
            return;
        }
        MovilidadUtil.addSuccessMessage("Vehiculo Valido");
        c_vehiculo = vehiculo.getCodigo();
    }

    public PrgBusUbicacion getBusUbicacion() {
        return busUbicacion;
    }

    public void setBusUbicacion(PrgBusUbicacion busUbicacion) {
        this.busUbicacion = busUbicacion;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public String getC_vehiculo() {
        return c_vehiculo;
    }

    public void setC_vehiculo(String c_vehiculo) {
        this.c_vehiculo = c_vehiculo;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

}
