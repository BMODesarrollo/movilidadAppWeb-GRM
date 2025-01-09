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
 * @author Omar.beltran
 */
@Named(value = "uploadDocumentJSFManagedBean")
@SessionScoped
public class UploadDocumentJSFManagedBean implements Serializable {

    private UploadedFile file;

    private StreamedContent imagen;

    private String updateComponent;
    private String path;
    private boolean flag = false;
    private List<String> listFiles = new ArrayList<>();
    private String modalHeader;
    private String modal;

    /**
     * Creates a new instance of UploadFotoJSFManagedBean
     */
    public UploadDocumentJSFManagedBean() {
    }

    @PostConstruct
    public void init() {
        file = null;
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        PrimeFaces current = PrimeFaces.current();
        current.executeScript(modal);
        if (updateComponent != null && !updateComponent.isEmpty()) {
            flag = true;
            current.ajax().update(updateComponent);
        }
        System.out.println("Cerrar");
        PrimeFaces.current().executeScript("PF('UploadFileDialog').hide()");
    }

    private String generateRandomIdForNotCaching() {
        return java.util.UUID.randomUUID().toString();
    }

    public StreamedContent getDocument() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            try {
                return Util.mostrarDocumento(path, generateRandomIdForNotCaching());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return new DefaultStreamedContent();
    }

    public StreamedContent getDinamicImage() {

        FacesContext context = FacesContext.getCurrentInstance();
        String pathT;
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            pathT = context.getExternalContext().getRequestParameterMap().get("path_foto");
            try {
                return MovilidadUtil.mostrarImagen(pathT);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return new DefaultStreamedContent();
    }

    public StreamedContent showImage(String path) throws Exception {
        if (path != null) {
            return Util.mostrarImagen(path);
        } else {
            MovilidadUtil.addAdvertenciaMessage("Path is empty");
        }
        return null;
    }

    public String saveDocument(int id, String ruta, String fileName) throws IOException {
        String pathT = "";
        if (file != null && id != 0) {
            pathT = MovilidadUtil.subirFichero("ruta", file, Integer.toString(id), ruta, fileName);
        }
        return pathT;
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

    public String getUpdateComponent() {
        return updateComponent;
    }

    public void setUpdateComponent(String updateComponent) {
        this.updateComponent = updateComponent;
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

    public List<String> getListFiles() {
        return listFiles;
    }

    public void setListFiles(List<String> listFiles) {
        this.listFiles = listFiles;
    }

}
