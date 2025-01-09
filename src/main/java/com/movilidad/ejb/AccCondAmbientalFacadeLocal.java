/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondAmbiental;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccCondAmbientalFacadeLocal {

    void create(AccCondAmbiental accCondAmbiental);

    void edit(AccCondAmbiental accCondAmbiental);

    void remove(AccCondAmbiental accCondAmbiental);

    AccCondAmbiental find(Object id);

    List<AccCondAmbiental> findAll();

    List<AccCondAmbiental> findRange(int[] range);

    int count();

    List<AccCondAmbiental> estadoReg();

}
