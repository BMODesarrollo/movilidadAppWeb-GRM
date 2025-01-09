/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.MyAppConfirmDepotEntryFacadeLocal;
import com.movilidad.ejb.MyAppConfirmDepotExitFacadeLocal;
import com.movilidad.model.MyAppConfirmDepotEntry;
import com.movilidad.model.MyAppConfirmDepotExit;
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
 * @author Carlos Ballestas
 */
@Named(value = "reporteInventarioPatioBean")
@ViewScoped
public class ReporteInventarioPatioBean implements Serializable {

    @EJB
    private MyAppConfirmDepotEntryFacadeLocal entryEjb;
    @EJB
    private MyAppConfirmDepotExitFacadeLocal exitEjb;

    //
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private Date fechaDesdeEntry;
    private Date fechaHastaEntry;
    private Date fechaDesdeExit;
    private Date fechaHastaExit;

    private List<MyAppConfirmDepotEntry> lstDepotEntries;
    private List<MyAppConfirmDepotExit> lstDepotExits;

    private int idGopUF;

    @PostConstruct
    public void init() {
        fechaDesdeEntry = fechaHastaEntry = fechaDesdeExit = fechaHastaExit = MovilidadUtil.fechaCompletaHoy();
    }

    public void consultarEntry() {
        cargarUF();
        lstDepotEntries = entryEjb.findByDateRange(fechaDesdeEntry, fechaHastaEntry, idGopUF);
    }

    public void consultarExit() {
        cargarUF();
        lstDepotExits = exitEjb.findByDateRange(fechaDesdeExit, fechaHastaExit, idGopUF);
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public Date getFechaDesdeEntry() {
        return fechaDesdeEntry;
    }

    public void setFechaDesdeEntry(Date fechaDesdeEntry) {
        this.fechaDesdeEntry = fechaDesdeEntry;
    }

    public Date getFechaHastaEntry() {
        return fechaHastaEntry;
    }

    public void setFechaHastaEntry(Date fechaHastaEntry) {
        this.fechaHastaEntry = fechaHastaEntry;
    }

    public Date getFechaDesdeExit() {
        return fechaDesdeExit;
    }

    public void setFechaDesdeExit(Date fechaDesdeExit) {
        this.fechaDesdeExit = fechaDesdeExit;
    }

    public Date getFechaHastaExit() {
        return fechaHastaExit;
    }

    public void setFechaHastaExit(Date fechaHastaExit) {
        this.fechaHastaExit = fechaHastaExit;
    }

    public List<MyAppConfirmDepotEntry> getLstDepotEntries() {
        return lstDepotEntries;
    }

    public void setLstDepotEntries(List<MyAppConfirmDepotEntry> lstDepotEntries) {
        this.lstDepotEntries = lstDepotEntries;
    }

    public List<MyAppConfirmDepotExit> getLstDepotExits() {
        return lstDepotExits;
    }

    public void setLstDepotExits(List<MyAppConfirmDepotExit> lstDepotExits) {
        this.lstDepotExits = lstDepotExits;
    }

}
