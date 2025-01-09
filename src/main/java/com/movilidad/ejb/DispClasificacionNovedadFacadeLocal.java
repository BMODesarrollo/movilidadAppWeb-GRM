/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispClasificacionNovedad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface DispClasificacionNovedadFacadeLocal {

    void create(DispClasificacionNovedad dispClasificacionNovedad);

    void edit(DispClasificacionNovedad dispClasificacionNovedad);

    void remove(DispClasificacionNovedad dispClasificacionNovedad);

    DispClasificacionNovedad find(Object id);

    List<DispClasificacionNovedad> findAll();

    List<DispClasificacionNovedad> findRange(int[] range);

    int updateSetestadoPendActual(Integer idDispClasificacionNovedad,
            Integer IdDispEstadoPendActual, String username);

    int count();

}
