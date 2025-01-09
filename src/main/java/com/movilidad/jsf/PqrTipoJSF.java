/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PqrTipoFacadeLocal;
import com.movilidad.model.PqrClasificacion;
import com.movilidad.model.PqrResponsable;
import com.movilidad.model.PqrTipo;
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
@Named(value = "pqrTipoJSF")
@ViewScoped
public class PqrTipoJSF implements Serializable {

    @EJB
    private PqrTipoFacadeLocal pqrTipoFacadeLocal;

    private PqrTipo pqrTipo;

    private List<PqrTipo> listPqrTipo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Integer idPrgClasificacion;
    private Integer idPrgResponsable;

    /**
     * Creates a new instance of PqrTipoJSF
     */
    public PqrTipoJSF() {
    }

    /**
     * Permite persistir la data del objeto PqrTipo en la base de datos
     */
    public void guardar() {
        try {
            if (pqrTipo != null) {
                if (validarObjeto()) {
                    return;
                }
                if (validarNombre(pqrTipo.getIdPqrTipo())) {
                    MovilidadUtil.addErrorMessage("Nombre tipo PQR no se encuentra disponible");
                    return;
                }
                cargarObjeto();
                pqrTipo.setCreado(new Date());
                pqrTipo.setModificado(new Date());
                pqrTipo.setEstadoReg(0);
                pqrTipo.setUsername(user.getUsername());
                pqrTipoFacadeLocal.create(pqrTipo);
                MovilidadUtil.addSuccessMessage("Se a registrado tipo PQR correctamente");
                reset();
                pqrTipo = new PqrTipo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar tipo PQR ");
        }
    }

    /**
     * Permite realizar un update del objeto PqrTipo en la base de datos
     */
    public void actualizar() {
        try {
            if (pqrTipo != null) {
                if (validarObjeto()) {
                    return;
                }
                if (validarNombre(pqrTipo.getIdPqrTipo())) {
                    MovilidadUtil.addErrorMessage("Nombre para Nombre tipo PQR no se encuentra disponible");
                    return;
                }
                cargarObjeto();
                pqrTipo.setModificado(new Date());
                pqrTipoFacadeLocal.edit(pqrTipo);
                MovilidadUtil.addSuccessMessage("Se a actualizado el tipo PQR  correctamente");
                reset();
                MovilidadUtil.hideModal("modalDlg");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar tipo PQR ");
        }
    }

    /*
    Permite validar si los objetos necesarios no son nulos*/
    private boolean validarObjeto() {
        if (idPrgClasificacion == null) {
            MovilidadUtil.addErrorMessage("Pqr Clasificación es requerido");
            return true;
        }
        if (idPrgResponsable == null) {
            MovilidadUtil.addErrorMessage("Pqr Responsable es requerido");
            return true;
        }
        return false;
    }

    /* Permite setear los objetos de responsable y clasificacion en el objeto principal de PQRTIPO*/
    private void cargarObjeto() {
        pqrTipo.setIdPqrResponsable(new PqrResponsable(idPrgResponsable));
        pqrTipo.setIdPqrClasificacion(new PqrClasificacion(idPrgClasificacion));
    }

    /**
     * Permite crear la instancia del objeto PqrTipo
     */
    public void prepareGuardar() {
        pqrTipo = new PqrTipo();
    }

    public void reset() {
        pqrTipo = null;
        idPrgClasificacion = null;
        idPrgResponsable = null;
    }

    /**
     * Permite capturar el objeto PqrTipo seleccionado por el usuario
     *
     * @param event Evento que captura el objeto PqrTipo
     */
    public void onGetPqrTipo(PqrTipo event) {
        pqrTipo = event;
        idPrgClasificacion = pqrTipo.getIdPqrClasificacion() != null ? pqrTipo.getIdPqrClasificacion().getIdPqrClasificacion() : null;
        idPrgResponsable = pqrTipo.getIdPqrResponsable() != null ? pqrTipo.getIdPqrResponsable().getIdPqrResponsable() : null;
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @param i identificador de TipoPqr
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre(Integer i) {
        int o = i != null ? i : 0;
        PqrTipo obj = pqrTipoFacadeLocal.verificarRegistro(o, pqrTipo.getNombre());
        return obj != null;
    }

    public PqrTipo getPqrTipo() {
        return pqrTipo;
    }

    public void setPqrTipo(PqrTipo pqrTipo) {
        this.pqrTipo = pqrTipo;
    }

    public List<PqrTipo> getListPqrTipo() {
        listPqrTipo = pqrTipoFacadeLocal.findAllByEstadoReg();
        return listPqrTipo;
    }

    public void setListPqrTipo(List<PqrTipo> listPqrTipo) {
        this.listPqrTipo = listPqrTipo;
    }

    public Integer getIdPrgClasificacion() {
        return idPrgClasificacion;
    }

    public void setIdPrgClasificacion(Integer idPrgClasificacion) {
        this.idPrgClasificacion = idPrgClasificacion;
    }

    public Integer getIdPrgResponsable() {
        return idPrgResponsable;
    }

    public void setIdPrgResponsable(Integer idPrgResponsable) {
        this.idPrgResponsable = idPrgResponsable;
    }

}
