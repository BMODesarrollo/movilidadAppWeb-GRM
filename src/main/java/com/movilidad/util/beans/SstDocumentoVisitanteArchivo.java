package com.movilidad.util.beans;

import com.movilidad.model.SstEmpresaVisitanteDocs;
import java.io.Serializable;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Carlos Ballestas
 */
public class SstDocumentoVisitanteArchivo implements Serializable {

    private SstEmpresaVisitanteDocs empresaVisitanteDoc;
    private UploadedFile file;

    public SstDocumentoVisitanteArchivo(SstEmpresaVisitanteDocs empresaVisitanteDoc) {
        this.empresaVisitanteDoc = empresaVisitanteDoc;
    }

    public SstEmpresaVisitanteDocs getEmpresaVisitanteDoc() {
        return empresaVisitanteDoc;
    }

    public void setEmpresaVisitanteDoc(SstEmpresaVisitanteDocs empresaVisitanteDoc) {
        this.empresaVisitanteDoc = empresaVisitanteDoc;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
