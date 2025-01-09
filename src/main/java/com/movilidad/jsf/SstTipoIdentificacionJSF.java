/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.SstTipoIdentificacionFacadeLocal;
import com.movilidad.model.SstTipoIdentificacion;
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
 * Permite gestionar toda los datos para el objeto SstTipoIdentificacion
 * principal tabla afectada sst_tipo?identificacion
 *
 * @author cesar
 */
@Named(value = "sstTipoIdentificacionJSF")
@ViewScoped
public class SstTipoIdentificacionJSF implements Serializable {

    @EJB
    private SstTipoIdentificacionFacadeLocal sstTipoIdentificacionFacadeLocal;

    private SstTipoIdentificacion sstTipoIdentificacion;

    private List<SstTipoIdentificacion> listSstTipoIdentificacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String cNombreTipoIdentificacionAux;

    /**
     * Creates a new instance of SstTipoIdentificacionJSF
     */
    public SstTipoIdentificacionJSF() {
    }

    @PostConstruct
    public void init() {
        cNombreTipoIdentificacionAux = "";
    }

    /**
     * Permite persistir la data del objeto SstTipoIdentificacion en la base de
     * datos
     */
    public void guardar() {
        try {
            if (sstTipoIdentificacion != null) {
                if (validarNombre()) {
                    MovilidadUtil.addErrorMessage("Nombre para sst tipo identificación no se encuentra disponible");
                    return;
                }
                sstTipoIdentificacion.setCreado(new Date());
                sstTipoIdentificacion.setModificado(new Date());
                sstTipoIdentificacion.setEstadoReg(0);
                sstTipoIdentificacion.setUsername(user.getUsername());
                sstTipoIdentificacionFacadeLocal.create(sstTipoIdentificacion);
                MovilidadUtil.addSuccessMessage("Se a registrado sst tipo identificación correctamente");
                sstTipoIdentificacion = new SstTipoIdentificacion();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar sst tipo identificación");
        }
    }

    /**
     * Permite realizar un update del objeto SstTipoIdentificacion en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (sstTipoIdentificacion != null) {
                if (!cNombreTipoIdentificacionAux.equals(sstTipoIdentificacion.getNombre())) {
                    if (validarNombre()) {
                        MovilidadUtil.addErrorMessage("Nombre para sst tipo identificación no se encuentra disponible");
                        return;
                    }
                }
                sstTipoIdentificacion.setModificado(new Date());
                sstTipoIdentificacionFacadeLocal.edit(sstTipoIdentificacion);
                MovilidadUtil.addSuccessMessage("Se a actualizado el sst tipo identificación correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar sst tipo identificación");
        }
    }

    /**
     * Permite crear la instancia del objeto SstTipoIdentificacion
     */
    public void prepareGuardar() {
        sstTipoIdentificacion = new SstTipoIdentificacion();
    }

    public void reset() {
        sstTipoIdentificacion = null;
        cNombreTipoIdentificacionAux = "";
    }

    /**
     * Permite capturar el objeto SstTipoIdentificacion seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto SstTipoIdentificacion
     */
    public void onGetSstTipoIdentificacion(SstTipoIdentificacion event) {
        sstTipoIdentificacion = event;
        cNombreTipoIdentificacionAux = event.getNombre();
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre() {
        List<SstTipoIdentificacion> findAllEstadoReg = sstTipoIdentificacionFacadeLocal.findAllEstadoReg();
        for (SstTipoIdentificacion sae : findAllEstadoReg) {
            if (sae.getNombre().equals(sstTipoIdentificacion.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public SstTipoIdentificacion getSstTipoIdentificacion() {
        return sstTipoIdentificacion;
    }

    public void setSstTipoIdentificacion(SstTipoIdentificacion sstTipoIdentificacion) {
        this.sstTipoIdentificacion = sstTipoIdentificacion;
    }

    public List<SstTipoIdentificacion> getListSstTipoIdentificacion() {
        listSstTipoIdentificacion = sstTipoIdentificacionFacadeLocal.findAllEstadoReg();
        return listSstTipoIdentificacion;
    }

    public void setListSstTipoIdentificacion(List<SstTipoIdentificacion> listSstTipoIdentificacion) {
        this.listSstTipoIdentificacion = listSstTipoIdentificacion;
    }

}
