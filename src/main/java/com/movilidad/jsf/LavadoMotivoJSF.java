/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.LavadoMotivoFacadeLocal;
import com.movilidad.model.LavadoMotivo;
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
@Named(value = "lavadoMotivoJSF")
@ViewScoped
public class LavadoMotivoJSF implements Serializable {

    @EJB
    private LavadoMotivoFacadeLocal lavadoMotivoFacadeLocal;

    private LavadoMotivo lavadoMotivo;

    private List<LavadoMotivo> listLavadoMotivo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreAux;

    /**
     * Creates a new instance of LavadoMotivoJSF
     */
    public LavadoMotivoJSF() {
        cNombreAux = "";
    }

    /**
     * Permite persistir la data del objeto LavadoMotivo en la base de datos
     */
    public void guardar() {
        try {
            if (lavadoMotivo != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para motivo lavado no se encuentra disponible");
                    return;
                }
                lavadoMotivo.setCreado(new Date());
                lavadoMotivo.setModificado(new Date());
                lavadoMotivo.setEstadoReg(0);
                lavadoMotivo.setUsername(user.getUsername());
                lavadoMotivoFacadeLocal.create(lavadoMotivo);
                MovilidadUtil.addSuccessMessage("Se a registrado motivo lavado correctamente");
                lavadoMotivo = new LavadoMotivo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar motivo lavado");
        }
    }

    /**
     * Permite realizar un update del objeto LavadoMotivo en la base de datos
     */
    public void actualizar() {
        try {
            if (lavadoMotivo != null) {
                if (!cNombreAux.equals(lavadoMotivo.getMotivo())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para motivo lavado no se encuentra disponible");
                        return;
                    }
                }
                lavadoMotivo.setModificado(new Date());
                lavadoMotivoFacadeLocal.edit(lavadoMotivo);
                MovilidadUtil.addSuccessMessage("Se a actualizado el motivo lavado correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc tipo");
        }
    }

    /**
     * Permite crear la instancia del objeto LavadoMotivo
     */
    public void prepareGuardar() {
        lavadoMotivo = new LavadoMotivo();
    }

    public void reset() {
        lavadoMotivo = null;
        cNombreAux = "";
    }

    /**
     * Permite capturar el objeto LavadoMotivo seleccionado por el usuario
     *
     * @param event Evento que captura el objeto LavadoMotivo
     */
    public void onGetLavadoMotivo(LavadoMotivo event) {
        lavadoMotivo = event;
        cNombreAux = event.getMotivo();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<LavadoMotivo> list = lavadoMotivoFacadeLocal.findAllEstadoReg();
        return list
                .stream()
                .map(lm -> lm.getMotivo())
                .anyMatch(lm -> lm.equalsIgnoreCase(lavadoMotivo.getMotivo()));
    }

    public LavadoMotivo getLavadoMotivo() {
        return lavadoMotivo;
    }

    public void setLavadoMotivo(LavadoMotivo lavadoMotivo) {
        this.lavadoMotivo = lavadoMotivo;
    }

    public List<LavadoMotivo> getListLavadoMotivo() {
        listLavadoMotivo = lavadoMotivoFacadeLocal.findAllEstadoReg();
        return listLavadoMotivo;
    }

    public void setListLavadoMotivo(List<LavadoMotivo> listLavadoMotivo) {
        this.listLavadoMotivo = listLavadoMotivo;
    }

}
