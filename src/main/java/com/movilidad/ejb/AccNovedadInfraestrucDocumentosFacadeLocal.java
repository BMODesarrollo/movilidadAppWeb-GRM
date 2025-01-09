/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadInfraestrucDocumentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccNovedadInfraestrucDocumentosFacadeLocal {

    void create(AccNovedadInfraestrucDocumentos accNovedadInfraestrucDocumentos);

    void edit(AccNovedadInfraestrucDocumentos accNovedadInfraestrucDocumentos);

    void remove(AccNovedadInfraestrucDocumentos accNovedadInfraestrucDocumentos);

    AccNovedadInfraestrucDocumentos find(Object id);

    List<AccNovedadInfraestrucDocumentos> findAll();
    
    List<AccNovedadInfraestrucDocumentos> findByNovedad(Integer idNovedad);

    List<AccNovedadInfraestrucDocumentos> findRange(int[] range);

    int count();
    
}
