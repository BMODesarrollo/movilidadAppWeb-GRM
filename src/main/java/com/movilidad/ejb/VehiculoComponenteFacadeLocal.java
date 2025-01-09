/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoComponente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoComponenteFacadeLocal {

    void create(VehiculoComponente vehiculoComponente);

    void edit(VehiculoComponente vehiculoComponente);

    void remove(VehiculoComponente vehiculoComponente);

    VehiculoComponente find(Object id);

    List<VehiculoComponente> findAll();

    List<VehiculoComponente> findRange(int[] range);

    int count();

    VehiculoComponente findByName(String Name);
}
