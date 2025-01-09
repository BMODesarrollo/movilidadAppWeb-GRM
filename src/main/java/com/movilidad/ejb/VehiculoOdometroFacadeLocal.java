/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoOdometro;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface VehiculoOdometroFacadeLocal {

    void create(VehiculoOdometro vehiculoOdometro);

    void edit(VehiculoOdometro vehiculoOdometro);

    void remove(VehiculoOdometro vehiculoOdometro);

    VehiculoOdometro find(Object id);

    List<VehiculoOdometro> findAll(Date fecha);

    List<VehiculoOdometro> findRange(int[] range);

    int count();
    
    boolean verificarSubida(Date fecha);
    
}
