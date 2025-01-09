/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AtvTipoEstadoFacadeLocal;
import com.movilidad.model.AtvTipoAtencion;
import com.movilidad.model.AtvTipoEstado;
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
@Named(value = "atvTipoEstadoJSF")
@ViewScoped
public class AtvTipoEstadoJSF implements Serializable {

    @EJB
    private AtvTipoEstadoFacadeLocal atvTipoEstadoFacadeLocal;

    private AtvTipoEstado atvTipoEstado;

    private List<AtvTipoEstado> listAtvTipoEstado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//    private String cNombreAux;
    private Integer idAtvAtencion;

    /**
     * Creates a new instance of AtvTipoEstadoJSF
     */
    public AtvTipoEstadoJSF() {
//        cNombreAux = "";
    }

    /**
     * Permite persistir la data del objeto AtvTipoEstado en la base de datos
     */
    public void guardar() {
        try {
            if (atvTipoEstado != null) {
//                if (validarNombre()) {
//                    MovilidadUtil.addErrorMessage("Nombre para motivo lavado no se encuentra disponible");
//                    return;
//                }
                if (idAtvAtencion == null) {
                    MovilidadUtil.addErrorMessage("Tipo Atención es requerido");
//                        return;
                }
                atvTipoEstado.setIdAtvTipoAtencion(new AtvTipoAtencion(idAtvAtencion));
                Date d = new Date();
                atvTipoEstado.setCreado(d);
                atvTipoEstado.setModificado(d);
                atvTipoEstado.setEstadoReg(0);
                atvTipoEstado.setUsername(user.getUsername());
                atvTipoEstadoFacadeLocal.create(atvTipoEstado);
                MovilidadUtil.addSuccessMessage("Se a registrado motivo lavado correctamente");
                atvTipoEstado = new AtvTipoEstado();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar motivo lavado");
        }
    }

    /**
     * Permite realizar un update del objeto AtvTipoEstado en la base de datos
     */
    public void actualizar() {
        try {
            if (atvTipoEstado != null) {
//                if (!cNombreAux.equals(atvTipoEstado.getTipoEstado())) {
//                    if (validarNombre()) {
//                        MovilidadUtil.addErrorMessage("Nombre para motivo lavado no se encuentra disponible");
//                        return;
//                    }
//                }
                if (idAtvAtencion == null) {
                    MovilidadUtil.addErrorMessage("Tipo Atención es requerido");
//                        return;
                }
                atvTipoEstado.setIdAtvTipoAtencion(new AtvTipoAtencion(idAtvAtencion));
                atvTipoEstado.setModificado(new Date());
                atvTipoEstadoFacadeLocal.edit(atvTipoEstado);
                MovilidadUtil.addSuccessMessage("Se a actualizado el motivo lavado correctamente");
                reset();
                MovilidadUtil.hideModal("modalDlg");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc tipo");
        }
    }

    /**
     * Permite crear la instancia del objeto AtvTipoEstado
     */
    public void prepareGuardar() {
        atvTipoEstado = new AtvTipoEstado();
        idAtvAtencion = null;
    }

    public void reset() {
        atvTipoEstado = null;
        idAtvAtencion = null;
//        cNombreAux = "";
    }

    /**
     * Permite capturar el objeto AtvTipoEstado seleccionado por el usuario
     *
     * @param event Evento que captura el objeto AtvTipoEstado
     */
    public void onGetAtvTipoEstado(AtvTipoEstado event) {
        atvTipoEstado = event;
        idAtvAtencion = event.getIdAtvTipoAtencion().getIdAtvTipoAtencion();
//        cNombreAux = event.getTipoEstado();
    }

    public AtvTipoEstado getAtvTipoEstado() {
        return atvTipoEstado;
    }

    public void setAtvTipoEstado(AtvTipoEstado atvTipoEstado) {
        this.atvTipoEstado = atvTipoEstado;
    }

    public List<AtvTipoEstado> getListAtvTipoEstado() {
        listAtvTipoEstado = atvTipoEstadoFacadeLocal.findByEstadoReg();
        return listAtvTipoEstado;
    }

    public void setListAtvTipoEstado(List<AtvTipoEstado> listAtvTipoEstado) {
        this.listAtvTipoEstado = listAtvTipoEstado;
    }

    public Integer getIdAtvAtencion() {
        return idAtvAtencion;
    }

    public void setIdAtvAtencion(Integer idAtvAtencion) {
        this.idAtvAtencion = idAtvAtencion;
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
//    boolean validarNombre() {
//        List<AtvTipoEstado> list = atvTipoEstadoFacadeLocal.findByEstadoReg();
//        return list
//                .stream()
//                .filter(lm -> lm.getIdAtvTipoAtencion().getIdAtvTipoAtencion().equals(atvTipoEstado.getid))
//                .map(lm -> lm.getTipoEstado())
//                .anyMatch(lm -> lm.equalsIgnoreCase(atvTipoEstado.getTipoEstado()));
//    }
}
