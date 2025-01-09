/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Soluciones IT
 */
public class PmGrupoDetalleFoto implements Serializable{

    private int codigoTM;
    private String Nombre;
    private String path;

    public PmGrupoDetalleFoto(int codigoTM, String Nombre, String path) {
        this.codigoTM = codigoTM;
        this.Nombre = Nombre;
        this.path = path;
    }
    
    public int getCodigoTM() {
        return codigoTM;
    }

    public void setCodigoTM(int codigoTM) {
        this.codigoTM = codigoTM;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
