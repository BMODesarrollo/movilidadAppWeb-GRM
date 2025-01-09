/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgTc;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author César
 */
@Named(value = "prgTcEntradaSalida")
@ViewScoped
public class PrgTcEntradaSalida implements Serializable {

    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @EJB
    private PrgStopPointFacadeLocal prgStopPointFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    private List<PrgTc> listEntrada;
    private List<PrgTc> listSalida;
    private List<PrgStopPoint> listPatios;
    private boolean b_visualizar = false;
    private boolean b_visualizar2 = false;
    private boolean b_visualizar3 = false;
    private int patio;

    public PrgTcEntradaSalida() {
    }

    @PostConstruct
    void init() {
        listPatios = prgStopPointFacadeLocal.getPatios();
    }

    public void reset() {
        listEntrada = new ArrayList<>();
        listSalida = new ArrayList<>();
        listPatios = new ArrayList<>();
        b_visualizar3 = false;
        b_visualizar2 = false;
        b_visualizar = false;
        patio = 0;
        init();
    }

    String restar30() {
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MINUTE, -30);
        now = c.getTime();
        return new SimpleDateFormat("HH:mm:ss").format(now);
    }

    public void entrar() {
        b_visualizar2 = false;
        b_visualizar3 = false;
        if (patio != 0) {
            b_visualizar = true;
            return;
        }
        MovilidadUtil.addErrorMessage("Seleccione un patio");
    }

    public void opcionVisualEntrada() {
        b_visualizar2 = true;
        b_visualizar = false;
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fecha_hora = restar30();
        listEntrada = prgTcFacadeLocal.entradasPatio(fecha, fecha_hora, patio, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listSalida = prgTcFacadeLocal.salidasPatio(fecha, fecha_hora, patio, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
//        System.out.println(patio);
    }

    public void opcionVisualSalida() {
        b_visualizar3 = true;
        b_visualizar = false;
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fecha_hora = restar30();
        listEntrada = prgTcFacadeLocal.entradasPatio(fecha, fecha_hora, patio, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
//        System.out.println(patio);
    }

    public int retornarPatio() {
        return patio;
    }

    public List<PrgTc> getListEntrada() {
        return listEntrada;
    }

    public void setListEntrada(List<PrgTc> listEntrada) {
        this.listEntrada = listEntrada;
    }

    public List<PrgTc> getListSalida() {
        return listSalida;
    }

    public void setListSalida(List<PrgTc> listSalida) {
        this.listSalida = listSalida;
    }

    public List<PrgStopPoint> getListPatios() {
        return listPatios;
    }

    public void setListPatios(List<PrgStopPoint> listPatios) {
        this.listPatios = listPatios;
    }

    public boolean isB_visualizar() {
        return b_visualizar;
    }

    public void setB_visualizar(boolean b_visualizar) {
        this.b_visualizar = b_visualizar;
    }

    public boolean isB_visualizar2() {
        return b_visualizar2;
    }

    public void setB_visualizar2(boolean b_visualizar2) {
        this.b_visualizar2 = b_visualizar2;
    }

    public int getPatio() {
        return patio;
    }

    public void setPatio(int patio) {
        this.patio = patio;
    }

    public boolean isB_visualizar3() {
        return b_visualizar3;
    }

    public void setB_visualizar3(boolean b_visualizar3) {
        this.b_visualizar3 = b_visualizar3;
    }

}
