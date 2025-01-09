/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccPlanAccionFacadeLocal;
import com.movilidad.model.CableAccPlanAccion;
import com.movilidad.model.CableAccResponsable;
import com.movilidad.model.CableAccidentalidad;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite gestionar la data del objeto CableAccPlanAccion, principal tabla
 * afectada cable_acc_plan_accion
 *
 * @author soluciones-it
 */
@Named(value = "cableAccPlanAccionJSF")
@ViewScoped
public class CableAccPlanAccionJSF implements Serializable {

    @EJB
    private CableAccPlanAccionFacadeLocal cableAccPlanAccionFacadeLocal;

    private CableAccPlanAccion cableAccPlanAccion;

    private List<CableAccPlanAccion> listCableAccPlanAccion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Integer idCableAccidentalidad;
    private Integer idCableAccResponsable;

    @Inject
    private CableAccidentalidadJSF caJSF;

    /**
     * Creates a new instance of CableAccPlanAccionJSF
     */
    public CableAccPlanAccionJSF() {
    }

    public void init() {
        idCableAccidentalidad = caJSF.compartirIdAccidente();
        idCableAccResponsable = null;
    }

    /**
     * Permite persistir la data del objeto CableAccPlanAccion en la base de
     * datos
     */
    public void guardar() {
        idCableAccidentalidad = caJSF.compartirIdAccidente();
        if (idCableAccidentalidad == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un caso de accidentaldad");
            return;
        }
        if (cableAccPlanAccion != null) {
            if (idCableAccResponsable != null) {
                cableAccPlanAccion.setIdCableAccResponsable(new CableAccResponsable(idCableAccResponsable));
            }
            cableAccPlanAccion.setIdCableAccidentalidad(new CableAccidentalidad(idCableAccidentalidad));
            cableAccPlanAccion.setCreado(new Date());
            cableAccPlanAccion.setModificado(new Date());
            cableAccPlanAccion.setEstadoReg(0);
            cableAccPlanAccion.setUsername(user.getUsername());
            cableAccPlanAccionFacadeLocal.create(cableAccPlanAccion);
            MovilidadUtil.addSuccessMessage("Se a registrado plan acción correctamente");
            reset();
        }
    }

    /**
     * Permite realizar un update del objeto CableAccPlanAccion en la base de
     * datos
     */
    public void actualizar() {
        idCableAccidentalidad = caJSF.compartirIdAccidente();
        if (idCableAccidentalidad == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un caso de accidentaldad");
            return;
        }
        if (cableAccPlanAccion != null) {
            if (idCableAccResponsable != null) {
                cableAccPlanAccion.setIdCableAccResponsable(new CableAccResponsable(idCableAccResponsable));
            }
            cableAccPlanAccion.setModificado(new Date());
            cableAccPlanAccionFacadeLocal.edit(cableAccPlanAccion);
            MovilidadUtil.addSuccessMessage("Se a actualizado plan acción correctamente");
            reset();
        }
    }

    public void eliminarPlanAccion(CableAccPlanAccion capa) {
        capa.setEstadoReg(1);
        capa.setModificado(new Date());
        cableAccPlanAccionFacadeLocal.edit(capa);
        MovilidadUtil.addSuccessMessage("Plan acción eliminado con éxito");
    }

    /**
     * Permite crear la instancia del objeto CableAccPlanAccion
     */
    public void prepareGuardar() {
        cableAccPlanAccion = new CableAccPlanAccion();
    }

    public void reset() {
        cableAccPlanAccion = null;
        idCableAccResponsable = null;
    }

    /**
     * Permite capturar el objeto CableAccPlanAccion seleccionado por el usuario
     *
     * @param event Evento que captura el objeto CableAccPlanAccion
     */
    public void onGetCableAccPlanAccion(CableAccPlanAccion event) {
        cableAccPlanAccion = event;
        idCableAccResponsable = event.getIdCableAccResponsable() != null ? event.getIdCableAccResponsable().getIdCableAccResponsable() : null;
    }

    public CableAccPlanAccion getCableAccPlanAccion() {
        return cableAccPlanAccion;
    }

    public void setCableAccPlanAccion(CableAccPlanAccion cableAccPlanAccion) {
        this.cableAccPlanAccion = cableAccPlanAccion;
    }

    public List<CableAccPlanAccion> getListCableAccPlanAccion() {
        idCableAccidentalidad = caJSF.compartirIdAccidente();
        if (idCableAccidentalidad != null) {
            listCableAccPlanAccion = cableAccPlanAccionFacadeLocal.findAllEstadoReg(idCableAccidentalidad);
        }
        return listCableAccPlanAccion;
    }

    public void setListCableAccPlanAccion(List<CableAccPlanAccion> listCableAccPlanAccion) {
        this.listCableAccPlanAccion = listCableAccPlanAccion;
    }

    public Integer getIdCableAccResponsable() {
        return idCableAccResponsable;
    }

    public void setIdCableAccResponsable(Integer idCableAccResponsable) {
        this.idCableAccResponsable = idCableAccResponsable;
    }

}
