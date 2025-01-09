/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaGeometricaFacadeLocal;
import com.movilidad.model.AccViaGeometrica;
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
@Named(value = "accViaGeometricaJSF")
@ViewScoped
public class AccViaGeometricaJSF implements Serializable {

    @EJB
    private AccViaGeometricaFacadeLocal accViaGeometricaFacadeLocal;

    private AccViaGeometrica accViaGeometrica;

    private List<AccViaGeometrica> listAccViaGeometrica;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccViaGeometricaJSF() {
    }

    public void guardar() {
        try {
            if (accViaGeometrica != null) {
                accViaGeometrica.setViaGeometrica(accViaGeometrica.getViaGeometrica().toUpperCase());
                accViaGeometrica.setCreado(new Date());
                accViaGeometrica.setModificado(new Date());
                accViaGeometrica.setEstadoReg(0);
                accViaGeometrica.setUsername(user.getUsername());
                accViaGeometricaFacadeLocal.create(accViaGeometrica);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc Vía Geométrica correctamente");
                accViaGeometrica = new AccViaGeometrica();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc Vía Geométrica");
        }
    }

    public void actualizar() {
        try {
            if (accViaGeometrica != null) {
                accViaGeometrica.setViaGeometrica(accViaGeometrica.getViaGeometrica().toUpperCase());
                accViaGeometricaFacadeLocal.edit(accViaGeometrica);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc Vía Geométrica correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('v-geometrica-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc Vía Geométrica");
        }
    }

    public void prepareGuardar() {
        accViaGeometrica = new AccViaGeometrica();
    }

    public void reset() {
        accViaGeometrica = null;
    }

    public void onRowSelect(SelectEvent event) {
        accViaGeometrica = (AccViaGeometrica) event.getObject();
    }

    public AccViaGeometrica getAccViaGeometrica() {
        return accViaGeometrica;
    }

    public void setAccViaGeometrica(AccViaGeometrica accViaGeometrica) {
        this.accViaGeometrica = accViaGeometrica;
    }

    public List<AccViaGeometrica> getListAccViaGeometrica() {
        listAccViaGeometrica = accViaGeometricaFacadeLocal.estadoReg();
        return listAccViaGeometrica;
    }

}
