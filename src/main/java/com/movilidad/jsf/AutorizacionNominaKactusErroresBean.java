package com.movilidad.jsf;

import com.movilidad.ejb.NominaAutorizacionDetFacadeLocal;
import com.movilidad.model.NominaAutorizacionDet;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "autorizacionNominaKactusErroresBean")
@ViewScoped
public class AutorizacionNominaKactusErroresBean implements Serializable {

    @EJB
    private NominaAutorizacionDetFacadeLocal autorizacionDetEJB;

    private Integer idNominaAutorizacion;

    private List<NominaAutorizacionDet> lstDetallesConError;

    public void obtenerNovedadesConErrores() {

        if (idNominaAutorizacion == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una autorización");
            return;
        }

        lstDetallesConError = autorizacionDetEJB
                .findByIdNominaAutorizacionAndCodigoError(
                        idNominaAutorizacion,
                        ConstantsUtil.CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS);

        MovilidadUtil.openModal("wlgErroresKactus");

    }

    public boolean isAutorizationWithErrors(Integer idNominaAutorizacion) {
        return autorizacionDetEJB
                .obtenerCantidadErrores(idNominaAutorizacion) > 0;
    }

    public List<NominaAutorizacionDet> getLstDetallesConError() {
        return lstDetallesConError;
    }

    public void setLstDetallesConError(List<NominaAutorizacionDet> lstDetallesConError) {
        this.lstDetallesConError = lstDetallesConError;
    }

    public Integer getIdNominaAutorizacion() {
        return idNominaAutorizacion;
    }

    public void setIdNominaAutorizacion(Integer idNominaAutorizacion) {
        this.idNominaAutorizacion = idNominaAutorizacion;
    }

}
