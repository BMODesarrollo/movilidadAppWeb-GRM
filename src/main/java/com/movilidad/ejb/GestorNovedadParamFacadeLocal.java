/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovedadParam;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GestorNovedadParamFacadeLocal {

    void create(GestorNovedadParam gestorNovedadParam);

    void edit(GestorNovedadParam gestorNovedadParam);

    void remove(GestorNovedadParam gestorNovedadParam);

    GestorNovedadParam find(Object id);

    List<GestorNovedadParam> findAll();

    List<GestorNovedadParam> findRange(int[] range);

    int count();
    
}
