/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadDocumentos;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadDocumentosFacadeLocal {

    void create(NovedadDocumentos novedadDocumentos);

    void edit(NovedadDocumentos novedadDocumentos);

    void remove(NovedadDocumentos novedadDocumentos);

    NovedadDocumentos find(Object id);

    NovedadDocumentos findByCreadoAndIdNovedad(Date creado,int idNovedad);

    List<NovedadDocumentos> findByIdNovedad(int id);

    List<NovedadDocumentos> findAll();

    List<NovedadDocumentos> findRange(int[] range);

    int count();

}
