package com.movilidad.jsf;

import com.movilidad.ejb.GenericaPdMaestroSeguimientoFacadeLocal;
import com.movilidad.model.GenericaPdMaestro;
import com.movilidad.model.GenericaPdMaestroSeguimiento;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
 * @author Carlos Ballestas
 */
@Named(value = "genericaPdMaestroSeguimientoBean")
@ViewScoped
public class GenericaPdMaestroSeguimientoBean implements Serializable {

    @EJB
    private GenericaPdMaestroSeguimientoFacadeLocal pdMaestroSeguimientoEjb;
    @Inject
    private ArchivosJSFManagedBean archivosBean;

    private GenericaPdMaestro pdMaestro;
    private GenericaPdMaestroSeguimiento seguimiento;
    private GenericaPdMaestroSeguimiento selected;
    private StreamedContent fileDescargar;

    private String tamanoNovedadSeguimiento;

    private boolean isEditing;
    private int height = 0;
    private int width = 0;

    private List<GenericaPdMaestroSeguimiento> lstSeguimientos;
    private List<UploadedFile> archivos;
    private List<String> fotosNovedades;

    private final UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void cargarSeguimientos() {
        this.fotosNovedades = new ArrayList<>();
        lstSeguimientos = pdMaestroSeguimientoEjb.findByIdProceso(pdMaestro.getIdGenericaPdMaestro());
    }

