/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgBorradoFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.PrgBorrado;
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
@Named(value = "prgBorradoJSF")
@ViewScoped
public class PrgBorradoJSF implements Serializable {

    @EJB
    private PrgTcFacadeLocal tcFacadeLocal;
    @EJB
    private PrgBorradoFacadeLocal prgBorradoFacadeLocal;

    //
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private PrgBorrado prgBorrado;
    private Date dFecha;
    private boolean bFlag;
    private int idGopUF;

    UserExtended user;

    public PrgBorradoJSF() {
    }

    @PostConstruct
    public void init() {
        prgBorrado = null;
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
        prgBorrado = new PrgBorrado();
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
        prgBorrado.setFechaHora(d);
        prgBorrado.setUsername(user.getUsername());
        prgBorrado.setCreado(d);
        prgBorrado.setModificado(d);
        prgBorrado.setEstadoReg(0);
        eliminar();
        prgBorradoFacadeLocal.create(prgBorrado);
        reset();
        MovilidadUtil.addSuccessMessage("Procedimiento realizado con éxito." + rowsPrg + "registro(s) afectados.");
    }

    private void eliminar() {
        tcFacadeLocal.removeByDate(dFecha, idGopUF);
    }

    public void reset() {
        prgBorrado = null;
        dFecha = null;
        bFlag = true;
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public String minDate() {
        return Util.dateFormat(Util.DiasAFecha(new Date(), 1));
    }

    public PrgBorrado getPrgBorrado() {
        return prgBorrado;
    }

    public void setPrgBorrado(PrgBorrado prgBorrado) {
        this.prgBorrado = prgBorrado;
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
