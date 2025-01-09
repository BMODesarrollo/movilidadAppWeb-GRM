/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MyAppCambioTarea;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface MyAppCambioTareaFacadeLocal {

    void create(MyAppCambioTarea myAppCambioTarea);

    void edit(MyAppCambioTarea myAppCambioTarea);

    void remove(MyAppCambioTarea myAppCambioTarea);

    MyAppCambioTarea find(Object id);

    List<MyAppCambioTarea> findAll();

    List<MyAppCambioTarea> findRange(int[] range);

    int count();

    /**
     * Permite consultar los registros con estado de registro 0
     *
     * @return List<MyAppCambioTarea>
     */
    List<MyAppCambioTarea> findAllEstadoReg();

    /**
     * Permite consultar los registros con estado de registro 0, por fecha y
     * unidad funcional del empleado
     *
     * @param desde Fecha Desde
     * @param hasta Fecha Hasta
     * @param idGopUF Identificador UF
     * @return List<MyAppCambioTarea>
     */
    List<MyAppCambioTarea> findAllByEstadoRegAndIdGopUFAndFechas(Date desde, Date hasta, Integer idGopUF);

}
