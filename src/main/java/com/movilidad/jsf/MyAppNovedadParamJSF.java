/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.MyAppNovedadParamFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.model.MyAppNovedadParam;
import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
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
 * @author soluciones-it
 */
@Named(value = "myAppNovedadParamJSF")
@ViewScoped
public class MyAppNovedadParamJSF implements Serializable {

    @EJB
    private MyAppNovedadParamFacadeLocal appNovedadParamFacadeLocal;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetallesFacadeLocal;
    @EJB
    private NovedadTipoFacadeLocal novedadTipoFacadeLocal;

    private MyAppNovedadParam appNovedadParam;
    private List<MyAppNovedadParam> listAppNovedadParam;

    private Integer idNovTpDetalles;
    private Integer idNovTp;
    private List<NovedadTipo> listNovedadTipo;
    private List<NovedadTipoDetalles> listNovedadTipoDetalles;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public MyAppNovedadParamJSF() {
    }

    /**
     * Permite persistir la data del objeto MyAppNovedadParam en la base de
     * datos
     */
    public void guardar() {
        try {
            if (appNovedadParam != null) {
                if (idNovTpDetalles == null) {
                    MovilidadUtil.addErrorMessage("Detalle de novedad es requerido");
                    return;
                }
                appNovedadParam.setIdNovedadTipoDetalle(new NovedadTipoDetalles(idNovTpDetalles));
                appNovedadParam.setCreado(new Date());
                appNovedadParam.setModificado(new Date());
                appNovedadParam.setEstadoReg(0);
                appNovedadParam.setUsername(user.getUsername());
                appNovedadParamFacadeLocal.create(appNovedadParam);
                MovilidadUtil.addSuccessMessage("Se a registrado parametrización novedad correctamente");
                reset();
                appNovedadParam = new MyAppNovedadParam();
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar parametrización novedad");
        }
    }

    /**
     * Permite realizar un update del objeto MyAppNovedadParam en la base de
     * datos
     */
    public void actualizar() {
        try {
            if (appNovedadParam != null) {
                if (idNovTpDetalles == null) {
                    MovilidadUtil.addErrorMessage("Detalle de novedad es requerido");
                    return;
                }
                appNovedadParam.setIdNovedadTipoDetalle(new NovedadTipoDetalles(idNovTpDetalles));
                appNovedadParam.setModificado(new Date());
                appNovedadParam.setUsername(user.getUsername());
                appNovedadParamFacadeLocal.edit(appNovedadParam);
                MovilidadUtil.addSuccessMessage("Se a actualizado parametrización novedad correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('modalDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar parametrización novedad");
        }
    }

    private void cargarNovedadTipos() {
        listNovedadTipo = novedadTipoFacadeLocal.findAllEstadoReg();
    }

    public void cargarNovedadTipoDetalles() {
        listNovedadTipoDetalles = novedadTipoDetallesFacadeLocal.findByTipoNovedad(idNovTp);
    }

    /**
     * Permite crear la instancia del objeto MyAppNovedadParam
     */
    public void prepareGuardar() {
        appNovedadParam = new MyAppNovedadParam();
        cargarNovedadTipos();
    }

    public void reset() {
        appNovedadParam = null;
        idNovTp = null;
        idNovTpDetalles = null;
    }

    /**
     * Permite capturar el objeto MyAppNovedadParam seleccionado por el usuario
     *
     * @param event Evento que captura el objeto MyAppNovedadParam
     */
    public void onMyAppNovedadParam(MyAppNovedadParam event) {
        cargarNovedadTipos();
        appNovedadParam = event;
        idNovTp = event.getIdNovedadTipoDetalle().getIdNovedadTipo().getIdNovedadTipo();
        cargarNovedadTipoDetalles();
        idNovTpDetalles = event.getIdNovedadTipoDetalle().getIdNovedadTipoDetalle();
    }

    public MyAppNovedadParam getAppNovedadParam() {
        return appNovedadParam;
    }

    public void setAppNovedadParam(MyAppNovedadParam appNovedadParam) {
        this.appNovedadParam = appNovedadParam;
    }

    public List<MyAppNovedadParam> getListAppNovedadParam() {
        listAppNovedadParam = appNovedadParamFacadeLocal.findAllEstadoReg();
        return listAppNovedadParam;
    }

    public void setListAppNovedadParam(List<MyAppNovedadParam> listAppNovedadParam) {
        this.listAppNovedadParam = listAppNovedadParam;
    }

    public Integer getIdNovTpDetalles() {
        return idNovTpDetalles;
    }

    public void setIdNovTpDetalles(Integer idNovTpDetalles) {
        this.idNovTpDetalles = idNovTpDetalles;
    }

    public Integer getIdNovTp() {
        return idNovTp;
    }

    public void setIdNovTp(Integer idNovTp) {
        this.idNovTp = idNovTp;
    }

    public List<NovedadTipo> getListNovedadTipo() {
        return listNovedadTipo;
    }

    public void setListNovedadTipo(List<NovedadTipo> listNovedadTipo) {
        this.listNovedadTipo = listNovedadTipo;
    }

    public List<NovedadTipoDetalles> getListNovedadTipoDetalles() {
        return listNovedadTipoDetalles;
    }

    public void setListNovedadTipoDetalles(List<NovedadTipoDetalles> listNovedadTipoDetalles) {
        this.listNovedadTipoDetalles = listNovedadTipoDetalles;
    }

}
