/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccClasificacionFacadeLocal;
import com.movilidad.model.CableAccClasificacion;
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
 * Permite parametrizar la data relacionada con los objetos
 * CableAccClasificacion Principal tabla afectada cable_acc_clasificacion
 *
 * @author soluciones-it
 */
@Named(value = "cableAccClasificacionJSF")
@ViewScoped
public class CableAccClasificacionJSF implements Serializable {

    @EJB
    private CableAccClasificacionFacadeLocal cableAccClasificacionFacadeLocal;

    private CableAccClasificacion cableAccClasificacion;

    private List<CableAccClasificacion> listCableAccClasificacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;

    /**
     * Creates a new instance of CableAccClasificacionJSF
     */
    public CableAccClasificacionJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAux = "";
    }

    /**
     * Permite persistir la data del objeto CableAccClasificacion en la base de
     * datos
     */
    public void guardar() {
        try {
            if (cableAccClasificacion != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para acc clasificacion no se encuentra disponible");
                    return;
                }
                cableAccClasificacion.setCreado(new Date());
                cableAccClasificacion.setModificado(new Date());
                cableAccClasificacion.setEstadoReg(0);
                cableAccClasificacion.setUsername(user.getUsername());
                cableAccClasificacionFacadeLocal.create(cableAccClasificacion);
                MovilidadUtil.addSuccessMessage("Se a registrado acc clasificacion correctamente");
                cableAccClasificacion = new CableAccClasificacion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar acc clasificacion");
        }
    }

    /**
     * Permite realizar un update del objeto CableAccClasificacion en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (cableAccClasificacion != null) {
                if (!cNombreAux.equals(cableAccClasificacion.getTipo())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para acc clasificacion no se encuentra disponible");
                        return;
                    }
                }
                cableAccClasificacion.setModificado(new Date());
                cableAccClasificacionFacadeLocal.edit(cableAccClasificacion);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc clasificacion correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc clasificacion");
        }
    }

    /**
     * Permite crear la instancia del objeto CableAccClasificacion
     */
    public void prepareGuardar() {
        cableAccClasificacion = new CableAccClasificacion();
    }

    public void reset() {
        cableAccClasificacion = null;
        cNombreAux = "";
    }

    /**
     * Permite capturar el objeto CableAccClasificacion seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto CableAccClasificacion
     */
    public void onGetCableAccClasificacion(CableAccClasificacion event) {
        cableAccClasificacion = event;
        cNombreAux = event.getTipo();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<CableAccClasificacion> findAllEstadoReg = cableAccClasificacionFacadeLocal.findAllEstadoReg();
        for (CableAccClasificacion sae : findAllEstadoReg) {
            if (sae.getTipo().equals(cableAccClasificacion.getTipo())) {
                return true;
            }
        }
        return false;
    }

    public CableAccClasificacion getCableAccClasificacion() {
        return cableAccClasificacion;
    }

    public void setCableAccClasificacion(CableAccClasificacion cableAccClasificacion) {
        this.cableAccClasificacion = cableAccClasificacion;
    }

    public List<CableAccClasificacion> getListCableAccClasificacion() {
        listCableAccClasificacion = cableAccClasificacionFacadeLocal.findAllEstadoReg();
        return listCableAccClasificacion;
    }

    public void setListCableAccClasificacion(List<CableAccClasificacion> listCableAccClasificacion) {
        this.listCableAccClasificacion = listCableAccClasificacion;
    }

}
