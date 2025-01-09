/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.model.NovedadInfrastruc;
import com.movilidad.model.NovedadMtto;
import java.io.Serializable;
import java.util.List;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author solucionesit
 */
public class NovedadInfraArchivo implements Serializable {

    private NovedadInfrastruc novedadInfrastruc;
    private List<UploadedFile> listasArchivo;

    public NovedadInfraArchivo() {
    }

    public NovedadInfraArchivo(NovedadInfrastruc novedadInfrastruc, List<UploadedFile> listasArchivo) {
        this.novedadInfrastruc = novedadInfrastruc;
        this.listasArchivo = listasArchivo;
    }

    public NovedadInfrastruc getNovedadInfrastruc() {
        return novedadInfrastruc;
    }

    public void setNovedadInfrastruc(NovedadInfrastruc novedadInfrastruc) {
        this.novedadInfrastruc = novedadInfrastruc;
    }

    public List<UploadedFile> getListasArchivo() {
        return listasArchivo;
    }

    public void setListasArchivo(List<UploadedFile> listasArchivo) {
        this.listasArchivo = listasArchivo;
    }

}
