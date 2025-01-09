/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteLugar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteLugarFacadeLocal {

    void create(AccidenteLugar accidenteLugar);

    void edit(AccidenteLugar accidenteLugar);

    void remove(AccidenteLugar accidenteLugar);

    AccidenteLugar find(Object id);

    List<AccidenteLugar> findAll();

    List<AccidenteLugar> findRange(int[] range);

    int count();
    
    AccidenteLugar buscarPorAccidente(int i_idAccidente);
}
