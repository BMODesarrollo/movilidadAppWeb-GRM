/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovRequerimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GestorNovRequerimientoFacadeLocal {

    void create(GestorNovRequerimiento gestorNovRequerimiento);

    void edit(GestorNovRequerimiento gestorNovRequerimiento);

    void remove(GestorNovRequerimiento gestorNovRequerimiento);

    GestorNovRequerimiento find(Object id);

    GestorNovRequerimiento findByNombre(Integer idRegistro, String nombre);

    List<GestorNovRequerimiento> findAll();

    List<GestorNovRequerimiento> findAllByEstadoReg();

    List<GestorNovRequerimiento> findRange(int[] range);

    int count();

}
