/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MultaTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface MultaTipoFacadeLocal {

    void create(MultaTipo multaTipo);

    void edit(MultaTipo multaTipo);

    void remove(MultaTipo multaTipo);

    MultaTipo find(Object id);

    List<MultaTipo> findAll();
    
    List<MultaTipo> findEstaRegis();

    List<MultaTipo> findRange(int[] range);

    int count();
    
}
