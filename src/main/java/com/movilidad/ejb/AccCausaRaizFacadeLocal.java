/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCausaRaiz;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AccCausaRaizFacadeLocal {

    void create(AccCausaRaiz accCausaRaiz);

    void edit(AccCausaRaiz accCausaRaiz);

    void remove(AccCausaRaiz accCausaRaiz);

    AccCausaRaiz find(Object id);

    List<AccCausaRaiz> findAll();

    List<AccCausaRaiz> findRange(int[] range);

    int count();
    
    List<AccCausaRaiz> estadoReg();
    
    List<AccCausaRaiz> findByCausaSub(int i_idAccCausaSub);

}
