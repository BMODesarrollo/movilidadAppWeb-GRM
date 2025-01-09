/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegMedioReporta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegMedioReportaFacadeLocal {

    void create(SegMedioReporta segMedioReporta);

    void edit(SegMedioReporta segMedioReporta);

    void remove(SegMedioReporta segMedioReporta);

    SegMedioReporta find(Object id);

    List<SegMedioReporta> findAll();

    List<SegMedioReporta> findRange(int[] range);

    int count();

    List<SegMedioReporta> findAllByEstadoReg();

    SegMedioReporta findByNombre(String nombre, int idSegMedioReporta);
}
