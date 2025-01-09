package com.movilidad.jsf;

import com.movilidad.ejb.GenericaNominaAutorizacionDetFacadeLocal;
import com.movilidad.model.GenericaNominaAutorizacionDet;
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
@Named(value = "genericaAutorizacionNominaKactusErroresBean")
@ViewScoped
public class GenericaNominaAutorizacionKactusErroresBean implements Serializable {
    
    @EJB
    private GenericaNominaAutorizacionDetFacadeLocal autorizacionDetEJB;
    
    private Integer idNominaAutorizacion;
    
    private List<GenericaNominaAutorizacionDet> lstDetallesConError;
    
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
    
    public List<GenericaNominaAutorizacionDet> getLstDetallesConError() {
        return lstDetallesConError;
    }
    
    public void setLstDetallesConError(List<GenericaNominaAutorizacionDet> lstDetallesConError) {
        this.lstDetallesConError = lstDetallesConError;
    }
    
    public Integer getIdNominaAutorizacion() {
        return idNominaAutorizacion;
    }
    
    public void setIdNominaAutorizacion(Integer idNominaAutorizacion) {
        this.idNominaAutorizacion = idNominaAutorizacion;
    }
    
}
