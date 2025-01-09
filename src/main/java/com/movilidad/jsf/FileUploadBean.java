/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author solucionesit
 */
@Named(value = "fileUploadBean")
@ViewScoped
public class FileUploadBean implements Serializable {

    /**
     * Creates a new instance of FileUploadBean
     */
    public FileUploadBean() {
    }

    private List<UploadedFile> archivos;
    private List<String> listFotos;

    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;

    /**
     * Agregar las iomagenes en la lista archivos, para posteriormente ser
     * alamcenadas
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        archivos.add(event.getFile());
    }

    public void cargarListArchivo() {
        if (archivos == null) {
            archivos = new ArrayList<>();
        } else {
            archivos.clear();
        }
    }

    public String guardarFotos(int idCarpeta, String keyCarpeta) {
        String path = "";
        if (archivos != null && !archivos.isEmpty()) {
            for (UploadedFile f : archivos) {
                path = Util.saveFile(f, idCarpeta, keyCarpeta);
            }
        }
        return path;
    }

    /**
     * Obtener lista de las rutas de cada una de las imagenes por registro
     *
     * @throws IOException
     */
    public void obtenerFotos(String path, int idCarpeta, String KeyCarpeta) throws IOException {
        if (this.listFotos != null) {
            this.listFotos.clear();
        } else {
            this.listFotos = new ArrayList<>();
        }
        List<String> lstNombresImg = Util.getFileList(idCarpeta, KeyCarpeta);
        System.out.println("lstNombresImg->" + lstNombresImg.size());

        for (String f : lstNombresImg) {
            f = path + f;
            System.out.println("foto->" + f);
            listFotos.add(f);
        }
        fotoJSFManagedBean.setListFotos(listFotos);
    }

    public List<UploadedFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }
    
}
