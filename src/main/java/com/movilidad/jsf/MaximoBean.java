/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.DispClasificacionNovedadFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.Novedad;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.ws.client.GRNMXSR;
import com.ws.client.GRNMXSRPortType;
import com.ws.client.GRNMXSRQueryType;
import com.ws.client.GRNMXSRQueryType.SR;
import com.ws.client.GRNMXSRSRType;
import com.ws.client.MXStringQueryType;
import com.ws.client.QueryGRNMXSRResponseType;
import com.ws.client.QueryGRNMXSRType;
import com.ws.client.controller.CreateMXSR;
import com.ws.client.dto.CreateSRDTO;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author solucionesit
 */
@Named(value = "maximoBean")
@ViewScoped
public class MaximoBean implements Serializable {

    /**
     * Creates a new instance of MaximoBean
     */
    public MaximoBean() {
    }
    @EJB
    private NovedadFacadeLocal novedadEJB;
    @EJB
    private DispClasificacionNovedadFacadeLocal clasificacionNovedadEJB;

    private Novedad novedad;

    List<GRNMXSRSRType> list;

    public void sendToMaximo(Novedad param) throws MalformedURLException {

        if (validar(param)) {
            return;
        }
        String DESCRIPTION = novedad.getIdDispClasificacionNovedad().getIdDispSistema().getNombre();
        String DESCRIPTION_LONGDESCRIPTION = param.getIdDispClasificacionNovedad().getObservacion();
        String assetnum = novedad.getIdVehiculo().getPlaca();
        String REPORTEDPRIORITY = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_REPORTED_PRIORITY_MAXIMO);
        String GRN_REPORTEDBYNAME = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_REPORTED_BY_NAME_MAXIMO);

        CreateSRDTO createSRDTO = new CreateSRDTO();

        createSRDTO.setDescription(DESCRIPTION);
        createSRDTO.setDesciptionLong(DESCRIPTION_LONGDESCRIPTION);
        createSRDTO.setAssetnum(assetnum);
        createSRDTO.setPriority(Integer.parseInt(REPORTEDPRIORITY));
        createSRDTO.setReportedBy(GRN_REPORTEDBYNAME);
        CreateMXSR createMXSR = new CreateMXSR();
        String response = createMXSR.create(createSRDTO,
                getURL(),
                SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_USER_SR_MAXIMO),
                SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_TOKEN_SR_MAXIMO));

        param.getIdDispClasificacionNovedad().setMxTicketId(response);
        param.getIdDispClasificacionNovedad().setEnviarMaximo(1);

        clasificacionNovedadEJB.edit(param.getIdDispClasificacionNovedad());
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
    }

    public void obtenerOrdenesTrabajo(Novedad param) throws MalformedURLException {
        if (validar(param)) {
            return;
        }
        String mkTicketId = param.getIdDispClasificacionNovedad().getMxTicketId();
        if (mkTicketId != null && !mkTicketId.isEmpty()) {
            MXStringQueryType type = new MXStringQueryType();
            type.setValue(mkTicketId);

            List<MXStringQueryType> list = new LinkedList<MXStringQueryType>();
            list.add(type);

            SR sr = new SR();
            sr.setTicketid(list);

            GRNMXSRQueryType srQueryType = new GRNMXSRQueryType();
            srQueryType.setSR(sr);

            QueryGRNMXSRType queryType = new QueryGRNMXSRType();
            queryType.setGRNMXSRQuery(srQueryType);

            GRNMXSR grnmxsr = new GRNMXSR();
            //MX Green
            GRNMXSRPortType port = grnmxsr.getGRNMXSRSOAP11Port(getURL(),
                    SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_USER_SR_MAXIMO),
                    SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_TOKEN_SR_MAXIMO));
            QueryGRNMXSRResponseType respuesta = port.queryGRNMXSR(queryType);

            this.list = respuesta.getGRNMXSRSet().getSR();
        }
    }

    boolean validar(Novedad param) throws MalformedURLException {
        novedad = novedadEJB.find(param.getIdNovedad());
        if (novedad == null) {
            MovilidadUtil.addErrorMessage("Error inesperado.");
            return true;
        }
        if (novedad.getIdDispClasificacionNovedad() == null) {
            MovilidadUtil.addErrorMessage("Se debe generar una clasificación para la novedad.");
            return true;
        }
        if (getURL() == null) {
            MovilidadUtil.addErrorMessage("No existe URL de MAXIMO en Config Empresa.");
            return true;
        }
        return false;
    }

    private URL getURL() throws MalformedURLException {
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_URL_SR_MAXIMO) == null) {
            return null;
        }
        return new URL(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_URL_SR_MAXIMO));
    }

    public List<GRNMXSRSRType> getList() {
        return list;
    }

    public void setList(List<GRNMXSRSRType> list) {
        this.list = list;
    }

}
