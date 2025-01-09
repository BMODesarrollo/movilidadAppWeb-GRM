package com.movilidad.util.beans;

import com.movilidad.model.SstDocumentoEmpresa;
import com.movilidad.model.SstEmpresaDocs;
import java.io.Serializable;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Carlos Ballestas
 */
public class SstDocumentoEmpresaArchivo implements Serializable {

    private SstEmpresaDocs empresaDoc;
    private UploadedFile file;

    public SstDocumentoEmpresaArchivo(SstEmpresaDocs empresaDoc) {
        this.empresaDoc = empresaDoc;
    }

    public SstEmpresaDocs getEmpresaDoc() {
        return empresaDoc;
    }

    public void setEmpresaDoc(SstEmpresaDocs empresaDoc) {
        this.empresaDoc = empresaDoc;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
