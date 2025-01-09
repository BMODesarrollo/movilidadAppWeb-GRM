/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamCargoCc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 */
@Local
public interface ParamCargoCcFacadeLocal {

    void create(ParamCargoCc paramCargoCc);

    void edit(ParamCargoCc paramCargoCc);

    void remove(ParamCargoCc paramCargoCc);

    ParamCargoCc find(Object id);

    List<ParamCargoCc> findAllActivos();

    List<ParamCargoCc> findAll();

    List<ParamCargoCc> findRange(int[] range);

    int count();

}
