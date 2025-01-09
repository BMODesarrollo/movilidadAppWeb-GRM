/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteAbogado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteAbogadoFacadeLocal {

    void create(AccidenteAbogado accidenteAbogado);

    void edit(AccidenteAbogado accidenteAbogado);

    void remove(AccidenteAbogado accidenteAbogado);

    AccidenteAbogado find(Object id);

    List<AccidenteAbogado> findAll();

    List<AccidenteAbogado> findRange(int[] range);

    int count();
    
    List<AccidenteAbogado> estadoReg();
    
}
