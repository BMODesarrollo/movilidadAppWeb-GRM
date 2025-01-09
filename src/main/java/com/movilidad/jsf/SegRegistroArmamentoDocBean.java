package com.movilidad.jsf;

import com.movilidad.ejb.SegRegistroArmamentoDocFacadeLocal;
import com.movilidad.ejb.SegRegistroArmamentoFacadeLocal;
import com.movilidad.model.SegRegistroArmamento;
import com.movilidad.model.SegRegistroArmamentoDoc;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "registroArmamentoDocBean")
@ViewScoped
public class SegRegistroArmamentoDocBean implements Serializable {

    @EJB
    private SegRegistroArmamentoFacadeLocal armamentoEjb;
    @EJB
    private SegRegistroArmamentoDocFacadeLocal armamentoDocEjb;

    @Inject
    private ArchivosJSFManagedBean archivosBean;

    private SegRegistroArmamento registroArmamento;
    private SegRegistroArmamentoDoc armamentoDoc;
    private SegRegistroArmamentoDoc selected;
    private String numDoc;

    private boolean isEditing;
    private boolean b_activo;

    private int height = 0;
    private int width = 0;

    private List<SegRegistroArmamentoDoc> lstArmamentoDocs;
    private List<UploadedFile> archivos;
    private List<String> fotosArmamentos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        registroArmamento = null;
    }

    public void nuevo() {
        armamentoDoc = new SegRegistroArmamentoDoc();
        selected = null;
        numDoc = "";
        b_activo = false;
        isEditing = false;
        archivos = new ArrayList<>();
    }

    public void editar() {
        isEditing = true;
        b_activo = (selected.getActivo() == 1);
        numDoc = selected.getNumeroDoc();
        armamentoDoc = selected;
        archivos = new ArrayList<>();
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    private void guardarTransactional() {
        String validacion = validarDatos();

        if (validacion == null) {
            if (isEditing) {
                armamentoDoc.setIdSegRegistroArmamento(registroArmamento);
                armamentoDoc.setActivo(b_activo ? 1 : 0);
                armamentoDoc.setNumeroDoc(numDoc);
                armamentoDoc.setUsername(user.getUsername());
                armamentoDoc.setModificado(new Date());
                armamentoDocEjb.edit(armamentoDoc);

                if (!archivos.isEmpty()) {
                    cargarFotosAlServidor();
                }

                PrimeFaces.current().executeScript("PF('wvArmamentoDoc').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                armamentoDoc.setIdSegRegistroArmamento(registroArmamento);
                armamentoDoc.setActivo(b_activo ? 1 : 0);
                armamentoDoc.setNumeroDoc(numDoc);
                armamentoDoc.setUsername(user.getUsername());
                armamentoDoc.setEstadoReg(0);
                armamentoDoc.setCreado(new Date());
                armamentoDocEjb.create(armamentoDoc);
                lstArmamentoDocs.add(armamentoDoc);

                cargarFotosAlServidor();

                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public void cargarRecurso() {
        if (selected.getPathDocumento() != null) {
            cargarDocumento();
        } else {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("El registro NO tiene documentos asociados");
        }
    }

    private void cargarDocumento() {
        PrimeFaces current = PrimeFaces.current();
        String ext = selected.getPathDocumento().substring(selected.getPathDocumento().lastIndexOf('.'), selected.getPathDocumento().length());

        if (ext.equals(".pdf")) {
            width = 700;
            height = 500;
            archivosBean.setExtension(ext);
            archivosBean.setPath(selected.getPathDocumento());
            archivosBean.setModalHeader("DOCUMENTO ARMAMENTO");
            current.executeScript("PF('DocumentoListDialog').show()");
        } else {

            try {
                Image i = Util.mostrarImagenN(selected.getPathDocumento());
                if (i != null) {
                    if (width < i.getWidth(null)) {
                        width = i.getWidth(null);
                    }
                    if (height < i.getHeight(null)) {
                        height = i.getHeight(null);
                    }
                }

                archivosBean.setExtension(ext);
                archivosBean.setPath(selected.getPathDocumento());
                archivosBean.setModalHeader("FOTO DOCUMENTO");
                current.executeScript("PF('DocumentoListDialog').show()");
            } catch (IOException ex) {
                Logger.getLogger(SegRegistroArmamentoDocBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void cargarFotosAlServidor() {
        String path_imagen;
        for (UploadedFile f : archivos) {
            if (isEditing) {
                if (Util.deleteFile(selected.getPathDocumento())) {
                    path_imagen = Util.saveFile(f, armamentoDoc.getIdSegRegistroArmamentoDoc(), "armamento_documentos");
                    armamentoDoc.setPathDocumento(path_imagen);
                    armamentoDocEjb.edit(armamentoDoc);
                } else {
                    path_imagen = Util.saveFile(f, armamentoDoc.getIdSegRegistroArmamentoDoc(), "armamento_documentos");
                    armamentoDoc.setPathDocumento(path_imagen);
                    armamentoDocEjb.edit(armamentoDoc);
                }
            } else {
                path_imagen = Util.saveFile(f, armamentoDoc.getIdSegRegistroArmamentoDoc(), "armamento_documentos");
                armamentoDoc.setPathDocumento(path_imagen);
                armamentoDocEjb.edit(armamentoDoc);
            }
        }
        archivos.clear();
    }

    private String validarDatos() {

        if (Util.validarFechaCambioEstado(armamentoDoc.getVigenteDesde(), armamentoDoc.getVigenteHasta())) {
            return "La fecha desde NO debe ser mayor a la fecha final";
        }

        if (isEditing) {
            if (!armamentoDoc.getNumeroDoc().equals(numDoc)) {
                if (armamentoDocEjb.findByNumDoc(numDoc.trim(), registroArmamento.getIdSegRegistroArmamento()) != null) {
                    return "YA existe un registro con el Número de Documento a ingresar";
                }
            }
            if (armamentoDocEjb.verificarRangoFechas(armamentoDoc.getVigenteDesde(), registroArmamento.getIdSegRegistroArmamento(), armamentoDoc.getIdSegRegistroArmamentoDoc()) != null) {
                return "YA existe un registro dentro del rango de fechas a ingresar ";
            }
            if (armamentoDocEjb.verificarRangoFechas(armamentoDoc.getVigenteHasta(), registroArmamento.getIdSegRegistroArmamento(), armamentoDoc.getIdSegRegistroArmamentoDoc()) != null) {
                return "YA existe un registro dentro del rango de fechas a ingresar ";
            }
        } else {

            if (lstArmamentoDocs != null) {

                if (armamentoDocEjb.verificarRangoFechas(armamentoDoc.getVigenteDesde(), registroArmamento.getIdSegRegistroArmamento(), 0) != null) {
                    return "YA existe un registro dentro del rango de fechas a ingresar";
                }
                if (armamentoDocEjb.verificarRangoFechas(armamentoDoc.getVigenteHasta(), registroArmamento.getIdSegRegistroArmamento(), 0) != null) {
                    return "YA existe un registro dentro del rango de fechas a ingresar ";
                }
                if (armamentoDocEjb.findByNumDoc(numDoc.trim(), registroArmamento.getIdSegRegistroArmamento()) != null) {
                    return "YA existe un registro con el Número de Documento a ingresar";
                }
                if (archivos.isEmpty()) {
                    return "DEBE seleccionar al una imágen ó PDF";
                }
            }
        }
        return null;
    }

    /**
     *
     * Evento que dispara la subida de archivos para la anexarlos al registro de
     * un documento
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();

        archivos.add(event.getFile());

        MovilidadUtil.addSuccessMessage("Documento agregado éxitosamente");
        current.executeScript("PF('AddFilesDocListDialog').hide()");
    }

    public SegRegistroArmamento getRegistroArmamento() {
        return registroArmamento;
    }

    public void setRegistroArmamento(SegRegistroArmamento registroArmamento) {
        this.registroArmamento = registroArmamento;
    }

    public SegRegistroArmamentoDoc getArmamentoDoc() {
        return armamentoDoc;
    }

    public void setArmamentoDoc(SegRegistroArmamentoDoc armamentoDoc) {
        this.armamentoDoc = armamentoDoc;
    }

    public SegRegistroArmamentoDoc getSelected() {
        return selected;
    }

    public void setSelected(SegRegistroArmamentoDoc selected) {
        this.selected = selected;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<SegRegistroArmamentoDoc> getLstArmamentoDocs() {
        return lstArmamentoDocs;
    }

    public void setLstArmamentoDocs(List<SegRegistroArmamentoDoc> lstArmamentoDocs) {
        this.lstArmamentoDocs = lstArmamentoDocs;
    }

    public List<UploadedFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }

    public List<String> getFotosArmamentos() {
        return fotosArmamentos;
    }

    public void setFotosArmamentos(List<String> fotosArmamentos) {
        this.fotosArmamentos = fotosArmamentos;
    }

    public boolean isB_activo() {
        return b_activo;
    }

    public void setB_activo(boolean b_activo) {
        this.b_activo = b_activo;
    }

}
