/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadDanoLiqDet;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadDanoLiqDetFacadeLocal {

    void create(NovedadDanoLiqDet novedadDanoLiqDet);

    void edit(NovedadDanoLiqDet novedadDanoLiqDet);

    void remove(NovedadDanoLiqDet novedadDanoLiqDet);

    NovedadDanoLiqDet find(Object id);

    List<NovedadDanoLiqDet> findAll();

    List<NovedadDanoLiqDet> findByRangoFechasAndUf(Date desde, Date hasta, int idGopUnidadFuncional);

    List<NovedadDanoLiqDet> findRange(int[] range);

    int count();

}
