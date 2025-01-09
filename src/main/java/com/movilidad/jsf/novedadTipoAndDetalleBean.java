/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "novTipoAndDetBean")
@ViewScoped

public class novedadTipoAndDetalleBean implements Serializable {

    /**
     * Creates a new instance of novedadTipoAndDetalleBean
     */
    public novedadTipoAndDetalleBean() {

    }
    private boolean pqrVisible = false;
    private boolean flagUserTC = false;
    private boolean visibleDesasignarServicios = false;
    private String rol_user = "";
    private List<NovedadTipo> lstNovedadTipos;
    private List<NovedadTipoDetalles> lstNovedadTipoDetalles;
    private NovedadTipo novedadTipo;
    private NovedadTipoDetalles novedadTipoDet;
    private String updateDialogNovedadTipo;
    private String updateDialogNovedadTipoDet;
    private String modulo = "";
    private String compUpdateVistaCreateNov = "frmNovedadesPm:novedad_tipo,"
            + "frmNovedadesPm:novedad_tipo_detalle,"
            + "frmNovedadesPm:novedad_tipo_detalleBtn,"
            + "frmNovedadesPm:desde_grp,"
            + "frmNovedadesPm:hasta_grp,"
            + "frmNovedadesPm:vehiculo,"
            + "frmNovedadesPm:operador,"
            + "frmNovedadesPm:hora_inicio_grp,"
            + "frmNovedadesPm:hora_fin_grp,"
            + "frmNovedadesPm:sitio_grp,"
            + "frmNovedadesPm:hora_grp,"
            + "frmNovedadesPm:inmovilizado_lbl,"
            + "frmNovedadesPm:SOMAtv_lbl_ii,"
            + "frmNovedadesPm:SOMAtv_lbl,"
            + "frmNovedadesPm:sistema_grp,"
            + "frmNovedadesPm:infraccion_grp,"
            + "frmNovedadesPm:motivo_grp";

