/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoConcFacadeLocal;
import com.movilidad.model.AccTipoConc;
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
@Named(value = "accTipoConcJSF")
@ViewScoped
public class AccTipoConcJSF implements Serializable {

    @EJB
    private AccTipoConcFacadeLocal accTipoConcFacadeLocal;

    private AccTipoConc accTipoConc;

    private List<AccTipoConc> listAccTipoConc;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccTipoConcJSF() {
    }

    public void guardar() {
        try {
            if (accTipoConc != null) {
                accTipoConc.setTipoConc(accTipoConc.getTipoConc().toUpperCase());
                accTipoConc.setCreado(new Date());
                accTipoConc.setModificado(new Date());
                accTipoConc.setEstadoReg(0);
                accTipoConc.setUsername(user.getUsername());
                accTipoConcFacadeLocal.create(accTipoConc);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Tipo Contrato correctamente");
                accTipoConc = new AccTipoConc();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Tipo Contrato");
        }
    }

    public void actualizar() {
        try {
            if (accTipoConc != null) {
                accTipoConc.setTipoConc(accTipoConc.getTipoConc().toUpperCase());
                accTipoConcFacadeLocal.edit(accTipoConc);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Tipo Contrato correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('t-conc-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Tipo Contrato");
        }
    }

    public void prepareGuardar() {
        accTipoConc = new AccTipoConc();
    }

    public void reset() {
        accTipoConc = null;
    }

    public void onRowSelect(SelectEvent event) {
        accTipoConc = (AccTipoConc) event.getObject();
    }

    public AccTipoConc getAccTipoConc() {
        return accTipoConc;
    }

    public void setAccTipoConc(AccTipoConc accTipoConc) {
        this.accTipoConc = accTipoConc;
    }

    public List<AccTipoConc> getListAccTipoConc() {
        listAccTipoConc = accTipoConcFacadeLocal.estadoReg();
        return listAccTipoConc;
    }

}
