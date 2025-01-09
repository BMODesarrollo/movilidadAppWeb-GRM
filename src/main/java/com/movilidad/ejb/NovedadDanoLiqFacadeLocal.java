/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadDanoLiq;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadDanoLiqFacadeLocal {

    void create(NovedadDanoLiq novedadDanoLiq);

    void edit(NovedadDanoLiq novedadDanoLiq);

    void remove(NovedadDanoLiq novedadDanoLiq);

    NovedadDanoLiq find(Object id);

    List<NovedadDanoLiq> findAll();

    List<NovedadDanoLiq> findRange(int[] range);

    int count();

    List<NovedadDanoLiq> findByRangoFechas(Date desde, Date hasta, int idGopUnidadFunc);
}
