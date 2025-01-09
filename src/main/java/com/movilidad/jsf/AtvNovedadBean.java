/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AtvTipoAtencionFacadeLocal;
import com.movilidad.ejb.OperacionPatiosFacadeLocal;
import com.movilidad.model.AtvTipoAtencion;
import com.movilidad.model.Novedad;
import com.movilidad.model.OperacionPatios;
import com.movilidad.util.beans.CurrentLocation;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movlidad.httpUtil.GeoService;
import com.movlidad.httpUtil.SenderAtvNovedad;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.json.JSONObject;

/**
 *
 * @author solucionesit
 */
@Named(value = "atvBean")
@ViewScoped
public class AtvNovedadBean implements Serializable {

    /**
     * Creates a new instance of SelectTipoAtencionBean
     */
    public AtvNovedadBean() {
    }
    @EJB
    private AtvTipoAtencionFacadeLocal atvTipoAtencionEJB;
    @EJB
    private OperacionPatiosFacadeLocal operacionPatiosEJB;

    private List<AtvTipoAtencion> lstAtvTipoAtencion;
    private List<OperacionPatios> lstOperacionPatios;
    private AtvTipoAtencion atvTipoAtencion;
    private OperacionPatios operacionPatios;
    private int i_tipoAtencion;
    private int i_operacionPatio;
    private boolean b_atv = true;
    private boolean requeridoDestino = true;

    public void consultarTipoAtencion() {
        if (lstAtvTipoAtencion == null) {
            lstAtvTipoAtencion = atvTipoAtencionEJB
                    .findAllByEstadoReg();
        }
    }

    public void consultarPatios() {
        if (lstOperacionPatios == null) {
            lstOperacionPatios = operacionPatiosEJB
                    .findAllActivosLatLong();
        }
    }

    public void sendAtvNovedad(Novedad param) throws IOException {
        String urlPost = SingletonConfigEmpresa.getMapConfiMapEmpresa()
                .get(ConstantsUtil.URL_POST_SEND_ATV);
        String urlUbicacionActualVehiculo = SingletonConfigEmpresa.getMapConfiMapEmpresa()
                .get(ConstantsUtil.URL_SERVICE_LOCATION_VEHICLE);

        String CodVehiculo = param.getIdVehiculo() != null ? param.getIdVehiculo().getCodigo() : "";
        urlUbicacionActualVehiculo = urlUbicacionActualVehiculo
                + CodVehiculo;
        CurrentLocation currentPosition = GeoService.getCurrentPosition(urlUbicacionActualVehiculo);

        if (urlPost != null) {
            JSONObject data = SenderAtvNovedad.getData();
            JSONObject objeto = SenderAtvNovedad.getObjeto();
            Integer idNovedad = param.getIdNovedad();
            data.put("username", "");
            data.put("latInicio", currentPosition == null ? "" : currentPosition.get_Latitude().toString());
            data.put("lngInicio", currentPosition == null ? "" : currentPosition.get_Longitude().toString());
            data.put("latFin", operacionPatios == null ? "" : operacionPatios.getLatitud());
            data.put("lngFin", operacionPatios == null ? "" : operacionPatios.getLongitud());
            data.put("fechaSolicitud", MovilidadUtil.fechaCompletaHoy().getTime());
            data.put("idAtvTipoServicio", getAtvTipoAtencion().getIdAtvTipoAtencion());
            data.put("idAtvEstado", 1);
            data.put("vehiculo", CodVehiculo);
            data.put("idAtvNovedad", idNovedad);
            objeto.put("data", data);
            objeto.put("id", idNovedad);
            boolean sent = SenderAtvNovedad.send(objeto, urlPost);
            System.out.println("sent-" + sent);

        }
    }

    public void findById() {
        atvTipoAtencion = atvTipoAtencionEJB.find(i_tipoAtencion);
        requeridoDestino = i_tipoAtencion == 1;
    }

    public void findPatioById() {
        operacionPatios = operacionPatiosEJB.find(i_operacionPatio);
    }

    public List<AtvTipoAtencion> getLstAtvTipoAtencion() {
        consultarTipoAtencion();
        return lstAtvTipoAtencion;
    }

    public void setLstAtvTipoAtencion(List<AtvTipoAtencion> lstAtvTipoAtencion) {
        this.lstAtvTipoAtencion = lstAtvTipoAtencion;
    }

    public int getI_tipoAtención() {
        return i_tipoAtencion;
    }

    public void setI_tipoAtención(int i_tipoAtencion) {
        this.i_tipoAtencion = i_tipoAtencion;
    }

    public AtvTipoAtencion getAtvTipoAtencion() {
        return atvTipoAtencion;
    }

    public void setAtvTipoAtencion(AtvTipoAtencion atvTipoAtencion) {
        this.atvTipoAtencion = atvTipoAtencion;
    }

    public boolean isB_atv() {
        return b_atv;
    }

    public void setB_atv(boolean b_atv) {
        this.b_atv = b_atv;
    }

    public List<OperacionPatios> getLstOperacionPatios() {
        consultarPatios();
        return lstOperacionPatios;
    }

    public void setLstOperacionPatios(List<OperacionPatios> lstOperacionPatios) {
        this.lstOperacionPatios = lstOperacionPatios;
    }

    public OperacionPatios getOperacionPatios() {
        return operacionPatios;
    }

    public void setOperacionPatios(OperacionPatios operacionPatios) {
        this.operacionPatios = operacionPatios;
    }

    public int getI_operacionPatio() {
        return i_operacionPatio;
    }

    public void setI_operacionPatio(int i_operacionPatio) {
        this.i_operacionPatio = i_operacionPatio;
    }

    public boolean isRequeridoDestino() {
        return requeridoDestino;
    }

    public void setRequeridoDestino(boolean requeridoDestino) {
        this.requeridoDestino = requeridoDestino;
    }

}
