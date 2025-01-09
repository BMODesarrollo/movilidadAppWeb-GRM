/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableEventoTipoDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableEventoTipoDetFacadeLocal {

    void create(CableEventoTipoDet cableEventoTipoDet);

    void edit(CableEventoTipoDet cableEventoTipoDet);

    void remove(CableEventoTipoDet cableEventoTipoDet);

    CableEventoTipoDet find(Object id);

    CableEventoTipoDet findByCodigo(String codigo, Integer idRegistro);

    CableEventoTipoDet findByNombre(String nombre, Integer idRegistro);
    
    CableEventoTipoDet verifyByClaseEvento(int claseEvento, Integer idRegistro);

    CableEventoTipoDet findByClaseEvento(Integer claseEvento);

    List<CableEventoTipoDet> findAll();

    List<CableEventoTipoDet> findAllByEstadoReg();

    List<CableEventoTipoDet> findAllByTipoEvento(Integer idCableEventoTipo);

    List<CableEventoTipoDet> findRange(int[] range);

    int count();

}
