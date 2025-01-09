/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccTipoUsuarioFacadeLocal;
import com.movilidad.model.CableAccTipoUsuario;
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
 * Permite gestionar toda los datos para el objeto CableAccTipoUsuario principal
 * tabla afectada cable_acc_tipo_usuario
 *
 * @author soluciones-it
 */
@Named(value = "cableAccTipoUsuarioJSF")
@ViewScoped
public class CableAccTipoUsuarioJSF implements Serializable {

    @EJB
    private CableAccTipoUsuarioFacadeLocal cableAccTipoUsuarioFacadeLocal;

    private CableAccTipoUsuario cableAccTipoUsuario;

    private List<CableAccTipoUsuario> listCableAccTipoUsuario;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;

    /**
     * Creates a new instance of CableAccTipoUsuario
     */
    public CableAccTipoUsuarioJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAux = "";
    }

    /**
     * Permite persistir la data del objeto CableAccTipoUsuario en la base de
     * datos
     */
    public void guardar() {
        try {
            if (cableAccTipoUsuario != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para tipo usuario no se encuentra disponible");
                    return;
                }
                cableAccTipoUsuario.setCreado(new Date());
                cableAccTipoUsuario.setModificado(new Date());
                cableAccTipoUsuario.setEstadoReg(0);
                cableAccTipoUsuario.setUsername(user.getUsername());
                cableAccTipoUsuarioFacadeLocal.create(cableAccTipoUsuario);
                MovilidadUtil.addSuccessMessage("Se a registrado tipo usuario correctamente");
                cableAccTipoUsuario = new CableAccTipoUsuario();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar tipo usuario");
        }
    }

    /**
     * Permite realizar un update del objeto CableAccTipoUsuario en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (cableAccTipoUsuario != null) {
                if (!cNombreAux.equals(cableAccTipoUsuario.getTipo())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para sst eps no se encuentra disponible");
                        return;
                    }
                }
                cableAccTipoUsuario.setModificado(new Date());
                cableAccTipoUsuarioFacadeLocal.edit(cableAccTipoUsuario);
                MovilidadUtil.addSuccessMessage("Se a actualizado el tipo usuario correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar tipo usuario");
        }
    }

    /**
     * Permite crear la instancia del objeto CableAccTipoUsuario
     */
    public void prepareGuardar() {
        cableAccTipoUsuario = new CableAccTipoUsuario();
    }

    public void reset() {
        cableAccTipoUsuario = null;
        cNombreAux = "";
    }

    /**
     * Permite capturar el objeto CableAccTipoUsuario seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto CableAccTipoUsuario
     */
    public void onGetCableAccTipoUsuario(CableAccTipoUsuario event) {
        cableAccTipoUsuario = event;
        cNombreAux = event.getTipo();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<CableAccTipoUsuario> findAllEstadoReg = cableAccTipoUsuarioFacadeLocal.findAllEstadoReg();
        for (CableAccTipoUsuario sae : findAllEstadoReg) {
            if (sae.getTipo().equals(cableAccTipoUsuario.getTipo())) {
                return true;
            }
        }
        return false;
    }

    public CableAccTipoUsuario getCableAccTipoUsuario() {
        return cableAccTipoUsuario;
    }

    public void setCableAccTipoUsuario(CableAccTipoUsuario cableAccTipoUsuario) {
        this.cableAccTipoUsuario = cableAccTipoUsuario;
    }

    public List<CableAccTipoUsuario> getListCableAccTipoUsuario() {
        listCableAccTipoUsuario = cableAccTipoUsuarioFacadeLocal.findAllEstadoReg();
        return listCableAccTipoUsuario;
    }

    public void setListCableAccTipoUsuario(List<CableAccTipoUsuario> listCableAccTipoUsuario) {
        this.listCableAccTipoUsuario = listCableAccTipoUsuario;
    }

}
