/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoCarrilFacadeLocal;
import com.movilidad.model.AccTipoCarril;
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
@Named(value = "accTipoCarrilJSF")
@ViewScoped
public class AccTipoCarrilJSF implements Serializable {

    @EJB
    private AccTipoCarrilFacadeLocal accTipoCarrilFacadeLocal;

    private AccTipoCarril accTipoCarril;

    private List<AccTipoCarril> listAccTipoCarril;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccTipoCarrilJSF() {
    }

    @PostConstruct
    void init() {
        listAccTipoCarril = new ArrayList<>();
    }

    public void guardar() {
        try {
            if (accTipoCarril != null) {
                accTipoCarril.setTipoCarril(accTipoCarril.getTipoCarril().toUpperCase());
                accTipoCarril.setCreado(new Date());
                accTipoCarril.setModificado(new Date());
                accTipoCarril.setEstadoReg(0);
                accTipoCarril.setUsername(user.getUsername());
                accTipoCarrilFacadeLocal.create(accTipoCarril);
                MovilidadUtil.addSuccessMessage("Se a registrado el tipo de carril correctamente");
                accTipoCarril = new AccTipoCarril();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al guardar tipo de carril");
        }
    }

    public void actualizar() {
        try {
            if (accTipoCarril != null) {
                accTipoCarril.setTipoCarril(accTipoCarril.getTipoCarril().toUpperCase());
                accTipoCarrilFacadeLocal.edit(accTipoCarril);
                MovilidadUtil.addSuccessMessage("Se a actualizado el tipo de carril correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('tp-carril-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar tipo de carril");
        }
    }

    public void prepareGuardar() {
        accTipoCarril = new AccTipoCarril();
    }

    public void reset() {
        accTipoCarril = null;
    }

    public void onRowSelect(SelectEvent event) {
        accTipoCarril = (AccTipoCarril) event.getObject();
    }

    public AccTipoCarril getAccTipoCarril() {
        return accTipoCarril;
    }

    public void setAccTipoCarril(AccTipoCarril accTipoCarril) {
        this.accTipoCarril = accTipoCarril;
    }

    public List<AccTipoCarril> getListAccTipoCarril() {
        listAccTipoCarril = accTipoCarrilFacadeLocal.estadoReg();
        return listAccTipoCarril;
    }

}
