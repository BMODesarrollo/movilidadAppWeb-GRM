/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PdTipoProceso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PdTipoProcesoFacadeLocal {

    void create(PdTipoProceso pdTipoProceso);

    void edit(PdTipoProceso pdTipoProceso);

    void remove(PdTipoProceso pdTipoProceso);

    PdTipoProceso find(Object id);
    
    PdTipoProceso findByNombre(Integer idRegistro,String nombre);

    List<PdTipoProceso> findAll();
    
    List<PdTipoProceso> findAllByEstadoReg();

    List<PdTipoProceso> findRange(int[] range);

    int count();
    
}
