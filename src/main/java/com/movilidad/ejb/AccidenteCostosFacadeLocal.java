/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteCostos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteCostosFacadeLocal {

    void create(AccidenteCostos accidenteCostos);

    void edit(AccidenteCostos accidenteCostos);

    void remove(AccidenteCostos accidenteCostos);

    AccidenteCostos find(Object id);

    List<AccidenteCostos> findAll();

    List<AccidenteCostos> findRange(int[] range);

    int count();

    List<AccidenteCostos> estadoReg(int i_tipoCosto, int i_idAccidente);

}
