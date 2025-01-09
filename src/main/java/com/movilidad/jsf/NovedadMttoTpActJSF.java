/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadMttoTipoActividadFacadeLocal;
import com.movilidad.model.NovedadMttoTipoActividad;
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
 * Permite gestionar toda los datos para el objeto NovedadMttoTipoActividad
 * principal tabla afectada novedad_mtto_tipo_actividad
 *
 * @author cesar
 */
@Named(value = "novedadMttoTpActJSF")
@ViewScoped
public class NovedadMttoTpActJSF implements Serializable {

    @EJB
    private NovedadMttoTipoActividadFacadeLocal novedadMttoTpActFacadeLocal;

    private NovedadMttoTipoActividad novedadMttoTpAct;

    private List<NovedadMttoTipoActividad> listNovedadMttoTpAct;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreNovAux;

    /**
     * Creates a new instance of NovedadMttoTipoActividad
     */
    public NovedadMttoTpActJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreNovAux = "";
    }

    /**
     * Permite persistir la data del objeto NovedadMttoTipoActividad en la base
     * de datos
     */
    public void guardar() {
        try {
            if (novedadMttoTpAct != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para Novedad Mantenimineto Tipo Actividad no se encuentra disponible");
                    return;
                }
                novedadMttoTpAct.setCreado(new Date());
                novedadMttoTpAct.setModificado(new Date());
                novedadMttoTpAct.setEstadoReg(0);
                novedadMttoTpAct.setUsername(user.getUsername());
                novedadMttoTpActFacadeLocal.create(novedadMttoTpAct);
                MovilidadUtil.addSuccessMessage("Se a registrado Novedad Mantenimineto Tipo Actividad correctamente");
                novedadMttoTpAct = new NovedadMttoTipoActividad();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Novedad Mantenimineto Tipo Actividad");
        }
    }

    /**
     * Permite realizar un update del objeto NovedadMttoTpAct en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (novedadMttoTpAct != null) {
                if (!cNombreNovAux.equals(novedadMttoTpAct.getNombre())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para Novedad Mantenimineto Tipo Actividad no se encuentra disponible");
                        return;
                    }
                }
                novedadMttoTpAct.setModificado(new Date());
                novedadMttoTpActFacadeLocal.edit(novedadMttoTpAct);
                MovilidadUtil.addSuccessMessage("Se a actualizado Novedad Mantenimineto Tipo Actividad correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Novedad Mantenimineto Tipo Actividad");
        }
    }

    /**
     * Permite crear la instancia del objeto NovedadMttoTipoActividad
     */
    public void prepareGuardar() {
        novedadMttoTpAct = new NovedadMttoTipoActividad();
    }

    public void reset() {
        novedadMttoTpAct = null;
        cNombreNovAux = "";
    }

    /**
     * Permite capturar el objeto NovedadMttoTipoActividad seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto NovedadMttoTipoActividad
     */
    public void onGetNovedadMttoTpAct(NovedadMttoTipoActividad event) {
        novedadMttoTpAct = event;
        cNombreNovAux = event.getNombre();
    }

    /**
     * Permite validar si el valor asignado al atributo nombre se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<NovedadMttoTipoActividad> findAllEstadoReg = novedadMttoTpActFacadeLocal.findAllEstadoReg();
        for (NovedadMttoTipoActividad sae : findAllEstadoReg) {
            if (sae.getNombre().equals(novedadMttoTpAct.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public NovedadMttoTipoActividad getNovedadMttoTpAct() {
        return novedadMttoTpAct;
    }

    public void setNovedadMttoTpAct(NovedadMttoTipoActividad novedadMttoTpAct) {
        this.novedadMttoTpAct = novedadMttoTpAct;
    }

    public List<NovedadMttoTipoActividad> getListNovedadMttoTpAct() {
        listNovedadMttoTpAct = novedadMttoTpActFacadeLocal.findAllEstadoReg();
        return listNovedadMttoTpAct;
    }

    public void setListNovedadMttoTpAct(List<NovedadMttoTipoActividad> listNovedadMttoTpAct) {
        this.listNovedadMttoTpAct = listNovedadMttoTpAct;
    }

}
