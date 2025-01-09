/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadSeguimientoFacadeLocal;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadSeguimiento;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;

/**
 *
 * @author solucionesit
 */
@Named(value = "novDuplicadaBean")
@ViewScoped
public class NovedadDuplicadaBean implements Serializable {

    /**
     * Creates a new instance of NovedadDuplicadaBean
     */
    public NovedadDuplicadaBean() {
    }

    private Novedad novedad;
    private String observacion;
    private String username;
    private String dialogToCerrar;

    @EJB
    private NovedadFacadeLocal novEJB;
    @EJB
    private NovedadSeguimientoFacadeLocal seguimientoEJB;

    boolean validarDuplicidadNovConVehiculo(String observacionParam, String usernameParam, Integer idVehiculo, boolean openModal) {
        observacion = observacionParam;
        username = usernameParam;
        if (idVehiculo != null) {
            novedad = novEJB.findNovedadByIdVehiculo(
                    idVehiculo,
                    ConstantsUtil.NOV_ESTADO_ABIERTO);
            if (novedad != null) {
                if (openModal) {
                    MovilidadUtil.openModal("wv_nov_duplicada");
                    MovilidadUtil.updateComponent("form_nov_duplicada");
                }
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void saveSeguimiento() {
        NovedadSeguimiento NovedadSeguimiento = new NovedadSeguimiento();
        NovedadSeguimiento.setUsername(username);
        NovedadSeguimiento.setEstadoReg(0);
        NovedadSeguimiento.setIdNovedad(novedad);
        NovedadSeguimiento.setSeguimiento(observacion);
        NovedadSeguimiento.setFecha(MovilidadUtil.fechaHoy());
        NovedadSeguimiento.setCreado(MovilidadUtil.fechaCompletaHoy());
        seguimientoEJB.create(NovedadSeguimiento);
//        System.out.println("NovedadSeguimiento->" + NovedadSeguimiento.getIdNovedad());

        if (novedad.getNovedadSeguimientoList() == null) {
            novedad.setNovedadSeguimientoList(new ArrayList<>());
        }
        novedad.getNovedadSeguimientoList().add(NovedadSeguimiento);
        MovilidadUtil.hideModal("wv_nov_duplicada");
        MovilidadUtil.hideModal(dialogToCerrar);
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDialogToCerrar() {
        return dialogToCerrar;
    }

    public void setDialogToCerrar(String dialogToCerrar) {
        this.dialogToCerrar = dialogToCerrar;
    }

}
