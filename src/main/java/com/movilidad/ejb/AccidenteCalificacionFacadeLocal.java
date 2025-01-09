/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteCalificacion;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface AccidenteCalificacionFacadeLocal {

    void create(AccidenteCalificacion accidenteCalificacion);

    void edit(AccidenteCalificacion accidenteCalificacion);

    void remove(AccidenteCalificacion accidenteCalificacion);

    AccidenteCalificacion find(Object id);

    List<AccidenteCalificacion> findAll();

    List<AccidenteCalificacion> findRange(int[] range);

    int count();

    boolean validateByPin(Date d, Integer iPin);
    
    boolean validateByAccidente(Integer idAccidente);
    
    List<AccidenteCalificacion> findByPin(Date d, Integer iPin, int op);

}
