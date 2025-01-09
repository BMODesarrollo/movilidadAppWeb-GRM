/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteVehiculo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteVehiculoFacadeLocal {

    void create(AccidenteVehiculo accidenteVehiculo);

    void edit(AccidenteVehiculo accidenteVehiculo);

    void remove(AccidenteVehiculo accidenteVehiculo);

    AccidenteVehiculo find(Object id);

    AccidenteVehiculo findByVehiculo(int idAccidente,String codigoVehiculo);

    List<AccidenteVehiculo> findAll();

    List<AccidenteVehiculo> findRange(int[] range);

    int count();

    List<AccidenteVehiculo> estadoReg(int id_accidente);

}
