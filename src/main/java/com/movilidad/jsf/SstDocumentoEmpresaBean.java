package com.movilidad.jsf;

import com.movilidad.ejb.SstDocumentoEmpresaFacadeLocal;
import com.movilidad.model.SstDocumentoEmpresa;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
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
 * @author Carlos Ballestas
 */
@Named(value = "sstDocumentoEmpresaBean")
@ViewScoped
public class SstDocumentoEmpresaBean implements Serializable {

    @EJB
    private SstDocumentoEmpresaFacadeLocal documentoEmpresaEjb;

    private SstDocumentoEmpresa documentoEmpresa;
    private SstDocumentoEmpresa selected;

    private boolean b_vigencia;
    private boolean b_requerido;
    private boolean b_numero;
    private boolean flagEdit;

    private String tipoDocumento;

    private List<SstDocumentoEmpresa> lstDocumentoEmpresas;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstDocumentoEmpresas = documentoEmpresaEjb.findAllEstadoReg();
    }

    public void nuevo() {
        documentoEmpresa = new SstDocumentoEmpresa();
        tipoDocumento = "";
        selected = null;
        b_vigencia = false;
        b_requerido = false;
        b_numero = false;
        flagEdit = false;
    }

    public void editar() {
        flagEdit = true;
        tipoDocumento = selected.getTipoDocumento();
        documentoEmpresa = selected;
        b_requerido = (documentoEmpresa.getRequerido() == 1);
        b_vigencia = (documentoEmpresa.getVigencia() == 1);
        b_numero = (documentoEmpresa.getNumero() == 1);
    }

    public void guardar() {
        documentoEmpresa.setNumero(b_numero ? 1 : 0);
        documentoEmpresa.setVigencia(b_vigencia ? 1 : 0);
        documentoEmpresa.setRequerido(b_requerido ? 1 : 0);

        if (flagEdit) {

            if (!validarDatosActualizar()) {
                documentoEmpresa.setTipoDocumento(tipoDocumento);
                documentoEmpresa.setModificado(new Date());
                documentoEmpresa.setUsername(user.getUsername());
                documentoEmpresaEjb.edit(documentoEmpresa);
                PrimeFaces.current().executeScript("PF('documentoEmpresas').hide();");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage("El tipo de documento a modificar YA SE ENCUENTRA REGISTRADO");
            }

        } else {
            if (documentoEmpresaEjb.findByTipoDoc(tipoDocumento) != null) {
                MovilidadUtil.addErrorMessage("El tipo de documento a registrar YA SE ENCUENTRA REGISTRADO");
                return;
            }
            documentoEmpresa.setTipoDocumento(tipoDocumento);
            documentoEmpresa.setCreado(new Date());
            documentoEmpresa.setEstadoReg(0);
            documentoEmpresa.setUsername(user.getUsername());
            documentoEmpresaEjb.create(documentoEmpresa);
            lstDocumentoEmpresas.add(documentoEmpresa);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
    }

    private boolean validarDatosActualizar() {
        return !documentoEmpresa.getTipoDocumento().equals(tipoDocumento) && documentoEmpresaEjb.findByTipoDoc(tipoDocumento) != null;
    }

    public SstDocumentoEmpresa getDocumentoEmpresa() {
        return documentoEmpresa;
    }

    public void setDocumentoEmpresa(SstDocumentoEmpresa documentoEmpresa) {
        this.documentoEmpresa = documentoEmpresa;
    }

    public SstDocumentoEmpresa getSelected() {
        return selected;
    }

    public void setSelected(SstDocumentoEmpresa selected) {
        this.selected = selected;
    }

    public boolean isB_vigencia() {
        return b_vigencia;
    }

    public void setB_vigencia(boolean b_vigencia) {
        this.b_vigencia = b_vigencia;
    }

    public boolean isB_requerido() {
        return b_requerido;
    }

    public void setB_requerido(boolean b_requerido) {
        this.b_requerido = b_requerido;
    }

    public boolean isB_numero() {
        return b_numero;
    }

    public void setB_numero(boolean b_numero) {
        this.b_numero = b_numero;
    }

    public boolean isFlagEdit() {
        return flagEdit;
    }

    public void setFlagEdit(boolean flagEdit) {
        this.flagEdit = flagEdit;
    }

    public List<SstDocumentoEmpresa> getLstDocumentoEmpresas() {
        return lstDocumentoEmpresas;
    }

    public void setLstDocumentoEmpresas(List<SstDocumentoEmpresa> lstDocumentoEmpresas) {
        this.lstDocumentoEmpresas = lstDocumentoEmpresas;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

}
