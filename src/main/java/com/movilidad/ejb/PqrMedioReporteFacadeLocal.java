/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrMedioReporte;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PqrMedioReporteFacadeLocal {

    void create(PqrMedioReporte pqrMedioReporte);

    void edit(PqrMedioReporte pqrMedioReporte);

    void remove(PqrMedioReporte pqrMedioReporte);

    PqrMedioReporte find(Object id);
    
    PqrMedioReporte verificarRegistro(Integer idRegistro, String nombre);

    List<PqrMedioReporte> findAll();
    
    List<PqrMedioReporte> findAllByEstadoReg();

    List<PqrMedioReporte> findRange(int[] range);

    int count();
    
}
