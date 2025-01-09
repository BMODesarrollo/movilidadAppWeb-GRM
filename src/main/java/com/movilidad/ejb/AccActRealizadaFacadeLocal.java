/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccActRealizada;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccActRealizadaFacadeLocal {

    void create(AccActRealizada accActRealizada);

    void edit(AccActRealizada accActRealizada);

    void remove(AccActRealizada accActRealizada);

    AccActRealizada find(Object id);

    List<AccActRealizada> findAll();

    List<AccActRealizada> findRange(int[] range);

    int count();

    List<AccActRealizada> estadoReg();

}
