/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCausaSub;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccCausaSubFacadeLocal {

    void create(AccCausaSub accCausaSub);

    void edit(AccCausaSub accCausaSub);

    void remove(AccCausaSub accCausaSub);

    AccCausaSub find(Object id);

    List<AccCausaSub> findAll();

    List<AccCausaSub> findRange(int[] range);

    int count();

    List<AccCausaSub> estadoReg();
    
    List<AccCausaSub> findByCausa(int i_idAccCausa);

}
