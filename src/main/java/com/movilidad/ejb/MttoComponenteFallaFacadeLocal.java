/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoComponenteFalla;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface MttoComponenteFallaFacadeLocal {

    void create(MttoComponenteFalla mttoComponenteFalla);

    void edit(MttoComponenteFalla mttoComponenteFalla);

    void remove(MttoComponenteFalla mttoComponenteFalla);

    MttoComponenteFalla find(Object id);

    List<MttoComponenteFalla> findAll();

    List<MttoComponenteFalla> getCompFallaByIdComp(int id);

    List<MttoComponenteFalla> findRange(int[] range);

    int count();

}
