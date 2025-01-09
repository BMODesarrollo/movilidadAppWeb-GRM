/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmGrupo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface PmGrupoFacadeLocal {

    void create(PmGrupo pmGrupo);

    void edit(PmGrupo pmGrupo);

    void remove(PmGrupo pmGrupo);

    PmGrupo find(Object id);

    List<PmGrupo> findAll();

    List<PmGrupo> findRange(int[] range);

    int count();

    List<PmGrupo> findAllByUnidadFuncional(int idGopUnidadFuncional);
    
    List<PmGrupo> findAllActivos();
    
    PmGrupo findByName(String nombre);
}
