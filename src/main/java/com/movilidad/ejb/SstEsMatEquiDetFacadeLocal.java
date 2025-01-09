/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEsMatEquiDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstEsMatEquiDetFacadeLocal {

    void create(SstEsMatEquiDet sstEsMatEquiDet);

    void edit(SstEsMatEquiDet sstEsMatEquiDet);

    void remove(SstEsMatEquiDet sstEsMatEquiDet);
    
    void removeDetallesByEsMatEquiId(Integer sstEsMatEquiId);

    SstEsMatEquiDet find(Object id);

    List<SstEsMatEquiDet> findAll();

    List<SstEsMatEquiDet> findRange(int[] range);

    int count();
    
}
