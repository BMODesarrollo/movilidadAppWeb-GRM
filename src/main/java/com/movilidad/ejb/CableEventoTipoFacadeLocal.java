/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableEventoTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableEventoTipoFacadeLocal {

    void create(CableEventoTipo cableEventoTipo);

    void edit(CableEventoTipo cableEventoTipo);

    void remove(CableEventoTipo cableEventoTipo);

    CableEventoTipo find(Object id);
    
    CableEventoTipo findByCodigo(String codigo, Integer idRegistro);

    CableEventoTipo findByNombre(String nombre, Integer idRegistro);

    List<CableEventoTipo> findAll();
    
    List<CableEventoTipo> findAllByEstadoReg();

    List<CableEventoTipo> findRange(int[] range);

    int count();
    
}
