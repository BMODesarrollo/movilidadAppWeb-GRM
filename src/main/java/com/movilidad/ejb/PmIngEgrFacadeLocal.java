/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmIngEgr;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PmIngEgrFacadeLocal {

    void create(PmIngEgr pmIngEgr);

    void edit(PmIngEgr pmIngEgr);

    void remove(PmIngEgr pmIngEgr);

    PmIngEgr find(Object id);

    PmIngEgr verificarRegistro(Integer idRegistro, Integer idEmpleado, Date fecha, Integer idConcepto);

    List<PmIngEgr> findAll();

    List<PmIngEgr> findAllByDateRange(Date fechaIni, Date fechaFin);

    List<PmIngEgr> findAllByEstadoReg();

    List<PmIngEgr> findRange(int[] range);

    int count();

}
