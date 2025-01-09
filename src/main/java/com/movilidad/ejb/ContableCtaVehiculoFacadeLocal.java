/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ContableCtaVehiculo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface ContableCtaVehiculoFacadeLocal {

    void create(ContableCtaVehiculo contableCtaVehiculo);

    void edit(ContableCtaVehiculo contableCtaVehiculo);

    void remove(ContableCtaVehiculo contableCtaVehiculo);

    ContableCtaVehiculo find(Object id);

    List<ContableCtaVehiculo> findAll();

    List<ContableCtaVehiculo> findRange(int[] range);

    int count();
    
    List<ContableCtaVehiculo> findAllEstadoReg();
    
}
