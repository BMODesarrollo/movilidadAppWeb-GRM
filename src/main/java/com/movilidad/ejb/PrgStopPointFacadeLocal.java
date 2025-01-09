/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgStopPoint;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface PrgStopPointFacadeLocal {

    void create(PrgStopPoint prgStopPoint);

    void edit(PrgStopPoint prgStopPoint);

    void remove(PrgStopPoint prgStopPoint);

    PrgStopPoint find(Object id);

    PrgStopPoint find(String field, String value);

//    List<PrgStopPoint> findAll();
    List<PrgStopPoint> findAllByUnidadFuncional(Integer idGopUnidadFuncional);

    List<PrgStopPoint> findRange(int[] range);

    int count();

    Long count(int idGopUnidadFuncional);

    List<PrgStopPoint> getPatios();

    List<PrgStopPoint> getparadasByNombre(String nombre, int idGopUnidadFuncional);

    List<PrgStopPoint> getParadasPropios(int idGopUnidadFuncional);

    PrgStopPoint validarparadaByNombre(String nombre);

    int desactivarStopPoints(String codigo);

    List<PrgStopPoint> findStopPointByName(String name, int idGopUnidadFuncional);

}
