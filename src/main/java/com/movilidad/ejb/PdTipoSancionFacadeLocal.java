/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PdTipoSancion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PdTipoSancionFacadeLocal {

    void create(PdTipoSancion pdTipoSancion);

    void edit(PdTipoSancion pdTipoSancion);

    void remove(PdTipoSancion pdTipoSancion);

    PdTipoSancion find(Object id);
    
    PdTipoSancion findByNombre(Integer idRegistro,String nombre);

    List<PdTipoSancion> findAll();
    
    List<PdTipoSancion> findAllByEstadoReg();

    List<PdTipoSancion> findRange(int[] range);

    int count();
    
}
