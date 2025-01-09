/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadPrgTc;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface NovedadPrgTcFacadeLocal {

    void create(NovedadPrgTc novedadPrgTc);

    void edit(NovedadPrgTc novedadPrgTc);

    void remove(NovedadPrgTc novedadPrgTc);
    
    void createList(List<NovedadPrgTc> list);

    NovedadPrgTc find(Object id);

    List<NovedadPrgTc> findAll();

    List<NovedadPrgTc> findAllByFechas(Date desde, Date hasta, int idGopUnidadFuncional);

    List<NovedadPrgTc> findTqByFechas(Date desde, Date hasta);
    
    List<NovedadPrgTc> findNovedadAusentismoPrgTcByFechas(Date desde, Date hasta, int idGopUnidadFuncional);

    List<NovedadPrgTc> findRange(int[] range);

    int count();

}
