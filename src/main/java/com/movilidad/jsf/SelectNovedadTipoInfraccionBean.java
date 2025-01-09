/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadTipoInfraccionFacadeLocal;
import com.movilidad.model.NovedadTipoInfraccion;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jjunco
 */
@Named(value = "selectNovedadTipoInfraccionBean")
@ViewScoped
public class SelectNovedadTipoInfraccionBean implements Serializable {

    public SelectNovedadTipoInfraccionBean() {
    }
    @EJB
    private NovedadTipoInfraccionFacadeLocal infraccionEJB;
    private Integer id_infraccion;

    private List<NovedadTipoInfraccion> listInfracciones;

    void consultarInfraccion() {
        listInfracciones = infraccionEJB.findAllByEstadoReg();
    }

    public List<NovedadTipoInfraccion> getListInfraccion() {
        if (listInfracciones == null) {
            consultarInfraccion();
        }
        return listInfracciones;
    }

    public Integer getId_infraccion() {
        return id_infraccion;
    }

    public void setId_infraccion(Integer id_infraccion) {
        this.id_infraccion = id_infraccion;
    }

    public void setListInfracciones(List<NovedadTipoInfraccion> listInfracciones) {
        this.listInfracciones = listInfracciones;
    }



}
