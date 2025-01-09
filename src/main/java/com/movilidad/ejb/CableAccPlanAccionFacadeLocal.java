/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccPlanAccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface CableAccPlanAccionFacadeLocal {

    void create(CableAccPlanAccion cableAccPlanAccion);

    void edit(CableAccPlanAccion cableAccPlanAccion);

    void remove(CableAccPlanAccion cableAccPlanAccion);

    CableAccPlanAccion find(Object id);

    List<CableAccPlanAccion> findAll();

    List<CableAccPlanAccion> findRange(int[] range);

    int count();
    
    List<CableAccPlanAccion> findAllEstadoReg(Integer idCableAccidentalidad);
    
}
