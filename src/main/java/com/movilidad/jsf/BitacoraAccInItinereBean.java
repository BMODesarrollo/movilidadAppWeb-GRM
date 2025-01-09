/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccInItinereFacadeLocal;
import com.movilidad.model.BitacoraAccInItinere;
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
import org.primefaces.PrimeFaces;

/**
 *
 * @author Julián Arévalo
 */
@Named(value = "bitacoraAccInItinereBean")
@ViewScoped
public class BitacoraAccInItinereBean implements Serializable {
    
    private List<BitacoraAccInItinere> lista;
    private Date fecha;
    private Date fechaFin;
    
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @EJB
    private AccInItinereFacadeLocal accidenteInItinereEjb;    
    
    @PostConstruct
    public void init() {
        fecha = MovilidadUtil.fechaHoy();
        fechaFin = MovilidadUtil.fechaHoy();
        consultar();
    }

    public void consultar() {
        if (Util.validarFechaCambioEstado(fecha, fechaFin)) {
            MovilidadUtil.addErrorMessage("La fecha inicio no puede ser mayor a la fecha final");
            PrimeFaces.current().ajax().update(":msgs");
            return;
        }

        lista = accidenteInItinereEjb.getAccidentesInItinere(fecha, fechaFin,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
        );

        if (lista == null) {
            MovilidadUtil.addErrorMessage("No existen registros para ése rango de fechas");
            PrimeFaces.current().ajax().update(":msgs");
        }
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public GopUnidadFuncionalSessionBean getUnidadFuncionalSessionBean() {
        return unidadFuncionalSessionBean;
    }

    public void setUnidadFuncionalSessionBean(GopUnidadFuncionalSessionBean unidadFuncionalSessionBean) {
        this.unidadFuncionalSessionBean = unidadFuncionalSessionBean;
    }

    public List<BitacoraAccInItinere> getLista() {
        return lista;
    }

    public void setLista(List<BitacoraAccInItinere> lista) {
        this.lista = lista;
    }

    public AccInItinereFacadeLocal getAccidenteInItinereEjb() {
        return accidenteInItinereEjb;
    }

    public void setAccidenteInItinereEjb(AccInItinereFacadeLocal accidenteInItinereEjb) {
        this.accidenteInItinereEjb = accidenteInItinereEjb;
    }
    
    
    
    

    

}
