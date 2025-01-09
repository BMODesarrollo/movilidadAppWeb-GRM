/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopAlertaPresentacionVehiculoFacadeLocal;
import com.movilidad.model.GopAlertaPresentacionVehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.ObjetoSigleton;
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
 * Permite parametrizar la data relacionada con los objetos
 * GopAlertaPresentacionVehiculo Principal tabla afectada gop_alerta_presentacion
 *
 * @author soluciones-it
 */
@Named(value = "gopAlertaPresentacionVehiculoJSF")
@ViewScoped
public class GopAlertaPresentacionVehiculoJSF implements Serializable {

    @EJB
    private GopAlertaPresentacionVehiculoFacadeLocal gopAlertaPresentacionVehiculoFacadeLocal;

    private GopAlertaPresentacionVehiculo gopAlertaPresentacionVehiculo;

    private List<GopAlertaPresentacionVehiculo> listGopAlertaPresentacionVehiculo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of GopAlertaPresentacionVehiculoJSF
     */
    public GopAlertaPresentacionVehiculoJSF() {
    }

    /**
     * Permite persistir la data del objeto GopAlertaPresentacionVehiculo en la base de
     * datos
     */
    public void guardar() {
        try {
            if (gopAlertaPresentacionVehiculo != null) {
                if (!listGopAlertaPresentacionVehiculo.isEmpty()) {
                    MovilidadUtil.addErrorMessage("Ya se encuentra un registro parametrizado");
                    return;
                }
                gopAlertaPresentacionVehiculo.setCreado(new Date());
                gopAlertaPresentacionVehiculo.setModificado(new Date());
                gopAlertaPresentacionVehiculo.setEstadoReg(0);
                gopAlertaPresentacionVehiculo.setUsername(user.getUsername());
                gopAlertaPresentacionVehiculoFacadeLocal.create(gopAlertaPresentacionVehiculo);
                setGopAlertaPresentacionVehiculoSingleton(gopAlertaPresentacionVehiculo);
                MovilidadUtil.addSuccessMessage("Se a registrado alerta tiempo presentación correctamente");
                gopAlertaPresentacionVehiculo = new GopAlertaPresentacionVehiculo();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar alerta tiempo presentación");
        }
    }

    /**
     * Permite realizar un update del objeto GopAlertaPresentacionVehiculo en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (gopAlertaPresentacionVehiculo != null) {
                gopAlertaPresentacionVehiculo.setModificado(new Date());
                gopAlertaPresentacionVehiculo.setUsername(user.getUsername());
                gopAlertaPresentacionVehiculoFacadeLocal.edit(gopAlertaPresentacionVehiculo);
                setGopAlertaPresentacionVehiculoSingleton(gopAlertaPresentacionVehiculo);
                MovilidadUtil.addSuccessMessage("Se a actualizado alerta tiempo presentación correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('gopAlertaPresentacionVehiculo-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar alerta tiempo presentación");
        }
    }

    /**
     * Settea la unica instancia para la parametricación util para el panel
     * principal.
     *
     * @param alertaPresentacionVehiculo
     */
    public void setGopAlertaPresentacionVehiculoSingleton(GopAlertaPresentacionVehiculo alertaPresentacionVehiculo) {
        ObjetoSigleton.setGopAlertaPresentacionVehiculo(alertaPresentacionVehiculo);
    }

    /**
     * Permite crear la instancia del objeto GopAlertaPresentacionVehiculo
     */
    public void prepareGuardar() {
        gopAlertaPresentacionVehiculo = new GopAlertaPresentacionVehiculo();
    }

    public void reset() {
        gopAlertaPresentacionVehiculo = null;
    }

    /**
     * Permite capturar el objeto GopAlertaPresentacionVehiculo seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto GopAlertaPresentacionVehiculo
     */
    public void onRowSelect(SelectEvent event) {
        gopAlertaPresentacionVehiculo = (GopAlertaPresentacionVehiculo) event.getObject();
    }

    public GopAlertaPresentacionVehiculoFacadeLocal getGopAlertaPresentacionVehiculoFacadeLocal() {
        return gopAlertaPresentacionVehiculoFacadeLocal;
    }

    public void setGopAlertaPresentacionVehiculoFacadeLocal(GopAlertaPresentacionVehiculoFacadeLocal gopAlertaPresentacionVehiculoFacadeLocal) {
        this.gopAlertaPresentacionVehiculoFacadeLocal = gopAlertaPresentacionVehiculoFacadeLocal;
    }

    public GopAlertaPresentacionVehiculo getGopAlertaPresentacionVehiculo() {
        return gopAlertaPresentacionVehiculo;
    }

    public void setGopAlertaPresentacionVehiculo(GopAlertaPresentacionVehiculo gopAlertaPresentacionVehiculo) {
        this.gopAlertaPresentacionVehiculo = gopAlertaPresentacionVehiculo;
    }

    public List<GopAlertaPresentacionVehiculo> getListGopAlertaPresentacionVehiculo() {
        listGopAlertaPresentacionVehiculo = gopAlertaPresentacionVehiculoFacadeLocal.findAllEstadoReg();
        return listGopAlertaPresentacionVehiculo;
    }

    public void setListGopAlertaPresentacionVehiculo(List<GopAlertaPresentacionVehiculo> listGopAlertaPresentacionVehiculo) {
        this.listGopAlertaPresentacionVehiculo = listGopAlertaPresentacionVehiculo;
    }
    
    

}
