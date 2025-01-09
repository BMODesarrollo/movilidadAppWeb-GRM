/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.util.beans.CurrentStatusVehicleGEO;
import com.movilidad.util.beans.SumDistanciaEntradaPatioDTO;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movlidad.httpUtil.GeoService;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author soluciones-it
 */
@Named(value = "gestorVehiculosDisponiblesBean")
@ViewScoped
public class GestorVehiculosDisponiblesJSF implements Serializable {

    private List<SumDistanciaEntradaPatioDTO> vehiculosSumDistancia;

    private String urlGeo;

    private Map<String, CurrentStatusVehicleGEO> hMapBateriasVehi;

    /**
     * Creates a new instance of ReporteSumDistanciaBttcJSF
     */
    public GestorVehiculosDisponiblesJSF() {
    }

    @PostConstruct
    void init() {
        urlGeo = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_URL_GEO_BATERIA_ALL_VEHICULO);

//        if (urlGeo == null || urlGeo.isEmpty()) {
//            MovilidadUtil.addErrorMessage("No se encontró endpoint que retorna la carga de las baterías");
//        }

    }

    void obtenerCargasBateria() {

        GeoService geo = new GeoService<>(CurrentStatusVehicleGEO.class);
        List<CurrentStatusVehicleGEO> data = geo.getData(urlGeo);

        if (data == null || data.isEmpty()) {
//            MovilidadUtil.addErrorMessage("No se encontró información respecto a la carga de las baterias");
            return;
        }

        hMapBateriasVehi = new HashMap<>();
        data.forEach(item -> {
            hMapBateriasVehi.put(item.getIdVehiculo(), item);
        });
    }

    public Float obtenerCargaBateriaPorVehiculo(String codVehiculo) {
        if (hMapBateriasVehi == null) {
            return Float.NaN;
        }
        CurrentStatusVehicleGEO item = hMapBateriasVehi.get(codVehiculo);

        return item != null ? item.getNivelRestanteEnergia() : null;
    }

    public List<SumDistanciaEntradaPatioDTO> getVehiculosSumDistancia() {
        return vehiculosSumDistancia;
    }

}
