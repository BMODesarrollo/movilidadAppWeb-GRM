/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.VehiculosDetenidosDTO;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author César
 */
@Named(value = "gestorVehiculoController")
@ViewScoped
public class GestorVehiculoController implements Serializable {

    @EJB
    private VehiculoFacadeLocal vehiculoEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private AlertaEntradaVehiculoBean alertaEntradaVehiculoBean;
    @Inject
    private MapGeoUbicacionVehiculosBean mapGeoBean;
    private Vehiculo vehiculo;

    private List<Vehiculo> listvehiculos;
    private List<VehiculosDetenidosDTO> lstVehiculosDetenidos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public GestorVehiculoController() {
    }

    @PostConstruct
    public void init() {
        consultarVehiculos();
        alertaEntradaVehiculoBean.cargarVehiculosDetenidos();
    }

    public void consultarVehiculos() {
        listvehiculos = vehiculoEJB.findAllVehiculosByidGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void mostrarUbicacionVehiculo(String codigo) {
        mapGeoBean.openMapGeo(codigo);
    }
    
    public void mostrarRecorrido(Vehiculo vehiculo) {
        PrgTc prgtc = new PrgTc();
        prgtc.setFecha(MovilidadUtil.fechaHoy());
        prgtc.setIdVehiculo(vehiculo);
        prgtc.setTimeOrigin(Util.dateToTimeHHMMSS(MovilidadUtil.fechaCompletaHoy()));
        prgtc.setTimeDestiny(Util.dateToTimeHHMMSS(MovilidadUtil.fechaCompletaHoy()));

        mapGeoBean.openMapGeoVehEspecifico(prgtc);
    }

    public void onRowSelect(SelectEvent event) {
        vehiculo = (Vehiculo) event.getObject();
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<Vehiculo> getListvehiculos() {
        return listvehiculos;
    }

    public void setListvehiculos(List<Vehiculo> listvehiculos) {
        this.listvehiculos = listvehiculos;
    }

}
