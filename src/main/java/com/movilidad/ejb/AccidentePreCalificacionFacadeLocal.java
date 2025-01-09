/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidentePreCalificacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface AccidentePreCalificacionFacadeLocal {

    void create(AccidentePreCalificacion accidentePreCalificacion);

    void edit(AccidentePreCalificacion accidentePreCalificacion);

    void remove(AccidentePreCalificacion accidentePreCalificacion);

    AccidentePreCalificacion find(Object id);

    List<AccidentePreCalificacion> findAll();

    List<AccidentePreCalificacion> findRange(int[] range);

    int count();

    AccidentePreCalificacion findByAccCla(Integer idAccClasificacion, Integer idAccCausaSub, Integer idAccCausaRaiz);
    
    List<AccidentePreCalificacion> findCausaSubByCalificaion(Integer idAccClasificacion);

}
