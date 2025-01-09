/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadSeguimientoDocsFacadeLocal;
import com.movilidad.ejb.NovedadSeguimientoFacadeLocal;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadSeguimiento;
import com.movilidad.model.NovedadSeguimientoDocs;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "novSeguiBean")
@ViewScoped
public class NovedadSeguimientoBean implements Serializable {

    /**
     * Creates a new instance of NovedadSeguimientoBean
     */
    public NovedadSeguimientoBean() {
    }

    @Inject
    private ClasificacionNovedadBean clasificacionNovedadBean;

    @EJB
    private NovedadSeguimientoFacadeLocal novedadSeguimientoEjb;
    @EJB
    private NovedadSeguimientoDocsFacadeLocal novedadSeguimientoDocsEjb;

    private List<NovedadSeguimiento> lstSeguimientos;
    private List<UploadedFile> archivos;

    private NovedadSeguimiento selectedSeguimiento;
    private NovedadSeguimiento novedadSeguimiento;
    private NovedadSeguimientoDocs novedadSeguimientoDoc;
    private Novedad selected;
    private StreamedContent fileDescargar;
    private String tamanoNovedadSeguimiento;
    private boolean flagEditarArchivoSegumiento;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void nuevo() {
        if (clasificacionNovedadBean.getSelected() == null) {
            return;
        }
        novedadSeguimiento = new NovedadSeguimiento();
        novedadSeguimiento.setFecha(MovilidadUtil.fechaHoy());
        novedadSeguimiento.setIdNovedad(clasificacionNovedadBean.getSelected());
        MovilidadUtil.openModal("seguiNewWV");
    }

    public void seguimientos(Novedad param) {
        if (param == null) {
            return;
        }
        clasificacionNovedadBean.setSelected(param);
        selected = param;
        lstSeguimientos = novedadSeguimientoEjb.findByNovedad(selected.getIdNovedad());
//        System.out.println("lstSeguimientos->" + lstSeguimientos.size());
    }

    /**
     * Carga datos de una novedad en la vista de edición
     */
    public void editar() {
        if (selectedSeguimiento != null) {
            this.novedadSeguimiento = this.selectedSeguimiento;
        }
    }

    /**
     * Realiza la descarga del archivo para mostrar el archivo en la vista
     *
     * @param path
     * @param nombreArchivo
     * @throws Exception
     */
    public void prepDownload(String path, String nombreArchivo) throws Exception {
        String fe = "";
        InputStream stream;
        File archivo = new File(path);
        int i = path.lastIndexOf('.');
        if (i > 0) {
            fe = path.substring(i + 1).toLowerCase();
            switch (fe) {
                case "mp3":
                    stream = new FileInputStream(archivo);
                    fileDescargar = new DefaultStreamedContent(stream, "audio/mpeg", nombreArchivo);
                    break;
                case "mp4":
                    stream = new FileInputStream(archivo);
                    fileDescargar = new DefaultStreamedContent(stream, "video/mp4", nombreArchivo);
                    break;
            }
        }
    }

    /**
     * Persiste en base de datos un seguimiento realizado a una novedad, y lo
     * agrega al listado de seguimientos
     */
    public void guardarSeguimiento() {
        if (selected == null) {
            MovilidadUtil.addAdvertenciaMessage("Debe seleccionar una novedad para aplicarle seguimiento. ");
            return;
        }
        novedadSeguimiento.setUsername(user.getUsername());
        novedadSeguimiento.setCreado(MovilidadUtil.fechaCompletaHoy());
        novedadSeguimiento.setEstadoReg(0);
        this.novedadSeguimientoEjb.create(novedadSeguimiento);

        if (archivos != null && archivos.size() > 0) {
            NovedadSeguimientoDocs doc;
            String pathArchivo;
            for (UploadedFile f : archivos) {
                doc = new NovedadSeguimientoDocs();
                doc.setNombreArchivo(f.getFileName());
                doc.setIdNovedadSeguimiento(novedadSeguimiento);
                doc.setUsername(user.getUsername());
                doc.setEstadoReg(0);
                doc.setCreado(MovilidadUtil.fechaCompletaHoy());
                pathArchivo = Util.saveFile(f, novedadSeguimiento.getIdNovedadSeguimiento(), "seguimiento_archivo");
                doc.setPathArchivo(pathArchivo);
                this.novedadSeguimientoDocsEjb.create(doc);
            }
            archivos.clear();
        }
        novedadSeguimiento = novedadSeguimientoEjb.find(novedadSeguimiento.getIdNovedadSeguimiento());
        this.lstSeguimientos = novedadSeguimientoEjb.findByNovedad(selected.getIdNovedad());

        if (selected.getNovedadSeguimientoList() == null) {
            selected.setNovedadSeguimientoList(new ArrayList<>());
            selected.getNovedadSeguimientoList().add(novedadSeguimiento);
        } else {
            selected.getNovedadSeguimientoList().add(novedadSeguimiento);
        }

        MovilidadUtil.addSuccessMessage("Seguimiento de novedad registrado éxitosamente.");
        MovilidadUtil.hideModal("novedadSeguimiento");
    }

