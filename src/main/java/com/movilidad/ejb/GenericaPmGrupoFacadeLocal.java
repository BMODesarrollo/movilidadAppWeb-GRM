/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmGrupo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaPmGrupoFacadeLocal {

    void create(GenericaPmGrupo genericaPmGrupo);

    void edit(GenericaPmGrupo genericaPmGrupo);

    void remove(GenericaPmGrupo genericaPmGrupo);

    GenericaPmGrupo find(Object id);

    List<GenericaPmGrupo> findAll();

    List<GenericaPmGrupo> findRange(int[] range);

    List<GenericaPmGrupo> getByIdArea(int idArea);

    int count();

}
