/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmGrupoDetalle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaPmGrupoDetalleFacadeLocal {

    void create(GenericaPmGrupoDetalle genericaPmGrupoDetalle);

    void edit(GenericaPmGrupoDetalle genericaPmGrupoDetalle);

    void remove(GenericaPmGrupoDetalle genericaPmGrupoDetalle);

    GenericaPmGrupoDetalle find(Object id);

    List<GenericaPmGrupoDetalle> findAll();

    List<GenericaPmGrupoDetalle> findRange(int[] range);

    int count();

    GenericaPmGrupoDetalle findByIdEmpleadoAndIdGrupoPm(int idGrupo, int idEmpleado);
}
