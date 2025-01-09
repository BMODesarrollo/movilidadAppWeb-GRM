/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstTipoIdentificacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface SstTipoIdentificacionFacadeLocal {

    void create(SstTipoIdentificacion sstTipoIdentificacion);

    void edit(SstTipoIdentificacion sstTipoIdentificacion);

    void remove(SstTipoIdentificacion sstTipoIdentificacion);

    SstTipoIdentificacion find(Object id);

    List<SstTipoIdentificacion> findAll();

    List<SstTipoIdentificacion> findRange(int[] range);

    int count();
    
    List<SstTipoIdentificacion> findAllEstadoReg();
    
}
