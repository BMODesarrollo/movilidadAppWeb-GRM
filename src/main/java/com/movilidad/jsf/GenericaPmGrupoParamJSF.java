package com.movilidad.jsf;

import com.movilidad.ejb.ParamAreaCargoFacadeLocal;
import com.movilidad.ejb.GenericaPmGrupoParamFacadeLocal;
import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.ParamArea;
import com.movilidad.model.GenericaPmGrupoParam;
import com.movilidad.model.ParamAreaCargo;
import com.movilidad.model.Users;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "genericaPmGrupoParamJSF")
@ViewScoped
public class GenericaPmGrupoParamJSF implements Serializable {

    @EJB
    private GenericaPmGrupoParamFacadeLocal genericaPmGrupoParamEjb;
    @EJB
    private ParamAreaFacadeLocal paramAreaFacadeLocal;
    @EJB
    private ParamAreaCargoFacadeLocal paramAreaCargoEjb;
    @EJB
    private UsersFacadeLocal usersFacadeLocal;

    private GenericaPmGrupoParam genericaPmGrupoParam;
    private ParamArea paramArea;
    private List<GenericaPmGrupoParam> listGenericaPmGrupoParam;
    private List<ParamAreaCargo> listEmpleadoCargo;
    private List<ParamArea> listParamArea;

    private Integer i_idEmpleadoCargo;
    private String c_usernameLogin;

    private HashMap<Integer, ParamArea> mapCargo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public GenericaPmGrupoParamJSF() {
    }

    @PostConstruct
    public void init() {
        genericaPmGrupoParam = null;
        listGenericaPmGrupoParam = new ArrayList<>();
        mapCargo = new HashMap<>();
        i_idEmpleadoCargo = null;
        listParamArea = paramAreaFacadeLocal.findAllEstadoReg();
        Users resp = usersFacadeLocal.findUserForUsername(user.getUsername());
        if (resp != null) {
            if (resp.getParamAreaUsrList() != null && !resp.getParamAreaUsrList().isEmpty()) {
                paramArea = resp.getParamAreaUsrList().get(0).getIdParamArea();
            }
        }
        if (paramArea != null) {
            listGenericaPmGrupoParam = genericaPmGrupoParamEjb.findAll(paramArea.getIdParamArea());
            listEmpleadoCargo = paramAreaCargoEjb.findAllArea(paramArea.getIdParamArea());
        }
        c_usernameLogin = user.getUsername();
    }

