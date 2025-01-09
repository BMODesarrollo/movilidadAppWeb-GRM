/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.MttoBateriaCriticaFacadeLocal;
import com.movilidad.model.MttoBateriaCritica;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.VehiculoTipo;
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
 * Permite parametrizar la data relacionada con los objetos MttoBateriaCritica
 * Principal tabla afectada mtto_bateria_critica
 *
 * @author soluciones-it
 */
@Named(value = "mttoBateriaCriticaJSF")
@ViewScoped
public class MttoBateriaCriticaJSF implements Serializable {

    @EJB
    private MttoBateriaCriticaFacadeLocal mttoBateriaCriticaFacadeLocal;

    private MttoBateriaCritica mttoBateriaCritica;

    private List<MttoBateriaCritica> listMttoBateriaCritica;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Integer idVehiculoTipo;
    private Integer idNotificacionProcesos;

    /**
     * Creates a new instance of MttoBateriaCriticaJSF
     */
    public MttoBateriaCriticaJSF() {
    }

    /**
     * Permite persistir la data del objeto MttoBateriaCritica en la base de
     * datos
     */
    public void guardar() {
        try {
            if (mttoBateriaCritica != null) {
                MttoBateriaCritica mbc = mttoBateriaCriticaFacadeLocal.findByCargaAndIdTipoVehiculo(mttoBateriaCritica.getCarga(), idVehiculoTipo, 0);
                if (mbc != null) {
                    MovilidadUtil.addErrorMessage("Ya se encuentra registrada una carga de " + mbc.getCarga() + "% para el tipo de vehículo "
                            + mbc.getIdVehiculoTipo().getNombreTipoVehiculo());
                    return;
                }
                mttoBateriaCritica.setIdNotificacionProcesos(new NotificacionProcesos(idNotificacionProcesos));
                mttoBateriaCritica.setIdVehiculoTipo(new VehiculoTipo(idVehiculoTipo));
                mttoBateriaCritica.setEstadoReg(0);
                mttoBateriaCritica.setUsername(user.getUsername());
                mttoBateriaCriticaFacadeLocal.create(mttoBateriaCritica);
                MovilidadUtil.addSuccessMessage("Se a registrado el bateria crítica correctamente");
                reset();
                mttoBateriaCritica = new MttoBateriaCritica();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar bateria crítica ");
        }
    }

    /**
     * Permite realizar un update del objeto MttoBateriaCritica en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (mttoBateriaCritica != null) {
                MttoBateriaCritica mbc = mttoBateriaCriticaFacadeLocal.findByCargaAndIdTipoVehiculo(mttoBateriaCritica.getCarga(), idVehiculoTipo, mttoBateriaCritica.getIdMttoBateriaCritica());
                if (mbc != null) {
                    MovilidadUtil.addErrorMessage("Ya se encuentra registrada una carga de " + mbc.getCarga() + "% para el tipo de vehículo "
                            + mbc.getIdVehiculoTipo().getNombreTipoVehiculo());
                    return;
                }
                mttoBateriaCritica.setIdNotificacionProcesos(new NotificacionProcesos(idNotificacionProcesos));
                mttoBateriaCritica.setIdVehiculoTipo(new VehiculoTipo(idVehiculoTipo));
                mttoBateriaCritica.setModificado(new Date());
                mttoBateriaCritica.setUsername(user.getUsername());
                mttoBateriaCriticaFacadeLocal.edit(mttoBateriaCritica);
                MovilidadUtil.addSuccessMessage("Se a actualizado el bateria crítica correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('bateria-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar batería crítica");
        }
    }

    /**
     * Permite crear la instancia del objeto MttoBateriaCritica
     */
    public void prepareGuardar() {
        mttoBateriaCritica = new MttoBateriaCritica();
    }

    public void reset() {
        mttoBateriaCritica = null;
        idVehiculoTipo = null;
        idNotificacionProcesos = null;
    }

    /**
     * Permite capturar el objeto MttoBateriaCritica seleccionado por el usuario
     *
     * @param event Evento que captura el objeto MttoBateriaCritica
     */
    public void onRowSelect(SelectEvent event) {
        mttoBateriaCritica = (MttoBateriaCritica) event.getObject();
        idVehiculoTipo = mttoBateriaCritica.getIdVehiculoTipo() != null ? mttoBateriaCritica.getIdVehiculoTipo().getIdVehiculoTipo() : null;
        idNotificacionProcesos = mttoBateriaCritica.getIdNotificacionProcesos() != null ? mttoBateriaCritica.getIdNotificacionProcesos().getIdNotificacionProceso() : null;
    }

    public MttoBateriaCritica getMttoBateriaCritica() {
        return mttoBateriaCritica;
    }

    public void setMttoBateriaCritica(MttoBateriaCritica mttoBateriaCritica) {
        this.mttoBateriaCritica = mttoBateriaCritica;
    }

    public List<MttoBateriaCritica> getListMttoBateriaCritica() {
        listMttoBateriaCritica = mttoBateriaCriticaFacadeLocal.findAllEstadoReg();
        return listMttoBateriaCritica;
    }

    public void setListMttoBateriaCritica(List<MttoBateriaCritica> listMttoBateriaCritica) {
        this.listMttoBateriaCritica = listMttoBateriaCritica;
    }

    public Integer getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(Integer idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    public Integer getIdNotificacionProcesos() {
        return idNotificacionProcesos;
    }

    public void setIdNotificacionProcesos(Integer idNotificacionProcesos) {
        this.idNotificacionProcesos = idNotificacionProcesos;
    }

}
