/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.model.PrgTc;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.CurrentLocation;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import com.movlidad.httpUtil.GeoService;
import java.io.Serializable;
import java.util.Date;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "mapGeoUbicacionVehiculosBean")
@ViewScoped
public class MapGeoUbicacionVehiculosBean implements Serializable {

    /**
     * Creates a new instance of MapGeoBean
     */
    public MapGeoUbicacionVehiculosBean() {
    }

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    private String urlMapGeoAll;
    private String urlMapGeo;
    private String urlMapGeoRecVehiEspecifico;
    private String codigoVehiuclo;
    private Float infoBateria;

    // Parámetros para visualizar el recorrido de un vehículo específico
    private Date fecha;
    private String horaInicio;
    private String horaFin;
    private String codigoVehiucloEspe;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void abrirMapaTodosvehiculos() {
        urlMapGeoAll = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.URL_MAP_GEO_VEH_ALL);
        if (urlMapGeoAll == null) {
            MovilidadUtil.addErrorMessage("No existe conficuracion de GEO en config empresa");
            return;
        }
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() > 0) {
            urlMapGeoAll = urlMapGeoAll + "?unit=" + unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        }
        MovilidadUtil.openModal("map_geo_all_mtto_wv");

    }

    public CurrentLocation getInfoBateria(String codVehiculo) {
        String urlUbicacionActualVehiculo = SingletonConfigEmpresa.getMapConfiMapEmpresa()
                .get(ConstantsUtil.URL_SERVICE_LOCATION_VEHICLE);

        urlUbicacionActualVehiculo = urlUbicacionActualVehiculo
                + codVehiculo;
        return GeoService.getCurrentPosition(urlUbicacionActualVehiculo);
    }

    public void openMapGeo(String codVehiculo) {
        infoBateria = null;
        String get = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.URL_MAP_GEO_VEH);
        if (get == null) {
            MovilidadUtil.addErrorMessage("No existe conficuracion de GEO en config empresa");
            return;
        }
        CurrentLocation currentLocation = getInfoBateria(codVehiculo);
        if (currentLocation != null) {
            infoBateria = currentLocation.get_BatteryCharge();
        }
        urlMapGeo = get += codVehiculo;

        MovilidadUtil.openModal("map_geo_mtto_wv");

    }

    public void openMapGeoVehEspecifico(PrgTc prgtc) {
        String get = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.URL_MAP_GEO_RECO_VEH_ESP);
        if (get == null) {
            MovilidadUtil.addErrorMessage("No existe conficuracion de GEO en config empresa");
            return;
        }

        if (prgtc != null) {
            // Se obtienen datos del servicio seleccionado y se agregan a la URL
            fecha = prgtc.getFecha();
            horaInicio = prgtc.getTimeOrigin();
            horaFin = prgtc.getTimeDestiny();
            codigoVehiucloEspe = prgtc.getIdVehiculo().getCodigo();
        }

        urlMapGeoRecVehiEspecifico = get + ("?date=" + Util.dateFormat(fecha) + "&stime=" + horaInicio + "&etime=" + horaFin + "&vehicle=" + codigoVehiucloEspe);

        MovilidadUtil.openModal("map_geo_rec_veh_esp_mtto_wv");

    }

    public String getUrlMapGeoAll() {
        return urlMapGeoAll;
    }

    public void setUrlMapGeoAll(String urlMapGeoAll) {
        this.urlMapGeoAll = urlMapGeoAll;
    }

    public String getUrlMapGeo() {
        return urlMapGeo;
    }

    public void setUrlMapGeo(String urlMapGeo) {
        this.urlMapGeo = urlMapGeo;
    }

    public String getCodigoVehiuclo() {
        return codigoVehiuclo;
    }

    public void setCodigoVehiuclo(String codigoVehiuclo) {
        this.codigoVehiuclo = codigoVehiuclo;
    }

    public String getUrlMapGeoRecVehiEspecifico() {
        return urlMapGeoRecVehiEspecifico;
    }

    public void setUrlMapGeoRecVehiEspecifico(String urlMapGeoRecVehiEspecifico) {
        this.urlMapGeoRecVehiEspecifico = urlMapGeoRecVehiEspecifico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getCodigoVehiucloEspe() {
        return codigoVehiucloEspe;
    }

    public void setCodigoVehiucloEspe(String codigoVehiucloEspe) {
        this.codigoVehiucloEspe = codigoVehiucloEspe;
    }

    public Float getInfoBateria() {
        return infoBateria;
    }

    public void setInfoBateria(Float infoBateria) {
        this.infoBateria = infoBateria;
    }

}