    public void nuevo() {
        isEditing = false;
        archivos = new ArrayList<>();
        seguimiento = new GenericaPdMaestroSeguimiento();
        selected = null;
        tamanoNovedadSeguimiento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_SEGUIMIENTO_TAMANO);
    }

    public void editar() {
        isEditing = true;
        archivos = new ArrayList<>();
        seguimiento = selected;
    }

    /**
     * Persiste en base de datos un seguimiento realizado a una novedad, y lo
     * agrega al listado de seguimientos
     */
    public void guardarSeguimiento() {
        if (pdMaestro == null) {
            MovilidadUtil.addAdvertenciaMessage("Debe seleccionar una proceso para aplicarle seguimiento. ");
            return;
        }
        seguimiento.setIdGenericaPdMaestro(pdMaestro);
        seguimiento.setUsername(user.getUsername());
        seguimiento.setCreado(MovilidadUtil.fechaCompletaHoy());
        seguimiento.setEstadoReg(0);
        pdMaestroSeguimientoEjb.create(seguimiento);

        if (archivos.size() > 0) {
            String pathArchivo;
            for (UploadedFile f : archivos) {
                if (f.getContentType().contains("pdf")) {
                    pathArchivo = Util.saveFile(f, seguimiento.getIdGenericaPdMaestroSeguimiento(), "procesoDisciplinarioGen");
                    seguimiento.setPath(pathArchivo);
                    pdMaestroSeguimientoEjb.edit(seguimiento);
                }
                if (f.getContentType().contains("image")) {
                    pathArchivo = Util.saveFile(f, seguimiento.getIdGenericaPdMaestroSeguimiento(), "procesoDisciplinarioGen");
                    seguimiento.setPath(pathArchivo);
                    pdMaestroSeguimientoEjb.edit(seguimiento);
                }
            }
            archivos.clear();
        }

        lstSeguimientos.add(seguimiento);

        nuevo();
        MovilidadUtil.hideModal("novedadDocumentos");
        MovilidadUtil.addSuccessMessage("Seguimiento registrado éxitosamente.");
    }

    /**
     * Modifica el registro de un seguimiento realizado a un proceso
     */
    public void actualizarSeguimiento() {
        seguimiento.setIdGenericaPdMaestro(pdMaestro);
        seguimiento.setUsername(user.getUsername());
        seguimiento.setCreado(MovilidadUtil.fechaCompletaHoy());
        seguimiento.setEstadoReg(0);
        pdMaestroSeguimientoEjb.edit(seguimiento);

        if (archivos.size() > 0) {
            String pathArchivo;
            if (seguimiento.getPath() != null) {
                if (!seguimiento.getPath().endsWith("/")) {
                    Util.deleteFile(seguimiento.getPath());
                }
//                } else {
//                    borrarImagenesSeguimiento(seguimiento.getIdGenericaPdMaestroSeguimiento(), seguimiento.getPath());
//                }
            }
            for (UploadedFile f : archivos) {
                if (f.getContentType().contains("pdf")) {
                    pathArchivo = Util.saveFile(f, seguimiento.getIdGenericaPdMaestroSeguimiento(), "procesoDisciplinarioGen");
                    seguimiento.setPath(pathArchivo);
                    pdMaestroSeguimientoEjb.edit(seguimiento);
                }
                if (f.getContentType().contains("image")) {
                    pathArchivo = Util.saveFile(f, seguimiento.getIdGenericaPdMaestroSeguimiento(), "procesoDisciplinarioGen");
                    seguimiento.setPath(pathArchivo);
                    pdMaestroSeguimientoEjb.edit(seguimiento);
                }
            }
            archivos.clear();
        }

        MovilidadUtil.hideModal("novedadDocumentos");
        MovilidadUtil.addSuccessMessage("Seguimiento actualizado éxitosamente.");
    }

    /**
     *
     * Evento que dispara la subida de archivos para la anexarlos a un
     * seguimiento de un proceso
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

        current.executeScript("PF('AddFilesListDialog').hide()");
        MovilidadUtil.updateComponent(":msgs");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Archivo agregado éxitosamente."));
    }

    /**
     * Realiza la muestra de pdf,imágenes,lista de imágenes de un documento que
     * halla sido anexado a una novedad
     */
    public void getDocumento() {
        String ext = "";
        fotosNovedades.clear();
        if (!selected.getPath().endsWith("/")) {
            ext = selected.getPath().substring(selected.getPath().lastIndexOf('.'), selected.getPath().length());
            if (!ext.equals(".pdf")) {
                try {
                    Image i = Util.mostrarImagenN(selected.getPath());
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
            archivosBean.setModalHeader("Seguimiento de novedad");
            archivosBean.setPath(selected.getPath());
        } else {
            try {
                obtenerFotosNovedad();
                archivosBean.setExtension(ext);
                archivosBean.setPath(selected.getPath());
                archivosBean.setModalHeader("Seguimiento de novedad");
            } catch (IOException ex) {
                Logger.getLogger(NovedadJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Obtiene fotos de una novedad
     *
     * @throws IOException
     */
    public void obtenerFotosNovedad() throws IOException {
        List<String> lstNombresImg = Util.getFileList(selected.getIdGenericaPdMaestroSeguimiento(), "procesoDisciplinarioGen");

        if (lstNombresImg != null) {
            width = 100;
            height = 100;
            for (String f : lstNombresImg) {
                fotosNovedades.add(f);
                Image i = Util.mostrarImagenN(f, selected.getPath());
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

    private void borrarImagenesSeguimiento(Integer idSeguimiento, String path) {
        List<String> lstImagenes = Util.getFileList(idSeguimiento, "procesoDisciplinarioGen");

        if (lstImagenes != null) {
            for (String imagen : lstImagenes) {
                System.out.println(imagen);
                String ruta = path + imagen;
                Util.deleteFile(ruta);
            }
        }

    }

    public GenericaPdMaestro getGenericaPdMaestro() {
        return pdMaestro;
    }

    public void setGenericaPdMaestro(GenericaPdMaestro pdMaestro) {
        this.pdMaestro = pdMaestro;
    }

    public GenericaPdMaestroSeguimiento getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(GenericaPdMaestroSeguimiento seguimiento) {
        this.seguimiento = seguimiento;
    }

    public GenericaPdMaestroSeguimiento getSelected() {
        return selected;
    }

    public void setSelected(GenericaPdMaestroSeguimiento selected) {
        this.selected = selected;
    }

    public StreamedContent getFileDescargar() {
        return fileDescargar;
    }

    public void setFileDescargar(StreamedContent fileDescargar) {
        this.fileDescargar = fileDescargar;
    }

    public String getTamanoNovedadSeguimiento() {
        return tamanoNovedadSeguimiento;
    }

    public void setTamanoNovedadSeguimiento(String tamanoNovedadSeguimiento) {
        this.tamanoNovedadSeguimiento = tamanoNovedadSeguimiento;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<GenericaPdMaestroSeguimiento> getLstSeguimientos() {
        return lstSeguimientos;
    }

    public void setLstSeguimientos(List<GenericaPdMaestroSeguimiento> lstSeguimientos) {
        this.lstSeguimientos = lstSeguimientos;
    }

    public List<UploadedFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }

    public List<String> getFotosNovedades() {
        return fotosNovedades;
    }

    public void setFotosNovedades(List<String> fotosNovedades) {
        this.fotosNovedades = fotosNovedades;
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
