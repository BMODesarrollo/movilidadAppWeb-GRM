/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "viewDocuJSFMB")
@SessionScoped
public class ViewDocumentoJSFManagedBean implements Serializable {

    private DefaultStreamedContent download;

    /**
     * Creates a new instance of ViewDocumentoJSFManagedBean
     */
    public ViewDocumentoJSFManagedBean() {
    }

    public DefaultStreamedContent getDownload() {
        return download;
    }

    public void setDownload(DefaultStreamedContent download) {
        this.download = download;
    }

    
}
