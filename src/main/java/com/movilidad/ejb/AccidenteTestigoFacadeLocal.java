/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteTestigo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteTestigoFacadeLocal {

    void create(AccidenteTestigo accidenteTestigo);

    void edit(AccidenteTestigo accidenteTestigo);

    void remove(AccidenteTestigo accidenteTestigo);

    AccidenteTestigo find(Object id);

    List<AccidenteTestigo> findAll();

    List<AccidenteTestigo> findRange(int[] range);

    int count();

    List<AccidenteTestigo> estadoReg(int i_idAccidente);

}
