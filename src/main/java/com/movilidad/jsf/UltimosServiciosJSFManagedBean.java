/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.ServbusTipoTablaDTO;
import com.movilidad.dto.UltimoServicioDTO;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author solucionesit
 */
@Named(value = "ultimosServiciosBean")
@ViewScoped
public class UltimosServiciosJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of UltimosServiciosJSFManagedBean
     */
    public UltimosServiciosJSFManagedBean() {
    }

    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private Date fecha;
    private List<UltimoServicioDTO> list;
    private List<ServbusTipoTablaDTO> tipoTablaDTOs;
    private Map<String, ServbusTipoTablaDTO> mapServbusTipoTabla;

    @PostConstruct
    public void init() {
        fecha = MovilidadUtil.fechaHoy();
    }

    public void consultar() {
        list = prgTcFacadeLocal.findUltimosServiciosPorServbus(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        cargarMapServbusTipoTabla();
        cargarTipoTabla();
    }

    private void cargarMapServbusTipoTabla() {
        tipoTablaDTOs = prgTcFacadeLocal.findServbusTipoTabla(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        mapServbusTipoTabla = convertList(tipoTablaDTOs);
    }

    private Map<String, ServbusTipoTablaDTO> convertList(List<ServbusTipoTablaDTO> list) {
        Map<String, ServbusTipoTablaDTO> map = new HashMap<>();
        for (ServbusTipoTablaDTO item : list) {
            map.put(item.getServbus(), item);
        }
        return map;
    }

    public void setNumEntryDepot(UltimoServicioDTO ultimoServicioDTO) {
        ServbusTipoTablaDTO get = mapServbusTipoTabla.get(ultimoServicioDTO.getServbus());
        if (get != null) {
            ultimoServicioDTO.setTipoTabla(get.getNumEntryDepot() > 1 ? "Partida" : "Larga");
        } else {
            ultimoServicioDTO.setTipoTabla("Larga");
        }
    }

    private void cargarTipoTabla() {
        for (UltimoServicioDTO item : list) {
            setNumEntryDepot(item);
        }
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<UltimoServicioDTO> getList() {
        return list;
    }

    public void setList(List<UltimoServicioDTO> list) {
        this.list = list;
    }

}
