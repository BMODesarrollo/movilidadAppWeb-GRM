/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.model.AseoCabinaNovedad;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class AseoCabinaNovedadArchivo implements Serializable {

    private AseoCabinaNovedad AseoCabinaNovedad;
    private List<UploadedFile> archivos;

    public AseoCabinaNovedadArchivo(AseoCabinaNovedad AseoCabinaNovedad, List<UploadedFile> archivos) {
        this.AseoCabinaNovedad = AseoCabinaNovedad;
        this.archivos = archivos;
    }

    public AseoCabinaNovedadArchivo() {
    }

    public AseoCabinaNovedad getAseoCabinaNovedad() {
        return AseoCabinaNovedad;
    }

    public void setAseoCabinaNovedad(AseoCabinaNovedad AseoCabinaNovedad) {
        this.AseoCabinaNovedad = AseoCabinaNovedad;
    }

    public List<UploadedFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }

}
