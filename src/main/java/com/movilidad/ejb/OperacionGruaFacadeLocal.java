/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.OperacionGrua;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface OperacionGruaFacadeLocal {

    void create(OperacionGrua operacionGrua);

    void edit(OperacionGrua operacionGrua);

    void remove(OperacionGrua operacionGrua);

    OperacionGrua find(Object id);

    List<OperacionGrua> findAll();

    List<OperacionGrua> findRange(int[] range);

    int count();

    List<OperacionGrua> findByRangeDates(Date desde, Date hasta);
}
