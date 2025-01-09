/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MyAppNovedadParam;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface MyAppNovedadParamFacadeLocal {

    void create(MyAppNovedadParam myAppNovedadParam);

    void edit(MyAppNovedadParam myAppNovedadParam);

    void remove(MyAppNovedadParam myAppNovedadParam);

    MyAppNovedadParam find(Object id);

    List<MyAppNovedadParam> findAll();
    
    List<MyAppNovedadParam> findAllEstadoReg();

    List<MyAppNovedadParam> findRange(int[] range);

    int count();
    
    MyAppNovedadParam findByCodigoProceso(String codigo);
    
}
