/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoServFacadeLocal;
import com.movilidad.model.AccTipoServ;
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
@Named(value = "accTipoServJSF")
@ViewScoped
public class AccTipoServJSF implements Serializable {

    @EJB
    private AccTipoServFacadeLocal accTipoServFacadeLocal;

    private AccTipoServ accTipoServ;

    private List<AccTipoServ> listAccTipoServ;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccTipoServJSF() {
    }

    public void guardar() {
        try {
            if (accTipoServ != null) {
                accTipoServ.setTipoServ(accTipoServ.getTipoServ().toUpperCase());
                accTipoServ.setCreado(new Date());
                accTipoServ.setModificado(new Date());
                accTipoServ.setEstadoReg(0);
                accTipoServ.setUsername(user.getUsername());
                accTipoServFacadeLocal.create(accTipoServ);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Tipo Servicio correctamente");
                accTipoServ = new AccTipoServ();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Tipo Servicio");
        }
    }

    public void actualizar() {
        try {
            if (accTipoServ != null) {
                accTipoServ.setTipoServ(accTipoServ.getTipoServ().toUpperCase());
                accTipoServFacadeLocal.edit(accTipoServ);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Tipo Servicio correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('t-serv-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Tipo Servicio");
        }
    }

    public void prepareGuardar() {
        accTipoServ = new AccTipoServ();
    }

    public void reset() {
        accTipoServ = null;
    }

    public void onRowSelect(SelectEvent event) {
        accTipoServ = (AccTipoServ) event.getObject();
    }

    public AccTipoServ getAccTipoServ() {
        return accTipoServ;
    }

    public void setAccTipoServ(AccTipoServ accTipoServ) {
        this.accTipoServ = accTipoServ;
    }

    public List<AccTipoServ> getListAccTipoServ() {
        listAccTipoServ = accTipoServFacadeLocal.estadoReg();
        return listAccTipoServ;
    }

}
