/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteAnalisis;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteAnalisisFacadeLocal {

    void create(AccidenteAnalisis accidenteAnalisis);

    void edit(AccidenteAnalisis accidenteAnalisis);

    void remove(AccidenteAnalisis accidenteAnalisis);

    AccidenteAnalisis find(Object id);

    List<AccidenteAnalisis> findAll();

    List<AccidenteAnalisis> findRange(int[] range);
    
    List<AccidenteAnalisis> estadoReg(int i_idAccidente);

    int count();
    
}
