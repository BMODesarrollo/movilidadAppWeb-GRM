/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.PrgTc;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
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
@Named(value = "parillaPrincipalJSF")
@ViewScoped
public class ParillaPrincipalJSF implements Serializable {

    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<PrgTc> listEntrada;
    private List<PrgTc> listSalida;
    private Date now;

    /**
     * Creates a new instance of ParillaPrincipalJSF
     */
    public ParillaPrincipalJSF() {
    }

    @PostConstruct
    public void init() {
        consultarEntradas();
        consultarSalidas();
    }

    /**
     * Permite consultar las entradas a patio programadas
     */
    public void consultarEntradas() {
        now = MovilidadUtil.fechaCompletaHoy();
//        now = Util.dateTimeFormat("2020-08-02 08:49:00");
        listEntrada = prgTcFacadeLocal.entradasPatio(now, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    /**
     * Permite consultar las salidas de patio programadas
     */
    public void consultarSalidas() {
        now = MovilidadUtil.fechaCompletaHoy();
//        now = Util.dateTimeFormat("2020-08-02 08:49:00");
        listSalida = prgTcFacadeLocal.salidasPatio(now, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void consultarEntradasSalidas() {
        consultarEntradas();
        consultarSalidas();
    }

    /**
     * Permite retornar la hora, minuto, segundos inicial de la fecha solicitada
     *
     * @return String HH:00:00
     */
    public String starTime() {
        return Util.startTimeByDate(now);
    }

    /**
     * Permite retornar la hora, minuto, segundos final de la fecha solicitada
     *
     * @return HH:59:59
     */
    public String endTime() {
        return Util.endTimeByDate(now);
    }

    public List<PrgTc> getListEntrada() {
        return listEntrada;
    }

    public List<PrgTc> getListSalida() {
        return listSalida;
    }

}
