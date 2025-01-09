/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPrgJornada;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface GenericaPrgJornadaFacadeLocal {

    void create(GenericaPrgJornada genericaPrgJornada);

    void edit(GenericaPrgJornada genericaPrgJornada);

    void remove(GenericaPrgJornada genericaPrgJornada);

    GenericaPrgJornada find(Object id);

    List<GenericaPrgJornada> findAll();

    List<GenericaPrgJornada> findRange(int[] range);

    int count();
    
    List<GenericaPrgJornada> findAllEstadoRegByFecha(Integer idParamArea, Date dDesde, Date dHasta);
    
}
