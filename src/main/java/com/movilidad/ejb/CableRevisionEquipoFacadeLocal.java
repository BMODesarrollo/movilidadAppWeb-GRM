/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableRevisionEquipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableRevisionEquipoFacadeLocal {

    void create(CableRevisionEquipo cableRevisionEquipo);

    void edit(CableRevisionEquipo cableRevisionEquipo);

    void remove(CableRevisionEquipo cableRevisionEquipo);

    CableRevisionEquipo find(Object id);
    
    CableRevisionEquipo findByNombre(String nombre,Integer idRegistro);

    List<CableRevisionEquipo> findAll();
    
    List<CableRevisionEquipo> findAllByEstadoReg();

    List<CableRevisionEquipo> findRange(int[] range);

    int count();
    
}
