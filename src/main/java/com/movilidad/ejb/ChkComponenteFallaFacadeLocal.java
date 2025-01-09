/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ChkComponenteFalla;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface ChkComponenteFallaFacadeLocal {

    void create(ChkComponenteFalla chkComponenteFalla);

    void edit(ChkComponenteFalla chkComponenteFalla);

    void remove(ChkComponenteFalla chkComponenteFalla);

    ChkComponenteFalla find(Object id);

    ChkComponenteFalla findByNombreAndComponente(Integer idChkComponenteFalla, String nombre, Integer idChkComponente);

    ChkComponenteFalla verificarAfectaDisponibilidad(Integer idChkComponenteFalla, String nombre, Integer idChkComponente, Integer idNovedadTipo, Integer idNovedadTipoDetalle, Integer idDispSistema);

    List<ChkComponenteFalla> findAll();

    List<ChkComponenteFalla> findAllByEstadoReg();

    List<ChkComponenteFalla> findRange(int[] range);

    int count();

}
