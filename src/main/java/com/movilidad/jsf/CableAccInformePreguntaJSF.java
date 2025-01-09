/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccInformePreguntaFacadeLocal;
import com.movilidad.model.CableAccInformePregunta;
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
 * Permite gestionar toda los datos para el objeto CableAccInformePregunta
 * principal tabla afectada cable_acc_informe_pregunta
 *
 * @author soluciones-it
 */
@Named(value = "cableAccInformePreguntaJSF")
@ViewScoped
public class CableAccInformePreguntaJSF implements Serializable {

    @EJB
    private CableAccInformePreguntaFacadeLocal cableAccInformePreguntaFacadeLocal;

    private CableAccInformePregunta cableAccInformePregunta;

    private List<CableAccInformePregunta> listCableAccInformePregunta;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;
    private boolean bRequerido;

    /**
     * Creates a new instance of CableAccInformePreguntaJSF
     */
    public CableAccInformePreguntaJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAux = "";
        bRequerido = false;
    }

    /**
     * Permite persistir la data del objeto CableAccInformePregunta en la base
     * de datos
     */
    public void guardar() {
        try {
            if (cableAccInformePregunta != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para acc pregunta no se encuentra disponible");
                    return;
                }
                cableAccInformePregunta.setRequerido(bRequerido ? 1 : 0);
                cableAccInformePregunta.setCreado(new Date());
                cableAccInformePregunta.setModificado(new Date());
                cableAccInformePregunta.setEstadoReg(0);
                cableAccInformePregunta.setUsername(user.getUsername());
                cableAccInformePreguntaFacadeLocal.create(cableAccInformePregunta);
                MovilidadUtil.addSuccessMessage("Se a registrado acc pregunta correctamente");
                cableAccInformePregunta = new CableAccInformePregunta();
                bRequerido = false;
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar sst eps");
        }
    }

    /**
     * Permite realizar un update del objeto CableAccInformePregunta en la base
     * de datos
     */
    public void actualizar() {
        try {
            if (cableAccInformePregunta != null) {
                if (!cNombreAux.equals(cableAccInformePregunta.getPregunta())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para acc pregunta no se encuentra disponible");
                        return;
                    }
                }
                cableAccInformePregunta.setRequerido(bRequerido ? 1 : 0);
                cableAccInformePregunta.setModificado(new Date());
                cableAccInformePreguntaFacadeLocal.edit(cableAccInformePregunta);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc pregunta correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc pregunta");
        }
    }

    /**
     * Permite crear la instancia del objeto CableAccInformePregunta
     */
    public void prepareGuardar() {
        cableAccInformePregunta = new CableAccInformePregunta();
    }

    public void reset() {
        cableAccInformePregunta = null;
        cNombreAux = "";
        bRequerido = false;
    }

    /**
     * Permite capturar el objeto CableAccInformePregunta seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto CableAccInformePregunta
     */
    public void onGetCableAccInformePregunta(CableAccInformePregunta event) {
        cableAccInformePregunta = event;
        cNombreAux = event.getPregunta();
        bRequerido = event.getRequerido().equals(1);
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<CableAccInformePregunta> findAllEstadoReg = cableAccInformePreguntaFacadeLocal.findAllEstadoReg();
        for (CableAccInformePregunta sae : findAllEstadoReg) {
            if (sae.getPregunta().equals(cableAccInformePregunta.getPregunta())) {
                return true;
            }
        }
        return false;
    }

    public CableAccInformePregunta getCableAccInformePregunta() {
        return cableAccInformePregunta;
    }

    public void setCableAccInformePregunta(CableAccInformePregunta cableAccInformePregunta) {
        this.cableAccInformePregunta = cableAccInformePregunta;
    }

    public List<CableAccInformePregunta> getListCableAccInformePregunta() {
        listCableAccInformePregunta = cableAccInformePreguntaFacadeLocal.findAllEstadoReg();
        return listCableAccInformePregunta;
    }

    public void setListCableAccInformePregunta(List<CableAccInformePregunta> listCableAccInformePregunta) {
        this.listCableAccInformePregunta = listCableAccInformePregunta;
    }

    public boolean isbRequerido() {
        return bRequerido;
    }

    public void setbRequerido(boolean bRequerido) {
        this.bRequerido = bRequerido;
    }

}
