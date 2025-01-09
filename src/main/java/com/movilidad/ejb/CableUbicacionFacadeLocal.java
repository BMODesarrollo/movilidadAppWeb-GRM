/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableUbicacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableUbicacionFacadeLocal {

    void create(CableUbicacion cableUbicacion);

    void edit(CableUbicacion cableUbicacion);

    void remove(CableUbicacion cableUbicacion);

    CableUbicacion find(Object id);

    CableUbicacion findByCodigo(String codigo);

    CableUbicacion findByNombre(String nombre);
    
    CableUbicacion findByIdArmamento(Integer id);
    
    CableUbicacion findByIdMedioComunicacion(Integer id);

    List<CableUbicacion> findAll();
    
    List<CableUbicacion> findAllByEstadoReg();
    
    List<CableUbicacion> getUbicacionesConArmamento();

    List<CableUbicacion> findRange(int[] range);

    int count();

}
