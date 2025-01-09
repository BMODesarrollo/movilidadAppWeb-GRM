/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.Novedad;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author solucionesit
 */
@Named(value = "repNovedadesDiferidasBean")
@ViewScoped
public class ReporteNovedadesDiferidasBean implements Serializable {

    /**
     * Creates a new instance of ClasificacionNovedadBean
     */
    public ReporteNovedadesDiferidasBean() {
    }

    @EJB
    private NovedadFacadeLocal novedadEJB;
    private List<Novedad> listNovs;
    private List<String> listaTipoDetalle;
    private List<String> listaCausaTipoDetalle;
    private List<String> listaSistema;
    private List<String> listaEstadoPendActual;
    private List<String> listaTipoNovedad;
    private Date desde;
    private Date hasta;

    @PostConstruct
    public void init() {
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
        consultarNov();
    }

    public void consultarNov() {
        listNovs = novedadEJB.findNovsAfectaDispByFechaAndEstado(desde, hasta, ConstantsUtil.NOV_ESTADO_DIFERIR);
        listaFiltros();
    }

    public int tiempoInoperativo(Novedad param) throws ParseException {
        if (param.getHora() != null) {
            if (param.getIdDispClasificacionNovedad() != null) {
                if (param.getIdDispClasificacionNovedad().getFechaHabilitacion() != null) {
                    Date converterToHour = MovilidadUtil.converterToHour(param.getFecha(), param.getHora());
                    return MovilidadUtil.getDiferenciaHora(converterToHour, param.getIdDispClasificacionNovedad().getFechaHabilitacion());
                }
            }
        }
        return 0;
    }

    public void listaFiltros() {
        listaTipoDetalle = new ArrayList<>();
        listaCausaTipoDetalle = new ArrayList<>();
        listaSistema = new ArrayList<>();
        listaEstadoPendActual = new ArrayList<>();
        listaTipoNovedad = new ArrayList<>();
        if (listNovs != null) {
            for (Novedad d : listNovs) {
                listaTipoDetalle.add(d.getIdNovedadTipoDetalle() == null
                        ? "N/A" : d.getIdNovedadTipoDetalle().getTituloTipoNovedad());
                listaCausaTipoDetalle.add(d.getIdNovedadTipoDetalle() == null
                        ? "N/A" : d.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet() == null
                        ? "N/A" : d.getIdNovedadTipoDetalle().getIdVehiculoTipoEstadoDet().getNombre());
                listaSistema.add(d.getIdDispClasificacionNovedad() == null
                        ? "N/A" : d.getIdDispClasificacionNovedad().getIdDispSistema() == null
                        ? "N/A" : d.getIdDispClasificacionNovedad().getIdDispSistema().getNombre());
                listaEstadoPendActual.add(d.getIdDispClasificacionNovedad() == null
                        ? "N/A" : d.getIdDispClasificacionNovedad().getIdDispEstadoPendActual() == null
                        ? "N/A" : d.getIdDispClasificacionNovedad().getIdDispEstadoPendActual().getNombre());
                listaTipoNovedad.add(d.getIdNovedadTipo() == null ? "N/A" : d.getIdNovedadTipo().getNombreTipoNovedad());
            }
            listaTipoDetalle = listaTipoDetalle.stream().distinct().collect(Collectors.toList());
            listaCausaTipoDetalle = listaCausaTipoDetalle.stream().distinct().collect(Collectors.toList());
            listaSistema = listaSistema.stream().distinct().collect(Collectors.toList());
            listaEstadoPendActual = listaEstadoPendActual.stream().distinct().collect(Collectors.toList());
            listaTipoNovedad = listaTipoNovedad.stream().distinct().collect(Collectors.toList());
        }
    }

    public List<Novedad> getListNovs() {
        return listNovs;
    }

    public void setListNovs(List<Novedad> listNovs) {
        this.listNovs = listNovs;
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

    public List<String> getListaTipoDetalle() {
        return listaTipoDetalle;
    }

    public void setListaTipoDetalle(List<String> listaTipoDetalle) {
        this.listaTipoDetalle = listaTipoDetalle;
    }

    public List<String> getListaCausaTipoDetalle() {
        return listaCausaTipoDetalle;
    }

    public void setListaCausaTipoDetalle(List<String> listaCausaTipoDetalle) {
        this.listaCausaTipoDetalle = listaCausaTipoDetalle;
    }

    public List<String> getListaSistema() {
        return listaSistema;
    }

    public void setListaSistema(List<String> listaSistema) {
        this.listaSistema = listaSistema;
    }

    public List<String> getListaEstadoPendActual() {
        return listaEstadoPendActual;
    }

    public void setListaEstadoPendActual(List<String> listaEstadoPendActual) {
        this.listaEstadoPendActual = listaEstadoPendActual;
    }

    public List<String> getListaTipoNovedad() {
        return listaTipoNovedad;
    }

    public void setListaTipoNovedad(List<String> listaTipoNovedad) {
        this.listaTipoNovedad = listaTipoNovedad;
    }

}
