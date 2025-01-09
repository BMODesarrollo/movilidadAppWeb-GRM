/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEmpresaTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstEmpresaTipoFacadeLocal {

    void create(SstEmpresaTipo sstEmpresaTipo);

    void edit(SstEmpresaTipo sstEmpresaTipo);

    void remove(SstEmpresaTipo sstEmpresaTipo);

    SstEmpresaTipo find(Object id);
    
    SstEmpresaTipo findByNombre(String nombre, Integer idRegistro);

    List<SstEmpresaTipo> findAllByEstadoReg();
    
    List<SstEmpresaTipo> findAll();

    List<SstEmpresaTipo> findRange(int[] range);

    int count();
    
}
