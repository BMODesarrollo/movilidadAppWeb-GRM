/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccViaCalzadas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccViaCalzadasFacadeLocal {

    void create(AccViaCalzadas accViaCalzadas);

    void edit(AccViaCalzadas accViaCalzadas);

    void remove(AccViaCalzadas accViaCalzadas);

    AccViaCalzadas find(Object id);

    List<AccViaCalzadas> findAll();

    List<AccViaCalzadas> findRange(int[] range);

    int count();

    List<AccViaCalzadas> estadoReg();

}
