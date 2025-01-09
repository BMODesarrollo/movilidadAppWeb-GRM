package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.dto.DocumentosPdDTO;
import com.movilidad.dto.PdPrincipalDTO;
import com.movilidad.ejb.AccidenteAnalisisFacadeLocal;
import com.movilidad.ejb.AccidenteConductorFacadeLocal;
import com.movilidad.ejb.AccidenteDocumentoFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.AccidenteLugarFacadeLocal;
import com.movilidad.ejb.AccidentePlanAccionFacadeLocal;
import com.movilidad.ejb.AccidenteTestigoFacadeLocal;
import com.movilidad.ejb.AccidenteVehiculoFacadeLocal;
import com.movilidad.ejb.AccidenteVictimaFacadeLocal;
import com.movilidad.ejb.PdMaestroFacadeLocal;
import com.movilidad.ejb.PdMaestroSeguimientoFacadeLocal;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteLugar;
import com.movilidad.model.AccidenteLugarDemar;
import com.movilidad.model.PdMaestro;
import com.movilidad.model.PdMaestroSeguimiento;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "pdMaestroSeguimientoBean")
@ViewScoped
public class PdMaestroSeguimientoBean implements Serializable {
    
    //Esta sección de importaciones hace parte de generación reporte accidentalidad
    @EJB
    private AccidenteLugarFacadeLocal accLugarFacadeLocal;
    @EJB
    private AccidenteConductorFacadeLocal accConductorFacadeLocal;
    @EJB
    private AccidenteVehiculoFacadeLocal accVehiculoFacadeLocal;
    @EJB
    private AccidenteDocumentoFacadeLocal accDocumentoFacadeLocal;
    @EJB
    private AccidenteVictimaFacadeLocal accVictimaFacadeLocal;
    @EJB
    private AccidentePlanAccionFacadeLocal accPlanAccionFacadeLocal;
    @EJB
    private AccidenteTestigoFacadeLocal accTestigoFacadeLocal;
    @EJB
    private AccidenteAnalisisFacadeLocal accAnalisisFacadeLocal;
    @EJB
    private PdMaestroFacadeLocal pdMaestroEjb;

    private StreamedContent file;
    //Esta sección de importaciones hace parte de generación reporte accidentalidad

    @EJB
    private PdMaestroSeguimientoFacadeLocal pdMaestroSeguimientoEjb;
    @EJB
    private AccidenteFacadeLocal accidenteEjb;
    @Inject
    private ArchivosJSFManagedBean archivosBean;
    private PdMaestro pdMaestro;
    private PdMaestroSeguimiento seguimiento;
    private DocumentosPdDTO selectedDocumento;
    private PdMaestroSeguimiento selected;
    private PdPrincipalDTO selectedDto;
    private StreamedContent fileDescargar;
    private Accidente accidente;

    private String tamanoNovedadSeguimiento;

    private boolean isEditing;
    private int height = 0;
    private int width = 0;

    private List<DocumentosPdDTO> lstSeguimientos;
    private List<UploadedFile> archivos;
    private List<String> fotosNovedades;
    StreamedContent archivo = new DefaultStreamedContent();

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void cargarSeguimientos() {
        pdMaestro = pdMaestroEjb.find(selectedDto.getIdPdMaestro());
        lstSeguimientos = null;
        this.fotosNovedades = new ArrayList<>();
        archivos = new ArrayList<>();
        lstSeguimientos = pdMaestroSeguimientoEjb.findByIdProceso(pdMaestro.getIdPdMaestro());
    }

