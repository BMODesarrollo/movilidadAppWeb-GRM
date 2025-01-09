/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PqrMedioReporteFacadeLocal;
import com.movilidad.model.PqrMedioReporte;
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
@Named(value = "pqrMedioReporteJSF")
@ViewScoped
public class PqrMedioReporteJSF implements Serializable {

    @EJB
    private PqrMedioReporteFacadeLocal pqrMedioReporteFacadeLocal;

    private PqrMedioReporte pqrMedioReporte;

    private List<PqrMedioReporte> listPqrMedioReporte;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of PqrMedioReporteJSF
     */
    public PqrMedioReporteJSF() {
    }

    /**
     * Permite persistir la data del objeto PqrMedioReporte en la base de datos
     */
    public void guardar() {
        try {
            if (pqrMedioReporte != null) {
                if (validarNombre(pqrMedioReporte.getIdPqrMedioReporte())) {
                    MovilidadUtil.addErrorMessage("Nombre medio reporte PQR no se encuentra disponible");
                    return;
                }
                pqrMedioReporte.setCreado(new Date());
                pqrMedioReporte.setModificado(new Date());
                pqrMedioReporte.setEstadoReg(0);
                pqrMedioReporte.setUsername(user.getUsername());
                pqrMedioReporteFacadeLocal.create(pqrMedioReporte);
                MovilidadUtil.addSuccessMessage("Se a registrado medio reporte PQR correctamente");
                pqrMedioReporte = new PqrMedioReporte();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar medio reporte PQR ");
        }
    }

    /**
     * Permite realizar un update del objeto PqrMedioReporte en la base de datos
     */
    public void actualizar() {
        try {
            if (pqrMedioReporte != null) {
                if (validarNombre(pqrMedioReporte.getIdPqrMedioReporte())) {
                    MovilidadUtil.addErrorMessage("Nombre para Nombre medio reporte PQR no se encuentra disponible");
                    return;
                }
                pqrMedioReporte.setModificado(new Date());
                pqrMedioReporteFacadeLocal.edit(pqrMedioReporte);
                MovilidadUtil.addSuccessMessage("Se a actualizado el medio reporte PQR  correctamente");
                reset();
                MovilidadUtil.hideModal("modalDlg");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar medio reporte PQR ");
        }
    }

    /**
     * Permite crear la instancia del objeto PqrMedioReporte
     */
    public void prepareGuardar() {
        pqrMedioReporte = new PqrMedioReporte();
    }

    public void reset() {
        pqrMedioReporte = null;
    }

    /**
     * Permite capturar el objeto PqrMedioReporte seleccionado por el usuario
     *
     * @param event Evento que captura el objeto PqrMedioReporte
     */
    public void onGetPqrMedioReporte(PqrMedioReporte event) {
        pqrMedioReporte = event;
    }

    /**
     * Permite validar si el valor asignado al atributo area se encuentra
     * registrado en el sistema
     *
     * @param i identificador de MedioReportePqr
     * @return false si el nombre no se encuentra
     */
    boolean validarNombre(Integer i) {
        int o = i != null ? i : 0;
        PqrMedioReporte obj = pqrMedioReporteFacadeLocal.verificarRegistro(o, pqrMedioReporte.getNombre());
        return obj != null;
    }

    public PqrMedioReporte getPqrMedioReporte() {
        return pqrMedioReporte;
    }

    public void setPqrMedioReporte(PqrMedioReporte pqrMedioReporte) {
        this.pqrMedioReporte = pqrMedioReporte;
    }

    public List<PqrMedioReporte> getListPqrMedioReporte() {
        listPqrMedioReporte = pqrMedioReporteFacadeLocal.findAllByEstadoReg();
        return listPqrMedioReporte;
    }

    public void setListPqrMedioReporte(List<PqrMedioReporte> listPqrMedioReporte) {
        this.listPqrMedioReporte = listPqrMedioReporte;
    }
    
    

}
