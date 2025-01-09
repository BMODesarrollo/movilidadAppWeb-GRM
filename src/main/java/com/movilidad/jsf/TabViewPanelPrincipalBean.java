/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author solucionesit
 */
@Named(value = "tabViewPPBean")
@ViewScoped
public class TabViewPanelPrincipalBean implements Serializable {

    /**
     * Creates a new instance of TabViewPanelPrincipalBean
     */
    public TabViewPanelPrincipalBean() {
    }
    private String tabId;

    @Inject
    private AlertaEntradaVehiculoBean alertaEntradaVehiculoBean;
    @Inject
    private ValidarDocumentacionBean validarDocumentacionBean;
    @Inject
    private AlertaAsistenciaBean alertaAsistenciaBean;

    @PostConstruct
    public void init() {
        tabId = "";
    }

    public void tabChange(TabChangeEvent event) {
        tabId = event.getTab().getId();
        if (ConstantsUtil.TAB_ASISTENCIA_VEHICULO.equals(tabId)) {
            alertaEntradaVehiculoBean.alertaSinPresentacion();
            MovilidadUtil.updateComponent("tab_pp:asistencia_veh_form:tblveh_present");
        } else if (ConstantsUtil.TAB_ASISTENCIA_OPERADOR.equals(tabId)) {
            alertaAsistenciaBean.consultarAllSinConfirmacion();
            MovilidadUtil.updateComponent("tab_pp:serconPresent_form:tblsercon_present");
        } else if (ConstantsUtil.TAB_DOC_VENCIDOS.equals(tabId)) {
            validarDocumentacionBean.consultarDocVencidosOpe();
            validarDocumentacionBean.consultarDocVencidosVehi();
            MovilidadUtil.updateComponent("tab_pp:tbldoc_venc_ope_form:tbldoc_venc_ope");
            MovilidadUtil.updateComponent("tab_pp:tbldoc_venc_veh_form:tbldoc_venc_veh");
        } else if (ConstantsUtil.TAB_VEHICULOS_DETENIDOS.equals(tabId)) {
            alertaEntradaVehiculoBean.obtenerVehiculosDetenidos();
            MovilidadUtil.updateComponent("tab_pp:vehiculos_detenidos_form:dtVehiculosDetenidos");
        }
    }

    public void cargarDataFromPoll() {
        if (ConstantsUtil.TAB_ASISTENCIA_VEHICULO.equals(tabId)) {
            alertaEntradaVehiculoBean.alertaSinPresentacion();
            MovilidadUtil.updateComponent("tab_pp:asistencia_veh_form:tblveh_present");
        } else if (ConstantsUtil.TAB_ASISTENCIA_OPERADOR.equals(tabId)) {
            alertaAsistenciaBean.consultarAllSinConfirmacion();
            MovilidadUtil.updateComponent("tab_pp:serconPresent_form:tblsercon_present");
        } else if (ConstantsUtil.TAB_DOC_VENCIDOS.equals(tabId)) {
            validarDocumentacionBean.consultarDocVencidosOpe();
            validarDocumentacionBean.consultarDocVencidosVehi();
            MovilidadUtil.updateComponent("tab_pp:tbldoc_venc_ope_form:tbldoc_venc_ope");
            MovilidadUtil.updateComponent("tab_pp:tbldoc_venc_veh_form:tbldoc_venc_veh");
        } else if (ConstantsUtil.TAB_VEHICULOS_DETENIDOS.equals(tabId)) {
            alertaEntradaVehiculoBean.obtenerVehiculosDetenidos();
            MovilidadUtil.updateComponent("tab_pp:vehiculos_detenidos_form:dtVehiculosDetenidos");
        }
    }

}
