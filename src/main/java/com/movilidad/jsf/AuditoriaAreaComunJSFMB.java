/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaAreaComunFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.AuditoriaAreaComun;
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
 * Lista, Persiste y edita las auditorias de ara comun
 *
 * @author solucionesit
 */
@Named(value = "audiAreaComunJSFMB")
@ViewScoped
public class AuditoriaAreaComunJSFMB implements Serializable {

    /**
     * Creates a new instance of AuditoriaAreaComunJSFMB
     */
    public AuditoriaAreaComunJSFMB() {
    }

    @EJB
    private AuditoriaAreaComunFacadeLocal audiAreaComunEJB;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;

    private List<AuditoriaAreaComun> list;
    private ParamAreaUsr paramAreaUsr;
    private UserExtended user;
    private AuditoriaAreaComun auditoriaAreaComun;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        consultar();
    }

    public void consultar() {
        list = audiAreaComunEJB.findByArea(paramAreaUsr.getIdParamArea().getIdParamArea());
    }

    public boolean validarCampos(AuditoriaAreaComun obj) {

        if (obj.getNombre() != null) {
            if (obj.getNombre().isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite un nombre de Área Común para auditoría");
                return true;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite Un nombre de Área Común para auditoría");
            return true;
        }
        if (audiAreaComunEJB.findByAreaIdAuditoriaAreaComunAndNombre(obj.getNombre(),
                obj.getIdAuditoriaAreaComun() == null ? 0 : obj.getIdAuditoriaAreaComun(),
                paramAreaUsr.getIdParamArea().getIdParamArea()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe un Área Común para con el nombre indicado.");
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
        if (validarCampos(auditoriaAreaComun)) {
            return;
        }
        auditoriaAreaComun.setModificado(MovilidadUtil.fechaCompletaHoy());
        audiAreaComunEJB.edit(auditoriaAreaComun);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        PrimeFaces.current().executeScript("PF('crear_audi_area_dialog_wv').hide()");
    }

    public void guardar() {
        guardarTransactional();
        consultar();
    }

    @Transactional
    public void guardarTransactional() {
        if (validarCampos(auditoriaAreaComun)) {
            return;
        }
        auditoriaAreaComun.setCreado(MovilidadUtil.fechaCompletaHoy());
        auditoriaAreaComun.setIdParamArea(paramAreaUsr.getIdParamArea());
        auditoriaAreaComun.setEstadoReg(0);
        audiAreaComunEJB.create(auditoriaAreaComun);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        auditoriaAreaComun = new AuditoriaAreaComun();
    }

    public void preEdit(AuditoriaAreaComun obj) {
        auditoriaAreaComun = obj;
    }

    public void preGuardar() {
        auditoriaAreaComun = new AuditoriaAreaComun();
    }

    public List<AuditoriaAreaComun> getList() {
        return list;
    }

    public void setList(List<AuditoriaAreaComun> list) {
        this.list = list;
    }

    public AuditoriaAreaComun getAuditoriaAreaComun() {
        return auditoriaAreaComun;
    }

    public void setAuditoriaAreaComun(AuditoriaAreaComun auditoriaAreaComun) {
        this.auditoriaAreaComun = auditoriaAreaComun;
    }

}
