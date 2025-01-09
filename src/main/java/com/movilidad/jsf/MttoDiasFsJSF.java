/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.MttoDiasFsFacadeLocal;
import com.movilidad.model.MttoDiasFs;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite parametrizar la data relacionada con los objetos MttoDiasFs Principal
 * tabla afectada mtto_dias_fs
 *
 * @author soluciones-it
 */
@Named(value = "mttoDiasFsJSF")
@ViewScoped
public class MttoDiasFsJSF implements Serializable {

    @EJB
    private MttoDiasFsFacadeLocal mttoDiasFsFacadeLocal;

    private MttoDiasFs mttoDiasFs;

    private List<MttoDiasFs> listMttoDiasFs;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Integer idNotificacionProcesos;

    /**
     * Creates a new instance of MttoDiasFsJSF
     */
    public MttoDiasFsJSF() {
    }

    /**
     * Permite persistir la data del objeto MttoDiasFs en la base de datos
     */
    public void guardar() {
        try {
            if (mttoDiasFs != null) {
                Optional<MttoDiasFs> opMttoFs = listMttoDiasFs
                        .stream()
                        .filter(x -> x.getDias().equals(mttoDiasFs.getDias()))
                        .findFirst();
                if (opMttoFs.isPresent()) {
                    MovilidadUtil.addErrorMessage("Ya se encuentra registrado " + opMttoFs.get().getDias() + " días en la parametrización");
                    return;
                }
                mttoDiasFs.setIdNotificacionProceso(new NotificacionProcesos(idNotificacionProcesos));
                mttoDiasFs.setCreado(new Date());
                mttoDiasFs.setModificado(new Date());
                mttoDiasFs.setEstadoReg(0);
                mttoDiasFs.setUsername(user.getUsername());
                mttoDiasFsFacadeLocal.create(mttoDiasFs);
                MovilidadUtil.addSuccessMessage("Se a registrado Días Fuera Servicio correctamente");
                mttoDiasFs = new MttoDiasFs();
                idNotificacionProcesos = null;
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Días Fuera Servicio");
        }
    }

    /**
     * Permite realizar un update del objeto MttoDiasFs en la base de datos
     */
    public void actualizar() {
        try {
            if (mttoDiasFs != null) {
                Optional<MttoDiasFs> opMttoFs = listMttoDiasFs
                        .stream()
                        .filter(x -> x.getDias().equals(mttoDiasFs.getDias()) && !x.getIdMttoDiasFs().equals(mttoDiasFs.getIdMttoDiasFs()))
                        .findFirst();
                if (opMttoFs.isPresent()) {
                    MovilidadUtil.addErrorMessage("Ya se encuentra registrado " + opMttoFs.get().getDias() + " días en la parametrización");
                    return;
                }
                mttoDiasFs.setIdNotificacionProceso(new NotificacionProcesos(idNotificacionProcesos));
                mttoDiasFs.setModificado(new Date());
                mttoDiasFs.setUsername(user.getUsername());
                mttoDiasFsFacadeLocal.edit(mttoDiasFs);
                MovilidadUtil.addSuccessMessage("Se a actualizado Días Fuera Servicio correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('mttoDiasFs-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Días Fuera Servicio");
        }
    }

    /**
     * Permite crear la instancia del objeto MttoDiasFs
     */
    public void prepareGuardar() {
        mttoDiasFs = new MttoDiasFs();
    }

    public void reset() {
        mttoDiasFs = null;
        idNotificacionProcesos = null;
    }

    /**
     * Permite capturar el objeto MttoDiasFs seleccionado por el usuario
     *
     * @param event Evento que captura el objeto MttoDiasFs
     */
    public void onRowSelect(SelectEvent event) {
        mttoDiasFs = (MttoDiasFs) event.getObject();
        idNotificacionProcesos = mttoDiasFs.getIdNotificacionProceso() != null ? mttoDiasFs.getIdNotificacionProceso().getIdNotificacionProceso() : null;
    }

    public MttoDiasFs getMttoDiasFs() {
        return mttoDiasFs;
    }

    public void setMttoDiasFs(MttoDiasFs mttoDiasFs) {
        this.mttoDiasFs = mttoDiasFs;
    }

    public List<MttoDiasFs> getListMttoDiasFs() {
        listMttoDiasFs = mttoDiasFsFacadeLocal.findAllEstadoReg();
        return listMttoDiasFs;
    }

    public void setListMttoDiasFs(List<MttoDiasFs> listMttoDiasFs) {
        this.listMttoDiasFs = listMttoDiasFs;
    }

    public Integer getIdNotificacionProcesos() {
        return idNotificacionProcesos;
    }

    public void setIdNotificacionProcesos(Integer idNotificacionProcesos) {
        this.idNotificacionProcesos = idNotificacionProcesos;
    }

}
