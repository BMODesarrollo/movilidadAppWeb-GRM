/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PdEstadoProceso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PdEstadoProcesoFacadeLocal {

    void create(PdEstadoProceso pdEstadoProceso);

    void edit(PdEstadoProceso pdEstadoProceso);

    void remove(PdEstadoProceso pdEstadoProceso);

    PdEstadoProceso find(Object id);
    
    PdEstadoProceso findByNombre(Integer idRegistro, String nombre);
    
    PdEstadoProceso verificarCierreProceso(Integer idRegistro);

    List<PdEstadoProceso> findAll();
    
    List<PdEstadoProceso> findAllByEstadoReg();

    List<PdEstadoProceso> findRange(int[] range);

    int count();
    
}
