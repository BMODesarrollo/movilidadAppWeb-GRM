/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegEstadoMedioComunica;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegEstadoMedioComunicaFacadeLocal {

    void create(SegEstadoMedioComunica segEstadoMedioComunica);

    void edit(SegEstadoMedioComunica segEstadoMedioComunica);

    void remove(SegEstadoMedioComunica segEstadoMedioComunica);

    SegEstadoMedioComunica find(Object id);

    List<SegEstadoMedioComunica> findAll();

    List<SegEstadoMedioComunica> findRange(int[] range);

    int count();

    List<SegEstadoMedioComunica> findRangoFechaEstadoReg(Date desde, Date hasta);
}
