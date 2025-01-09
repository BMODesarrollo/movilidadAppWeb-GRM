/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.PrgDesasignarParamFacadeLocal;
import com.movilidad.ejb.PrgSerconMotivoFacadeLocal;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.PrgDesasignarParam;
import com.movilidad.model.PrgSerconMotivo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "prgDesasignarParamJSF")
@ViewScoped
public class PrgDesasignarParamJSF implements Serializable {

    @EJB
    private PrgDesasignarParamFacadeLocal prgDesasignarParamFacadeLocal;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetalleFacadeLocal;
    @EJB
    private PrgSerconMotivoFacadeLocal prgSerconMotivoFacadeLocal;

    private List<PrgDesasignarParam> listPrgDesasignarParam;
    private List<NovedadTipoDetalles> listNovedadTipoDetalles;
    private List<PrgSerconMotivo> listPrgSerconMotivo;
    //
    private PrgDesasignarParam prgDesasignarParam;
    private Integer idNovedadTipoDetalles;
    private Integer idPrgSerconMotivo;
    //
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of PrgDesasignarParamJSF
     */
    public PrgDesasignarParamJSF() {
    }

    /**
     * Persistir el objeto PrgDesasignarParam
     */
    public void guardar() {
        try {
            if (prgDesasignarParam != null) {
                if (idNovedadTipoDetalles == null) {
                    MovilidadUtil.addErrorMessage("Novedad Tipo Detalle es requerido");
                    return;
                }
                if (idPrgSerconMotivo == null) {
                    MovilidadUtil.addErrorMessage("Motivo es requerido");
                    return;
                }
                if (validarRegistro(0, idNovedadTipoDetalles)) {
                    MovilidadUtil.addErrorMessage("Ya existe una parametrización con la novedad seleccionada");
                    return;
                }
                prgDesasignarParam.setIdNovedadTipoDetalle(new NovedadTipoDetalles(idNovedadTipoDetalles));
                prgDesasignarParam.setIdPrgSerconMotivo(new PrgSerconMotivo(idPrgSerconMotivo));
                prgDesasignarParam.setCreado(new Date());
                prgDesasignarParam.setModificado(new Date());
                prgDesasignarParam.setEstadoReg(0);
                prgDesasignarParam.setUsername(user.getUsername());
                prgDesasignarParamFacadeLocal.create(prgDesasignarParam);
                reset();
                MovilidadUtil.addSuccessMessage("Parametrización Desasignación registrada con éxito.");
                prgDesasignarParam = new PrgDesasignarParam();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al tratar de registrar");
        }
    }

    /**
     * Actualizar el objeto PrgDesasignarParam
     */
    public void actualizar() {
        try {
            if (prgDesasignarParam != null) {
                if (idNovedadTipoDetalles == null) {
                    MovilidadUtil.addErrorMessage("Novedad Tipo Detalle es requerido");
                    return;
                }
                if (idPrgSerconMotivo == null) {
                    MovilidadUtil.addErrorMessage("Motivo es requerido");
                    return;
                }
                if (validarRegistro(prgDesasignarParam.getIdPrgDesasignarParam(), idNovedadTipoDetalles)) {
                    MovilidadUtil.addErrorMessage("Ya existe una parametrización con la novedad seleccionada");
                    return;
                }
                prgDesasignarParam.setIdNovedadTipoDetalle(new NovedadTipoDetalles(idNovedadTipoDetalles));
                prgDesasignarParam.setIdPrgSerconMotivo(new PrgSerconMotivo(idPrgSerconMotivo));
                prgDesasignarParam.setModificado(new Date());
                prgDesasignarParam.setUsername(user.getUsername());
                prgDesasignarParamFacadeLocal.edit(prgDesasignarParam);
                MovilidadUtil.addSuccessMessage("Parametrización Desasignación registrada con éxito.");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al tratar de actualizar");
        }
    }

    public void prepareGuardar() {
        prgDesasignarParam = new PrgDesasignarParam();
        cargarListas();
    }

    public void reset() {
        prgDesasignarParam = null;
        idNovedadTipoDetalles = null;
        idPrgSerconMotivo = null;
    }

    /**
     * Permite capturar el evento seleccionado por el usuario
     *
     * @param event Objeto PrgDesasignarParam seleccionado por el usuario
     */
    public void onSelectPrgDesasignarParam(PrgDesasignarParam event) {
        cargarListas();
        prgDesasignarParam = event;
        idNovedadTipoDetalles = prgDesasignarParam.getIdNovedadTipoDetalle() != null
                ? prgDesasignarParam.getIdNovedadTipoDetalle().getIdNovedadTipoDetalle()
                : null;
        idPrgSerconMotivo = prgDesasignarParam.getIdPrgSerconMotivo() != null
                ? prgDesasignarParam.getIdPrgSerconMotivo().getIdPrgSerconMotivo()
                : null;
    }

    void cargarListas() {
        listNovedadTipoDetalles = novedadTipoDetalleFacadeLocal.findByFechaIsActive();
        listPrgSerconMotivo = prgSerconMotivoFacadeLocal.findAllEstadoRegistro();
    }

    boolean validarRegistro(Integer id, Integer id2) {
        List<PrgDesasignarParam> list = prgDesasignarParamFacadeLocal.findByIdNovedadTipoDetalle(id, id2);
        return !list.isEmpty();
    }

    public List<PrgDesasignarParam> getListPrgDesasignarParam() {
        listPrgDesasignarParam = prgDesasignarParamFacadeLocal.findAllEstadoReg();
        return listPrgDesasignarParam;
    }

    public void setListPrgDesasignarParam(List<PrgDesasignarParam> listPrgDesasignarParam) {
        this.listPrgDesasignarParam = listPrgDesasignarParam;
    }

    public List<NovedadTipoDetalles> getListNovedadTipoDetalles() {
        return listNovedadTipoDetalles;
    }

    public void setListNovedadTipoDetalles(List<NovedadTipoDetalles> listNovedadTipoDetalles) {
        this.listNovedadTipoDetalles = listNovedadTipoDetalles;
    }

    public List<PrgSerconMotivo> getListPrgSerconMotivo() {
        return listPrgSerconMotivo;
    }

    public void setListPrgSerconMotivo(List<PrgSerconMotivo> listPrgSerconMotivo) {
        this.listPrgSerconMotivo = listPrgSerconMotivo;
    }

    public PrgDesasignarParam getPrgDesasignarParam() {
        return prgDesasignarParam;
    }

    public void setPrgDesasignarParam(PrgDesasignarParam prgDesasignarParam) {
        this.prgDesasignarParam = prgDesasignarParam;
    }

    public Integer getIdNovedadTipoDetalles() {
        return idNovedadTipoDetalles;
    }

    public void setIdNovedadTipoDetalles(Integer idNovedadTipoDetalles) {
        this.idNovedadTipoDetalles = idNovedadTipoDetalles;
    }

    public Integer getIdPrgSerconMotivo() {
        return idPrgSerconMotivo;
    }

    public void setIdPrgSerconMotivo(Integer idPrgSerconMotivo) {
        this.idPrgSerconMotivo = idPrgSerconMotivo;
    }

}
