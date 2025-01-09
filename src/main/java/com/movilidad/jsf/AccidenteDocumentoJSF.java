/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteDocumentoFacadeLocal;
import com.movilidad.model.AccTipoDocs;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteDocumento;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accidenteDocumentoJSF")
@ViewScoped
public class AccidenteDocumentoJSF implements Serializable {

    @EJB
    private AccidenteDocumentoFacadeLocal accidenteDocumentoFacadeLocal;

    private AccidenteDocumento accidenteDocumento;

    private List<AccidenteDocumento> listAccidenteDocumento;

    private int i_idAccidente;
    private int i_idAccTipoDocs;

    private UploadedFile file;
    private StreamedContent fileDown;

    private boolean b_flag;
    private boolean b_control;

    private List<String> fotosNovedades;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private AccidenteJSF accidenteJSF;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;

    public AccidenteDocumentoJSF() {
    }

    @PostConstruct
    public void init() {
        i_idAccTipoDocs = 0;
        b_flag = true;
        i_idAccidente = accidenteJSF.compartirIdAccidente();
    }

    public void guardar() {
        try {
            if (i_idAccidente != 0) {
                if (accidenteDocumento != null) {
                    cargarObjetos();
                    if (b_control) {
                        b_control = false;
                        return;
                    }
                    if (file == null) {
                        MovilidadUtil.addErrorMessage("Debe cargar un archivo relacionado al tipo de documento");
                        return;
                    }
                    accidenteDocumento.setIdAccidente(new Accidente(i_idAccidente));
                    accidenteDocumento.setCreado(new Date());
                    accidenteDocumento.setModificado(new Date());
                    accidenteDocumento.setUsername(user.getUsername());
                    accidenteDocumento.setEstadoReg(0);
                    accidenteDocumento.setIdAccTipoDocs(new AccTipoDocs(i_idAccTipoDocs));
                    accidenteDocumentoFacadeLocal.create(accidenteDocumento);
                    if (file != null) {
                        if (file.getContents().length > 0) {
                            String path = MovilidadUtil.cargarArchivosAccidentalidad(file, i_idAccidente, "Documentos", accidenteDocumento.getIdAccidenteDocumento(), accidenteDocumento.getIdAccTipoDocs().getTipoDocs());
                            accidenteDocumento.setPath(path);
                            accidenteDocumentoFacadeLocal.edit(accidenteDocumento);
                        }
                    }
                    MovilidadUtil.addSuccessMessage("Se guardó el Accidente Documento correctamente");
                    reset();
                }
                return;
            }
            MovilidadUtil.addErrorMessage("No se puede realizar esta acción, no se seleccionó un accidente");
        } catch (Exception e) {

        }
    }

    public void prepareGuardar() {
        accidenteDocumento = new AccidenteDocumento();
        accidenteDocumento.setFecha(new Date());
    }

    public void editar() {
        try {
            if (accidenteDocumento != null) {
                cargarObjetos();
                if (b_control) {
                    b_control = false;
                    return;
                }
                if (file != null) {
                    if (file.getContents().length > 0) {
                        // String oldPath = accidenteDocumento.getPath() != null ? accidenteDocumento.getPath() : null;
                        String path = MovilidadUtil.cargarArchivosAccidentalidad(file, i_idAccidente, "Documentos", accidenteDocumento.getIdAccidenteDocumento(), accidenteDocumento.getIdAccTipoDocs().getTipoDocs());
                        accidenteDocumento.setPath(path);
//                        if (oldPath != null) {
//                            Util.deleteFile(oldPath);
//                        }
                    }
                }
                accidenteDocumentoFacadeLocal.edit(accidenteDocumento);
                MovilidadUtil.addSuccessMessage("Se actualizó el Accidente Documento correctamente");
                reset();
            }
        } catch (Exception e) {
        }
    }

    public void eliminarLista(AccidenteDocumento at) {
        try {
            at.setEstadoReg(1);
            accidenteDocumentoFacadeLocal.edit(at);
            MovilidadUtil.addSuccessMessage("Se elimino el Accidente Documento de la lista");
            reset();
        } catch (Exception e) {
        }
    }

    public void prepareEditar(AccidenteDocumento at) {
        accidenteDocumento = at;
        b_flag = false;
        if (at.getIdAccTipoDocs() != null) {
            i_idAccTipoDocs = at.getIdAccTipoDocs().getIdAccTipoDocs();
        }
    }

    void cargarObjetos() {
        if (i_idAccTipoDocs != 0) {
            accidenteDocumento.setIdAccTipoDocs(new AccTipoDocs(i_idAccTipoDocs));
        } else {
            b_control = true;
            MovilidadUtil.addErrorMessage("Tipo de documento es requerido");
        }
    }

    public void prepDownloadLocal(String path) throws Exception {
        fileDown = MovilidadUtil.prepDownload(path);
        String contentType = fileDown.getContentType();
        if (contentType == null || contentType.equals("application/pdf")) {
            cargarPdf(path);
        } else {
            cargarFoto(path);
        }
    }

    private void cargarPdf(String path) throws Exception {
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
        MovilidadUtil.openModal("vistaDocumentoDialog");
        MovilidadUtil.updateComponent("tabView:frmVistaDocumento");
    }

    public void cargarFoto(String path) throws IOException {
        if (fotosNovedades != null) {
            this.fotosNovedades.clear();
        } else {
            fotosNovedades = new ArrayList<>();
        }
        fotosNovedades.add(path);

        fotoJSFManagedBean.setListFotos(fotosNovedades);
        MovilidadUtil.openModal("galeria_foto_dialog_wv");
        MovilidadUtil.updateComponent("tabView:galeria_fotos_form");
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        if (file.getContents().length > 0) {
            MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
            PrimeFaces.current().executeScript("PF('documentoDlg').hide();");
            PrimeFaces.current().ajax().update("tabView:form-carga-documento");
            PrimeFaces.current().ajax().update(":accidente-form:msg");
        } else {
            MovilidadUtil.addErrorMessage("Error al cargar archivo");
        }
    }

    public void reset() {
        accidenteDocumento = null;
        i_idAccTipoDocs = 0;
        b_flag = true;
        file = null;
    }

    public AccidenteDocumento getAccidenteDocumento() {
        return accidenteDocumento;
    }

    public void setAccidenteDocumento(AccidenteDocumento accidenteDocumento) {
        this.accidenteDocumento = accidenteDocumento;
    }

    public List<AccidenteDocumento> getListAccidenteDocumento() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        listAccidenteDocumento = accidenteDocumentoFacadeLocal.estadoReg(i_idAccidente);
        return listAccidenteDocumento;
    }

    public int getI_idAccTipoDocs() {
        return i_idAccTipoDocs;
    }

    public void setI_idAccTipoDocs(int i_idAccTipoDocs) {
        this.i_idAccTipoDocs = i_idAccTipoDocs;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isB_flag() {
        return b_flag;
    }

    public void setB_flag(boolean b_flag) {
        this.b_flag = b_flag;
    }

    public StreamedContent getFileDown() {
        return fileDown;
    }

}
