/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaMaterial;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaMaterialFacadeLocal {

    void create(AccViaMaterial accViaMaterial);

    void edit(AccViaMaterial accViaMaterial);

    void remove(AccViaMaterial accViaMaterial);

    AccViaMaterial find(Object id);

    List<AccViaMaterial> findAll();

    List<AccViaMaterial> findRange(int[] range);

    int count();

    List<AccViaMaterial> estadoReg();

}
