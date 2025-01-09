/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoDanoCosto;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface VehiculoDanoCostoFacadeLocal {

    void create(VehiculoDanoCosto vehiculoDanoCosto);

    void edit(VehiculoDanoCosto vehiculoDanoCosto);

    void remove(VehiculoDanoCosto vehiculoDanoCosto);

    VehiculoDanoCosto find(Object id);

    VehiculoDanoCosto verificarRegistro(Integer idVehiculoDanoCosto, Integer idVehiculoDanoSeveridad, Date desde, Date hasta);
    
    VehiculoDanoCosto findBySeveridadAndFechas(Integer idVehiculoDanoSeveridad, Date desde, Date hasta);

    List<VehiculoDanoCosto> findAll();
    
    List<VehiculoDanoCosto> findAllByEstadoReg();

    List<VehiculoDanoCosto> findRange(int[] range);

    int count();

}
