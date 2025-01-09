/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaVisual;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaVisualFacadeLocal {

    void create(AccViaVisual accViaVisual);

    void edit(AccViaVisual accViaVisual);

    void remove(AccViaVisual accViaVisual);

    AccViaVisual find(Object id);

    List<AccViaVisual> findAll();

    List<AccViaVisual> findRange(int[] range);

    int count();

    List<AccViaVisual> estadoReg();

}
