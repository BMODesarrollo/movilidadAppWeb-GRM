/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "configEmpresaJSF")
@ViewScoped
public class ConfigEmpresaJSF implements Serializable {

    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;

    private ConfigEmpresa configEmpresa;

    //atributos
    String dia_inicio_semana;
    String dia_fin_semana;
    String dia_corte_semana;
    String hora_corte_semana;
    private String cNuevoVal;

    //colecciones
    private List<ConfigEmpresa> listConfigEmpresa;
    private List<ConfigEmpresa> listConfigSemana;//lista que se emplea para cargar 
    //los valores parametrizados para la configuración de semana en el proceso green de programación
    List<String> listDias;

//    private HashMap<String, ConfigEmpresa> mapConfigEmpresa;
    private String cKeyAux;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public ConfigEmpresaJSF() {
    }

    @PostConstruct
    public void init() {
        consultar();
        configEmpresa = null;
        cKeyAux = null;
//        mapConfigEmpresa = new HashMap<>();
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa() == null) {
            cargarMapConfigEmpresa();
        }
        cargarParametrizacionSemanaProgramacion();
    }

    public void consultar() {
        listConfigEmpresa = configEmpresaFacadeLocal.findEstadoReg();
    }

    public void cargarMapConfigEmpresa() {
        List<ConfigEmpresa> listCe = configEmpresaFacadeLocal.findEstadoReg();
        SingletonConfigEmpresa.getMapConfiMapEmpresa().clear();
        for (ConfigEmpresa ce : listCe) {
            SingletonConfigEmpresa.getMapConfiMapEmpresa().put(ce.getLlave(), ce.getValor());
        }
    }

    public void prepareGuardar() {
        configEmpresa = new ConfigEmpresa();
    }

    public void guardar() {
        if (configEmpresa != null) {
//            cargarMap();
            if (validarKey()) {
                MovilidadUtil.addErrorMessage("La llave a utilizar hace referencia a otro registro, intente con otra.");
                return;
            }
            Date d = new Date();
            configEmpresa.setCreado(d);
            configEmpresa.setModificado(d);
            configEmpresa.setEstadoReg(0);
            configEmpresa.setUsername(user.getUsername());
            configEmpresaFacadeLocal.create(configEmpresa);
            reset();
            configEmpresa = new ConfigEmpresa();
            cargarMapConfigEmpresa();
            MovilidadUtil.addSuccessMessage("Configuración registrada correctamente");
            PrimeFaces.current().executeScript("PF('config_empDlg_wv').hide()");
        }
    }

    public void editar() {
        if (configEmpresa != null) {
//            cargarMap();
            if (!cKeyAux.equals(configEmpresa.getLlave())) {
                if (validarKey()) {
                    MovilidadUtil.addErrorMessage("La llave a utilizar hace referencia a otro registro, intente con otra.");
                    return;
                }
            }
            Date d = new Date();
            configEmpresa.setModificado(d);
            configEmpresaFacadeLocal.edit(configEmpresa);
            reset();
            cargarMapConfigEmpresa();
            MovilidadUtil.addSuccessMessage("Configuración actualizada correctamente");
            PrimeFaces.current().executeScript("PF('config_empDlg_wv').hide()");
        }
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            ConfigEmpresa semanaConfig = (ConfigEmpresa) ((DataTable) event.getComponent()).getRowData();
            if (semanaConfig != null) {
                if (cNuevoVal != null && validarDato(cNuevoVal.toUpperCase())) {
                    if (event.getColumn().getAriaHeaderText().equals("1")) { // 1 para la columna Valor
                        semanaConfig.setValor(cNuevoVal.toUpperCase());
                    }
                    if (event.getColumn().getAriaHeaderText().equals("2")) { // 2 para la columna observacion
                        semanaConfig.setValor(cNuevoVal);
                    }
                    configEmpresaFacadeLocal.edit(semanaConfig);
                    MovilidadUtil.addSuccessMessage("Se ha guardado la parametrización de semana correctamente");
                    return;
                }
                MovilidadUtil.addErrorMessage("Verifique el día ingresado");
            } else {
                MovilidadUtil.addErrorMessage("Error al guardar la paremetrización de semana");
            }
            cNuevoVal = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarParamSemana() {
        if (hora_corte_semana != null && validarHoraCorte(hora_corte_semana)) {
            listConfigSemana.get(0).setValor(dia_inicio_semana);
            listConfigSemana.get(1).setValor(dia_fin_semana);
            listConfigSemana.get(2).setValor(dia_corte_semana);
            listConfigSemana.get(3).setValor(hora_corte_semana);
            configEmpresaFacadeLocal.edit(listConfigSemana.get(0));
            configEmpresaFacadeLocal.edit(listConfigSemana.get(1));
            configEmpresaFacadeLocal.edit(listConfigSemana.get(2));
            configEmpresaFacadeLocal.edit(listConfigSemana.get(3));
            MovilidadUtil.addSuccessMessage("Se ha guardado la parametrización de semana correctamente");
        } else {
            MovilidadUtil.addSuccessMessage("Valor 'Hora Corte' no válido");
        }
    }

    private boolean validarHoraCorte(String hora) {
        return Util.isTimeValidate(hora);
    }

    private boolean validarDato(String valor) {
        boolean flag = false;
        switch (valor) {
            case "LUNES":
            case "MARTES":
            case "MIERCOLES":
            case "JUEVES":
            case "VIERNES":
            case "SABADO":
            case "DOMINGO":
                flag = true;
        }
        return flag;
    }

    public void onCellEdit(ConfigEmpresa obj) {
        try {
            if (cNuevoVal != null) {
                configEmpresaFacadeLocal.edit(obj);
                MovilidadUtil.addSuccessMessage("Operación realizada con éxito");
                return;
            }
            MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
            cNuevoVal = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSelecConfigEmpresa(ConfigEmpresa ce) {
        cKeyAux = ce.getLlave();
        configEmpresa = ce;
    }

    public void reset() {
        configEmpresa = null;
        cKeyAux = null;
    }

    private void cargarParametrizacionSemanaProgramacion() {
        loadParamSemanaProgramacion();
        listDias = llenarDias();
    }

    private void loadParamSemanaProgramacion() {
        listConfigSemana = new ArrayList<>();
        listConfigSemana.add(configEmpresaFacadeLocal.findByLlave("DIA_INICIO_SEMANA_PROGRAMACION"));
        listConfigSemana.add(configEmpresaFacadeLocal.findByLlave("DIA_FIN_SEMANA_PROGRAMACION"));
        listConfigSemana.add(configEmpresaFacadeLocal.findByLlave("DIA_CORTE_SEMANA_PROGRAMACION"));
        listConfigSemana.add(configEmpresaFacadeLocal.findByLlave("HORA_CORTE_SEMANA_PROGRAMACION"));
        dia_inicio_semana = listConfigSemana.get(0).getValor();
        dia_fin_semana = listConfigSemana.get(1).getValor();
        dia_corte_semana = listConfigSemana.get(2).getValor();
        hora_corte_semana = listConfigSemana.get(3).getValor();
    }

    private ArrayList<String> llenarDias() {
        List<String> list = new ArrayList<>();
        list.add("LUNES");
        list.add("MARTES");
        list.add("MIERCOLES");
        list.add("JUEVES");
        list.add("VIERNES");
        list.add("SABADO");
        list.add("DOMINGO");
        return (ArrayList<String>) list;
    }

//    void cargarMap() {
//        mapConfigEmpresa.clear();
//        List<ConfigEmpresa> findAll = configEmpresaFacadeLocal.findAll();
//        if (findAll != null && !findAll.isEmpty()) {
//            for (ConfigEmpresa ce : findAll) {
//                mapConfigEmpresa.put(ce.getLlave(), ce);
//            }
//        }
//    }
    boolean validarKey() {
        String ce = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(configEmpresa.getLlave());
        return ce != null;
    }

    public ConfigEmpresa getConfigEmpresa() {
        return configEmpresa;
    }

    public void setConfigEmpresa(ConfigEmpresa configEmpresa) {
        this.configEmpresa = configEmpresa;
    }

    public List<ConfigEmpresa> getListConfigEmpresa() {
        return listConfigEmpresa;
    }

    public void setListConfigEmpresa(List<ConfigEmpresa> listConfigEmpresa) {
        this.listConfigEmpresa = listConfigEmpresa;
    }

    public List<ConfigEmpresa> getListConfigSemana() {
        return listConfigSemana;
    }

    public void setListConfigSemana(List<ConfigEmpresa> listConfigSemana) {
        this.listConfigSemana = listConfigSemana;
    }

    public String getDia_inicio_semana() {
        return dia_inicio_semana;
    }

    public void setDia_inicio_semana(String dia_inicio_semana) {
        this.dia_inicio_semana = dia_inicio_semana;
    }

    public String getDia_fin_semana() {
        return dia_fin_semana;
    }

    public void setDia_fin_semana(String dia_fin_semana) {
        this.dia_fin_semana = dia_fin_semana;
    }

    public String getDia_corte_semana() {
        return dia_corte_semana;
    }

    public void setDia_corte_semana(String dia_corte_semana) {
        this.dia_corte_semana = dia_corte_semana;
    }

    public List<String> getListDias() {
        return listDias;
    }

    public void setListDias(List<String> listDias) {
        this.listDias = listDias;
    }

    public String getcNuevoVal() {
        return cNuevoVal;
    }

    public void setcNuevoVal(String cNuevoVal) {
        this.cNuevoVal = cNuevoVal;
    }

    public String getHora_corte_semana() {
        return hora_corte_semana;
    }

    public void setHora_corte_semana(String hora_corte_semana) {
        this.hora_corte_semana = hora_corte_semana;
    }

}
