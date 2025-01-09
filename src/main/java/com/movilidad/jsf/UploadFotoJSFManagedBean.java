/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "uploadFotoJSFManagedBean")
@SessionScoped
public class UploadFotoJSFManagedBean implements Serializable {

    private UploadedFile file;

    private StreamedContent imagen;

    private String CompoUpdate;
    private String path;
    private boolean flag = false;
    private List<String> listFotos = new ArrayList<>();
    private String modalHeader;
    private String modal;

    /**
     * Creates a new instance of UploadFotoJSFManagedBean
     */
    public UploadFotoJSFManagedBean() {
    }

    @PostConstruct
    public void init() {
        file = null;
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        PrimeFaces current = PrimeFaces.current();
        current.executeScript(modal);
        if (!CompoUpdate.isEmpty() && CompoUpdate != null) {
            flag = true;
            current.ajax().update(CompoUpdate);
        }
        System.out.println("Cerrar");
        PrimeFaces.current().executeScript("PF('UploadFotoDialog').hide()");
    }

    private String generateRandomIdForNotCaching() {
        return java.util.UUID.randomUUID().toString();
    }

    public StreamedContent getDocumento() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            try {
                return Util.mostrarDocumento(path, generateRandomIdForNotCaching());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return new DefaultStreamedContent();
    }

    public StreamedContent getImagenDinamica() {

        FacesContext context = FacesContext.getCurrentInstance();
        String path;
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            path = context.getExternalContext().getRequestParameterMap().get("path_foto");
            try {
                return MovilidadUtil.mostrarImagen(path);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return new DefaultStreamedContent();
    }

    public StreamedContent mostrarImagen(String path) throws Exception {
        if (path != null) {
            return Util.mostrarImagen(path);
        } else {
            MovilidadUtil.addAdvertenciaMessage("no hay Path");
        }
        return null;
    }

    public String GuardarFoto(int id, String ruta, String codigoVhcl) throws IOException {
        String path = "";
        if (file != null && id != 0) {
            path = MovilidadUtil.subirFichero("ruta", file, Integer.toString(id), ruta, codigoVhcl);
        }
        return path;
    }

    public String GuardarFotoEmpleado(String cedula) throws IOException {
        String path = "";
        if (file != null && !cedula.isEmpty()) {
            path = MovilidadUtil.subirFotoEmpleado("ruta", file, cedula);
        }
        return path;
    }
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public StreamedContent getImagen() {
        return imagen;
    }

    public void setImagen(StreamedContent imagen) {
        this.imagen = imagen;
    }

    public String getCompoUpdate() {
        return CompoUpdate;
    }

    public void setCompoUpdate(String CompoUpdate) {
        this.CompoUpdate = CompoUpdate;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getListFotos() {
        return listFotos;
    }

    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }

}
