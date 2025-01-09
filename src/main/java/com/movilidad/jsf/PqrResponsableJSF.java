/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PqrResponsableFacadeLocal;
import com.movilidad.model.PqrResponsable;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "pqrResponsableJSF")
@ViewScoped
public class PqrResponsableJSF implements Serializable {

    @EJB
    private PqrResponsableFacadeLocal pqrResponsableFacadeLocal;

    private PqrResponsable pqrResponsable;

    private List<PqrResponsable> listPqrResponsable;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of PqrResponsableJSF
     */
    public PqrResponsableJSF() {
    }

    /**
     * Permite persistir la data del objeto PqrResponsable en la base de datos
     */
    public void guardar() {
        try {
            if (pqrResponsable != null) {
                if (validarNombre(pqrResponsable.getIdPqrResponsable())) {
                    MovilidadUtil.addErrorMessage("Nombre responsable PQR no se encuentra disponible");
                    return;
                }
                pqrResponsable.setCreado(new Date());
                pqrResponsable.setModificado(new Date());
                pqrResponsable.setEstadoReg(0);
                pqrResponsable.setUsername(user.getUsername());
                pqrResponsableFacadeLocal.create(pqrResponsable);
                MovilidadUtil.addSuccessMessage("Se a registrado responsable PQR correctamente");
                pqrResponsable = new PqrResponsable();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar responsable PQR ");
        }
    }

    /**
     * Permite realizar un update del objeto PqrResponsable en la base de datos
     */
    public void actualizar() {
        try {
            if (pqrResponsable != null) {
                if (validarNombre(pqrResponsable.getIdPqrResponsable())) {
                    MovilidadUtil.addErrorMessage("Nombre para Nombre responsable PQR no se encuentra disponible");
                    return;
                }
                pqrResponsable.setModificado(new Date());
                pqrResponsableFacadeLocal.edit(pqrResponsable);
                MovilidadUtil.addSuccessMessage("Se a actualizado el responsable PQR  correctamente");
                reset();
                MovilidadUtil.hideModal("modalDlg");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar responsable PQR ");
        }
    }

    /**
     * Permite crear la instancia del objeto PqrResponsable
     */
    public void prepareGuardar() {
        pqrResponsable = new PqrResponsable();
    }

    public void reset() {
        pqrResponsable = null;
    }

    /**
     * Permite capturar el objeto PqrResponsable seleccionado por el usuario
     *
     * @param event Evento que captura el objeto PqrResponsable
     */
    public void onGetPqrResponsable(PqrResponsable event) {
        pqrResponsable = event;
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @param i identificador de ResponsablePqr
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre(Integer i) {
        int o = i != null ? i : 0;
        PqrResponsable obj = pqrResponsableFacadeLocal.verificarRegistro(o, pqrResponsable.getResponsable());
        return obj != null;
    }

    public PqrResponsable getPqrResponsable() {
        return pqrResponsable;
    }

    public void setPqrResponsable(PqrResponsable pqrResponsable) {
        this.pqrResponsable = pqrResponsable;
    }

    public List<PqrResponsable> getListPqrResponsable() {
        listPqrResponsable = pqrResponsableFacadeLocal.findAllByEstadoReg();
        return listPqrResponsable;
    }

    public void setListPqrResponsable(List<PqrResponsable> listPqrResponsable) {
        this.listPqrResponsable = listPqrResponsable;
    }

}
