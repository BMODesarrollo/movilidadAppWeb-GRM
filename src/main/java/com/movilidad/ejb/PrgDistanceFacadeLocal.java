/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgDistance;
import com.movilidad.model.PrgStopPoint;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PrgDistanceFacadeLocal {

    void create(PrgDistance prgDistance);

    void edit(PrgDistance prgDistance);

    void remove(PrgDistance prgDistance);

    PrgDistance find(Object id);

    BigDecimal getDistance(PrgStopPoint prgFromStop, PrgStopPoint prgToStop);

    PrgDistance validarDistancia(PrgStopPoint prgFromStop, PrgStopPoint prgToStop, Integer id);

    List<PrgDistance> findAll();

    List<PrgDistance> findAllPropias(int idGopUnidadFunc);

    List<PrgDistance> findRange(int[] range);

    int count();

}
