/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaExtra;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaJornadaExtraFacadeLocal {

    void create(GenericaJornadaExtra genericaJornadaExtra);

    void edit(GenericaJornadaExtra genericaJornadaExtra);

    void remove(GenericaJornadaExtra genericaJornadaExtra);

    GenericaJornadaExtra find(Object id);

    GenericaJornadaExtra getByEmpleadoAndFecha(Integer id, Date fecha);

    List<GenericaJornadaExtra> findAll();

    List<GenericaJornadaExtra> findRange(int[] range);

    int count();

}
