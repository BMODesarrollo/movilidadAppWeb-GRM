/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.DanoFlotaParamSeveridadFacadeLocal;
import com.movilidad.model.DanoFlotaParamSeveridad;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Julián Arévalo
 */
@Named(value = "danoFlotaParamSeveridadJSF")
@ViewScoped
public class DanoFlotaParamSeveridadJSF implements Serializable {

    @EJB
    private DanoFlotaParamSeveridadFacadeLocal danoFlotaParamSeveridadFacadeLocal;

    private List<DanoFlotaParamSeveridad> listParamSeveridad;

    private DanoFlotaParamSeveridad danoFlotaSeveridad;

    private Integer IdParamSeveridad;

    private final PrimeFaces current = PrimeFaces.current();
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public DanoFlotaParamSeveridadJSF() {
    }

    @PostConstruct
    public void init() {
        IdParamSeveridad = 0;
        listParamSeveridad = danoFlotaParamSeveridadFacadeLocal.getAllActivo();
        danoFlotaSeveridad = new DanoFlotaParamSeveridad();
    }

    public void activarDesactivarSeveridad(DanoFlotaParamSeveridad obj, int opc) {
        if (opc == 0) {
            obj.setEstadoReg(1);
        } else {
            obj.setEstadoReg(0);
        }
        danoFlotaParamSeveridadFacadeLocal.edit(obj);
        MovilidadUtil.addSuccessMessage("Éxito.");        
    }

    public void guardar() {
        guardarTransactional();
        init();
    }

    @Transactional
    public void guardarTransactional() {

        danoFlotaSeveridad.setUsername(user.getUsername());
        danoFlotaSeveridad.setEstadoReg(0);
        danoFlotaSeveridad.setCreado(MovilidadUtil.fechaCompletaHoy());
        danoFlotaParamSeveridadFacadeLocal.create(danoFlotaSeveridad);
        MovilidadUtil.addSuccessMessage("Se guardó el registro correctamente.");
        MovilidadUtil.hideModal("wv_create_dlg");
        limpiarForm();
    }

    @Transactional
    public void editarTransactional(DanoFlotaParamSeveridad obj) {
        danoFlotaParamSeveridadFacadeLocal.edit(danoFlotaSeveridad);
        MovilidadUtil.addSuccessMessage("Se actualizó el registro correctamente.");
        MovilidadUtil.hideModal("wv_create_dlg");
        limpiarForm();
        init();
    }

    public void preEdit(DanoFlotaParamSeveridad obj) {
        IdParamSeveridad = obj.getIdParamSeveridad();
        danoFlotaSeveridad = obj;
    }

    public void limpiarForm() {
        danoFlotaSeveridad = new DanoFlotaParamSeveridad();
        IdParamSeveridad = 0;
    }

    public DanoFlotaParamSeveridadFacadeLocal getDanoFlotaParamSeveridadFacadeLocal() {
        return danoFlotaParamSeveridadFacadeLocal;
    }

    public void setDanoFlotaParamSeveridadFacadeLocal(DanoFlotaParamSeveridadFacadeLocal danoFlotaParamSeveridadFacadeLocal) {
        this.danoFlotaParamSeveridadFacadeLocal = danoFlotaParamSeveridadFacadeLocal;
    }

    public List<DanoFlotaParamSeveridad> getListParamSeveridad() {
        return listParamSeveridad;
    }

    public void setListParamSeveridad(List<DanoFlotaParamSeveridad> listParamSeveridad) {
        this.listParamSeveridad = listParamSeveridad;
    }

    public DanoFlotaParamSeveridad getDanoFlotaSeveridad() {
        return danoFlotaSeveridad;
    }

    public void setDanoFlotaSeveridad(DanoFlotaParamSeveridad danoFlotaSeveridad) {
        this.danoFlotaSeveridad = danoFlotaSeveridad;
    }

    public Integer getIdParamSeveridad() {
        return IdParamSeveridad;
    }

    public void setIdParamSeveridad(Integer IdParamSeveridad) {
        this.IdParamSeveridad = IdParamSeveridad;
    }

}