    /**
     * Modifica el registro de un seguimiento realizado a una novedad
     */
    public void actualizarSeguimiento() {
        novedadSeguimiento.setIdNovedad(selected);
        novedadSeguimiento.setUsername(user.getUsername());
        novedadSeguimiento.setModificado(MovilidadUtil.fechaCompletaHoy());
        this.novedadSeguimientoEjb.edit(novedadSeguimiento);

        if (archivos != null && archivos.size() > 0) {
            NovedadSeguimientoDocs doc;
            String pathArchivo;
            for (UploadedFile f : archivos) {
                doc = new NovedadSeguimientoDocs();
                doc.setNombreArchivo(f.getFileName());
                doc.setIdNovedadSeguimiento(novedadSeguimiento);
                doc.setUsername(user.getUsername());
                doc.setCreado(MovilidadUtil.fechaCompletaHoy());
                pathArchivo = Util.saveFile(f, novedadSeguimiento.getIdNovedadSeguimiento(), "seguimiento_archivo");
                doc.setPathArchivo(pathArchivo);
                this.novedadSeguimientoDocsEjb.create(doc);
            }
            archivos.clear();
        }

        MovilidadUtil.hideModal("novedadSeguimiento");
        MovilidadUtil.addSuccessMessage("Seguimiento de novedad actualizado éxitosamente.");
    }

    /**
     *
     * Evento que dispara la subida de archivos para anexarlos a un seguimiento
     * de novedad
     *
     * @param event
     */
    public void handleFileUploadSeguimiento(FileUploadEvent event) {

        if (event.getFile().getFileName().length() > 50) {
            MovilidadUtil.updateComponent("msgs");
            MovilidadUtil.updateComponent("frmAddFilesSeguimientos:messages");
            MovilidadUtil.addErrorMessage("El nombre de archivo DEBE ser MENOR 50 a caracteres");
            return;
        }
        archivos = archivos == null ? new ArrayList<>() : archivos;

        if (event.getFile() != null) {
            if (flagEditarArchivoSegumiento) {
                if (novedadSeguimientoDoc.getPathArchivo() != null) {
                    Util.deleteFile(novedadSeguimientoDoc.getPathArchivo());
                    String pathArchivo;
                    novedadSeguimientoDoc.setNombreArchivo(event.getFile().getFileName());
                    novedadSeguimientoDoc.setIdNovedadSeguimiento(selectedSeguimiento);
                    novedadSeguimientoDoc.setUsername(user.getUsername());
                    novedadSeguimientoDoc.setModificado(MovilidadUtil.fechaCompletaHoy());
                    pathArchivo = Util.saveFile(event.getFile(), selectedSeguimiento.getIdNovedadSeguimiento(), "seguimiento_archivo");
                    novedadSeguimientoDoc.setPathArchivo(pathArchivo);
                    this.novedadSeguimientoDocsEjb.edit(novedadSeguimientoDoc);
                    flagEditarArchivoSegumiento = false;
                    MovilidadUtil.updateComponent("frmNovedadSeguimientoList:dtNovedadSeguimiento");
                }
            } else {
                archivos.add(event.getFile());
            }
        }

        MovilidadUtil.hideModal("AddFilesSeguimientoDialog");
        MovilidadUtil.updateComponent("msgs");
        MovilidadUtil.addSuccessMessage("Archivo(s) agregado(s) éxitosamente");
    }

    public boolean validarEditarSeguimiento(String userName) {
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

    public List<NovedadSeguimiento> getLstSeguimientos() {
        return lstSeguimientos;
    }

    public void setLstSeguimientos(List<NovedadSeguimiento> lstSeguimientos) {
        this.lstSeguimientos = lstSeguimientos;
    }

    public String getTamanoNovedadSeguimiento() {
        if (tamanoNovedadSeguimiento == null) {
            tamanoNovedadSeguimiento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_SEGUIMIENTO_TAMANO);
        }
        return tamanoNovedadSeguimiento;
    }

    public void setTamanoNovedadSeguimiento(String tamanoNovedadSeguimiento) {
        this.tamanoNovedadSeguimiento = tamanoNovedadSeguimiento;
    }

    public NovedadSeguimiento getSelectedSeguimiento() {
        return selectedSeguimiento;
    }

    public void setSelectedSeguimiento(NovedadSeguimiento selectedSeguimiento) {
        this.selectedSeguimiento = selectedSeguimiento;
    }

    public NovedadSeguimiento getNovedadSeguimiento() {
        return novedadSeguimiento;
    }

    public void setNovedadSeguimiento(NovedadSeguimiento novedadSeguimiento) {
        this.novedadSeguimiento = novedadSeguimiento;
    }

    public boolean isFlagEditarArchivoSegumiento() {
        return flagEditarArchivoSegumiento;
    }

    public void setFlagEditarArchivoSegumiento(boolean flagEditarArchivoSegumiento) {
        this.flagEditarArchivoSegumiento = flagEditarArchivoSegumiento;
    }

    public NovedadSeguimientoDocs getNovedadSeguimientoDoc() {
        return novedadSeguimientoDoc;
    }

    public void setNovedadSeguimientoDoc(NovedadSeguimientoDocs novedadSeguimientoDoc) {
        this.novedadSeguimientoDoc = novedadSeguimientoDoc;
    }

    public StreamedContent getFileDescargar() {
        return fileDescargar;
    }

    public void setFileDescargar(StreamedContent fileDescargar) {
        this.fileDescargar = fileDescargar;
    }

    public Novedad getSelected() {
        return selected;
    }

    public void setSelected(Novedad selected) {
        this.selected = selected;
    }

}
