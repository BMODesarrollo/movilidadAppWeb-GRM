/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PqrClasificacionFacadeLocal;
import com.movilidad.model.PqrClasificacion;
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
@Named(value = "pqrClasificacionJSF")
@ViewScoped
public class PqrClasificacionJSF implements Serializable {

    @EJB
    private PqrClasificacionFacadeLocal pqrClasificacionFacadeLocal;

    private PqrClasificacion pqrClasificacion;

    private List<PqrClasificacion> listPqrClasificacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of PqrClasificacionJSF
     */
    public PqrClasificacionJSF() {
    }

    /**
     * Permite persistir la data del objeto PqrClasificacion en la base de datos
     */
    public void guardar() {
        try {
            if (pqrClasificacion != null) {
                if (validarNombre(pqrClasificacion.getIdPqrClasificacion())) {
                    MovilidadUtil.addErrorMessage("Nombre claficación PQR no se encuentra disponible");
                    return;
                }
                pqrClasificacion.setCreado(new Date());
                pqrClasificacion.setModificado(new Date());
                pqrClasificacion.setEstadoReg(0);
                pqrClasificacion.setUsername(user.getUsername());
                pqrClasificacionFacadeLocal.create(pqrClasificacion);
                MovilidadUtil.addSuccessMessage("Se a registrado claficación PQR correctamente");
                pqrClasificacion = new PqrClasificacion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar claficación PQR ");
        }
    }

    /**
     * Permite realizar un update del objeto PqrClasificacion en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (pqrClasificacion != null) {
                if (validarNombre(pqrClasificacion.getIdPqrClasificacion())) {
                    MovilidadUtil.addErrorMessage("Nombre para Nombre claficación PQR no se encuentra disponible");
                    return;
                }
                pqrClasificacion.setModificado(new Date());
                pqrClasificacionFacadeLocal.edit(pqrClasificacion);
                MovilidadUtil.addSuccessMessage("Se a actualizado el claficación PQR  correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar claficación PQR ");
        }
    }

    /**
     * Permite crear la instancia del objeto PqrClasificacion
     */
    public void prepareGuardar() {
        pqrClasificacion = new PqrClasificacion();
    }

    public void reset() {
        pqrClasificacion = null;
    }

    /**
     * Permite capturar el objeto PqrClasificacion seleccionado por el usuario
     *
     * @param event Evento que captura el objeto PqrClasificacion
     */
    public void onGetPqrClasificacion(PqrClasificacion event) {
        pqrClasificacion = event;
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @param i identificador de ClasificacionPqr
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre(Integer i) {
        int o = i != null ? i : 0;
        PqrClasificacion obj = pqrClasificacionFacadeLocal.verificarRegistro(o, pqrClasificacion.getNombre());
        return obj != null;
    }

    public PqrClasificacion getPqrClasificacion() {
        return pqrClasificacion;
    }

    public void setPqrClasificacion(PqrClasificacion pqrClasificacion) {
        this.pqrClasificacion = pqrClasificacion;
    }

    public List<PqrClasificacion> getListPqrClasificacion() {
        listPqrClasificacion = pqrClasificacionFacadeLocal.findAllByEstadoReg();
        return listPqrClasificacion;
    }

    public void setListPqrClasificacion(List<PqrClasificacion> listPqrClasificacion) {
        this.listPqrClasificacion = listPqrClasificacion;
    }

}
