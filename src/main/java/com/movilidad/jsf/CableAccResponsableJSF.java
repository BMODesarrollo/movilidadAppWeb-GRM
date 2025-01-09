/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccResponsableFacadeLocal;
import com.movilidad.model.CableAccResponsable;
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
 * Permite gestionar toda los datos para el objeto CableAccResponsable principal
 * tabla afectada cable_acc_responsable
 *
 * @author soluciones-it
 */
@Named(value = "cableAccResponsableJSF")
@ViewScoped
public class CableAccResponsableJSF implements Serializable {

    @EJB
    private CableAccResponsableFacadeLocal cableAccResponsableFacadeLocal;

    private CableAccResponsable cableAccResponsable;

    private List<CableAccResponsable> listCableAccResponsable;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;

    /**
     * Creates a new instance of CableAccResponsableJSF
     */
    public CableAccResponsableJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAux = "";
    }

    /**
     * Permite persistir la data del objeto CableAccResponsable en la base de
     * datos
     */
    public void guardar() {
        try {
            if (cableAccResponsable != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para acc responsable no se encuentra disponible");
                    return;
                }
                cableAccResponsable.setCreado(new Date());
                cableAccResponsable.setModificado(new Date());
                cableAccResponsable.setEstadoReg(0);
                cableAccResponsable.setUsername(user.getUsername());
                cableAccResponsableFacadeLocal.create(cableAccResponsable);
                MovilidadUtil.addSuccessMessage("Se a registrado acc responsable correctamente");
                cableAccResponsable = new CableAccResponsable();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar acc responsable");
        }
    }

    /**
     * Permite realizar un update del objeto CableAccResponsable en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (cableAccResponsable != null) {
                if (!cNombreAux.equals(cableAccResponsable.getNombre())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para acc responsable no se encuentra disponible");
                        return;
                    }
                }
                cableAccResponsable.setModificado(new Date());
                cableAccResponsableFacadeLocal.edit(cableAccResponsable);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc responsable correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc responsable");
        }
    }

    /**
     * Permite crear la instancia del objeto CableAccResponsable
     */
    public void prepareGuardar() {
        cableAccResponsable = new CableAccResponsable();
    }

    public void reset() {
        cableAccResponsable = null;
        cNombreAux = "";
    }

    /**
     * Permite capturar el objeto CableAccResponsable seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto CableAccResponsable
     */
    public void onGetCableAccResponsable(CableAccResponsable event) {
        cableAccResponsable = event;
        cNombreAux = event.getNombre();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<CableAccResponsable> findAllEstadoReg = cableAccResponsableFacadeLocal.findAllEstadoReg();
        for (CableAccResponsable sae : findAllEstadoReg) {
            if (sae.getNombre().equals(cableAccResponsable.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public CableAccResponsable getCableAccResponsable() {
        return cableAccResponsable;
    }

    public void setCableAccResponsable(CableAccResponsable cableAccResponsable) {
        this.cableAccResponsable = cableAccResponsable;
    }

    public List<CableAccResponsable> getListCableAccResponsable() {
        listCableAccResponsable = cableAccResponsableFacadeLocal.findAllEstadoReg();
        return listCableAccResponsable;
    }

    public void setListCableAccResponsable(List<CableAccResponsable> listCableAccResponsable) {
        this.listCableAccResponsable = listCableAccResponsable;
    }

}
