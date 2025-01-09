/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.TP28UltimoServicioDTO;
import com.movilidad.ejb.PrgTcFacadeLocal;
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
 * @author solucionesit
 */
@Named(value = "tp28ultimosServiciosBean")
@ViewScoped
public class TP28UltimosServiciosJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of UltimosServiciosJSFManagedBean
     */
    public TP28UltimosServiciosJSFManagedBean() {
    }
    
    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    
    private Date fecha;
    private List<TP28UltimoServicioDTO> list;
    
    @PostConstruct
    public void init() {
        fecha = MovilidadUtil.fechaHoy();
    }
    
    public void consultar() {
        list = prgTcFacadeLocal.findTP28UltimosServicios(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public List<TP28UltimoServicioDTO> getList() {
        return list;
    }
    
    public void setList(List<TP28UltimoServicioDTO> list) {
        this.list = list;
    }
    
}
