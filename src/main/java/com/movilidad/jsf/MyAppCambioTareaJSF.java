/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.MyAppCambioTareaFacadeLocal;
import com.movilidad.model.MyAppCambioTarea;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "myAppCambioTareaJSF")
@ViewScoped
public class MyAppCambioTareaJSF implements Serializable {

    @EJB
    private MyAppCambioTareaFacadeLocal myAppCambioTareaFacadeLocal;

    private MyAppCambioTarea myAppCambioTarea;

    private List<MyAppCambioTarea> listMyAppCambioTarea;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    //
    private Date desde;
    private Date hasta;
    private int idGopUF;

    /**
     * Creates a new instance of MyAppCambioTareaJSF
     */
    public MyAppCambioTareaJSF() {
    }

    @PostConstruct
    void init() {
        desde = new Date();
        hasta = new Date();
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        if (idGopUF != 0) {
            listMyAppCambioTarea = myAppCambioTareaFacadeLocal.findAllByEstadoRegAndIdGopUFAndFechas(desde, hasta, idGopUF);
        }
    }

    public void gestionarCambioTarea() {
        if (myAppCambioTarea == null) {
            MovilidadUtil.addErrorMessage("Error");
            return;
        }
        myAppCambioTarea.setGestionado(1);
        myAppCambioTarea.setFechaGestiona(new Date());
        myAppCambioTarea.setUsrGestiona(user.getUsername());
        myAppCambioTareaFacadeLocal.edit(myAppCambioTarea);
        reset();
        MovilidadUtil.addSuccessMessage("Cambio de tarea gestionado correctamente");
        MovilidadUtil.hideModal("modalDlg");
    }

    public void onCambioTarea(MyAppCambioTarea event) {
        myAppCambioTarea = event;
        MovilidadUtil.openModal("modalDlg");
    }

    public void buscarCambiosTarea() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        if (idGopUF == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
            return;
        }
        listMyAppCambioTarea = myAppCambioTareaFacadeLocal.findAllByEstadoRegAndIdGopUFAndFechas(desde, hasta, idGopUF);
        MovilidadUtil.addSuccessMessage(listMyAppCambioTarea.size() + " Registros encontrados");
    }

    private void reset() {
        myAppCambioTarea = null;
    }

    public MyAppCambioTarea getMyAppCambioTarea() {
        return myAppCambioTarea;
    }

    public void setMyAppCambioTarea(MyAppCambioTarea myAppCambioTarea) {
        this.myAppCambioTarea = myAppCambioTarea;
    }

    public List<MyAppCambioTarea> getListMyAppCambioTarea() {
        return listMyAppCambioTarea;
    }

    public void setListMyAppCambioTarea(List<MyAppCambioTarea> listMyAppCambioTarea) {
        this.listMyAppCambioTarea = listMyAppCambioTarea;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

}
