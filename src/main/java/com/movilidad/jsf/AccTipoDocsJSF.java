/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoDocsFacadeLocal;
import com.movilidad.model.AccTipoDocs;
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
@Named(value = "accTipoDocsJSF")
@ViewScoped
public class AccTipoDocsJSF implements Serializable {

    @EJB
    private AccTipoDocsFacadeLocal accTipoDocsFacadeLocal;

    private AccTipoDocs accTipoDocs;

    private List<AccTipoDocs> listAccTipoDocs;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccTipoDocsJSF() {
    }

    @PostConstruct
    void init() {
        listAccTipoDocs = new ArrayList<>();
    }

    public void guardar() {
        try {
            if (accTipoDocs != null) {
                accTipoDocs.setTipoDocs(accTipoDocs.getTipoDocs().toUpperCase());
                accTipoDocs.setCreado(new Date());
                accTipoDocs.setModificado(new Date());
                accTipoDocs.setEstadoReg(0);
                accTipoDocs.setUsername(user.getUsername());
                accTipoDocsFacadeLocal.create(accTipoDocs);
                MovilidadUtil.addSuccessMessage("Se a registrado el tipo de documento correctamente");
                accTipoDocs = new AccTipoDocs();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al guardar tipo de documento");
        }
    }

    public void actualizar() {
        try {
            if (accTipoDocs != null) {
                accTipoDocs.setTipoDocs(accTipoDocs.getTipoDocs().toUpperCase());
                accTipoDocs.setModificado(new Date());
                accTipoDocsFacadeLocal.edit(accTipoDocs);
                MovilidadUtil.addSuccessMessage("Se a actualizado el tipo de documento correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('tp-docs-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar tipo de documento");
        }
    }

    public void prepareGuardar() {
        accTipoDocs = new AccTipoDocs();
    }

    public void reset() {
        accTipoDocs = null;
    }

    public void onRowSelect(SelectEvent event) {
        accTipoDocs = (AccTipoDocs) event.getObject();
    }

    public AccTipoDocs getAccTipoDocs() {
        return accTipoDocs;
    }

    public void setAccTipoDocs(AccTipoDocs accTipoDocs) {
        this.accTipoDocs = accTipoDocs;
    }

    public List<AccTipoDocs> getListAccTipoDocs() {
        listAccTipoDocs = accTipoDocsFacadeLocal.estadoReg();
        return listAccTipoDocs;
    }
}
