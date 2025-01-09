/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaCalzadasFacadeLocal;
import com.movilidad.model.AccViaCalzadas;
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
@Named(value = "accViaCalzadasJSF")
@ViewScoped
public class AccViaCalzadasJSF implements Serializable {

    @EJB
    private AccViaCalzadasFacadeLocal accViaCalzadasFacadeLocal;

    private AccViaCalzadas accViaCalzadas;

    private List<AccViaCalzadas> listAccViaCalzadas;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaCalzadasJSF() {
    }

    public void guardar() {
        try {
            if (accViaCalzadas != null) {
                accViaCalzadas.setViaCalzadas(accViaCalzadas.getViaCalzadas().toUpperCase());
                accViaCalzadas.setCreado(new Date());
                accViaCalzadas.setModificado(new Date());
                accViaCalzadas.setEstadoReg(0);
                accViaCalzadas.setUsername(user.getUsername());
                accViaCalzadasFacadeLocal.create(accViaCalzadas);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Calzadas correctamente");
                accViaCalzadas = new AccViaCalzadas();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Calzadas");
        }
    }

    public void actualizar() {
        try {
            if (accViaCalzadas != null) {
                accViaCalzadas.setViaCalzadas(accViaCalzadas.getViaCalzadas().toUpperCase());
                accViaCalzadasFacadeLocal.edit(accViaCalzadas);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Calzadas correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-calzadas-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Calzadas");
        }
    }

    public void prepareGuardar() {
        accViaCalzadas = new AccViaCalzadas();
    }

    public void reset() {
        accViaCalzadas = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaCalzadas = (AccViaCalzadas) event.getObject();
    }

    public AccViaCalzadas getAccViaCalzadas() {
        return accViaCalzadas;
    }

    public void setAccViaCalzadas(AccViaCalzadas accViaCalzadas) {
        this.accViaCalzadas = accViaCalzadas;
    }

    public List<AccViaCalzadas> getListAccViaCalzadas() {
        listAccViaCalzadas = accViaCalzadasFacadeLocal.estadoReg();
        return listAccViaCalzadas;
    }

}
