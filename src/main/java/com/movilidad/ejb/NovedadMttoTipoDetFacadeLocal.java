/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMttoTipoDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadMttoTipoDetFacadeLocal {

    void create(NovedadMttoTipoDet novedadMttoTipoDet);

    void edit(NovedadMttoTipoDet novedadMttoTipoDet);

    void remove(NovedadMttoTipoDet novedadMttoTipoDet);

    NovedadMttoTipoDet find(Object id);

    NovedadMttoTipoDet findByNombre(String nombre, Integer idRegistro,Integer idNovedadMttoTipo);

    List<NovedadMttoTipoDet> findAllByEstadoReg();

    List<NovedadMttoTipoDet> findAll();

    List<NovedadMttoTipoDet> findRange(int[] range);

    int count();

}
