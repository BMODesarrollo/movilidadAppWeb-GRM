/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidentePlanAccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface AccidentePlanAccionFacadeLocal {

    void create(AccidentePlanAccion accidentePlanAccion);

    void edit(AccidentePlanAccion accidentePlanAccion);

    void remove(AccidentePlanAccion accidentePlanAccion);

    AccidentePlanAccion find(Object id);

    List<AccidentePlanAccion> findAll();

    List<AccidentePlanAccion> findRange(int[] range);

    int count();

    List<AccidentePlanAccion> findAllEstadoReg(Integer idAccidente);

}
