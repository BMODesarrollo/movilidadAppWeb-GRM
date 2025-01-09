/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgTarea;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface PrgTareaFacadeLocal {

    void create(PrgTarea prgTarea);

    void edit(PrgTarea prgTarea);

    void remove(PrgTarea prgTarea);

    PrgTarea find(Object id);

    PrgTarea findByNombreTareaAndIdGopUnidadFuncional(String nombreTarea, int idGopUnidadFuncional, int idPrgTarea);

    List<PrgTarea> findallEst();

    List<PrgTarea> findAllByIdGopUnidadFuncional(int idGopUnidadFuncional);

    List<PrgTarea> findRange(int[] range);

    List<PrgTarea> obtenerServicios();

    int count();

    List<PrgTarea> findFromAddServices(int idGopUnidadFuncional);

    List<PrgTarea> findAllTareasSumDistancia(int idGopUnidadFunc);
}
