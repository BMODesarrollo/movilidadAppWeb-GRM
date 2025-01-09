/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteConductor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteConductorFacadeLocal {

    void create(AccidenteConductor accidenteConductor);

    void edit(AccidenteConductor accidenteConductor);

    void remove(AccidenteConductor accidenteConductor);

    AccidenteConductor find(Object id);

    AccidenteConductor findByVehiculo(int idAccidente, int idAccidenteVehiculo, String cedula);

    List<AccidenteConductor> findAll();

    List<AccidenteConductor> findRange(int[] range);

    int count();

    List<AccidenteConductor> estadoReg(int i_idAccidente);

}
