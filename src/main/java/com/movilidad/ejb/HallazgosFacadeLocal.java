/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Hallazgo;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface HallazgosFacadeLocal {

    void create(Hallazgo hallazgos);

    void edit(Hallazgo hallazgos);

    void remove(Hallazgo hallazgos);

    Hallazgo find(Object id);

    Hallazgo findByConsecutivo(int consecutivo);

    List<Hallazgo> findAll();

    List<Hallazgo> findAllByDateRangeAndEstadoReg(Date desde, Date hasta);

    List<Hallazgo> findAllByDateRangeAndArea(Date desde, Date hasta, int idHallazgoParamArea);

    List<Hallazgo> findRange(int[] range);

    int count();

}
