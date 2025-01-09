/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.LavadoCalificacionFacadeLocal;
import com.movilidad.model.LavadoCalificacion;
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
@Named(value = "lavadoCalificacionJSF")
@ViewScoped
public class LavadoCalificacionJSF implements Serializable {

    @EJB
    private LavadoCalificacionFacadeLocal lavadoCalificacionFacadeLocal;

    private LavadoCalificacion lavadoCalificacion;

    private List<LavadoCalificacion> listLavadoCalificacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;

    /**
     * Creates a new instance of LavadoCalificacionJSF
     */
    public LavadoCalificacionJSF() {
        cNombreAux = "";
    }

    /**
     * Permite persistir la data del objeto LavadoCalificacion en la base de
     * datos
     */
    public void guardar() {
        try {
            if (lavadoCalificacion != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para motivo lavado no se encuentra disponible");
                    return;
                }
                lavadoCalificacion.setCreado(new Date());
                lavadoCalificacion.setModificado(new Date());
                lavadoCalificacion.setEstadoReg(0);
                lavadoCalificacion.setUsername(user.getUsername());
                lavadoCalificacionFacadeLocal.create(lavadoCalificacion);
                MovilidadUtil.addSuccessMessage("Se a registrado motivo calificacíon correctamente");
                lavadoCalificacion = new LavadoCalificacion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar motivo calificación");
        }
    }

    /**
     * Permite realizar un update del objeto LavadoCalificacion en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (lavadoCalificacion != null) {
                if (!cNombreAux.equals(lavadoCalificacion.getNombre())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para motivo calificación no se encuentra disponible");
                        return;
                    }
                }
                lavadoCalificacion.setModificado(new Date());
                lavadoCalificacionFacadeLocal.edit(lavadoCalificacion);
                MovilidadUtil.addSuccessMessage("Se a actualizado el motivo calificación correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar lavado calificación");
        }
    }

    /**
     * Permite crear la instancia del objeto LavadoCalificacion
     */
    public void prepareGuardar() {
        lavadoCalificacion = new LavadoCalificacion();
    }

    public void reset() {
        lavadoCalificacion = null;
        cNombreAux = "";
    }

    /**
     * Permite capturar el objeto LavadoCalificacion seleccionado por el usuario
     *
     * @param event Evento que captura el objeto LavadoCalificacion
     */
    public void onGetLavadoCalificacion(LavadoCalificacion event) {
        lavadoCalificacion = event;
        cNombreAux = event.getNombre();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<LavadoCalificacion> list = lavadoCalificacionFacadeLocal.findAllEstadoReg();
        return list
                .stream()
                .map(lm -> lm.getNombre())
                .anyMatch(lm -> lm.equalsIgnoreCase(lavadoCalificacion.getNombre()));
    }

    public LavadoCalificacion getLavadoCalificacion() {
        return lavadoCalificacion;
    }

    public void setLavadoCalificacion(LavadoCalificacion lavadoCalificacion) {
        this.lavadoCalificacion = lavadoCalificacion;
    }

    public List<LavadoCalificacion> getListLavadoCalificacion() {
        return listLavadoCalificacion = lavadoCalificacionFacadeLocal.findAllEstadoReg();
    }

    public void setListLavadoCalificacion(List<LavadoCalificacion> listLavadoCalificacion) {
        this.listLavadoCalificacion = listLavadoCalificacion;
    }
    
    

}
