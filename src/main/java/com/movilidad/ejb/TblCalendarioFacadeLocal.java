/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TblCalendario;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface TblCalendarioFacadeLocal {

    void create(TblCalendario tblCalendario);

    void edit(TblCalendario tblCalendario);

    void remove(TblCalendario tblCalendario);

    TblCalendario find(Object id);

    TblCalendario findByFechaAndIdCaracteristica(Integer idRegistro, Integer idCaracteristica, Date fecha, int idGopUnidadFuncional);

    List<TblCalendario> findAll();

    List<TblCalendario> findAllByDateRange(Date desde, Date hasta, int idGopUnidadFuncional);

    List<TblCalendario> findRange(int[] range);

    int count();

}
