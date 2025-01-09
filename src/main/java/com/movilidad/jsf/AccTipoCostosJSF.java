/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoCostosFacadeLocal;
import com.movilidad.model.AccTipoCostos;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accTipoCostosJSF")
@ViewScoped
public class AccTipoCostosJSF implements Serializable {

    @EJB
    private AccTipoCostosFacadeLocal accTipoCostosFacadeLocal;

    private AccTipoCostos accTipoCostos;

    private List<AccTipoCostos> listAccTipoCostos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccTipoCostosJSF() {
    }

    @PostConstruct
    void init() {
        listAccTipoCostos = new ArrayList<>();
    }

    public void guardar() {
        try {
            if (accTipoCostos != null) {
                accTipoCostos.setTipoCostos(accTipoCostos.getTipoCostos().toUpperCase());
                accTipoCostos.setCreado(new Date());
                accTipoCostos.setModificado(new Date());
                accTipoCostos.setEstadoReg(0);
                accTipoCostos.setUsername(user.getUsername());
                accTipoCostosFacadeLocal.create(accTipoCostos);
                MovilidadUtil.addSuccessMessage("Se a registrado el tipo de costos correctamente");
                accTipoCostos = new AccTipoCostos();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al guardar tipo de costos");
        }
    }

    public void actualizar() {
        try {
            if (accTipoCostos != null) {
                accTipoCostos.setTipoCostos(accTipoCostos.getTipoCostos().toUpperCase());
                accTipoCostos.setModificado(new Date());
                accTipoCostosFacadeLocal.edit(accTipoCostos);
                MovilidadUtil.addSuccessMessage("Se a actualizado el tipo de costos correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('tp-costos-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar tipo de costos");
        }
    }

    public void prepareGuardar() {
        accTipoCostos = new AccTipoCostos();
    }

    public void reset() {
        accTipoCostos = null;
    }

    public void onRowSelect(SelectEvent event) {
        accTipoCostos = (AccTipoCostos) event.getObject();
    }

    public AccTipoCostos getAccTipoCostos() {
        return accTipoCostos;
    }

    public void setAccTipoCostos(AccTipoCostos accTipoCostos) {
        this.accTipoCostos = accTipoCostos;
    }

    public List<AccTipoCostos> getListAccTipoCostos() {
        listAccTipoCostos = accTipoCostosFacadeLocal.estadoReg();
        return listAccTipoCostos;
    }

}
