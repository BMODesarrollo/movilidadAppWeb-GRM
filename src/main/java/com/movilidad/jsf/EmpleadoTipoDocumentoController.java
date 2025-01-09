/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoTipoDocumentosFacadeLocal;
import com.movilidad.model.EmpleadoTipoDocumentos;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "emplTipDocCtrl")
@ViewScoped
public class EmpleadoTipoDocumentoController implements Serializable {

    @EJB
    private EmpleadoTipoDocumentosFacadeLocal emplTipDocEJB;

    private EmpleadoTipoDocumentos emplTipDoc;

    private List<EmpleadoTipoDocumentos> listEmplTipDocs;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean obligatorio = false;
    private boolean vencimiento = false;
    private boolean radicado = false;

    @PostConstruct
    public void init() {
        obligatorio = false;
        vencimiento = false;
        radicado = false;
        emplTipDoc = new EmpleadoTipoDocumentos();
        listEmplTipDocs = new ArrayList();
        listarEmpleadoTipoDocumentos();
    }

    /**
     * Creates a new instance of EmpleadoTipoDocumentoController
     */
    public EmpleadoTipoDocumentoController() {
    }

    public void listarEmpleadoTipoDocumentos() {
        try {
            listEmplTipDocs = emplTipDocEJB.findAll();
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage(e.getMessage());
        }
    }

    public EmpleadoTipoDocumentos getEmplTipDoc() {
        return emplTipDoc;
    }

    public void setEmplTipDoc(EmpleadoTipoDocumentos emplTipDoc) {
        this.emplTipDoc = emplTipDoc;
    }

    public List<EmpleadoTipoDocumentos> getListEmplTipDocs() {
        return listEmplTipDocs;
    }

    public void setListEmplTipDocs(List<EmpleadoTipoDocumentos> listEmplTipDocs) {
        this.listEmplTipDocs = listEmplTipDocs;
    }

    public void setCamposInt() {
        emplTipDoc.setVencimiento((vencimiento) ? 1 : 0);
        emplTipDoc.setObligatorio((obligatorio) ? 1 : 0);
        emplTipDoc.setRadicado((radicado) ? 1 : 0);
    }

    public void guargar() {
        if (emplTipDocEJB.findByNombreTipoDoc(emplTipDoc.getNombreTipoDocumento(), 0) != null) {
            MovilidadUtil.addErrorMessage("Ya existe Tipo de Documento con este Nombre");
            return;
        }
        if (emplTipDoc != null) {
            emplTipDoc.setUsername(user.getUsername());
            emplTipDoc.setCreado(new Date());
            emplTipDoc.setEstadoReg(0);

            setCamposInt();

            try {
                emplTipDocEJB.create(emplTipDoc);
                MovilidadUtil.addSuccessMessage("Exito. Registro exitoso Tipo de Documento");
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('dlg2').hide();");
                init();
            } catch (Exception e) {
                MovilidadUtil.addErrorMessage(e.getMessage());
            }
        }

    }

    public void guardarEdit() {
        if (emplTipDocEJB.findByNombreTipoDoc(emplTipDoc.getNombreTipoDocumento(), emplTipDoc.getIdEmpleadoTipoDocumento()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe Tipo de Documento con este Nombre");
            return;
        }
        emplTipDoc.setUsername(user.getUsername());
        emplTipDoc.setModificado(new Date());
        setCamposInt();

        try {
            emplTipDocEJB.edit(emplTipDoc);
            MovilidadUtil.addSuccessMessage("Exito. Se modificó el registro exitosamente");
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dlg2').hide();");
            init();
        } catch (Exception e) {
        }
    }

    public void editar(EmpleadoTipoDocumentos etd) {
        emplTipDoc = etd;
        this.obligatorio = etd.getObligatorio() > 0;
        this.radicado = etd.getRadicado() > 0;
        this.vencimiento = etd.getVencimiento() > 0;
    }

    public void openDialog() {
        emplTipDoc = new EmpleadoTipoDocumentos();
    }

    public void eliminar(EmpleadoTipoDocumentos etd) {
        etd.setEstadoReg(1);
        try {
            emplTipDocEJB.edit(etd);
            MovilidadUtil.addSuccessMessage("Eliminado. Se eliminó el registro exitosamente");
            init();
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage(e.getMessage());
        }
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public boolean isVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(boolean vencimiento) {
        this.vencimiento = vencimiento;
    }

    public boolean isRadicado() {
        return radicado;
    }

    public void setRadicado(boolean radicado) {
        this.radicado = radicado;
    }

}