    @EJB
    private NovedadTipoFacadeLocal novedadTipoEjb;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetEjb;
    @Inject
    private ClasificacionNovedadBean clasificacionNovedadBean;
    @Inject
    private SelectDispSistemaBean selectDispSistemaBean;
    @Inject
    private NovedadJSFManagedBean novJSF;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void prepareModulo() {
        setModulo(ConstantsUtil.MDL_NOVEDAD_CLASIFICA);
        String get = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_ID_NOV_MTTO);
        if (get != null) {
            novedadTipo = novedadTipoEjb.find(Integer.parseInt(get));
        }
    }

    public void prepareModuloAusentismo() {
        String get = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_ID_NOV_AUSENTISMO);
        if (get != null) {
            novedadTipo = novedadTipoEjb.find(Integer.parseInt(get));
        }
        for (GrantedAuthority auth : user.getAuthorities()) {
            rol_user = auth.getAuthority();
        }
        if (novedadTipo.getIdNovedadTipo() == 1 && flagUserTC) {
            lstNovedadTipoDetalles = novedadTipoDetEjb.findByTipoNovedadForTC(novedadTipo.getIdNovedadTipo());//traer solo ausencia, ausencia parcial, inoperable TM;
            visibleDesasignarServicios = false;
        } else {
            visibleDesasignarServicios = true;
        }
    }

    /**
     * Evento que se dispara al seleccionar un tipo de novedad en el modal que
     * muestra los tipos de novedades y asigna el tipo de novedad seleccionado
     * al valor para la vista
     *
     * @param event
     */
    public void onRowSelectNovTipo(final SelectEvent event) {
        if (event.getObject() instanceof NovedadTipo) {
            novedadTipo = (NovedadTipo) event.getObject();
        }
        if (novedadTipo.getIdNovedadTipo() == 13) {
            pqrVisible = true;
        } else {
            pqrVisible = false;
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
        if (event.getObject() instanceof NovedadTipoDetalles) {
            novedadTipoDet = (NovedadTipoDetalles) event.getObject();
            if (novedadTipoDet.getAfectaDisponibilidad() == 1) {
                selectDispSistemaBean.consultarSistema();
                PrimeFaces.current().executeScript("validarNovedadDuplicada()");
            }
            if (novedadTipoDet.getReqHora() == 1) {
                Novedad nov = novJSF.getNovedad();
                if (nov != null) {
                    nov.setHora(Util.dateToTimeHHMMSS(MovilidadUtil.fechaCompletaHoy()));
                }
            }
        }
        if (modulo.equals(ConstantsUtil.MDL_NOVEDAD_CLASIFICA) || modulo.equals(ConstantsUtil.MDL_HISTORICO_NOV)) {
            clasificacionNovedadBean.setEstadoPendienteActual();
        }
        MovilidadUtil.clearFilter("dialog_nov_tipo_det_dt_wv");
    }

    /**
     * Prepara los tipos de novedades antes de registrar/modificar una novedad
     *
     */
    public void prepareListNovedadTipo() {//ausentismo
        if (modulo.equals(ConstantsUtil.MDL_NOVEDAD_CLASIFICA) || modulo.equals(ConstantsUtil.MDL_HISTORICO_NOV)) {
            lstNovedadTipos = novedadTipoEjb.findAllByNovTipDetMtto();
        } else {
            lstNovedadTipos = novedadTipoEjb.obtenerTipos();
        }
        visibleDesasignarServicios = true;
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
        for (GrantedAuthority auth : user.getAuthorities()) {
            rol_user = auth.getAuthority();
        }
        flagUserTC = rol_user.equals("ROLE_TC");        
        if (novedadTipo.getIdNovedadTipo() == 1 && flagUserTC) {
            lstNovedadTipoDetalles = novedadTipoDetEjb.findByTipoNovedadForTC(novedadTipo.getIdNovedadTipo());//traer solo ausencia, ausencia parcial, inoperable TM;
            visibleDesasignarServicios = false;
        } else {
            if (modulo.equals(ConstantsUtil.MDL_NOVEDAD_CLASIFICA) || modulo.equals(ConstantsUtil.MDL_HISTORICO_NOV)) {
                lstNovedadTipoDetalles = novedadTipoDetEjb.findByTipoNovedadAndBeMtto(novedadTipo.getIdNovedadTipo());
            } else {
                lstNovedadTipoDetalles = novedadTipoDetEjb.findByTipoNovedad(novedadTipo.getIdNovedadTipo());
            }
        }
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

        lstNovedadTipoDetalles = novedadTipoDetEjb.obtenerDetallesNomina(idDetalles != null ? idDetalles.substring(1, idDetalles.length() - 1) : null);

        MovilidadUtil.clearFilter("dialog_nov_tipo_det_dt_wv");
    }

    public boolean visibleMotivoJornada() {
        if (novedadTipoDet == null) {
            return true;
        } else if (novedadTipoDet.getFechas() == ConstantsUtil.ON_INT) {
            if (novedadTipoDet.getAfectaProgramacion() == ConstantsUtil.ON_INT) {
                return false;
            }
        }
        return true;
    }

    public boolean esDano() {
        return novedadTipo.getNombreTipoNovedad().contains(Util.DANO);
    }

    public List<NovedadTipo> getLstNovedadTipos() {
        return lstNovedadTipos;
    }

    public void setLstNovedadTipos(List<NovedadTipo> lstNovedadTipos) {
        this.lstNovedadTipos = lstNovedadTipos;
    }

    public List<NovedadTipoDetalles> getLstNovedadTipoDetalles() {
        return lstNovedadTipoDetalles;
    }

    public void setLstNovedadTipoDetalles(List<NovedadTipoDetalles> lstNovedadTipoDetalles) {
        this.lstNovedadTipoDetalles = lstNovedadTipoDetalles;
    }

    public NovedadTipo getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(NovedadTipo novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public NovedadTipoDetalles getNovedadTipoDet() {
        return novedadTipoDet;
    }

    public void setNovedadTipoDet(NovedadTipoDetalles novedadTipoDet) {
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

    public boolean isPqrVisible() {
        return pqrVisible;
    }

    public void setPqrVisible(boolean pqrVisible) {
        this.pqrVisible = pqrVisible;
    }

    public String getCompUpdateVistaCreateNov() {
        return compUpdateVistaCreateNov;
    }

    public void setCompUpdateVistaCreateNov(String compUpdateVistaCreateNov) {
        this.compUpdateVistaCreateNov = compUpdateVistaCreateNov;
    }

    public boolean isVisibleDesasignarServicios() {
        return visibleDesasignarServicios;
    }

    public void setVisibleDesasignarServicios(boolean visibleDesasignarServicios) {
        this.visibleDesasignarServicios = visibleDesasignarServicios;
    }

}
