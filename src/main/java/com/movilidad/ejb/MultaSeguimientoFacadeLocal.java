/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MultaSeguimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface MultaSeguimientoFacadeLocal {

    void create(MultaSeguimiento multaSeguimiento);

    void edit(MultaSeguimiento multaSeguimiento);

    void remove(MultaSeguimiento multaSeguimiento);

    MultaSeguimiento find(Object id);

    List<MultaSeguimiento> findAll();
    
    List<MultaSeguimiento> estadoRegistro();

    List<MultaSeguimiento> findRange(int[] range);

    int count();
    
    List<MultaSeguimiento> obtenerMS(int i);
    
}
