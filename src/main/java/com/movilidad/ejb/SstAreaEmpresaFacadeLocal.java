/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstAreaEmpresa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface SstAreaEmpresaFacadeLocal {

    void create(SstAreaEmpresa sstAreaEmpresa);

    void edit(SstAreaEmpresa sstAreaEmpresa);

    void remove(SstAreaEmpresa sstAreaEmpresa);

    SstAreaEmpresa find(Object id);

    List<SstAreaEmpresa> findAll();

    List<SstAreaEmpresa> findRange(int[] range);

    int count();

    List<SstAreaEmpresa> findAllEstadoReg();
    
}
