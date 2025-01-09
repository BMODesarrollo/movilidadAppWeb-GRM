/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondInfra;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccCondInfraFacadeLocal {

    void create(AccCondInfra accCondInfra);

    void edit(AccCondInfra accCondInfra);

    void remove(AccCondInfra accCondInfra);

    AccCondInfra find(Object id);

    List<AccCondInfra> findAll();

    List<AccCondInfra> findRange(int[] range);

    int count();

    List<AccCondInfra> estadoReg();

}
