/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccChoqueInforme;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccChoqueInformeFacadeLocal {

    void create(AccChoqueInforme accChoqueInforme);

    void edit(AccChoqueInforme accChoqueInforme);

    void remove(AccChoqueInforme accChoqueInforme);

    AccChoqueInforme find(Object id);

    List<AccChoqueInforme> findAll();

    List<AccChoqueInforme> findRange(int[] range);

    int count();
    
    List<AccChoqueInforme> getAccChoqueInformeOpe(int idAccChoqueInforme);
}
