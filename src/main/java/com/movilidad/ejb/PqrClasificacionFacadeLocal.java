/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrClasificacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jeisson Junco
 */
@Local
public interface PqrClasificacionFacadeLocal {

    void create(PqrClasificacion pqrClasificacion);

    void edit(PqrClasificacion pqrClasificacion);

    void remove(PqrClasificacion pqrClasificacion);

    PqrClasificacion find(Object id);
    
    PqrClasificacion verificarRegistro(Integer idRegistro, String nombre);

    List<PqrClasificacion> findAll();
    
    List<PqrClasificacion> findAllByEstadoReg();

    List<PqrClasificacion> findRange(int[] range);

    int count();
    
}
