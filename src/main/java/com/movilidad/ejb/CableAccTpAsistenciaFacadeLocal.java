/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccTpAsistencia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface CableAccTpAsistenciaFacadeLocal {

    void create(CableAccTpAsistencia cableAccTpAsistencia);

    void edit(CableAccTpAsistencia cableAccTpAsistencia);

    void remove(CableAccTpAsistencia cableAccTpAsistencia);

    CableAccTpAsistencia find(Object id);

    List<CableAccTpAsistencia> findAll();

    List<CableAccTpAsistencia> findRange(int[] range);

    int count();
    
    List<CableAccTpAsistencia> findAllEstadoReg();
    
}
