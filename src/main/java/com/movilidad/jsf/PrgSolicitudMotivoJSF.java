/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgSolicitudMotivoFacadeLocal;
import com.movilidad.model.PrgSolicitudMotivo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "prgMotivoJSF")
@ViewScoped
public class PrgSolicitudMotivoJSF implements Serializable {

    @EJB
    private PrgSolicitudMotivoFacadeLocal prgMotivoFacadeLocal;

    private PrgSolicitudMotivo prgMotivo;

    private List<PrgSolicitudMotivo> listPrgMotivo;

    UserExtended user;

    public PrgSolicitudMotivoJSF() {
    }

    public void guardar() {
        try {
            if (prgMotivo != null) {
                user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                prgMotivo.setMotivo(prgMotivo.getMotivo().toUpperCase());
                prgMotivo.setCreado(new Date());
                prgMotivo.setModificado(new Date());
                prgMotivo.setEstadoReg(0);
                prgMotivo.setUsername(user.getUsername());
                prgMotivoFacadeLocal.create(prgMotivo);
                MovilidadUtil.addSuccessMessage("Se a registrado el motivo correctamente");
                prgMotivo = new PrgSolicitudMotivo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar el motivo");
        }
    }

    public void actualizar() {
        try {
            if (prgMotivo != null) {
                prgMotivo.setMotivo(prgMotivo.getMotivo().toUpperCase());
                prgMotivoFacadeLocal.edit(prgMotivo);
                MovilidadUtil.addSuccessMessage("Se a actualizado el motivo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('motivoWV').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar el motivo");
        }
    }

    public void prepareGuardar() {
        prgMotivo = new PrgSolicitudMotivo();
    }

    public void reset() {
        prgMotivo = null;
    }

    public void onPrgMotivoSelect(PrgSolicitudMotivo event) {
        prgMotivo = event;
    }

    public PrgSolicitudMotivo getPrgMotivo() {
        return prgMotivo;
    }

    public void setPrgMotivo(PrgSolicitudMotivo prgMotivo) {
        this.prgMotivo = prgMotivo;
    }

    public List<PrgSolicitudMotivo> getListPrgMotivo() {
        listPrgMotivo = prgMotivoFacadeLocal.findAllEstadoReg();
        return listPrgMotivo;
    }

    public void setListPrgMotivo(List<PrgSolicitudMotivo> listPrgMotivo) {
        this.listPrgMotivo = listPrgMotivo;
    }

}
