/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccTpAsistenciaFacadeLocal;
import com.movilidad.model.CableAccTpAsistencia;
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
@Named(value = "cableAccTpAsistenciaJSF")
@ViewScoped
public class CableAccTpAsistenciaJSF implements Serializable {

    @EJB
    private CableAccTpAsistenciaFacadeLocal cableAccTpAsistenciaFacadeLocal;

    private CableAccTpAsistencia cableAccTpAsistencia;

    private List<CableAccTpAsistencia> listCableAccTpAsistencia;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;

    /**
     * Creates a new instance of CableAccTpAsistenciaJSF
     */
    public CableAccTpAsistenciaJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreAux = "";
    }

    /**
     * Permite persistir la data del objeto CableAccTpAsistencia en la base de
     * datos
     */
    public void guardar() {
        if (cableAccTpAsistencia != null) {
            if (validarNombre()) {
                MovilidadUtil.addErrorMessage("Nombre para acc tipo asistencia no se encuentra disponible");
                return;
            }
            cableAccTpAsistencia.setCreado(new Date());
            cableAccTpAsistencia.setModificado(new Date());
            cableAccTpAsistencia.setEstadoReg(0);
            cableAccTpAsistencia.setUsername(user.getUsername());
            cableAccTpAsistenciaFacadeLocal.create(cableAccTpAsistencia);
            MovilidadUtil.addSuccessMessage("Se a registrado acc tipo asistencia correctamente");
            cableAccTpAsistencia = new CableAccTpAsistencia();
        }
    }

    /**
     * Permite realizar un update del objeto CableAccTpAsistencia en la base de
     * datos
     */
    public void actualizar() {
        if (cableAccTpAsistencia != null) {
            if (!cNombreAux.equals(cableAccTpAsistencia.getTipo())) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para acc tipo asistencia no se encuentra disponible");
                    return;
                }
            }
            cableAccTpAsistencia.setModificado(new Date());
            cableAccTpAsistenciaFacadeLocal.edit(cableAccTpAsistencia);
            MovilidadUtil.addSuccessMessage("Se a actualizado el acc tipo asistencia correctamente");
            reset();
            PrimeFaces.current().executeScript("PF('modalDlg').hide();");
        }
    }

    /**
     * Permite crear la instancia del objeto CableAccTpAsistencia
     */
    public void prepareGuardar() {
        cableAccTpAsistencia = new CableAccTpAsistencia();
    }

    public void reset() {
        cableAccTpAsistencia = null;
        cNombreAux = "";
    }

    /**
     * Permite capturar el objeto CableAccTpAsistencia seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto CableAccTpAsistencia
     */
    public void onGetCableAccTpAsistencia(CableAccTpAsistencia event) {
        cableAccTpAsistencia = event;
        cNombreAux = event.getTipo();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<CableAccTpAsistencia> findAllEstadoReg = cableAccTpAsistenciaFacadeLocal.findAllEstadoReg();
        for (CableAccTpAsistencia sae : findAllEstadoReg) {
            if (sae.getTipo().equals(cableAccTpAsistencia.getTipo())) {
                return true;
            }
        }
        return false;
    }

    public CableAccTpAsistencia getCableAccTpAsistencia() {
        return cableAccTpAsistencia;
    }

    public void setCableAccTpAsistencia(CableAccTpAsistencia cableAccTpAsistencia) {
        this.cableAccTpAsistencia = cableAccTpAsistencia;
    }

    public List<CableAccTpAsistencia> getListCableAccTpAsistencia() {
        listCableAccTpAsistencia = cableAccTpAsistenciaFacadeLocal.findAllEstadoReg();
        return listCableAccTpAsistencia;
    }

    public void setListCableAccTpAsistencia(List<CableAccTpAsistencia> listCableAccTpAsistencia) {
        this.listCableAccTpAsistencia = listCableAccTpAsistencia;
    }

}
