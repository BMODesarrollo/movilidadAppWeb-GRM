/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgSerconMotivoFacadeLocal;
import com.movilidad.model.PrgSerconMotivo;
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
@Named(value = "prgSerconMotivoJSF")
@ViewScoped
public class PrgSerconMotivoJSF implements Serializable {

    /**
     * Creates a new instance of PrgSerconMotivoJSF
     */
    public PrgSerconMotivoJSF() {
    }
    private Integer i_prgSerconMotivo;
    private PrgSerconMotivo prgSerconMotivo;
    private List<PrgSerconMotivo> listPrgSerconMotivo;

    @EJB
    private PrgSerconMotivoFacadeLocal prgSerconMotivoEJB;

    @PostConstruct
    public void init() {
        i_prgSerconMotivo = null;
    }

    public void cargarPrgSerconMotivo() {
        if (i_prgSerconMotivo != null) {
            prgSerconMotivo = prgSerconMotivoEJB.find(i_prgSerconMotivo);
        }
    }

    public void cargarListaMotivos() {
        listPrgSerconMotivo = prgSerconMotivoEJB.findAllEstadoRegistro();
    }

    public Integer getI_prgSerconMotivo() {
        return i_prgSerconMotivo;
    }

    public void setI_prgSerconMotivo(Integer i_prgSerconMotivo) {
        this.i_prgSerconMotivo = i_prgSerconMotivo;
    }

    public List<PrgSerconMotivo> getListPrgSerconMotivo() {
        cargarListaMotivos();
        return listPrgSerconMotivo;
    }

    public void setListPrgSerconMotivo(List<PrgSerconMotivo> listPrgSerconMotivo) {
        this.listPrgSerconMotivo = listPrgSerconMotivo;
    }

    public PrgSerconMotivo getPrgSerconMotivo() {
        return prgSerconMotivo;
    }

    public void setPrgSerconMotivo(PrgSerconMotivo prgSerconMotivo) {
        this.prgSerconMotivo = prgSerconMotivo;
    }

}
