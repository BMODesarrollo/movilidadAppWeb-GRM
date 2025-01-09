/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadMttoTipoActividadDetFacadeLocal;
import com.movilidad.model.NovedadMttoTipoActividad;
import com.movilidad.model.NovedadMttoTipoActividadDet;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite gestionar toda los datos para el objeto NovedadMttoTipoActividadDet
 * principal tabla afectada novedad_mtto_tipo_actividad_det
 *
 * @author cesar
 */
@Named(value = "novedadMttoTpActDetJSF")
@ViewScoped
public class NovedadMttoTpActDetJSF implements Serializable {

    @EJB
    private NovedadMttoTipoActividadDetFacadeLocal novedadMttoTpActDetFacadeLocal;

    private NovedadMttoTipoActividadDet novedadMttoTpActDet;

    private List<NovedadMttoTipoActividadDet> listNovedadMttoTpActDet;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreNovDetAux;
    private Integer idNovMttoTpAct;

    /**
     * Creates a new instance of NovedadMttoTipoActividadDet
     */
    public NovedadMttoTpActDetJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreNovDetAux = "";
        idNovMttoTpAct = null;
    }

    /**
     * Permite persistir la data del objeto NovedadMttoTipoActividadDet en la
     * base de datos
     */
    public void guardar() {
        try {
            if (novedadMttoTpActDet != null) {
                if (idNovMttoTpAct == null) {
                    MovilidadUtil.addErrorMessage("Novedad Mantenimineto Tipo Actividad es requerido");
                    return;
                }
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para Novedad Mantenimineto Tipo Actividad Det no se encuentra disponible");
                    return;
                }
                novedadMttoTpActDet.setIdNovedadMttoTipoActividad(new NovedadMttoTipoActividad(idNovMttoTpAct));
                novedadMttoTpActDet.setCreado(new Date());
                novedadMttoTpActDet.setModificado(new Date());
                novedadMttoTpActDet.setEstadoReg(0);
                novedadMttoTpActDet.setUsername(user.getUsername());
                novedadMttoTpActDetFacadeLocal.create(novedadMttoTpActDet);
                MovilidadUtil.addSuccessMessage("Se a registrado Novedad Mantenimineto Tipo Actividad Det correctamente");
                novedadMttoTpActDet = new NovedadMttoTipoActividadDet();
                idNovMttoTpAct = null;
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Novedad Mantenimineto Tipo Actividad Det");
        }
    }

    /**
     * Permite realizar un update del objeto NovedadMttoTpActDet en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (novedadMttoTpActDet != null) {
                if (idNovMttoTpAct == null) {
                    MovilidadUtil.addErrorMessage("Novedad Mantenimineto Tipo Actividad es requerido");
                    return;
                }
                if ((!cNombreNovDetAux.equals(novedadMttoTpActDet.getNombre()))
                        || (!idNovMttoTpAct.equals(novedadMttoTpActDet.getIdNovedadMttoTipoActividad().getIdNovedadMttoTipoActividad()))) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para Novedad Mantenimineto Tipo Actividad Det no se encuentra disponible");
                        return;
                    }
                }
                novedadMttoTpActDet.setIdNovedadMttoTipoActividad(new NovedadMttoTipoActividad(idNovMttoTpAct));
                novedadMttoTpActDet.setModificado(new Date());
                novedadMttoTpActDetFacadeLocal.edit(novedadMttoTpActDet);
                MovilidadUtil.addSuccessMessage("Se a actualizado Novedad Mantenimineto Tipo Actividad Det correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Novedad Mantenimineto Tipo Actividad Det");
        }
    }

    /**
     * Permite crear la instancia del objeto NovedadMttoTipoActividadDet
     */
    public void prepareGuardar() {
        novedadMttoTpActDet = new NovedadMttoTipoActividadDet();
    }

    public void reset() {
        novedadMttoTpActDet = null;
        cNombreNovDetAux = "";
        idNovMttoTpAct = null;
    }

    /**
     * Permite capturar el objeto NovedadMttoTipoActividadDet seleccionado por
     * el usuario
     *
     * @param event Evento que captura el objeto NovedadMttoTipoActividadDet
     */
    public void onGetNovedadMttoTpActDet(NovedadMttoTipoActividadDet event) {
        novedadMttoTpActDet = event;
        cNombreNovDetAux = event.getNombre();
        idNovMttoTpAct = event.getIdNovedadMttoTipoActividad().getIdNovedadMttoTipoActividad();
    }

    /**
     * Permite validar si el valor asignado al atributo nombre se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<NovedadMttoTipoActividadDet> findAllEstadoReg = novedadMttoTpActDetFacadeLocal.findAllEstadoRegByIdNovMttoTpAct(idNovMttoTpAct);
        for (NovedadMttoTipoActividadDet sae : findAllEstadoReg) {
            if (sae.getNombre().equals(novedadMttoTpActDet.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public NovedadMttoTipoActividadDet getNovedadMttoTpActDet() {
        return novedadMttoTpActDet;
    }

    public void setNovedadMttoTpActDet(NovedadMttoTipoActividadDet novedadMttoTpActDet) {
        this.novedadMttoTpActDet = novedadMttoTpActDet;
    }

    public List<NovedadMttoTipoActividadDet> getListNovedadMttoTpActDet() {
        listNovedadMttoTpActDet = novedadMttoTpActDetFacadeLocal.findAllEstadoReg();
        return listNovedadMttoTpActDet;
    }

    public void setListNovedadMttoTpActDet(List<NovedadMttoTipoActividadDet> listNovedadMttoTpActDet) {
        this.listNovedadMttoTpActDet = listNovedadMttoTpActDet;
    }

    public Integer getIdNovMttoTpAct() {
        return idNovMttoTpAct;
    }

    public void setIdNovMttoTpAct(Integer idNovMttoTpAct) {
        this.idNovMttoTpAct = idNovMttoTpAct;
    }

}
