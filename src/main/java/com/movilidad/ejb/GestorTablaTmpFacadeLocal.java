/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorTablaTmp;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GestorTablaTmpFacadeLocal {

    void create(GestorTablaTmp gestorTablaTmp);

    void edit(GestorTablaTmp gestorTablaTmp);

    void remove(GestorTablaTmp gestorTablaTmp);

    GestorTablaTmp find(Object id);

    List<GestorTablaTmp> findAll();

    List<GestorTablaTmp> findAllByIdEmpleadoCargo(Integer idEmpleadoCargo, int idGopUnidadFuncional);
    
    List<GestorTablaTmp> findAllByUf(Integer idEmpleadoCargo, int idGopUnidadFuncional);

    List<GestorTablaTmp> findRange(int[] range);

    int count();

}
