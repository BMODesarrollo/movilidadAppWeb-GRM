/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigFreewayFacadeLocal;
import com.movilidad.model.ConfigFreeway;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author solucionesit
 */
@Named(value = "selectConfigFreewayBean")
@ViewScoped
public class SelectConfigFreewayBean implements Serializable {

    /**
     * Creates a new instance of SelectConfigFreewayBean
     */
    public SelectConfigFreewayBean() {
    }
    @EJB
    private ConfigFreewayFacadeLocal configFreewayEJB;
    private Integer idConfigFreeway = 0;
    private ConfigFreeway configFreeway;
    private boolean disabled;

    private List<ConfigFreeway> listConfigFreeway;

    void consultarSistema() {
        listConfigFreeway = configFreewayEJB.findAllByEstadoReg();
    }

    public void findIdConfigFreeway() {
        if (idConfigFreeway == null) {
            return;
        }
        configFreeway = configFreewayEJB.find(idConfigFreeway);
    }

    public List<ConfigFreeway> getListConfigFreeway() {
        consultarSistema();
        return listConfigFreeway;
    }

    int getIdGopUF() {
        return getConfigFreeway() == null ? 0
                : getConfigFreeway().getIdGopUnidadFuncional().getIdGopUnidadFuncional();
    }

    public void setListConfigFreeway(List<ConfigFreeway> listConfigFreeway) {
        this.listConfigFreeway = listConfigFreeway;
    }

    public Integer getIdConfigFreeway() {
        return idConfigFreeway;
    }

    public void setIdConfigFreeway(Integer idConfigFreeway) {
        this.idConfigFreeway = idConfigFreeway;
    }

    public ConfigFreeway getConfigFreeway() {
        return configFreeway;
    }

    public void setConfigFreeway(ConfigFreeway configFreeway) {
        this.configFreeway = configFreeway;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}
