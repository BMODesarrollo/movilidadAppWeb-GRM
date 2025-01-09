/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmIngEgr;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaPmIngEgrFacadeLocal {

    void create(GenericaPmIngEgr genericaPmIngEgr);

    void edit(GenericaPmIngEgr genericaPmIngEgr);

    void remove(GenericaPmIngEgr genericaPmIngEgr);

    GenericaPmIngEgr find(Object id);
    
    GenericaPmIngEgr verificarRegistro(Integer idRegistro, Integer idEmpleado, Date fecha, Integer idConcepto,Integer idArea);

    List<GenericaPmIngEgr> findAll();
    
    List<GenericaPmIngEgr> findAllByDateRange(Date fechaIni, Date fechaFin,Integer idArea);
    
    List<GenericaPmIngEgr> findAllByEstadoReg(Integer idArea);

    List<GenericaPmIngEgr> findRange(int[] range);

    int count();
    
}
