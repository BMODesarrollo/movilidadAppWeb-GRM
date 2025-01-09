/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstMatEquiTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstMatEquiTipoFacadeLocal {

    void create(SstMatEquiTipo sstMatEquiTipo);

    void edit(SstMatEquiTipo sstMatEquiTipo);

    void remove(SstMatEquiTipo sstMatEquiTipo);

    SstMatEquiTipo find(Object id);

    List<SstMatEquiTipo> findAll();
    
    List<SstMatEquiTipo> findAllByEstadoReg();

    List<SstMatEquiTipo> findRange(int[] range);

    int count();
    
}
