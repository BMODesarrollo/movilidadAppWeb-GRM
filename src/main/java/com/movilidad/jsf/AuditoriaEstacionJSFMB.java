/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaEstacionFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.AuditoriaEstacion;
import com.movilidad.model.ParamAreaUsr;
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
 * @author solucionesit
 */
@Named(value = "audiEstacionJSFMB")
@ViewScoped
public class AuditoriaEstacionJSFMB implements Serializable {

    /**
     * Creates a new instance of AuditoriaEstacionJSFMB
     */
    public AuditoriaEstacionJSFMB() {
    }

    @EJB
    private AuditoriaEstacionFacadeLocal audiEstacionEJB;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;

    private List<AuditoriaEstacion> list;
    private ParamAreaUsr paramAreaUsr;
    private UserExtended user;
    private AuditoriaEstacion auditoriaEstacion;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        consultar();
    }

    public void consultar() {
        list = audiEstacionEJB.findByArea(paramAreaUsr.getIdParamArea().getIdParamArea());
    }

    public boolean validarCampos(AuditoriaEstacion obj) {

        if (obj.getNombre() != null) {
            if (obj.getNombre().isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite Un nombre de Estación para auditoría");
                return true;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite Un nombre de Estación para auditoría");
            return true;
        }
        if (audiEstacionEJB.findByAreaIdAuditoriaEstacionAndNombre(obj.getNombre(),
                obj.getIdAuditoriaEstacion() == null ? 0 : obj.getIdAuditoriaEstacion(),
                paramAreaUsr.getIdParamArea().getIdParamArea()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe una estacion con el nombre indicado.");
            return true;
        }
        if (obj.getDescripcion() != null) {
            if (obj.getDescripcion().isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite la observación");
                return true;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite la observación");
            return true;
        }
        return false;
    }

    public void edit() {
        editTransactional();
        consultar();
    }

    @Transactional
    public void editTransactional() {
        if (validarCampos(auditoriaEstacion)) {
            return;
        }
        auditoriaEstacion.setModificado(MovilidadUtil.fechaCompletaHoy());
        audiEstacionEJB.edit(auditoriaEstacion);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        PrimeFaces.current().executeScript("PF('crear_audi_estacion_dialog_wv').hide()");
    }

    public void guardar() {
        guardarTransactional();
        consultar();
    }

    @Transactional
    public void guardarTransactional() {
        if (validarCampos(auditoriaEstacion)) {
            return;
        }
        auditoriaEstacion.setCreado(MovilidadUtil.fechaCompletaHoy());
        auditoriaEstacion.setIdParamArea(paramAreaUsr.getIdParamArea());
        auditoriaEstacion.setEstadoReg(0);
        audiEstacionEJB.create(auditoriaEstacion);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        auditoriaEstacion = new AuditoriaEstacion();
    }

    public void preEdit(AuditoriaEstacion obj) {
        auditoriaEstacion = obj;
    }

    public void preGuardar() {
        auditoriaEstacion = new AuditoriaEstacion();
    }

    public List<AuditoriaEstacion> getList() {
        return list;
    }

    public void setList(List<AuditoriaEstacion> list) {
        this.list = list;
    }

    public AuditoriaEstacion getAuditoriaEstacion() {
        return auditoriaEstacion;
    }

    public void setAuditoriaEstacion(AuditoriaEstacion auditoriaEstacion) {
        this.auditoriaEstacion = auditoriaEstacion;
    }

}
