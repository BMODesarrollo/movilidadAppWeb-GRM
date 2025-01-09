/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccTipoEventoFacadeLocal;
import com.movilidad.model.CableAccTipoEvento;
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
 * Permite gestionar toda los datos para el objeto CableAccTipoEvento principal
 * tabla afectada cable_acc_tipo_evento
 *
 * @author soluciones-it
 */
@Named(value = "cableAccTipoEventoJSF")
@ViewScoped
public class CableAccTipoEventoJSF implements Serializable{

    @EJB
    private CableAccTipoEventoFacadeLocal cableAccTipoEventoFacadeLocal;

    private CableAccTipoEvento cableAccTipoEvento;

    private List<CableAccTipoEvento> listCableAccTipoEvento;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;

    /**
     * Creates a new instance of CableAccTipoEventoJSF
     */
    public CableAccTipoEventoJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAux = "";
    }

    /**
     * Permite persistir la data del objeto CableAccTipoEvento en la base de
     * datos
     */
    public void guardar() {
        try {
            if (cableAccTipoEvento != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para acc tipo evento no se encuentra disponible");
                    return;
                }
                cableAccTipoEvento.setCreado(new Date());
                cableAccTipoEvento.setModificado(new Date());
                cableAccTipoEvento.setEstadoReg(0);
                cableAccTipoEvento.setUsername(user.getUsername());
                cableAccTipoEventoFacadeLocal.create(cableAccTipoEvento);
                MovilidadUtil.addSuccessMessage("Se a registrado acc tipo evento correctamente");
                cableAccTipoEvento = new CableAccTipoEvento();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar acc tipo evento");
        }
    }

    /**
     * Permite realizar un update del objeto CableAccTipoEvento en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (cableAccTipoEvento != null) {
                if (!cNombreAux.equals(cableAccTipoEvento.getNombre())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para acc tipo evento no se encuentra disponible");
                        return;
                    }
                }
                cableAccTipoEvento.setModificado(new Date());
                cableAccTipoEventoFacadeLocal.edit(cableAccTipoEvento);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc tipo evento correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc tipo evento");
        }
    }

    /**
     * Permite crear la instancia del objeto CableAccTipoEvento
     */
    public void prepareGuardar() {
        cableAccTipoEvento = new CableAccTipoEvento();
    }

    public void reset() {
        cableAccTipoEvento = null;
        cNombreAux = "";
    }

    /**
     * Permite capturar el objeto CableAccTipoEvento seleccionado por el usuario
     *
     * @param event Evento que captura el objeto CableAccTipoEvento
     */
    public void onGetCableAccTipoEvento(CableAccTipoEvento event) {
        cableAccTipoEvento = event;
        cNombreAux = event.getNombre();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<CableAccTipoEvento> findAllEstadoReg = cableAccTipoEventoFacadeLocal.findAllEstadoReg();
        for (CableAccTipoEvento sae : findAllEstadoReg) {
            if (sae.getNombre().equals(cableAccTipoEvento.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public CableAccTipoEvento getCableAccTipoEvento() {
        return cableAccTipoEvento;
    }

    public void setCableAccTipoEvento(CableAccTipoEvento cableAccTipoEvento) {
        this.cableAccTipoEvento = cableAccTipoEvento;
    }

    public List<CableAccTipoEvento> getListCableAccTipoEvento() {
        listCableAccTipoEvento = cableAccTipoEventoFacadeLocal.findAllEstadoReg();
        return listCableAccTipoEvento;
    }

    public void setListCableAccTipoEvento(List<CableAccTipoEvento> listCableAccTipoEvento) {
        this.listCableAccTipoEvento = listCableAccTipoEvento;
    }

}
