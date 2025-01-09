/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccSentidoFacadeLocal;
import com.movilidad.model.AccSentido;
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
@Named(value = "accSentidoJSF")
@ViewScoped
public class AccSentidoJSF implements Serializable {

    @EJB
    private AccSentidoFacadeLocal accSentidoFacadeLocal;

    private AccSentido accSentido;

    private List<AccSentido> listAccSentido;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccSentidoJSF() {
    }

    public void guardar() {
        try {
            if (accSentido != null) {
                accSentido.setSentido(accSentido.getSentido().toUpperCase());
                accSentido.setCreado(new Date());
                accSentido.setModificado(new Date());
                accSentido.setEstadoReg(0);
                accSentido.setUsername(user.getUsername());
                accSentidoFacadeLocal.create(accSentido);
                MovilidadUtil.addSuccessMessage("Se a registrado el tipo de sentido correctamente");
                accSentido = new AccSentido();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar tipo de sentido");
        }
    }

    public void actualizar() {
        try {
            if (accSentido != null) {
                accSentido.setSentido(accSentido.getSentido().toUpperCase());
                accSentidoFacadeLocal.edit(accSentido);
                MovilidadUtil.addSuccessMessage("Se a actualizado sentido de accidente correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('sentido-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar sentido de accidente");
        }
    }

    public void prepareGuardar() {
        accSentido = new AccSentido();
    }

    public void reset() {
        accSentido = null;
    }

    public void onRowSelect(SelectEvent event) {
        accSentido = (AccSentido) event.getObject();
    }

    public AccSentido getAccSentido() {
        return accSentido;
    }

    public void setAccSentido(AccSentido accSentido) {
        this.accSentido = accSentido;
    }

    public List<AccSentido> getListAccSentido() {
        listAccSentido = accSentidoFacadeLocal.estadoReg();
        return listAccSentido;
    }

}
