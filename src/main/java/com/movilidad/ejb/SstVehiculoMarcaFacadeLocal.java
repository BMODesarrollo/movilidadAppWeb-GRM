/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstVehiculoMarca;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface SstVehiculoMarcaFacadeLocal {

    void create(SstVehiculoMarca sstVehiculoMarca);

    void edit(SstVehiculoMarca sstVehiculoMarca);

    void remove(SstVehiculoMarca sstVehiculoMarca);

    SstVehiculoMarca find(Object id);

    List<SstVehiculoMarca> findAll();

    List<SstVehiculoMarca> findRange(int[] range);

    int count();
    
    List<SstVehiculoMarca> findAllEstadoReg();
    
}
