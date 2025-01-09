/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaSemaforoFacadeLocal;
import com.movilidad.model.AccViaSemaforo;
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
@Named(value = "accViaSemaforoJSF")
@ViewScoped
public class AccViaSemaforoJSF implements Serializable {

    @EJB
    private AccViaSemaforoFacadeLocal accViaSemaforoFacadeLocal;

    private AccViaSemaforo accViaSemaforo;

    private List<AccViaSemaforo> listAccViaSemaforo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaSemaforoJSF() {
    }

    public void guardar() {
        try {
            if (accViaSemaforo != null) {
                accViaSemaforo.setViaSemaforo(accViaSemaforo.getViaSemaforo().toUpperCase());
                accViaSemaforo.setCreado(new Date());
                accViaSemaforo.setModificado(new Date());
                accViaSemaforo.setEstadoReg(0);
                accViaSemaforo.setUsername(user.getUsername());
                accViaSemaforoFacadeLocal.create(accViaSemaforo);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Semáforo correctamente");
                accViaSemaforo = new AccViaSemaforo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Semáforo");
        }
    }

    public void actualizar() {
        try {
            if (accViaSemaforo != null) {
                accViaSemaforo.setViaSemaforo(accViaSemaforo.getViaSemaforo().toUpperCase());
                accViaSemaforoFacadeLocal.edit(accViaSemaforo);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Semáforo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-semaforo-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Semáforo");
        }
    }

    public void prepareGuardar() {
        accViaSemaforo = new AccViaSemaforo();
    }

    public void reset() {
        accViaSemaforo = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaSemaforo = (AccViaSemaforo) event.getObject();
    }

    public AccViaSemaforo getAccViaSemaforo() {
        return accViaSemaforo;
    }

    public void setAccViaSemaforo(AccViaSemaforo accViaSemaforo) {
        this.accViaSemaforo = accViaSemaforo;
    }

    public List<AccViaSemaforo> getListAccViaSemaforo() {
        listAccViaSemaforo = accViaSemaforoFacadeLocal.estadoReg();
        return listAccViaSemaforo;
    }

}
