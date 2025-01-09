/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GopUnidadFuncional;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GopUnidadFuncionalFacadeLocal {

    void create(GopUnidadFuncional gopUnidadFuncional);

    void edit(GopUnidadFuncional gopUnidadFuncional);

    void remove(GopUnidadFuncional gopUnidadFuncional);

    GopUnidadFuncional find(Object id);

    List<GopUnidadFuncional> findAll();

    List<GopUnidadFuncional> findRange(int[] range);

    int count();

    List<GopUnidadFuncional> findAllByEstadoReg();

    GopUnidadFuncional findByNombre(String nombre, int idGopUnidadFuncional);

    GopUnidadFuncional findByCodigo(String codigo, int idGopUnidadFuncional);
    
    GopUnidadFuncional findByCodigo(String codigo);
}
