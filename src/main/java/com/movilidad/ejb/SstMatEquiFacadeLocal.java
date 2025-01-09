/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstMatEqui;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstMatEquiFacadeLocal {

    void create(SstMatEqui sstMatEqui);

    void edit(SstMatEqui sstMatEqui);

    void remove(SstMatEqui sstMatEqui);

    SstMatEqui find(Object id);
    
    SstMatEqui findByNombre(String nombreMaterial,Integer idSstEmpresa);

    List<SstMatEqui> findAll();
    
    List<SstMatEqui> findAllEstadoReg();

    List<SstMatEqui> findRange(int[] range);

    int count();
    
    List<SstMatEqui> findAllEstadoReg(Integer idSstEmpresa);
    
}
