/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaTipo;
import com.movilidad.model.ParamAreaUsr;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaTipoFacadeLocal {

    void create(GenericaTipo genericaTipo);

    void edit(GenericaTipo genericaTipo);

    void remove(GenericaTipo genericaTipo);

    GenericaTipo find(Object id);

    ParamAreaUsr findByUsername(String username);

    List<GenericaTipo> findAll();

    List<GenericaTipo> findAllByArea(int usArea);

    List<GenericaTipo> findRange(int[] range);

    int count();

    List<GenericaTipo> obtenerTipos();

    List<GenericaTipo> findAllByAreaAfectaPm(int usArea, int opc);
}
