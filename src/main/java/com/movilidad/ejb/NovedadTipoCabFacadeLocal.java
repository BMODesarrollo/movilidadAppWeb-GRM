/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipoCab;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadTipoCabFacadeLocal {

    void create(NovedadTipoCab novedadTipoCab);

    void edit(NovedadTipoCab novedadTipoCab);

    void remove(NovedadTipoCab novedadTipoCab);

    NovedadTipoCab find(Object id);

    NovedadTipoCab findByNombre(String nombre, Integer idRegistro);

    List<NovedadTipoCab> findAll();

    List<NovedadTipoCab> findAllByEstadoReg();

    List<NovedadTipoCab> findRange(int[] range);

    int count();
}
