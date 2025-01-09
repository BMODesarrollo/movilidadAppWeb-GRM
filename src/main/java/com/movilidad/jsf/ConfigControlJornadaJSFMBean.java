/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigControlJornadaFacadeLocal;
import com.movilidad.model.ConfigControlJornada;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigControlJornada;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "configControlJornadaBean")
@ViewScoped
public class ConfigControlJornadaJSFMBean implements Serializable {

    @EJB
    private ConfigControlJornadaFacadeLocal configControlJornadaFacadeLocal;

    private List<ConfigControlJornada> list;
    private ConfigControlJornada configControlJornada;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of ConfigControlJornadaJSFMBean
     */
    public ConfigControlJornadaJSFMBean() {
    }

    @PostConstruct
    public void init() {
        list = configControlJornadaFacadeLocal.findAllByEstadoRegActivo();
    }

    public void prepareGuardar() {
        configControlJornada = new ConfigControlJornada();
        MovilidadUtil.openModal("config_wv");
    }

    public void guardar() {
        if (configControlJornada == null) {
            MovilidadUtil.addErrorMessage("Error al guardar");
            return;
        }
        if (validarConfigJornada()) {
            MovilidadUtil.addErrorMessage("Configuración ya se encuentra registrada");
            return;
        }
        Date d = new Date();
        configControlJornada.setCreado(d);
        configControlJornada.setModificado(d);
        configControlJornada.setEstadoReg(ConstantsUtil.CODE_ESTADO_REG_ACTIVO);
        configControlJornada.setUsername(user.getUsername());
        configControlJornadaFacadeLocal.create(configControlJornada);
        list.add(configControlJornada);
        cargarMapConfigJornada();
        MovilidadUtil.addSuccessMessage("Se ha guardado el registro con éxito");
        MovilidadUtil.hideModal("config_wv");
    }

    public void onCellEdit(CellEditEvent event) {
        ConfigControlJornada c = (ConfigControlJornada) ((DataTable) event.getComponent()).getRowData();
        String columnKey = event.getColumn().getColumnKey();
        if (columnKey.contains("columnEstado")) {
            c.setEstado(Integer.valueOf(event.getNewValue().toString()));
        }
        if (columnKey.contains("columnTiempo")) {
            c.setTiempo(String.valueOf(event.getNewValue()));
        }
        if (columnKey.contains("columnRestringe")) {
            c.setRestringe(Integer.valueOf(event.getNewValue().toString()));
        }
        if (columnKey.contains("columnObservacion")) {
            c.setObservacion(String.valueOf(event.getNewValue()));
        }
        configControlJornadaFacadeLocal.edit(c);
        cargarMapConfigJornada();
        MovilidadUtil.addSuccessMessage("Se ha actualizado el registro con éxito");
    }

    private boolean validarConfigJornada() {
        if (configControlJornada.getNombreConfig() == null) {
            return true;
        }
        if (configControlJornada.getNombreConfig().isEmpty()) {
            return true;
        }
        list = configControlJornadaFacadeLocal.findAllByEstadoRegActivo();
        return list.stream()
                .anyMatch(confg -> confg.getNombreConfig().equalsIgnoreCase(configControlJornada.getNombreConfig()));
    }

    private void cargarMapConfigJornada() {
        List<ConfigControlJornada> configJornadas = configControlJornadaFacadeLocal.findAllByEstadoRegActivo();
        SingletonConfigControlJornada.getMapConfigControlJornada().clear();
        for (ConfigControlJornada cj : configJornadas) {
            SingletonConfigControlJornada.getMapConfigControlJornada().put(cj.getNombreConfig(), cj);
        }
    }

    public List<ConfigControlJornada> getList() {
        return list;
    }

    public void setList(List<ConfigControlJornada> list) {
        this.list = list;
    }

    public ConfigControlJornada getConfigControlJornada() {
        return configControlJornada;
    }

    public void setConfigControlJornada(ConfigControlJornada configControlJornada) {
        this.configControlJornada = configControlJornada;
    }

}