    public void nuevo() {
        isEditing = false;
        seguimiento = new PdMaestroSeguimiento();
        selected = null;
        tamanoNovedadSeguimiento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_SEGUIMIENTO_TAMANO);
    }

    public void editar() {

        try {
            if (selectedDocumento.getIdPdMaestroSeguimiento() > 0) {
                seguimiento = new PdMaestroSeguimiento();
                seguimiento.setIdPdMaestroSeguimiento(selectedDocumento.getIdPdMaestroSeguimiento());
                seguimiento.setPath(selectedDocumento.getPath());
                seguimiento.setSeguimiento(selectedDocumento.getSeguimiento());
                seguimiento.setCreado(selectedDocumento.getCreado());

                selected = seguimiento;
            }
        } catch (Exception e) {
        }
        isEditing = true;
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
        seguimiento.setIdPdMaestro(pdMaestro);
        seguimiento.setUsername(user.getUsername());
        seguimiento.setCreado(MovilidadUtil.fechaCompletaHoy());
        seguimiento.setEstadoReg(0);
        pdMaestroSeguimientoEjb.create(seguimiento);

        if (archivos.size() > 0) {
            String pathArchivo;
            for (UploadedFile f : archivos) {
                if (f.getContentType().contains("pdf")) {
                    pathArchivo = Util.saveFile(f, seguimiento.getIdPdMaestroSeguimiento(), "procesoDisciplinario");
                    seguimiento.setPath(pathArchivo);
                    pdMaestroSeguimientoEjb.edit(seguimiento);
                }
                if (f.getContentType().contains("image")) {
                    pathArchivo = Util.saveFile(f, seguimiento.getIdPdMaestroSeguimiento(), "procesoDisciplinario");
                    seguimiento.setPath(pathArchivo);
                    pdMaestroSeguimientoEjb.edit(seguimiento);
                }
            }
            archivos.clear();
        }

        cargarSeguimientos();

        nuevo();
        MovilidadUtil.hideModal("novedadDocumentos");
        MovilidadUtil.addSuccessMessage("Seguimiento registrado éxitosamente.");
    }

    /**
     * Modifica el registro de un seguimiento realizado a un proceso
     */
    public void actualizarSeguimiento() {
        seguimiento.setIdPdMaestro(pdMaestro);
        seguimiento.setUsername(user.getUsername());
        seguimiento.setModificado(MovilidadUtil.fechaCompletaHoy());
        seguimiento.setEstadoReg(0);
        pdMaestroSeguimientoEjb.edit(seguimiento);

        if (archivos.size() > 0) {
            String pathArchivo;
            for (UploadedFile f : archivos) {
                if (f.getContentType().contains("pdf")) {
                    if (seguimiento.getPath() != null) {
                        Util.deleteFile(seguimiento.getPath());
                    }
                    pathArchivo = Util.saveFile(f, seguimiento.getIdPdMaestroSeguimiento(), "procesoDisciplinario");
                    seguimiento.setPath(pathArchivo);
                    pdMaestroSeguimientoEjb.edit(seguimiento);
                }
                if (f.getContentType().contains("image")) {
                    if (seguimiento.getPath() != null) {
                        Util.deleteFile(seguimiento.getPath());
                    }
                    pathArchivo = Util.saveFile(f, seguimiento.getIdPdMaestroSeguimiento(), "procesoDisciplinario");
                    seguimiento.setPath(pathArchivo);
                    pdMaestroSeguimientoEjb.edit(seguimiento);
                }
            }
            archivos.clear();
        }
        cargarSeguimientos();
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
     *
     * @param documentos
     */
    public void getDocumento(DocumentosPdDTO documentos) {
        selected = new PdMaestroSeguimiento();
        selected.setPath(documentos.getPath());
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
        List<String> lstNombresImg = Util.getFileList(selected.getIdPdMaestroSeguimiento(), "procesoDisciplinario");

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

    public StreamedContent getArchivo(String Path, String Name) {
        // Ruta al archivo en tu servidor
        archivo = new DefaultStreamedContent();
        String rutaArchivo = Path;
        try {
            // Lee el contenido del archivo
            byte[] contenido = Files.readAllBytes(Paths.get(rutaArchivo));

            // Determina el tipo MIME del archivo
            String tipoMime = Files.probeContentType(Paths.get(rutaArchivo));
            String ext = Path.substring(Path.lastIndexOf('.'), Path.length());
            Name = Name.replace(".", "_");
            Name = Name + ext;
            // Crea un StreamedContent para el archivo
            InputStream inputStream = new ByteArrayInputStream(contenido);
            archivo = new DefaultStreamedContent(inputStream, tipoMime, Name);
            return archivo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void generarReporteAccidente(Integer idAccidente) throws FileNotFoundException{
        accidente = accidenteEjb.find(idAccidente);
    }
    
    public PdMaestro getPdMaestro() {
        return pdMaestro;
    }

    public void setPdMaestro(PdMaestro pdMaestro) {
        this.pdMaestro = pdMaestro;
    }

    public PdMaestroSeguimiento getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(PdMaestroSeguimiento seguimiento) {
        this.seguimiento = seguimiento;
    }

    public PdMaestroSeguimiento getSelected() {
        return selected;
    }

    public void setSelected(PdMaestroSeguimiento selected) {
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

    public List<DocumentosPdDTO> getLstSeguimientos() {
        return lstSeguimientos;
    }

    public void setLstSeguimientos(List<DocumentosPdDTO> lstSeguimientos) {
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

    public StreamedContent getArchivo() {
        return archivo;
    }

    public void setArchivo(StreamedContent archivo) {
        this.archivo = archivo;
    }

    public DocumentosPdDTO getSelectedDocumento() {
        return selectedDocumento;
    }

    public void setSelectedDocumento(DocumentosPdDTO selectedDocumento) {
        this.selectedDocumento = selectedDocumento;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public PdPrincipalDTO getSelectedDto() {
        return selectedDto;
    }

    public void setSelectedDto(PdPrincipalDTO selectedDto) {
        this.selectedDto = selectedDto;
    }

    public Accidente getAccidente() {
        return accidente;
    }

    public void setAccidente(Accidente accidente) {
        this.accidente = accidente;
    }
    
    
       

}
