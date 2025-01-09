package com.movilidad.jsf;

import com.movilidad.ejb.GenericaJornadaParamFacadeLocal;
import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.model.GenericaJornadaParam;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "genericaJornadaParamBean")
@ViewScoped
public class GenericaJornadaParamBean implements Serializable {

    @EJB
    private GenericaJornadaParamFacadeLocal genericaJornadaParamEjb;

    @EJB
    private ParamAreaFacadeLocal paramAreaFacadeLocal;

    private GenericaJornadaParam genericaJornadaParam;
    private GenericaJornadaParam selected;
    private ParamAreaUsr paramAreaUsr;

    private List<GenericaJornadaParam> lstGenericaJornadaParams;
    private List<ParamArea> listParamArea;
    private HashMap<Integer, GenericaJornadaParam> mapGenericaJornadaParam;

    private boolean flagBtnNuevo;
    private boolean isEditing;
    private boolean b_cambio_jornada;
    private boolean b_autorizar_extension_jornada;
    private boolean b_aprobar_extras_feriadas;
    private boolean b_requerir_orden_trabajo;
    private boolean b_validar_descanso_dominical;
    private boolean b_control_horas_extras;
    private boolean b_notifica;
    private Integer i_idArea = null;

    private UserExtended user;

    @PostConstruct
    public void init() {
        mapGenericaJornadaParam = new HashMap<>();
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        flagBtnNuevo = getUserAuthAll();
    }

    private void cargarMapa() {
        List<GenericaJornadaParam> lstGenJornadaParamAux = genericaJornadaParamEjb.findAllByEstadoReg();

        if (lstGenJornadaParamAux != null) {
            for (GenericaJornadaParam gj : lstGenJornadaParamAux) {
                mapGenericaJornadaParam.put(gj.getIdParamArea().getIdParamArea(), gj);
            }
        }
    }

    public void cargarEmails() {
        if (b_notifica) {
            genericaJornadaParam.setEmails("");
        }
    }

    public void nuevo() {
        isEditing = false;
        i_idArea = null;
        b_cambio_jornada = false;
        b_autorizar_extension_jornada = false;
        b_aprobar_extras_feriadas = false;
        b_requerir_orden_trabajo = false;
        b_validar_descanso_dominical = false;
        b_control_horas_extras = false;
        b_notifica = false;
        genericaJornadaParam = new GenericaJornadaParam();
        listParamArea = paramAreaFacadeLocal.findAllEstadoReg();
        selected = null;
    }

    public void editar() {
        isEditing = true;
        listParamArea = paramAreaFacadeLocal.findAllEstadoReg();
        genericaJornadaParam = selected;
        i_idArea = genericaJornadaParam.getIdParamArea().getIdParamArea();
        b_cambio_jornada = (genericaJornadaParam.getCtrlCambioJornada() == 1);
        b_autorizar_extension_jornada = (genericaJornadaParam.getCtrlAutorizarExtensionJornada() == 1);
        b_aprobar_extras_feriadas = (genericaJornadaParam.getCtrlAprobarExtrasFeriadas() == 1);
        b_requerir_orden_trabajo = (genericaJornadaParam.getRequerirOrdenTrabajo() == 1);
        b_validar_descanso_dominical = (genericaJornadaParam.getValidarDescansoDominical() == 1);
        b_notifica = (genericaJornadaParam.getNotifica() == 1);

        if (genericaJornadaParam.getHorasExtrasMensuales() >= 0
                && genericaJornadaParam.getHorasExtrasSemanales() >= 0) {
            b_control_horas_extras = true;
        }

    }

    public void guardar() {
        if (!validarDatos()) {
            cargarMapa();

            if (!b_control_horas_extras) {
                genericaJornadaParam.setHorasExtrasMensuales(-1);
                genericaJornadaParam.setHorasExtrasSemanales(-1);
            }

            genericaJornadaParam.setCtrlCambioJornada(b_cambio_jornada ? 1 : 0);
            genericaJornadaParam.setCtrlAutorizarExtensionJornada(b_autorizar_extension_jornada ? 1 : 0);
            genericaJornadaParam.setCtrlAprobarExtrasFeriadas(b_aprobar_extras_feriadas ? 1 : 0);
            genericaJornadaParam.setRequerirOrdenTrabajo(b_requerir_orden_trabajo ? 1 : 0);
            genericaJornadaParam.setValidarDescansoDominical(b_validar_descanso_dominical ? 1 : 0);
            genericaJornadaParam.setNotifica(b_notifica ? 1 : 0);
            genericaJornadaParam.setUsername(user.getUsername());

            if (!b_notifica) {
                genericaJornadaParam.setEmails("");
            }

            if (!isEditing) {
                if (mapGenericaJornadaParam.containsKey(i_idArea)) {
                    MovilidadUtil.addErrorMessage(mapGenericaJornadaParam.get(i_idArea).getIdParamArea().getArea()
                            + " cuenta con parametrización. ");
                    return;
                }
                genericaJornadaParam.setIdParamArea(new ParamArea(i_idArea));
                genericaJornadaParam.setEstadoReg(0);
                genericaJornadaParam.setCreado(new Date());
                genericaJornadaParamEjb.create(genericaJornadaParam);
                lstGenericaJornadaParams.add(genericaJornadaParam);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro guardado éxitosamente");
            } else {
                if (!genericaJornadaParam.getIdParamArea().getIdParamArea().equals(i_idArea)) {
                    if (mapGenericaJornadaParam.containsKey(i_idArea)) {
                        MovilidadUtil.addErrorMessage(mapGenericaJornadaParam.get(i_idArea).getIdParamArea().getArea()
                                + " cuenta con parametrización. ");
                        return;
                    }
                }
                genericaJornadaParam.setIdParamArea(new ParamArea(i_idArea));
                genericaJornadaParam.setModificado(new Date());
                genericaJornadaParamEjb.edit(genericaJornadaParam);
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
                PrimeFaces.current().executeScript("PF('GenericaJornadaParamCreateDialog').hide()");
            }
        }

    }

