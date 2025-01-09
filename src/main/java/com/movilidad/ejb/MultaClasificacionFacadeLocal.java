/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MultaClasificacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface MultaClasificacionFacadeLocal {

    void create(MultaClasificacion multaClasificacion);

    void edit(MultaClasificacion multaClasificacion);

    void remove(MultaClasificacion multaClasificacion);

    MultaClasificacion find(Object id);

    List<MultaClasificacion> findAll();

    List<MultaClasificacion> findRange(int[] range);
    
    List<MultaClasificacion> obtenerTM(int i);

    int count();
    List <MultaClasificacion> findallEst ();
    
}
