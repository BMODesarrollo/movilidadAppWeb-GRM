package com.movilidad.jsf;

import com.movilidad.ejb.SstDocumentoTerceroFacadeLocal;
import com.movilidad.model.SstDocumentoTercero;
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
@Named(value = "sstDocumentoTerceroBean")
@ViewScoped
public class SstDocumentoTerceroBean implements Serializable {

    @EJB
    private SstDocumentoTerceroFacadeLocal documentoTerceroEjb;

    private SstDocumentoTercero documentoTercero;
    private SstDocumentoTercero selected;

    private boolean b_vigencia;
    private boolean b_requerido;
    private boolean b_numero;
    private boolean flagEdit;

    private String tipoDocTercero;

    private List<SstDocumentoTercero> lstDocumentoTerceros;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstDocumentoTerceros = documentoTerceroEjb.findAllEstadoReg();
    }

    public void nuevo() {
        documentoTercero = new SstDocumentoTercero();
        tipoDocTercero = "";
        selected = null;
        b_vigencia = false;
        b_requerido = false;
        b_numero = false;
        flagEdit = false;
    }

    public void editar() {
        flagEdit = true;
        tipoDocTercero = selected.getTipoDocTercero();
        documentoTercero = selected;
        b_requerido = (documentoTercero.getRequerido() == 1);
        b_vigencia = (documentoTercero.getVigencia() == 1);
        b_numero = (documentoTercero.getNumero() == 1);
    }

    public void guardar() {
        documentoTercero.setNumero(b_numero ? 1 : 0);
        documentoTercero.setVigencia(b_vigencia ? 1 : 0);
        documentoTercero.setRequerido(b_requerido ? 1 : 0);

        if (flagEdit) {

            if (!validarDatosAlActualizar()) {
                documentoTercero.setTipoDocTercero(tipoDocTercero);
                documentoTercero.setUsername(user.getUsername());
                documentoTercero.setModificado(new Date());
                documentoTerceroEjb.edit(documentoTercero);
                PrimeFaces.current().executeScript("PF('wvDocumentoTerceros').hide();");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage("El tipo de documento a modificar YA EXISTE");
            }

        } else {

            if (documentoTerceroEjb.findByTipoDocumento(tipoDocTercero) != null) {
                MovilidadUtil.addErrorMessage("El tipo de documento a registrar YA EXISTE");
                return;
            }

            documentoTercero.setTipoDocTercero(tipoDocTercero);
            documentoTercero.setUsername(user.getUsername());
            documentoTercero.setCreado(new Date());
            documentoTercero.setEstadoReg(0);
            documentoTerceroEjb.create(documentoTercero);
            lstDocumentoTerceros.add(documentoTercero);
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            nuevo();
        }
    }

    private boolean validarDatosAlActualizar() {
        if (!documentoTercero.getTipoDocTercero().equals(tipoDocTercero)) {
            if (documentoTerceroEjb.findByTipoDocumento(tipoDocTercero) != null) {
                return true;
            }
        }

        return false;
    }

    public SstDocumentoTercero getSelected() {
        return selected;
    }

    public void setSelected(SstDocumentoTercero selected) {
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

    public SstDocumentoTercero getDocumentoTercero() {
        return documentoTercero;
    }

    public void setDocumentoTercero(SstDocumentoTercero documentoTercero) {
        this.documentoTercero = documentoTercero;
    }

    public List<SstDocumentoTercero> getLstDocumentoTerceros() {
        return lstDocumentoTerceros;
    }

    public void setLstDocumentoTerceros(List<SstDocumentoTercero> lstDocumentoTerceros) {
        this.lstDocumentoTerceros = lstDocumentoTerceros;
    }

    public String getTipoDocTercero() {
        return tipoDocTercero;
    }

    public void setTipoDocTercero(String tipoDocTercero) {
        this.tipoDocTercero = tipoDocTercero;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

}
