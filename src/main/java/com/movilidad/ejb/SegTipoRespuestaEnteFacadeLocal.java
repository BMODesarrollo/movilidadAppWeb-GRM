/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegTipoRespuestaEnte;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegTipoRespuestaEnteFacadeLocal {

    void create(SegTipoRespuestaEnte segTipoRespuestaEnte);

    void edit(SegTipoRespuestaEnte segTipoRespuestaEnte);

    void remove(SegTipoRespuestaEnte segTipoRespuestaEnte);

    SegTipoRespuestaEnte find(Object id);

    List<SegTipoRespuestaEnte> findAll();

    List<SegTipoRespuestaEnte> findRange(int[] range);

    int count();

    List<SegTipoRespuestaEnte> findAllByEstadoReg();

    SegTipoRespuestaEnte findByNombre(String nombre, int idSegTipoRespuestaEnte);
}
