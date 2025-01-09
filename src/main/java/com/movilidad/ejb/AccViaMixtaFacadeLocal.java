/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaMixta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaMixtaFacadeLocal {

    void create(AccViaMixta accViaMixta);

    void edit(AccViaMixta accViaMixta);

    void remove(AccViaMixta accViaMixta);

    AccViaMixta find(Object id);

    List<AccViaMixta> findAll();

    List<AccViaMixta> findRange(int[] range);

    int count();

    List<AccViaMixta> estadoReg();

}
