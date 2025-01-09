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
@Named(value = "atvReporteJSF")
@ViewScoped
public class AtvReporteJSF implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadFacadeLocal;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<Novedad> listNovedadATV;

    private Integer idAtvPrestador;
    private Date desde;
    private Date hasta;

    private int idGopUF;

    /**
     * Creates a new instance of AtvReporteJSF
     */
    public AtvReporteJSF() {
    }

    @PostConstruct
    public void init() {
        desde = new Date();
        hasta = new Date();
        idAtvPrestador = null;
    }

    public void consultarNov() {
        cargarUF();
        if (idAtvPrestador == null) {
            MovilidadUtil.addErrorMessage("Prestador es requerido");
            return;
        }
        listNovedadATV = novedadFacadeLocal.findNovedadAtvLiquidadaByPropietario(desde, hasta, idAtvPrestador, idGopUF);
    }

    void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public List<Novedad> getListNovedadATV() {
        return listNovedadATV;
    }

    public void setListNovedadATV(List<Novedad> listNovedadATV) {
        this.listNovedadATV = listNovedadATV;
    }

    public Integer getIdAtvPrestador() {
        return idAtvPrestador;
    }

    public void setIdAtvPrestador(Integer idAtvPrestador) {
        this.idAtvPrestador = idAtvPrestador;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

}
