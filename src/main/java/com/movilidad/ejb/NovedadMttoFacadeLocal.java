/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMtto;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface NovedadMttoFacadeLocal {

    void create(NovedadMtto novedadMtto);

    void edit(NovedadMtto novedadMtto);

    void remove(NovedadMtto novedadMtto);

    NovedadMtto find(Object id);

    List<NovedadMtto> findAll();

    List<NovedadMtto> findRange(int[] range);

    int count();

    List<NovedadMtto> findRanfoFechaEstadoReg(Date desde, Date hasta);

}
