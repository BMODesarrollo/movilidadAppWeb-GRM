package com.movilidad.jsf;

import com.movilidad.ejb.SstDocumentoTerceroFacadeLocal;
import com.movilidad.ejb.SstLaborTipoDocsFacadeLocal;
import com.movilidad.ejb.SstTipoLaborFacadeLocal;
import com.movilidad.model.SstDocumentoTercero;
import com.movilidad.model.SstLaborTipoDocs;
import com.movilidad.model.SstTipoLabor;
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
@Named(value = "sstDocTipoLaborBean")
@ViewScoped
public class SstDocTipoLaborBean implements Serializable {

    @EJB
    private SstLaborTipoDocsFacadeLocal laborTipoDocsEjb;

    @EJB
    private SstTipoLaborFacadeLocal tipoLaborEjb;

    @EJB
    private SstDocumentoTerceroFacadeLocal documentoTerceroEjb;

    private SstLaborTipoDocs laborTipoDoc;
    private SstLaborTipoDocs selected;

    private Integer idTipoLabor;
    private Integer idTipoDocTercero;

    private boolean flagEdit;

    private List<SstLaborTipoDocs> lstLaborTipoDocs;
    private List<SstTipoLabor> lstSstTipoLabores;
    private List<SstDocumentoTercero> lstSstDocumentoTerceros;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstLaborTipoDocs = laborTipoDocsEjb.findAllEstadoReg();
    }

    public void nuevo() {
        laborTipoDoc = new SstLaborTipoDocs();
        selected = null;
        flagEdit = false;
        idTipoDocTercero = null;
        idTipoLabor = null;
        lstSstTipoLabores = tipoLaborEjb.findAllEstadoReg();
        lstSstDocumentoTerceros = documentoTerceroEjb.findAllEstadoReg();
    }

    public void editar() {
        flagEdit = true;
        idTipoLabor = selected.getIdTipoLabor().getIdSstTipoLabor();
        idTipoDocTercero = selected.getIdTipoDocTercero().getIdSstDocumentoTercero();
        laborTipoDoc = selected;
        lstSstTipoLabores = tipoLaborEjb.findAllEstadoReg();
        lstSstDocumentoTerceros = documentoTerceroEjb.findAllEstadoReg();
    }

    public void guardar() {
        if (flagEdit) {
            if (!verificarDatosAlActualizar()) {
                laborTipoDoc.setIdTipoLabor(tipoLaborEjb.find(idTipoLabor));
                laborTipoDoc.setIdTipoDocTercero(documentoTerceroEjb.find(idTipoDocTercero));
                laborTipoDoc.setModificado(new Date());
                laborTipoDoc.setUsername(user.getUsername());
                laborTipoDocsEjb.edit(laborTipoDoc);
                PrimeFaces.current().executeScript("PF('wvdocsTipoLabor').hide();");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage("El tipo de documento a actualizar YA SE ENCUENTRA ASOCIADO A UN TIPO DE LABOR");
            }
        } else {
            if (laborTipoDocsEjb.findByTipoLaborAndDocTercero(idTipoDocTercero, idTipoLabor) != null) {
                MovilidadUtil.addErrorMessage("El tipo de documento a guardar YA SE ENCUENTRA ASOCIADO A UN TIPO DE LABOR");
                return;
            }
            laborTipoDoc.setIdTipoLabor(tipoLaborEjb.find(idTipoLabor));
            laborTipoDoc.setIdTipoDocTercero(documentoTerceroEjb.find(idTipoDocTercero));
            laborTipoDoc.setCreado(new Date());
            laborTipoDoc.setEstadoReg(0);
            laborTipoDoc.setUsername(user.getUsername());
            laborTipoDocsEjb.create(laborTipoDoc);
            lstLaborTipoDocs.add(laborTipoDoc);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
    }

    private boolean verificarDatosAlActualizar() {
        if (!selected.getIdTipoDocTercero().getIdSstDocumentoTercero().equals(idTipoDocTercero)) {
            if (laborTipoDocsEjb.findByTipoLaborAndDocTercero(idTipoDocTercero, idTipoLabor) != null) {
                return true;
            }
        }
        if (!selected.getIdTipoLabor().getIdSstTipoLabor().equals(idTipoLabor)) {
            if (laborTipoDocsEjb.findByTipoLaborAndDocTercero(idTipoDocTercero, idTipoLabor) != null) {
                return true;
            }
        }

        return false;
    }

    public SstLaborTipoDocs getLaborTipoDoc() {
        return laborTipoDoc;
    }

    public void setLaborTipoDoc(SstLaborTipoDocs laborTipoDoc) {
        this.laborTipoDoc = laborTipoDoc;
    }

    public SstLaborTipoDocs getSelected() {
        return selected;
    }

    public void setSelected(SstLaborTipoDocs selected) {
        this.selected = selected;
    }

    public boolean isFlagEdit() {
        return flagEdit;
    }

    public void setFlagEdit(boolean flagEdit) {
        this.flagEdit = flagEdit;
    }

    public List<SstLaborTipoDocs> getLstLaborTipoDocs() {
        return lstLaborTipoDocs;
    }

    public void setLstLaborTipoDocs(List<SstLaborTipoDocs> lstLaborTipoDocs) {
        this.lstLaborTipoDocs = lstLaborTipoDocs;
    }

    public List<SstTipoLabor> getLstSstTipoLabores() {
        return lstSstTipoLabores;
    }

    public void setLstSstTipoLabores(List<SstTipoLabor> lstSstTipoLabores) {
        this.lstSstTipoLabores = lstSstTipoLabores;
    }

    public List<SstDocumentoTercero> getLstSstDocumentoTerceros() {
        return lstSstDocumentoTerceros;
    }

    public void setLstSstDocumentoTerceros(List<SstDocumentoTercero> lstSstDocumentoTerceros) {
        this.lstSstDocumentoTerceros = lstSstDocumentoTerceros;
    }

    public Integer getIdTipoLabor() {
        return idTipoLabor;
    }

    public void setIdTipoLabor(Integer idTipoLabor) {
        this.idTipoLabor = idTipoLabor;
    }

    public Integer getIdTipoDocTercero() {
        return idTipoDocTercero;
    }

    public void setIdTipoDocTercero(Integer idTipoDocTercero) {
        this.idTipoDocTercero = idTipoDocTercero;
    }

}
