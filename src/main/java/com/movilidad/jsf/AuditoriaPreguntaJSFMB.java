/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaPreguntaFacadeLocal;
import com.movilidad.ejb.AuditoriaRespuestaFacadeLocal;
import com.movilidad.ejb.AuditoriaTipoRespuestaFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.AuditoriaAlternativaRespuesta;
import com.movilidad.model.AuditoriaPregunta;
import com.movilidad.model.AuditoriaTipoRespuesta;
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
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "audiPreguntaJSFMB")
@ViewScoped
public class AuditoriaPreguntaJSFMB implements Serializable {

    /**
     * Creates a new instance of AuditoriaPreguntaJSFMB
     */
    public AuditoriaPreguntaJSFMB() {
    }

    @EJB
    private AuditoriaTipoRespuestaFacadeLocal tipoRespuestaEJB;
    @EJB
    private AuditoriaPreguntaFacadeLocal audiPreguntataEJB;
    @EJB
    private AuditoriaRespuestaFacadeLocal audiRespuestaEJB;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;

    private List<AuditoriaPregunta> list;
    private List<AuditoriaTipoRespuesta> listTipoRespuesta;
    private AuditoriaPregunta audiPregunta;
    private int i_tipoRespuesta;
    private int i_idArea;
    private ParamAreaUsr paramAreaUsr;
    private UserExtended user;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        i_idArea = paramAreaUsr.getIdParamArea().getIdParamArea();
        consultar();
    }

    boolean validarCampos() {
        if (audiPregunta.getCodigo() == null || audiPregunta.getCodigo().isEmpty()) {
            MovilidadUtil.addErrorMessage("No se ha digitado un código.");
            return true;
        }
        if (audiPregunta.getEnunciado() == null || audiPregunta.getEnunciado().isEmpty()) {
            MovilidadUtil.addErrorMessage("No se ha digitado un enunciado.");
            return true;
        }
        if (i_tipoRespuesta == 0) {
            MovilidadUtil.addErrorMessage("No ha seleccionado un tipo de respuesta.");
            return true;
        }
        if (audiPregunta.getNumero() == null) {
            MovilidadUtil.addErrorMessage("No se ha digitado un número.");
            return true;
        }
        if (audiPreguntataEJB.findByAreaIdAuditoriaPreguntaAndCodigo(audiPregunta.getCodigo(),
                audiPregunta.getIdAuditoriaPregunta() == null ? 0 : audiPregunta.getIdAuditoriaPregunta(),
                i_idArea) != null) {
            MovilidadUtil.addErrorMessage("Ya existe una pregunta con el código indicado.");
            return true;
        }
        return false;
    }

    public void consultar() {
        list = audiPreguntataEJB.findByIdArea(i_idArea);
    }

    public void guardar() {
        if (validarCampos()) {
            return;
        }
        guardarTransactional();
        consultar();
    }

    @Transactional
    public void guardarTransactional() {

        audiPregunta.setIdAuditoriaTipoRespuesta(new AuditoriaTipoRespuesta(i_tipoRespuesta));
        audiPregunta.setCreado(MovilidadUtil.fechaCompletaHoy());
        audiPregunta.setUsername(user.getUsername());
        audiPregunta.setIdParamArea(paramAreaUsr.getIdParamArea());
        audiPregunta.setEstadoReg(0);
        audiPreguntataEJB.create(audiPregunta);
        int numero = audiPregunta.getNumero();
        numero++;
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        audiPregunta = new AuditoriaPregunta();
        audiPregunta.setNumero(numero);

    }

    public void edit() {
        if (validarCampos()) {
            return;
        }
        editTransactional();
        consultar();
    }

    @Transactional
    public void editTransactional() {
        audiPregunta.setIdAuditoriaTipoRespuesta(new AuditoriaTipoRespuesta(i_tipoRespuesta));
        audiPregunta.setUsername(user.getUsername());
        audiPregunta.setModificado(MovilidadUtil.fechaCompletaHoy());
        audiPreguntataEJB.edit(audiPregunta);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        MovilidadUtil.hideModal("crear_audi_pregunta_dialog_wv");
    }

    public void preGuardar() {
        audiPregunta = new AuditoriaPregunta();
        i_tipoRespuesta = 0;
        listTipoRespuesta = tipoRespuestaEJB.findByArea(i_idArea);
    }

    public void preEdit(AuditoriaPregunta obj) {
        if (audiRespuestaEJB.findByIdPregunta(obj.getIdAuditoriaPregunta()) != null) {
            MovilidadUtil.addErrorMessage("No se puede Modificar esta pregunta, ya se utilizó en una auditoría.");
            return;
        }
        audiPregunta = obj;
        listTipoRespuesta = tipoRespuestaEJB.findByArea(i_idArea);
        i_tipoRespuesta = audiPregunta.getIdAuditoriaTipoRespuesta().getIdAuditoriaTipoRespuesta();
        MovilidadUtil.openModal("crear_audi_pregunta_dialog_wv");
    }

    public List<AuditoriaAlternativaRespuesta> getListaAlternativas(AuditoriaPregunta obj) {
        return obj.getIdAuditoriaTipoRespuesta().getAuditoriaAlternativaRespuestaList();
    }

    public String tipoRespuestaDesc(AuditoriaTipoRespuesta obj) {
        String descripcion = "";
        if (obj.getAuditoriaAlternativaRespuestaList() != null) {
            for (AuditoriaAlternativaRespuesta a : obj.getAuditoriaAlternativaRespuestaList()) {
                descripcion = descripcion + "\n Enunciado: "
                        + a.getEnunciado() + " Valor: " + a.getValor();
            }
        }
        return descripcion;
    }

    public List<AuditoriaPregunta> getList() {
        return list;
    }

    public void setList(List<AuditoriaPregunta> list) {
        this.list = list;
    }

    public AuditoriaPregunta getAudiPregunta() {
        return audiPregunta;
    }

    public void setAudiPregunta(AuditoriaPregunta audiPregunta) {
        this.audiPregunta = audiPregunta;
    }

    public List<AuditoriaTipoRespuesta> getListTipoRespuesta() {
        return listTipoRespuesta;
    }

    public void setListTipoRespuesta(List<AuditoriaTipoRespuesta> listTipoRespuesta) {
        this.listTipoRespuesta = listTipoRespuesta;
    }

    public int getI_tipoRespuesta() {
        return i_tipoRespuesta;
    }

    public void setI_tipoRespuesta(int i_tipoRespuesta) {
        this.i_tipoRespuesta = i_tipoRespuesta;
    }

}
