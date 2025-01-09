/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccReincidencia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface AccReincidenciaFacadeLocal {

    void create(AccReincidencia accReincidencia);

    void edit(AccReincidencia accReincidencia);

    void remove(AccReincidencia accReincidencia);

    AccReincidencia find(Object id);

    List<AccReincidencia> findAll();

    List<AccReincidencia> findRange(int[] range);

    int count();

    List<AccReincidencia> findAllEstadoReg();

    AccReincidencia findByDiasAndIdNovTipoDetalles(Integer idAccReincidencia, Integer idNovTipoDetalle);
}
