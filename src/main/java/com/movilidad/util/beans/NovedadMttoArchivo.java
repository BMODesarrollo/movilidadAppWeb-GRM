/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.model.NovedadMtto;
import java.io.Serializable;
import java.util.List;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author solucionesit
 */
public class NovedadMttoArchivo implements Serializable {

    private NovedadMtto novedadMtto;
    private List<UploadedFile> listasArchivo;

    public NovedadMttoArchivo() {
    }

    public NovedadMttoArchivo(NovedadMtto novedadMtto, List<UploadedFile> listasArchivo) {
        this.novedadMtto = novedadMtto;
        this.listasArchivo = listasArchivo;
    }

    public NovedadMtto getNovedadMtto() {
        return novedadMtto;
    }

    public void setNovedadMtto(NovedadMtto novedadMtto) {
        this.novedadMtto = novedadMtto;
    }

    public List<UploadedFile> getListasArchivo() {
        return listasArchivo;
    }

    public void setListasArchivo(List<UploadedFile> listasArchivo) {
        this.listasArchivo = listasArchivo;
    }
    
    

}
