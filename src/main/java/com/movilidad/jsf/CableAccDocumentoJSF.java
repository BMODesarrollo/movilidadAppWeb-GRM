/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoDocsFacadeLocal;
import com.movilidad.ejb.CableAccDocumentoFacadeLocal;
import com.movilidad.model.AccTipoDocs;
import com.movilidad.model.CableAccidentalidad;
import com.movilidad.model.CableAccDocumento;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "cableAccDocumentoJSF")
@ViewScoped
public class CableAccDocumentoJSF implements Serializable {

    @EJB
    private CableAccDocumentoFacadeLocal cableAccDocumentoFacadeLocal;
    @EJB
    private AccTipoDocsFacadeLocal accTipoDocsFacadeLocal;

    private CableAccDocumento cableAccDocumento;

    private List<CableAccDocumento> listCableAccDocumento;

    private Integer idCableAccidentalidad;
    private Integer idAccTipoDocs;

    private UploadedFile file;
    private StreamedContent fileDown;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private CableAccidentalidadJSF accidenteJSF;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    /**
     * Creates a new instance of CableAccDocumentoJSF
     */
    public CableAccDocumentoJSF() {
    }

    @PostConstruct
    public void init() {
        idAccTipoDocs = null;
    }

    public void guardar() {
        if (idCableAccidentalidad == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un caso de accidentaldad");
            return;
        }
        if (cableAccDocumento != null) {
            if (file == null) {
                MovilidadUtil.addErrorMessage("Debe cargar un archivo relacionado al tipo de documento");
                return;
            }
            cableAccDocumento.setIdCableAccidentalidad(new CableAccidentalidad(idCableAccidentalidad));
            cableAccDocumento.setIdAccTpDocs(new AccTipoDocs(idAccTipoDocs));
            cableAccDocumento.setCreado(new Date());
            cableAccDocumento.setModificado(new Date());
            cableAccDocumento.setUsername(user.getUsername());
            cableAccDocumento.setEstadoReg(0);
            cableAccDocumentoFacadeLocal.create(cableAccDocumento);
            if (file != null) {
                String path = Util.saveFile(file, cableAccDocumento.getIdCableAccDocumento(), "cableAccDocumento");
                cableAccDocumento.setPath(path);
                cableAccDocumentoFacadeLocal.edit(cableAccDocumento);
            }
            MovilidadUtil.addSuccessMessage("Documento registrado con éxito");
            reset();
        }
    }

    public void prepareGuardar() {
        cableAccDocumento = new CableAccDocumento();
    }

    public void editar() {
        if (idCableAccidentalidad == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un caso de accidentaldad");
            return;
        }
        if (cableAccDocumento != null) {
            if (idAccTipoDocs == null) {
                MovilidadUtil.addErrorMessage("Tipo documento es requerido");
                return;
            }
            if (file != null) {
                String path = Util.saveFile(file, cableAccDocumento.getIdCableAccDocumento(), "cableAccDocumento");
                cableAccDocumento.setPath(path);
            }
            cableAccDocumento.setIdAccTpDocs(new AccTipoDocs(idAccTipoDocs));
            cableAccDocumentoFacadeLocal.edit(cableAccDocumento);
            MovilidadUtil.addSuccessMessage("Se actualizó el CableAccidentalidad Documento correctamente");
            reset();
        }
    }

    public void eliminarLista(CableAccDocumento at) {
        at.setEstadoReg(1);
        at.setModificado(new Date());
        cableAccDocumentoFacadeLocal.edit(at);
        MovilidadUtil.addSuccessMessage("Se ha eliminado el documento seleccionado");
        reset();
    }

    public void prepareEditar(CableAccDocumento at) {
        cableAccDocumento = at;
        if (at.getIdAccTpDocs() != null) {
            idAccTipoDocs = at.getIdAccTpDocs().getIdAccTipoDocs();
        }
    }

    public void prepDownloadLocal(String path) throws Exception {
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
        fileDown = MovilidadUtil.prepDownload(path);
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
    }

    public void reset() {
        cableAccDocumento = null;
        idAccTipoDocs = null;
        file = null;
    }

    public CableAccDocumento getCableAccDocumento() {
        return cableAccDocumento;
    }

    public void setCableAccDocumento(CableAccDocumento cableAccDocumento) {
        this.cableAccDocumento = cableAccDocumento;
    }

    public List<CableAccDocumento> getListCableAccDocumento() {
        idCableAccidentalidad = accidenteJSF.compartirIdAccidente();
        if (idCableAccidentalidad != null) {
            listCableAccDocumento = cableAccDocumentoFacadeLocal.findByAccidentalidadAndEstadoReg(idCableAccidentalidad);
        }
        return listCableAccDocumento;
    }

    public void setListCableAccDocumento(List<CableAccDocumento> listCableAccDocumento) {
        this.listCableAccDocumento = listCableAccDocumento;
    }

    public Integer getIdAccTipoDocs() {
        return idAccTipoDocs;
    }

    public void setIdAccTipoDocs(Integer idAccTipoDocs) {
        this.idAccTipoDocs = idAccTipoDocs;
    }

    public StreamedContent getFileDown() {
        return fileDown;
    }

    public void setFileDown(StreamedContent fileDown) {
        this.fileDown = fileDown;
    }

}
