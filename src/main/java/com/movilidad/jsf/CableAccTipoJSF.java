/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccTipoFacadeLocal;
import com.movilidad.model.CableAccTipo;
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
 * Permite parametrizar la data relacionada con los objetos CableAccTipo
 * Principal tabla afectada cable_acc_tipo
 *
 * @author soluciones-it
 */
@Named(value = "cableAccTipoJSF")
@ViewScoped
public class CableAccTipoJSF implements Serializable {

    @EJB
    private CableAccTipoFacadeLocal cableAccTipoFacadeLocal;

    private CableAccTipo cableAccTipo;

    private List<CableAccTipo> listCableAccTipo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;

    /**
     * Creates a new instance of CableAccTipoJSF
     */
    public CableAccTipoJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAux = "";
    }

    /**
     * Permite persistir la data del objeto CableAccTipo en la base de datos
     */
    public void guardar() {
        try {
            if (cableAccTipo != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para acc tipo no se encuentra disponible");
                    return;
                }
                cableAccTipo.setCreado(new Date());
                cableAccTipo.setModificado(new Date());
                cableAccTipo.setEstadoReg(0);
                cableAccTipo.setUsername(user.getUsername());
                cableAccTipoFacadeLocal.create(cableAccTipo);
                MovilidadUtil.addSuccessMessage("Se a registrado acc tipo correctamente");
                cableAccTipo = new CableAccTipo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar acc tipo");
        }
    }

    /**
     * Permite realizar un update del objeto CableAccTipo en la base de datos
     */
    public void actualizar() {
        try {
            if (cableAccTipo != null) {
                if (!cNombreAux.equals(cableAccTipo.getTipo())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para acc tipo no se encuentra disponible");
                        return;
                    }
                }
                cableAccTipo.setModificado(new Date());
                cableAccTipoFacadeLocal.edit(cableAccTipo);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc tipo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc tipo");
        }
    }

    /**
     * Permite crear la instancia del objeto CableAccTipo
     */
    public void prepareGuardar() {
        cableAccTipo = new CableAccTipo();
    }

    public void reset() {
        cableAccTipo = null;
        cNombreAux = "";
    }

    /**
     * Permite capturar el objeto CableAccTipo seleccionado por el usuario
     *
     * @param event Evento que captura el objeto CableAccTipo
     */
    public void onGetCableAccTipo(CableAccTipo event) {
        cableAccTipo = event;
        cNombreAux = event.getTipo();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<CableAccTipo> findAllEstadoReg = cableAccTipoFacadeLocal.findAllEstadoReg();
        for (CableAccTipo sae : findAllEstadoReg) {
            if (sae.getTipo().equals(cableAccTipo.getTipo())) {
                return true;
            }
        }
        return false;
    }

    public CableAccTipo getCableAccTipo() {
        return cableAccTipo;
    }

    public void setCableAccTipo(CableAccTipo cableAccTipo) {
        this.cableAccTipo = cableAccTipo;
    }

    public List<CableAccTipo> getListCableAccTipo() {
        listCableAccTipo = cableAccTipoFacadeLocal.findAllEstadoReg();
        return listCableAccTipo;
    }

    public void setListCableAccTipo(List<CableAccTipo> listCableAccTipo) {
        this.listCableAccTipo = listCableAccTipo;
    }

}
