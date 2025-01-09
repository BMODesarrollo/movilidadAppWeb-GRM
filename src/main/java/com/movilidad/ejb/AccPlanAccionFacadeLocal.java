/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccPlanAccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccPlanAccionFacadeLocal {

    void create(AccPlanAccion accPlanAccion);

    void edit(AccPlanAccion accPlanAccion);

    void remove(AccPlanAccion accPlanAccion);

    AccPlanAccion find(Object id);

    AccPlanAccion findByPlan(String plan);

    List<AccPlanAccion> findAll();

    List<AccPlanAccion> findRange(int[] range);

    int count();

}
