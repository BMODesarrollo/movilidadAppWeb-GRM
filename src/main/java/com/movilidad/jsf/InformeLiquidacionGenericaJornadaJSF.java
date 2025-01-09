/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.ParamArea;
import com.movilidad.security.UserExtended;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "informeLiquidacionGenericaJornadaJSF")
@ViewScoped
public class InformeLiquidacionGenericaJornadaJSF implements Serializable {

    @EJB
    private GenericaJornadaFacadeLocal genericaJornadaFacadeLocal;
    @EJB
    private ParamAreaFacadeLocal paramAreaFacadeLocal;

    private List<GenericaJornada> listtGenericaJornada;
    private List<ParamArea> listParamArea;

    private Integer idArea;
    private Date dDesde;
    private Date dHasta;

    UserExtended user;

    public InformeLiquidacionGenericaJornadaJSF() {
    }

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        listtGenericaJornada = new ArrayList<>();
        listParamArea = new ArrayList<>();
        idArea = null;
        dDesde = new Date();
        dHasta = new Date();
        listParamArea = paramAreaFacadeLocal.findAllEstadoReg();

    }

    public void cargarDatos() {
        listtGenericaJornada.clear();
        if (idArea != null) {
            listtGenericaJornada = genericaJornadaFacadeLocal.getByDate(dDesde, dHasta, idArea);
        } else {
            listtGenericaJornada = genericaJornadaFacadeLocal.getByDateAllArea(dDesde, dHasta);
        }
    }

    public List<GenericaJornada> getListtGenericaJornada() {
        return listtGenericaJornada;
    }

    public void setListtGenericaJornada(List<GenericaJornada> listtGenericaJornada) {
        this.listtGenericaJornada = listtGenericaJornada;
    }

    public List<ParamArea> getListParamArea() {
        return listParamArea;
    }

    public void setListParamArea(List<ParamArea> listParamArea) {
        this.listParamArea = listParamArea;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Date getdDesde() {
        return dDesde;
    }

    public void setdDesde(Date dDesde) {
        this.dDesde = dDesde;
    }

    public Date getdHasta() {
        return dHasta;
    }

    public void setdHasta(Date dHasta) {
        this.dHasta = dHasta;
    }

}
