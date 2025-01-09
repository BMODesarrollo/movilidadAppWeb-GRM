/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoProcesoFacadeLocal;
import com.movilidad.model.AccTipoProceso;
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
 * @author HP
 */
@Named(value = "accTipoProcesoJSF")
@ViewScoped
public class AccTipoProcesoJSF implements Serializable {

    @EJB
    private AccTipoProcesoFacadeLocal accTipoProcesoFacadeLocal;

    private AccTipoProceso accTipoProceso;

    private List<AccTipoProceso> listAccTipoProceso;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccTipoProcesoJSF() {
    }

    public void guardar() {
        try {
            if (accTipoProceso != null) {
                accTipoProceso.setCausaRaiz(accTipoProceso.getCausaRaiz().toUpperCase());
                accTipoProceso.setCreado(new Date());
                accTipoProceso.setModificado(new Date());
                accTipoProceso.setEstadoReg(0);
                accTipoProceso.setUsername(user.getUsername());
                accTipoProcesoFacadeLocal.create(accTipoProceso);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc TipoProceso correctamente");
                accTipoProceso = new AccTipoProceso();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc TipoProceso");
        }
    }

    public void actualizar() {
        try {
            if (accTipoProceso != null) {
                accTipoProceso.setCausaRaiz(accTipoProceso.getCausaRaiz().toUpperCase());
                accTipoProcesoFacadeLocal.edit(accTipoProceso);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc TipoProceso correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('tp-proceso-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc TipoProceso");
        }
    }

    public void prepareGuardar() {
        accTipoProceso = new AccTipoProceso();
    }

    public void reset() {
        accTipoProceso = null;
    }

    public void onRowSelect(SelectEvent event) {
        accTipoProceso = (AccTipoProceso) event.getObject();
    }

    public AccTipoProceso getAccTipoProceso() {
        return accTipoProceso;
    }

    public void setAccTipoProceso(AccTipoProceso accTipoProceso) {
        this.accTipoProceso = accTipoProceso;
    }

    public List<AccTipoProceso> getListAccTipoProceso() {
        listAccTipoProceso = accTipoProcesoFacadeLocal.estadoReg();
        return listAccTipoProceso;
    }
}
