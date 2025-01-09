/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoTipoCargo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface EmpleadoTipoCargoFacadeLocal {

    void create(EmpleadoTipoCargo empleadoTipoCargo);

    void edit(EmpleadoTipoCargo empleadoTipoCargo);

    void remove(EmpleadoTipoCargo empleadoTipoCargo);

    EmpleadoTipoCargo find(Object id);

    List<EmpleadoTipoCargo> findAll();

    List<EmpleadoTipoCargo> findAllActivos();
    
    List<EmpleadoTipoCargo> obtenerCargosOperadores();
    
    List<EmpleadoTipoCargo> findRange(int[] range);

    int count();

    EmpleadoTipoCargo findByNombre(String value, int id);
    
    EmpleadoTipoCargo cargar(int i);
    
    List<EmpleadoTipoCargo> getCargosPorArea(int idArea);

}
