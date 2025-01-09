/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaDet;
import com.movilidad.model.PrgSerconDet;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaJornadaDetFacadeLocal {

    void create(GenericaJornadaDet genericaJornadaDet);

    void createList(List<GenericaJornadaDet> genericaJornadaDetList);

    void edit(GenericaJornadaDet genericaJornadaDet);

    void remove(GenericaJornadaDet genericaJornadaDet);

    GenericaJornadaDet find(Object id);

    List<GenericaJornadaDet> findAll();

    List<GenericaJornadaDet> findRange(int[] range);

    int count();

    List<GenericaJornadaDet> findByRangoFecha(Date desde, Date hasta);

}
