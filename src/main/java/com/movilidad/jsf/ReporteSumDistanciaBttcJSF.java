/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.util.beans.CurrentStatusVehicleGEO;
import com.movilidad.util.beans.SumDistanciaEntradaPatioDTO;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movlidad.httpUtil.GeoService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author soluciones-it
 */
@Named(value = "reporteSumDistanciaBttcJSF")
@ViewScoped
public class ReporteSumDistanciaBttcJSF implements Serializable {

    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    //
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private MapGeoBean mapGeoBean;

    private List<SumDistanciaEntradaPatioDTO> vehiculosSumDistancia;

    private int idGopUF;
    private String urlGeo;
    private Integer kmFor100;

    /**
     * Creates a new instance of ReporteSumDistanciaBttcJSF
     */
    public ReporteSumDistanciaBttcJSF() {
    }

    @PostConstruct
    void init() {
        urlGeo = configEmpresaFacadeLocal.findByLlave(ConstantsUtil.ID_URL_GEO_BATERIA_ALL_VEHICULO).getValor();
        kmFor100 = new Integer(configEmpresaFacadeLocal
                .findByLlave(ConstantsUtil.ID_AUTONOMIA_METROS_BATERIA_100)
                .getValor());
    }

    public void consultarReporte() {
        String urlLocationVehicle = SingletonConfigEmpresa.getMapConfiMapEmpresa()
                .get(ConstantsUtil.URL_SERVICE_LOCATION_VEHICLE);
        if (urlLocationVehicle == null) {
            MovilidadUtil.addErrorMessage("No existe conficuracion de GEO en config empresa");
            return;
        }
        cargarUF();
        vehiculosSumDistancia = vehiculoFacadeLocal.findSumDistanciaForVehiculo(idGopUF);
        if (vehiculosSumDistancia.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encuentra resumen de los vehiculos para la fecha");
            return;
        }
        GeoService geo = new GeoService<>(CurrentStatusVehicleGEO.class);
        List<CurrentStatusVehicleGEO> data = geo.getData(urlGeo);
        Map<String, List<CurrentStatusVehicleGEO>> mapDataGeo = data
                .stream()
                .collect(Collectors.groupingBy(x -> x.getIdVehiculo()));
        vehiculosSumDistancia.stream().map(v -> {
            if (mapDataGeo.containsKey(v.getCodigoVehiculo())) {
                v.setCurrentStatusVehicleGEO(mapDataGeo.get(v.getCodigoVehiculo()).get(0));
            }
            return v;
        }).forEachOrdered(v -> {
            v.calcularCriterio();
            v.calcularKmSegunCarga(kmFor100);
        });
    }

    public void mostrarUbicacionVehiculo(String codigo) {
        mapGeoBean.openMapGeo(codigo);
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public List<SumDistanciaEntradaPatioDTO> getVehiculosSumDistancia() {
        return vehiculosSumDistancia;
    }

}
