/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaMixtaFacadeLocal;
import com.movilidad.model.AccViaMixta;
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
@Named(value = "accViaMixtaJSF")
@ViewScoped
public class AccViaMixtaJSF implements Serializable {

    @EJB
    private AccViaMixtaFacadeLocal accViaMixtaFacadeLocal;

    private AccViaMixta accViaMixta;

    private List<AccViaMixta> listAccViaMixta;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaMixtaJSF() {
    }

    public void guardar() {
        try {
            if (accViaMixta != null) {
                accViaMixta.setViaMixta(accViaMixta.getViaMixta().toUpperCase());
                accViaMixta.setCreado(new Date());
                accViaMixta.setModificado(new Date());
                accViaMixta.setEstadoReg(0);
                accViaMixta.setUsername(user.getUsername());
                accViaMixtaFacadeLocal.create(accViaMixta);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Mixta correctamente");
                accViaMixta = new AccViaMixta();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Mixta");
        }
    }

    public void actualizar() {
        try {
            if (accViaMixta != null) {
                accViaMixta.setViaMixta(accViaMixta.getViaMixta().toUpperCase());
                accViaMixtaFacadeLocal.edit(accViaMixta);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Mixta correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-mixta-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Mixta");
        }
    }

    public void prepareGuardar() {
        accViaMixta = new AccViaMixta();
    }

    public void reset() {
        accViaMixta = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaMixta = (AccViaMixta) event.getObject();
    }

    public AccViaMixta getAccViaMixta() {
        return accViaMixta;
    }

    public void setAccViaMixta(AccViaMixta accViaMixta) {
        this.accViaMixta = accViaMixta;
    }

    public List<AccViaMixta> getListAccViaMixta() {
        listAccViaMixta = accViaMixtaFacadeLocal.estadoReg();
        return listAccViaMixta;
    }

}
