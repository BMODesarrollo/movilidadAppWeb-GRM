/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.model.SegReportePilonaNovedad;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class SegReportePilonaNovedadArchivo implements Serializable {

    private SegReportePilonaNovedad segReportePilonaNovedad;
    private List<UploadedFile> archivos;

    public SegReportePilonaNovedadArchivo(SegReportePilonaNovedad segReportePilonaNovedad, List<UploadedFile> archivos) {
        this.segReportePilonaNovedad = segReportePilonaNovedad;
        this.archivos = archivos;
    }

    public SegReportePilonaNovedadArchivo() {
    }

    public SegReportePilonaNovedad getSegReportePilonaNovedad() {
        return segReportePilonaNovedad;
    }

    public void setSegReportePilonaNovedad(SegReportePilonaNovedad segReportePilonaNovedad) {
        this.segReportePilonaNovedad = segReportePilonaNovedad;
    }

    public List<UploadedFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }

}
