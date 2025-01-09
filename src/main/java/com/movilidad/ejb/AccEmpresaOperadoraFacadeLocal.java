/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccEmpresaOperadora;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccEmpresaOperadoraFacadeLocal {

    void create(AccEmpresaOperadora accEmpresaOperadora);

    void edit(AccEmpresaOperadora accEmpresaOperadora);

    void remove(AccEmpresaOperadora accEmpresaOperadora);

    AccEmpresaOperadora find(Object id);

    List<AccEmpresaOperadora> findAll();

    List<AccEmpresaOperadora> findRange(int[] range);

    int count();
    
    List<AccEmpresaOperadora> estadoReg();
    
}
