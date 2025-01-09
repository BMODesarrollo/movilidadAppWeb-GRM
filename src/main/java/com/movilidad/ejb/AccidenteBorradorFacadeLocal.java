/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteBorrador;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteBorradorFacadeLocal {

    void create(AccidenteBorrador accidenteBorrador);

    void edit(AccidenteBorrador accidenteBorrador);

    void remove(AccidenteBorrador accidenteBorrador);

    AccidenteBorrador find(Object id);

    List<AccidenteBorrador> findAll();

    List<AccidenteBorrador> findRange(int[] range);

    int count();
    
    List<AccidenteBorrador> findAllEstadoReg(Date ini, Date fin);
    
}
