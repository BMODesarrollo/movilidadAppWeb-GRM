/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.SstMatEquiTipoFacadeLocal;
import com.movilidad.model.SstMatEquiTipo;
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
 * Permite gestionar toda los datos para el objeto SstMatEquiTipo principal
 * tabla afectada sst_mat_equi_tipo
 *
 * @author cesar
 */
@Named(value = "sstMatEquiTipoJSF")
@ViewScoped
public class SstMatEquiTipoJSF implements Serializable {

    @EJB
    private SstMatEquiTipoFacadeLocal sstMatEquiTipoFacadeLocal;

    private SstMatEquiTipo sstMatEquiTipo;

    private List<SstMatEquiTipo> listSstMatEquiTipo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreMatEquiTipoAux;

    /**
     * Creates a new instance of SstMatEquiTipoJSF
     */
    public SstMatEquiTipoJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreMatEquiTipoAux = "";
    }

    /**
     * Permite persistir la data del objeto SstMatEquiTipo en la base de datos
     */
    public void guardar() {
        try {
            if (sstMatEquiTipo != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para sst materiales equipo tipo no se encuentra disponible");
                    return;
                }
                sstMatEquiTipo.setCreado(new Date());
                sstMatEquiTipo.setModificado(new Date());
                sstMatEquiTipo.setEstadoReg(0);
                sstMatEquiTipo.setUsername(user.getUsername());
                sstMatEquiTipoFacadeLocal.create(sstMatEquiTipo);
                MovilidadUtil.addSuccessMessage("Se a registrado sst materiales equipo tipo correctamente");
                sstMatEquiTipo = new SstMatEquiTipo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar sst materiales equipo tipo");
        }
    }

    /**
     * Permite realizar un update del objeto SstMatEquiTipo en la base de datos
     */
    public void actualizar() {
        try {
            if (sstMatEquiTipo != null) {
                if (!cNombreMatEquiTipoAux.equals(sstMatEquiTipo.getTipo())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para sst materiales equipo tipo no se encuentra disponible");
                        return;
                    }
                }
                sstMatEquiTipo.setModificado(new Date());
                sstMatEquiTipoFacadeLocal.edit(sstMatEquiTipo);
                MovilidadUtil.addSuccessMessage("Se a actualizado el sst materiales equipo tipo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar sst materiales equipo tipo");
        }
    }

    /**
     * Permite crear la instancia del objeto SstMatEquiTipo
     */
    public void prepareGuardar() {
        sstMatEquiTipo = new SstMatEquiTipo();
    }

    public void reset() {
        sstMatEquiTipo = null;
        cNombreMatEquiTipoAux = "";
    }

    /**
     * Permite capturar el objeto SstMatEquiTipo seleccionado por el usuario
     *
     * @param event Evento que captura el objeto SstMatEquiTipo
     */
    public void onGetSstMatEquiTipo(SstMatEquiTipo event) {
        sstMatEquiTipo = event;
        cNombreMatEquiTipoAux = event.getTipo();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<SstMatEquiTipo> findAllEstadoReg = sstMatEquiTipoFacadeLocal.findAllByEstadoReg();
        for (SstMatEquiTipo sae : findAllEstadoReg) {
            if (sae.getTipo().equals(sstMatEquiTipo.getTipo())) {
                return true;
            }
        }
        return false;
    }

    public SstMatEquiTipo getSstMatEquiTipo() {
        return sstMatEquiTipo;
    }

    public void setSstMatEquiTipo(SstMatEquiTipo sstMatEquiTipo) {
        this.sstMatEquiTipo = sstMatEquiTipo;
    }

    public List<SstMatEquiTipo> getListSstMatEquiTipo() {
        listSstMatEquiTipo = sstMatEquiTipoFacadeLocal.findAllByEstadoReg();
        return listSstMatEquiTipo;
    }

    public void setListSstMatEquiTipo(List<SstMatEquiTipo> listSstMatEquiTipo) {
        this.listSstMatEquiTipo = listSstMatEquiTipo;
    }

}
