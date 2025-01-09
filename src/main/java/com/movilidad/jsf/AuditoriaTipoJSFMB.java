/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaTipoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.AuditoriaTipo;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.applet.AudioClip;
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
@Named(value = "audiTipoJSFMB")
@ViewScoped
public class AuditoriaTipoJSFMB implements Serializable {

    /**
     * Creates a new instance of AuditoriaTipoJSFMB
     */
    public AuditoriaTipoJSFMB() {
    }
    
    @EJB
    private AuditoriaTipoFacadeLocal audiTipoEJB;
    
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;
    
    private List<AuditoriaTipo> list;
    private ParamAreaUsr paramAreaUsr;
    private UserExtended user;
    private AuditoriaTipo auditoriaTipo;
    
    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        consultar();
    }
    
    public void consultar() {
        list = audiTipoEJB.findByArea(paramAreaUsr.getIdParamArea().getIdParamArea());
    }
    
    public boolean validarCampos(AuditoriaTipo obj) {
        
        if (obj.getNombre() != null) {
            if (obj.getNombre().isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite Un nombre de tipo auditoría");
                return true;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite Un nombre de tipo auditoría");
            return true;
        }
        if (audiTipoEJB.findByAreaIdAuditoriaTipoAndNombre(obj.getNombre(),
                obj.getIdAuditoriaTipo() == null ? 0 : obj.getIdAuditoriaTipo(),
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
        if (validarCampos(auditoriaTipo)) {
            return;
        }
        auditoriaTipo.setModificado(MovilidadUtil.fechaCompletaHoy());
        audiTipoEJB.edit(auditoriaTipo);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        MovilidadUtil.hideModal("crear_audi_turno_dialog_wv");
        
    }
    
    public void guardar() {
        guardarTransactional();
        consultar();
    }
    
    @Transactional
    public void guardarTransactional() {
        if (validarCampos(auditoriaTipo)) {
            return;
        }
        auditoriaTipo.setCreado(MovilidadUtil.fechaCompletaHoy());
        auditoriaTipo.setIdParamArea(paramAreaUsr.getIdParamArea());
        auditoriaTipo.setEstadoReg(0);
        audiTipoEJB.create(auditoriaTipo);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        auditoriaTipo = new AuditoriaTipo();
    }
    
    public void preEdit(AuditoriaTipo obj) {
        if (audiTipoEJB.findTipoUsado(obj.getIdAuditoriaTipo()) != null) {
            MovilidadUtil.addErrorMessage("No es posible modificar este tipo de auditoría, Ya ha sido utilizado para una auditoría.");
            return;
        }
        auditoriaTipo = obj;
        MovilidadUtil.openModal("crear_audi_turno_dialog_wv");
    }
    
    public void preGuardar() {
        auditoriaTipo = new AuditoriaTipo();
    }
    
    public List<AuditoriaTipo> getList() {
        return list;
    }
    
    public void setList(List<AuditoriaTipo> list) {
        this.list = list;
    }
    
    public AuditoriaTipo getAuditoriaTipo() {
        return auditoriaTipo;
    }
    
    public void setAuditoriaTipo(AuditoriaTipo auditoriaTipo) {
        this.auditoriaTipo = auditoriaTipo;
    }
    
}
