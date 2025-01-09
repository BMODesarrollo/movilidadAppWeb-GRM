/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmVrbonoTipovehi;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface PmVrbonoTipovehiFacadeLocal {

    void create(PmVrbonoTipovehi pmVrbonoTipovehi);

    void edit(PmVrbonoTipovehi pmVrbonoTipovehi);

    void remove(PmVrbonoTipovehi pmVrbonoTipovehi);

    PmVrbonoTipovehi find(Object id);

    List<PmVrbonoTipovehi> findAll();

    List<PmVrbonoTipovehi> findRange(int[] range);

    List<PmVrbonoTipovehi> cargarEstadoRegistro();

    int count();

}
