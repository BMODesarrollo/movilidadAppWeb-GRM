/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgAsignacionFacadeLocal;
import com.movilidad.ejb.PrgBorradoAsignacionBusFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.PrgBorradoAsignacionBus;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "prgBorradoAsignacionBusJSF")
@ViewScoped
public class PrgBorradoAsignacionBusJSF implements Serializable {

    @EJB
    private PrgAsignacionFacadeLocal prgAsignacionFacadeLocal;
    @EJB
    private PrgTcFacadeLocal tcFacadeLocal;
    @EJB
    private PrgBorradoAsignacionBusFacadeLocal prgBorradoAsignacionBusFacadeLocal;

    //
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private PrgBorradoAsignacionBus prgBorradoAsignacionBus;
    private Date dFecha;
    private boolean bFlag;
    private int idGopUF;

    UserExtended user;

    public PrgBorradoAsignacionBusJSF() {
    }

    @PostConstruct
    public void init() {
        prgBorradoAsignacionBus = null;
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dFecha = MovilidadUtil.sumarDias(new Date(), 1);
        bFlag = true;
        cargarUF();

    }

    public void prepareGuardar() {
        cargarUF();
        if (idGopUF == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
            return;
        }
        prgBorradoAsignacionBus = new PrgBorradoAsignacionBus();
    }

    @Transactional
    public void guardar() {
        cargarUF();
        if (idGopUF == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
            return;
        }
        if (dFecha == null) {
            MovilidadUtil.addErrorMessage("Fecha es requerido");
            return;
        }
        long rowsPrg = tcFacadeLocal.countByFechas(dFecha, dFecha, idGopUF);
        if (rowsPrg == 0) {
            MovilidadUtil.addErrorMessage("Para la fecha seleccionada no existe programación");
            return;
        }
        Date d = new Date();
        prgBorradoAsignacionBus.setFechaHora(d);
        prgBorradoAsignacionBus.setUsername(user.getUsername());
        prgBorradoAsignacionBus.setCreado(d);
        prgBorradoAsignacionBus.setModificado(d);
        prgBorradoAsignacionBus.setEstadoReg(0);
        eliminar();
        prgBorradoAsignacionBusFacadeLocal.create(prgBorradoAsignacionBus);
        reset();
        MovilidadUtil.addSuccessMessage("Procedimiento realizado con éxito.");
    }

    private void eliminar() {
        tcFacadeLocal.updateByDate(dFecha, idGopUF);
        prgAsignacionFacadeLocal.removeByDate(dFecha, idGopUF);
    }

    public void reset() {
        prgBorradoAsignacionBus = null;
        dFecha = null;
        bFlag = true;
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public String minDate() {
        return Util.dateFormat(Util.DiasAFecha(new Date(), 1));
    }

    public PrgBorradoAsignacionBus getPrgBorradoAsignacionBus() {
        return prgBorradoAsignacionBus;
    }

    public void setPrgBorradoAsignacionBus(PrgBorradoAsignacionBus prgBorradoAsignacionBus) {
        this.prgBorradoAsignacionBus = prgBorradoAsignacionBus;
    }

    public Date getdFecha() {
        return dFecha;
    }

    public void setdFecha(Date dFecha) {
        this.dFecha = dFecha;
    }

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

}
