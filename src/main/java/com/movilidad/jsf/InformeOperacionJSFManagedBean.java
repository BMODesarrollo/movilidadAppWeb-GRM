/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.util.beans.InformeOperacion;
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
@Named(value = "informeOpBean")
@ViewScoped
public class InformeOperacionJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of InformeOperacionJSFManagedBean
     */
    public InformeOperacionJSFManagedBean() {
    }
    @EJB
    private PrgTcFacadeLocal prgTcEJB;
    private List<InformeOperacion> list;

    @PostConstruct
    public void init() {
        list = prgTcEJB.InformeOperacionParaMtto();
    }

    public List<InformeOperacion> getList() {
        return list;
    }

    public void setList(List<InformeOperacion> list) {
        this.list = list;
    }

    
}
