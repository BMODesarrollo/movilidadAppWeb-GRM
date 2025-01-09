/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccAtencionViaFacadeLocal;
import com.movilidad.model.AccAtencionVia;
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
 *
 * @author soluciones-it
 */
@Named(value = "accAtencionViaJSF")
@ViewScoped
public class AccAtencionViaJSF implements Serializable {

    @EJB
    private AccAtencionViaFacadeLocal accAtencionViaFacadeLocal;
    private AccAtencionVia accAtencionVia;
    private List<AccAtencionVia> listAccAtencionVia;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;

    /**
     * Creates a new instance of AccAtencionViaJSF
     */
    public AccAtencionViaJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAux = "";
    }

    /**
     * Permite persistir la data del objeto AccAtencionVia en la base de datos
     */
    public void guardar() {
        try {
            if (accAtencionVia != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre no se encuentra disponible");
                    return;
                }
                accAtencionVia.setCreado(new Date());
                accAtencionVia.setModificado(new Date());
                accAtencionVia.setEstadoReg(0);
                accAtencionVia.setUsername(user.getUsername());
                accAtencionViaFacadeLocal.create(accAtencionVia);
                MovilidadUtil.addSuccessMessage("Se a registrado correctamente");
                accAtencionVia = new AccAtencionVia();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar");
        }
    }

    /**
     * Permite realizar un update del objeto AccAtencionVia en la base de datos
     */
    public void actualizar() {
        try {
            if (accAtencionVia != null) {
                if (!cNombreAux.equals(accAtencionVia.getNombre())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre no se encuentra disponible");
                        return;
                    }
                }
                accAtencionVia.setModificado(new Date());
                accAtencionViaFacadeLocal.edit(accAtencionVia);
                MovilidadUtil.addSuccessMessage("Se a actualizado correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar");
        }
    }

    /**
     * Permite crear la instancia del objeto AccAtencionVia
     */
    public void prepareGuardar() {
        accAtencionVia = new AccAtencionVia();
    }

    public void reset() {
        accAtencionVia = null;
        cNombreAux = "";
    }

    /**
     * Permite capturar el objeto AccAtencionVia seleccionado por el usuario
     *
     * @param event Evento que captura el objeto AccAtencionVia
     */
    public void onAccAtencionVia(AccAtencionVia event) {
        accAtencionVia = event;
        cNombreAux = event.getNombre();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        return listAccAtencionVia.stream().anyMatch(aav -> {
            return aav.getNombre().equalsIgnoreCase(accAtencionVia.getNombre());
        });
    }

    public AccAtencionVia getAccAtencionVia() {
        return accAtencionVia;
    }

    public void setAccAtencionVia(AccAtencionVia accAtencionVia) {
        this.accAtencionVia = accAtencionVia;
    }

    public List<AccAtencionVia> getListAccAtencionVia() {
        listAccAtencionVia = accAtencionViaFacadeLocal.findAllEstadoReg();
        return listAccAtencionVia;
    }

    public void setListAccAtencionVia(List<AccAtencionVia> listAccAtencionVia) {
        this.listAccAtencionVia = listAccAtencionVia;
    }

    public String getcNombreAux() {
        return cNombreAux;
    }

    public void setcNombreAux(String cNombreAux) {
        this.cNombreAux = cNombreAux;
    }

}
