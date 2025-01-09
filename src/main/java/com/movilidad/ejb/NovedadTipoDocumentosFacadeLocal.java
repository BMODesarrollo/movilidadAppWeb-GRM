/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipoDocumentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pc
 */
@Local
public interface NovedadTipoDocumentosFacadeLocal {

    void create(NovedadTipoDocumentos novedadTipoDocumentos);

    void edit(NovedadTipoDocumentos novedadTipoDocumentos);

    void remove(NovedadTipoDocumentos novedadTipoDocumentos);

    NovedadTipoDocumentos find(Object id);

    List<NovedadTipoDocumentos> findAll();

    List<NovedadTipoDocumentos> findRange(int[] range);

    int count();
    
}
