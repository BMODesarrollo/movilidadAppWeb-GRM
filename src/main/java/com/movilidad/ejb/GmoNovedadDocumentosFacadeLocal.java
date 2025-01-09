/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GmoNovedadDocumentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GmoNovedadDocumentosFacadeLocal {

    void create(GmoNovedadDocumentos gmoNovedadDocumentos);

    void edit(GmoNovedadDocumentos gmoNovedadDocumentos);

    void remove(GmoNovedadDocumentos gmoNovedadDocumentos);

    GmoNovedadDocumentos find(Object id);

    List<GmoNovedadDocumentos> findAll();

    List<GmoNovedadDocumentos> findRange(int[] range);
    
    List<GmoNovedadDocumentos> findAllByIdNovedad(Integer idGmoNovedadInfra);

    int count();
    
}
