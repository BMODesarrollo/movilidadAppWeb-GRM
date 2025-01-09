/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamAreaCargo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface ParamAreaCargoFacadeLocal {

    void create(ParamAreaCargo paramAreaCargo);

    void edit(ParamAreaCargo paramAreaCargo);

    void remove(ParamAreaCargo paramAreaCargo);

    ParamAreaCargo find(Object id);
    
    ParamAreaCargo getByCargoArea(int idCargoEmpleado, int idParamArea);

    List<ParamAreaCargo> findAll();

    List<ParamAreaCargo> findRange(int[] range);

    int count();

    List<ParamAreaCargo> findAllEstadoReg();

    List<ParamAreaCargo> findAllArea(Integer idArea);

    List<ParamAreaCargo> findByUsernameAllCargos(String cUsername);

    ParamAreaCargo getCargoAreaByCargo(Integer idCargoEmpleado);

}
