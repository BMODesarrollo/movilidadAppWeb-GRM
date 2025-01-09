/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgClasificacionMotivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface PrgClasificacionMotivoFacadeLocal {

    void create(PrgClasificacionMotivo prgClasificacionMotivo);

    void edit(PrgClasificacionMotivo prgClasificacionMotivo);

    void remove(PrgClasificacionMotivo prgClasificacionMotivo);

    PrgClasificacionMotivo find(Object id);

    List<PrgClasificacionMotivo> findAll();

    List<PrgClasificacionMotivo> findRange(int[] range);

    int count();

    List<PrgClasificacionMotivo> findByEstadoReg();

    PrgClasificacionMotivo findBynombreAndIdPrgResponsable(String nombre, int idPrgResponsable, int idPrgClasificacionMotivo);
    
    List<PrgClasificacionMotivo> findByIdPrgResponsableEstadoReg(Integer idPrgResponsable);
}
