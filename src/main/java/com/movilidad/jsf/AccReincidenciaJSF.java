/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccReincidenciaFacadeLocal;
import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.model.AccReincidencia;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
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
 * Permite parametrizar la data relacionada con los objetos AccReincidencia
 * Principal tabla afectada acc_reincidencia
 *
 * @author soluciones-it
 */
@Named(value = "accReincidenciaJSF")
@ViewScoped
public class AccReincidenciaJSF implements Serializable {

    @EJB
    private AccReincidenciaFacadeLocal accReincidenciaFacadeLocal;

    private AccReincidencia accReincidencia;

    private List<AccReincidencia> listAccReincidencia;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Integer idNotificacionProcesos;

    public AccReincidenciaJSF() {
    }

    @PostConstruct
    public void init() {

    }

    /**
     * Permite persistir la data del objeto AccReincidencia en la base de datos
     */
    public void guardar() {
        try {
            if (accReincidencia != null) {
                if (!listAccReincidencia.isEmpty()) {
                    MovilidadUtil.addErrorMessage("Solo debe existir un registro para este modulo");
                    return;
                }
                accReincidencia.setIdNotificacionProceso(new NotificacionProcesos(idNotificacionProcesos));
                accReincidencia.setCreado(new Date());
                accReincidencia.setModificado(new Date());
                accReincidencia.setEstadoReg(0);
                accReincidencia.setUsername(user.getUsername());
                accReincidenciaFacadeLocal.create(accReincidencia);
                MovilidadUtil.addSuccessMessage("Se a registrado el acc reincidencia correctamente");
                reset();
                accReincidencia = new AccReincidencia();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar acc reincidencia");
        }
    }

    /**
     * Permite realizar un update del objeto AccReincidencia en la base de datos
     */
    public void actualizar() {
        try {
            if (accReincidencia != null) {
                accReincidencia.setIdNotificacionProceso(new NotificacionProcesos(idNotificacionProcesos));
                accReincidencia.setModificado(new Date());
                accReincidencia.setUsername(user.getUsername());
                accReincidenciaFacadeLocal.edit(accReincidencia);
                MovilidadUtil.addSuccessMessage("Se a actualizado el acc reincidencia correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('reincidencia-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar acc reincidencia");
        }
    }

    /**
     * Permite crear la instancia del objeto AccReincidencia
     */
    public void prepareGuardar() {
        accReincidencia = new AccReincidencia();

    }

    public void reset() {
        accReincidencia = null;
        idNotificacionProcesos = null;
    }

    /**
     * Permite capturar el objeto AccReincidencia seleccionado por el usuario
     *
     * @param event Evento que captura el objeto AccReincidencia
     */
    public void onRowSelect(SelectEvent event) {
        accReincidencia = (AccReincidencia) event.getObject();
        idNotificacionProcesos = accReincidencia.getIdNotificacionProceso() != null ? accReincidencia.getIdNotificacionProceso().getIdNotificacionProceso() : null;

    }

    public AccReincidencia getAccReincidencia() {
        return accReincidencia;
    }

    public void setAccReincidencia(AccReincidencia accReincidencia) {
        this.accReincidencia = accReincidencia;
    }

    public List<AccReincidencia> getListAccReincidencia() {
        listAccReincidencia = accReincidenciaFacadeLocal.findAllEstadoReg();
        return listAccReincidencia;
    }

    public void setListAccReincidencia(List<AccReincidencia> listAccReincidencia) {
        this.listAccReincidencia = listAccReincidencia;
    }

    public Integer getIdNotificacionProcesos() {
        return idNotificacionProcesos;
    }

    public void setIdNotificacionProcesos(Integer idNotificacionProcesos) {
        this.idNotificacionProcesos = idNotificacionProcesos;
    }

}
