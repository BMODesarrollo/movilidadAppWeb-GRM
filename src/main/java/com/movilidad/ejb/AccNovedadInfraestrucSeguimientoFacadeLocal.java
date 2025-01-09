/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadInfraestrucSeguimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccNovedadInfraestrucSeguimientoFacadeLocal {

    void create(AccNovedadInfraestrucSeguimiento accNovedadInfraestrucSeguimiento);

    void edit(AccNovedadInfraestrucSeguimiento accNovedadInfraestrucSeguimiento);

    void remove(AccNovedadInfraestrucSeguimiento accNovedadInfraestrucSeguimiento);

    AccNovedadInfraestrucSeguimiento find(Object id);

    List<AccNovedadInfraestrucSeguimiento> findAll();
    
    List<AccNovedadInfraestrucSeguimiento> findByNovedad(Integer idNovedad);

    List<AccNovedadInfraestrucSeguimiento> findRange(int[] range);

    int count();
    
}
