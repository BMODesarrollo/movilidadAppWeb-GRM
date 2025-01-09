/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccInformeMasterAlbum;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccInformeMasterAlbumFacadeLocal {

    void create(AccInformeMasterAlbum accInformeMasterAlbum);

    void edit(AccInformeMasterAlbum accInformeMasterAlbum);

    void remove(AccInformeMasterAlbum accInformeMasterAlbum);

    AccInformeMasterAlbum find(Object id);

    List<AccInformeMasterAlbum> findAll();

    List<AccInformeMasterAlbum> findRange(int[] range);

    int count();
    
}
