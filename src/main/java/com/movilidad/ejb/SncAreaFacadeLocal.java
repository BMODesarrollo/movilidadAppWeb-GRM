/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SncArea;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pc
 */
@Local
public interface SncAreaFacadeLocal {

    void create(SncArea sncArea);

    void edit(SncArea sncArea);

    void remove(SncArea sncArea);

    SncArea find(Object id);

    List<SncArea> findAll();

    List<SncArea> findRange(int[] range);

    int count();

    public List<SncArea> findallEst();
    
}
