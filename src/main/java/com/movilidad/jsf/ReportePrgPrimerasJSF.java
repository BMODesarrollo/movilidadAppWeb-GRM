/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgPrimerasFacadeLocal;
import com.movilidad.model.PrgPrimeras;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author soluciones-it
 */
@Named(value = "reportePrgPrimerasJSF")
@ViewScoped
public class ReportePrgPrimerasJSF implements Serializable {
    
    @EJB
    private PrgPrimerasFacadeLocal prgPrimerasFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    //
    private List<PrgPrimeras> listPrgPrimeras;
    private int idGopUF;
    private boolean isValidProcessTime;

    /**
     * Creates a new instance of ReportePrgPrimerasJSF
     */
    public ReportePrgPrimerasJSF() {
    }
    
    @PostConstruct
    void init() {
        isValidProcessTime = MovilidadUtil.isDateBetween0100And0130(MovilidadUtil.fechaCompletaHoy());
        consultar();
    }
    
    public void procesar() {
        cargarUF();
        if (idGopUF == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
            return;
        }
        Date d = MovilidadUtil.fechaCompletaHoy();
        if (MovilidadUtil.isDateBetween0100And0130(d)) {
            prgPrimerasFacadeLocal.ejecutarProcesoPrimeras(d, idGopUF);
            MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
            return;
        }
        MovilidadUtil.addErrorMessage("No permitido el proceso");
    }
    
    public void consultar() {
        cargarUF();
        listPrgPrimeras = prgPrimerasFacadeLocal.findAllByGopUf(MovilidadUtil.fechaCompletaHoy(), idGopUF);
    }
    
    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }
    
    public List<PrgPrimeras> getListPrgPrimeras() {
        return listPrgPrimeras;
    }
    
    public void setListPrgPrimeras(List<PrgPrimeras> listPrgPrimeras) {
        this.listPrgPrimeras = listPrgPrimeras;
    }
    
    public boolean isIsValidProcessTime() {
        return isValidProcessTime;
    }
    
    public void setIsValidProcessTime(boolean isValidProcessTime) {
        this.isValidProcessTime = isValidProcessTime;
    }
    
}
