/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface NovedadTipoFacadeLocal {

    void create(NovedadTipo novedadTipo);

    void edit(NovedadTipo novedadTipo);

    void remove(NovedadTipo novedadTipo);

    NovedadTipo find(Object id);

    List<NovedadTipo> findAll();

    List<NovedadTipo> findAllEstadoReg();

    List<NovedadTipo> obtenerTipos();

    List<NovedadTipo> findRange(int[] range);

    int count();

    List<NovedadTipo> findAllAfectaPm(int opc);

    List<NovedadTipo> findAllAfectaDisp();

    List<NovedadTipo> findAllByNovTipDetMtto();
    
    List<NovedadTipo> findAllByNovTipTC();//tipo de novedades que se cargan para el rol TC
    
    NovedadTipo findByIdNovedadTipo(int idNovedadTipo);
    
}
