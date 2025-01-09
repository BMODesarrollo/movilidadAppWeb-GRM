package com.movilidad.jsf;

import com.movilidad.ejb.PqrMaestroDocumentosFacadeLocal;
import com.movilidad.model.PqrMaestro;
import com.movilidad.model.PqrMaestroDocumentos;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "pqrMaestroDocBean")
@ViewScoped
public class PqrMaestroDocBean implements Serializable {

    @EJB
    private PqrMaestroDocumentosFacadeLocal documentoEjb;

    @Inject
    private MaestroPqrBean prqMaestroBean;
    @Inject
    private ArchivosJSFManagedBean archivosBean;

    private PqrMaestroDocumentos documento;
    private PqrMaestroDocumentos selected;
    private PqrMaestro pqrMaestro;

    private int height = 0;
    private int width = 0;
    private String tamanoNovedadDocumento;
    private boolean isEditing;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private List<PqrMaestroDocumentos> lstDocumentos;
    private List<UploadedFile> archivos;
    private List<String> fotosNovedades;

    public void nuevo() {
        isEditing = false;
        documento = new PqrMaestroDocumentos();
        tamanoNovedadDocumento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_SST_EMPRESA_TAMANO);
        selected = null;
    }

    /**
     *
     * @return true si el usuario logueado es quien creo el registro, de lo
     * contrario retorna false.
     */
    public boolean esAlterable() {
        if (selected == null) {
            return false;
        }
        if (selected.getUsuario().equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    public void editar() {

        if (!esAlterable()) {
            MovilidadUtil.addErrorMessage("No es posible modificar éste registro");
            return;
        }

        isEditing = true;
        documento = selected;

        MovilidadUtil.openModal("novedadDocumentos");

    }

    public void prepararModulo() {

        MovilidadUtil.openModal("NovedadDocumentosListDialog");

        pqrMaestro = prqMaestroBean.getPqrMaestro();
        fotosNovedades = new ArrayList<>();
        archivos = new ArrayList<>();

        lstDocumentos = documentoEjb.findByIdPqrMaestro(pqrMaestro.getIdPqrMaestro());
    }

    public void guardar() {
        documento.setIdPqrMaestro(pqrMaestro);
        documento.setUsuario(user.getUsername());
        documento.setTipoDocumento(0);

        if (isEditing) {
            documento.setModificado(MovilidadUtil.fechaCompletaHoy());
            documentoEjb.edit(documento);

            if (archivos.size() > 0) {
                String pathArchivo;
                for (UploadedFile f : archivos) {
                    if (!documento.getPathDocumento().endsWith("/")) {
                        Util.deleteFile(documento.getPathDocumento());
                    }
                    pathArchivo = Util.saveFile(f, pqrMaestro.getIdPqrMaestro(), "pqr");
                    documento.setPathDocumento(pathArchivo);
                    documentoEjb.edit(documento);
                }
                archivos.clear();
            }

            MovilidadUtil.addSuccessMessage("Registro modificado con éxito");
            MovilidadUtil.hideModal("novedadDocumentos");
        } else {
            documento.setEstadoReg(0);
            documento.setCreado(MovilidadUtil.fechaCompletaHoy());
            documentoEjb.create(documento);

            if (archivos.size() > 0) {
                String pathArchivo;
                for (UploadedFile f : archivos) {
                    pathArchivo = Util.saveFile(f, pqrMaestro.getIdPqrMaestro(), "pqr");
                    documento.setPathDocumento(pathArchivo);
                    documentoEjb.edit(documento);
                }
                archivos.clear();
            }

            lstDocumentos.add(documento);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado con éxito");
        }

        prqMaestroBean.consultar();
    }

    public String retornarTipoArchivo(String path) {

        List<String> extensions = Arrays.asList("jpg", "jpeg", "png", "JPG", "JPEG", ".PNG");

        if (path.endsWith("/")) {
            return "IMÁGENES";
        } else if (path.endsWith(".PDF") || path.endsWith(".pdf")) {
            return "PDF";
        } else if (extensions.contains(path.substring(path.lastIndexOf(".") + 1))) {
            return "IMÁGEN";
        }

        return "";
    }

    /**
     *
     * Evento que dispara la subida de archivos para la anexarlos a un un
     * documento de pqrMaestro
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();

        if (event.getFile().getFileName().length() > 50) {
            MovilidadUtil.updateComponent(":msgs");
            MovilidadUtil.addErrorMessage("El nombre de archivo DEBE ser MENOR 50 a caracteres");
            return;
        }

        archivos.add(event.getFile());

        current.executeScript("PF('AddFilesDocumentosDialog').hide()");
        MovilidadUtil.updateComponent(":msgs");
        MovilidadUtil.addSuccessMessage("Archivo agregado éxitosamente.");
    }

    /**
     * Realiza la muestra de pdf,imágenes,lista de imágenes de un documento que
     * halla sido anexado a una pqrMaestro
     */
    public void obtenerDocumento() {
        String ext = "";
        fotosNovedades.clear();
        if (!selected.getPathDocumento().endsWith("/")) {
            ext = selected.getPathDocumento().substring(selected.getPathDocumento().lastIndexOf('.'), selected.getPathDocumento().length());
            if (!ext.equals(".pdf")) {
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
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                width = 700;
                height = 500;
            }
            archivosBean.setExtension(ext);
            archivosBean.setModalHeader("Documento de novedad");
            archivosBean.setPath(selected.getPathDocumento());
        } else {
            try {
                obtenerFotosNovedad();
                archivosBean.setExtension(ext);
                archivosBean.setPath(selected.getPathDocumento());
                archivosBean.setModalHeader("Documento de novedad");
            } catch (IOException ex) {
                Logger.getLogger(NovedadJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Obtiene fotos de una pqrMaestro
     *
     * @throws IOException
     */
    public void obtenerFotosNovedad() throws IOException {
        List<String> lstNombresImg = Util.getFileList(pqrMaestro.getIdPqrMaestro(), "pqr");

        if (lstNombresImg != null) {
            width = 100;
            height = 100;
            for (String f : lstNombresImg) {

                if (!f.endsWith(".pdf") || !f.endsWith(".PDF")) {
                    fotosNovedades.add(f);
                    Image i = Util.mostrarImagenN(f, selected.getPathDocumento());
                    if (i != null) {
                        if (width < i.getWidth(null)) {
                            width = i.getWidth(null);
                        }
                        if (height < i.getHeight(null)) {
                            height = i.getHeight(null);
                        }
                    }
                }

            }
        }
    }

    public boolean validarEditarDocumento(String userName) {
        if (user.getUsername().equals(userName)) {
            return false;
        }
        for (GrantedAuthority g : user.getAuthorities()) {
            if (g.getAuthority().equals("ROLE_PROFOP")) {
                return false;
            }
        }
        return true;
    }

    public PqrMaestroDocumentos getDocumento() {
        return documento;
    }

    public void setDocumento(PqrMaestroDocumentos documento) {
        this.documento = documento;
    }

    public PqrMaestroDocumentos getSelected() {
        return selected;
    }

    public void setSelected(PqrMaestroDocumentos selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<PqrMaestroDocumentos> getLstDocumentos() {
        return lstDocumentos;
    }

    public void setLstDocumentos(List<PqrMaestroDocumentos> lstDocumentos) {
        this.lstDocumentos = lstDocumentos;
    }

    public PqrMaestro getPqrMaestro() {
        return pqrMaestro;
    }

    public void setPqrMaestro(PqrMaestro pqrMaestro) {
        this.pqrMaestro = pqrMaestro;
    }

    public List<String> getFotosNovedades() {
        return fotosNovedades;
    }

    public void setFotosNovedades(List<String> fotosNovedades) {
        this.fotosNovedades = fotosNovedades;
    }

    public String getTamanoNovedadDocumento() {
        return tamanoNovedadDocumento;
    }

    public void setTamanoNovedadDocumento(String tamanoNovedadDocumento) {
        this.tamanoNovedadDocumento = tamanoNovedadDocumento;
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

}
