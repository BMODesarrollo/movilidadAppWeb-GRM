/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MultaTipoDocumentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface MultaTipoDocumentoFacadeLocal {

    void create(MultaTipoDocumentos multaTipoDocumento);

    void edit(MultaTipoDocumentos multaTipoDocumento);

    void remove(MultaTipoDocumentos multaTipoDocumento);

    MultaTipoDocumentos find(Object id);

    List<MultaTipoDocumentos> findAll();

    List<MultaTipoDocumentos> findRange(int[] range);

    int count();
    
}
