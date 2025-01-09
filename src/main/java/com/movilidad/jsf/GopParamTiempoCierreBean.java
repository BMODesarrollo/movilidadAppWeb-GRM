/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopParamTiempoCierreFacadeLocal;
import com.movilidad.model.GopParamTiempoCierre;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.ObjetoSigleton;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "ParamTiempoCierreBean")
@ViewScoped
public class GopParamTiempoCierreBean implements Serializable {

    @EJB
    private GopParamTiempoCierreFacadeLocal GopParamTiempoCierreFacadeLocal;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private GopParamTiempoCierre gopParamTiempoCierre;
    private List<GopParamTiempoCierre> listGopParamTiempoCierre;

    private String tiempo;
    private String descripcion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public GopParamTiempoCierreBean() {
    }

    @PostConstruct
    public void init() {
        consultar();
    }

    public void consultar() {
        listGopParamTiempoCierre = GopParamTiempoCierreFacadeLocal
                .findAllEstadoRegByUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void prepareEditar(GopParamTiempoCierre param) {
        gopParamTiempoCierre = param;
        tiempo = param.getTiempo();
        descripcion = param.getDescripcion();
        MovilidadUtil.openModal("wv_create_dlg");
    }

    public void prepareNuevo() {
        gopParamTiempoCierre = new GopParamTiempoCierre();
        MovilidadUtil.openModal("wv_create_dlg");
    }

    public void guardar() {
        if (gopParamTiempoCierre != null) {
            gopParamTiempoCierre.setIdGopUnidadFuncional(
                    new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
            gopParamTiempoCierre.setTiempo(tiempo);
            gopParamTiempoCierre.setDescripcion(descripcion);
            gopParamTiempoCierre.setCreado(MovilidadUtil.fechaCompletaHoy());
            gopParamTiempoCierre.setModificado(MovilidadUtil.fechaCompletaHoy());
            gopParamTiempoCierre.setEstadoReg(0);
            gopParamTiempoCierre.setUsername(user.getUsername());
            GopParamTiempoCierreFacadeLocal.create(gopParamTiempoCierre);
            setGopAlertaPresentacionSingleton(gopParamTiempoCierre);
            consultar();
            MovilidadUtil.addSuccessMessage("Operación finalizada con exito.");
            PrimeFaces.current().executeScript("PF('wv_create_dlg').hide()");
        }
    }

    public void editar() {
        if (gopParamTiempoCierre != null) {
            gopParamTiempoCierre.setTiempo(tiempo);
            gopParamTiempoCierre.setDescripcion(descripcion);
            gopParamTiempoCierre.setModificado(MovilidadUtil.fechaCompletaHoy());
            GopParamTiempoCierreFacadeLocal.edit(gopParamTiempoCierre);
            setGopAlertaPresentacionSingleton(gopParamTiempoCierre);
            MovilidadUtil.addSuccessMessage("Operación finalizada con exito.");
            consultar();
            PrimeFaces.current().executeScript("PF('wv_create_dlg').hide()");
        }
    }

    /**
     * Settea la unica instancia para la parametricación util para el panel
     * principal.
     *
     * @param paramTiempoCierre
     */
    public void setGopAlertaPresentacionSingleton(GopParamTiempoCierre paramTiempoCierre) {
        ObjetoSigleton.setGopParamTiempoCierre(paramTiempoCierre);
    }

    public List<GopParamTiempoCierre> getListGopParamTiempoCierre() {
        return listGopParamTiempoCierre;
    }

    public void setListGopParamTiempoCierre(List<GopParamTiempoCierre> listGopParamTiempoCierre) {
        this.listGopParamTiempoCierre = listGopParamTiempoCierre;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getDescripción() {
        return descripcion;
    }

    public void setDescripción(String descripcion) {
        this.descripcion = descripcion;
    }

    public GopParamTiempoCierre getGopParamTiempoCierre() {
        return gopParamTiempoCierre;
    }

    public void setGopParamTiempoCierre(GopParamTiempoCierre gopParamTiempoCierre) {
        this.gopParamTiempoCierre = gopParamTiempoCierre;
    }

}
