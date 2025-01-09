/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.SstArlFacadeLocal;
import com.movilidad.model.SstArl;
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
 * Permite gestionar toda los datos para el objeto SstArl principal tabla
 * afectada sst_arl
 *
 * @author cesar
 */
@Named(value = "sstArlJSF")
@ViewScoped
public class SstArlJSF implements Serializable {

    @EJB
    private SstArlFacadeLocal sstArlFacadeLocal;

    private SstArl sstArl;

    private List<SstArl> listSstArl;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreArlAux;

    public SstArlJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreArlAux = "";
    }

    /**
     * Permite persistir la data del objeto SstArl en la base de datos
     */
    public void guardar() {
        try {
            if (sstArl != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para sst arl no se encuentra disponible");
                    return;
                }
                sstArl.setCreado(new Date());
                sstArl.setModificado(new Date());
                sstArl.setEstadoReg(0);
                sstArl.setUsername(user.getUsername());
                sstArlFacadeLocal.create(sstArl);
                MovilidadUtil.addSuccessMessage("Se a registrado sst arl correctamente");
                sstArl = new SstArl();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar sst arl");
        }
    }

    /**
     * Permite realizar un update del objeto SstArl en la base de datos
     */
    public void actualizar() {
        try {
            if (sstArl != null) {
                if (!cNombreArlAux.equals(sstArl.getNombre())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para sst arl no se encuentra disponible");
                        return;
                    }
                }
                sstArl.setModificado(new Date());
                sstArlFacadeLocal.edit(sstArl);
                MovilidadUtil.addSuccessMessage("Se a actualizado el sst arl correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar sst arl");
        }
    }

    /**
     * Permite crear la instancia del objeto SstArl
     */
    public void prepareGuardar() {
        sstArl = new SstArl();
    }

    public void reset() {
        sstArl = null;
        cNombreArlAux = "";
    }

    /**
     * Permite capturar el objeto SstArl seleccionado por el usuario
     *
     * @param event Evento que captura el objeto SstArl
     */
    public void onGetSstArl(SstArl event) {
        sstArl = event;
        cNombreArlAux = event.getNombre();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<SstArl> findAllEstadoReg = sstArlFacadeLocal.findAllEstadoReg();
        for (SstArl sae : findAllEstadoReg) {
            if (sae.getNombre().equals(sstArl.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public SstArl getSstArl() {
        return sstArl;
    }

    public void setSstArl(SstArl sstArl) {
        this.sstArl = sstArl;
    }

    public List<SstArl> getListSstArl() {
        listSstArl = sstArlFacadeLocal.findAllEstadoReg();
        return listSstArl;
    }

    public void setListSstArl(List<SstArl> listSstArl) {
        this.listSstArl = listSstArl;
    }

}
