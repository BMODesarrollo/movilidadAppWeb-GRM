/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.DispSistemaFacadeLocal;
import com.movilidad.model.DispSistema;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author solucionesit
 */
@Named(value = "selectDispSistemaBean")
@ViewScoped
public class SelectDispSistemaBean implements Serializable {

    /**
     * Creates a new instance of SelectDispSistemaBean
     */
    public SelectDispSistemaBean() {
    }
    @EJB
    private DispSistemaFacadeLocal sistemaEJB;
    private Integer id_dis_sistema;

    private List<DispSistema> listDistSistema;

    void consultarSistema() {
        listDistSistema = sistemaEJB.findAllByEstadoReg();
    }

    public List<DispSistema> getListDistSistema() {
        if (listDistSistema == null) {
            consultarSistema();
        }
        return listDistSistema;
    }

    public void setListDistSistema(List<DispSistema> listDistSistema) {
        this.listDistSistema = listDistSistema;
    }

    public Integer getId_dis_sistema() {
        return id_dis_sistema;
    }

    public void setId_dis_sistema(Integer id_dis_sistema) {
        this.id_dis_sistema = id_dis_sistema;
    }

}
