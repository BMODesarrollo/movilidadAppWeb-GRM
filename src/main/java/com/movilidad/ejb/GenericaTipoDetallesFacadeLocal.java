/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaTipoDetalles;
import com.movilidad.model.ParamAreaUsr;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaTipoDetallesFacadeLocal {

    void create(GenericaTipoDetalles genericaTipoDetalles);

    void edit(GenericaTipoDetalles genericaTipoDetalles);

    void remove(GenericaTipoDetalles genericaTipoDetalles);

    GenericaTipoDetalles find(Object id);

    ParamAreaUsr findByUsername(String username);

    List<GenericaTipoDetalles> findAll();

    List<GenericaTipoDetalles> findAllByArea(int area);

    List<GenericaTipoDetalles> findRange(int[] range);

    List<GenericaTipoDetalles> findByTipo(int idTipo);

    List<GenericaTipoDetalles> obtenerDetallesActuales(Integer idParamArea, String idsListaDetalles);

    int count();

}
