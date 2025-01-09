/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamArea;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface ParamAreaFacadeLocal {

    void create(ParamArea paramArea);

    void edit(ParamArea paramArea);

    void remove(ParamArea paramArea);

    ParamArea find(Object id);

    List<ParamArea> findAll();

    List<ParamArea> findRange(int[] range);

    int count();

    List<ParamArea> findAllEstadoReg();
    
    ParamArea findParamAreaByArea(String area);

}
