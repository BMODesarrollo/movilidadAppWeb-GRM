/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.SstEpsFacadeLocal;
import com.movilidad.model.SstEps;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite gestionar toda los datos para el objeto SstEps principal tabla
 * afectada sst_eps
 *
 * @author cesar
 */
@Named(value = "sstEpsJSF")
@ViewScoped
public class SstEpsJSF implements Serializable {

    @EJB
    private SstEpsFacadeLocal sstEpsFacadeLocal;

    private SstEps sstEps;

    private List<SstEps> listSstEps;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreEpsAux;

    /**
     * Creates a new instance of SstEpsJSF
     */
    public SstEpsJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreEpsAux = "";
    }

    /**
     * Permite persistir la data del objeto SstEps en la base de datos
     */
    public void guardar() {
        try {
            if (sstEps != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para sst eps no se encuentra disponible");
                    return;
                }
                sstEps.setCreado(new Date());
                sstEps.setModificado(new Date());
                sstEps.setEstadoReg(0);
                sstEps.setUsername(user.getUsername());
                sstEpsFacadeLocal.create(sstEps);
                MovilidadUtil.addSuccessMessage("Se a registrado sst eps correctamente");
                sstEps = new SstEps();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar sst eps");
        }
    }

    /**
     * Permite realizar un update del objeto SstEps en la base de datos
     */
    public void actualizar() {
        try {
            if (sstEps != null) {
                if (!cNombreEpsAux.equals(sstEps.getNombre())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para sst eps no se encuentra disponible");
                        return;
                    }
                }
                sstEps.setModificado(new Date());
                sstEpsFacadeLocal.edit(sstEps);
                MovilidadUtil.addSuccessMessage("Se a actualizado el sst eps correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar sst eps");
        }
    }

    /**
     * Permite crear la instancia del objeto SstEps
     */
    public void prepareGuardar() {
        sstEps = new SstEps();
    }

    public void reset() {
        sstEps = null;
        cNombreEpsAux = "";
    }

    /**
     * Permite capturar el objeto SstEps seleccionado por el usuario
     *
     * @param event Evento que captura el objeto SstEps
     */
    public void onGetSstEps(SstEps event) {
        sstEps = event;
        cNombreEpsAux = event.getNombre();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<SstEps> findAllEstadoReg = sstEpsFacadeLocal.findAllEstadoReg();
        for (SstEps sae : findAllEstadoReg) {
            if (sae.getNombre().equals(sstEps.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public SstEps getSstEps() {
        return sstEps;
    }

    public void setSstEps(SstEps sstEps) {
        this.sstEps = sstEps;
    }

    public List<SstEps> getListSstEps() {
        listSstEps = sstEpsFacadeLocal.findAllEstadoReg();
        return listSstEps;
    }

    public void setListSstEps(List<SstEps> listSstEps) {
        this.listSstEps = listSstEps;
    }

}
