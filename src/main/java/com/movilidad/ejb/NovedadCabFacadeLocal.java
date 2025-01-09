/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadCab;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadCabFacadeLocal {

    void create(NovedadCab novedadCab);

    void edit(NovedadCab novedadCab);

    void remove(NovedadCab novedadCab);

    NovedadCab find(Object id);

    List<NovedadCab> findAll();

    List<NovedadCab> findRange(int[] range);

    int count();

    List<NovedadCab> findEstadoReg(Date desde, Date hasta);
}
