/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccObjetoFijoInforme;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccObjetoFijoInformeFacadeLocal {

    void create(AccObjetoFijoInforme accObjetoFijoInforme);

    void edit(AccObjetoFijoInforme accObjetoFijoInforme);

    void remove(AccObjetoFijoInforme accObjetoFijoInforme);

    AccObjetoFijoInforme find(Object id);

    List<AccObjetoFijoInforme> findAll();

    List<AccObjetoFijoInforme> findRange(int[] range);

    int count();
    
    List<AccObjetoFijoInforme> getAccObjetoFijoInformeInformeOpe(int idAccObjetoFijoInforme);
    
}
