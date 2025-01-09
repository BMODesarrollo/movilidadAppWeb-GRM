/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmDiasEmpresa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pc
 */
@Local
public interface PmDiasEmpresaFacadeLocal {

    void create(PmDiasEmpresa pmDiasEmpresa);

    void edit(PmDiasEmpresa pmDiasEmpresa);

    void remove(PmDiasEmpresa pmDiasEmpresa);

    PmDiasEmpresa find(Object id);

    List<PmDiasEmpresa> findAll();

    List<PmDiasEmpresa> findRange(int[] range);

    int count();

    public List<PmDiasEmpresa> findallEst();
    
}
