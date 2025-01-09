package com.movilidad.jsf;

import com.movilidad.ejb.DispConciliacionDetFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.DispConciliacionDet;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.util.beans.ResEstadoActFlota;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
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
@Named(value = "reporteEstadoActualFlotaBean")
@ViewScoped
public class ReporteEstadoActualFlotaBean implements Serializable {

    @EJB
    private DispConciliacionDetFacadeLocal dispConciliacionDetEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private VehiculoTipoFacadeLocal vehiculoTipoEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<DispConciliacionDet> lstDetalles;
    private List<ResEstadoActFlota> lstResumen;

    Map<Integer, String> hMTipoVehiculos;

    private String tipoVehi1;
    private String tipoVehi2;

    private boolean b_input_causa_estrada;

    @PostConstruct
    public void init() {
        consultar();
    }

    private void consultar() {
        cargarTipoVehiculo();
        b_input_causa_estrada = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.NOV_MTTO_INPUT_CAUSA_ENTRADA).equals("1");
        lstDetalles = dispConciliacionDetEjb.obtenerDetalles(MovilidadUtil.fechaCompletaHoy(), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        lstResumen = vehiculoEjb.getResumenEstadoActualFlota(unidadFuncionalSessionBean.getIdGopUnidadFuncional());
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

    public List<DispConciliacionDet> getLstDetalles() {
        return lstDetalles;
    }

    public void setLstDetalles(List<DispConciliacionDet> lstDetalles) {
        this.lstDetalles = lstDetalles;
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

    public boolean isB_input_causa_estrada() {
        return b_input_causa_estrada;
    }

    public void setB_input_causa_estrada(boolean b_input_causa_estrada) {
        this.b_input_causa_estrada = b_input_causa_estrada;
    }

}
