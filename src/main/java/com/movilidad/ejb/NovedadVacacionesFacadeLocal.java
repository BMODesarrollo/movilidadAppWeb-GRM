/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadVacaciones;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadVacacionesFacadeLocal {

    void create(NovedadVacaciones novedadVacaciones);

    void edit(NovedadVacaciones novedadVacaciones);

    void remove(NovedadVacaciones novedadVacaciones);

    NovedadVacaciones find(Object id);

    NovedadVacaciones findByDateRange(Date fechaDesde, Date fechaHasta, String ccColaborador);

    List<NovedadVacaciones> findAll();

    List<NovedadVacaciones> findRange(int[] range);

    int count();

}
