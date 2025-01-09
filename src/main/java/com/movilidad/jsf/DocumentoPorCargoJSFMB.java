/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.DocumentoPorCargoFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoCargoFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoDocumentosFacadeLocal;
import com.movilidad.model.DocumentoPorCargo;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.EmpleadoTipoDocumentos;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "docuPorCargoBean")
@ViewScoped
public class DocumentoPorCargoJSFMB implements Serializable {
    
    @EJB
    private EmpleadoTipoCargoFacadeLocal emplTipoCargoEJB;
    @EJB
    private EmpleadoTipoDocumentosFacadeLocal emplTipoDocumentosEJB;
    @EJB
    private DocumentoPorCargoFacadeLocal documentoPorCargoEJB;
    private List<DocumentoPorCargo> list;
    private List<EmpleadoTipoCargo> listTipoCargo;
    private List<EmpleadoTipoDocumentos> listTipoDocu;
    private DocumentoPorCargo selected;
    private int i_idTipoCargo;
    private int i_idTipoDocumento;
    
    UserExtended user = null;

    /**
     * Creates a new instance of DocumentoPorCargoJSFMB
     */
    public DocumentoPorCargoJSFMB() {
    }
    
    @PostConstruct
    private void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        consultar();
    }
    
    public void consultarListas() {
        listTipoCargo = emplTipoCargoEJB.findAllActivos();
        listTipoDocu = emplTipoDocumentosEJB.findAllActivos();
    }
    
    public void consultar() {
        list = documentoPorCargoEJB.findAllActivos();
    }
    
    public void nuevo() {
        selected = new DocumentoPorCargo();
        i_idTipoCargo = 0;
        i_idTipoDocumento = 0;
        consultarListas();
    }
    
    public void editar(DocumentoPorCargo param) {
        selected = param;
        i_idTipoCargo = selected.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo();
        i_idTipoDocumento = selected.getIdEmpleadoTipoDocumento().getIdEmpleadoTipoDocumento();
        consultarListas();
    }
    
    public void guardar() {
        if (validar()) {
            MovilidadUtil.addErrorMessage("No es posible crear el registro.");
            return;
        }
        selected.setUsername(user.getUsername());
        selected.setCreado(MovilidadUtil.fechaCompletaHoy());
        selected.setEstadoReg(0);
        selected.setIdEmpleadoTipoCargo(new EmpleadoTipoCargo(i_idTipoCargo));
        selected.setIdEmpleadoTipoDocumento(new EmpleadoTipoDocumentos(i_idTipoDocumento));
        documentoPorCargoEJB.create(selected);
        selected.setIdDocumentoPorCargo(null);
        selected.setCreado(null);
        selected.setIdEmpleadoTipoDocumento(null);
        i_idTipoDocumento = 0;
        consultar();
        MovilidadUtil.addSuccessMessage("Registro creado!");
    }
    
    public void actualizar() {
        if (validar()) {
            MovilidadUtil.addErrorMessage("No es posible actualizar el registro.");
            return;
        }
        selected.setUsername(user.getUsername());
        selected.setModificado(MovilidadUtil.fechaCompletaHoy());
        selected.setIdEmpleadoTipoCargo(new EmpleadoTipoCargo(i_idTipoCargo));
        selected.setIdEmpleadoTipoDocumento(new EmpleadoTipoDocumentos(i_idTipoDocumento));
        documentoPorCargoEJB.edit(selected);
        consultar();
        selected = null;
        MovilidadUtil.addSuccessMessage("Registro actualizado!");
        MovilidadUtil.hideModal("wvdocsTipoCargo");
    }
    
    private boolean validar() {
        
        if (documentoPorCargoEJB.findByIdTipoCargoAndIdTipoDocu(i_idTipoCargo,
                i_idTipoDocumento,
                selected.getIdDocumentoPorCargo() == null ? 0 : selected.getIdDocumentoPorCargo()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe un registro similar");
            return true;
        }
        if (i_idTipoCargo == 0) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar un tipo de cargo");
            return true;
        }
        if (i_idTipoDocumento == 0) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar un tipo de documento");
            
            return true;
        }
        return false;
    }
    
    public List<DocumentoPorCargo> getList() {
        return list;
    }
    
    public void setList(List<DocumentoPorCargo> list) {
        this.list = list;
    }
    
    public DocumentoPorCargo getSelected() {
        return selected;
    }
    
    public void setSelected(DocumentoPorCargo selected) {
        this.selected = selected;
    }
    
    public int getI_idTipoCargo() {
        return i_idTipoCargo;
    }
    
    public void setI_idTipoCargo(int i_idTipoCargo) {
        this.i_idTipoCargo = i_idTipoCargo;
    }
    
    public int getI_idTipoDocumento() {
        return i_idTipoDocumento;
    }
    
    public void setI_idTipoDocumento(int i_idTipoDocumento) {
        this.i_idTipoDocumento = i_idTipoDocumento;
    }
    
    public List<EmpleadoTipoCargo> getListTipoCargo() {
        return listTipoCargo;
    }
    
    public void setListTipoCargo(List<EmpleadoTipoCargo> listTipoCargo) {
        this.listTipoCargo = listTipoCargo;
    }
    
    public List<EmpleadoTipoDocumentos> getListTipoDocu() {
        return listTipoDocu;
    }
    
    public void setListTipoDocu(List<EmpleadoTipoDocumentos> listTipoDocu) {
        this.listTipoDocu = listTipoDocu;
    }
    
}
