/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadAutorizacionAusentismos;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadAutorizacionAusentismosFacadeLocal {

    void create(NovedadAutorizacionAusentismos novedadAutorizacionAusentismos);

    void edit(NovedadAutorizacionAusentismos novedadAutorizacionAusentismos);

    void remove(NovedadAutorizacionAusentismos novedadAutorizacionAusentismos);

    NovedadAutorizacionAusentismos find(Object id);

    List<NovedadAutorizacionAusentismos> findAll();
    
    List<NovedadAutorizacionAusentismos> findAllByRangoFechasAndUF(Date desde, Date hasta, int idGopUnidadFuncional);

    List<NovedadAutorizacionAusentismos> findRange(int[] range);

    int count();
    
}
