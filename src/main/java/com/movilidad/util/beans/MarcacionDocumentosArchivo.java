package com.movilidad.util.beans;

import com.movilidad.model.MarcacionDocumentos;
import java.io.Serializable;
import org.primefaces.model.UploadedFile;

/**
 * Omar Beltrán
 */
public class MarcacionDocumentosArchivo implements Serializable {

    private MarcacionDocumentos marcacionDocumentos;
    private UploadedFile archivo;

    public MarcacionDocumentos getMarcacionDocumentos() {
        return marcacionDocumentos;
    }

    public void setMarcacionDocumentos(MarcacionDocumentos marcacionDocumentos) {
        this.marcacionDocumentos = marcacionDocumentos;
    }

    
    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }
    
}
