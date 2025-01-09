/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopAlertaPresentacionVehiculoFacadeLocal;
import com.movilidad.ejb.GopAlertaTiempoDetenidoFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.GopAlertaPresentacionVehiculo;
import com.movilidad.model.GopAlertaTiempoDetenido;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Vehiculo;
import com.movilidad.util.beans.VehiculosDetenidosDTO;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.ObjetoSigleton;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import com.movlidad.httpUtil.GeoService;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author solucionesit
 */
@Named(value = "alertaEntradaVehBean")
@ViewScoped
public class AlertaEntradaVehiculoBean implements Serializable {

    @EJB
    private PrgTcFacadeLocal prgTcEJB;
    @EJB
    private GopAlertaTiempoDetenidoFacadeLocal alertaTiempoDetenidoEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private MapGeoBean mapGeoBean;

    @EJB
    private GopAlertaPresentacionVehiculoFacadeLocal presentacionVehiculoEJB;

    private List<PrgTc> list;
    private List<VehiculosDetenidosDTO> lstVehiculosDetenidos;

    private Map<String, String> hMVehiculosDetenidos;

    /**
     * Creates a new instance of AlertaEntradaVehiculoBean
     */
    public AlertaEntradaVehiculoBean() {
    }
    
    public void alertaSinPresentacion() {
        list = prgTcEJB.findVehiculosSinPresentacion(MovilidadUtil.fechaHoy(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void obtenerVehiculosDetenidos() {
        String get = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.URL_MAP_GEO_VEH_DETENIDOS);
        if (get == null) {
            MovilidadUtil.addErrorMessage("No existe conficuracion de GEO en config empresa");
            return;
        }

        GopAlertaTiempoDetenido alerta = alertaTiempoDetenidoEjb.find(ConstantsUtil.ID_GOP_ALERTA_TIEMPO_DETENIDO);

        if (alerta == null) {
            MovilidadUtil.addErrorMessage("No existe parametrización alerta tiempo detenido");
            return;
        }

        String url = get + "?minutes=" + alerta.getMinutos();
        url = url + "&unit=" + unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        GeoService gService = new GeoService<>(VehiculosDetenidosDTO.class);
        lstVehiculosDetenidos = gService.getData(url);
    }

    void cargarVehiculosDetenidos() {
        obtenerVehiculosDetenidos();

        hMVehiculosDetenidos = new HashMap<>();
        if (lstVehiculosDetenidos != null) {
            for (VehiculosDetenidosDTO item : lstVehiculosDetenidos) {
                hMVehiculosDetenidos.put(item.get_Vehicle(), item.get_Status());
            }
        }
    }

    public String obtenerEstadoVehiculo(String codigoVehiculo) {
        return hMVehiculosDetenidos.get(codigoVehiculo);
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

    public List<PrgTc> getList() {
        return list;
    }

    public void setList(List<PrgTc> list) {
        this.list = list;
    }

    public GopAlertaPresentacionVehiculo getGopAlertaPresentacion() {
        if (ObjetoSigleton.getGopAlertaPresentacionVehiculo() == null) {
            ObjetoSigleton.setGopAlertaPresentacionVehiculo(presentacionVehiculoEJB.find(1));
        }
        return ObjetoSigleton.getGopAlertaPresentacionVehiculo();
    }

    public List<VehiculosDetenidosDTO> getLstVehiculosDetenidos() {
        return lstVehiculosDetenidos;
    }

    public void setLstVehiculosDetenidos(List<VehiculosDetenidosDTO> lstVehiculosDetenidos) {
        this.lstVehiculosDetenidos = lstVehiculosDetenidos;
    }

    public Map<String, String> gethMVehiculosDetenidos() {
        return hMVehiculosDetenidos;
    }

    public void sethMVehiculosDetenidos(Map<String, String> hMVehiculosDetenidos) {
        this.hMVehiculosDetenidos = hMVehiculosDetenidos;
    }

}
