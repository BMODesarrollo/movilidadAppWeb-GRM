/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaNotifProcesos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface GenericaNotifProcesosFacadeLocal {

    void create(GenericaNotifProcesos genericaNotifProcesos);

    void edit(GenericaNotifProcesos genericaNotifProcesos);

    void remove(GenericaNotifProcesos genericaNotifProcesos);

    GenericaNotifProcesos find(Object id);
    
    Empleado findEmpleadoByEmail(String email);

    List<GenericaNotifProcesos> findAll(int idGopUnidadFuncional);

    List<Empleado> getEmployeesEmail(int idGopUnidadFuncional);

    List<GenericaNotifProcesos> findRange(int[] range);

    int count();

}
