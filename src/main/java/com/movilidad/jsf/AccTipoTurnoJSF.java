/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoTurnoFacadeLocal;
import com.movilidad.model.AccTipoTurno;
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
@Named(value = "accTipoTurnoJSF")
@ViewScoped
public class AccTipoTurnoJSF implements Serializable {

    @EJB
    private AccTipoTurnoFacadeLocal accTipoTurnoFacadeLocal;

    private AccTipoTurno accTipoTurno;

    private List<AccTipoTurno> listAccTipoTurno;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccTipoTurnoJSF() {
    }

    public void guardar() {
        try {
            if (accTipoTurno != null) {
                accTipoTurno.setTipoTurno(accTipoTurno.getTipoTurno().toUpperCase());
                accTipoTurno.setCreado(new Date());
                accTipoTurno.setModificado(new Date());
                accTipoTurno.setEstadoReg(0);
                accTipoTurno.setUsername(user.getUsername());
                accTipoTurnoFacadeLocal.create(accTipoTurno);
                MovilidadUtil.addSuccessMessage("Se a registrado el acc tipo turno correctamente");
                accTipoTurno = new AccTipoTurno();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar acc tipo turno");
        }
    }

    public void actualizar() {
        try {
            if (accTipoTurno != null) {
                accTipoTurno.setTipoTurno(accTipoTurno.getTipoTurno().toUpperCase());
                accTipoTurnoFacadeLocal.edit(accTipoTurno);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc tipo turno correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('tp-turno-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc tipo turno");
        }
    }

    public void prepareGuardar() {
        accTipoTurno = new AccTipoTurno();
    }

    public void reset() {
        accTipoTurno = null;
    }

    public void onRowSelect(SelectEvent event) {
        accTipoTurno = (AccTipoTurno) event.getObject();
    }

    public AccTipoTurno getAccTipoTurno() {
        return accTipoTurno;
    }

    public void setAccTipoTurno(AccTipoTurno accTipoTurno) {
        this.accTipoTurno = accTipoTurno;
    }

    public List<AccTipoTurno> getListAccTipoTurno() {
        listAccTipoTurno = accTipoTurnoFacadeLocal.estadoReg();
        return listAccTipoTurno;
    }

}
