/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author solucionesit
 */
@Named(value = "selectUnidFuncBean")
@ViewScoped
public class SelectGopUnidadFuncionalBean implements Serializable {

    /**
     * Creates a new instance of SelectGopUnidadFuncionalBean
     */
    public SelectGopUnidadFuncionalBean() {
    }
    private List<GopUnidadFuncional> listGopUnidadFuncional;
    private Integer i_unidad_funcional;
    private String s_nombre_uf;

    @EJB
    private GopUnidadFuncionalFacadeLocal gopUniFuncEjb;

    @PostConstruct
    public void init() {
        consultarList();
    }

    public void setterNombre() {
        for (GopUnidadFuncional uf : listGopUnidadFuncional) {
            if (uf.getIdGopUnidadFuncional().equals(i_unidad_funcional)) {
                s_nombre_uf = uf.getCodigo();
            }
        }
    }

    void consultarList() {
        listGopUnidadFuncional = gopUniFuncEjb.findAllByEstadoReg();
    }

    public List<GopUnidadFuncional> getListGopUnidadFuncional() {
        return listGopUnidadFuncional;
    }

    public void setListGopUnidadFuncional(List<GopUnidadFuncional> listGopUnidadFuncional) {
        this.listGopUnidadFuncional = listGopUnidadFuncional;
    }

    public Integer getI_unidad_funcional() {
        return i_unidad_funcional;
    }

    public void setI_unidad_funcional(Integer i_unidad_funcional) {
        this.i_unidad_funcional = i_unidad_funcional;
    }

    public String getS_nombre_uf() {
        return s_nombre_uf;
    }

    public void setS_nombre_uf(String s_nombre_uf) {
        this.s_nombre_uf = s_nombre_uf;
    }

}
