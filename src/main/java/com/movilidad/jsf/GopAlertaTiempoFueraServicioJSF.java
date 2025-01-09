/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopAlertaTiempoFueraServicioFacadeLocal;
import com.movilidad.model.GopAlertaTiempoFueraServicio;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.ObjetoSigleton;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite parametrizar la data relacionada con los objetos
 * GopAlertaTiempoFueraServicio Principal tabla afectada
 * gop_alerta_tiempo_detenido
 *
 * @author soluciones-it
 */
@Named(value = "gopFueraServicioBean")
@ViewScoped
public class GopAlertaTiempoFueraServicioJSF implements Serializable {

    @EJB
    private GopAlertaTiempoFueraServicioFacadeLocal tiempoFueraServicioEJB;

    private GopAlertaTiempoFueraServicio tiempoFueraServicio;

    private List<GopAlertaTiempoFueraServicio> list;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of GopAlertaTiempoFueraServicioJSF
     */
    public GopAlertaTiempoFueraServicioJSF() {
    }

    @PostConstruct
    public void init() {
        consultar();
    }

    /**
     * Permite persistir la data del objeto GopAlertaTiempoFueraServicio en la
     * base de datos
     */
    public void guardar() {
        try {
            if (tiempoFueraServicio != null) {
                if (list != null && list.size() > 1) {
                    MovilidadUtil.addErrorMessage("Ya se encuentra un registro parametrizado");
                    return;
                }
                tiempoFueraServicio.setCreado(MovilidadUtil.fechaCompletaHoy());
                tiempoFueraServicio.setEstadoReg(0);
                tiempoFueraServicio.setUsername(user.getUsername());
                tiempoFueraServicioEJB.create(tiempoFueraServicio);
                MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
                consultar();
                MovilidadUtil.hideModal("gop_fuera_servicio_dlg_wv");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage(ConstantsUtil.ERROR_SAVE);
        }
    }

    void setObjetoSigleton(List<GopAlertaTiempoFueraServicio> param) {
        if (param.isEmpty()) {
            return;
        }
        ObjetoSigleton.setGopAlertaTiempoFueraServicio(param.get(0));
    }

    /**
     * Permite realizar un update del objeto GopAlertaTiempoFueraServicio en la
     * base de datos
     */
    public void actualizar() {
        try {
            if (tiempoFueraServicio != null) {
                tiempoFueraServicio.setModificado(MovilidadUtil.fechaCompletaHoy());
                tiempoFueraServicio.setUsername(user.getUsername());
                tiempoFueraServicioEJB.edit(tiempoFueraServicio);
                MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
                reset();
                consultar();
                MovilidadUtil.hideModal("gop_fuera_servicio_dlg_wv");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar alerta tiempo detenido");
        }
    }

    /**
     * Permite crear la instancia del objeto GopAlertaTiempoFueraServicio
     */
    public void prepareGuardar() {
        tiempoFueraServicio = new GopAlertaTiempoFueraServicio();
    }

    public void reset() {
        tiempoFueraServicio = null;
    }

    void consultar() {
        list = tiempoFueraServicioEJB.findEstadoReg();
        setObjetoSigleton(list);
    }

    /**
     * Permite capturar el objeto GopAlertaTiempoFueraServicio seleccionado por
     * el usuario
     *
     * @param event Evento que captura el objeto GopAlertaTiempoFueraServicio
     */
    public void onRowSelect(SelectEvent event) {
        tiempoFueraServicio = (GopAlertaTiempoFueraServicio) event.getObject();
    }

    public GopAlertaTiempoFueraServicioFacadeLocal getGopAlertaTiempoFueraServicioFacadeLocal() {
        return tiempoFueraServicioEJB;
    }

    public void setGopAlertaTiempoFueraServicioFacadeLocal(GopAlertaTiempoFueraServicioFacadeLocal tiempoFueraServicioEJB) {
        this.tiempoFueraServicioEJB = tiempoFueraServicioEJB;
    }

    public GopAlertaTiempoFueraServicio getGopAlertaTiempoFueraServicio() {
        return tiempoFueraServicio;
    }

    public void setGopAlertaTiempoFueraServicio(GopAlertaTiempoFueraServicio tiempoFueraServicio) {
        this.tiempoFueraServicio = tiempoFueraServicio;
    }

    public List<GopAlertaTiempoFueraServicio> getList() {
        return list;
    }

    public void setList(List<GopAlertaTiempoFueraServicio> list) {
        this.list = list;
    }

}
