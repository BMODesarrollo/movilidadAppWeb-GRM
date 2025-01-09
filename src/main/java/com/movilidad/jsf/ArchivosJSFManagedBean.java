package com.movilidad.jsf;

import com.movilidad.model.GenericaDocumentos;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadDocumentos;
import com.movilidad.model.SegRegistroArmamento;
import com.movilidad.model.SegRegistroArmamentoDoc;
import com.movilidad.utils.Util;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "archivosBean")
@SessionScoped
public class ArchivosJSFManagedBean implements Serializable {

    private String modalHeader;
    private String path;
    private String extension;
    private SegRegistroArmamento selectedArmamento;
    private SegRegistroArmamentoDoc selectedArmamentoDoc;
    private Novedad selectedNovedad;
    private NovedadDocumentos selectedDocumento;
    private GenericaDocumentos genericaDocumento;

    @PostConstruct
    public void init() {
        modalHeader = "";
        path = "";
        extension = "";
        selectedArmamento = null;
        selectedArmamentoDoc = null;
        selectedDocumento = null;
        genericaDocumento = null;
        selectedNovedad = null;
    }

    /**
     * Renderiza documento PDF en la vista que muestra los documentos
     *
     * @return
     */
    public StreamedContent getDocumento() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (path != null & modalHeader != null) {

            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                return new DefaultStreamedContent();
            } else {
                try {
                    return Util.mostrarDocumento(path, modalHeader);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return new DefaultStreamedContent();
    }

    /**
     *
     * @return
     */
    public StreamedContent getImagenCompleta() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (path != null & modalHeader != null) {

            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                return new DefaultStreamedContent();
            } else {
                try {
                    return Util.mostrarDocumento(path, modalHeader);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return new DefaultStreamedContent();
    }

    /**
     * Renderiza carrusel de imágenes en la vista que muestra los documentos
     *
     * @return
     */
    public StreamedContent getImagenDinamica() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedDocumento != null) {
            String nombre_imagen;
            String pathImagen = selectedDocumento.getPathDocumento();

            if (pathImagen != null) {
                if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                    return new DefaultStreamedContent();
                } else {
                    nombre_imagen = context.getExternalContext().getRequestParameterMap().get("nombre_imagen");
                    try {
                        return Util.mostrarImagen(nombre_imagen, pathImagen);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return new DefaultStreamedContent();
    }

    /**
     * Renderiza carrusel de imágenes en la vista que muestra los documentos
     * (Novedades genéricas)
     *
     * @return
     */
    public StreamedContent getImagenDinamicaGenerica() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (genericaDocumento != null) {
            String nombre_imagen;
            String pathImagen = genericaDocumento.getPathDocumento();

            if (pathImagen != null) {
                if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                    return new DefaultStreamedContent();
                } else {
                    nombre_imagen = context.getExternalContext().getRequestParameterMap().get("nombre_imagen");
                    try {
                        return Util.mostrarImagen(nombre_imagen, pathImagen);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return new DefaultStreamedContent();
    }

    /**
     * Renderiza imágenes realacionadas a novedades de daño en la vista que
     * muestra los documentos
     *
     * @return
     */
    public StreamedContent getImagenDano() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedNovedad != null) {
            String nombre_imagen;
            String pathImagen = selectedNovedad.getIdNovedadDano().getPathFotos();

            if (pathImagen != null) {
                if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                    return new DefaultStreamedContent();
                } else {
                    nombre_imagen = context.getExternalContext().getRequestParameterMap().get("nombre_imagen");
                    try {
                        return Util.mostrarImagen(nombre_imagen, pathImagen);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return new DefaultStreamedContent();
    }

    /**
     * Renderiza imágenes realacionadas a un registro de un armamento en la
     * vista que muestra la galeria de imágenes
     *
     * @return
     */
    public StreamedContent getImagenArmamento() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedArmamento != null) {
            String nombre_imagen;
            String pathImagen = selectedArmamento.getPathFoto();

            if (pathImagen != null) {
                if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                    return new DefaultStreamedContent();
                } else {
                    nombre_imagen = context.getExternalContext().getRequestParameterMap().get("nombre_imagen");
                    try {
                        return Util.mostrarImagen(nombre_imagen, pathImagen);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return new DefaultStreamedContent();
    }

    /**
     * Renderiza imágenes realacionadas a un registro de estado de un armamento
     * en la vista que muestra la galeria de imágenes
     *
     * @return
     */
    public StreamedContent getGaleriaImagenes() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (path != null) {
            String nombre_imagen;

            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                return new DefaultStreamedContent();
            } else {
                nombre_imagen = context.getExternalContext().getRequestParameterMap().get("nombre_imagen");
                try {
                    return Util.mostrarImagen(nombre_imagen, path);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return new DefaultStreamedContent();
    }

    /**
     * Reinicia a su valor por defecto los campos requeridos para la carga de
     * imágenes/PDF en la vista modal
     */
    public void reset() {
        extension = "";
        modalHeader = "";
        path = "";
    }

    public String getModalHeader() {
        return modalHeader;
    }

    public void setModalHeader(String modalHeader) {
        this.modalHeader = modalHeader;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public NovedadDocumentos getSelectedDocumento() {
        return selectedDocumento;
    }

    public void setSelectedDocumento(NovedadDocumentos selectedDocumento) {
        this.selectedDocumento = selectedDocumento;
    }

    public Novedad getSelectedNovedad() {
        return selectedNovedad;
    }

    public void setSelectedNovedad(Novedad selectedNovedad) {
        this.selectedNovedad = selectedNovedad;
    }

    public GenericaDocumentos getGenericaDocumento() {
        return genericaDocumento;
    }

    public void setGenericaDocumento(GenericaDocumentos genericaDocumento) {
        this.genericaDocumento = genericaDocumento;
    }

    public SegRegistroArmamento getSelectedArmamento() {
        return selectedArmamento;
    }

    public void setSelectedArmamento(SegRegistroArmamento selectedArmamento) {
        this.selectedArmamento = selectedArmamento;
    }

    public SegRegistroArmamentoDoc getSelectedArmamentoDoc() {
        return selectedArmamentoDoc;
    }

    public void setSelectedArmamentoDoc(SegRegistroArmamentoDoc selectedArmamentoDoc) {
        this.selectedArmamentoDoc = selectedArmamentoDoc;
    }

}
