/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Aseo;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AseoFacadeLocal {

    void create(Aseo aseo);

    void edit(Aseo aseo);

    void remove(Aseo aseo);

    Aseo find(Object id);

    List<Aseo> findAll();

    List<Aseo> findRange(int[] range);

    int count();

    List<Aseo> findAllByRangoFechas(Date desde, Date hasta);

}
