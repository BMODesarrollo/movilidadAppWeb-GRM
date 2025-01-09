/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaTipoDetallesFacadeLocal;
import com.movilidad.ejb.GenericaTipoFacadeLocal;
import com.movilidad.model.GenericaTipo;
import com.movilidad.model.GenericaTipoDetalles;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author solucionesit
 */
@Named(value = "novTipoAndDetGenBean")
@ViewScoped
public class NovedadTipoAndDetalleGenBean implements Serializable {

    /**
     * Creates a new instance of novedadTipoAndDetalleBean
     */
    public NovedadTipoAndDetalleGenBean() {
    }
    private List<GenericaTipo> lstNovedadTipos;
    private List<GenericaTipoDetalles> lstNovedadTipoDetalles;
    private GenericaTipo novedadTipo;
    private GenericaTipoDetalles novedadTipoDet;
    private Integer idParamArea;
    private String updateDialogNovedadTipo;
    private String updateDialogNovedadTipoDet;
    private String modulo = "";
    private String compUpdateVistaCreateNov = ":frmPrincipal:pnlDatos";

    @EJB
    private GenericaTipoFacadeLocal novedadTipoEjb;
    @EJB
    private GenericaTipoDetallesFacadeLocal novedadTipoDetEjb;

    /**
     * Evento que se dispara al seleccionar un tipo de novedad en el modal que
     * muestra los tipos de novedades y asigna el tipo de novedad seleccionado
     * al valor para la vista
     *
     * @param event
     */
    public void onRowSelectNovTipo(final SelectEvent event) {
        if (event.getObject() instanceof GenericaTipo) {
            novedadTipo = (GenericaTipo) event.getObject();
        }
        MovilidadUtil.clearFilter("dialog_nov_tipo_det_dt_wv");
        prepareListNovedadTipoDetalle();
        MovilidadUtil.openModal("dialog_nov_tipo_det_wv");
        this.novedadTipoDet = null;
    }

    /**
     * Evento que se dispara al seleccionar un detalle de novedad en el modal
     * que muestra los detalles de tipos
     *
     * @param event
     */
    public void onRowSelectNovTipoDet(final SelectEvent event) {
        if (event.getObject() instanceof GenericaTipoDetalles) {
            novedadTipoDet = (GenericaTipoDetalles) event.getObject();
            MovilidadUtil.clearFilter("dialog_nov_tipo_det_dt_wv");
        }
    }

    /**
     * Prepara los tipos de novedades antes de registrar/modificar una novedad
     *
     */
    public void prepareListNovedadTipo() {
        lstNovedadTipos = novedadTipoEjb.findAllByArea(idParamArea);
        MovilidadUtil.clearFilter("dialog_nov_tipo_dt_wv");
    }

    /**
     * Prepara los detalles del tipo seleccionado antes de registrar/modificar
     * una novedad
     *
     */
    public void prepareListNovedadTipoDetalle() {
        if (novedadTipo == null) {
            return;
        }
        lstNovedadTipoDetalles = novedadTipoDetEjb.findByTipo(novedadTipo.getIdGenericaTipo());

        MovilidadUtil.clearFilter("dialog_nov_tipo_det_dt_wv");
    }

    /**
     * Prepara los detalles que correspondan una novedad de nómina
     * (novedad_nomina = 1)
     *
     * @param idsListaDetalles Id de detalles que NO se van a mostrar en la
     * lista de detalles que se despliega en la vista
     */
    public void prepareListNovedadTipoDetalleNomina(Set<Integer> idsListaDetalles) {

        String idDetalles = idsListaDetalles != null ? idsListaDetalles.toString() : null;

        lstNovedadTipoDetalles = novedadTipoDetEjb.obtenerDetallesActuales(idParamArea, idDetalles != null ? idDetalles.substring(1, idDetalles.length() - 1) : null);

        MovilidadUtil.clearFilter("dialog_nov_tipo_det_dt_wv");
    }

    public List<GenericaTipo> getLstNovedadTipos() {
        return lstNovedadTipos;
    }

    public void setLstNovedadTipos(List<GenericaTipo> lstNovedadTipos) {
        this.lstNovedadTipos = lstNovedadTipos;
    }

    public List<GenericaTipoDetalles> getLstNovedadTipoDetalles() {
        return lstNovedadTipoDetalles;
    }

    public void setLstNovedadTipoDetalles(List<GenericaTipoDetalles> lstNovedadTipoDetalles) {
        this.lstNovedadTipoDetalles = lstNovedadTipoDetalles;
    }

    public GenericaTipo getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(GenericaTipo novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public GenericaTipoDetalles getNovedadTipoDet() {
        return novedadTipoDet;
    }

    public void setNovedadTipoDet(GenericaTipoDetalles novedadTipoDet) {
        this.novedadTipoDet = novedadTipoDet;
    }

    public String getUpdateDialogNovedadTipo() {
        return updateDialogNovedadTipo;
    }

    public void setUpdateDialogNovedadTipo(String updateDialogNovedadTipo) {
        this.updateDialogNovedadTipo = updateDialogNovedadTipo;
    }

    public String getUpdateDialogNovedadTipoDet() {
        return updateDialogNovedadTipoDet;
    }

    public void setUpdateDialogNovedadTipoDet(String updateDialogNovedadTipoDet) {
        this.updateDialogNovedadTipoDet = updateDialogNovedadTipoDet;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getCompUpdateVistaCreateNov() {
        return compUpdateVistaCreateNov;
    }

    public void setCompUpdateVistaCreateNov(String compUpdateVistaCreateNov) {
        this.compUpdateVistaCreateNov = compUpdateVistaCreateNov;
    }

    public Integer getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(Integer idParamArea) {
        this.idParamArea = idParamArea;
    }

}
