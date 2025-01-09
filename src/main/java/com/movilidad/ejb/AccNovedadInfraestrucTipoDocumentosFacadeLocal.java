/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadInfraestrucTipoDocumentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccNovedadInfraestrucTipoDocumentosFacadeLocal {

    void create(AccNovedadInfraestrucTipoDocumentos accNovedadInfraestrucTipoDocumentos);

    void edit(AccNovedadInfraestrucTipoDocumentos accNovedadInfraestrucTipoDocumentos);

    void remove(AccNovedadInfraestrucTipoDocumentos accNovedadInfraestrucTipoDocumentos);

    AccNovedadInfraestrucTipoDocumentos find(Object id);
    
    AccNovedadInfraestrucTipoDocumentos findByNombre(String nombre, Integer idRegistro);

    List<AccNovedadInfraestrucTipoDocumentos> findAll();
    
    List<AccNovedadInfraestrucTipoDocumentos> findAllByEstadoReg();

    List<AccNovedadInfraestrucTipoDocumentos> findRange(int[] range);

    int count();
    
}
