/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaParam;
import com.movilidad.model.ParamAreaUsr;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaJornadaParamFacadeLocal {

    void create(GenericaJornadaParam genericaJornadaParam);

    void edit(GenericaJornadaParam genericaJornadaParam);

    void remove(GenericaJornadaParam genericaJornadaParam);

    GenericaJornadaParam find(Object id);

    GenericaJornadaParam getByIdArea(int idArea);

    List<GenericaJornadaParam> findAll();
    
    ParamAreaUsr findByUsername(String username);

    List<GenericaJornadaParam> findAllByEstadoReg();

    List<GenericaJornadaParam> findRange(int[] range);

    int count();
}
