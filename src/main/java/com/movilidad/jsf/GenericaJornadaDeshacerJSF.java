/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "genericaJornadaDeshacerJSF")
@ViewScoped
public class GenericaJornadaDeshacerJSF implements Serializable {

    @EJB
    private GenericaJornadaFacadeLocal jornadaFacadeLocal;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;

    private List<GenericaJornada> listGenericaJornada;
    private ParamAreaUsr paramAreaUsr;
    private Date dDesde;
    private Date dHasta;

    UserExtended user;

    public GenericaJornadaDeshacerJSF() {
    }

    @PostConstruct
    public void init() {
        paramAreaUsr = null;
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        listGenericaJornada = new ArrayList<>();
        dDesde = new Date();
        dHasta = new Date();
    }

    public void buscarJornadas() {
        if (validarDatos(dDesde, dHasta)) {
            return;
        }
        if (paramAreaUsr == null) {
            MovilidadUtil.addErrorMessage("Proceso no permitido");
            return;
        }
        listGenericaJornada = jornadaFacadeLocal.findByDateAndLiquidado(dDesde,
                dHasta,
                paramAreaUsr.getIdParamArea().getIdParamArea(),
                Util.ID_LIQUIDADO, 0);
        if (listGenericaJornada != null && listGenericaJornada.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron registros entre las fechas indicadas.");
            return;
        }
        MovilidadUtil.addSuccessMessage("Registros encontrados");
        PrimeFaces.current().executeScript("PF('jornadaWV').hide()");
    }

    public void onDeshacerJornadaSeleccionada() {
        try {
            if (validarDatos(dDesde, dHasta)) {
                return;
            }
            listGenericaJornada = jornadaFacadeLocal.findByDateAndLiquidado(dDesde,
                    dHasta,
                    paramAreaUsr.getIdParamArea().getIdParamArea(),
                    Util.ID_LIQUIDADO, 0);
            if (listGenericaJornada != null && listGenericaJornada.isEmpty()) {
                MovilidadUtil.addErrorMessage("No se encontraron registros entre las fechas indicadas.");
                reset();
                return;
            }
            for (GenericaJornada gj : listGenericaJornada) {
                gj.setLiquidado(0);
                gj.setUserLiquida(null);
                jornadaFacadeLocal.edit(gj);
            }
            //Deshacer generica jornada
            MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
            reset();
        } catch (Exception e) {
            reset();
            MovilidadUtil.addErrorMessage("Error al realizar procedimiento");
        }
    }

    boolean validarDatos(Date desde, Date hasta) {
        try {
            if (MovilidadUtil.fechasIgualMenorMayor(hasta, desde, false) == 0) {
                MovilidadUtil.addErrorMessage("Fecha Hasta no puede ser inferior a Fecha Desde");
                return true;
            }
            return false;
        } catch (ParseException e) {
            return false;
        }
    }

    public void reset() {
        listGenericaJornada = new ArrayList<>();
        dDesde = new Date();
        dHasta = new Date();
    }

    public String getFormatFecha(Date d) {
        return Util.dateFormat(d);
    }

    public List<GenericaJornada> getListGenericaJornada() {
        return listGenericaJornada;
    }

    public void setListGenericaJornada(List<GenericaJornada> listGenericaJornada) {
        this.listGenericaJornada = listGenericaJornada;
    }

    public ParamAreaUsr getParamAreaUsr() {
        return paramAreaUsr;
    }

    public void setParamAreaUsr(ParamAreaUsr paramAreaUsr) {
        this.paramAreaUsr = paramAreaUsr;
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
