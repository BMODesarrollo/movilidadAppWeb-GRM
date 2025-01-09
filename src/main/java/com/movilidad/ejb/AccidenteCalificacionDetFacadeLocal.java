/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteCalificacionDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface AccidenteCalificacionDetFacadeLocal {

    void create(AccidenteCalificacionDet accidenteCalificacionDet);

    void edit(AccidenteCalificacionDet accidenteCalificacionDet);

    void remove(AccidenteCalificacionDet accidenteCalificacionDet);

    AccidenteCalificacionDet find(Object id);

    List<AccidenteCalificacionDet> findAll();

    List<AccidenteCalificacionDet> findRange(int[] range);

    int count();

    List<AccidenteCalificacionDet> findByAccClasificacion(Integer idAccClasificacion);
    
    Integer calcularevaluacion(Integer idAccClasificacion, Integer idAccAnalisis);
}
