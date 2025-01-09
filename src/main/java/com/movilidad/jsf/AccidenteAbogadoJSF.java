/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteAbogadoFacadeLocal;
import com.movilidad.model.AccidenteAbogado;
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
@Named(value = "accidenteAbogadoJSF")
@ViewScoped
public class AccidenteAbogadoJSF implements Serializable {

    @EJB
    private AccidenteAbogadoFacadeLocal accidenteAbogadoFacadeLocal;

    private AccidenteAbogado accidenteAbogado;

    private List<AccidenteAbogado> listAccidenteAbogado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccidenteAbogadoJSF() {
    }

    public void guardar() {
        try {
            if (accidenteAbogado != null) {
                accidenteAbogado.setCausaRaiz(accidenteAbogado.getCausaRaiz().toUpperCase());
                accidenteAbogado.setCreado(new Date());
                accidenteAbogado.setModificado(new Date());
                accidenteAbogado.setEstadoReg(0);
                accidenteAbogado.setUsername(user.getUsername());
                accidenteAbogadoFacadeLocal.create(accidenteAbogado);
                MovilidadUtil.addSuccessMessage("Se a registrado accidente abogado correctamente");
                accidenteAbogado = new AccidenteAbogado();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar accidente abogado");
        }
    }

    public void actualizar() {
        try {
            if (accidenteAbogado != null) {
                accidenteAbogado.setCausaRaiz(accidenteAbogado.getCausaRaiz().toUpperCase());
                accidenteAbogadoFacadeLocal.edit(accidenteAbogado);
                MovilidadUtil.addSuccessMessage("Se a actualizado accidente abogado correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('abogado-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar accidente abogado");
        }
    }

    public void prepareGuardar() {
        accidenteAbogado = new AccidenteAbogado();
    }

    public void reset() {
        accidenteAbogado = null;
    }

    public void onRowSelect(SelectEvent event) {
        accidenteAbogado = (AccidenteAbogado) event.getObject();
    }

    public AccidenteAbogado getAccidenteAbogado() {
        return accidenteAbogado;
    }

    public void setAccidenteAbogado(AccidenteAbogado accidenteAbogado) {
        this.accidenteAbogado = accidenteAbogado;
    }

    public List<AccidenteAbogado> getListAccidenteAbogado() {
        listAccidenteAbogado = accidenteAbogadoFacadeLocal.estadoReg();
        return listAccidenteAbogado;
    }

}
