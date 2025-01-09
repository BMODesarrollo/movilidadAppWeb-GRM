/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import com.movilidad.model.AccInformeMasterAlbum;
import java.io.Serializable;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author HP
 */
public class AccInformeMasterAlbumFoto implements Serializable {

    private AccInformeMasterAlbum accInformeMasterAlbum;
    private UploadedFile uploadedFile;

    public AccInformeMasterAlbumFoto() {
    }

    public AccInformeMasterAlbumFoto(AccInformeMasterAlbum accInformeMasterAlbum, UploadedFile uploadedFile) {
        this.accInformeMasterAlbum = accInformeMasterAlbum;
        this.uploadedFile = uploadedFile;
    }

    public AccInformeMasterAlbum getAccInformeMasterAlbum() {
        return accInformeMasterAlbum;
    }

    public void setAccInformeMasterAlbum(AccInformeMasterAlbum accInformeMasterAlbum) {
        this.accInformeMasterAlbum = accInformeMasterAlbum;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

}
