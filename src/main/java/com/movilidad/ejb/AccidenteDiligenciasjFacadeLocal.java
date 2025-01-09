/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteDiligenciasj;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteDiligenciasjFacadeLocal {

    void create(AccidenteDiligenciasj accidenteDiligenciasj);

    void edit(AccidenteDiligenciasj accidenteDiligenciasj);

    void remove(AccidenteDiligenciasj accidenteDiligenciasj);

    AccidenteDiligenciasj find(Object id);

    List<AccidenteDiligenciasj> findAll();

    List<AccidenteDiligenciasj> findRange(int[] range);

    int count();
    
    List<AccidenteDiligenciasj> estadoReg(int i_idAccidente);
    
}
