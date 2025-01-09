/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaDocumentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaDocumentosFacadeLocal {

    void create(GenericaDocumentos genericaDocumentos);

    void edit(GenericaDocumentos genericaDocumentos);

    void remove(GenericaDocumentos genericaDocumentos);

    GenericaDocumentos find(Object id);

    List<GenericaDocumentos> findAll();
    
    List<GenericaDocumentos> findByIdNovedad(int id);

    List<GenericaDocumentos> findRange(int[] range);

    int count();
    
}
