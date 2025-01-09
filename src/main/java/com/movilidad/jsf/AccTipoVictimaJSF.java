/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoVictimaFacadeLocal;
import com.movilidad.model.AccTipoVictima;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "accTipoVictimaJSF")
@ViewScoped
public class AccTipoVictimaJSF implements Serializable {

    @EJB
    private AccTipoVictimaFacadeLocal accTipoVictimaFacadeLocal;

    private AccTipoVictima accTipoVictima;

    private List<AccTipoVictima> listAccTipoVictima;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of AccTipoVictimaJSF
     */
    public AccTipoVictimaJSF() {
    }

    /**
     * Permite persistir la data del objeto AccTipoVictima en la base de datos
     */
    public void guardar() {
        try {
            if (accTipoVictima != null) {
                accTipoVictima.setTipo(accTipoVictima.getTipo().toUpperCase());
                accTipoVictima.setCreado(new Date());
                accTipoVictima.setModificado(new Date());
                accTipoVictima.setEstadoReg(0);
                accTipoVictima.setUsername(user.getUsername());
                accTipoVictimaFacadeLocal.create(accTipoVictima);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc TipoVictima correctamente");
                accTipoVictima = new AccTipoVictima();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Tipo Víctima");
        }
    }

    /**
     * Permite realizar un update del objeto AccTipoVictima en la base de datos
     */
    public void actualizar() {
        try {
            if (accTipoVictima != null) {
                accTipoVictima.setTipo(accTipoVictima.getTipo().toUpperCase());
                accTipoVictima.setUsername(user.getUsername());
                accTipoVictimaFacadeLocal.edit(accTipoVictima);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc TipoVictima correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('tpVictima-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Tipo Víctima");
        }
    }

    /**
     * Permite crear la instancia del objeto AccTipoVictima
     */
    public void prepareGuardar() {
        accTipoVictima = new AccTipoVictima();
    }

    public void reset() {
        accTipoVictima = null;
    }

    /**
     *
     * @param event Objeto AccTipoVictima seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accTipoVictima = (AccTipoVictima) event.getObject();
    }

    public AccTipoVictima getAccTipoVictima() {
        return accTipoVictima;
    }

    public void setAccTipoVictima(AccTipoVictima accTipoVictima) {
        this.accTipoVictima = accTipoVictima;
    }

    public List<AccTipoVictima> getListAccTipoVictima() {
        listAccTipoVictima = accTipoVictimaFacadeLocal.findAllEstadoReg();
        return listAccTipoVictima;
    }

    public void setListAccTipoVictima(List<AccTipoVictima> listAccTipoVictima) {
        this.listAccTipoVictima = listAccTipoVictima;
    }

}
