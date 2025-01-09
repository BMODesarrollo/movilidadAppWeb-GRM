/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaRespuestaFacadeLocal;
import com.movilidad.ejb.AuditoriaTipoRespuestaFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.AuditoriaAlternativaRespuesta;
import com.movilidad.model.AuditoriaTipoRespuesta;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
@Named(value = "audiTipoRespuestaJSFMB")
@ViewScoped
public class AuditoriaTipoRespuestaJSFMB implements Serializable {

    /**
     * Creates a new instance of AuditoriaTipoRespuestaJSFMB
     */
    public AuditoriaTipoRespuestaJSFMB() {
    }

    @EJB
    private AuditoriaTipoRespuestaFacadeLocal audiTipoRespuestaEJB;
    @EJB
    private AuditoriaRespuestaFacadeLocal audiRespuestaEJB;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;

    private List<AuditoriaTipoRespuesta> list;
    private List<AuditoriaAlternativaRespuesta> listAlternativas;
    private AuditoriaAlternativaRespuesta audiAlterResp;
    private ParamAreaUsr paramAreaUsr;
    private UserExtended user;
    private AuditoriaTipoRespuesta auditoriaTipoRespuesta;
    private boolean b_seleccion_multiple;
    private boolean b_observacion;
    private boolean b_abierta;
    private boolean b_documento;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        consultar();
    }

    public void consultar() {
        list = audiTipoRespuestaEJB.findByArea(paramAreaUsr.getIdParamArea().getIdParamArea());
    }

    public void eliminar(int index) {
        listAlternativas.remove(index);
    }

    public boolean validarCamposAlternativa() {
        if (audiAlterResp.getEnunciado() != null) {
            if (Util.getStringSinEspacios(audiAlterResp.getEnunciado()).isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite un código");
                return true;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite un código");
            return true;
        }
        return false;
    }

    public boolean validarCampos(AuditoriaTipoRespuesta obj) {

        if (obj.getNombre() != null) {
            if (Util.getStringSinEspacios(obj.getNombre()).isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite Un nombre de tipo de pregunta para auditoría");
                return true;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite Un nombre de tipo de pregunta para auditoría");
            return true;
        }
        if (b_seleccion_multiple) {
            if (listAlternativas.isEmpty()) {
                MovilidadUtil.addErrorMessage("Si el tipo de respuesta es cerrada se debe agregar al menos una alternativa.");
                return true;
            }
        }

        return false;
    }

    public void edit() {
        if (validarCampos(auditoriaTipoRespuesta)) {
            return;
        }
        editTransactional();
        consultar();
    }

    public void agregarAlternativa() {
        listAlternativas.add(audiAlterResp);
        int numero = audiAlterResp.getNumero();
        audiAlterResp = new AuditoriaAlternativaRespuesta();
        numero++;
        audiAlterResp.setNumero(numero);
    }

    @Transactional
    public void editTransactional() {
        if (!b_seleccion_multiple) {
            if (auditoriaTipoRespuesta.getAuditoriaAlternativaRespuestaList() != null) {
                auditoriaTipoRespuesta.setAuditoriaAlternativaRespuestaList(null);
            }
        } else {
            for (AuditoriaAlternativaRespuesta aar : listAlternativas) {
                if (aar.getIdAuditoriaTipoRespuesta() == null) {
                    aar.setIdAuditoriaTipoRespuesta(auditoriaTipoRespuesta);
                }
            }
        }
        auditoriaTipoRespuesta.setSeleccionMultiple(b_seleccion_multiple ? 1 : 0);
        auditoriaTipoRespuesta.setObservacion(b_observacion ? 1 : 0);
        auditoriaTipoRespuesta.setDocumento(b_documento ? 1 : 0);
        auditoriaTipoRespuesta.setAbierta(b_abierta ? 1 : 0);
        auditoriaTipoRespuesta.setUsername(user.getUsername());
        auditoriaTipoRespuesta.setModificado(MovilidadUtil.fechaCompletaHoy());
        audiTipoRespuestaEJB.edit(auditoriaTipoRespuesta);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        PrimeFaces.current().executeScript("PF('crear_audi_tipo_pregunta_dialog_wv').hide()");
    }

    public void guardar() {
        if (validarCampos(auditoriaTipoRespuesta)) {
            return;
        }
        guardarTransactional();
        consultar();
        auditoriaTipoRespuesta = new AuditoriaTipoRespuesta();
        auditoriaTipoRespuesta.setAuditoriaAlternativaRespuestaList(new ArrayList<AuditoriaAlternativaRespuesta>());
        listAlternativas = auditoriaTipoRespuesta.getAuditoriaAlternativaRespuestaList();
    }

    @Transactional
    public void guardarTransactional() {
        if (b_seleccion_multiple) {
            for (AuditoriaAlternativaRespuesta aar : listAlternativas) {
                aar.setIdAuditoriaTipoRespuesta(auditoriaTipoRespuesta);
            }
        }

        auditoriaTipoRespuesta.setSeleccionMultiple(b_seleccion_multiple ? 1 : 0);
        auditoriaTipoRespuesta.setObservacion(b_observacion ? 1 : 0);
        auditoriaTipoRespuesta.setDocumento(b_documento ? 1 : 0);
        auditoriaTipoRespuesta.setAbierta(b_abierta ? 1 : 0);
        auditoriaTipoRespuesta.setCreado(MovilidadUtil.fechaCompletaHoy());
        auditoriaTipoRespuesta.setIdParamArea(paramAreaUsr.getIdParamArea());
        auditoriaTipoRespuesta.setEstadoReg(0);
        auditoriaTipoRespuesta.setUsername(user.getUsername());
        audiTipoRespuestaEJB.create(auditoriaTipoRespuesta);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        b_documento = false;
        b_seleccion_multiple = false;
        b_observacion = false;
        b_abierta = false;
    }

    public void preEdit(AuditoriaTipoRespuesta obj) {
        if (audiRespuestaEJB.findByIdTipoRespuesta(obj.getIdAuditoriaTipoRespuesta()) != null) {
            MovilidadUtil.addErrorMessage("No es posible modificar este tipo de respuesta, Ya ha sido utilizado para una auditoría.");
            return;
        }
        auditoriaTipoRespuesta = obj;
        b_seleccion_multiple = auditoriaTipoRespuesta.getSeleccionMultiple() == 1;
        b_observacion = auditoriaTipoRespuesta.getObservacion() == 1;
        b_documento = auditoriaTipoRespuesta.getDocumento() == 1;
        b_abierta = auditoriaTipoRespuesta.getAbierta() == 1;
        Collections.sort(auditoriaTipoRespuesta.getAuditoriaAlternativaRespuestaList());
        listAlternativas = auditoriaTipoRespuesta.getAuditoriaAlternativaRespuestaList();
        MovilidadUtil.openModal("crear_audi_tipo_pregunta_dialog_wv");
    }

    public void preGuardar() {
        b_seleccion_multiple = false;
        b_observacion = false;
        b_documento = false;
        b_abierta = false;
        auditoriaTipoRespuesta = new AuditoriaTipoRespuesta();

        auditoriaTipoRespuesta.setAuditoriaAlternativaRespuestaList(new ArrayList<AuditoriaAlternativaRespuesta>());
        listAlternativas = auditoriaTipoRespuesta.getAuditoriaAlternativaRespuestaList();
    }

    public List<AuditoriaAlternativaRespuesta> getListaAlternativas(AuditoriaTipoRespuesta obj) {
        return obj.getAuditoriaAlternativaRespuestaList();
    }

    public void preAgregarAlternativa() {
        audiAlterResp = new AuditoriaAlternativaRespuesta();
    }

    public List<AuditoriaTipoRespuesta> getList() {
        return list;
    }

    public void setList(List<AuditoriaTipoRespuesta> list) {
        this.list = list;
    }

    public AuditoriaTipoRespuesta getAuditoriaTipoRespuesta() {
        return auditoriaTipoRespuesta;
    }

    public void setAuditoriaTipoRespuesta(AuditoriaTipoRespuesta auditoriaTipoRespuesta) {
        this.auditoriaTipoRespuesta = auditoriaTipoRespuesta;
    }

    public boolean isB_seleccion_multiple() {
        return b_seleccion_multiple;
    }

    public void setB_seleccion_multiple(boolean b_seleccion_multiple) {
        this.b_seleccion_multiple = b_seleccion_multiple;
    }

    public boolean isB_documento() {
        return b_documento;
    }

    public void setB_documento(boolean b_documento) {
        this.b_documento = b_documento;
    }

    public List<AuditoriaAlternativaRespuesta> getListAlternativas() {
        return listAlternativas;
    }

    public void setListAlternativas(List<AuditoriaAlternativaRespuesta> listAlternativas) {
        this.listAlternativas = listAlternativas;
    }

    public AuditoriaAlternativaRespuesta getAudiAlterResp() {
        return audiAlterResp;
    }

    public void setAudiAlterResp(AuditoriaAlternativaRespuesta audiAlterResp) {
        this.audiAlterResp = audiAlterResp;
    }

    public boolean isB_observacion() {
        return b_observacion;
    }

    public void setB_observacion(boolean b_observacion) {
        this.b_observacion = b_observacion;
    }

    public boolean isB_abierta() {
        return b_abierta;
    }

    public void setB_abierta(boolean b_abierta) {
        this.b_abierta = b_abierta;
    }

}
