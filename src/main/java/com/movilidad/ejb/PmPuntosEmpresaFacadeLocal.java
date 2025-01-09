/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmPuntosEmpresa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pc
 */
@Local
public interface PmPuntosEmpresaFacadeLocal {

    void create(PmPuntosEmpresa pmPuntosEmpresa);

    void edit(PmPuntosEmpresa pmPuntosEmpresa);

    void remove(PmPuntosEmpresa pmPuntosEmpresa);

    PmPuntosEmpresa find(Object id);

    List<PmPuntosEmpresa> findAll();

    List<PmPuntosEmpresa> findRange(int[] range);

    int count();

    public List<PmPuntosEmpresa> findallEst();
    
}
