/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.model.EmpleadoDocumentos;
import java.io.Serializable;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author solucionesit
 */
public class EmpleadoDocumentosArchivo implements Serializable {

    private EmpleadoDocumentos empleadoDocumentos;
    private UploadedFile archivo;

    public EmpleadoDocumentos getEmpleadoDocumentos() {
        return empleadoDocumentos;
    }

    public void setEmpleadoDocumentos(EmpleadoDocumentos empleadoDocumentos) {
        this.empleadoDocumentos = empleadoDocumentos;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }
    
    
}
