/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaTurnoJornadaDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaTurnoJornadaDetFacadeLocal {

    void create(GenericaTurnoJornadaDet genericaTurnoJornadaDet);

    void edit(GenericaTurnoJornadaDet genericaTurnoJornadaDet);

    void remove(GenericaTurnoJornadaDet genericaTurnoJornadaDet);

    GenericaTurnoJornadaDet find(Object id);

    List<GenericaTurnoJornadaDet> findAll();

    List<GenericaTurnoJornadaDet> findRange(int[] range);

    List<GenericaTurnoJornadaDet> getByIdGenericaTurnoJornada(int idGenericaTurnoJornada);

    Integer getSumCantidad(int op, int idCargo, int id);

    int count();

}
