/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegTipoSancion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegTipoSancionFacadeLocal {

    void create(SegTipoSancion segTipoSancion);

    void edit(SegTipoSancion segTipoSancion);

    void remove(SegTipoSancion segTipoSancion);

    SegTipoSancion find(Object id);

    List<SegTipoSancion> findAll();

    List<SegTipoSancion> findRange(int[] range);

    int count();

    List<SegTipoSancion> findAllByEstadoReg();

    SegTipoSancion findByNombre(String nombre, int idSegTipoSancion);
}
