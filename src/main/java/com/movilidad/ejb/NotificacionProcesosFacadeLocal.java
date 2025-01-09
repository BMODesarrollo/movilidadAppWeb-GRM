/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionProcesos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface NotificacionProcesosFacadeLocal {

    void create(NotificacionProcesos notificacionProcesos);

    void edit(NotificacionProcesos notificacionProcesos);

    void remove(NotificacionProcesos notificacionProcesos);

    NotificacionProcesos find(Object id);

    NotificacionProcesos findByCodigoByIdGopUnidadFunc(String codigo, int idGopUnidadFuncional);

    NotificacionProcesos findByCodigo(String codigo);

    Empleado findEmpleadoByEmail(String email);

    List<NotificacionProcesos> findAll(int idGopUnidadFuncional);

    List<Empleado> getEmployeesEmail(int idGopUnidadFuncional);

    List<NotificacionProcesos> findRange(int[] range);

    int count();
}
