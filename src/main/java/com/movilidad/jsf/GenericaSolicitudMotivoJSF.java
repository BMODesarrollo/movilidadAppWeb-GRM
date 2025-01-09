/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaSolicitudMotivoFacadeLocal;
import com.movilidad.model.GenericaSolicitudMotivo;
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
@Named(value = "genericaMotivoJSF")
@ViewScoped
public class GenericaSolicitudMotivoJSF implements Serializable {

    @EJB
    private GenericaSolicitudMotivoFacadeLocal genericaMotivoFacadeLocal;

    private GenericaSolicitudMotivo genericaMotivo;

    private List<GenericaSolicitudMotivo> listGenericaMotivo;

    UserExtended user;

    public GenericaSolicitudMotivoJSF() {
    }

    public void guardar() {
        try {
            if (genericaMotivo != null) {
                user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                genericaMotivo.setMotivo(genericaMotivo.getMotivo().toUpperCase());
                genericaMotivo.setCreado(new Date());
                genericaMotivo.setModificado(new Date());
                genericaMotivo.setEstadoReg(0);
                genericaMotivo.setUsername(user.getUsername());
                genericaMotivoFacadeLocal.create(genericaMotivo);
                MovilidadUtil.addSuccessMessage("Se a registrado el motivo correctamente");
                genericaMotivo = new GenericaSolicitudMotivo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar el motivo");
        }
    }

    public void actualizar() {
        try {
            if (genericaMotivo != null) {
                genericaMotivo.setMotivo(genericaMotivo.getMotivo().toUpperCase());
                genericaMotivoFacadeLocal.edit(genericaMotivo);
                MovilidadUtil.addSuccessMessage("Se a actualizado el motivo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('motivoWV').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar el motivo");
        }
    }

    public void prepareGuardar() {
        genericaMotivo = new GenericaSolicitudMotivo();
    }

    public void reset() {
        genericaMotivo = null;
    }

    public void onGenericaMotivoSelect(GenericaSolicitudMotivo event) {
        genericaMotivo = event;
    }

    public GenericaSolicitudMotivo getGenericaMotivo() {
        return genericaMotivo;
    }

    public void setGenericaMotivo(GenericaSolicitudMotivo genericaMotivo) {
        this.genericaMotivo = genericaMotivo;
    }

    public List<GenericaSolicitudMotivo> getListGenericaMotivo() {
        listGenericaMotivo = genericaMotivoFacadeLocal.findAllEstadoReg();
        return listGenericaMotivo;
    }

    public void setListGenericaMotivo(List<GenericaSolicitudMotivo> listGenericaMotivo) {
        this.listGenericaMotivo = listGenericaMotivo;
    }

}