    public void guardar() {
        try {
            cargarMap();

            if (genericaPmGrupoParamEjb.verificarRegistro(paramArea.getIdParamArea()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro correspondiente al área de " + paramArea.getArea().toUpperCase());
                return;
            }

            if (genericaPmGrupoParam != null) {
                if (i_idEmpleadoCargo == null) {
                    MovilidadUtil.addErrorMessage("Cargo es requerido");
                    return;
                }
                if (mapCargo.containsKey(i_idEmpleadoCargo)) {
                    MovilidadUtil.addErrorMessage("Cargo seleccionado tiene relación existente con " + mapCargo.get(i_idEmpleadoCargo).getArea());
                    return;
                }
                cargarObjetos();
                genericaPmGrupoParam.setUsername(user.getUsername());
                genericaPmGrupoParam.setCreado(new Date());
                genericaPmGrupoParam.setModificado(new Date());
                genericaPmGrupoParam.setEstadoReg(0);
                genericaPmGrupoParamEjb.create(genericaPmGrupoParam);
                MovilidadUtil.addSuccessMessage("Relación creada con éxito");
                listGenericaPmGrupoParam = genericaPmGrupoParamEjb.findAll(paramArea.getIdParamArea());
                reset();
                genericaPmGrupoParam = new GenericaPmGrupoParam();
                genericaPmGrupoParam.setIdParamArea(paramArea);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editar() {
        try {
            cargarMap();
            if (genericaPmGrupoParam != null) {
                if (i_idEmpleadoCargo == null) {
                    MovilidadUtil.addErrorMessage("Cargo es requerido");
                    return;
                }
                if (!genericaPmGrupoParam.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo().equals(i_idEmpleadoCargo)) {
                    if (mapCargo.containsKey(i_idEmpleadoCargo)) {
                        MovilidadUtil.addErrorMessage("Cargo seleccionado tiene relación existente con " + mapCargo.get(i_idEmpleadoCargo).getArea());
                        return;
                    }
                }
                cargarObjetos();
                genericaPmGrupoParam.setModificado(new Date());
                genericaPmGrupoParamEjb.edit(genericaPmGrupoParam);
                MovilidadUtil.addSuccessMessage("Relación actualizada con éxito");
                listGenericaPmGrupoParam = genericaPmGrupoParamEjb.findAll(paramArea.getIdParamArea());
                reset();
                PrimeFaces.current().executeScript("PF('cargoDlg').hide()");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void onGenericaPmGrupoParamEvent(GenericaPmGrupoParam event) {
        genericaPmGrupoParam = event;
        i_idEmpleadoCargo = genericaPmGrupoParam.getIdEmpleadoTipoCargo() != null ? genericaPmGrupoParam.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo() : null;
    }

    public void prepareGuardar() {
        reset();
        genericaPmGrupoParam = new GenericaPmGrupoParam();
        if (paramArea != null) {
            genericaPmGrupoParam.setIdParamArea(paramArea);
            PrimeFaces.current().executeScript("PF('cargoDlg').show()");
        } else {
            eliminarMensages();
            MovilidadUtil.addErrorMessage(user.getUsername() + " no cuenta con Área asociada");
        }
    }

    public void reset() {
        genericaPmGrupoParam = null;
        i_idEmpleadoCargo = null;
    }

    public void eliminarEvent(GenericaPmGrupoParam event) {
        event.setEstadoReg(1);
        event.setModificado(new Date());
        genericaPmGrupoParamEjb.edit(event);
        MovilidadUtil.addSuccessMessage("Relación eliminada con éxito");
    }

    void cargarObjetos() {
        genericaPmGrupoParam.setIdEmpleadoTipoCargo(new EmpleadoTipoCargo(i_idEmpleadoCargo));
    }

    void cargarMap() {
        List<GenericaPmGrupoParam> findAllEstadoReg = genericaPmGrupoParamEjb.findAllEstadoReg();
        if (findAllEstadoReg != null) {
            mapCargo.clear();
            for (GenericaPmGrupoParam p : findAllEstadoReg) {
                mapCargo.put(p.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), p.getIdParamArea());
            }
        }
    }

    void eliminarMensages() {
        FacesContext context = FacesContext.getCurrentInstance();
        Iterator<FacesMessage> it = context.getMessages();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    public GenericaPmGrupoParam getGenericaPmGrupoParam() {
        return genericaPmGrupoParam;
    }

    public void setGenericaPmGrupoParam(GenericaPmGrupoParam paramAreaCargo) {
        this.genericaPmGrupoParam = paramAreaCargo;
    }

    public List<GenericaPmGrupoParam> getListGenericaPmGrupoParam() {
        return listGenericaPmGrupoParam;
    }

    public void setListGenericaPmGrupoParam(List<GenericaPmGrupoParam> listGenericaPmGrupoParam) {
        this.listGenericaPmGrupoParam = listGenericaPmGrupoParam;
    }

    public Integer getI_idEmpleadoCargo() {
        return i_idEmpleadoCargo;
    }

    public void setI_idEmpleadoCargo(Integer i_idEmpleadoCargo) {
        this.i_idEmpleadoCargo = i_idEmpleadoCargo;
    }

    public List<ParamAreaCargo> getListEmpleadoCargo() {
        return listEmpleadoCargo;
    }

    public void setListEmpleadoCargo(List<ParamAreaCargo> listEmpleadoCargo) {
        this.listEmpleadoCargo = listEmpleadoCargo;
    }

    public List<ParamArea> getListParamArea() {
        return listParamArea;
    }

    public void setListParamArea(List<ParamArea> listParamArea) {
        this.listParamArea = listParamArea;
    }

    public String getC_usernameLogin() {
        return c_usernameLogin;
    }

}
