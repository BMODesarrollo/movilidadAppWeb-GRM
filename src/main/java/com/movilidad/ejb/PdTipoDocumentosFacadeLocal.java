/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PdTipoDocumentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PdTipoDocumentosFacadeLocal {

    void create(PdTipoDocumentos pdTipoDocumentos);

    void edit(PdTipoDocumentos pdTipoDocumentos);

    void remove(PdTipoDocumentos pdTipoDocumentos);

    PdTipoDocumentos find(Object id);
    
    PdTipoDocumentos findByNombre(Integer idRegistro,String nombre);

    List<PdTipoDocumentos> findAll();
    
    List<PdTipoDocumentos> findAllByEstadoReg();

    List<PdTipoDocumentos> findRange(int[] range);

    int count();
    
}