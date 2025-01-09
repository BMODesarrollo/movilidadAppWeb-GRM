/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSerconDet;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface PrgSerconDetFacadeLocal {

    void create(PrgSerconDet prgSerconDet);

    void createList(List<PrgSerconDet> prgSerconDetList);

    void edit(PrgSerconDet prgSerconDet);

    void remove(PrgSerconDet prgSerconDet);

    PrgSerconDet find(Object id);

    List<PrgSerconDet> findAll();

    List<PrgSerconDet> findRange(int[] range);

    int count();

    List<PrgSerconDet> findByRangoFecha(Date desde, Date hasta);
}
