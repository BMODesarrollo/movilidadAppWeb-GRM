/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.SstVehiculoTipoFacadeLocal;
import com.movilidad.model.SstVehiculoTipo;
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
 * Permite gestionar toda los datos para el objeto SstVehiculoTipo principal
 * tabla afectada sst_vehiculo_tipo
 *
 * @author cesar
 */
@Named(value = "sstVehiculoTipoJSF")
@ViewScoped
public class SstVehiculoTipoJSF implements Serializable {

    @EJB
    private SstVehiculoTipoFacadeLocal sstVehiculoTipoFacadeLocal;

    private SstVehiculoTipo sstVehiculoTipo;

    private List<SstVehiculoTipo> listSstVehiculoTipo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreVehiculoTipoAux;

    /**
     * Creates a new instance of SstVehiculoTipoJSF
     */
    public SstVehiculoTipoJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreVehiculoTipoAux = "";
    }

    /**
     * Permite persistir la data del objeto SstVehiculoTipo en la base de datos
     */
    public void guardar() {
        try {
            if (sstVehiculoTipo != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para sst vehículo tipo no se encuentra disponible");
                    return;
                }
                sstVehiculoTipo.setCreado(new Date());
                sstVehiculoTipo.setModificado(new Date());
                sstVehiculoTipo.setEstadoReg(0);
                sstVehiculoTipo.setUsername(user.getUsername());
                sstVehiculoTipoFacadeLocal.create(sstVehiculoTipo);
                MovilidadUtil.addSuccessMessage("Se a registrado sst vehículo tipo correctamente");
                sstVehiculoTipo = new SstVehiculoTipo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar sst vehículo tipo");
        }
    }

    /**
     * Permite realizar un update del objeto SstVehiculoTipo en la base de datos
     */
    public void actualizar() {
        try {
            if (sstVehiculoTipo != null) {
                if (!cNombreVehiculoTipoAux.equals(sstVehiculoTipo.getTipo())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para sst vehículo tipo no se encuentra disponible");
                        return;
                    }
                }
                sstVehiculoTipo.setModificado(new Date());
                sstVehiculoTipoFacadeLocal.edit(sstVehiculoTipo);
                MovilidadUtil.addSuccessMessage("Se a actualizado el sst vehículo tipo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar sst vehículo tipo");
        }
    }

    /**
     * Permite crear la instancia del objeto SstVehiculoTipo
     */
    public void prepareGuardar() {
        sstVehiculoTipo = new SstVehiculoTipo();
    }

    public void reset() {
        sstVehiculoTipo = null;
        cNombreVehiculoTipoAux = "";
    }

    /**
     * Permite capturar el objeto SstVehiculoTipo seleccionado por el usuario
     *
     * @param event Evento que captura el objeto SstVehiculoTipo
     */
    public void onGetSstVehiculoTipo(SstVehiculoTipo event) {
        sstVehiculoTipo = event;
        cNombreVehiculoTipoAux = event.getTipo();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<SstVehiculoTipo> findAllEstadoReg = sstVehiculoTipoFacadeLocal.findAllEstadoReg();
        for (SstVehiculoTipo sae : findAllEstadoReg) {
            if (sae.getTipo().equals(sstVehiculoTipo.getTipo())) {
                return true;
            }
        }
        return false;
    }

    public SstVehiculoTipo getSstVehiculoTipo() {
        return sstVehiculoTipo;
    }

    public void setSstVehiculoTipo(SstVehiculoTipo sstVehiculoTipo) {
        this.sstVehiculoTipo = sstVehiculoTipo;
    }

    public List<SstVehiculoTipo> getListSstVehiculoTipo() {
        listSstVehiculoTipo = sstVehiculoTipoFacadeLocal.findAllEstadoReg();
        return listSstVehiculoTipo;
    }

    public void setListSstVehiculoTipo(List<SstVehiculoTipo> listSstVehiculoTipo) {
        this.listSstVehiculoTipo = listSstVehiculoTipo;
    }

}
