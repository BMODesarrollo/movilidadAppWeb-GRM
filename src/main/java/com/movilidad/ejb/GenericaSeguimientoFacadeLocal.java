/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaSeguimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaSeguimientoFacadeLocal {

    void create(GenericaSeguimiento genericaSeguimiento);

    void edit(GenericaSeguimiento genericaSeguimiento);

    void remove(GenericaSeguimiento genericaSeguimiento);

    GenericaSeguimiento find(Object id);

    List<GenericaSeguimiento> findAll();

    List<GenericaSeguimiento> findByNovedad(int id);

    List<GenericaSeguimiento> findRange(int[] range);

    int count();

}
