/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ContableCtaTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface ContableCtaTipoFacadeLocal {

    void create(ContableCtaTipo contableCtaTipo);

    void edit(ContableCtaTipo contableCtaTipo);

    void remove(ContableCtaTipo contableCtaTipo);

    ContableCtaTipo find(Object id);

    List<ContableCtaTipo> findAll();

    List<ContableCtaTipo> findRange(int[] range);

    int count();
    
    List<ContableCtaTipo> findAllEstadoReg();
    
}
