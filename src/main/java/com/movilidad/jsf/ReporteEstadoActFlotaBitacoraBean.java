package com.movilidad.jsf;

import com.movilidad.ejb.DispConciliacionDetFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.util.beans.ResEstadoActFlota;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "reporteEstadoActFlotaBitacoraBean")
@ViewScoped
public class ReporteEstadoActFlotaBitacoraBean implements Serializable {

    @EJB
    private DispConciliacionDetFacadeLocal dispConciliacionDetEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private VehiculoTipoFacadeLocal vehiculoTipoEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<ResEstadoActFlota> lstResumen;

    Map<Integer, String> hMTipoVehiculos;

    private String tipoVehi1;
    private String tipoVehi2;

    @PostConstruct
    public void init() {
        consultar();
    }

    public void consultar() {
        cargarTipoVehiculo();
        lstResumen = vehiculoEjb.getResumenEstadoActualFlota(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        tipoVehi1 = hMTipoVehiculos.get(Util.ID_ART) != null ? hMTipoVehiculos.get(Util.ID_ART) : "TIPO 1";
        tipoVehi2 = hMTipoVehiculos.get(Util.ID_BI) != null ? hMTipoVehiculos.get(Util.ID_BI) : "TIPO 2";
    }

    private void cargarTipoVehiculo() {
        List<VehiculoTipo> lista = vehiculoTipoEjb.findAllEstadoR();
        hMTipoVehiculos = new HashMap<>();

        if (lista != null) {
            lista.forEach(item -> {
                hMTipoVehiculos.put(item.getIdVehiculoTipo(), item.getNombreTipoVehiculo());
            });
        }
    }

    public List<ResEstadoActFlota> getLstResumen() {
        return lstResumen;
    }

    public void setLstResumen(List<ResEstadoActFlota> lstResumen) {
        this.lstResumen = lstResumen;
    }

    public String getTipoVehi1() {
        return tipoVehi1;
    }

    public void setTipoVehi1(String tipoVehi1) {
        this.tipoVehi1 = tipoVehi1;
    }

    public String getTipoVehi2() {
        return tipoVehi2;
    }

    public void setTipoVehi2(String tipoVehi2) {
        this.tipoVehi2 = tipoVehi2;
    }

}
