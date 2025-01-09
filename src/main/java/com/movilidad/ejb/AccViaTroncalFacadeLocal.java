/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaTroncal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaTroncalFacadeLocal {

    void create(AccViaTroncal accViaTroncal);

    void edit(AccViaTroncal accViaTroncal);

    void remove(AccViaTroncal accViaTroncal);

    AccViaTroncal find(Object id);

    List<AccViaTroncal> findAll();

    List<AccViaTroncal> findRange(int[] range);

    int count();

    List<AccViaTroncal> estadoReg();
}
