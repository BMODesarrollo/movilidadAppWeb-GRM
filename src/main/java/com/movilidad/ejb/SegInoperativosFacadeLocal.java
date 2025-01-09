/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegInoperativos;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegInoperativosFacadeLocal {

    void create(SegInoperativos segInoperativos);

    void edit(SegInoperativos segInoperativos);

    void remove(SegInoperativos segInoperativos);

    SegInoperativos find(Object id);

    List<SegInoperativos> findAll();

    List<SegInoperativos> findRange(int[] range);

    int count();

    List<SegInoperativos> findByRangoAndEstadoReg(Date desde, Date hasta);

    SegInoperativos findByFechaEventoAndIdEmpleado(int idEmpleado, Date fechaEvento, int idSegInoperativos);

    /**
     *
     * @param idEmpleado
     * @param fechaEvento
     * @param idSegInoperativos
     * @return
     */
    SegInoperativos findByFechaSancionBetweeHabilitacionnAndIdEmpleado(int idEmpleado, Date fechaEvento);
}
