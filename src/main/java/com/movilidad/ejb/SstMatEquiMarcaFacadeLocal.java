/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstMatEquiMarca;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstMatEquiMarcaFacadeLocal {

    void create(SstMatEquiMarca sstMatEquiMarca);

    void edit(SstMatEquiMarca sstMatEquiMarca);

    void remove(SstMatEquiMarca sstMatEquiMarca);

    SstMatEquiMarca find(Object id);

    List<SstMatEquiMarca> findAll();
    
    List<SstMatEquiMarca> findAllByEstadoReg();

    List<SstMatEquiMarca> findRange(int[] range);

    int count();
    
    List<SstMatEquiMarca> findAllEstadoReg(Integer idSstEmpresa);

}