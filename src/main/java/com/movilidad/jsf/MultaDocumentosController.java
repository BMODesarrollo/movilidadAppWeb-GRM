package com.movilidad.jsf;

import com.movilidad.ejb.MultaDocumentosFacadeLocal;
import com.movilidad.model.Multa;
import com.movilidad.model.MultaDocumentos;
import com.movilidad.model.MultaTipoDocumentos;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.JsfUtil.PersistAction;
import com.movilidad.utils.MovilidadUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("multaDocumentosController")
@ViewScoped
public class MultaDocumentosController implements Serializable {

    @EJB
    private MultaDocumentosFacadeLocal MultaDEJB;
    private List<MultaDocumentos> items = null;
    private MultaDocumentos selected;
    private UploadedFile fileUpload;
    private List<UploadedFile> fileList;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    @Inject
    private UploadFotoJSFManagedBean uploadFotoMB;

    //----
    private int i_idMulta;
    private int i_idMultaTipoDocumento;

    @PostConstruct
    public void init() {
        this.fileList = new ArrayList<>();
    }

    public void openDialogFileUpLoad() {
        PrimeFaces.current().ajax().update("formPDF");
        uploadFotoMB.setFlag(false);
        uploadFotoMB.setCompoUpdate("MultaDocumentosCreateForm:idFULOP");
        uploadFotoMB.setFile(null);
        uploadFotoMB.setModal("PF('UploadPDFDialog').hide();");
        PrimeFaces.current().executeScript("PF('UploadPDFDialog').show();");
    }

    public MultaDocumentos prepareCreate() {
        selected = new MultaDocumentos();
        fileList.clear();
        return selected;
    }

    public void prepDownloadLocal(String path) throws Exception {
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    public void create() {
        persist(PersistAction.CREATE, "Documento de la Multa se creó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Documento de la Multa no se creó correctamente");
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Documento de la Multa se actualizó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Documento de la Multa no se actualizó correctamente");
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Documento de la Multa se eliminó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Documento de la Multa no se eliminó correctamente");
        }
    }

    public void cargarIdMulta(int i_aux) {
        i_idMulta = i_aux;
        items = MultaDEJB.idMultaEstadoRegistro(i_aux);
        fileList.clear();
    }

    public void reset() {
        selected = new MultaDocumentos();
        i_idMultaTipoDocumento = 0;
        items = MultaDEJB.idMultaEstadoRegistro(i_idMulta);
        fileUpload = null;
    }

    public void cargarEditar() {
        i_idMultaTipoDocumento = selected.getIdMultaTipoDocumento().getIdMultaTipoDocumento();
    }

    void guardarEditar() {
        MultaTipoDocumentos multaTipoDocumento = new MultaTipoDocumentos();
        multaTipoDocumento.setIdMultaTipoDocumento(i_idMultaTipoDocumento);
        selected.setIdMultaTipoDocumento(multaTipoDocumento);
    }

    void cargar() {
        Multa multa = new Multa();
        MultaTipoDocumentos multaTipoDocumento = new MultaTipoDocumentos();
        multa.setIdMulta(i_idMulta);
        multaTipoDocumento.setIdMultaTipoDocumento(i_idMultaTipoDocumento);
        selected.setIdMulta(multa);
        selected.setIdMultaTipoDocumento(multaTipoDocumento);
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        cargar();
                        getSelected().setModificado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setUsuario(user.getUsername());
                        getSelected().setCreado(new Date());
                        MultaDEJB.create(selected);
                        String path_documento = " ";
                        if (uploadFotoMB.getFile() != null) {
                            path_documento = uploadFotoMB.GuardarFoto(selected.getIdMulta().getIdMulta(), "multa_documentos", "");
                            selected.setPathDocumento(path_documento);
                            MultaDEJB.edit(selected);
                            reset();
                            JsfUtil.addSuccessMessage(successMessage);
                        } else {
                            MovilidadUtil.addAdvertenciaMessage("No hay archivo cargado");
                        }
                        break;
                    case DELETE:
                        getSelected().setModificado(new Date());
                        getSelected().setEstadoReg(1);
                        MultaDEJB.edit(selected);
                        reset();
                        JsfUtil.addSuccessMessage(successMessage);
                        break;
                    case UPDATE:
                        String path_documentoEdit = " ";
                        if (uploadFotoMB.getFile() != null) {
                            path_documentoEdit = uploadFotoMB.GuardarFoto(selected.getIdMulta().getIdMulta(), "multa_documentos","");
                            selected.setPathDocumento(path_documentoEdit);
                        }
                        guardarEditar();
                        getSelected().setUsuario(user.getUsername());
                        MultaDEJB.edit(selected);
                        reset();
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('MultaDocumentosEditDialog').hide();");
                        JsfUtil.addSuccessMessage(successMessage);
                        break;
                    default:
                        break;
                }

            } catch (EJBException ex) {
                JsfUtil.addErrorMessage("Error del sistema");
            } catch (Exception ex) {
                JsfUtil.addErrorMessage("Error del sistema");
            }
        }
    }

    public MultaDocumentos getSelected() {
        return selected;
    }

    public void setSelected(MultaDocumentos selected) {
        this.selected = selected;
    }

    public int getI_idMulta() {
        return i_idMulta;
    }

    public void setI_idMulta(int i_idMulta) {
        this.i_idMulta = i_idMulta;
    }

    public int getI_idMultaTipoDocumento() {
        return (Integer) i_idMultaTipoDocumento;
    }

    public void setI_idMultaTipoDocumento(int i_idMultaTipoDocumento) {
        this.i_idMultaTipoDocumento = i_idMultaTipoDocumento;
    }

    public UploadedFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(UploadedFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    public List<UploadedFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadedFile> fileList) {
        this.fileList = fileList;
    }


    public List<MultaDocumentos> getItems() {
        return items;
    }

}