    private boolean validarDatos() {
        if (i_idArea == null) {
            MovilidadUtil.addErrorMessage("Área es requerido");
            return true;
        }
        if (genericaJornadaParam.getNroDiasDescansoDominical() == 0 && genericaJornadaParam.getNroDiasDescansoDominical() >= 100) {
            MovilidadUtil.addErrorMessage("Los días de descanso dominical deben ser mayor a CERO y menor a CIEN");
            return true;
        }
        if (genericaJornadaParam.getHorasExtrasSemanales() == 0 && genericaJornadaParam.getHorasExtrasSemanales() >= 100) {
            MovilidadUtil.addErrorMessage("Las horas extras semanales deben ser mayor a CERO y menor a CIEN");
            return true;
        }
        if (genericaJornadaParam.getHorasExtrasMensuales() == 0 && genericaJornadaParam.getHorasExtrasMensuales() >= 100) {
            MovilidadUtil.addErrorMessage("Las horas extras mensuales deben ser mayor a CERO y menor a CIEN");
            return true;
        }

        return false;
    }

    boolean getUserAuthAll() {
        for (GrantedAuthority ua : user.getAuthorities()) {
            if (ua.getAuthority().equals(Util.USER_AUT_ROLE)) {
                return true;
            }
        }
        return false;
    }

    public GenericaJornadaParam getGenericaJornadaParam() {
        return genericaJornadaParam;
    }

    public void setGenericaJornadaParam(GenericaJornadaParam genericaJornadaParam) {
        this.genericaJornadaParam = genericaJornadaParam;
    }

    public GenericaJornadaParam getSelected() {
        return selected;
    }

    public void setSelected(GenericaJornadaParam selected) {
        this.selected = selected;
    }

    public List<GenericaJornadaParam> getLstGenericaJornadaParams() {
        lstGenericaJornadaParams = genericaJornadaParamEjb.findAllByEstadoReg();
        return lstGenericaJornadaParams;
    }

    public void setLstGenericaJornadaParams(List<GenericaJornadaParam> lstGenericaJornadaParams) {
        this.lstGenericaJornadaParams = lstGenericaJornadaParams;
    }

    public boolean isFlagBtnNuevo() {
        return flagBtnNuevo;
    }

    public void setFlagBtnNuevo(boolean flagBtnNuevo) {
        this.flagBtnNuevo = flagBtnNuevo;
    }

    public boolean isB_cambio_jornada() {
        return b_cambio_jornada;
    }

    public void setB_cambio_jornada(boolean b_cambio_jornada) {
        this.b_cambio_jornada = b_cambio_jornada;
    }

    public boolean isB_notifica() {
        return b_notifica;
    }

    public void setB_notifica(boolean b_notifica) {
        this.b_notifica = b_notifica;
    }

    public boolean isB_autorizar_extension_jornada() {
        return b_autorizar_extension_jornada;
    }

    public void setB_autorizar_extension_jornada(boolean b_autorizar_extension_jornada) {
        this.b_autorizar_extension_jornada = b_autorizar_extension_jornada;
    }

    public boolean isB_aprobar_extras_feriadas() {
        return b_aprobar_extras_feriadas;
    }

    public void setB_aprobar_extras_feriadas(boolean b_aprobar_extras_feriadas) {
        this.b_aprobar_extras_feriadas = b_aprobar_extras_feriadas;
    }

    public boolean isB_requerir_orden_trabajo() {
        return b_requerir_orden_trabajo;
    }

    public void setB_requerir_orden_trabajo(boolean b_requerir_orden_trabajo) {
        this.b_requerir_orden_trabajo = b_requerir_orden_trabajo;
    }

    public boolean isB_validar_descanso_dominical() {
        return b_validar_descanso_dominical;
    }

    public void setB_validar_descanso_dominical(boolean b_validar_descanso_dominical) {
        this.b_validar_descanso_dominical = b_validar_descanso_dominical;
    }

    public ParamAreaUsr getParamArea() {
        return paramAreaUsr;
    }

    public void setParamArea(ParamAreaUsr paramAreaUsr) {
        this.paramAreaUsr = paramAreaUsr;
    }

    public Integer getI_idArea() {
        return i_idArea;
    }

    public void setI_idArea(Integer i_idArea) {
        this.i_idArea = i_idArea;
    }

    public List<ParamArea> getListParamArea() {
        return listParamArea;
    }

    public void setListParamArea(List<ParamArea> listParamArea) {
        this.listParamArea = listParamArea;
    }

    public boolean isB_control_horas_extras() {
        return b_control_horas_extras;
    }

    public void setB_control_horas_extras(boolean b_control_horas_extras) {
        this.b_control_horas_extras = b_control_horas_extras;
    }

}
