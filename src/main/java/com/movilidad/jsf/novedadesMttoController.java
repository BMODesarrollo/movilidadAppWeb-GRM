/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.Novedad;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author HP
 */
@Named(value = "novedadesMttoController")
@ViewScoped
public class novedadesMttoController implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEjb;
    private List<Novedad> listforMtto;
    private Date fechaInicio;
    private Date fechaFin;

    public void getByDateRange() {
        if (fechaFin.compareTo(fechaInicio) < 0) {
            MovilidadUtil.addErrorMessage("Fecha Fin no puede ser menor a Fecha Inicio");
            listforMtto = new ArrayList<>();
            listforMtto = novedadEjb.findAllForMtto(new Date());
            fechaInicio = new Date();
            fechaFin = new Date();
            return;
        }
        listforMtto = new ArrayList<>();
        listforMtto = novedadEjb.findByDateRangeForMtto(fechaInicio, fechaFin);
    }

    @PostConstruct
    public void init() {
        listforMtto = novedadEjb.findAllForMtto(new Date());
        fechaInicio = new Date();
        fechaFin = new Date();
    }

    public List<Novedad> getListforMtto() {
        return listforMtto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

}
