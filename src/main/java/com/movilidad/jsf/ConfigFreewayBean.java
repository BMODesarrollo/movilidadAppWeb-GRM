/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigFreewayFacadeLocal;
import com.movilidad.model.ConfigFreeway;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "configFreewayBean")
@ViewScoped
public class ConfigFreewayBean implements Serializable {

    /**
     * Creates a new instance of ConfigFreewayBean
     */
    public ConfigFreewayBean() {
    }

    @EJB
    private ConfigFreewayFacadeLocal configFreewayEJB;

    @Inject
    private GopUnidadFuncionalSessionBean gopUnidadFuncionalSessionBean;
    private ConfigFreeway configFreeway;
    private ConfigFreeway selected;
    private String codSolucion;
    private String urlServicio;
    private String userFreeway;
    private String passwordFreeway;

    private boolean isEditing;

    private List<ConfigFreeway> lstConfigFreeway;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    private void consultar() {
        lstConfigFreeway = configFreewayEJB.findAllByEstadoReg();
    }

    public void nuevo() {
        codSolucion = null;
        urlServicio = null;
        userFreeway = null;
        passwordFreeway = null;
        configFreeway = new ConfigFreeway();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        codSolucion = selected.getCodigoSolucion();
        userFreeway = selected.getUserFreeway();
        passwordFreeway = selected.getPasswordFreeway();
        urlServicio = selected.getUrlServicio();
        userFreeway = selected.getUserFreeway();
        gopUnidadFuncionalSessionBean.setI_unidad_funcional(
                selected.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        configFreeway = selected;
        isEditing = true;
        MovilidadUtil.openModal("wv_config_freeway");
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        if (validarDatos()) {
            return;
        }
        configFreeway.setCodigoSolucion(codSolucion);
        configFreeway.setUrlServicio(urlServicio);
        configFreeway.setUserFreeway(userFreeway);
        configFreeway.setPasswordFreeway(passwordFreeway);
        configFreeway.setUsername(user.getUsername());
        configFreeway.setIdGopUnidadFuncional(
                new GopUnidadFuncional(
                        gopUnidadFuncionalSessionBean.getI_unidad_funcional()));

        if (isEditing) {
            configFreeway.setModificado(MovilidadUtil.fechaCompletaHoy());
            configFreewayEJB.edit(configFreeway);
        } else {
            configFreeway.setEstadoReg(0);
            configFreeway.setCreado(MovilidadUtil.fechaCompletaHoy());
            configFreewayEJB.create(configFreeway);
            nuevo();
        }
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        MovilidadUtil.hideModal("wv_config_freeway");
        consultar();

    }

    public boolean validarDatos() {
        if (configFreewayEJB.findByCodSolucion(codSolucion, isEditing
                ? selected.getIdConfigFreeway() : 0) != null) {
            MovilidadUtil.addErrorMessage("Ya existe un registro con el Código Solución ingresado");
            return true;
        }
        if (configFreewayEJB.findByIdGopUnidadFuncional(
                gopUnidadFuncionalSessionBean.getI_unidad_funcional(),
                isEditing ? selected.getIdConfigFreeway() : 0) != null) {
            MovilidadUtil.addErrorMessage("Ya existe un registro con la Unidad Funcional seleccionada");
            return true;
        }

        return false;
    }

    public ConfigFreeway getConfigFreeway() {
        return configFreeway;
    }

    public void setConfigFreeway(ConfigFreeway configFreeway) {
        this.configFreeway = configFreeway;
    }

    public ConfigFreeway getSelected() {
        return selected;
    }

    public void setSelected(ConfigFreeway selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<ConfigFreeway> getLstConfigFreeway() {
        return lstConfigFreeway;
    }

    public void setLstConfigFreeway(List<ConfigFreeway> lstConfigFreeway) {
        this.lstConfigFreeway = lstConfigFreeway;
    }

    public String getCodSolucion() {
        return codSolucion;
    }

    public void setCodSolucion(String codSolucion) {
        this.codSolucion = codSolucion;
    }

    public String getUrlServicio() {
        return urlServicio;
    }

    public void setUrlServicio(String urlServicio) {
        this.urlServicio = urlServicio;
    }

    public String getUserFreeway() {
        return userFreeway;
    }

    public void setUserFreeway(String userFreeway) {
        this.userFreeway = userFreeway;
    }

    public String getPasswordFreeway() {
        return passwordFreeway;
    }

    public void setPasswordFreeway(String passwordFreeway) {
        this.passwordFreeway = passwordFreeway;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

}
