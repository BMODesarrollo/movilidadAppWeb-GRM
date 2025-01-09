/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccEtapaProcesoFacadeLocal;
import com.movilidad.model.AccCausa;
import com.movilidad.model.AccEtapaProceso;
import com.movilidad.model.AccTipoProceso;
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
 * Permite parametrizar la data relacionada con los objetos AccEtapaProceso Principal
 * tabla afectada acc_etapa_proceso
 *
 * @author HP
 */
@Named(value = "accEtapaProcesoJSF")
@ViewScoped
public class AccEtapaProcesoJSF implements Serializable {

    @EJB
    private AccEtapaProcesoFacadeLocal accEtapaProcesoFacadeLocal;

    private AccEtapaProceso accEtapaProceso;

    private List<AccEtapaProceso> listAccEtapaProceso;

    private int i_idAccTipoProceso = 0;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccEtapaProcesoJSF() {
    }

    /**
     * Permite persistir la data del objeto AccEtapaProceso en la base de datos
     */
    public void guardar() {
        try {
            if (accEtapaProceso != null) {
                accEtapaProceso.setIdAccTipoProceso(new AccTipoProceso(i_idAccTipoProceso));
                accEtapaProceso.setCausaRaiz(accEtapaProceso.getCausaRaiz().toUpperCase());
                accEtapaProceso.setCreado(new Date());
                accEtapaProceso.setModificado(new Date());
                accEtapaProceso.setEstadoReg(0);
                accEtapaProceso.setUsername(user.getUsername());
                accEtapaProcesoFacadeLocal.create(accEtapaProceso);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc EtapaProceso correctamente");
                accEtapaProceso = new AccEtapaProceso();
                i_idAccTipoProceso = 0;
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc EtapaProceso");
        }
    }

    /**
     * Permite realizar un update del objeto AccEtapaProceso en la base de datos
     */
    public void actualizar() {
        try {
            if (accEtapaProceso != null) {
                accEtapaProceso.setIdAccTipoProceso(new AccTipoProceso(i_idAccTipoProceso));
                accEtapaProceso.setCausaRaiz(accEtapaProceso.getCausaRaiz().toUpperCase());
                accEtapaProcesoFacadeLocal.edit(accEtapaProceso);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc EtapaProceso correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('et-proceso-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc EtapaProceso");
        }
    }

    /**
     * Permite crear la instancia del objeto AccEtapaProceso
     */
    public void prepareGuardar() {
        accEtapaProceso = new AccEtapaProceso();
    }

    public void prepareEditar() {
        if (accEtapaProceso != null) {
            i_idAccTipoProceso = accEtapaProceso.getIdAccTipoProceso().getIdAccTipoProceso();
        }
    }

    public void reset() {
        i_idAccTipoProceso = 0;
        accEtapaProceso = null;
    }

    /**
     *
     * @param event Objeto AccEtapaProceso seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accEtapaProceso = (AccEtapaProceso) event.getObject();
    }

    public AccEtapaProceso getAccEtapaProceso() {
        return accEtapaProceso;
    }

    public void setAccEtapaProceso(AccEtapaProceso accEtapaProceso) {
        this.accEtapaProceso = accEtapaProceso;
    }

    public List<AccEtapaProceso> getListAccEtapaProceso() {
        listAccEtapaProceso = accEtapaProcesoFacadeLocal.estadoReg();
        return listAccEtapaProceso;
    }

    public int getI_idAccTipoProceso() {
        return i_idAccTipoProceso;
    }

    public void setI_idAccTipoProceso(int i_idAccTipoProceso) {
        this.i_idAccTipoProceso = i_idAccTipoProceso;
    }

}
