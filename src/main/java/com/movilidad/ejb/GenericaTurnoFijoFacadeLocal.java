/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaTurnoFijo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaTurnoFijoFacadeLocal {

    void create(GenericaTurnoFijo genericaTurnoFijo);

    void edit(GenericaTurnoFijo genericaTurnoFijo);

    void remove(GenericaTurnoFijo genericaTurnoFijo);

    GenericaTurnoFijo find(Object id);

    GenericaTurnoFijo findTurnoByIdEmpleado(int idEmpleado, int idRegistro);

    List<GenericaTurnoFijo> findAll();

    List<GenericaTurnoFijo> findByArea(int idArea);

    List<GenericaTurnoFijo> findRange(int[] range);

    int count();

}