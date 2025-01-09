/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import com.movilidad.model.Novedad;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.ejb.NovedadFacadeLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author César Mercado
 */
@Named(value = "liquidaPM")
@ViewScoped
public class LiquidaPM implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadFacadeLocal;

    private List<Novedad> listNovedadPm;
    private Novedad novedad = null;
    private Integer i_puntosConciliados;
    private Date fechaInicio;
    private Date fechaFin;

    public LiquidaPM() {
    }

    public void getByDateRange() {
        if (fechaFin.compareTo(fechaInicio) < 0) {
            MovilidadUtil.addErrorMessage("Fecha Fin no puede ser menor a Fecha Inicio");
            listNovedadPm = new ArrayList<>();
            listNovedadPm = novedadFacadeLocal.liquidaPM();
            fechaInicio = new Date();
            fechaFin = new Date();
            return;
        }
        listNovedadPm = new ArrayList<>();
        listNovedadPm = novedadFacadeLocal.findByDateRangeForLiquidaPM(fechaInicio, fechaFin);
    }

    @PostConstruct
    public void init() {
        listNovedadPm = novedadFacadeLocal.liquidaPM();
        fechaInicio = new Date();
        fechaFin = new Date();
    }

    public void modal() {
        if (novedad != null) {
            PrimeFaces.current().executeScript("PF('apli-pm').show()");
//            System.out.println(novedad.getPuntosPm());
        }
    }

    public void aplicarPuntosPM() {
        if (novedad == null) {
            MovilidadUtil.addErrorMessage("Error al seleccionar la Novedad");
            PrimeFaces.current().executeScript("PF('apli-pm').hide();");
            PrimeFaces.current().ajax().update("formLiquida:msg");
            reset();
            return;
        }
        if (i_puntosConciliados >= 0 && i_puntosConciliados <= 100) {
            novedad.setPuntosPmConciliados(i_puntosConciliados);
            novedad.setProcede(1);
            MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
            PrimeFaces.current().ajax().update("formLiquida:msg");
            PrimeFaces.current().executeScript("PF('apli-pm').hide();");
            novedadFacadeLocal.edit(novedad);
            reset();
//            System.out.println(i_puntosConciliados);
            return;
        }
        MovilidadUtil.addErrorMessage("Puntos Conciliados No Valido");
        PrimeFaces.current().ajax().update("formLiquida:msg");
    }

    public void procedeCociliacion(Novedad nov) {
//        System.out.println(nov.getIdNovedad());
//        novedad = new Novedad();
        novedad = nov;
        novedad.setPuntosPmConciliados(novedad.getPuntosPm());
        novedad.setProcede(1);
        MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
        PrimeFaces.current().ajax().update("formLiquida:msg");
        novedadFacadeLocal.edit(novedad);
        reset();
    }

    public void noProcedeConciliacion(Novedad nov) {
//        System.out.println(nov.getIdNovedad());
//        novedad = new Novedad();
        novedad = nov;
        novedad.setPuntosPmConciliados(0);
        novedad.setProcede(0);
        MovilidadUtil.addSuccessMessage("Se ha realizado la acción correctamente");
        PrimeFaces.current().ajax().update("formLiquida:msg");
        novedadFacadeLocal.edit(novedad);
        reset();
    }

    public String master(Novedad nov) {
        if (nov.getIdEmpleado() == null) {
            return "N/A";
        }
        try {
            return nov.getIdEmpleado().getPmGrupoDetalleList().get(0).getIdGrupo().getNombreGrupo();
        } catch (Exception e) {
            return "N/A";
        }
    }

    public void reset() {
        novedad = new Novedad();
        i_puntosConciliados = 0;
        listNovedadPm = new ArrayList<>();
        listNovedadPm = novedadFacadeLocal.liquidaPM();
        PrimeFaces.current().ajax().update("formLiquida:datalist");
    }

    public void onRowSelect(SelectEvent event) {
//        novedad = new Novedad();
        novedad = (Novedad) event.getObject();
    }

    public List<Novedad> getListNovedadPm() {
        return listNovedadPm;
    }

    public Integer getI_puntosConciliados() {
        return i_puntosConciliados;
    }

    public void setI_puntosConciliados(Integer i_puntosConciliados) {
        this.i_puntosConciliados = i_puntosConciliados;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
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
