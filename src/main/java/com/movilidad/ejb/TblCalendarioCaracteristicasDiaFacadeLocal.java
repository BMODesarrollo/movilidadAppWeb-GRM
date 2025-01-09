/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TblCalendarioCaracteristicasDia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface TblCalendarioCaracteristicasDiaFacadeLocal {

    void create(TblCalendarioCaracteristicasDia tblCalendarioCaracteristicasDia);

    void edit(TblCalendarioCaracteristicasDia tblCalendarioCaracteristicasDia);

    void remove(TblCalendarioCaracteristicasDia tblCalendarioCaracteristicasDia);

    TblCalendarioCaracteristicasDia find(Object id);

    TblCalendarioCaracteristicasDia findByNombre(Integer idRegistro, String nombre);

    List<TblCalendarioCaracteristicasDia> findAll();
    
    List<TblCalendarioCaracteristicasDia> findAllByEstadoReg();
    
    List<TblCalendarioCaracteristicasDia> findAllByCampo(String campo);

    List<TblCalendarioCaracteristicasDia> findRange(int[] range);

    int count();

}
