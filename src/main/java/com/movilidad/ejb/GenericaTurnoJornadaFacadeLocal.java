/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaTurnoJornada;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaTurnoJornadaFacadeLocal {

    void create(GenericaTurnoJornada genericaTurnoJornada);

    void edit(GenericaTurnoJornada genericaTurnoJornada);

    void remove(GenericaTurnoJornada genericaTurnoJornada);

    GenericaTurnoJornada find(Object id);

    List<GenericaTurnoJornada> findAll();

    List<GenericaTurnoJornada> findRange(int[] range);

    int count();
    
    List<GenericaTurnoJornada> findEstadoReg(Integer idParamArea, Integer idGenericaTurno);
}
