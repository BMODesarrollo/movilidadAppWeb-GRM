/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.MttoElementosAbordoFacadeLocal;
import com.movilidad.model.MttoElementosAbordo;
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
 * Permite parametrizar la data relacionada con los objetos MttoElementosAbordo
 * Principal tabla afectada mtto_elementos_abordo
 *
 * @author soluciones-it
 */
@Named(value = "mttoElementosAbordoJSF")
@ViewScoped
public class MttoElementosAbordoJSF implements Serializable {

    @EJB
    private MttoElementosAbordoFacadeLocal mttoElementosAbordoFacadeLocal;

    private MttoElementosAbordo mttoElementosAbordo;

    private List<MttoElementosAbordo> listMttoElementosAbordo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Integer idVehiculoTipo;

    /**
     * Creates a new instance of MttoElementosAbordo
     */
    public MttoElementosAbordoJSF() {
    }

    /**
     * Permite persistir la data del objeto MttoElementosAbordo en la base de
     * datos
     */
    public void guardar() {
        try {
            if (mttoElementosAbordo != null) {
                mttoElementosAbordo.setIdVehiculoTipo(new VehiculoTipo(idVehiculoTipo));
                mttoElementosAbordo.setEstadoReg(0);
                mttoElementosAbordo.setUsername(user.getUsername());
                mttoElementosAbordoFacadeLocal.create(mttoElementosAbordo);
                MovilidadUtil.addSuccessMessage("Se a registrado el elemento a bordo correctamente");
                mttoElementosAbordo = new MttoElementosAbordo();
                idVehiculoTipo = null;
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar elemento a bordo ");
        }
    }

    /**
     * Permite realizar un update del objeto MttoElementosAbordo en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (mttoElementosAbordo != null) {
                mttoElementosAbordo.setIdVehiculoTipo(new VehiculoTipo(idVehiculoTipo));
                mttoElementosAbordo.setModificado(new Date());
                mttoElementosAbordo.setUsername(user.getUsername());
                mttoElementosAbordoFacadeLocal.edit(mttoElementosAbordo);
                MovilidadUtil.addSuccessMessage("Se a actualizado el elemento a bordo correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('elemento-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar elemento a bordo");
        }
    }

    /**
     * Permite crear la instancia del objeto MttoElementosAbordo
     */
    public void prepareGuardar() {
        mttoElementosAbordo = new MttoElementosAbordo();
    }

    public void reset() {
        mttoElementosAbordo = null;
        idVehiculoTipo = null;
    }

    /**
     * Permite capturar el objeto MttoElementosAbordo seleccionado por el
     * usuario
     *
     * @param event Evento que captura el objeto MttoElementosAbordo
     */
    public void onRowSelect(SelectEvent event) {
        mttoElementosAbordo = (MttoElementosAbordo) event.getObject();
        idVehiculoTipo = mttoElementosAbordo.getIdVehiculoTipo() != null ? mttoElementosAbordo.getIdVehiculoTipo().getIdVehiculoTipo() : null;
    }

    public MttoElementosAbordo getMttoElementosAbordo() {
        return mttoElementosAbordo;
    }

    public void setMttoElementosAbordo(MttoElementosAbordo mttoElementosAbordo) {
        this.mttoElementosAbordo = mttoElementosAbordo;
    }

    public List<MttoElementosAbordo> getListMttoElementosAbordo() {
        listMttoElementosAbordo = mttoElementosAbordoFacadeLocal.findAllEstadoReg();
        return listMttoElementosAbordo;
    }

    public void setListMttoElementosAbordo(List<MttoElementosAbordo> listMttoElementosAbordo) {
        this.listMttoElementosAbordo = listMttoElementosAbordo;
    }

    public Integer getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(Integer idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

}
